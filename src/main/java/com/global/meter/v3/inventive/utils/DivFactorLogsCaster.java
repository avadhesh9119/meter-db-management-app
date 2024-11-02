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
import com.global.meter.v3.inventive.business.model.DivisionFactorFileLog;
import com.global.meter.v3.inventive.models.AppPropertiesDataResponse;

@Component
public class DivFactorLogsCaster {
	private static final Logger LOG = LoggerFactory.getLogger(DivFactorLogsCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareDivisionFactorLogs(String configLogsData, List<AppPropertiesDataResponse> ispResponseList)
			throws Exception {
		LOG.info("Division Factor Data Caster called....");
		List<DivisionFactorFileLog> divFactorLogs = new ArrayList<DivisionFactorFileLog>();
		divFactorLogs = CommonUtils.getMapper().readValue(configLogsData,
				new TypeReference<List<DivisionFactorFileLog>>() {
				});

		LOG.info("Division factor Response Data Caster Adding.");

		for (DivisionFactorFileLog ispData : divFactorLogs) {
			AppPropertiesDataResponse ispResponse = new AppPropertiesDataResponse();

			ispResponse.setTrackingId(ispData.getTracking_id());
			ispResponse.setDescription(ispData.getDescription());
			ispResponse.setNewdivisionFactorValue(ispData.getNew_division_factor_value());
			ispResponse.setOldDivisionFactorValue(ispData.getOld_division_factor_value());
			ispResponse.setSource(ispData.getSource());
			ispResponse.setUpdatedBy(ispData.getUpdated_by());
			ispResponse.setUpdatedOn(
					ispData.getUpdated_on() != null ? dateConverter.convertDateToHesString(ispData.getUpdated_on())
							: "-");
			ispResponse.setCreatedBy(ispData.getCreated_by());
			ispResponse.setCreatedOn(
					ispData.getCreated_on() != null ? dateConverter.convertDateToHesString(ispData.getCreated_on())
							: "-");
			ispResponse.setUserComment(ispData.getUser_comment());
			ispResponse.setDivisionFactorType(ispData.getDiv_factor_type() != null ? ispData.getDiv_factor_type() : "-");

			ispResponseList.add(ispResponse);
		}
		LOG.info("Division factor Response Data Caster Added.");
	}

}
