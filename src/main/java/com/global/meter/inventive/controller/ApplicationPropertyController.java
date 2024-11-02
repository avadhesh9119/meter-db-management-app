package com.global.meter.inventive.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.global.meter.MeterDBAppStarter;
import com.global.meter.inventive.enums.PropertyDataSet;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.AppPropertyData;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.PropertyRequest;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.utils.PropertyConstants;
import com.global.meter.utils.PropertyUtils;

@RestController
@RequestMapping(value = "/hes/app/property/")
public class ApplicationPropertyController {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationPropertyController.class);

	@Autowired
	MetersCommandsConfiguration metersCommandsConfiguration;

	@RequestMapping(value = "/retry/update", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateAppProperty(@RequestBody PropertyRequest req) {
		LOG.info("Request recieved to update Scheduler Property...");
		CommonResponse response = new CommonResponse();
		String isPropertyAvailableString = "";
		try {

			// Converting Time into Cron Conversion
			if(!PropertyDataSet.contains(req.getName())) {
				response.setMessage("Invalid Schedule Entered : "+req.getName());
				response.setCode(404);
				response.setError(true);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			else {
				
				
				
				String schedulerKey = "";
				
					schedulerKey = PropertyDataSet.getPropertyKeyByUiName(req.getName());	
				
				
				
				

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
					while ((strLine = br.readLine()) != null) {

						String newString = "";
						String updated = "";
						

						// Replace scheduler time with new cron time 
						newString = PropertyUtils.replaceString(strLine, schedulerKey, req.getValue());
						// Set Property Updated 
						updated = PropertyUtils.replaceString(strLine,
								PropertyConstants.PropertyName.PROPERTY_UPDATE_KEY,
								PropertyConstants.PropertyName.ENABLE);

						if (newString != "") {
							buffer.append(newString);
							isPropertyAvailableString = "Y";
						}
						else if(updated != "") {
							buffer.append(updated);
						}
						else {
							buffer.append(strLine);
						}
						if (strLine != "") {
							buffer.append('\n');
						}

						// Print the content on the console
						System.out.println(strLine);

					}
					// Close the input stream
					in.close();

					String query = buffer.substring(0, buffer.length());

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

				LOG.info("Request success to update MetersCommandsConfiguration informations...");
				if(isPropertyAvailableString != "") {
					response.setMessage(req.getName() + " Succesfully Updated.");
				}
				else {
					response.setMessage(req.getName() + " is not available in property file.");
					response.setCode(404);
				}
				
				// MeterDBAppStarter.restart();
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			LOG.error("Issue in updating schedular property due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	
	@RequestMapping(value = "/retry/get", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getAppProperty() {
		LOG.info("Request recieved to update Scheduler Property...");
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
					schedulerUiName = PropertyConstants.getUiNameValue(schedulerKey.trim());

					if (PropertyDataSet.contains(schedulerUiName)) {
						// Replace scheduler time with new cron time

						schedulerValue = PropertyUtils.getValueFromProperty(strLine);

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

				LOG.info("Request success to update MetersCommandsConfiguration informations...");

				response.setData(schedulerList);
				response.setCode(200);

			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}

			// MeterDBAppStarter.restart();
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in updating schedular property due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	public void disableUpdateAppProperty() {
		LOG.info("Request recieved to disable Scheduler Property...");
		try {
			String isPropertyUpdated = "";
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
				while ((strLine = br.readLine()) != null) {

					String isPropertyUpdate = "";

					if (strLine.contains(PropertyConstants.PropertyName.PROPERTY_UPDATE_KEY)) {
						isPropertyUpdate = PropertyUtils.replaceString(strLine,
								PropertyConstants.PropertyName.PROPERTY_UPDATE_KEY, PropertyConstants.PropertyName.DISABLE);
						isPropertyUpdated = PropertyUtils.getValueFromProperty(strLine,PropertyConstants.PropertyName.PROPERTY_UPDATE_KEY);
					}

					if (isPropertyUpdate != "") {
						buffer.append(isPropertyUpdate);
					}
					else {
						buffer.append(strLine);
					}
					if (strLine != "") {
						buffer.append('\n');
					}

					// Print the content on the console
					System.out.println(strLine);

				}
				// Close the input stream
				in.close();
				if(isPropertyUpdated.trim() != "" && isPropertyUpdated.trim().equals(PropertyConstants.PropertyName.ENABLE)) {
				String query = buffer.substring(0, buffer.length());

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
						
						MeterDBAppStarter.restart();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				}

			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}

			LOG.info("Request success to disable scheduler property...");

		} catch (Exception e) {
			LOG.error("Issue in disable scheduler property due to :" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/restart", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> restartAppProperty() {
        LOG.info("Request recieved to get Scheduler Property.");
        CommonResponse response = new CommonResponse();
        try {
            // If force restart getting true. Scheduler direct restart without check update and change flag.
            disableUpdateAppProperty();
            response.setCode(200);
            response.setMessage("Scheduler Restart Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (Exception e) {
            LOG.error("Issue in get schedular property due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
	
}
