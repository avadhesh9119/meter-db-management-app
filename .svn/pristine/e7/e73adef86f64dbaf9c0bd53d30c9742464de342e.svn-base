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
import com.global.meter.business.model.UserHierMapping;
import com.global.meter.business.model.UserHierMappingLog;
import com.global.meter.data.repository.UserHierMappingLogRepository;
import com.global.meter.data.repository.UserHierMappingRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.IamUserHierarchy;
import com.global.meter.inventive.models.UserHierarchyMappingReq;
import com.global.meter.inventive.models.UsersHierMappingDataRequest;
import com.global.meter.inventive.models.UsersHierMappingDataResponse;
import com.global.meter.inventive.service.UsersHierMappingService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class UsersHierMappingServiceImpl implements UsersHierMappingService {
	private static final Logger LOG = LoggerFactory.getLogger(UsersHierMappingServiceImpl.class);

	@Autowired
	UserHierMappingRepository userRepo;
	
	@Autowired
	UserHierMappingLogRepository userRepoLog;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Override
	public CommonResponse addUserHierMapping(UserHierarchyMappingReq dataRequest) {
		List<UserHierMapping> userHierMapping = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		int mappingId = 0;
		String table = meterConfiguration.getKeyspace() + "." + Tables.USER_HIER_MAPPING;

		LOG.info("Wrapping Users Hier Mapping Data to save in DB:--> ");

		try {

				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT COALESCE(max(mapping_id), 0)+1 as mapping_id FROM ").append(table);

				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<UserHierMapping> id = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<UserHierMapping>>() {
						});
				mappingId = id.stream().iterator().next().getMapping_id();

				for(IamUserHierarchy hier: dataRequest.getHierarchyList()) {
					
				UserHierMapping user = new UserHierMapping();
					
			    user.setUser_id(dataRequest.getUserId());
				user.setMapping_id(mappingId);
				user.setCreated_by(dataRequest.getCreatedBy());
					
				user.setCreated_on(new Date(System.currentTimeMillis()));
				user.setCompany_id(hier.getCompanyId());
				user.setLevel1_id(hier.getLevel1Id());
				user.setLevel2_id(hier.getLevel2Id());
				user.setLevel3_id(hier.getLevel3Id());
				user.setLevel4_id(hier.getLevel4Id());
				user.setLevel5_id(hier.getLevel5Id());
				user.setLevel6_id(hier.getLevel6Id());
				mappingId++;
				userHierMapping.add(user);
				}

			userRepo.saveAll(userHierMapping);
			LOG.info("Users Hier mapping Info Successfully Stored in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.error("Issue in storing data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateUserHierMapping(UserHierarchyMappingReq dataRequest) {
		List<UserHierMapping> userHierMapping = new ArrayList<>();
		List<UserHierMapping> userHierMappingLogs = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		int mappingId = 0;
		String table = meterConfiguration.getKeyspace() + "." + Tables.USER_HIER_MAPPING;
		LOG.info("Users hier mapping Info Data to update in DB:--> ");

		try {
				if (!StringUtils.isEmpty(dataRequest.getUserId())) {
					Optional<List<UserHierMapping>> userHierRepo = userRepo.getUserByUserId(dataRequest.getUserId());

					if (userHierRepo.get().isEmpty()) {
						error.setErrorMessage(
								dataRequest.getUserId() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					userHierMappingLogs = userHierRepo.get();
					
				}
				 String jsonSource = CommonUtils.getMapper().writeValueAsString(userHierMappingLogs);

		            List<UserHierMappingLog> userLogs = CommonUtils.getMapper().readValue(jsonSource, new TypeReference<List<UserHierMappingLog>>() {});
		 				userRepoLog.saveAll(userLogs);
				userRepo.deleteUserByUserId(dataRequest.getUserId());
				
				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT COALESCE(max(mapping_id), 0)+1 as mapping_id FROM ").append(table);

				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<UserHierMapping> id = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<UserHierMapping>>() {
						});
				mappingId = id.stream().iterator().next().getMapping_id();

				for(IamUserHierarchy hier: dataRequest.getHierarchyList()) {
					
				UserHierMapping user = new UserHierMapping();
					
			    user.setUser_id(dataRequest.getUserId());
				user.setMapping_id(mappingId);
				user.setLast_updated_by(dataRequest.getUpdatedBy());
					
				user.setLast_updated_on(new Date(System.currentTimeMillis()));
				user.setCompany_id(hier.getCompanyId());
				user.setLevel1_id(hier.getLevel1Id());
				user.setLevel2_id(hier.getLevel2Id());
				user.setLevel3_id(hier.getLevel3Id());
				user.setLevel4_id(hier.getLevel4Id());
				user.setLevel5_id(hier.getLevel5Id());
				user.setLevel6_id(hier.getLevel6Id());
				mappingId++;
				userHierMapping.add(user);
				}

			userRepo.saveAll(userHierMapping);
				
			LOG.info("User Hier mapping Info Successfully Updated in DB.");
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in updating data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getUserHierMapping(UsersHierMappingDataRequest usersHierMappingDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Users Hier Mapping Info Data to get from DB:--> ");

		try {
			UsersHierMappingDataResponse userDataRes = new UsersHierMappingDataResponse();

			if (!StringUtils.isEmpty(usersHierMappingDataRequest.getMappingId())) {
				Optional<UserHierMapping> user = userRepo.findById(usersHierMappingDataRequest.getMappingId());

				if (!user.isPresent()) {
					error.setErrorMessage(
							usersHierMappingDataRequest.getMappingId() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				UserHierMapping userHierMap = user.get();

				userDataRes.setMappingId(userHierMap.getMapping_id());
				userDataRes.setCompanyId(userHierMap.getCompany_id());
				userDataRes.setCreatedBy(userHierMap.getCreated_by());
				userDataRes.setCreatedOn(String.valueOf(new Date(System.currentTimeMillis())));
				userDataRes.setIpAddress(userHierMap.getIp_address());
				userDataRes.setIsActive(userHierMap.getIs_active());
				userDataRes.setIsDefault(userHierMap.getIs_default());
				userDataRes.setLastUpdatedBy(userHierMap.getLast_updated_by());
				userDataRes.setLastUpdatedOn(String.valueOf(new Date(System.currentTimeMillis())));
				userDataRes.setLevel1Id(userHierMap.getLevel1_id());
				userDataRes.setLevel2Id(userHierMap.getLevel2_id());
				userDataRes.setLevel3Id(userHierMap.getLevel3_id());
				userDataRes.setLevel4Id(userHierMap.getLevel4_id());
				userDataRes.setLevel5Id(userHierMap.getLevel5_id());
				userDataRes.setLevel6Id(userHierMap.getLevel6_id());
				userDataRes.setUserId(userHierMap.getUser_id());

			}

			response.setData(userDataRes);
			LOG.info("User Hier Mapping Service Data Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse deleteUserHierMapping(UsersHierMappingDataRequest usersHierMappingDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		UserHierMapping userHierMap = new UserHierMapping();
		LOG.info("User Info Data to delete User Hier Mapping Info from DB:--> ");

		try {

			if (!StringUtils.isEmpty(usersHierMappingDataRequest.getMappingId())) {
				Optional<UserHierMapping> users = userRepo.findById(usersHierMappingDataRequest.getMappingId());

				if (!users.isPresent()) {
					error.setErrorMessage(
							usersHierMappingDataRequest.getMappingId() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				userHierMap = users.get();
				userRepo.deleteById(userHierMap.getMapping_id());
				LOG.info("User Hier Mapping Info Successfully Deleted from DB.");

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
	public CommonResponse getUserHierMappingList() {
		CommonResponse response = new CommonResponse();

		LOG.info("User Info Data to get user hier mapping list from DB:--> ");

		try {

			List<UserHierMapping> userHier = userRepo.findAll();
			List<UsersHierMappingDataResponse> userResList = new ArrayList<>();

			for (UserHierMapping userHierMap : userHier) {
				UsersHierMappingDataResponse userDataRes = new UsersHierMappingDataResponse();

				userDataRes.setMappingId(userHierMap.getMapping_id());
				userDataRes.setCompanyId(userHierMap.getCompany_id());
				userDataRes.setCreatedBy(userHierMap.getCreated_by());
				userDataRes.setCreatedOn(String.valueOf(new Date(System.currentTimeMillis())));
				userDataRes.setIpAddress(userHierMap.getIp_address());
				userDataRes.setIsActive(userHierMap.getIs_active());
				userDataRes.setIsDefault(userHierMap.getIs_default());
				userDataRes.setLastUpdatedBy(userHierMap.getLast_updated_by());
				userDataRes.setLastUpdatedOn(String.valueOf(new Date(System.currentTimeMillis())));
				userDataRes.setLevel1Id(userHierMap.getLevel1_id());
				userDataRes.setLevel2Id(userHierMap.getLevel2_id());
				userDataRes.setLevel3Id(userHierMap.getLevel3_id());
				userDataRes.setLevel4Id(userHierMap.getLevel4_id());
				userDataRes.setLevel5Id(userHierMap.getLevel5_id());
				userDataRes.setLevel6Id(userHierMap.getLevel6_id());
				userDataRes.setUserId(userHierMap.getUser_id());

				userResList.add(userDataRes);
			}

			response.setData(userResList);
			LOG.info("User Hier Mapping List Data Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting user hier mapping list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getUserHierMappingIdList() {
		CommonResponse response = new CommonResponse();

		LOG.info("Users Info Data to get user hier mapping id list from DB:--> ");

		try {

			List<UserHierMapping> user = userRepo.findAll();

			response.setData(user.stream().map(station -> station.getMapping_id()).collect(Collectors.toList()));
			LOG.info("User Hier Mapping Id List Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting user hier mapping id list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

}
