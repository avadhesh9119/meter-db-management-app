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
import com.global.meter.common.enums.CommandStatus;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.enums.EventCommands;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.mdm.service.MeterDataVisualizationMdmService;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.Constants;

/**
 * 
 * @author Nitin Sethi
 *
 */
@RestController
@RequestMapping(value="/hes/mdm/reports")
public class MeterDataVisualizationMdmController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MeterDataVisualizationMdmController.class);
	
	
	@Autowired
	MeterDataVisualizationMdmService meterDataVisualizationService;
	
	@Autowired
	DevicesInfoService devicesInfoService;
	
	@RequestMapping(value = "/instant" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getInstant(@Valid @RequestBody MeterDataVisualizationReq req)
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
			
			response = meterDataVisualizationService.getInstantData(req);
			LOG.info("Request sucess to generate instant report for : ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generating instant report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/dailyLP" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDailyLP(@Valid @RequestBody MeterDataVisualizationReq req)
	{
		LOG.info("Request received to generate Daily LP report for : "+ req.toString());
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
			
			response = meterDataVisualizationService.getDailyLPData(req);
			LOG.info("Request success to generate Daily LP report for : ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generating Daily LP report report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/deltaLP" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeltaLP(@Valid @RequestBody MeterDataVisualizationReq req)
	{
		LOG.info("Request received to generate Delta LP report for : "+ req.toString());
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
			
			response = meterDataVisualizationService.getDeltaLPData(req);
			LOG.info("Request success to generate Delta LP report for : ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generating Delta LP report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/commissioining", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getNamePlateData(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to generate Commissioning report for : " + req.toString());
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
			
			response = meterDataVisualizationService.getNamePlateData(req);
			LOG.info("Request success to generate Commissioning report for : "+ req.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generating Commissioning report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/namePlateHistory", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getNamePlateHistoryData(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to generate Commissioning report for : " + req.toString());
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
			
			response = meterDataVisualizationService.getNamePlateHistoryData(req);
			LOG.info("Request success to generate Commissioning report for : "+ req.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generating Commissioning report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/events" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getEvent(@Valid @RequestBody MeterDataVisualizationReq req)
	{
		LOG.info("Request received to generate"+ req.getHier()+" Event Data report for : "+ req.toString());
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
			
			if(ExternalConstants.DeviceTypes.CT_METER.contains(req.getDevType()) 
					&& Constants.CONTROL_RELATED.contains(req.getEventType())) {
			response.setMessage(Constants.CONTROL_DEVICE_TYPE);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			
			response = meterDataVisualizationService.getEventData(req);
			LOG.info("Request success to get Events: ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting Event Data due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/billing" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getBillingData(@Valid @RequestBody MeterDataVisualizationReq req)
	{
		LOG.info("Request received to generate Billing data report for : "+ req.toString());
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
			
			response = meterDataVisualizationService.getBillingData(req);
			LOG.info("Request success to get Billing Data report: ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting Billing Data due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/devices/configs/onMeter", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getConfigurationReadData(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to generate Configuration Read Data report for : " + req.toString());
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
			
			if(!ConfigCommands.contains(req.getCommand()) && !Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				response.setMessage("Wrong Command Sent : "+req.getCommand());
				response.setCode(404);
				response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response = meterDataVisualizationService.getConfigurationReadDataByHier(req);
			LOG.info("Request success to generate Configuration Read Data report for : "+ req.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generating Configuration Read Data report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/devices/configs/logs", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getConfigurationReadLogsData(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to generate Configuration Read Logs Data report for : " + req.toString());
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
			
			if(!ConfigCommands.contains(req.getCommand()) && !Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				response.setMessage("Wrong Command Sent : "+req.getCommand());
				response.setCode(404);
				response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			
			response = meterDataVisualizationService.getConfigurationReadDataLogsByHier(req);
			LOG.info("Request success to generate Configuration Read Logs Data report for : "+ req.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generating Configuration Read Logs Data report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			
		}
		
	}
	
	@RequestMapping(value = "billing/monthlyProfile" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMonthlyBillingProfile(@Valid @RequestBody MeterDataVisualizationReq req)
	{
		LOG.info("Request received to generate Monthly Billing report for : "+ req.toString());
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
			
			response = meterDataVisualizationService.getMonthlyBillingProfile(req);
			LOG.info("Request success to generate Monthly Billing report for : ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generating Monthly Billing report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/pushEvents" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getPushEvents(@Valid @RequestBody MeterDataVisualizationReq req)
	{
		LOG.info("Request received to generate push events report for : "+ req.toString());
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
			
			if(req.getEventName()!=null && !req.getEventName().isEmpty()) {
				String[] events = req.getEventName().split(",");
				for(String event:events) {
				if(!EventCommands.contains(event.trim())) {
					response.setMessage("Invalid event type : "+event);
					response.setCode(404);
					response.setError(true);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				   }
				  }
				}
			response = meterDataVisualizationService.getEventsPushData(req);
			LOG.info("Request sucess to generate push events report for : ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generating push events report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/pushInstant" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getPushInstant(@Valid @RequestBody MeterDataVisualizationReq req)
	{
		LOG.info("Request received to generate push instant report for : "+ req.toString());
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
			
			response = meterDataVisualizationService.getInstantPushData(req);
			LOG.info("Request sucess to generate push instant report for : ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generating push instant report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/namePlateDevicesInfo", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getNamePlateDevicesInf(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to generate Name Plate Devices Info : " + req.toString());
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
			
			response = meterDataVisualizationService.getNamePlateDevicesInfo(req);
			LOG.info("Request success to generate Name Plate Devices Info : "+ req.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generating Name Plate Devices Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/recent-relay-status", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getRecentRelayStatus(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to generate recent relay status : " + req.toString());
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
			
			if(req.getCommand() != null && !req.getCommand().isEmpty()) {
				
				String[] cmd = req.getCommand().split(",");
				for(String commandName:cmd) {
				if(!ConfigCommands.contains(commandName.trim())) {
					response.setMessage("Invalid command type : "+commandName);
					response.setCode(404);
					response.setError(true);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				   }
				  }
			  }
				if(req.getStatus() != null && !req.getStatus().isEmpty()) {
					
				String[] stat = req.getStatus().split(",");
				for(String statusName:stat) {
				if(!CommandStatus.contains(statusName)) {
					response.setMessage("Invalid status type : "+statusName);
					response.setCode(404);
					response.setError(true);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				   }
				  }
			 }
				response = meterDataVisualizationService.getRecentRelayStatus(req);
				LOG.info("Request success to generate recent relay status : "+ req.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generating recent relay status due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(value = "/query-data", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDataByQuery(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to generate data by query : ");
		CommonResponse response = new CommonResponse();
		try {
			if(req.getQuery() == null || req.getQuery().isEmpty()) {
				
					response.setMessage("Query Can not be empty");
					response.setCode(404);
					response.setError(true);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			  }
				response = meterDataVisualizationService.getDataByQuery(req);
				LOG.info("Request success to generate data by query : "+ req.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in generating data by query due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
		
}
