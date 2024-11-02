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

import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.PriorityEventRequest;
import com.global.meter.inventive.service.PullEventCategoryService;
import com.global.meter.utils.Constants;

@RestController
@RequestMapping(value = "/hes/devices/pull/event")
public class PullEventCategoryController {

	private static final Logger LOG = LoggerFactory.getLogger(PullEventCategoryController.class);

	@Autowired
	PullEventCategoryService pullEventCategoryService;

	@RequestMapping(value = "/add-category", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addEventsCategory(@Valid @RequestBody PriorityEventRequest req) {
		LOG.info("Request received to add pull events category.");
		CommonResponse response = new CommonResponse();
		try {

			response = pullEventCategoryService.addEventsCategory(req);
			LOG.info("Add pull events category Success.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in add push events Owner due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/update-category", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateEventsCategory(@Valid @RequestBody PriorityEventRequest req) {
		LOG.info("Request received to update pull events Category.");
		CommonResponse response = new CommonResponse();
		try {

			response = pullEventCategoryService.updateEventsCategory(req);
			LOG.info("Update pull events Category Success.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in update pull events report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get-category", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getEventsCategory(@Valid @RequestBody PriorityEventRequest req) {
		LOG.info("Request received to get pull events Category.");
		CommonResponse response = new CommonResponse();
		try {

			response = pullEventCategoryService.getEventsCategory(req);
			LOG.info("get pull events Category Success.");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in get pull events report due to :" + e.getMessage());
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
			response = pullEventCategoryService.getPullEventsCategoryCountDetail(req);
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
			response = pullEventCategoryService.getPullEventsCategoryCountDetail(req);
			LOG.info("critical or non-critical Event Count Report Success On Controller.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in get critical or non-critical events report due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
