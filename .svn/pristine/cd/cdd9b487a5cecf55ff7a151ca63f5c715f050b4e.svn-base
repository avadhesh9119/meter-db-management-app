package com.global.meter.inventive.mdm.serviceImpl;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.MdmMaster;
import com.global.meter.business.model.MdmMasterLogs;
import com.global.meter.business.model.MdmPushLogs;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.mdm.models.MdmMasterRequest;
import com.global.meter.inventive.mdm.models.MdmMasterResponse;
import com.global.meter.inventive.mdm.repository.MdmMasterRepository;
import com.global.meter.inventive.mdm.repository.MdmPushLogRepository;
import com.global.meter.inventive.mdm.repository.MdmdMasterLogRepository;
import com.global.meter.inventive.mdm.service.MdmMasterService;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;


@Service
public class MdmMasterImpl implements MdmMasterService {
	private static final Logger LOG = LoggerFactory.getLogger(MdmMasterService.class);

	@Autowired
	MdmMasterRepository mdmdMasterRepository;
	
	@Autowired
	MdmdMasterLogRepository mdmdMasterLogRepository;
	
	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	DevicesInfoService devicesInfoService;
	
	@Autowired
	MdmPushLogRepository mdmPushLogRepository;
	
	@Override
	public CommonResponse addMdmMaster(List<MdmMasterRequest> mdmMasterRequest) {
		CommonResponse response = new CommonResponse();
		LOG.info("Mdm Master Service Impl called for add Master Data.");
		try {
			ErrorData error = new ErrorData();
			List<MdmMaster> mdmMasterList = new ArrayList<>();
			List<MdmMasterLogs> mdmMasterLogList = new ArrayList<>();

			for (MdmMasterRequest isData : mdmMasterRequest) {
				 if(isData.getUserId() == null || isData.getUserId().isEmpty()) {
						error.setErrorMessage("Invalid or missing UserId");
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}

				Optional<MdmMaster> devicesInfo = mdmdMasterRepository.findById(isData.getMdmId());

				if (devicesInfo.isPresent()) {
					error.setErrorMessage(isData.getMdmId() + " : " + ExternalConstants.Message.EXISTS);
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				MdmMaster mdmMaster = new MdmMaster();

				mdmMaster.setMdm_id(isData.getMdmId());
				mdmMaster.setFtp_address(isData.getFtpAddress());
				mdmMaster.setFtp_login(isData.getFtpLogin());
				mdmMaster.setFtp_password(isData.getFtpPassword());
				mdmMaster.setLogin_id(isData.getLoginId());
				mdmMaster.setLogin_password(isData.getLoginPassword());
				mdmMaster.setMdm_auth_url(isData.getMdmAuthUrl());
				mdmMaster.setMdm_name(isData.getMdmName());
				mdmMaster.setIs_active(isData.getIsActive());
				mdmMaster.setCreated_by(isData.getUserId());
				mdmMaster.setCreated_on(new Date(System.currentTimeMillis()));
				
				MdmMasterLogs mdmLog = CommonUtils.getMapper().readValue(
						CommonUtils.getMapper().writeValueAsString(mdmMaster), MdmMasterLogs.class);
				mdmLog.setCreated_by(isData.getUpdatedBy());
				mdmLog.setCreated_on(new Date(System.currentTimeMillis()));
			 
				mdmMasterLogList.add(mdmLog);
				mdmMasterList.add(mdmMaster);
			}
			mdmdMasterRepository.saveAll(mdmMasterList);
			mdmdMasterLogRepository.saveAll(mdmMasterLogList);
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.info("Issue in inserting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateMdmMaster(List<MdmMasterRequest> mdmMasterRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Process for update MDM Master called: ");
		try {
			List<MdmMaster> mdmMasterList = new ArrayList<>();
			List<MdmMasterLogs> mdmMasterLogList = new ArrayList<>();

			for (MdmMasterRequest isData : mdmMasterRequest) {
				MdmMaster mdmMaster = new MdmMaster();

				if (!StringUtils.isEmpty(isData.getMdmId())) {
					Optional<MdmMaster> mdmMasterInfo = mdmdMasterRepository.findById(isData.getMdmId());

					if (!mdmMasterInfo.isPresent()) {
						error.setErrorMessage(isData.getMdmId() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}

					mdmMaster = mdmMasterInfo.get();
				}
				MdmMasterLogs mdmLog = CommonUtils.getMapper().readValue(
						CommonUtils.getMapper().writeValueAsString(mdmMaster), MdmMasterLogs.class);
				mdmLog.setCreated_by(isData.getUpdatedBy());
				mdmLog.setCreated_on(new Date(System.currentTimeMillis()));
				
				mdmMaster.setMdm_id(isData.getMdmId());
				mdmMaster.setFtp_address(isData.getFtpAddress());
				mdmMaster.setFtp_login(isData.getFtpLogin());
				mdmMaster.setFtp_password(isData.getFtpPassword());
				mdmMaster.setLogin_id(isData.getLoginId());
				mdmMaster.setLogin_password(isData.getLoginPassword());
				mdmMaster.setMdm_auth_url(isData.getMdmAuthUrl());
				mdmMaster.setMdm_name(isData.getMdmName());
				mdmMaster.setIs_active(isData.getIsActive());
				mdmMaster.setUpdated_by(isData.getUserId());
				mdmMaster.setUpdated_on(new Date(System.currentTimeMillis()));

				mdmMasterList.add(mdmMaster);
				mdmMasterLogList.add(mdmLog);
			}
			mdmdMasterRepository.saveAll(mdmMasterList);
			mdmdMasterLogRepository.saveAll(mdmMasterLogList);
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.info("Issue in updating data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse deleteMdmMaster(MdmMasterRequest mdmdMasterRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Process for update MDM Master called: ");
		try {

			if (!StringUtils.isEmpty(mdmdMasterRequest.getMdmId())) {
				Optional<MdmMaster> mdmMasterInfo = mdmdMasterRepository.findById(mdmdMasterRequest.getMdmId());

				if (!mdmMasterInfo.isPresent()) {
					error.setErrorMessage(mdmdMasterRequest.getMdmId() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				MdmMasterLogs mdmLog = CommonUtils.getMapper().readValue(
						CommonUtils.getMapper().writeValueAsString(mdmMasterInfo.get()), MdmMasterLogs.class);

				mdmdMasterLogRepository.save(mdmLog);
				mdmdMasterRepository.deleteById(mdmdMasterRequest.getMdmId());

			}

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.DELETED);
		} catch (Exception e) {
			LOG.info("Issue in updating data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getMdmMaster(MdmMasterRequest mdmdMasterRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Process for get MDM Master called: ");
		try {
			MdmMaster mdmMaster = new MdmMaster();
			MdmMasterResponse mdmMasterRes = new MdmMasterResponse();

			if (!StringUtils.isEmpty(mdmdMasterRequest.getMdmId())) {
				Optional<MdmMaster> mdmMasterInfo = mdmdMasterRepository.findById(mdmdMasterRequest.getMdmId());

				if (!mdmMasterInfo.isPresent()) {
					error.setErrorMessage(mdmdMasterRequest.getMdmId() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				mdmMaster = mdmMasterInfo.get();
			}
			mdmMasterRes.setMdmId(mdmMaster.getMdm_id());
			mdmMasterRes.setMdmName(mdmMaster.getMdm_name());
			mdmMasterRes.setMdmAuthUrl(mdmMaster.getMdm_auth_url());
			mdmMasterRes.setLoginId(mdmMaster.getLogin_id());
			mdmMasterRes.setLoginPassword(mdmMaster.getLogin_password());
			mdmMasterRes.setFtpLogin(mdmMaster.getFtp_login());
			mdmMasterRes.setFtpAddress(mdmMaster.getFtp_address());
			mdmMasterRes.setFtpPassword(mdmMaster.getFtp_password());
			mdmMasterRes.setIsActive(mdmMaster.isIs_active());
			mdmMasterRes.setCreatedBy(mdmMaster.getCreated_by());
			mdmMasterRes.setCreatedOn(mdmMaster.getCreated_on() != null ? dateConverter.convertDateToHesString(mdmMaster.getCreated_on()) : mdmdMasterRequest.getReplaceBy());
			mdmMasterRes.setUpdatedBy(mdmMaster.getUpdated_by());
			mdmMasterRes.setUpdatedOn(mdmMaster.getUpdated_on() != null ? dateConverter.convertDateToHesString(mdmMaster.getUpdated_on()) : mdmdMasterRequest.getReplaceBy());

			response.setCode(200);
			response.setData(mdmMasterRes);
		} catch (Exception e) {
			LOG.info("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getMdmMasterList(MdmMasterRequest mdmdMasterRequest) {
		CommonResponse response = new CommonResponse();
		LOG.info("Process for get MDM Master List called: ");
		try {
			List<MdmMasterResponse> mdmMasterResList = new ArrayList<>();
			List<MdmMaster> mdmMasterInfo = mdmdMasterRepository.findAll();

			for (MdmMaster isData : mdmMasterInfo) {

				MdmMasterResponse mdmMasterRes = new MdmMasterResponse();

				mdmMasterRes.setMdmId(isData.getMdm_id());
				mdmMasterRes.setMdmName(isData.getMdm_name());
				mdmMasterRes.setMdmAuthUrl(isData.getMdm_auth_url());
				mdmMasterRes.setLoginId(isData.getLogin_id());
				mdmMasterRes.setLoginPassword(isData.getLogin_password());
				mdmMasterRes.setFtpLogin(isData.getFtp_login());
				mdmMasterRes.setFtpAddress(isData.getFtp_address());
				mdmMasterRes.setFtpPassword(isData.getFtp_password());
				mdmMasterRes.setIsActive(isData.isIs_active());
				mdmMasterRes.setCreatedBy(isData.getCreated_by());
				mdmMasterRes.setCreatedOn(isData.getCreated_on() != null ? dateConverter.convertDateToHesString(isData.getCreated_on()) : mdmdMasterRequest.getReplaceBy());
				mdmMasterRes.setUpdatedBy(isData.getUpdated_by());
				mdmMasterRes.setUpdatedOn(isData.getUpdated_on() != null ? dateConverter.convertDateToHesString(isData.getUpdated_on()) : mdmdMasterRequest.getReplaceBy());

				mdmMasterResList.add(mdmMasterRes);
			}
			response.setCode(200);
			response.setData(mdmMasterResList);
		} catch (Exception e) {
			LOG.info("Issue in get MDM master List data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getMdmMasterLog(MdmMasterRequest req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Process for get MDM Master Log called: ");
		try {
			List<MdmMasterLogs> mdmMasterLogList = new ArrayList<MdmMasterLogs>();
			String table = meterConfiguration.getKeyspace() + "." + Tables.MDM_MASTER_LOGS;
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table);
			if (req.getMdmId() != null && !req.getMdmId().isEmpty()) {
				queryBuilder.append(" where mdm_id = '").append(req.getMdmId()).append("'");
			}
			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<MdmMasterLogs> mdmMasterLogs = new ArrayList<MdmMasterLogs>();
			mdmMasterLogs = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<MdmMasterLogs>>() {
			});

			for (MdmMasterLogs mdmMaster : mdmMasterLogs) {

				MdmMasterResponse mdmMasterRes = new MdmMasterResponse();

				mdmMasterRes.setMdmId(mdmMaster.getMdm_id());
				mdmMasterRes.setMdmName(mdmMaster.getMdm_name());
				mdmMasterRes.setMdmAuthUrl(mdmMaster.getMdm_auth_url());
				mdmMasterRes.setLoginId(mdmMaster.getLogin_id());
				mdmMasterRes.setLoginPassword(mdmMaster.getLogin_password());
				mdmMasterRes.setFtpLogin(mdmMaster.getFtp_login());
				mdmMasterRes.setFtpAddress(mdmMaster.getFtp_address());
				mdmMasterRes.setFtpPassword(mdmMaster.getFtp_password());
				mdmMasterRes.setIsActive(mdmMaster.getIs_active());
				mdmMasterRes.setCreatedBy(mdmMaster.getCreated_by());
				mdmMasterRes.setCreatedOn(mdmMaster.getCreated_on() != null
						? dateConverter.convertDateToHesString(mdmMaster.getCreated_on())
						: req.getReplaceBy());
				mdmMasterRes.setUpdatedBy(mdmMaster.getUpdated_by());
				mdmMasterRes.setUpdatedOn(mdmMaster.getUpdated_on() != null
						? dateConverter.convertDateToHesString(mdmMaster.getUpdated_on())
						: req.getReplaceBy());
				mdmMasterLogList.add(mdmMaster);
			}
			response.setCode(200);
			response.setData(mdmMasterLogList);
		} catch (Exception e) {
			LOG.info("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateMDMIdHierarchyWise(MdmMasterRequest req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Process for update mdmId hierarchy wise: ");
		ErrorData error = new ErrorData();
		try {
			List<DevicesInfo> devicesList = null;
			if (req.getUserId() == null || req.getUserId().isEmpty()) {

				error.setErrorMessage("Invalid or missing UserId");
				response.setCode(400);
				response.setError(true);
				response.addErrorMessage(error);
				return response;

			}
			if (req.getHier().getName() == null || req.getHier().getName().isEmpty() 
					|| req.getHier().getValue() == null || req.getHier().getValue().isEmpty()) {

				error.setErrorMessage("Invalid or missing hierarchy");
				response.setCode(400);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}
			if (!StringUtils.isEmpty(req.getMdmId())) {
				Optional<MdmMaster> mdmMasterInfo = mdmdMasterRepository.findById(req.getMdmId());

				if (!mdmMasterInfo.isPresent()) {
					error.setErrorMessage(req.getMdmId() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
			}
			String levelName = ExternalConstants.getUILevelValue(req.getHier().getName());
			devicesList = devicesInfoService.getDevicesList(levelName, req.getHier().getValue());
			List<MdmPushLogs> pushLogList = new ArrayList<>();

			for (DevicesInfo isData : devicesList) {

				MdmPushLogs pushLogs = new MdmPushLogs();
				Optional<MdmPushLogs> mdmPush = mdmPushLogRepository.findById(isData.getDevice_serial_number());
				if (mdmPush.isPresent()) {
					pushLogs = mdmPush.get();
					pushLogs.setMdm_id(req.getMdmId());
					pushLogs.setUpdated_by(req.getUserId());
					pushLogs.setUpdated_on(new Date(System.currentTimeMillis()));
				}
				else {
					pushLogs.setDevice_serial_number(isData.getDevice_serial_number());
					pushLogs.setDevice_type(isData.getDevice_type());
					pushLogs.setManufacturer(isData.getMeter_type());
					pushLogs.setMdm_id(req.getMdmId());
					pushLogs.setDevice_status(isData.getCommissioning_status());
					pushLogs.setCreated_by(req.getUserId());
					pushLogs.setCreated_on(new Date(System.currentTimeMillis()));
				}
				pushLogList.add(pushLogs);
			}
			if (pushLogList.size() > 0) {
				mdmPushLogRepository.saveAll(pushLogList);
			}
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.info("Issue in update mdmId hierarchy wise due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

}
