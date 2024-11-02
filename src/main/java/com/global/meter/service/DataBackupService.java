package com.global.meter.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DeviceCommandBackupLog;
import com.global.meter.business.model.DeviceCommandLog;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.data.repository.DeviceCommandBackupLogRepository;
import com.global.meter.data.repository.DeviceCommandLogRepository;
import com.global.meter.request.model.DataBackup;
import com.global.meter.utils.CommonUtils;

@Service
public class DataBackupService {

	private String devicesConfigTable = "devices_commands";
	
	private static final Logger LOG = LoggerFactory.getLogger(DataBackupService.class);

	@Autowired
	DeviceCommandLogRepository deviceCommandLogRepository;

	@Autowired
	DeviceCommandBackupLogRepository deviceCommandBackupLogRepository;

	@Autowired
	private CassandraOperations cassandraTemplate;

	// get Devices Commands on mdasbased
	public List<DeviceCommandLog> findByMdasBased(DataBackup deviceCommand) {
		
		List<DeviceCommandLog> devicesCommandLog = null;
		String query = null;
		try {
			query = "SELECT * FROM "+devicesConfigTable+" WHERE mdas_datetime<='" + deviceCommand.getDatetime()
					+ "' AND command_name='" + deviceCommand.getDatasetName() + "' limit 5000 ALLOW FILTERING";
			devicesCommandLog = cassandraTemplate.select(query, DeviceCommandLog.class);
		} catch (Exception e) {
			LOG.error("Issue in fetching data for backup...");
			throw e;
		}
		LOG.info("Data Fetch to take backup..");
		return devicesCommandLog;
	}

	public boolean storeBackup(DataBackup deviceCommand, MeterResponse response) throws Exception {
		boolean FLAG = false;
		try {
			List<DeviceCommandLog> devicesCommandLog = findByMdasBased(deviceCommand);
			
			if (devicesCommandLog.size() > 0) {
				String cmdLogs = CommonUtils.getMapper().writeValueAsString(devicesCommandLog);
				List<DeviceCommandBackupLog> devicesCommandBackupLog = CommonUtils.getMapper().readValue(cmdLogs,
						new TypeReference<List<DeviceCommandBackupLog>>() {
						});
				deviceCommandBackupLogRepository.saveAll(devicesCommandBackupLog);
				deviceCommandLogRepository.deleteAll(devicesCommandLog);
				LOG.info("Data Backup is done for "+ deviceCommand.getDatetime());
				response.setMessage("Data Backup is done");
				response.setStatus(200);
			}
			else {
				response.setMessage("Data not found for backup...");
				response.setStatus(400);
			}
		} catch (Exception e) {
			LOG.error("Issue in taking backup of data ");
			throw e;
		}
		return FLAG;
	}
}
