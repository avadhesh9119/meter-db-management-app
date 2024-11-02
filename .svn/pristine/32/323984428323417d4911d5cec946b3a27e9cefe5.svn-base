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
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.models.AppPropertiesDataResponse;
import com.global.meter.v3.inventive.repository.DivisionFactorFileLogsRepository;
import com.global.meter.v3.inventive.utils.DivFactorLogsCaster;

@Service
public class DivFactorFileLogsService {
	private static final Logger LOG = LoggerFactory.getLogger(DivFactorFileLogsService.class);

	@Autowired
	DivisionFactorFileLogsRepository divisionFactorFileLogsRepository;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	DivFactorLogsCaster divFactorLogsCaster;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	public CommonResponse getDivisionFactorFileLogs(PropertyRequest req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Division Factor Logs Data from DB:");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.DIVISION_FACTOR_FILE_LOGS;

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append("div_factor_type = '").append(Constants.DivFactorType.PULL).append("'and mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper()
					.writeValueAsString(prestoJdbcTemplate.queryForList(queryBuilder.toString()));

			List<AppPropertiesDataResponse> ispResponseList = new ArrayList<>();
			divFactorLogsCaster.prepareDivisionFactorLogs(outputList, ispResponseList);
			response.setData(ispResponseList);
			LOG.info("Division factor Logs Data Service Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching division factor Logs Data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

}
