package com.global.meter.inventive.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.global.meter.MeterDBAppStarter;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;

@Component
public class ReloadPropertyScheduler {

	private static final Logger LOG = LoggerFactory.getLogger(ReloadPropertyScheduler.class);

	@Autowired
	MeterDBAppStarter meterDBAppStarter;

	@Autowired
	MetersCommandsConfiguration metersCommandsConfiguration;

	@Scheduled(cron = "${schedule.process.reloadProperty.cron}")
	public void reloadtPropertyScheduler() {

		LOG.info("Reload Property Scheduler Called.");

		try {
			if (metersCommandsConfiguration.getReloadPropertyEnable().equalsIgnoreCase(Constants.ENABLE)) {
				meterDBAppStarter.reload();
			}

		} catch (Exception e) {
			LOG.error("Issue in call the scheduler : " + e.getMessage());

		}

	}
}
