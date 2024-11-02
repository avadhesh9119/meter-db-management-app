package com.global.meter.inventive.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.data.repository.DevicesCommandRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.DeviceCommandService;
import com.global.meter.utils.Constants;

@RestController
@RequestMapping(value = "/hes/devicesCommand")

public class DevicesCommandController {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesCommandController.class);

	@Autowired
	DeviceCommandService deviceCommandService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DevicesCommandRepository deviceCommandRepo;

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeviceCommandData(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get device command informations... : " + req.toString());
		CommonResponse response = new CommonResponse();
		try {
			
			if(!ConfigCommands.contains(req.getCommand()) && !Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				response.setMessage("Wrong Command Sent : "+req.getCommand());
				response.setCode(404);
				response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			response = deviceCommandService.getDeviceCommand(req);
			LOG.info("Request success to get device command informations.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting devices command informations due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeviceCommandList(
			@Valid @RequestBody MeterDataVisualizationReq meterDataVisualizationReq) {
		LOG.info("Request recieved to get devices command list...");
		CommonResponse response = new CommonResponse();
		try {

			response = deviceCommandService.getDeviceCommandList();
			
			LOG.info("Request success to get device command lists.");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting devices command lists due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get/byId", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeviceCommandDataByTraceId(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get device command informations... : " + req.toString());
		CommonResponse response = new CommonResponse();
		try {
			
			if(StringUtils.isEmpty(req.getTraceId())) {
				response.setMessage("Please provide trace id.");
				response.setCode(204);
				response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
			}
			
			response = deviceCommandService.getDeviceCommandByTraceId(req);
			LOG.info("Request success to get device command informations by trace id: "+req.getTraceId());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting devices command informations due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}


}
