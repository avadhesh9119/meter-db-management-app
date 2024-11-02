package com.global.meter.common.asyncTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.global.meter.controller.DevicesCommandsController;
import com.global.meter.request.model.DeviceCommand;

@Component
@Scope("prototype")
public class OnDemandReadCommands extends Thread{

	private static final Logger LOG = LoggerFactory.getLogger(DevicesCommandsController.class);
	
	private DeviceCommand deviceCommand;
	
	private DevicesCommandsController devicesCommandsController;
	
	public OnDemandReadCommands(DeviceCommand deviceCommand, DevicesCommandsController devicesCommandsController) {
		this.deviceCommand = deviceCommand;
		this.devicesCommandsController = devicesCommandsController;
	}
	
	@Override
	public void run() {
		LOG.info("On Demand Command received: "+ deviceCommand.toString());
		ResponseEntity<?> response = devicesCommandsController.readData(deviceCommand);
		LOG.info("On Demand Command completed: "+ deviceCommand.getDevice().getPlainText()
				+" : "+ response.getStatusCode());
	}
}
