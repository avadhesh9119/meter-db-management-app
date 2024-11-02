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
import com.global.meter.service.DevicesInfoService;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogsReq;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;
import com.global.meter.v3.inventive.service.SingleConnectionDashboardService;

@RestController
@RequestMapping(value = "/hes/scp")
public class SingleConnectionDashboardController {

	private static final Logger LOG = LoggerFactory.getLogger(SingleConnectionDashboardController.class);

	@Autowired
	SingleConnectionDashboardService singleConnectionDashboardService;

	@Autowired
	DevicesInfoService devicesInfoService;

	@RequestMapping(value = "/get-command-status-count", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSingleConnectionCommandStatusCount(
			@Valid @RequestBody SingleConnectionCommandReq req) {
		LOG.info("Request received to get command status count");
		CommonResponse response = new CommonResponse();
		try {
			response = singleConnectionDashboardService.getSingleConnectionCommandStatusCount(req);
			LOG.info("Request sucess to get command status count");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in get command status count :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get-success-count", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSingleConnectionCommandSuccessCount(
			@Valid @RequestBody SingleConnectionCommandReq req) {
		LOG.info("Request received to get device count for success command");
		CommonResponse response = new CommonResponse();
		try {
			response = singleConnectionDashboardService.getSingleConnectionCommandSuccessCount(req);
			LOG.info("Request sucess to get device count for success command");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in get device count for success command :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get-success-drillDown", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSingleConnectionCommandSuccessDrillDown(
			@Valid @RequestBody SingleConnectionCommandLogsReq req) {
		LOG.info("Request received to get device DrillDown for success command");
		CommonResponse response = new CommonResponse();
		try {
			response = singleConnectionDashboardService.getSingleConnectionCommandSuccessDrillDown(req);
			LOG.info("Request sucess to get device DrillDown for success command");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in get device DrillDown for success command :" + e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get-network-report", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSingleConnectionCommandNetworkReport(
			@Valid @RequestBody SingleConnectionCommandLogsReq req) {
		LOG.info("Request received to get network failure report");
		CommonResponse response = new CommonResponse();
		try {
			response = singleConnectionDashboardService.getSingleConnectionCommandNetworkReport(req);
			LOG.info("Request sucess to get network failure report");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in get network failure report :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get-network-report-drillDown", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSingleConnectionCommandNetworkReportDrillDown(
			@Valid @RequestBody SingleConnectionCommandLogsReq req) {
		LOG.info("Request received to get network failure report drill down");
		CommonResponse response = new CommonResponse();
		try {
			response = singleConnectionDashboardService.getSingleConnectionCommandNetworkReportDrillDown(req);
			LOG.info("Request sucess to get network failure report drill down");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in get network failure report drill down :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get-gis-report", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getGISIntegrationReport(
			@Valid @RequestBody SingleConnectionCommandLogsReq req) {
		LOG.info("Request received to get GIS report");
		CommonResponse response = new CommonResponse();
		try {
			response = singleConnectionDashboardService.getGISReport(req);
			LOG.info("Request sucess to get GIS report");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting GIS report :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}
