package com.global.meter.inventive.mdm.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.inventive.models.DevicesConfigModel;
import com.global.meter.inventive.models.DevicesConfigResponse;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class DevicesConfigMdmCaster {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesConfigMdmCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareDevicesConfig(String outputList, List<DevicesConfigResponse> ispResponseList) throws Exception {
		LOG.info("Device Command Data Caster called....");
		List<DevicesConfigModel> deviceCommandData = new ArrayList<DevicesConfigModel>();
		deviceCommandData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DevicesConfigModel>>() {
		});

		LOG.info("Device Command Response Data Adding....");

		for (DevicesConfigModel ispData : deviceCommandData) {
			DevicesConfigResponse ispResponse = new DevicesConfigResponse();

			ispResponse.setMeterNo(String.valueOf(ispData.getDevice_serial_number()));
			ispResponse.setCommandName(ispData.getMod_command_name() != null ?
					String.valueOf(ispData.getMod_command_name()) : ispData.getCommand_name() != null && !Constants.nullKey.equalsIgnoreCase(ispData.getCommand_name()) ? String.valueOf(ispData.getCommand_name()) : "-");
			ispResponse.setTrackingId(String.valueOf(ispData.getTracking_id()));
			ispResponse.setCmdCompletionTimestamp(ispData.getCommand_completion_datetime() != null ? dateConverter.convertDateToHesString(ispData.getCommand_completion_datetime()) : "");
			ispResponse.setDcuSerialNo(ispData.getDcu_serial_number() != null ? String.valueOf(ispData.getDcu_serial_number()) : "-");
			ispResponse.setDt(String.valueOf(ispData.getDt_name()));
			ispResponse.setFeeder(String.valueOf(ispData.getFeeder_name()));
			ispResponse.setMdasDatetime(ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : "");
			ispResponse.setReason(String.valueOf(ispData.getReason()));
			ispResponse.setStatus(String.valueOf(ispData.getStatus()));
			ispResponse.setSubdivisionName(String.valueOf(ispData.getSubdevision_name()));
			ispResponse.setSubstationName(String.valueOf(ispData.getSubstation_name()));
			ispResponse.setTotalAttempts(String.valueOf(ispData.getTot_attempts()));
			ispResponse.setBatchId(String.valueOf(ispData.getBatch_id()));
			ispResponse.setSource(String.valueOf(ispData.getSource()));
			ispResponse.setUserId(String.valueOf(ispData.getUser_id()));

			ispResponseList.add(ispResponse);
		}
		LOG.info("Device Command Response Data Added....");
	}

}
