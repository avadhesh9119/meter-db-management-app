package com.global.meter.v3.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.inventive.dashboard.service.RetryAttemptService;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.business.model.DevicesCommandLog;
import com.global.meter.v3.inventive.business.model.SingleConnectionMeterCommandLog;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogResponse;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogsReq;
import com.global.meter.v3.inventive.repository.SingleConnectionMeterCommandLogRepository;

@Service
public class RetryAttemptServiceImpl implements RetryAttemptService {
	private static final Logger LOG = LoggerFactory.getLogger(RetryAttemptServiceImpl.class);

	@Autowired
	SingleConnectionMeterCommandLogRepository singleConnectionMeterCommandLogRepository;

	@Autowired
	SingleConnectionCommandLogsServiceImple singleConnectionCommandLogsServiceImple;

	@Autowired
	DevicesInfoService devicesInfoService;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	private CassandraOperations cassandraTemplate;

	@Override
	public CommonResponse getRetryAttempt(SingleConnectionCommandLogsReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		List<DevicesInfo> devInfo = null;
		String[] commands = req.getCommand().split(",");
		Set<String> commandSet = new HashSet<>();
		ArrayList<SingleConnectionMeterCommandLog> meterInfoList = new ArrayList<SingleConnectionMeterCommandLog>();
		ArrayList<SingleConnectionCommandLogResponse> responsesList = new ArrayList<SingleConnectionCommandLogResponse>();
		String device = "";
		String table = meterConfiguration.getKeyspace() + "." + Tables.SINGLE_CONNECTION_METER_COMMAND_LOGS;
		LOG.info("Getting data from DB :");
		String tableDevInfo = meterConfiguration.getKeyspace() + "." + Tables.devInfo;
		try {
			if (req.getHier().getName().equals(Constants.HIRE_NAME)) {
				devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
				if (devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
					response.setMessage("Device Not Found");
					response.setCode(404);
					return response;
				}
			}
			
			if(req.getAttemptFrom()== null && req.getAttemptTo()== null && req.getAttemptFrom().isEmpty() && req.getAttemptTo().isEmpty()) {
				response.setMessage("Retry range can not be empty");
				response.setCode(404);
				return response;
				
			}

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select device_serial_number from ").append(tableDevInfo).append(" where ");
			if (req.getDevMode() != null && !req.getDevMode().isEmpty()) {

				queryBuilder.append("dev_mode ='").append(req.getDevMode()).append("' and ");
			}
			if (req.getDevType() != null && !req.getDevType().isEmpty()) {

				queryBuilder.append("device_type ='").append(req.getDevType()).append("' and ");
			}
			if (req.getSimOperator() != null && !req.getSimOperator().isEmpty()) {

				queryBuilder.append("network ='").append(req.getSimOperator()).append("' and ");
			}
			if (req.getMeterType() != null && !req.getMeterType().isEmpty()) {

				queryBuilder.append("meter_type ='").append(req.getMeterType()).append("' and ");
			}
			queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			queryBuilder.replace(queryBuilder.length() - 1, queryBuilder.length(), ")");
			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			devInfo = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DevicesInfo>>() {
			});
			StringBuilder devBuilder = new StringBuilder();
			devBuilder.append("(");
			for (DevicesInfo data : devInfo) {

				devBuilder.append("'").append(data.getDevice_serial_number()).append("',");
			}
			devBuilder = devBuilder.replace(devBuilder.length() - 1, devBuilder.length(), ")");
			device = devBuilder.substring(0, devBuilder.length());
			
			StringBuilder cqlBuilder = new StringBuilder();
			cqlBuilder.append("select * from ").append(table).append(" where ").append("hes_date >= '")
					.append(req.getFromDate()).append("'").append(" and hes_date <= '").append(req.getToDate())
					.append("' and ").append("device_serial_number in ").append(device).append(" ALLOW FILTERING");

			String cqlQuery = cqlBuilder.substring(0, cqlBuilder.length());
			List<SingleConnectionMeterCommandLog> results = null;
			results = cassandraTemplate.select(cqlQuery, SingleConnectionMeterCommandLog.class);

			meterInfoList = (ArrayList<SingleConnectionMeterCommandLog>) results;
			for (String str : commands) {
				commandSet.add(str.trim());
			}
			for (SingleConnectionMeterCommandLog isData : meterInfoList) {

				for (DevicesCommandLog commandLog : isData.getCommand_list()) {
					if (Integer.valueOf(req.getAttemptFrom()) <= commandLog.getRetry()
							&& Integer.valueOf(req.getAttemptTo()) >= commandLog.getRetry())
						if (req.getCommand() != null && !req.getCommand().isEmpty()) {
							if (commandSet.contains(commandLog.getCommand_name())) {
								responsesList.add(singleConnectionCommandLogsServiceImple
										.setCommandRetryResponse(isData, commandLog, req));
							}
						} else {
							responsesList.add(singleConnectionCommandLogsServiceImple.setCommandRetryResponse(isData,
									commandLog, req));
						}
				}
			}
			

			response.setData(responsesList);
			LOG.info("Retry attempts data response success.");
		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}
}
