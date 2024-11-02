package com.global.meter.inventive.mdm.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.FirmwareConfig;
import com.global.meter.inventive.models.FirmwareConfigResponse;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class FirmwareConfigMdmCaster {

	private static final Logger LOG = LoggerFactory.getLogger(FirmwareConfigMdmCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareFirmwareConfig(String outputList, List<FirmwareConfigResponse> ispResponseList)
			throws Exception {
		LOG.info("Firmware Config Data Caster called....");
		List<FirmwareConfig> deviceCommandData = new ArrayList<FirmwareConfig>();
		deviceCommandData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<FirmwareConfig>>() {
		});

		LOG.info("Firmware Config Response Data Adding....");

		for (FirmwareConfig ispData : deviceCommandData) {
			FirmwareConfigResponse ispResponse = new FirmwareConfigResponse();

			ispResponse.setMeterNo(String.valueOf(ispData.getDevice_serial_number()));
			ispResponse.setTrackingId(String.valueOf(ispData.getTracking_id()));
			ispResponse.setCmdCompletionTimestamp(ispData.getCommand_completion_datetime() != null
					? dateConverter.convertDateToHesString(ispData.getCommand_completion_datetime())
					: "");
			ispResponse.setDt(String.valueOf(ispData.getDt_name()));
			ispResponse.setFeeder(String.valueOf(ispData.getFeeder_name()));
			ispResponse.setMdasDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			ispResponse.setUtility(String.valueOf(ispData.getOwner_name()));
			ispResponse.setReason(ispData.getReason() != null ? String.valueOf(ispData.getReason()) : "");
			ispResponse.setStatus(String.valueOf(ispData.getStatus()));
			ispResponse.setSubdivisionName(String.valueOf(ispData.getSubdevision_name()));
			ispResponse.setSubstationName(String.valueOf(ispData.getSubstation_name()));
			ispResponse.setTotalAttempts(String.valueOf(ispData.getTot_attempts()));
			ispResponse.setCmdVal(ispData.getCommand_val() != null ? ispData.getCommand_val() : "-");
			ispResponse.setVersion(ispData.getVersion());
			ispResponseList.add(ispResponse);
		}
		LOG.info("Firmware Config Response Data Added....");
	}

}
