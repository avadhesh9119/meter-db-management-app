package com.global.meter.v3.inventive.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
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
import com.global.meter.inventive.models.AppPropertyData;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.PropertyRequest;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.utils.PropertyUtils;
import com.global.meter.v3.common.enums.AppPropertyDataSet;
import com.global.meter.v3.inventive.business.model.AppPropertiesFileLog;
import com.global.meter.v3.inventive.repository.AppPropertyFileLogsRepository;
import com.global.meter.v3.inventive.serviceImpl.AppPropertiesFileLogsService;
import com.global.meter.v3.utils.AppPropertyConstants;

@RestController
@RequestMapping(value = "/hes/app/property/")
public class ApplicationPropertiesController {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationPropertiesController.class);

	@Autowired
	MetersCommandsConfiguration metersCommandsConfiguration;

	@Autowired
	AppPropertiesFileLogsService appPropertiesFileLogsService;

	@Autowired
	AppPropertyFileLogsRepository appPropertyFileLogsRepository;

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateAppProperty(@RequestBody PropertyRequest req) {
		LOG.info("Request recieved to Update Property...");
		CommonResponse response = new CommonResponse();
		String isPropertyAvailableString = "";
		try {

			// Converting Time into Cron Conversion
			if (!AppPropertyDataSet.contains(req.getName())) {
				response.setMessage("Invalid Property Entered : " + req.getName());
				response.setCode(404);
				response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {

				String schedulerKey = "";

				schedulerKey = AppPropertyDataSet.getPropertyKeyByUiName(req.getName());

				try {
					// Open the file that is the first
					// command line parameter
					FileInputStream fstream = new FileInputStream(metersCommandsConfiguration.getApplicationProperty());
					// Get the object of DataInputStream
					DataInputStream in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					String strLine;
					// Read File Line By Line
					StringBuilder buffer = new StringBuilder();
					StringBuilder oldBuffer = new StringBuilder();
					while ((strLine = br.readLine()) != null) {

						String newString = "";
						String updated = "";

						// Replace scheduler time with new cron time
						newString = PropertyUtils.replaceString(strLine, schedulerKey, req.getNewPropertyValue());
						// Set Property Updated
						updated = PropertyUtils.replaceString(strLine,
								AppPropertyConstants.AppPropertyName.PROPERTY_UPDATE_KEY,
								AppPropertyConstants.AppPropertyName.ENABLE);

						if (newString != "") {
							buffer.append(newString);
							isPropertyAvailableString = "Y";
						} else if (updated != "") {
							buffer.append(updated);
						} else {
							buffer.append(strLine);
						}
						oldBuffer.append(strLine);

						if (strLine != "") {
							buffer.append('\n');
							oldBuffer.append('\n');
						}

						// Print the content on the console
						System.out.println(strLine);

					}
					// Close the input stream
					in.close();

					String query = buffer.substring(0, buffer.length());

					AppPropertiesFileLog log = new AppPropertiesFileLog();
					StringBuilder descBuffer = new StringBuilder();
					descBuffer.append(req.toString());
					log.setDescription(manageDesc(req));
					log.setTracking_id(String.valueOf(System.nanoTime()));
					log.setMdas_datetime(new Date(System.currentTimeMillis()));
					log.setNew_property_value(req.getNewPropertyValue());
					log.setOld_property_value(req.getOldPropertyValue());
					log.setSource(req.getSource());
					log.setUpdated_by(req.getUpdatedBy());
					log.setUpdated_on(new Date(System.currentTimeMillis()));
					log.setUser_comment(req.getUserComment());

					appPropertyFileLogsRepository.save(log);

					File file = new File(metersCommandsConfiguration.getApplicationProperty());
					FileWriter fr = null;
					try {
						if (!file.createNewFile()) {
							fr = new FileWriter(file);
						}
						fr.write(query);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						// close resources
						try {
							fr.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				} catch (Exception e) {// Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}

				LOG.info("Request success to update Application Properties informations...");
				if (isPropertyAvailableString != "") {
					response.setMessage(req.getName() + " Succesfully Updated.");
				} else {
					response.setMessage(req.getName() + " is not available in property file.");
					response.setCode(404);
				}

				// MeterDBAppStarter.restart();
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			LOG.error("Issue in updating Application Property due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getAppProperty() {
		LOG.info("Request recieved to update Application Property...");
		CommonResponse response = new CommonResponse();
		try {

			try {

				List<AppPropertyData> schedulerList = new ArrayList<>();

				// Open the file that is the first
				// command line parameter
				FileInputStream fstream = new FileInputStream(metersCommandsConfiguration.getApplicationProperty());
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				// Read File Line By Line
				while ((strLine = br.readLine()) != null) {

					String schedulerKey = "";
					String schedulerValue = "";
					String schedulerUiName = "";

					AppPropertyData propertyData = new AppPropertyData();

					schedulerKey = PropertyUtils.getKeyFromProperty(strLine);
					schedulerUiName = AppPropertyConstants.getUiNameValue(schedulerKey.trim());

					if (AppPropertyDataSet.contains(schedulerUiName)) {
						// Replace scheduler time with new cron time

						schedulerValue = PropertyUtils.getValuesFromProperty(strLine);

						if (schedulerValue != "") {
							propertyData.setValue(schedulerValue);
						}

						if (schedulerUiName != "") {
							propertyData.setName(schedulerUiName);
						}

						schedulerList.add(propertyData);

					}

				}
				// Close the input stream
				in.close();

				LOG.info("Request success to update Application Properties Informations...");

				response.setData(schedulerList);
				response.setCode(200);

			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}

			// MeterDBAppStarter.restart();
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating Application Property due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get-logs", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getAppPropertiesFileLogs(@Valid @RequestBody PropertyRequest req) {
		LOG.info("Request recieved to get Properties File logs...");
		CommonResponse response = new CommonResponse();
		try {
			response = appPropertiesFileLogsService.getAppPropertiesFileLogs(req);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Issue in getting Properties file logs due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	private String manageDesc(PropertyRequest req) {

		StringBuilder builder = new StringBuilder();
		if (req.getDescription() != null && !req.getDescription().isEmpty()) {
			builder.append("User Desc: ").append(req.getDescription()).append(", ");
		}
		builder.append("Property : ").append(req.getName()).append(", ").append("OldPropertyValue : ")
				.append(req.getOldPropertyValue()).append(", ").append("NewPropertyValue: ")
				.append(req.getNewPropertyValue()).append(", ");
		return builder.substring(0, builder.length());
	}

}
