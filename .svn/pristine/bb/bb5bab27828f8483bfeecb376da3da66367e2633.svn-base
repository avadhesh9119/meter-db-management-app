package com.global.meter.inventive.dashboard.utils;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.inventive.dashboard.business.model.MeterCommissioningLogs;
import com.global.meter.inventive.dashboard.model.MeterCommissioningReportResponse;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class MeterCommissioningReportCaster {
	private static final Logger LOG = LoggerFactory.getLogger(MeterCommissioningReportCaster.class);

	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	CommonUtils commonUtils;

	public void prepareAddCommissioningReportData(List<DevicesInfo> devicesList,
			MeterCommissioningLogs commissioningLogs) throws Exception {
		LOG.info("Meter Commissioning Report Caster called....");
			
		int commissioningCount = 0;
		int nonCommissioningCount = 0;
		int installationCount = 0;
		int nonInstallationCount = 0;
		for (DevicesInfo ispData : devicesList) {
			
			if (ispData.getCommissioning_datetime() != null) {
				commissioningCount++;
			}else {
				nonCommissioningCount++;
			}
			
			if(ispData.getInstallation_datetime() != null) {
				installationCount++;
			}else {
				nonInstallationCount++;
			}
			
		}
		
		commissioningLogs.setDatetime(CommonUtils.getTodayDate());
		commissioningLogs.setCommissioning_count(String.valueOf(commissioningCount));
		commissioningLogs.setNon_commissioning_count(String.valueOf(nonCommissioningCount));
		commissioningLogs.setDevices_count(String.valueOf(devicesList.size()));
		commissioningLogs.setInstallation_count(String.valueOf(installationCount));
		commissioningLogs.setNon_installation_count(String.valueOf(nonInstallationCount));
		commissioningLogs.setMdas_datetime(new Date(System.currentTimeMillis()));
		
		LOG.info("Meter Commissioning Report Caster Data Prepared.");
	}

	public void prepareGetCommissioningReportData(List<MeterCommissioningLogs> commissioningLogs,
			List<MeterCommissioningReportResponse> commissioningResponses) throws Exception {
		LOG.info("Meter Commissioning Report Caster called....");

		for (MeterCommissioningLogs ispData : commissioningLogs) {
			MeterCommissioningReportResponse ispResponse = new MeterCommissioningReportResponse();

			ispResponse.setDatetime(dateConverter.convertDateToHesString(ispData.getDatetime()));
			ispResponse.setHesTimestamp(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			ispResponse.setCommissioningCount(ispData.getCommissioning_count());
			ispResponse.setNonCommissioningCount(ispData.getNon_commissioning_count());
			ispResponse.setInstallationCount(ispData.getInstallation_count());
			ispResponse.setNonInstallationCount(ispData.getNon_installation_count());
			ispResponse.setDevicesCount(ispData.getDevices_count());
			ispResponse.setCommissioningAvg(roundPercentage(Integer.parseInt(ispData.getDevices_count()), Integer.parseInt(ispData.getCommissioning_count()))+"%");
			ispResponse.setNonCommissioningAvg(roundPercentage(Integer.parseInt(ispData.getDevices_count()), Integer.parseInt(ispData.getNon_commissioning_count()))+"%");
			ispResponse.setInstallationAvg(roundPercentage(Integer.parseInt(ispData.getDevices_count()), Integer.parseInt(ispData.getInstallation_count()))+"%");
			ispResponse.setNonInstallationAvg(roundPercentage(Integer.parseInt(ispData.getDevices_count()), Integer.parseInt(ispData.getNon_installation_count()))+"%");

			commissioningResponses.add(ispResponse);
		}

		LOG.info("Meter Commissioning Report Caster Data Prepared.");
	}
	
	private String roundPercentage(int totalNo, int count) {
		String result = "";
		result = String.valueOf(Math.round(CommonUtils.calculatePercentage(totalNo, 
				count)));
		return result;
	}
}
