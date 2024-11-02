package com.global.meter.v3.inventive.validator;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.global.meter.MeterDBAppStarter;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.common.enums.OnDemandCommands;
import com.global.meter.v3.inventive.business.model.DevicesCommandLog;
import com.global.meter.v3.inventive.business.model.SingleConnectionMeterCommandLog;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogs;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;

@Service
public class SingleConnectionCommandValidator {

	@Autowired
	DateConverter dateConverter;

	@Autowired
	MetersCommandsConfiguration meteConfiguration;

	public CommonResponse validatePrepayCommand(SingleConnectionMeterCommandLog singleConnectionCommandLog,
			List<DevicesCommandLog> commandLogList, List<String> readCommandList, int seqNo, CommonResponse response,
			String batchId, SingleConnectionCommandReq req, String extBatchId) throws ParseException {
		for (String command : readCommandList) {

			if (!ConfigCommands.contains(command.trim())) {
				response.setMessage(Constants.WRONG_COMMAND_TYPE);
				return response;
			}
			for (DevicesCommandLog info : singleConnectionCommandLog.getCommand_list()) {
				seqNo = info.getSeqno() >= seqNo ? info.getSeqno() + 1 : seqNo;

				// check command already in queue and get permission from user
				if (req.getIsDiscard() != null && req.getIsDiscard().equals(Constants.NO)
						&& req.getReqFrom().equals(Constants.HES) && command.equals(info.getCommand_name())
						&& (info.getStatus().equals(Constants.ADDED)
								|| info.getStatus().equals(Constants.IN_PROGRESS))) {

					response.setMessage(
							"The command " + command + " " + Constants.ALREADY_IN_QUEUE + " do you want to cancel.");
					response.setCode(200);

					return response;
				}

				// check and return command is already in queue
				else if (req.getReqFrom() != null && (req.getReqFrom().equals(Constants.EXT)
						|| (req.getReqFrom().equals(Constants.HES) && req.getIsDiscard().equals(Constants.YES)))
						&& command.equals(info.getCommand_name()) && (info.getStatus().equals(Constants.ADDED)
								|| info.getStatus().equals(Constants.IN_PROGRESS))) {

					info.setStatus(Constants.DISCARD);
				}
			}
			
			DevicesCommandLog commandLog = setDeviceCommand(command, seqNo++, req, extBatchId,Constants.CONFIG_READ);
			commandLogList.add(commandLog);

			batchId = commandLog.getTracking_id();
		}
		return response;
	}

