package com.global.meter.inventive.controller;

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

import com.global.meter.data.repository.UserHierMappingRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.UserHierarchyMappingReq;
import com.global.meter.inventive.models.UsersHierMappingDataRequest;
import com.global.meter.inventive.service.UsersHierMappingService;

@RestController
@RequestMapping(value = "/hes/user/hier/mapping")
public class UsersHierarchyMappingController {

	private static final Logger LOG = LoggerFactory.getLogger(UsersHierarchyMappingController.class);

	@Autowired
	UsersHierMappingService usersHierMappingService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	UserHierMappingRepository userRepo;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addUsersHierMapping(@Valid @RequestBody UserHierarchyMappingReq usersHierMappingDataRequests) {
		LOG.info("Request recieved to add hier mapping users informations");
		CommonResponse response = new CommonResponse();
		try {
			response = usersHierMappingService.addUserHierMapping(usersHierMappingDataRequests);
			LOG.info("Request success to add users hier mapping informations");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding user hier mapping Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateUsersHierMapping(@Valid @RequestBody UserHierarchyMappingReq usersHierMappingDataRequest) {
		LOG.info("Request recieved to update users hier mapping informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = usersHierMappingService.updateUserHierMapping(usersHierMappingDataRequest);
			LOG.info("Request success to update users hier mapping informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating users hier mapping Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getUsersHierMapping(@Valid @RequestBody UsersHierMappingDataRequest usersHierMappingDataRequest) {
		LOG.info("Request recieved to get users hier mapping informations..." + usersHierMappingDataRequest.getMappingId());
		CommonResponse response = new CommonResponse();
		try {

			response = usersHierMappingService.getUserHierMapping(usersHierMappingDataRequest);
			LOG.info("Request success to get users hier mapping informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting users hier mapping Info due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteUsersHierMapping(@Valid @RequestBody UsersHierMappingDataRequest usersHierMappingDataRequest) {
		LOG.info("Request recieved to delete user hier mapping informations..." + usersHierMappingDataRequest.getMappingId());
		CommonResponse response = new CommonResponse();
		try {

			response = usersHierMappingService.deleteUserHierMapping(usersHierMappingDataRequest);
			
			LOG.info("Request success to delete user hier mapping informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in deleting user  hier mapping Info due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get-list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getUserHierMappingList() {
		LOG.info("Request recieved to get user hier mapping list...");
		CommonResponse response = new CommonResponse();
		try {

			response = usersHierMappingService.getUserHierMappingList();
			
			LOG.info("Request success to get list of user hier mapping informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of user hier mapping Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get-id-list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getUserHierMappingIdList() {
		LOG.info("Request recieved to get user hier mapping Id list...");
		CommonResponse response = new CommonResponse();
		try {

			response = usersHierMappingService.getUserHierMappingIdList();
			
			LOG.info("Request success to get user hier mapping id list informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting user hier id list  due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}
