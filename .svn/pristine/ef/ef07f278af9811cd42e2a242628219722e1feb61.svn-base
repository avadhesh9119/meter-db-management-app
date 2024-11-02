package com.global.meter.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;

@Service
public class DevicesInfoService {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesInfoService.class);

	@Autowired
	DevicesInfoRepository devicesInfoRepository;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	public DevicesInfo getDevicesInfo(String deviceSNO) {
		Optional<DevicesInfo> devicesInfo = devicesInfoRepository.findById(deviceSNO);
		DevicesInfo deviceInfo = null;
		if (devicesInfo.isPresent()) {
			deviceInfo = devicesInfo.get();
		}
		return deviceInfo;
	}

	public List<DevicesInfo> getAllDevicesInfo() {
		List<DevicesInfo> devicesInfo = devicesInfoRepository.findAll();
		return devicesInfo;
	}
	
	/**
	 * used to get devices list
	 * 
	 * @return
	 */
	public List<String> getDevicesList() {
		List<String> deviceList = new ArrayList<String>();
		try {
			String query = Constants.Query.FETCH_DEVICE_SNO;
			List<Map<String, Object>> devices = prestoJdbcTemplate.queryForList(query);
			devices.stream().forEach(x -> deviceList.add((String) x.get("device_serial_number")));
		} catch (Exception ex) {
			LOG.error("Not able to fetch devices list from db due to :" + ex.getMessage());
		}
		return deviceList;
	}
	
	/**
	 * used to get all devices list mainly for nameplate.
	 * 
	 * @return
	 */
	public List<String> getAllDevicesList() {
		List<String> deviceList = new ArrayList<String>();
		try {
			String query = Constants.Query.FETCH_ALL_DEVICE_SNO;
			List<Map<String, Object>> devices = prestoJdbcTemplate.queryForList(query);
			devices.stream().forEach(x -> deviceList.add((String) x.get("device_serial_number")));
		} catch (Exception ex) {
			LOG.error("Not able to fetch devices list from db due to :" + ex.getMessage());
		}
		return deviceList;
	}
	
	/**
	 * used to get devices list
	 * 
	 * @return
	 */
	public List<String> getNamePlateDevicesList() {
		List<String> deviceList = new ArrayList<String>();
		try {
			String query = Constants.Query.NAME_PLATE_DEVICES;
			List<Map<String, Object>> devices = prestoJdbcTemplate.queryForList(query);
			devices.stream().forEach(x -> deviceList.add((String) x.get("device_serial_number")));
		} catch (Exception ex) {
			LOG.error("Not able to fetch devices list from db due to :" + ex.getMessage());
		}
		return deviceList;
	}
	
	/**
	 * used to get devices list
	 * 
	 * @return
	 */
	public List<String> getAllOwnersList() {    
		List<String> deviceList = new ArrayList<String>();
		try {
			String query = Constants.Query.OWNER_TABLE;
			List<Map<String, Object>> devices = prestoJdbcTemplate.queryForList(query);
			devices.stream().forEach(x -> deviceList.add((String) x.get("owner_name")));
		} catch (Exception ex) {
			LOG.error("Not able to fetch devices list from db due to :" + ex.getMessage());
		}
		return deviceList;
	}

	/**
	 * used to get devices list
	 * 
	 * @return
	 */
	public List<String> getCommandWiseDevicesList(DeviceCommand deviceCommand) {
		List<String> deviceList = new ArrayList<String>();
		try {
			StringBuilder query = new StringBuilder(Constants.Query.COMMANDWISE_FETCH_DEVICE_SNO);
			if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(deviceCommand.getCommand())) {
				query.append(" and lastdailylpreaddatetime <= cast('").append(CommonUtils.getCurrentDate())
						.append("' as timestamp) ").append("or lastdailylpreaddatetime Is Null");
			} else if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(deviceCommand.getCommand())) {
				query.append(" and lastdeltalpreaddatetime <= cast('").append(CommonUtils.getCurrentDate())
						.append("' as timestamp) ").append("or lastdeltalpreaddatetime Is Null");
			} else if (ConfigCommands.BILLING_DATA.commandName.equals(deviceCommand.getCommand())) {
				query.append(" and lastbillingreaddatetime <= cast('")
						.append((deviceCommand.getReadWise() != null && deviceCommand.getReadWise().equalsIgnoreCase("M")) 
								? CommonUtils.getCurrentFirstDate() : CommonUtils.getCurrentDate())
						.append("' as timestamp) ").append("or lastbillingreaddatetime Is Null");
			} else if (ConfigCommands.INSTANTANEOUS_READ.commandName.equals(deviceCommand.getCommand())) {
				query.append(" and lastinstanteousreaddatetime <= cast('").append(CommonUtils.getCurrentDate())
						.append("' as timestamp) ").append("or lastinstanteousreaddatetime Is Null");
			} 
			else if (ConfigCommands.NAME_PLATES.commandName.equals(deviceCommand.getCommand())) {
				query.append(" and device_serial_number not in(select device_serial_number from "+meterConfiguration.getKeyspace()+".name_plate) ");
			}
			else if (ConfigCommands.FULL_DATA_READ.commandName.equals(deviceCommand.getCommand())) {
				// Bypass in case of full data reads 
			}
			else {
				query.append(" and lasteventsreaddatetime <= cast('").append(CommonUtils.getCurrentDate())
						.append("' as timestamp) ").append("or lasteventsreaddatetime Is Null");
			}
			List<Map<String, Object>> devices = prestoJdbcTemplate.queryForList(query.toString());
			devices.stream().forEach(x -> deviceList.add((String) x.get("device_serial_number")));
		} catch (Exception ex) {
			LOG.error("Not able to fetch devices list from db due to :" + ex.getMessage());
		}
		return deviceList;
	}

	/**
	 * return cell based devices list
	 * @param deviceCommand
	 * @return
	 */
	public Map<String, CopyOnWriteArrayList<String>> getCellWiseDevicesList(DeviceCommand deviceCommand) {
		Map<String, CopyOnWriteArrayList<String>> cellBasedDevices = new LinkedHashMap<String, CopyOnWriteArrayList<String>>();
		try {
			StringBuilder query = new StringBuilder(Constants.Query.COMMANDWISE_FETCH_DEVICE_SNO);
			if(meterConfiguration.getScheduleCommandRfEnable().equalsIgnoreCase(Constants.DISABLE)) {
				query.append(" and mode_of_comm = '").append(Constants.ModeComm.CELLULAR).append("'");
			}

			List<Map<String, Object>> devices = prestoJdbcTemplate.queryForList(query.toString());
			for (Map<String, Object> deviceRow : devices) {
				String cellId = deviceRow.get("cell_id") != null ? (String) deviceRow.get("cell_id") : Constants.nullKey;
				if(!cellBasedDevices.containsKey(cellId)) {
					cellBasedDevices.put(cellId, new CopyOnWriteArrayList<String>());
				}
				List<String> devList = cellBasedDevices.get(cellId);
				devList.add((String) deviceRow.get("device_serial_number"));
			}
		} catch (Exception ex) {
			LOG.error("Not able to fetch devices list from db due to :" + ex.getMessage());
		}
		return cellBasedDevices;
	}

	public void updateDevicesRow(DevicesInfo devicesInfo) {
		devicesInfoRepository.save(devicesInfo);
	}

	/**
	 * Used to get the List of devices.
	 * 
	 * @param levelName
	 * @param levelValue
	 * @return
	 * @throws JsonProcessingException 
	 * @throws DataAccessException 
	 */
	public synchronized List<DevicesInfo> getDevicesList(String levelName, String levelValue) throws DataAccessException, JsonProcessingException {
		String deviceTable = meterConfiguration.getKeyspace()+".devices_info";
		String fieldName = "";
		
		List<DevicesInfo> devicesList = new ArrayList<DevicesInfo>();
		if (Constants.HierLevelName.ALL.equalsIgnoreCase(levelName) ||
				"owner_name".equalsIgnoreCase(levelName)) {
			fieldName = "owner_name";
		} else if (Constants.HierLevelName.SUBDEVISION.equalsIgnoreCase(levelName)) {
			fieldName = "subdevision_name";
		} else if (Constants.HierLevelName.SUBSTATION.equalsIgnoreCase(levelName)) {
			fieldName = "substation_name";
		} else if (Constants.HierLevelName.FEEDER.equalsIgnoreCase(levelName)) {
			fieldName = "feeder_name";
		} else if (Constants.HierLevelName.DCU.equalsIgnoreCase(levelName)) {
			fieldName = "dcu_serial_number";
		} else if (Constants.HierLevelName.DTMETER.equalsIgnoreCase(levelName)) {
			fieldName = "dt_name";
		} else if (Constants.HierLevelName.METER.equalsIgnoreCase(levelName)
				|| "device_serial_number".equalsIgnoreCase(levelName)) {
			fieldName = "device_serial_number";
		}else if (Constants.HierLevelName.MANUFACTURER.equalsIgnoreCase(levelName)) {
			fieldName = "meter_type";
		}
		
		else {
			return devicesList;
		}

		String[] levels = levelValue.split(",");

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select device_serial_number, meter_type, ip_address_main, dcu_serial_number, dt_name, feeder_name, owner_name, subdevision_name, substation_name, commissioning_status, device_type")
					.append(" from ").append(deviceTable).append(" where ").append(fieldName).append(" in (");
		for (String level : levels) {
			queryBuilder.append("'").append(level).append("',");
		}
		String query = queryBuilder.substring(0, queryBuilder.length() - 1);
		query = query.concat(") ");

		String devicesInfoList = CommonUtils.getMapper()
				.writeValueAsString(prestoJdbcTemplate.queryForList(query));
		devicesList = CommonUtils.getMapper().readValue(devicesInfoList,
						new TypeReference<List<DevicesInfo>>() {});

		return devicesList;
	}
	
	public synchronized List<DevicesInfo> getDevicesList(String levelName, MeterDataVisualizationReq req) throws DataAccessException, JsonProcessingException {
		String deviceTable = meterConfiguration.getKeyspace()+".devices_info";
		String fieldName = "";
		
		List<DevicesInfo> devicesList = new ArrayList<DevicesInfo>();
		if (Constants.HierLevelName.ALL.equalsIgnoreCase(levelName) ||
				"owner_name".equalsIgnoreCase(levelName)) {
			fieldName = "owner_name";
		} else if (Constants.HierLevelName.SUBDEVISION.equalsIgnoreCase(levelName)) {
			fieldName = "subdevision_name";
		} else if (Constants.HierLevelName.SUBSTATION.equalsIgnoreCase(levelName)) {
			fieldName = "substation_name";
		} else if (Constants.HierLevelName.FEEDER.equalsIgnoreCase(levelName)) {
			fieldName = "feeder_name";
		} else if (Constants.HierLevelName.DCU.equalsIgnoreCase(levelName)) {
			fieldName = "dcu_serial_number";
		} else if (Constants.HierLevelName.DTMETER.equalsIgnoreCase(levelName)) {
			fieldName = "dt_name";
		} else if (Constants.HierLevelName.METER.equalsIgnoreCase(levelName)
				|| "device_serial_number".equalsIgnoreCase(levelName)) {
			fieldName = "device_serial_number";
		}else if (Constants.HierLevelName.MANUFACTURER.equalsIgnoreCase(levelName)) {
			fieldName = "meter_type";
		}
		
		else {
			return devicesList;
		}

		String[] levels = req.getHier().getValue().split(",");
		String[] meterTypes = req.getManufacturer().split(",");

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select device_serial_number, meter_type, ip_address_main, dcu_serial_number, dt_name, feeder_name, owner_name, subdevision_name, substation_name, device_type")
					.append(" from ").append(deviceTable).append(" where ").append(fieldName).append(" in (");
		for (String level : levels) {
			queryBuilder.append("'").append(level.trim()).append("',");
		}
		queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
		
		if(!req.getManufacturer().isEmpty())
		{
		queryBuilder.append(") and meter_type in (");
		for (String meterType : meterTypes) {
			queryBuilder.append("'").append(meterType).append("',");
		}
		queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
		
		}
	 queryBuilder.append(") ");
	 
	 String	query = queryBuilder.substring(0,queryBuilder.length());

		String devicesInfoList = CommonUtils.getMapper()
				.writeValueAsString(prestoJdbcTemplate.queryForList(query));
		devicesList = CommonUtils.getMapper().readValue(devicesInfoList,
						new TypeReference<List<DevicesInfo>>() {});

		return devicesList;
	}
	
	/**
	 * Used to get the List of devices.
	 * 
	 * @param levelName
	 * @param levelValue
	 * @return
	 * @throws JsonProcessingException 
	 * @throws DataAccessException 
	 */
	public synchronized List<String> getDevicesListAsString(String levelName, String levelValue) throws DataAccessException, JsonProcessingException {
		String deviceTable = meterConfiguration.getKeyspace()+".devices_info";
		String fieldName = "";
		
		List<String> devicesList = new ArrayList<String>();
		if (Constants.HierLevelName.ALL.equalsIgnoreCase(levelName) ||
				"owner_name".equalsIgnoreCase(levelName)) {
			fieldName = "owner_name";
		} else if (Constants.HierLevelName.SUBDEVISION.equalsIgnoreCase(levelName)) {
			fieldName = "subdevision_name";
		} else if (Constants.HierLevelName.SUBSTATION.equalsIgnoreCase(levelName)) {
			fieldName = "substation_name";
		} else if (Constants.HierLevelName.FEEDER.equalsIgnoreCase(levelName)) {
			fieldName = "feeder_name";
		} else if (Constants.HierLevelName.DCU.equalsIgnoreCase(levelName)) {
			fieldName = "dcu_serial_number";
		} else if (Constants.HierLevelName.DTMETER.equalsIgnoreCase(levelName)) {
			fieldName = "dt_name";
		} else if (Constants.HierLevelName.METER.equalsIgnoreCase(levelName) 
				|| "device_serial_number".equalsIgnoreCase(levelName)) {
			fieldName = "device_serial_number";
		}
		else {
			return devicesList;
		}
		

		String[] levels = levelValue.split(",");

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select device_serial_number")
					.append(" from ").append(deviceTable).append(" where ").append(fieldName).append(" in (");
		for (String level : levels) {
			queryBuilder.append("'").append(level).append("',");
		}
		 
		String	query = queryBuilder.substring(0,queryBuilder.length()-1);
		query = query.concat(") ");

		List<Map<String, Object>> devices = prestoJdbcTemplate.queryForList(query.toString());
		devices.stream().forEach(x -> devicesList.add((String) x.get("device_serial_number")));
		

		return devicesList;
	}
	public synchronized List<String> getDevicesListAsString(String levelName, DeviceCommand req) throws DataAccessException, JsonProcessingException {
		String deviceTable = meterConfiguration.getKeyspace()+".devices_info";
		String fieldName = "";
		
		List<String> devicesList = new ArrayList<String>();
		if (Constants.HierLevelName.ALL.equalsIgnoreCase(levelName) ||
				"owner_name".equalsIgnoreCase(levelName)) {
			fieldName = "owner_name";
		} else if (Constants.HierLevelName.SUBDEVISION.equalsIgnoreCase(levelName)) {
			fieldName = "subdevision_name";
		} else if (Constants.HierLevelName.SUBSTATION.equalsIgnoreCase(levelName)) {
			fieldName = "substation_name";
		} else if (Constants.HierLevelName.FEEDER.equalsIgnoreCase(levelName)) {
			fieldName = "feeder_name";
		} else if (Constants.HierLevelName.DCU.equalsIgnoreCase(levelName)) {
			fieldName = "dcu_serial_number";
		} else if (Constants.HierLevelName.DTMETER.equalsIgnoreCase(levelName)) {
			fieldName = "dt_name";
		} else if (Constants.HierLevelName.METER.equalsIgnoreCase(levelName) 
				|| "device_serial_number".equalsIgnoreCase(levelName)) {
			fieldName = "device_serial_number";
		}
		else {
			return devicesList;
		}
		String[] levels = req.getHier().getValue().split(",");
		String[] meterTypes = req.getManufacturer().split(",");

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select device_serial_number, meter_type")
					.append(" from ").append(deviceTable).append(" where ").append(fieldName).append(" in (");
		for (String level : levels) {
			queryBuilder.append("'").append(level.trim()).append("',");
		}
		queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
		if(!req.getManufacturer().isEmpty())
		{
		queryBuilder.append(") and meter_type in (");
		for (String meterType : meterTypes) {
			queryBuilder.append("'").append(meterType).append("',");
		}
		queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
		}
		queryBuilder.append(") ");
		String query = queryBuilder.substring(0, queryBuilder.length());

		List<Map<String, Object>> devices = prestoJdbcTemplate.queryForList(query.toString());
		devices.stream().forEach(x -> devicesList.add((String) x.get("device_serial_number")));
		
		return devicesList;
	}
	
	public synchronized List<String> getDevicesListAsString(String levelName, SingleConnectionCommandReq req) throws DataAccessException, JsonProcessingException {
		String deviceTable = meterConfiguration.getKeyspace()+".devices_info";
		String fieldName = "";
		
		List<String> devicesList = new ArrayList<String>();
		if (Constants.HierLevelName.ALL.equalsIgnoreCase(levelName) ||
				"owner_name".equalsIgnoreCase(levelName)) {
			fieldName = "owner_name";
		} else if (Constants.HierLevelName.SUBDEVISION.equalsIgnoreCase(levelName)) {
			fieldName = "subdevision_name";
		} else if (Constants.HierLevelName.SUBSTATION.equalsIgnoreCase(levelName)) {
			fieldName = "substation_name";
		} else if (Constants.HierLevelName.FEEDER.equalsIgnoreCase(levelName)) {
			fieldName = "feeder_name";
		} else if (Constants.HierLevelName.DCU.equalsIgnoreCase(levelName)) {
			fieldName = "dcu_serial_number";
		} else if (Constants.HierLevelName.DTMETER.equalsIgnoreCase(levelName)) {
			fieldName = "dt_name";
		} else if (Constants.HierLevelName.METER.equalsIgnoreCase(levelName) 
				|| "device_serial_number".equalsIgnoreCase(levelName)) {
			fieldName = "device_serial_number";
		}
		else {
			return devicesList;
		}
		String[] levels = req.getHier().getValue().split(",");
		String[] meterTypes = req.getManufacturer().split(",");

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select device_serial_number, meter_type")
					.append(" from ").append(deviceTable).append(" where ").append(fieldName).append(" in (");
		for (String level : levels) {
			queryBuilder.append("'").append(level.trim()).append("',");
		}
		queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
		if(!req.getManufacturer().isEmpty())
		{
		queryBuilder.append(") and meter_type in (");
		for (String meterType : meterTypes) {
			queryBuilder.append("'").append(meterType).append("',");
		}
		queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
		}
		queryBuilder.append(") ");
		String query = queryBuilder.substring(0, queryBuilder.length());

		List<Map<String, Object>> devices = prestoJdbcTemplate.queryForList(query.toString());
		devices.stream().forEach(x -> devicesList.add((String) x.get("device_serial_number")));
		
		return devicesList;
	}
}
