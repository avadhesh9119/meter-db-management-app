package com.global.meter.inventive.mdm.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.global.meter.inventive.models.DeviceConfigLogsResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class DeviceConfigLogsMdmCaster {
	private static final Logger LOG = LoggerFactory.getLogger(DeviceConfigLogsMdmCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareDeviceConfigLogs(String configLogData, List<DeviceConfigLogsResponse> ispResponseList, MeterDataVisualizationReq req)
			throws Exception {
		LOG.info("Get Device Config Logs Data Caster Called:");

		try {

			LOG.info("Get Device Config Logs Response Data Caster Adding.");

			JsonNode deviceConfigList = CommonUtils.getMapper().readTree(configLogData);

			for (JsonNode logs : deviceConfigList) {
				DeviceConfigLogsResponse ispResponse = new DeviceConfigLogsResponse();
				ispResponse.setDeviceSerialNumber(logs.get(Constants.DeviceConfigLogs.DEVICE_SERIAL_NO) != null
						? String.valueOf(logs.get(Constants.DeviceConfigLogs.DEVICE_SERIAL_NO).asText())
						: null);
				ispResponse.setTrackingId(logs.get(Constants.DeviceConfigLogs.TRACKING_ID) != null
						? String.valueOf(logs.get(Constants.DeviceConfigLogs.TRACKING_ID).asText())
						: null);
				ispResponse.setCommandName(logs.get(Constants.DeviceConfigLogs.MOD_COMMAND_NAME) != null
						? logs.get(Constants.DeviceConfigLogs.MOD_COMMAND_NAME).asText()
						: logs.get(Constants.DeviceConfigLogs.COMMAND_NAME).asText());
//				ispResponse.setCommandStatus(logs.get(Constants.DeviceConfigLogs.MOD_COMMAND_STATUS) != null
//						? logs.get(Constants.DeviceConfigLogs.MOD_COMMAND_STATUS).asText()
//						: logs.get(Constants.DeviceConfigLogs.COMMAND_STATUS).asText());
				ispResponse.setStatus(logs.get(Constants.DeviceConfigLogs.STATUS) != null
						? String.valueOf(logs.get(Constants.DeviceConfigLogs.STATUS).asText())
						: null);
				ispResponse.setHesTimestamp(logs.get(Constants.DeviceConfigLogs.MDAS_DATETIME) != null
									? dateConverter.convertStringDateToString(logs.get(
											Constants.DeviceConfigLogs.MDAS_DATETIME).asText().toString()): req.getReplaceBy());

								ispResponse.setCommandCompletionDatetime(logs.get(Constants.DeviceConfigLogs.COMMAND_COMPLETION_DATETIME) != null
						? dateConverter.convertStringDateToString(logs.get(
								Constants.DeviceConfigLogs.COMMAND_COMPLETION_DATETIME).asText().toString()): req.getReplaceBy());
				
				ispResponse.setActivateActivityCalDatetime(
						logs.get(Constants.DeviceConfigLogs.ACTIVATE_ACTIVITY_CAL_DATETIME) != null
								? String.valueOf(logs.get(Constants.DeviceConfigLogs.ACTIVATE_ACTIVITY_CAL_DATETIME).asText())
								: null);
				ispResponse.setTotAttempts(logs.get(Constants.DeviceConfigLogs.TOT_ATTEMPTS) != null
						? String.valueOf(logs.get(Constants.DeviceConfigLogs.TOT_ATTEMPTS).asText())
						: logs.get(Constants.DeviceConfigLogs.TOT_ATTEMPTS).asText());
				ispResponse.setBatchId(logs.get(Constants.DeviceConfigLogs.BATCH_ID) != null
						? String.valueOf(logs.get(Constants.DeviceConfigLogs.BATCH_ID).asText())
						: req.getReplaceBy());
				ispResponse.setHierName(logs.get(Constants.DeviceConfigLogs.HIER_NAME) != null
						? String.valueOf(logs.get(Constants.DeviceConfigLogs.HIER_NAME).asText())
						: req.getReplaceBy());
				ispResponse.setHierValue(logs.get(Constants.DeviceConfigLogs.HIER_VALUE) != null
						? String.valueOf(logs.get(Constants.DeviceConfigLogs.HIER_VALUE).asText())
						: req.getReplaceBy());
				ispResponse.setSource(logs.get(Constants.DeviceConfigLogs.SOURCE) != null
						? String.valueOf(logs.get(Constants.DeviceConfigLogs.SOURCE).asText())
						: req.getReplaceBy());
				ispResponse.setUserId(logs.get(Constants.DeviceConfigLogs.USER_ID) != null
						? String.valueOf(logs.get(Constants.DeviceConfigLogs.USER_ID).asText())
						: req.getReplaceBy());
				

				ispResponseList.add(ispResponse);

			}
			LOG.info("Get Device Config Logs Response Data Caster Added.");
		} catch (Exception e) {
			e.getMessage();
		}

	}

}
