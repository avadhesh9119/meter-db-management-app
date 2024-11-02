package com.global.meter.inventive.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.Error;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.DevicesConfigurationsService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.Constants;
import com.global.meter.utils.Constants.CreateBatch;
import com.global.meter.utils.DateConverter;

@RestController
@RequestMapping(value="/hes/devices/configs")
public class ConfigurationController {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationController.class);
	
	@Autowired
	DevicesInfoService devicesInfoService;
	
	@Autowired
	DevicesConfigurationsService devicesConfigService;
	
	@Autowired
	Error error;
	
	@Autowired
	Constants constants;
	
	@Autowired
	DateConverter dateConverter;

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
				LOG.info("Configurations added for :"+ deviceCommand.getHier().getValue());
			}
			else {
				meterResponse.setMessage("No Device found for input : " + deviceCommand.getHier().getValue());
				meterResponse.setStatus(404);
			}
		} catch (Exception e) {
			LOG.error("Issue in adding configs due to :" + e.getMessage());
			meterResponse.setMessage("Issue in adding configs. Please check with admin.");
			meterResponse.setStatus(500);
			return new ResponseEntity<>(meterResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(meterResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addAll" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> multipleAddConfigs(@Valid @RequestBody MeterDataVisualizationReq req){
		CommonResponse response = new CommonResponse();
		List<DevicesInfo> devicesList = null;
		String levelName = ExternalConstants.getUILevelValue(req.getHier().getName());
		LOG.info("Command received to add devices multiple configs for : "+ levelName);
		try {
			
			Map<String, String> configVals = req.getConfigVals();
			Map<String, String> configValsStatus = new LinkedHashMap<String, String>();
			
			for(Entry<String, String> configIterator: configVals.entrySet()){
				if(!ConfigCommands.contains(configIterator.getKey())){
					response.setMessage("NOT FOUND");
					response.setCode(404);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
				if (ConfigCommands.LAST_TOKEN_RECHARGE_TIME.commandName.equalsIgnoreCase(configIterator.getKey())
						|| ConfigCommands.CURRENT_BALANCE_TIME.commandName.equalsIgnoreCase(configIterator.getKey())) {
					if(configIterator.getValue() != null && !configIterator.getValue().isEmpty()) {
						if (System.currentTimeMillis() < dateConverter.convertStringToDate(configIterator.getValue()).getTime()) {
							response.setMessage(Constants.CANT_USE_GREATER_DATE_TIME);
							response.setCode(240);
							return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
						}
					}
				}
				if(req.getSource().isEmpty() || req.getUserId().isEmpty()) {
					response.setMessage("Source & UserId can not be empty");
					response.setCode(404);
					response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
				else {
					/*
					if(ConfigCommands.ACTIVITY_CALENDER.commandName.equalsIgnoreCase(configIterator.getKey())
							&& StringUtils.isEmpty(deviceCommand.getActivateActivityCalDatetime())) {
						response.setMessage("In case of ActivityCalendar, activateActivityCalDatetime is also required along with commandsVal");
						response.setCode(404);
						return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
					}
					*/
					configValsStatus.put(configIterator.getKey(), Constants.ADDED);
				}
			}
			
			devicesList = devicesInfoService.getDevicesList(levelName,
					req);
			req.setConfigValsStatus(configValsStatus);
			
			LOG.info("Devices list retrieved based on :"+ levelName);
			if(devicesList != null && devicesList.size() > 0){
				
				String batchId =CreateBatch.BATCH+String.valueOf(System.nanoTime());
				devicesConfigService.addMultipleConfigs(response, devicesList, req, batchId);
				LOG.info("Multiple Configurations added for :"+ levelName);
				response.setMessage("Configs persisted to execute.");
				response.setBatchId(batchId);
				response.setConfigCmdVals(req.getConfigVals().keySet());
			}
			else {
				response.setMessage("Device not found for the selected manufacturer...");
				response.setError(true);
			}
		} catch (Exception e) {
			LOG.error("Issue in adding multiple configs due to :" + e.getMessage());
			response.setMessage("Issue in adding configs. Please check with admin.");
			response.setCode(500);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
