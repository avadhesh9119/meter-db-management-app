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

import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.OwnerDataRequest;
import com.global.meter.inventive.service.OwnerService;

@RestController
@RequestMapping(value = "/hes/owner")
public class OwnerMasterController {

	private static final Logger LOG = LoggerFactory.getLogger(SubstationsController.class);

	@Autowired
	OwnerService ownerService;

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addOwner(
			@Valid @RequestBody List<OwnerDataRequest> ownerDataRequests) {
		LOG.info("Request recieved to add owner informations...");
		CommonResponse response = new CommonResponse();
		try {
			response = ownerService.addOwner(ownerDataRequests);
			LOG.info("Request success to add owner informations...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding owner Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateOwner(
			@Valid @RequestBody List<OwnerDataRequest> ownerDataRequests) {
		LOG.info("Request recieved to update owners informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = ownerService.updateOwner(ownerDataRequests);
			LOG.info("Request success to update owner informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating owners Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getOwner(@Valid @RequestBody OwnerDataRequest ownerDataRequest) {
		LOG.info("Request recieved to get owners informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = ownerService.getOwner(ownerDataRequest);
			LOG.info("Request success to get owners informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting owners Info due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(value = "/getList", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getOwnerList() {
		LOG.info("Request recieved to get owners list informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = ownerService.getOwnerList();
			LOG.info("Request success to get owners list informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting owners list Info due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
//	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
//	public ResponseEntity<Object> deleteOwner(@Valid @RequestBody OwnerDataRequest ownerDataRequest) {
//		LOG.info("Request recieved to delete owner..." + ownerDataRequest.getOwnerId());
//		CommonResponse response = new CommonResponse();
//		try {
//
//			response = ownerService.deleteOwner(ownerDataRequest);
//			
//			LOG.info("Request success to delete owners informations...");
//
//			return new ResponseEntity<>(response, HttpStatus.OK);
//
//		} catch (Exception e) {
//			LOG.error("Issue in deleting owners Info due to:" + e.getMessage());
//			ErrorData error = new ErrorData();
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//		}
//	}


}