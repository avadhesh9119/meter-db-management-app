package com.global.meter.v3.inventive.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.v3.common.enums.OnDemandCommands;
import com.global.meter.v3.inventive.business.model.DevicesCommandLog;
import com.global.meter.v3.inventive.business.model.SingleConnectionMeterCommandLog;
import com.global.meter.v3.inventive.models.CommandStatusRes;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;

@Service
public class SingleConnectionCommandLogService {

	
	@Autowired
	DateConverter dateConverter;
	
	public SingleConnectionMeterCommandLog logConnectionCommand(DevicesInfo deviceInfo, SingleConnectionCommandReq req, String batchId) throws ParseException {
		SingleConnectionMeterCommandLog commandLog = new SingleConnectionMeterCommandLog();
		commandLog.setOwner_name(deviceInfo.getOwner_name());
		commandLog.setSource(req.getSource());
		commandLog.setUser_id(req.getUserId());
		commandLog.setDevice_serial_number(req.getDevice().getPlainText());
		commandLog.setBatch_id(batchId);
		commandLog
				.setHes_date(new DateConverter().convertStringToDate(CommonUtils.getCurrentDate()));
		commandLog.setMdas_datetime(new Date(System.currentTimeMillis()));
		commandLog.setSubdivision_name(deviceInfo.getSubdevision_name());
		commandLog.setSubstation_name(deviceInfo.getSubstation_name());
		commandLog.setFeeder_name(deviceInfo.getFeeder_name());
		commandLog.setDt_name(deviceInfo.getDt_name());
		commandLog.setDcu_serial_number(deviceInfo.getDcu_serial_number());
		commandLog.setDevice_type(deviceInfo.getDevice_type());
		commandLog.setBackend_status(Constants.BackendStatus.Y);
		
		return commandLog;
	}
	
	public DevicesCommandLog logDeviceCommand(String command, int seqNo, String status, Date cmdDate,
			String trackindId, String extBatchId,  SingleConnectionCommandReq req, String mode) throws ParseException {
		DevicesCommandLog commandLog = new DevicesCommandLog();
		commandLog.setCommand_name(command);
		commandLog.setRetry(0);
		commandLog.setSeqno(seqNo);
		commandLog.setStatus(status);
		commandLog.setCommand_datetime(cmdDate);
		commandLog.setCommand_completion_datetime(null);
		commandLog.setTracking_id(trackindId);
		commandLog.setExt_batch_id(extBatchId);
		commandLog.setSource(req.getSource());
		commandLog.setUser_id(req.getUserId());
		commandLog.setMode(mode);
		commandLog.setTemp_installation_datetime(req.getInstallation_datetime() != null ? dateConverter.convertStringToDate(req.getInstallation_datetime()) : new Date(System.currentTimeMillis()));
		return commandLog;
	}
	
	public CommandStatusRes commandStatusRes(String command, String trackindId,String extBatchId,String status, Date cmdDate) throws ParseException {
		CommandStatusRes commandStatusRes = new CommandStatusRes();
		commandStatusRes.setCommandDateTime(dateConverter.convertDateToHesString(cmdDate));
		commandStatusRes.setCommandName(command);
		commandStatusRes.setTrackingId(trackindId);
		commandStatusRes.setStatus(status);
		commandStatusRes.setExtBatchId(extBatchId);
		return commandStatusRes;
	}
	
	// Method to add dependent commands
	public void addDependentCommands(String dependentCommands, List<String> commandList, List<String> readCommandList) {
	    if (dependentCommands != null && !dependentCommands.isEmpty()) {
	        String[] cmdList = dependentCommands.split(",");
	        for (String cmd : cmdList) {
	            if (!(commandList.contains(cmd) || readCommandList.contains(cmd)) && !cmd.isEmpty()) {
	                if (OnDemandCommands.contains(cmd)) {
	                    commandList.add(cmd);
	                } else if (ConfigCommands.contains(cmd)) {
	                    readCommandList.add(cmd);
	                }
	            }
	        }
	    }
	}
	
}
