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
import org.springframework.web.client.RestTemplate;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.mdm.service.InstantDevicesInfoMdmService;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.Constants;

@RestController
@RequestMapping(value = "/hes/mdm/instantDevicesInfo")
public class InstantDevicesInfoMdmController {

	private static final Logger LOG = LoggerFactory.getLogger(InstantDevicesInfoMdmController.class);

	@Autowired
	InstantDevicesInfoMdmService instantDevicesInfoService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DevicesInfoRepository devicesInfoRepo;

	@Autowired
	DevicesInfoService devicesInfoService;

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getInstantDeviceInfo(
			@Valid @RequestBody MeterDataVisualizationReq meterDataVisualizationReq) {
		LOG.info("Request recieved to get recent instant informations...");
		CommonResponse response = new CommonResponse();
		try {
			
			if(Constants.HierLevelKey.METER.equalsIgnoreCase(meterDataVisualizationReq.getHier().getName())) 
			{
				DevicesInfo devicesInfo = devicesInfoService.getDevicesInfo(meterDataVisualizationReq.getHier().getValue());
				
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

			response = instantDevicesInfoService.getDevicesInfo(meterDataVisualizationReq);
			
			LOG.info("Request success to get instant device informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.info("Issue in getting instant device informations due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
