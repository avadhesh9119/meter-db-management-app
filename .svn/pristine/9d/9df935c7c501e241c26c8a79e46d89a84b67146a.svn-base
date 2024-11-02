package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ConfigurationReadDataLogsResponse;
import com.global.meter.inventive.models.ConfigurationReadDataResponse;
import com.global.meter.inventive.models.DeviceConfigLogsResponse;
import com.global.meter.inventive.models.DevicesConfigBatchLogsResponse;
import com.global.meter.inventive.models.DevicesConfigReadBatchLogsResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.DeviceBatchLogsServiceImpl;
import com.global.meter.inventive.service.DevicesConfigBatchLogsService;
import com.global.meter.inventive.utils.ConfigurationReadDataCaster;
import com.global.meter.inventive.utils.ConfigurationReadDataLogsCaster;
import com.global.meter.inventive.utils.DeviceConfigLogsCaster;
import com.global.meter.inventive.utils.DevicesConfigBatchLogsCaster;
import com.global.meter.inventive.utils.DevicesConfigCaster;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DeviceConfigBatchLogsServiceImpl implements DevicesConfigBatchLogsService {

	private static final Logger LOG = LoggerFactory.getLogger(DeviceBatchLogsServiceImpl.class);

	@Autowired
	DevicesConfigBatchLogsCaster batchCaster;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	DevicesConfigBatchLogsService devicesConfigBatchLogsService;
	
	@Autowired
	ConfigurationReadDataCaster configurationReadDataCaster;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	CommonUtils commonUtils;
	
	@Autowired
	DevicesConfigCaster deviceConfigCaster;
	
	@Autowired
	DeviceConfigLogsCaster deviceConfigLogsCaster;
	
	@Autowired
	ConfigurationReadDataLogsCaster configurationReadDataLogsCaster;

	@Override
	public void saveDevicesConfigBatchLogs() {

		LOG.info("Getting DevicesConfigLogs Data from DB.");
		try {
			
			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CONFIG_LOGS;

			saveBatchLogsByTable(table);

		} catch (Exception e) {
			LOG.error("Issue in fetching devicesConfigLogs data due to : " + e.getMessage());
		}
	}

	private void saveBatchLogsByTable(String table) throws Exception {

		StringBuilder queryBuilder = new StringBuilder();

		queryBuilder.append("SELECT batch_id, status, mdas_datetime, owner_name as owner, subdevision_name AS subdevision, substation_name AS substation, feeder_name AS feeder, dt_name AS dt, device_serial_number as meterNo, command_name, source, user_id from ")
		.append(table)
		.append(" where batch_id IS NOT NULL AND batch_id != '' and ").append(" mdas_datetime >= cast('")
		.append(new DateConverter().convertDateToString(commonUtils.getPrevDate())).append("' as timestamp) ")
		.append(" order by batch_id, status");
		
		String query = queryBuilder.substring(0, queryBuilder.length());
		String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

		batchCaster.saveDevicesConfigBatchLogs(outputList);
	}
	
	
	@Override
	public CommonResponse getDevicesConfigBatchLogs(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();

		LOG.info("Getting DevicesConfigLogs Data from DB:-->");
		try {
			
			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CONFIG_BATCH_LOGS;

			StringBuilder queryBuilder = new StringBuilder();

			queryBuilder.append("SELECT * from ").append(table).append(" where ")
			.append(" mdas_datetime >= cast('")
			.append(req.getFromDate()).append("' as timestamp) ").append("and mdas_datetime <= cast('")
			.append(req.getToDate()).append("' as timestamp)")
			.append(" and hier_name = '")
			.append(req.getHier().getName()).append("' and hier_value = '")
			.append(req.getHier().getValue()).append("' order by mdas_datetime desc");

			String query = queryBuilder.substring(0, queryBuilder.length());
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DevicesConfigBatchLogsResponse> ispResponseList = new ArrayList<>();
			batchCaster.prepareDevicesConfigBatchLogs(outputList, ispResponseList);
			response.setData(ispResponseList);

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getDevicesConfigBatchDrillDown(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();

		LOG.info("Getting DevicesConfigLogs Data from DB:-->");
		try {
			
			String	table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CONFIG_LOGS;

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("SELECT * from ").append(table).append(" where ")
     		.append(" batch_id = '").append(req.getBatchId()).append("' order by mdas_datetime desc");
			
			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<DeviceConfigLogsResponse> ispResponseList = new ArrayList<>();
				deviceConfigLogsCaster.prepareDeviceConfigLogs(outputList, ispResponseList, req);
				response.setData(ispResponseList);

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}
	@Override
	public void saveDevicesConfigReadBatchLogs() {

		LOG.info("Getting DevicesConfigReadBatchLogs Data from DB.");
		try {
			
			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS_PREPAY_LOGS;
			saveBatchReadLogsByTable(table);

		} catch (Exception e) {
			LOG.error("Issue in fetching devicesConfigReadBatchLogs data due to : " + e.getMessage());
		}
	}

	private void saveBatchReadLogsByTable(String table) throws Exception {

		StringBuilder queryBuilder = new StringBuilder();

		queryBuilder.append("SELECT batch_id, status, mdas_datetime, owner_name as owner, subdevision_name AS subdevision, substation_name AS substation, feeder_name AS feeder, dt_name AS dt, device_serial_number as meterNo, source, user_id, command_name from ")
		.append(table)
		.append(" where batch_id IS NOT NULL AND batch_id != '' and ").append(" mdas_datetime >= cast('")
		.append(new DateConverter().convertDateToString(commonUtils.getPrevDate())).append("' as timestamp) ")
		.append(" order by batch_id");

		String query = queryBuilder.substring(0, queryBuilder.length());
		String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

		batchCaster.saveDevicesConfigReadBatchLogs(outputList);
	}

	@Override
	public CommonResponse getDevicesConfigReadBatchData(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();

		LOG.info("Getting DevicesConfigLogs Data from DB:-->");
		try {
			
			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CONFIG_READ_BATCH_LOGS;

			StringBuilder queryBuilder = new StringBuilder();

			queryBuilder.append("SELECT * from ").append(table).append(" where ")
			.append(" mdas_datetime >= cast('")
			.append(req.getFromDate()).append("' as timestamp) ").append("and mdas_datetime <= cast('")
			.append(req.getToDate()).append("' as timestamp)")
			.append(" and hier_name = '")
			.append(req.getHier().getName()).append("' and hier_value = '")
			.append(req.getHier().getValue()).append("' order by mdas_datetime desc");

			String query = queryBuilder.substring(0, queryBuilder.length());
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DevicesConfigReadBatchLogsResponse> ispResponseList = new ArrayList<>();
			batchCaster.prepareDevicesConfigReadBatchLogs(outputList, ispResponseList);
			response.setData(ispResponseList);

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getDevicesConfigReadBatchDrillDown(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();

		LOG.info("Getting DevicesConfigReadLogs DrillDown Data from DB:-->");
		try {

			String	prepayLogTable = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS_PREPAY_LOGS;
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("SELECT * from ").append(prepayLogTable).append(" where ")
     		.append(" batch_id = '").append(req.getBatchId())
     		.append("'");
			
			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			
			if (outputList != null && !outputList.isEmpty() && outputList.length() >2) {
				
				List<ConfigurationReadDataLogsResponse> ispResponseList = new ArrayList<>();
				configurationReadDataLogsCaster.prepareConfigurationReadDataLogs(outputList, ispResponseList, req);
				response.setData(ispResponseList);
			}else {
				
				String	prepayDataTable = meterConfiguration.getKeyspace() + "." + Tables.PREPAY_DATA;
				
				queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT * from ").append(prepayDataTable).append(" where ")
	     		.append(" batch_id = '").append(req.getBatchId()).append("'");
				
				
				String prePayQuery = queryBuilder.substring(0, queryBuilder.length());
				outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(prePayQuery));

				List<ConfigurationReadDataResponse> ispResponseList = new ArrayList<>();
				configurationReadDataCaster.prepareConfigurationReadData(outputList, ispResponseList,req);
				response.setData(ispResponseList);
			}
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
}
