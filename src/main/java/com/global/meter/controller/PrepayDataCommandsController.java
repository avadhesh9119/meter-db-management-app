package com.global.meter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.global.meter.common.model.FullDataMeterResponse;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.PrepayDataCommandService;
import com.global.meter.utils.CommonUtils;

@RestController
@RequestMapping(value="/devices/prepayData")
public class PrepayDataCommandsController {

	@Autowired
	PrepayDataCommandService prepayDataCommandService;

	
	@RequestMapping(value = "/oneGo/fullData/get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> readFullDataInSingleConnection(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		FullDataMeterResponse fullDataResponse = new FullDataMeterResponse();
		ResponseEntity<?> response = null;
		
		try {
			response = prepayDataCommandService.getResponseReadEntity(deviceCommand, fullDataResponse);
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
	
	
}