	public CommonResponse validateOnDemandCommand(SingleConnectionMeterCommandLog singleConnectionCommandLog,
			List<DevicesCommandLog> commandLogList, List<String> commandList, int seqNo, CommonResponse response,
			String batchId, SingleConnectionCommandReq req, String extBatchId, DevicesInfo deviceInfo) throws ParseException {

		DevicesCommandLog preCommandLog = new DevicesCommandLog();
		Set<String> connectDepCmdSet = new HashSet<>();
		Set<String> disConnectDepCmdSet = new HashSet<>();
		String connectCmdList[];
		String disConnectCmdList[];
		// add connect dependent command
		if (commandList.contains(Constants.CONNECT)) {
			connectCmdList = meteConfiguration.getDependentConnectCommands().split(",");
			if (connectCmdList.length > 0) {
				for (String cmd : connectCmdList) {
					if (!cmd.isEmpty()) {
						connectDepCmdSet.add(cmd);
					}
				}
			}
		}
		// add disconnect dependent command
		if (commandList.contains(Constants.DISCONNECT)) {
			disConnectCmdList = meteConfiguration.getDependentDisconnectCommands().split(",");
			if (disConnectCmdList.length > 0) {
				for (String cmd : disConnectCmdList) {
					if (!cmd.isEmpty()) {
						disConnectDepCmdSet.add(cmd);
					}
				}
			}
		}
		for (String command : commandList) {
			if (!ConfigCommands.contains(command.trim())) {
				response.setMessage(Constants.WRONG_COMMAND_TYPE);
				return response;
			}
			if (connectDepCmdSet.contains(command)) {
				connectDepCmdSet.remove(command);
			}
			for (DevicesCommandLog info : commandLogList) {
				seqNo = info.getSeqno() >= seqNo ? info.getSeqno() + 1 : seqNo;

				// discard previous commands who contain in full data command
				if (req.getObisCodeList() != null && req.getObisCodeList().contains(Constants.CommandName.FullData)
						&& commandList.contains(info.getCommand_name()) && command.equals(info.getCommand_name())
						&& (info.getStatus().equals(Constants.ADDED)
								|| info.getStatus().equals(Constants.IN_PROGRESS))) {
					//info.setStatus(Constants.DISCARD);

				}

				// discard previous disconnect command for current connect command
				if (command.equals(Constants.CONNECT) && info.getCommand_name().equals(Constants.DISCONNECT)
						&& (info.getStatus().equals(Constants.ADDED)
								|| info.getStatus().equals(Constants.IN_PROGRESS))) {
					info.setStatus(Constants.DISCARD);
				}
				// discard previous connect command for current disconnect command 
				else if (command.equals(Constants.DISCONNECT) && info.getCommand_name().equals(Constants.CONNECT)
						&& (info.getStatus().equals(Constants.ADDED)
								|| info.getStatus().equals(Constants.IN_PROGRESS))) {
					info.setStatus(Constants.DISCARD);
				}

				// check command already in queue and get permission from user
				if (req.getIsDiscard() != null && req.getIsDiscard().equals(Constants.NO)
						&& req.getReqFrom().equals(Constants.HES) && command.equals(info.getCommand_name())
						&& (info.getStatus().equals(Constants.ADDED)
								|| info.getStatus().equals(Constants.IN_PROGRESS))) {

					if(Constants.CONNECT.equalsIgnoreCase(command) || Constants.DISCONNECT.equalsIgnoreCase(command)) {
						response.setMessage(
							"The command " + command + " " + Constants.ALREADY_IN_QUEUE + " do you want to cancel.");
					
						response.setCode(200);
						return response;
					}
					
				}

				// check and discard previous command which is already in queue
				else if ((Constants.EXT.equals(req.getReqFrom())
						|| (req.getReqFrom() != null && Constants.HES.equals(req.getReqFrom()) && req.getIsDiscard().equals(Constants.YES)))
						&& command.equals(info.getCommand_name()) && (info.getStatus().equals(Constants.ADDED)
								|| info.getStatus().equals(Constants.IN_PROGRESS))) {
					if(Constants.CONNECT.equalsIgnoreCase(command) || Constants.DISCONNECT.equalsIgnoreCase(command)) {
						info.setStatus(Constants.DISCARD);
					}					
				}
				// remove connect dependent command which is already in queue
				if (commandList.contains(Constants.CONNECT) && (connectDepCmdSet.contains(info.getCommand_name())
						&& (info.getStatus().equals(Constants.ADDED)
								|| info.getStatus().equals(Constants.IN_PROGRESS)))) {
					connectDepCmdSet.remove(info.getCommand_name());
				}
				// remove disconnect dependent command which is already in queue
				if (commandList.contains(Constants.DISCONNECT) && (disConnectDepCmdSet.contains(info.getCommand_name())
						&& (info.getStatus().equals(Constants.ADDED)
								|| info.getStatus().equals(Constants.IN_PROGRESS)))) {
					disConnectDepCmdSet.remove(info.getCommand_name());
				}
			}
			
			DevicesCommandLog commandLog = setDeviceCommand(command, seqNo++, req, extBatchId, Constants.ON_DEMAND);
			
			if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equalsIgnoreCase(command)) {
				commandLog.setDaily_range_date(req.getDailyRangeDate());
			}
			if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equalsIgnoreCase(command)) {
				commandLog.setDelta_range_date(req.getDeltaRangeDate());
			}
			else if (Constants.CommandName.FullData.equalsIgnoreCase(command)) {
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
			
			if(command.equalsIgnoreCase(Constants.BILLING_DATA) && req.getReadWise() != null 
					&& req.getReadWise().equalsIgnoreCase("M") &&
			deviceInfo.getLastbillingreaddatetime() != null && 
			(CommonUtils.compareWithFirstDayOfMonth(deviceInfo.getLastbillingreaddatetime()) == 1)) {
			commandLog.setStatus(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH);
			}						
			
			//set first priority for connect command 
			if (command.equals(Constants.CONNECT) && (!commandLogList.get(0).getCommand_name().equals(Constants.CONNECT)
					|| (commandLogList.get(0).getCommand_name().equals(Constants.CONNECT)
							&& (!commandLogList.get(0).getStatus().equals(Constants.ADDED)
									&& !commandLogList.get(0).getStatus().equals(Constants.IN_PROGRESS))))) {
				preCommandLog = commandLogList.get(0);
				commandLogList.remove(0);
				preCommandLog.setSeqno(seqNo - 1);
				commandLog.setSeqno(1);
				commandLogList.add(0, commandLog);
				commandLogList.add(preCommandLog);

			}
			else if (command.equals(Constants.DISCONNECT) && commandList.contains(Constants.CONNECT)) {
				commandLog.setStatus(Constants.DISCARD);
				commandLogList.add(commandLog);
			}
			//set first priority for disconnect command
			else if (command.equals(Constants.DISCONNECT) && (!commandLogList.get(0).getCommand_name().equals(Constants.CONNECT)
					|| (commandLogList.get(0).getCommand_name().equals(Constants.CONNECT)
							&& (!commandLogList.get(0).getStatus().equals(Constants.ADDED)
									&& !commandLogList.get(0).getStatus().equals(Constants.IN_PROGRESS))))) {
				preCommandLog = commandLogList.get(0);
				commandLogList.remove(0);
				preCommandLog.setSeqno(seqNo - 1);
				commandLog.setSeqno(1);
				commandLogList.add(0, commandLog);
				commandLogList.add(preCommandLog);

			} 
			else {
				commandLogList.add(commandLog);
			}
			batchId = commandLog.getTracking_id();
		}

