package com.global.meter.v3.inventive.task;

import java.util.List;
import java.util.Map;

import com.global.meter.v3.inventive.service.SingleConnectionPerformService;

public class OnDemandSingleConnectionCommandTask extends Thread{

	private SingleConnectionPerformService singleConnectionPerformService;
	private List<String> devicesList;
	private String commandName;
	private String balanceCommand;
	private String reqFrom;
	private String batchId;
	private String source;
	private String userId;
	private Map<String, String> dailyRangeDate;
	private Map<String, String> deltaRangeDate;
	private List<String> prepayObisCodeList;
	private List<String> obisCodeList;
	private Map<String,String> configVals;
	private String extBatchId;
	
	
	public OnDemandSingleConnectionCommandTask(SingleConnectionPerformService singleConnectionPerformService, List<String> devicesList,
			String commandName,String balanceCommand, String reqFrom, List<String> obisCodeList,List<String> prepayObisCodeList,
			Map<String,String> configVals,
			String batchId, String source, String userId, Map<String, String> dailyRangeDate, Map<String, String> deltaRangeDate,
			String extBatchId) {
		super();
		this.singleConnectionPerformService = singleConnectionPerformService;
		this.devicesList = devicesList;
		this.commandName = commandName;
		this.balanceCommand = balanceCommand;
		this.reqFrom = reqFrom;
		this.obisCodeList = obisCodeList;
		this.prepayObisCodeList = prepayObisCodeList;
		this.configVals = configVals;
		this.batchId=batchId;
		this.source = source;
		this.userId = userId;
		this.dailyRangeDate = dailyRangeDate;
		this.deltaRangeDate = deltaRangeDate;
		this.extBatchId = extBatchId;
	}

	@Override
	public void run() {
		singleConnectionPerformService.initiateCommand(devicesList, commandName, balanceCommand, 
				reqFrom, obisCodeList,prepayObisCodeList,configVals, batchId, source, userId, dailyRangeDate, deltaRangeDate,extBatchId);
	}
	
}
