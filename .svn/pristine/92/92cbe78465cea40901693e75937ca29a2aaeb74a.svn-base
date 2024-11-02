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
import com.global.meter.v3.inventive.models.MeterCommunicationConfig;
import com.global.meter.v3.inventive.service.MeterCommunicationConfigService;

@RestController
@RequestMapping(value="/hes/v3")
public class MeterCommunicationConfigController {
private static final Logger LOG = LoggerFactory.getLogger(MeterCommunicationConfigController.class);
	
	@Autowired
	MeterCommunicationConfigService meterCommunicationConfigService;
	
	@RequestMapping(value = "/meter-comm/config" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addMeterTypeInfo(@Valid @RequestBody List<MeterCommunicationConfig> masterPasswordRequest)
	{
		LOG.info("Request recieved to add meter communication configuration info.");
		CommonResponse response = new CommonResponse();
		try {
			response = meterCommunicationConfigService.addMeterTypeInfo(masterPasswordRequest);
			LOG.info("Request success to add meter communication configuration info.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding meter communication configuration due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/meter-comm/config" , method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateMeterTypeInfo(@Valid @RequestBody List<MeterCommunicationConfig> masterPasswordRequest)
	{
		LOG.info("Request recieved to update meter communication configuration info.");
		CommonResponse response = new CommonResponse();
		try {
			response = meterCommunicationConfigService.updateMeterTypeInfo(masterPasswordRequest);
			LOG.info("Request success to update master password informations.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in updating meter communication configuration info due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/meter-comm/config/get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterTypeInfo(@Valid @RequestBody MeterCommunicationConfig masterPasswordRequest)
	{
		LOG.info("Request recieved to get meter communication configuration info.");
		CommonResponse response = new CommonResponse();
		try {
			response = meterCommunicationConfigService.getMeterTypeInfo(masterPasswordRequest);
			LOG.info("Request success to get meter communication configuration info.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting master password due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/meter-comm/config/list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterTypeInfo()
	{
		LOG.info("Request recieved to get meter communication configuration list info.");
		CommonResponse response = new CommonResponse();
		try {
			response = meterCommunicationConfigService.getMeterTypeInfoList();
			LOG.info("Request success to get meter communication configuration list info.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting meter communication configuration list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
