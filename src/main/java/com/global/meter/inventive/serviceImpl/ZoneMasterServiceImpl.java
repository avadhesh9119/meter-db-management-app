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
import com.global.meter.business.model.UserHierMapping;
import com.global.meter.business.model.ZoneMaster;
import com.global.meter.data.repository.OwnersRepository;
import com.global.meter.data.repository.UserHierMappingRepository;
import com.global.meter.data.repository.ZoneMasterRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.ZoneMasterDataRequest;
import com.global.meter.inventive.models.ZoneMasterDataResponse;
import com.global.meter.inventive.service.ZoneMasterService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class ZoneMasterServiceImpl implements ZoneMasterService {
	private static final Logger LOG = LoggerFactory.getLogger(SubdivisionsServiceImpl.class);

	@Autowired
	ZoneMasterRepository zoneRepo;

	@Autowired
	OwnersRepository ownersRepository;
	
	@Autowired
	UserHierMappingRepository userHierMappingRepository;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Override
	public CommonResponse addZone(List<ZoneMasterDataRequest> zoneDataRequests) {
		List<ZoneMaster> zone = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		String hesZoneId = "";
		String table = meterConfiguration.getKeyspace() + "." + Tables.ZONE_MASTER;

		LOG.info("Wrapping Sub Zone Data to save in DB:--> ");

		try {
			for (ZoneMasterDataRequest dataRequest : zoneDataRequests) {
				ZoneMaster subDiv = new ZoneMaster();

				if (!StringUtils.isEmpty(dataRequest.getLevel2Id())) {
					Optional<ZoneMaster> subDivRepo = zoneRepo.findById(Integer.valueOf(dataRequest.getLevel2Id()));

					if (subDivRepo.isPresent()) {
						error.setErrorMessage(dataRequest.getLevel2Id() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}
				StringBuilder queryBuilder = new StringBuilder();

				queryBuilder.append("SELECT max(hes_zone_id) as hes_zone_id FROM ").append(table);

				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<ZoneMaster> id = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<ZoneMaster>>() {

						});

				hesZoneId = String.valueOf(Integer.valueOf(id.stream().iterator().next().getHes_zone_id() != null 
			    		? id.stream().iterator().next().getHes_zone_id() : "0")+1);
				subDiv.setI_am_id(Integer.valueOf(dataRequest.getLevel2Id()));
				subDiv.setOwner_id(dataRequest.getLevel1Id());
				subDiv.setHes_zone_id(hesZoneId);
				subDiv.setZone_name(dataRequest.getZoneName());
				subDiv.setZone_code(dataRequest.getZoneCode());
				subDiv.setCreated(new Date(System.currentTimeMillis()));
				subDiv.setLatitude(dataRequest.getLatitude());
				subDiv.setLongitude(dataRequest.getLongitude());
				subDiv.setSource(dataRequest.getSource());
				subDiv.setUpdated_by(dataRequest.getUpdatedBy());
				subDiv.setUser_id(dataRequest.getUserId());
				subDiv.setIs_active(dataRequest.getIsActive());

				zone.add(subDiv);
			}

			zoneRepo.saveAll(zone);
			LOG.info("Zone Info Successfully Stored in DB.");

			response.setHesId(hesZoneId);
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.error("Issue in storing data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateZone(List<ZoneMasterDataRequest> zoneDataRequests) {
		List<ZoneMaster> subDivisions = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Subdivisions Info Data to update in DB:--> ");

		try {
			for (ZoneMasterDataRequest dataRequest : zoneDataRequests) {
				ZoneMaster subDiv = new ZoneMaster();

				if (!StringUtils.isEmpty(dataRequest.getLevel2Id())) {
					Optional<ZoneMaster> subDivRepo = zoneRepo.findById(Integer.valueOf(dataRequest.getLevel2Id()));

					if (!subDivRepo.isPresent()) {
						error.setErrorMessage(
								dataRequest.getLevel2Id() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					subDiv = subDivRepo.get();
				}
				subDiv.setOwner_id(dataRequest.getLevel1Id() != null ? dataRequest.getLevel1Id() : subDiv.getOwner_id());
				subDiv.setZone_name(
						dataRequest.getZoneName() != null ? dataRequest.getZoneName() : subDiv.getZone_name());
				subDiv.setZone_code(
						dataRequest.getZoneCode() != null ? dataRequest.getZoneCode() : subDiv.getZone_code());
				subDiv.setLatitude(
						dataRequest.getLatitude() != null ? dataRequest.getLatitude() : subDiv.getLatitude());
				subDiv.setLongitude(
						dataRequest.getLongitude() != null ? dataRequest.getLongitude() : subDiv.getLongitude());
				subDiv.setModified(new Date(System.currentTimeMillis()));
				subDiv.setSource(
						dataRequest.getSource() != null ? dataRequest.getSource() : subDiv.getSource());
				subDiv.setUpdated_by(
						dataRequest.getUpdatedBy() != null ? dataRequest.getUpdatedBy() : subDiv.getUpdated_by());
				subDiv.setUser_id(
						dataRequest.getUserId() != null ? dataRequest.getUserId() : subDiv.getUser_id());
				subDiv.setIs_active(
						dataRequest.getIsActive() != null ? dataRequest.getIsActive() : subDiv.getIs_active());

				subDivisions.add(subDiv);
				response.setHesId(subDiv.getHes_zone_id());
			}
			zoneRepo.saveAll(subDivisions);
			LOG.info("Zone Info Successfully Updated in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in updating data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

//	@Override
//	public CommonResponse deleteZone(ZoneMasterDataRequest zoneDataRequests) {
//		CommonResponse response = new CommonResponse();
//		ErrorData error = new ErrorData();
//		ZoneMaster subdivisionsMaster = new ZoneMaster();
//		LOG.info("Zone Info Data to delete from:--> ");
//
//		try {
//
//			if (!StringUtils.isEmpty(zoneDataRequests.getHesZoneId())) {
//				Optional<ZoneMaster> subdivisions = zoneRepo.findByZoneId(zoneDataRequests.getHesZoneId());
//
//				String table = MeterDBAppStarter.keyspace + "." + Tables.CIRCLE_MASTER;
//				String query = "Select * from " + table + " Where hes_zone_id = '" + zoneDataRequests.getHesZoneId()
//						+ "'";
//				String queryString = CommonUtils.getMapper()
//						.writeValueAsString(prestoJdbcTemplate.queryForList(query).size());
//
//				Integer count = CommonUtils.getMapper().readValue(queryString, new TypeReference<Integer>() {
//				});
//
//				if (count != null && count > 0) {
//
//					error.setErrorMessage(
//							"This zone is associated in the network. Please de-associate it and then try again");
//					response.setError(true);
//					response.addErrorMessage(error);
//					return response;
//
//				} else {
//
//					if (!subdivisions.isPresent()) {
//						error.setErrorMessage(
//								zoneDataRequests.getZoneName() + " : " + ExternalConstants.Message.NOT_EXISTS);
//						response.setCode(404);
//						response.setError(true);
//						response.addErrorMessage(error);
//						return response;
//					}
//					subdivisionsMaster = subdivisions.get();
//					response.setiAmId(String.valueOf(subdivisionsMaster.getI_am_id()));
//					zoneRepo.deleteById(subdivisionsMaster.getI_am_id());
//					LOG.info("Zone Info Successfully Deleted from DB.");
//
//					response.setCode(200);
//					response.setMessage(ExternalConstants.Message.DELETED);
//
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
	public CommonResponse getZone(ZoneMasterDataRequest zoneDataRequests) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Zone Info Data to get from DB:--> ");

		try {
			ZoneMasterDataResponse subDivRes = new ZoneMasterDataResponse();
			if (!StringUtils.isEmpty(zoneDataRequests.getLevel2Id())) {
				Optional<ZoneMaster> zoneData = zoneRepo.findById(Integer.parseInt(zoneDataRequests.getLevel2Id()));

				if (!zoneData.isPresent()) {
					error.setErrorMessage(
							zoneDataRequests.getLevel2Id() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				ZoneMaster zone = zoneData.get();

				subDivRes.setiAmId(String.valueOf(zone.getI_am_id()));
				subDivRes.setHesZoneId(zone.getHes_zone_id());
				subDivRes.setCreated(
						zone.getCreated() != null ? dateConverter.convertDateToString(zone.getCreated()) : null);
				subDivRes.setLatitude(zone.getLatitude());
				subDivRes.setLongitude(zone.getLongitude());
				subDivRes.setModified(
						zone.getModified() != null ? dateConverter.convertDateToString(zone.getModified()) : null);
				subDivRes.setOwnerId(zone.getOwner_id());
				subDivRes.setSource(zone.getSource());
				subDivRes.setZoneName(zone.getZone_name());
				subDivRes.setUpdatedBy(zone.getUpdated_by());
				subDivRes.setUserId(zone.getUser_id());
				subDivRes.setZoneCode(zone.getZone_code());

			}

			response.setData(subDivRes);
			LOG.info("Zone Service Data Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getZoneList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Zone Info Data to get list from DB:--> ");

		try {

			List<ZoneMaster> zones = zoneRepo.findAll();
			List<ZoneMasterDataResponse> zoneResList = new ArrayList<>();

			for (ZoneMaster zone : zones) {
				ZoneMasterDataResponse zoneRes = new ZoneMasterDataResponse();

				zoneRes.setiAmId(String.valueOf(zone.getI_am_id()));
				zoneRes.setHesZoneId(zone.getHes_zone_id());
				zoneRes.setCreated(
						zone.getCreated() != null ? dateConverter.convertDateToString(zone.getCreated()) : null);
				zoneRes.setLatitude(zone.getLatitude());
				zoneRes.setLongitude(zone.getLongitude());
				zoneRes.setModified(
						zone.getModified() != null ? dateConverter.convertDateToString(zone.getModified()) : null);
				zoneRes.setOwnerId(zone.getOwner_id());
				zoneRes.setSource(zone.getSource());
				zoneRes.setZoneName(zone.getZone_name());
				zoneRes.setUpdatedBy(zone.getUpdated_by());
				zoneRes.setUserId(zone.getUser_id());
				zoneRes.setZoneCode(zone.getZone_code());

				zoneResList.add(zoneRes);
			}

			response.setData(zoneResList);
			LOG.info("Zone  Service Data List Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting zone list from DB due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

//	@Override
//	public CommonResponse getZoneNameList() {
//		CommonResponse response = new CommonResponse();
//		ErrorData error = new ErrorData();
//		LOG.info("Zone Info Data to get zone namelist from DB:--> ");
//
//		try {
//			List<ZoneMaster> zones = zoneRepo.findAll();
//
//			response.setData(zones.stream().map(division -> division.getZone_name()).collect(Collectors.toList()));
//			LOG.info("Zone Name List Service Response Success.");
//
//		} catch (Exception e) {
//			LOG.error("Issue in getting zone name list from DB due to : " + e.getMessage());
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//		}
//		return response;
//
//	}

	@Override
	public CommonResponse getListByUtility(ZoneMasterDataRequest zoneDataRequests) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Zone Info Data to get list by utility from DB:--> ");

		try {
			List<ZoneMasterDataResponse> zoneResList = new ArrayList<>();
			String table = meterConfiguration.getKeyspace() + "." + Tables.ZONE_MASTER;
			String tableUtility = meterConfiguration.getKeyspace() + "." + Tables.UTILITY_MASTER;


			if (!StringUtils.isEmpty(zoneDataRequests.getOwnerCode())) {
				StringBuilder queryBuilder = new StringBuilder();
				
				queryBuilder.append("select z.* from ").append(table).append(" as z inner join ")
				.append(tableUtility).append(" as u on z.owner_id = cast(u.i_am_id as varchar)").append(" where u.owner_code = '")
				.append(zoneDataRequests.getOwnerCode()).append("'");
				if(zoneDataRequests.getUserId() != null && !zoneDataRequests.getUserId().isEmpty()) {
				Optional<UserHierMapping> useHierMapping = userHierMappingRepository.findById(Integer.parseInt(zoneDataRequests.getUserId()));
				    if(useHierMapping.isPresent()) {
				       UserHierMapping userHier = useHierMapping.get();
				
				      if(userHier.getLevel2_id() != 0)
				      {
					   queryBuilder.append(" and z.i_am_id = ").append(userHier.getLevel2_id());
				      }
				    }   
				}
				String query = queryBuilder.substring(0, queryBuilder.length());
				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<ZoneMaster> zoneList  = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<ZoneMaster>>() {

						});

				if (zoneList.size()==0) {
					error.setErrorMessage(
							zoneDataRequests.getOwnerCode() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				for (ZoneMaster zone : zoneList) {
					ZoneMasterDataResponse zoneRes = new ZoneMasterDataResponse();

					zoneRes.setZoneName(zone.getZone_name());
					zoneRes.setZoneCode(zone.getZone_code());

					zoneResList.add(zoneRes);
				}
			}
			response.setData(zoneResList);
			LOG.info("Zone Name List Response By utility Data Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting list by utility from DB due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
}