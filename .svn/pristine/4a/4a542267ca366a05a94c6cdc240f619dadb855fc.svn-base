package com.global.meter.controller;

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

import com.global.meter.request.model.DataCorrector;
import com.global.meter.service.DataCorrectorService;

@RestController
@RequestMapping(value="/data/corrector")
public class DataCorrectorController {

	private static final Logger LOG = LoggerFactory.getLogger(DataCorrectorController.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DataCorrectorService dataCorrectorService;
	
	@RequestMapping(value = "/instantSinglePhase" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> instantSinglePhase(@Valid @RequestBody DataCorrector dataCorrector){
		LOG.info("Correcting Instant Garbage Data:-----");
		
		try {
			dataCorrectorService.correctSPInstantData(dataCorrector);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/devicesInfo" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> devicesInfo(@Valid @RequestBody DataCorrector dataCorrector){
		LOG.info("Correcting DevicesInfo Data:-----");
		
		try {
			dataCorrectorService.correctDevicesInfoData(dataCorrector);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/lastBillingSP" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> lastBillingSP(@Valid @RequestBody DataCorrector dataCorrector){
		LOG.info("Correcting lastBillingSP Data:-----");
		
		try {
			dataCorrectorService.correctSPLastBillingData(dataCorrector);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/billingSP" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> billingSP(@Valid @RequestBody DataCorrector dataCorrector){
		LOG.info("Correcting billingSP Data:-----");
		
		try {
			dataCorrectorService.correctSPBillingData(dataCorrector);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/dailyLPSinglePhase" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> dailyLPSinglePhase(@Valid @RequestBody DataCorrector dataCorrector){
		LOG.info("Correcting dailyLPSinglePhase Data:-----");
		
		try {
			dataCorrectorService.correctSPDailyLPData(dataCorrector);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
		
	}
	
}
