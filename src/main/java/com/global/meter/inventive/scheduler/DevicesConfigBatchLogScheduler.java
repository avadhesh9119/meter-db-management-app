package com.global.meter.inventive.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.global.meter.inventive.service.DevicesConfigBatchLogsService;
import com.global.meter.utils.MetersCommandsConfiguration;

@Component
public class DevicesConfigBatchLogScheduler {
	

	private static final Logger LOG = LoggerFactory.getLogger(DevicesConfigBatchLogScheduler.class);
	
	@Autowired
	DevicesConfigBatchLogsService devicesConfigBatchLogsService;
	
	@Autowired
	MetersCommandsConfiguration metersCommandsConfiguration;
	
	@Scheduled(cron = "${schedule.process.deviceConfigBatchLog.cron}")
	public void processDevicesCommandBatchLog() {
		
		LOG.info("Devices Config Batch Logs Scheduler Called");

		try {
			if(metersCommandsConfiguration.getUpdateBatchLogEnable().equalsIgnoreCase("Y")) {

			devicesConfigBatchLogsService.saveDevicesConfigBatchLogs();
			}
		} catch (Exception e) {
			LOG.error("Issue in call config batch logs scheduler : " + e.getMessage());

		}
		
	}
	
	@Scheduled(cron = "${schedule.process.deviceConfigReadBatchLog.cron}")
	public void processDevicesCommandReadBatchLog() {
		
		LOG.info("Devices Config Read Batch Logs Scheduler Called");
		
		try {
			if(metersCommandsConfiguration.getUpdateBatchLogEnable().equalsIgnoreCase("Y")) {

			devicesConfigBatchLogsService.saveDevicesConfigReadBatchLogs();
			}
		} catch (Exception e) {
			LOG.error("Issue in call config read batch logs scheduler : " + e.getMessage());
			
		}
		
	}
}
