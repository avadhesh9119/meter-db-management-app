package com.global.meter.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.global.meter.common.asyncTask.OnDemandReadCommands;
import com.global.meter.common.asyncTask.OnDemandWriteCommands;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.utils.Constants;

@RestController
@RequestMapping(value = "devices/onDemandCommands")
public class OnDemandCommandController {
	
	private static final Logger LOG = LoggerFactory.getLogger(OnDemandCommandController.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DevicesCommandsController devicesCommandsController;
	
	@Autowired
	private MeterResponse meterResponse;
	
	@RequestMapping(value = "/data/get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> readData(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		LOG.info("On Demand Command received --> " + deviceCommand.getDevice().getPlainText());

		try {
			ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
			OnDemandReadCommands onDemandReadCommands = new OnDemandReadCommands(deviceCommand, devicesCommandsController);
			taskExecutor.execute(onDemandReadCommands);
			
			meterResponse.setMessage(Constants.SUCCESS);
			meterResponse.setStatus(200);
			meterResponse.setOutputResponse("On Demand Command Sent to Meter: "+ deviceCommand.getDevice().getPlainText());
		} catch (BeansException e) {
			meterResponse.setMessage(e.getMessage());
			meterResponse.setStatus(500);
			return new ResponseEntity<>(meterResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(meterResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/data/write" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> writeData(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		LOG.info("On Demand Write Command received --> " + deviceCommand.getDevice().getPlainText());

		try {
			ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
			OnDemandWriteCommands onDemandReadCommands = new OnDemandWriteCommands(deviceCommand, devicesCommandsController);
			taskExecutor.execute(onDemandReadCommands);
			
			meterResponse.setMessage(Constants.SUCCESS);
			meterResponse.setStatus(200);
			meterResponse.setOutputResponse("On Demand Command Sent to Meter: "+ deviceCommand.getDevice().getPlainText());
		} catch (BeansException e) {
			meterResponse.setMessage(e.getMessage());
			meterResponse.setStatus(500);
			return new ResponseEntity<>(meterResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(meterResponse, HttpStatus.OK);
	}
}
