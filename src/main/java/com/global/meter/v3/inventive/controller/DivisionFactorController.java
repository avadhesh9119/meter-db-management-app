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
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.business.model.DivisionFactorFileLog;
import com.global.meter.v3.inventive.repository.DivisionFactorFileLogsRepository;
import com.global.meter.v3.inventive.serviceImpl.DivFactorFileLogsService;

@RestController
@RequestMapping(value = "/hes/div-factor/property/")
public class DivisionFactorController {

	private static final Logger LOG = LoggerFactory.getLogger(DivisionFactorController.class);

	@Autowired
	MetersCommandsConfiguration metersCommandsConfiguration;

	@Autowired
	DivisionFactorFileLogsRepository divisionFactorFileLogsRepository;

	@Autowired
	DivFactorFileLogsService divFactorFileLogsService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addDivFactorProperty(@RequestBody List<PropertyRequest> reqList) {
		LOG.info("Request recieved to add Division Factor Property");
		CommonResponse response = new CommonResponse();
		List<DivisionFactorFileLog> logList = new ArrayList<DivisionFactorFileLog>();
		try {

				try {
					for(PropertyRequest req:reqList) {
					// Open the file
					FileInputStream fstream = new FileInputStream(metersCommandsConfiguration.getDivFactorList());
					// Get the object of DataInputStream
					DataInputStream in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					String strLine;
					// Read File Line By Line
					StringBuilder buffer = new StringBuilder();
					String newManufacturar = req.getName().trim().split("_")[0];
					String preStr = "";
					Boolean isNewManufacturar = false;
					while ((strLine = br.readLine()) != null) {
						
						String curStr = strLine.split("_")[0];
						//added existing manufacturer
						if (!isNewManufacturar && req.getName().equalsIgnoreCase(strLine.split("=")[0].trim())) {
							buffer.append(req.getName().trim()+" = "+req.getValue());
							isNewManufacturar = true;
							req.setOldDivisionFactorValue(!strLine.isEmpty() ? strLine.split("=")[1].trim() : "-");
						}
						else if (!isNewManufacturar && !newManufacturar.equals(curStr) && newManufacturar.equals(preStr)) {
                           buffer.append(req.getName().trim()+" = "+req.getValue().trim());
                           buffer.append('\n');
                           isNewManufacturar = true;
                           req.setOldDivisionFactorValue(!strLine.isEmpty() ? strLine.split("=")[1].trim() : "-");
						}
						else {
							buffer.append(strLine);
						}
						preStr = strLine.split("_")[0];
							buffer.append('\n');
					}
					//added new manufacturer
					if(!isNewManufacturar) {
						if(newManufacturar.equals(preStr)) {
							buffer.append(req.getName().trim()+" = "+req.getValue().trim());
						}
						else {
							buffer.append('\n');
						    buffer.append(req.getName().trim()+" = "+req.getValue().trim());
						}
					}
					// Close the input stream
					in.close();

					String newDivFactorProperty = buffer.substring(0, buffer.length());

					DivisionFactorFileLog log = new DivisionFactorFileLog();
					log.setTracking_id(String.valueOf(System.nanoTime()));
					log.setMdas_datetime(new Date(System.currentTimeMillis()));
					log.setSource(req.getSource());
					log.setCreated_by(req.getCreatedBy());
					log.setCreated_on(new Date(System.currentTimeMillis()));
					log.setUser_comment(req.getUserComment());
					log.setDiv_factor_type(Constants.DivFactorType.PULL);
					log.setUpdated_by(req.getCreatedBy());
					log.setUpdated_on(new Date(System.currentTimeMillis()));
					log.setNew_division_factor_value(req.getValue().trim() != null ? req.getValue().trim() : "-");
					log.setOld_division_factor_value(req.getOldDivisionFactorValue() != null ? req.getOldDivisionFactorValue() : "-");
					
					logList.add(log);
					

					File file = new File(metersCommandsConfiguration.getDivFactorList());
					FileWriter fr = null;
					try {
						if (!file.createNewFile()) {
							fr = new FileWriter(file);
						}
						if(fr != null)
						fr.write(newDivFactorProperty);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						// close resources
							if(fr != null) {
							fr.close();
							}
							in.close();
							br.close();
					}
				}
				} catch (Exception e) {// Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
				divisionFactorFileLogsRepository.saveAll(logList);
				LOG.info("Request success to add Division Factor Property...");
				response.setMessage(ExternalConstants.Message.ADDED);

				return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			LOG.error("Issue in add division factor property due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getAppProperty(@RequestBody PropertyRequest req) {
		LOG.info("Request recieved to get division factor Property...");
		CommonResponse response = new CommonResponse();
		try {

			try {

				List<AppPropertyData> divFactorList = new ArrayList<>();

				// Open the file that is the first
				// command line parameter
				FileInputStream fstream = new FileInputStream(metersCommandsConfiguration.getDivFactorList());
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				String name = "";
				String value = "";
				String devType = "";
				if(req.getDevType() != null) {
				if(req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE_DEV)) {
					devType = "_1ph_";
				}
				else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE_DEV)) {
					devType = "_3ph_";
				}
				else if(req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
					devType = "_ct_";
				}
				}
				// Read File Line By Line
				while ((strLine = br.readLine()) != null) {

					AppPropertyData propertyData = new AppPropertyData();
                  
                    if(strLine != null && !strLine.isEmpty() && strLine.contains("=")) {
                    	  name = strLine.split("=")[0].trim();
                          value = strLine.split("=")[1].trim();
                          if((req.getManufacturer() != null && !req.getManufacturer().isEmpty()) || (req.getDevType() != null && !req.getDevType().isEmpty()) || (req.getProfileType() != null && !req.getProfileType().isEmpty())){
                    	if(req.getManufacturer() != null && !req.getManufacturer().isEmpty() && req.getDevType() != null 
                    			&& !req.getDevType().isEmpty() && req.getProfileType() != null && !req.getProfileType().isEmpty() && name.contains(req.getManufacturer()+"_") && name.contains(devType) && name.contains(req.getProfileType())) {
                    		
                    		propertyData.setName(name);
							propertyData.setValue(value);
                    	}
                    	else if(req.getManufacturer() != null && !req.getManufacturer().isEmpty() && req.getDevType() != null 
                    			&& !req.getDevType().isEmpty() && (req.getProfileType() == null || req.getProfileType().isEmpty()) && name.contains(req.getManufacturer()+"_") && name.contains(devType)) {
                    		
                    		propertyData.setName(name);
                    		propertyData.setValue(value);
                    	}
                    	else if(req.getDevType() != null && !req.getDevType().isEmpty() && req.getProfileType() != null 
                    			&& !req.getProfileType().isEmpty() && (req.getManufacturer() == null || req.getManufacturer().isEmpty()) && name.contains(req.getProfileType()) && name.contains(devType)) {
                    		
                    		propertyData.setName(name);
                    		propertyData.setValue(value);
                    	}
                    	else if(req.getManufacturer() != null && !req.getManufacturer().isEmpty() && req.getProfileType() != null 
                    			&& !req.getProfileType().isEmpty() && (req.getDevType() == null || req.getDevType().isEmpty()) && name.contains(req.getManufacturer()+"_") && name.contains(req.getProfileType())) {
                    		
                    		propertyData.setName(name);
                    		propertyData.setValue(value);
                    	}
                    	else if(req.getManufacturer() != null && !req.getManufacturer().isEmpty()  
                    			&& (req.getDevType() == null || req.getDevType().isEmpty())  && (req.getProfileType() == null || req.getProfileType().isEmpty()) && name.contains(req.getManufacturer()+"_")) {
                    		
                    		propertyData.setName(name);
                    		propertyData.setValue(value);
                    	}
                    	else if(req.getDevType() != null && !req.getDevType().isEmpty()  
                    			&& (req.getManufacturer() == null || req.getManufacturer().isEmpty())  && (req.getProfileType() == null || req.getProfileType().isEmpty()) && name.contains(devType)) {
                    		
                    		propertyData.setName(name);
                    		propertyData.setValue(value);
                    	}
                    	else if(req.getProfileType() != null && !req.getProfileType().isEmpty()  
                    			&& (req.getDevType() == null || req.getDevType().isEmpty())  && (req.getManufacturer() == null || req.getManufacturer().isEmpty()) && name.contains(req.getProfileType())) {
                    		
                    		propertyData.setName(name);
                    		propertyData.setValue(value);
                    	}
                          }
                    	else {
                    		propertyData.setName(name);
							propertyData.setValue(value);
						}
							if(propertyData.getName() != null && propertyData.getValue() != null) {
						divFactorList.add(propertyData);
							}
                          }
					}
				// Close the input stream
				in.close();

				LOG.info("Request success to get division factor property...");

				response.setData(divFactorList);
				response.setCode(200);

			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}

			// MeterDBAppStarter.restart();
			return new ResponseEntity<>(response, HttpStatus.OK);

		}  catch (Exception e) {
			LOG.error("Issue in getting division factor property due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/get-logs", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getDivisionPropertiesFileLogs(@Valid @RequestBody PropertyRequest req) {
		LOG.info("Request recieved to get Division Factor File logs...");
		CommonResponse response = new CommonResponse();
		try {

			response = divFactorFileLogsService.getDivisionFactorFileLogs(req);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error("Issue in getting Division Factor file logs due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}
