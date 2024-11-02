package com.global.meter.v3.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.global.meter.business.model.MeterPwds;
import com.global.meter.business.model.MeterPwdsHistory;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.models.MeterCommunicationConfig;
import com.global.meter.v3.inventive.models.MeterCommunicationConfigRes;
import com.global.meter.v3.inventive.repository.MeterPwdsHistoryRepository;
import com.global.meter.v3.inventive.repository.MeterPwdsRepository;
import com.global.meter.v3.inventive.service.MeterCommunicationConfigService;
import com.global.meter.v3.inventive.utils.MeterCommunicationConfigCaster;

@Service
public class MeterCommunicationConfigServiceImpl implements MeterCommunicationConfigService {

	private static final Logger LOG = LoggerFactory.getLogger(MeterCommunicationConfigServiceImpl.class);

	@Autowired
	MeterPwdsRepository meterPwdsRepository;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	MeterCommunicationConfigCaster meterCommunicationConfigCaster;

	@Autowired
	MeterPwdsHistoryRepository meterPwdsHistoryRepository;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Override
	public CommonResponse addMeterTypeInfo(List<MeterCommunicationConfig> meterCommunicationConfigs) {
		List<MeterPwds> meterPwds = new ArrayList<>();
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Meter communication config data saving in DB.");

		try {
			for (MeterCommunicationConfig meterCommConfig : meterCommunicationConfigs) {
				if (!StringUtils.isEmpty(meterCommConfig.getManufacturer())) {
					Optional<MeterPwds> meters = meterPwdsRepository.getManufacturarData(
							meterCommConfig.getManufacturer(), meterCommConfig.getDeviceType(),
							meterCommConfig.getModeOfComm());

					if (meters.isPresent()) {
						error.setErrorMessage(
								meterCommConfig.getManufacturer() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}

				}

				MeterPwds meterPwd = new MeterPwds();
				meterPwd.setAuthKey(meterCommConfig.getAuthKey());
				meterPwd.setAuthMode(meterCommConfig.getAuthMode());
				meterPwd.setCipheringKey(meterCommConfig.getCipheringKey());
				meterPwd.setCipheringMode(meterCommConfig.getCipheringMode());
				meterPwd.setHighPwd(meterCommConfig.getHighPwd());
				meterPwd.setFimrwarePwd(meterCommConfig.getFirmwarePwd());
				meterPwd.setLowPwd(meterCommConfig.getLowPwd());
				meterPwd.setManufacturer(meterCommConfig.getManufacturer());
				meterPwd.setPart(meterCommConfig.getPart());
				meterPwd.setSystemTitle(meterCommConfig.getSystemTitle());
				meterPwd.setPushPorts(meterCommConfig.getPushPorts());
				meterPwd.setDeviceType(meterCommConfig.getDeviceType());
				meterPwd.setTrackingId(String.valueOf(System.nanoTime()));
				meterPwd.setCreatedBy(meterCommConfig.getCreatedBy());
				meterPwd.setCreatedOn(new Date(System.currentTimeMillis()));
				meterPwd.setModeOfComm(meterCommConfig.getModeOfComm());

				meterPwds.add(meterPwd);
			}

			response.setCode(200);
			meterPwdsRepository.saveAll(meterPwds);
			response.setMessage(ExternalConstants.Message.ADDED);
			LOG.info("Meter communication config data saved in DB.");

		} catch (Exception e) {
			LOG.error("Issue in adding master password due to : {}", e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

		}
		
		return response;
	}

	@Override
	public CommonResponse updateMeterTypeInfo(List<MeterCommunicationConfig> meterCommunicationConfigs) {
		List<MeterPwds> meterPwds = new ArrayList<>();
		List<MeterPwdsHistory> meterPwdsHistory = new ArrayList<>();
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		
		LOG.info("Meter communication config data update in DB:");
		
		try {
			for(MeterCommunicationConfig meterCommConfig :  meterCommunicationConfigs) {
				
				if(!StringUtils.isEmpty(meterCommConfig.getTrackingId())) {
					
					if (meterCommConfig.getUpdatedBy() == null || meterCommConfig.getUpdatedBy().isEmpty()) {
						error.setErrorMessage("Upadate By is "+Constants.MISSING);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					
					Optional<MeterPwds> meters = meterPwdsRepository.getData(meterCommConfig.getTrackingId());	
					
					if(!meters.isPresent()) {
						error.setErrorMessage(meterCommConfig.getTrackingId() + " : " +ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					
					
				
				
				MeterPwds meterPwd = meters.get();
				meterPwd.setAuthKey(meterCommConfig.getAuthKey() != null ? meterCommConfig.getAuthKey() : meterPwd.getAuthKey());
				meterPwd.setAuthMode(meterCommConfig.getAuthMode() != null ? meterCommConfig.getAuthMode() : meterPwd.getAuthMode());
				meterPwd.setCipheringKey(meterCommConfig.getCipheringKey() != null ? meterCommConfig.getCipheringKey() : meterPwd.getCipheringKey());
				meterPwd.setCipheringMode(meterCommConfig.getCipheringMode() != null ? meterCommConfig.getCipheringMode() : meterPwd.getCipheringMode());
				meterPwd.setHighPwd(meterCommConfig.getHighPwd() != null ? meterCommConfig.getHighPwd() : meterPwd.getHighPwd());
				meterPwd.setFimrwarePwd(meterCommConfig.getFirmwarePwd() != null ? meterCommConfig.getFirmwarePwd() : meterPwd.getFimrwarePwd());
				meterPwd.setLowPwd(meterCommConfig.getLowPwd() != null ? meterCommConfig.getLowPwd() : meterPwd.getLowPwd());
				meterPwd.setPart(meterCommConfig.getPart() != null ? meterCommConfig.getPart() : meterPwd.getPart());
				meterPwd.setSystemTitle(meterCommConfig.getSystemTitle() != null ? meterCommConfig.getSystemTitle() : meterPwd.getSystemTitle());
				meterPwd.setPushPorts(meterCommConfig.getPushPorts() != null ? meterCommConfig.getPushPorts() : meterPwd.getPushPorts());
				meterPwd.setDeviceType(meterCommConfig.getDeviceType() != null ? meterCommConfig.getDeviceType() : meterPwd.getDeviceType());
				meterPwd.setUpdatedBy(meterCommConfig.getUpdatedBy());
				meterPwd.setUpdatedOn(new Date(System.currentTimeMillis()));
				meterPwd.setModeOfComm(meterCommConfig.getModeOfComm() != null ? meterCommConfig.getModeOfComm() : meterPwd.getModeOfComm());

				meterPwds.add(meterPwd);
				
				
				MeterPwdsHistory meterPwdHistory = CommonUtils.getMapper().readValue(
						CommonUtils.getMapper().writeValueAsString(meterPwd), MeterPwdsHistory.class);
				
				meterPwdHistory.setUpdatedBy(meterCommConfig.getUpdatedBy());
				meterPwdHistory.setUpdatedOn(meterPwd.getUpdatedOn());
				
				meterPwdsHistory.add(meterPwdHistory);
				}else {
					error.setErrorMessage(Constants.ENTER_TRACKING_ID);
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
			}
			meterPwdsRepository.saveAll(meterPwds);
			meterPwdsHistoryRepository.saveAll(meterPwdsHistory);
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
			LOG.info("Meter communication config data updated in DB.");
			
		} catch (Exception e) {
			LOG.error("Issue in updating meter communication config due to : {}", e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

		}

		return response;
	}

	@Override
	public CommonResponse getMeterTypeInfo(MeterCommunicationConfig metersDataRequests) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		
		LOG.info("Meter communication config get data Impl Called");
		
		try {
			
			if(!StringUtils.isEmpty(metersDataRequests.getTrackingId())) {
				Optional<MeterPwds> meters = meterPwdsRepository.getData(metersDataRequests.getTrackingId());
				
				if(!meters.isPresent()) {
					error.setErrorMessage(metersDataRequests.getTrackingId() + " : " +ExternalConstants.Message.NOT_EXISTS);
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				
				
			}
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace() + "." +Tables.METER_PWDS)
						.append(" where tracking_id = '").append(metersDataRequests.getTrackingId()).append("'");
			
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queryBuilder.toString()));
			
			List<MeterCommunicationConfigRes> meterCommunicationConfigRes = new ArrayList<>();
			meterCommunicationConfigCaster.prepareMeterCommunicationConfig(outputList, meterCommunicationConfigRes);
			response.setData(meterCommunicationConfigRes);
			LOG.info("Meter communication config data get Success.");
		
		} catch (Exception e) {
			LOG.error("Issue in getting device list due to : {}", e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

		}
		return response;
	}

	@Override
	public CommonResponse getMeterTypeInfoList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Meter communication config get data list Impl Called");
		
		try {
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace() + "." +Tables.METER_PWDS + " order by created_on desc");
			
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queryBuilder.toString()));
			
			List<MeterCommunicationConfigRes> masterPasswordRequests = new ArrayList<>();
			meterCommunicationConfigCaster.prepareMeterCommunicationConfig(outputList, masterPasswordRequests);
			if (masterPasswordRequests.size() > 0) {
				response.setData(masterPasswordRequests);
			}
			else {
				response.setMessage(Constants.NO_DATA_FOUND);
			}
			
			LOG.info("Meter communication config data list get Success.");
		
		} catch (Exception e) {
			LOG.error("Issue in getting meter communication config data list due to : {}", e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

		}
		return response;
	}

}
