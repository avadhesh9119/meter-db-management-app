package com.global.meter.v3.inventive.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.inventive.service.OnDemandCommandService;
import com.global.meter.request.model.Device;
import com.global.meter.service.DeviceCommandService;
import com.global.meter.service.FullDataCommandService;
import com.global.meter.service.PrepayDataCommandService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.common.enums.ObisProfileName;
import com.global.meter.v3.common.enums.OnDemandCommands;
import com.global.meter.v3.inventive.business.model.DevicesCommandLog;
import com.global.meter.v3.inventive.business.model.ManufacturerSpecificObis;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogs;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;
import com.global.meter.v3.inventive.repository.ManufacturerSpecificObisRepository;
import com.global.meter.v3.inventive.task.SingleConnectionReadCommandTask;

@Service
public class SingleConnectionPerformService {

	private static final Logger LOG = LoggerFactory.getLogger(SingleConnectionPerformService.class);
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
	
	@Autowired 
	SingleConnectionCommandService singleConnectionCommandService;
	
	@Autowired
	ManufacturerSpecificObisRepository manufacturerSpecificObisRepository;
	
	@Autowired
	CommonUtils commonUtils;
	
	@Autowired
	MetersCommandsConfiguration meterCommands;
	
	public void initiateCommand(List<String> devicesList, String commandName, String balanceCommand, String reqFrom,
			List<String> obisCodeList,List<String> prepayObisCodeList,
			Map<String,String> configVals, String batchId, String source, String userId
			,Map<String, String> dailyRangeDate, Map<String, String> deltaRangeDate, String extBatchId)
	{
		int threadCount =  NTHREDS;
		if(devicesList != null && devicesList.size() > 0)
		{
			CountDownLatch countDownLatch = null;
			try {
			
				ExecutorService executor = Executors.newFixedThreadPool(threadCount);
				
				countDownLatch = new CountDownLatch(devicesList.size());
				int initialRange = 0;
				int finalRange = devicesList.size();
				for (int i = initialRange; i < finalRange; i++) {
					Device deviceObj = new Device();
					deviceObj.setPlainText(devicesList.get(i));
					
					SingleConnectionCommandReq commandsReq = new SingleConnectionCommandReq();
					
					commandsReq.setBatchId(batchId); 
					commandsReq.setUserId(userId);
					commandsReq.setSource(source);
					commandsReq.setCommand(commandName); 
					commandsReq.setDevice(deviceObj);
					commandsReq.setReqFrom(reqFrom);
					commandsReq.setDailyRangeDate(dailyRangeDate);
					commandsReq.setDeltaRangeDate(deltaRangeDate);
					commandsReq.setPrepayObisCodeList(prepayObisCodeList);
					commandsReq.setConfigVals(configVals);
					commandsReq.setObisCodeList(obisCodeList);
					commandsReq.setExtBatchId(extBatchId);
					commandsReq.setReadWise("X"); //If we need to read billing every time then give values other than 'M'.
					
				
					SingleConnectionReadCommandTask commandTask = new SingleConnectionReadCommandTask(countDownLatch,
							 balanceCommand,commandsReq, singleConnectionCommandService);
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
	
	public boolean checkValidForUpdateLog(DevicesCommandLog cmdLog) {
		boolean isValid = false;
		if (Constants.IN_PROGRESS.equalsIgnoreCase(cmdLog.getStatus())
				|| Constants.ADDED.equalsIgnoreCase(cmdLog.getStatus())) {
			isValid = true;
		}
//		if (!Constants.SUCCESS.equalsIgnoreCase(cmdLog.getStatus()) 
//			&& !Constants.FAILURE.equalsIgnoreCase(cmdLog.getStatus())
//			&& !Constants.FAILURE_NA.equalsIgnoreCase(cmdLog.getStatus())
//			&& !Constants.DISCARD.equalsIgnoreCase(cmdLog.getStatus()) 
//			&& !Constants.CANCELLED.equalsIgnoreCase(cmdLog.getStatus()) 
//			&& !Constants.BILL_ALREADY_EXIST_CUURENT_MONTH.equalsIgnoreCase(cmdLog.getStatus())) {
//			isValid = true;
//		}
		return isValid;
	}

	public void setExtraObisCode(SingleConnectionCommandLogs mResponse, DevicesInfo deviceInfo) throws Exception {
		if (mResponse.getMeterResponse().getObisCode() != null
				&& mResponse.getMeterResponse().getObisCode().size() > 0) {
			HashSet<String> obisList = new HashSet<String>(mResponse.getMeterResponse().getObisCode());
			HashSet<String> extraObisSet = new HashSet<String>(mResponse.getMeterResponse().getObisCode());
			ManufacturerSpecificObis mnfObisList = new ManufacturerSpecificObis();
			if (deviceInfo.getMeter_type() != null && deviceInfo.getDevice_type() != null
					&& mResponse.getCommandName() != null && deviceInfo.getVersion() != null) {
				Optional<ManufacturerSpecificObis> mnfSpeObis = manufacturerSpecificObisRepository.findData(
						deviceInfo.getMeter_type(), deviceInfo.getDevice_type(), mResponse.getCommandName(),
						deviceInfo.getVersion());
				Properties properties = commonUtils.getPropertiesFile(meterCommands.getObisCodeList());
				if(properties != null) 
				{
					String str = (String) properties.get(ObisProfileName.getCommandId(
							OnDemandCommands.NAME_PLATES.commandName.equals(mResponse.getCommandName())
							? mResponse.getCommandName()
							: mResponse.getCommandName() + "_" + deviceInfo.getDevice_type()));
					
					if(str != null && str.contains(",")){
						String[] strList = str.split(",");
						HashSet<String> impObisList = new HashSet<>();
						for (String s : strList) {
							impObisList.add(s);
						}
						if (!impObisList.containsAll(obisList)) {
							if (mnfSpeObis.isPresent()) {
								mnfObisList = mnfSpeObis.get();
							}
							mnfObisList.setManufacturer(mnfObisList.getManufacturer() != null ? mnfObisList.getManufacturer()
									: deviceInfo.getMeter_type());
							mnfObisList.setDevice_type(mnfObisList.getDevice_type() != null ? mnfObisList.getDevice_type()
									: deviceInfo.getDevice_type());
							mnfObisList.setVersion(
									mnfObisList.getVersion() != null ? mnfObisList.getVersion() : deviceInfo.getVersion());
							mnfObisList.setProfile_type(mnfObisList.getProfile_type() != null ? mnfObisList.getProfile_type()
									: mResponse.getCommandName());
							mnfObisList.setMdas_datetime(new Date(System.currentTimeMillis()));

							extraObisSet.removeAll(impObisList);
							mnfObisList.setObis_code(obisList);
							mnfObisList.setExtra_obis_code(extraObisSet);
							mnfObisList.setOccurence_datetime(new Date(System.currentTimeMillis()));
							mnfObisList.setStatus(Constants.Inactive);
							manufacturerSpecificObisRepository.save(mnfObisList);
						}
					}	
				}
				
			}
		}
	}
}
