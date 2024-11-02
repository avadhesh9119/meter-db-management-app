package com.global.meter.inventive.mdm.controller;

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

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.mdm.service.SingleConnectionCommandMdmService;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.Constants;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogsReq;

/**
 * 
 * @author Nitin Sethi
 *
 */
@RestController
@RequestMapping(value = "/hes/mdm")
public class SingleConnectionMdmController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MeterDataVisualizationMdmController.class);
	
	
	@Autowired
	SingleConnectionCommandMdmService singleConnectionCommandMdmService;

	@Autowired
	DevicesInfoService devicesInfoService;
	
	@RequestMapping(value = "/get-single-connection-commandLog" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSingleConnectionCommandLogs(@Valid @RequestBody SingleConnectionCommandLogsReq req)
	{
		LOG.info("Request received to generate instant report for : "+ req.toString());
		CommonResponse response = new CommonResponse();
		try {
			
			if(Constants.HierLevelKey.METER.equalsIgnoreCase(req.getHier().getName())) 
			{
				DevicesInfo devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
				
				if(devicesInfo == null) {
					ErrorData error = new ErrorData();
					error.setErrorMessage(Constants.DEVICE_NOT_EXISTS);
					response.addErrorMessage(error);
					response.setError(true);
					response.setMessage(Constants.DEVICE_NOT_EXISTS);
					response.setCode(404);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			
			}
			
			response = singleConnectionCommandMdmService.getSingleConnectionCommandLogs(req);
			LOG.info("Request sucess to get single connection command logs for : ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting single connection command logs :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
}
