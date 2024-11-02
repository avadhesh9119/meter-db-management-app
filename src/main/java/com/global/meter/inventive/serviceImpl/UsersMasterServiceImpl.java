package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.UserMst;
import com.global.meter.data.repository.UserMstRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.UsersMasterDataRequest;
import com.global.meter.inventive.models.UsersMasterDataResponse;
import com.global.meter.inventive.service.UsersMasterService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class UsersMasterServiceImpl implements UsersMasterService {
	private static final Logger LOG = LoggerFactory.getLogger(UsersMasterServiceImpl.class);

	@Autowired
	UserMstRepository userRepo;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Override
	public CommonResponse addUser(List<UsersMasterDataRequest> userMasterDataRequest) {
		List<UserMst> userMaster = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		int autoId = 0;
		String table = meterConfiguration.getKeyspace() + "." + Tables.USER_MST;

		LOG.info("Wrapping Users Data to save in DB:--> ");

		try {
			for (UsersMasterDataRequest dataRequest : userMasterDataRequest) {
				UserMst user = new UserMst();

				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT COALESCE(max(auto_id), 0)+1 as auto_id FROM ").append(table);

				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<UserMst> id = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<UserMst>>() {
				});
				autoId = id.stream().iterator().next().getAuto_id();

				user.setAuto_id(autoId);
				user.setUser_id(dataRequest.getUserId());
				user.setUser_name(dataRequest.getUserName());
				user.setUser_code(dataRequest.getUserCode());
				user.setLogin_id(dataRequest.getLoginId());
				user.setFather_name(dataRequest.getFatherName());
				user.setUser_type_name(dataRequest.getUserTypeName());
				user.setMobile_no(dataRequest.getMobileNo());
				user.setEmail_id(dataRequest.getEmailId());
				user.setDepartment_code(dataRequest.getDepartmentCode());
				user.setDepartment_name(dataRequest.getDepartmentName());
				user.setDesignation_code(dataRequest.getDepartmentCode());
				user.setDesignation_name(dataRequest.getDesignationName());
				user.setPhoto(dataRequest.getPhoto());
				user.setChange_password_flag(dataRequest.getChangePasswordFlag());
				user.setIdentity_type_name(dataRequest.getIdentityTypeName());
				user.setIdentity_no(dataRequest.getIdentityNo());
				user.setUser_remark_id(dataRequest.getUserRemarkId());
				user.setIs_active(dataRequest.getIsActive());
				user.setCreated_by(dataRequest.getCreatedBy());
				user.setCreated_on(new Date(System.currentTimeMillis()));
				user.setLast_updated_by(dataRequest.getLastUpdatedBy());
				user.setLast_updated_on(new Date(System.currentTimeMillis()));
				user.setIp_address(dataRequest.getIpAddress());
				user.setStart_date(dateConverter.convertStringToLocalDate(dataRequest.getStartDate()));
				if(dataRequest.getEndDate() != null && !dataRequest.getEndDate().isEmpty()) 
				{
					user.setEnd_date(dateConverter.convertStringToLocalDate(dataRequest.getEndDate()));

				}
				user.setAttribute1(dataRequest.getAttribute1());
				user.setAttribute2(dataRequest.getAttribute2());
				user.setAttribute3(dataRequest.getAttribute3());
				user.setAttribute4(dataRequest.getAttribute4());
				user.setAttribute5(dataRequest.getAttribute5());
				user.setReporting_user_id(dataRequest.getReportingUserId());

				userMaster.add(user);
			}

			userRepo.saveAll(userMaster);
			LOG.info("Users Info Successfully Stored in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
			response.setAutoId(String.valueOf(autoId));
		} catch (Exception e) {
			LOG.error("Issue in storing data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateUser(List<UsersMasterDataRequest> userMasterDataRequest) {
		List<UserMst> user = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Users Info Data to update in DB:--> ");

		try {
			for (UsersMasterDataRequest dataRequest : userMasterDataRequest) {
				UserMst userMst = new UserMst();
				if (String.valueOf(dataRequest.getLastUpdatedBy()) == null
						|| StringUtils.isEmpty(dataRequest.getLastUpdatedBy())) {
					response.setMessage("Last Updated by can not be empty");
					response.setCode(404);
					response.setError(true);
					return response;
				}

				if (!StringUtils.isEmpty(dataRequest.getUserId())) {
					Optional<UserMst> userMstRepo = userRepo.findByUserId(dataRequest.getUserId());

					if (!userMstRepo.isPresent()) {
						error.setErrorMessage(dataRequest.getUserId() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					userMst = userMstRepo.get();
				}

//				userMst.setAuto_id(dataRequest.getAutoId());
				userMst.setAttribute1(dataRequest.getAttribute1());
				userMst.setAttribute2(dataRequest.getAttribute2());
				userMst.setAttribute3(dataRequest.getAttribute3());
				userMst.setAttribute4(dataRequest.getAttribute4());
				userMst.setAttribute5(dataRequest.getAttribute5());
				userMst.setChange_password_flag(dataRequest.getChangePasswordFlag());
				userMst.setCreated_by(dataRequest.getCreatedBy());
				userMst.setCreated_on(new Date(System.currentTimeMillis()));
				userMst.setDepartment_code(dataRequest.getDepartmentCode());
				userMst.setDepartment_name(dataRequest.getDepartmentName());
				userMst.setDesignation_code(dataRequest.getDesignationCode());
				userMst.setDesignation_name(dataRequest.getDesignationName());
				userMst.setEmail_id(dataRequest.getEmailId());
				if(dataRequest.getEndDate() != null && !dataRequest.getEndDate().isEmpty()) 
				{
					userMst.setEnd_date(dateConverter.convertStringToLocalDate(dataRequest.getEndDate()));

				}
				userMst.setFather_name(dataRequest.getFatherName());
				userMst.setIdentity_no(dataRequest.getIdentityNo());
				userMst.setIdentity_type_name(dataRequest.getIdentityTypeName());
				userMst.setIp_address(dataRequest.getIpAddress());
				userMst.setIs_active(dataRequest.getIsActive());
				userMst.setLast_updated_by(dataRequest.getLastUpdatedBy());
				userMst.setLast_updated_on(new Date(System.currentTimeMillis()));
				userMst.setLogin_id(dataRequest.getLoginId());
				userMst.setMobile_no(dataRequest.getMobileNo());
				userMst.setPhoto(dataRequest.getPhoto());
				userMst.setReporting_user_id(dataRequest.getReportingUserId());
				userMst.setStart_date(dateConverter.convertStringToLocalDate(dataRequest.getStartDate()));
				userMst.setUser_code(dataRequest.getUserCode());
				userMst.setUser_id(dataRequest.getUserId());
				userMst.setUser_name(dataRequest.getUserName());
				userMst.setUser_remark_id(dataRequest.getUserRemarkId());
				userMst.setUser_type_name(dataRequest.getUserTypeName());

				user.add(userMst);
				response.setAutoId(String.valueOf(userMst.getAuto_id()));

			}
			userRepo.saveAll(user);
			LOG.info("User Info Successfully Updated in DB.");
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in updating data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getUser(UsersMasterDataRequest userMasterDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Users Info Data to get from DB:--> ");

		try {
			UsersMasterDataResponse userDataRes = new UsersMasterDataResponse();

			if (!StringUtils.isEmpty(userMasterDataRequest.getAutoId())) {
				Optional<UserMst> user = userRepo.findById(userMasterDataRequest.getAutoId());

				if (!user.isPresent()) {
					error.setErrorMessage(
							userMasterDataRequest.getAutoId() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				UserMst userMst = user.get();

				userDataRes.setAutoId(String.valueOf(userMst.getAuto_id()));
				userDataRes.setAttribute1(userMst.getAttribute1());
				userDataRes.setAttribute2(userMst.getAttribute2());
				userDataRes.setAttribute3(userMst.getAttribute3());
				userDataRes.setAttribute4(userMst.getAttribute4());
				userDataRes.setAttribute5(userMst.getAttribute5());
				userDataRes.setChangePasswordFlag(String.valueOf(userMst.getChange_password_flag()));
				userDataRes.setCreatedBy(String.valueOf(userMst.getCreated_by()));
				userDataRes.setCreatedOn(String.valueOf(new Date(System.currentTimeMillis())));
				userDataRes.setDepartmentCode(userMst.getDepartment_code());
				userDataRes.setDepartmentName(userMst.getDepartment_name());
				userDataRes.setDesignationCode(userMst.getDesignation_code());
				userDataRes.setDesignationName(userMst.getDesignation_name());
				userDataRes.setEmailId(userMst.getEmail_id());
				userDataRes.setEndDate(userMst.getEnd_date() != null ? dateConverter.convertDateToHesString(userMst.getEnd_date()) : "-");
				userDataRes.setFatherName(userMst.getFather_name());
				userDataRes.setIdentityNo(userMst.getIdentity_no());
				userDataRes.setIdentityTypeName(userMst.getIdentity_type_name());
				userDataRes.setIpAddress(userMst.getIp_address());
				userDataRes.setIsActive(String.valueOf(userMst.getIs_active()));
				userDataRes.setLastUpdatedBy(String.valueOf(userMst.getLast_updated_by()));
				userDataRes.setLastUpdatedOn(String.valueOf(new Date(System.currentTimeMillis())));
				userDataRes.setLoginId(userMst.getLogin_id());
				userDataRes.setMobileNo(userMst.getMobile_no());
				userDataRes.setPhoto(userMst.getPhoto());
				userDataRes.setReportingUserId(String.valueOf(userMst.getReporting_user_id()));
				userDataRes.setStartDate(dateConverter.convertDateToHesString(userMst.getStart_date()));
				userDataRes.setUserCode(userMst.getUser_code());
				userDataRes.setUserId(String.valueOf(userMst.getUser_id()));
				userDataRes.setUserName(userMst.getUser_name());
				userDataRes.setUserRemarkId(String.valueOf(userMst.getUser_remark_id()));
				userDataRes.setUserTypeName(userMst.getUser_type_name());

			}

			response.setData(userDataRes);
			LOG.info("User Service Data Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse deleteUser(UsersMasterDataRequest userMasterDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		UserMst userMst = new UserMst();
		LOG.info("User Info Data to delete from DB:--> ");

		try {

			if (!StringUtils.isEmpty(userMasterDataRequest.getAutoId())) {
				Optional<UserMst> users = userRepo.findById(userMasterDataRequest.getAutoId());

				if (!users.isPresent()) {
					error.setErrorMessage(
							userMasterDataRequest.getAutoId() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				userMst = users.get();
				userRepo.deleteById(userMst.getAuto_id());
				LOG.info("User Info Successfully Deleted from DB.");

				response.setCode(200);
				response.setMessage(ExternalConstants.Message.DELETED);
			}

		} catch (Exception e) {
			LOG.error("Issue in deleting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getUserList() {

		CommonResponse response = new CommonResponse();

		LOG.info("User Info Data to get user list from DB:--> ");

		try {

			List<UserMst> user = userRepo.findAll();
			List<UsersMasterDataResponse> userResList = new ArrayList<>();

			for (UserMst userMst : user) {
				UsersMasterDataResponse userDataRes = new UsersMasterDataResponse();

				userDataRes.setAutoId(String.valueOf(userMst.getAuto_id()));
				userDataRes.setAttribute1(userMst.getAttribute1());
				userDataRes.setAttribute2(userMst.getAttribute2());
				userDataRes.setAttribute3(userMst.getAttribute3());
				userDataRes.setAttribute4(userMst.getAttribute4());
				userDataRes.setAttribute5(userMst.getAttribute5());
				userDataRes.setChangePasswordFlag(String.valueOf(userMst.getChange_password_flag()));
				userDataRes.setCreatedBy(String.valueOf(userMst.getCreated_by()));
				userDataRes.setCreatedOn(String.valueOf(new Date(System.currentTimeMillis())));
				userDataRes.setDepartmentCode(userMst.getDepartment_code());
				userDataRes.setDepartmentName(userMst.getDepartment_name());
				userDataRes.setDesignationCode(userMst.getDesignation_code());
				userDataRes.setDesignationName(userMst.getDesignation_name());
				userDataRes.setEmailId(userMst.getEmail_id());
				userDataRes.setEndDate(dateConverter.convertDateToString(userMst.getEnd_date()));
				userDataRes.setFatherName(userMst.getFather_name());
				userDataRes.setIdentityNo(userMst.getIdentity_no());
				userDataRes.setIdentityTypeName(userMst.getIdentity_type_name());
				userDataRes.setIpAddress(userMst.getIp_address());
				userDataRes.setIsActive(String.valueOf(userMst.getIs_active()));
				userDataRes.setLastUpdatedBy(String.valueOf(userMst.getLast_updated_by()));
				userDataRes.setLastUpdatedOn(String.valueOf(new Date(System.currentTimeMillis())));
				userDataRes.setLoginId(userMst.getLogin_id());
				userDataRes.setMobileNo(userMst.getMobile_no());
				userDataRes.setPhoto(userMst.getPhoto());
				userDataRes.setReportingUserId(String.valueOf(userMst.getReporting_user_id()));
				userDataRes.setStartDate(dateConverter.convertDateToString(userMst.getStart_date()));
				userDataRes.setUserCode(userMst.getUser_code());
				userDataRes.setUserId(String.valueOf(userMst.getUser_id()));
				userDataRes.setUserName(userMst.getUser_name());
				userDataRes.setUserRemarkId(String.valueOf(userMst.getUser_remark_id()));
				userDataRes.setUserTypeName(userMst.getUser_type_name());

				userResList.add(userDataRes);
			}

			response.setData(userResList);
			LOG.info("User List Data Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting user list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getUserIdList() {
		CommonResponse response = new CommonResponse();

		LOG.info("Users Info Data to get user id list from DB:--> ");

		try {

			List<UserMst> user = userRepo.findAll();

			response.setData(user.stream().map(station -> station.getAuto_id()).collect(Collectors.toList()));
			LOG.info("User Name List Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting user id list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

}
