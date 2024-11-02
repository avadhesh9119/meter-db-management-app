package com.global.meter.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.FullDataMeterResponse;
import com.global.meter.common.model.FullDataModel;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.DeviceCommandService;
import com.global.meter.service.FullDataCommandService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;

@RestController
@RequestMapping(value="/devices/commands")
public class FullDataCommandsController {

	@Autowired
	FullDataCommandService fullDataCommandService;
	
	@Autowired
	MetersCommandsConfiguration meterCommands;
	
	@Autowired
	DeviceCommandService deviceCommandService;
	
	@Autowired
	CommonUtils commonUtils;
	
	@RequestMapping(value = "/oneGo/perDay/fullData" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> readPerDayFullDataInSingleConnection(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		FullDataMeterResponse fullDataResponse = new FullDataMeterResponse();
		ResponseEntity<?> response = null;
		
		try {
			response = fullDataCommandService.getFullDataPerDayResponseReadEntity(deviceCommand, fullDataResponse);
			if(response != null && response.getStatusCode().value() == 200 && commonUtils.isValid()) {
				fullDataResponse = CommonUtils.getMapper().readValue(
						CommonUtils.getMapper().writeValueAsString(response.getBody()),
						FullDataMeterResponse.class);
			}
			
		} catch (Exception e) {
			fullDataResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(fullDataResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
		if(fullDataResponse.getResponse() == null || fullDataResponse.getResponse().size() == 0) {
			return new ResponseEntity<>(fullDataResponse, HttpStatus.OK);	
		}
		return new ResponseEntity<>(fullDataResponse, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/oneGo/fullData/get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> readFullDataInSingleConnection(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		FullDataMeterResponse fullDataResponse = new FullDataMeterResponse();
		ResponseEntity<?> response = null;
		
		try {
			response = fullDataCommandService.getResponseReadEntity(deviceCommand, fullDataResponse);
			if(response != null && response.getStatusCode().value() == 200) {
				fullDataResponse = CommonUtils.getMapper().readValue(
						CommonUtils.getMapper().writeValueAsString(response.getBody()),
						FullDataMeterResponse.class);
			}
			
		} catch (Exception e) {
			fullDataResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(fullDataResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
		if(fullDataResponse.getResponse() == null || fullDataResponse.getResponse().size() == 0) {
			return new ResponseEntity<>(fullDataResponse, HttpStatus.OK);	
		}
		return new ResponseEntity<>(fullDataResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fullData/get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> readFullData(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		List<FullDataModel> responseList = new ArrayList<FullDataModel>();
		ResponseEntity<?> response = null;
		int Flag = 0;
		String msg = Constants.FAILURE;
		
		List<String> cmdList = new ArrayList<String>();
		cmdList.add(ConfigCommands.INSTANTANEOUS_READ.commandName);
		cmdList.add(ConfigCommands.BILLING_DATA.commandName);
		cmdList.add(ConfigCommands.DELTA_LOAD_PROFILE.commandName);
		cmdList.add(ConfigCommands.DAILY_LOAD_PROFILE.commandName);
		cmdList.add(ConfigCommands.VOLTAGE_RELATED_EVENTS.commandName);
		cmdList.add(ConfigCommands.CURRENT_RELATED_EVENTS.commandName);
		cmdList.add(ConfigCommands.POWER_RELATED_EVENTS.commandName);
		cmdList.add(ConfigCommands.TRANSACTION_RELATED_EVENTS.commandName);
		cmdList.add(ConfigCommands.OTHER_RELATED_EVENTS.commandName);
		cmdList.add(ConfigCommands.CONTROL_RELATED_EVENTS.commandName);

		for (String cmdName : cmdList) {
			
			do {
				FullDataModel fullDataModel = new FullDataModel();
				fullDataModel.setCmdName(cmdName);
				fullDataModel.setMessage(Constants.FAILURE);	
				
				try {
					Flag ++;
					msg = Constants.FAILURE;
					
					deviceCommand.setCommand(cmdName);
					response = deviceCommandService.getResponseReadEntity(deviceCommand);
					
					MeterResponse meterResponse = CommonUtils.getMapper().readValue(
							CommonUtils.getMapper().writeValueAsString(response.getBody()),
							MeterResponse.class);
					if(Constants.SUCCESS.equalsIgnoreCase(meterResponse.getMessage())) {
						fullDataModel.setMessage(Constants.SUCCESS);
						fullDataModel.setRetry(String.valueOf(Flag));
						msg = Constants.SUCCESS;
						responseList.add(fullDataModel);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(Flag == 5) {
					fullDataModel.setRetry(String.valueOf(Flag));
					responseList.add(fullDataModel);	
				}
			} while ((Flag < 5) && msg.equals(Constants.FAILURE));
			
			Flag = 0;
			msg = Constants.FAILURE;
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
	
	
	
}
