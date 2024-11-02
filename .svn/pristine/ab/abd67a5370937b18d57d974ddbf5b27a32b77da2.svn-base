package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.BillingCTResponse;
import com.global.meter.inventive.models.BillingSinglePhaseResponse;
import com.global.meter.inventive.models.BillingThreePhaseResponse;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataRes;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.ProcessBillingDataResponse;
import com.global.meter.inventive.service.DeviceBatchLogsServiceImpl;
import com.global.meter.inventive.service.ProcessBillingDataService;
import com.global.meter.inventive.utils.BillingDataCaster;
import com.global.meter.inventive.utils.DevicesCommandLogsCaster;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.MeterCommCountCaster;
import com.global.meter.inventive.utils.ProcessBillingDataCaster;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class ProcessBillingDataServiceImpl implements ProcessBillingDataService {

	private static final Logger LOG = LoggerFactory.getLogger(DeviceBatchLogsServiceImpl.class);

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	DevicesCommandLogsCaster deviceCmdLogsCaster;

	@Autowired
	DevicesInfoRepository devicesInfoRepository;

	@Autowired
	DevicesInfoService devicesInfoService;

	@Autowired
	BillingDataCaster billingCaster;

	@Autowired
	ProcessBillingDataCaster processBillingDataCaster;
	
	@Autowired
	MeterCommCountCaster meterCommCountCaster;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Override
	public void saveBillingDataProcess() {

		LOG.info("Getting DevicesCommands Data from DB:-->");
		try {
			List<DevicesInfo> deviceList = new ArrayList<>();
			String table = meterConfiguration.getKeyspace() + "." + Tables.devInfo;

			StringBuilder queryBuilder = new StringBuilder();

			queryBuilder.append("select *  from ")
			.append(table).append(" where installation_datetime <= cast('").append(new DateConverter().convertLocalDateToString(CommonUtils.getFirstDateOfMonth())).append("' as timestamp) and ")
			.append(" commissioning_status in ('Up','Down')");
			String query = queryBuilder.substring(0, queryBuilder.length());
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			deviceList = CommonUtils.getMapper().readValue(outputList,
					new TypeReference<List<DevicesInfo>>() {
					});
			for (DevicesInfo device : deviceList) {

				String billingTable = "";

				if (device.getDevice_type().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {

					billingTable = meterConfiguration.getKeyspace() + "." + Tables.BILLING_DATA_SP;
				} else if (device.getDevice_type().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {

					billingTable = meterConfiguration.getKeyspace() + "." + Tables.BILLING_DATA_TP;
				} else if (device.getDevice_type().contains(ExternalConstants.DeviceTypes.CT_METER)) {

					billingTable = meterConfiguration.getKeyspace() + "." + Tables.BILLING_DATA_CT;
				}
				processBillingData(device, billingTable);

			}
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
		}

	}

	private void processBillingData(DevicesInfo device, String billingTable) throws Exception {

		StringBuilder queryBuilder = new StringBuilder();

		queryBuilder.append(
				"select device_serial_number, mdas_datetime, billing_datetime, subdevision_name, substation_name, feeder_name, dt_name from ");

		queryBuilder.append(billingTable).append(" where billing_datetime >= cast('")
				.append(new DateConverter().convertLocalDateToString(CommonUtils.getFirstDateOfMonth()))
				.append("' as timestamp) and device_serial_number = '").append(device.getDevice_serial_number())
				.append("' order by billing_datetime");
		String query = queryBuilder.substring(0, queryBuilder.length());
		String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

		processBillingDataCaster.saveProcessBillingData(outputList, device);

	}

	@Override
	public CommonResponse getProcessBillingData(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();

		LOG.info("Getting DevicesConfigLogs Data from DB:-->");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.PROCESS_BILLING_PROFILE_DATA;
			String devTable = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_INFO;

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select count(*) as devCount, SUM(CASE WHEN b.is_received_billing = 'yes' THEN 1 ELSE 0 END) AS recivedCount,"
					+ " SUM(CASE WHEN b.is_on_time_billing = 'yes' THEN 1 ELSE 0 END) as onTimeCount from ")
			.append(table).append(" as b inner join ").append(devTable).append(" as d on b.device_serial_number = d.device_serial_number where b.commissioning_status in ('Up','Down') and d.commissioning_status in ('Up','Down') and ")
			.append("d.installation_datetime <= cast('").append(new DateConverter().convertLocalDateToString(CommonUtils.getFirstDateOfMonth())).append("' as timestamp) and ");
			if(req.getDevType() != null && !req.getDevType().isEmpty()) {
					queryBuilder.append("b.device_type = '").append(req.getDevType()).append("' and b.");
			}
					queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(")");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForMap(query));

			ProcessBillingDataResponse ispResponseList = new ProcessBillingDataResponse();
			processBillingDataCaster.prepareProcessBillingData(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("Instant Single Phase Data Service Response by Hier  Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getProcessBillingDataDrillDown(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Billing Data from DB:--> ");
		try {

			String table = "";
			String devTable = meterConfiguration.getKeyspace() + "." + Tables.devInfo;
			
			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.BILLING_DATA_SP;
			} else if (req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.BILLING_DATA_TP;
			} else if (req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.BILLING_DATA_CT;
			} else {
				response.setMessage("Wrong Device type sent");
				response.setCode(404);
				response.setError(true);
				return response;
			}

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();

			if(req.getIsRecievedBilling() != null && !req.getIsRecievedBilling().isEmpty() 
					&& req.getIsRecievedBilling().equalsIgnoreCase(Constants.YES)) {
				
				queryBuilder.append("WITH RankedData AS ( SELECT ")
				.append(table).append(".device_serial_number, ").append(table).append(".billing_datetime, ")
				.append(table).append(".cumulative_energy_kvah_export, ").append(table).append(".cumulative_energy_kvah_import, ")
				.append(table).append(".cumulative_energy_kwh_export, ").append(table).append(".cumulative_energy_kwh_import, ")
				.append("ROW_NUMBER() OVER (PARTITION BY ").append(table).append(".device_serial_number ORDER BY ")
				.append(table).append(".billing_datetime DESC) AS rn from ").append(table)
				.append(" INNER JOIN ").append(devTable).append(" on ").append(table).append(".device_serial_number")
				.append(" = ").append(devTable).append(".device_serial_number where ").append(devTable).append(".commissioning_status in ('Up','Down') and ")
				.append(devTable).append(".installation_datetime <= cast('").append(new DateConverter().convertLocalDateToString(CommonUtils.getFirstDateOfMonth())).append("' as timestamp) and ")
				.append(devTable).append(".").append(fieldName).append(" in (");
				for (String level : levels) {
					queryBuilder.append("'").append(level).append("',");
				}
				queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ")")
				.append(" and ").append(table).append(".billing_datetime >= cast('").append(new DateConverter().convertLocalDateToString(CommonUtils.getFirstDateOfMonth()))
				.append("' as timestamp))").append(" select device_serial_number, billing_datetime, cumulative_energy_kvah_export, cumulative_energy_kvah_import,"
						+ " cumulative_energy_kwh_export, cumulative_energy_kwh_import").append(" from RankedData").append(" where rn = 1")
				.append(" ORDER BY device_serial_number DESC");
				
				String query = queryBuilder.substring(0, queryBuilder.length());
				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
					List<BillingSinglePhaseResponse> ispResponseList = new ArrayList<>();
					billingCaster.prepareSinglePhase(outputList, ispResponseList, req);
					response.setData(ispResponseList);
					LOG.info("Single Phase Billing Data Service Response by Hier  Success.");
				} else if (req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
					List<BillingThreePhaseResponse> ispResponseList = new ArrayList<>();
					billingCaster.prepareThreePhase(outputList, ispResponseList, req);
					response.setData(ispResponseList);
					LOG.info("Three Phase Billing Data Service Response by Hier  Success.");
				} else if (req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
					List<BillingCTResponse> ispResponseList = new ArrayList<>();
					billingCaster.prepareCT(outputList, ispResponseList, req);
					response.setData(ispResponseList);
					LOG.info("CT Meter Billing Data Service Response by Hier  Success.");
				}
				
			}
			else if(req.getIsRecievedBilling() != null && !req.getIsRecievedBilling().isEmpty() 
					&& req.getIsRecievedBilling().equalsIgnoreCase(Constants.NO)) {
				queryBuilder.append("select * from ").append(devTable).append(" where device_serial_number in (")
				.append(" select device_serial_number from ").append(devTable).append(" where commissioning_status in ('Up','Down') and device_type = '").append(req.getDevType())
				.append("' except select distinct device_serial_number from ").append(table).append(" where billing_datetime >= cast('")
				.append(new DateConverter().convertLocalDateToString(CommonUtils.getFirstDateOfMonth()))
				.append("' as timestamp)) and ")
				.append(" installation_datetime <= cast('").append(new DateConverter().convertLocalDateToString(CommonUtils.getFirstDateOfMonth()))
				.append("' as timestamp) and ").append(fieldName).append(" in (");
				for (String level : levels) {
					queryBuilder.append("'").append(level).append("',");
				}
				queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ")");
				
				String query = queryBuilder.substring(0, queryBuilder.length());
				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<MeterDataRes> ispResponseList = new ArrayList<>();
				meterCommCountCaster.prepareDeviceCommandSuccessDrillDown(outputList, ispResponseList, req);
				response.setData(ispResponseList);
			}

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

}
