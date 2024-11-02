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

import com.global.meter.data.repository.SubdivisionsRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.SubdivisionsDataRequest;
import com.global.meter.inventive.service.SubdivisionsService;

@RestController
@RequestMapping(value = "/hes/subdivision")
public class SubdivisionsController {

	private static final Logger LOG = LoggerFactory.getLogger(SubdivisionsController.class);

	@Autowired
	SubdivisionsService subdivisionsService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	SubdivisionsRepository subDivisionRepo;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addDevice(
			@Valid @RequestBody List<SubdivisionsDataRequest> subdivisionsDataRequests) {
		LOG.info("Request recieved to add subdivision informations");
		CommonResponse response = new CommonResponse();
		try {
			response = subdivisionsService.addSubdivision(subdivisionsDataRequests);
			LOG.info("Request success to add subdivisions informations");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding subdivision Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateSubdivision(
			@Valid @RequestBody List<SubdivisionsDataRequest> subdivisionsDataRequests) {
		LOG.info("Request recieved to update subdivision informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = subdivisionsService.updateSubdivision(subdivisionsDataRequests);
			
			LOG.info("Request success to update subdivisions informations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating subdivision Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSubdivision(@Valid @RequestBody SubdivisionsDataRequest subDivisionsDataRequests) {
		LOG.info("Request recieved to get subdevision informations..." + subDivisionsDataRequests.getSubdivisionName());
		CommonResponse response = new CommonResponse();
		try {

			response = subdivisionsService.getSubdivision(subDivisionsDataRequests);
			
			LOG.info("Request success to get subdivisions informations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting subdivision Info due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteDevice(@Valid @RequestBody SubdivisionsDataRequest subdivisionsDataRequests) {
		LOG.info("Request recieved to delete subdivision..." + subdivisionsDataRequests.getSubdivisionName());
		CommonResponse response = new CommonResponse();
		try {

			response = subdivisionsService.deleteSubdivision(subdivisionsDataRequests);
			
			LOG.info("Request success to delete subdivisions informations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in deleting subdivision Info due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/getList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSubdevisionList(
			@Valid @RequestBody SubdivisionsDataRequest subDivisionDataRequest) {
		LOG.info("Request recieved to get subdivision list..." + subDivisionDataRequest.getSubdivisionName());
		CommonResponse response = new CommonResponse();
		try {

			response = subdivisionsService.getSubdivisionList();
			
			LOG.info("Request success to get list of subdivisions informations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting subdivision list due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/getNameList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSubdivisionNameList(
			@Valid @RequestBody SubdivisionsDataRequest subDivisionDataRequest) {
		LOG.info("Request recieved to get subdivision name list..." + subDivisionDataRequest.getSubdivisionName());
		CommonResponse response = new CommonResponse();
		try {

			response = subdivisionsService.getSubdivisionNameList();
			
			LOG.info("Request success to get list of subdivisions name informations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of subdivision name due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	
	@RequestMapping(value = "/getListByUtility", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSubdivisionListByUtility(
			@Valid @RequestBody SubdivisionsDataRequest subDivisionDataRequest) {
		LOG.info("Request recieved to get subdivision list by utility..." + subDivisionDataRequest.getUtility());
		CommonResponse response = new CommonResponse();
		try {

			response = subdivisionsService.getListByUtility(subDivisionDataRequest);
			
			LOG.info("Request success to get list of subdivisions by utility");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of subdivision by utility due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
