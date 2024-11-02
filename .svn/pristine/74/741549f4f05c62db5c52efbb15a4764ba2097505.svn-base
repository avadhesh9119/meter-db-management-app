package com.global.meter.inventive.controller;

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
import com.global.meter.inventive.models.SchedulerDataReq;
import com.global.meter.inventive.serviceImpl.SchedulerConfigurationLogsService;

@RestController
@RequestMapping(value = "/hes/scheduler/logs")
public class SchedulerConfigurationLogsController {

	private static final Logger LOG = LoggerFactory.getLogger(SchedulerConfigurationLogsController.class);

	@Autowired
	SchedulerConfigurationLogsService schedulerConfigurationLogsService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addSchedulerConfigLogs(@Valid @RequestBody SchedulerDataReq schedulerDataReq) {
		LOG.info("Request recieved to add Scheduler config logs...");
		CommonResponse response = new CommonResponse();
		try {

			schedulerConfigurationLogsService.addSchedulerConfigLogs(schedulerDataReq);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in adding Scheduler config logs property due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get/by-tracking-id", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSchedulerConfigLogsByTrackingId(
			@Valid @RequestBody SchedulerDataReq schedulerDataReq) {
		LOG.info("Request recieved to add Scheduler config logs...");
		CommonResponse response = new CommonResponse();
		try {

			schedulerDataReq = schedulerConfigurationLogsService.getSchedulerConfigLogsByTrackingId(schedulerDataReq);
			return new ResponseEntity<>(schedulerDataReq, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in adding Scheduler config logs property due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSchedulerConfigLogs(
			@Valid @RequestBody SchedulerDataReq schedulerDataReq) {
		LOG.info("Request recieved to get Scheduler config logs...");
		CommonResponse response = new CommonResponse();
		try {

			response = schedulerConfigurationLogsService.getSchedulerConfigLogs(schedulerDataReq);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in adding Scheduler config logs property due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}
