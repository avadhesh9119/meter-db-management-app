package com.global.meter.v3.inventive.serviceImpl;

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
import com.global.meter.inventive.models.PropertyRequest;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.models.AppPropertiesDataResponse;
import com.global.meter.v3.inventive.repository.AppPropertyFileLogsRepository;
import com.global.meter.v3.inventive.utils.AppPropertiesFileLogsCaster;

@Service
public class AppPropertiesFileLogsService {
	private static final Logger LOG = LoggerFactory.getLogger(AppPropertiesFileLogsService.class);

	@Autowired
	AppPropertyFileLogsRepository appPropertyFileLogsRepository;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	AppPropertiesFileLogsCaster appPropertiesFileLogsCaster;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	public CommonResponse getAppPropertiesFileLogs(PropertyRequest req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Properties File Logs Data from DB:");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.PROPERTIES_FILE_LOGS;

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper()
					.writeValueAsString(prestoJdbcTemplate.queryForList(queryBuilder.toString()));

			List<AppPropertiesDataResponse> ispResponseList = new ArrayList<>();
			appPropertiesFileLogsCaster.prepareAppPropertiesLogs(outputList, ispResponseList);
			response.setData(ispResponseList);
			LOG.info("Properties File Logs Data Service Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching Properties File Logs Data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

}
