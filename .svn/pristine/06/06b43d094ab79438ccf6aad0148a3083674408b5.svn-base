package com.global.meter.inventive.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.global.meter.inventive.models.ConfigurationReadDataLogsResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class ConfigurationReadDataLogsCaster {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationReadDataLogsCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareConfigurationReadDataLogs(String configReadLogsData,
			List<ConfigurationReadDataLogsResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception {
		LOG.info("Get Configuration Read Logs Data Caster Called:");

		try {

			LOG.info("Get Configuration Read Logs Response Data Adding.");

			JsonNode configurationReadList = CommonUtils.getMapper().readTree(configReadLogsData);

			for (JsonNode logs : configurationReadList) {
				ConfigurationReadDataLogsResponse ispResponse = new ConfigurationReadDataLogsResponse();
				ispResponse.setDeviceSerialNumber(
						logs.get(Constants.ConfigurationReadDataLogs.DEVICE_SERIAL_NUMBER).asText());
				ispResponse.setTrackingId(logs.get(Constants.ConfigurationReadDataLogs.TRACKING_ID).asText());

				ispResponse.setCommandCompletionDateTime(
						logs.get(Constants.ConfigurationReadDataLogs.COMMAND_COMPLETION_DATETIME) != null
								? dateConverter.convertStringDateToString(
										logs.get(Constants.ConfigurationReadDataLogs.COMMAND_COMPLETION_DATETIME)
												.asText().toString()) : req.getReplaceBy());
				ispResponse.setCommandName(logs.get(Constants.ConfigurationReadDataLogs.MOD_COMMAND_NAME) != null
						? String.valueOf(logs.get(Constants.ConfigurationReadDataLogs.MOD_COMMAND_NAME).asText())
						: renameCommandName(String.valueOf(logs.get(Constants.ConfigurationReadDataLogs.COMMAND_NAME).asText())));

				ispResponse.setMdasDateTime(logs.get(Constants.ConfigurationReadDataLogs.MDAS_DATETIME) != null
						? dateConverter.convertStringDateToString(
								logs.get(Constants.ConfigurationReadDataLogs.MDAS_DATETIME).asText().toString())
						: req.getReplaceBy());
//				ispResponse.setReason(logs.get(Constants.ConfigurationReadDataLogs.REASON) != null
//						? String.valueOf(logs.get(Constants.ConfigurationReadDataLogs.REASON).asText())
//						: null);
				ispResponse.setStatus(logs.get(Constants.ConfigurationReadDataLogs.STATUS) != null
						? String.valueOf(logs.get(Constants.ConfigurationReadDataLogs.STATUS).asText())
						: req.getReplaceBy());
				ispResponse.setTotAttempts(logs.get(Constants.ConfigurationReadDataLogs.TOT_ATTEMPTS) != null
						? String.valueOf(logs.get(Constants.ConfigurationReadDataLogs.TOT_ATTEMPTS).asText())
						: req.getReplaceBy());
				ispResponse.setSource(logs.get(Constants.ConfigurationReadDataLogs.SOURCE) != null
						? String.valueOf(logs.get(Constants.ConfigurationReadDataLogs.SOURCE).asText())
						: req.getReplaceBy());
				ispResponse.setUserId(logs.get(Constants.ConfigurationReadDataLogs.USER_ID) != null
						? String.valueOf(logs.get(Constants.ConfigurationReadDataLogs.USER_ID).asText())
						: req.getReplaceBy());
				ispResponse.setBatchId(logs.get(Constants.ConfigurationReadDataLogs.BATCH_ID) != null ?
						String.valueOf(logs.get(Constants.ConfigurationReadDataLogs.BATCH_ID).asText()) : req.getReplaceBy());

				ispResponseList.add(ispResponse);

			}
			LOG.info("Get Configuration Read Logs Response Data Added.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String renameCommandName(String command) {
		String newCommand = command;
		if(command.contains("ActivityCalendar")) {
		String []str=	command.split(":");
			newCommand =  "{"+"\"Activity Calendar/TOD\""+" : "+str[1];
		}
		if(command.contains("EnableDisableDisconnectControl")) {
			String []str=	command.split(":");
			newCommand =  "{"+"\"Load Control mode\""+" : "+str[1];
		}
		if(command.contains("ActivitySchedulePush")) {
			String []str=	command.split(":");
			newCommand =  "{"+"\"Push Setup Duration\""+" : "+str[1];
		}
		return newCommand;
		
	}
}
