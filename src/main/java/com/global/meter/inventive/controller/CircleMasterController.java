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

import com.global.meter.data.repository.SubstationRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CircleMasterDataRequest;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.service.CircleMasterService;

@RestController
@RequestMapping(value = "/hes/circle")
public class CircleMasterController {

	private static final Logger LOG = LoggerFactory.getLogger(CircleMasterController.class);

	@Autowired
	CircleMasterService circleMasterService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	SubstationRepository subStationRepo;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addCircle(@Valid @RequestBody List<CircleMasterDataRequest> circleMasterDataRequest) {
		LOG.info("Request recieved to add circle informations...");
		CommonResponse response = new CommonResponse();
		try {
			response = circleMasterService.addCircle(circleMasterDataRequest);
			LOG.info("Request success to add circle informations...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding circle Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateCircle(
			@Valid @RequestBody List<CircleMasterDataRequest> circleMasterDataRequest) {
		LOG.info("Request recieved to update circle informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = circleMasterService.updateCircle(circleMasterDataRequest);
			LOG.info("Request success to update circle informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating circle Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getCircle(@Valid @RequestBody CircleMasterDataRequest circleMasterDataRequest) {
		LOG.info("Request recieved to get circle informations..." + circleMasterDataRequest.getCircleName());
		CommonResponse response = new CommonResponse();
		try {

			response = circleMasterService.getCircle(circleMasterDataRequest);
			LOG.info("Request success to get circle informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting circle Info due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

//	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
//	public ResponseEntity<Object> deleteCircle(@Valid @RequestBody CircleMasterDataRequest circleMasterDataRequest) {
//		LOG.info("Request recieved to delete circle..." + circleMasterDataRequest.getCircleName());
//		CommonResponse response = new CommonResponse();
//		try {
//
//			response = circleMasterService.deleteCircle(circleMasterDataRequest);
//
//			LOG.info("Request success to delete circle informations...");
//
//			return new ResponseEntity<>(response, HttpStatus.OK);
//
//		} catch (Exception e) {
//			LOG.error("Issue in deleting circle Info due to:" + e.getMessage());
//			ErrorData error = new ErrorData();
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//		}
//	}

	@RequestMapping(value = "/getList", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getCircleList() {
		LOG.info("Request recieved to get circle list...");
		CommonResponse response = new CommonResponse();
		try {

			response = circleMasterService.getCircleList();

			LOG.info("Request success to get list of circle informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of circle Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

//	@RequestMapping(value = "/getNameList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//	public ResponseEntity<Object> getCircleNameList() {
//		LOG.info("Request recieved to get circle Name list...");
//		CommonResponse response = new CommonResponse();
//		try {
//
//			response = circleMasterService.getCircleNameList();
//
//			LOG.info("Request success to get list of circle name informations...");
//
//			return new ResponseEntity<>(response, HttpStatus.OK);
//
//		} catch (Exception e) {
//			LOG.error("Issue in getting list of circle name  due to:" + e.getMessage());
//			ErrorData error = new ErrorData();
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//		}
//	}
//
//	@RequestMapping(value = "/getListByZone", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//	public ResponseEntity<Object> getCircleListByUtility(
//			@Valid @RequestBody CircleMasterDataRequest circleMasterDataRequest) {
//		LOG.info("Request recieved to get circle list by zone..." + circleMasterDataRequest.getCircleName());
//		CommonResponse response = new CommonResponse();
//		try {
//
//			response = circleMasterService.getListByCircle(circleMasterDataRequest);
//
//			LOG.info("Request success to get list of circle informations by zone");
//
//			return new ResponseEntity<>(response, HttpStatus.OK);
//
//		} catch (Exception e) {
//			LOG.error("Issue in getting list of circle name by zone due to:" + e.getMessage());
//			ErrorData error = new ErrorData();
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//		}
//	}

	@RequestMapping(value = "/get-list-by-zone", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSubstationsByUtility(
			@Valid @RequestBody CircleMasterDataRequest circleMasterDataRequest) {
		LOG.info("Request recieved to get circle list by utility..." + circleMasterDataRequest.getZoneCode());
		CommonResponse response = new CommonResponse();
		try {

			response = circleMasterService.getCircleByZone(circleMasterDataRequest);

			LOG.info("Request success to get list of circle informations by zone");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of circle name by utility due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
