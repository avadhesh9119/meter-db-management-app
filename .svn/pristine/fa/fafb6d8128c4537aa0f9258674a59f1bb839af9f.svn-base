package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.global.meter.data.repository.DevicesCommandRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DeviceCommandResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.DeviceCommandService;
import com.global.meter.inventive.utils.DevicesCommandCaster;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {

	private static final Logger LOG = LoggerFactory.getLogger(SubstationsServiceImpl.class);

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	DevicesCommandRepository devCommandRepo;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	DevicesCommandCaster deviceCmdCaster;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Override
	public CommonResponse getDeviceCommand(MeterDataVisualizationReq meterDataVisualizationReq) {
		CommonResponse response = new CommonResponse();
		LOG.info("Devices Command Data to get from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS;

			String fieldName = ExternalConstants.getLevelValue(meterDataVisualizationReq.getHier().getName());
			String[] levels = meterDataVisualizationReq.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" mdas_datetime >= cast('")
					.append(meterDataVisualizationReq.getFromDate()).append("' as timestamp) ")
					.append(" and mdas_datetime <= cast('").append(meterDataVisualizationReq.getToDate())
					.append("' as timestamp) and ")
					.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}

			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			if(Constants.ALL.equalsIgnoreCase(meterDataVisualizationReq.getCommand())) {
			query = query.concat(") order by mdas_datetime desc");
			}else {
			query = query.concat(") and command_name like '%").concat(meterDataVisualizationReq.getCommand()).concat("%' order by mdas_datetime desc");
			}
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

	@Override
	public CommonResponse getDeviceCommandList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Devices Command Data List to get from DB:--> ");
		try {

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace()).append(".")
					.append(Tables.DEVICES_COMMANDS);

			String query = queryBuilder.substring(0, queryBuilder.length());
			query = query.concat(" order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DeviceCommandResponse> devicesCommandList = new ArrayList<>();

			deviceCmdCaster.prepareDeviceCommand(outputList, devicesCommandList);
			response.setData(devicesCommandList);
			LOG.info("Devices Command Data List Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}

		return response;
	}
	
	
	@Override
	public CommonResponse getDeviceCommandByTraceId(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Devices Command Data to get from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS;

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where tracking_id = '").append(req.getTraceId());
					
			String query = queryBuilder.substring(0, queryBuilder.length());
			query = query.concat("' order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DeviceCommandResponse> ispResponseList = new ArrayList<>();
			deviceCmdCaster.prepareDeviceCommand(outputList, ispResponseList);
			response.setData(ispResponseList);
			
			LOG.info("Devices Command Service Data By Hier Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}


}
