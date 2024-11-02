package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.global.meter.business.model.MeterTypeInfo;
import com.global.meter.data.repository.MeterTypeRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.MasterPasswordRequest;
import com.global.meter.inventive.models.MasterPasswordResponse;
import com.global.meter.inventive.service.MasterPasswordService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.MasterPasswordCaster;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class MasterPasswordServiceImpl implements MasterPasswordService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MasterPasswordServiceImpl.class);
	
	@Autowired 
	MeterTypeRepository meterTypeRepository;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	MasterPasswordCaster masterPasswordCaster;
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Override
	public CommonResponse addMeterTypeInfo(List<MasterPasswordRequest> passwordRequests) {
		List<MeterTypeInfo> meterTypeInfos = new ArrayList<>();
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		
		LOG.info("Master password info data save in DB:--> ");
		
		try {
			for(MasterPasswordRequest passwordRequest :  passwordRequests) {
				if(!StringUtils.isEmpty(passwordRequest.getMeterType())) {
					Optional<MeterTypeInfo> meters = meterTypeRepository.findById(passwordRequest.getMeterType());
					
					if(meters.isPresent()) {
						error.setErrorMessage(passwordRequest.getMeterType() + " : " +ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					
					
				}
				
				MeterTypeInfo meterTypeInfo = new MeterTypeInfo();
				meterTypeInfo.setMeterType(passwordRequest.getMeterType());
				meterTypeInfo.setAuthKey(passwordRequest.getAuthKey());
				meterTypeInfo.setAuthMode(passwordRequest.getAuthMode());
				meterTypeInfo.setCipheringKey(passwordRequest.getCipheringKey());
				meterTypeInfo.setCipheringMode(passwordRequest.getCipheringMode());
				meterTypeInfo.setHighPwd(passwordRequest.getHighPwd());
				meterTypeInfo.setFimrwarePwd(passwordRequest.getFirmwarePwd());
				meterTypeInfo.setLowPwd(passwordRequest.getLowPwd());
				meterTypeInfo.setManufacturer(passwordRequest.getManufacturer());
				meterTypeInfo.setMetertypes(passwordRequest.getMeterType());
				meterTypeInfo.setMiosFile(passwordRequest.getMiosFile());
				meterTypeInfo.setMiosFormat(passwordRequest.getMiosFormat());
				meterTypeInfo.setPart(passwordRequest.getPart());
				meterTypeInfo.setSystemTitle(passwordRequest.getSystemTitle());
				meterTypeInfo.setPushPorts(passwordRequest.getPushPorts());
				
				meterTypeInfos.add(meterTypeInfo);
			}
			meterTypeRepository.saveAll(meterTypeInfos);
			
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
			
		} catch (Exception e) {
			LOG.error("Issue in adding master password due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		
		return response;
	}

	@Override
	public CommonResponse updateMeterTypeInfo(List<MasterPasswordRequest> passwordRequests) {
		List<MeterTypeInfo> meterTypeInfos = new ArrayList<>();
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		
		LOG.info("Master password info data update in DB:--> ");
		
		try {
			for(MasterPasswordRequest passwordRequest :  passwordRequests) {
				
				if(!StringUtils.isEmpty(passwordRequest.getMeterType())) {
					Optional<MeterTypeInfo> meters = meterTypeRepository.findById(passwordRequest.getMeterType());
					
					if(!meters.isPresent()) {
						error.setErrorMessage(passwordRequest.getMeterType() + " : " +ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					
					
				
				
				MeterTypeInfo meterTypeInfo = meters.get();
				meterTypeInfo.setMeterType(passwordRequest.getMeterType());
				meterTypeInfo.setAuthKey(passwordRequest.getAuthKey() != null ? passwordRequest.getAuthKey() : meterTypeInfo.getAuthKey());
				meterTypeInfo.setAuthMode(passwordRequest.getAuthMode() != null ? passwordRequest.getAuthMode() : meterTypeInfo.getAuthMode());
				meterTypeInfo.setCipheringKey(passwordRequest.getCipheringKey() != null ? passwordRequest.getCipheringKey() : meterTypeInfo.getCipheringKey());
				meterTypeInfo.setCipheringMode(passwordRequest.getCipheringMode() != null ? passwordRequest.getCipheringMode() : meterTypeInfo.getCipheringMode());
				meterTypeInfo.setHighPwd(passwordRequest.getHighPwd() != null ? passwordRequest.getHighPwd() : meterTypeInfo.getHighPwd());
				meterTypeInfo.setFimrwarePwd(passwordRequest.getFirmwarePwd() != null ? passwordRequest.getFirmwarePwd() : meterTypeInfo.getFimrwarePwd());
				meterTypeInfo.setLowPwd(passwordRequest.getLowPwd() != null ? passwordRequest.getLowPwd() : meterTypeInfo.getLowPwd());
				meterTypeInfo.setManufacturer(passwordRequest.getManufacturer() != null ? passwordRequest.getManufacturer() : meterTypeInfo.getManufacturer());
				meterTypeInfo.setMetertypes(passwordRequest.getMeterType() != null ? passwordRequest.getMeterType() : meterTypeInfo.getMetertypes());
				meterTypeInfo.setMiosFile(passwordRequest.getMiosFile() != null ? passwordRequest.getMiosFile() : meterTypeInfo.getMiosFile());
				meterTypeInfo.setMiosFormat(passwordRequest.getMiosFormat() != null ? passwordRequest.getMiosFormat() : meterTypeInfo.getMiosFormat());
				meterTypeInfo.setPart(passwordRequest.getPart() != null ? passwordRequest.getPart() : meterTypeInfo.getPart());
				meterTypeInfo.setSystemTitle(passwordRequest.getSystemTitle() != null ? passwordRequest.getSystemTitle() : meterTypeInfo.getSystemTitle());
				meterTypeInfo.setPushPorts(passwordRequest.getPushPorts() != null ? passwordRequest.getPushPorts() : meterTypeInfo.getPushPorts());
				
				meterTypeInfos.add(meterTypeInfo);
				
				}
			}
			meterTypeRepository.saveAll(meterTypeInfos);
			
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
			
		} catch (Exception e) {
			LOG.error("Issue in updating master password due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		
		return response;
	}

	@Override
	public CommonResponse getMeterTypeInfo(MasterPasswordRequest masterPasswordRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Getting Devices List from DB:--> ");
		
		try {
			
			if(!StringUtils.isEmpty(masterPasswordRequest.getMeterType())) {
				Optional<MeterTypeInfo> meters = meterTypeRepository.findById(masterPasswordRequest.getMeterType());
				
				if(!meters.isPresent()) {
					error.setErrorMessage(masterPasswordRequest.getMeterType() + " : " +ExternalConstants.Message.NOT_EXISTS);
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				
				
			}
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace() + "." +Tables.METER_TYPE_INFO)
						.append(" where meter_type = '").append(masterPasswordRequest.getMeterType()).append("'");
			
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queryBuilder.toString()));
			
			List<MasterPasswordResponse> masterPasswordRequests = new ArrayList<>();
			masterPasswordCaster.prepareMasterPassword(outputList, masterPasswordRequests);
			response.setData(masterPasswordRequests);
			LOG.info("Devices Service Data List Response Success.");
		
		} catch (Exception e) {
			LOG.error("Issue in getting device list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getMeterTypeInfoList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Devices List from DB:--> ");
		
		try {
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace() + "." +Tables.METER_TYPE_INFO);
			
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queryBuilder.toString()));
			
			List<MasterPasswordResponse> masterPasswordRequests = new ArrayList<>();
			masterPasswordCaster.prepareMasterPassword(outputList, masterPasswordRequests);
			if (masterPasswordRequests.size() > 0) {
				response.setData(masterPasswordRequests);
			}
			else {
				response.setMessage(Constants.NO_DATA_FOUND);
			}
			
			LOG.info("Devices Service Data List Response Success.");
		
		} catch (Exception e) {
			LOG.error("Issue in getting device list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

}
