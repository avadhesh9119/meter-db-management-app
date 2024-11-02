package com.global.meter.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.global.meter.business.model.DeviceCommandLog;
import com.global.meter.business.model.DeviceCommandLogs;
import com.global.meter.business.model.DeviceCommandLogsOnePerDay;
import com.global.meter.business.model.DeviceConfigLogs;
import com.global.meter.data.repository.DeviceCommandLogRepository;
import com.global.meter.request.model.Device;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.request.model.DevicesCommandReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DevicesCommandsLogService {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesCommandsLogService.class);
	
	private String devicesCommands = "devices_commands";
	private String devicesCmdLogs = "devices_commands_logs";
	private String devicesCmdPrepayLogs = "devices_commands_prepay_logs";
	private String devicesConfigLogs = "devices_config_logs";
	private String devicesCmdLogsOnePerDay = "devices_commands_logs_one_per_day";
	
	@Autowired
	DeviceCommandLogRepository deviceCommandLogRepository; 
	
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
	
	/**
	 * Get Logs from Devices Command Logs 
	 * @param commandName
	 * @return
	 */
	public Map<String, CopyOnWriteArrayList<DeviceCommand>> getCellBasedFullPrepayDataDeviceCommandLog(String commandName)
	{
		LOG.info("Fetching Full Prepay Data Devices Commands for retry mechanism.");
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT device_serial_number , tracking_id, cell_id")
			 .append(" FROM "+meterConfiguration.getKeyspace()+"."+devicesCmdPrepayLogs+" WHERE status='")
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
	
	/**
	 * Get Logs from Devices Command Logs 
	 * @param commandName
	 * @return
	 */
	public Map<String, CopyOnWriteArrayList<DeviceCommand>> getCellBasedFullDataDeviceCommandLogPerDay(String commandName)
	{
		LOG.info("Fetching Full Data Devices Commands for retry mechanism.");
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT device_serial_number , tracking_id, cell_id")
			 .append(" FROM "+meterConfiguration.getKeyspace()+"."+devicesCmdLogsOnePerDay+" WHERE status='")
			 .append(Constants.IN_PROGRESS).append("'");
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
	
	public Map<String, CopyOnWriteArrayList<DeviceCommand>> getCellBasedFullConfigLogs()
	{
		LOG.info("Fetching Full Data Devices Config Logs for retry mechanism.");
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT device_serial_number , tracking_id, cell_id")
			 .append(" FROM "+meterConfiguration.getKeyspace()+"."+devicesConfigLogs+" WHERE status in (")
			 .append("'").append(Constants.IN_PROGRESS).append("','").append(Constants.ADDED).append("') ");
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
	
	/**
	 * Used to get the Command 
	 * @param commandName
	 * @return
	 */
	public List<DeviceCommand> getDeviceCommandLog(String commandName)
	{
		LOG.info("Fetching Devices Commands for retry mechanism.");
		StringBuilder query = new StringBuilder();
		query.append("SELECT device_serial_number , command_name, tracking_id")
			 .append(" FROM "+meterConfiguration.getKeyspace()+"."+devicesCommands+" WHERE status='")
			 .append(Constants.IN_PROGRESS).append("'");
		if(!StringUtils.isEmpty(commandName)) {
			query.append(" and command_name = '").append(commandName).append("'");
		}
		query.append(" and mdas_datetime >= cast('").append(CommonUtils.getCurrentDate())
				.append("' as timestamp) ").append(" order by tracking_id desc LIMIT 15000");

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
	 * Used to get the Command 
	 * @param commandName
	 * @return
	 */
	public List<DeviceCommandLog> getDeviceCommandLogCurrentDayWise(DeviceCommand deviceCommand)
	{
		List<DeviceCommandLog> deviceCommandsLog =  null;
		LOG.info("Fetching Devices Commands for retry mechanism.");
		StringBuilder query = new StringBuilder();
		query.append("SELECT * ")
			 .append(" FROM "+meterConfiguration.getKeyspace()+"."+devicesCommands+" WHERE status='")
			 .append(Constants.IN_PROGRESS).append("'");
		if(!StringUtils.isEmpty(deviceCommand.getCommand())) {
			query.append(" and command_name = '").append(deviceCommand.getCommand()).append("'");
		}
		query.append(" and device_serial_number = '").append(deviceCommand.getDevice().getPlainText()).append("'");
		query.append(" and mdas_datetime >= cast('").append(CommonUtils.getCurrentDate())
				.append("' as timestamp) ");

		try {
			String deviceCommandsL = CommonUtils.getMapper().writeValueAsString(
					prestoJdbcTemplate.queryForList(query.toString()));
			deviceCommandsLog = (List<DeviceCommandLog>) CommonUtils.getMapper()
					.readValue(deviceCommandsL, new TypeReference<List<DeviceCommandLog>>(){});
			
		} catch (JsonProcessingException e) {
			LOG.info("Error in fetching Devices Commands for retry ");
		}
		LOG.info("Devices Commands for retry mechanism is received.");
		return deviceCommandsLog;
	}
	
	/**
	 * Used to get the Command When Data Contains Date Wise Range
	 * @param commandName
	 * @return
	 */
	public List<DeviceCommandLog> getDeviceCommandLogRangeWise(DeviceCommand deviceCommand)
	{
		List<DeviceCommandLog> deviceCommandsLog =  null;
		LOG.info("Fetching Devices Commands for retry mechanism.");
		StringBuilder query = new StringBuilder();
		query.append("SELECT * ")
			 .append(" FROM "+meterConfiguration.getKeyspace()+"."+devicesCommands+" WHERE status='")
			 .append(Constants.IN_PROGRESS).append("'");
		if(!StringUtils.isEmpty(deviceCommand.getCommand())) {
			query.append(" and command_name = '").append(deviceCommand.getCommand()).append("'");
		}
		query.append(" and device_serial_number = '").append(deviceCommand.getDevice().getPlainText()).append("'");
		query.append(" and mdas_datetime >= cast('").append(CommonUtils.getCurrentDate())
				.append("' as timestamp) ");

		try {
			String deviceCommandsL = CommonUtils.getMapper().writeValueAsString(
					prestoJdbcTemplate.queryForList(query.toString()));
			JsonNode devicesCommandList = CommonUtils.getMapper().readTree(deviceCommandsL);
			deviceCommandsLog = new ArrayList<>();
			for (JsonNode logs : devicesCommandList) {
				DeviceCommandLog commandLog = new DeviceCommandLog();
				commandLog.setBatch_id(logs.get(Constants.DeviceCommandLogs.BATCH_ID) != null 
						? logs.get(Constants.DeviceCommandLogs.BATCH_ID).asText() : null);
				commandLog.setCommand_completion_datetime(logs.get(Constants.DeviceCommandLogs.COMMAND_COMPLETION_DATETIME) != null ?
					dateConverter.convertStringDayToDate(logs.get(Constants.DeviceCommandLogs.COMMAND_COMPLETION_DATETIME).asText()) : null);
				commandLog.setCommand_name(logs.get(Constants.DeviceCommandLogs.COMMAND_NAME) != null 
						? logs.get(Constants.DeviceCommandLogs.COMMAND_NAME).asText() : null);
				if (logs.get(Constants.DeviceCommandLogs.DATE_WISE_RANGE) != null) {
					JsonNode dateRange = CommonUtils.getMapper().readTree(logs.get(Constants.DeviceCommandLogs.DATE_WISE_RANGE).asText());
					if (dateRange instanceof JsonNode) {
						Map<String, String> map = new HashMap<String, String>() ;
						if (dateRange.get(Constants.DateRange.STARTDATE) != null) {
							map.put(Constants.DateRange.STARTDATE, dateRange.get(Constants.DateRange.STARTDATE).asText());
						}
						if (dateRange.get(Constants.DateRange.ENDDATE) != null) {
							map.put(Constants.DateRange.ENDDATE, dateRange.get(Constants.DateRange.ENDDATE).asText());
						}
						commandLog.setDatewise_range(map);
					}
					
				}
				commandLog.setDcu_serial_number(logs.get(Constants.DeviceCommandLogs.DCU_SERIAL_NUMBER) != null ?
						logs.get(Constants.DeviceCommandLogs.DCU_SERIAL_NUMBER).asText() : null);
				commandLog.setDevice_serial_number(logs.get(Constants.DeviceCommandLogs.DEVICE_SERIAL_NUMBER) != null ?
						logs.get(Constants.DeviceCommandLogs.DEVICE_SERIAL_NUMBER).asText() : null);
				commandLog.setDt_name(logs.get(Constants.DeviceCommandLogs.DT_NAME) != null ?
						logs.get(Constants.DeviceCommandLogs.DT_NAME).asText() : null);
				commandLog.setFeeder_name(logs.get(Constants.DeviceCommandLogs.FEEDER_NAME) != null ?
						logs.get(Constants.DeviceCommandLogs.FEEDER_NAME).asText() : null);
				commandLog.setMdas_datetime(logs.get(Constants.DeviceCommandLogs.MDAS_DATETIME) != null ?
					dateConverter.convertStringDayToDate(logs.get(Constants.DeviceCommandLogs.MDAS_DATETIME).asText()): null);
				commandLog.setOwner_name(logs.get(Constants.DeviceCommandLogs.OWNER_NAME) != null ?
						logs.get(Constants.DeviceCommandLogs.OWNER_NAME).asText() : null);
				commandLog.setReason(logs.get(Constants.DeviceCommandLogs.REASON) != null ?
						logs.get(Constants.DeviceCommandLogs.REASON).asText() : null);
				commandLog.setSource(logs.get(Constants.DeviceCommandLogs.SOURCE) != null ?
						logs.get(Constants.DeviceCommandLogs.SOURCE).asText() : null);
				commandLog.setStatus(logs.get(Constants.DeviceCommandLogs.STATUS) != null ?
						logs.get(Constants.DeviceCommandLogs.STATUS).asText() : null);
				commandLog.setSubdevision_name(logs.get(Constants.DeviceCommandLogs.SUBDEVISION_NAME) != null ?
						logs.get(Constants.DeviceCommandLogs.SUBDEVISION_NAME).asText() : null);
				commandLog.setSubstation_name(logs.get(Constants.DeviceCommandLogs.SUBSTATION_NAME) != null ?
						logs.get(Constants.DeviceCommandLogs.SUBSTATION_NAME).asText() : null);
				commandLog.setTot_attempts(logs.get(Constants.DeviceCommandLogs.TOT_ATTEMPTS) != null ?
						logs.get(Constants.DeviceCommandLogs.TOT_ATTEMPTS).asInt() : null);
				commandLog.setTracking_id(logs.get(Constants.DeviceCommandLogs.TRACKING_ID) != null ?
						logs.get(Constants.DeviceCommandLogs.TRACKING_ID).asText() : null);
				commandLog.setUser_id(logs.get(Constants.DeviceCommandLogs.USER_ID) != null ?
						logs.get(Constants.DeviceCommandLogs.USER_ID).asText() : null);
				
				deviceCommandsLog.add(commandLog);
			}
		} catch (JsonProcessingException e) {
			LOG.info("Error in fetching Devices Commands for retry ");
		}catch (Exception e) {
			// TODO: handle exception
			LOG.info("Error in fetching Devices Commands for retry ");
		}
		LOG.info("Devices Commands for retry mechanism is received.");
		return deviceCommandsLog;
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
	
	
	//get Devices Command find by trackingId
	public DeviceCommandLog findByTrackingId(String device, String commandName,String trackingId)
	{
		Where query = QueryBuilder.select().from(devicesCommands).where(
				QueryBuilder.eq("device_serial_number", device))
				.and(QueryBuilder.eq("command_name", commandName))
				.and(QueryBuilder.eq("tracking_id", trackingId));
		
		List<DeviceCommandLog> devicesCommandLog =  cassandraTemplate.select(query, DeviceCommandLog.class);
		return (devicesCommandLog != null && devicesCommandLog.size() > 0) ? devicesCommandLog.get(0) : null;
	}
	
	//get Devices Command Logs for Full Data find by trackingId
	public DeviceCommandLogs findCmdLogsByTrackingId(String device, String trackingId)
	{
		Where query = QueryBuilder.select().from(devicesCmdLogs).where(
				QueryBuilder.eq("device_serial_number", device))
				.and(QueryBuilder.eq("tracking_id", trackingId));
		
		List<DeviceCommandLogs> devicesCommandLogs =  cassandraTemplate.select(query, DeviceCommandLogs.class);
		return devicesCommandLogs != null && devicesCommandLogs.size() > 0 ? devicesCommandLogs.get(0) : null;
	}
		
	
	//get Devices Command Logs for Full Data Per Daywise find by trackingId
	public DeviceCommandLogsOnePerDay findFullDataCmdLogsPerDayByTrackingId(String device, String trackingId)
	{
		Select query = QueryBuilder.select().from(devicesCmdLogsOnePerDay).where(
				QueryBuilder.eq("device_serial_number", device))
				.and(QueryBuilder.eq("tracking_id", trackingId)).allowFiltering();
		
		List<DeviceCommandLogsOnePerDay> devicesCommandLogsOnePerDay =  cassandraTemplate.select(query, DeviceCommandLogsOnePerDay.class);
		return devicesCommandLogsOnePerDay.get(0);
	}
	
	//get Devices Command Logs for Full Data Per Daywise find by trackingId
	public DeviceCommandLogsOnePerDay findFullDataCmdLogsPerDayByDate(String device, String date) throws ParseException
	{
		Where query = QueryBuilder.select().from(devicesCmdLogsOnePerDay).where(
				QueryBuilder.eq("device_serial_number", device))
				.and(QueryBuilder.eq("command_datetime", dateConverter.convertStringToDate(date)));
		
		
		List<DeviceCommandLogsOnePerDay> devicesCommandLogsOnePerDay =  cassandraTemplate.select(query, DeviceCommandLogsOnePerDay.class);
		return devicesCommandLogsOnePerDay.get(0);
	}
		
		
	//get Devices Config Logs for Full Data find by trackingId
	public DeviceConfigLogs findConfigLogsByTrackingId(String device, String trackingId)
	{
		Where query = QueryBuilder.select().from(devicesConfigLogs).where(
				QueryBuilder.eq("device_serial_number", device))
				.and(QueryBuilder.eq("tracking_id", trackingId));
		
		List<DeviceConfigLogs> devicesConfigLogs =  cassandraTemplate.select(query, DeviceConfigLogs.class);
		return devicesConfigLogs.get(0);
	}
	
	public DeviceCommandLogRepository getDeviceCommandLogRepository() {
		return deviceCommandLogRepository;
	}
}
