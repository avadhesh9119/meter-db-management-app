package com.global.meter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.global.meter.business.model.DeviceCommandLogs;
import com.global.meter.business.model.DeviceCommandPrepayLogs;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.Devices;
import com.global.meter.common.model.FullDataMeterResponse;
import com.global.meter.data.repository.DeviceCommandPrepayLogRepository;
import com.global.meter.request.model.Device;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class PrepayDataCommandService {
	
	private static final Logger LOG = LoggerFactory.getLogger(PrepayDataCommandService.class);
	
	@Autowired
	MetersCommandsConfiguration meterCommands;
	
	@Autowired
	DevicesCommandsPrepayLogService devicesCommandsPrepayLogService;
	
	@Autowired
	DeviceCommandPrepayLogRepository deviceCommandPrepayLogRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DevicesInfoService devicesInfoService;
	
	@Autowired
	CommonUtils commonUtils;
	
	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	DeviceCommandService deviceCommandService;
	
	@Autowired
	HistoricalDataService historicalDataService;
	
	public ResponseEntity<?> getResponseReadEntity(DeviceCommand deviceCommand, FullDataMeterResponse responseList)
	{
		Device device = deviceCommand.getDevice();
		LOG.info("Received "+  deviceCommand.getCommand() + " Commands to perform on meter: " + device.getPlainText());

		ResponseEntity<?> response = null;
		DevicesInfo deviceInfo = null;
		DeviceCommandLogs deviceCommandLogs = null;
		String trackingId = "";
		
		Date mdasDatetime = new Date(System.currentTimeMillis());
		FullDataMeterResponse fullDataResponse = new FullDataMeterResponse();

		int retry = Integer.parseInt(meterCommands.getRetry());
		Map<String, String> dataSaveResults = new LinkedHashMap<String, String>();
		Map<String, Integer> dataRetryCount = new LinkedHashMap<String, Integer>();
		List<String> commandList = new ArrayList<String>();
		
		if(!ConfigCommands.contains(deviceCommand.getCommand())){
			fullDataResponse.setMessage("NOT FOUND");
			fullDataResponse.setStatus("404");
			return new ResponseEntity<>(fullDataResponse, HttpStatus.NOT_FOUND);
		}
		else {
			trackingId = StringUtils.isEmpty(deviceCommand.getTrackingId()) ? String.valueOf(System.nanoTime())
					: deviceCommand.getTrackingId()  ;
		}
		
		if(!StringUtils.isEmpty(deviceCommand.getRequestFrom())) {
			deviceCommand.setTrackingId("");
		}
		
		if(!StringUtils.isEmpty(deviceCommand.getTrackingId())) {
			deviceCommandLogs = devicesCommandsPrepayLogService.findCmdLogsByTrackingId(
					deviceCommand.getDevice().getPlainText(), deviceCommand.getTrackingId());
			deviceCommand.setBatchId(deviceCommandLogs.getBatch_id());
			deviceCommand.setUserId(deviceCommandLogs.getUser_id());
			deviceCommand.setSource(deviceCommandLogs.getSource());
			
			mdasDatetime = deviceCommandLogs.getMdas_datetime() != null ? deviceCommandLogs.getMdas_datetime():
							mdasDatetime;
			dataSaveResults = deviceCommandLogs.getCommand_name() != null ? deviceCommandLogs.getCommand_name() :
				dataSaveResults;
			dataRetryCount = deviceCommandLogs.getCommand_retry() != null ? deviceCommandLogs.getCommand_retry() :
				dataRetryCount;
			
			for (Entry<String, String> entry : dataSaveResults.entrySet()) {
		        //This will pick those commands which are not in below condition.
		        if(!entry.getValue().equalsIgnoreCase(Constants.SUCCESS) 
		        		&& !entry.getValue().equalsIgnoreCase(Constants.FAILURE)) {
		        	commandList.add(entry.getKey());
		        }
		    }
		}
		else{
			commandList.addAll(deviceCommand.getPrepayObisCodeList());
			deviceCommandService.createCmdList(deviceCommand.getPrepayObisCodeList(), dataSaveResults, dataRetryCount);
		}
		
		try {
				
			    responseList.setTrackingId(trackingId);
						
				deviceInfo = devicesInfoService.getDevicesInfo(device.getPlainText());
				
				if(deviceInfo == null) {
					fullDataResponse.setMessage("Device Not Found. Please Contact Authorized Utility to get it commissioned.");
					fullDataResponse.setStatus("404");
					return new ResponseEntity<>(fullDataResponse, HttpStatus.NOT_FOUND);
				}
				if(!deviceInfo.getCommissioning_status().equalsIgnoreCase(Constants.Status.UP)) {
						
					fullDataResponse.setMessage("Device is "+deviceInfo.getCommissioning_status());
					fullDataResponse.setStatus("422");
					return new ResponseEntity<>(fullDataResponse, HttpStatus.NOT_FOUND);
				}
				
				if(!Constants.Status.BUSY.equalsIgnoreCase(deviceInfo.getStatus())){
					deviceInfo.setStatus(Constants.Status.BUSY);
					devicesInfoService.updateDevicesRow(deviceInfo);
					
					Devices devices =  commonUtils.setDevicesInfo(deviceInfo, deviceCommand, System.currentTimeMillis());
					

					if(commandList.size() > 0) {
						devices.setPrepayObisCodeList(commandList);
					}
					
					LOG.info("Device info created & sending "+ deviceCommand.getCommand() +" command : " + device.getPlainText());
					response = commonUtils.getResponseEntity(devices, deviceCommand.getCommand());
					LOG.info("Data response received from meter:" + device.getPlainText(), response.getBody());
					
					String fullDataStatus = Constants.IN_PROGRESS;
					if(response != null && response.getStatusCode().value() == 200) {
						fullDataResponse = CommonUtils.getMapper().readValue(response.getBody().toString(),
								FullDataMeterResponse.class);
						
						if(fullDataResponse != null && fullDataResponse.getResponse() != null && fullDataResponse.getResponse().size() > 0) {
							fullDataStatus = deviceCommandService.saveFullPrepayHistoryData(devices ,deviceInfo, fullDataResponse.getResponse(), 
									 dataSaveResults, dataRetryCount, fullDataResponse, deviceCommand);
						}
						else if(fullDataResponse.getStatus().equals("408") &&
								deviceCommand.getPrepayObisCodeList().contains(ConfigCommands.PING_METER.commandName)) {
							historicalDataService.insertPrepayData(deviceInfo, deviceCommand);	
						}
					}
					
					
					int totAttempts = deviceCommandLogs == null ? 0 : deviceCommandLogs.getTot_attempts();
					String reason = deviceCommandLogs != null ? deviceCommandLogs.getReason()
							+" : "+fullDataResponse.getMessage() : fullDataResponse.getMessage();
					if(Constants.SUCCESS == fullDataStatus) {
						logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
								dataSaveResults, Constants.SUCCESS, reason , trackingId, new Date(System.currentTimeMillis()),
								totAttempts + 1, mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), deviceCommand);
						fullDataStatus = Constants.SUCCESS;
					}
					else if(Constants.SUCCESS != fullDataStatus){
						if(totAttempts + 1 >= retry)
						{
							logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
									dataSaveResults, Constants.FAILURE, reason, trackingId , new Date(System.currentTimeMillis()),
									totAttempts+1, mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), deviceCommand);
							fullDataStatus = Constants.FAILURE;
						}
						else {
							logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
									dataSaveResults, Constants.IN_PROGRESS, reason, trackingId , null, totAttempts+1, 
									mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), deviceCommand);
							fullDataStatus = Constants.IN_PROGRESS;
						}
					}
	
					fullDataResponse.setTrackingId(trackingId);
					fullDataResponse.setMessage(fullDataResponse.getMessage() != null ? fullDataResponse.getMessage() : fullDataStatus);
					
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
					
			}
			else
			{
				fullDataResponse.setMessage(Constants.Status.BUSY);
				
				if(!StringUtils.isEmpty(deviceCommand.getRequestFrom())) {
					
					logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
							dataSaveResults, Constants.IN_PROGRESS, "", trackingId , null, 0, 
							mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), deviceCommand);

				}
				
				if(deviceInfo.getLastcommunicationdatetime()== null 
						|| commonUtils.dateDiffInMins(deviceInfo.getLastcommunicationdatetime()) > 5){
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
				}
				
			}
		} catch (Exception e) {
			LOG.error("Error in inserting data for device : "+ deviceCommand.getDevice().getPlainText() + " due to : "+e.getMessage() + " : "+ deviceCommand.getCommand());
			fullDataResponse.setMessage(e.getMessage());
			
			if(deviceCommandLogs != null && deviceInfo != null)
			{
				String reason = deviceCommandLogs.getReason() != null ? deviceCommandLogs.getReason()
						+" : "+e.getMessage() : e.getMessage();
				if(deviceCommandLogs.getTot_attempts()+1 >= retry)
				{
					logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
							dataSaveResults, Constants.FAILURE, reason,
							deviceCommand.getTrackingId(), new Date(System.currentTimeMillis()),
							deviceCommandLogs.getTot_attempts()+1 , mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), deviceCommand);
				}
				else {
					logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
							dataSaveResults, Constants.IN_PROGRESS, reason,
							deviceCommand.getTrackingId(), null, deviceCommandLogs.getTot_attempts()+1,
							mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), deviceCommand);
				}
			}
			
			deviceInfo.setStatus(Constants.Status.CONNECTED);
			devicesInfoService.updateDevicesRow(deviceInfo);
			
		}
		return new ResponseEntity<>(fullDataResponse, HttpStatus.OK);
	}
	
	
	/**
	 * Used to save logs
	 * @param deviceSNO
	 * @param cmdName
	 * @param status
	 * @return
	 */
	public void logDevicesCommand(DevicesInfo devicesInfo, String deviceSNO, Map<String,String> cmdName, String status, String reason,
			String trackingId, Date commandCompletionDate, Integer attempts, Date mdasDatetime, Map<String,Integer> retry, String cellId, DeviceCommand deviceCommand)
	{
		if(StringUtils.isEmpty(trackingId)) {
			trackingId = String.valueOf(System.nanoTime());
		}
		DeviceCommandPrepayLogs deviceCommandLogs = new DeviceCommandPrepayLogs(deviceSNO, trackingId,
				cmdName, status, mdasDatetime,
				devicesInfo.getDcu_serial_number(), devicesInfo.getDt_name(),
				devicesInfo.getFeeder_name(), devicesInfo.getOwner_name(),
				devicesInfo.getSubdevision_name(), devicesInfo.getSubstation_name(),
				reason ,commandCompletionDate, attempts, retry);
		deviceCommandLogs.setCell_id(cellId);
		deviceCommandLogs.setBatch_id(deviceCommand.getBatchId());
		deviceCommandLogs.setSource(deviceCommand.getSource());
		deviceCommandLogs.setUser_id(deviceCommand.getUserId());
		deviceCommandLogs.setDevice_type(devicesInfo.getDevice_type());
		deviceCommandLogs.setMeter_type(devicesInfo.getMeter_type());
		
		deviceCommandPrepayLogRepository.save(deviceCommandLogs);
	}
	
}
