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
import com.global.meter.inventive.models.DevicesConfigResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.DevicesConfigService;
import com.global.meter.inventive.utils.DevicesConfigCaster;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DevicesConfigServiceImpl implements DevicesConfigService {

	private static final Logger LOG = LoggerFactory.getLogger(SubstationsServiceImpl.class);

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	DevicesConfigCaster deviceConfigCaster;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Override
	public CommonResponse getDevicesConfig(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Devices Config Data to get from DB:--> ");

		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CONFIG;

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
					.append(req.getToDate()).append("' as timestamp) and ")
					.append("source = '").append(req.getSource()).append("' and user_id = '").append(req.getUserId())
					.append("' and ")
					.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			if(Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") order by mdas_datetime desc");
			}else {
			query = query.concat(") and command_name like '%").concat(req.getCommand()).concat("%' order by mdas_datetime desc");
			}

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DevicesConfigResponse> ispResponseList = new ArrayList<>();
			deviceConfigCaster.prepareDevicesConfig(outputList, ispResponseList);
			response.setData(ispResponseList);
			LOG.info("Devices Config Data Service By Hier Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getDevicesConfigList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Devices Config Data List to get from DB:--> ");

		try {

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace()).append(".")
					.append(Tables.DEVICES_CONFIG);

			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
			query = query.concat(" order by mdas_datetime desc");

			List<DevicesConfigResponse> devicesCommandList = new ArrayList<>();

			deviceConfigCaster.prepareDevicesConfig(outputList, devicesCommandList);
			response.setData(devicesCommandList);
			LOG.info("Devices Config Data List Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}

		return response;
	}
}
