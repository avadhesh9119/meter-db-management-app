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

import com.global.meter.inventive.dashboard.service.RetryAttemptService;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogsReq;

@RestController
@RequestMapping(value = "/hes/dashboard")
public class RetryAttemptController {

	private static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	RetryAttemptService retryAttemptService;

	@RequestMapping(value = "/retry-attempts", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getRetryAttempts(@Valid @RequestBody SingleConnectionCommandLogsReq req) {
		LOG.info("Request received to get meter retry attempt.");
		CommonResponse response = new CommonResponse();
		try {
			response = retryAttemptService.getRetryAttempt(req);
			LOG.info("Request success to get meter retry attempt.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated while Meter retry attempt:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
