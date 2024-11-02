package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.global.meter.business.model.SchedulerConfigurationLogs;
import com.global.meter.data.repository.SchedulerConfigurationLogsRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.SchedulerDataReq;
import com.global.meter.inventive.models.SchedulerDataResponse;
import com.global.meter.inventive.utils.SchedulerConfigurationLogsCaster;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class SchedulerConfigurationLogsService {
	private static final Logger LOG = LoggerFactory.getLogger(SchedulerConfigurationLogsService.class);

	@Autowired
	SchedulerConfigurationLogsRepository configurationLogsRepository;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;
	
	@Autowired
	SchedulerConfigurationLogsCaster schedulerConfigurationLogsCaster;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	public CommonResponse addSchedulerConfigLogs(SchedulerDataReq schedulerDataReq) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Devices Info Data from DB: ");
		SchedulerConfigurationLogs configurationLogs = new SchedulerConfigurationLogs();
		try {

			configurationLogs.setData(schedulerDataReq.getData());
			configurationLogs.setDescription(schedulerDataReq.getDescription());
			configurationLogs.setMdas_datetime(new Date(System.currentTimeMillis()));
			configurationLogs.setSource(schedulerDataReq.getSource());
			configurationLogs.setUser_id(schedulerDataReq.getUserId());
			configurationLogs.setTracking_id(schedulerDataReq.getTrackingId());

			configurationLogsRepository.save(configurationLogs);
			response.setCode(200);
			LOG.info("successfully added scheduler config logs in DB: ");
		} catch (Exception e) {
			LOG.info("Issue in adding data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	public SchedulerDataReq getSchedulerConfigLogsByTrackingId(@Valid SchedulerDataReq req) {
		SchedulerDataReq response = new SchedulerDataReq();
		SchedulerConfigurationLogs data = new SchedulerConfigurationLogs();
		LOG.info("Getting Instant Data from DB:--> ");
		try {

			Optional<SchedulerConfigurationLogs> responseData = configurationLogsRepository
					.findById(req.getTrackingId());
			data = responseData.get();
			response.setData(data.getData());
			
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
		}

		return response;
	}

	
	public CommonResponse getSchedulerConfigLogs(@Valid SchedulerDataReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Scheduler Config Logs Data from DB:");
		try {

		    String table = meterConfiguration.getKeyspace() + "." + Tables.SCHEDULER_CONFIG_LOGS;
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) order by mdas_datetime desc");
			
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queryBuilder.toString()));

			List<SchedulerDataResponse> ispResponseList = new ArrayList<>();
			schedulerConfigurationLogsCaster.prepareSchedulerConfigLogs(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("Scheduler Config Logs Data Service Response Success.");
		
		} catch (Exception e) {
			LOG.error("Issue in fetching Scheduler Config Logs Data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

}
