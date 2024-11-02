package com.global.meter.v3.inventive.serviceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.business.model.ManufacturerMaster;
import com.global.meter.v3.inventive.models.ManufacturerMasterDataRequest;
import com.global.meter.v3.inventive.models.ManufacturerMasterDataResponse;
import com.global.meter.v3.inventive.repository.ManufacturerMasterRepository;
import com.global.meter.v3.inventive.service.ManufacturerMasterService;
import com.global.meter.v3.utils.HazelcastUtil;

@Service
public class ManufacturerMasterServiceImpl implements ManufacturerMasterService {
	private static final Logger LOG = LoggerFactory.getLogger(ManufacturerMasterServiceImpl.class);

	@Autowired
	ManufacturerMasterRepository manufacturerRepo;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;
	
	@Autowired
	HazelcastUtil hazelcastUtil;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Override
	public CommonResponse addManufacturer(List<ManufacturerMasterDataRequest> manufacturerDataRequests) {
		List<ManufacturerMaster> manufacturer = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Wrapping Manufacturer Data to save in DB:--> ");

		try {
			for (ManufacturerMasterDataRequest dataRequest : manufacturerDataRequests) {
				ManufacturerMaster manufactureData = new ManufacturerMaster();

				if (!StringUtils.isEmpty(dataRequest.getCode())) {
					Optional<ManufacturerMaster> manufactureRepo = manufacturerRepo.findById(dataRequest.getCode());

					if (manufactureRepo.isPresent()) {
						error.setErrorMessage(dataRequest.getCode() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}

				manufactureData.setCode(dataRequest.getCode());
				manufactureData.setIs_active(dataRequest.getIsActive());
				manufactureData.setName(dataRequest.getName());
				manufactureData.setCreated_by(dataRequest.getCreatedBy());
				manufactureData.setCreated_on(new Date(System.currentTimeMillis()));

				manufacturer.add(manufactureData);
				hazelcastUtil.manufacturerCache().put(dataRequest.getCode(), manufactureData);
			}

			manufacturerRepo.saveAll(manufacturer);
			LOG.info("Manufacturer Info Successfully Stored in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.error("Issue in storing data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateManufacturer(List<ManufacturerMasterDataRequest> manufacturerDataRequests) {
		List<ManufacturerMaster> manufacturer = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Manufacturer Info Data to update in DB:--> ");

		try {
			for (ManufacturerMasterDataRequest dataRequest : manufacturerDataRequests) {
				ManufacturerMaster manufactureData = new ManufacturerMaster();

				if (!StringUtils.isEmpty(dataRequest.getCode())) {
					Optional<ManufacturerMaster> manufactureRepo = manufacturerRepo.findById(dataRequest.getCode());

					if (!manufactureRepo.isPresent()) {
						error.setErrorMessage(dataRequest.getCode() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					manufactureData = manufactureRepo.get();
				}
				manufactureData.setCode(dataRequest.getCode());
				manufactureData.setIs_active(dataRequest.getIsActive());
				manufactureData.setName(dataRequest.getName());
				manufactureData.setUpdated_by(dataRequest.getUpdatedBy());
				manufactureData.setUpdated_on(new Date(System.currentTimeMillis()));

				manufacturer.add(manufactureData);
				hazelcastUtil.manufacturerCache().put(dataRequest.getCode(), manufactureData);
			}
			manufacturerRepo.saveAll(manufacturer);
			LOG.info("Manufacturer Info Successfully Updated in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		}catch (Exception e) {
			LOG.error("Issue in updating data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse deleteManufacturer(ManufacturerMasterDataRequest manufacturerDataRequests) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Manufacturer Info Data to delete from DB:--> ");

		try {

			if (!StringUtils.isEmpty(manufacturerDataRequests.getCode())) {
				Optional<ManufacturerMaster> manufactureRepo = manufacturerRepo
						.findById(manufacturerDataRequests.getCode());

				if (!manufactureRepo.isPresent()) {
					error.setErrorMessage(
							manufacturerDataRequests.getCode() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				manufacturerRepo.deleteById(manufacturerDataRequests.getCode());
				hazelcastUtil.manufacturerCache().get(manufacturerDataRequests.getCode());
				LOG.info("Manufacturer Info Successfully Deleted from DB.");

				response.setCode(200);
				response.setMessage(ExternalConstants.Message.DELETED);
			}

		} catch (Exception e) {
			LOG.error("Issue in deleting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getManufacturer(ManufacturerMasterDataRequest manufacturerDataRequests) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Manufacturer Info Data to get from DB:--> ");

		try {
			ManufacturerMaster manufacture = null;
			ManufacturerMasterDataResponse manRes = new ManufacturerMasterDataResponse();
			if (!StringUtils.isEmpty(manufacturerDataRequests.getCode())) {
				
				Object resCacheData = hazelcastUtil.resDataCache().get(manufacturerDataRequests.getCode());
				if(resCacheData != null && resCacheData instanceof ManufacturerMasterDataResponse) {
					
					response.setData(resCacheData);
					return response;
				}
				Object cacheData = hazelcastUtil.manufacturerCache().get(manufacturerDataRequests.getCode());
				if(cacheData != null && cacheData instanceof ManufacturerMaster) {
				 manufacture = (ManufacturerMaster) cacheData;
				}
				else {
					
				Optional<ManufacturerMaster> manData = manufacturerRepo.findById(manufacturerDataRequests.getCode());

				if (!manData.isPresent()) {
					error.setErrorMessage(
							manufacturerDataRequests.getCode() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				manufacture = manData.get();
				}
				manRes.setCode(manufacture.getCode());
				manRes.setIsActive(manufacture.getIs_active());
				manRes.setName(manufacture.getName());
			}

			response.setData(manRes);
			hazelcastUtil.resDataCache().put(manufacturerDataRequests.getCode(), manRes);
			LOG.info("Manufacturer Service Data Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getManufacturerNameList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Manufacturer Info Data to get list from DB:--> ");

		try {

			List<ManufacturerMaster> manufacturer = manufacturerRepo.findAll();
			List<ManufacturerMasterDataResponse> manufacturerList = new ArrayList<>();

			for (ManufacturerMaster manufacture : manufacturer) {
				ManufacturerMasterDataResponse manufafacturerRes = new ManufacturerMasterDataResponse();

				manufafacturerRes.setCode(manufacture.getCode());
				manufafacturerRes.setName(manufacture.getName());
				manufafacturerRes.setCreatedOn(manufacture.getCreated_on() != null
						? dateConverter.convertDateToHesString(manufacture.getCreated_on())
						: null);
				manufafacturerRes.setIsActive(manufacture.getIs_active());
				manufafacturerRes.setCreatedBy(manufacture.getCreated_by());
				manufafacturerRes.setUpdatedOn(manufacture.getUpdated_on() != null
						? dateConverter.convertDateToHesString(manufacture.getUpdated_on())
						: null);
				manufafacturerRes.setUpdatedBy(manufacture.getUpdated_by());

				manufacturerList.add(manufafacturerRes);
			}

			response.setData(manufacturerList);
			LOG.info("Manufacturer  Service Data List Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting manufacturer list from DB due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

}