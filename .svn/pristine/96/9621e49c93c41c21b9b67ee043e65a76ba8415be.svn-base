package com.global.meter.v3.inventive.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.MeterTypeInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.Devices;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.request.model.Device;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.common.enums.ConfigsCommands;
import com.global.meter.v3.common.enums.OnDemandCommands;
import com.global.meter.v3.inventive.business.model.DevicesCommandLog;
import com.global.meter.v3.inventive.business.model.MeterResponseRawData;
import com.global.meter.v3.inventive.business.model.SingleConnectionMeterCommandLog;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogs;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;
import com.global.meter.v3.inventive.models.SingleConnectionReq;
import com.global.meter.v3.inventive.models.SingleConnectionRes;
import com.global.meter.v3.inventive.repository.ManufacturerSpecificObisRepository;
import com.global.meter.v3.inventive.repository.MeterResponseRawDataRepository;
import com.global.meter.v3.inventive.repository.SingleConnectionMeterCommandLogRepository;
import com.global.meter.v3.inventive.validator.SingleConnectionCommandValidator;

@Service
public class SingleConnectionCommandExecutionService {

	private static final Logger LOG = LoggerFactory.getLogger(SingleConnectionCommandExecutionService.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	CommonUtils commonUtils;

	@Autowired
	DevicesInfoService devicesInfoService;

	@Autowired
	SingleConnectionCommandLogsService singleConnectionCommandLogsService;

	@Autowired
	MetersCommandsConfiguration meterCommands;

	@Autowired
	SingleConnectionMeterCommandLogRepository singleConnectionMeterCommandLogRepository;

	@Autowired
	SingleConnectionCommandValidator commandValidator;

	@Autowired
	MeterResponseRawDataRepository rawDataRepository;

	@Autowired
	SingleConnectionPerformService singleConnectionPerformService;
	
	@Autowired
	ManufacturerSpecificObisRepository manufacturerSpecificObisRepository;
	
	@Autowired
	SingleConnectionCommandService singleConnectionCommandService;
	
	@SuppressWarnings("unused")
	public CommonResponse runSingleConnectionCommandLogs(SingleConnectionCommandReq req, CommonResponse response) {
		ResponseEntity<?> deviceResponse = null;
		Optional<MeterTypeInfo> meterTypeInfo = Optional.empty();
		SingleConnectionRes meterRes = new SingleConnectionRes();
		SingleConnectionReq singleConnectionReq = new SingleConnectionReq();
		int retry = Integer.parseInt(meterCommands.getRetry());
		Devices devices = new Devices();
		String finalStatus = "";
		Date commandCompletionDate = null;
		int attempts = 0;
		int cmdCount = 0;
		int cmdFullDataCount = 0;
		Map<String, String> dailyRangeDate = null;
		Map<String, String> deltaRangeDate = null;
		Set<String> statusSet = new HashSet<>();
		DevicesInfo deviceInfo = null;
		Set<String> fullDataCmdSet = new HashSet<>();
		List<SingleConnectionCommandLogs> singleConnectionCommandLogs = new ArrayList<SingleConnectionCommandLogs>();
		List<MeterResponse> meterResponseList = new ArrayList<MeterResponse>();
		Set<String> responseStatusSet = new HashSet<>();
		Set<String> fullDataCmdTrackingList = new HashSet<>();
		HashMap<String, MeterResponseRawData> meterRawResponse = null;
		List<MeterResponseRawData> meterRawDataList = null;
		List<String> cfgReadCmdSet = new ArrayList<String>();
		Map<String,String> cfgWriteCmdSet = new HashMap<String, String>();
		
		LOG.info(req.getDevice().getPlainText() + ":  meter command execution service called for: " + req.getCommand());

		try {

			Optional<SingleConnectionMeterCommandLog> meterInfo = null;

				meterInfo = singleConnectionMeterCommandLogRepository.getMeterCommandLog(
						new DateConverter().convertStringToDate(CommonUtils.getCurrentDate()),
						req.getDevice().getPlainText());

			if (!meterInfo.isPresent()) {
				LOG.info(req.getDevice().getPlainText() + ": Command not available date for execution.");
				response.setMessage("Command not available for execution");
				return response;
			} else {
				
				SingleConnectionMeterCommandLog commandLog = meterInfo.get();

				if (commandLog != null) {
				
				deviceInfo = devicesInfoService.getDevicesInfo(commandLog.getDevice_serial_number());
			
				if (deviceInfo == null) {
					response.setMessage("Device Not Found");
					response.setCode(404);
					return response;
				}

				Map<String,String> cmdRes = new LinkedHashMap<String, String>();
				Set<String> processedCommands = new HashSet<>();
				if (!Constants.Status.BUSY.equalsIgnoreCase(deviceInfo.getStatus())) {
					
					commandLog.setBackend_status(Constants.BackendStatus.N);
					singleConnectionMeterCommandLogRepository.save(commandLog);
					deviceInfo.setStatus(Constants.Status.BUSY);
					devicesInfoService.updateDevicesRow(deviceInfo);
					singleConnectionReq.setWrite(Constants.FALSE);
					
					Set<String> uniqueCmdSet = new HashSet<>();
					meterRawResponse = new HashMap<String, MeterResponseRawData>();
					MeterResponseRawData rawDataReq = null;
					meterRawDataList = new ArrayList<MeterResponseRawData>();
					
					devices = commonUtils.setDevicesInfo(deviceInfo);
					
					for (DevicesCommandLog command : commandLog.getCommand_list()) {
						
						SingleConnectionCommandLogs newCommandLog = new SingleConnectionCommandLogs();
						rawDataReq = new MeterResponseRawData();

						if ((command.getStatus().equalsIgnoreCase(Constants.ADDED)
								|| command.getStatus().equalsIgnoreCase(Constants.IN_PROGRESS))
									&& !uniqueCmdSet.contains(command.getCommand_name())) {
								
								newCommandLog.setTrackingId(command.getTracking_id());
								newCommandLog.setCommandName(command.getCommand_name());
								newCommandLog.setExtBatchId(command.getExt_batch_id());
								
								//set Meter Response Raw data for single command
								if (!Constants.CommandName.FullData.equals(command.getCommand_name())) {
									
									rawDataReq.setTracking_id(command.getTracking_id());
									rawDataReq.setProfile_type(command.getCommand_name());
									rawDataReq.setDevice_serial_number(commandLog.getDevice_serial_number());
									rawDataReq.setMdas_datetime(command.getCommand_datetime());
									
									meterRawResponse.put(command.getCommand_name(), rawDataReq);
								}
								
							
								// set on demand command
								if (OnDemandCommands.contains(command.getCommand_name())) {

									if (command.getCommand_name().equalsIgnoreCase(Constants.CONNECT)
											|| command.getCommand_name().equalsIgnoreCase(Constants.DISCONNECT)) {

										newCommandLog.setCommandType(Constants.COMMAND_WRITE);
										singleConnectionReq.setWrite(Constants.TRUE);

									} else {

										
										newCommandLog =	commonUtils.setRangeDate(devices, deviceInfo, newCommandLog, System.currentTimeMillis(), command, command.getCommand_name());
										
									}
									uniqueCmdSet.add(command.getCommand_name());
								}
								// set on demand full data cmd
								else if(Constants.CommandName.FullData.equalsIgnoreCase(command.getCommand_name())) {
										for(Map.Entry<String, String> map : command.getCommands().entrySet()) {
											if(!uniqueCmdSet.contains(map.getKey()) && !fullDataCmdSet.contains(map.getKey())) {
												newCommandLog = new SingleConnectionCommandLogs();
												newCommandLog =	commonUtils.setRangeDate(devices, deviceInfo, newCommandLog, System.currentTimeMillis(), command, map.getKey());
												newCommandLog.setCommandName(map.getKey());
												newCommandLog.setCommandType(Constants.COMMAND_READ);
												newCommandLog.setTrackingId(command.getTracking_id());
												singleConnectionCommandLogs.add(newCommandLog);
												
												uniqueCmdSet.add(map.getKey());
												
												rawDataReq.setTracking_id(command.getTracking_id());
												rawDataReq.setProfile_type(command.getCommand_name());
												rawDataReq.setDevice_serial_number(commandLog.getDevice_serial_number());
												rawDataReq.setMdas_datetime(command.getCommand_datetime());
												
												
												meterRawResponse.put(map.getKey(), rawDataReq);
												
											}
												fullDataCmdSet.add(map.getKey());
										}
										uniqueCmdSet.add(command.getCommand_name());
								}
								// set config read and write
								else if (ConfigsCommands.contains(command.getCommand_name())) {

									if (command.getCommand_val() != null && !command.getCommand_val().isEmpty()) {
										newCommandLog.setCommandVal(command.getCommand_val());
										if (ConfigCommands.LOAD_LIMIT.commandName.equalsIgnoreCase(command.getCommand_name())) {
											String configVal = command.getCommand_val();
											Double loadLimit = CommonUtils.kWToWatts(Double.parseDouble(configVal));
											int limit = loadLimit.intValue();
											newCommandLog.setCommandVal(String.valueOf(limit));
										}
										singleConnectionReq.setWrite(Constants.TRUE);
										newCommandLog.setCommandType(Constants.CONFIG_WRITE);
									} else {
										newCommandLog.setCommandType(Constants.CONFIG_READ);
										uniqueCmdSet.add(command.getCommand_name());
									}
								}
								// set priority for connect and disconnect
								if (command != null && (command.getCommand_name().equals(Constants.CONNECT)
										|| command.getCommand_name().equals(Constants.DISCONNECT))) {
									commandValidator.setConnectDisconnectPriority(singleConnectionCommandLogs, command,
											newCommandLog);
								} else {
									if(!Constants.CommandName.FullData.equalsIgnoreCase(command.getCommand_name())) {
										singleConnectionCommandLogs.add(newCommandLog);
									}
								}
								
						}
							
					}

					singleConnectionReq.setDevicesInfo(devices);
					singleConnectionReq.setCommandList(singleConnectionCommandLogs);
					// GPRS service called
					if (!singleConnectionCommandLogs.isEmpty()) {
						LOG.info("Device info created & sending command on: {}", deviceInfo.getDevice_serial_number());
						
						String url = "";
						if (Constants.ModeComm.CELLULAR.equalsIgnoreCase(deviceInfo.getMode_of_comm())) {
							url = meterCommands.getAppAddress() + meterCommands.getSingleConnectionCommand();
						}
						else if(Constants.ModeComm.RF.equalsIgnoreCase(deviceInfo.getMode_of_comm())) {
							url = meterCommands.getAppAddress() + meterCommands.getSingleConnectionRFCommand();
						}
						
						if(url != "") {
							deviceResponse = restTemplate.exchange(url, HttpMethod.POST,
									CommonUtils.getHttpEntity(singleConnectionReq, MediaType.APPLICATION_JSON),
									String.class);
	
						}
						
						LOG.info("Data response received from meter: " + deviceInfo.getDevice_serial_number() +": "+ deviceResponse != null ? deviceResponse.getBody().toString() : "null");

						
					}
					
					// checking for new command in queue
					Optional<SingleConnectionMeterCommandLog> meterQueueInfo = singleConnectionMeterCommandLogRepository
							.checkNewCommand(new DateConverter().convertStringToDate(CommonUtils.getCurrentDate()),
									req.getDevice().getPlainText(), Constants.BackendStatus.Y);
					Set<String> newCmdSet = new HashSet<>();
					if (meterQueueInfo.isPresent()) {		
					
						commandLog = meterQueueInfo.get();

						
						for (DevicesCommandLog cmdLog : commandLog.getCommand_list()) {
							
							if(cmdLog.getStatus().equalsIgnoreCase(Constants.ADDED)
									&& cmdLog.getRetry() == 0) {
								
								newCmdSet.add(cmdLog.getCommand_name());
							}
						}
					}

					if (deviceResponse != null && deviceResponse.getStatusCode().value() == 200) {
						meterRes = CommonUtils.getMapper().readValue(deviceResponse.getBody().toString(),
								SingleConnectionRes.class);
						finalStatus = Constants.IN_PROGRESS;
						
						if (meterRes != null && meterRes.getCommandList() != null
								&& !meterRes.getCommandList().isEmpty()) {
							
							
							for (SingleConnectionCommandLogs mResponse : meterRes.getCommandList()) {

								Map<String, String> configsValues = new LinkedHashMap<String, String>();
								Map<String, String> configsStatus = new LinkedHashMap<String, String>();
								Map<String, Integer> configRetryCount = new LinkedHashMap<String, Integer>();
								Map<String, String> dataSaveResults = new LinkedHashMap<String, String>();
								Map<String, Integer> dataRetryCount = new LinkedHashMap<String, Integer>();
								cmdCount++;
								cmdRes.put(mResponse.getCommandName(), 
										(mResponse.getMeterResponse() != null && mResponse.getMeterResponse().getReason() != null ) ? mResponse.getMeterResponse().getReason() + " : " + meterRes.getMessage()
										: meterRes.getMessage());
								
								// update reports table when command success
								if (mResponse.getMeterResponse().getMessage() != null
										&& mResponse.getMeterResponse().getStatus() == 200 && mResponse
												.getMeterResponse().getMessage().equalsIgnoreCase(Constants.SUCCESS)) {

									if (OnDemandCommands.contains(mResponse.getCommandName())) {
										if (mResponse.getCommandName().equalsIgnoreCase(Constants.CONNECT)
												|| mResponse.getCommandName().equalsIgnoreCase(Constants.DISCONNECT)) {
											meterResponseList.add(mResponse.getMeterResponse());
										}
										else {
											//add  current relay status
											singleConnectionCommandLogsService.saveHistoryData(deviceInfo,
													mResponse.getCommandName(), mResponse.getMeterResponse());
											// set extra obis code
											try 
											{
												singleConnectionPerformService.setExtraObisCode(mResponse, deviceInfo);
											}catch(Exception e)
											{
												LOG.info("Issue in adding Extra ObisCode for meter: {}",deviceInfo.getDevice_serial_number());
											}
										}
									} else if (ConfigsCommands.BILLING_DATES.commandName.equalsIgnoreCase(mResponse.getCommandName())
											&& mResponse.getCommandVal() != null) {
										deviceInfo.setBilling_datetime(mResponse.getCommandVal() != null 
												? mResponse.getCommandVal() : deviceInfo.getBilling_datetime());
										cfgReadCmdSet.add(ConfigsCommands.BILLING_DATES.commandName);
									} else if (ConfigsCommands.PROFILE_CAPTURE_PERIOD.commandName.equalsIgnoreCase(
											mResponse.getCommandName()) && mResponse.getCommandVal() != null) {
										deviceInfo.setProfile_capture_period(mResponse.getCommandVal() != null
												? Integer.parseInt(mResponse.getCommandVal())
												: deviceInfo.getProfile_capture_period());
										cfgReadCmdSet.add(ConfigsCommands.PROFILE_CAPTURE_PERIOD.commandName);
									}
									else if (ConfigsCommands.contains(mResponse.getCommandName())
											&& mResponse.getCommandVal() != null) {
										
										if (Constants.CONFIG_WRITE.equals(mResponse.getCommandType()) 
												&& Constants.SUCCESS.equalsIgnoreCase(mResponse.getMeterResponse().getMessage()) 
													&& ConfigCommands.PAYMENT_MODE.commandName.equalsIgnoreCase(mResponse.getCommandName())) {
													
													deviceInfo.setPayment_mode(Integer.parseInt(mResponse.getCommandVal()));
													deviceInfo.setDev_mode(mResponse.getCommandVal().equalsIgnoreCase("1") ? 
															Constants.DevMode.PREPAID : Constants.DevMode.POSTPAID);
													cfgReadCmdSet.add(ConfigsCommands.PAYMENT_MODE.commandName);
												}
									}
									else if (ConfigsCommands.contains(mResponse.getCommandName())
											&& mResponse.getMeterResponse() != null 
												&& Constants.CONFIG_READ.equals(mResponse.getCommandType())) {
											meterResponseList.add(mResponse.getMeterResponse());
									}
									if (mResponse.getMeterResponse().getObisCode() != null
											&& !mResponse.getMeterResponse().getObisCode().isEmpty()
											&& !OnDemandCommands.NAME_PLATES.commandName
													.equals(mResponse.getCommandName())) {
										commonUtils.setDatasetsReadDatetimeInDevicesInfo(devices, deviceInfo, mResponse);
									}
									if (mResponse.getMeterResponse().getObisCode() != null
											&& !mResponse.getMeterResponse().getObisCode().isEmpty()
											&& OnDemandCommands.NAME_PLATES.commandName
											.equals(mResponse.getCommandName()) && ( deviceInfo.getProfile_capture_period() == null 
											|| deviceInfo.getBilling_datetime() == null)) {
										cfgReadCmdSet.add(ConfigsCommands.PROFILE_CAPTURE_PERIOD.commandName);
										cfgReadCmdSet.add(ConfigsCommands.BILLING_DATES.commandName);
										cfgReadCmdSet.add(ConfigsCommands.PAYMENT_MODE.commandName);
									}
								}
								
								if (cmdCount == meterRes.getCommandList().size() && !meterResponseList.isEmpty()) {

									singleConnectionCommandLogsService.insertPrepayData(deviceInfo, meterResponseList,
											req);
								}
							
								// update command queue after execution
								
								for (DevicesCommandLog cmdLog : commandLog.getCommand_list()) {
									
									MeterResponse meterResponse = mResponse.getMeterResponse();
									
									String cmdWiseReason = null;
									if(Constants.CommandName.FullData.equals(cmdLog.getCommand_name())
											&& !cmdRes.containsKey(Constants.CommandName.FullData)){
										cmdRes.put(Constants.CommandName.FullData, 
												(mResponse.getMeterResponse() != null && mResponse.getMeterResponse().getReason() != null ) ? mResponse.getMeterResponse().getReason() + " : " + meterRes.getMessage()
												: meterRes.getMessage());
									}
									if (commandLog.getCmd_res() != null) {
									    cmdWiseReason = commandLog.getCmd_res().get(cmdLog.getCommand_name());
									}
									if (cmdWiseReason != null && !processedCommands.contains(cmdLog.getCommand_name())) {
								        cmdRes.replace(cmdLog.getCommand_name(), cmdWiseReason + ":" + cmdRes.get(cmdLog.getCommand_name()));
								        processedCommands.add(cmdLog.getCommand_name());
									}
									
								
								 	
									if(meterResponse != null) {
										if (meterResponse.getMessage() != null
												&& meterResponse.getStatus() == 200
												&& (Constants.SUCCESS.equalsIgnoreCase(meterResponse.getMessage())
													|| ((Constants.FAILURE_NA.equalsIgnoreCase(meterResponse.getMessage())
													|| Constants.IN_PROGRESS.equalsIgnoreCase(meterResponse.getMessage()))
												&& Constants.Status.PARTIAL_SUCCESS.equalsIgnoreCase(meterResponse.getReason())))
												&& mResponse.getCommandName().equalsIgnoreCase(cmdLog.getCommand_name())) {
											//installation process there due to installation dateTime stored in SCP command log.
											if(OnDemandCommands.NAME_PLATES.commandName.equalsIgnoreCase(cmdLog.getCommand_name()) 
													&& OnDemandCommands.NAME_PLATES.commandName.equals(mResponse.getCommandName()) 
													&& deviceInfo.getApproved_datetime() == null 
															&& (deviceInfo.getApproved_by() == null || deviceInfo.getApproved_by().isEmpty())) {
												if(Constants.Source.HES.equalsIgnoreCase(cmdLog.getSource())) {
													deviceInfo.setCommissioning_status(Constants.Status.UP);
													deviceInfo.setApproved_datetime(new Date(System.currentTimeMillis()));
													deviceInfo.setApproved_by(cmdLog.getUser_id());
												}
												else {
													deviceInfo.setCommissioning_status(Constants.Types.INSTALLED);
												}
												deviceInfo.setInstallation_datetime(cmdLog.getTemp_installation_datetime() != null ? cmdLog.getTemp_installation_datetime() : new Date(System.currentTimeMillis()));
												cfgWriteCmdSet.put(ConfigsCommands.RTC_ClOCK.commandName, dateConverter
														.convertDateToHesString(new Date(System.currentTimeMillis())));
											}
											
											if (singleConnectionPerformService.checkValidForUpdateLog(cmdLog)){
												attempts = cmdLog.getRetry();
												String reason = mResponse.getMeterResponse().getReason();
												
												finalStatus = Constants.SUCCESS;
												statusSet.add(finalStatus);
											
												cmdLog = updateCommandLog(mResponse, cmdLog, attempts, Constants.Status.PARTIAL_SUCCESS.equalsIgnoreCase(reason) ? reason : Constants.SUCCESS);
												cmdLog.setCommand_completion_datetime(new Date(System.currentTimeMillis()));
												
												MeterResponseRawData meterRawData =	 meterRawResponse.get(mResponse.getCommandName());
											
												if (meterRawData != null) {
													meterRawData.setData(CommonUtils.getMapper().writeValueAsString(meterResponse));
												
													meterRawDataList.add(meterRawData);
												}
											}
										}
										else if (meterResponse.getMessage() != null && meterResponse.getStatus() == 200
												&& (Constants.SUCCESS.equalsIgnoreCase(meterResponse.getMessage())
													|| ((Constants.FAILURE_NA.equalsIgnoreCase(meterResponse.getMessage())
													|| Constants.IN_PROGRESS.equalsIgnoreCase(meterResponse.getMessage()))
												&& Constants.Status.PARTIAL_SUCCESS.equalsIgnoreCase(meterResponse.getReason())))
												&& Constants.CommandName.FullData.equalsIgnoreCase(cmdLog.getCommand_name())) {
											
											if (singleConnectionPerformService.checkValidForUpdateLog(cmdLog)){
												cmdFullDataCount++;
												
												String reason = mResponse.getMeterResponse().getReason();
												finalStatus = updateFullDataCommandsLog(mResponse, cmdLog, Constants.Status.PARTIAL_SUCCESS.equalsIgnoreCase(reason) ? reason : Constants.SUCCESS);
										
												if (finalStatus.equals(Constants.SUCCESS)) {
													cmdLog.setCommand_completion_datetime(new Date(System.currentTimeMillis()));
												}
												cmdLog.setStatus(finalStatus);
												if(!fullDataCmdTrackingList.contains(cmdLog.getTracking_id())) {
													attempts = cmdLog.getRetry() + 1;
													cmdLog.setRetry(attempts);
													fullDataCmdTrackingList.add(cmdLog.getTracking_id());
												}
												
												statusSet.add(finalStatus);
												
											}
											
										}
										else {

											if(!cmdLog.getStatus().equalsIgnoreCase(Constants.SUCCESS)
													&& !cmdLog.getStatus().equalsIgnoreCase(Constants.FAILURE)
													&& !cmdLog.getStatus().equalsIgnoreCase(Constants.FAILURE_NA)
													&& cmdLog.getRetry() + 1 >= retry) {
												
												if(mResponse.getCommandName()
													.equalsIgnoreCase(cmdLog.getCommand_name())) {
													
													if (singleConnectionPerformService.checkValidForUpdateLog(cmdLog)){
														finalStatus = Constants.FAILURE;
														attempts = cmdLog.getRetry();
														statusSet.add(finalStatus);
													
														cmdLog = updateCommandLog(mResponse, cmdLog, attempts, finalStatus);
														cmdLog.setCommand_completion_datetime(new Date(System.currentTimeMillis()));
													}
												}else if(Constants.CommandName.FullData.equalsIgnoreCase(cmdLog.getCommand_name())
														&& singleConnectionPerformService.checkValidForUpdateLog(cmdLog)){
													
														finalStatus = updateFullDataCommandsLog(mResponse, cmdLog, Constants.FAILURE);
														attempts = cmdLog.getRetry() + 1;
														cmdLog.setRetry(attempts);
														cmdLog.setStatus(Constants.FAILURE);
														cmdLog.setCommand_completion_datetime(
																new Date(System.currentTimeMillis()));
														finalStatus = Constants.FAILURE;
														
														statusSet.add(finalStatus);
												}
												
											}else {
												
												if(mResponse.getCommandName().equalsIgnoreCase(cmdLog.getCommand_name())) {
														
													if (singleConnectionPerformService.checkValidForUpdateLog(cmdLog)){
														attempts = cmdLog.getRetry();
														String reason = mResponse.getMeterResponse().getReason();
														finalStatus = Constants.IN_PROGRESS;
														statusSet.add(finalStatus);
														
														cmdLog = updateCommandLog(mResponse, cmdLog, attempts, Constants.Status.PARTIAL_SUCCESS.equalsIgnoreCase(reason) ? reason : Constants.IN_PROGRESS);
													}
												}
												else if (Constants.CommandName.FullData.equalsIgnoreCase(cmdLog.getCommand_name())
														&& singleConnectionPerformService.checkValidForUpdateLog(cmdLog)) {
														
														cmdFullDataCount++;
														
														String reason = mResponse.getMeterResponse().getReason();

														
														finalStatus = updateFullDataCommandsLog(mResponse, cmdLog, Constants.Status.PARTIAL_SUCCESS.equalsIgnoreCase(reason) ? reason : Constants.IN_PROGRESS);
														
															cmdLog.setStatus(finalStatus);
												
														if(!fullDataCmdTrackingList.contains(cmdLog.getTracking_id())) {
															cmdLog.setRetry(cmdLog.getRetry() + 1);
															attempts = cmdLog.getRetry();
															fullDataCmdTrackingList.add(cmdLog.getTracking_id());
														}
														statusSet.add(finalStatus);
														
														
													
												}
											}
						
										}
									
									if (ConfigsCommands.contains(mResponse.getCommandName())
											&& mResponse.getCommandVal() == null) {
										dataSaveResults.put(mResponse.getCommandName(), finalStatus);
										dataRetryCount.put(mResponse.getCommandName(), attempts);
									}
									if (ConfigsCommands.contains(mResponse.getCommandName())
											&& mResponse.getCommandVal() != null
											&& !mResponse.getCommandVal().isEmpty()) {
										configsValues.put(mResponse.getCommandName(),
												mResponse.getCommandVal() != null ? mResponse.getCommandVal()
														: cmdLog.getCommand_val());
										configsStatus.put(mResponse.getCommandName(),
												mResponse.getMeterResponse().getMessage() != null
														? mResponse.getMeterResponse().getMessage()
														: finalStatus);
										configRetryCount.put(mResponse.getCommandName(), attempts);
									}

									dailyRangeDate = cmdLog.getDaily_range_date();
									deltaRangeDate = cmdLog.getDelta_range_date();
									
									}
								}
								newCmdSet.remove(mResponse.getCommandName());	
								
							}

						}
					}
					if (statusSet.contains(Constants.IN_PROGRESS)) {
						commandLog.setStatus(Constants.IN_PROGRESS);
					} 
					else if ((statusSet.contains(Constants.SUCCESS) 
							|| statusSet.contains(Constants.Status.PARTIAL_SUCCESS)) 
							&& !statusSet.contains(Constants.FAILURE)) {
						commandLog.setStatus(Constants.SUCCESS);
					}
					else {
						commandLog.setStatus(Constants.FAILURE);
					}
					commandLog.setBackend_status(Constants.BackendStatus.N);
					commandLog.setTot_attempts(commandLog.getTot_attempts() + 1);
					commandLog.setReason(
							commandLog.getReason() != null ? commandLog.getReason() + " : " + meterRes.getMessage()
									: meterRes.getMessage());
					commandLog.setCmd_res(cmdRes);
					singleConnectionMeterCommandLogRepository.save(commandLog);
					deviceInfo.setStatus(Constants.Status.CONNECTED);
					devicesInfoService.updateDevicesRow(deviceInfo);
					rawDataRepository.saveAll(meterRawDataList);
					// if new command in queue then send command to GPRS 
					if(!newCmdSet.isEmpty()) {
						runSingleConnectionCommandLogs(req, response);
					}
					//autotrigger config read while config write success
					if(!cfgReadCmdSet.isEmpty() || !cfgWriteCmdSet.isEmpty()) {
						SingleConnectionCommandReq request=new SingleConnectionCommandReq();
						Device device=new Device();
						device.setPlainText(deviceInfo.getDevice_serial_number());
						request.setSource(req.getSource());
						request.setReqFrom(req.getReqFrom());
						request.setUserId(req.getUserId());
						request.setPrepayObisCodeList(cfgReadCmdSet);
						request.setConfigVals(cfgWriteCmdSet);
						request.setDevice(device);
						singleConnectionCommandService.addSingleConnectionCommandLogs(request);
					}
					LOG.info("Meter command logs Info Successfully Stored in DB.");
					response.setMessage(meterRes.getMessage());
					response.setCode(200);

				} else {

						if (deviceInfo.getLastcommunicationdatetime() == null
								|| commonUtils.dateDiffInMins(deviceInfo.getLastcommunicationdatetime()) > 5) {
							deviceInfo.setStatus(Constants.Status.CONNECTED);
							devicesInfoService.updateDevicesRow(deviceInfo);
						}
						response.setMessage(Constants.BUSY);
						response.setCode(500);
						response.setBatchId(req.getBatchId());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error in inserting data for device : " + req.getDevice().getPlainText() + " due to : "
					+ e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
			response.setBatchId(req.getBatchId());
			deviceInfo.setStatus(Constants.Status.CONNECTED);
			devicesInfoService.updateDevicesRow(deviceInfo);
		}
		return response;
	}
	
	
	private String updateFullDataCommandsLog(SingleConnectionCommandLogs mResponse, DevicesCommandLog commandLog, String finalStatus){
	
		String fullDataStatus = Constants.SUCCESS;
		if (commandLog.getCommands() != null && commandLog.getCommands().size() > 0) {
			
		 	String currentValue = commandLog.getCommands().get(mResponse.getCommandName());

		 	if(currentValue != null) {
		 			
		 		if (Constants.Status.PARTIAL_SUCCESS.equalsIgnoreCase(finalStatus)) {
		 				commandLog.getCommands().replace(mResponse.getCommandName(), Constants.FAILURE_NA);
				}
		 		else if (mResponse.getCommandName().equalsIgnoreCase(Constants.BILLING_DATA) 
		 				&& commandLog.getCommands().get(Constants.BILLING_DATA).equalsIgnoreCase(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH)) {
					commandLog.getCommands().replace(mResponse.getCommandName(), Constants.BILL_ALREADY_EXIST_CUURENT_MONTH);
				}
		 		else {
					commandLog.getCommands().replace(mResponse.getCommandName(), finalStatus);
				}
		 			
	 			if (commandLog.getCommands().values().contains(Constants.IN_PROGRESS) || commandLog.getCommands().values().contains(Constants.ADDED) ) {
	 				fullDataStatus = Constants.IN_PROGRESS;
	 			}
		 	}
		   
		}
		return fullDataStatus;
	}
	
	private DevicesCommandLog updateCommandLog(SingleConnectionCommandLogs mResponse, DevicesCommandLog cmdLog, int retry, String status) {
		
		cmdLog.setRetry(retry + 1);
		cmdLog.setStatus(Constants.Status.PARTIAL_SUCCESS.equalsIgnoreCase(status) ? Constants.FAILURE_NA : status);
		
		return cmdLog;
	}
	
}