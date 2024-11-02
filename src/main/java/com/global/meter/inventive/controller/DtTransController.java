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

import com.global.meter.data.repository.DtTransRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DtTransDataRequest;
import com.global.meter.inventive.service.DtTransService;

@RestController
@RequestMapping(value = "/hes/dt")
public class DtTransController {

	private static final Logger LOG = LoggerFactory.getLogger(DtTransController.class);

	@Autowired
	DtTransService dtService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DtTransRepository dtRepository;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addDtTrans(@Valid @RequestBody List<DtTransDataRequest> dtTransDataRequests) {
		LOG.info("Request recieved to add DT informations...");
		CommonResponse response = new CommonResponse();
		try {
			response = dtService.addDtTrans(dtTransDataRequests);
			LOG.info("Request success to add DT informations...");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding DT Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateDtTrans(@Valid @RequestBody List<DtTransDataRequest> dtTransDataRequests) {
		LOG.info("Request recieved to update DT informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = dtService.updateDtTrans(dtTransDataRequests);
			
			LOG.info("Request success to update DT informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating DT Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDtTrans(@Valid @RequestBody DtTransDataRequest dtTransDataRequests) {
		LOG.info("Request recieved to get DT informations..." + dtTransDataRequests.getSubdivisionName());
		CommonResponse response = new CommonResponse();
		try {

			response = dtService.getDtTrans(dtTransDataRequests);
			
			LOG.info("Request success to get DT informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting DT Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteDtTrans(@Valid @RequestBody DtTransDataRequest dtTransDataRequests) {
		LOG.info("Request recieved to DT device..." + dtTransDataRequests.getSubdivisionName());
		CommonResponse response = new CommonResponse();
		try {

			response = dtService.deleteDtTrans(dtTransDataRequests);
			
			LOG.info("Request success to delete DT informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in deleting DT Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/getList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDtTransList(@Valid @RequestBody DtTransDataRequest dtTransDataRequests) {
		LOG.info("Request recieved to get DT list..." + dtTransDataRequests.getSubdivisionName());
		CommonResponse response = new CommonResponse();
		try {

			response = dtService.getDtTransList();
			
			LOG.info("Request success to get DT list informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting DT list Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/getNameList" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDtTransNameList(@Valid @RequestBody DtTransDataRequest dtTransDataRequests)
	{
		LOG.info("Request recieved to get DT Name list..."+ dtTransDataRequests.getDtName());
		CommonResponse response = new CommonResponse();
		try {
			
			response = dtService.getDtTransNameList();
			
			LOG.info("Request success to get DT name list informations...");
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			LOG.error("Issue in getting DT name list due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@RequestMapping(value = "/getdtListByFeeder", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDtListByFeeder(
			@Valid @RequestBody DtTransDataRequest dtTransDataRequests) {
		LOG.info("Request recieved to get dt list by feeder..." + dtTransDataRequests.getFeederName());
		CommonResponse response = new CommonResponse();
		try {

			response = dtService.getDtListByFeeder(dtTransDataRequests);
			
			LOG.info("Request success to get DT list by feeder...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting DT list by feeder due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value = "/meterList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterListByDt(
			@Valid @RequestBody DtTransDataRequest dtTransDataRequests) {
		LOG.info("Request recieved to get Meter list by DT..." + dtTransDataRequests.getFeederName());
		CommonResponse response = new CommonResponse();
		try {

			response = dtService.getMeterListByDt(dtTransDataRequests);
			
			LOG.info("Request success to get Meter list by DT...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting Meter list by DT due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "utility/get/list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDtListByUtility(
			@Valid @RequestBody DtTransDataRequest dtTransDataRequests) {
		LOG.info("Request recieved to get dt list by utility..." + dtTransDataRequests.getUtility());
		CommonResponse response = new CommonResponse();
		try {

			response = dtService.getDtListByUtility(dtTransDataRequests);
			
			LOG.info("Request success to get DT list by utility...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting DT list by utility due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
