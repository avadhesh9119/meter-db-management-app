package com.global.meter.inventive.dashboard.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.inventive.dashboard.model.DeviceStatusCountRequest;
import com.global.meter.inventive.dashboard.model.DevicesStatusResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class DashboardMeterStatusCaster {
	private static final Logger LOG = LoggerFactory.getLogger(DashboardMeterStatusCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareMeterDataCount(String outputList, List<DevicesStatusResponse> isResponseList)
			throws com.fasterxml.jackson.databind.JsonMappingException, JsonProcessingException {

		LOG.info("Meter Status Count Caster Called...");

		List<DeviceStatusCountRequest> devStatusCount = new ArrayList<DeviceStatusCountRequest>();
		devStatusCount = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<DeviceStatusCountRequest>>() {
				});

		for (DeviceStatusCountRequest isData : devStatusCount) {
			DevicesStatusResponse isResponse = new DevicesStatusResponse();
			isResponse.setTotMeter(isData.getTotalMeter() != null ? isData.getTotalMeter() : "0");
			isResponse.setInActiveMeter(isData.getInActive() != null ? isData.getInActive() : "0");
			isResponse.setDown(isData.getDown() != null ? isData.getDown() : "0");
			isResponse.setFaultyMeter(isData.getFaulty() != null ? isData.getFaulty() : "0");
			isResponse.setInstalledMeter(isData.getInstalled() != null ? isData.getInstalled() : "0");
			isResponse.setTodaysInstalledMeter(isData.getTodaysInstalled() != null ? isData.getTodaysInstalled() : "0");
			isResponse.setUp(isData.getCommissioned() != null ? isData.getCommissioned() : "0");
			isResponse.setTodaysUp(isData.getTodaysCommissioned() != null ? isData.getTodaysCommissioned() : "0");

			isResponseList.add(isResponse);

		}
		LOG.info("Meter Data Count Caster Data Added Sucess.");
	}

	public void prepareTodaysInstalledMeterDatadrilldown(String outputList, List<DevicesStatusResponse> isResponseList, MeterDataVisualizationReq req)
			throws Exception {
		LOG.info("Device Meter Status Response Data Caster called....");
		List<DevicesInfo> devStatusCount = new ArrayList<DevicesInfo>();
		devStatusCount = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DevicesInfo>>() {
		});

		LOG.info("Device Meter Status Response Data Caster Adding.");

		for (DevicesInfo ispData : devStatusCount) {
			DevicesStatusResponse ispResponse = new DevicesStatusResponse();

			ispResponse.setDeviceSerialNo(ispData.getDevice_serial_number() != null ? String.valueOf(ispData.getDevice_serial_number()) : req.getReplaceBy());
			ispResponse.setManufacturer(ispData.getMeter_type() != null ? String.valueOf(ispData.getMeter_type()) : req.getReplaceBy());
			ispResponse.setOwnerName(ispData.getOwner_name() != null ? String.valueOf(ispData.getOwner_name()) : req.getReplaceBy());
			ispResponse.setMode(ispData.getMetering_mode() != null ? String.valueOf(ispData.getMetering_mode()) : req.getReplaceBy());
			ispResponse.setDeviceType(ispData.getDevice_type() != null ? String.valueOf(ispData.getDevice_type()) : req.getReplaceBy());
			ispResponse.setIpAddress(ispData.getIp_address_main() != null ? String.valueOf(ispData.getIp_address_main()) : req.getReplaceBy());
			ispResponse.setSanctionedLoad(ispData.getSanction_load() != null ? String.valueOf(ispData.getSanction_load()) : req.getReplaceBy());
			ispResponse.setCommissioningStatus(ispData.getCommissioning_status() != null ? String.valueOf(ispData.getCommissioning_status()) : req.getReplaceBy());
			
			isResponseList.add(ispResponse);
		}
		LOG.info("Device Meter Status Response Data Caster Added.");
	}

}
