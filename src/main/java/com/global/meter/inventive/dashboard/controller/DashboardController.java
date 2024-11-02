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

import com.global.meter.inventive.dashboard.service.DashboardService;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;

@RestController
@RequestMapping(value = "/hes/dashboard")
public class DashboardController {

	private static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	DashboardService dashboardService;

	@RequestMapping(value = "/meter-status", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterStatus(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get meter status controller.");
		CommonResponse response = new CommonResponse();
		try {
			response = dashboardService.getMeterStatus(req);
			LOG.info("Request success to get meter status.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While Meter Status Controller: " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get-today-installed-meter-drilldown", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getTodayInstalledMeterDrilldown(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get today's installed meter Drill Down Info By: "+req);
		CommonResponse response = new CommonResponse();
		try {
			response = dashboardService.getTodayInstalledMeterDrilldown(req);
			LOG.info("Request success to get today's installed meter Drill Down Info: ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While getting today's installed meter Drilldown: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get-today-commissioned-meter-drilldown", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getTodayCommissionedMeterDrilldown(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get today's commissioned meter Drill Down Info By: "+req);
		CommonResponse response = new CommonResponse();
		try {
			response = dashboardService.getTodayCommissionedMeterDrilldown(req);
			LOG.info("Request success to get today's commissioned meter Drill Down Info: ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While getting today's commissioned meter Drilldown: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
