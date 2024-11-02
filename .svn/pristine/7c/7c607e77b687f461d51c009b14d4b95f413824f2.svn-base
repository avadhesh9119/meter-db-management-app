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
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.DevicesConfigBatchLogsService;

@RestController
@RequestMapping(value="/hes/config/batch")
public class DevicesConfigBatchLogsController {
	
	@Autowired
	DevicesConfigBatchLogsService devicesConfigBatchLogsService;
	
private static final Logger LOG = LoggerFactory.getLogger(MeterCommCountController.class);
	
	
	
	@RequestMapping(value = "/process", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDevicesCommandsBatchLog(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get DevicesConfigBatchLogs");
		CommonResponse response = new CommonResponse();
		try {
			response = devicesConfigBatchLogsService.getDevicesConfigBatchLogs(req);
			LOG.info("Request success to get DevicesConfogBatchLogs");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While Process DevicesConfigBatchLogs due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value = "/process/drilldown", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDevicesConfigBatchDrillDown(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get DevicesConfigBatch Log Drill Down Info By: "+req);
		CommonResponse response = new CommonResponse();
		try {
			response = devicesConfigBatchLogsService.getDevicesConfigBatchDrillDown(req);
			LOG.info("Request success to get DevicesConfigBatch Log Drill Down Info: ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While Process DevicesConfigBatch Log Drill Down: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/read/process", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> saveConfigurationReadBatchData(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get configuration read batch data Info By: "+req);
		CommonResponse response = new CommonResponse();
		try {
			response = devicesConfigBatchLogsService.getDevicesConfigReadBatchData(req);
			LOG.info("Request success to read configuration read batch data Info: ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While getting configuration read batch data: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/read/process/drilldown", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDevicesConfigReadBatchDrillDown(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get configuration read batch Log Drill Down Info By: "+req);
		CommonResponse response = new CommonResponse();
		try {
			response = devicesConfigBatchLogsService.getDevicesConfigReadBatchDrillDown(req);
			LOG.info("Request success to get configuration read batch Log Drill Down Info: ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While Process configuration read batch Log Drill Down: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}

