package com.global.meter.inventive.controller;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.MeterDBAppStarter;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.FirmwareConfig;
import com.global.meter.business.model.FirmwareData;
import com.global.meter.business.model.FirmwareDeleteLogs;
import com.global.meter.data.repository.FirmwareConfigRepository;
import com.global.meter.data.repository.FirmwareDataRepository;
import com.global.meter.data.repository.FirmwareDeleteLogRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.FirmwareDataResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.FirmwareOperationService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.FirmwareConfigCaster;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.DataCorrectorService;
import com.global.meter.service.DevicesConfigService;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.service.FirmwareDataService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;

@RestController
@RequestMapping(value="/firmware/operations")
public class FirmwareOpeartionsController {

	private static final Logger LOG = LoggerFactory.getLogger(FirmwareOpeartionsController.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DataCorrectorService dataCorrectorService;
	
	@Autowired
	DevicesInfoService devicesInfoService;
	
	@Autowired
	DevicesConfigService devicesConfigService;
	
	@Autowired
	FirmwareDataRepository firmwareDataRepository;
	
	@Autowired
	FirmwareDataService firmwareDataService;

	@Autowired
	FirmwareOperationService firmwareOperationService;
	
	@Autowired
	FirmwareConfigCaster firmwareConfigCaster;
	
	@Autowired
	FirmwareConfigRepository firmwareConfigRepository;
	
	@Autowired
	FirmwareDeleteLogRepository deleteLogRepository;
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> uploadFirmwareFile(@RequestPart("data") String data,
			@RequestPart("firmwareFile") MultipartFile firmwareFile) {
		LOG.info("Request received to upload firmware file in DB : ");

		FirmwareData firmwareData = null;
		ErrorData error = new ErrorData();
		CommonResponse response = new CommonResponse();
		try {
			firmwareData = CommonUtils.getMapper().readValue(data, FirmwareData.class);
			// Check if file is not empty and has XML content type
	        if (firmwareFile.isEmpty()) {
	        	
	        	error.setErrorMessage("File is empty");
				response.setCode(404);
				response.setError(true);
				response.addErrorMessage(error);
				return ResponseEntity.ok().body(response);
	        }
 
	        // Check if file has XML extension or XML MIME type
	        String contentType = firmwareFile.getContentType();
	        if (!"xml".equals(contentType) && !"application/xml".equals(contentType)) {
				
	        	error.setErrorMessage("Invalid file type. Please upload an XML file.");
				response.setCode(404);
				response.setError(true);
				response.addErrorMessage(error);
				return ResponseEntity.ok().body(response);
	        }
 
		} catch (Exception e) {
			LOG.info("Issue in uploading file due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return ResponseEntity.ok().body(response);
		}
		
		if(firmwareData.getSource() == null || firmwareData.getSource().isEmpty() 
				|| firmwareData.getUser_id() == null || firmwareData.getUser_id().isEmpty()) {
			error.setErrorMessage("Missing source or user Id");
			response.setCode(400);
			response.setError(true);
			response.addErrorMessage(error);
			return ResponseEntity.ok().body(response);
		}

		if (firmwareDataRepository.findByOwnerNameAndFileName(firmwareData.getFile_name(), firmwareData.getOwner())
				.isPresent()) {
			response.setError(true);
			response.setMessage("File Already Exist.");
			response.setCode(409);
			return ResponseEntity.ok().body(response);
		}

		try {

			byte[] buffer = firmwareFile.getBytes();

			firmwareData.setFile_data(ByteBuffer.wrap(buffer));
			firmwareData.setFile_upload_datetime(new Date(System.currentTimeMillis()));

			firmwareDataRepository.save(firmwareData);
			response.setCode(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			LOG.info("Issue in uploading file due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> uploadFirmwareDataFile(@Valid @RequestBody FirmwareData firmwareData) {
		LOG.info("Uploading firmware file in DB : ");

		CommonResponse response = new CommonResponse();

		if (StringUtils.isEmpty(firmwareData.getFile_name())) {
			response.setMessage("Invalid Request.");
			response.setCode(400);
			return ResponseEntity.ok().body(response);
    	}
		
		if(firmwareDataRepository.findByOwnerNameAndFileName(firmwareData.getFile_name(), firmwareData.getOwner()).isPresent()) {
			response.setMessage("File Already Exist.");
			response.setCode(409);
			return ResponseEntity.ok().body(response);
		}

		try {

			firmwareData.setFile_upload_datetime(new Date(System.currentTimeMillis()));
			firmwareDataRepository.save(firmwareData);
			response.setCode(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			LOG.info("Issue in uploading file due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/list" , method = RequestMethod.POST, consumes  = {MediaType.APPLICATION_JSON_VALUE},
			produces  = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> uploadFirmwareFile(@Valid @RequestBody MeterDataVisualizationReq req){
		LOG.info("Uploading firmware file in DB : ");
		
	    CommonResponse response = new CommonResponse();
		try {
			
			String table = MeterDBAppStarter.keyspace + "." + Tables.FIRMWARE_DATA;
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select file_name as fileName, owner, version, source, user_id as userId, updated_by as updatedBy,"
					+ " file_upload_datetime, image_identifier as imageIdentifier, manufacturer, status, device_type as deviceType from ")
			.append(table).append(" where").append(" owner = '")
			.append(req.getOwner()).append("'");
			if(req.getManufacturer() != null && !req.getManufacturer().isEmpty()) {
			
				queryBuilder.append(" and manufacturer = '").append(req.getManufacturer()).append("'");
			}
			if(req.getDevType() != null && !req.getDevType().isEmpty()) {
				queryBuilder.append(" and device_type = '").append(req.getDevType()).append("'");
			}
	
			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper()
					.writeValueAsString(prestoJdbcTemplate.queryForList(query));
			List<FirmwareData> firmwareList = new ArrayList<FirmwareData>();
			firmwareList = CommonUtils.getMapper().readValue(outputList,
					new TypeReference<List<FirmwareData>>() {
					});
			List<FirmwareDataResponse> ispResponseList = new ArrayList<>();
			firmwareConfigCaster.prepareFirmwareData(firmwareList, ispResponseList, req);
			response.setData(ispResponseList);
			
			response.setCode(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			LOG.info("Issue in uploading firmware file due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/list" , method = RequestMethod.PUT, consumes  = {MediaType.APPLICATION_JSON_VALUE},
			produces  = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> updateFirmwareFile(@Valid @RequestBody List<FirmwareData> updateFirmwareParamsList){
		LOG.info("Uploading firmware file in DB : ");

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		List<FirmwareData> updatedFirmwareFiles = new ArrayList<FirmwareData>();
		try {

			for (FirmwareData updateFirmwareParams : updateFirmwareParamsList) {
				
				if(updateFirmwareParams.getUpdated_by() == null || updateFirmwareParams.getUpdated_by().isEmpty()) {
					error.setErrorMessage("Missing Updated By");
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return ResponseEntity.ok().body(response);
				}

				FirmwareData firmwareFile = firmwareDataRepository.findByOwnerNameAndFileName(
						updateFirmwareParams.getFile_name(), updateFirmwareParams.getOwner()).get();

				if (firmwareFile == null) {
					response.setMessage("File Not Exist.");
					response.setCode(404);

					return ResponseEntity.ok().body(response);
				}

				firmwareFile.setImage_identifier(
						updateFirmwareParams.getImage_identifier() != null ? updateFirmwareParams.getImage_identifier()
								: firmwareFile.getImage_identifier());

				firmwareFile.setStatus(updateFirmwareParams.getStatus() != null ? updateFirmwareParams.getStatus()
						: firmwareFile.getStatus());

				firmwareFile.setManufacturer(
						updateFirmwareParams.getManufacturer() != null ? updateFirmwareParams.getManufacturer()
								: firmwareFile.getManufacturer());

				firmwareFile.setVersion(updateFirmwareParams.getVersion() != null ? updateFirmwareParams.getVersion()
						: firmwareFile.getVersion());
				
				firmwareFile.setFile_name(updateFirmwareParams.getFile_name() != null ? updateFirmwareParams.getFile_name() : 
					firmwareFile.getFile_name());
				
				firmwareFile.setUpdated_by(updateFirmwareParams.getUpdated_by());
				firmwareFile.setModified(new Date(System.currentTimeMillis()));
				firmwareFile.setDevice_type(updateFirmwareParams.getDevice_type());

				updatedFirmwareFiles.add(firmwareFile);
			}

			if (updatedFirmwareFiles.size() > 0) {
				firmwareDataRepository.saveAll(updatedFirmwareFiles);
			}

			response.setCode(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			LOG.info("Issue in updating file due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/list", method = RequestMethod.DELETE, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> deleteFirmwareFile(@Valid @RequestBody List<MeterDataVisualizationReq> deleteReq) {
		LOG.info("Uploading firmware file in DB : ");

		CommonResponse response = new CommonResponse();
		List<FirmwareData> filesToDelete = new ArrayList<FirmwareData>();
		List<FirmwareDeleteLogs> deletedFiles = new ArrayList<FirmwareDeleteLogs>();
		
		String table = MeterDBAppStarter.keyspace + "." + Tables.FIRMWARE_CONFIG;
		try {

			for (MeterDataVisualizationReq req : deleteReq) {

				Optional<FirmwareData> firmwareFiles = firmwareDataRepository.findByOwnerNameAndFileName(
						req.getFileName(), req.getOwner());

				if (!firmwareFiles.isPresent()) {
					response.setMessage("File Not Exist.");
					response.setCode(404);

				return ResponseEntity.ok().body(response);
				}
				FirmwareData firmwareFile = firmwareFiles.get();
				
				String query="select device_serial_number from "+table+" where command_val = '"+req.getFileName()+"' and status in ('ADDED','IN_PROGRESS')";
				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
				List<FirmwareConfig> deviceCommandData = null;
				deviceCommandData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<FirmwareConfig>>() {});
				
				if (deviceCommandData != null && deviceCommandData.size()>0) {
					response.setMessage("This File is inprogress to update firmware");
					response.setCode(404);
					
					return ResponseEntity.ok().body(response);
				}
				FirmwareDeleteLogs deleteLogs = CommonUtils.getMapper().readValue(
				CommonUtils.getMapper().writeValueAsString(firmwareFile), FirmwareDeleteLogs.class);
				deleteLogs.setDeleted_by(req.getUserId());
				deleteLogs.setDeleted_on(new Date(System.currentTimeMillis()));
				deleteLogs.setRemarks(req.getRemarks());
				deletedFiles.add(deleteLogs);
				filesToDelete.add(firmwareFile);
			}
			if(filesToDelete.size() > 0) {
				firmwareDataRepository.deleteAll(filesToDelete);
				deleteLogRepository.saveAll(deletedFiles);
			}		
			response.setCode(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			LOG.info("Issue in deleting file due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/configs/add" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addConfigs(@Valid @RequestBody DeviceCommand req){
		
		CommonResponse response = new CommonResponse();
		List<DevicesInfo> devicesList = null;
		String levelName = ExternalConstants.getUILevelValue(req.getHier().getName());
		ErrorData error = new ErrorData();
		LOG.info("Firmware configuration received to add devices configs for : "+ levelName);
		try {
			if(req.getConfigVals() == null || req.getConfigVals().size() == 0){
				response.setMessage("NOT FOUND");
				response.setCode(404);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			if(req.getSource() == null || req.getSource().isEmpty() ||
					req.getUserId()== null || req.getUserId().isEmpty()) {
				error.setErrorMessage("Missing source or user Id");
				response.setCode(400);
				response.setError(true);
				response.addErrorMessage(error);
				return ResponseEntity.ok().body(response);
			}
			
			devicesList = devicesInfoService.getDevicesList(levelName,
					req.getHier().getValue());
			
			LOG.info("Devices list retrieved based on :"+ levelName);
			if(devicesList != null && devicesList.size() > 0){
				if("7".equalsIgnoreCase(req.getHier().getName()) 
						&&(Constants.Status.INACTIVE.equalsIgnoreCase(devicesList.get(0).getCommissioning_status()) 
								|| Constants.Status.DOWN.equalsIgnoreCase(devicesList.get(0).getCommissioning_status())
								|| Constants.Status.FAULTY.equalsIgnoreCase(devicesList.get(0).getCommissioning_status()))) {
					error.setErrorMessage("Device Status is : "+devicesList.get(0).getCommissioning_status());
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				devicesConfigService.addFirmwareConfigs(response, devicesList, req);
				LOG.info("Firmware Configurations added for :"+ levelName);
			}else {
				response.setError(true);
				response.setMessage("Device Not Found");
				response.setCode(404);
				return ResponseEntity.ok().body(response);
			}
		} catch (Exception e) {
			LOG.error("Issue in adding configs due to :" + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/configs/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getfirmwareConfig(
			@Valid @RequestBody MeterDataVisualizationReq firmwareConfigRequest) {
		LOG.info("Request recieved to get firmware config informations for: " + firmwareConfigRequest);
		CommonResponse response = new CommonResponse();
		try {

			response = firmwareOperationService.getFirwareConfig(firmwareConfigRequest);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.info("Issue in getting config informations due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/configs/getlist", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getfirmwareConfigList() {
		LOG.info("Request recieved to get firmware config list");
		CommonResponse response = new CommonResponse();
		try {

			response = firmwareOperationService.getFirwareConfigList();

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			LOG.info("Issue in getting firmware config list due to: "+e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 
	 * @param deviceCommand
	 * @return
	 */
	@RequestMapping(value = "/upgrade", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> writeData(@Valid @RequestBody DeviceCommand deviceCommand) {
		return firmwareDataService.upgradeFirmware(deviceCommand);
	}

	@RequestMapping(value = "/deleted-logs/get", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> firmwareDeletedLogs(
			@Valid @RequestBody MeterDataVisualizationReq firmwareConfigRequest) {
		LOG.info("Request recieved to get firmware deleted Log...");
		CommonResponse response = new CommonResponse();
		try {
			response = firmwareOperationService.firmwareDeletedLogs(firmwareConfigRequest);
			LOG.info("Request success to get firmware deleted Log for: " + firmwareConfigRequest);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response = ExceptionHandlerConfig.setErrorData(e);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}