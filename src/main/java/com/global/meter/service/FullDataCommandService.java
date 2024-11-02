package com.global.meter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.global.meter.business.model.DeviceCommandLogs;
import com.global.meter.business.model.DeviceCommandLogsOnePerDay;
import com.global.meter.business.model.DeviceConfigLogs;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.Devices;
import com.global.meter.common.model.FullDataMeterResponse;
import com.global.meter.common.model.FullMeterConfiguration;
import com.global.meter.common.model.FullMeterDataResponse;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.data.repository.DeviceCommandLogsOnePerDayRepository;
import com.global.meter.data.repository.DeviceCommandLogsRepository;
import com.global.meter.data.repository.DeviceConfigLogsRepository;
import com.global.meter.request.model.Device;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class FullDataCommandService {
	
	private static final Logger LOG = LoggerFactory.getLogger(FullDataCommandService.class);
	
	@Autowired
	MetersCommandsConfiguration meterCommands;
	
	@Autowired
	DeviceCommandLogsRepository devCommandLogRepository;

	@Autowired
	DeviceCommandLogsOnePerDayRepository deviceCommandLogsOnePerDayRepository;
	
	@Autowired
	DeviceConfigLogsRepository deviceConfigLogsRepository;
	
	@Autowired
	DevicesCommandsLogService devicesCommandsLogService;
	
	@Autowired
	FullDataCommandService fullDataCommandService;
	
	@Autowired
	HistoricalDataService historicalDataService;
	
	@Autowired
	NamePlatesDataService namePlatesDataService;
	
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
			deviceCommandLogs = devicesCommandsLogService.findCmdLogsByTrackingId(
					deviceCommand.getDevice().getPlainText(), deviceCommand.getTrackingId());
			
			if(deviceCommandLogs != null) {
				deviceCommand.setUserId(deviceCommandLogs.getUser_id());
				deviceCommand.setBatchId(deviceCommandLogs.getBatch_id());
				deviceCommand.setSource(deviceCommandLogs.getSource());
			
				mdasDatetime = deviceCommandLogs.getMdas_datetime() != null ? deviceCommandLogs.getMdas_datetime():
					mdasDatetime;
				dataSaveResults = deviceCommandLogs.getCommand_name() != null ? deviceCommandLogs.getCommand_name() :
					dataSaveResults;
				dataRetryCount = deviceCommandLogs.getCommand_retry() != null ? deviceCommandLogs.getCommand_retry() :
					dataRetryCount;
				
					if(deviceCommandLogs.getDaily_range_date() != null) {
						Map<String,String> map = deviceCommandLogs.getDaily_range_date();
						map.put(Constants.DateRange.STARTDATE, map.get(Constants.DateRange.STARTDATE));
						map.put(Constants.DateRange.ENDDATE, map.get(Constants.DateRange.ENDDATE));
						deviceCommand.setDailyRangeDate(map);															
						}
					if(deviceCommandLogs.getDelta_range_date() != null) {
						Map<String,String> map = deviceCommandLogs.getDelta_range_date();
						map.put(Constants.DateRange.STARTDATE, map.get(Constants.DateRange.STARTDATE));
						map.put(Constants.DateRange.ENDDATE, map.get(Constants.DateRange.ENDDATE));
						deviceCommand.setDeltaRangeDate(map);	
					}
				}
			
			
			for (Entry<String, String> entry : dataSaveResults.entrySet()) {
		        //This will pick those commands which are not in below condition.
		        if(!entry.getValue().equalsIgnoreCase(Constants.SUCCESS) 
		        		&& !entry.getValue().equalsIgnoreCase(Constants.FAILURE_NA)
		        		&& !entry.getValue().equalsIgnoreCase(Constants.FAILURE)
		        		&& !entry.getValue().equalsIgnoreCase(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH)) {
		        	commandList.add(entry.getKey());
		        }
		    }
		}
		else{
			commandList.addAll(deviceCommand.getObisCodeList());
			deviceCommandService.createCmdList(deviceCommand.getObisCodeList(), dataSaveResults, dataRetryCount);
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
						
					fullDataResponse.setMessage("Commissioning status is"+deviceInfo.getCommissioning_status());
					fullDataResponse.setStatus("422");
					return new ResponseEntity<>(fullDataResponse, HttpStatus.NOT_FOUND);
				}
				
				if(!Constants.Status.BUSY.equalsIgnoreCase(deviceInfo.getStatus())){
					deviceInfo.setStatus(Constants.Status.BUSY);
					devicesInfoService.updateDevicesRow(deviceInfo);
					
					Devices devices =  commonUtils.setFullDataDevicesInfo(deviceInfo, deviceCommand, System.currentTimeMillis());
					
					if(deviceCommand.getReadWise() != null && deviceCommand.getReadWise().equalsIgnoreCase("M") &&
							devices.getBillingCommDatetime() != null && 
							(CommonUtils.compareWithFirstDayOfMonth(devices.getBillingCommDatetime()) == 1) ) {
								commandList.remove(ConfigCommands.BILLING_DATA.commandName);
								dataSaveResults.put(ConfigCommands.BILLING_DATA.commandName, Constants.BILL_ALREADY_EXIST_CUURENT_MONTH);
					}

					if(commandList.size() > 0) {
						devices.setObisCodeList(commandList);
					}
					
					LOG.info("Device info created & sending "+ deviceCommand.getCommand() +" command : " + device.getPlainText());
					response = commonUtils.getResponseEntity(devices, deviceCommand.getCommand());
					LOG.info("Data response received from meter:" + device.getPlainText(), response.getBody());
					
					String fullDataStatus = Constants.IN_PROGRESS;
					if(response != null && response.getStatusCode().value() == 200) {
						fullDataResponse = CommonUtils.getMapper().readValue(response.getBody().toString(),
								FullDataMeterResponse.class);
						
						if(fullDataResponse != null && fullDataResponse.getResponse() != null && fullDataResponse.getResponse().size() > 0) {
							fullDataStatus = deviceCommandService.saveFullHistoryData(devices ,deviceInfo, fullDataResponse.getResponse(), 
									 dataSaveResults, dataRetryCount, fullDataResponse);
						}
					}
					
					int totAttempts = deviceCommandLogs == null ? 0 : deviceCommandLogs.getTot_attempts();
					String reason = deviceCommandLogs != null ? deviceCommandLogs.getReason()
							+" : "+fullDataResponse.getMessage() : fullDataResponse.getMessage();
					if(Constants.SUCCESS == fullDataStatus) {
						fullDataCommandService.logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
								dataSaveResults, Constants.SUCCESS, reason , trackingId, new Date(System.currentTimeMillis()),
								totAttempts + 1, mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), deviceCommand);
						fullDataStatus = Constants.SUCCESS;
					}
					else if(Constants.SUCCESS != fullDataStatus){
						if(totAttempts + 1 >= retry)
						{
							fullDataCommandService.logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
									dataSaveResults, Constants.FAILURE, reason, trackingId , new Date(System.currentTimeMillis()),
									totAttempts+1, mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), deviceCommand);
							fullDataStatus = Constants.FAILURE;
						}
						else {
							fullDataCommandService.logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
									dataSaveResults, Constants.IN_PROGRESS, reason, trackingId , null, totAttempts+1, 
									mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), deviceCommand);
							fullDataStatus = Constants.IN_PROGRESS;
						}
					}
	
					fullDataResponse.setTrackingId(trackingId);
					fullDataResponse.setMessage(fullDataResponse.getMessage() != null ? fullDataResponse.getMessage() : fullDataStatus);
					
					//update last read datetime as per the command name
					if(fullDataResponse != null && fullDataResponse.getResponse() != null
							&& fullDataResponse.getResponse().size() > 0){
						commonUtils.setDevicesInfoFromFullData(devices, deviceInfo , fullDataResponse);
					}
					
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
					
			}
			else
			{
				fullDataResponse.setMessage(Constants.Status.BUSY);
				
				if(!StringUtils.isEmpty(deviceCommand.getRequestFrom())) {
					
					fullDataCommandService.logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
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
					fullDataCommandService.logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
							dataSaveResults, Constants.FAILURE, reason,
							deviceCommand.getTrackingId(), new Date(System.currentTimeMillis()),
							deviceCommandLogs.getTot_attempts()+1 , mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), deviceCommand);
				}
				else {
					fullDataCommandService.logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
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
		DeviceCommandLogs deviceCommandLogs = new DeviceCommandLogs(deviceSNO, trackingId,
				cmdName, status, mdasDatetime,
				devicesInfo.getDcu_serial_number(), devicesInfo.getDt_name(),
				devicesInfo.getFeeder_name(), devicesInfo.getOwner_name(),
				devicesInfo.getSubdevision_name(), devicesInfo.getSubstation_name(),
				reason ,commandCompletionDate, attempts, retry);
		deviceCommandLogs.setBatch_id(deviceCommand.getBatchId());
		deviceCommandLogs.setCell_id(cellId);
		deviceCommandLogs.setSource(deviceCommand.getSource());
		deviceCommandLogs.setUser_id(deviceCommand.getUserId());
		
		if (deviceCommand.getDailyRangeDate() != null) {
			deviceCommandLogs.setDaily_range_date(deviceCommand.getDailyRangeDate());
		}
		if(deviceCommand.getDeltaRangeDate() != null) {
			deviceCommandLogs.setDelta_range_date(deviceCommand.getDeltaRangeDate());
		}
		
		devCommandLogRepository.save(deviceCommandLogs);
	}
	
	/**
	 * Used to save logs
	 * @param deviceSNO
	 * @param cmdName
	 * @param status
	 * @return
	 */
	public void logFullDataOnePerDayDevicesCommand(DevicesInfo devicesInfo, String deviceSNO, Map<String,String> cmdName, String status, String reason,
			String trackingId, Date commandCompletionDate, Integer attempts, Date mdasDatetime, Map<String,Integer> retry, String cellId,
			Date commandDate)
	{
		if(StringUtils.isEmpty(trackingId)) {
			trackingId = String.valueOf(System.nanoTime());
		}
		DeviceCommandLogsOnePerDay deviceCommandLogsOnePerDay = new DeviceCommandLogsOnePerDay(deviceSNO, trackingId,
				cmdName, status, mdasDatetime,
				devicesInfo.getDcu_serial_number(), devicesInfo.getDt_name(),
				devicesInfo.getFeeder_name(), devicesInfo.getOwner_name(),
				devicesInfo.getSubdevision_name(), devicesInfo.getSubstation_name(),
				reason ,commandCompletionDate, attempts, retry);
		deviceCommandLogsOnePerDay.setCell_id(cellId);
		deviceCommandLogsOnePerDay.setCommand_datetime(commandDate);
		
		deviceCommandLogsOnePerDayRepository.save(deviceCommandLogsOnePerDay);
	}
	
	/**
	 * This is used to write multiple configs at a time.
	 * @param deviceCommand
	 * @param responseList
	 * @return
	 */
	public ResponseEntity<?> writeMultipleConfifResponseEntity(DeviceCommand deviceCommand, FullMeterDataResponse responseList)
	{
		Device device = deviceCommand.getDevice();
		LOG.info("Received "+  deviceCommand.getCommand() + " Commands to perform on meter: " + device.getPlainText());

		ResponseEntity<?> response = null;
		DevicesInfo deviceInfo = null;
		DeviceConfigLogs deviceConfigLogs = null;
		String trackingId = "";
		
		Date mdasDatetime = new Date(System.currentTimeMillis());
		FullMeterDataResponse fullDataResponse = new FullMeterDataResponse();

		int retry = Integer.parseInt(meterCommands.getConfigRetry());
		Map<String, String> configsValues = new LinkedHashMap<String, String>();
		Map<String, String> configsStatus = new LinkedHashMap<String, String>();
		Map<String, Integer> configRetryCount = new LinkedHashMap<String, Integer>();
		
		Map<String, String> configsValuesToExecute = new LinkedHashMap<String, String>();
		
		if(!StringUtils.isEmpty(deviceCommand.getTrackingId())) {
			try {
				deviceConfigLogs = devicesCommandsLogService.findConfigLogsByTrackingId(
						deviceCommand.getDevice().getPlainText(), deviceCommand.getTrackingId());
				
				if(deviceConfigLogs == null) {
					fullDataResponse.setMessage("NOT FOUND");
					fullDataResponse.setStatus("404");
					return new ResponseEntity<>(fullDataResponse, HttpStatus.NOT_FOUND);
				}
				
				deviceCommand.setUserId(deviceConfigLogs.getUser_id());
				deviceCommand.setBatchId(deviceConfigLogs.getBatch_id());
				deviceCommand.setSource(deviceConfigLogs.getSource());
				
				
				mdasDatetime = deviceConfigLogs.getMdas_datetime() != null ? deviceConfigLogs.getMdas_datetime():
								mdasDatetime;
				configsValues = deviceConfigLogs.getCommand_name() != null ? deviceConfigLogs.getCommand_name() :
					configsValues;
				configsStatus = deviceConfigLogs.getCommand_status() != null ? deviceConfigLogs.getCommand_status() :
					configsStatus;
				configRetryCount = deviceConfigLogs.getCommand_retry() != null ? deviceConfigLogs.getCommand_retry() :
					configRetryCount;
				deviceCommand.setActivateActivityCalDatetime(deviceConfigLogs.getActivate_activity_cal_datetime() != null ?
						dateConverter.convertDateToString(deviceConfigLogs.getActivate_activity_cal_datetime()): "");
				
				for (Entry<String, String> configVal : configsValues.entrySet()) {
					String status = configsStatus.get(configVal.getKey());
					
					if(StringUtils.isEmpty(status) 
						 || status.equalsIgnoreCase(Constants.IN_PROGRESS)
						 || status.equalsIgnoreCase(Constants.ADDED)) {
						if (ConfigCommands.LOAD_LIMIT.commandName.equalsIgnoreCase(configVal.getKey())) {
							if(configVal.getValue() != null && !configVal.getValue().isEmpty()) {
								Double loadLimit = CommonUtils.kWToWatts(Double.parseDouble(configVal.getValue()));
								//configVal.setValue(String.valueOf(loadLimit));
								int limit = (int)loadLimit.intValue();
								configsValuesToExecute.put(configVal.getKey(), String.valueOf(limit));
							}
						}else {
							configsValuesToExecute.put(configVal.getKey(), configVal.getValue());
						}
						
						status = StringUtils.isEmpty(status) ? Constants.ADDED : status;
						configsStatus.put(configVal.getKey(), status);
						
						if(!status.equalsIgnoreCase(Constants.IN_PROGRESS)) {
							configRetryCount.put(configVal.getKey(), 0);
						}
					}
				}
			} catch (Exception e) {
				LOG.error("Issue in writing config : "+ e.getMessage());
			}
		}
		else{
			//Used to validate the configs
			for(Entry<String, String> configIterator: deviceCommand.getConfigVals().entrySet()){
				if(!ConfigCommands.contains(configIterator.getKey())){
					fullDataResponse.setMessage("NOT FOUND");
					fullDataResponse.setStatus("404");
					return new ResponseEntity<>(fullDataResponse, HttpStatus.NOT_FOUND);
				}
				/*else {
					if(ConfigCommands.ACTIVITY_CALENDER.commandName.equalsIgnoreCase(configIterator.getKey())
							&& StringUtils.isEmpty(deviceCommand.getActivateActivityCalDatetime())) {
						fullDataResponse.setMessage("In case of ActivityCalendar, activateActivityCalDatetime is also required along with commandsVal");
						fullDataResponse.setStatus("404");
						return new ResponseEntity<>(fullDataResponse, HttpStatus.NOT_FOUND);
					}
				}*/
				if (ConfigCommands.LOAD_LIMIT.commandName.equalsIgnoreCase(configIterator.getKey())) {
					if(configIterator.getValue() != null && !configIterator.getValue().isEmpty()) {
						Double loadLimit = CommonUtils.kWToWatts(Double.parseDouble(configIterator.getValue()));
						//configVal.setValue(String.valueOf(loadLimit));
						configsValuesToExecute.put(configIterator.getKey(), String.valueOf(loadLimit));
					}
				}else {
					configsValuesToExecute.put(configIterator.getKey(), configIterator.getValue());
				}
				
			}	
			//configsValues = deviceCommand.getConfigVals();
			//configsValuesToExecute = configsValues;
		}
		
		trackingId = StringUtils.isEmpty(deviceCommand.getTrackingId()) ? String.valueOf(System.nanoTime())
				: deviceCommand.getTrackingId();
				
		try {
						
				deviceInfo = devicesInfoService.getDevicesInfo(device.getPlainText());
				
				if(deviceInfo == null) {
					fullDataResponse.setMessage("Device Not Found. Please Contact Authorized Utility to get it commissioned.");
					fullDataResponse.setStatus("404");
					return new ResponseEntity<>(fullDataResponse, HttpStatus.NOT_FOUND);
				}
				
				if(!Constants.Status.BUSY.equalsIgnoreCase(deviceInfo.getStatus())){
					deviceInfo.setStatus(Constants.Status.BUSY);
					devicesInfoService.updateDevicesRow(deviceInfo);
					
					
					Devices devices = commonUtils.setDevicesInfoForConfig(deviceInfo);

					FullMeterConfiguration fullMeterConfiguration = null;
					fullMeterConfiguration = new FullMeterConfiguration();
					fullMeterConfiguration.setDevicesInfo(devices);
					fullMeterConfiguration.setConfigVals(configsValuesToExecute);
					fullMeterConfiguration.setCommandsName(deviceCommand.getCommand());
					fullMeterConfiguration.setActivateActivityCalDatetime(deviceCommand.getActivateActivityCalDatetime());
					
					
					LOG.info("Device info created & sending "+ deviceCommand.getCommand() +" command : " + device.getPlainText());
					response = deviceCommandService.getFullConfigsResponseEntity(fullMeterConfiguration, null);
					LOG.info("Data response received from meter:" + device.getPlainText(), response.getBody());
					
					String fullDataStatus = Constants.IN_PROGRESS;
					if(response != null && response.getStatusCode().value() == 200) {
						fullDataResponse = CommonUtils.getMapper().readValue(response.getBody().toString(),
								FullMeterDataResponse.class);
						
					}
					
					int successFlag = 0;
					for (MeterResponse meterResponse : fullDataResponse.getResponse()) {
						configsStatus.put(meterResponse.getObisCmd(), String.valueOf(meterResponse.getMessage()));
						if(meterResponse.getMessage().equalsIgnoreCase(Constants.SUCCESS)) {
							successFlag++;
							
							if(ConfigCommands.PAYMENT_MODE.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
								deviceInfo.setDev_mode(configsValuesToExecute.containsKey(meterResponse.getObisCmd()) && 
										configsValuesToExecute.get(meterResponse.getObisCmd()).equalsIgnoreCase("1") ? 
										Constants.DevMode.PREPAID : Constants.DevMode.POSTPAID);
							}
							if(ConfigCommands.BILLING_DATES.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
								deviceInfo.setBilling_datetime(configsValuesToExecute.get(meterResponse.getObisCmd()) != null 
										? configsValuesToExecute.get(meterResponse.getObisCmd()) : deviceInfo.getBilling_datetime());
							}
						}
						
						int totInternalAttempts = (deviceConfigLogs == null || deviceConfigLogs.getCommand_retry() == null) 
								? 1 : deviceConfigLogs.getCommand_retry().get(meterResponse.getObisCmd())+1;
						configRetryCount.put(meterResponse.getObisCmd(), totInternalAttempts);
					}
					if(configsValuesToExecute.size() == successFlag) {
						fullDataStatus = Constants.SUCCESS;
					}
					
					int totAttempts = deviceConfigLogs == null ? 0 : deviceConfigLogs.getTot_attempts();
					String reason = deviceConfigLogs != null ? deviceConfigLogs.getReason()
							+" : "+fullDataResponse.getMessage() : fullDataResponse.getMessage();
							
					if(Constants.SUCCESS == fullDataStatus) {
						fullDataCommandService.logDeviceConfigs(deviceInfo,deviceInfo.getDevice_serial_number(),
								configsValues, Constants.SUCCESS, reason , trackingId, new Date(System.currentTimeMillis()),
								totAttempts + 1, mdasDatetime, configRetryCount,
								deviceInfo.getCell_id(), configsStatus, deviceCommand);
						fullDataStatus = Constants.SUCCESS;
					}
					else if(Constants.SUCCESS != fullDataStatus){
						if(totAttempts + 1 >= retry)
						{
							fullDataCommandService.logDeviceConfigs(deviceInfo,deviceInfo.getDevice_serial_number(),
									configsValues, Constants.FAILURE, reason, trackingId , new Date(System.currentTimeMillis()),
									totAttempts+1, mdasDatetime, configRetryCount, 
									deviceInfo.getCell_id(), configsStatus, deviceCommand);
							fullDataStatus = Constants.FAILURE;
						}
						else {
							fullDataCommandService.logDeviceConfigs(deviceInfo,deviceInfo.getDevice_serial_number(),
									configsValues, Constants.IN_PROGRESS, reason, trackingId , null, totAttempts+1, 
									mdasDatetime, configRetryCount, 
									deviceInfo.getCell_id(), configsStatus, deviceCommand);
							fullDataStatus = Constants.IN_PROGRESS;
						}
					}
	
					fullDataResponse.setMessage(fullDataResponse.getMessage() != null ? fullDataResponse.getMessage() : fullDataStatus);
					
					fullDataResponse.setTrackingId(trackingId);
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
			}
			else
			{
				fullDataResponse.setMessage(Constants.Status.BUSY);
				
				if(deviceInfo.getLastcommunicationdatetime()== null 
						|| commonUtils.dateDiffInMins(deviceInfo.getLastcommunicationdatetime()) > 5){
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
				}
			}
		} catch (Exception e) {
			LOG.error("Error in inserting multiple configs for device : "+ deviceCommand.getDevice().getPlainText() + " due to : "+e.getMessage() + " : "+ deviceCommand.getCommand());
			fullDataResponse.setMessage(e.getMessage());
			
			if(deviceConfigLogs != null && deviceInfo != null)
			{
				String reason = deviceConfigLogs.getReason() != null ? deviceConfigLogs.getReason()
						+" : "+e.getMessage() : e.getMessage();
				if(deviceConfigLogs.getTot_attempts()+1 >= retry)
				{
					fullDataCommandService.logDeviceConfigs(deviceInfo,deviceInfo.getDevice_serial_number(),
							configsValues, Constants.FAILURE, reason,
							deviceCommand.getTrackingId(), new Date(System.currentTimeMillis()),
							deviceConfigLogs.getTot_attempts()+1 , mdasDatetime, configRetryCount,
							deviceInfo.getCell_id(), configsStatus, deviceCommand);
				}
				else {
					fullDataCommandService.logDeviceConfigs(deviceInfo,deviceInfo.getDevice_serial_number(),
							configsValues, Constants.IN_PROGRESS, reason,
							deviceCommand.getTrackingId(), null, deviceConfigLogs.getTot_attempts()+1,
							mdasDatetime, configRetryCount, deviceInfo.getCell_id(), configsStatus, deviceCommand);
				}
				fullDataResponse.setTrackingId(trackingId);
			}
			
			deviceInfo.setStatus(Constants.Status.CONNECTED);
			devicesInfoService.updateDevicesRow(deviceInfo);
			
		}
		
		return new ResponseEntity<>(fullDataResponse, HttpStatus.OK);
	}
	
	
	/**
	 * Used to save multiple devices Configs.
	 * @param deviceSNO
	 * @param cmdName
	 * @param status
	 * @return
	 */
	private void logDeviceConfigs(DevicesInfo devicesInfo, String deviceSNO, Map<String,String> cmdName, String status, String reason,
			String trackingId, Date commandCompletionDate, Integer attempts, Date mdasDatetime, Map<String,Integer> retry, String cellId,
			Map<String,String> configStatus, DeviceCommand deviceCommand)
	{
		if(StringUtils.isEmpty(trackingId)) {
			trackingId = String.valueOf(System.nanoTime());
		}
		
		DeviceConfigLogs deviceConfigLogs = new DeviceConfigLogs(deviceSNO, trackingId,
				cmdName, status, mdasDatetime,
				devicesInfo.getDcu_serial_number(), devicesInfo.getDt_name(),
				devicesInfo.getFeeder_name(), devicesInfo.getOwner_name(),
				devicesInfo.getSubdevision_name(), devicesInfo.getSubstation_name(),
				reason ,commandCompletionDate, attempts, retry);
		deviceConfigLogs.setCommand_status(configStatus);
		deviceConfigLogs.setUser_id(deviceCommand.getUserId());
		deviceConfigLogs.setBatch_id(deviceCommand.getBatchId());
		deviceConfigLogs.setSource(deviceCommand.getSource());
		deviceConfigLogs.setDevice_type(devicesInfo.getDevice_type());
		deviceConfigLogs.setMeter_type(devicesInfo.getMeter_type());
		
		deviceConfigLogsRepository.save(deviceConfigLogs);
	}

	
	public ResponseEntity<?> getFullDataPerDayResponseReadEntity(DeviceCommand deviceCommand, FullDataMeterResponse responseList)
	{
		Device device = deviceCommand.getDevice();
		LOG.info("Received "+  deviceCommand.getCommand() + " Commands to perform on meter: " + device.getPlainText());

		ResponseEntity<?> response = null;
		DevicesInfo deviceInfo = null;
		DeviceCommandLogsOnePerDay deviceCommandLogsOnePerDay = null;
		String trackingId = "";
		
		Date mdasDatetime = new Date(System.currentTimeMillis());
		Date commandDatetime = null;
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
			deviceCommandLogsOnePerDay = devicesCommandsLogService.findFullDataCmdLogsPerDayByTrackingId(
					deviceCommand.getDevice().getPlainText(), deviceCommand.getTrackingId());
			mdasDatetime = deviceCommandLogsOnePerDay.getMdas_datetime() != null ? deviceCommandLogsOnePerDay.getMdas_datetime():
							mdasDatetime;
			dataSaveResults = deviceCommandLogsOnePerDay.getCommand_name() != null ? deviceCommandLogsOnePerDay.getCommand_name() :
				dataSaveResults;
			dataRetryCount = deviceCommandLogsOnePerDay.getCommand_retry() != null ? deviceCommandLogsOnePerDay.getCommand_retry() :
				dataRetryCount;
			
			for (Entry<String, String> entry : dataSaveResults.entrySet()) {
		        //This will pick those commands which are not in below condition.
		        if(!entry.getValue().equalsIgnoreCase(Constants.SUCCESS) 
		        		&& !entry.getValue().equalsIgnoreCase(Constants.FAILURE_NA)
		        		&& !entry.getValue().equalsIgnoreCase(Constants.FAILURE)
		        		&& !entry.getValue().equalsIgnoreCase(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH)) {
		        	commandList.add(entry.getKey());
		        }
		    }
		}
		else{
			try {
				deviceCommandLogsOnePerDay = devicesCommandsLogService.findFullDataCmdLogsPerDayByDate(
						deviceCommand.getDevice().getPlainText(), CommonUtils.getCurrentDate());
			} catch (Exception e) {
				LOG.error("No data available in db for full data per day command for device: "+ deviceCommand.getDevice().getPlainText());
			}
			
			if(deviceCommandLogsOnePerDay != null) {
				// when command is present.
				dataSaveResults = deviceCommandLogsOnePerDay.getCommand_name() != null ? deviceCommandLogsOnePerDay.getCommand_name() :
					dataSaveResults;
				dataRetryCount = deviceCommandLogsOnePerDay.getCommand_retry() != null ? deviceCommandLogsOnePerDay.getCommand_retry() :
					dataRetryCount;
				
				for (Entry<String, String> entry : dataSaveResults.entrySet()) {
			        //This will pick those commands which are not in below condition.
			        if(!entry.getValue().equalsIgnoreCase(Constants.SUCCESS) 
			        		&& !entry.getValue().equalsIgnoreCase(Constants.FAILURE_NA)
			        		&& !entry.getValue().equalsIgnoreCase(Constants.FAILURE)
			        		&& !entry.getValue().equalsIgnoreCase(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH)) {
			        	commandList.add(entry.getKey());
			        }
			    }
				
				for (String cmd : deviceCommand.getObisCodeList()) {
					commandList.add(cmd);
					dataSaveResults.put(cmd, Constants.IN_PROGRESS);
					dataRetryCount.put(cmd, dataRetryCount.containsKey(cmd) ? dataRetryCount.get(cmd) : 1);
				}
				commandList = commandList.stream().distinct().collect(Collectors.toList());
			}
			else {
				commandList.addAll(deviceCommand.getObisCodeList());
				deviceCommandService.createCmdList(deviceCommand.getObisCodeList(), dataSaveResults, dataRetryCount);
			}
		}
		
		try {
				
			    responseList.setTrackingId(trackingId);
				deviceInfo = devicesInfoService.getDevicesInfo(device.getPlainText());
				
				if(deviceInfo == null) {
					fullDataResponse.setMessage("Device Not Found. Please Contact Authorized Utility to get it commissioned.");
					fullDataResponse.setStatus("404");
					return new ResponseEntity<>(fullDataResponse, HttpStatus.NOT_FOUND);
				}
				
				if(!Constants.Status.BUSY.equalsIgnoreCase(deviceInfo.getStatus())){
					deviceInfo.setStatus(Constants.Status.BUSY);
					devicesInfoService.updateDevicesRow(deviceInfo);
					
					commandDatetime = (deviceCommandLogsOnePerDay == null || deviceCommandLogsOnePerDay.getCommand_datetime() == null)
							? CommonUtils.getTodayDate() : deviceCommandLogsOnePerDay.getCommand_datetime();
					
					Devices devices =  commonUtils.setDevicesInfo(deviceInfo, deviceCommand, System.currentTimeMillis());
					
					if(deviceCommand.getReadWise() != null && deviceCommand.getReadWise().equalsIgnoreCase("M") &&
							devices.getBillingCommDatetime() != null && 
							(CommonUtils.compareWithFirstDayOfMonth(devices.getBillingCommDatetime()) == 1) ) {
								commandList.remove(ConfigCommands.BILLING_DATA.commandName);
								dataSaveResults.put(ConfigCommands.BILLING_DATA.commandName, Constants.BILL_ALREADY_EXIST_CUURENT_MONTH);
					}

					if(commandList.size() > 0) {
						devices.setObisCodeList(commandList);
					}
					
					LOG.info("Device info created & sending "+ deviceCommand.getCommand() +" command : " + device.getPlainText());
					response = commonUtils.getResponseEntity(devices, deviceCommand.getCommand());
					LOG.info("Data response received from meter:" + device.getPlainText(), response.getBody());
					
					String fullDataStatus = Constants.IN_PROGRESS;
					if(response != null && response.getStatusCode().value() == 200 && commonUtils.isValid()) {
						fullDataResponse = CommonUtils.getMapper().readValue(response.getBody().toString(),
								FullDataMeterResponse.class);
						
						if(fullDataResponse != null && fullDataResponse.getResponse() != null && fullDataResponse.getResponse().size() > 0) {
							fullDataStatus = deviceCommandService.saveFullHistoryData(devices, deviceInfo, fullDataResponse.getResponse(), 
									 dataSaveResults, dataRetryCount, fullDataResponse);
						}
					}
					
					int totAttempts = deviceCommandLogsOnePerDay == null ? 0 : deviceCommandLogsOnePerDay.getTot_attempts();
					String reason = deviceCommandLogsOnePerDay != null ? deviceCommandLogsOnePerDay.getReason()
							+" : "+fullDataResponse.getMessage() : fullDataResponse.getMessage();
					if(Constants.SUCCESS == fullDataStatus) {
						fullDataCommandService.logFullDataOnePerDayDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
								dataSaveResults, Constants.SUCCESS, reason , trackingId, new Date(System.currentTimeMillis()),
								totAttempts + 1, mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), commandDatetime);
						fullDataStatus = Constants.SUCCESS;
					}
					else if(Constants.SUCCESS != fullDataStatus){
						if(totAttempts + 1 >= retry)
						{
							fullDataCommandService.logFullDataOnePerDayDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
									dataSaveResults, Constants.FAILURE, reason, trackingId , new Date(System.currentTimeMillis()),
									totAttempts+1, mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), commandDatetime);
							fullDataStatus = Constants.FAILURE;
						}
						else {
							fullDataCommandService.logFullDataOnePerDayDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
									dataSaveResults, Constants.IN_PROGRESS, reason, trackingId , null, totAttempts+1, 
									mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), commandDatetime);
							fullDataStatus = Constants.IN_PROGRESS;
						}
					}
	
					fullDataResponse.setTrackingId(trackingId);
					fullDataResponse.setMessage(fullDataResponse.getMessage() != null 
								? fullDataResponse.getMessage() : fullDataStatus);
					
					//update last read datetime as per the command name
					if(fullDataResponse != null && fullDataResponse.getResponse() != null
							&& fullDataResponse.getResponse().size() > 0){
						commonUtils.setDevicesInfoFromFullData(devices, deviceInfo , fullDataResponse);
					}
					
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
					
			}
			else
			{
				fullDataResponse.setMessage(Constants.Status.BUSY);
				
				if(!StringUtils.isEmpty(deviceCommand.getRequestFrom())) {
					
					fullDataCommandService.logFullDataOnePerDayDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
							dataSaveResults, Constants.IN_PROGRESS, "", trackingId , null, 0, 
							mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), commandDatetime);

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
			
			if(deviceCommandLogsOnePerDay != null && deviceInfo != null)
			{
				String reason = deviceCommandLogsOnePerDay.getReason() != null ? deviceCommandLogsOnePerDay.getReason()
						+" : "+e.getMessage() : e.getMessage();
				if(deviceCommandLogsOnePerDay.getTot_attempts()+1 >= retry)
				{
					fullDataCommandService.logFullDataOnePerDayDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
							dataSaveResults, Constants.FAILURE, reason,
							deviceCommand.getTrackingId(), new Date(System.currentTimeMillis()),
							deviceCommandLogsOnePerDay.getTot_attempts()+1 , mdasDatetime, 
							dataRetryCount, deviceInfo.getCell_id(), commandDatetime);
				}
				else {
					fullDataCommandService.logFullDataOnePerDayDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
							dataSaveResults, Constants.IN_PROGRESS, reason,
							deviceCommand.getTrackingId(), null, deviceCommandLogsOnePerDay.getTot_attempts()+1,
							mdasDatetime, dataRetryCount, deviceInfo.getCell_id(), commandDatetime);
				}
			}
			
			deviceInfo.setStatus(Constants.Status.CONNECTED);
			devicesInfoService.updateDevicesRow(deviceInfo);
			
		}
		return new ResponseEntity<>(fullDataResponse, HttpStatus.OK);
	}
}
