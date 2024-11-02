package com.global.meter.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DeviceCommandLogs;
import com.global.meter.data.repository.DeviceCommandPrepayLogRepository;
import com.global.meter.request.model.Device;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.request.model.DevicesCommandReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DevicesCommandsPrepayLogService {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesCommandsPrepayLogService.class);
	
	private String devicesCmdLogs = "devices_commands_prepay_logs";

	@Autowired
	DeviceCommandPrepayLogRepository deviceCommandPrepayLogRepository; 
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	private CassandraOperations cassandraTemplate;
	
	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate ;
	
	/**
	 * Get Logs from Devices Command Logs 
	 * @param commandName
	 * @return
	 */
	public List<DeviceCommand> getFullDataDeviceCommandLog(String commandName)
	{
		LOG.info("Fetching Full Data Devices Commands for retry mechanism.");
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT device_serial_number , tracking_id")
			 .append(" FROM "+meterConfiguration.getKeyspace()+"."+devicesCmdLogs+" WHERE status='")
			 .append(Constants.IN_PROGRESS).append("'");
		if(!StringUtils.isEmpty(commandName)) {
			query.append(" and command_name = '").append(commandName).append("'");
		}
		query.append(" and mdas_datetime >= cast('").append(CommonUtils.getCurrentDate())
				.append("' as timestamp) ").append(" order by tracking_id desc LIMIT 40000");

		List<DeviceCommand> devicesCommandLogList = new ArrayList<DeviceCommand>();
		try {
			String deviceCommandsL = CommonUtils.getMapper().writeValueAsString(
					prestoJdbcTemplate.queryForList(query.toString()));
			List<DevicesCommandReq> deviceCommandsLog = (List<DevicesCommandReq>) CommonUtils.getMapper()
					.readValue(deviceCommandsL, new TypeReference<List<DevicesCommandReq>>(){});
			
			mergeDeviceCommands(deviceCommandsLog, devicesCommandLogList);
		} catch (JsonProcessingException e) {
			LOG.info("Error in fetching Devices Commands for retry ");
		}
		LOG.info("Devices Commands for retry mechanism is received.");
		return devicesCommandLogList;
	}
	
	/**
	 * Get Logs from Devices Command Logs 
	 * @param commandName
	 * @return
	 */
	public Map<String, CopyOnWriteArrayList<DeviceCommand>> getCellBasedFullDataDeviceCommandLog(String commandName)
	{
		LOG.info("Fetching Full Data Devices Commands for retry mechanism.");
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT device_serial_number , tracking_id, cell_id")
			 .append(" FROM "+meterConfiguration.getKeyspace()+"."+devicesCmdLogs+" WHERE status='")
			 .append(Constants.IN_PROGRESS).append("'");
		query.append(" and mdas_datetime >= cast('").append(CommonUtils.getCurrentDate())
				.append("' as timestamp) ").append(" order by tracking_id desc LIMIT 20000");

		List<DeviceCommand> devicesCommandLogList = new ArrayList<DeviceCommand>();
		try {
			String deviceCommandsL = CommonUtils.getMapper().writeValueAsString(
					prestoJdbcTemplate.queryForList(query.toString()));
			List<DevicesCommandReq> deviceCommandsLog = (List<DevicesCommandReq>) CommonUtils.getMapper()
					.readValue(deviceCommandsL, new TypeReference<List<DevicesCommandReq>>(){});
			
			mergeDeviceCommands(deviceCommandsLog, devicesCommandLogList);
		} catch (JsonProcessingException e) {
			LOG.info("Error in fetching Devices Commands for retry ");
		}
		LOG.info("Devices Commands for retry mechanism is received.");
		
		Map<String, CopyOnWriteArrayList<DeviceCommand>> cellBasedDevices = new LinkedHashMap<String, CopyOnWriteArrayList<DeviceCommand>>();
		if(devicesCommandLogList != null && devicesCommandLogList.size() > 0) {
			for (DeviceCommand deviceCommand : devicesCommandLogList) {
				
				String cellId = deviceCommand.getCellId() != null ? deviceCommand.getCellId() : Constants.nullKey;
				if(!cellBasedDevices.containsKey(cellId)) {
					cellBasedDevices.put(cellId, new CopyOnWriteArrayList<DeviceCommand>());
				}
				List<DeviceCommand> devList = cellBasedDevices.get(cellId);
				devList.add(deviceCommand);
			}
		}
		return cellBasedDevices;
	}
	
	private void mergeDeviceCommands(List<DevicesCommandReq> deviceCommandsLog,
			List<DeviceCommand> devicesCommandLogList){
		if(devicesCommandLogList == null)
		{
			devicesCommandLogList = new ArrayList<DeviceCommand>();
		}
		
		if(deviceCommandsLog != null && deviceCommandsLog.size() > 0) {
			for (DevicesCommandReq deviceCommandLog : deviceCommandsLog) {
				DeviceCommand deviceCommand = new DeviceCommand();
				
				Device device = new Device();
				device.setPlainText(deviceCommandLog.getDevice_serial_number());
				deviceCommand.setDevice(device);
				deviceCommand.setCommand(deviceCommandLog.getCommand_name() != null ? deviceCommandLog.getCommand_name()
						: "");
				deviceCommand.setTrackingId(deviceCommandLog.getTracking_id());
				deviceCommand.setCellId(deviceCommandLog.getCell_id());
				devicesCommandLogList.add(deviceCommand);
			} 
		}
		
	}
	
	//get Devices Command Logs for Full Data find by trackingId
	public DeviceCommandLogs findCmdLogsByTrackingId(String device, String trackingId)
	{
		Where query = QueryBuilder.select().from(devicesCmdLogs).where(
				QueryBuilder.eq("device_serial_number", device))
				.and(QueryBuilder.eq("tracking_id", trackingId));
		
		List<DeviceCommandLogs> devicesCommandLogs =  cassandraTemplate.select(query, DeviceCommandLogs.class);
		return devicesCommandLogs.get(0);
	}
		
	public DeviceCommandPrepayLogRepository getDeviceCommandLogRepository() {
		return deviceCommandPrepayLogRepository;
	}
}
