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

import com.global.meter.data.repository.ZoneMasterRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ZoneMasterDataRequest;
import com.global.meter.inventive.service.ZoneMasterService;

@RestController
@RequestMapping(value = "/hes/zone")
public class ZoneMasterController {

	private static final Logger LOG = LoggerFactory.getLogger(ZoneMasterController.class);

	@Autowired
	ZoneMasterService zoneService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ZoneMasterRepository zoneRepo;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addZone(@Valid @RequestBody List<ZoneMasterDataRequest> zoneDataRequests) {
		LOG.info("Request recieved to add zone informations");
		CommonResponse response = new CommonResponse();
		try {
			response = zoneService.addZone(zoneDataRequests);
			LOG.info("Request success to add zone informations");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding zone Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateZone(@Valid @RequestBody List<ZoneMasterDataRequest> zoneDataRequests) {
		LOG.info("Request recieved to update zone informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = zoneService.updateZone(zoneDataRequests);

			LOG.info("Request success to update zone informations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating zone Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

//	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
//	public ResponseEntity<Object> deleteZone(@Valid @RequestBody ZoneMasterDataRequest zoneDataRequests) {
//		LOG.info("Request recieved to delete zone..." + zoneDataRequests.get());
//		CommonResponse response = new CommonResponse();
//		try {
//
//			response = zoneService.deleteZone(zoneDataRequests);
//
//			LOG.info("Request success to delete zone informations");
//
//			return new ResponseEntity<>(response, HttpStatus.OK);
//
//		} catch (Exception e) {
//			LOG.error("Issue in deleting zone Info due to:" + e.getMessage());
//			ErrorData error = new ErrorData();
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//		}
//	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getZone(@Valid @RequestBody ZoneMasterDataRequest zoneDataRequests) {
		LOG.info("Request recieved to get zone informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = zoneService.getZone(zoneDataRequests);

			LOG.info("Request success to get zone informations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting zone Info due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/getList", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getZoneList(@Valid @RequestBody ZoneMasterDataRequest zoneDataRequests) {
		LOG.info("Request recieved to get zone list...");
		CommonResponse response = new CommonResponse();
		try {

			response = zoneService.getZoneList();

			LOG.info("Request success to get list of zone informations");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting zone list due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

//	@RequestMapping(value = "/getNameList", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//	public ResponseEntity<Object> getSubdivisionNameList(@Valid @RequestBody ZoneMasterDataRequest zoneDataRequests) {
//		LOG.info("Request recieved to get zone name list..." + zoneDataRequests.getHesZoneId());
//		CommonResponse response = new CommonResponse();
//		try {
//
//			response = zoneService.getZoneNameList();
//
//			LOG.info("Request success to get list of zone name informations");
//
//			return new ResponseEntity<>(response, HttpStatus.OK);
//
//		} catch (Exception e) {
//			LOG.error("Issue in getting list of zone name due to:" + e.getMessage());
//			ErrorData error = new ErrorData();
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//		}
//	}
//
	@RequestMapping(value = "/get-list-by-utility", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getListByUtility(@Valid @RequestBody ZoneMasterDataRequest zoneDataRequests) {
		LOG.info("Request recieved to get zone list by utility..." + zoneDataRequests.getLevel1Id());
		CommonResponse response = new CommonResponse();
		try {

			response = zoneService.getListByUtility(zoneDataRequests);

			LOG.info("Request success to get list of zone by utility");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of zone by utility due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

}
