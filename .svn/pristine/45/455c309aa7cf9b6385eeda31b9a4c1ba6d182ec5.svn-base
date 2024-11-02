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
import com.global.meter.business.model.DtTrans;
import com.global.meter.business.model.Feeders;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.data.repository.DtTransRepository;
import com.global.meter.data.repository.FeedersRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.FeederDataRequest;
import com.global.meter.inventive.models.FeederDataResponse;
import com.global.meter.inventive.service.FeedersService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class FeedersServiceImpl implements FeedersService {

	private static final Logger LOG = LoggerFactory.getLogger(FeedersServiceImpl.class);

	@Autowired
	FeedersRepository feedersRepository;
	
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
	DtTransRepository dtTransRepository;

	@Override
	public CommonResponse addFeeders(List<FeederDataRequest> feederDataReqList) {

		List<Feeders> feeders = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Wrapping Feeder Data to save in DB:--> ");

		try {

			for (FeederDataRequest feederData : feederDataReqList) {
				Feeders feeder = new Feeders();
				
				if((feederData.getSource() == null || StringUtils.isEmpty(feederData.getSource())) || 
						(feederData.getUserId() == null || StringUtils.isEmpty(feederData.getUserId()))) {
					response.setMessage("Source & UserId can not be empty");
					response.setCode(404);
					response.setError(true);
					return response;
					}
				
				if (!StringUtils.isEmpty(feederData.getFeederName())) {
					Optional<Feeders> feederRepo = feedersRepository.findById(feederData.getFeederName());

					if (feederRepo.isPresent()) {
						error.setErrorMessage(feederData.getFeederName() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}

				feeder.setFeeder_name(feederData.getFeederName());
				feeder.setCreated(new Date(System.currentTimeMillis()));
				feeder.setLatitude(feederData.getLatitude());
				feeder.setLongitude(feederData.getLongitude());
				feeder.setModified(new Date(System.currentTimeMillis()));
				feeder.setOwner_name(feederData.getUtility());
				feeder.setSubdevision_name(feederData.getSubdivisionName());
				feeder.setSubstation_name(feederData.getSubstationName());
				feeder.setSource(feederData.getSource());
				feeder.setUser_id(feederData.getUserId());

				feeders.add(feeder);
			}
			feedersRepository.saveAll(feeders);
			LOG.info("Feeders Successfully Stored in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.error("Issue in storing data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateFeeders(List<FeederDataRequest> feederDataReqList) {

		List<Feeders> feeders = new ArrayList<>();
		List<DtTrans> dts = new ArrayList<>();
		List<DevicesInfo> devInfoList = new ArrayList<>();
		String table = meterConfiguration.getKeyspace() + "." +Tables.DEVICES_INFO;
		String tableDt = meterConfiguration.getKeyspace() + "." +Tables.DT_TRANS;

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Wrapping Feeder Data to update in DB:--> ");

		try {

			for (FeederDataRequest feederData : feederDataReqList) {

				Feeders feeder = new Feeders();
				
				if(feederData.getUpdatedBy() == null || StringUtils.isEmpty(feederData.getUpdatedBy())) {
					response.setMessage("Updated by can not be empty");
					response.setCode(404);
					response.setError(true);
					return response;
					}

				if (!StringUtils.isEmpty(feederData.getFeederName())) {
					Optional<Feeders> feederRepo = feedersRepository.findById(feederData.getFeederName());

					if (!feederRepo.isPresent()) {
						error.setErrorMessage(
								feederData.getFeederName() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}

					feeder = feederRepo.get();
				}

				feeder.setFeeder_name(
						feederData.getFeederName() != null ? feederData.getFeederName() : feeder.getFeeder_name());
				// feeder.setCreated(new Date(System.currentTimeMillis()));
				feeder.setLatitude(feederData.getLatitude() != null ? feederData.getLatitude() : feeder.getLatitude());
				feeder.setLongitude(
						feederData.getLongitude() != null ? feederData.getLongitude() : feeder.getLongitude());
				feeder.setModified(new Date(System.currentTimeMillis()));
				feeder.setOwner_name(
						feederData.getUtility() != null ? feederData.getUtility() : feeder.getOwner_name());
				feeder.setSubdevision_name(feederData.getSubdivisionName() != null ? feederData.getSubdivisionName()
						: feeder.getSubdevision_name());
				feeder.setSubstation_name(feederData.getSubstationName() != null ? feederData.getSubstationName()
						: feeder.getSubstation_name());
				feeder.setUpdated_by(feederData.getUpdatedBy() != null ? feederData.getUpdatedBy()
						: feeder.getUpdated_by());

				feeders.add(feeder);
				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT * FROM ").append(table).append(" where feeder_name = '").append(feederData.getFeederName())
				.append("'");
				String query = queryBuilder.substring(0, queryBuilder.length());
					
				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<DevicesInfo> devicesInfos = new ArrayList<>();
				devicesInfos = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<DevicesInfo>>() {});
				
				for(DevicesInfo devInfo1 : devicesInfos) {
					devInfo1.setSubdevision_name(feederData.getSubdivisionName()
						!= null ? feederData.getSubdivisionName() : devInfo1.getSubdevision_name());
					devInfo1.setSubstation_name(feederData.getSubstationName()
							!= null ? feederData.getSubstationName() : devInfo1.getSubstation_name());
				
				devInfoList.add(devInfo1);
			}
			
				StringBuilder queryBuilderDt = new StringBuilder();
				queryBuilderDt.append("SELECT * FROM ").append(tableDt).append(" where feeder_name = '").append(feederData.getFeederName())
				.append("'");
				String queryDt = queryBuilderDt.substring(0, queryBuilderDt.length());
					
				String outputListDt = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queryDt));

				List<DtTrans> dtList = new ArrayList<>();
				dtList = CommonUtils.getMapper().readValue(outputListDt,
						new TypeReference<List<DtTrans>>() {});
				for(DtTrans dt:dtList) {
					dt.setSubdevision_name(feederData.getSubdivisionName());
					dt.setSubstation_name(feederData.getSubstationName());
					
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
	public CommonResponse deleteFeeders(FeederDataRequest feederDataRequests) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Wrapping Feeder Data to delete from DB:--> ");

		try {
			if (!StringUtils.isEmpty(feederDataRequests.getFeederName())) {
				Optional<Feeders> devicesInfo = feedersRepository.findById(feederDataRequests.getFeederName());

				
				String table = MeterDBAppStarter.keyspace + "." + Tables.DT_TRANS;
				String query = "Select * from " + table + " Where feeder_name = '" + feederDataRequests.getFeederName() + "'";
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
					
					 if (!devicesInfo.isPresent()) { 
					  error.setErrorMessage(feederDataRequests.getFeederName() + " : " +
					  ExternalConstants.Message.NOT_EXISTS); 
					  response.setCode(404);
					  response.setError(true); response.addErrorMessage(error); 
					 
					  return response; 
					  
					  }
					
					 LOG.info("Feeders Successfully Deleted from DB.");
					 feedersRepository.deleteById(feederDataRequests.getFeederName());
					 
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
	public CommonResponse getFeeder(FeederDataRequest feederDataRequests) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Wrapping Feeder Data to get from DB:--> ");

		try {
			FeederDataResponse feederDataResponse = new FeederDataResponse();
			if (!StringUtils.isEmpty(feederDataRequests.getFeederName())) {
				Optional<Feeders> feederRepo = feedersRepository.findById(feederDataRequests.getFeederName());

				if (!feederRepo.isPresent()) {
					error.setErrorMessage(
							feederDataRequests.getFeederName() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				Feeders feeder = feederRepo.get();

				feederDataResponse.setFeederName(feeder.getFeeder_name());
				feederDataResponse.setCreated(
						feeder.getCreated() != null ? dateConverter.convertDateToString(feeder.getCreated()) : null);
				feederDataResponse.setLatitude(feeder.getLatitude());
				feederDataResponse.setLongitude(feeder.getLongitude());
				feederDataResponse.setModified(
						feeder.getModified() != null ? dateConverter.convertDateToString(feeder.getModified()) : null);
				feederDataResponse.setUtility(feeder.getOwner_name());
				feederDataResponse.setSubdivisionName(feeder.getSubdevision_name());
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

			List<Feeders> feedersRepoList = feedersRepository.findAll();
			List<FeederDataResponse> feederResList = new ArrayList<>();

			for (Feeders feeder : feedersRepoList) {
				FeederDataResponse feederData = new FeederDataResponse();

				feederData.setFeederName(feeder.getFeeder_name());
				feederData.setCreated(
						feeder.getCreated() != null ? dateConverter.convertDateToString(feeder.getCreated()) : null);
				feederData.setLatitude(feeder.getLatitude());
				feederData.setLongitude(feeder.getLongitude());
				feederData.setModified(
						feeder.getModified() != null ? dateConverter.convertDateToString(feeder.getModified()) : null);
				feederData.setUtility(feeder.getOwner_name());
				feederData.setSubdivisionName(feeder.getSubdevision_name());
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

			List<Feeders> feedersRepoList = feedersRepository.findAll();

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
	public CommonResponse getFeedersBySubstation(FeederDataRequest feederDataRequests) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		try {

			LOG.info("Get Feeder List Data from DB By Substation:--> " + feederDataRequests.getSubstationName());
			List<FeederDataResponse> feederResList = new ArrayList<>();

			if (!StringUtils.isEmpty(feederDataRequests.getSubstationName())) {
				Optional<List<Feeders>> feedersRepoList = feedersRepository
						.findBySubstationName(feederDataRequests.getSubstationName());

				if (feedersRepoList.get().isEmpty()) {
					error.setErrorMessage(
							feederDataRequests.getSubstationName() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				List<Feeders> feederDataList = feedersRepoList.get();

				for (Feeders feeder : feederDataList) {
					FeederDataResponse feederData = new FeederDataResponse();

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
					feederData.setSubdivisionName(feeder.getSubdevision_name());
					feederData.setSubstationName(feeder.getSubstation_name());
					feederData.setSource(feeder.getSource());
					feederData.setCreatedBy(feeder.getUser_id());
					feederData.setLastupdatedBy(feeder.getUpdated_by());

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
	public CommonResponse getFeedersByUtility(FeederDataRequest feederDataRequests) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		try {

			LOG.info("Get Feeder List Data from DB By Utility:--> " + feederDataRequests.getUtility());
			List<FeederDataResponse> feederResList = new ArrayList<>();

			if (!StringUtils.isEmpty(feederDataRequests.getUtility())) {
				Optional<List<Feeders>> feedersRepoList = feedersRepository
						.findByUtility(feederDataRequests.getUtility());

				if (feedersRepoList.get().isEmpty()) {
					error.setErrorMessage(
							feederDataRequests.getUtility() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				List<Feeders> feederDataList = feedersRepoList.get();

				for (Feeders feeder : feederDataList) {
					FeederDataResponse feederData = new FeederDataResponse();

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
					feederData.setSubdivisionName(feeder.getSubdevision_name());
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
