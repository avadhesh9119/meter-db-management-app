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
import com.global.meter.data.repository.DevicesConfigRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.DevicesConfigService;
import com.global.meter.utils.Constants;

@RestController
@RequestMapping(value = "/hes/devicesConfig")
public class DevicesConfigController {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesCommandController.class);

	@Autowired
	DevicesConfigService devicesConfigService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DevicesConfigRepository deviceConfigRepo;

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeviceCommand(
			@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request recieved to get device config informations..." + req);
		CommonResponse response = new CommonResponse();
		try {
			
			
			if(!ConfigCommands.contains(req.getCommand()) && !Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				response.setMessage("Wrong Command Sent : "+req.getCommand());
				response.setCode(404);
				response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if(req.getSource().isEmpty() || req.getUserId().isEmpty()) {
				response.setMessage("Source & UserId can not be empty");
				response.setCode(404);
				response.setError(true);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			response = devicesConfigService.getDevicesConfig(req);
			
			LOG.info("Request success to get device config informations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting devices config informations due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/getlist", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeviceCommandList() {
		LOG.info("Request recieved to get devices config list...");
		CommonResponse response = new CommonResponse();
		try {

			response = devicesConfigService.getDevicesConfigList();
			
			LOG.info("Request success to get device config lists.");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting devices config list due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
