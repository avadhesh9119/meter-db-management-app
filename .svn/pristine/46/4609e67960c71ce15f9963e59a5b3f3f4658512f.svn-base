package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DeviceCommandLogsResponse;
import com.global.meter.inventive.models.DeviceCommandResponse;
import com.global.meter.inventive.models.DevicesCommandRequest;
import com.global.meter.inventive.models.DevicesCommandsBatchLogsResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.DeviceBatchLogsServiceImpl;
import com.global.meter.inventive.service.DevicesCommandsBatchLogsService;
import com.global.meter.inventive.utils.DevicesCommandCaster;
import com.global.meter.inventive.utils.DevicesCommandLogsCaster;
import com.global.meter.inventive.utils.DevicesCommandsBatchLogsCaster;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DeviceCommandBatchLogsServiceImpl implements DevicesCommandsBatchLogsService {

	private static final Logger LOG = LoggerFactory.getLogger(DeviceBatchLogsServiceImpl.class);

	@Autowired
	DevicesCommandsBatchLogsCaster batchCaster;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	DevicesCommandsBatchLogsService devicesCommandsBatchLogsService;

	@Autowired
	DevicesCommandCaster deviceCmdCaster;

	@Autowired
	DevicesCommandLogsCaster deviceCmdLogsCaster;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	CommonUtils commonUtils;

	@Override
	public void saveDevicesCommandsBatchLogs() {

		LOG.info("Getting DevicesCommands Data from DB:-->");
		try {
            String table =  meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS;

			processBatchLogs(table);
			
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
		}

	}
	@Override
	public void saveDevicesCommandsLogBatchLogs() {

		LOG.info("Getting DevicesCommandsLogs Data from DB:-->");
		try {
			
			String table =  meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CMD_LOGS;

			processBatchLogs(table);

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
		}

	}

	private void processBatchLogs(String table) throws Exception {


		StringBuilder queryBuilder = new StringBuilder();

		queryBuilder.append("SELECT batch_id, status, mdas_datetime, owner_name as owner, subdevision_name AS subdevision, substation_name AS substation, feeder_name AS feeder, dt_name AS dt, device_serial_number as meterNo, source, user_id,");
		
		if(table.contains(Tables.DEVICES_CMD_LOGS)) {
			queryBuilder.append(" command_name from ");
		}
		else {
			queryBuilder.append(" command_name from ");
		}

	   queryBuilder.append(table)
				.append(" where batch_id IS NOT NULL AND batch_id != '' and ").append(" mdas_datetime >= cast('")
				.append(new DateConverter().convertDateToString(commonUtils.getPrevDate())).append("' as timestamp) ")
				.append(" order by batch_id, status");

		String query = queryBuilder.substring(0, queryBuilder.length());
		String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

		batchCaster.saveDevicesCommandBatchLogs(outputList);
	}
	
	@Override
	public CommonResponse getDevicesCommandsBatchLogs(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();

		LOG.info("Getting DevicesCommandsLogs Data from DB:-->");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CMD_BATCH_LOGS;

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

			List<DevicesCommandsBatchLogsResponse> ispResponseList = new ArrayList<>();
			batchCaster.prepareDevicesCommandBatchLogs(outputList, ispResponseList);
			response.setData(ispResponseList);

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getDevicesCommandsBatchDrillDown(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();

		LOG.info("Getting DevicesCommandsLogs Data from DB:-->");
		try {

			String table = meterConfiguration.getKeyspace() + ".";
			if (ConfigCommands.FULL_DATA_READ.commandName.equalsIgnoreCase(req.getCommand())) {
				table = table + Tables.DEVICES_CMD_LOGS;
			}
			else {
				table = table + Tables.DEVICES_COMMANDS;
			}

			StringBuilder queryBuilder = new StringBuilder();

			queryBuilder.append("SELECT * from ").append(table).append(" where")
	    	.append(" batch_id = '").append(req.getBatchId()).append("' order by mdas_datetime desc");

			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if (ConfigCommands.FULL_DATA_READ.commandName.equalsIgnoreCase(req.getCommand())) {
				List<DeviceCommandLogsResponse> ispResponseList = new ArrayList<>();
				deviceCmdLogsCaster.prepareDevicesCommandLogs(outputList, ispResponseList,req);
				response.setData(ispResponseList);
			}
			else {
				List<DeviceCommandResponse> ispResponseList = new ArrayList<>();
				deviceCmdCaster.prepareDeviceCommand(outputList, ispResponseList, req);
				response.setData(ispResponseList);
			}

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	
	@Override
	public CommonResponse getCommandLogByBatchId(DevicesCommandRequest req) {
		CommonResponse response = new CommonResponse();
		
		try {
			String devCmd = meterConfiguration.getKeyspace() + "." +Tables.DEVICES_COMMANDS;
			String devCmdLogs = meterConfiguration.getKeyspace() + "." +Tables.DEVICES_CMD_LOGS;
			String devConfigLogs = meterConfiguration.getKeyspace() + "." +Tables.DEVICES_CONFIG_LOGS;
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ");
			if(ConfigCommands.FULL_DATA_READ.commandName.equalsIgnoreCase(req.getCommandName())) {
				queryBuilder.append(devCmdLogs);
			}
			else if(ConfigCommands.FULL_CONFIG_READ.commandName.equalsIgnoreCase(req.getCommandName())){
				queryBuilder.append(devConfigLogs);
			}
			else {
				queryBuilder.append(devCmd);
			}
				
			queryBuilder.append(" where batch_id = '").append(req.getBatchId()).append("'");
			
			
			String query = queryBuilder.substring(0, queryBuilder.length());
			
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
			List<DeviceCommandResponse> ispResponseList = new ArrayList<>();
			deviceCmdCaster.prepareDeviceCommand(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("Devices Command Log Response Success.");
		
		} catch (Exception e) {
			LOG.error("Issue in getting Devices Commands Logs due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
}
