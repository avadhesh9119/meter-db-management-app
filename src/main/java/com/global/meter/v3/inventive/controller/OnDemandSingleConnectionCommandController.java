package com.global.meter.v3.inventive.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.service.DevicesService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.Constants.CreateBatch;
import com.global.meter.v3.common.enums.OnDemandCommands;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;
import com.global.meter.v3.inventive.service.OnDemandSingleConnectionCommandService;
import com.global.meter.v3.inventive.service.SingleConnectionCommandService;
import com.global.meter.v3.inventive.service.SingleConnectionPerformService;
import com.global.meter.v3.inventive.task.OnDemandSingleConnectionCommandTask;

@RestController
@RequestMapping("hes/on-demand/single-connection")
public class OnDemandSingleConnectionCommandController {
		
	private static final Logger LOG = LoggerFactory.getLogger(OnDemandSingleConnectionCommandController.class);

	
	@Autowired
	DevicesService devicesService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DevicesInfoService devicesInfoService;
	
	@Autowired
	OnDemandSingleConnectionCommandService singleConnectionCommandService;
	
	@Autowired
	SingleConnectionPerformService singleConnectionPerformService;
	
	@Autowired
	SingleConnectionCommandService scpCommandService;
	
	@RequestMapping(value = "/execute" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> onDemandReadCommands(@Valid @RequestBody SingleConnectionCommandReq req)
	{
		LOG.info("Request received to read and execute on demand commands : " + req.toString());
		CommonResponse response = new CommonResponse();
		List<ErrorData> errorList = new ArrayList<>();
		String batchId = CommonUtils.createBatchId();
		String extBatchId = CreateBatch.EXT_BATCH + String.valueOf(System.nanoTime());
		List<String> devicesList = null;
		DevicesInfo devicesInfo = null;
		ErrorData error = new ErrorData();
		
		String levelName = ExternalConstants.getUILevelValue(req.getHier().getName());
		try {
			//check meter mumber is exist or not and also check valid commissioning status for send command 
			if("7".equalsIgnoreCase(req.getHier().getName()))
			{
                devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
				
				if(devicesInfo == null) {
					error.setErrorMessage("Device Not Found");
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				if((Constants.Status.INACTIVE.equalsIgnoreCase(devicesInfo.getCommissioning_status()) 
						|| Constants.Status.DOWN.equalsIgnoreCase(devicesInfo.getCommissioning_status())) 
						&& !req.getObisCodeList().contains(OnDemandCommands.NAME_PLATES.commandName)) {
					error.setErrorMessage("Device Status is : "+devicesInfo.getCommissioning_status());
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			}
				
				// data is already exist or not for dailyLP and deltaLP 
			if("7".equalsIgnoreCase(req.getHier().getName()) && (req.getDailyRangeDate()!=null || req.getDeltaRangeDate()!=null) 
					&& (req.getObisCodeList().contains(OnDemandCommands.DAILY_LOAD_PROFILE.commandName) 
							|| req.getObisCodeList().contains(OnDemandCommands.DELTA_LOAD_PROFILE.commandName))) 
			{
			
				if(singleConnectionCommandService.isRangeDataExist(req, devicesInfo)) {
					error.setErrorMessage("Data Already Exist!!");
					response.setCode(403);
					response.setError(true);
					response.addErrorMessage(error);
					return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
				}
			}	
			
			devicesList = devicesInfoService.getDevicesListAsString(levelName, req);
			if(devicesList != null && devicesList.size() > 0) {
				boolean isContainsDailyCmd = false;
				boolean isValid = false;
				if(req.getObisCodeList() != null && req.getObisCodeList().size() > 0) {
					for (String obisCode : req.getObisCodeList()) {
					    if (ConfigCommands.contains(obisCode)) {
					        isValid = true;
					        if(ConfigCommands.DAILY_LOAD_PROFILE.commandName.equalsIgnoreCase(obisCode)) {
					        	isContainsDailyCmd = true;
					        }
					        break; // Exit the loop early because we found a valid command
					    }
					}
				}
				
				if (req.getPrepayObisCodeList() != null && req.getPrepayObisCodeList().size() > 0) {
					for (String obisCode : req.getPrepayObisCodeList()) {
					    if (ConfigCommands.contains(obisCode)) {
					        isValid = true;
					        break; // Exit the loop early because we found a valid command
					    }
					}
				}
				
				if (req.getConfigVals() != null && req.getConfigVals().size() > 0) {
					for (Map.Entry<String, String> obisCode : req.getConfigVals().entrySet()) {
					    if (ConfigCommands.contains(obisCode.getKey())) {
					        isValid = true;
					        break; // Exit the loop early because we found a valid command
					    }
					}
				}

				if (!isValid) {

					error.setErrorMessage("Wrong Command Sent: " + req.getHier().getValue());
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
				    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
				
				if(req.getSource().isEmpty() || req.getUserId().isEmpty()) {
					error.setErrorMessage("Source & UserId can not be empty");
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
				
//				if("7".equalsIgnoreCase(req.getHier().getName()) 
//						&& isContainsDailyCmd) {
//					boolean isDailyDataExist = singleConnectionCommandService.isDataExist(req.getHier().getValue());
//					if(isDailyDataExist) {
//						error.setErrorMessage("Data Already Exist!!");
//						response.setCode(403);
//						response.setError(true);
//						errorList.add(error);
//						response.setErrorMessage(errorList);
//						return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
//					}
//				}
				
				
				LOG.info("Sending On-Demand on retrieved meter list..");
				String balanceCommand = System.currentTimeMillis() + req.getCommand();
				
				OnDemandSingleConnectionCommandTask onDemandGroupCommandTask = new OnDemandSingleConnectionCommandTask(singleConnectionPerformService,
						devicesList, req.getCommand(), balanceCommand, "EXT", req.getObisCodeList(),req.getPrepayObisCodeList()
						,req.getConfigVals(),batchId,
						req.getSource(), req.getUserId(), req.getDailyRangeDate(),req.getDeltaRangeDate(),extBatchId);
				
				onDemandGroupCommandTask.start();
				
				response.setCode(200);
				response.setMessage(Constants.SUCCESS);
				response.setBatchId(batchId);
				response.setExtBatchId(extBatchId);
				response.setCommandType(req.getCommand());
				
				LOG.info("On-Demand Sent to retrieved meter list..");
			}
			else
			{
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
			error.setErrorMessage("Issue in sending on-Demand Commands. Please contact administrator.");
			LOG.error("Issue in sending on-Demand Commands due to : {}", e.getMessage());
			response.setMessage("Issue in sending on-Demand Commands due to :" + e.getMessage());
			response.setCode(500);
			errorList.add(error);
			response.setErrorMessage(errorList);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/upload-xml-file", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> uploadXmlFile(@RequestParam("xmlFile") MultipartFile xmlFile ,
			@RequestPart("data") String data) {
	    LOG.info("Request received to upload XML file.");

		LOG.info("Request received to upload firmware file in DB : ");

		CommonResponse response = new CommonResponse();
		try {
			response = scpCommandService.uploadXmlFile(xmlFile, data);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting xml file due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
}
