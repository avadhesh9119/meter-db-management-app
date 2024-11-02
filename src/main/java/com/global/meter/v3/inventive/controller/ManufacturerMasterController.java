package com.global.meter.v3.inventive.controller;

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
import com.global.meter.v3.inventive.models.ManufacturerMasterDataRequest;
import com.global.meter.v3.inventive.repository.ManufacturerMasterRepository;
import com.global.meter.v3.inventive.service.ManufacturerMasterService;

@RestController
@RequestMapping(value = "/hes/manufacturer-master")
public class ManufacturerMasterController {

	private static final Logger LOG = LoggerFactory.getLogger(ManufacturerMasterController.class);

	@Autowired
	ManufacturerMasterService manufacturerService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ManufacturerMasterRepository manufacturerRepo;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addManufacturer(
			@Valid @RequestBody List<ManufacturerMasterDataRequest> manufacturerMasterDataRequests) {
		LOG.info("Request recieved to add manufacturer informations");
		CommonResponse response = new CommonResponse();
		try {
			response = manufacturerService.addManufacturer(manufacturerMasterDataRequests);
			LOG.info("Request success to add manufacturer informations");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding manufacturer Info due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateManufacturer(
			@Valid @RequestBody List<ManufacturerMasterDataRequest> manufacturerMasterDataRequests) {
		LOG.info("Request recieved to update manufacturer informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = manufacturerService.updateManufacturer(manufacturerMasterDataRequests);

			LOG.info("Request success to update manufacturer informations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating manufacturer Info due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteManufacturer(
			@Valid @RequestBody ManufacturerMasterDataRequest manufacturerMasterDataRequests) {
		LOG.info("Request recieved to delete Manufacturer..." + manufacturerMasterDataRequests.getCode());
		CommonResponse response = new CommonResponse();
		try {

			response = manufacturerService.deleteManufacturer(manufacturerMasterDataRequests);

			LOG.info("Request success to delete manufacturer informations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in deleting manufacturer Info due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getManufacturer(
			@Valid @RequestBody ManufacturerMasterDataRequest manufacturerMasterDataRequest) {
		LOG.info("Request recieved to get manufacturer informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = manufacturerService.getManufacturer(manufacturerMasterDataRequest);

			LOG.info("Request success to get manufacturer informations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting manufacturer Info due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/getList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getManufacturerList(@Valid @RequestBody ManufacturerMasterDataRequest manufacturerMasterDataRequest) {
		LOG.info("Request recieved to get manufacturer list...");
		CommonResponse response = new CommonResponse();
		try {

			response = manufacturerService.getManufacturerNameList();

			LOG.info("Request success to get list of manufacturer informations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting manufacturer list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
