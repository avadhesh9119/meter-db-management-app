package com.global.meter.inventive.mdm.controller;

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
import com.global.meter.inventive.mdm.models.MdmPushRequest;
import com.global.meter.inventive.mdm.service.MdmPushLogService;
import com.global.meter.inventive.models.CommonResponse;

@RestController
@RequestMapping(value = "/hes/mdm-push-log")
public class MdmPushLogController {

	private static final Logger LOG = LoggerFactory.getLogger(MdmPushLogController.class);

	@Autowired
	MdmPushLogService mdmPushLogService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addMDMPushLog(
			@Valid @RequestBody MdmPushRequest mdmPushRequest) {
		LOG.info("Request recieved to add MDM push logs...");
		CommonResponse response = new CommonResponse();
		try {

			response = mdmPushLogService.addMDMPushLog(mdmPushRequest);
			
			LOG.info("Request success to add MDM push logs...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.info("Issue in add MDM push logs due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateMDMPushLog(
			@Valid @RequestBody MdmPushRequest mdmPushRequest) {
		LOG.info("Request recieved to update MDM push logs...");
		CommonResponse response = new CommonResponse();
		try {
			
			response = mdmPushLogService.updateMdmPushLog(mdmPushRequest);
			
			LOG.info("Request success to add MDM push logs...");
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			LOG.info("Issue in update MDM push logs due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
