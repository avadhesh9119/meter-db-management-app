package com.global.meter.v3.inventive.controller;

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
import com.global.meter.v3.inventive.models.MeterResponseRawDataReq;
import com.global.meter.v3.inventive.service.MeterResponseRawDataService;

@RestController
@RequestMapping(value = "/hes/meter-response-raw-data")
public class MeterResponseRawDataController {

	private static final Logger LOG = LoggerFactory.getLogger(MeterResponseRawDataController.class);

	@Autowired
	MeterResponseRawDataService meterResponseRawDataService;

	@RequestMapping(value = "/get-by-trackingId", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getProfileDataByTrackingId(@Valid @RequestBody MeterResponseRawDataReq req) {
		LOG.info("Request received to get profile data logs by TrackingId : " + req.getTrackingId());
		CommonResponse response = new CommonResponse();
		try {
			response = meterResponseRawDataService.getMeterResponseRawDataByTrackingId(req);
			LOG.info("Request sucess to get profile data logs for : ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting profile data logs :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
