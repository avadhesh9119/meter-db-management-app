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

import com.global.meter.data.repository.UserMstRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.UsersMasterDataRequest;
import com.global.meter.inventive.service.UsersMasterService;

@RestController
@RequestMapping(value = "/hes/user/mst")
public class UsersMasterController {

	private static final Logger LOG = LoggerFactory.getLogger(UsersMasterController.class);

	@Autowired
	UsersMasterService usersService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	UserMstRepository userRepo;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addUsers(@Valid @RequestBody List<UsersMasterDataRequest> usersDataRequest) {
		LOG.info("Request recieved to add users informations");
		CommonResponse response = new CommonResponse();
		try {
			response = usersService.addUser(usersDataRequest);
			LOG.info("Request success to add users informations");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in adding user Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateUsers(@Valid @RequestBody List<UsersMasterDataRequest> usersDataRequest) {
		LOG.info("Request recieved to update users informations...");
		CommonResponse response = new CommonResponse();
		try {

			response = usersService.updateUser(usersDataRequest);
			LOG.info("Request success to update users informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating users Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getUsers(@Valid @RequestBody UsersMasterDataRequest usersDataRequest) {
		LOG.info("Request recieved to get users informations..." + usersDataRequest.getAutoId());
		CommonResponse response = new CommonResponse();
		try {

			response = usersService.getUser(usersDataRequest);
			LOG.info("Request success to get users informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting users Info due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteUsers(@Valid @RequestBody UsersMasterDataRequest usersDataRequest) {
		LOG.info("Request recieved to delete user..." + usersDataRequest.getAutoId());
		CommonResponse response = new CommonResponse();
		try {

			response = usersService.deleteUser(usersDataRequest);
			
			LOG.info("Request success to delete user informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in deleting user Info due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get-list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getUserList() {
		LOG.info("Request recieved to get user list...");
		CommonResponse response = new CommonResponse();
		try {

			response = usersService.getUserList();
			
			LOG.info("Request success to get list of user informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of user Info due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/get-id-list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getUserIdList() {
		LOG.info("Request recieved to get user Id list...");
		CommonResponse response = new CommonResponse();
		try {

			response = usersService.getUserIdList();
			
			LOG.info("Request success to get list of user id informations...");

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting list of user id  due to:" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}
