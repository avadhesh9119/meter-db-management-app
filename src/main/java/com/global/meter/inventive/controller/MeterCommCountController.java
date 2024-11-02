package com.global.meter.inventive.controller;

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
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.data.repository.MeterCommCountRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterCommCountRequest;
import com.global.meter.inventive.models.MeterCommDrillDownRequest;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.MeterCommCountService;


@RestController
@RequestMapping(value="/hes/devices/count")
public class MeterCommCountController {
	
private static final Logger LOG = LoggerFactory.getLogger(MeterCommCountController.class);
	
	
	@Autowired
	MeterCommCountService meterCommCountService ;
	
	@Autowired
	MeterCommCountRepository meterCommCountRepo;
	
	@RequestMapping(value = "/process", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterCommCount() {
		LOG.info("Request received to get Meter Communication Count");
		CommonResponse response = new CommonResponse();
		try {
			response = meterCommCountService.getMeterCommCount();
			LOG.info("Request success to get Meter Communication Count");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While Process Meter Comm Count due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value = "/data", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterCommCount(@Valid @RequestBody MeterCommCountRequest meterCommCountRequest) {
		LOG.info("Request recieved to get meter comm count informations by Utility: " + meterCommCountRequest.getUtility());
		CommonResponse response = new CommonResponse();
		try {

			response = meterCommCountService.getMeterCommCountInfoByOwnerName(meterCommCountRequest);
			
			LOG.info("Request success to get meter comm count informations by Utility: ");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.info("Error Generated While Get Data For Meter Comm Count due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value = "/process/drilldown", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterCommDrillDown(@Valid @RequestBody MeterCommDrillDownRequest meterCommDrillDownRequest) {
		LOG.info("Request received to get Meter Communication Drill Down Info By: "+meterCommDrillDownRequest);
		CommonResponse response = new CommonResponse();
		try {
			response = meterCommCountService.getMeterCommDrillDown(meterCommDrillDownRequest);
			LOG.info("Request success to get Meter Communication Drill Down Info: ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While Process Meter Comm Count: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	// version v2 changes
	@RequestMapping(value = "/get/all", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterDataCount(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get meter data count controller.");
		CommonResponse response = new CommonResponse();
		try {
			response = meterCommCountService.getMeterDataCount(req);
			LOG.info("Request success to meter data count.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While Meter Data Count Controller: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(value = "/barChart/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterBarChartDataCount(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get Meter Bar Chart Data Count controller.");
		CommonResponse response = new CommonResponse();
		try {
			
			if(req.getCommand()!=null) {
				String[] commands = req.getCommand().split(",");
				for(String command:commands) {
				if(!ConfigCommands.contains(command.trim())) {
					response.setMessage("Invalid command type : "+command);
					response.setCode(404);
					response.setError(true);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				   }
				  }
				}
			
			response = meterCommCountService.getMeterBarChartDataCount(req);
			LOG.info("Request success to get Meter Bar Chart Data Count...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While to Process Meter Bar Chart Data Count due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(value = "/barChart/drillDown", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterBarChartDataDrillDown(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get Meter BarChart Data Drill Down controller.");
		CommonResponse response = new CommonResponse();
		try {
			
			if(req.getCommand()!=null) {
				String[] commands = req.getCommand().split(",");
				for(String command:commands) {
				if(!ConfigCommands.contains(command.trim())) {
					response.setMessage("Invalid command type : "+command);
					response.setCode(404);
					response.setError(true);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				   }
				  }
				}
			
			response = meterCommCountService.getMeterBarChartDataCountDrillDown(req);
			LOG.info("Request success to get Meter BarChart Data Drill Down");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While get Meter BarChart Data Drill Down due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/command/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterCountSuccessCommand(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get Meter count for success command controller.");
		CommonResponse response = new CommonResponse();
		try {
			
			if(req.getCommand()!=null) {
				String[] commands = req.getCommand().split(",");
				for(String command:commands) {
				if(!ConfigCommands.contains(command.trim())) {
					response.setMessage("Invalid command type : "+command);
					response.setCode(404);
					response.setError(true);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				   }
				  }
				}
			
			response = meterCommCountService.getMeterCountSuccessCommand(req);
			LOG.info("Request success to get Meter count for success command...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While to Process Meter count for success command due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/command/drillDown", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterCountSuccessCommandDrillDown(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get DrillDown Meter count for success command controller.");
		CommonResponse response = new CommonResponse();
		try {
			
			if(req.getCommand()!=null) {
				String[] commands = req.getCommand().split(",");
				for(String command:commands) {
					if(!ConfigCommands.contains(command.trim())) {
						response.setMessage("Invalid command type : "+command);
						response.setCode(404);
						response.setError(true);
						return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
					}
				}
			}
			
			response = meterCommCountService.getMeterCountSuccessCommandDrillDown(req);
			LOG.info("Request success to get DrillDown Meter count for success command...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While to Process Meter count for success command due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/success/drillDown", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterSuccessCommandDrillDown(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get DrillDown Meter count for success command controller.");
		CommonResponse response = new CommonResponse();
		try {
			
			if(req.getCommand()!=null) {
				String[] commands = req.getCommand().split(",");
				for(String command:commands) {
					if(!ConfigCommands.contains(command.trim())) {
						response.setMessage("Invalid command type : "+command);
						response.setCode(404);
						response.setError(true);
						return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
					}
				}
			}
			
			response = meterCommCountService.getMeterSuccessCommandDrillDown(req);
			LOG.info("Request success to get DrillDown Meter count for success command...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.info("Error Generated While to Process Meter count for success command due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
