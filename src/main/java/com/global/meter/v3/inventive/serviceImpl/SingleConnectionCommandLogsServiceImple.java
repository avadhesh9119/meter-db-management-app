package com.global.meter.v3.inventive.serviceImpl;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.MeterDBAppStarter;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.ManufacturerSpecificOBISCodeResponse;
import com.global.meter.inventive.models.MeterDataRes;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.Constants.CreateBatch;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.common.enums.MasterObisCode;
import com.global.meter.v3.common.enums.ObisProfileName;
import com.global.meter.v3.common.enums.OnDemandCommands;
import com.global.meter.v3.inventive.business.model.DevicesCommandLog;
import com.global.meter.v3.inventive.business.model.ManufacturerSpecificObis;
import com.global.meter.v3.inventive.business.model.SingleConnectionMeterCommandLog;
import com.global.meter.v3.inventive.models.CommandStatusRes;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogResponse;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogsReq;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;
import com.global.meter.v3.inventive.repository.ManufacturerSpecificObisRepository;
import com.global.meter.v3.inventive.repository.SingleConnectionMeterCommandLogRepository;
import com.global.meter.v3.inventive.service.SingleConnectionCommandExecutionService;
import com.global.meter.v3.inventive.service.SingleConnectionCommandLogService;
import com.global.meter.v3.inventive.service.SingleConnectionCommandLogsService;
import com.global.meter.v3.inventive.service.SingleConnectionCommandService;
import com.global.meter.v3.inventive.validator.SingleConnectionCommandValidator;

@Service
public class SingleConnectionCommandLogsServiceImple extends DefaultHandler implements SingleConnectionCommandService {
	private static final Logger LOG = LoggerFactory.getLogger(SingleConnectionCommandLogsServiceImple.class);
	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	CommonUtils commonUtils;

	@Autowired
	DevicesInfoService devicesInfoService;

	@Autowired
	MetersCommandsConfiguration meteConfiguration;

	@Autowired
	SingleConnectionCommandValidator commandValidator;

	@Autowired
	SingleConnectionCommandExecutionService commandExecutorService;

	@Autowired
	SingleConnectionMeterCommandLogRepository singleConnectionMeterCommandLogRepository;
	
	@Autowired
	DevicesInfoRepository devicesInfoRepository;
	
	@Autowired
	SingleConnectionCommandLogService meterCommandService;
	
	@Autowired
	ManufacturerSpecificObisRepository manufacturerSpecificObisRepository;
	
	@Autowired
	SingleConnectionCommandLogsService singleConnectionCommandLogsService;
	
	@Autowired
	SingleConnectionCommandService singleConnectionCommandService;

