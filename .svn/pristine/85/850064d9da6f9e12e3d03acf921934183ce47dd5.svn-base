package com.global.meter.controller;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.FirmwareData;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.data.repository.FirmwareDataRepository;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.DataCorrectorService;
import com.global.meter.service.DevicesConfigService;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.service.FirmwareDataService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;

@RestController
@RequestMapping(value="/firmware")
public class FirmwareDataController {

	private static final Logger LOG = LoggerFactory.getLogger(FirmwareDataController.class);
	
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
	
	@RequestMapping(value = "/uploadFile" , method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> uploadFirmwareFile(@RequestPart("data") String data,
			@RequestPart("firmwareFile") MultipartFile firmwareFile){
		LOG.info("Uploading firmware file in DB : ");
		
		FirmwareData firmwareData = null;
	    MeterResponse response = new MeterResponse();
		try {
			firmwareData = CommonUtils.getMapper().readValue(data, FirmwareData.class);
		}  catch (Exception e) {
			response.setMessage("Invalid Request.");
			response.setStatus(400);
			return ResponseEntity.ok().body(response);
		}
		
		if(firmwareDataRepository.findByOwnerNameAndFileName(firmwareData.getFile_name(), firmwareData.getOwner()).isPresent()) {
			response.setMessage("File Already Exist.");
			response.setStatus(409);
			return ResponseEntity.ok().body(response);
		}
		
		try {
			
			byte[] buffer = firmwareFile.getBytes();
            
			firmwareData.setFile_data(ByteBuffer.wrap(buffer));
			firmwareData.setFile_upload_datetime(new Date(System.currentTimeMillis()));
			
			firmwareDataRepository.save(firmwareData);
			response.setStatus(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			response.setMessage(Constants.FAILURE);
			response.setStatus(500);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/upload" , method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> uploadFirmwareDataFile(@Valid @RequestBody FirmwareData firmwareData){
		LOG.info("Uploading firmware file in DB : ");
		
	    MeterResponse response = new MeterResponse();
	    
	    if(StringUtils.isEmpty(firmwareData.getFile_name())) {
    		response.setMessage("Invalid Request.");
			response.setStatus(400);
			return ResponseEntity.ok().body(response);
    	}
		
		if(firmwareDataRepository.findByOwnerNameAndFileName(firmwareData.getFile_name(), firmwareData.getOwner()).isPresent()) {
			response.setMessage("File Already Exist.");
			response.setStatus(409);
			return ResponseEntity.ok().body(response);
		}
		
		try {
			
			firmwareData.setFile_upload_datetime(new Date(System.currentTimeMillis()));
			firmwareDataRepository.save(firmwareData);
			response.setStatus(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			response.setMessage(Constants.FAILURE);
			response.setStatus(500);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/list" , method = RequestMethod.POST, consumes  = {MediaType.APPLICATION_JSON_VALUE},
			produces  = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> uploadFirmwareFile(@Valid @RequestBody FirmwareData firmwareData){
		LOG.info("Uploading firmware file in DB : ");
		
	    MeterResponse response = new MeterResponse();
		try {
			
			List<FirmwareData> firmwareList = firmwareDataRepository.findByOwnerName(firmwareData.getOwner());
			
			response.setFirmwareList(firmwareList);
			
			response.setStatus(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(500);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/list" , method = RequestMethod.PUT, consumes  = {MediaType.APPLICATION_JSON_VALUE},
			produces  = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> updateFirmwareFile(@Valid @RequestBody List<FirmwareData> updateFirmwareParamsList){
		LOG.info("Uploading firmware file in DB : ");
		
	    MeterResponse response = new MeterResponse();
	    List<FirmwareData> updatedFirmwareFiles = new ArrayList<FirmwareData>();
		try {
			
			for (FirmwareData updateFirmwareParams : updateFirmwareParamsList) {
				
				FirmwareData firmwareFile = firmwareDataRepository.findByOwnerNameAndFileName(
						updateFirmwareParams.getFile_name(), updateFirmwareParams.getOwner()).get();
				
				if(firmwareFile == null) {
					response.setMessage("File Not Exist.");
					response.setStatus(409);
					
					return ResponseEntity.ok().body(response);
				}
				
				firmwareFile.setImage_identifier(updateFirmwareParams.getImage_identifier() != null ?
						updateFirmwareParams.getImage_identifier() : firmwareFile.getImage_identifier());
				
				firmwareFile.setStatus(updateFirmwareParams.getStatus() != null ?
						updateFirmwareParams.getStatus() : firmwareFile.getStatus());
				
				firmwareFile.setManufacturer(updateFirmwareParams.getManufacturer() != null ?
						updateFirmwareParams.getManufacturer() : firmwareFile.getManufacturer());
				
				firmwareFile.setVersion(updateFirmwareParams.getVersion() != null ? 
						updateFirmwareParams.getVersion() : firmwareFile.getVersion());
				
				updatedFirmwareFiles.add(firmwareFile);
			}
			
			if(updatedFirmwareFiles.size() > 0) {
				firmwareDataRepository.saveAll(updatedFirmwareFiles);
			}
			
			response.setStatus(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(500);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/list" , method = RequestMethod.DELETE, consumes  = {MediaType.APPLICATION_JSON_VALUE},
			produces  = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> deleteFirmwareFile(@Valid @RequestBody List<FirmwareData> updateFirmwareParamsList){
		LOG.info("Uploading firmware file in DB : ");
		
	    MeterResponse response = new MeterResponse();
	    List<FirmwareData> filesToDelete = new ArrayList<FirmwareData>();
		try {
			
			for (FirmwareData updateFirmwareParams : updateFirmwareParamsList) {
				
				FirmwareData firmwareFile = firmwareDataRepository.findByOwnerNameAndFileName(
						updateFirmwareParams.getFile_name(), updateFirmwareParams.getOwner()).get();
				
				if(firmwareFile == null) {
					response.setMessage("File Not Exist.");
					response.setStatus(409);
					
					return ResponseEntity.ok().body(response);
				}
				
				filesToDelete.add(firmwareFile);
			}
			
			if(filesToDelete.size() > 0) {
				firmwareDataRepository.deleteAll(filesToDelete);
			}
			response.setStatus(200);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(500);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/configs/add" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addConfigs(@Valid @RequestBody DeviceCommand deviceCommand){
		
		MeterResponse meterResponse = new MeterResponse();
		List<DevicesInfo> devicesList = null;
		LOG.info("Firmware configuration received to add devices configs for : "+ deviceCommand.getLevelName());
		try {
			if(deviceCommand.getConfigVals() == null || deviceCommand.getConfigVals().size() == 0){
				meterResponse.setMessage("NOT FOUND");
				meterResponse.setStatus(404);
				return new ResponseEntity<>(meterResponse, HttpStatus.NOT_FOUND);
			}
			
			devicesList = devicesInfoService.getDevicesList(deviceCommand.getLevelName(),
					deviceCommand.getLevelValue());
			
			LOG.info("Devices list retrieved based on :"+ deviceCommand.getLevelName());
			if(devicesList != null && devicesList.size() > 0){
				devicesConfigService.addFirmwareConfigs(meterResponse, devicesList, deviceCommand);
				LOG.info("Firmware Configurations added for :"+ deviceCommand.getLevelName());
			}
		} catch (Exception e) {
			LOG.error("Issue in adding configs due to :" + e.getMessage());
			meterResponse.setMessage("Issue in adding configs due to :" + e.getMessage());
			meterResponse.setStatus(500);
			return new ResponseEntity<>(meterResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(meterResponse, HttpStatus.OK);
	}

	/**
	 * 
	 * @param deviceCommand
	 * @return
	 */
	@RequestMapping(value = "/upgrade" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> writeData(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		return firmwareDataService.upgradeFirmware(deviceCommand);
	}
}
