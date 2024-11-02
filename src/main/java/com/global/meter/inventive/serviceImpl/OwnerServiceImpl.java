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
import com.global.meter.business.model.Owners;
import com.global.meter.data.repository.OwnersRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.OwnerDataRequest;
import com.global.meter.inventive.models.OwnerDataResponse;
import com.global.meter.inventive.service.OwnerService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class OwnerServiceImpl implements OwnerService {

	private static final Logger LOG = LoggerFactory.getLogger(SubstationsServiceImpl.class);

	@Autowired
	OwnersRepository ownersRepository;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Override
	public CommonResponse addOwner(List<OwnerDataRequest> ownerDataRequests) {
		List<Owners> owners = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		String hesOwnerId = "";
		String table = meterConfiguration.getKeyspace() + "." + Tables.UTILITY_MASTER;
		LOG.info("Wrapping Owner Data to save in DB:--> ");

		try {
			for (OwnerDataRequest ownerDataRequest : ownerDataRequests) {
				Owners owner = new Owners();

				if (!StringUtils.isEmpty(ownerDataRequest.getLevel1Id())) {
					Optional<Owners> ownerRepo = ownersRepository.findById(Integer.valueOf(ownerDataRequest.getLevel1Id()));

					if (ownerRepo.isPresent()) {
						error.setErrorMessage(
								ownerDataRequest.getLevel1Id() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}

				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT max(hes_owner_id) as hes_owner_id FROM ").append(table);

				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<Owners> id = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<Owners>>() {
				});
			    hesOwnerId = String.valueOf(Integer.valueOf(id.stream().iterator().next().getHes_owner_id() != null 
			    		? id.stream().iterator().next().getHes_owner_id() : "0")+1);
			    
				owner.setI_am_id(Integer.valueOf(ownerDataRequest.getLevel1Id()));
				owner.setHes_owner_id(hesOwnerId);
				owner.setOwner_name(ownerDataRequest.getOwnerName());
				owner.setOwner_code(ownerDataRequest.getOwnerCode());
				owner.setEmail(ownerDataRequest.getEmail());
				owner.setCompany_id(ownerDataRequest.getCompanyId());
				owner.setIs_active(ownerDataRequest.getIsActive());
				owner.setCreated_by(ownerDataRequest.getCreatedBy());
				owner.setCreated_on(new Date(System.currentTimeMillis()));
				
				owners.add(owner);
			}

			ownersRepository.saveAll(owners);
			LOG.info("owner Info Successfully Stored in DB.");
			
			response.setHesId(hesOwnerId);
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.error("Issue in storing data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse updateOwner(List<OwnerDataRequest> ownerDataRequests) {
		List<Owners> ownersList = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Owners Info Data to update in DB:--> ");

		try {
			for (OwnerDataRequest ownerDataRequest : ownerDataRequests) {
				Owners owners = new Owners();

				if (!StringUtils.isEmpty(ownerDataRequest.getLevel1Id())) {
					Optional<Owners> ownerRepo = ownersRepository.findById(Integer.valueOf(ownerDataRequest.getLevel1Id()));

					if (!ownerRepo.isPresent()) {
						error.setErrorMessage(
								ownerDataRequest.getLevel1Id() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					owners = ownerRepo.get();
				}
				owners.setEmail(ownerDataRequest.getEmail() != null ? ownerDataRequest.getEmail() : owners.getEmail());
				owners.setIs_active(ownerDataRequest.getIsActive() != null ? ownerDataRequest.getIsActive() : owners.getIs_active());
				owners.setUpdated_by(ownerDataRequest.getUpdatedBy() != null ? ownerDataRequest.getUpdatedBy() : owners.getUpdated_by());
				owners.setModified(new Date(System.currentTimeMillis()));
				owners.setOwner_name(ownerDataRequest.getOwnerName() != null ? ownerDataRequest.getOwnerName() : owners.getOwner_name());
				owners.setOwner_code(ownerDataRequest.getOwnerCode() != null ? ownerDataRequest.getOwnerCode() : owners.getOwner_code());

				response.setHesId(owners.getHes_owner_id());
				ownersList.add(owners);

			}
			ownersRepository.saveAll(ownersList);
			LOG.info("Owners Info Successfully Updated in DB.");
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in updationg data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getOwner(OwnerDataRequest ownerDataRequests) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Owners Info Data to get from DB:--> ");

		try {
			OwnerDataResponse ownerDataResponse = new OwnerDataResponse();

			if (!StringUtils.isEmpty(ownerDataRequests.getLevel1Id())) {
				Optional<Owners> owners = ownersRepository.findById(Integer.parseInt(ownerDataRequests.getLevel1Id()));

				if (!owners.isPresent()) {
					error.setErrorMessage(
							ownerDataRequests.getLevel1Id() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				Owners owner = owners.get();

				ownerDataResponse.setiAmId(owner.getI_am_id());
				ownerDataResponse.setHesOwnerId(owner.getHes_owner_id());
				ownerDataResponse.setEmail(owner.getEmail());
				ownerDataResponse.setOwnerName(owner.getOwner_name());
				ownerDataResponse.setOwnerCode(owner.getOwner_code());
			}

			response.setData(ownerDataResponse);
			LOG.info("Owner Service Data Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getOwnerList() {
		CommonResponse response = new CommonResponse();
		List<OwnerDataResponse> ownerResponseList = new ArrayList<>();
		LOG.info("Owners Info Data list to get from DB:--> ");

		try {
			List<Owners> owners = ownersRepository.findAll();

			if (owners.isEmpty()) {
				response.setCode(200);
				response.setError(false);
				response.setMessage("Owner is not exist");
				return response;
			}
			for (Owners isdata : owners) {

				OwnerDataResponse ownerResponse = new OwnerDataResponse();
				ownerResponse.setiAmId(isdata.getI_am_id());
				ownerResponse.setHesOwnerId(isdata.getHes_owner_id());
				ownerResponse.setEmail(isdata.getEmail());
				ownerResponse.setOwnerName(isdata.getOwner_name());
				ownerResponse.setOwnerCode(isdata.getOwner_code());

				ownerResponseList.add(ownerResponse);
			}
			response.setData(ownerResponseList);
			LOG.info("Owner Service Data Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

//	@Override
//	public CommonResponse deleteOwner(OwnerDataRequest ownerDataRequests) {
//		CommonResponse response = new CommonResponse();
//		ErrorData error = new ErrorData();
//		Owners owners = new Owners();
//		LOG.info("Owners Info Data to delete from DB:--> ");
//
//		try {
//
//			if (!StringUtils.isEmpty(ownerDataRequests.getOwnerId())) {
//				Optional<Owners> owner = ownersRepository.findByOwnerId(ownerDataRequests.getOwnerId());
//
//				if (!owner.isPresent()) {
//					error.setErrorMessage(
//							ownerDataRequests.getOwnerId() + " : " + ExternalConstants.Message.NOT_EXISTS);
//					response.setCode(404);
//					response.setError(true);
//					response.addErrorMessage(error);
//					return response;
//				}
//				owners = owner.get();
//				ownersRepository.deleteById(owners.getI_am_id());
//				LOG.info("Owners Info Successfully Deleted from DB.");
//				
//				response.setiAmId(String.valueOf(owners.getI_am_id()));
//				response.setCode(200);
//				response.setMessage(ExternalConstants.Message.DELETED);
//
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

}