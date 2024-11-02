package com.global.meter.inventive.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DevicesCommandRequest;
import com.global.meter.inventive.service.DevicesService;
import com.global.meter.request.model.Device;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.DeviceCommandService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

/**
 * 
 * @author Nitin Sethi
 *
 */
@RestController
@RequestMapping(value="/hes/ondemand")
public class OnDemandCommandsController {
	
	private static final Logger LOG = LoggerFactory.getLogger(OnDemandCommandsController.class);

	@Autowired
	DevicesService devicesService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DeviceCommandService deviceCommandService;
	
	@Autowired
	MetersCommandsConfiguration meterCommands;
	
	@Autowired
	CommonUtils commonUtils;
	
	@Autowired
	DateConverter dateConverter;
	
	
	@RequestMapping(value = "/read/execute" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public CommonResponse onDemandReadCommands(@Valid @RequestBody DevicesCommandRequest req)
	{
		LOG.info("Request received to read and execute on demand commands : " + req.toString());
		String traceId = String.valueOf(System.currentTimeMillis());
		Device device = new Device();
		device.setPlainText(req.getDeviceSerialNo());
		
		DeviceCommand deviceCommand = new DeviceCommand();
		deviceCommand.setTrackingId(traceId);
		deviceCommand.setRequestFrom("EXT");
		deviceCommand.setDevice(device);
		
		ResponseEntity<?> response = null;
		if(!StringUtils.isEmpty(req.getCommandName())) {
			if(Constants.CommandName.Instantaneous.equalsIgnoreCase(req.getCommandName())) {
				deviceCommand.setCommand(ConfigCommands.INSTANTANEOUS_READ.commandName);
				response = deviceCommandService.getResponseReadEntity(deviceCommand);
			}
		}
		
		MeterResponse meterResponse = (MeterResponse) response.getBody();
		
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCode(meterResponse.getStatus());
		commonResponse.setMessage(meterResponse.getMessage());
		commonResponse.setError(meterResponse.getStatus() == 200 ? false : true);
		commonResponse.setTraceId(traceId);
		
		
		return commonResponse;
	}
	
	@RequestMapping(value = "/write/execute" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public CommonResponse onDemandWriteCommands(@Valid @RequestBody DevicesCommandRequest req)
	{
		LOG.info("Request received to write and execute on demand commands : " + req.toString());
		String traceId = String.valueOf(System.currentTimeMillis());
		Device device = new Device();
		device.setPlainText(req.getDeviceSerialNo());
		
		DeviceCommand deviceCommand = new DeviceCommand();
		deviceCommand.setTrackingId(traceId);
		deviceCommand.setRequestFrom("EXT");
		deviceCommand.setDevice(device);
		
		ResponseEntity<?> response = null;
		if(!StringUtils.isEmpty(req.getCommandName())) {
			if(Constants.CommandName.Connect.equalsIgnoreCase(req.getCommandName())) {
				deviceCommand.setCommand(ConfigCommands.CONNECT.commandName);
			}
			else if(Constants.CommandName.Disconnect.equalsIgnoreCase(req.getCommandName())) {

				deviceCommand.setCommand(ConfigCommands.DISCONNECT.commandName);
			}else {
				CommonResponse commonResponse = new CommonResponse();
				commonResponse.setCode(400);
				commonResponse.setMessage("Wrong command send");
				commonResponse.setError(true);
				
				return commonResponse;
			}
		}
		response = deviceCommandService.writeConnectDisconnectEntity(deviceCommand);
		MeterResponse meterResponse = (MeterResponse) response.getBody();
		
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setCode(meterResponse.getStatus());
		commonResponse.setMessage(meterResponse.getMessage());
		commonResponse.setError(meterResponse.getStatus() == 200 ? false : true);
		commonResponse.setTraceId(traceId);
		
		
		return commonResponse;
	}
}
