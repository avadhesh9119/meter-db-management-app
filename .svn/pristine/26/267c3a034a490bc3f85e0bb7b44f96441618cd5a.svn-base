package com.global.meter.inventive.dashboard.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.global.meter.inventive.dashboard.model.DevicesStatusResponse;
import com.global.meter.inventive.dashboard.service.DashboardService;
import com.global.meter.inventive.dashboard.utils.DashboardMeterStatusCaster;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.serviceImpl.MeterCommCountImpl;
import com.global.meter.inventive.utils.DevicesCommandCaster;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DashboardServiceImpl implements DashboardService {
	private static final Logger LOG = LoggerFactory.getLogger(MeterCommCountImpl.class);

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	DashboardMeterStatusCaster dashboardMeterStatusCaster;

	@Autowired
	DevicesCommandCaster deviceCmdCaster;

	@Autowired
	DateConverter dateConverter;
	
	@Override
	public CommonResponse getMeterStatus(MeterDataVisualizationReq req) {
		LOG.info("Meter data count service called.");
		CommonResponse response = new CommonResponse();

		try {
			LOG.info("Get devices info & name plate data count from DB:");

			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_INFO;
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select count(*) as totalMeter, ")
					.append("sum(case when commissioning_status = 'Up' and ").append("Commissioning_datetime >= cast('")
					.append(CommonUtils.getCurrentDate()).append("' as timestamp)")
					.append(" then 1 else 0 end) as todaysCommissioned, ")
					.append("sum(case when commissioning_status = 'Up' then 1 else 0 end) as commissioned, ")
					.append("sum(case when commissioning_status = 'Down' then 1 else 0 end) as down, ")
					.append("sum(case when ").append("installation_datetime >= cast('")
					.append(CommonUtils.getCurrentDate()).append("' as timestamp)")
					.append(" then 1 else 0 end) as todaysInstalled, ")
					.append("sum(case when installation_datetime IS NOT NULL then 1 else 0 end) as installed, ")
					.append("sum(case when commissioning_status = 'Inactive' then 1 else 0 end) as inActive, ")
					.append("sum(case when commissioning_status = 'Faulty' then 1 else 0 end) as faulty ")
					.append("from ").append(table).append(" where ");

			if (req.getDevType() != null && !req.getDevType().isEmpty()) {
				queryBuilder.append("device_type = '").append(req.getDevType()).append("' and ");
			}

			queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("'");
			}
			queryBuilder.append(")");
			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DevicesStatusResponse> isResponseList = new ArrayList<>();
			dashboardMeterStatusCaster.prepareMeterDataCount(outputList, isResponseList);
			response.setData(isResponseList);
			LOG.info("Meter data count service success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getTodayInstalledMeterDrilldown(MeterDataVisualizationReq req) {
		LOG.info("Today's Installed Meter Data Drilldown service called.");
		CommonResponse response = new CommonResponse();

		try {
			LOG.info("Get Today's Installed Meter Data Drilldown from DB:");

			String table = meterConfiguration.getKeyspace() + "." + Tables.devInfo;

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();

			queryBuilder.append("select * from ").append(table).append(" where ")
					.append("installation_datetime >= cast('").append(CommonUtils.getCurrentDate())
					.append("' as timestamp) and ")

					.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("')");
			}
			if (req.getDevType() != null && !req.getDevType().isEmpty()) {
				queryBuilder.append(" and device_type = '").append(req.getDevType()).append("'");
			}

			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
			List<DevicesStatusResponse> isResponseList = new ArrayList<>();
			dashboardMeterStatusCaster.prepareTodaysInstalledMeterDatadrilldown(outputList, isResponseList, req);
			response.setData(isResponseList);

			LOG.info("Today's Installed Meter Data Drilldown Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getTodayCommissionedMeterDrilldown(MeterDataVisualizationReq req) {
		LOG.info("Today's Commissioned Meter Data Drilldown service called.");
		CommonResponse response = new CommonResponse();

		try {
			LOG.info("Get Today's Commissioned Meter Data Drilldown from DB:");

			String table = meterConfiguration.getKeyspace() + "." + Tables.devInfo;

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();

			queryBuilder.append("select * from ").append(table).append(" where ")
					.append("commissioning_datetime >= cast('").append(CommonUtils.getCurrentDate())
					.append("' as timestamp) and ")

					.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("')");
			}
			if (req.getDevType() != null && !req.getDevType().isEmpty()) {
				queryBuilder.append(" and device_type = '").append(req.getDevType()).append("'");
			}

			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
			List<DevicesStatusResponse> isResponseList = new ArrayList<>();
			dashboardMeterStatusCaster.prepareTodaysInstalledMeterDatadrilldown(outputList, isResponseList, req);
			response.setData(isResponseList);

			LOG.info("Today's Commissioned Meter Data Drilldown Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}


}
