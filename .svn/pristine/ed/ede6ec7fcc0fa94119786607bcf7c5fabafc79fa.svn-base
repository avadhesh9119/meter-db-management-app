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

import com.global.meter.data.repository.FeedersMasterRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.FeederMasterDataRequest;
import com.global.meter.inventive.service.FeedersMasterService;

@RestController
@RequestMapping(value = "/hes/feeder")
public class FeedersMasterController {

	private static final Logger LOG = LoggerFactory.getLogger(FeedersController.class);

	@Autowired
	FeedersMasterService feedersService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	FeedersMasterRepository feedersRepository;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addFeeders(@Valid @RequestBody List<FeederMasterDataRequest> feederDataRequests) {
		LOG.info("Request recieved to add feeders informations...");
		CommonResponse response = new CommonResponse();
		try {
			response = feedersService.addFeeders(feederDataRequests);
			LOG.info("Request success to add feeders informations...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding feeders Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateFeeders(@Valid @RequestBody List<FeederMasterDataRequest> feederDataRequests) {
		LOG.info("Request recieved to update feeders informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = feedersService.updateFeeders(feederDataRequests);
			
			LOG.info("Request success to update feeders informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating feeders Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getFeeder(@Valid @RequestBody FeederMasterDataRequest feederDataRequests) {
		LOG.info("Request recieved to get feeder informations..." + feederDataRequests.getSubdivisionName());
		CommonResponse response = new CommonResponse();
		try {

			response = feedersService.getFeeder(feederDataRequests);
			
			LOG.info("Request success to get feeders informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting feeders Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteFeeder(@Valid @RequestBody FeederMasterDataRequest feederDataRequests) {
		LOG.info("Request recieved to delete feeder..." + feederDataRequests.getSubdivisionName());
		CommonResponse response = new CommonResponse();
		try {

			response = feedersService.deleteFeeders(feederDataRequests);
			
			LOG.info("Request success to delete feeders informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in deleting feeders Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/getList", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getFeederList(@Valid @RequestBody FeederMasterDataRequest feederDataRequests) {
		LOG.info("Request recieved to get feeders list..." + feederDataRequests.getSubdivisionName());
		CommonResponse response = new CommonResponse();
		try {

			response = feedersService.getFeedersList();
			
			LOG.info("Request success to get feeders list");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of feeders Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/getFeederNameList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getFeederNameList(@Valid @RequestBody FeederMasterDataRequest feederDataRequests) {
		LOG.info("Request recieved to get feeders list..." + feederDataRequests.getSubdivisionName());
		CommonResponse response = new CommonResponse();
		try {

			response = feedersService.getFeederNameList();
			
			LOG.info("Request success to get feeders name list");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting feeders name list due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value = "/get-list-by-substation", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDeviceCommandLogList(
			@Valid @RequestBody FeederMasterDataRequest feederDataRequests) {
		LOG.info("Request recieved to get feeder list by substation..." + feederDataRequests.getSubstationName());
		CommonResponse response = new CommonResponse();
		try {

			response = feedersService.getFeedersBySubstation(feederDataRequests);
			
			LOG.info("Request success to get feeders informations by substations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting feeders Info by substation due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value = "utility/get/list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getFeedersByUtility(
			@Valid @RequestBody FeederMasterDataRequest feederDataRequests) {
//		LOG.info("Request recieved to get feeder list by utitlity..." + feederDataRequests.getUtility());
		CommonResponse response = new CommonResponse();
		try {

			response = feedersService.getFeedersByUtility(feederDataRequests);
			
			LOG.info("Request success to get feeders informations by utitlity");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting feeders Info by utitlity due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}
