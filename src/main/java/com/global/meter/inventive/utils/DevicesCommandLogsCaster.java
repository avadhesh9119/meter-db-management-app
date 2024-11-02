package com.global.meter.inventive.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.global.meter.inventive.models.DeviceCommandLogsResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class DevicesCommandLogsCaster {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesCommandLogsCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareDevicesCommandLogs(String commandLogData, List<DeviceCommandLogsResponse> ispResponseList)
			throws Exception {
		LOG.info("Get Device Command Logs Data Caster Called:");

		try {

			LOG.info("Get Device Command Logs Response Data Adding.");

			JsonNode devicesCommandList = CommonUtils.getMapper().readTree(commandLogData);

			for (JsonNode logs : devicesCommandList) {
				DeviceCommandLogsResponse ispResponse = new DeviceCommandLogsResponse();
				ispResponse.setBatchId(logs.get(Constants.DeviceCommandLogs.BATCH_ID) != null ? 
						logs.get(Constants.DeviceCommandLogs.BATCH_ID).asText() : null);
				ispResponse.setMeterNumber(logs.get(Constants.DeviceCommandLogs.DEVICE_SERIAL_NUMBER) != null ? 
						logs.get(Constants.DeviceCommandLogs.DEVICE_SERIAL_NUMBER).asText() : null);
				ispResponse.setTrackingId(logs.get(Constants.DeviceCommandLogs.TRACKING_ID).asText());

				ispResponse.setCmdCompletionTimestamp(
						logs.get(Constants.DeviceCommandLogs.COMMAND_COMPLETION_DATETIME) != null
								? dateConverter.convertStringDateToString(
										logs.get(Constants.DeviceCommandLogs.COMMAND_COMPLETION_DATETIME).asText()
												.toString())
								: "-");

				ispResponse
						.setHesTimestamp(
								logs.get(Constants.DeviceCommandLogs.MDAS_DATETIME) != null
										? dateConverter.convertStringDateToString(
												logs.get(Constants.DeviceCommandLogs.MDAS_DATETIME).asText().toString())
										: "-");
//				ispResponse.setReason(logs.get(Constants.DeviceCommandLogs.REASON).asText());
				ispResponse.setStatus(logs.get(Constants.DeviceCommandLogs.STATUS).asText());
				ispResponse.setTotAttempts(logs.get(Constants.DeviceCommandLogs.TOT_ATTEMPTS) != null
						? String.valueOf(logs.get(Constants.DeviceCommandLogs.TOT_ATTEMPTS).asText())
						: "-");
				ispResponse.setSubdivisionName(logs.get(Constants.DeviceCommandLogs.SUBDEVISION_NAME).asText());
				ispResponse.setSubstationName(logs.get(Constants.DeviceCommandLogs.SUBSTATION_NAME).asText());
				ispResponse.setDt(logs.get(Constants.DeviceCommandLogs.DT_NAME).asText());
				ispResponse.setFeeder(logs.get(Constants.DeviceCommandLogs.FEEDER_NAME).asText());
				ispResponse.setCmdName(logs.get(Constants.DeviceCommandLogs.MOD_COMMAND_NAME) != null 
						? logs.get(Constants.DeviceCommandLogs.MOD_COMMAND_NAME).asText() :
							logs.get(Constants.DeviceCommandLogs.COMMAND_NAME).asText());
				ispResponse.setSource(logs.get(Constants.DeviceCommandLogs.SOURCE) != null ? logs.get(Constants.DeviceCommandLogs.SOURCE).asText()
						: "-");
				ispResponse.setUserId(logs.get(Constants.DeviceCommandLogs.USER_ID) != null ? logs.get(Constants.DeviceCommandLogs.USER_ID).asText()
						: "-");

				ispResponseList.add(ispResponse);
				

			}
			LOG.info("Get Device Command Logs Response Data Added.");
		} catch (Exception e) {
			e.getMessage();
		}

	}
	
	public void prepareDevicesCommandLogs(String commandLogData, List<DeviceCommandLogsResponse> ispResponseList,
			MeterDataVisualizationReq req)
			throws Exception {
		LOG.info("Get Device Command Logs Data Caster Called:");

		try {

			LOG.info("Get Device Command Logs Response Data Adding.");

			JsonNode devicesCommandList = CommonUtils.getMapper().readTree(commandLogData);

			for (int i =0 ; i < devicesCommandList.size(); i++) {
				JsonNode logs = devicesCommandList.get(i);
				DeviceCommandLogsResponse ispResponse = new DeviceCommandLogsResponse();
				JsonNode batchId = logs.get(Constants.DeviceCommandLogs.BATCH_ID);
				ispResponse.setBatchId(batchId != null ? batchId.asText() : req.getReplaceBy());
				ispResponse.setMeterNumber(logs.get(Constants.DeviceCommandLogs.DEVICE_SERIAL_NUMBER).asText());
				ispResponse.setTrackingId(logs.get(Constants.DeviceCommandLogs.TRACKING_ID).asText());

				ispResponse.setCmdCompletionTimestamp(
						logs.get(Constants.DeviceCommandLogs.COMMAND_COMPLETION_DATETIME) != null
								? dateConverter.convertStringDateToString(
										logs.get(Constants.DeviceCommandLogs.COMMAND_COMPLETION_DATETIME).asText()
												.toString())
								: req.getReplaceBy());

				ispResponse
						.setHesTimestamp(
								logs.get(Constants.DeviceCommandLogs.MDAS_DATETIME) != null
										? dateConverter.convertStringDateToString(
												logs.get(Constants.DeviceCommandLogs.MDAS_DATETIME).asText().toString())
										: req.getReplaceBy());
//				ispResponse.setReason(logs.get(Constants.DeviceCommandLogs.REASON).asText());
				ispResponse.setStatus(logs.get(Constants.DeviceCommandLogs.STATUS).asText());
				ispResponse.setTotAttempts(logs.get(Constants.DeviceCommandLogs.TOT_ATTEMPTS) != null
						? String.valueOf(logs.get(Constants.DeviceCommandLogs.TOT_ATTEMPTS).asText())
						: req.getReplaceBy());
				ispResponse.setCmdName(logs.get(Constants.DeviceCommandLogs.MOD_COMMAND_NAME) != null 
						? logs.get(Constants.DeviceCommandLogs.MOD_COMMAND_NAME).asText() :
							logs.get(Constants.DeviceCommandLogs.COMMAND_NAME).asText());
				ispResponse.setSource(logs.get(Constants.DeviceCommandLogs.SOURCE) != null ? logs.get(Constants.DeviceCommandLogs.SOURCE).asText()
						: req.getReplaceBy());
				ispResponse.setUserId(logs.get(Constants.DeviceCommandLogs.USER_ID) != null ? logs.get(Constants.DeviceCommandLogs.USER_ID).asText()
						: req.getReplaceBy());

				ispResponseList.add(ispResponse);
				

			}
			LOG.info("Get Device Command Logs Response Data Added.");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Issue in preparing logs due to :" + e.getMessage());
		}

	}
}
