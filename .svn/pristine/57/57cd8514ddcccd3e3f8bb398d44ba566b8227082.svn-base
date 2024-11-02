package com.global.meter.inventive.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.global.meter.inventive.controller.ApplicationPropertyController;

@Component
public class AppPropertyReadScheduler {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppPropertyReadScheduler.class);
	
	@Autowired
	ApplicationPropertyController propertyController;
	
	@Scheduled(cron = "${meter.applicationProperties.read.update}")
	public void restartApplicationPropertyScheduler() {
		
		LOG.info("Application Property Read Scheduler Called.");

		try {
			
			propertyController.disableUpdateAppProperty();		
			
		} catch (Exception e) {
			LOG.error("Issue in call the scheduler : " + e.getMessage());

		}
		
	}
}
