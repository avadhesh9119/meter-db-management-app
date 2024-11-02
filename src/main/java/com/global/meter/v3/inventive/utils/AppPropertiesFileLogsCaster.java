package com.global.meter.v3.inventive.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.v3.inventive.business.model.AppPropertiesFileLog;
import com.global.meter.v3.inventive.models.AppPropertiesDataResponse;

@Component
public class AppPropertiesFileLogsCaster {
	private static final Logger LOG = LoggerFactory.getLogger(AppPropertiesFileLogsCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareAppPropertiesLogs(String configLogsData, List<AppPropertiesDataResponse> ispResponseList)
			throws Exception {
		LOG.info("Application properties Data Caster called....");
		List<AppPropertiesFileLog> appPrppertiesLogs = new ArrayList<AppPropertiesFileLog>();
		appPrppertiesLogs = CommonUtils.getMapper().readValue(configLogsData,
				new TypeReference<List<AppPropertiesFileLog>>() {
				});

		LOG.info("Application properties Response Data Caster Adding.");

		for (AppPropertiesFileLog ispData : appPrppertiesLogs) {
			AppPropertiesDataResponse ispResponse = new AppPropertiesDataResponse();

			ispResponse.setTrackingId(ispData.getTracking_id());
			ispResponse.setDescription(ispData.getDescription());
			ispResponse.setNewPropertyValue(ispData.getNew_property_value());
			ispResponse.setOldPropertyValue(ispData.getOld_property_value());
			ispResponse.setSource(ispData.getSource());
			ispResponse.setUpdatedBy(ispData.getUpdated_by());
			ispResponse.setUpdatedOn(
					ispData.getUpdated_on() != null ? dateConverter.convertDateToHesString(ispData.getUpdated_on())
							: "-");
			ispResponse.setUserComment(ispData.getUser_comment());

			ispResponseList.add(ispResponse);
		}
		LOG.info("Application properties Response Data Caster Added.");
	}

}
