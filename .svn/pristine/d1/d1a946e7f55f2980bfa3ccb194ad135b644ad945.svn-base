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
import com.global.meter.business.model.Subdivisions;
import com.global.meter.data.repository.SubdivisionsRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.SubdivisionsDataRequest;
import com.global.meter.inventive.models.SubdivisionsDataResponse;
import com.global.meter.inventive.service.SubdivisionsService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Service
public class SubdivisionsServiceImpl implements SubdivisionsService {

	private static final Logger LOG = LoggerFactory.getLogger(SubdivisionsServiceImpl.class);

	@Autowired
	SubdivisionsRepository subDivisionRepo;

	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Override
	public CommonResponse addSubdivision(List<SubdivisionsDataRequest> subDivisionDataRequest) {
		List<Subdivisions> subDivision = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Wrapping Sub Divisions Data to save in DB:--> ");

		try {
			for (SubdivisionsDataRequest dataRequest : subDivisionDataRequest) {
				Subdivisions subDiv = new Subdivisions();
				
				if((dataRequest.getSource() == null || StringUtils.isEmpty(dataRequest.getSource())) || 
						(dataRequest.getUserId() == null || StringUtils.isEmpty(dataRequest.getUserId()))) {
					response.setMessage("Source & UserId can not be empty");
					response.setCode(404);
					response.setError(true);
					return response;
					}
				if (!StringUtils.isEmpty(dataRequest.getSubdivisionName())) {
					Optional<Subdivisions> subDivRepo = subDivisionRepo.findById(dataRequest.getSubdivisionName());

					if (subDivRepo.isPresent()) {
						error.setErrorMessage(
								dataRequest.getSubdivisionName() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}

				subDiv.setSubdivision_name(dataRequest.getSubdivisionName());
				subDiv.setCreated(new Date(System.currentTimeMillis()));
				subDiv.setLatitude(dataRequest.getLatitude());
				subDiv.setLongitude(dataRequest.getLongitude());
				subDiv.setModified(new Date(System.currentTimeMillis()));
				subDiv.setOwner_name(dataRequest.getUtility());
				subDiv.setSource(dataRequest.getSource());
				subDiv.setUser_id(dataRequest.getUserId());

				subDivision.add(subDiv);
			}

			subDivisionRepo.saveAll(subDivision);
			LOG.info("Subdivisions Info Successfully Stored in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.error("Issue in storing data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateSubdivision(List<SubdivisionsDataRequest> subDivisionDataRequest) {
		List<Subdivisions> subDivision = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Subdivisions Info Data to update in DB:--> ");

		try {
			for (SubdivisionsDataRequest dataRequest : subDivisionDataRequest) {
				Subdivisions subDiv = new Subdivisions();
				if(dataRequest.getUpdatedBy() == null || StringUtils.isEmpty(dataRequest.getUpdatedBy())) {
					response.setMessage("Updated by can not be empty");
					response.setCode(404);
					response.setError(true);
					return response;
					}

				if (!StringUtils.isEmpty(dataRequest.getSubdivisionName())) {
					Optional<Subdivisions> subDivRepo = subDivisionRepo.findById(dataRequest.getSubdivisionName());

					if (!subDivRepo.isPresent()) {
						error.setErrorMessage(
								dataRequest.getSubdivisionName() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					subDiv = subDivRepo.get();
				}

				subDiv.setSubdivision_name(dataRequest.getSubdivisionName() != null ? dataRequest.getSubdivisionName()
						: subDiv.getSubdivision_name());
				subDiv.setLatitude(
						dataRequest.getLatitude() != null ? dataRequest.getLatitude() : subDiv.getLatitude());
				subDiv.setLongitude(
						dataRequest.getLongitude() != null ? dataRequest.getLongitude() : subDiv.getLongitude());
				subDiv.setModified(new Date(System.currentTimeMillis()));
				subDiv.setOwner_name(
						dataRequest.getUtility() != null ? dataRequest.getUtility() : subDiv.getOwner_name());
				subDiv.setUpdated_by(
						dataRequest.getUpdatedBy() != null ? dataRequest.getUpdatedBy() : subDiv.getUpdated_by());


				subDivision.add(subDiv);
				
			}
			subDivisionRepo.saveAll(subDivision);
			LOG.info("Subdivisions Info Successfully Updated in DB.");
			// selet * from hes_db.substation where substation_name = "Delhi";
			//update devices_info SET subdevision = "SUBD0001", WHERE substation_name = "Delhi";

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in updating data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse deleteSubdivision(SubdivisionsDataRequest subDivisionDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		
		LOG.info("Subdivisions Info Data to delete from:--> ");

		try {

			if (!StringUtils.isEmpty(subDivisionDataRequest.getSubdivisionName())) {
				Optional<Subdivisions> Subdivisions = subDivisionRepo
						.findById(subDivisionDataRequest.getSubdivisionName());
				
				
				String table = MeterDBAppStarter.keyspace + "." + Tables.SUBSTATIONS;
				String query = "Select * from " + table + " Where subdevision_name = '" + subDivisionDataRequest.getSubdivisionName() + "'";
				String queryString = CommonUtils.getMapper()
						.writeValueAsString(prestoJdbcTemplate.queryForList(query).size());
				
				Integer count = CommonUtils.getMapper().readValue(queryString,
						new TypeReference<Integer>() {
						});
				
				
				if(count != null && count > 0) {
					
					error.setErrorMessage("This subdivision is associated in the network. Please de-associate it and then try again"); 
                    response.setError(true); 
                    response.addErrorMessage(error); 
                    return response; 
							  
				}else {

				if (!Subdivisions.isPresent()) {
					error.setErrorMessage(
					subDivisionDataRequest.getSubdivisionName() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				subDivisionRepo.deleteById(subDivisionDataRequest.getSubdivisionName());
				LOG.info("Subdivisions Info Successfully Deleted from DB.");

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
	public CommonResponse getSubdivision(SubdivisionsDataRequest subDivisionDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Subdivisions Info Data to get from DB:--> ");

		try {
			SubdivisionsDataResponse subDivRes = new SubdivisionsDataResponse();
			if (!StringUtils.isEmpty(subDivisionDataRequest.getSubdivisionName())) {
				Optional<Subdivisions> subDivisions = subDivisionRepo
						.findById(subDivisionDataRequest.getSubdivisionName());

				if (!subDivisions.isPresent()) {
					error.setErrorMessage(
							subDivisionDataRequest.getSubdivisionName() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				Subdivisions subDiv = subDivisions.get();

				subDivRes.setSubdivisionName(subDiv.getSubdivision_name());
				subDivRes.setCreated(
						subDiv.getCreated() != null ? dateConverter.convertDateToString(subDiv.getCreated()) : null);
				subDivRes.setLatitude(subDiv.getLatitude());
				subDivRes.setLongitude(subDiv.getLongitude());
				subDivRes.setModified(
						subDiv.getModified() != null ? dateConverter.convertDateToString(subDiv.getModified()) : null);
				subDivRes.setUtility(subDiv.getOwner_name());
				subDivRes.setSource(subDiv.getSource());
				subDivRes.setCreatedBy(subDiv.getUser_id());
				subDivRes.setLastUpdatedBy(subDiv.getUpdated_by());

			}

			response.setData(subDivRes);
			LOG.info("Subdivision Service Data Response Success.");
			
		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getSubdivisionList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Subdivisions Info Data to get list from DB:--> ");

		try {

			List<Subdivisions> subDivisions = subDivisionRepo.findAll();
			List<SubdivisionsDataResponse> subDivResList = new ArrayList<>();

			for (Subdivisions subdivisions : subDivisions) {
				SubdivisionsDataResponse subDivisionDataRequest = new SubdivisionsDataResponse();

				subDivisionDataRequest.setSubdivisionName(subdivisions.getSubdivision_name());
				subDivisionDataRequest.setCreated(
						subdivisions.getCreated() != null ? dateConverter.convertDateToString(subdivisions.getCreated())
								: null);
				subDivisionDataRequest.setLatitude(subdivisions.getLatitude());
				subDivisionDataRequest.setLongitude(subdivisions.getLongitude());
				subDivisionDataRequest.setModified(subdivisions.getModified() != null
						? dateConverter.convertDateToString(subdivisions.getModified())
						: null);
				subDivisionDataRequest.setUtility(subdivisions.getOwner_name());
				subDivisionDataRequest.setSource(subdivisions.getSource());
				subDivisionDataRequest.setCreatedBy(subdivisions.getUser_id());
				subDivisionDataRequest.setLastUpdatedBy(subdivisions.getUpdated_by());

				subDivResList.add(subDivisionDataRequest);
			}

			response.setData(subDivResList);
			LOG.info("Subdivision  Service Data List Response Success.");
			

		} catch (Exception e) {
			LOG.error("Issue in getting subdivision list from DB due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getSubdivisionNameList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Subdivisions Info Data to get subdivision namelist from DB:--> ");

		try {
			List<Subdivisions> subDivisions = subDivisionRepo.findAll();

			response.setData(
					subDivisions.stream().map(division -> division.getSubdivision_name()).collect(Collectors.toList()));
			LOG.info("Subdivision Name List Service Response Success.");
			

		} catch (Exception e) {
			LOG.error("Issue in getting subdivision name list from DB due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getListByUtility(SubdivisionsDataRequest subDivisionDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Subdivisions Info Data to get list by utility from DB:--> ");

		try {

			List<SubdivisionsDataResponse> subDivResList = new ArrayList<>();

			if (!StringUtils.isEmpty(subDivisionDataRequest.getUtility())) {
				Optional<List<Subdivisions>> subDivisions = subDivisionRepo
						.findByUtility(subDivisionDataRequest.getUtility());

				if (subDivisions.get().isEmpty()) {
					error.setErrorMessage(
							subDivisionDataRequest.getUtility() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				List<Subdivisions> subDivDataList = subDivisions.get();

				for (Subdivisions subDiv : subDivDataList) {
					SubdivisionsDataResponse dataResponse = new SubdivisionsDataResponse();
					dataResponse.setSubdivisionName(subDiv.getSubdivision_name());
					dataResponse.setSubdivisionName(subDiv.getSubdivision_name());
					dataResponse.setCreated(
							subDiv.getCreated() != null ? dateConverter.convertDateToString(subDiv.getCreated())
									: null);
					dataResponse.setLatitude(subDiv.getLatitude());
					dataResponse.setLongitude(subDiv.getLongitude());
					dataResponse.setModified(
							subDiv.getModified() != null ? dateConverter.convertDateToString(subDiv.getModified())
									: null);
					dataResponse.setUtility(subDiv.getOwner_name());
					dataResponse.setSource(subDiv.getSource());
					dataResponse.setCreatedBy(subDiv.getUser_id());
					dataResponse.setLastUpdatedBy(subDiv.getUpdated_by());

					subDivResList.add(dataResponse);
					
				}

			}

			response.setData(subDivResList);
			LOG.info("Subdivision Name List Response By utility Data Response Success.");
			
		} catch (Exception e) {
			LOG.error("Issue in getting list by utility from DB due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

}
