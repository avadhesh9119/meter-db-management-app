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
import com.global.meter.MeterDBAppStarter;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.DtTransMaster;
import com.global.meter.business.model.FeedersMaster;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.data.repository.DtTransMasterRepository;
import com.global.meter.data.repository.FeedersMasterRepository;
import com.global.meter.data.repository.OwnersRepository;
import com.global.meter.data.repository.SubdivisionsMasterRepository;
import com.global.meter.data.repository.SubstationsMasterRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.FeederDataResponse;
import com.global.meter.inventive.models.FeederMasterDataRequest;
import com.global.meter.inventive.service.FeedersMasterService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class FeedersMasterServiceImpl implements FeedersMasterService {

	private static final Logger LOG = LoggerFactory.getLogger(FeedersServiceImpl.class);

	@Autowired
	FeedersMasterRepository feedersRepository;
	
	@Autowired
	SubstationsMasterRepository substationsMasterRepository;
	
	@Autowired
	SubdivisionsMasterRepository subDivisionRepo;
	
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
	DtTransMasterRepository dtTransRepository;

	@Override
	public CommonResponse addFeeders(List<FeederMasterDataRequest> feederDataReqList) {

		List<FeedersMaster> feeders = new ArrayList<>();
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		int iAmId = 0;
		String table = meterConfiguration.getKeyspace() + "." + Tables.FEEDERS_MASTER;

		LOG.info("Wrapping Feeder Data to save in DB:--> ");

		try {

			for (FeederMasterDataRequest feederData : feederDataReqList) {
				FeedersMaster feeder = new FeedersMaster();
				
				if((feederData.getSource() == null || StringUtils.isEmpty(feederData.getSource())) || 
						(feederData.getUserId() == null || StringUtils.isEmpty(feederData.getUserId()))) {
					error.setErrorMessage("Source & UserId can not be empty");
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
					}
				
				if (!StringUtils.isEmpty(feederData.getFeederName())) {
					Optional<FeedersMaster> feederRepo = feedersRepository.findByhesFeederId(feederData.getHesFeederId());

					if (feederRepo.isPresent()) {
						error.setErrorMessage(feederData.getFeederName() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}
				StringBuilder queryBuilder = new StringBuilder();

				queryBuilder.append("SELECT COALESCE(max(i_am_id), 0)+1 as i_am_id FROM ").append(table);

				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<FeedersMaster> id = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<FeedersMaster>>() {

						});

			    iAmId = id.stream().iterator().next().getI_am_id();

				feeder.setI_am_id(iAmId);
				feeder.setHes_feeder_id(feederData.getHesFeederId());
				feeder.setHes_substation_id(feederData.getHesSubstationId());
				feeder.setHes_subdivision_id(feederData.getHesSubdivisionId());
				feeder.setOwner_id(feederData.getOwnerId());

				feeder.setFeeder_name(feederData.getFeederName());
				feeder.setCreated(new Date(System.currentTimeMillis()));
				feeder.setLatitude(feederData.getLatitude());
				feeder.setLongitude(feederData.getLongitude());
				feeder.setModified(new Date(System.currentTimeMillis()));
				feeder.setOwner_name(feederData.getOwnerName());
				feeder.setSubdivision_name(feederData.getSubdivisionName());
				feeder.setSubstation_name(feederData.getSubstationName());
				feeder.setSource(feederData.getSource());
				feeder.setUser_id(feederData.getUserId());

				feeders.add(feeder);
			}
			feedersRepository.saveAll(feeders);
			LOG.info("Feeders Successfully Stored in DB.");
            response.setiAmId(String.valueOf(iAmId));
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.error("Issue in storing data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateFeeders(List<FeederMasterDataRequest> feederDataReqList) {

		List<FeedersMaster> feeders = new ArrayList<>();
		List<DtTransMaster> dts = new ArrayList<>();
		List<DevicesInfo> devInfoList = new ArrayList<>();

		String table = meterConfiguration.getKeyspace() + "." +Tables.DEVICES_INFO;
		String tableDt = meterConfiguration.getKeyspace() + "." +Tables.DT_TRANS_MASTER;

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Wrapping Feeder Data to update in DB:--> ");

		try {

			for (FeederMasterDataRequest feederData : feederDataReqList) {

				FeedersMaster feeder = new FeedersMaster();
				
				if(feederData.getUpdatedBy() == null || StringUtils.isEmpty(feederData.getUpdatedBy())) {
					error.setErrorMessage("Updated by can not be empty");
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
					}

				if (!StringUtils.isEmpty(feederData.getHesFeederId())) {
					Optional<FeedersMaster> feederRepo = feedersRepository.findByhesFeederId(feederData.getHesFeederId());

					if (!feederRepo.isPresent()) {
						error.setErrorMessage(feederData.getFeederName()+" : "+ExternalConstants.Message.NOT_EXISTS);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}

					feeder = feederRepo.get();
				}
				feeder.setHes_feeder_id(
						feederData.getHesFeederId() != null ? feederData.getHesFeederId() : feeder.getHes_feeder_id());
				feeder.setHes_substation_id(
						feederData.getHesSubstationId() != null ? feederData.getHesSubstationId() : feeder.getHes_substation_id());
				feeder.setHes_subdivision_id(
						feederData.getHesSubdivisionId() != null ? feederData.getHesSubdivisionId() : feeder.getHes_subdivision_id());
				feeder.setOwner_id(
						feederData.getOwnerId() != null ? feederData.getOwnerId() : feeder.getOwner_id());
				feeder.setFeeder_name(
						feederData.getFeederName() != null ? feederData.getFeederName() : feeder.getFeeder_name());
				feeder.setLatitude(feederData.getLatitude() != null ? feederData.getLatitude() : feeder.getLatitude());
				feeder.setLongitude(
						feederData.getLongitude() != null ? feederData.getLongitude() : feeder.getLongitude());
				feeder.setModified(new Date(System.currentTimeMillis()));
				feeder.setOwner_name(
						feederData.getOwnerName() != null ? feederData.getOwnerId() : feeder.getOwner_name());
				feeder.setSubdivision_name(feederData.getSubdivisionName() != null ? feederData.getSubdivisionName()
						: feeder.getSubdivision_name());
				feeder.setSubstation_name(feederData.getSubstationName() != null ? feederData.getSubstationName()
						: feeder.getSubstation_name());
				feeder.setUpdated_by(feederData.getUpdatedBy() != null ? feederData.getUpdatedBy()
						: feeder.getUpdated_by());

				feeders.add(feeder);
				response.setiAmId(String.valueOf(feeder.getI_am_id()));
				StringBuilder queryBuilder = new StringBuilder();
				
				queryBuilder.append("SELECT * FROM ").append(table).append(" where feeder_name = '").append(feederData.getFeederName())
				.append("'");
				String query = queryBuilder.substring(0, queryBuilder.length());
					
				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<DevicesInfo> devicesInfos = new ArrayList<>();
				devicesInfos = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<DevicesInfo>>() {});
				
				for(DevicesInfo devInfo : devicesInfos) {
					devInfo.setSubdevision_name(feederData.getSubdivisionName()
						!= null ? feederData.getSubdivisionName() : devInfo.getSubdevision_name());
					devInfo.setSubstation_name(feederData.getSubstationName()
							!= null ? feederData.getSubstationName() : devInfo.getSubstation_name());
				
				devInfoList.add(devInfo);
			}
			
				StringBuilder queryBuilderDt = new StringBuilder();
				queryBuilderDt.append("SELECT * FROM ").append(tableDt).append(" where hes_feeder_id = '").append(feederData.getHesFeederId())
				.append("'");
				String queryDt = queryBuilderDt.substring(0, queryBuilderDt.length());
					
				String outputListDt = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queryDt));

				List<DtTransMaster> dtList = new ArrayList<>();
				dtList = CommonUtils.getMapper().readValue(outputListDt,
						new TypeReference<List<DtTransMaster>>() {});
				for(DtTransMaster dt:dtList) {
					dt.setHes_subdivision_id(feederData.getHesSubdivisionId()
							!= null ? feederData.getHesSubdivisionId() : dt.getHes_subdivision_id());
					dt.setSubdevision_name(feederData.getSubdivisionName()
							!= null ? feederData.getSubdivisionName() : dt.getSubdevision_name());
					dt.setHes_substation_id(feederData.getHesSubstationId()
							!= null ? feederData.getHesSubstationId() : dt.getHes_substation_id());
					dt.setSubstation_name(feederData.getSubstationName()
							!= null ? feederData.getSubstationName() : dt.getSubstation_name());
					dts.add(dt);
				}
				
			}
			dtTransRepository.saveAll(dts);
			feedersRepository.saveAll(feeders);
			devicesInfoRepository.saveAll(devInfoList);
			LOG.info("Feeders Successfully Updated in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in Updating data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse deleteFeeders(FeederMasterDataRequest feederDataRequests) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Wrapping Feeder Data to delete from DB:--> ");

		try {
			if (!StringUtils.isEmpty(feederDataRequests.getHesFeederId())) {
				Optional<FeedersMaster> feeders = feedersRepository.findByhesFeederId(feederDataRequests.getHesFeederId());

				
				String table = MeterDBAppStarter.keyspace + "." + Tables.DT_TRANS_MASTER;
				String query = "Select * from " + table + " Where hes_feeder_id = '" + feederDataRequests.getHesFeederId() + "'";
				String queryString = CommonUtils.getMapper()
						.writeValueAsString(prestoJdbcTemplate.queryForList(query).size());
				
				Integer count = CommonUtils.getMapper().readValue(queryString,
						new TypeReference<Integer>() {
						});
				
				if(count != null && count > 0) {
					
					 error.setErrorMessage("This feeder is associated in the network. Please de-associate it and then try again"); 
                     response.setError(true); 
                     response.addErrorMessage(error); 
							 
					 return response; 
							  
				}else {
					
					 if (!feeders.isPresent()) { 
					  error.setErrorMessage(feederDataRequests.getHesFeederId() + " : " +
					  ExternalConstants.Message.NOT_EXISTS); 
					  response.setCode(404);
					  response.setError(true); response.addErrorMessage(error); 
					 
					  return response; 
					  }
					 FeedersMaster feeder = feeders.get();
					 LOG.info("Feeders Successfully Deleted from DB.");
					 feedersRepository.deleteById(feeder.getI_am_id());
					 response.setCode(200);
					 response.setMessage(ExternalConstants.Message.DELETED);
				}
			}
		} catch (Exception e) {
			LOG.error("Issue in Deleting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getFeeder(FeederMasterDataRequest feederDataRequests) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Wrapping Feeder Data to get from DB:--> ");

		try {
			FeederDataResponse feederDataResponse = new FeederDataResponse();
			if (!StringUtils.isEmpty(feederDataRequests.getHesFeederId())) {
				Optional<FeedersMaster> feederRepo = feedersRepository.findByhesFeederId(feederDataRequests.getHesFeederId());

				if (!feederRepo.isPresent()) {
					error.setErrorMessage(
							feederDataRequests.getFeederName() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				FeedersMaster feeder = feederRepo.get();
				
				feederDataResponse.setiAmId(String.valueOf(feeder.getI_am_id()));
				feederDataResponse.setHesFeederId(feeder.getHes_feeder_id());
				feederDataResponse.setHesSubstationId(feeder.getHes_substation_id());
				feederDataResponse.setHesSubdivisionId(feeder.getHes_subdivision_id());
				feederDataResponse.setHesOwnerId(feeder.getOwner_id());
				feederDataResponse.setFeederName(feeder.getFeeder_name());
				feederDataResponse.setCreated(
						feeder.getCreated() != null ? dateConverter.convertDateToString(feeder.getCreated()) : null);
				feederDataResponse.setLatitude(feeder.getLatitude());
				feederDataResponse.setLongitude(feeder.getLongitude());
				feederDataResponse.setModified(
						feeder.getModified() != null ? dateConverter.convertDateToString(feeder.getModified()) : null);
				feederDataResponse.setUtility(feeder.getOwner_name());
				feederDataResponse.setSubdivisionName(feeder.getSubdivision_name());
				feederDataResponse.setSubstationName(feeder.getSubstation_name());
				feederDataResponse.setSource(feeder.getSource());
				feederDataResponse.setCreatedBy(feeder.getUser_id());
				feederDataResponse.setLastupdatedBy(feeder.getUpdated_by());

			}

			response.setData(feederDataResponse);
			LOG.info("Feeder Data Service Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in Getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getFeedersList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting feeder Data from DB:--> ");

		try {

			List<FeedersMaster> feedersRepoList = feedersRepository.findAll();
			List<FeederDataResponse> feederResList = new ArrayList<>();

			for (FeedersMaster feeder : feedersRepoList) {
				FeederDataResponse feederData = new FeederDataResponse();
				
				feederData.setiAmId(String.valueOf(feeder.getI_am_id()));
				feederData.setHesFeederId(feeder.getHes_feeder_id());
				feederData.setHesSubstationId(feeder.getHes_substation_id());
				feederData.setHesSubdivisionId(feeder.getHes_subdivision_id());
				feederData.setHesOwnerId(feeder.getOwner_id());
				feederData.setFeederName(feeder.getFeeder_name());
				feederData.setCreated(
						feeder.getCreated() != null ? dateConverter.convertDateToString(feeder.getCreated()) : null);
				feederData.setLatitude(feeder.getLatitude());
				feederData.setLongitude(feeder.getLongitude());
				feederData.setModified(
						feeder.getModified() != null ? dateConverter.convertDateToString(feeder.getModified()) : null);
				feederData.setUtility(feeder.getOwner_name());
				feederData.setSubdivisionName(feeder.getSubdivision_name());
				feederData.setSubstationName(feeder.getSubstation_name());
				feederData.setSource(feeder.getSource());
				feederData.setCreatedBy(feeder.getUser_id());
				feederData.setLastupdatedBy(feeder.getUpdated_by());

				feederResList.add(feederData);
				
			}
			response.setData(feederResList);
			LOG.info("Feeder Data List Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in Getting Feeder list data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getFeederNameList() {
		CommonResponse response = new CommonResponse();

		try {

			List<FeedersMaster> feedersRepoList = feedersRepository.findAll();

			LOG.info("Get Feeder Name List Data from DB" );
			response.setData(
					feedersRepoList.stream().map(feeder -> feeder.getFeeder_name()).collect(Collectors.toList()));

		} catch (Exception e) {
			LOG.error("Issue in Getting feeder name list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getFeedersBySubstation(FeederMasterDataRequest feederDataRequests) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		try {

			LOG.info("Get Feeder List Data from DB By Substation:--> " + feederDataRequests.getSubstationCode());
			List<FeederDataResponse> feederResList = new ArrayList<>();

			if (!StringUtils.isEmpty(feederDataRequests.getSubstationCode())) {
				Optional<List<FeedersMaster>> feedersRepoList = feedersRepository
						.findBySubstationId(feederDataRequests.getSubstationCode());

				if (feedersRepoList.get().isEmpty()) {
					error.setErrorMessage(
							feederDataRequests.getSubstationCode() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				List<FeedersMaster> feederDataList = feedersRepoList.get();

				for (FeedersMaster feeder : feederDataList) {
					FeederDataResponse feederData = new FeederDataResponse();

					feederData.setFeederCode(feeder.getHes_feeder_id());
					feederData.setFeederName(feeder.getFeeder_name());

					feederResList.add(feederData);
				}

			}

			response.setData(feederResList);
			LOG.info("Feeder Data Service by Substation Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in Getting feeder by substations due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	
	@Override
	public CommonResponse getFeedersByUtility(FeederMasterDataRequest feederDataRequests) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		try {

			LOG.info("Get Feeder List Data from DB By Utility:--> " + feederDataRequests.getOwnerName());
			List<FeederDataResponse> feederResList = new ArrayList<>();

			if (!StringUtils.isEmpty(feederDataRequests.getOwnerName())) {
				Optional<List<FeedersMaster>> feedersRepoList = feedersRepository
						.findByUtility(feederDataRequests.getOwnerName());

				if (feedersRepoList.get().isEmpty()) {
					error.setErrorMessage(
							feederDataRequests.getOwnerName() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				List<FeedersMaster> feederDataList = feedersRepoList.get();

				for (FeedersMaster feeder : feederDataList) {
					FeederDataResponse feederData = new FeederDataResponse();
					
					feederData.setiAmId(String.valueOf(feeder.getI_am_id()));
					feederData.setHesFeederId(feeder.getHes_feeder_id());
					feederData.setHesSubstationId(feeder.getHes_substation_id());
					feederData.setHesSubdivisionId(feeder.getHes_subdivision_id());
					feederData.setHesOwnerId(feeder.getOwner_id());
					feederData.setFeederName(feeder.getFeeder_name());
					feederData.setCreated(
							feeder.getCreated() != null ? dateConverter.convertDateToString(feeder.getCreated())
									: null);
					feederData.setLatitude(feeder.getLatitude());
					feederData.setLongitude(feeder.getLongitude());
					feederData.setModified(
							feeder.getModified() != null ? dateConverter.convertDateToString(feeder.getModified())
									: null);
					feederData.setUtility(feeder.getOwner_name());
					feederData.setSubdivisionName(feeder.getSubdivision_name());
					feederData.setSubstationName(feeder.getSubstation_name());
					feederData.setSource(feeder.getSource());
					feederData.setCreatedBy(feeder.getUser_id());
					feederData.setLastupdatedBy(feeder.getUpdated_by());

					feederResList.add(feederData);
				}
			}
			response.setData(feederResList);
			LOG.info("Feeder Data Service by Utility Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in Getting feeder by utility due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
}
