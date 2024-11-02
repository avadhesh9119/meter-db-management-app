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
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.SubstationsMasterDataRequest;
import com.global.meter.inventive.service.SubstationsMasterService;

@RestController
@RequestMapping(value = "/hes/substations")
public class SubstationsMasterController {

	private static final Logger LOG = LoggerFactory.getLogger(SubstationsController.class);

	@Autowired
	SubstationsMasterService substationsMasterService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	SubstationRepository subStationRepo;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addSubstation(
			@Valid @RequestBody List<SubstationsMasterDataRequest> substationsMasterDataRequest) {
		LOG.info("Request recieved to add substations informations...");
		CommonResponse response = new CommonResponse();
		try {
			response = substationsMasterService.addSubstation(substationsMasterDataRequest);
			LOG.info("Request success to add substations informations...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding substations Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateSubstation(
			@Valid @RequestBody List<SubstationsMasterDataRequest> substationsMasterDataRequest) {
		LOG.info("Request recieved to update substations informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = substationsMasterService.updateSubstation(substationsMasterDataRequest);
			LOG.info("Request success to update substations informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating substations Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSubstation(@Valid @RequestBody SubstationsMasterDataRequest substationsDataRequest) {
		LOG.info("Request recieved to get substations informations..." + substationsDataRequest.getSubstationName());
		CommonResponse response = new CommonResponse();
		try {

			response = substationsMasterService.getSubstation(substationsDataRequest);
			LOG.info("Request success to get substations informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting substations Info due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteSubstation(@Valid @RequestBody SubstationsMasterDataRequest substationsDataRequest) {
		LOG.info("Request recieved to delete substation..." + substationsDataRequest.getSubstationName());
		CommonResponse response = new CommonResponse();
		try {

			response = substationsMasterService.deleteSubstation(substationsDataRequest);
			
			LOG.info("Request success to delete substations informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in deleting substations Info due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/getList", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSubstationList() {
		LOG.info("Request recieved to get substation list...");
		CommonResponse response = new CommonResponse();
		try {

			response = substationsMasterService.getSubstationList();
			
			LOG.info("Request success to get list of substations informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of substations Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/getNameList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSubstationNameList() {
		LOG.info("Request recieved to get substation Name list...");
		CommonResponse response = new CommonResponse();
		try {

			response = substationsMasterService.getSubstationNameList();
			
			LOG.info("Request success to get list of substations name informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of substations name  due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get-list-by-subdivision", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSubdivisionListByUtility(
			@Valid @RequestBody SubstationsMasterDataRequest substationsDataRequest) {
		LOG.info("Request recieved to get substation list by subdivision..."
				+ substationsDataRequest.getSubdivisionName());
		CommonResponse response = new CommonResponse();
		try {

			response = substationsMasterService.getListBySubdivision(substationsDataRequest);
			
			LOG.info("Request success to get list of substations informations by subdivision");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of substations name by subdivision due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	
	@RequestMapping(value = "utility/get/list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getSubstationsByUtility(
			@Valid @RequestBody SubstationsMasterDataRequest substationsDataRequest) {
		LOG.info("Request recieved to get substation list by utility..."
				+ substationsDataRequest.getSubdivisionName());
		CommonResponse response = new CommonResponse();
		try {

			response = substationsMasterService.getSubstationsByUtility(substationsDataRequest);
			
			LOG.info("Request success to get list of substations informations by subdivision");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of substations name by utility due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
