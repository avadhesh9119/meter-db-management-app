package com.global.meter.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.global.meter.business.model.DeviceCommandLog;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.model.Devices;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.request.model.Device;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.DevicesCommandsLogService;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.service.HistoricalDataService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.MetersCommandsConfiguration;

@RestController
@RequestMapping(value="/devices/info")
public class DevicesInfoController {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesInfoController.class);
	
	@Autowired
	DevicesInfoRepository devicesInfoRepository;
	
	@Autowired
	DevicesCommandsLogService devicesCommandsLogService;
	
	@Autowired
	MetersCommandsConfiguration meterCommands;

	@Autowired
	HistoricalDataService historicalDataService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DevicesInfoService devicesInfoService;
	
	@RequestMapping(value = "/get" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getMeterInfo(@Valid @RequestBody Device device)
	{
		LOG.info("Requesting to get single meter details --> ");
		Devices devices = new Devices();
		try {
			LOG.info("Reading Meter Details: " + device.getPlainText());
			Optional<DevicesInfo> devicesInfo = devicesInfoRepository.findById(device.getPlainText());
			long currentTime = System.currentTimeMillis();
			
			CommonUtils commonUtils = new CommonUtils();
			devices = commonUtils.setDevicesInfo(devicesInfo.get(), null , currentTime);
			return new ResponseEntity<>(devices, HttpStatus.OK);
			
		} catch (Exception e) {
			LOG.error("Device not found due to "+ e.getMessage());
			return new ResponseEntity<>(devices, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/meterList/get" , method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getMeterList()
	{
		LOG.info("Requesting to get all meter list --> ");
		List<String> deviceList = new ArrayList<String>();
		try {
			deviceList = devicesInfoService.getDevicesList();
			return new ResponseEntity<>(deviceList, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Device list not found due to "+ e.getMessage());
			return new ResponseEntity<>(deviceList, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/meterList/get/all" , method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllMeterList()
	{
		LOG.info("Requesting to get all meter list --> ");
		List<String> deviceList = new ArrayList<String>();
		try {
			deviceList = devicesInfoService.getAllDevicesList();
			return new ResponseEntity<>(deviceList, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Device list not found due to "+ e.getMessage());
			return new ResponseEntity<>(deviceList, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/nameplate/meterList/get" , method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getNamePlateDevicesList()
	{
		LOG.info("Requesting to get meter list for name plates --> ");
		List<String> deviceList = new ArrayList<String>();
		try {
			deviceList = devicesInfoService.getNamePlateDevicesList();
			return new ResponseEntity<>(deviceList, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Device list not found due to "+ e.getMessage());
			return new ResponseEntity<>(deviceList, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/commandWise/meterList" , method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getCommandWiseMeterList(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		LOG.info("Requesting to get command wise meter list --> ");
		List<String> deviceList = new ArrayList<String>();
		try {
			deviceList = devicesInfoService.getCommandWiseDevicesList(deviceCommand);
			Collections.sort(deviceList);
			return new ResponseEntity<>(deviceList, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Device list not found due to "+ e.getMessage());
			return new ResponseEntity<>(deviceList, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/cellBased/meterList" , method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getCellBasedMeterList(@Valid @RequestBody DeviceCommand deviceCommand)
	{
		LOG.info("Requesting to get command wise meter list --> ");
		Map<String, CopyOnWriteArrayList<String>> deviceList = new LinkedHashMap<String, CopyOnWriteArrayList<String>>();
		try {
			deviceList = devicesInfoService.getCellWiseDevicesList(deviceCommand);
			return new ResponseEntity<>(deviceList, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Device list not found due to "+ e.getMessage());
			return new ResponseEntity<>(deviceList, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/getDeviceCommandWise" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getCommandList(@Valid @RequestBody DeviceCommand deviceCommand){
		LOG.info("Command received to get list of Devices Configurations from DB:");
		List<DeviceCommand> devicesCommandList = null;
		try {
			devicesCommandList = devicesCommandsLogService.getDeviceCommandLog(deviceCommand.getCommand());
		} catch (Exception e) {
			devicesCommandList = new ArrayList<DeviceCommand>();
			LOG.error("Issue in fetching devices command logs due to :" + e.getMessage());
		}
		return new ResponseEntity<>(devicesCommandList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getDeviceCommandCurrentDayWise" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getCommandListCurrentDayWise(@Valid @RequestBody DeviceCommand deviceCommand){
		LOG.info("Command received to get list of Devices Configurations from DB:");
		List<DeviceCommandLog> devicesCommandList = null;
		try {
			devicesCommandList = devicesCommandsLogService.getDeviceCommandLogCurrentDayWise(deviceCommand);
		} catch (Exception e) {
			devicesCommandList = new ArrayList<DeviceCommandLog>();
			LOG.error("Issue in fetching devices command logs due to :" + e.getMessage());
		}
		return new ResponseEntity<>(devicesCommandList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getFullDataDeviceCommandWise" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getFullDataDeviceCommandWise(@Valid @RequestBody DeviceCommand deviceCommand){
		LOG.info("Command received to get Full Data Commnd List from : Device Command Logs");
		List<DeviceCommand> devicesCommandList = null;
		try {
			devicesCommandList = devicesCommandsLogService.getFullDataDeviceCommandLog(deviceCommand.getCommand());
		} catch (Exception e) {
			devicesCommandList = new ArrayList<DeviceCommand>();
			LOG.error("Issue in fetching devices command logs due to :" + e.getMessage());
		}
		return new ResponseEntity<>(devicesCommandList, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/cellBased/getFullDataDeviceCommandWise" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getCellBasedFullDataDeviceCommandWise(@Valid @RequestBody DeviceCommand deviceCommand){
		LOG.info("Command received to get Full Data Commnd List from : Device Command Logs");
		Map<String, CopyOnWriteArrayList<DeviceCommand>> devicesCommandList = null;
		try {
			devicesCommandList = devicesCommandsLogService.getCellBasedFullDataDeviceCommandLog(deviceCommand.getCommand());
		} catch (Exception e) {
			devicesCommandList = new LinkedHashMap<String, CopyOnWriteArrayList<DeviceCommand>>();
			LOG.error("Issue in fetching devices command logs due to :" + e.getMessage());
		}
		return new ResponseEntity<>(devicesCommandList, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/cellBased/getFullPrepayDataDeviceCommandWise" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getCellBasedFullPrepayDataDeviceCommandWise(@Valid @RequestBody DeviceCommand deviceCommand){
		LOG.info("Command received to get Full Data Commnd List from : Device Command Logs");
		Map<String, CopyOnWriteArrayList<DeviceCommand>> devicesCommandList = null;
		try {
			devicesCommandList = devicesCommandsLogService.getCellBasedFullPrepayDataDeviceCommandLog(deviceCommand.getCommand());
		} catch (Exception e) {
			devicesCommandList = new LinkedHashMap<String, CopyOnWriteArrayList<DeviceCommand>>();
			LOG.error("Issue in fetching devices command logs due to :" + e.getMessage());
		}
		return new ResponseEntity<>(devicesCommandList, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/cellBased/getFullDataDeviceCommand/perDay" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getCellBasedFullDataDeviceCommandsPerDay(@Valid @RequestBody DeviceCommand deviceCommand){
		LOG.info("Get Full Data Per Day Commnds List from : Device Command Logs Per Day");
		Map<String, CopyOnWriteArrayList<DeviceCommand>> devicesCommandList = null;
		try {
			devicesCommandList = devicesCommandsLogService.getCellBasedFullDataDeviceCommandLogPerDay(
					deviceCommand.getCommand());
		} catch (Exception e) {
			devicesCommandList = new LinkedHashMap<String, CopyOnWriteArrayList<DeviceCommand>>();
			LOG.error("Issue in fetching devices command logs due to :" + e.getMessage());
		}
		return new ResponseEntity<>(devicesCommandList, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/cellBased/getFullConfigLogs" , method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getFullConfigLogs(){
		LOG.info("Command received to get Full Data Commnd List from : Device Command Logs");
		Map<String, CopyOnWriteArrayList<DeviceCommand>> devicesCommandList = null;
		try {
			devicesCommandList = devicesCommandsLogService.getCellBasedFullConfigLogs();
		} catch (Exception e) {
			devicesCommandList = new LinkedHashMap<String, CopyOnWriteArrayList<DeviceCommand>>();
			LOG.error("Issue in fetching devices command logs due to :" + e.getMessage());
		}
		return new ResponseEntity<>(devicesCommandList, HttpStatus.OK);
		
	}
}
