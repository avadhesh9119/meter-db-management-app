package com.global.meter.v3.inventive.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.global.meter.MeterDBAppStarter;
import com.global.meter.v3.inventive.service.PushDataToExternalAddressService;

public class PushDataToExtenalAddTask implements Runnable{

	private static final Logger LOG = LoggerFactory.getLogger(PushDataToExtenalAddTask.class);

	private String url;
	private Object logs;
	private String commandName;
	private String meterNumber;
	
	public PushDataToExtenalAddTask(Object logs, String url, String commandName, String meterNumber) {
		this.url = url;
		this.logs = logs;
		this.commandName = commandName;
		this.meterNumber = meterNumber;
	}
	
	@Override
	public void run() {
		LOG.info(this.meterNumber + ": Push "+ this.commandName +" Data To External Address Started.");
		PushDataToExternalAddressService pushEventsSinglePhaseService = MeterDBAppStarter.mContext.getBean(PushDataToExternalAddressService.class);
		pushEventsSinglePhaseService.pushDataToExternalURL(logs, url);
		LOG.info(this.meterNumber + ": Push "+ this.commandName +" Data To External Address Send Completed.");
	}
}
