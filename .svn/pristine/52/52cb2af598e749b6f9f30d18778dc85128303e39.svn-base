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
import org.springframework.web.client.RestTemplate;

import com.global.meter.data.repository.DeviceBatchLogsRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DeviceBatchLogsDataRequest;
import com.global.meter.inventive.service.DeviceBatchLogsService;

/**
 * 
 * @author Nitin Sethi
 *
 */
@RestController
@RequestMapping(value="/hes/devices")
public class DeviceBatchLogsController {

	private static final Logger LOG = LoggerFactory.getLogger(DeviceBatchLogsController.class);

	@Autowired
	DeviceBatchLogsService deviceBatchLogService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DeviceBatchLogsRepository deviceBatchLogsRepository;
	
	@RequestMapping(value = "/batch" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addDevice(@Valid @RequestBody DeviceBatchLogsDataRequest metersDataRequests)
	{
		LOG.info("Request receieved to add batch logs...");
		CommonResponse response = new CommonResponse();
		try {
			response = deviceBatchLogService.createLogs(metersDataRequests);
			LOG.info("Batch Information Logged Successfully.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding batch logs due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/batch/byId/get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getBatchById(@Valid @RequestBody DeviceBatchLogsDataRequest batchId)
	{
		LOG.info("Request receieved to get batch logs by batch id");
		CommonResponse response = new CommonResponse();
		try {
			response = deviceBatchLogService.getBatchById(batchId);
			LOG.info("Batch Information Logged Successfully.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in fetching batch logs due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/batch/byUser/get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getBatchByUser(@Valid @RequestBody DeviceBatchLogsDataRequest user)
	{
		LOG.info("Request receieved to get batch logs By Username");
		CommonResponse response = new CommonResponse();
		try {
			response = deviceBatchLogService.getBatchByUsername(user);
			LOG.info("Batch Information Logged Successfully.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting batch logs due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/batch" , method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateDevice(@Valid @RequestBody DeviceBatchLogsDataRequest metersDataRequests)
	{
		LOG.info("Request recieved to update Batch Informations.");
		CommonResponse response = new CommonResponse();
		try {
			
			response = deviceBatchLogService.updateLogs(metersDataRequests);
			
			LOG.info("Request success to update Batch Informations.");
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			LOG.error("Issue in updating Batch Informations due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}