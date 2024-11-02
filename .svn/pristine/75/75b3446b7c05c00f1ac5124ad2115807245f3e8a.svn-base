package com.global.meter.inventive.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.inventive.task.HistoricalGroupReadCommandTask;
import com.global.meter.request.model.Device;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.DeviceCommandService;
import com.global.meter.service.FullDataCommandService;
import com.global.meter.service.PrepayDataCommandService;
import com.global.meter.utils.Constants;

@Service
public class DevicesCommandPerformService {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesCommandPerformService.class);
	private final int NTHREDS = 500;
	
	/**
	 * Used to start task to read specific datasets 
	 * @param devicesList
	 * @param commandName
	 * @param url
	 * @param balanceCommand
	 */
	
	@Autowired
	DeviceCommandService deviceCommandService;
	
	@Autowired
	FullDataCommandService fullDataCommandService;
	
	@Autowired
	PrepayDataCommandService prepayDataCommandService;
	
	@Autowired
	OnDemandCommandService onDemandCommandService;
	
	public void initiateCommand(List<String> devicesList, String commandName, String balanceCommand, String reqFrom,
			List<String> commandList, String batchId, String source, String userId
			,Map<String, String> dailyRangeDate, Map<String, String> deltaRangeDate)
	{
		int threadCount =  NTHREDS;
		if(devicesList != null && devicesList.size() > 0)
		{
			CountDownLatch countDownLatch = null;
			try {
				//List<String> devicesListAsPerTracking = SchdulerConfig.allTaskTracker.get(taskNumber);
				ExecutorService executor = Executors.newFixedThreadPool(threadCount);
				
				countDownLatch = new CountDownLatch(devicesList.size());
				int initialRange = 0;
				int finalRange = devicesList.size();
				
				for (int i = initialRange; i < finalRange; i++) {
					Device deviceObj = new Device();
					deviceObj.setPlainText(devicesList.get(i));
					
					DeviceCommand deviceCommands = new DeviceCommand();
					
					deviceCommands.setBatchId(batchId); 
					deviceCommands.setUserId(userId);
					deviceCommands.setSource(source);
					deviceCommands.setCommand(commandName); 
					deviceCommands.setDevice(deviceObj);
					deviceCommands.setRequestFrom(reqFrom);
					deviceCommands.setDailyRangeDate(dailyRangeDate);
					deviceCommands.setDeltaRangeDate(deltaRangeDate);
					deviceCommands.setObisCodeList(Constants.FullDataList.onDemandObisCodeList());
					deviceCommands.setReadWise("X"); //If we need to read billing every time then give values other than 'M'.
					
					if(ConfigCommands.FULL_CONFIG_READ.commandName.equals(commandName)) {
						deviceCommands.setPrepayObisCodeList(commandList);
						deviceCommands.setCommand(Constants.DataSet.Full_Prepay_Data);
					}
					
					HistoricalGroupReadCommandTask commandTask = new HistoricalGroupReadCommandTask(countDownLatch,
							deviceCommands, balanceCommand, deviceCommandService, fullDataCommandService, 
							prepayDataCommandService);
					executor.execute(commandTask);
				}
				// This will make the executor accept no new threads
		        // and finish all existing threads in the queue
		        executor.shutdown();
		       
			 	LOG.info("Waiting for the "+ commandName +" task to get finish...");
				countDownLatch.await();
				//LOG.info(commandName +" Task is finished... Remaining devices list -> "
				//+ SchdulerConfig.allTaskTracker.get(taskNumber));
			} catch (Exception exception) {
				LOG.error("Commands are not initiating due to:" + exception.getMessage());
			}
		}
	}

}
