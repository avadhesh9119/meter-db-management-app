package com.global.meter.inventive.controller;

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
import com.global.meter.inventive.models.MasterPasswordRequest;
import com.global.meter.inventive.service.MasterPasswordService;

@RestController
@RequestMapping(value="/hes")
public class MasterPasswordController {

	private static final Logger LOG = LoggerFactory.getLogger(MasterPasswordController.class);
	
	@Autowired
	MasterPasswordService masterPasswordService;
	
	
	@RequestMapping(value = "/master-password" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addMeterTypeInfo(@Valid @RequestBody List<MasterPasswordRequest> masterPasswordRequest)
	{
		LOG.info("Request recieved to add master password informations...");
		CommonResponse response = new CommonResponse();
		try {
			response = masterPasswordService.addMeterTypeInfo(masterPasswordRequest);
			LOG.info("Request success to add master password informations.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding master password due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/master-password" , method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateMeterTypeInfo(@Valid @RequestBody List<MasterPasswordRequest> masterPasswordRequest)
	{
		LOG.info("Request recieved to update master password informations...");
		CommonResponse response = new CommonResponse();
		try {
			response = masterPasswordService.updateMeterTypeInfo(masterPasswordRequest);
			LOG.info("Request success to update master password informations.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in updating master password due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/master-password/get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterTypeInfo(@Valid @RequestBody MasterPasswordRequest masterPasswordRequest)
	{
		LOG.info("Request recieved to get master password informations...");
		CommonResponse response = new CommonResponse();
		try {
			response = masterPasswordService.getMeterTypeInfo(masterPasswordRequest);
			LOG.info("Request success to get master password informations.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting master password due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/master-password/list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterTypeInfo()
	{
		LOG.info("Request recieved to get master password list informations...");
		CommonResponse response = new CommonResponse();
		try {
			response = masterPasswordService.getMeterTypeInfoList();
			LOG.info("Request success to get master password list informations.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting master password list due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
