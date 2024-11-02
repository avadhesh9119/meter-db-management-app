package com.global.meter.inventive.task;

import java.util.List;
import java.util.Map;

import com.global.meter.inventive.service.DevicesCommandPerformService;

public class OnDemandGroupCommandTask extends Thread{

	private DevicesCommandPerformService devicesCommandPerformService;
	private List<String> devicesList;
	private String commandName;
	private String balanceCommand;
	private String reqFrom;
	private List<String> commandList;
	private String batchId;
	private String source;
	private String userId;
	private Map<String, String> dailyRangeDate;
	private Map<String, String> deltaRangeDate;
	
	
	public OnDemandGroupCommandTask(DevicesCommandPerformService devicesCommandPerformService, List<String> devicesList,
			String commandName, String balanceCommand, String reqFrom, List<String> commandList,String batchId
			, String source, String userId, Map<String, String> dailyRangeDate, Map<String, String> deltaRangeDate) {
		super();
		this.devicesCommandPerformService = devicesCommandPerformService;
		this.devicesList = devicesList;
		this.commandName = commandName;
		this.balanceCommand = balanceCommand;
		this.reqFrom = reqFrom;
		this.commandList = commandList;
		this.batchId=batchId;
		this.source = source;
		this.userId = userId;
		this.dailyRangeDate = dailyRangeDate;
		this.deltaRangeDate = deltaRangeDate;
	}

	@Override
	public void run() {
		devicesCommandPerformService.initiateCommand(devicesList, commandName, balanceCommand, 
				reqFrom, commandList, batchId, source, userId, dailyRangeDate, deltaRangeDate);
	}
	
}
