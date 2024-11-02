package com.global.meter.inventive.controller;

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

import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.GetDeviceListsRequest;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.MetersDataRequest;
import com.global.meter.inventive.service.DevicesService;
import com.global.meter.utils.CommonUtils;

/**
 * 
 * @author Nitin Sethi
 *
 */
@RestController
@RequestMapping(value="/hes")
public class DevicesController {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesController.class);

	@Autowired
	DevicesService devicesService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DevicesInfoRepository devicesInfoRepository;
	
	@RequestMapping(value = "/device" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addDevice(@Valid @RequestBody List<MetersDataRequest> metersDataRequests)
	{
		LOG.info("Request recieved to add devices informations...");
		CommonResponse response = new CommonResponse();
		try {
			response = devicesService.addDevice(metersDataRequests);
			LOG.info("Request success to add devices informations.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding devices Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/device" , method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateDevice(@Valid @RequestBody List<MetersDataRequest> metersDataRequests)
	{
		LOG.info("Request recieved to update devices informations...");
		CommonResponse response = new CommonResponse();
		try {
			
			response = devicesService.updateDevice(metersDataRequests);
			
			LOG.info("Request success to update devices informations...");
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			LOG.error("Issue in updating devices Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/device/get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDevice(@Valid @RequestBody MetersDataRequest metersDataRequests)
	{
		LOG.info("Request recieved to get device informations..."+ metersDataRequests.getMeterNumber());
		CommonResponse response = new CommonResponse();
		try {
			
			response = devicesService.getDevice(metersDataRequests);
			
			LOG.info("Request success to get devices informations...");
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			LOG.error("Issue in getting devices Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/device" , method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteDevice(@Valid @RequestBody MetersDataRequest metersDataRequests)
	{
		LOG.info("Request recieved to delete device..."+ metersDataRequests.getMeterNumber());
		CommonResponse response = new CommonResponse();
		try {
			
			response = devicesService.deleteDevice(metersDataRequests);
			
			LOG.info("Request success to delete devices informations...");
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			LOG.error("Issue in deleting devices Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/device/get/list" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeviceList(@Valid @RequestBody MeterDataVisualizationReq inputReq)
	{
		LOG.info("Request recieved to get devices list..."+ inputReq.getHier().getName());
		CommonResponse response = new CommonResponse();
		try {
			if(inputReq.getHier() == null) {
				response.setMessage("Invalid request type : ");
				response.setCode(404);
				response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response = devicesService.getDeviceList(inputReq);
			LOG.info("Request success to get devices list...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting devices list due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/getDeviceList" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeviceLists(@Valid @RequestBody GetDeviceListsRequest inputReq)
	{
		LOG.info("Request recieved to get device lists...");
		CommonResponse response = new CommonResponse();
		try {
			if(CommonUtils.isNullOrEmpty(inputReq.getStartLatitude()) || CommonUtils.isNullOrEmpty(inputReq.getEndLatitude())
					|| CommonUtils.isNullOrEmpty(inputReq.getStartLongitude()) || CommonUtils.isNullOrEmpty(inputReq.getEndLongitude())
					|| CommonUtils.isNullOrEmpty(inputReq.getRange()) || CommonUtils.isNullOrEmpty(inputReq.getShowCount())) {
				response.setMessage("Please enter all mandatory fields...");
				response.setCode(404);
				response.setError(true);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			
			response = devicesService.getDeviceLists(inputReq);
			LOG.info("Request success to get device Lists.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting devices Lists due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/deletedDevice/get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeletedDeviceList(@Valid @RequestBody MeterDataVisualizationReq inputReq)
	{
		LOG.info("Request recieved to get deleted device list..."+ inputReq.getHier().getName());
		CommonResponse response = new CommonResponse();
		try {
			if(inputReq.getHier() == null) {
				response.setMessage("Invalid request type : ");
				response.setCode(404);
				response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response = devicesService.getDeletedDeviceList(inputReq);
			LOG.info("Request success to get deleted devices list...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting deleted devices list due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/decommissioning-request" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> decommissioningApproval(@Valid @RequestBody MetersDataRequest inputReq)
	{
		LOG.info("Request recieved to approve the decommissione to device : "+ inputReq.getMeterNumber());
		CommonResponse response = new CommonResponse();
		try {
			
			response = devicesService.decommissioningApproval(inputReq);
			LOG.info("Request success to approve the decommissione to device...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in approve the decommissione to device due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/device/get/decommissioned-request-list" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDecommissionedRequestDeviceList(@Valid @RequestBody MeterDataVisualizationReq inputReq)
	{
		LOG.info("Request recieved to get decommissioned request devices list...");
		CommonResponse response = new CommonResponse();
		try {
			response = devicesService.getDecommissionedRequestDeviceList(inputReq);
			LOG.info("Request success to get devices list...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting devices list due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/device-ping" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> pingDevice(@Valid @RequestBody MetersDataRequest inputReq)
	{
		LOG.info("Request recieved to ping device...");
		CommonResponse response = new CommonResponse();
		try {
			response = devicesService.pingDevice(inputReq);
			LOG.info("Request success to ping device...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in ping device due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(value = "/device-log-get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeviceLogs(@Valid @RequestBody MeterDataVisualizationReq inputReq)
	{
		LOG.info("Request recieved to get device logs...");
		CommonResponse response = new CommonResponse();
		try {
			response = devicesService.getDeviceLogs(inputReq);
			LOG.info("Request success to get device logs...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in get device logs due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(value = "/device-replace-report" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getReplaceDeviceReport(@Valid @RequestBody MeterDataVisualizationReq inputReq)
	{
		LOG.info("Request recieved to get replace devices report...");
		CommonResponse response = new CommonResponse();
		try {
			response = devicesService.getReplaceDeviceReport(inputReq);
			LOG.info("Request success to get replace devices report...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting replace devices report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}