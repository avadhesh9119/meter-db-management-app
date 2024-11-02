package com.global.meter.inventive.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.SchedulerConfigurationLogs;
import com.global.meter.inventive.models.SchedulerDataReq;
import com.global.meter.inventive.models.SchedulerDataResponse;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class SchedulerConfigurationLogsCaster {
	private static final Logger LOG = LoggerFactory.getLogger(SchedulerConfigurationLogsCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareSchedulerConfigLogs(String configLogsData,
			List<SchedulerDataResponse> ispResponseList, SchedulerDataReq req) throws Exception {
		LOG.info("Device Command Data Caster called....");
		List<SchedulerConfigurationLogs> schedulerConfigurationLogs = new ArrayList<SchedulerConfigurationLogs>();
		schedulerConfigurationLogs = CommonUtils.getMapper().readValue(configLogsData, new TypeReference<List<SchedulerConfigurationLogs>>() {
		});

		LOG.info("Device Command Response Data Caster Adding.");

		for (SchedulerConfigurationLogs ispData : schedulerConfigurationLogs) {
			SchedulerDataResponse ispResponse = new SchedulerDataResponse();
			
			ispResponse.setDescription(req.getReplaceBy());
			ispResponse.setScheduler(req.getReplaceBy());
			ispResponse.setValue(req.getReplaceBy());
			ispResponse.setStatus(req.getReplaceBy());
			ispResponse.setRetry(req.getReplaceBy());
			if (ispData.getDescription() != null) {
			
			if(ispData.getDescription().contains(",")) {
				String[] keys = ispData.getDescription().split(",");
				
				if (keys != null && keys.length > 0) {
					
					for (String key : keys) {
					
						String keyName = "";
						
						String val = "-";
						if (key.contains(":")) {
							String[] values = key.split(":",2);
							if(values != null && values.length > 0 && values.length == 2) {
								keyName = values[0];
								val = values[1];
								}
							}
						
						switch (keyName.trim()) {
						case Constants.User_Desc:
							ispResponse.setDescription(val.trim());
							break;
						case Constants.Property:
						case Constants.Scheduler:
							ispResponse.setScheduler(val.trim());
							break;
						case Constants.Value:
							ispResponse.setValue(val.trim());
							break;
						case Constants.Status_:
							ispResponse.setStatus(Constants.TRUE.equalsIgnoreCase(val.trim()) ? Constants.Active : Constants.Inactive);
							break;
						case Constants.Retry:
							ispResponse.setRetry(Constants.TRUE.equalsIgnoreCase(val.trim()) ? Constants.YES : Constants.NO);
							break;
						default:
							break;
						}
					}
				}
			}
		}
			ispResponse.setTrackingId(ispData.getTracking_id());
			ispResponse.setHesTimestamp(ispData.getMdas_datetime() != null
					? dateConverter.convertDateToHesString(ispData.getMdas_datetime())
					: "-");
			ispResponse.setSource(ispData.getSource());
			ispResponse.setUserId(ispData.getUser_id());

			ispResponseList.add(ispResponse);
		}
		LOG.info("Device Command Response Data Caster Added.");
	}

}
