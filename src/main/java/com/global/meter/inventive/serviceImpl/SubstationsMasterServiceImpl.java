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
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.DtTransMaster;
import com.global.meter.business.model.FeedersMaster;
import com.global.meter.business.model.SubdivisionsMaster;
import com.global.meter.business.model.SubstationsMaster;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.data.repository.DtTransMasterRepository;
import com.global.meter.data.repository.FeedersMasterRepository;
import com.global.meter.data.repository.OwnersRepository;
import com.global.meter.data.repository.SubdivisionsMasterRepository;
import com.global.meter.data.repository.SubstationsMasterRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.SubstationsMasterDataRequest;
import com.global.meter.inventive.models.SubstationsMasterDataResponse;
import com.global.meter.inventive.service.SubstationsMasterService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class SubstationsMasterServiceImpl implements SubstationsMasterService {

	private static final Logger LOG = LoggerFactory.getLogger(SubstationsServiceImpl.class);

	@Autowired
	SubstationsMasterRepository substationsMasterRepository;
	
	@Autowired
	SubdivisionsMasterRepository subDivisionRepo;
	
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
	FeedersMasterRepository feedersRepository;

	@Autowired
	DtTransMasterRepository dtTransRepository;

	@Override
	public CommonResponse addSubstation(List<SubstationsMasterDataRequest> subStationDataRequest) {
		List<SubstationsMaster> subStation = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		int iAmId = 0;
		LOG.info("Wrapping Sub Stations Data to save in DB:--> ");

		try {
			String table = meterConfiguration.getKeyspace() + "." + Tables.SUBSTATIONS_MASTER;

			for (SubstationsMasterDataRequest subDataRequest : subStationDataRequest) {
				SubstationsMaster subSta = new SubstationsMaster();

				if ((subDataRequest.getSource() == null || StringUtils.isEmpty(subDataRequest.getSource()))
						|| (subDataRequest.getUserId() == null || StringUtils.isEmpty(subDataRequest.getUserId()))) {
					error.setErrorMessage("Source & UserId can not be empty");
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				if (!StringUtils.isEmpty(subDataRequest.getHesSubstationId())) {
					Optional<SubstationsMaster> subStaRepo = substationsMasterRepository
							.findByhesSubstationId(subDataRequest.getHesSubstationId());

					if (subStaRepo.isPresent()) {
						error.setErrorMessage(
								subDataRequest.getSubstationName() + " : " + ExternalConstants.Message.EXISTS);
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

				List<SubstationsMaster> id = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<SubstationsMaster>>() {
						});
				iAmId = id.stream().iterator().next().getI_am_id();

				subSta.setI_am_id(iAmId);
				subSta.setHes_substation_id(subDataRequest.getHesSubstationId());
				subSta.setHes_subdivision_id(subDataRequest.getSubdivisionCode());
				subSta.setHes_owner_id(subDataRequest.getOwnerId());
				subSta.setSubstation_name(subDataRequest.getSubstationName());
				subSta.setCreated(new Date(System.currentTimeMillis()));
				subSta.setLatitude(subDataRequest.getLatitude());
				subSta.setLongitude(subDataRequest.getLongitude());
				subSta.setModified(new Date(System.currentTimeMillis()));
				subSta.setOwner_name(subDataRequest.getOwnerName());
				subSta.setSubdivision_name(subDataRequest.getSubdivisionName());
				subSta.setSource(subDataRequest.getSource());
				subSta.setUser_id(subDataRequest.getUserId());
				subSta.setAddress(subDataRequest.getAddress());

				subStation.add(subSta);

			}

			substationsMasterRepository.saveAll(subStation);
			LOG.info("Substation Info Successfully Stored in DB.");
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
	public CommonResponse updateSubstation(List<SubstationsMasterDataRequest> subStationDataRequest) {

		List<SubstationsMaster> substationsList = new ArrayList<>();
		List<DevicesInfo> devInfoList = new ArrayList<>();
		List<DtTransMaster> dtTransList = new ArrayList<>();
		List<FeedersMaster> feedersList = new ArrayList<>();
		String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_INFO;
		String feederTable = meterConfiguration.getKeyspace() + "." + Tables.FEEDERS_MASTER;
		String dtTable = meterConfiguration.getKeyspace() + "." + Tables.DT_TRANS_MASTER;
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		SubdivisionsMaster subdivision = new SubdivisionsMaster();

		LOG.info("Substations Info Data to update in DB:--> ");

		try {
			for (SubstationsMasterDataRequest subDataRequest : subStationDataRequest) {
				SubstationsMaster subStation = new SubstationsMaster();

				if (subDataRequest.getUpdatedBy() == null || StringUtils.isEmpty(subDataRequest.getUpdatedBy())) {
					error.setErrorMessage("Updated by can not be empty");
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				if (!StringUtils.isEmpty(subDataRequest.getHesSubstationId())) {
					Optional<SubstationsMaster> subStaRepo = substationsMasterRepository
							.findByhesSubstationId(subDataRequest.getHesSubstationId());

					if (!subStaRepo.isPresent()) {
						error.setErrorMessage(
								subDataRequest.getHesSubstationId() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					subStation = subStaRepo.get();
				}
				
				subStation.setHes_substation_id(subDataRequest.getHesSubstationId());
				subStation.setHes_subdivision_id(subDataRequest.getSubdivisionCode() != null ? subDataRequest.getSubdivisionCode()
								: subStation.getHes_subdivision_id());
				subStation.setHes_owner_id(subDataRequest.getOwnerId() != null ? subDataRequest.getOwnerId()
						: subStation.getHes_owner_id());
				subStation.setSubstation_name(subDataRequest.getSubstationName());
				subStation.setLatitude(
						subDataRequest.getLatitude() != null ? subDataRequest.getLatitude() : subStation.getLatitude());
				subStation.setLongitude(subDataRequest.getLongitude() != null ? subDataRequest.getLongitude()
						: subStation.getLongitude());
				subStation.setModified(new Date(System.currentTimeMillis()));
				subStation.setOwner_name(
						subDataRequest.getOwnerName() != null ? subDataRequest.getOwnerName() : subStation.getOwner_name());
				subStation.setSubdivision_name(
						subDataRequest.getSubdivisionName() != null ? subDataRequest.getSubdivisionName()
								: subStation.getSubdivision_name());
				subStation.setUpdated_by(subDataRequest.getUpdatedBy());

				substationsList.add(subStation);
				response.setiAmId(String.valueOf(subStation.getI_am_id()));
				// update devices Info
				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT * FROM ").append(table).append(" where substation_name = '")
						.append(subDataRequest.getSubstationName()).append("'");
				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<DevicesInfo> devicesInfos = new ArrayList<>();
				devicesInfos = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DevicesInfo>>() {
				});

				for (DevicesInfo devInfo : devicesInfos) {
					devInfo.setSubdevision_name(
							subdivision.getSubdivision_name() != null ? subdivision.getSubdivision_name()
									: devInfo.getSubdevision_name());

					devInfoList.add(devInfo);
				}
				// update feeder
				StringBuilder queryBuilderFeeder = new StringBuilder();
				queryBuilderFeeder.append("SELECT * FROM ").append(feederTable).append(" where hes_substation_id = '")
						.append(subDataRequest.getHesSubstationId()).append("'");
				String feederQuery = queryBuilderFeeder.substring(0, queryBuilderFeeder.length());

				String feederOutputList = CommonUtils.getMapper()
						.writeValueAsString(prestoJdbcTemplate.queryForList(feederQuery));

				List<FeedersMaster> feederList = new ArrayList<>();
				feederList = CommonUtils.getMapper().readValue(feederOutputList,
						new TypeReference<List<FeedersMaster>>() {
						});

				for (FeedersMaster feeder : feederList) {
					// update dt
					StringBuilder queryBuilderDt = new StringBuilder();
					queryBuilderDt.append("SELECT * FROM ").append(dtTable).append(" where hes_feeder_id = '")
							.append(feeder.getHes_feeder_id()).append("'");
					String dtQuery = queryBuilderDt.substring(0, queryBuilderDt.length());

					String dtOutputList = CommonUtils.getMapper()
							.writeValueAsString(prestoJdbcTemplate.queryForList(dtQuery));

					List<DtTransMaster> dtList = new ArrayList<>();
					dtList = CommonUtils.getMapper().readValue(dtOutputList, new TypeReference<List<DtTransMaster>>() {
					});

					for (DtTransMaster dt : dtList) {

						dt.setSubdevision_name(subdivision.getSubdivision_name() != null ? subdivision.getSubdivision_name() : dt.getSubdevision_name());
						dt.setHes_subdivision_id(subDataRequest.getSubdivisionCode() != null ? subDataRequest.getSubdivisionCode() : dt.getHes_subdivision_id());

						dtTransList.add(dt);
					}
					feeder.setSubdivision_name(subdivision.getSubdivision_name() != null ? subdivision.getSubdivision_name() : feeder.getSubdivision_name());
					feeder.setHes_subdivision_id(subDataRequest.getSubdivisionCode() != null ? subDataRequest.getSubdivisionCode() : feeder.getHes_subdivision_id());

					feedersList.add(feeder);
				}

			}
			dtTransRepository.saveAll(dtTransList);
			feedersRepository.saveAll(feedersList);
			substationsMasterRepository.saveAll(substationsList);
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
	public CommonResponse deleteSubstation(SubstationsMasterDataRequest subStationDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		SubstationsMaster substation = new SubstationsMaster();
		LOG.info("Substations Info Data to delete from DB:--> ");

		try {

			if (!StringUtils.isEmpty(subStationDataRequest.getHesSubstationId())) {
				Optional<SubstationsMaster> substations = substationsMasterRepository
						.findByhesSubstationId(subStationDataRequest.getHesSubstationId());

				String table = meterConfiguration.getKeyspace() + "." + Tables.FEEDERS_MASTER;
				String query = "Select * from " + table + " Where hes_substation_id = '"
						+ subStationDataRequest.getHesSubstationId() + "'";
				String queryString = CommonUtils.getMapper()
						.writeValueAsString(prestoJdbcTemplate.queryForList(query).size());

				Integer count = CommonUtils.getMapper().readValue(queryString, new TypeReference<Integer>() {
				});

				if (count != null && count > 0) {

					error.setErrorMessage(
							"This substation is associated in the network. Please de-associate it and then try again");
					response.setError(true);
					response.addErrorMessage(error);
					return response;

				} else {
					if (!substations.isPresent()) {
						error.setErrorMessage(subStationDataRequest.getHesSubstationId() + " : "
								+ ExternalConstants.Message.NOT_EXISTS);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					substation = substations.get();
					substationsMasterRepository.deleteById(substation.getI_am_id());
					response.setiAmId(String.valueOf(substation.getI_am_id()));
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
	public CommonResponse getSubstation(SubstationsMasterDataRequest subStationDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Substations Info Data to get from DB:--> ");

		try {
			SubstationsMasterDataResponse substationDataRes = new SubstationsMasterDataResponse();

			if (!StringUtils.isEmpty(subStationDataRequest.getHesSubstationId())) {
				Optional<SubstationsMaster> substations = substationsMasterRepository
						.findByhesSubstationId(subStationDataRequest.getHesSubstationId());

				if (!substations.isPresent()) {
					error.setErrorMessage(
							subStationDataRequest.getHesSubstationId() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				SubstationsMaster subStation = substations.get();

				substationDataRes.setiAmId(String.valueOf(subStation.getI_am_id()));
				substationDataRes.setHesOwnerId(subStation.getHes_owner_id());
				substationDataRes.setHesSubdivisionId(subStation.getHes_subdivision_id());
				substationDataRes.setHesSubstationId(subStation.getHes_substation_id());
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

			List<SubstationsMaster> subStations = substationsMasterRepository.findAll();
			List<SubstationsMasterDataResponse> substationsResList = new ArrayList<>();

			for (SubstationsMaster substations : subStations) {
				SubstationsMasterDataResponse substationDataRes = new SubstationsMasterDataResponse();

				substationDataRes.setiAmId(String.valueOf(substations.getI_am_id()));
				substationDataRes.setHesOwnerId(substations.getHes_owner_id());
				substationDataRes.setHesSubdivisionId(substations.getHes_subdivision_id());
				substationDataRes.setHesSubstationId(substations.getHes_substation_id());

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
				substationDataRes.setSubdivisionName(substations.getSubdivision_name());
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

			List<SubstationsMaster> subStations = substationsMasterRepository.findAll();

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
	public CommonResponse getListBySubdivision(SubstationsMasterDataRequest subStationDataRequest) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Substations Info Data to get list by subdivision from DB:--> ");

		try {

			List<SubstationsMasterDataResponse> substationsResList = new ArrayList<>();

			if (!StringUtils.isEmpty(subStationDataRequest.getSubdivisionCode())) {
				Optional<List<SubstationsMaster>> substations = substationsMasterRepository
						.findBySubdivisionId(subStationDataRequest.getSubdivisionCode());

				if (substations.get().isEmpty()) {
					error.setErrorMessage(
							subStationDataRequest.getSubdivisionCode() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				List<SubstationsMaster> subStationDataList = substations.get();

				for (SubstationsMaster subStation : subStationDataList) {
					SubstationsMasterDataResponse substationDataRes = new SubstationsMasterDataResponse();

					substationDataRes.setSubstationCode(subStation.getHes_substation_id());
					substationDataRes.setSubstationName(subStation.getSubstation_name());

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
	public CommonResponse getSubstationsByUtility(SubstationsMasterDataRequest subStationDataRequest) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Substations Info Data list by utility from DB:--> ");

		try {

			List<SubstationsMasterDataResponse> substationsResList = new ArrayList<>();

			if (!StringUtils.isEmpty(subStationDataRequest.getOwnerId())) {
				Optional<List<SubstationsMaster>> substations = substationsMasterRepository
						.findByUtility(subStationDataRequest.getOwnerId());

				if (substations.get().isEmpty()) {
					error.setErrorMessage(
							subStationDataRequest.getUtility() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				List<SubstationsMaster> subStationDataList = substations.get();

				for (SubstationsMaster subStation : subStationDataList) {
					SubstationsMasterDataResponse substationDataRes = new SubstationsMasterDataResponse();

					substationDataRes.setiAmId(String.valueOf(subStation.getI_am_id()));
					substationDataRes.setHesOwnerId(subStation.getHes_owner_id());
					substationDataRes.setHesSubdivisionId(subStation.getHes_subdivision_id());
					substationDataRes.setHesSubstationId(subStation.getHes_substation_id());

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
					substationDataRes.setSubdivisionName(subStation.getSubdivision_name());
					substationDataRes.setSource(subStation.getSource());
					substationDataRes.setCreatedBy(subStation.getUser_id());
					;
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
