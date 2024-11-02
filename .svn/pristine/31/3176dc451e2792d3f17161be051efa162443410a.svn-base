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
import com.global.meter.business.model.SubdivisionsMaster;
import com.global.meter.business.model.UserHierMapping;
import com.global.meter.data.repository.CircleMasterRepository;
import com.global.meter.data.repository.DivisionMasterRepository;
import com.global.meter.data.repository.OwnersRepository;
import com.global.meter.data.repository.SubdivisionsMasterRepository;
import com.global.meter.data.repository.UserHierMappingRepository;
import com.global.meter.data.repository.ZoneMasterRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.SubdivisionMasterDataResponse;
import com.global.meter.inventive.models.SubdivisionsMasterDataRequest;
import com.global.meter.inventive.service.SubdivisionsMasterService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class SubdivisionsMasterServiceImpl implements SubdivisionsMasterService {
	private static final Logger LOG = LoggerFactory.getLogger(SubdivisionsServiceImpl.class);

	@Autowired
	SubdivisionsMasterRepository subDivisionRepo;
	
	@Autowired
	OwnersRepository ownersRepository;

	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	ZoneMasterRepository zoneRepo;
	
	@Autowired
	CircleMasterRepository circleMasterRepository;
	
	@Autowired
	DivisionMasterRepository divisionRepository;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	UserHierMappingRepository userHierMappingRepository;

	@Override
	public CommonResponse addSubdivision(List<SubdivisionsMasterDataRequest> subdivisionsDataRequest) {
		List<SubdivisionsMaster> subDivisions = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		String hesSubdivisionId = "";
		String table = meterConfiguration.getKeyspace() + "." + Tables.SUBDIVISIONS_MASTER;

		LOG.info("Wrapping Sub Divisions Data to save in DB:--> ");

		try {
			for (SubdivisionsMasterDataRequest dataRequest : subdivisionsDataRequest) {
				SubdivisionsMaster subDiv = new SubdivisionsMaster();

				if (!StringUtils.isEmpty(dataRequest.getLevel5Id())) {
					Optional<SubdivisionsMaster> subDivRepo = subDivisionRepo
							.findById(Integer.valueOf(dataRequest.getLevel5Id()));

					if (subDivRepo.isPresent()) {
						error.setErrorMessage(
								dataRequest.getLevel5Id() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}
				
				StringBuilder queryBuilder = new StringBuilder();

				queryBuilder.append("SELECT max(hes_subdivision_id) as hes_subdivision_id FROM ").append(table);

				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<SubdivisionsMaster> id = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<SubdivisionsMaster>>() {

						});

			    hesSubdivisionId = String.valueOf(Integer.valueOf(id.stream().iterator().next().getHes_subdivision_id() != null 
			    		? id.stream().iterator().next().getHes_subdivision_id() : "0")+1);
				subDiv.setI_am_id(Integer.valueOf(dataRequest.getLevel5Id()));
				subDiv.setHes_subdivision_id(hesSubdivisionId);
				subDiv.setHes_division_id(dataRequest.getLevel4Id());
				subDiv.setSubdivision_name(dataRequest.getSubdivisionName());
				subDiv.setSubdivision_code(dataRequest.getSubdivisionCode());
				subDiv.setCreated(new Date(System.currentTimeMillis()));
				subDiv.setLatitude(dataRequest.getLatitude());
				subDiv.setLongitude(dataRequest.getLongitude());
				subDiv.setSource(dataRequest.getSource());
				subDiv.setUser_id(dataRequest.getUserId());

				subDivisions.add(subDiv);
			}

			subDivisionRepo.saveAll(subDivisions);
			LOG.info("Subdivisions Info Successfully Stored in DB.");
			
            response.setHesId(hesSubdivisionId);
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.error("Issue in storing data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateSubdivision(List<SubdivisionsMasterDataRequest> subdivisionsDataRequest) {
		List<SubdivisionsMaster> subDivisions = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Subdivisions Info Data to update in DB:--> ");

		try {
			for (SubdivisionsMasterDataRequest dataRequest : subdivisionsDataRequest) {
				SubdivisionsMaster subDiv = new SubdivisionsMaster();

				if (!StringUtils.isEmpty(dataRequest.getLevel5Id())) {
					Optional<SubdivisionsMaster> subDivRepo = subDivisionRepo
							.findById(Integer.valueOf(dataRequest.getLevel5Id()));

					if (!subDivRepo.isPresent()) {
						error.setErrorMessage(
								dataRequest.getLevel5Id() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					subDiv = subDivRepo.get();
				}
				subDiv.setHes_division_id(
						dataRequest.getLevel4Id() != null ? dataRequest.getLevel4Id() : subDiv.getHes_division_id());
				subDiv.setSubdivision_code(
						dataRequest.getSubdivisionCode() != null ? dataRequest.getSubdivisionCode() : subDiv.getSubdivision_code());
				subDiv.setSubdivision_name(
						dataRequest.getSubdivisionName() != null ? dataRequest.getSubdivisionName() : subDiv.getSubdivision_name());
				subDiv.setLatitude(
						dataRequest.getLatitude() != null ? dataRequest.getLatitude() : subDiv.getLatitude());
				subDiv.setLongitude(
						dataRequest.getLongitude() != null ? dataRequest.getLongitude() : subDiv.getLongitude());
				subDiv.setModified(new Date(System.currentTimeMillis()));
				subDiv.setUpdated_by(
						dataRequest.getUpdatedBy() != null ? dataRequest.getUpdatedBy() : subDiv.getUpdated_by());
				subDiv.setSource(
						dataRequest.getSource() != null ? dataRequest.getSource() : subDiv.getSource());
				subDiv.setUser_id(
						dataRequest.getUserId() != null ? dataRequest.getUserId() : subDiv.getUser_id());

				subDivisions.add(subDiv);
				response.setHesId(subDiv.getHes_subdivision_id());

			}
			subDivisionRepo.saveAll(subDivisions);
			LOG.info("Subdivisions Info Successfully Updated in DB.");
			
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in updating data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

//	@Override
//	public CommonResponse deleteSubdivision(SubdivisionsMasterDataRequest subDivisionDataRequest) {
//		CommonResponse response = new CommonResponse();
//		ErrorData error = new ErrorData();
//		SubdivisionsMaster subdivisionsMaster = new SubdivisionsMaster();
//		LOG.info("Subdivisions Info Data to delete from:--> ");
//
//		try {
//
//			if (!StringUtils.isEmpty(subDivisionDataRequest.getHesSubdivisionId())) {
//				Optional<SubdivisionsMaster> subdivisions = subDivisionRepo
//						.findBySubdivisionId(subDivisionDataRequest.getHesSubdivisionId());
//
//				String table = MeterDBAppStarter.keyspace + "." + Tables.SUBSTATIONS_MASTER;
//				String query = "Select * from " + table + " Where hes_subdivision_id = '" + subDivisionDataRequest.getHesSubdivisionId() + "'";
//				String queryString = CommonUtils.getMapper()
//						.writeValueAsString(prestoJdbcTemplate.queryForList(query).size());
//				
//				Integer count = CommonUtils.getMapper().readValue(queryString,
//						new TypeReference<Integer>() {
//						});
//				
//				
//				if(count != null && count > 0) {
//					
//					error.setErrorMessage("This subdivision is associated in the network. Please de-associate it and then try again"); 
//                    response.setError(true); 
//                    response.addErrorMessage(error); 
//                    return response; 
//							  
//				}else {
//
//				if (!subdivisions.isPresent()) {
//					error.setErrorMessage(
//					subDivisionDataRequest.getSubdivisionName() + " : " + ExternalConstants.Message.NOT_EXISTS);
//					response.setCode(404);
//					response.setError(true);
//					response.addErrorMessage(error);
//					return response;
//				}
//				subdivisionsMaster = subdivisions.get();
//				subDivisionRepo.deleteById(subdivisionsMaster.getI_am_id());
//				LOG.info("Subdivisions Info Successfully Deleted from DB.");
//
//				response.setCode(200);
//				response.setMessage(ExternalConstants.Message.DELETED);
//
//			}
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
	public CommonResponse getSubdivision(SubdivisionsMasterDataRequest subDivisionDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Subdivisions Info Data to get from DB:--> ");

		try {
			SubdivisionMasterDataResponse subDivRes = new SubdivisionMasterDataResponse();
			if (!StringUtils.isEmpty(subDivisionDataRequest.getLevel5Id())) {
				Optional<SubdivisionsMaster> subDivisions = subDivisionRepo
						.findById(Integer.parseInt(subDivisionDataRequest.getLevel5Id()));

				if (!subDivisions.isPresent()) {
					error.setErrorMessage(subDivisionDataRequest.getLevel5Id() + " : "
							+ ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				SubdivisionsMaster subDiv = subDivisions.get();

				subDivRes.setiAmId(String.valueOf(subDiv.getI_am_id()));
				subDivRes.setHesSubdivisionId(subDiv.getHes_subdivision_id());
				subDivRes.setSubdivisionCode(subDiv.getSubdivision_code());;
				subDivRes.setCreated(
						subDiv.getCreated() != null ? dateConverter.convertDateToString(subDiv.getCreated()) : null);
				subDivRes.setLatitude(subDiv.getLatitude());
				subDivRes.setLongitude(subDiv.getLongitude());
				subDivRes.setModified(
						subDiv.getModified() != null ? dateConverter.convertDateToString(subDiv.getModified()) : null);
				subDivRes.setSource(subDiv.getSource());
				subDivRes.setSubdivisionName(subDiv.getSubdivision_name());
				subDivRes.setUpdatedBy(subDiv.getUpdated_by());
				subDivRes.setUserId(subDiv.getUser_id());
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

			List<SubdivisionsMaster> subDivisions = subDivisionRepo.findAll();
			List<SubdivisionMasterDataResponse> subDivResList = new ArrayList<>();

			for (SubdivisionsMaster subdivisions : subDivisions) {
				SubdivisionMasterDataResponse subDivRes = new SubdivisionMasterDataResponse();

				subDivRes.setiAmId(String.valueOf(subdivisions.getI_am_id()));
				subDivRes.setHesSubdivisionId(subdivisions.getHes_subdivision_id());
				subDivRes.setSubdivisionCode(subdivisions.getSubdivision_code());;
				subDivRes.setCreated(
						subdivisions.getCreated() != null ? dateConverter.convertDateToString(subdivisions.getCreated()) : null);
				subDivRes.setLatitude(subdivisions.getLatitude());
				subDivRes.setLongitude(subdivisions.getLongitude());
				subDivRes.setModified(
						subdivisions.getModified() != null ? dateConverter.convertDateToString(subdivisions.getModified()) : null);
				subDivRes.setSource(subdivisions.getSource());
				subDivRes.setSubdivisionName(subdivisions.getSubdivision_name());
				subDivRes.setUpdatedBy(subdivisions.getUpdated_by());
				subDivRes.setUserId(subdivisions.getUser_id());

				subDivResList.add(subDivRes);
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
	public CommonResponse getSubdivisionNameListByDivision(SubdivisionsMasterDataRequest subDivisionDataRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Subdivisions Info Data to get list from DB:--> ");

		try {

			
			List<SubdivisionMasterDataResponse> subDivResList = new ArrayList<>();
			
			String table = meterConfiguration.getKeyspace() + "." + Tables.SUBDIVISIONS_MASTER;
			String tableDivision = meterConfiguration.getKeyspace() + "." + Tables.DIVISION_MASTER;


			if (!StringUtils.isEmpty(subDivisionDataRequest.getDivisionCode())) {
				StringBuilder queryBuilder = new StringBuilder();
				
				queryBuilder.append("select s.* from ").append(table).append(" as s inner join ")
				.append(tableDivision).append(" as u on s.hes_division_id = cast(u.i_am_id as varchar)").append(" where u.division_code = '")
				.append(subDivisionDataRequest.getDivisionCode()).append("'");
				
				if(subDivisionDataRequest.getUserId() != null && !subDivisionDataRequest.getUserId().isEmpty()) {
				Optional<UserHierMapping> useHierMapping = userHierMappingRepository.findById(Integer.parseInt(subDivisionDataRequest.getUserId()));
				    if(useHierMapping.isPresent()) {
				       UserHierMapping userHier = useHierMapping.get();
				
				      if(userHier.getLevel5_id() != 0)
				      {
					   queryBuilder.append(" and s.i_am_id = ").append(userHier.getLevel5_id());
				      }
				    }   
				}	
				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<SubdivisionsMaster> subdivisionList  = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<SubdivisionsMaster>>() {

						});

			
				if (subdivisionList.size()==0) {
					error.setErrorMessage(
							subDivisionDataRequest.getDivisionCode() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

			
			for (SubdivisionsMaster subdivisions : subdivisionList) {
				SubdivisionMasterDataResponse subDivRes = new SubdivisionMasterDataResponse();

				subDivRes.setSubdivisionCode(subdivisions.getSubdivision_code());;
				subDivRes.setSubdivisionName(subdivisions.getSubdivision_name());

				subDivResList.add(subDivRes);
			 }
			}
			response.setData(subDivResList);
			LOG.info("Subdivision  Service Data List Response Success.");
		
		} catch (Exception e) {
			LOG.error("Issue in getting subdivision list from DB due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
	
//	@Override
//	public CommonResponse getListByUtility(SubdivisionsMasterDataRequest subDivisionDataRequest) {
//		CommonResponse response = new CommonResponse();
//		ErrorData error = new ErrorData();
//		LOG.info("Subdivisions Info Data to get list by utility from DB:--> ");
//
//		try {
//			List<SubdivisionMasterDataResponse> subDivResList = new ArrayList<>();
//
//			if (!StringUtils.isEmpty(subDivisionDataRequest.getOwnerName())) {
//				Optional<List<SubdivisionsMaster>> subDivisions = subDivisionRepo
//						.findByOwnerName(subDivisionDataRequest.getOwnerName());
//
//				if (subDivisions.get().isEmpty()) {
//					error.setErrorMessage(
//							subDivisionDataRequest.getOwnerName() + " : " + ExternalConstants.Message.NOT_EXISTS);
//					response.setCode(404);
//					response.setError(true);
//					response.addErrorMessage(error);
//					return response;
//				}
//
//				List<SubdivisionsMaster> subDivDataList = subDivisions.get();
//
//				for (SubdivisionsMaster subDiv : subDivDataList) {
//					SubdivisionMasterDataResponse dataResponse = new SubdivisionMasterDataResponse();
//					
//					dataResponse.setiAmId(String.valueOf(subDiv.getI_am_id()));
//					dataResponse.setHesSubdivisionId(subDiv.getHes_subdivision_id());
//					dataResponse.setOwnerId(subDiv.getOwner_id());
//					dataResponse.setSubdivisionName(subDiv.getSubdivision_name());
//					dataResponse.setSubdivisionName(subDiv.getSubdivision_name());
//					dataResponse.setCreated(
//							subDiv.getCreated() != null ? dateConverter.convertDateToString(subDiv.getCreated())
//									: null);
//					dataResponse.setLatitude(subDiv.getLatitude());
//					dataResponse.setLongitude(subDiv.getLongitude());
//					dataResponse.setModified(
//							subDiv.getModified() != null ? dateConverter.convertDateToString(subDiv.getModified())
//									: null);
//					dataResponse.setOwnerName(subDiv.getOwner_name());
//					dataResponse.setSource(subDiv.getSource());
//					dataResponse.setUserId(subDiv.getUser_id());
//					dataResponse.setUpdatedBy(subDiv.getUpdated_by());
//
//					subDivResList.add(dataResponse);
//					
//				}
//			}
//			response.setData(subDivResList);
//			LOG.info("Subdivision Name List Response By utility Data Response Success.");
//			
//		} catch (Exception e) {
//			LOG.error("Issue in getting list by utility from DB due to : " + e.getMessage());
//			error.setErrorMessage(e.getMessage());
//			response.setCode(500);
//			response.setError(true);
//			response.addErrorMessage(error);
//		}
//		return response;
//	}
}
