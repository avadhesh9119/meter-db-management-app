package com.global.meter.v3.inventive.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogsReq;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;
import com.global.meter.v3.inventive.service.SingleConnectionCommandService;

@RestController
@RequestMapping(value = "/hes/commandLog")
@Service
public class SingleConnectionCommandLogsController {

	private static final Logger LOG = LoggerFactory.getLogger(SingleConnectionCommandLogsController.class);

	@Autowired
	SingleConnectionCommandService singleConnectionCommandLogsService;

	@Autowired
	DevicesInfoService devicesInfoService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addSingleConnectionCommandLogs(
			@Valid @RequestBody SingleConnectionCommandReq req) {
		LOG.info("Request received to generate command queue");
		CommonResponse response = new CommonResponse();
		try {
			response = singleConnectionCommandLogsService.addSingleConnectionCommandLogs(req);
			LOG.info("Request sucess to generate command queue");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generate command queue :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSingleConnectionCommandLogs(@Valid @RequestBody SingleConnectionCommandLogsReq req)
	{
		LOG.info("Request received to get single connection command logs for : "+ req.getCommand());
		CommonResponse response = new CommonResponse();
		try {
			response = singleConnectionCommandLogsService.getSingleConnectionCommandLogs(req);
			LOG.info("Request sucess to get single connection command logs for : ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting single connection command logs :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/command-cancellation" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSingleConnectionCancelledCommand(@Valid @RequestBody SingleConnectionCommandLogsReq req)
	{
		LOG.info("Request received cancel single connection command : "+ req.getBatchId());
		CommonResponse response = new CommonResponse();
		try {
			response = singleConnectionCommandLogsService.cancelSingleConnectionCommandLog(req);
			LOG.info("Request sucess to cancel single connection command logs for : ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in cancelling single connection command :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get/by-batch-id" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSingleConnectionCommandLogsByBatchId(@Valid @RequestBody SingleConnectionCommandLogsReq req)
	{
		LOG.info("Request received to get single connection command logs batch wise for : "+ req.getBatchId());
		CommonResponse response = new CommonResponse();
		try {
			response = singleConnectionCommandLogsService.getSingleConnectionCommandLogsByBatchId(req);
			LOG.info("Request sucess to get single connection command logs batch wise for : ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting single connection command logs batch wise :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	/*
	 * Code For Extra Obis code Implementation
	 */

	@RequestMapping(value = "/obis-code/getList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getObisCodeList() {
		LOG.info("Request recieved to get Obis Code Informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = singleConnectionCommandLogsService.getObisCodeDetails();

			LOG.info("Request success to get Obis Code Informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting Obis Code Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/obis-code/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateObis(@Valid @RequestBody SingleConnectionCommandLogsReq req){
		LOG.info("Request recieved to update obis informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = singleConnectionCommandLogsService.updateObisCodeDetails(req);
			LOG.info("Request success to update obis informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating obis Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
}
