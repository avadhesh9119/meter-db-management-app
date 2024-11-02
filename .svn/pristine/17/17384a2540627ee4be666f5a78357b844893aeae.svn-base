package com.global.meter.v3.inventive.controller;

import java.util.List;

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

import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;
import com.global.meter.v3.inventive.models.SingleConnectionScheduleCommandReq;
import com.global.meter.v3.inventive.service.SingleConnectionCommandExecutionService;
import com.global.meter.v3.inventive.service.SingleConnectionRetryService;

@RestController
@RequestMapping(value="single/connection/devices/commands")
public class SingleConnectionDevicesCommandsController {

	private static final Logger LOG = LoggerFactory.getLogger(SingleConnectionDevicesCommandsController.class);

	@Autowired
	SingleConnectionRetryService singleConnectionRetryService;
	
	@Autowired
	MetersCommandsConfiguration meterCommands;	

	@Autowired
	SingleConnectionCommandExecutionService singleConnectionCommandExecutionService;
	
	
	@RequestMapping(value = "/getSingleConnectionCommandsRetry" , method = RequestMethod.GET,  produces = "application/json")
	public ResponseEntity<?> getSingleConnectionCommandListWise(){
		LOG.info("Command received to get list of Single Connection Command Logs from DB:");
		List<SingleConnectionScheduleCommandReq> devicesCommandList = null;
		try {
			devicesCommandList = singleConnectionRetryService.getSingleConnectionCommandListWise();
		} catch (Exception e) {
			LOG.error("Issue in fetching single connection command logs due to :" + e.getMessage());
		}
		return new ResponseEntity<>(devicesCommandList, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/scheduler/retry", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> retrySingleConnectionCommandLogsByScheduler(
			@Valid @RequestBody SingleConnectionCommandReq connectionCommandLogsReq) {
		LOG.info("Request received to retry single connection command logs by scherdular for : "+connectionCommandLogsReq.getDevice().getPlainText());
		CommonResponse response = new CommonResponse();
		try {		
			singleConnectionCommandExecutionService.runSingleConnectionCommandLogs(connectionCommandLogsReq, response);
			LOG.info("Retry scheduler command logs by scheduler updated for: "+connectionCommandLogsReq.getDevice().getPlainText());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in retry single connection command logs by scherdular due to: " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}
