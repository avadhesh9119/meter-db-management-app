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
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.data.repository.DtTransRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DtTransDataRequest;
import com.global.meter.inventive.models.DtTransDataResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.service.DtTransService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DtTransServiceImpl implements DtTransService {

	private static final Logger LOG = LoggerFactory.getLogger(DtTransServiceImpl.class);
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	DevicesInfoRepository devicesInfoRepository;

	@Autowired
	DtTransRepository dtRepository;

	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;
	
	@Override
	public CommonResponse addDtTrans(List<DtTransDataRequest> dtDataReqList) {

		List<DtTrans> dtTransList = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Wrapping DT Data to save in DB:--> ");

		try {

			for (DtTransDataRequest dtData : dtDataReqList) {
				DtTrans dtTrans = new DtTrans();
				
				if((dtData.getSource() == null || StringUtils.isEmpty(dtData.getSource())) || 
						(dtData.getUserId() == null || StringUtils.isEmpty(dtData.getUserId()))) {
					response.setMessage("Source & UserId can not be empty");
					response.setCode(404);
					response.setError(true);
					return response;
					}
				
				if (!StringUtils.isEmpty(dtData.getDtName())) {
					Optional<DtTrans> dtRepo = dtRepository.findById(dtData.getDtName());

					if (dtRepo.isPresent()) {
						error.setErrorMessage(dtData.getDtName() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}

				dtTrans.setDt_name(dtData.getDtName());
				dtTrans.setFeeder_name(dtData.getFeederName());
				dtTrans.setCreated(new Date(System.currentTimeMillis()));
				dtTrans.setLatitude(dtData.getLatitude());
				dtTrans.setLongitude(dtData.getLongitude());
				dtTrans.setModified(new Date(System.currentTimeMillis()));
				dtTrans.setOwner_name(dtData.getUtility());
				dtTrans.setSubdevision_name(dtData.getSubdivisionName());
				dtTrans.setSubstation_name(dtData.getSubstationName());
				dtTrans.setSource(dtData.getSource());
				dtTrans.setUser_id(dtData.getUserId());


				dtTransList.add(dtTrans);
			}
			dtRepository.saveAll(dtTransList);
			LOG.info("DT Successfully Stored in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.error("Issue in adding data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateDtTrans(List<DtTransDataRequest> dtDataReqList) {

		List<DtTrans> dtTransList = new ArrayList<>();
		List<DevicesInfo> devInfoList = new ArrayList<>();
		String table = meterConfiguration.getKeyspace() + "." +Tables.DEVICES_INFO;

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Wrapping DT Data to update in DB:--> ");

		try {

			for (DtTransDataRequest dtData : dtDataReqList) {

				DtTrans dtTrans = new DtTrans();
				
				if(dtData.getUpdatedBy() == null || StringUtils.isEmpty(dtData.getUpdatedBy())) {
					response.setMessage("Updated By can not be empty");
					response.setCode(404);
					response.setError(true);
					return response;
					}

				if (!StringUtils.isEmpty(dtData.getDtName())) {
					Optional<DtTrans> dtRepo = dtRepository.findById(dtData.getDtName());

					if (!dtRepo.isPresent()) {
						error.setErrorMessage(dtData.getDtName() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}

					dtTrans = dtRepo.get();
				}

				dtTrans.setDt_name(dtData.getDtName() != null ? dtData.getDtName() : dtTrans.getDt_name());
				dtTrans.setFeeder_name(
						dtData.getFeederName() != null ? dtData.getFeederName() : dtTrans.getFeeder_name());
				// dtTrans.setCreated(new Date(System.currentTimeMillis()));
				dtTrans.setLatitude(dtData.getLatitude() != null ? dtData.getLatitude() : dtTrans.getLatitude());
				dtTrans.setLongitude(dtData.getLongitude() != null ? dtData.getLongitude() : dtTrans.getLongitude());
				dtTrans.setModified(new Date(System.currentTimeMillis()));
				dtTrans.setOwner_name(dtData.getUtility() != null ? dtData.getUtility() : dtTrans.getOwner_name());
				dtTrans.setSubdevision_name(dtData.getSubdivisionName() != null ? dtData.getSubdivisionName()
						: dtTrans.getSubdevision_name());
				dtTrans.setSubstation_name(
						dtData.getSubstationName() != null ? dtData.getSubstationName() : dtTrans.getSubstation_name());
				dtTrans.setUpdated_by(
						dtData.getUpdatedBy() != null ? dtData.getUpdatedBy() : dtTrans.getUpdated_by());

				dtTransList.add(dtTrans);
				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT * FROM ").append(table).append(" where dt_name = '").append(dtData.getDtName())
				.append("'");
				String query = queryBuilder.substring(0, queryBuilder.length());
					
				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<DevicesInfo> devicesInfos = new ArrayList<>();
				devicesInfos = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<DevicesInfo>>() {});
				
				for(DevicesInfo devInfo1 : devicesInfos) {
					devInfo1.setSubdevision_name(dtData.getSubdivisionName()
						!= null ? dtData.getSubdivisionName() : devInfo1.getSubdevision_name());
					devInfo1.setSubstation_name(dtData.getSubstationName()
							!= null ? dtData.getSubstationName() : devInfo1.getSubstation_name());
					devInfo1.setFeeder_name(dtData.getFeederName()
							!= null ? dtData.getFeederName() : devInfo1.getFeeder_name());
				
				devInfoList.add(devInfo1);
			}
				}
			devicesInfoRepository.saveAll(devInfoList);
			dtRepository.saveAll(dtTransList);
			LOG.info("DT Successfully Updated in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in updating data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse deleteDtTrans(DtTransDataRequest dtDataReqList) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Wrapping DT Data to Delete From DB:--> ");

		try {
			if (!StringUtils.isEmpty(dtDataReqList.getDtName())) {
				Optional<DtTrans> devicesInfo = dtRepository.findById(dtDataReqList.getDtName());
				
				
				
				String table = MeterDBAppStarter.keyspace + "." + Tables.DEVICES_INFO;
				String query = "Select * from " + table + " Where dt_name = '" + dtDataReqList.getDtName() + "'";
				String queryString = CommonUtils.getMapper()
						.writeValueAsString(prestoJdbcTemplate.queryForList(query).size());
				
				Integer count = CommonUtils.getMapper().readValue(queryString,
						new TypeReference<Integer>() {
						});
				
				if(count != null && count > 0) {
					
					 error.setErrorMessage("This dt is associated in the network. Please de-associate it and then try again"); 
                     response.setError(true); 
                     response.addErrorMessage(error); 
							 
					 return response; 
							  
				}else {
					
					if (!devicesInfo.isPresent()) {
						error.setErrorMessage(dtDataReqList.getDtName() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}

					dtRepository.deleteById(dtDataReqList.getDtName());
					LOG.info("DT Successfully Deleted from DB.");

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
	public CommonResponse getDtTrans(DtTransDataRequest dtDataReq) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("DT Data to get From DB:--> ");
		

		try {
			DtTransDataResponse dtTransResponse = new DtTransDataResponse();
			if (!StringUtils.isEmpty(dtDataReq.getDtName())) {
				Optional<DtTrans> dtRepo = dtRepository.findById(dtDataReq.getDtName());

				if (!dtRepo.isPresent()) {
					error.setErrorMessage(dtDataReq.getDtName() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				DtTrans dtTrans = dtRepo.get();

				dtTransResponse.setDtName(dtTrans.getDt_name());
				dtTransResponse.setFeederName(dtTrans.getFeeder_name());
				dtTransResponse.setCreated(
						dtTrans.getCreated() != null ? dateConverter.convertDateToString(dtTrans.getCreated()) : null);
				dtTransResponse.setLatitude(dtTrans.getLatitude());
				dtTransResponse.setLongitude(dtTrans.getLongitude());
				dtTransResponse.setModified(
						dtTrans.getModified() != null ? dateConverter.convertDateToString(dtTrans.getModified())
								: null);
				dtTransResponse.setUtility(dtTrans.getOwner_name());
				dtTransResponse.setSubdivisionName(dtTrans.getSubdevision_name());
				dtTransResponse.setSubstationName(dtTrans.getSubstation_name());
				dtTransResponse.setSource(dtTrans.getSource());
				dtTransResponse.setCreatedBy(dtTrans.getUser_id());
				dtTransResponse.setLastUpdatedBy(dtTrans.getUpdated_by());

			}

			response.setData(dtTransResponse);
			LOG.info("DT Trans Service Data Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getDtTransList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Wrapping DT Data to get DT list From DB:--> ");

		try {

			List<DtTrans> dtRepoList = dtRepository.findAll();
			List<DtTransDataResponse> dtTransResList = new ArrayList<>();

			for (DtTrans dtTrans : dtRepoList) {
				DtTransDataResponse dtDataRes = new DtTransDataResponse();

				dtDataRes.setDtName(dtTrans.getDt_name());
				dtDataRes.setFeederName(dtTrans.getFeeder_name());
				dtDataRes.setCreated(
						dtTrans.getCreated() != null ? dateConverter.convertDateToString(dtTrans.getCreated()) : null);
				dtDataRes.setLatitude(dtTrans.getLatitude());
				dtDataRes.setLongitude(dtTrans.getLongitude());
				dtDataRes.setModified(
						dtTrans.getModified() != null ? dateConverter.convertDateToString(dtTrans.getModified())
								: null);
				dtDataRes.setUtility(dtTrans.getOwner_name());
				dtDataRes.setSubdivisionName(dtTrans.getSubdevision_name());
				dtDataRes.setSubstationName(dtTrans.getSubstation_name());
				dtDataRes.setSource(dtTrans.getSource());
				dtDataRes.setCreatedBy(dtTrans.getUser_id());
				dtDataRes.setLastUpdatedBy(dtTrans.getUpdated_by());

				dtTransResList.add(dtDataRes);
			}

			response.setData(dtTransResList);
			LOG.info("DT Trans Data List Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in getting DT list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getDtTransNameList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Get DT name list From DB:--> ");

		try {

			List<DtTrans> dtRepoList = dtRepository.findAll();

			LOG.info("Get DT Name List Data from DB");
			response.setData(dtRepoList.stream().map(dt -> dt.getDt_name()).collect(Collectors.toList()));

		} catch (Exception e) {
			LOG.error("Issue in DT name list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getDtListByFeeder(DtTransDataRequest dtDataReq) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Get DT List Data from DB By Feeder:--> " + dtDataReq.getFeederName());
		try {
			List<DtTransDataResponse> dtTransResList = new ArrayList<>();

			if (!StringUtils.isEmpty(dtDataReq.getFeederName())) {
				Optional<List<DtTrans>> dtRepo = dtRepository.findByFeederName(dtDataReq.getFeederName());

				if (dtRepo.get().isEmpty()) {
					error.setErrorMessage(dtDataReq.getFeederName() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				List<DtTrans> dtTransDataList = dtRepo.get();
				for (DtTrans dtTrans : dtTransDataList) {
					DtTransDataResponse dtDataRes = new DtTransDataResponse();
					dtDataRes.setDtName(dtTrans.getDt_name());
					dtDataRes.setFeederName(dtTrans.getFeeder_name());
					dtDataRes.setCreated(
							dtTrans.getCreated() != null ? dateConverter.convertDateToString(dtTrans.getCreated())
									: null);
					dtDataRes.setLatitude(dtTrans.getLatitude());
					dtDataRes.setLongitude(dtTrans.getLongitude());
					dtDataRes.setModified(
							dtTrans.getModified() != null ? dateConverter.convertDateToString(dtTrans.getModified())
									: null);
					dtDataRes.setUtility(dtTrans.getOwner_name());
					dtDataRes.setSubdivisionName(dtTrans.getSubdevision_name());
					dtDataRes.setSubstationName(dtTrans.getSubstation_name());
					dtDataRes.setSource(dtTrans.getSource());
					dtDataRes.setCreatedBy(dtTrans.getUser_id());
					dtDataRes.setLastUpdatedBy(dtTrans.getUpdated_by());

					dtTransResList.add(dtDataRes);

				}

			}

			response.setData(dtTransResList);
			LOG.info("DT Tran Data List by Feeder Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in DT list by feeder due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getMeterListByDt(DtTransDataRequest dtDataReq) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Get Meter Name List Data from DB By DT:--> " + dtDataReq.getDtName());
		try {
			
			if (!StringUtils.isEmpty(dtDataReq.getDtName())) {
			
				String table = MeterDBAppStarter.keyspace + "." + Tables.DEVICES_INFO;
				
				String query = "Select * from " + table + " Where dt_name = '" + dtDataReq.getDtName() + "'";

				String devicesInfoList = CommonUtils.getMapper()
						.writeValueAsString(prestoJdbcTemplate.queryForList(query));
				List<DevicesInfo> devicesList = CommonUtils.getMapper().readValue(devicesInfoList,
						new TypeReference<List<DevicesInfo>>() {
						});
				
				LOG.info("Get Meter Name List Data from DB");
				if(devicesList != null && devicesList.size() > 0) {
					response.setData(devicesList.stream().map(dt -> dt.getDevice_serial_number()).collect(Collectors.toList()));
				}else {
					error.setErrorMessage(dtDataReq.getDtName() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				LOG.info("DT Tran Data List by Feeder Response Success.");
				
			}

		} catch (Exception e) {
			LOG.error("Issue in DT list by feeder due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
		
	}
	
	@Override
	public CommonResponse getDtListByUtility(DtTransDataRequest dtDataReq) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Get DT List Data from DB By Utility:--> " + dtDataReq.getUtility());
		try {
			List<DtTransDataResponse> dtTransResList = new ArrayList<>();

			if (!StringUtils.isEmpty(dtDataReq.getUtility())) {
				Optional<List<DtTrans>> dtRepo = dtRepository.findByUtility(dtDataReq.getUtility());

				if (dtRepo.get().isEmpty()) {
					error.setErrorMessage(dtDataReq.getUtility() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				List<DtTrans> dtTransDataList = dtRepo.get();
				for (DtTrans dtTrans : dtTransDataList) {
					DtTransDataResponse dtDataRes = new DtTransDataResponse();
					dtDataRes.setDtName(dtTrans.getDt_name());
					dtDataRes.setFeederName(dtTrans.getFeeder_name());
					dtDataRes.setCreated(
							dtTrans.getCreated() != null ? dateConverter.convertDateToString(dtTrans.getCreated())
									: null);
					dtDataRes.setLatitude(dtTrans.getLatitude());
					dtDataRes.setLongitude(dtTrans.getLongitude());
					dtDataRes.setModified(
							dtTrans.getModified() != null ? dateConverter.convertDateToString(dtTrans.getModified())
									: null);
					dtDataRes.setUtility(dtTrans.getOwner_name());
					dtDataRes.setSubdivisionName(dtTrans.getSubdevision_name());
					dtDataRes.setSubstationName(dtTrans.getSubstation_name());
					dtDataRes.setSource(dtTrans.getSource());
					dtDataRes.setCreatedBy(dtTrans.getUser_id());
					dtDataRes.setLastUpdatedBy(dtTrans.getUpdated_by());

					dtTransResList.add(dtDataRes);

				}

			}

			response.setData(dtTransResList);
			LOG.info("DT Tran Data List by Utility Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in DT list by utility due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

}
