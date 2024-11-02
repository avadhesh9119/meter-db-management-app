package com.global.meter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DeviceCommandLogs;
import com.global.meter.business.model.DeviceConfigLogs;
import com.global.meter.business.model.DevicesConfig;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.FirmwareConfig;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.DeviceTrackingIDs;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.data.repository.DeviceCommandLogsRepository;
import com.global.meter.data.repository.DeviceConfigLogsRepository;
import com.global.meter.data.repository.DevicesConfigRepository;
import com.global.meter.data.repository.FirmwareConfigRepository;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.service.DevicesCommandPerformService;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.request.model.Device;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DevicesConfigService {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesConfigService.class);
	
	private String devicesConfigTable = "devices_config";
	
	@Autowired
	DevicesConfigRepository devicesConfigRepository; 
	
	@Autowired
	FirmwareConfigRepository firmwareConfigRepository;
	
	@Autowired
	DeviceConfigLogsRepository deviceConfigLogsRepository;
	
	@Autowired
	private CassandraOperations cassandraTemplate;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	DeviceCommandLogsRepository devCommandLogRepository;
	
	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	DevicesCommandPerformService devicesCommandPerformService;
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate ;
	
	public void addConfigs(MeterResponse response ,List<DevicesInfo> devicesList, DeviceCommand deviceCommand) {
		List<DevicesConfig> deviceConfigList = new ArrayList<>();

		try {
			for (DevicesInfo device : devicesList) {
				long timeInMilliseconds = System.nanoTime();
				if(device.getIp_address_main() != null) {
						DevicesConfig devicesConfig = new DevicesConfig(device.getDevice_serial_number(),
								deviceCommand.getCommand(),new Date(System.currentTimeMillis()),null,
								deviceCommand.getCommandsVal(),device.getDcu_serial_number(),
								device.getDt_name(),device.getFeeder_name()
								,new Date(System.currentTimeMillis())
								,device.getOwner_name(),
								Constants.CmdConfigStatus.ADDED,
								device.getSubdevision_name(),
								device.getSubstation_name(),
								String.valueOf(timeInMilliseconds),null,0, deviceCommand.getBatchId());
						deviceConfigList.add(devicesConfig);
					} 
			}
			devicesConfigRepository.saveAll(deviceConfigList);
			LOG.info("Configs Added for : "+deviceCommand.getLevelName());
			response.setMessage("Configs Added for : "+deviceCommand.getLevelName());
			response.setStatus(200);
		} catch (Exception e) {
			LOG.error("Error in adding configs due to : "+e.getMessage());
			response.setMessage("Error in adding configs due to : "+e.getMessage());
		}
	}
	
	public void addFullDataCmds(MeterResponse response ,List<DevicesInfo> devicesList) {
		List<DeviceCommandLogs> deviceConfigList = new ArrayList<>();

		Map<String, String> dataSaveResults = new LinkedHashMap<String, String>();
		Map<String, Integer> dataRetryCount = new LinkedHashMap<String, Integer>();
		
		List<String> cmdList = Constants.FullDataList.getList();
		String batchId = Constants.createBatchId();
		for (String cmd : cmdList) {
			dataSaveResults.put(cmd, Constants.IN_PROGRESS);
			dataRetryCount.put(cmd, 0);
		}
		try {
			for (DevicesInfo device : devicesList) {
				long timeInMilliseconds = System.nanoTime();
				if(device.getIp_address_main() != null
						&& Constants.Status.UP.equalsIgnoreCase(device.getCommissioning_status())) {
						
					DeviceCommandLogs deviceCommandLogs = new DeviceCommandLogs(
								device.getDevice_serial_number(),String.valueOf(timeInMilliseconds), 
								dataSaveResults, Constants.IN_PROGRESS, new Date(System.currentTimeMillis()),
								device.getDcu_serial_number(), device.getDt_name(),
								device.getFeeder_name(), device.getOwner_name(),
								device.getSubdevision_name(), device.getSubstation_name(),"" 
								,null, 0, dataRetryCount);
					deviceCommandLogs.setBatch_id(batchId);
					deviceCommandLogs.setUser_id(Constants.USER_ID);
					deviceCommandLogs.setSource(Constants.SOURCE);
						
					deviceCommandLogs.setCell_id(device.getCell_id());
						
						deviceConfigList.add(deviceCommandLogs);
				} 
			}
			devCommandLogRepository.saveAll(deviceConfigList);
			response.setMessage("Full Data command added for all devices : ");
			response.setStatus(200);
		} catch (Exception e) {
			LOG.error("Error in adding configs due to : "+e.getMessage());
			response.setMessage("Error in adding configs due to : "+e.getMessage());
		}
	}
	
	public void addFirmwareConfigs(MeterResponse response ,List<DevicesInfo> devicesList, DeviceCommand deviceCommand) {
		List<FirmwareConfig> firmwareConfigList = new ArrayList<>();
		List<DeviceTrackingIDs> trackingIdList = new ArrayList<DeviceTrackingIDs>();
		try {
			for (DevicesInfo device : devicesList) {
				long timeInMilliseconds = System.nanoTime();
				String trackingId = String.valueOf(timeInMilliseconds);

				if(deviceCommand.getConfigVals().containsKey(device.getMeter_type()) && device.getIp_address_main() != null) {
					FirmwareConfig devicesConfig = new FirmwareConfig(device.getDevice_serial_number(),
							new Date(System.currentTimeMillis()),null,
							deviceCommand.getConfigVals().get(device.getMeter_type()),device.getDcu_serial_number(),
							device.getDt_name(),device.getFeeder_name(),new Date(System.currentTimeMillis())
							,device.getOwner_name(),Constants.IN_PROGRESS,device.getSubdevision_name(),
							device.getSubstation_name(), trackingId,null,0);
					if (deviceCommand.getFirmwareData() != null && deviceCommand.getFirmwareData().getVersion() != null) {
						devicesConfig.setVersion(deviceCommand.getFirmwareData().getVersion());
					}
					devicesConfig.setSource(deviceCommand.getSource());
					devicesConfig.setUser_id(deviceCommand.getUserId());
					devicesConfig.setCreated(new Date(System.currentTimeMillis()));
					devicesConfig.setVersion(deviceCommand.getFirmwareVersion().get(device.getMeter_type()));
					
					firmwareConfigList.add(devicesConfig);
					
					DeviceTrackingIDs deviceTrackingIDs = new DeviceTrackingIDs();
					deviceTrackingIDs.setDeviceSno(device.getDevice_serial_number());
					deviceTrackingIDs.setMessage(Constants.IN_PROGRESS);
					deviceTrackingIDs.setTrackingId(trackingId);
					trackingIdList.add(deviceTrackingIDs);
				}
				else {
					if(!deviceCommand.isSkipOtherManufacturer()) {
						throw new Exception("Please also add firmware file in request for "+device.getMeter_type());
					}
				}
			}
			response.setTrackingIds(trackingIdList);
			firmwareConfigRepository.saveAll(firmwareConfigList);
			LOG.info("Configs Added for : "+deviceCommand.getLevelName());
			response.setMessage(Constants.SUCCESS);
			response.setStatus(200);
		} catch (Exception e) {
			LOG.error("Error in adding configs due to : "+e.getMessage());
			response.setStatus(500);
			response.setMessage("Error : "+e.getMessage());
		}
	}
	
	public void addFirmwareConfigs(CommonResponse response ,List<DevicesInfo> devicesList, DeviceCommand deviceCommand) {
		List<FirmwareConfig> firmwareConfigList = new ArrayList<>();
		List<DeviceTrackingIDs> trackingIdList = new ArrayList<DeviceTrackingIDs>();
		List<String> missingFileDevList = new ArrayList<>();
		List<String> deviceList = new ArrayList<>();
		List<String> inprogressDevList = new ArrayList<>();
		DeviceTrackingIDs deviceTrackingID;
		try {
			String query="select device_serial_number from "+meterConfiguration.getKeyspace() + "." +Tables.FIRMWARE_CONFIG+" where status in ('ADDED','IN_PROGRESS')";

			List<Map<String, Object>> devices = prestoJdbcTemplate.queryForList(query);
			devices.stream().forEach(x -> deviceList.add((String) x.get("device_serial_number")));
			
			for (DevicesInfo device : devicesList) {
				
				if(Constants.Status.UP.equalsIgnoreCase(device.getCommissioning_status()) 
						|| Constants.Types.INSTALLED.equalsIgnoreCase(device.getCommissioning_status())) {
				long timeInMilliseconds = System.nanoTime();
				String trackingId = String.valueOf(timeInMilliseconds);
				
				if(deviceList.contains(device.getDevice_serial_number())) {
					inprogressDevList.add(device.getDevice_serial_number());
				}
				else if(deviceCommand.getConfigVals().containsKey(device.getMeter_type()) && device.getIp_address_main() != null) {
					FirmwareConfig devicesConfig = new FirmwareConfig(device.getDevice_serial_number(),
							new Date(System.currentTimeMillis()),null,
							deviceCommand.getConfigVals().get(device.getMeter_type()),device.getDcu_serial_number(),
							device.getDt_name(),device.getFeeder_name(),new Date(System.currentTimeMillis())
							,device.getOwner_name(),Constants.ADDED,device.getSubdevision_name(),
							device.getSubstation_name(), trackingId,null,0);
					
					devicesConfig.setSource(deviceCommand.getSource());
					devicesConfig.setUser_id(deviceCommand.getUserId());
					devicesConfig.setCreated(new Date(System.currentTimeMillis()));
					devicesConfig.setVersion(deviceCommand.getFirmwareVersion().get(device.getMeter_type()));
					
					firmwareConfigList.add(devicesConfig);
					
					DeviceTrackingIDs deviceTrackingIDs = new DeviceTrackingIDs();
					deviceTrackingIDs.setDeviceSno(device.getDevice_serial_number());
					deviceTrackingIDs.setMessage(Constants.ADDED);
					deviceTrackingIDs.setTrackingId(trackingId);
					trackingIdList.add(deviceTrackingIDs);
				}
				else {
					missingFileDevList.add(device.getDevice_serial_number());
					if(!deviceCommand.isSkipOtherManufacturer()) {
						throw new Exception("Please also add firmware file in request for "+device.getMeter_type());
					}
				}
			 }
			}
			if(inprogressDevList.size()>0) {
			deviceTrackingID = new DeviceTrackingIDs();
			deviceTrackingID.setMessage(Constants.ALREADY_IN_QUEUE);
			deviceTrackingID.setInprogressDevList(inprogressDevList);
			trackingIdList.add(deviceTrackingID);
			}
		 	if(missingFileDevList.size()>0) {              
				deviceTrackingID = new DeviceTrackingIDs();
				deviceTrackingID.setMessage(Constants.MISSING_FILE);
				deviceTrackingID.setMissingFileDevList(missingFileDevList);
				trackingIdList.add(deviceTrackingID);
			}
			response.setData(trackingIdList);
			if(firmwareConfigList.size()>0) {
			firmwareConfigRepository.saveAll(firmwareConfigList);
			}
			LOG.info("Configs Added for : "+deviceCommand.getHier().getValue());
			response.setMessage(Constants.SUCCESS);
			response.setCode(200);
		} catch (Exception e) {
			LOG.error("Error in adding configs due to : "+e.getMessage());
			response.setCode(500);
			response.setMessage("Note : "+e.getMessage());
		}
	}
	
	public void addMultipleConfigs(MeterResponse response ,List<DevicesInfo> devicesList, DeviceCommand deviceCommand) {
		List<DeviceConfigLogs> deviceConfigList = new ArrayList<>();
		List<DeviceTrackingIDs> trackingIds = new ArrayList<DeviceTrackingIDs>();
		try {
			for (DevicesInfo device : devicesList) {
				if(device.getIp_address_main() != null) {
						DeviceConfigLogs devicesConfig = new DeviceConfigLogs();
						
						String trackingId = String.valueOf(System.nanoTime());
						
						
						devicesConfig.setCommand_name(deviceCommand.getConfigVals());
						devicesConfig.setCommand_status(deviceCommand.getConfigValsStatus());
						devicesConfig.setDcu_serial_number(device.getDcu_serial_number());
						devicesConfig.setDevice_serial_number(device.getDevice_serial_number());
						devicesConfig.setDt_name(device.getDt_name());
						devicesConfig.setFeeder_name(device.getFeeder_name());
						devicesConfig.setMdas_datetime(new Date(System.currentTimeMillis()));
						devicesConfig.setOwner_name(device.getOwner_name());
						devicesConfig.setStatus(Constants.ADDED);
						devicesConfig.setSubdevision_name(device.getSubdevision_name());
						devicesConfig.setSubstation_name(device.getSubstation_name());
						devicesConfig.setTot_attempts(0);
						devicesConfig.setTracking_id(trackingId);
						
						if(deviceCommand.getConfigVals().containsKey(ConfigCommands.ACTIVITY_CALENDER.commandName)) {
							devicesConfig.setActivate_activity_cal_datetime(
									dateConverter.convertStringToDate(deviceCommand.getActivateActivityCalDatetime()));
						}
						
						
						deviceConfigList.add(devicesConfig);
						
						DeviceTrackingIDs deviceTrackingIDs = new DeviceTrackingIDs();
						deviceTrackingIDs.setDeviceSno(device.getDevice_serial_number());
						deviceTrackingIDs.setTrackingId(trackingId);
						deviceTrackingIDs.setMessage(Constants.ADDED);
						trackingIds.add(deviceTrackingIDs);
					} 
			}
			deviceConfigLogsRepository.saveAll(deviceConfigList);
			LOG.info("Multiple Configs Added for : "+deviceCommand.getLevelName());
			response.setTrackingIds(trackingIds);
			response.setMessage("Multiple Configs Added for : "+deviceCommand.getLevelName());
			response.setStatus(200);
		} catch (Exception e) {
			LOG.error("Error in adding multiple configs due to : "+e.getMessage());
			response.setMessage("Error in adding multiple configs due to : "+e.getMessage());
		}
	}
	
	/**
	 * Used to get Configs command List
	 * @return
	 * @throws Exception
	 */
	public List<DeviceCommand> getConfigs() throws Exception{
		
		String inQuery = "select * from "+meterConfiguration.getKeyspace()+".devices_config where status in ('IN_PROGRESS','ADDED','FAILURE') limit 25000";
		String configCommandList = CommonUtils.getMapper().writeValueAsString(
									prestoJdbcTemplate.queryForList(inQuery.toString()));
		List<DevicesConfig> devicesConfig =  CommonUtils.getMapper()
				.readValue(configCommandList, new TypeReference<List<DevicesConfig>>(){});
		
		List<DeviceCommand> devicesCommandList = new ArrayList<DeviceCommand>();
		if(devicesConfig != null && devicesConfig.size() > 0) {
			for (DevicesConfig devicesConfigs : devicesConfig) {
				DeviceCommand deviceCommand = new DeviceCommand();
				
				Device device = new Device();
				device.setPlainText(devicesConfigs.getDevice_serial_number());
				deviceCommand.setDevice(device);
				deviceCommand.setCommand(devicesConfigs.getCommand_name());
				deviceCommand.setCommandsVal(devicesConfigs.getCommand_val());
				deviceCommand.setTrackingId(devicesConfigs.getTracking_id());
				devicesCommandList.add(deviceCommand);
			}
		}
		return devicesCommandList;
	}
	
	/**
	 * Used to get Configs command List
	 * @return
	 * @throws Exception
	 */
	public List<DeviceCommand> getFirmwareConfigs() throws Exception{
		
		String inQuery = "select * from "+meterConfiguration.getKeyspace()+".firmware_config where status not in ('SUCCESS','FAILURE') limit 25000";
		String configCommandList = CommonUtils.getMapper().writeValueAsString(
									prestoJdbcTemplate.queryForList(inQuery.toString()));
		List<FirmwareConfig> firmwareConfigList =  CommonUtils.getMapper()
				.readValue(configCommandList, new TypeReference<List<FirmwareConfig>>(){});
		
		List<DeviceCommand> devicesCommandList = new ArrayList<DeviceCommand>();
		if(firmwareConfigList != null && firmwareConfigList.size() > 0) {
			for (FirmwareConfig firmwareConfig : firmwareConfigList) {
				DeviceCommand deviceCommand = new DeviceCommand();
				
				Device device = new Device();
				device.setPlainText(firmwareConfig.getDevice_serial_number());
				deviceCommand.setDevice(device);
				deviceCommand.setCommand(ConfigCommands.FW_UPDATE.commandName);
				deviceCommand.setCommandsVal(firmwareConfig.getCommand_val());
				deviceCommand.setTrackingId(firmwareConfig.getTracking_id());
				devicesCommandList.add(deviceCommand);
			}
		}
		return devicesCommandList;
	}
	
	//get Devices Config find by trackingId
	public DevicesConfig findByTrackingId(String deviceNo , String trackingId , String cmdName)
	{
		Where query = QueryBuilder.select().from(devicesConfigTable).where(
				QueryBuilder.eq("tracking_id", trackingId))
				.and(QueryBuilder.eq("device_serial_number", deviceNo))
				.and(QueryBuilder.eq("command_name", cmdName));
		List<DevicesConfig> devicesConfig =  cassandraTemplate.select(query, DevicesConfig.class);
		return devicesConfig.get(0);
	}
	
	public DevicesConfigRepository getDevicesConfigRepository() {
		return devicesConfigRepository;
	}
}
