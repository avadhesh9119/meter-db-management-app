package com.global.meter.inventive.mdm.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.global.meter.business.model.MdmPushLogs;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.mdm.models.MdmPushLogRequest;
import com.global.meter.inventive.mdm.models.MdmPushRequest;
import com.global.meter.inventive.mdm.repository.MdmPushLogRepository;
import com.global.meter.inventive.mdm.service.MdmPushLogService;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.utils.Constants;

@Service
public class MdmPushLogImpl implements MdmPushLogService {
	private static final Logger LOG = LoggerFactory.getLogger(MdmPushLogImpl.class);

	@Autowired
	MdmPushLogRepository mdmPushLogRepository;

	@Override
	public CommonResponse addMDMPushLog(MdmPushRequest mdmPushRequest) {
		CommonResponse response = new CommonResponse();
		LOG.info("Process for add MDM push logs called: ");
		ErrorData error = new ErrorData();
		try {

			List<MdmPushLogs> pushLogList = new ArrayList<>();

			for (MdmPushLogRequest isData : mdmPushRequest.getPushLog()) {

				Optional<MdmPushLogs> pushLogInfo = mdmPushLogRepository.findById(isData.getMeterNumber());

				if (pushLogInfo.isPresent()) {
					error.setErrorMessage(isData.getMeterNumber() + " : " + ExternalConstants.Message.EXISTS);
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				MdmPushLogs pushLogs = new MdmPushLogs();
				
				pushLogs.setDevice_serial_number(isData.getMeterNumber());
				pushLogs.setCreated_by(mdmPushRequest.getUserId());
				pushLogs.setCreated_on(new Date(System.currentTimeMillis()));
				
				if(ConfigCommands.INSTANTANEOUS_READ.commandName.equals(mdmPushRequest.getProfile())){
					pushLogs.setInstantaneous_hes_datetime(isData.getHesTime());
					
				}
				else if(ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(mdmPushRequest.getProfile())){
					pushLogs.setDaily_load_profile_hes_datetime(isData.getHesTime());
					
				} 
				else if(ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(mdmPushRequest.getProfile())){
					pushLogs.setBlock_load_profile_hes_datetime(isData.getHesTime());
					
				} 
				else if(ConfigCommands.BILLING_DATA.commandName.equals(mdmPushRequest.getProfile())){
					pushLogs.setDaily_billing_hes_datetime(isData.getHesTime());
					
				} 
				else if(Constants.MONTHLY_BILLING_DATA.equalsIgnoreCase(mdmPushRequest.getProfile())){
					pushLogs.setMonthly_billing_hes_datetime(isData.getHesTime());
					
				} 
				else if(Constants.EVENT_DATA.equalsIgnoreCase(mdmPushRequest.getProfile())){
					pushLogs.setEvent_data_hes_datetime(isData.getHesTime());
					
				} 
				else if(ConfigCommands.NAME_PLATES.commandName.equals(mdmPushRequest.getProfile())){
					pushLogs.setName_plate_hes_datetime(isData.getHesTime());
					
				}else {
					
					error.setErrorMessage("Profile type not found");
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				pushLogList.add(pushLogs);
			}
			mdmPushLogRepository.saveAll(pushLogList);
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.info("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateMdmPushLog(MdmPushRequest mdmPushRequest) {
		CommonResponse response = new CommonResponse();
		LOG.info("Process for add MDM push logs called: ");
		ErrorData error = new ErrorData();
		try {

			List<MdmPushLogs> pushLogList = new ArrayList<>();

			for (MdmPushLogRequest isData : mdmPushRequest.getPushLog()) {

				MdmPushLogs pushLogs = new MdmPushLogs();
				if (!StringUtils.isEmpty(isData.getMeterNumber())) {
					Optional<MdmPushLogs> pushLogInfo = mdmPushLogRepository.findById(isData.getMeterNumber());

					if (!pushLogInfo.isPresent()) {
						error.setErrorMessage(isData.getMeterNumber() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					pushLogs = pushLogInfo.get();
				}

				pushLogs.setDevice_serial_number(isData.getMeterNumber());
				pushLogs.setUpdated_by(mdmPushRequest.getUserId());
				pushLogs.setUpdated_on(new Date(System.currentTimeMillis()));

				if (ConfigCommands.INSTANTANEOUS_READ.commandName.equals(mdmPushRequest.getProfile())) {
					pushLogs.setInstantaneous_hes_datetime(isData.getHesTime());

				} else if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(mdmPushRequest.getProfile())) {
					pushLogs.setDaily_load_profile_hes_datetime(isData.getHesTime());

				} else if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(mdmPushRequest.getProfile())) {
					pushLogs.setBlock_load_profile_hes_datetime(isData.getHesTime());

				} else if (ConfigCommands.BILLING_DATA.commandName.equals(mdmPushRequest.getProfile())) {
					pushLogs.setDaily_billing_hes_datetime(isData.getHesTime());

				} else if (Constants.MONTHLY_BILLING_DATA.equalsIgnoreCase(mdmPushRequest.getProfile())) {
					pushLogs.setMonthly_billing_hes_datetime(isData.getHesTime());

				} else if (Constants.EVENT_DATA.equalsIgnoreCase(mdmPushRequest.getProfile())) {
					pushLogs.setEvent_data_hes_datetime(isData.getHesTime());

				} else if (Constants.ALARM_DATA.equalsIgnoreCase(mdmPushRequest.getProfile())) {
					pushLogs.setAlarm_data_hes_datetime(isData.getHesTime());

				} else if (ConfigCommands.NAME_PLATES.commandName.equals(mdmPushRequest.getProfile())) {
					pushLogs.setName_plate_hes_datetime(isData.getHesTime());

				} else {

					error.setErrorMessage("Profile type not found");
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				pushLogList.add(pushLogs);
			}
			mdmPushLogRepository.saveAll(pushLogList);
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.info("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

}
