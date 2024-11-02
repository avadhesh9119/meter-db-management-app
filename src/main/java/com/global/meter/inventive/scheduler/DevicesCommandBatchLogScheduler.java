package com.global.meter.inventive.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.global.meter.inventive.service.DevicesCommandsBatchLogsService;
import com.global.meter.utils.MetersCommandsConfiguration;

@Component
public class DevicesCommandBatchLogScheduler {
	

	private static final Logger LOG = LoggerFactory.getLogger(DevicesCommandBatchLogScheduler.class);
	
	@Autowired
	DevicesCommandsBatchLogsService devicesCommandsBatchLogsService;
	
	@Autowired
	MetersCommandsConfiguration metersCommandsConfiguration;
	
	@Scheduled(cron = "${schedule.process.deviceCommandBatchLog.cron}")
	public void processDevicesCommandBatchLog() {
		
		LOG.info("Devices Command Batch Logs Scheduler Called for devices commands");

		try {
			if(metersCommandsConfiguration.getUpdateBatchLogEnable().equalsIgnoreCase("Y")) {
				
			devicesCommandsBatchLogsService.saveDevicesCommandsBatchLogs();
			}
		} catch (Exception e) {
			LOG.error("Issue in call the scheduler : " + e.getMessage());

		}
		
	}
	
	@Scheduled(cron = "${schedule.process.deviceCommandBatchLog.cron}")
	public void processDevicesCommandLogBatchLog() {
		
		LOG.info("Devices Command Batch Logs Scheduler Called for devices commands log");

		try {
			if(metersCommandsConfiguration.getUpdateBatchLogEnable().equalsIgnoreCase("Y")) {
				
			devicesCommandsBatchLogsService.saveDevicesCommandsLogBatchLogs();
			}

		} catch (Exception e) {
			LOG.error("Issue in call the scheduler : " + e.getMessage());

		}
		
	}
}
