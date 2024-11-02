package com.global.meter.inventive.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.global.meter.inventive.service.ProcessBillingDataService;
import com.global.meter.utils.MetersCommandsConfiguration;

@Component
public class ProcessBillingDataScheduler {

	private static final Logger LOG = LoggerFactory.getLogger(ProcessBillingDataScheduler.class);

	@Autowired
	ProcessBillingDataService processBillingDataService;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Scheduled(cron = "${schedule.process.billingData.cron}")
	public void processDevicesCommandBatchLog() {

		try {
			if (meterConfiguration.getBillingdataEnable().equalsIgnoreCase("Y")) {
				
				LOG.info("Process Billing data profile Scheduler Called for devices commands");
				
				processBillingDataService.saveBillingDataProcess();
			}

		} catch (Exception e) {
			LOG.error("Issue in call the scheduler : " + e.getMessage());

		}

	}
}
