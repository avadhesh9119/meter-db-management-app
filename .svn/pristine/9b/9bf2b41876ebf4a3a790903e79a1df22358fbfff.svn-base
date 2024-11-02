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

import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DevicesCommandRequest;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.DevicesCommandsBatchLogsService;

@RestController
@RequestMapping(value="/hes/command/batch")
public class DevicesCommandsBatchLogsController {
	
	@Autowired
	DevicesCommandsBatchLogsService devicesCommandBatchService;
	
private static final Logger LOG = LoggerFactory.getLogger(MeterCommCountController.class);
	
	
	
	@RequestMapping(value = "/process", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDevicesCommandsBatchLog(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get DevicesCommandsBatchLogs");
		CommonResponse response = new CommonResponse();
		try {
			response = devicesCommandBatchService.getDevicesCommandsBatchLogs(req);
			LOG.info("Request success to get DevicesCommandsBatchLogs");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While Process DevicesCommandsBatchLogs due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value = "/process/drilldown", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDevicesCommandsBatchDrillDown(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get DevicesCommandsBatch Log Drill Down Info By: "+req);
		CommonResponse response = new CommonResponse();
		try {
			response = devicesCommandBatchService.getDevicesCommandsBatchDrillDown(req);
			LOG.info("Request success to get DevicesCommandsBatch Log Drill Down Info: ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While Process DevicesCommandsBatch Log Drill Down: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get/command", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDevicesLogsByBatchId(@Valid @RequestBody DevicesCommandRequest req) {
		LOG.info("Request recieved to get devices commands informations by BatchId..." + req.getBatchId());
		CommonResponse response = new CommonResponse();
		try {
			
			if(!ConfigCommands.contains(req.getCommandName()) || req.getBatchId() == null || req.getBatchId().isEmpty()) {
				response.setMessage("Invalid Command or Missing Batch Id.");
				response.setCode(404);
				response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response = devicesCommandBatchService.getCommandLogByBatchId(req);
			LOG.info("Request success to get devices commands informations by BatchId...");

			return new ResponseEntity<>(response, HttpStatus.OK);
			

		} catch (Exception e) {
			LOG.error("Issue in getting devices commands informations by BatchId due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}

