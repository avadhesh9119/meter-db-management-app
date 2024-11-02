package com.global.meter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.FirmwareConfig;
import com.global.meter.business.model.FirmwareData;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.Devices;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.data.repository.FirmwareConfigRepository;
import com.global.meter.data.repository.FirmwareDataRepository;
import com.global.meter.request.model.Device;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;
import com.global.meter.v3.inventive.serviceImpl.SingleConnectionCommandLogsServiceImple;

@Service
public class FirmwareDataService {
	
	private static final Logger LOG = LoggerFactory.getLogger(FirmwareDataService.class);
	
	@Autowired
	SingleConnectionCommandLogsServiceImple singleConnectionCommandLogsServiceImple;
	
	@Autowired
	FirmwareConfigRepository firmwareConfigRepository;
	
	@Autowired
	FirmwareDataRepository firmwareDataRepository;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	DevicesInfoService devicesInfoService;
	
	@Autowired
	CommonUtils commonUtils;

	public ResponseEntity<?> upgradeFirmware(DeviceCommand deviceCommand)
	{
		Device device = deviceCommand.getDevice();
		LOG.info("Received "+  deviceCommand.getCommand() + " Commands to perform on meter: " + device.getPlainText());
		Devices devices = null;
		ResponseEntity<?> response = null;
		FirmwareConfig firmwareConfigLog = null;
		DevicesInfo deviceInfo = null;
		MeterResponse meterResponse = new MeterResponse();
		meterResponse.setStatus(200);
		String commandStatus = Constants.IN_PROGRESS;
		int retry = Integer.parseInt(meterConfiguration.getFirmwareRetry());
		String trackingId = StringUtils.isEmpty(deviceCommand.getTrackingId()) ? String.valueOf(System.nanoTime())
				: deviceCommand.getTrackingId();
		
		if (device.getPlainText() != null) {
			deviceInfo = devicesInfoService.getDevicesInfo(device.getPlainText());
			
			if(deviceInfo == null) {
				meterResponse.setMessage("Device Not Found");
				meterResponse.setStatus(404);
				return new ResponseEntity<>(meterResponse, HttpStatus.OK);
			}
			
			if(!Constants.Status.BUSY.equalsIgnoreCase(deviceInfo.getStatus()))
			{
				deviceInfo.setStatus(Constants.Status.BUSY);
				devicesInfoService.updateDevicesRow(deviceInfo);
				
				Date mdasDatetime = new Date(System.currentTimeMillis());
				FirmwareData firmwareData = null;
				Optional<FirmwareData> firmware = null;
				
				if(!StringUtils.isEmpty(deviceCommand.getTrackingId())) {
					firmwareConfigLog = firmwareConfigRepository.findByTrackingId(deviceCommand.getTrackingId()).get();
					mdasDatetime = firmwareConfigLog.getMdas_datetime() != null ? firmwareConfigLog.getMdas_datetime():
									mdasDatetime;
					firmware = firmwareDataRepository.findByFileName(firmwareConfigLog.getCommand_val());
				}
				else{
					firmware = firmwareDataRepository.findByFileName(deviceCommand.getCommandsVal());	
				}
				if (!firmware.isPresent()) {

					meterResponse.setMessage("File Not Found");
					meterResponse.setStatus(404);
					return new ResponseEntity<>(meterResponse, HttpStatus.OK);

				}
				firmwareData = firmware.get();
				int attempts = (firmwareConfigLog != null && firmwareConfigLog.getTot_attempts() != null)  
								? firmwareConfigLog.getTot_attempts() : 0;
				try {
					
					devices = commonUtils.setDevicesInfoForFirmwareData(deviceInfo, firmwareData);
					
					LOG.info("Device info created & sending "+ deviceCommand.getCommand() +" command : " + device.getPlainText());
					response = commonUtils.getResponseEntity(devices, ConfigCommands.FW_UPDATE.commandName);
					LOG.info("Data response received from meter:" + device.getPlainText(), response.getBody());
					
					if(response != null && response.getStatusCode().value() == 200) {
						meterResponse = CommonUtils.getMapper().readValue(response.getBody().toString(),
								MeterResponse.class);
						if(meterResponse.getMessage().equalsIgnoreCase(Constants.SUCCESS)) {
							commandStatus = Constants.SUCCESS;
						}
						else if(meterResponse.getMessage().trim().equalsIgnoreCase(Constants.IMAGE_VERIFICATION_FAILED)) {
							commandStatus = Constants.FAILURE;
						}
					}

					String commandVal = (firmwareConfigLog != null && firmwareConfigLog.getCommand_val() != null)
											? firmwareConfigLog.getCommand_val() : deviceCommand.getCommandsVal();
					
					if(Constants.SUCCESS.equalsIgnoreCase(commandStatus)) {
						logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
								commandVal, Constants.SUCCESS, 
								(firmwareConfigLog != null && firmwareConfigLog.getReason() != null) ? firmwareConfigLog.getReason()
										+" : "+meterResponse.getMessage() : meterResponse.getMessage(),
								trackingId, new Date(System.currentTimeMillis()), attempts +1 ,
								mdasDatetime, firmwareConfigLog);
						
						deviceInfo.setStatus(Constants.Status.CONNECTED);
						devicesInfoService.updateDevicesRow(deviceInfo);
						SingleConnectionCommandReq req = new SingleConnectionCommandReq();
						List<String> obisCodeList = new ArrayList<>();
						req.setDevice(device);
						req.setExtBatchId(deviceCommand.getBatchId());
						req.setSource(firmwareConfigLog.getSource());
						req.setUserId(firmwareConfigLog.getUser_id());
						req.setCommand(ConfigCommands.NAME_PLATES.commandName);
						obisCodeList.add(ConfigCommands.NAME_PLATES.commandName);
						req.setObisCodeList(obisCodeList);
						singleConnectionCommandLogsServiceImple.addSingleConnectionCommandLogs(req);
						commandStatus = Constants.SUCCESS;
					}
					else
					{
						if(attempts >= retry) {
							logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
									commandVal, Constants.FAILURE, 
									(firmwareConfigLog != null && firmwareConfigLog.getReason() != null) ? firmwareConfigLog.getReason()
											+" : "+meterResponse.getMessage() : meterResponse.getMessage(),
									trackingId, new Date(System.currentTimeMillis()), attempts,
									mdasDatetime, firmwareConfigLog);
							
							commandStatus = Constants.FAILURE;
						}
						else {
							logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
									commandVal, Constants.IN_PROGRESS, 
									(firmwareConfigLog != null && firmwareConfigLog.getReason() != null) ? firmwareConfigLog.getReason()
											+" : "+meterResponse.getMessage() : meterResponse.getMessage(),
									trackingId, null, attempts +1 ,
									mdasDatetime, firmwareConfigLog);
							commandStatus = Constants.IN_PROGRESS;
						}
					}
					
					meterResponse.setTrackingId(trackingId);
					
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
				}  catch (Exception e) {
					if(attempts >= retry) {
						logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
								deviceCommand.getCommandsVal(), Constants.FAILURE, 
								(firmwareConfigLog != null && firmwareConfigLog.getReason() != null) ? firmwareConfigLog.getReason()
										+" : "+meterResponse.getMessage() : meterResponse.getMessage(),
								trackingId, new Date(System.currentTimeMillis()), attempts,
								mdasDatetime, firmwareConfigLog);
						
						commandStatus = Constants.FAILURE;
					}
					else {
						logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
								deviceCommand.getCommandsVal(), Constants.IN_PROGRESS, 
								(firmwareConfigLog != null && firmwareConfigLog.getReason() != null) ? firmwareConfigLog.getReason()
										+" : "+meterResponse.getMessage() : meterResponse.getMessage(),
								trackingId, null, attempts +1 ,
								mdasDatetime, firmwareConfigLog);
						commandStatus = Constants.IN_PROGRESS;
					}
					meterResponse.setMessage(Constants.IN_PROGRESS);
					meterResponse.setTrackingId(trackingId);
					
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
				}
			}	
			else
			{
				meterResponse.setMessage("Busy");
				
				if(deviceInfo.getLastcommunicationdatetime()== null 
						|| commonUtils.dateDiffInMins(deviceInfo.getLastcommunicationdatetime()) > 5){
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
				}
				return new ResponseEntity<>(meterResponse, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<>(meterResponse, HttpStatus.OK);
	
	}
	
	/**
	 * Used to save logs
	 * @param deviceSNO
	 * @param cmdName
	 * @param status
	 * @param firmwareConfigLog 
	 * @return
	 */
	public void logDevicesCommand(DevicesInfo devicesInfo, String deviceSNO, String cmdName, String status, String reason,
			String trackingId, Date commandCompletionDate, int attempts, Date mdasDatetime, FirmwareConfig firmwareConfigLog)
	{
		if(StringUtils.isEmpty(trackingId)) {
			trackingId = String.valueOf(System.nanoTime());
		}
		FirmwareConfig firmwareConfig = new FirmwareConfig(deviceSNO,
				mdasDatetime, commandCompletionDate, cmdName, devicesInfo.getDcu_serial_number(),
				devicesInfo.getDt_name(),devicesInfo.getFeeder_name(), mdasDatetime,
				devicesInfo.getOwner_name(),status,devicesInfo.getSubdevision_name(),
				devicesInfo.getSubstation_name(), trackingId,reason,attempts);
		firmwareConfig.setVersion(firmwareConfigLog.getVersion());
		firmwareConfig.setSource(firmwareConfigLog.getSource());
		firmwareConfig.setUser_id(firmwareConfigLog.getUser_id());
		
		firmwareConfigRepository.save(firmwareConfig);
	}
	
}
