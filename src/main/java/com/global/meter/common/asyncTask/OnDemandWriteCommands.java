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
public class OnDemandWriteCommands extends Thread{

	private static final Logger LOG = LoggerFactory.getLogger(OnDemandWriteCommands.class);
	
	private DeviceCommand deviceCommand;
	
	private DevicesCommandsController devicesCommandsController;
	
	public OnDemandWriteCommands(DeviceCommand deviceCommand, DevicesCommandsController devicesCommandsController) {
		this.deviceCommand = deviceCommand;
		this.devicesCommandsController = devicesCommandsController;
	}
	
	@Override
	public void run() {
		LOG.info("On Demand Write Command received: "+ deviceCommand.toString());
		ResponseEntity<?> response = devicesCommandsController.writeData(deviceCommand);
		LOG.info("On Demand Write Command completed: "+ deviceCommand.getDevice().getPlainText()
				+" : "+ response.getStatusCode());
	}
}
