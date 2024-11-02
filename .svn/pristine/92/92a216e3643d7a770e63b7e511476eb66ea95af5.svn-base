package com.global.meter.inventive.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.global.meter.MeterDBAppStarter;
import com.global.meter.business.model.DeviceConfigLogs;
import com.global.meter.business.model.DevicesConfig;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.data.repository.DeviceCommandLogsRepository;
import com.global.meter.data.repository.DeviceConfigLogsRepository;
import com.global.meter.data.repository.DevicesConfigRepository;
import com.global.meter.data.repository.FirmwareConfigRepository;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DeviceTraceIDs;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DevicesConfigurationsService {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesConfigurationsService.class);
	
	@Autowired
	DevicesConfigRepository devicesConfigRepository; 
	
	@Autowired
	FirmwareConfigRepository firmwareConfigRepository;
	
	@Autowired
	DeviceConfigLogsRepository deviceConfigLogsRepository;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	DeviceCommandLogsRepository devCommandLogRepository;
	
	@Autowired
	DateConverter dateConverter;
	
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
								String.valueOf(timeInMilliseconds),null,0,deviceCommand.getBatchId());
						
						deviceConfigList.add(devicesConfig);
					} 
			}
			devicesConfigRepository.saveAll(deviceConfigList);
			LOG.info("Configs Added for : "+deviceCommand.getHier().getValue());
			response.setMessage("Configs Added for : "+deviceCommand.getHier().getValue());
			response.setStatus(200);
		} catch (Exception e) {
			LOG.error("Error in adding configs due to : "+e.getMessage());
			response.setMessage("Error in adding configs due to : "+e.getMessage());
		}
	}
	
	public void addMultipleConfigs(CommonResponse response ,List<DevicesInfo> devicesList, MeterDataVisualizationReq req, String batchId) {
		List<DeviceConfigLogs> deviceConfigList = new ArrayList<>();
		List<DeviceTraceIDs> trackingIds = new ArrayList<DeviceTraceIDs>();
		try {
			for (DevicesInfo device : devicesList) {
				if(device.getIp_address_main() != null) {
					if(device.getCommissioning_status().equalsIgnoreCase(Constants.Status.UP)) {
						DeviceConfigLogs devicesConfig = new DeviceConfigLogs();
						
						String trackingId = String.valueOf(System.nanoTime());
						
						
						Map<String, String> modifiedConfigVals = new HashMap<>();
						modifiedConfigVals.putAll(req.getConfigVals());
						
						String port = CommonUtils.getPushPort(device);
						if(StringUtils.isEmpty(port)) {
							port = MeterDBAppStarter.pushPortsInfo.get(Constants.MeterMake.IHM);
						}
						
						if(modifiedConfigVals.containsKey(ConfigCommands.ALERT_IP_PUSH.commandName)) {
							
							String configVal = modifiedConfigVals.get(ConfigCommands.ALERT_IP_PUSH.commandName);
							configVal = configVal + ":" + port;
							modifiedConfigVals.put(ConfigCommands.ALERT_IP_PUSH.commandName, configVal);
						}
						
						if(modifiedConfigVals.containsKey(ConfigCommands.INSTANT_IP_PUSH.commandName)) {
							String configVal = modifiedConfigVals.get(ConfigCommands.INSTANT_IP_PUSH.commandName);
							configVal = configVal + ":" + port;
							modifiedConfigVals.put(ConfigCommands.INSTANT_IP_PUSH.commandName, configVal);
						}
						
						devicesConfig.setCommand_name(modifiedConfigVals);
						devicesConfig.setCommand_status(req.getConfigValsStatus());
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
						devicesConfig.setBatch_id(batchId);
						devicesConfig.setSource(req.getSource());
						devicesConfig.setUser_id(req.getUserId());
						devicesConfig.setDevice_type(device.getDevice_type());
						devicesConfig.setMeter_type(device.getMeter_type());
						
						deviceConfigList.add(devicesConfig);
						
						DeviceTraceIDs deviceTrackingIDs = new DeviceTraceIDs();
						deviceTrackingIDs.setDeviceNo(device.getDevice_serial_number());
						deviceTrackingIDs.setTraceId(trackingId);
						deviceTrackingIDs.setMessage(Constants.ADDED);
						trackingIds.add(deviceTrackingIDs);
						
					} 
			  }
			}
			if(deviceConfigList.size()>0) {
			deviceConfigLogsRepository.saveAll(deviceConfigList);
			}
			LOG.info("Multiple Configs Added.. ");
			response.setData(trackingIds);
			response.setCode(200);
		} catch (Exception e) {
			LOG.error("Error in adding multiple configs due to : "+e.getMessage());
			response.setMessage("Error in adding multiple configs due to : "+e.getMessage());
		}
	}
}
