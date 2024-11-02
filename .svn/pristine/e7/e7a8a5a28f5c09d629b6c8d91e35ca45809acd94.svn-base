package com.global.meter.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.Error;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.DevicesConfigService;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.Constants;

@RestController
@RequestMapping(value="/devices/commands/configs")
public class DevicesConfigurationCommandsController {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesConfigurationCommandsController.class);
	
	@Autowired
	DevicesInfoService devicesInfoService;
	
	@Autowired
	DevicesConfigService devicesConfigService;
	
	@Autowired
	Error error;
	

	@RequestMapping(value = "/add" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addConfigs(@Valid @RequestBody DeviceCommand deviceCommand){
		MeterResponse meterResponse = new MeterResponse();
		List<DevicesInfo> devicesList = null;
		LOG.info("Command received to add devices configs for : "+ deviceCommand.getLevelName());
		try {
			if(!ConfigCommands.contains(deviceCommand.getCommand())){
				meterResponse.setMessage("NOT FOUND");
				meterResponse.setStatus(404);
				return new ResponseEntity<>(meterResponse, HttpStatus.NOT_FOUND);
			}
			
			devicesList = devicesInfoService.getDevicesList(deviceCommand.getLevelName(),
					deviceCommand.getLevelValue());
			
			
			LOG.info("Devices list retrieved based on :"+ deviceCommand.getLevelName());
			if(devicesList != null && devicesList.size() > 0){
				devicesConfigService.addConfigs(meterResponse, devicesList, deviceCommand);
				LOG.info("Configurations added for :"+ deviceCommand.getLevelValue());
			}
			else {
				meterResponse.setMessage("No Device found for input : " + deviceCommand.getLevelValue());
				meterResponse.setStatus(404);
			}
		} catch (Exception e) {
			LOG.error("Issue in adding configs due to :" + e.getMessage());
			meterResponse.setMessage("Issue in adding configs due to :" + e.getMessage());
			meterResponse.setStatus(500);
			return new ResponseEntity<>(meterResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(meterResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add/fulldata/cmd" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addFulldata(@Valid @RequestBody DeviceCommand deviceCommand){
		MeterResponse meterResponse = new MeterResponse();
		List<DevicesInfo> devicesList = null;
		try {
			
			devicesList = devicesInfoService.getAllDevicesInfo();
			
			if(devicesList != null && devicesList.size() > 0){
				devicesConfigService.addFullDataCmds(meterResponse, devicesList);
			}
			else {
				meterResponse.setMessage("No Device found for input : " );
				meterResponse.setStatus(404);
			}
		} catch (Exception e) {
			LOG.error("Issue in adding configs due to :" + e.getMessage());
			meterResponse.setMessage("Issue in adding configs due to :" + e.getMessage());
			meterResponse.setStatus(500);
			return new ResponseEntity<>(meterResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(meterResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/multiple/add" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> multipleAddConfigs(@Valid @RequestBody DeviceCommand deviceCommand){
		MeterResponse meterResponse = new MeterResponse();
		List<DevicesInfo> devicesList = null;
		LOG.info("Command received to add devices multiple configs for : "+ deviceCommand.getLevelName());
		try {
			
			Map<String, String> configVals = deviceCommand.getConfigVals();
			Map<String, String> configValsStatus = new LinkedHashMap<String, String>();
			
			for(Entry<String, String> configIterator: configVals.entrySet()){
				if(!ConfigCommands.contains(configIterator.getKey())){
					meterResponse.setMessage("NOT FOUND");
					meterResponse.setStatus(404);
					return new ResponseEntity<>(meterResponse, HttpStatus.NOT_FOUND);
				}
				else {
					if(ConfigCommands.ACTIVITY_CALENDER.commandName.equalsIgnoreCase(configIterator.getKey())
							&& StringUtils.isEmpty(deviceCommand.getActivateActivityCalDatetime())) {
						meterResponse.setMessage("In case of ActivityCalendar, activateActivityCalDatetime is also required along with commandsVal");
						meterResponse.setStatus(404);
						return new ResponseEntity<>(meterResponse, HttpStatus.NOT_FOUND);
					}
					configValsStatus.put(configIterator.getKey(), Constants.ADDED);
				}
			}
			
			devicesList = devicesInfoService.getDevicesList(deviceCommand.getLevelName(),
					deviceCommand.getLevelValue());
			deviceCommand.setConfigValsStatus(configValsStatus);
			
			LOG.info("Devices list retrieved based on :"+ deviceCommand.getLevelName());
			if(devicesList != null && devicesList.size() > 0){
				devicesConfigService.addMultipleConfigs(meterResponse, devicesList, deviceCommand);
				LOG.info("Multiple Configurations added for :"+ deviceCommand.getLevelName());
			}
		} catch (Exception e) {
			LOG.error("Issue in adding multiple configs due to :" + e.getMessage());
			meterResponse.setMessage("Issue in adding multiple configs due to :" + e.getMessage());
			meterResponse.setStatus(500);
			return new ResponseEntity<>(meterResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(meterResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getList" , method = RequestMethod.GET,  produces = "application/json")
	public ResponseEntity<?> getCommandList(){
		LOG.info("Command received to get list of Devices Configurations from DB:");
		List<DeviceCommand> devicesCommandList = null;
		try {
			devicesCommandList = devicesConfigService.getConfigs();
		} catch (Exception e) {
			devicesCommandList = new ArrayList<DeviceCommand>();
			LOG.error("Issue in fetching configs due to :" + e.getMessage());
		}
		return new ResponseEntity<>(devicesCommandList, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/fwupdate/getList" , method = RequestMethod.GET,  produces = "application/json")
	public ResponseEntity<?> getFwUpdateConfigList(){
		LOG.info("Command received to get list of Devices  for firmware upgrade Configurations from DB:");
		List<DeviceCommand> devicesCommandList = null;
		try {
			devicesCommandList = devicesConfigService.getFirmwareConfigs();
		} catch (Exception e) {
			devicesCommandList = new ArrayList<DeviceCommand>();
			LOG.error("Issue in fetching configs due to :" + e.getMessage());
		}
		return new ResponseEntity<>(devicesCommandList, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/addForAll" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addForAllConfigs(@Valid @RequestBody DeviceCommand deviceCommand){
		MeterResponse meterResponse = new MeterResponse();
		LOG.info("Command received to add devices configs for : "+ deviceCommand.getLevelName());
		try {
			if(!ConfigCommands.contains(deviceCommand.getCommand())){
				meterResponse.setMessage("Wrong configs name. Please check the constants.");
				meterResponse.setStatus(404);
				return new ResponseEntity<>(meterResponse, HttpStatus.NOT_FOUND);
			}
			
			List<String> ownersList = devicesInfoService.getAllOwnersList();
			
			StringBuilder levels = new StringBuilder();
			for (String owner : ownersList) {
				levels.append(owner).append(",");
			}
			String levelValue = levels.substring(0 , levels.length() - 1);
			deviceCommand.setLevelValue(levelValue);
			deviceCommand.setLevelName(Constants.HierLevelName.ALL);
			
			addConfigs(deviceCommand);
			
			meterResponse.setStatus(200);
			meterResponse.setMessage("Configs added successfully.");
		} catch (Exception e) {
			LOG.error("Issue in adding configs due to :" + e.getMessage());
			meterResponse.setMessage("Issue in adding configs due to :" + e.getMessage());
			meterResponse.setStatus(500);
			return new ResponseEntity<>(meterResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(meterResponse, HttpStatus.OK);
	}
	
}
