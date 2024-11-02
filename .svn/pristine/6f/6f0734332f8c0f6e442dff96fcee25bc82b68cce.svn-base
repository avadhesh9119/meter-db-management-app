package com.global.meter.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.global.meter.business.model.DevicesConfig;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.Devices;
import com.global.meter.common.model.Error;
import com.global.meter.common.model.MeterConfiguration;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.request.model.Device;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.DeviceCommandService;
import com.global.meter.service.DevicesCommandsLogService;
import com.global.meter.service.DevicesConfigService;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;

@RestController
@RequestMapping(value="/devices/commands")
public class DevicesCommandsController {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesCommandsController.class);
	
	@Autowired
	DevicesInfoRepository devicesInfoRepository;
	
	@Autowired
	Error error;

	@Autowired
	DevicesInfoService devicesInfoService;
	
	@Autowired
	DevicesConfigService devicesConfigService;
	
	@Autowired
	DeviceCommandService deviceCommandService;
	
	@Autowired
	DevicesCommandsLogService devicesCommandsLogService;
	
	@Autowired
	MeterResponse meterResponse;
	
	@Autowired
	CommonUtils commonUtils;
	
	@Autowired
	MetersCommandsConfiguration meterCommands;
	
	@RequestMapping(value = "/data/get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> readData(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		return deviceCommandService.getResponseReadEntity(deviceCommand);
	}
	
	@RequestMapping(value = "/data/get/instant" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> readInstantData(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		return deviceCommandService.getResponseReadEntity(deviceCommand);
	}
	
	@RequestMapping(value = "/data/get/namePlates" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> readNamePlatesData(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		return deviceCommandService.getResponseReadEntity(deviceCommand);
	}
	
	@RequestMapping(value = "/data/get/billing" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> readBillingData(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		return deviceCommandService.getResponseReadEntity(deviceCommand);
	}
	
	@RequestMapping(value = "/data/get/events" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> readEventsData(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		return deviceCommandService.getResponseReadEntity(deviceCommand);
	}
	
	@RequestMapping(value = "/data/get/dailyLP" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> readDailyLPData(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		return deviceCommandService.getResponseReadEntity(deviceCommand);
	}
	
	@RequestMapping(value = "/data/get/deltaLP" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> readDeltaLPData(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		return deviceCommandService.getResponseReadEntity(deviceCommand);
	}
	
	/**
	 * 
	 * @param deviceCommand
	 * @return
	 */
	@RequestMapping(value = "/data/write" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> writeData(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		return deviceCommandService.writeConnectDisconnectEntity(deviceCommand);
	}
	
	
	/**
	 * 
	 * @param deviceCommand
	 * @return
	 */
	@RequestMapping(value = "/update/config" , method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateConfigurations(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		int retry = Integer.parseInt(meterCommands.getConfigRetry());;
		Device device = deviceCommand.getDevice();
		LOG.info("Received "+  deviceCommand.getCommand() + " Commands to perform on meter: " + device.getPlainText());
		Devices devices = null;
		MeterConfiguration meterConfiguration = null;
		ResponseEntity<?> response = null;
		DevicesInfo deviceInfo = null;
		DevicesConfig devicesConfig = null;
		long currentTime = System.currentTimeMillis();
		boolean status = Constants.FLAG_FAILURE;
		
		if(!ConfigCommands.contains(deviceCommand.getCommand())){
			meterResponse.setMessage("NOT FOUND");
			meterResponse.setStatus(404);
			return new ResponseEntity<>(meterResponse, HttpStatus.NOT_FOUND);
		}
		
		if (device.getPlainText() != null) {
			deviceInfo = devicesInfoService.getDevicesInfo(device.getPlainText());
			
			if(deviceInfo == null) {
				meterResponse.setMessage("DEVICE NOT FOUND");
				meterResponse.setStatus(404);
				return new ResponseEntity<>(meterResponse, HttpStatus.OK);	
			}
			
			if(!Constants.Status.BUSY.equalsIgnoreCase(deviceInfo.getStatus()))
			{
				try {
					deviceInfo.setStatus(Constants.Status.BUSY);
					devicesInfoService.updateDevicesRow(deviceInfo);
					
					if(StringUtils.isEmpty(deviceCommand.getTrackingId()) && commonUtils.isValid()) {
						devicesConfig = new DevicesConfig();
						devicesConfig.setTracking_id(String.valueOf(System.nanoTime()));
						devicesConfig.setTot_attempts(1);
						devicesConfig.setCommand_val(deviceCommand.getCommandsVal());
						devicesConfig.setCommand_name(deviceCommand.getCommand());
						devicesConfig.setCommand_datetime(new Date(System.currentTimeMillis()));
						devicesConfig.setDevice_serial_number(deviceCommand.getDevice().getPlainText());
						devicesConfig.setOwner_name(deviceInfo.getOwner_name());
						devicesConfig.setSubdevision_name(deviceInfo.getSubdevision_name());
						devicesConfig.setSubstation_name(deviceInfo.getSubstation_name());
						devicesConfig.setFeeder_name(deviceInfo.getFeeder_name());
						devicesConfig.setDt_name(deviceInfo.getDt_name());
						devicesConfig.setMdas_datetime(new Date(System.currentTimeMillis()));	
						
					}
					else {
						devicesConfig = devicesConfigService.findByTrackingId(deviceCommand.getDevice().getPlainText(),
								deviceCommand.getTrackingId(), deviceCommand.getCommand());
						devicesConfig.setTot_attempts(devicesConfig.getTot_attempts() != null ? devicesConfig.getTot_attempts()+1 : 1);
					}
					
					try {
						CommonUtils commonUtils = new CommonUtils();
						devices = commonUtils.setDevicesInfo(deviceInfo, deviceCommand, currentTime);
						meterConfiguration = new MeterConfiguration();
						meterConfiguration.setDevicesInfo(devices);
						meterConfiguration.setCommandsVal(deviceCommand.getCommandsVal());
						meterConfiguration.setCommandsName(deviceCommand.getCommand());
						meterConfiguration.setActivateActivityCalDatetime(deviceCommand.getActivateActivityCalDatetime());
					} catch (IOException e) {
						LOG.error("Device info not available due to "+ e.getMessage());
						error.setMessage(e.getMessage());
						return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
					}
					
					LOG.info("Device Configurations info created & sending "+ deviceCommand.getCommand() +" command : " + device.getPlainText());
					response = deviceCommandService.getConfigsResponseEntity(meterConfiguration, deviceCommand.getCommand());
					LOG.info("Data Configuration response received from meter for configs update:" + device.getPlainText(), response.getBody());
					
					if(response != null && response.getStatusCode().value() == 200) {
						meterResponse = CommonUtils.getMapper().readValue(response.getBody().toString(),
								MeterResponse.class);
						if(Constants.SUCCESS.equalsIgnoreCase(meterResponse.getMessage())) {
							status = Constants.FLAG_SUCCESS;
							devicesConfig.setStatus(Constants.SUCCESS);
							devicesConfig.setCommand_completion_datetime(new Date(System.currentTimeMillis()));	
							
							if(ConfigCommands.PAYMENT_MODE.commandName.equalsIgnoreCase(deviceCommand.getCommand())) {
								deviceInfo.setDev_mode(deviceCommand.getCommandsVal().equalsIgnoreCase("1") ? 
										Constants.DevMode.PREPAID : Constants.DevMode.POSTPAID);
							}
						}
						else {
							if(devicesConfig.getTot_attempts() >= retry) {
								devicesConfig.setStatus(Constants.FAILURE);
								devicesConfig.setCommand_completion_datetime(new Date(System.currentTimeMillis()));
							}
							else
								devicesConfig.setStatus(Constants.IN_PROGRESS);
						}
					}
					/*
					deviceCommandService.logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
							deviceCommand.getCommand(), Constants.FLAG_SUCCESS, meterResponse.getMessage(),
							deviceCommand.getTrackingId());
					*/
					devicesConfig.setReason(devicesConfig.getReason() != null ? devicesConfig.getReason()+":"
							+meterResponse.getMessage() : meterResponse.getMessage());
					devicesConfigService.getDevicesConfigRepository().save(devicesConfig);
					
					//update last read datetime as per the command name
					if(Constants.FLAG_SUCCESS == status){
						//deviceInfo.setLastcommunicationdatetime(new Date(System.currentTimeMillis()));
					}
					
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
				}  catch (Exception e) {
					//Inserting log of api
					/*
					deviceCommandService.logDevicesCommand(deviceInfo, deviceInfo.getDevice_serial_number(),
							deviceCommand.getCommand(), status, meterResponse.getMessage(),
							String.valueOf(System.currentTimeMillis()));
					*/
					//devicesConfigService.getDevicesConfigRepository().save(devicesConfig);
					meterResponse.setMessage(e.getMessage());
					meterResponse.setStatus(500);
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
					return new ResponseEntity<>(meterResponse, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			else{
				meterResponse.setMessage(Constants.Status.BUSY);
			
				if(deviceInfo.getLastcommunicationdatetime()== null 
						|| commonUtils.dateDiffInMins(deviceInfo.getLastcommunicationdatetime()) > 5)
				{
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
				}
			}	
			
			return new ResponseEntity<>(meterResponse, HttpStatus.OK);
		}
		else { 
			LOG.error("Device Info is not present. Please contact to administrator.");
			meterResponse.setMessage("Device Info is not present. Please contact to administrator.");
			return new ResponseEntity<>(meterResponse, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/getDeviceCommandsLogList" , method = RequestMethod.GET,  produces = "application/json")
	public ResponseEntity<?> getCommandList(){
		LOG.info("Command received to get list of Devices Configurations from DB:");
		List<DeviceCommand> devicesCommandList = null;
		try {
			devicesCommandList = devicesCommandsLogService.getDeviceCommandLog("");
		} catch (Exception e) {
			devicesCommandList = new ArrayList<DeviceCommand>();
			LOG.error("Issue in fetching devices command logs due to :" + e.getMessage());
		}
		return new ResponseEntity<>(devicesCommandList, HttpStatus.OK);
		
	}
}