	public CommonResponse addSingleConnectionCommandLogs(SingleConnectionCommandReq req) {
		SingleConnectionMeterCommandLog singleConnectionCommandLog = null;
		CommonResponse response = new CommonResponse();
		String batchId = req.getBatchId() != null ? req.getBatchId() : CommonUtils.createBatchId();
		String extBatchId = req.getExtBatchId() != null ? req.getExtBatchId() : CreateBatch.EXT_BATCH + String.valueOf(System.nanoTime());
		DevicesCommandLog preCommandLog = new DevicesCommandLog();
		List<String> commandList = null;
		DevicesInfo deviceInfo = null;
		List<CommandStatusRes> commandStatus = new ArrayList<CommandStatusRes>();
		CommandStatusRes commandStatusRes = null;
		int seqNo = 0;
		String commandDateTime = null;
		LOG.info("generating single connection command queue :--> ");

		try {
			commandDateTime = dateConverter.convertDateToHesString(new Date(System.currentTimeMillis()));
			commandList = req.getObisCodeList();

			List<String> readCommandList = req.getPrepayObisCodeList();
			Map<String, String> writeCommandList = req.getConfigVals();
			if (req.getDevice().getPlainText() != null && !req.getDevice().getPlainText().isEmpty()) {

				deviceInfo = devicesInfoService.getDevicesInfo(req.getDevice().getPlainText());

				if (deviceInfo == null) {
					response.setMessage("Device Not Found");
					response.setCode(404);
					return response;
				}
				
				if ((deviceInfo.getCommissioning_status() != null 
						&& (Constants.Status.UP.equalsIgnoreCase(deviceInfo.getCommissioning_status()) 
								|| (Constants.Types.INSTALLED.equalsIgnoreCase(deviceInfo.getCommissioning_status()) && deviceInfo.getApproved_datetime() == null)))		
						|| (OnDemandCommands.NAME_PLATES.commandName.equalsIgnoreCase(req.getCommand()) 
								&& (Constants.Status.DOWN.equalsIgnoreCase(deviceInfo.getCommissioning_status()) 
										|| (Constants.Status.INACTIVE.equalsIgnoreCase(deviceInfo.getCommissioning_status()) 
												&& deviceInfo.getInstallation_datetime() == null)))){

				Optional<SingleConnectionMeterCommandLog> meterInfo = singleConnectionMeterCommandLogRepository
						.getMeterCommandLog(new DateConverter().convertStringToDate(CommonUtils.getCurrentDate()),
								req.getDevice().getPlainText());
				
				singleConnectionCommandLog = new SingleConnectionMeterCommandLog();
				
				if (!meterInfo.isPresent()) {

					List<DevicesCommandLog> commandLogList = new LinkedList<DevicesCommandLog>();
					
					
					singleConnectionCommandLog = meterCommandService.logConnectionCommand(deviceInfo, req, batchId);
					
					// add connect dependent command
					if (req.getObisCodeList().contains(Constants.CONNECT)) {
						meterCommandService.addDependentCommands(meteConfiguration.getDependentConnectCommands(),
								commandList, readCommandList);
					}
					// add disconnect dependent command
					if (req.getObisCodeList().contains(Constants.DISCONNECT)) {
						meterCommandService.addDependentCommands(meteConfiguration.getDependentDisconnectCommands(),
								commandList, readCommandList);
					}
					// set on demand command
					if (commandList != null) {
						for (String command : commandList) {
							commandStatusRes = new CommandStatusRes();
							if (!ConfigCommands.contains(command.trim())) {
								response.setMessage(Constants.WRONG_COMMAND_TYPE);
								return response;
							}
							
							Date cmdDate = new Date(System.currentTimeMillis());
							String trackindId = String.valueOf(System.nanoTime());
							seqNo++;
							DevicesCommandLog commandLog = meterCommandService.logDeviceCommand(command, seqNo, Constants.ADDED ,
									cmdDate, trackindId, extBatchId, req,Constants.ON_DEMAND);
							
							
							commandStatusRes = meterCommandService.commandStatusRes(command, trackindId, extBatchId, Constants.ADDED, cmdDate);
							
							
							if (command.contains(Constants.CommandName.FullData)) {
								Map<String,String> map=new HashMap<>();
								for(String cmd:Constants.FullDataList.onDemandObisCodeList()) {
									if(cmd.equalsIgnoreCase(Constants.BILLING_DATA) && req.getReadWise() != null 
											&& req.getReadWise().equalsIgnoreCase("M") &&
									deviceInfo.getLastbillingreaddatetime() != null && 
									(CommonUtils.compareWithFirstDayOfMonth(deviceInfo.getLastbillingreaddatetime()) == 1)) {
										map.put(cmd, Constants.BILL_ALREADY_EXIST_CUURENT_MONTH);
									}else {
										map.put(cmd, Constants.ADDED);
									}
								}
								commandLog.setCommands(map);
							}
							//check already billing data for current month.
							if(command.equalsIgnoreCase(Constants.BILLING_DATA) && req.getReadWise() != null 
									&& req.getReadWise().equalsIgnoreCase("M") &&
							deviceInfo.getLastbillingreaddatetime() != null && 
							(CommonUtils.compareWithFirstDayOfMonth(deviceInfo.getLastbillingreaddatetime()) == 1)) {
							commandLog.setStatus(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH);
							}						
							
							if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equalsIgnoreCase(command)) {
								commandLog.setDaily_range_date(req.getDailyRangeDate());
							}
							if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equalsIgnoreCase(command)) {
								commandLog.setDelta_range_date(req.getDeltaRangeDate());
							}
							if (commandLogList.size() > 0 && command.equals(Constants.CONNECT)
									&& !commandLogList.get(0).getCommand_name().equals(Constants.CONNECT)) {

								preCommandLog = commandLogList.get(0);
								commandLogList.remove(0);
								preCommandLog.setSeqno(seqNo - 1);
								commandLog.setSeqno(1);
								commandLogList.add(0, commandLog);
								commandLogList.add(preCommandLog);

							} else if (command.equals(Constants.DISCONNECT)
									&& commandList.contains(Constants.CONNECT)) {
								commandLog.setStatus(Constants.DISCARD);
								commandLogList.add(commandLog);

							} else {
								commandLogList.add(commandLog);

							}
							commandStatus.add(commandStatusRes);
						}
					}

					// set read command
					if (readCommandList != null) {
						for (String command : readCommandList) {

							if (!ConfigCommands.contains(command.trim())) {
								response.setMessage(Constants.WRONG_COMMAND_TYPE);
								return response;
							}
							Date cmdDate = new Date(System.currentTimeMillis());
							String trackindId = String.valueOf(System.nanoTime());
							DevicesCommandLog commandLog = meterCommandService.logDeviceCommand(command, seqNo, Constants.ADDED ,
									cmdDate, trackindId, extBatchId, req,Constants.CONFIG_READ);
							
							commandLogList.add(commandLog);
							
							commandStatusRes = meterCommandService.commandStatusRes(command, trackindId, extBatchId, Constants.ADDED, cmdDate);
					
							commandStatus.add(commandStatusRes);

						}
					}
					// set write command
					if (writeCommandList != null) {
						
						
						Map<String, String> modifiedConfigVals = new HashMap<>();
						modifiedConfigVals.putAll(writeCommandList);
						
						String port = CommonUtils.getPushPort(deviceInfo);
						if(StringUtils.isEmpty(port)) {
							port = MeterDBAppStarter.pushPortsInfo.get(Constants.MeterMake.IHM);
						}
						
						if(modifiedConfigVals.containsKey(ConfigCommands.ALERT_IP_PUSH.commandName)) {
							
							String configVal = modifiedConfigVals.get(ConfigCommands.ALERT_IP_PUSH.commandName);
							configVal = configVal + ":" + port;
							modifiedConfigVals.put(ConfigCommands.ALERT_IP_PUSH.commandName, configVal);
						}
						
						if(modifiedConfigVals.containsKey(ConfigCommands.INSTANT_IP_PUSH.commandName)) {
							String configVal = modifiedConfigVals.get(ConfigCommands.INSTANT_IP_PUSH.commandName);
							configVal = configVal + ":" + port;
							modifiedConfigVals.put(ConfigCommands.INSTANT_IP_PUSH.commandName, configVal);
						}
						
						for (Entry<String, String> command : modifiedConfigVals.entrySet()) {

							if (!ConfigCommands.contains(command.getKey())) {
								response.setMessage(Constants.WRONG_COMMAND_TYPE);
								return response;
							}

							Date cmdDate = new Date(System.currentTimeMillis());
							String trackindId = String.valueOf(System.nanoTime());
							
							
							DevicesCommandLog commandLog = meterCommandService.logDeviceCommand(command.getKey().trim(), seqNo, Constants.ADDED ,
									cmdDate, trackindId, extBatchId, req,Constants.CONFIG_WRITE);
							commandLog.setCommand_val(command.getValue());
							commandLogList.add(commandLog);

							commandStatusRes = meterCommandService.commandStatusRes(command.getKey().trim(), trackindId, extBatchId, Constants.ADDED, cmdDate);
							
							commandStatus.add(commandStatusRes);


						}
					}
					singleConnectionCommandLog.setStatus(Constants.ADDED);
					singleConnectionCommandLog.setCommand_list(commandLogList);
					response.setCommandDateTime(commandDateTime);
					response.setCommandStatus(commandStatus);
				} else {

					singleConnectionCommandLog = meterInfo.get();
					List<DevicesCommandLog> commandLogList = new ArrayList<DevicesCommandLog>();
					
					commandLogList.addAll(singleConnectionCommandLog.getCommand_list());
					singleConnectionCommandLog.setBackend_status(Constants.BackendStatus.Y);
					singleConnectionCommandLog.setStatus(Constants.ADDED);

					// set read command
					if (readCommandList != null && readCommandList.size() != 0) {
						commandValidator.validatePrepayCommand(singleConnectionCommandLog, commandLogList,
								readCommandList, seqNo, response, batchId, req, extBatchId);
						if (response.getMessage() != null
								&& response.getMessage().contains(Constants.ALREADY_IN_QUEUE)) {
							return response;
						}
					}

					// set on demand command
					if (commandList != null && commandList.size() != 0) {
						commandValidator.validateOnDemandCommand(singleConnectionCommandLog, commandLogList,
								commandList, seqNo, response, batchId, req, extBatchId, deviceInfo);
						if (response.getMessage() != null
								&& response.getMessage().contains(Constants.ALREADY_IN_QUEUE)) {
							return response;
						}
					}

					// set write command
					if (writeCommandList != null && writeCommandList.size() != 0) {
						commandValidator.validateConfigWriteCommand(singleConnectionCommandLog, commandLogList,
								writeCommandList, seqNo, response, batchId, req, extBatchId, deviceInfo);
					}
					singleConnectionCommandLog.setCommand_list(commandLogList);
					
					for(DevicesCommandLog cmdLog : commandLogList) {
						
						if(!Constants.SUCCESS.equalsIgnoreCase(cmdLog.getStatus())
													&& !Constants.FAILURE.equalsIgnoreCase(cmdLog.getStatus())) {
							commandStatusRes = meterCommandService.commandStatusRes(cmdLog.getCommand_name(),
									cmdLog.getTracking_id(), extBatchId, cmdLog.getStatus(), cmdLog.getCommand_datetime());
							
							commandStatus.add(commandStatusRes);
						}
						
					}
					response.setCommandDateTime(commandDateTime);
					response.setCommandStatus(commandStatus);
				}
						}
				LOG.info("Device Status is : "+deviceInfo.getCommissioning_status());
				response.setMessage("Device Status is : "+deviceInfo.getCommissioning_status());
				response.setBatchId(batchId);
				response.setExtBatchId(extBatchId);
			}
			
