package com.global.meter.v3.inventive.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.v3.inventive.business.model.DevicesCommandLog;
import com.global.meter.v3.inventive.models.MeterCommunicationConfigRes;
import com.global.meter.v3.inventive.models.MeterPwdsRead;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;

@Component
public class MeterCommunicationConfigCaster {
private static final Logger LOG = LoggerFactory.getLogger(MeterCommunicationConfigCaster.class);


	@Autowired
	DateConverter dateConverter;

	public void prepareMeterCommunicationConfig(String outputList, List<MeterCommunicationConfigRes> ispResponseList)
			throws Exception {
		LOG.info("Meter Communication Config Caster called....");
		List<MeterPwdsRead> meterPwdsReads = new ArrayList<MeterPwdsRead>();
		meterPwdsReads = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<MeterPwdsRead>>() {
		});
		LOG.info("Meter Communication Config Caster Response Adding");
		for (MeterPwdsRead meterPwdsRead : meterPwdsReads) {
			MeterCommunicationConfigRes commConfiRes = new MeterCommunicationConfigRes();
			
			commConfiRes.setManufacturer(meterPwdsRead.getManufacturer());
			commConfiRes.setTrackingId(meterPwdsRead.getTracking_id() != null ? meterPwdsRead.getTracking_id() : "-");
			commConfiRes.setAuthKey(meterPwdsRead.getAuthkey() != null ? meterPwdsRead.getAuthkey(): "-");
			commConfiRes.setAuthMode(meterPwdsRead.getAuthmode() != null ? meterPwdsRead.getAuthmode() : "-");
			commConfiRes.setCipheringKey(meterPwdsRead.getCipheringkey() != null ? meterPwdsRead.getCipheringkey() : "-");
			commConfiRes.setCipheringMode(meterPwdsRead.getCipheringmode() != null ? meterPwdsRead.getCipheringmode() : "-");
			commConfiRes.setFirmwarePwd(meterPwdsRead.getFirmwarepwd() != null ? meterPwdsRead.getFirmwarepwd() : "-");
			commConfiRes.setHighPwd(meterPwdsRead.getHighpwd() != null ? meterPwdsRead.getHighpwd() : "-");
			commConfiRes.setLowPwd(meterPwdsRead.getLowpwd() != null ? meterPwdsRead.getLowpwd() : "-");
			commConfiRes.setPart(meterPwdsRead.getPart());
			commConfiRes.setPushPorts(meterPwdsRead.getPushports() != null ? meterPwdsRead.getPushports() : "-");
			commConfiRes.setDeviceType(meterPwdsRead.getDevice_type() != null ? meterPwdsRead.getDevice_type() : "-");
			commConfiRes.setSystemTitle(meterPwdsRead.getSystemtitle() != null ? meterPwdsRead.getSystemtitle() : "-");
			commConfiRes.setCreatedTimestamp(meterPwdsRead.getCreated_on() != null 
					? dateConverter.convertDateToHesString(meterPwdsRead.getCreated_on()) : "-");
			commConfiRes.setCreatedBy(meterPwdsRead.getCreated_by() != null ? meterPwdsRead.getCreated_by() : "-");
			commConfiRes.setUpdatedBy(meterPwdsRead.getUpdated_by() != null ? meterPwdsRead.getUpdated_by() : "-");
			commConfiRes.setUpdatedTimestamp(meterPwdsRead.getUpdated_on() != null 
					? dateConverter.convertDateToHesString(meterPwdsRead.getUpdated_on()) : "-");
			commConfiRes.setModeOfComm(meterPwdsRead.getMode_of_comm() != null ? meterPwdsRead.getMode_of_comm() : "-");
			ispResponseList.add(commConfiRes);
		}
		
		LOG.info("Meter Communication Config Caster Response Added Success");
	}
	
	public DevicesCommandLog setDeviceCommand(String command, int seqNo, SingleConnectionCommandReq req, String extBatchId) {
		DevicesCommandLog commandLog = new DevicesCommandLog();
		commandLog.setCommand_name(command);
		commandLog.setRetry(0);
		commandLog.setSeqno(seqNo++);
		commandLog.setStatus(Constants.ADDED);
		commandLog.setCommand_datetime(new Date(System.currentTimeMillis()));
		commandLog.setCommand_completion_datetime(null);
		commandLog.setExt_batch_id(extBatchId);
		commandLog.setTracking_id(String.valueOf(System.nanoTime()));
		commandLog.setSource(req.getSource());
		commandLog.setUser_id(req.getUserId());		
		return commandLog;
	}
}
