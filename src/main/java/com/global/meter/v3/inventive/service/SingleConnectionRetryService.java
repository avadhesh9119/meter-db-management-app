package com.global.meter.v3.inventive.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.meter.request.model.Device;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.business.model.SingleConnectionMeterCommandLog;
import com.global.meter.v3.inventive.models.SingleConnectionScheduleCommandReq;
import com.global.meter.v3.inventive.repository.SingleConnectionMeterCommandLogRepository;

@Service
public class SingleConnectionRetryService {

	private static final Logger LOG = LoggerFactory.getLogger(SingleConnectionRetryService.class);

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	SingleConnectionMeterCommandLogRepository singleConnectionMeterCommandLogRepository;

	@Autowired
	DateConverter dateConverter;

	
	public List<SingleConnectionScheduleCommandReq> getSingleConnectionCommandListWise()
	{
		LOG.info("Fetching Single Connection Commands for retry mechanism.");

		List<SingleConnectionMeterCommandLog> deviceCommandsLog = new ArrayList<>();

		List<SingleConnectionScheduleCommandReq> devicesCommandLogList = new ArrayList<SingleConnectionScheduleCommandReq>();

		try {

			Optional<List<SingleConnectionMeterCommandLog>> deviceCommandsAdded =

					singleConnectionMeterCommandLogRepository.getDeviceBatchByStaus(

							new DateConverter().convertStringToDate(CommonUtils.getCurrentDate()), Constants.ADDED);

			Optional<List<SingleConnectionMeterCommandLog>> deviceCommandsInProgress =

					singleConnectionMeterCommandLogRepository.getDeviceBatchByStaus(

							new DateConverter().convertStringToDate(CommonUtils.getCurrentDate()),
							Constants.IN_PROGRESS);

			if (deviceCommandsAdded.isPresent() && !deviceCommandsAdded.get().isEmpty()) {

				deviceCommandsLog.addAll(deviceCommandsAdded.get());

			}

			// Check if deviceCommandsInProgress has data and add it to the combined list

			if (deviceCommandsInProgress.isPresent() && !deviceCommandsInProgress.get().isEmpty()) {

				deviceCommandsLog.addAll(deviceCommandsInProgress.get());

			}

			mergeSingleConnectionCommands(deviceCommandsLog, devicesCommandLogList);

		} catch (Exception e) {

			LOG.info("Error in fetching Single Connection Commands for retry ");

		}

		LOG.info("Single Connection Commands Commands for retry mechanism is received.");

		return devicesCommandLogList;

	}

	private void mergeSingleConnectionCommands(List<SingleConnectionMeterCommandLog> deviceCommandsLog,

			List<SingleConnectionScheduleCommandReq> devicesCommandLogList) {
		

		if (deviceCommandsLog != null && !deviceCommandsLog.isEmpty()) {

			for (SingleConnectionMeterCommandLog deviceCommandLog : deviceCommandsLog) {
		
				SingleConnectionScheduleCommandReq deviceCommand = new SingleConnectionScheduleCommandReq();

				Device device = new Device();

				deviceCommand.setBatchId(deviceCommandLog.getBatch_id());
             
				device.setPlainText(deviceCommandLog.getDevice_serial_number());
               
				deviceCommand.setDevice(device);

				devicesCommandLogList.add(deviceCommand);
				
			}

		}

	}
}
