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

import com.global.meter.common.enums.EventCommands;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.PriorityEventRequest;
import com.global.meter.inventive.service.PushEventAlarmService;
import com.global.meter.utils.Constants;


@RestController
@RequestMapping(value="/hes/devices/push/event")
public class PushEventAlarmController {
	
private static final Logger LOG = LoggerFactory.getLogger(PushEventAlarmController.class);
		
	@Autowired
	PushEventAlarmService pushEventAlarmService ;
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterCommCount(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get push events count report.");
		CommonResponse response = new CommonResponse();
		try {
			if(req.getEventType()!=null) {
			String[] events = req.getEventType().split(",");
			for(String event:events) {
			if(!EventCommands.contains(event.trim())) {
				response.setMessage("Invalid event type : "+event);
				response.setCode(404);
				response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			   }
			  }
			}
			response = pushEventAlarmService.getEventsPushData(req);
			LOG.info("Push Event Count Report Success On Controller.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in get push events report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/add-category", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addEventsCategory(@Valid @RequestBody List<PriorityEventRequest> req) {
		LOG.info("Request received to add push events Owner.");
		CommonResponse response = new CommonResponse();
		try {
			
			response = pushEventAlarmService.addEventsCategory(req);
			LOG.info("Add push events Owner Success.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in add push events Owner due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/update-category", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateEventsCategory(@Valid @RequestBody List<PriorityEventRequest> req) {
		LOG.info("Request received to add push events Category.");
		CommonResponse response = new CommonResponse();
		try {

			response = pushEventAlarmService.updateEventsCategory(req);
			LOG.info("Add push events Category Success.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in add push events report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get-category", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getEventsCategory(@Valid @RequestBody PriorityEventRequest req) {
		LOG.info("Request received to get push events Category.");
		CommonResponse response = new CommonResponse();
		try {
			
			response = pushEventAlarmService.getEventsCategory(req);
			LOG.info("get push events Category Success.");
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			LOG.error("Issue in get push events report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/delete-category", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteEventsCategory(@Valid @RequestBody PriorityEventRequest req) {
		LOG.info("Request received to delete push events Category.");
		CommonResponse response = new CommonResponse();
		try {
				if((req.getHighPriorityEventList() == null || req.getHighPriorityEventList().isEmpty()) &&
						(req.getCriticalEventList() == null || req.getCriticalEventList().isEmpty())) {
					 response.setMessage("High Priority and Critical events is null or empty.");
					 response.setCode(404);
					 response.setError(true);
					 return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				   }
			response = pushEventAlarmService.deleteEventsCategory(req);
			LOG.info("delete push events Category Success.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in delete push events report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get/highPriorityEvents", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getHighPriorityEvents(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get High Priority Events count report.");
		CommonResponse response = new CommonResponse();
		try {
			response = pushEventAlarmService.getHighPriorityEvents(req);
			LOG.info("High Priority Events Count Report Success On Controller.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in get High Priority Events report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get/categorizedEvents", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getCriticalNonCriticalEvents(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get critical and non-critical Events count report.");
		CommonResponse response = new CommonResponse();
		try {
			req.setCategorizedEvent(Constants.CATEGORIZED_EVENTS);
			response = pushEventAlarmService.getEventsPushData(req);
			LOG.info("critical and non-critical Event Count Report Success On Controller.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in get critical and non-critical events report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get/categorizedEventsList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getCriticalNonCriticalEventsList(@Valid @RequestBody MeterDataVisualizationReq req) {
		LOG.info("Request received to get critical or non-critical Events list count report.");
		CommonResponse response = new CommonResponse();
		try {
			req.setCategorizedEventList(Constants.CATEGORIZED_EVENTS_LIST);
			response = pushEventAlarmService.getEventsPushData(req);
			LOG.info("critical or non-critical Event Count Report Success On Controller.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in get critical or non-critical events report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/getList", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getPushEventsList() {
		LOG.info("Request received to get Push Events List report.");
		CommonResponse response = new CommonResponse();
		try {
			response = pushEventAlarmService.getPushEventsList();
			LOG.info("Get Push Events List Report Success On Controller.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in get Push Events List report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/getCategoryList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getPushEventsCategoryList(@Valid @RequestBody PriorityEventRequest req) {
		LOG.info("Request received to get Push Events Catergory List report.");
		CommonResponse response = new CommonResponse();
		try {
			response = pushEventAlarmService.getPushEventsCategoryList(req);
			LOG.info("Get Push Events Catergory List Report Success On Controller.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in get Push Events Catergory List report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}
