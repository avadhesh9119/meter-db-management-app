package com.global.meter.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.global.meter.business.model.DeviceCommandLog;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.Devices;
import com.global.meter.common.model.FullDataMeterResponse;
import com.global.meter.common.model.FullDataModel;
import com.global.meter.common.model.FullMeterConfiguration;
import com.global.meter.common.model.MeterConfiguration;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.data.repository.DeviceCommandLogRepository;
import com.global.meter.request.model.Device;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DeviceCommandService {
	
	private static final Logger LOG = LoggerFactory.getLogger(DeviceCommandService.class);
	
	@Autowired
	MetersCommandsConfiguration meterCommands;
	
	@Autowired
	DeviceCommandLogRepository devCommandLogRepository;
	
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
	
	public ResponseEntity<?> getResponseReadEntity(DeviceCommand deviceCommand)
	{
		Device device = deviceCommand.getDevice();
		LOG.info("Received "+  deviceCommand.getCommand() + " Commands to perform on meter: " + device.getPlainText());
		ResponseEntity<?> response = null;
		DevicesInfo deviceInfo = null;
		DeviceCommandLog deviceCommandLog = null;
		Date mdasDatetime = new Date(System.currentTimeMillis());
		MeterResponse meterResponse = new MeterResponse();
		boolean status = Constants.FLAG_FAILURE;
		int retry = Integer.parseInt(meterCommands.getRetry());
		String commandStatus = Constants.IN_PROGRESS;
		String trackingId = String.valueOf(System.nanoTime());
		
		if(!StringUtils.isEmpty(deviceCommand.getRequestFrom())) {
			trackingId = !StringUtils.isEmpty(deviceCommand.getTrackingId()) ? deviceCommand.getTrackingId() : trackingId;
			deviceCommand.setTrackingId("");
		}
		
		/*
		if(!ConfigCommands.contains(deviceCommand.getCommand())){
			System.out.println("NOT");
			meterResponse.setMessage("NOT FOUND");
			meterResponse.setStatus(404);
			return new ResponseEntity<>(meterResponse, HttpStatus.NOT_FOUND);
		}
		*/
		
		if(!StringUtils.isEmpty(deviceCommand.getTrackingId())) {
			deviceCommandLog = devicesCommandsLogService.findByTrackingId(
					deviceCommand.getDevice().getPlainText(), deviceCommand.getCommand(),
					deviceCommand.getTrackingId());
			
			if(deviceCommandLog != null) {
			
				deviceCommand.setBatchId(deviceCommandLog.getBatch_id());
				deviceCommand.setUserId(deviceCommandLog.getUser_id());
				deviceCommand.setSource(deviceCommandLog.getSource());
			
				mdasDatetime = deviceCommandLog.getMdas_datetime() != null ? deviceCommandLog.getMdas_datetime():
							mdasDatetime;
				
				if(deviceCommandLog.getDatewise_range() != null) {
					
					Map<String,String> map = deviceCommandLog.getDatewise_range();
					map.put(Constants.DateRange.STARTDATE, map.get(Constants.DateRange.STARTDATE));
					map.put(Constants.DateRange.ENDDATE, map.get(Constants.DateRange.ENDDATE));
					
					if(ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(deviceCommand.getCommand())) {
						deviceCommand.setDailyRangeDate(map);								
					}
					else if(ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(deviceCommand.getCommand())) {
						deviceCommand.setDeltaRangeDate(map);								
					}
				}
			}
		}

		try {
				deviceInfo = devicesInfoService.getDevicesInfo(device.getPlainText());
				
				
				if(deviceInfo == null) {
					meterResponse.setMessage("Device Not Found");
					meterResponse.setStatus(404);
					return new ResponseEntity<>(meterResponse, HttpStatus.OK);
				}
				
				
				if(!ConfigCommands.NAME_PLATES.commandName.equals(deviceCommand.getCommand()) &&
					!deviceInfo.getCommissioning_status().equalsIgnoreCase(Constants.Status.UP)) {
					
					meterResponse.setMessage(Constants.Status.NOT_COMMISSIONED);
					meterResponse.setStatus(422);
					return new ResponseEntity<>(meterResponse, HttpStatus.OK);
				}
				
				if(ConfigCommands.NAME_PLATES.commandName.equalsIgnoreCase(deviceCommand.getCommand()) 
								&& !Constants.Status.UP.equalsIgnoreCase(deviceInfo.getCommissioning_status()) 
								&& !Constants.Status.DOWN.equalsIgnoreCase(deviceInfo.getCommissioning_status())
										&& !(Constants.Status.INACTIVE.equalsIgnoreCase(deviceInfo.getCommissioning_status()) 
												&& deviceInfo.getInstallation_datetime() == null)) {
					
					meterResponse.setMessage("Device is "+deviceInfo.getCommissioning_status());
					meterResponse.setStatus(422);
					return new ResponseEntity<>(meterResponse, HttpStatus.OK);
				}
					
				if(!Constants.Status.BUSY.equalsIgnoreCase(deviceInfo.getStatus())){
					deviceInfo.setStatus(Constants.Status.BUSY);
					devicesInfoService.updateDevicesRow(deviceInfo);
					
					Devices devices =  commonUtils.setDevicesInfo(deviceInfo, deviceCommand, System.currentTimeMillis());
					
					LOG.info("Device info created & sending "+ deviceCommand.getCommand() +" command : " + device.getPlainText());
					response = commonUtils.getResponseEntity(devices, deviceCommand.getCommand());
					LOG.info("Data response received from meter:" + device.getPlainText(), response.getBody());
					
					if(response != null && response.getStatusCode().value() == 200 && commonUtils.isValid()) {
						meterResponse = CommonUtils.getMapper().readValue(response.getBody().toString(),
								MeterResponse.class);
						if(meterResponse.getData() != null && meterResponse.getData().length > 0 ){
							status = saveHistoryData(deviceInfo, deviceCommand.getCommand(), meterResponse);
						}
						if(meterResponse.getObisCode() != null && meterResponse.getObisCode().size() > 0) {
							status = Constants.FLAG_SUCCESS;
						}
					}
					
					if(Constants.FLAG_SUCCESS == status && StringUtils.isEmpty(deviceCommand.getTrackingId())){
						logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
								deviceCommand.getCommand(), Constants.SUCCESS, meterResponse.getMessage(),
								trackingId , new Date(System.currentTimeMillis()),1 , mdasDatetime, deviceCommand);
						
						commandStatus = Constants.SUCCESS;

						changePreviousLogsDevicesCommand(deviceCommand, commandStatus, meterResponse.getMessage(),
								new Date(System.currentTimeMillis()));
					}
					else if(Constants.FLAG_SUCCESS == status && !StringUtils.isEmpty(deviceCommand.getTrackingId())) {
						logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
								deviceCommand.getCommand(), Constants.SUCCESS, 
								deviceCommandLog.getReason() != null ? deviceCommandLog.getReason()
										+" : "+meterResponse.getMessage() : meterResponse.getMessage(),
								deviceCommandLog.getTracking_id(), new Date(System.currentTimeMillis()),
								deviceCommandLog.getTot_attempts() + 1, mdasDatetime, deviceCommand);
						commandStatus = Constants.SUCCESS;
						
						changePreviousLogsDevicesCommand(deviceCommand, commandStatus, meterResponse.getMessage(),
								new Date(System.currentTimeMillis()));
					}
					else if(Constants.FLAG_SUCCESS != status && StringUtils.isEmpty(deviceCommand.getTrackingId())){
						logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
								deviceCommand.getCommand(), Constants.IN_PROGRESS, meterResponse.getMessage(),
								trackingId , null ,1 , mdasDatetime, deviceCommand);
					}
					else if(Constants.FLAG_SUCCESS != status && !StringUtils.isEmpty(deviceCommand.getTrackingId())){
						if(deviceCommandLog.getTot_attempts()+1 >= retry)
						{
							logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
									deviceCommand.getCommand(), Constants.FAILURE, 
									deviceCommandLog.getReason() != null ? deviceCommandLog.getReason()
										+" : "+meterResponse.getMessage() : meterResponse.getMessage(),
									deviceCommand.getTrackingId() , new Date(System.currentTimeMillis()),
									deviceCommandLog.getTot_attempts()+1, mdasDatetime, deviceCommand);
							commandStatus = Constants.FAILURE;
							
						}
						else {
							logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
									deviceCommand.getCommand(), Constants.IN_PROGRESS, 
									deviceCommandLog.getReason() != null ? deviceCommandLog.getReason()
										+" : "+meterResponse.getMessage() : meterResponse.getMessage(),
									deviceCommand.getTrackingId() , null,deviceCommandLog.getTot_attempts()+1, 
									mdasDatetime, deviceCommand);
						}
					}
	
					//update last read datetime as per the command name
					if(meterResponse.getObisCode() != null 
							&& meterResponse.getObisCode().size() > 0 
							&& !ConfigCommands.NAME_PLATES.commandName.equals(deviceCommand.getCommand())){
						boolean dlpStatus = commonUtils.setDatasetsReadDatetimeInDevicesInfo(devices, deviceInfo , meterResponse
								, deviceCommand.getCommand(), null);
						
						//This will run in case of dlp when no data is present for current day.
						if(dlpStatus) {
							logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
									deviceCommand.getCommand(), Constants.SUCCESS, meterResponse.getMessage(),
									trackingId , new Date(System.currentTimeMillis()) ,1 , mdasDatetime, deviceCommand);
							
							commandStatus = Constants.SUCCESS;
							
							changePreviousLogsDevicesCommand(deviceCommand, commandStatus, meterResponse.getMessage(),
									new Date(System.currentTimeMillis()));
						}
					}
					else if(ConfigCommands.NAME_PLATES.commandName.equals(deviceCommand.getCommand())
							&& Constants.FLAG_SUCCESS == status
							&& Constants.Status.UP.equalsIgnoreCase(deviceInfo.getCommissioning_status())) {
						/*DeviceCommand fullDataDevCommand = new DeviceCommand();
						fullDataDevCommand.setDevice(deviceCommand.getDevice());
						fullDataDevCommand.setObisCodeList(Constants.FullDataList.getList());
						fullDataDevCommand.setCommand(ConfigCommands.FULL_DATA_READ.commandName);
						fullDataDevCommand.setRequestFrom("G");
						
						try {
							FullDataMeterResponse fullDataResponse = new FullDataMeterResponse();
							fullDataCommandService.getResponseReadEntity(fullDataDevCommand, fullDataResponse);
						} catch (Exception e) {
							LOG.error("Read is not fully done when name plate is success for dev: "+device.getPlainText());
						}*/
					}
					/*trackingId = StringUtils.isEmpty(deviceCommand.getTrackingId()) ? trackingId:
						deviceCommand.getTrackingId();*/
					
					meterResponse.setMessage(commandStatus);
					meterResponse.setTrackingId(trackingId);
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
			}
			else
			{
				meterResponse.setMessage(Constants.BUSY);
				meterResponse.setStatus(500);
				meterResponse.setTrackingId(trackingId);
				
				if(!StringUtils.isEmpty(deviceCommand.getRequestFrom())) {
					
					logDevicesCommand(deviceInfo, deviceInfo.getDevice_serial_number(),
							deviceCommand.getCommand(), Constants.IN_PROGRESS, 
							"", trackingId , null,0, mdasDatetime, deviceCommand);

				}
				
				if(deviceInfo.getLastcommunicationdatetime()== null 
						|| commonUtils.dateDiffInMins(deviceInfo.getLastcommunicationdatetime()) > 5){
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error in inserting data for device : "+ deviceCommand.getDevice().getPlainText() + " due to : "+e.getMessage() + " : "+ deviceCommand.getCommand());
			meterResponse.setMessage(e.getMessage());
			if(deviceCommandLog != null && deviceInfo != null)
			{
				String reason = deviceCommandLog.getReason() != null ? deviceCommandLog.getReason()
						+" : "+e.getMessage() : e.getMessage();
				if(deviceCommandLog.getTot_attempts()+1 >= retry)
				{
					logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
							deviceCommand.getCommand(), Constants.FAILURE, reason,
							deviceCommand.getTrackingId() , new Date(System.currentTimeMillis()),
							deviceCommandLog.getTot_attempts()+1 , mdasDatetime, deviceCommand);
					commandStatus = Constants.FAILURE;
					
				}
				else {
					logDevicesCommand(deviceInfo,deviceInfo.getDevice_serial_number(),
							deviceCommand.getCommand(), Constants.IN_PROGRESS, reason,
							deviceCommand.getTrackingId(), null, deviceCommandLog.getTot_attempts()+1,
							mdasDatetime, deviceCommand);
				}
			}
			meterResponse.setMessage(commandStatus);
			meterResponse.setTrackingId(trackingId);
			deviceInfo.setStatus(Constants.Status.CONNECTED);
			devicesInfoService.updateDevicesRow(deviceInfo);
		}
		
		return new ResponseEntity<>(meterResponse, HttpStatus.OK);
	
	}
	
	public ResponseEntity<?> getResponseWriteEntity(DeviceCommand deviceCommand)
	{
		Device device = deviceCommand.getDevice();
		LOG.info("Received "+  deviceCommand.getCommand() + " Commands to perform on meter: " + device.getPlainText());
		Devices devices = null;
		ResponseEntity<?> response = null;
		DevicesInfo deviceInfo = null;
		MeterResponse meterResponse = new MeterResponse();
		long currentTime = System.currentTimeMillis();
		boolean status = Constants.FLAG_FAILURE;
		String commandStatus = Constants.IN_PROGRESS;
		String trackingId = StringUtils.isEmpty(deviceCommand.getTrackingId()) ? String.valueOf(System.nanoTime())
				: deviceCommand.getTrackingId();
		
		if(!StringUtils.isEmpty(deviceCommand.getRequestFrom())) {
			trackingId = deviceCommand.getTrackingId();
			deviceCommand.setTrackingId("");
		}
		
		if (device.getPlainText() != null) {
			deviceInfo = devicesInfoService.getDevicesInfo(device.getPlainText());
			
			if(deviceInfo == null) {
				meterResponse.setMessage("Device Not Found");
				meterResponse.setStatus(404);
				return new ResponseEntity<>(meterResponse, HttpStatus.OK);
			}
			
			Date mdasDatetime = new Date(System.currentTimeMillis());
			
			if(!Constants.Status.BUSY.equalsIgnoreCase(deviceInfo.getStatus()))
			{
				try {
					deviceInfo.setStatus(Constants.Status.BUSY);
					devicesInfoService.updateDevicesRow(deviceInfo);
					
					//CommonUtils commonUtils = new CommonUtils();
					devices = commonUtils.setDevicesInfo(deviceInfo, deviceCommand, currentTime);
					
					LOG.info("Device info created & sending "+ deviceCommand.getCommand() +" command : " + device.getPlainText());
					response = commonUtils.getResponseEntity(devices, deviceCommand.getCommand());
					LOG.info("Data response received from meter:" + device.getPlainText(), response.getBody());
					
					if(response != null && response.getStatusCode().value() == 200) {
						 meterResponse = CommonUtils.getMapper().readValue(response.getBody().toString(),
								MeterResponse.class);
						if(meterResponse.getMessage().equalsIgnoreCase(Constants.SUCCESS)) {
							status = Constants.FLAG_SUCCESS;
						}
					}

					//update last read datetime as per the command name
					if(Constants.FLAG_SUCCESS == status){
						logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
								deviceCommand.getCommand(), Constants.SUCCESS, meterResponse.getMessage(),
								trackingId, new Date(System.currentTimeMillis()),1,
								mdasDatetime, deviceCommand);
						
						meterResponse.setMessage(Constants.SUCCESS);
						deviceInfo.setLastcommunicationdatetime(new Date(System.currentTimeMillis()));
						if(ConfigCommands.CONNECT.commandName.equals(deviceCommand.getCommand())){
							deviceInfo.setLastconnectdatetime(new Date(System.currentTimeMillis()));
						}
						else if(ConfigCommands.DISCONNECT.commandName.equals(deviceCommand.getCommand())){
							deviceInfo.setLastdisconnectdatetime(new Date(System.currentTimeMillis()));
						}
						commandStatus = Constants.SUCCESS;
					}
					else{
						logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
								deviceCommand.getCommand(), Constants.FAILURE, meterResponse.getMessage(),
								trackingId , new Date(System.currentTimeMillis()),1,
								mdasDatetime, deviceCommand);
						commandStatus = Constants.FAILURE;
					}
					meterResponse.setMessage(commandStatus);
					meterResponse.setTrackingId(trackingId);
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
				}  catch (Exception e) {
					//Inserting log of api
					logDevicesCommand(deviceInfo, deviceInfo.getDevice_serial_number(),
							deviceCommand.getCommand(), Constants.FAILURE , meterResponse.getMessage()+":"+e.getMessage(),
							trackingId, new Date(System.currentTimeMillis()),1,
							mdasDatetime, deviceCommand);
					meterResponse.setMessage(Constants.FAILURE);
					meterResponse.setTrackingId(trackingId);
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
				}
			}	
			else
			{
				meterResponse.setMessage("Device is Busy or is not commissioned yet.");
				logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
						deviceCommand.getCommand(), Constants.FAILURE, "Device is not Present in record",
						trackingId, new Date(System.currentTimeMillis()),1,
						mdasDatetime, deviceCommand);
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
	
	public ResponseEntity<?> writeConnectDisconnectEntity(DeviceCommand deviceCommand)
	{
		Device device = deviceCommand.getDevice();
		LOG.info("Received "+  deviceCommand.getCommand() + " Commands to perform on meter: " + device.getPlainText());
		Devices devices = null;
		ResponseEntity<?> response = null;
		DeviceCommandLog deviceCommandLog = null;
		DevicesInfo deviceInfo = null;
		MeterResponse meterResponse = new MeterResponse();
		meterResponse.setStatus(200);
		long currentTime = System.currentTimeMillis();
		//boolean status = Constants.FLAG_FAILURE;
		String commandStatus = Constants.IN_PROGRESS;
		int retry = Integer.parseInt(meterCommands.getRetry());
		String trackingId = StringUtils.isEmpty(deviceCommand.getTrackingId()) ? String.valueOf(System.nanoTime())
				: deviceCommand.getTrackingId();
		
		if(!StringUtils.isEmpty(deviceCommand.getRequestFrom())) {
			trackingId = !StringUtils.isEmpty(deviceCommand.getTrackingId()) ? deviceCommand.getTrackingId() : trackingId;
			deviceCommand.setTrackingId("");
		}
		
		if (device.getPlainText() != null) {
			deviceInfo = devicesInfoService.getDevicesInfo(device.getPlainText());
			
			if(deviceInfo == null) {
				meterResponse.setMessage("Device Not Found");
				meterResponse.setStatus(404);
				return new ResponseEntity<>(meterResponse, HttpStatus.OK);
			}
			if(!deviceInfo.getCommissioning_status().equalsIgnoreCase(Constants.Status.UP)) {
					
				meterResponse.setMessage("Commissioning status is"+deviceInfo.getCommissioning_status());
				meterResponse.setStatus(422);
				return new ResponseEntity<>(meterResponse, HttpStatus.NOT_FOUND);
			}
			
			if(!Constants.Status.BUSY.equalsIgnoreCase(deviceInfo.getStatus()))
			{
				deviceInfo.setStatus(Constants.Status.BUSY);
				devicesInfoService.updateDevicesRow(deviceInfo);
				
				Date mdasDatetime = new Date(System.currentTimeMillis());
				if(!StringUtils.isEmpty(deviceCommand.getTrackingId())) {
					deviceCommandLog = devicesCommandsLogService.findByTrackingId(
							deviceCommand.getDevice().getPlainText(), deviceCommand.getCommand(),
							deviceCommand.getTrackingId());
					deviceCommand.setBatchId(deviceCommandLog.getBatch_id());
					deviceCommand.setUserId(deviceCommandLog.getUser_id());
					deviceCommand.setSource(deviceCommandLog.getSource());
					mdasDatetime = deviceCommandLog.getMdas_datetime() != null ? deviceCommandLog.getMdas_datetime():
									mdasDatetime;
				}
					try {
						if((commonUtils.after()||commonUtils.before())&&Constants.CommandName.Disconnect.equalsIgnoreCase(deviceCommand.getCommand())) {
							deviceInfo.setStatus(Constants.Status.CONNECTED);
							devicesInfoService.updateDevicesRow(deviceInfo);
							meterResponse.setMessage("Device Will not Disconnect due to Happy hour");
							meterResponse.setStatus(200);
							meterResponse.setTrackingId("");
							return new ResponseEntity<>(meterResponse, HttpStatus.OK);
                           }
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				
				int attempts = (deviceCommandLog != null && deviceCommandLog.getTot_attempts() != null)  
								? deviceCommandLog.getTot_attempts() : 0;
				try {
					deviceInfo.setStatus(Constants.Status.BUSY);
					devicesInfoService.updateDevicesRow(deviceInfo);
					
					devices = commonUtils.setDevicesInfo(deviceInfo, deviceCommand, currentTime);
					
					LOG.info("Device info created & sending "+ deviceCommand.getCommand() +" command : " + device.getPlainText());
					response = commonUtils.getResponseEntity(devices, deviceCommand.getCommand());
					LOG.info("Data response received from meter:" + device.getPlainText(), response.getBody());
					
					if(response != null && response.getStatusCode().value() == 200) {
						 meterResponse = CommonUtils.getMapper().readValue(response.getBody().toString(),
								MeterResponse.class);
						if(meterResponse.getMessage().equalsIgnoreCase(Constants.SUCCESS)) {
							//status = Constants.FLAG_SUCCESS;
							commandStatus = Constants.SUCCESS;
						}
					}

					if(Constants.SUCCESS.equalsIgnoreCase(commandStatus)) {
						logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
								deviceCommand.getCommand(), Constants.SUCCESS, 
								(deviceCommandLog != null && deviceCommandLog.getReason() != null) ? deviceCommandLog.getReason()
										+" : "+meterResponse.getMessage() : meterResponse.getMessage(),
								trackingId, new Date(System.currentTimeMillis()), attempts +1 ,
								mdasDatetime, deviceCommand);
						
						if(ConfigCommands.CONNECT.commandName.equals(deviceCommand.getCommand())){
							deviceInfo.setLastconnectdatetime(new Date(System.currentTimeMillis()));
						}
						else if(ConfigCommands.DISCONNECT.commandName.equals(deviceCommand.getCommand())){
							deviceInfo.setLastdisconnectdatetime(new Date(System.currentTimeMillis()));
						}
						commandStatus = Constants.SUCCESS;
						
						changePreviousLogsDevicesCommand(deviceCommand, commandStatus, meterResponse.getMessage(),
								new Date(System.currentTimeMillis()));
						
						deviceInfo.setStatus(Constants.Status.CONNECTED);
						devicesInfoService.updateDevicesRow(deviceInfo);
						
						DeviceCommand devCmd = new DeviceCommand();
						devCmd.setDevice(deviceCommand.getDevice());
						devCmd.setCommand(ConfigCommands.INSTANTANEOUS_READ.commandName);
						devCmd.setUserId(deviceCommand.getUserId());
						devCmd.setBatchId(deviceCommand.getBatchId());
						devCmd.setSource(deviceCommand.getSource());
						getResponseReadEntity(devCmd);
					}
					else
					{
						if(attempts >= retry) {
							logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
									deviceCommand.getCommand(), Constants.FAILURE, 
									(deviceCommandLog != null && deviceCommandLog.getReason() != null) ? deviceCommandLog.getReason()
											+" : "+meterResponse.getMessage() : meterResponse.getMessage(),
									trackingId, new Date(System.currentTimeMillis()), attempts,
									mdasDatetime, deviceCommand);
							
							commandStatus = Constants.FAILURE;
							
						}
						else {
							logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
									deviceCommand.getCommand(), Constants.IN_PROGRESS, 
									(deviceCommandLog != null && deviceCommandLog.getReason() != null) ? deviceCommandLog.getReason()
											+" : "+meterResponse.getMessage() : meterResponse.getMessage(),
									trackingId, null, attempts +1 ,
									mdasDatetime, deviceCommand);
							commandStatus = Constants.IN_PROGRESS;
						}
					}
					
					meterResponse.setMessage(commandStatus);
					meterResponse.setTrackingId(trackingId);
					
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
				}  catch (Exception e) {
					if(attempts >= retry) {
						logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
								deviceCommand.getCommand(), Constants.FAILURE, 
								(deviceCommandLog != null && deviceCommandLog.getReason() != null) ? deviceCommandLog.getReason()
										+" : "+meterResponse.getMessage() : meterResponse.getMessage(),
								trackingId, new Date(System.currentTimeMillis()), attempts,
								mdasDatetime, deviceCommand);
						
						commandStatus = Constants.FAILURE;
					}
					else {
						logDevicesCommand(deviceInfo, deviceCommand.getDevice().getPlainText(),
								deviceCommand.getCommand(), Constants.IN_PROGRESS, 
								(deviceCommandLog != null && deviceCommandLog.getReason() != null) ? deviceCommandLog.getReason()
										+" : "+meterResponse.getMessage() : meterResponse.getMessage(),
								trackingId, null, attempts +1 ,
								mdasDatetime, deviceCommand);
						
						
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
				meterResponse.setMessage(Constants.IN_PROGRESS);
				meterResponse.setStatus(500);
				meterResponse.setTrackingId(trackingId);
				
				if(!StringUtils.isEmpty(deviceCommand.getRequestFrom())) {
					
					logDevicesCommand(deviceInfo, deviceInfo.getDevice_serial_number(),
							deviceCommand.getCommand(), Constants.IN_PROGRESS, 
							"", trackingId , null,0, new Date(System.currentTimeMillis()), deviceCommand);
					
				}
				
				if(deviceInfo.getLastcommunicationdatetime()== null 
						|| commonUtils.dateDiffInMins(deviceInfo.getLastcommunicationdatetime()) > 5){
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
				}
				return new ResponseEntity<>(meterResponse, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(meterResponse, HttpStatus.OK);
	
	}
	
	/**
	 * Used to get the Configs Response entity.
	 * @param devices
	 * @param cmdName
	 * @return
	 */
	public ResponseEntity<?> getConfigsResponseEntity(MeterConfiguration meterConfiguration ,String cmdName)
	{
		ResponseEntity<?> response = null;
		try {
			if(ConfigCommands.PAYMENT_MODE.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getEnablePaymentMode(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.METERING_MODE.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getEnableMeteringMode(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.LAST_TOKEN_RECHARGE_AMOUNT.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getLastTokenRechargeAmount(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.LAST_TOKEN_RECHARGE_TIME.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getLastTokenRechargeTime(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.TOTAL_AMOUNT_AT_LAST_RECHARGE.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getTotalAmountAtLastRecharge(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.CURRENT_BALANCE_AMOUNT.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getCurrentBalanceAmount(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.CURRENT_BALANCE_TIME.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getCurrentBalanceTime(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.LOAD_LIMIT.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getLoadLimitKw(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.ENABLE_DISABLE_DISCONNECT_CONTROL.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getEnableDisableDisControl(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.RTC_ClOCK.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getRtcClock(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.ACTIVITY_CALENDER.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getActivityCalendar(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.DEMAND_INTEGRATION_PERIOD.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getDemandIntegrationPeriod(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.PROFILE_CAPTURE_PERIOD.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getProfileCapturePeriod(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.COVER_OPEN.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getCoverOpen(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.MD_RESET.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getMdReset(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.INSTANT_IP_PUSH.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getInstantIPPush(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.ALERT_IP_PUSH.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getAlertIPPush(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.ACTIVITY_SCHEDULE_PUSH.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getActivitySchedulePush(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
			else if(ConfigCommands.BILLING_DATES.commandName.equals(cmdName)){
				
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getBillingDates(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(meterConfiguration, MediaType.APPLICATION_JSON), String.class);
			}
		} catch (RestClientException e) {
			throw e;
		}
		return response;
	}

	/**
	 * Used to read full Meter Configs
	 * @param fullMeterConfiguration
	 * @param cmdName
	 * @return
	 */
	public ResponseEntity<?> getFullConfigsResponseEntity(FullMeterConfiguration fullMeterConfiguration ,String cmdName)
	{
		ResponseEntity<?> response = null;
		try {
			response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getFullConfigs(),
					HttpMethod.PUT, CommonUtils.getHttpEntity(fullMeterConfiguration, MediaType.APPLICATION_JSON), String.class);
		} catch (RestClientException e) {
			throw e;
		}
		return response;
	}
	
	/**
	 * Used to save history data.
	 * @param response
	 * @param deviceInfo
	 * @param cmdName
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public boolean saveHistoryData( DevicesInfo deviceInfo, String cmdName,
			MeterResponse meterResponse) throws Exception
	{
		boolean flag = false;
		try {
			if(ConfigCommands.INSTANTANEOUS_READ.commandName.equals(cmdName)){
				flag = historicalDataService.insertInstantaneousSinglePhaseData( deviceInfo, meterResponse);
			}
			else if(ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(cmdName)){
				flag = historicalDataService.insertDailyLoadProfileData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(cmdName)){
				flag = historicalDataService.insertDeltaLoadProfileData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.BILLING_DATA.commandName.equals(cmdName)){
				flag = historicalDataService.insertBillingData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.POWER_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertPowerRelatedEventsData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.VOLTAGE_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertVoltageRelatedEventsData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.TRANSACTION_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertTransactionRelatedEventsData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.CURRENT_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertCurrentRelatedEventsData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.OTHER_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertOthersRelatedEventsData( deviceInfo, meterResponse);
			}
			else if(ConfigCommands.NON_ROLLOVER_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertNonRolloverRelatedEventsData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.CONTROL_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertControlRelatedEventsData( deviceInfo, meterResponse);
			}
			else if(ConfigCommands.NAME_PLATES.commandName.equals(cmdName)){
				flag = namePlatesDataService.insertNamePlateData(deviceInfo, meterResponse);
			}
			return flag;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	
	public String saveFullHistoryData(Devices devices, DevicesInfo deviceInfo, List<MeterResponse> meterResponses, 
			 Map<String, String> dataSaveResults, Map<String, Integer> dataRetryCount, FullDataMeterResponse responseList) throws Exception
	{
		int fullDataStatusReadCount = 0;
		String fullDataStatus = Constants.IN_PROGRESS;
		List<FullDataModel> fullDataModels = responseList.getResponseList();
		
		for (MeterResponse meterResponse : meterResponses) {
			 String cmdName = meterResponse.getObisCmd();
			 
			 if(Constants.SUCCESS.equalsIgnoreCase(meterResponse.getMessage())) {
				saveHistoryData(deviceInfo, cmdName, meterResponse);
			 }

			 if(ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(cmdName)
						&& devices.getDlpDayDiff() == 0) {
				 meterResponse.setMessage(Constants.SUCCESS);
			 }
				 
			 int count = dataRetryCount.containsKey(cmdName) ? dataRetryCount.get(cmdName) +1 : 1;
			 dataRetryCount.put(cmdName, count);
			 dataSaveResults.put(cmdName, meterResponse.getMessage());
			 
			 FullDataModel fullDataModel = new FullDataModel();
			 fullDataModel.setCmdName(cmdName);
			 fullDataModel.setMessage(dataSaveResults.get(cmdName));
			 fullDataModel.setRetry(String.valueOf(count));
			 fullDataModels.add(fullDataModel);

			 if(dataSaveResults.get(cmdName).equalsIgnoreCase(Constants.SUCCESS) ||
					 dataSaveResults.get(cmdName).equalsIgnoreCase(Constants.FAILURE_NA)) {
				 fullDataStatusReadCount++;
			 }
		}
		
		if(fullDataStatusReadCount == meterResponses.size()) {
			fullDataStatus = Constants.SUCCESS;
		}
		responseList.setResponseList(fullDataModels);
		return fullDataStatus;
	}
	
	public String saveFullPrepayHistoryData(Devices devices, DevicesInfo deviceInfo, List<MeterResponse> meterResponses, 
			 Map<String, String> dataSaveResults, Map<String, Integer> dataRetryCount, FullDataMeterResponse responseList,DeviceCommand deviceCommand) throws Exception
	{
		String fullDataStatus = Constants.IN_PROGRESS;
		List<FullDataModel> fullDataModels = responseList.getResponseList();
		AtomicInteger totSuccessResult = new AtomicInteger(0);
		if(meterResponses != null && meterResponses.size() > 0) {
			historicalDataService.insertPrepayData( deviceInfo, meterResponses, dataSaveResults, dataRetryCount, totSuccessResult, deviceCommand);
			if(totSuccessResult.get() == devices.getPrepayObisCodeList().size()) {
				fullDataStatus = Constants.SUCCESS;
			}
			else
				fullDataStatus = Constants.IN_PROGRESS;
		}
		responseList.setResponseList(fullDataModels);
		return fullDataStatus;
	}
	
	/**
	 * This is used to create initial result map to store in 
	 * @param meterResponses
	 * @param dataSaveResults
	 * @param dataRetryCount
	 * @throws Exception
	 */
	public void createCmdList(List<String> cmdList,  Map<String, String> dataSaveResults,
			Map<String, Integer> dataRetryCount)
	{
		for (String cmdName : cmdList) {
			 dataSaveResults.put(cmdName, Constants.IN_PROGRESS);
			 dataRetryCount.put(cmdName, 0);
		}
	}
	
	/**
	 * Used to save logs
	 * @param deviceSNO
	 * @param cmdName
	 * @param status
	 * @return
	 */
	public void logDevicesCommand(DevicesInfo devicesInfo, String deviceSNO, String cmdName, String status, String reason,
			String trackingId, Date commandCompletionDate, int attempts, Date mdasDatetime, DeviceCommand deviceCommand)
	{
		if(StringUtils.isEmpty(trackingId)) {
			trackingId = String.valueOf(System.nanoTime());
		}
		DeviceCommandLog deviceCommandLogs = new DeviceCommandLog(deviceSNO, trackingId,
				cmdName, status, mdasDatetime,
				devicesInfo.getDcu_serial_number(), devicesInfo.getDt_name(),
				devicesInfo.getFeeder_name(), devicesInfo.getOwner_name(),
				devicesInfo.getSubdevision_name(), devicesInfo.getSubstation_name(),
				reason ,commandCompletionDate, attempts);
		deviceCommandLogs.setBatch_id(deviceCommand.getBatchId());
		deviceCommandLogs.setSource(deviceCommand.getSource());
		deviceCommandLogs.setUser_id(deviceCommand.getUserId());
		
		if(ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(deviceCommand.getCommand())) {
				deviceCommandLogs.setDatewise_range(deviceCommand.getDailyRangeDate());	
		}else if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(deviceCommand.getCommand())) {
				deviceCommandLogs.setDatewise_range(deviceCommand.getDeltaRangeDate());
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
	public void changePreviousLogsDevicesCommand(DeviceCommand deviceCommand, String status, String reason,
			 Date commandCompletionDate)
	{
		List<DeviceCommandLog>	deviceCommandLogs = null;
		
		if (deviceCommand.getDailyRangeDate() != null || deviceCommand.getDeltaRangeDate() != null) {
			deviceCommandLogs = devicesCommandsLogService.getDeviceCommandLogRangeWise(deviceCommand);
		}else {
			deviceCommandLogs = devicesCommandsLogService.getDeviceCommandLogCurrentDayWise(deviceCommand);
		}
		
		for (DeviceCommandLog deviceCommandLog : deviceCommandLogs) {
			
			deviceCommandLog.setStatus(status);
			deviceCommandLog.setCommand_completion_datetime(commandCompletionDate);
			deviceCommandLog.setReason(deviceCommandLog.getReason()+ " : "+reason);
			deviceCommandLog.setTot_attempts(deviceCommandLog.getTot_attempts() + 1);
			
			devCommandLogRepository.save(deviceCommandLog);
		}
		
		LOG.info("Command Log Status is changed for previous commands....");
	}
	
	
}
