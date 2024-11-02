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
import com.global.meter.business.model.CircleMaster;
import com.global.meter.business.model.UserHierMapping;
import com.global.meter.data.repository.CircleMasterRepository;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.data.repository.DivisionMasterRepository;
import com.global.meter.data.repository.OwnersRepository;
import com.global.meter.data.repository.SubdivisionsMasterRepository;
import com.global.meter.data.repository.UserHierMappingRepository;
import com.global.meter.data.repository.ZoneMasterRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CircleMasterDataRequest;
import com.global.meter.inventive.models.CircleMasterDataResponse;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.service.CircleMasterService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class CircleMasterServiceImpl implements CircleMasterService {

	private static final Logger LOG = LoggerFactory.getLogger(SubstationsServiceImpl.class);

	@Autowired
	CircleMasterRepository circleMasterRepository;

	@Autowired
	ZoneMasterRepository zoneMasterRepo;

	@Autowired
	OwnersRepository ownersRepository;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	DevicesInfoRepository devicesInfoRepository;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	DivisionMasterRepository divisionRepository;

	@Autowired
	SubdivisionsMasterRepository subDivisionRepository;
	
	@Autowired
	UserHierMappingRepository userHierMappingRepository;

	@Override
	public CommonResponse addCircle(List<CircleMasterDataRequest> circleDataRequest) {
		List<CircleMaster> subStation = new ArrayList<>();
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		String hesCircleId = "";
		LOG.info("Wrapping circle Data to save in DB:--> ");

		try {
			String table = meterConfiguration.getKeyspace() + "." + Tables.CIRCLE_MASTER;

			for (CircleMasterDataRequest subDataRequest : circleDataRequest) {
				CircleMaster circle = new CircleMaster();

				
				if (!StringUtils.isEmpty(subDataRequest.getLevel3Id())) {
					Optional<CircleMaster> subStaRepo = circleMasterRepository
							.findById(Integer.valueOf(subDataRequest.getLevel3Id()));

					if (subStaRepo.isPresent()) {
						error.setErrorMessage(
								subDataRequest.getLevel3Id() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}
				
				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT max(hes_circle_id) as hes_circle_id FROM ").append(table);

				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<CircleMaster> id = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<CircleMaster>>() {
						});
				hesCircleId = String.valueOf(Integer.valueOf(id.stream().iterator().next().getHes_circle_id() != null 
			    		? id.stream().iterator().next().getHes_circle_id() : "0")+1);
				circle.setI_am_id(Integer.valueOf(subDataRequest.getLevel3Id()));
				circle.setHes_circle_id(hesCircleId);
				circle.setCircle_name(subDataRequest.getCircleName());
				circle.setCircle_code(subDataRequest.getCircleCode());
				circle.setHes_zone_id(subDataRequest.getLevel2Id());
				circle.setCreated(new Date(System.currentTimeMillis()));
				circle.setLatitude(subDataRequest.getLatitude());
				circle.setLongitude(subDataRequest.getLongitude());
				circle.setModified(new Date(System.currentTimeMillis()));
				circle.setSource(subDataRequest.getSource());
				circle.setUser_id(subDataRequest.getUserId());
				circle.setIs_active(subDataRequest.getIsActive());

				subStation.add(circle);

			}

			circleMasterRepository.saveAll(subStation);
			LOG.info("Circle Info Successfully Stored in DB.");
			response.setHesId(hesCircleId);
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.error("Issue in storing data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse updateCircle(List<CircleMasterDataRequest> circleDataRequest) {

		List<CircleMaster> substationsList = new ArrayList<>();
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Circle Info Data to update in DB:--> ");

		try {
			for (CircleMasterDataRequest subDataRequest : circleDataRequest) {
				CircleMaster circle = new CircleMaster();

				if (!StringUtils.isEmpty(subDataRequest.getLevel3Id())) {
					Optional<CircleMaster> subStaRepo = circleMasterRepository
							.findById(Integer.valueOf(subDataRequest.getLevel3Id()));

					if (!subStaRepo.isPresent()) {
						error.setErrorMessage(
								subDataRequest.getLevel3Id() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					circle = subStaRepo.get();
				}
				circle.setHes_zone_id(subDataRequest.getLevel2Id() != null ? subDataRequest.getLevel2Id()
						: circle.getHes_zone_id());
				circle.setCircle_code(
						subDataRequest.getCircleCode() != null ? subDataRequest.getCircleCode() : circle.getCircle_code());
				circle.setCircle_name(
						subDataRequest.getCircleName() != null ? subDataRequest.getCircleName() : circle.getCircle_name());
				circle.setLatitude(
						subDataRequest.getLatitude() != null ? subDataRequest.getLatitude() : circle.getLatitude());
				circle.setLongitude(
						subDataRequest.getLongitude() != null ? subDataRequest.getLongitude() : circle.getLongitude());
				circle.setModified(new Date(System.currentTimeMillis()));
				circle.setIs_active(
						subDataRequest.getIsActive() != null ? subDataRequest.getIsActive() : circle.getIs_active());

				circle.setUpdated_by(subDataRequest.getUpdatedBy());

				substationsList.add(circle);
				response.setHesId(circle.getHes_circle_id());
				
			}
			circleMasterRepository.saveAll(substationsList);
			LOG.info("Circle Info Successfully Updated in DB.");
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in updationg data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}
//	@Override
//	public CommonResponse deleteCircle(CircleMasterDataRequest circleDataRequest) {
//		CommonResponse response = new CommonResponse();
//		ErrorData error = new ErrorData();
//		CircleMaster circle = new CircleMaster();
//		LOG.info("Substations Info Data to delete from DB:--> ");
//
//		try {
//
//			if (!StringUtils.isEmpty(circleDataRequest.getHesCircleId())) {
//				Optional<CircleMaster> substations = circleMasterRepository
//						.findByhesCircleId(circleDataRequest.getHesCircleId());
//
//				String table = meterConfiguration.getKeyspace() + "." + Tables.DIVISION_MASTER;
//				String query = "Select * from " + table + " Where hes_circle_id = '"
//						+ circleDataRequest.getHesCircleId() + "'";
//				String queryString = CommonUtils.getMapper()
//						.writeValueAsString(prestoJdbcTemplate.queryForList(query).size());
//
//				Integer count = CommonUtils.getMapper().readValue(queryString, new TypeReference<Integer>() {
//				});
//
//				if (count != null && count > 0) {
//
//					error.setErrorMessage(
//							"This circle is associated in the network. Please de-associate it and then try again");
//					response.setError(true);
//					response.addErrorMessage(error);
//					return response;
//
//				} else {
//					if (!substations.isPresent()) {
//						error.setErrorMessage(
//								circleDataRequest.getHesCircleId() + " : " + ExternalConstants.Message.NOT_EXISTS);
//						response.setCode(404);
//						response.setError(true);
//						response.addErrorMessage(error);
//						return response;
//					}
//					circle = substations.get();
//					circleMasterRepository.deleteById(circle.getI_am_id());
//					LOG.info("Circle Info Successfully Deleted from DB.");
//					
//					response.setiAmId(String.valueOf(circle.getI_am_id()));
//					response.setCode(200);
//					response.setMessage(ExternalConstants.Message.DELETED);
//				}
//			}
//
//		} catch (Exception e) {
//			LOG.error("Issue in deleting data due to : " + e.getMessage());
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//		}
//		return response;
//	}

	@Override
	public CommonResponse getCircle(CircleMasterDataRequest circleDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("circles Info Data to get from DB:--> ");

		try {
			CircleMasterDataResponse circleDataRes = new CircleMasterDataResponse();

			if (!StringUtils.isEmpty(circleDataRequest.getLevel3Id())) {
				Optional<CircleMaster> circles = circleMasterRepository
						.findById(Integer.parseInt(circleDataRequest.getLevel3Id()));

				if (!circles.isPresent()) {
					error.setErrorMessage(
							circleDataRequest.getLevel3Id() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				CircleMaster circle = circles.get();

				circleDataRes.setiAmId(String.valueOf(circle.getI_am_id()));
				circleDataRes.setHesCircleId(circle.getHes_circle_id());
				circleDataRes.setCircleName(circle.getCircle_name());
				circleDataRes.setCircleCode(circle.getCircle_code());
				circleDataRes.setCreated(
						circle.getCreated() != null ? dateConverter.convertDateToString(circle.getCreated())
								: null);
				circleDataRes.setLatitude(circle.getLatitude());
				circleDataRes.setLongitude(circle.getLongitude());
				circleDataRes.setModified(
						circle.getModified() != null ? dateConverter.convertDateToString(circle.getModified())
								: null);
				circleDataRes.setSource(circle.getSource());
				circleDataRes.setCreatedBy(circle.getUser_id());
				circleDataRes.setLastUpdatedBy(circle.getUpdated_by());

			}

			response.setData(circleDataRes);
			LOG.info("Circle Service Data Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getCircleList() {

		CommonResponse response = new CommonResponse();

		LOG.info("Circles Info Data to get circle list from DB:--> ");

		try {

			List<CircleMaster> circles = circleMasterRepository.findAll();
			List<CircleMasterDataResponse> circleResList = new ArrayList<>();

			for (CircleMaster circle : circles) {
				CircleMasterDataResponse circleDataRes = new CircleMasterDataResponse();

				circleDataRes.setiAmId(String.valueOf(circle.getI_am_id()));
				circleDataRes.setHesCircleId(circle.getHes_circle_id());
				circleDataRes.setCircleName(circle.getCircle_name());
				circleDataRes.setCircleCode(circle.getCircle_code());
				circleDataRes.setCreated(
						circle.getCreated() != null ? dateConverter.convertDateToString(circle.getCreated())
								: null);
				circleDataRes.setLatitude(circle.getLatitude());
				circleDataRes.setLongitude(circle.getLongitude());
				circleDataRes.setModified(
						circle.getModified() != null ? dateConverter.convertDateToString(circle.getModified())
								: null);
				circleDataRes.setSource(circle.getSource());
				circleDataRes.setCreatedBy(circle.getUser_id());
				circleDataRes.setLastUpdatedBy(circle.getUpdated_by());
				
				circleResList.add(circleDataRes);
			}

			response.setData(circleResList);
			LOG.info("Circle List Data Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting Circle list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

//	@Override
//	public CommonResponse getCircleNameList() {
//		CommonResponse response = new CommonResponse();
//		ErrorData error = new ErrorData();
//
//		LOG.info("Circles Info Data to get Circle name list from DB:--> ");
//
//		try {
//
//			List<CircleMaster> circles = circleMasterRepository.findAll();
//
//			response.setData(
//					circles.stream().map(circle -> circle.getCircle_name()).collect(Collectors.toList()));
//			LOG.info("Circle Name List Response Success.");
//
//		} catch (Exception e) {
//			LOG.error("Issue in getting Circle name list due to : " + e.getMessage());
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//		}
//		return response;
//
//	}

	@Override
	public CommonResponse getCircleByZone(CircleMasterDataRequest circleDataRequest) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Circles Info Data to get list by Circle from DB:--> ");

		try {

			List<CircleMasterDataResponse> circleResList = new ArrayList<>();

			String table = meterConfiguration.getKeyspace() + "." + Tables.CIRCLE_MASTER;
			String tableZone = meterConfiguration.getKeyspace() + "." + Tables.ZONE_MASTER;


			if (!StringUtils.isEmpty(circleDataRequest.getZoneCode())) {
				StringBuilder queryBuilder = new StringBuilder();
				
				queryBuilder.append("select c.* from ").append(table).append(" as c inner join ")
				.append(tableZone).append(" as u on c.hes_zone_id = cast(u.i_am_id as varchar)").append(" where u.zone_code = '")
				.append(circleDataRequest.getZoneCode()).append("'");
				if(circleDataRequest.getUserId() != null && !circleDataRequest.getUserId ().isEmpty()) {
				Optional<UserHierMapping> useHierMapping = userHierMappingRepository.findById(Integer.parseInt(circleDataRequest.getUserId()));
				    if(useHierMapping.isPresent()) {
				       UserHierMapping userHier = useHierMapping.get();
				
				      if(userHier.getLevel3_id() != 0)
				      {
					   queryBuilder.append(" and c.i_am_id = ").append(userHier.getLevel3_id());
				      }
				    }   
				}		
				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<CircleMaster> circleList  = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<CircleMaster>>() {

						});

				
				if (circleList.size()==0) {
					error.setErrorMessage(
							circleDataRequest.getZoneCode() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}


				for (CircleMaster circle : circleList) {
					CircleMasterDataResponse circleDataRes = new CircleMasterDataResponse();

					circleDataRes.setCircleName(circle.getCircle_name());
					circleDataRes.setCircleCode(circle.getCircle_code());
					
					circleResList.add(circleDataRes);
				}

			}

			response.setData(circleResList);
			LOG.info("Circles Name List By circle Data Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting circle list by circle due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

//	@Override
//	public CommonResponse getCircleByUtility(CircleMasterDataRequest circleDataRequest) {
//
//		CommonResponse response = new CommonResponse();
//		ErrorData error = new ErrorData();
//
//		LOG.info("Circles Info Data list by utility from DB:--> ");
//
//		try {
//
//			List<CircleMasterDataResponse> circleResList = new ArrayList<>();
//
//			if (!StringUtils.isEmpty(circleDataRequest.getOwnerId())) {
//				Optional<List<CircleMaster>> circles = circleMasterRepository
//						.findByUtility(circleDataRequest.getOwnerId());
//
//				if (circles.get().isEmpty()) {
//					error.setErrorMessage(
//							circleDataRequest.getUtility() + " : " + ExternalConstants.Message.NOT_EXISTS);
//					response.setCode(404);
//					response.setError(true);
//					response.addErrorMessage(error);
//					return response;
//				}
//
//				List<CircleMaster> circleDataList = circles.get();
//
//				for (CircleMaster circle : circleDataList) {
//					CircleMasterDataResponse circleDataRes = new CircleMasterDataResponse();
//
//					circleDataRes.setiAmId(String.valueOf(circle.getI_am_id()));
//					circleDataRes.setHesOwnerId(circle.getHes_owner_id());
//					circleDataRes.setHesZoneId(circle.getHes_zone_id());
//					circleDataRes.setHesCircleId(circle.getHes_circle_id());
//					circleDataRes.setZoneName(circle.getZone_name());
//					circleDataRes.setCircleName(circle.getCircle_name());
//					circleDataRes.setCreated(
//							circle.getCreated() != null ? dateConverter.convertDateToString(circle.getCreated())
//									: null);
//					circleDataRes.setLatitude(circle.getLatitude());
//					circleDataRes.setLongitude(circle.getLongitude());
//					circleDataRes.setModified(
//							circle.getModified() != null ? dateConverter.convertDateToString(circle.getModified())
//									: null);
//					circleDataRes.setUtility(circle.getOwner_name());
//					circleDataRes.setSource(circle.getSource());
//					circleDataRes.setCreatedBy(circle.getUser_id());
//					circleDataRes.setLastUpdatedBy(circle.getUpdated_by());
//
//					circleResList.add(circleDataRes);
//				}
//
//			}
//
//			response.setData(circleResList);
//			LOG.info("Circle List By utility Data Response Success.");
//
//		} catch (Exception e) {
//			LOG.error("Issue in getting circle list by utility due to : " + e.getMessage());
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//		}
//		return response;
//
//	}
}
