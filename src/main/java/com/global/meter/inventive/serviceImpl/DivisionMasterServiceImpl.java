package com.global.meter.inventive.serviceImpl;

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
import com.global.meter.business.model.DivisionMaster;
import com.global.meter.business.model.UserHierMapping;
import com.global.meter.data.repository.CircleMasterRepository;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.data.repository.DivisionMasterRepository;
import com.global.meter.data.repository.OwnersRepository;
import com.global.meter.data.repository.SubdivisionsMasterRepository;
import com.global.meter.data.repository.UserHierMappingRepository;
import com.global.meter.data.repository.ZoneMasterRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DivisionDataResponse;
import com.global.meter.inventive.models.DivisionMasterDataRequest;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.service.DivisionMasterService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DivisionMasterServiceImpl implements DivisionMasterService {

	private static final Logger LOG = LoggerFactory.getLogger(FeedersServiceImpl.class);

	@Autowired
	DivisionMasterRepository divisionRepository;

	@Autowired
	CircleMasterRepository circleMasterRepository;

	@Autowired
	ZoneMasterRepository zoneRepo;

	@Autowired
	OwnersRepository ownersRepository;

	@Autowired
	DevicesInfoRepository devicesInfoRepository;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	SubdivisionsMasterRepository subdivisionRepository;
	
	@Autowired
	UserHierMappingRepository userHierMappingRepository;

	@Override
	public CommonResponse addDivision(List<DivisionMasterDataRequest> divisionDataReqList) {

		List<DivisionMaster> divisions = new ArrayList<>();
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		String hesDivisionId = "";
		String table = meterConfiguration.getKeyspace() + "." + Tables.DIVISION_MASTER;

		LOG.info("Wrapping Division Data to save in DB:--> ");

		try {

			for (DivisionMasterDataRequest divisionData : divisionDataReqList) {
				DivisionMaster division = new DivisionMaster();

					if (!StringUtils.isEmpty(divisionData.getLevel4Id())) {
					Optional<DivisionMaster> divisionRepo = divisionRepository
							.findById(Integer.valueOf(divisionData.getLevel4Id()));

					if (divisionRepo.isPresent()) {
						error.setErrorMessage(
								divisionData.getLevel4Id() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}
				
				StringBuilder queryBuilder = new StringBuilder();

				queryBuilder.append("SELECT max(hes_division_id) as hes_division_id FROM ").append(table);

				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<DivisionMaster> id = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<DivisionMaster>>() {

						});

				hesDivisionId = String.valueOf(Integer.valueOf(id.stream().iterator().next().getHes_division_id() != null 
			    		? id.stream().iterator().next().getHes_division_id() : "0")+1);
				division.setI_am_id(Integer.valueOf(divisionData.getLevel4Id()));
				division.setHes_division_id(hesDivisionId);
				division.setHes_circle_id(divisionData.getLevel3Id());
				division.setDivision_name(divisionData.getDivisionName());
				division.setDivision_code(divisionData.getDivisionCode());
				division.setCreated(new Date(System.currentTimeMillis()));
				division.setLatitude(divisionData.getLatitude());
				division.setLongitude(divisionData.getLongitude());
				division.setSource(divisionData.getSource());
				division.setUser_id(divisionData.getUserId());
				division.setIs_active(divisionData.getIsActive());

				divisions.add(division);
			}
			divisionRepository.saveAll(divisions);
			LOG.info("Division Successfully Stored in DB.");
			response.setHesId(hesDivisionId);
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.error("Issue in storing data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateDivision(List<DivisionMasterDataRequest> divisionDataReqList) {

		List<DivisionMaster> divisions = new ArrayList<>();
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Wrapping Division Data to update in DB:--> ");

		try {

			for (DivisionMasterDataRequest divisionData : divisionDataReqList) {

				DivisionMaster division = new DivisionMaster();

				if (!StringUtils.isEmpty(divisionData.getLevel4Id())) {
					Optional<DivisionMaster> divisionRepo = divisionRepository
							.findById(Integer.valueOf(divisionData.getLevel4Id()));

					if (!divisionRepo.isPresent()) {
						error.setErrorMessage(
								divisionData.getLevel4Id() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}

					division = divisionRepo.get();
				}
				
				division.setHes_circle_id(divisionData.getLevel3Id() != null ? divisionData.getLevel3Id()
						: division.getHes_circle_id());
				division.setDivision_name(divisionData.getDivisionName() != null ? divisionData.getDivisionName()
						: division.getDivision_name());
				division.setDivision_code(divisionData.getDivisionCode() != null ? divisionData.getDivisionCode()
						: division.getDivision_code());
				division.setLatitude(
						divisionData.getLatitude() != null ? divisionData.getLatitude() : division.getLatitude());
				division.setLongitude(
						divisionData.getLongitude() != null ? divisionData.getLongitude() : division.getLongitude());
				division.setModified(new Date(System.currentTimeMillis()));
				division.setUpdated_by(
						divisionData.getUpdatedBy() != null ? divisionData.getUpdatedBy() : division.getUpdated_by());
				division.setIs_active(
						divisionData.getIsActive() != null ? divisionData.getIsActive() : division.getIs_active());

				divisions.add(division);
				response.setHesId(division.getHes_division_id());

			}
			divisionRepository.saveAll(divisions);
			LOG.info("Division Successfully Updated in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in Updating data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

//	@Override
//	public CommonResponse deleteDivision(DivisionMasterDataRequest divisionDataRequests) {
//		CommonResponse response = new CommonResponse();
//		ErrorData error = new ErrorData();
//		LOG.info("Wrapping Division Data to delete from DB:--> ");
//
//		try {
//			if (!StringUtils.isEmpty(divisionDataRequests.getHesDivisionId())) {
//				Optional<DivisionMaster> divisions = divisionRepository
//						.findByhesDivisionId(divisionDataRequests.getHesDivisionId());
//
//				String table = MeterDBAppStarter.keyspace + "." + Tables.SUBDIVISIONS_MASTER;
//				String query = "Select * from " + table + " Where hes_division_id = '"
//						+ divisionDataRequests.getHesDivisionId() + "'";
//				String queryString = CommonUtils.getMapper()
//						.writeValueAsString(prestoJdbcTemplate.queryForList(query).size());
//
//				Integer count = CommonUtils.getMapper().readValue(queryString, new TypeReference<Integer>() {
//				});
//
//				if (count != null && count > 0) {
//
//					error.setErrorMessage(
//							"This division is associated in the network. Please de-associate it and then try again");
//					response.setError(true);
//					response.addErrorMessage(error);
//
//					return response;
//
//				} else {
//
//					if (!divisions.isPresent()) {
//						error.setErrorMessage(
//								divisionDataRequests.getHesDivisionId() + " : " + ExternalConstants.Message.NOT_EXISTS);
//						response.setCode(404);
//						response.setError(true);
//						response.addErrorMessage(error);
//
//						return response;
//					}
//					DivisionMaster division = divisions.get();
//					LOG.info("Division Successfully Deleted from DB.");
//					divisionRepository.deleteById(division.getI_am_id());
//					response.setiAmId(String.valueOf(division.getI_am_id()));
//					response.setCode(200);
//					response.setMessage(ExternalConstants.Message.DELETED);
//				}
//			}
//		} catch (Exception e) {
//			LOG.error("Issue in Deleting data due to : " + e.getMessage());
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//		}
//		return response;
//	}

	@Override
	public CommonResponse getDivision(DivisionMasterDataRequest divisionDataRequests) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Wrapping Division Data to get from DB:--> ");

		try {
			DivisionDataResponse divisionDataResponse = new DivisionDataResponse();
			if (!StringUtils.isEmpty(divisionDataRequests.getLevel4Id())) {
				Optional<DivisionMaster> divisionRepo = divisionRepository
						.findById(Integer.parseInt(divisionDataRequests.getLevel4Id()));

				if (!divisionRepo.isPresent()) {
					error.setErrorMessage(
							divisionDataRequests.getLevel4Id() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				DivisionMaster division = divisionRepo.get();

				divisionDataResponse.setiAmId(String.valueOf(division.getI_am_id()));
				divisionDataResponse.setHesDivisionId(division.getHes_division_id());
				divisionDataResponse.setDivisionName(division.getDivision_name());
				divisionDataResponse.setDivisionCode(division.getDivision_code());
				divisionDataResponse.setCreated(
						division.getCreated() != null ? dateConverter.convertDateToString(division.getCreated())
								: null);
				divisionDataResponse.setLatitude(division.getLatitude());
				divisionDataResponse.setLongitude(division.getLongitude());
				divisionDataResponse.setModified(
						division.getModified() != null ? dateConverter.convertDateToString(division.getModified())
								: null);
				divisionDataResponse.setUtility(division.getOwner_name());
				divisionDataResponse.setSource(division.getSource());
				divisionDataResponse.setCreatedBy(division.getUser_id());
				divisionDataResponse.setLastupdatedBy(division.getUpdated_by());

			}

			response.setData(divisionDataResponse);
			LOG.info("Division Data Service Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in Getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getDivisionList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting feeder Data from DB:--> ");

		try {

			List<DivisionMaster> divisionsRepoList = divisionRepository.findAll();
			List<DivisionDataResponse> divisionResList = new ArrayList<>();

			for (DivisionMaster division : divisionsRepoList) {
				DivisionDataResponse divisionData = new DivisionDataResponse();

				divisionData.setiAmId(String.valueOf(division.getI_am_id()));
				divisionData.setHesDivisionId(division.getHes_division_id());
				divisionData.setDivisionName(division.getDivision_name());
				divisionData.setDivisionCode(division.getDivision_code());
				divisionData.setCreated(
						division.getCreated() != null ? dateConverter.convertDateToString(division.getCreated())
								: null);
				divisionData.setLatitude(division.getLatitude());
				divisionData.setLongitude(division.getLongitude());
				divisionData.setModified(
						division.getModified() != null ? dateConverter.convertDateToString(division.getModified())
								: null);
				divisionData.setUtility(division.getOwner_name());
				divisionData.setSource(division.getSource());
				divisionData.setCreatedBy(division.getUser_id());
				divisionData.setLastupdatedBy(division.getUpdated_by());

				divisionResList.add(divisionData);

			}
			response.setData(divisionResList);
			LOG.info("Division Data List Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in Getting Division list data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

//	@Override
//	public CommonResponse getDivisionNameList() {
//		CommonResponse response = new CommonResponse();
//		ErrorData error = new ErrorData();
//
//		try {
//
//			List<DivisionMaster> divisionRepoList = divisionRepository.findAll();
//
//			LOG.info("Get Division Name List Data from DB");
//			response.setData(divisionRepoList.stream().map(division -> division.getDivision_name())
//					.collect(Collectors.toList()));
//
//		} catch (Exception e) {
//			LOG.error("Issue in Getting division name list due to : " + e.getMessage());
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//		}
//		return response;
//
//	}

	@Override
	public CommonResponse getDivisionByCircle(DivisionMasterDataRequest divisionDataRequests) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		try {

			LOG.info("Get Division List Data from DB By circle:--> " + divisionDataRequests.getCircleCode());
			List<DivisionDataResponse> divisionResList = new ArrayList<>();

			String table = meterConfiguration.getKeyspace() + "." + Tables.DIVISION_MASTER;
			String tableCircle = meterConfiguration.getKeyspace() + "." + Tables.CIRCLE_MASTER;

			if (!StringUtils.isEmpty(divisionDataRequests.getCircleCode())) {
				StringBuilder queryBuilder = new StringBuilder();
				
				queryBuilder.append("select d.* from ").append(table).append(" as d inner join ")
				.append(tableCircle).append(" as u on d.hes_circle_id = cast(u.i_am_id as varchar)").append(" where u.circle_code = '")
				.append(divisionDataRequests.getCircleCode()).append("'");
				
				if(divisionDataRequests.getUserId() != null && !divisionDataRequests.getUserId().isEmpty()) {
				Optional<UserHierMapping> useHierMapping = userHierMappingRepository.findById(Integer.parseInt(divisionDataRequests.getUserId()));
				    if(useHierMapping.isPresent()) {
				       UserHierMapping userHier = useHierMapping.get();
				
				      if(userHier.getLevel4_id() != 0)
				      {
					   queryBuilder.append(" and d.i_am_id = ").append(userHier.getLevel4_id());
				      }
				    }   
				}	

				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<DivisionMaster> divisionList  = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<DivisionMaster>>() {

						});

				
				if (divisionList.size()==0) {
					error.setErrorMessage(
							divisionDataRequests.getCircleCode() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}


				for (DivisionMaster division : divisionList) {
					DivisionDataResponse divisionData = new DivisionDataResponse();

					divisionData.setDivisionName(division.getDivision_name());
					divisionData.setDivisionCode(division.getDivision_code());

					divisionResList.add(divisionData);

				}

			}

			response.setData(divisionResList);
			LOG.info("Division Data Service by Substation Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in Getting Division by substations due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

//	@Override
//	public CommonResponse getDivisionByUtility(DivisionMasterDataRequest divisionDataRequests) {
//		CommonResponse response = new CommonResponse();
//		ErrorData error = new ErrorData();
//
//		try {
//
//			LOG.info("Get Division List Data from DB By Utility:--> " + divisionDataRequests.getOwnerName());
//			List<DivisionDataResponse> divisionResList = new ArrayList<>();
//
//			if (!StringUtils.isEmpty(divisionDataRequests.getOwnerName())) {
//				Optional<List<DivisionMaster>> divisionRepoList = divisionRepository
//						.findByUtility(divisionDataRequests.getOwnerName());
//
//				if (divisionRepoList.get().isEmpty()) {
//					error.setErrorMessage(
//							divisionDataRequests.getOwnerName() + " : " + ExternalConstants.Message.NOT_EXISTS);
//					response.setCode(404);
//					response.setError(true);
//					response.addErrorMessage(error);
//					return response;
//				}
//
//				List<DivisionMaster> divisionDataList = divisionRepoList.get();
//
//				for (DivisionMaster division : divisionDataList) {
//					DivisionDataResponse divisionData = new DivisionDataResponse();
//
//					divisionData.setiAmId(String.valueOf(division.getI_am_id()));
//					divisionData.setHesDivisionId(division.getHes_division_id());
//					divisionData.setHesCircleId(division.getHes_circle_id());
//					divisionData.setHesZoneId(division.getHes_zone_id());
//					divisionData.setHesOwnerId(division.getOwner_id());
//					divisionData.setDivisionName(division.getDivision_name());
//					divisionData.setCreated(
//							division.getCreated() != null ? dateConverter.convertDateToString(division.getCreated())
//									: null);
//					divisionData.setLatitude(division.getLatitude());
//					divisionData.setLongitude(division.getLongitude());
//					divisionData.setModified(
//							division.getModified() != null ? dateConverter.convertDateToString(division.getModified())
//									: null);
//					divisionData.setUtility(division.getOwner_name());
//					divisionData.setZoneName(division.getZone_name());
//					divisionData.setCircleName(division.getCircle_name());
//					divisionData.setSource(division.getSource());
//					divisionData.setCreatedBy(division.getUser_id());
//					divisionData.setLastupdatedBy(division.getUpdated_by());
//
//					divisionResList.add(divisionData);
//				}
//			}
//			response.setData(divisionResList);
//			LOG.info("Division Data Service by Utility Response Success.");
//
//		} catch (Exception e) {
//			LOG.error("Issue in Getting feeder by utility due to : " + e.getMessage());
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//		}
//		return response;
//	}
}
