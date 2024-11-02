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
import com.global.meter.inventive.service.ProcessBillingDataService;

@RestController
@RequestMapping(value = "/hes/billing/process")
public class ProcessBillingDataController {

	@Autowired
	ProcessBillingDataService processBillingDataService;

	private static final Logger LOG = LoggerFactory.getLogger(ProcessBillingDataController.class);

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getProcessBillingData(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to save Billing Data Process");
		CommonResponse response = new CommonResponse();
		try {
			response = processBillingDataService.getProcessBillingData(req);
			LOG.info("Request success to get Billing Data Process");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While save Billing Data Process due to: " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/drilldown", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getProcessBillingDataDrillDown(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get ProcessBillingData Log Drill Down Info");
		CommonResponse response = new CommonResponse();
		try {
			response = processBillingDataService.getProcessBillingDataDrillDown(req);
			LOG.info("Request success to get ProcessBillingData Log Drill Down Info: ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While Process ProcessBillingData Log Drill Down: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}


}