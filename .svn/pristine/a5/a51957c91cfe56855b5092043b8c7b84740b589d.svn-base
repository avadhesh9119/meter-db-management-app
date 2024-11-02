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
import com.global.meter.business.model.Substations;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.data.repository.DtTransRepository;
import com.global.meter.data.repository.FeedersRepository;
import com.global.meter.data.repository.SubstationRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.SubstationsDataRequest;
import com.global.meter.inventive.models.SubstationsDataResponse;
import com.global.meter.inventive.service.SubstationsService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class SubstationsServiceImpl implements SubstationsService {

	private static final Logger LOG = LoggerFactory.getLogger(SubstationsServiceImpl.class);

	@Autowired
	SubstationRepository subStationRepo;

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
	FeedersRepository feedersRepository;
	
	@Autowired
	DtTransRepository dtTransRepository;

	
	@Override
	public CommonResponse addSubstation(List<SubstationsDataRequest> subStationDataRequest) {
		List<Substations> subStation = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Wrapping Sub Stations Data to save in DB:--> ");

		try {
			for (SubstationsDataRequest subDataRequest : subStationDataRequest) {
				Substations subSta = new Substations();
				
				if((subDataRequest.getSource() == null || StringUtils.isEmpty(subDataRequest.getSource())) || 
						(subDataRequest.getUserId() == null || StringUtils.isEmpty(subDataRequest.getUserId()))) {
					response.setMessage("Source & UserId can not be empty");
					response.setCode(404);
					response.setError(true);
					return response;
					}

				if (!StringUtils.isEmpty(subDataRequest.getSubstationName())) {
					Optional<Substations> subStaRepo = subStationRepo.findById(subDataRequest.getSubstationName());

					if (subStaRepo.isPresent()) {
						error.setErrorMessage(
								subDataRequest.getSubstationName() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}

				subSta.setSubstation_name(subDataRequest.getSubstationName());
				subSta.setCreated(new Date(System.currentTimeMillis()));
				subSta.setLatitude(subDataRequest.getLatitude());
				subSta.setLongitude(subDataRequest.getLongitude());
				subSta.setModified(new Date(System.currentTimeMillis()));
				subSta.setOwner_name(subDataRequest.getUtility());
				subSta.setSubdevision_name(subDataRequest.getSubdivisionName());
				subSta.setSource(subDataRequest.getSource());
				subSta.setUser_id(subDataRequest.getUserId());

				subStation.add(subSta);
			}

			subStationRepo.saveAll(subStation);
			LOG.info("Substation Info Successfully Stored in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.error("Issue in storing data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse updateSubstation(List<SubstationsDataRequest> subStationDataRequest) {

		List<Substations> substationsList = new ArrayList<>();
		List<DevicesInfo> devInfoList = new ArrayList<>();
		List<DtTrans> dtTransList= new ArrayList<>();
		List<Feeders> feedersList = new ArrayList<>();
		String table = meterConfiguration.getKeyspace() + "." +Tables.DEVICES_INFO;
		String feederTable = meterConfiguration.getKeyspace() + "." +Tables.FEEDERS;
		String dtTable = meterConfiguration.getKeyspace() + "." +Tables.DT_TRANS;
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Substations Info Data to update in DB:--> ");

		try {
			for (SubstationsDataRequest subDataRequest : subStationDataRequest) {
				Substations subStation = new Substations();
				
				if(subDataRequest.getUpdatedBy() == null || StringUtils.isEmpty(subDataRequest.getUpdatedBy())) {
					response.setMessage("Updated by can not be empty");
					response.setCode(404);
					response.setError(true);
					return response;
					}
				
				if (!StringUtils.isEmpty(subDataRequest.getSubstationName())) {
					Optional<Substations> subStaRepo = subStationRepo.findById(subDataRequest.getSubstationName());

					if (!subStaRepo.isPresent()) {
						error.setErrorMessage(
								subDataRequest.getSubstationName() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					subStation = subStaRepo.get();
				}

				subStation.setSubstation_name(subDataRequest.getSubstationName());
				subStation.setLatitude(
						subDataRequest.getLatitude() != null ? subDataRequest.getLatitude() : subStation.getLatitude());
				subStation.setLongitude(subDataRequest.getLongitude() != null ? subDataRequest.getLongitude()
						: subStation.getLongitude());
				subStation.setModified(new Date(System.currentTimeMillis()));
				subStation.setOwner_name(
						subDataRequest.getUtility() != null ? subDataRequest.getUtility() : subStation.getOwner_name());
				subStation.setSubdevision_name(
						subDataRequest.getSubdivisionName() != null ? subDataRequest.getSubdivisionName()
								: subStation.getsubdevision_name());
				subStation.setUpdated_by(subDataRequest.getUpdatedBy());

				substationsList.add(subStation);
				
				//
				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT * FROM ").append(table).append(" where substation_name = '").append(subDataRequest.getSubstationName())
				.append("'");
				String query = queryBuilder.substring(0, queryBuilder.length());
					
				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<DevicesInfo> devicesInfos = new ArrayList<>();
				devicesInfos = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<DevicesInfo>>() {});
				
				for(DevicesInfo devInfo : devicesInfos) {
					devInfo.setSubdevision_name(subDataRequest.getSubdivisionName()
						!= null ? subDataRequest.getSubdivisionName() : devInfo.getSubdevision_name());
				
				devInfoList.add(devInfo);
				}
				StringBuilder queryBuilderFeeder = new StringBuilder();
				queryBuilderFeeder.append("SELECT * FROM ").append(feederTable).append(" where substation_name = '").append(subDataRequest.getSubstationName())
				.append("'");
				String feederQuery = queryBuilderFeeder.substring(0, queryBuilderFeeder.length());
					
				String feederOutputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(feederQuery));

				List<Feeders> feederList = new ArrayList<>();
				feederList = CommonUtils.getMapper().readValue(feederOutputList,
						new TypeReference<List<Feeders>>() {});
				
				for(Feeders feeder : feederList) {

					StringBuilder queryBuilderDt = new StringBuilder();
					queryBuilderDt.append("SELECT * FROM ").append(dtTable).append(" where feeder_name = '").append(feeder.getFeeder_name())
					.append("'");
					String dtQuery = queryBuilderDt.substring(0, queryBuilderDt.length());
						
					String dtOutputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(dtQuery));
					
					
					List<DtTrans> dtList = new ArrayList<>();
					dtList = CommonUtils.getMapper().readValue(dtOutputList,
							new TypeReference<List<DtTrans>>() {});
					
					for(DtTrans dt:dtList) {
						
						dt.setSubdevision_name(subDataRequest.getSubdivisionName());
						dtTransList.add(dt);
					}
					feeder.setSubdevision_name(subDataRequest.getSubdivisionName());
						feedersList.add(feeder);
					}
				
			}
			dtTransRepository.saveAll(dtTransList);
			feedersRepository.saveAll(feedersList);
			subStationRepo.saveAll(substationsList);
			devicesInfoRepository.saveAll(devInfoList);
			LOG.info("Substations Info Successfully Updated in DB.");
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in updationg data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse deleteSubstation(SubstationsDataRequest subStationDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		
		LOG.info("Substations Info Data to delete from DB:--> ");

		try {

			if (!StringUtils.isEmpty(subStationDataRequest.getSubstationName())) {
				Optional<Substations> Substations = subStationRepo.findById(subStationDataRequest.getSubstationName());

				
				String table = MeterDBAppStarter.keyspace + "." + Tables.FEEDERS;
				String query = "Select * from " + table + " Where substation_name = '" + subStationDataRequest.getSubstationName() + "'";
				String queryString = CommonUtils.getMapper()
						.writeValueAsString(prestoJdbcTemplate.queryForList(query).size());
				
				Integer count = CommonUtils.getMapper().readValue(queryString,
						new TypeReference<Integer>() {
						});
				
				
				if(count != null && count > 0) {
					
					error.setErrorMessage("This substation is associated in the network. Please de-associate it and then try again"); 
                    response.setError(true); 
                    response.addErrorMessage(error); 
					return response; 
							  
				}else {
				
				
				if (!Substations.isPresent()) {
					error.setErrorMessage(
					subStationDataRequest.getSubstationName() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				subStationRepo.deleteById(subStationDataRequest.getSubstationName());
				LOG.info("Substations Info Successfully Deleted from DB.");

				response.setCode(200);
				response.setMessage(ExternalConstants.Message.DELETED);
				}
			}

		} catch (Exception e) {
			LOG.error("Issue in deleting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getSubstation(SubstationsDataRequest subStationDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		
		LOG.info("Substations Info Data to get from DB:--> ");

		try {
			SubstationsDataResponse substationDataRes = new SubstationsDataResponse();
			
			if (!StringUtils.isEmpty(subStationDataRequest.getSubstationName())) {
				Optional<Substations> substations = subStationRepo.findById(subStationDataRequest.getSubstationName());

				if (!substations.isPresent()) {
					error.setErrorMessage(
							subStationDataRequest.getSubstationName() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				Substations subStation = substations.get();
				
				substationDataRes.setSubstationName(subStation.getSubstation_name());
				substationDataRes.setCreated(
						subStation.getCreated() != null ? dateConverter.convertDateToString(subStation.getCreated())
								: null);
				substationDataRes.setLatitude(subStation.getLatitude());
				substationDataRes.setLongitude(subStation.getLongitude());
				substationDataRes.setModified(
						subStation.getModified() != null ? dateConverter.convertDateToString(subStation.getModified())
								: null);
				substationDataRes.setUtility(subStation.getOwner_name());
				substationDataRes.setSource(subStation.getSource());
				substationDataRes.setCreatedBy(subStation.getUser_id());
				substationDataRes.setLastUpdatedBy(subStation.getUpdated_by());
				substationDataRes.setSubdivisionName(subStation.getsubdevision_name());	
				

			}

			response.setData(substationDataRes);
			LOG.info("Substation Service Data Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getSubstationList() {

		CommonResponse response = new CommonResponse();
		
		LOG.info("Substations Info Data to get substation list from DB:--> ");

		try {

			List<Substations> subStations = subStationRepo.findAll();
			List<SubstationsDataResponse> substationsResList = new ArrayList<>();

			for (Substations substations : subStations) {
				SubstationsDataResponse substationDataRes = new SubstationsDataResponse();

				substationDataRes.setSubstationName(substations.getSubstation_name());
				substationDataRes.setCreated(
						substations.getCreated() != null ? dateConverter.convertDateToString(substations.getCreated())
								: null);
				substationDataRes.setLatitude(substations.getLatitude());
				substationDataRes.setLongitude(substations.getLongitude());
				substationDataRes.setModified(
						substations.getModified() != null ? dateConverter.convertDateToString(substations.getModified())
								: null);
				substationDataRes.setUtility(substations.getOwner_name());
				substationDataRes.setSubdivisionName(substations.getsubdevision_name());
				substationDataRes.setSource(substations.getSource());
				substationDataRes.setCreatedBy(substations.getUser_id());
				substationDataRes.setLastUpdatedBy(substations.getUpdated_by());

				substationsResList.add(substationDataRes);
			}

			response.setData(substationsResList);
			LOG.info("Substation List Data Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting substation list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getSubstationNameList() {
		CommonResponse response = new CommonResponse();
		
		LOG.info("Substations Info Data to get substation name list from DB:--> ");

		try {

			List<Substations> subStations = subStationRepo.findAll();

			response.setData(
					subStations.stream().map(station -> station.getSubstation_name()).collect(Collectors.toList()));
			LOG.info("Substation Name List Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting substation name list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getListBySubdivision(SubstationsDataRequest subStationDataRequest) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		
		LOG.info("Substations Info Data to get list by subdivision from DB:--> ");

		try {

			List<SubstationsDataResponse> substationsResList = new ArrayList<>();

			if (!StringUtils.isEmpty(subStationDataRequest.getSubdivisionName())) {
				Optional<List<Substations>> substations = subStationRepo
						.findBySubdivision(subStationDataRequest.getSubdivisionName());

				if (substations.get().isEmpty()) {
					error.setErrorMessage(
							subStationDataRequest.getSubdivisionName() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				List<Substations> subStationDataList = substations.get();

				for (Substations subStation : subStationDataList) {
					SubstationsDataResponse substationDataRes = new SubstationsDataResponse();
					substationDataRes.setSubstationName(subStation.getSubstation_name());
					substationDataRes.setCreated(
							subStation.getCreated() != null ? dateConverter.convertDateToString(subStation.getCreated())
									: null);
					substationDataRes.setLatitude(subStation.getLatitude());
					substationDataRes.setLongitude(subStation.getLongitude());
					substationDataRes.setModified(subStation.getModified() != null
							? dateConverter.convertDateToString(subStation.getModified())
							: null);
					substationDataRes.setUtility(subStation.getOwner_name());
					substationDataRes.setSubdivisionName(subStation.getsubdevision_name());
					substationDataRes.setSource(subStation.getSource());
					substationDataRes.setCreatedBy(subStation.getUser_id());;
					substationDataRes.setLastUpdatedBy(subStation.getUpdated_by());

					substationsResList.add(substationDataRes);
				}

			}

			response.setData(substationsResList);
			LOG.info("Substation Name List By Subdivision Data Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting substation list by subdivision due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}
	
	@Override
	public CommonResponse getSubstationsByUtility(SubstationsDataRequest subStationDataRequest) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		
		LOG.info("Substations Info Data list by utility from DB:--> ");

		try {

			List<SubstationsDataResponse> substationsResList = new ArrayList<>();

			if (!StringUtils.isEmpty(subStationDataRequest.getUtility())) {
				Optional<List<Substations>> substations = subStationRepo
						.findByUtility(subStationDataRequest.getUtility());

				if (substations.get().isEmpty()) {
					error.setErrorMessage(
							subStationDataRequest.getUtility() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				List<Substations> subStationDataList = substations.get();

				for (Substations subStation : subStationDataList) {
					SubstationsDataResponse substationDataRes = new SubstationsDataResponse();
					substationDataRes.setSubstationName(subStation.getSubstation_name());
					substationDataRes.setCreated(
							subStation.getCreated() != null ? dateConverter.convertDateToString(subStation.getCreated())
									: null);
					substationDataRes.setLatitude(subStation.getLatitude());
					substationDataRes.setLongitude(subStation.getLongitude());
					substationDataRes.setModified(subStation.getModified() != null
							? dateConverter.convertDateToString(subStation.getModified())
							: null);
					substationDataRes.setUtility(subStation.getOwner_name());
					substationDataRes.setSubdivisionName(subStation.getsubdevision_name());
					substationDataRes.setSource(subStation.getSource());
					substationDataRes.setCreatedBy(subStation.getUser_id());;
					substationDataRes.setLastUpdatedBy(subStation.getUpdated_by());

					substationsResList.add(substationDataRes);
				}

			}

			response.setData(substationsResList);
			LOG.info("Substation List By utility Data Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting substation list by utility due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}
}
