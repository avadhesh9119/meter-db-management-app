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

import com.global.meter.data.repository.DivisionMasterRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DivisionMasterDataRequest;
import com.global.meter.inventive.service.DivisionMasterService;

@RestController
@RequestMapping(value = "/hes/division")
public class DivisionMasterController {

	private static final Logger LOG = LoggerFactory.getLogger(DivisionMasterController.class);

	@Autowired
	DivisionMasterService divisionService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DivisionMasterRepository feedersRepository;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addDivision(
			@Valid @RequestBody List<DivisionMasterDataRequest> divisionDataRequests) {
		LOG.info("Request recieved to add divisions informations...");
		CommonResponse response = new CommonResponse();
		try {
			response = divisionService.addDivision(divisionDataRequests);
			LOG.info("Request success to add divisions informations...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding divisions Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateDivision(
			@Valid @RequestBody List<DivisionMasterDataRequest> divisionDataRequests) {
		LOG.info("Request recieved to update divisions informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = divisionService.updateDivision(divisionDataRequests);

			LOG.info("Request success to update divisions informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating divisions Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDivision(@Valid @RequestBody DivisionMasterDataRequest divisionDataRequests) {
		LOG.info("Request recieved to get divisions informations..." + divisionDataRequests.getZoneName());
		CommonResponse response = new CommonResponse();
		try {

			response = divisionService.getDivision(divisionDataRequests);

			LOG.info("Request success to get divisions informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting divisions Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

//	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
//	public ResponseEntity<Object> deleteDivision(@Valid @RequestBody DivisionMasterDataRequest divisionDataRequests) {
//		LOG.info("Request recieved to delete divisions..." + divisionDataRequests.getZoneName());
//		CommonResponse response = new CommonResponse();
//		try {
//
//			response = divisionService.deleteDivision(divisionDataRequests);
//
//			LOG.info("Request success to delete divisions informations...");
//
//			return new ResponseEntity<>(response, HttpStatus.OK);
//
//		} catch (Exception e) {
//			LOG.error("Issue in deleting divisions Info due to :" + e.getMessage());
//			ErrorData error = new ErrorData();
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//		}
//	}

	@RequestMapping(value = "/getList", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDivisionList(@Valid @RequestBody DivisionMasterDataRequest divisionDataRequests) {
		LOG.info("Request recieved to get divisions list..." + divisionDataRequests.getZoneName());
		CommonResponse response = new CommonResponse();
		try {

			response = divisionService.getDivisionList();

			LOG.info("Request success to get divisions list");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of divisions Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

//	@RequestMapping(value = "/getDivisionNameList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//	public ResponseEntity<Object> getDivisionNameList(
//			@Valid @RequestBody DivisionMasterDataRequest divisionDataRequests) {
//		LOG.info("Request recieved to get Division list..." + divisionDataRequests.getZoneName());
//		CommonResponse response = new CommonResponse();
//		try {
//
//			response = divisionService.getDivisionNameList();
//
//			LOG.info("Request success to get divisions name list");
//
//			return new ResponseEntity<>(response, HttpStatus.OK);
//
//		} catch (Exception e) {
//			LOG.error("Issue in getting divisions name list due to :" + e.getMessage());
//			ErrorData error = new ErrorData();
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//		}
//	}
//
	@RequestMapping(value = "/get-list-by-circle", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDivisionByCircle(
			@Valid @RequestBody DivisionMasterDataRequest divisionDataRequests) {
		LOG.info("Request recieved to get division list by circle..." + divisionDataRequests.getCircleCode());
		CommonResponse response = new CommonResponse();
		try {

			response = divisionService.getDivisionByCircle(divisionDataRequests);

			LOG.info("Request success to get division informations by circle");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting divisions Info by circle due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

//	@RequestMapping(value = "utility/get/list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//	public ResponseEntity<Object> getDivisionByUtility(
//			@Valid @RequestBody DivisionMasterDataRequest divisionDataRequests) {
//		LOG.info("Request recieved to get division list by utitlity..." + divisionDataRequests.getOwnerName());
//		CommonResponse response = new CommonResponse();
//		try {
//
//			response = divisionService.getDivisionByUtility(divisionDataRequests);
//
//			LOG.info("Request success to get divisions informations by utitlity");
//
//			return new ResponseEntity<>(response, HttpStatus.OK);
//
//		} catch (Exception e) {
//			LOG.error("Issue in getting divisions Info by utitlity due to :" + e.getMessage());
//			ErrorData error = new ErrorData();
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//		}
//	}
}
