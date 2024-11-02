package com.global.meter.inventive.mdm.controller;

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
import com.global.meter.inventive.mdm.models.MdmMasterRequest;
import com.global.meter.inventive.mdm.service.MdmMasterService;
import com.global.meter.inventive.models.CommonResponse;

@RestController
@RequestMapping(value = "/hes/mdm-master")
public class MdmMasterController {

	private static final Logger LOG = LoggerFactory.getLogger(MdmMasterController.class);

	@Autowired
	MdmMasterService mdmMasterService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addMdmMaster(@Valid @RequestBody List<MdmMasterRequest> mdmdMasterRequest) {
		LOG.info("Request recieved to add MDM Master...");
		CommonResponse response = new CommonResponse();
		try {

			response = mdmMasterService.addMdmMaster(mdmdMasterRequest);

			LOG.info("Request success to add MDM Master...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.info("Issue in add MDM Master due to: " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateMdmMaster(@Valid @RequestBody List<MdmMasterRequest> mdmdMasterRequest) {
		LOG.info("Request recieved to update MDM Master...");
		CommonResponse response = new CommonResponse();
		try {

			response = mdmMasterService.updateMdmMaster(mdmdMasterRequest);

			LOG.info("Request success to update MDM Master...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.info("Issue in update MDM Master due to: " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteMdmMaster(@Valid @RequestBody MdmMasterRequest mdmdMasterRequest) {
		LOG.info("Request recieved to delete MDM Master...");
		CommonResponse response = new CommonResponse();
		try {

			response = mdmMasterService.deleteMdmMaster(mdmdMasterRequest);

			LOG.info("Request success to delete MDM Master...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.info("Issue in delete MDM Master due to: " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMdmMaster(@Valid @RequestBody MdmMasterRequest mdmdMasterRequest) {
		LOG.info("Request recieved to get MDM Master...");
		CommonResponse response = new CommonResponse();
		try {

			response = mdmMasterService.getMdmMaster(mdmdMasterRequest);

			LOG.info("Request success to get MDM Master...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.info("Issue in delete MDM get due to: " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get-list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMdmMasterList(@RequestBody MdmMasterRequest mdmdMasterRequest) {
		LOG.info("Request recieved to get list MDM Master...");
		CommonResponse response = new CommonResponse();
		try {

			response = mdmMasterService.getMdmMasterList(mdmdMasterRequest);

			LOG.info("Request success to get MDM Master List...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.info("Issue in get MDM master List due to: " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get-log",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
	public ResponseEntity<Object> getMdmMasterLog(@Valid @RequestBody MdmMasterRequest MdmMasterRequest){
		LOG.info("Request recieved to get MDM Master Log...");
		CommonResponse response=new CommonResponse();
		try {
			
			response = mdmMasterService.getMdmMasterLog(MdmMasterRequest);
			LOG.info("Request success to get MDM Master Log...");
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		}
		catch(Exception e) {
			LOG.info("Issue in get MDM master log due to: " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			
		}
	}
	
	@RequestMapping(value = "/update-mdmId",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
	public ResponseEntity<Object> updateMDMIdHierarchyWise(@Valid @RequestBody MdmMasterRequest MdmMasterRequest){
		LOG.info("Request recieved to update mdmId hierarchy wise...");
		CommonResponse response=new CommonResponse();
		try {
			
			response = mdmMasterService.updateMDMIdHierarchyWise(MdmMasterRequest);
			LOG.info("Request success to update mdmId hierarchy wise...");
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		}
		catch(Exception e) {
			LOG.info("Issue in update MDM master id due to: " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			
		}
	}
}
