package com.global.meter.inventive.dashboard.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.global.meter.inventive.dashboard.service.ProcessSlaDataService;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;

@Component
public class ProcessSlaDataScheduler {

	private static final Logger LOG = LoggerFactory.getLogger(ProcessSlaDataScheduler.class);

	@Autowired
	ProcessSlaDataService processSlaDataService;

	@Autowired
	MetersCommandsConfiguration metersCommandsConfiguration;

	@Scheduled(cron = "${schedule.process.dailySummary.cron}")
	public void dailySummaryReportScheduler() {
		
		LOG.info("Daily Summary Report Scheduler Called.");
		
		if (Constants.ENABLE.equalsIgnoreCase(metersCommandsConfiguration.getDailySummaryEnable())) {
			try {
				processSlaDataService.addDailySummaryData();
			} catch (Exception e) {
				LOG.error("Issue in call daily summary scheduler : " + e.getMessage());

			}
		}else {
			LOG.info("Daily Summary Report Scheduler is Disable.");
		}
	}
	
	
	@Scheduled(cron = "${schedule.process.communicationSummary.cron}")
	public void addmeterCommissioningReport() {
		LOG.info("Communication Summary Report Scheduler Called.");
		if (Constants.ENABLE.equalsIgnoreCase(metersCommandsConfiguration.getCommunicationSummaryEnable())) {

			try {
				processSlaDataService.addmeterCommissioningReport();
			} catch (Exception e) {
				LOG.error("Issue in call communication summary scheduler : " + e.getMessage());

			}
		}else {
			LOG.info("Communication Summary Report Scheduler is Disable.");
		}
	}
}
