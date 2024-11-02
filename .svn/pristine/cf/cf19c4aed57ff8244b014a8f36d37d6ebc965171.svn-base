package com.global.meter.inventive.service;

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
import com.global.meter.business.model.DeviceBatchLogs;
import com.global.meter.data.repository.DeviceBatchLogsRepository;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DeviceBatchLogsDataRequest;
import com.global.meter.inventive.models.DeviceBatchLogsDataResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DeviceBatchLogsServiceImpl implements DeviceBatchLogsService{

	private static final Logger LOG = LoggerFactory.getLogger(DeviceBatchLogsServiceImpl.class);
	
	@Autowired
	DeviceBatchLogsRepository deviceBatchLogsRepository;
	
	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Override
	public CommonResponse createLogs(DeviceBatchLogsDataRequest meters) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		try {

			DeviceBatchLogs batchLogs = new DeviceBatchLogs();
			
			if(!StringUtils.isEmpty(meters.getBatchId())) {
				Optional<DeviceBatchLogs> deviceBatchLogs = deviceBatchLogsRepository.findById(meters.getBatchId());
				
				if(deviceBatchLogs.isPresent()) {
					error.setErrorMessage(meters.getBatchId() + " : " +ExternalConstants.Message.BATCH_ID_EXISTS);
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
			}
			
			if(ExternalConstants.BulkType.DEVICE.equalsIgnoreCase(meters.getBulkType()))
				batchLogs.setBulk_type(ExternalConstants.BulkType.DEVICE);
			else if(ExternalConstants.BulkType.DIVISION.equalsIgnoreCase(meters.getBulkType()))
				batchLogs.setBulk_type(ExternalConstants.BulkType.DIVISION);
			else {
				error.setErrorMessage(ExternalConstants.Message.INVALID_BULK_TYPE+" : "+meters.getBulkType());
				response.setCode(400);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}
			batchLogs.setBatch_id(meters.getBatchId());
			batchLogs.setCompletion_datetime(meters.getStatus().equalsIgnoreCase("Completed") ?
					new Date(System.currentTimeMillis()) : null);
			batchLogs.setFailure_records(meters.getFailureRecords());
			batchLogs.setFile_name(meters.getFileName());
			batchLogs.setMdas_datetime(new Date(System.currentTimeMillis()));
			batchLogs.setStatus(meters.getStatus());
			batchLogs.setSuccess_records(meters.getSuccessRecords());
			batchLogs.setTot_records(meters.getTotRecords());
			batchLogs.setUser_name(meters.getUserName());
			
			deviceBatchLogsRepository.save(batchLogs);
			LOG.info("Batch Logs Successfully Stored in DB.");
			
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.BATCH_ADDED);
		} catch (Exception e) {
			LOG.error("Issue in adding Batch Logs due to : " + e.getMessage());
			error.setErrorMessage(e.getMessage());
			response.setCode(500);
			response.setError(true);
			response.addErrorMessage(error);
		}
		return response;
	}

	
	@Override
	public CommonResponse getBatchById(DeviceBatchLogsDataRequest meters) {
		
		CommonResponse response = new CommonResponse();
		DeviceBatchLogsDataResponse batchData=new DeviceBatchLogsDataResponse();
		ErrorData error = new ErrorData();
		
		try {
			if(meters.getBatchId() == null || meters.getBatchId().isEmpty()) {
				error.setErrorMessage(ExternalConstants.Message.INVALID_BATCH_ID +" : "+meters.getBatchId());
				response.setCode(400);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}

			if(!StringUtils.isEmpty(meters.getBatchId())) {
				Optional<DeviceBatchLogs> deviceBatchLogs = deviceBatchLogsRepository.findById(meters.getBatchId());
				
				if(!deviceBatchLogs.isPresent()) {
					error.setErrorMessage(meters.getBatchId() + " : " +ExternalConstants.Message.NOT_EXISTS);
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				
				DeviceBatchLogs batchInfo = deviceBatchLogs.get();
		    
				batchData.setBatchId(batchInfo.getBatch_id());
				batchData.setFileName(batchInfo.getFile_name());
				batchData.setFailureRecords(batchInfo.getFailure_records().toString());
				batchData.setStatus(batchInfo.getStatus());
				batchData.setSuccessRecords(String.valueOf(batchInfo.getSuccess_records()));
				batchData.setTotRecords(batchInfo.getTot_records().toString());
				batchData.setUserName(batchInfo.getUser_name());
				batchData.setCompletionTimestamp(batchInfo.getCompletion_datetime() !=null ? dateConverter.convertDateToString(batchInfo.getCompletion_datetime()): "-");
				batchData.setHesTimestamp(dateConverter.convertDateToString(batchInfo.getMdas_datetime()));
				response.setData(batchData);
			
				LOG.info("Data add in DeviceBatchLogsDataResponse Success");
			}
			
		} catch (Exception e) {
			LOG.error("Issue in fetch Batch Logs due to : " + e.getMessage());
			error.setErrorMessage(e.getMessage());
			response.setCode(500);
			response.setError(true);
			response.addErrorMessage(error);
		}
		return response;
	}

	@Override
	public CommonResponse getBatchByUsername(DeviceBatchLogsDataRequest meters) {
		CommonResponse response = new CommonResponse();

		
		ErrorData error = new ErrorData();
		LOG.info("Getting batch list from DB by userName");
		
		try {
			if(meters.getUserName() == null || meters.getUserName().isEmpty()) {
				error.setErrorMessage(ExternalConstants.Message.INVALID_USER +" : "+meters.getUserName());
				response.setCode(400);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}
			if(meters.getBulkType() == null || meters.getBulkType().isEmpty()) {
				error.setErrorMessage(ExternalConstants.Message.INVALID_BULK_TYPE+" : "+meters.getBulkType());
				response.setCode(400);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace() + "." +Tables.DEVICES_BATCH_LOGS)
						.append(" where ").append(" user_name = '").append(meters.getUserName())
						.append("' and bulk_type = '").append(meters.getBulkType()).append("'");

			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DeviceBatchLogs> batchLogs = new ArrayList<DeviceBatchLogs>();
			batchLogs = CommonUtils.getMapper().readValue(outputList,
					new TypeReference<List<DeviceBatchLogs>>() {});
			
			List<DeviceBatchLogsDataResponse> batchList = new ArrayList<>();
			
			for (DeviceBatchLogs batchInfo : batchLogs) {
				
				DeviceBatchLogsDataResponse batchData = new DeviceBatchLogsDataResponse();

				batchData.setBatchId(batchInfo.getBatch_id());
				batchData.setFileName(batchInfo.getFile_name());
				batchData.setFailureRecords(batchInfo.getFailure_records().toString());
				batchData.setStatus(batchInfo.getStatus());
				batchData.setSuccessRecords(String.valueOf(batchInfo.getSuccess_records()));
				batchData.setTotRecords(batchInfo.getTot_records().toString());
				batchData.setUserName(batchInfo.getUser_name());
				batchData.setCompletionTimestamp(batchInfo.getCompletion_datetime() !=null ? dateConverter.convertDateToString(batchInfo.getCompletion_datetime()): "-");
				batchData.setHesTimestamp(dateConverter.convertDateToString(batchInfo.getMdas_datetime()));
				batchList.add(batchData);
			}
			
			response.setData(batchList);
			LOG.info("Devices Batch Data List Response Success.");
		
		} catch (Exception e) {
			LOG.error("Issue in getting batch list due to : " + e.getMessage());
			error.setErrorMessage(e.getMessage());
			response.setCode(500);
			response.setError(true);
			response.addErrorMessage(error);
		}
		return response;
	}

	@Override
	public CommonResponse updateLogs(DeviceBatchLogsDataRequest meters) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		try {
			if(meters.getBatchId() == null || meters.getBatchId().isEmpty()) {
				error.setErrorMessage(ExternalConstants.Message.INVALID_BATCH_ID +" : "+meters.getBatchId());
				response.setCode(400);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}
			if(meters.getBulkType() == null || meters.getBulkType().isEmpty()) {
				error.setErrorMessage(ExternalConstants.Message.INVALID_BULK_TYPE+" : "+meters.getBulkType());
				response.setCode(400);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}
			DeviceBatchLogs batchLogs = new DeviceBatchLogs();

			if (!StringUtils.isEmpty(meters.getBatchId())) {
				Optional<DeviceBatchLogs> deviceBatchLogs = deviceBatchLogsRepository.findById(meters.getBatchId());

				if (!deviceBatchLogs.isPresent()) {
					error.setErrorMessage(meters.getBatchId() + " : " + ExternalConstants.Message.BATCH_ID_NOT_EXISTS);
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}else {
					
					DeviceBatchLogs deviceBatch = deviceBatchLogs.get();
					
					if(!("Completed").equalsIgnoreCase(deviceBatch.getStatus())) {
						
					batchLogs.setBatch_id(deviceBatch.getBatch_id());
					batchLogs.setBulk_type(meters.getBulkType());
					batchLogs.setCompletion_datetime(meters.getStatus().equalsIgnoreCase("Completed") ?
							new Date(System.currentTimeMillis()) : null);
					batchLogs.setFailure_records(meters.getFailureRecords());
					batchLogs.setFile_name(deviceBatch.getFile_name());
					batchLogs.setMdas_datetime(new Date(System.currentTimeMillis()));
					batchLogs.setStatus(meters.getStatus());
					batchLogs.setSuccess_records(meters.getSuccessRecords());
					batchLogs.setTot_records(deviceBatch.getTot_records());
					batchLogs.setUser_name(deviceBatch.getUser_name());
					deviceBatchLogsRepository.save(batchLogs);
					LOG.info("Batch Logs Successfully Updated in DB.");
					response.setCode(200);
					response.setMessage(ExternalConstants.Message.BATCH_UPDATED);
					}
					else {
					LOG.error(meters.getBatchId() + " : is Already Updated.");
					error.setErrorMessage(meters.getBatchId() + " : is Already Updated.");
					response.setCode(409);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				}
			}

		} catch (Exception e) {
			LOG.error("Issue in updating Batch Logs due to : " + e.getMessage());
			error.setErrorMessage(e.getMessage());
			response.setCode(500);
			response.setError(true);
			response.addErrorMessage(error);
		}
		return response;

	}
}