		if (connectDepCmdSet.size() > 0) {
			disConnectDepCmdSet.removeAll(connectDepCmdSet);
			addDependentCommand(commandLogList, seqNo, connectDepCmdSet, req, extBatchId);
		}
		if (disConnectDepCmdSet.size() > 0) { 
			addDependentCommand(commandLogList, seqNo, disConnectDepCmdSet, req, extBatchId);
		}

		return response;
	}

	public CommonResponse validateConfigWriteCommand(SingleConnectionMeterCommandLog singleConnectionCommandLog,
			List<DevicesCommandLog> commandLogList, Map<String, String> writeCommandList, int seqNo,
			CommonResponse response, String batchId, SingleConnectionCommandReq req, String extBatchId, DevicesInfo deviceInfo) throws ParseException {

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

			if (!ConfigCommands.contains(command.getKey().trim())) {
				response.setMessage(Constants.WRONG_COMMAND_TYPE);
				return response;
			}
			for (DevicesCommandLog info : commandLogList) {
				seqNo = info.getSeqno() >= seqNo ? info.getSeqno() + 1 : seqNo;

				if (command.getKey().equalsIgnoreCase(info.getCommand_name()) && info.getCommand_val() != null
						&& (info.getStatus().equalsIgnoreCase(Constants.ADDED)
								|| info.getStatus().equalsIgnoreCase(Constants.IN_PROGRESS))) {

					info.setStatus(Constants.DISCARD);
				}
			}
			DevicesCommandLog commandLog = setDeviceCommand(command.getKey().trim() ,seqNo++, req, extBatchId, Constants.CONFIG_WRITE);
			commandLog.setCommand_val(command.getValue().trim());
			commandLogList.add(commandLog);

			batchId = commandLog.getTracking_id();
		}
		singleConnectionCommandLog.setCommand_list(commandLogList);
		response.setCommandDateTime(dateConverter.convertDateToHesString(new Date(System.currentTimeMillis())));
		return response;
	}

	public void addDependentCommand(List<DevicesCommandLog> commandLogList, int seqNo,
			Set<String> connectIndCmdSet, SingleConnectionCommandReq req, String extBatchId) throws ParseException {

		for (String command : connectIndCmdSet) {
			
			DevicesCommandLog commandLog = setDeviceCommand(command, seqNo++, req, extBatchId, OnDemandCommands.commands.containsKey(command) ? Constants.ON_DEMAND : Constants.CONFIG_READ);
			
			if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equalsIgnoreCase(command)) {
				commandLog.setDaily_range_date(req.getDailyRangeDate());
			}
			if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equalsIgnoreCase(command)) {
				commandLog.setDelta_range_date(req.getDeltaRangeDate());
			}

			commandLogList.add(commandLog);
		}

	}

	public void setConnectDisconnectPriority(List<SingleConnectionCommandLogs> singleConnectionCommandLogs,
			DevicesCommandLog command, SingleConnectionCommandLogs newCommandLog) {

		if (command.getCommand_name().equals(Constants.CONNECT) && singleConnectionCommandLogs.size() > 0
				&& !singleConnectionCommandLogs.get(0).getCommandName().equalsIgnoreCase(Constants.CONNECT)) {

			singleConnectionCommandLogs.add(0, newCommandLog);
		} else if (command.getCommand_name().equals(Constants.DISCONNECT)) {

			if (singleConnectionCommandLogs.size() > 0
					&& !singleConnectionCommandLogs.get(0).getCommandName().equals(Constants.CONNECT)) {
				singleConnectionCommandLogs.add(0, newCommandLog);
			} else {
				if (singleConnectionCommandLogs.size() > 1) {
					singleConnectionCommandLogs.add(1, newCommandLog);
				} else {
					singleConnectionCommandLogs.add(newCommandLog);
				}
			}
		} else {
			singleConnectionCommandLogs.add(newCommandLog);
		}

	}
	
	
	private DevicesCommandLog setDeviceCommand(String command, int seqNo, SingleConnectionCommandReq req, String extBatchId, String mode) throws ParseException {
		DevicesCommandLog commandLog = new DevicesCommandLog();
		commandLog.setCommand_name(command);
		commandLog.setRetry(0);
		commandLog.setSeqno(seqNo);
		commandLog.setStatus(Constants.ADDED);
		commandLog.setCommand_datetime(new Date(System.currentTimeMillis()));
		commandLog.setCommand_completion_datetime(null);
		commandLog.setExt_batch_id(extBatchId);
		commandLog.setTracking_id(String.valueOf(System.nanoTime()));
		commandLog.setSource(req.getSource());
		commandLog.setUser_id(req.getUserId());		
		commandLog.setMode(mode);
		commandLog.setTemp_installation_datetime(req.getInstallation_datetime() != null ? dateConverter.convertStringToDate(req.getInstallation_datetime()) : new Date(System.currentTimeMillis()));
		return commandLog;
	}

}
