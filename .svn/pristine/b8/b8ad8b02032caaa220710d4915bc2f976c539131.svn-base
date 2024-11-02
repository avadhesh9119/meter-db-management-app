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
import org.springframework.web.client.RestTemplate;

import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.data.repository.DeviceCommandLogsRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DeviceCommandLogsRequest;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.DeviceCommandLogsService;
import com.global.meter.utils.Constants;

@RestController
@RequestMapping(value = "/hes/device/command/log")
public class DeviceCommandLogsController {

	private static final Logger LOG = LoggerFactory.getLogger(DeviceCommandLogsController.class);

	@Autowired
	DeviceCommandLogsService commandLogsService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DeviceCommandLogsRepository commandLogsRepository;

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeviceCommandLogByTrackingId(
			@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request recieved to get device command logs informations... "
				+ req);
		CommonResponse response = new CommonResponse();
		try {
			
			if(!ConfigCommands.contains(req.getCommand()) && !Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				response.setMessage("Wrong Command Sent : "+req.getCommand());
				response.setCode(404);
				response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			response = commandLogsService.getDeviceCommandLogByHier(req);
			
			LOG.info("Request success to get device command logs informations... ");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting devices command logs information due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/getlist", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeviceCommandLogList(
			@Valid @RequestBody DeviceCommandLogsRequest deviceCommandLogsRequest) {
		LOG.info("Request recieved to get device command logs list...");
		CommonResponse response = new CommonResponse();
		try {

			response = commandLogsService.getDeviceCommandLogList();
			
			LOG.info("Request success to get device command logs lists... ");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting devices command log lists due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/getBatch", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeviceCommandLogByBatch(
			@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request recieved to get device command logs informations... "
				+ req);
		CommonResponse response = new CommonResponse();
		try {
			
//			if(!ConfigCommands.contains(req.getCommand()) && !Constants.ALL.equalsIgnoreCase(req.getCommand())) {
//				response.setMessage("Wrong Command Sent : "+req.getCommand());
//				response.setCode(404);
//				response.setError(true);
//				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//			}

			response = commandLogsService.getDeviceCommandLogByBatch(req);
			
			LOG.info("Request success to get device command logs informations... ");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting devices command logs information due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
}
