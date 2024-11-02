package com.global.meter.inventive.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.global.meter.inventive.service.MeterCommCountService;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.DateConverter;

@Component
public class MeterCommCountScheduler {
	
	private static final Logger LOG = LoggerFactory.getLogger(MeterCommCountScheduler.class);

	@Autowired
	DevicesInfoService devicesInfoService;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;


	@Autowired
	MeterCommCountService meterCommCountService;
	
	@Autowired
	DateConverter dateConverter;
	
	@Scheduled(cron = "${schedule.process.metercommcount.cron}")
	public void processMeterCommCount() {
		
		LOG.info("Meter Comm Count Process Scheduler Called");

		try {
			meterCommCountService.getMeterCommCount();
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());

		}
		
	}
}
