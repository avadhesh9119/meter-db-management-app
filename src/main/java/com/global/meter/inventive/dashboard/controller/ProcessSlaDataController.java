package com.global.meter.inventive.dashboard.controller;

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

import com.global.meter.inventive.dashboard.model.ProcessSlaDataRequest;
import com.global.meter.inventive.dashboard.service.ProcessSlaDataService;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;

@RestController
@RequestMapping("hes/process-sla-data")
public class ProcessSlaDataController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProcessSlaDataController.class);
	
	@Autowired
	ProcessSlaDataService processSlaDataService;

	
	@RequestMapping(value = "/daily-summary/add", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addDailySummaryData() {
		LOG.info("Request received to generate Daily Summary Data Report.");
		CommonResponse response = new CommonResponse();
		try {
			response = processSlaDataService.addDailySummaryData();
			LOG.info("Generate Daily Summary Data Report Success On Controller.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generate Process Sla Data Report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/daily-summary/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDailySummaryData(@Valid @RequestBody ProcessSlaDataRequest processSlaDataRequest) {
		LOG.info("Request received to get Daily Summary Data Report.");
		CommonResponse response = new CommonResponse();
		try {
			response = processSlaDataService.getDailySummaryData(processSlaDataRequest);
			LOG.info("Get Daily Summary Data Report Success On Controller.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generate Process Sla Data Report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value = "/communication-summary/get", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getCommunicationSummaryData() {
		LOG.info("Request received to generate Communication Summary Data Report.");
		CommonResponse response = new CommonResponse();
		try {
			response = processSlaDataService.getCommunicationSummaryData();
			LOG.info("Generate Communication Summary Data Report Success On Controller.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting Communication Summary Data Report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value = "/communication-summary/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getCommunicationSummaryData(@Valid @RequestBody ProcessSlaDataRequest processSlaDataRequest) {
		LOG.info("Request received to get Communication Summary Data Report.");
		CommonResponse response = new CommonResponse();
		try {
			response = processSlaDataService.getCommunicationSummaryData(processSlaDataRequest);
			LOG.info("Get Communication Summary Data Report Success On Controller.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting Communication Summary Data Report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/meter-commissioning-report/add", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addmeterCommissioningReport() {
		LOG.info("Request received to generate commissioning report.");
		CommonResponse response = new CommonResponse();
		try {
			response = processSlaDataService.addmeterCommissioningReport();
			LOG.info("Generate commissioning report success on controller.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generate commissioning report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/meter-commissioning-report/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getmeterCommissioningReport(@Valid @RequestBody ProcessSlaDataRequest processSlaDataRequest) {
		LOG.info("Request received to get commissioning report.");
		CommonResponse response = new CommonResponse();
		try {
			response = processSlaDataService.getmeterCommissioningReport(processSlaDataRequest);
			LOG.info("Get commissioning report success On Controller.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting commissioning report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	
}
