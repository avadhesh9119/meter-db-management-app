package com.global.meter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.global.meter.common.model.FullMeterDataResponse;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.DeviceCommandService;
import com.global.meter.service.FullDataCommandService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.MetersCommandsConfiguration;

@RestController
@RequestMapping(value="/devices/configs")
public class FullDataConfigsController {

	@Autowired
	FullDataCommandService fullDataCommandService;
	
	@Autowired
	MetersCommandsConfiguration meterCommands;
	
	@Autowired
	DeviceCommandService deviceCommandService;
	
	@RequestMapping(value = "/fullConfigurationProcess" , method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> readFullDataInSingleConnection(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		FullMeterDataResponse fullDataResponse = new FullMeterDataResponse();
		ResponseEntity<?> response = null;
		
		try {
			response = fullDataCommandService.writeMultipleConfifResponseEntity(deviceCommand, fullDataResponse);
			if(response != null && response.getStatusCode().value() == 200) {
				fullDataResponse = CommonUtils.getMapper().readValue(
						CommonUtils.getMapper().writeValueAsString(response.getBody()),
						FullMeterDataResponse.class);
			}
			
		} catch (Exception e) {
			fullDataResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(fullDataResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
		return new ResponseEntity<>(fullDataResponse, HttpStatus.OK);
	}
	
	
	
}
