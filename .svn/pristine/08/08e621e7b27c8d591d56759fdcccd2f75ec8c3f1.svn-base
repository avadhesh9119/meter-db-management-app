package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.global.meter.data.repository.DeviceCommandLogsRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DeviceCommandLogsResponse;
import com.global.meter.inventive.models.DeviceCommandResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.DeviceCommandLogsService;
import com.global.meter.inventive.utils.DevicesCommandCaster;
import com.global.meter.inventive.utils.DevicesCommandLogsCaster;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DeviceCommandLogsImpl implements DeviceCommandLogsService {

	private static final Logger LOG = LoggerFactory.getLogger(DeviceCommandLogsImpl.class);

	@Autowired
	DeviceCommandLogsRepository devicesCmdInfoRepository;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	DevicesCommandLogsCaster deviceCmdLogsCaster;
	
	@Autowired
	DevicesCommandCaster deviceCmdCaster;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Override
	public CommonResponse getDeviceCommandLogByHier(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Devices Command Log Data By Hier Service Called to get from DB:--> "+req.toString());

		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CMD_LOGS;

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			if(Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				queryBuilder.append("select * from ");
			}else {
				queryBuilder.append("select *, concat(cast('{\"").append(req.getCommand())
				.append("\" : \"' as varchar) , concat(cast(json_extract(command_name,'$.").append(req.getCommand())
				.append("') as varchar), '\"}')) mod_command_name from ");
					
			}
			queryBuilder.append(table).append(" where ").append(" mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			if(Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") order by mdas_datetime desc");
			}else {
			query = query.concat(") and command_name like '%").concat(req.getCommand()).concat("%' order by mdas_datetime desc");
			}
			

			List<Map<String, Object>> out = prestoJdbcTemplate.queryForList(query);

			String outputList = CommonUtils.getMapper().writeValueAsString(out);
			// outputList = outputList.replaceAll("\\\\","");

			List<DeviceCommandLogsResponse> ispResponseList = new ArrayList<>();
			deviceCmdLogsCaster.prepareDevicesCommandLogs(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("Devices Command Log Service Response Success");
	
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}

		return response;
	}

	@Override
	public CommonResponse getDeviceCommandLogList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Devices Command Log Data List to get from DB.");

		try {

			LOG.info("Get All Device Command Logs Data from DB");
			StringBuilder queryBuilder = new StringBuilder();

			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace()).append(".")
					.append(Tables.DEVICES_CMD_LOGS);

			String query = queryBuilder.substring(0, queryBuilder.length());
			query = query.concat(" order by mdas_datetime desc");
			String configCommandList = CommonUtils.getMapper().writerWithDefaultPrettyPrinter()
					.writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DeviceCommandLogsResponse> deviceCommandLogResponse = new ArrayList<>();

			deviceCmdLogsCaster.prepareDevicesCommandLogs(configCommandList, deviceCommandLogResponse);
			response.setData(deviceCommandLogResponse);
			LOG.info("Devices Command Log Data List Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}

		return response;
	}

	@Override
	public CommonResponse getDeviceCommandLogByBatch(MeterDataVisualizationReq meterDataVisualizationReq) {
		CommonResponse response = new CommonResponse();
		LOG.info("Devices Command Data to get from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS;

			String fieldName = ExternalConstants.getLevelValue(meterDataVisualizationReq.getHier().getName());
			String[] levels = meterDataVisualizationReq.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select DISTINCT batch_id from ").append(table).append(" where ").append(" mdas_datetime >= cast('")
					.append(meterDataVisualizationReq.getFromDate()).append("' as timestamp) ")
					.append(" and mdas_datetime <= cast('").append(meterDataVisualizationReq.getToDate())
					.append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("'),");
			}

			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
//			query = query.concat(") and command_name like '%").concat(meterDataVisualizationReq.getCommand()).concat("%' order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DeviceCommandResponse> ispResponseList = new ArrayList<>();
			deviceCmdCaster.prepareDeviceCommand(outputList, ispResponseList, meterDataVisualizationReq);
			response.setData(ispResponseList);
			
			LOG.info("Devices Command Service Data By Hier Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

}
