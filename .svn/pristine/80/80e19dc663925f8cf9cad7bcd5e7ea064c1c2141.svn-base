package com.global.meter.inventive.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.FirmwareConfig;
import com.global.meter.business.model.FirmwareData;
import com.global.meter.inventive.models.FirmwareConfigResponse;
import com.global.meter.inventive.models.FirmwareDataResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class FirmwareConfigCaster {

	private static final Logger LOG = LoggerFactory.getLogger(FirmwareConfigCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareFirmwareConfig(String outputList, List<FirmwareConfigResponse> ispResponseList,MeterDataVisualizationReq req)
			throws Exception {
		LOG.info("Firmware Config Data Caster called....");
		List<FirmwareConfig> deviceCommandData = new ArrayList<FirmwareConfig>();
		deviceCommandData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<FirmwareConfig>>() {
		});

		LOG.info("Firmware Config Response Data Adding....");

		for (FirmwareConfig ispData : deviceCommandData) {
			FirmwareConfigResponse ispResponse = new FirmwareConfigResponse();

			ispResponse.setMeterNo(ispData.getDevice_serial_number());
			ispResponse.setTrackingId(ispData.getDevice_serial_number());
			ispResponse.setCmdCompletionTimestamp(ispData.getCommand_completion_datetime() != null
					? dateConverter.convertDateToHesString(ispData.getCommand_completion_datetime())
					: req.getReplaceBy());
			ispResponse.setDt(ispData.getDt_name() != null ? ispData.getDt_name() : req.getReplaceBy());
			ispResponse.setFeeder(ispData.getFeeder_name() != null ? ispData.getFeeder_name() : req.getReplaceBy());
			ispResponse.setMdasDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			ispResponse.setUtility(ispData.getOwner_name() != null ? ispData.getOwner_name() : req.getReplaceBy());
			ispResponse.setStatus(ispData.getStatus() != null ? ispData.getStatus() : req.getReplaceBy());
			ispResponse.setSubdivisionName(ispData.getSubdevision_name() != null ? ispData.getSubdevision_name() : req.getReplaceBy());
			ispResponse.setSubstationName(ispData.getSubstation_name() != null ? ispData.getSubstation_name() : req.getReplaceBy());
			ispResponse.setTotalAttempts(ispData.getTot_attempts() != null ? String.valueOf(req.getReplaceBy()) : req.getReplaceBy());
			ispResponse.setCmdVal(ispData.getCommand_val() != null ? ispData.getCommand_val() : req.getReplaceBy());
			ispResponse.setVersion(ispData.getVersion() != null ? ispData.getVersion() : req.getReplaceBy());
			ispResponse.setSource(ispData.getSource() != null ? ispData.getSource() : req.getReplaceBy());
			ispResponse.setUserId(ispData.getUser_id() != null ? ispData.getUser_id() : req.getReplaceBy());
			ispResponse.setReason(ispData.getReason() != null ? CommonUtils.splitReason(ispData.getReason(), ispData.getTot_attempts()) : req.getReplaceBy());
			
			ispResponseList.add(ispResponse);
		}
		LOG.info("Firmware Config Response Data Added....");
	}
	
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
			ispResponse.setStatus(String.valueOf(ispData.getStatus()));
			ispResponse.setSubdivisionName(String.valueOf(ispData.getSubdevision_name()));
			ispResponse.setSubstationName(String.valueOf(ispData.getSubstation_name()));
			ispResponse.setTotalAttempts(String.valueOf(ispData.getTot_attempts()));
			ispResponse.setCmdVal(ispData.getCommand_val() != null ? ispData.getCommand_val() : "-");
			ispResponse.setVersion(ispData.getVersion());
			ispResponse.setSource(ispData.getSource() != null ? ispData.getSource() : "-");
			ispResponse.setUserId(ispData.getUser_id() != null ? ispData.getUser_id() : "-");
			ispResponse.setReason(ispData.getReason() != null ? CommonUtils.splitReason(ispData.getReason(), ispData.getTot_attempts()) : "-");
			ispResponseList.add(ispResponse);
		}
		LOG.info("Firmware Config Response Data Added....");
	}
	
	public void prepareFirmwareData(List<FirmwareData> firmwareList, List<FirmwareDataResponse> ispResponseList, MeterDataVisualizationReq req)
			throws Exception {

		LOG.info("Firmware Response Data Adding....");

		for (FirmwareData ispData : firmwareList) {
			FirmwareDataResponse ispResponse = new FirmwareDataResponse();

			ispResponse.setFileName(ispData.getFile_name() != null ? ispData.getFile_name() : req.getReplaceBy());
			ispResponse.setOwner(ispData.getOwner() != null ? ispData.getOwner() : req.getReplaceBy());
			ispResponse.setStatus(ispData.getStatus() != null ? ispData.getStatus() : req.getReplaceBy());
			ispResponse.setVersion(ispData.getVersion() != null ? ispData.getVersion() : req.getReplaceBy());
			ispResponse.setManufacturer(ispData.getManufacturer() != null ? ispData.getManufacturer() : req.getReplaceBy());
			ispResponse.setDeviceType(ispData.getDevice_type() != null ? ispData.getDevice_type() : req.getReplaceBy());
			ispResponse.setSource(ispData.getSource() != null ? ispData.getSource() : req.getReplaceBy());
			ispResponse.setUserId(ispData.getUser_id() != null ? ispData.getUser_id() : req.getReplaceBy());
			ispResponse.setUpdatedBy(ispData.getUpdated_by() != null ? ispData.getUpdated_by() : req.getReplaceBy());
			ispResponse.setImageIdentifier(ispData.getImage_identifier() != null ? ispData.getImage_identifier() : req.getReplaceBy());
			
			ispResponseList.add(ispResponse);
		}
		LOG.info("Firmware Response Data Added....");
	}


}
