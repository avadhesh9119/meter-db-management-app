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

import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DcuMasterReq;
import com.global.meter.inventive.models.DcuPowerReq;
import com.global.meter.inventive.service.DcuMasterService;

@RestController
@RequestMapping(value = "/hes/dcu-master")
public class DcuMasterController {

	private static final Logger LOG = LoggerFactory.getLogger(DcuMasterController.class);

	@Autowired
	DcuMasterService dcuMasterService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addDcuMaster(@Valid @RequestBody List<DcuMasterReq> Req) {
		LOG.info("Request recieved to add Dcu Meter master...");
		CommonResponse response = new CommonResponse();
		try {
			response = dcuMasterService.addDcuMaster(Req);
			LOG.info("Request success to add Dcu master.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding Dcu master Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateDcuMaster(@Valid @RequestBody List<DcuMasterReq> Req) {
		LOG.info("Request recieved to update Dcu Meter master...");
		CommonResponse response = new CommonResponse();
		try {
			response = dcuMasterService.updateDcuMaster(Req);
			LOG.info("Request success to update Dcu master.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding Dcu master Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteDcuMaster(@Valid @RequestBody DcuMasterReq Req) {
		LOG.info("Request recieved delete Dcu master...");
		CommonResponse response = new CommonResponse();
		try {
			response = dcuMasterService.deleteDcuMaster(Req);

			LOG.info("Request success to delete Dcu master.");

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in delete Dcu master Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDcuMaster(@Valid @RequestBody DcuMasterReq Req) {
		LOG.info("Request recieved get Dcu Meter master...");
		CommonResponse response = new CommonResponse();
		try {
			response = dcuMasterService.getDcuMaster(Req);

			LOG.info("Request success to get Dcu master.");

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding Dcu master Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get-list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getListDcuMaster(@Valid @RequestBody DcuMasterReq Req) {
		LOG.info("Request recieved get Dcu getway Meter master...");
		CommonResponse response = new CommonResponse();
		try {
			response = dcuMasterService.getListDcuMaster(Req);

			LOG.info("Request success to get dcu master informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding Dcu list master Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/deleted-dcu-logs/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDcuDeleteMaster(@Valid @RequestBody DcuMasterReq Req) {
		LOG.info("Request recieved get Dcu getway Meter master...");
		CommonResponse response = new CommonResponse();
		try {
			response = dcuMasterService.getDcuDeleteMaster(Req);

			LOG.info("Request success to get delete dcu master informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding Dcu list master Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	//DCU health report for Push data 
	
	@RequestMapping(value = "/dcu-power", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")		
		public ResponseEntity<Object> dcuPower(@Valid @RequestBody DcuPowerReq Req){
		LOG.info("Request recieved add Dcu power data.");
		CommonResponse response = new CommonResponse();
		try {
			response = dcuMasterService.dcuPower(Req);

			LOG.info("Request success to add dcu power ");

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding Dcu list master Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	

	@RequestMapping(value = "/dcu-push/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")		
		public ResponseEntity<Object> getDcuPush(@Valid @RequestBody DcuPowerReq Req){
		
		LOG.info("Request received to push Dcu report for : "+ Req.toString());
		CommonResponse response = new CommonResponse();
		try {
			response = dcuMasterService.getDcuPush(Req);

			LOG.info("Request sucess to generate push dcu report for : ");

 			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding Dcu list master Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}
