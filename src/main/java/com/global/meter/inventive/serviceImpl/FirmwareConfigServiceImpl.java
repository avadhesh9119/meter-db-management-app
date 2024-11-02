package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.FirmwareDeleteLogs;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.FirmwareConfigResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.FirmwareOperationService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.FirmwareConfigCaster;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class FirmwareConfigServiceImpl implements FirmwareOperationService {

	private static final Logger LOG = LoggerFactory.getLogger(SubstationsServiceImpl.class);

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	FirmwareConfigCaster firmwareConfigCaster;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Override
	public CommonResponse getFirwareConfig(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Firmware Config Data from DB: ");

		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.FIRMWARE_CONFIG;

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<FirmwareConfigResponse> ispResponseList = new ArrayList<>();
			firmwareConfigCaster.prepareFirmwareConfig(outputList, ispResponseList,req);
			response.setData(ispResponseList);
			LOG.info("Firmware Config Data By Hier Service Response Success.");
			
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getFirwareConfigList() {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Firmware Config Data List from DB: ");

		try {

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace()).append(".")
					.append(Tables.FIRMWARE_CONFIG);

			String query = queryBuilder.substring(0, queryBuilder.length());
			query = query.concat(" order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<FirmwareConfigResponse> devicesCommandList = new ArrayList<>();

			firmwareConfigCaster.prepareFirmwareConfig(outputList, devicesCommandList);
			response.setData(devicesCommandList);
			LOG.info("Firmware Config Data List Service Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}

		return response;
	}
	
	@Override
	public CommonResponse firmwareDeletedLogs(MeterDataVisualizationReq req) {

		CommonResponse response = new CommonResponse();
		LOG.info("Getting firmware deleted Log Data from DB:--> ");

		try {
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ")
					.append(meterConfiguration.getKeyspace() + "." + Tables.FIRMWARE_DELETED_LOGS);
			queryBuilder.append(" where");

			if (req.getDevType() != null && !StringUtils.isEmpty(req.getDevType())) {
				queryBuilder.append(" device_type = '").append(req.getDevType()).append("' and ");
			}
			if (req.getManufacturer() != null && !StringUtils.isEmpty(req.getManufacturer())) {
				queryBuilder.append(" manufacturer = '").append(req.getManufacturer()).append("' and ");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 5);

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<FirmwareDeleteLogs> firmwareLogs = new ArrayList<FirmwareDeleteLogs>();
			firmwareLogs = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<FirmwareDeleteLogs>>() {
			});

			List<FirmwareConfigResponse> devicesList = new ArrayList<>();

			for (FirmwareDeleteLogs firmwareInfo : firmwareLogs) {

				FirmwareConfigResponse configResponse = new FirmwareConfigResponse();

				configResponse.setCreated(
						firmwareInfo.getCreated() != null ? dateConverter.convertDateToString(firmwareInfo.getCreated())
								: req.getReplaceBy());
				configResponse.setDeviceType(
						firmwareInfo.getDevice_type() != null ? firmwareInfo.getDevice_type() : req.getReplaceBy());
				configResponse.setModified(firmwareInfo.getModified() != null
						? dateConverter.convertDateToString(firmwareInfo.getModified())
						: req.getReplaceBy());
				configResponse.setOwnerName(
						firmwareInfo.getOwner_name() != null ? firmwareInfo.getOwner_name() : req.getReplaceBy());
				configResponse
						.setSource(firmwareInfo.getSource() != null ? firmwareInfo.getSource() : req.getReplaceBy());
				configResponse
						.setStatus(firmwareInfo.getStatus() != null ? firmwareInfo.getStatus() : req.getReplaceBy());
				configResponse.setUpdatedBy(
						firmwareInfo.getUpdated_by() != null ? firmwareInfo.getUpdated_by() : req.getReplaceBy());
				configResponse
						.setUserId(firmwareInfo.getUser_id() != null ? firmwareInfo.getUser_id() : req.getReplaceBy());
				configResponse
						.setVersion(firmwareInfo.getVersion() != null ? firmwareInfo.getVersion() : req.getReplaceBy());
				configResponse.setDeletedBy(
						firmwareInfo.getDeleted_by() != null ? firmwareInfo.getDeleted_by() : req.getReplaceBy());
				configResponse.setDeletedOn(firmwareInfo.getDeleted_on() != null
						? dateConverter.convertDateToString(firmwareInfo.getDeleted_on())
						: req.getReplaceBy());
				configResponse.setCmdVal(
						firmwareInfo.getFile_name() != null ? firmwareInfo.getFile_name() : req.getReplaceBy());
				configResponse.setManufacturer(
						firmwareInfo.getManufacturer() != null ? firmwareInfo.getManufacturer() : req.getReplaceBy());

				devicesList.add(configResponse);
			}

			response.setData(devicesList);
			LOG.info("firmware deleted Log Data List Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in getting firmware deleted Log due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

}