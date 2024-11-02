package com.global.meter.controller;

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

import com.global.meter.common.model.MeterResponse;
import com.global.meter.request.model.DataBackup;
import com.global.meter.service.DataBackupService;

@RestController
@RequestMapping(value="/data/back-up")
public class DataBackupController {

	private static final Logger LOG = LoggerFactory.getLogger(DataBackupController.class);
	
	@Autowired
	DataBackupService dataBackupService;
	
	@RequestMapping(value = "/storeDeviceCmdLogs" , method = RequestMethod.POST,consumes = "application/json",  produces = "application/json")
	public ResponseEntity<?> storeDeviceCmdLogs(@Valid @RequestBody DataBackup deviceCommand){
		LOG.info("Command received to take backup of data:");
		MeterResponse response = new MeterResponse();
		try {
			dataBackupService.storeBackup(deviceCommand, response);
		} catch (Exception e) {
			response.setMessage("Data Backup is not done due to: "+ e.getMessage());
			response.setStatus(200);
			LOG.error("Data Backup is not done due to: "+ e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
}