			if (singleConnectionCommandLog != null) {
				singleConnectionMeterCommandLogRepository.save(singleConnectionCommandLog);
				LOG.info("command logs queue Successfully Stored in DB.");
				
				commandExecutorService.runSingleConnectionCommandLogs(req, response);
				response.setBatchId(batchId);
			}
			
		} catch (Exception e) {
			LOG.error("Issue in adding data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

		}
		return response;
	}
	

	@Override
	public CommonResponse getSingleConnectionCommandLogs(SingleConnectionCommandLogsReq req) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		String devType = "";
		ArrayList<SingleConnectionMeterCommandLog> meterInfoList = new ArrayList<SingleConnectionMeterCommandLog>();
		ArrayList<SingleConnectionCommandLogResponse> responsesList = new ArrayList<SingleConnectionCommandLogResponse>();
		String[] commands = req.getCommand().split(",");
		Set<String> commandSet=new HashSet<>();
		Optional<List<SingleConnectionMeterCommandLog>> singleConnectionInfo = null;
		LOG.info("Getting data from DB :");
		try {
			if(ExternalConstants.DeviceTypes.SINGLE_PHASE_DEV.contains(req.getDevType())) {
				devType = ExternalConstants.DeviceTypes.SINGLE_PHASE_DEV;
  			}
			else if(ExternalConstants.DeviceTypes.THREE_PHASE_DEV.contains(req.getDevType())){
				devType = ExternalConstants.DeviceTypes.THREE_PHASE_DEV;
			}
			else if(ExternalConstants.DeviceTypes.CT_METER.contains(req.getDevType())){
				devType = ExternalConstants.DeviceTypes.CT_METER;
			}
			
			if (req.getHier().getName()!= null && !req.getHier().getName().isEmpty() &&
					req.getHier().getName().equals("1")) {
				
			 singleConnectionInfo = singleConnectionMeterCommandLogRepository
					.getDataByUtility(new DateConverter().convertStringToDate(req.getFromDate()), 
							new DateConverter().convertStringToDate(req.getToDate()),req.getHier().getValue(),devType);
			}
			
			if (req.getHier().getName()!= null && !req.getHier().getName().isEmpty() &&
					req.getHier().getName().equals("7")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository
						.getDataByMeter(new DateConverter().convertStringToDate(req.getFromDate()), 
								new DateConverter().convertStringToDate(req.getToDate()),req.getHier().getValue(), devType);
			}
			
			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty() && 
					req.getHier().getName().equals("2")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository
						.getDataBySubdivision(new DateConverter().convertStringToDate(req.getFromDate()), 
								new DateConverter().convertStringToDate(req.getToDate()),req.getHier().getValue(), devType);
			}
			
			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty() && 
					req.getHier().getName().equals("3")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository
						.getDataBySubstation(new DateConverter().convertStringToDate(req.getFromDate()), 
								new DateConverter().convertStringToDate(req.getToDate()),req.getHier().getValue(), devType);
			}
			
			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty() && 
					req.getHier().getName().equals("4")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository
						.getDataByFeeder(new DateConverter().convertStringToDate(req.getFromDate()), 
								new DateConverter().convertStringToDate(req.getToDate()),req.getHier().getValue(), devType);
			}
			
			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty() && 
					req.getHier().getName().equals("5")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository
						.getDataByDt(new DateConverter().convertStringToDate(req.getFromDate()), 
								new DateConverter().convertStringToDate(req.getToDate()),req.getHier().getValue(), devType);
			}
			
					if(!singleConnectionInfo.isPresent()) {
						error.setErrorMessage(req.getBatchId() + " : " +ExternalConstants.Message.NOT_EXISTS);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					meterInfoList = (ArrayList<SingleConnectionMeterCommandLog>) singleConnectionInfo.get();
					
			for(String str : commands) {
				commandSet.add(str.trim());
			}
			for (SingleConnectionMeterCommandLog isData : meterInfoList) {
				 
				for (DevicesCommandLog commandLog : isData.getCommand_list()) {
					if (req.getMode().equalsIgnoreCase(commandLog.getMode())) {
 
						if (req.getCommand() != null && !req.getCommand().isEmpty()) {
 
							if (commandSet.contains(commandLog.getCommand_name())) {
								if (req.getStatus() != null && !req.getStatus().isEmpty()) {
									
									if (req.getStatus().equalsIgnoreCase(commandLog.getStatus())
											|| (req.getStatus().equalsIgnoreCase(Constants.SUCCESS) && commandLog.getStatus().equalsIgnoreCase(Constants.FAILURE_NA)))
										responsesList.add(setCommandResponse(isData, commandLog, req));
								}
								else {
									responsesList.add(setCommandResponse(isData, commandLog, req));
								}
							}
						}
						else {
							if (req.getStatus() != null && !req.getStatus().isEmpty()) {
								if (req.getStatus().equalsIgnoreCase(commandLog.getStatus())
										|| (req.getStatus().equalsIgnoreCase(Constants.SUCCESS) && commandLog.getStatus().equalsIgnoreCase(Constants.FAILURE_NA)))
									responsesList.add(setCommandResponse(isData, commandLog, req));
							}
							else {
								responsesList.add(setCommandResponse(isData, commandLog, req));
							}
						}
					}
				}
			}			
			
		response.setData(responsesList);
		LOG.info("Single connection command logs Data Response Success.");
	} catch (Exception e) {
		LOG.error("Issue in getting data due to : " + e.getMessage());
		response = ExceptionHandlerConfig.setErrorData(e);
	}
	return response;
	}
	
	
	SingleConnectionCommandLogResponse setCommandResponse(SingleConnectionMeterCommandLog isData, DevicesCommandLog commandLog,
			SingleConnectionCommandLogsReq req) throws ParseException {
		SingleConnectionCommandLogResponse logResponse = new SingleConnectionCommandLogResponse();
		String command = ConfigCommands.getUiCommandName(commandLog.getCommand_name());
		
		    logResponse.setHesDate(isData.getHes_date() != null ? dateConverter.convertDateToHesString(isData.getHes_date()): req.getReplaceBy());
			logResponse.setDeviceSerialNumber(isData.getDevice_serial_number());
			logResponse.setBatchId(isData.getBatch_id());
			logResponse.setDeviceType(isData.getDevice_type());
			logResponse.setMdasDatetime(commandLog.getCommand_datetime() != null ? dateConverter.convertDateToHesString(commandLog.getCommand_datetime()): req.getReplaceBy());
			logResponse.setOwnerName(isData.getOwner_name());
			//logResponse.setReason(isData.getReason()!= null ? CommonUtils.splitReason(isData.getReason(), commandLog.getRetry()).trim() : req.getReplaceBy());
			logResponse.setReason((isData.getCmd_res() != null && isData.getCmd_res().get(commandLog.getCommand_name()) != null) 
					? CommonUtils.splitReason(isData.getCmd_res().get(commandLog.getCommand_name()), commandLog.getRetry()).trim() 
							: req.getReplaceBy());
			logResponse.setSource(commandLog.getSource());
			logResponse.setStatus(commandLog.getStatus());
			logResponse.setTotAttempts(String.valueOf(commandLog.getRetry()));
			logResponse.setUserId(commandLog.getUser_id());
			logResponse.setExtBatchId(commandLog.getExt_batch_id());
			logResponse.setTrackingId(commandLog.getTracking_id());
			logResponse.setCommandCompletionDatetime(commandLog.getCommand_completion_datetime() 
					!= null ? dateConverter.convertDateToHesString(commandLog.getCommand_completion_datetime()) : req.getReplaceBy());
			logResponse.setCommandName(commandLog.getMode().equalsIgnoreCase(Constants.CONFIG_WRITE) ? "{\"" + command + "\" : \"" + commandLog.getCommand_val() + "\"}" : command);
			
			if(commandLog.getCommands() != null && commandLog.getCommands().size()>0) {
				logResponse.setCommandList(commandLog.getCommands().toString().replace("=", ":"));
			}
			else {
				logResponse.setCommandList(req.getReplaceBy() != null ? req.getReplaceBy() : "-");
			}
			
			logResponse.setCancelledBy(commandLog.getCancel_by_user() != null ? commandLog.getCancel_by_user() : req.getReplaceBy());
			
			if(commandLog.getCommand_name().equals(ConfigCommands.DAILY_LOAD_PROFILE.commandName)) {
			logResponse.setRangeDate(commandLog.getDaily_range_date().size()>0 
					? "("+commandLog.getDaily_range_date().get(Constants.DateRange.STARTDATE)+" - "+commandLog.getDaily_range_date().get(Constants.DateRange.ENDDATE)+")" 
							: req.getReplaceBy());
			}
			if(commandLog.getCommand_name().equals(ConfigCommands.DELTA_LOAD_PROFILE.commandName)) {
			logResponse.setRangeDate(commandLog.getDelta_range_date().size()>0 
					? "("+commandLog.getDelta_range_date().get(Constants.DateRange.STARTDATE)+" - "+commandLog.getDelta_range_date().get(Constants.DateRange.ENDDATE)+")" 
							: req.getReplaceBy());
			}
		return logResponse;
	}
	
	
	@Override
	public CommonResponse cancelSingleConnectionCommandLog(SingleConnectionCommandLogsReq req) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		List<SingleConnectionMeterCommandLog> meterInfoList = new ArrayList<SingleConnectionMeterCommandLog>();
		List<MeterDataRes> busyDevList = new ArrayList<MeterDataRes>();
		Set<String> busyMeterList = new HashSet<String>();
		Optional<List<SingleConnectionMeterCommandLog>> singleConnectionInfo = null;
		boolean isIdExist = false;
		boolean isBusy = false;
		DevicesInfo deviceInfo = null;
		String successStatus = "";
		String alreadyCancelledStatus = "";
		String cancelledStatus = "";
		LOG.info("Cancellation of Single Connection Command:--> ");
		try {
			String batchId = req.getBatchId();
			String extBatchId = req.getExtBatchId();
			
			if ((batchId == null || batchId.isEmpty()) || (extBatchId == null || extBatchId.isEmpty())) {
				
				error.setErrorMessage("Global and External Batch Id can not be empty");
				response.setCode(404);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}

			if (req.getUserId() == null || req.getUserId().isEmpty()) {
				error.setErrorMessage("Missing User Id");
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}
			if (req.getDeviceSerialNumber() != null && !req.getDeviceSerialNumber().isEmpty()) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository
						.getBatchIdLogs(req.getBatchId(), req.getDeviceSerialNumber());
			}else {
					singleConnectionInfo = singleConnectionMeterCommandLogRepository
							.getDataByBatchId(req.getBatchId());
			}
			
			if ((req.getDeviceSerialNumber() != null && !req.getDeviceSerialNumber().isEmpty()) && singleConnectionInfo.get().isEmpty()) {
				error.setErrorMessage("Device Not Found");
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}
			
			
					if(!singleConnectionInfo.isPresent()) {
						error.setErrorMessage(req.getBatchId() + " : " +ExternalConstants.Message.NOT_EXISTS);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}		
			
			meterInfoList = singleConnectionInfo.get();
			for(SingleConnectionMeterCommandLog isData : meterInfoList) {
				deviceInfo = devicesInfoService.getDevicesInfo(isData.getDevice_serial_number());

				for(DevicesCommandLog  cmd: isData.getCommand_list()) {
					
/*
 * Command Cancellation on basis of Tracking Id					
 */
					if(req.getTrackingId() != null && !req.getTrackingId().isEmpty()) {
					if(req.getExtBatchId().equalsIgnoreCase(cmd.getExt_batch_id())
							&& req.getTrackingId().equalsIgnoreCase(cmd.getTracking_id())) {
						isIdExist = true;
						if((Constants.SUCCESS.equalsIgnoreCase(cmd.getStatus())
								|| Constants.FAILURE.equalsIgnoreCase(cmd.getStatus()))) {
							successStatus = Constants.SUCCESS;
						}
						else if (Constants.CANCELLED.equalsIgnoreCase(cmd.getStatus())) {
							alreadyCancelledStatus = Constants.ALREADY_CANCELLED;
							
						}
						else if((Constants.ADDED.equalsIgnoreCase(cmd.getStatus())
								|| Constants.IN_PROGRESS.equalsIgnoreCase(cmd.getStatus()))) {
							cancelledStatus = Constants.CANCELLED;
						if (deviceInfo != null && !Constants.Status.BUSY.equalsIgnoreCase(deviceInfo.getStatus())) {
						if((Constants.ADDED.equalsIgnoreCase(cmd.getStatus())
								|| Constants.IN_PROGRESS.equalsIgnoreCase(cmd.getStatus()))) {
							
							if (cmd.getCommand_name().equalsIgnoreCase(Constants.CommandName.FullData)) {
								
								for (Map.Entry<String, String> entry : cmd.getCommands().entrySet()) {
						            if (Constants.ADDED.equalsIgnoreCase(entry.getValue())
											|| Constants.IN_PROGRESS.equalsIgnoreCase(entry.getValue())) { // Check if the value is the one you want to update
						                entry.setValue(Constants.CANCELLED); // Update the value
						            }
							}
							}
							cmd.setStatus(Constants.CANCELLED);
							isData.setStatus(Constants.CANCELLED);
							cmd.setCancel_by_user(req.getUserId());
							isBusy = true;
						}
						}else {

							MeterDataRes busyDevice = new MeterDataRes();
							if(deviceInfo != null) {
							busyDevice.setMeterNumber(deviceInfo.getDevice_serial_number());
							busyDevice.setConsumerName(deviceInfo.getConsumer_name());
							busyDevice.setCommissioningStatus(deviceInfo.getCommissioning_status());
							busyDevice.setDevMode(deviceInfo.getDev_mode());
							busyDevice.setDeviceType(deviceInfo.getDevice_type());
							busyDevice.setIpAddress(deviceInfo.getIp_address_main());
							busyDevice.setPort(deviceInfo.getIp_port_main());
							busyDevice.setManufacturer(deviceInfo.getMeter_type());
							
							busyDevList.add(busyDevice);
							}
						
						}
						
					   }
					}
					}
/*
 * Command Cancellation on basis of Batch Id
 */
					else {
					  if(req.getExtBatchId().equalsIgnoreCase(cmd.getExt_batch_id())) {
						  isIdExist = true;
							if((Constants.SUCCESS.equalsIgnoreCase(cmd.getStatus())
									|| Constants.FAILURE.equalsIgnoreCase(cmd.getStatus()))) {
								successStatus = Constants.SUCCESS;
							}
							else if (Constants.CANCELLED.equalsIgnoreCase(cmd.getStatus())) {
								alreadyCancelledStatus = Constants.ALREADY_CANCELLED;
								
							}
							else if((Constants.ADDED.equalsIgnoreCase(cmd.getStatus())
										|| Constants.IN_PROGRESS.equalsIgnoreCase(cmd.getStatus()))) {
								cancelledStatus = Constants.CANCELLED;
							 if (deviceInfo != null && !Constants.Status.BUSY.equalsIgnoreCase(deviceInfo.getStatus())) {
								if((Constants.ADDED.equalsIgnoreCase(cmd.getStatus())
										|| Constants.IN_PROGRESS.equalsIgnoreCase(cmd.getStatus()))) {
									
									if (cmd.getCommand_name().equalsIgnoreCase(Constants.CommandName.FullData)) {
										
										for (Map.Entry<String, String> entry : cmd.getCommands().entrySet()) {
								            if (Constants.ADDED.equalsIgnoreCase(entry.getValue())
													|| Constants.IN_PROGRESS.equalsIgnoreCase(entry.getValue())) { // Check if the value is the one you want to update
								                entry.setValue(Constants.CANCELLED); // Update the value
								            }
									}
									}
									cmd.setStatus(Constants.CANCELLED);
									isData.setStatus(Constants.CANCELLED);
									cmd.setCancel_by_user(req.getUserId());
									isBusy = true;
								}
								}else {

									MeterDataRes busyDevice = new MeterDataRes();
									if(deviceInfo != null) {
										if (!busyMeterList.contains(deviceInfo.getDevice_serial_number())) {
											
											busyDevice.setMeterNumber(deviceInfo.getDevice_serial_number());
											busyDevice.setConsumerName(deviceInfo.getConsumer_name());
											busyDevice.setCommissioningStatus(deviceInfo.getCommissioning_status());
											busyDevice.setStatus(deviceInfo.getStatus());
											busyDevice.setDevMode(deviceInfo.getDev_mode());
											busyDevice.setDeviceType(deviceInfo.getDevice_type());
											busyDevice.setIpAddress(deviceInfo.getIp_address_main());
											busyDevice.setPort(deviceInfo.getIp_port_main());
											busyDevice.setManufacturer(deviceInfo.getMeter_type());
											
											busyDevList.add(busyDevice);
											
											busyMeterList.add(deviceInfo.getDevice_serial_number());
										}
									
									}
								
								}
							
						   }
					  }
					}
				}
				}
				
			if (!isIdExist) {
				error.setErrorMessage(ExternalConstants.Message.ID_NOT_EXISTS);
				response.setCode(404);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}
			if (!isBusy && busyDevList.size()>0) {
				error.setErrorMessage("Below Device List already in progress");
				response.setCode(404);
				response.setError(true);
				response.addErrorMessage(error);
				response.setData(busyDevList);
				return response; 
			}
			if((successStatus.equalsIgnoreCase(Constants.SUCCESS) && alreadyCancelledStatus.isEmpty() && cancelledStatus.isEmpty()) 
					|| (successStatus.equalsIgnoreCase(Constants.SUCCESS) && alreadyCancelledStatus.equalsIgnoreCase(Constants.ALREADY_CANCELLED) && cancelledStatus.isEmpty())) {
				
				response.setCode(200);
				response.setMessage("Already Success Or Failure");
				return response;
			}
			else if(alreadyCancelledStatus.equalsIgnoreCase(Constants.ALREADY_CANCELLED) && successStatus.isEmpty() && cancelledStatus.isEmpty()) {
				
				response.setCode(200);
				response.setMessage("Already Cancelled.");
				return response;
				
			}
			singleConnectionMeterCommandLogRepository.saveAll(meterInfoList);
			response.setData(busyDevList);
			response.setMessage(ExternalConstants.Message.CANCELLED_SUCCESSFULLY);

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

		}
		return response;
	
	}

	@Override
	public CommonResponse getSingleConnectionCommandLogsByBatchId(SingleConnectionCommandLogsReq req) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		List<SingleConnectionMeterCommandLog> meterInfoList = new ArrayList<SingleConnectionMeterCommandLog>();
		ArrayList<SingleConnectionCommandLogResponse> responsesList = new ArrayList<SingleConnectionCommandLogResponse>();
		Optional<List<SingleConnectionMeterCommandLog>> singleConnectionInfo = null;
		Set<String> commandSet=new HashSet<>();
		String devType = "";
		LOG.info("Getting data from DB batch wise :");
		try {
			
			if (req.getDeviceSerialNumber() != null && !req.getDeviceSerialNumber().isEmpty()) {
				DevicesInfo devicesInfo = devicesInfoService.getDevicesInfo(req.getDeviceSerialNumber());
				
				if (devicesInfo == null) {
					error.setErrorMessage("Device "+Constants.NOT_FOUND);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
			}
			
			String batchId = req.getBatchId();
			String extBatchId = req.getExtBatchId();
			
			if(req.getCommand() != null && !req.getCommand().isEmpty()) {
			String[] commands = req.getCommand().split(",");
			commandSet = new HashSet<>(Arrays.asList(commands));
			}
			
			if(req.getDevType() != null && !req.getDevType().isEmpty()) {
			   if(ExternalConstants.DeviceTypes.SINGLE_PHASE_DEV.contains(req.getDevType())) {
				   devType = ExternalConstants.DeviceTypes.SINGLE_PHASE_DEV;
  			   }
			   else if(ExternalConstants.DeviceTypes.THREE_PHASE_DEV.contains(req.getDevType())){
				   devType = ExternalConstants.DeviceTypes.THREE_PHASE_DEV;
			   }
			   else if(ExternalConstants.DeviceTypes.CT_METER.contains(req.getDevType())){
				   devType = ExternalConstants.DeviceTypes.CT_METER;
			   }
			}
			if ((batchId == null || batchId.isEmpty()) || (extBatchId == null || extBatchId.isEmpty())) {
				
				error.setErrorMessage("Global Batch Id and External Batch Id can not be empty");
				response.setCode(404);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}

            if (req.getDeviceSerialNumber() != null && !req.getDeviceSerialNumber().isEmpty()) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository
						.getBatchIdLogs(req.getBatchId(), req.getDeviceSerialNumber());
			}else if(req.getDeviceSerialNumber() == null && req.getDevType() != null && !req.getDevType().isEmpty()){
					singleConnectionInfo = singleConnectionMeterCommandLogRepository
							.getLogByBatchIdDevType(req.getBatchId(), devType);
			}else {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository
						.getDataByBatchId(req.getBatchId());
			}
			if(singleConnectionInfo.get().isEmpty()) {
				error.setErrorMessage(req.getBatchId() + " : " +ExternalConstants.Message.NOT_EXISTS);
				response.setCode(404);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}		
			meterInfoList = singleConnectionInfo.get();
			
			for(SingleConnectionMeterCommandLog isData : meterInfoList) {
				
				for(DevicesCommandLog  commandLog: isData.getCommand_list()) {
	/*
	 * Command logs on basis of Tracking Id					
	 */
						if(req.getTrackingId() != null && !req.getTrackingId().isEmpty()) {
						
							if(req.getExtBatchId().equalsIgnoreCase(commandLog.getExt_batch_id())
								&& req.getTrackingId().equalsIgnoreCase(commandLog.getTracking_id())) {
							
							responsesList.add(setCommandResponse(isData,commandLog, req));
						     }
						 }
	/*
	 * Command logs on basis of Batch Id
	 */
						else {
						  if(req.getExtBatchId().equalsIgnoreCase(commandLog.getExt_batch_id())) {
							  
							  if(req.getCommand() != null && !req.getCommand().isEmpty()) {
									
								      if(commandSet.contains(commandLog.getCommand_name())) {
									  
										responsesList.add(setCommandResponse(isData,commandLog,req));
									   }
								 }
								  else {
										responsesList.add(setCommandResponse(isData,commandLog, req));
									}
						    }
						}
					}
				}
		response.setData(responsesList);
		LOG.info("meter command logs batch wise Data Response Success.");
	} catch (Exception e) {
		LOG.error("Issue in getting batch wise data due to : " + e.getMessage());
		response = ExceptionHandlerConfig.setErrorData(e);

	}
	return response;
	}
	
	SingleConnectionCommandLogResponse setCommandRetryResponse(SingleConnectionMeterCommandLog isData, DevicesCommandLog commandLog,
			SingleConnectionCommandLogsReq req) throws ParseException {
		
		DevicesInfo devicesInfo = new DevicesInfo();
		SingleConnectionCommandLogResponse logResponse = new SingleConnectionCommandLogResponse();
			logResponse.setDeviceSerialNumber(isData.getDevice_serial_number());
			logResponse.setCommandName(commandLog.getCommand_name());
			logResponse.setRetryCount(String.valueOf(commandLog.getRetry()));
			logResponse.setStatus(commandLog.getStatus());
			logResponse.setMdasDatetime(dateConverter.convertDateToString(commandLog.getCommand_datetime()));
			logResponse.setRelayStatus(devicesInfo.getRelay_status());
			
			
		return logResponse;
	}
	
	/*
	 * Code For Extra Obis code Implementation
	 */

	@Override
	public CommonResponse getObisCodeDetails() {
		CommonResponse response = new CommonResponse();
		
		LOG.info("Obis code Info Data to get from DB:--> ");

		try {

			List<ManufacturerSpecificObis> subStations = manufacturerSpecificObisRepository.findAll();
			List<ManufacturerSpecificOBISCodeResponse> substationsResList = new ArrayList<>();

			for (ManufacturerSpecificObis obisCode : subStations) {
				ManufacturerSpecificOBISCodeResponse manufacRes = new ManufacturerSpecificOBISCodeResponse();

				manufacRes.setManufacturer(obisCode.getManufacturer());
				manufacRes.setDeviceType(obisCode.getDevice_type());
				manufacRes.setProfileType(obisCode.getProfile_type());
				manufacRes.setVersion(obisCode.getVersion());
				manufacRes.setExtraObisCode(obisCode.getExtra_obis_code() != null ? String.valueOf(obisCode.getExtra_obis_code()) : "-");
				manufacRes.setMdasDatetime(
						obisCode.getMdas_datetime() != null ? dateConverter.convertDateToString(obisCode.getMdas_datetime())
								: null);
				manufacRes.setObisCode(String.valueOf(obisCode.getObis_code()));
				manufacRes.setOccurenceDatetime(
						obisCode.getOccurence_datetime() != null ? dateConverter.convertDateToString(obisCode.getOccurence_datetime())
								: null);
				manufacRes.setImplementationStatus(obisCode.getStatus());

				substationsResList.add(manufacRes);
			}

			response.setData(substationsResList);
			LOG.info("Obis Code Info Data Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting Obis Code Info Data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

		}
		return response;

	}

    @Override
	public CommonResponse updateObisCodeDetails(SingleConnectionCommandLogsReq req) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Obis Info Data to update in DB:--> ");

		try {
			Optional<ManufacturerSpecificObis> mnfSpeObis = manufacturerSpecificObisRepository
					.findData(req.getMeterType(), req.getDevType(), req.getProfileType(), req.getVersion());
			if (!mnfSpeObis.isPresent()) {
				error.setErrorMessage(req.getMeterType() + " and " + req.getProfileType() + " " + Constants.NOT_FOUND);
				response.setCode(404);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}

			ManufacturerSpecificObis mnfObisList = mnfSpeObis.get();
			Set<String> impObisList = new HashSet<>(Arrays.asList(((String) commonUtils
					.getPropertiesFile(meteConfiguration.getObisCodeList())
					.get(ObisProfileName.getCommandId(
							OnDemandCommands.NAME_PLATES.commandName.equals(req.getProfileType()) ? req.getProfileType()
									: req.getProfileType() + "_" + req.getDevType())))
					.split(",")));

			if (!impObisList.containsAll(req.getObisCodeList())) {
				error.setErrorMessage("This obis code is not implemented yet");
				response.setCode(404);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}

			Set<String> hashSet = new HashSet<>(
					req.getObisCodeList().stream().map(String::valueOf).collect(Collectors.toSet()));
			if (mnfObisList.getDefault_obis_code() != null) {
				mnfObisList.getDefault_obis_code().addAll(hashSet);
			} else {
				mnfObisList.setDefault_obis_code(hashSet);
			}
			if (mnfObisList.getExtra_obis_code() != null) {
				mnfObisList.getExtra_obis_code().removeAll(req.getObisCodeList());
			}
			mnfObisList.setImplementation_datetime(new Date(System.currentTimeMillis()));
			mnfObisList.setMdas_datetime(new Date(System.currentTimeMillis()));
			
			manufacturerSpecificObisRepository.save(mnfObisList);

			LOG.info("Obis Info Successfully Updated in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in updating data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

		}
		return response;
	}


	@Override
	public CommonResponse uploadXmlFile(MultipartFile xmlFile, String data) {
		CommonResponse response = new CommonResponse();
		try {
			SingleConnectionCommandReq reqData = CommonUtils.getMapper().readValue(data,
					new TypeReference<SingleConnectionCommandReq>() {
					});

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			SingleConnectionCommandLogsServiceImple handler = new SingleConnectionCommandLogsServiceImple();
			
			InputStream inputStream = xmlFile.getInputStream();
			inputStream.read();
			
			saxParser.parse(xmlFile.getInputStream(), handler);
			MeterResponse meterResponse = new MeterResponse();
			Map<String, List<Object>> parsedData = new HashMap<String, List<Object>>();
			parsedData.putAll(handler.getCellDataMap());
			parsedData.putAll(handler.getLnDataMap());
			List<Object> dataList = new ArrayList<Object>();
			for (Map.Entry<String, List<Object>> entry : parsedData.entrySet()) {

				if (entry.getKey().equals("Cell")) {
					if (entry.getValue() != null) {
						dataList.add(entry.getValue());
					}
				} else if (entry.getKey().equals("LN")) {
						meterResponse.setObisCmd(MasterObisCode.getCommandName(entry.getValue().get(0).toString()));
						entry.getValue().remove(0);
						int lastIndex = entry.getValue().size()-1;
						entry.getValue().remove(lastIndex);
						meterResponse.setObisCode(entry.getValue().stream()
		                         .map(Object::toString)
		                         .collect(Collectors.toList()));
				}

			}
			meterResponse.setData(dataList.toArray());
			meterResponse.setStatus(200);
			meterResponse.setMessage(Constants.SUCCESS);
			singleConnectionCommandLogsService.saveHistoryData(devicesInfoService.getDevicesInfo(reqData.getDeviceSerialNo()), meterResponse.getObisCmd(), meterResponse);
			response.setMessage(Constants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private Map<String, List<Object>> cellDataMap = new HashMap<>();
    private Map<String, List<Object>> lnDataMap = new HashMap<>();
    private String currentElement;

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch, start, length).trim();
        if (!data.isEmpty()) {
            if (currentElement.equalsIgnoreCase("Cell")) {
                List<Object> cellDataList = cellDataMap.getOrDefault(currentElement, new ArrayList<>());
                cellDataList.add(data);
                cellDataMap.put(currentElement, cellDataList);
            } else if (currentElement.equalsIgnoreCase("LN")) {
                List<Object> lnDataList = lnDataMap.getOrDefault(currentElement, new ArrayList<>());
                lnDataList.add(data);
                lnDataMap.put(currentElement, lnDataList);
            }
        }
    }

    public Map<String, List<Object>> getCellDataMap() {
        return cellDataMap;
    }

    public Map<String, List<Object>> getLnDataMap() {
        return lnDataMap;
    }


}