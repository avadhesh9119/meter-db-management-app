package com.global.meter.inventive.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.service.DevicesCommandPerformService;
import com.global.meter.inventive.service.DevicesService;
import com.global.meter.inventive.service.OnDemandCommandService;
import com.global.meter.inventive.task.OnDemandGroupCommandTask;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.DeviceCommandService;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.Constants;
import com.global.meter.utils.Constants.CreateBatch;

/**
 * 
 * @author Nitin Sethi
 *
 */
@RestController
@RequestMapping(value="/hes/ondemand/group")
public class OnDemandGroupCommandsController {
	
	private static final Logger LOG = LoggerFactory.getLogger(OnDemandCommandsController.class);

	@Autowired
	DevicesService devicesService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DeviceCommandService deviceCommandService;
	
	@Autowired
	DevicesInfoService devicesInfoService;
	
	@Autowired
	DevicesCommandPerformService devicesCommandPerformService;
	
	@Autowired
	OnDemandCommandService onDemandCommandService;
	
	@Autowired
	Constants constants;
	
	@RequestMapping(value = "/execute" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> onDemandReadCommands(@Valid @RequestBody DeviceCommand req)
	{
		LOG.info("Request received to read and execute on demand commands : " + req.toString());
		CommonResponse response = new CommonResponse();
		List<ErrorData> errorList = new ArrayList<>();
		String batchId =CreateBatch.BATCH+String.valueOf(System.nanoTime());
		List<String> devicesList = null;
		DevicesInfo devicesInfo = null;
		
		String levelName = ExternalConstants.getUILevelValue(req.getHier().getName());
		try {
			if("7".equalsIgnoreCase(req.getHier().getName()) && (req.getDailyRangeDate()!=null || req.getDeltaRangeDate()!=null) 
					&& (req.getCommand().equalsIgnoreCase("DailyLoadProfile") || req.getCommand().equalsIgnoreCase("DeltaLoadProfile"))) 
			{
				devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
				
				if(devicesInfo == null) {
					response.setMessage("Device Not Found");
					response.setCode(404);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			
				if(onDemandCommandService.isRangeDataExist(req, devicesInfo)) {
					response.setMessage("Data Already Exist!!");
					response.setCode(403);
					response.setError(true);
					return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
				}
			}	
			
			devicesList = devicesInfoService.getDevicesListAsString(levelName, req);
			if(devicesList != null && devicesList.size() > 0) {
				
				if(!ConfigCommands.contains(req.getCommand())) {
					response.setMessage("Wrong Command Sent : "+req.getHier().getValue());
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
				if("7".equalsIgnoreCase(req.getHier().getName()) 
						&& ConfigCommands.DAILY_LOAD_PROFILE.commandName.equalsIgnoreCase(req.getCommand())) {
					boolean isDailyDataExist = onDemandCommandService.isDataExist(req.getHier().getValue());
					if(isDailyDataExist) {
						ErrorData error = new ErrorData();
						error.setErrorMessage("Data Already Exist!!");
						response.setMessage("Data Already Exist!!");
						response.setCode(403);
						response.setError(true);
						errorList.add(error);
						response.setErrorMessage(errorList);
						return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
					}
				}
				
				
				LOG.info("Sending On-Demand on retrieved meter list..");
				String balanceCommand = System.currentTimeMillis() + req.getCommand();
				
				/*devicesCommandPerformService.initiateCommand(devicesList, req.getCommand(), balanceCommand, 
						"EXT", req.getCommandList());*/
				
				OnDemandGroupCommandTask onDemandGroupCommandTask = new OnDemandGroupCommandTask(devicesCommandPerformService,
						devicesList, req.getCommand(), balanceCommand, "EXT", req.getCommandList(),batchId,
						req.getSource(), req.getUserId(), req.getDailyRangeDate(),req.getDeltaRangeDate());
				
				onDemandGroupCommandTask.start();
				
				response.setCode(200);
				response.setMessage(Constants.SUCCESS);
				response.setBatchId(batchId);
				response.setCommandType(req.getCommand());
				
				LOG.info("On-Demand Sent to retrieved meter list..");
			}
			else
			{
				ErrorData error = new ErrorData();
				if(req.getManufacturer() != null && !req.getManufacturer().isEmpty()) {
				error.setErrorMessage("Device Not Found for the selected manufacturer");
				response.setMessage("Device Not Found for the selected manufacturer.");
				}else {
					error.setErrorMessage("Meter number not found.");
					response.setMessage("Meter number not found.");
				}
				response.setCode(404);
				response.setError(true);
				errorList.add(error);
				response.setErrorMessage(errorList);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			ErrorData error = new ErrorData();
			error.setErrorMessage("Issue in sending on-Demand Commands. Please contact administrator.");
			LOG.error("Issue in sending on-Demand Commands due to :" + e.getMessage());
			response.setMessage("Issue in sending on-Demand Commands due to :" + e.getMessage());
			response.setCode(500);
			errorList.add(error);
			response.setErrorMessage(errorList);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
