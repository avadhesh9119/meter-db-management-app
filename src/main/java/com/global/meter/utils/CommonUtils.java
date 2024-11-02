package com.global.meter.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.global.meter.MeterDBAppStarter;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.DtTrans;
import com.global.meter.business.model.Feeders;
import com.global.meter.business.model.FirmwareData;
import com.global.meter.business.model.Subdivisions;
import com.global.meter.business.model.Substations;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.Devices;
import com.global.meter.common.model.FullDataMeterResponse;
import com.global.meter.common.model.FullDataModel;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.v3.inventive.business.model.DevicesCommandLog;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogs;

import jnr.ffi.Struct.int16_t;

@Component
public class CommonUtils {

	private static final Logger LOG = LoggerFactory.getLogger(CommonUtils.class);
	private static ObjectMapper mapper;
	private static ObjectMapper logMapperLongToDateFormat;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	MetersCommandsConfiguration meterCommands;

	@Autowired
	DateConverter dateConverter;
	
	
	JdbcTemplate prestoJdbcTemplate;
	
	
	public CommonUtils() {
		
	}
	
	public CommonUtils(JdbcTemplate  prestoJdbcTemplate) {
		this.prestoJdbcTemplate = prestoJdbcTemplate;
	}


	// Creating date format
	static {
		mapper = new ObjectMapper();
		// ignore the null fields globally
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		
		logMapperLongToDateFormat = new ObjectMapper();
		// ignore the null fields globally
		logMapperLongToDateFormat.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		logMapperLongToDateFormat.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		logMapperLongToDateFormat.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		logMapperLongToDateFormat.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
	}

	@SuppressWarnings("removal")
	public synchronized static Date getInstanDate(Object calObject) {
		Date result = null;
		if (calObject == null) {
			return new Date(System.currentTimeMillis());
		}
		else if (isValidDateFormat(calObject.toString())) {
			
			DateConverter dateConverter=new DateConverter();
				return	dateConverter.convertXmlDateToHesDate(calObject.toString());
		}
		else {
			try {
				@SuppressWarnings("unchecked")
				Map<String, Object> dateMap = (Map<String, Object>) calObject;
				if(dateMap.get("skip") != null && dateMap.get("skip") instanceof ArrayList) {
					@SuppressWarnings("unchecked")
					ArrayList<String> skipList = (ArrayList<String>) dateMap.get("skip");
					if(skipList.contains(Constants.SkipDate.MILLISECOND)
							&& skipList.contains(Constants.SkipDate.SECOND)
							&& skipList.contains(Constants.SkipDate.YEAR)
							&& skipList.contains(Constants.SkipDate.HOUR)
							&& skipList.contains(Constants.SkipDate.DAY_OF_WEEK)
							&& skipList.contains(Constants.SkipDate.MONTH)
							&& skipList.contains(Constants.SkipDate.MINUTE)
							&& skipList.contains(Constants.SkipDate.DAY)
							) {
//						result = null;
						result = new Date((long) dateMap.get("value"));
					}else {
						result = new Date((long) dateMap.get("value"));
					}
				}else {
					result = new Date((long) dateMap.get("value"));
				}				
			} catch (Exception e) {
				result = new Date(new Long(757675171));
			}
			return result;
		}
	}
	public static boolean isValidDateFormat(String dateString) {
       
        try 
        {
        	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
     		sdf.setLenient(false);
     		sdf.parse(dateString);
            return true;
        } 
        catch(ParseException e) 
        {
            return false;
        }
    }
	
	@SuppressWarnings("removal")
	public static Date getInstanDate(Object calObject, DateConverter dateConverter) {
		Date result = null;
		if (calObject == null) {
			return new Date(System.currentTimeMillis());
		} else {
			try {
				if (calObject instanceof String) {
					String date = (String) calObject;
					dateConverter.convertStringToDateXML(date);
					return dateConverter.convertStringToDateXML(date);
				}
				@SuppressWarnings("unchecked")
				Map<String, Object> dateMap = (Map<String, Object>) calObject;
				if(dateMap.get("skip") != null && dateMap.get("skip") instanceof ArrayList) {
					@SuppressWarnings("unchecked")
					ArrayList<String> skipList = (ArrayList<String>) dateMap.get("skip");
					if(skipList.contains(Constants.SkipDate.MILLISECOND)
							&& skipList.contains(Constants.SkipDate.SECOND)
							&& skipList.contains(Constants.SkipDate.YEAR)
							&& skipList.contains(Constants.SkipDate.HOUR)
							&& skipList.contains(Constants.SkipDate.DAY_OF_WEEK)
							&& skipList.contains(Constants.SkipDate.MONTH)
							&& skipList.contains(Constants.SkipDate.MINUTE)
							&& skipList.contains(Constants.SkipDate.DAY)
							) {
						result = null;
					}else {
						result = new Date((long) dateMap.get("value"));
					}
				}else {
					result = new Date((long) dateMap.get("value"));
				}				
			} catch (Exception e) {
				result = new Date(new Long(757675171));
			}
			return result;
		}
	}
	
	public String getInstanDateString(Object calObject, DateConverter dateConverter) {
		String result = null;
		String date = null;
		Date result1 = null;
		if (calObject == null) {
			return result;
		} else {
			try {
				@SuppressWarnings("unchecked")
				Map<String, Object> dateMap = (Map<String, Object>) calObject;
				if(dateMap.get("skip") != null && dateMap.get("skip") instanceof ArrayList) {
					@SuppressWarnings("unchecked")
					ArrayList<String> skipList = (ArrayList<String>) dateMap.get("skip");
					result1 = new Date((long) dateMap.get("value"));
					date = dateConverter.convertDateToSkipTime(result1);
					String[] dt = date.split(":");
					if (dt.length > 0 && dt.length < 4) {
						if (skipList.contains(Constants.SkipDate.HOUR)) {
							result = "*:"+dt[1]+":"+dt[2];
						}
						if(skipList.contains(Constants.SkipDate.HOUR)
								&& skipList.contains(Constants.SkipDate.MINUTE)) {
							result = "*:*:"+dt[2];
						}
						if(skipList.contains(Constants.SkipDate.HOUR)
								&& skipList.contains(Constants.SkipDate.MINUTE)
								&& skipList.contains(Constants.SkipDate.SECOND)) {
							result = "*:*:*";
						}
						if (result == null) {
							result = date;
						}
					}else {
						result = dateConverter.convertDateToString(new Date((long) dateMap.get("value")));
					}		
				}	
				else {
					result = dateConverter.convertDateToString(new Date((long) dateMap.get("value")));
				}
			} catch (Exception e) {
				return result;
			}
			return result;
		}
	}

	public static Double getDoubleTypeObject(Object object, Integer divident) {
		double val = 0;
		if (object != null) {
			val = Double.valueOf(String.valueOf(object));
			if (val != 0)
				return (val / divident);
			else
				return val;
		}
		return val;
	}

	public static Integer convertIntegerTypeObject(Object object) {
		int val = 0;
		if (object instanceof Double) {
			val = ((Double) object).intValue();
		} 
		else if(object instanceof String) {
			val = Integer.valueOf((String) object);
		}
		else
			val = (Integer) object;

		return val;
	}

	/*
	 * convert data to long type.
	*/
	public static long convertLongTypeObject(Object object) {
	    long val = 0;
	    
	        if (object instanceof Double) {
	            val = ((Double) object).longValue();
	        }
	        else if (object instanceof String) {
	            val = Long.parseLong((String) object);
	        }
	        else if (object instanceof Integer) {
	            val = ((Integer) object).longValue();
	        }
	        else if (object instanceof Long) {
	            val = (Long) object;
	        }
	    return val;
	}


	public static Integer checkEnableDisableLoad(Object object) {
		int val = 0;
		try {
			boolean flag = (boolean) object;
			val = (flag == true ? 1 : 0);
		} catch (Exception e) {
			val = 2;
		}
		return val;
	}

	public static HttpEntity<?> getHttpEntity(Object object, MediaType mediaType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(mediaType));

		return new HttpEntity<>(object, headers);
	}

	public Devices setDevicesInfo(DevicesInfo deviceInfo, DeviceCommand deviceCommand, long currentTime)
			throws Exception {
		if (!StringUtils.isEmpty(deviceInfo)) {
			
			Devices devices = getDevicesKeyMaster(deviceInfo);

			if (isValid()) {
				devices.setIpAddress(deviceInfo.getIp_address_main());
				devices.setPort(
						deviceInfo.getIp_port_main() != null ? Integer.parseInt(deviceInfo.getIp_port_main()) : 4059);
				devices.setSerialNumber(deviceInfo.getDevice_serial_number());
				devices.setDeviceType(deviceInfo.getDevice_type());
				devices.setCount(deviceInfo.getBill_count());
				devices.setBillingCommDatetime(deviceInfo.getLastbillingreaddatetime());
			}

			if (deviceCommand != null) {
				if (!StringUtils.isEmpty(deviceCommand.getDailyRangeDate()) && isValid()) {
					Map<String, String> dailyMap = deviceCommand.getDailyRangeDate();
					devices.setDailyLpStartDate(dailyMap.get(Constants.DateRange.STARTDATE));
					devices.setDailyLpEndDate(dailyMap.get(Constants.DateRange.ENDDATE)!= null ? dailyMap.get(Constants.DateRange.ENDDATE) : dateConverter.convertDateToString(new Date(currentTime)));
				} 
				else if (!StringUtils.isEmpty(deviceCommand.getDeltaRangeDate()) && isValid()) {
					Map<String, String> deltaMap = deviceCommand.getDeltaRangeDate();
					devices.setDeltaLpStartDate(deltaMap.get(Constants.DateRange.STARTDATE));
					devices.setDeltaLpEndDate(deltaMap.get(Constants.DateRange.ENDDATE)!= null ? deltaMap.get(Constants.DateRange.ENDDATE) : dateConverter.convertDateToString(new Date(currentTime)));
				} 
				else if (!StringUtils.isEmpty(deviceCommand.getStartDate()) && isValid()) {
					devices.setDeltaLpStartDate(deviceCommand.getStartDate());
					devices.setDeltaLpEndDate(dateConverter.convertDateToString(new Date(currentTime)));
					devices.setDailyLpStartDate(deviceCommand.getStartDate());
					devices.setDailyLpEndDate(dateConverter.convertDateToString(new Date(currentTime)));
				} else if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(deviceCommand.getCommand())) {
					setStartEndDate(devices, deviceInfo.getLastdeltalp_range_readdatetime(),
							deviceCommand.getCommand());
					devices.setDeltaLpStartDate(addSubHours(dateConverter.convertStringToDate(devices.getStartDate()), 1, "M"));
					devices.setDeltaLpEndDate(devices.getEndDate());
				} else if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(deviceCommand.getCommand())) {
					setStartEndDate(devices, deviceInfo.getLastdailylp_range_readdatetime(),
							deviceCommand.getCommand());
					devices.setDailyLpStartDate(devices.getStartDate());
					devices.setDailyLpEndDate(devices.getEndDate());
				} else if (ConfigCommands.FULL_DATA_READ.commandName.equals(deviceCommand.getCommand())) {
					devices.setObisCodeList(deviceCommand.getObisCodeList());

					setStartEndDate(devices, deviceInfo.getLastdeltalp_range_readdatetime(),
							ConfigCommands.DELTA_LOAD_PROFILE.commandName);
					devices.setDeltaLpStartDate(addSubHours(dateConverter.convertStringToDate(devices.getStartDate()), 1, "M"));
					devices.setDeltaLpEndDate(devices.getEndDate());

					setStartEndDate(devices, deviceInfo.getLastdailylp_range_readdatetime(),
							ConfigCommands.DAILY_LOAD_PROFILE.commandName);
					devices.setDailyLpStartDate(devices.getStartDate());
					devices.setDailyLpEndDate(devices.getEndDate());
				}

			}
			return devices;
		}
		return null;
	}
	

	public Devices setDevicesInfo(DevicesInfo deviceInfo) {
			
		LOG.info("Fetching device configuration from meter pwd");

		try {
			if (!StringUtils.isEmpty(deviceInfo)) {
			
				Devices devices = getDevicesKeyMaster(deviceInfo);

			
					devices.setIpAddress((deviceInfo.getIp_address_main() != null && !deviceInfo.getIp_address_main().isEmpty())? deviceInfo.getIp_address_main().trim() : null);
					devices.setPort(
							deviceInfo.getIp_port_main() != null ? Integer.parseInt(deviceInfo.getIp_port_main()) : 4059);
					devices.setSerialNumber(deviceInfo.getDevice_serial_number());
					devices.setDeviceType(deviceInfo.getDevice_type());
					devices.setCount(deviceInfo.getBill_count() != null? deviceInfo.getBill_count():null);
					devices.setBillingCommDatetime(deviceInfo.getLastbillingreaddatetime());
					devices.setMeterNICId((deviceInfo.getMeter_nic_id() != null && !deviceInfo.getMeter_nic_id().isEmpty())? deviceInfo.getMeter_nic_id().trim() : null);

					devices.setAuthKey(deviceInfo.getAuthkey() != null ? deviceInfo.getAuthkey() : devices.getAuthKey());
					devices.setCipheringKey(deviceInfo.getCipheringkey() != null ? deviceInfo.getCipheringkey() : devices.getCipheringKey());
					devices.setFirwarePwd(deviceInfo.getFirmwarepwd() != null ? deviceInfo.getFirmwarepwd() : devices.getFirwarePwd());
					devices.setHighPwd(deviceInfo.getHighpwd() != null ? deviceInfo.getHighpwd() : devices.getHighPwd());
					devices.setLowPwd(deviceInfo.getLowpwd() != null ? deviceInfo.getLowpwd() : devices.getLowPwd());
				
				return devices;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;

	}

	private Devices getDevicesKeyMaster(DevicesInfo deviceInfo){
		Devices devices = new Devices();
		try {
		Map<String, Devices> devicesMasterInfoPair = MeterDBAppStarter.devicesMasterInfo;

		
		String key = (deviceInfo.getMeter_type() == null || deviceInfo.getMeter_type().equalsIgnoreCase("JPM") ? "IHM"
				: deviceInfo.getMeter_type())+ ":" + deviceInfo.getDevice_type()+":"+deviceInfo.getMode_of_comm();
		if(!devicesMasterInfoPair.containsKey(key)) {
			key = deviceInfo.getMeter_type().equalsIgnoreCase("JPM") ? "IHM"+":"+deviceInfo.getMode_of_comm() : deviceInfo.getMeter_type()+":"+deviceInfo.getMode_of_comm();
		}
		
		Devices devicesMasterInfo = devicesMasterInfoPair.get(key);
		if(devicesMasterInfo != null) {
			devices = mapper.readValue(mapper.writeValueAsString(devicesMasterInfo), Devices.class);
		}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return devices;
	}
	
	public Devices setFullDataDevicesInfo(DevicesInfo deviceInfo, DeviceCommand deviceCommand, long currentTime)
			throws Exception {
		if (!StringUtils.isEmpty(deviceInfo)) {
			
			Devices devices = getDevicesKeyMaster(deviceInfo);

			if (isValid()) {
				devices.setIpAddress(deviceInfo.getIp_address_main());
				devices.setPort(
						deviceInfo.getIp_port_main() != null ? Integer.parseInt(deviceInfo.getIp_port_main()) : 4059);
				devices.setSerialNumber(deviceInfo.getDevice_serial_number());
				devices.setDeviceType(deviceInfo.getDevice_type());
				devices.setCount(deviceInfo.getBill_count());
				devices.setBillingCommDatetime(deviceInfo.getLastbillingreaddatetime());
			}

			if (deviceCommand != null) {
				 if (ConfigCommands.FULL_DATA_READ.commandName.equals(deviceCommand.getCommand())) {
					devices.setObisCodeList(deviceCommand.getObisCodeList());

					if (deviceCommand.getDeltaRangeDate() != null) {
						Map<String, String> deltaMap = deviceCommand.getDeltaRangeDate();
						
						if (deltaMap.get(Constants.DateRange.STARTDATE) != null) {
							devices.setDeltaLpStartDate(deltaMap.get(Constants.DateRange.STARTDATE));
						}else {
							setStartEndDate(devices, deviceInfo.getLastdeltalp_range_readdatetime(),
									ConfigCommands.DELTA_LOAD_PROFILE.commandName);
							devices.setDeltaLpStartDate(addSubHours(dateConverter.convertStringToDate(devices.getStartDate()), 1, "M"));
							devices.setDeltaLpEndDate(devices.getEndDate());
						}
						
						if(deltaMap.get(Constants.DateRange.ENDDATE)!= null) {							
							devices.setDeltaLpEndDate(deltaMap.get(Constants.DateRange.ENDDATE));
						}else {	
							devices.setDeltaLpEndDate(dateConverter.convertDateToString(new Date(System.currentTimeMillis())));
						}
						
					} 
					else {
						setStartEndDate(devices, deviceInfo.getLastdeltalp_range_readdatetime(),
								ConfigCommands.DELTA_LOAD_PROFILE.commandName);
						devices.setDeltaLpStartDate(addSubHours(dateConverter.convertStringToDate(devices.getStartDate()), 1, "M"));
						devices.setDeltaLpEndDate(devices.getEndDate());
					}
					
					if (deviceCommand.getDailyRangeDate() != null) {
						Map<String, String> dailyMap = deviceCommand.getDailyRangeDate();
						
						if (dailyMap.get(Constants.DateRange.STARTDATE) != null) {
							devices.setDailyLpStartDate(dailyMap.get(Constants.DateRange.STARTDATE));
						}else {
							setStartEndDate(devices, deviceInfo.getLastdailylp_range_readdatetime(),
									ConfigCommands.DAILY_LOAD_PROFILE.commandName);
							devices.setDailyLpStartDate(addSubHours(dateConverter.convertStringToDate(devices.getStartDate()), 1, "M"));
							devices.setDailyLpEndDate(devices.getEndDate());
						}
						
						if(dailyMap.get(Constants.DateRange.ENDDATE)!= null) {							
							devices.setDailyLpEndDate(dailyMap.get(Constants.DateRange.ENDDATE));
						}else {	
							devices.setDailyLpEndDate(dateConverter.convertDateToString(new Date(System.currentTimeMillis())));
						}
						
					}else {
						setStartEndDate(devices, deviceInfo.getLastdailylp_range_readdatetime(),
								ConfigCommands.DAILY_LOAD_PROFILE.commandName);
						devices.setDailyLpStartDate(devices.getStartDate());
						devices.setDailyLpEndDate(devices.getEndDate());
					}
				}

			}
			return devices;
		}
		return null;
	}
	
	public SingleConnectionCommandLogs setRangeDate(Devices devices, DevicesInfo deviceInfo, 
			SingleConnectionCommandLogs deviceCommand, long currentTime, DevicesCommandLog command, String commandName)
			throws Exception {
		if (!StringUtils.isEmpty(deviceInfo)) {
			
				if (command != null) {
					if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(commandName)) {
						
						if (command.getDelta_range_date() != null && command.getDelta_range_date().size() > 0) {
							Map<String, String> deltaMap = command.getDelta_range_date();
							
							if (deltaMap.get(Constants.DateRange.STARTDATE) != null) {
								deviceCommand.setDeltaLpStartDate(deltaMap.get(Constants.DateRange.STARTDATE));
							}else {
								setStartEndDate(devices, deviceInfo.getLastdeltalp_range_readdatetime(),
										ConfigCommands.DELTA_LOAD_PROFILE.commandName);
								deviceCommand.setDeltaLpStartDate(addSubHours(dateConverter.convertStringToDate(devices.getStartDate()), 1, "M"));
								deviceCommand.setDeltaLpEndDate(devices.getEndDate());
							}
							
							if(deltaMap.get(Constants.DateRange.ENDDATE)!= null) {							
								deviceCommand.setDeltaLpEndDate(deltaMap.get(Constants.DateRange.ENDDATE));
							}else {	
								deviceCommand.setDeltaLpEndDate(dateConverter.convertDateToString(new Date(System.currentTimeMillis())));
							}
				
						} 
						else {
							setStartEndDate(devices, deviceInfo.getLastdeltalp_range_readdatetime(),
									ConfigCommands.DELTA_LOAD_PROFILE.commandName);
							deviceCommand.setDeltaLpStartDate(addSubHours(dateConverter.convertStringToDate(devices.getStartDate()), 1, "M"));
							deviceCommand.setDeltaLpEndDate(devices.getEndDate());
						}
						
					}
					
					if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(commandName)) {
						if (command.getDaily_range_date() != null && command.getDaily_range_date().size() > 0) {
							Map<String, String> dailyMap = command.getDaily_range_date();
							
							if (dailyMap.get(Constants.DateRange.STARTDATE) != null) {
								deviceCommand.setDailyLpStartDate(dailyMap.get(Constants.DateRange.STARTDATE));
							}else {
								setStartEndDate(devices, deviceInfo.getLastdailylp_range_readdatetime(),
										ConfigCommands.DAILY_LOAD_PROFILE.commandName);
								deviceCommand.setDailyLpStartDate(addSubHours(dateConverter.convertStringToDate(devices.getStartDate()), 1, "M"));
								deviceCommand.setDailyLpEndDate(devices.getEndDate());
							}
							
							if(dailyMap.get(Constants.DateRange.ENDDATE)!= null) {							
								deviceCommand.setDailyLpEndDate(dailyMap.get(Constants.DateRange.ENDDATE));
							}else {	
								deviceCommand.setDailyLpEndDate(dateConverter.convertDateToString(new Date(System.currentTimeMillis())));
							}
							
						}else {
							setStartEndDate(devices, deviceInfo.getLastdailylp_range_readdatetime(),
									ConfigCommands.DAILY_LOAD_PROFILE.commandName);
							deviceCommand.setDailyLpStartDate(devices.getStartDate());
							deviceCommand.setDailyLpEndDate(devices.getEndDate());
						}
					}
					
					deviceCommand.setCommandType(Constants.COMMAND_READ);
				
			}
			return deviceCommand;
		}
		return null;
	}
	

	public Devices setDevicesInfoForConfig(DevicesInfo deviceInfo) throws Exception {
		if (!StringUtils.isEmpty(deviceInfo) && isValid()) {
			
			Devices devices = getDevicesKeyMaster(deviceInfo);

			if (devices != null) {
				devices.setIpAddress(deviceInfo.getIp_address_main());
				devices.setPort(
						deviceInfo.getIp_port_main() != null ? Integer.parseInt(deviceInfo.getIp_port_main()) : 4059);
				devices.setSerialNumber(deviceInfo.getDevice_serial_number());
				devices.setDeviceType(deviceInfo.getDevice_type());
				devices.setCount(deviceInfo.getBill_count());
				devices.setBillingCommDatetime(deviceInfo.getLastbillingreaddatetime());
				
				return devices;
			}
			
		}
		return null;
	}

	public Devices setDevicesInfoForFirmwareData(DevicesInfo deviceInfo, FirmwareData firmwareData) throws Exception {
		if (!StringUtils.isEmpty(deviceInfo) && isValid()) {
			Map<String, Devices> devicesMasterInfoPair = MeterDBAppStarter.devicesMasterInfo;

			String key = (deviceInfo.getMeter_type() == null || deviceInfo.getMeter_type().equalsIgnoreCase("JPM") ? "IHM"
					: deviceInfo.getMeter_type())+ ":" + deviceInfo.getDevice_type()+":"+deviceInfo.getMode_of_comm();
			if(!devicesMasterInfoPair.containsKey(key)) {
				key = deviceInfo.getMeter_type();
			}
				
			Devices devicesMasterInfo = devicesMasterInfoPair.get(key);
			
			Devices devices = CommonUtils.getMapper().readValue(CommonUtils.getMapper().writeValueAsString(devicesMasterInfo), Devices.class);

			devices.setIpAddress(deviceInfo.getIp_address_main());
			devices.setPort(
					deviceInfo.getIp_port_main() != null ? Integer.parseInt(deviceInfo.getIp_port_main()) : 4059);
			devices.setSerialNumber(deviceInfo.getDevice_serial_number());
			devices.setDeviceType(deviceInfo.getDevice_type());
			devices.setCount(deviceInfo.getBill_count());
			devices.setBillingCommDatetime(deviceInfo.getLastbillingreaddatetime());

			devices.setFirmwareData(firmwareData);
			devices.setFirwarePwd(devicesMasterInfo.getFirwarePwd());
			
			return devices;
		}
		return null;
	}

	public static ObjectMapper getMapper() {
		return mapper;
	}
	
	public static ObjectMapper getLogMapper() {
		return logMapperLongToDateFormat;
	}

	public static void setMapper(ObjectMapper mapper) {
		CommonUtils.mapper = mapper;
	}

	public static String getEventName(Object eventNumber) {
		return (String) MeterDBAppStarter.eventProperties.get(String.valueOf(eventNumber));
	}

	public static int getDivFactor(int index, String commandName, String dev) {
		int divFactorVal = 1;
		try {
			String divFactor = MeterDBAppStarter.meterDivFactorProperties.getProperty(commandName);
			String divFactorArr[] = divFactor.split("-");
			for (int i = 0; i < divFactorArr.length; i++) {
				String divFactorValArr[] = divFactorArr[i].split(":");
				if (Integer.parseInt(divFactorValArr[0]) == index) {
					divFactorVal = Integer.parseInt(divFactorValArr[1]);
					break;
				}
			}
		} catch (Exception e) {
			LOG.error("Issue in getting div Factor due to : " + e.getMessage() + " command: " + commandName
					+ " For device : " + dev + " & index: " + index);
		}
		return divFactorVal;
	}

	public long dateDiffInMins(Date lastCommDate) {
		long timeDiffInMins = 0;
		Date currentDate = new Date(System.currentTimeMillis());
		try {
			long timeDiff = currentDate.getTime() - lastCommDate.getTime();
			timeDiffInMins = (timeDiff / (1000 * 60)) % 60;
		} catch (Exception e) {
			LOG.error("Error in finding date difference: " + e.getMessage() + " dates 1:" + currentDate + " date 2: "
					+ lastCommDate);
		}
		return timeDiffInMins;

	}

	public boolean isLessThanCurrentDate(Date lastCommDate) {
		boolean flag = false;
		try {
			Date currentDate = new Date(System.currentTimeMillis());

			long timeDiff = currentDate.getTime() - lastCommDate.getTime();
			long timeDiffInMins = (timeDiff / (1000 * 60)) % 60;
			if (timeDiffInMins >= 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

	public static Double getObisDoubleObject(List<Object> data, List<String> obisCodeList, String obisCode,
			String commandName, String dev) {
		Double val = 0.0;

		if (!obisCodeList.contains(obisCode)) {
			return null;
		}

		int index = obisCodeList.indexOf(obisCode);
		try {
			val = getDoubleTypeObject(data.get(index), getDivFactor(index, commandName, dev));
		} catch (Exception e) {
			LOG.error("Value is missing for index:" + index);
		}

		return val;
	}

	public static String getObisStringObject(List<Object> data, List<String> obisCodeList, String obisCode) {
		String val = null;

		if (!obisCodeList.contains(obisCode)) {
			return val;
		}

		int index = obisCodeList.indexOf(obisCode);
		try {
			val = String.valueOf(data.get(index));
		} catch (Exception e) {
			LOG.error("Value is missing for index:" + index);
		}
		return val;
	}

	public boolean setDatasetsReadDatetimeInDevicesInfo(Devices devices, DevicesInfo deviceInfo,
			MeterResponse meterResponse, String command, List<FullDataModel> responseList) throws ParseException {
		boolean flag = false;
		String dataSaveStatus = "";
		List<String> obisCodeList = meterResponse.getObisCode();
		if (responseList != null) {
			for (FullDataModel fullDataModel : responseList) {
				if (command.equalsIgnoreCase(fullDataModel.getCmdName())) {
					dataSaveStatus = fullDataModel.getMessage();
					break;
				}
			}
		} else {
			/*
			 * if(meterResponse.getObisCode() != null && meterResponse.getObisCode().size()
			 * > 0 && meterResponse.getData() != null && meterResponse.getData().length ==
			 * 0) {
			 * 
			 * if(ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(command) &&
			 * devices.getDlpDayDiff() == 0) { flag = true; dataSaveStatus =
			 * Constants.SUCCESS; } else dataSaveStatus = Constants.IN_PROGRESS;
			 * 
			 * }
			 */
			if (meterResponse.getObisCode() != null && meterResponse.getObisCode().size() > 0) {
				dataSaveStatus = Constants.SUCCESS;
				if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(command) && devices.getDlpDayDiff() == 0) {
					flag = true;
				}
			} else {
				dataSaveStatus = meterResponse.getMessage();
			}
		}

		if (dataSaveStatus.equalsIgnoreCase(Constants.IN_PROGRESS)) {
			if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(command) && devices.getLpDayDiff() > 10) {
				deviceInfo.setLastdeltalp_range_readdatetime(devices.getDeltaLpEndDate() != null
						? dateConverter.convertStringToDate(devices.getDeltaLpEndDate())
						: null);
			} else if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(command) && devices.getDlpDayDiff() > 10) {
				deviceInfo.setLastdailylp_range_readdatetime(devices.getDailyLpEndDate() != null
						? dateConverter.convertStringToDate(devices.getDailyLpEndDate())
						: null);
			}
		}

		if (meterResponse.getStatus() == 200 && dataSaveStatus.equalsIgnoreCase(Constants.SUCCESS)) {
			deviceInfo.setLastcommunicationdatetime(new Date(System.currentTimeMillis()));

			if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(command)) {
				deviceInfo.setLastdeltalpreaddatetime(new Date(System.currentTimeMillis()));
				deviceInfo.setLastdeltalp_range_readdatetime(devices.getDeltaLpEndDate() != null
						? dateConverter.convertStringToDate(devices.getDeltaLpEndDate())
						: null);
			} else if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(command)) {
				deviceInfo.setLastdailylpreaddatetime(new Date(System.currentTimeMillis()));
				deviceInfo.setLastdailylp_range_readdatetime(devices.getDailyLpEndDate() != null
						? dateConverter.convertStringToDate(devices.getDailyLpEndDate())
						: null);
			} else if (ConfigCommands.BILLING_DATA.commandName.equals(command)) {
				deviceInfo.setLastbillingreaddatetime(new Date(System.currentTimeMillis()));
			} else if (ConfigCommands.INSTANTANEOUS_READ.commandName.equals(command)) {
				deviceInfo.setLastinstanteousreaddatetime(new Date(System.currentTimeMillis()));

				// This is temp added will be commented if not needed.
				Object responseData[] = meterResponse.getData();
				@SuppressWarnings("unchecked")
				List<Object> data = (List<Object>) responseData[0];

				String phaseVal = Constants.PhaseVal._1PH;
				if (Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
					phaseVal = Constants.PhaseVal._3PH;
				}
				else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
						|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
					phaseVal = Constants.PhaseVal._CT;
				}
				//String commandName = deviceInfo.getMeter_type() + "_" + phaseVal + "_" + Constants.INSTANTANEOUS_READ;

				String commandName = deviceInfo.getMeter_type()+"_"+deviceInfo.getVersion()+ "_" + phaseVal + "_" +Constants.INSTANTANEOUS_READ;
				if(MeterDBAppStarter.meterDivFactorProperties.getProperty(commandName) == null) {
					commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.INSTANTANEOUS_READ;
				}
				
				if (Constants.DeviceTypes.SINGLE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
					
					if("FLA".equalsIgnoreCase(deviceInfo.getMeter_type()) 
							|| "AME".equalsIgnoreCase(deviceInfo.getMeter_type()) 
							|| "RMI".equalsIgnoreCase(deviceInfo.getMeter_type())) {
						deviceInfo.setRelay_status((Integer) data.get(20) == 1 ? "UP" : "DOWN");
					}
					else{
						deviceInfo.setRelay_status((Boolean) data.get(20) == true ? "UP" : "DOWN");
					}

					deviceInfo.setMeter_datetime(CommonUtils.getInstanDate(data.get(0)));
					deviceInfo.setCumulative_energy_kvah_export(getDoubleTypeObject(data.get(19),
							getDivFactor(19, commandName, deviceInfo.getDevice_serial_number())));
					deviceInfo.setCumulative_energy_kvah_import(getDoubleTypeObject(data.get(9),
							getDivFactor(9, commandName, deviceInfo.getDevice_serial_number())));
					deviceInfo.setCumulative_energy_kwh_export(getDoubleTypeObject(data.get(18),
							getDivFactor(18, commandName, deviceInfo.getDevice_serial_number())));
					deviceInfo.setCumulative_energy_kwh_import(getDoubleTypeObject(data.get(8),
							getDivFactor(8, commandName, deviceInfo.getDevice_serial_number())));
					deviceInfo.setMaximum_demand_kva(getDoubleTypeObject(data.get(12),
							getDivFactor(12, commandName, deviceInfo.getDevice_serial_number())));
					deviceInfo.setMaximum_demand_kva_datetime(getInstanDate(data.get(13)));
					deviceInfo.setMaximum_demand_kw(getDoubleTypeObject(data.get(10),
							getDivFactor(10, commandName, deviceInfo.getDevice_serial_number())));
					deviceInfo.setMaximum_demand_kw_datetime(getInstanDate(data.get(11)));
					//deviceInfo.setLoad_limits((Double.valueOf(String.valueOf(data.get(21))) / 1));
					deviceInfo.setLoad_limits(obisCodeList.contains(Constants.ObisCode.INSTANT_1P.LOAD_LIMIT_VAL) ? getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.LOAD_LIMIT_VAL, commandName, deviceInfo.getDevice_serial_number()) : null);
					
					deviceInfo.setCumulative_tamper_count((Integer) data.get(15));
					deviceInfo.setBill_count((Integer) data.get(16));
				} else if (Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
					if("AME".equalsIgnoreCase(deviceInfo.getMeter_type()) || "RMI".equalsIgnoreCase(deviceInfo.getMeter_type())) {
						deviceInfo.setRelay_status(meterResponse.getObisCode().contains(Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_FUNCTION_STATUS) 
								? ((Integer)data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_FUNCTION_STATUS)) == 1 ? "UP": "DOWN") : null);
					}
					else{
						deviceInfo.setRelay_status(meterResponse.getObisCode().contains(Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_FUNCTION_STATUS) 
								? ((Boolean)data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_FUNCTION_STATUS)) == true ? "UP": "DOWN") : null);
					}
					deviceInfo.setMeter_datetime(CommonUtils.getInstanDate(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.RTC_DATETIME))));
					deviceInfo.setCumulative_energy_kvah_export(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_3P.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setCumulative_energy_kvah_import(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_3P.KAVH_IMPORT , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setCumulative_energy_kwh_export(CommonUtils.getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_3P.KWH_EXPORT , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setCumulative_energy_kwh_import(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_3P.KWH_IMPORT , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setMaximum_demand_kva(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_3P.MD_ACTIVE_IMPORT_KVA , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setMaximum_demand_kva_datetime(CommonUtils.getInstanDate(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.MD_ACTIVE_IMPORT_DATETIME_KVA)+1)));
					deviceInfo.setMaximum_demand_kw(getObisDoubleObject(data, meterResponse.getObisCode(), Constants.ObisCode.INSTANT_3P.MD_ACTIVE_IMPORT_KW , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setMaximum_demand_kw_datetime(getInstanDate(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.MD_ACTIVE_IMPORT_DATETIME_KW)+1)));
					deviceInfo.setLoad_limits(meterResponse.getObisCode().contains(Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_KW) ? 
						getObisDoubleObject(data, meterResponse.getObisCode(), Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_KW , commandName, deviceInfo.getDevice_serial_number()) : null);
					deviceInfo.setCumulative_tamper_count(convertIntegerTypeObject(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.TAMPER_COUNT))));
					deviceInfo.setBill_count(convertIntegerTypeObject(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.BILLING_COUNT))));

				}
				else if (Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())
						|| Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())) {
					
					deviceInfo.setRelay_status(meterResponse.getObisCode().contains(Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_FUNCTION_STATUS) 
							? ((Boolean)data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_FUNCTION_STATUS)) == true ? "UP": "DOWN") : null);
					deviceInfo.setMeter_datetime(CommonUtils.getInstanDate(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_CT.RTC_DATETIME))));
					deviceInfo.setCumulative_energy_kvah_export(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_CT.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setCumulative_energy_kvah_import(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_CT.KAVH_IMPORT , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setCumulative_energy_kwh_export(CommonUtils.getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_CT.KWH_EXPORT , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setCumulative_energy_kwh_import(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_CT.KWH_IMPORT , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setMaximum_demand_kva(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_CT.MD_ACTIVE_IMPORT_KVA , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setMaximum_demand_kva_datetime(CommonUtils.getInstanDate(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_CT.MD_ACTIVE_IMPORT_DATETIME_KVA)+1)));
					deviceInfo.setMaximum_demand_kw(getObisDoubleObject(data, meterResponse.getObisCode(), Constants.ObisCode.INSTANT_CT.MD_ACTIVE_IMPORT_KW , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setMaximum_demand_kw_datetime(getInstanDate(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_CT.MD_ACTIVE_IMPORT_DATETIME_KW)+1)));
					deviceInfo.setLoad_limits(meterResponse.getObisCode().contains(Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_KW) ? 
						getObisDoubleObject(data, meterResponse.getObisCode(), Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_KW , commandName, deviceInfo.getDevice_serial_number()) : null);
					deviceInfo.setCumulative_tamper_count(convertIntegerTypeObject(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_CT.TAMPER_COUNT))));
					deviceInfo.setBill_count(convertIntegerTypeObject(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_CT.BILLING_COUNT))));

				}
			} else if (!StringUtils.isEmpty(command) && command.contains("Events")) {
				deviceInfo.setLasteventsreaddatetime(new Date(System.currentTimeMillis()));
			}
		}

		return flag;
	}
	
	
	
	// Single Connection 
	public boolean setDatasetsReadDatetimeInDevicesInfo(Devices devices, DevicesInfo deviceInfo,
			SingleConnectionCommandLogs commandLogs) throws Exception {
		MeterResponse meterResponse = commandLogs.getMeterResponse();
		String command = commandLogs.getCommandName();
		boolean flag = false;
		String dataSaveStatus = "";
		List<String> obisCodeList = meterResponse.getObisCode();
		
		if (meterResponse.getObisCode() != null && meterResponse.getObisCode().size() > 0) {
			dataSaveStatus = Constants.SUCCESS;
			if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(command) && devices.getDlpDayDiff() == 0) {
					flag = true;
				}
			} else {
				dataSaveStatus = meterResponse.getMessage();
		}
		

		if (dataSaveStatus.equalsIgnoreCase(Constants.IN_PROGRESS)) {
			if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(command) && devices.getLpDayDiff() > 10) {
				deviceInfo.setLastdeltalp_range_readdatetime(commandLogs.getDeltaLpEndDate() != null
						? dateConverter.convertStringToDate(commandLogs.getDeltaLpEndDate())
						: null);
			} else if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(command) && devices.getDlpDayDiff() > 10) {
				deviceInfo.setLastdailylp_range_readdatetime(commandLogs.getDailyLpEndDate() != null
						? dateConverter.convertStringToDate(commandLogs.getDailyLpEndDate())
						: null);
			}
		}

		if (meterResponse.getStatus() == 200 && dataSaveStatus.equalsIgnoreCase(Constants.SUCCESS)) {
			deviceInfo.setLastcommunicationdatetime(new Date(System.currentTimeMillis()));

			if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(command)) {
				deviceInfo.setLastdeltalpreaddatetime(new Date(System.currentTimeMillis()));
				deviceInfo.setLastdeltalp_range_readdatetime(commandLogs.getDeltaLpEndDate() != null
						? dateConverter.convertStringToDate(commandLogs.getDeltaLpEndDate())
						: null);
			} else if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(command)) {
				deviceInfo.setLastdailylpreaddatetime(new Date(System.currentTimeMillis()));
				deviceInfo.setLastdailylp_range_readdatetime(commandLogs.getDailyLpEndDate() != null
						? dateConverter.convertStringToDate(commandLogs.getDailyLpEndDate())
						: null);
			} else if (ConfigCommands.BILLING_DATA.commandName.equals(command)) {
				deviceInfo.setLastbillingreaddatetime(new Date(System.currentTimeMillis()));
			} else if (ConfigCommands.INSTANTANEOUS_READ.commandName.equals(command)) {
				deviceInfo.setLastinstanteousreaddatetime(new Date(System.currentTimeMillis()));

				// This is temp added will be commented if not needed.
				Object responseData[] = meterResponse.getData();
				@SuppressWarnings("unchecked")
				List<Object> data = (List<Object>) responseData[0];

				String phaseVal = Constants.PhaseVal._1PH;
				if (Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
					phaseVal = Constants.PhaseVal._3PH;
				}
				else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
						|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
					phaseVal = Constants.PhaseVal._CT;
				}
				//String commandName = deviceInfo.getMeter_type() + "_" + phaseVal + "_" + Constants.INSTANTANEOUS_READ;

				String commandName = deviceInfo.getMeter_type()+"_"+deviceInfo.getVersion()+ "_" + phaseVal + "_" +Constants.INSTANTANEOUS_READ;
				if(MeterDBAppStarter.meterDivFactorProperties.getProperty(commandName) == null) {
					commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.INSTANTANEOUS_READ;
				}
				
				if (Constants.DeviceTypes.SINGLE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
					
					if("FLA".equalsIgnoreCase(deviceInfo.getMeter_type()) 
							|| "AME".equalsIgnoreCase(deviceInfo.getMeter_type()) 
							|| "RMI".equalsIgnoreCase(deviceInfo.getMeter_type())) {
						deviceInfo.setRelay_status((Integer) data.get(20) == 1 ? "UP" : "DOWN");
					}
					else{
						deviceInfo.setRelay_status((Boolean) data.get(20) == true ? "UP" : "DOWN");
					}

					deviceInfo.setMeter_datetime(CommonUtils.getInstanDate(data.get(0)));
					deviceInfo.setCumulative_energy_kvah_export(getDoubleTypeObject(data.get(19),
							getDivFactor(19, commandName, deviceInfo.getDevice_serial_number())));
					deviceInfo.setCumulative_energy_kvah_import(getDoubleTypeObject(data.get(9),
							getDivFactor(9, commandName, deviceInfo.getDevice_serial_number())));
					deviceInfo.setCumulative_energy_kwh_export(getDoubleTypeObject(data.get(18),
							getDivFactor(18, commandName, deviceInfo.getDevice_serial_number())));
					deviceInfo.setCumulative_energy_kwh_import(getDoubleTypeObject(data.get(8),
							getDivFactor(8, commandName, deviceInfo.getDevice_serial_number())));
					deviceInfo.setMaximum_demand_kva(getDoubleTypeObject(data.get(12),
							getDivFactor(12, commandName, deviceInfo.getDevice_serial_number())));
					deviceInfo.setMaximum_demand_kva_datetime(getInstanDate(data.get(13)));
					deviceInfo.setMaximum_demand_kw(getDoubleTypeObject(data.get(10),
							getDivFactor(10, commandName, deviceInfo.getDevice_serial_number())));
					deviceInfo.setMaximum_demand_kw_datetime(getInstanDate(data.get(11)));
					//deviceInfo.setLoad_limits((Double.valueOf(String.valueOf(data.get(21))) / 1));
					deviceInfo.setLoad_limits(obisCodeList.contains(Constants.ObisCode.INSTANT_1P.LOAD_LIMIT_VAL) ? getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.LOAD_LIMIT_VAL, commandName, deviceInfo.getDevice_serial_number()) : null);
					
					deviceInfo.setCumulative_tamper_count((Integer) data.get(15));
					deviceInfo.setBill_count((Integer) data.get(16));
				} else if (Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
					if("AME".equalsIgnoreCase(deviceInfo.getMeter_type()) || "RMI".equalsIgnoreCase(deviceInfo.getMeter_type())) {
						deviceInfo.setRelay_status(meterResponse.getObisCode().contains(Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_FUNCTION_STATUS) 
								? ((Integer)data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_FUNCTION_STATUS)) == 1 ? "UP": "DOWN") : null);
					}
					else{
						deviceInfo.setRelay_status(meterResponse.getObisCode().contains(Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_FUNCTION_STATUS) 
								? ((Boolean)data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_FUNCTION_STATUS)) == true ? "UP": "DOWN") : null);
					}
					deviceInfo.setMeter_datetime(CommonUtils.getInstanDate(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.RTC_DATETIME))));
					deviceInfo.setCumulative_energy_kvah_export(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_3P.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setCumulative_energy_kvah_import(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_3P.KAVH_IMPORT , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setCumulative_energy_kwh_export(CommonUtils.getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_3P.KWH_EXPORT , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setCumulative_energy_kwh_import(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_3P.KWH_IMPORT , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setMaximum_demand_kva(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_3P.MD_ACTIVE_IMPORT_KVA , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setMaximum_demand_kva_datetime(CommonUtils.getInstanDate(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.MD_ACTIVE_IMPORT_DATETIME_KVA)+1)));
					deviceInfo.setMaximum_demand_kw(getObisDoubleObject(data, meterResponse.getObisCode(), Constants.ObisCode.INSTANT_3P.MD_ACTIVE_IMPORT_KW , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setMaximum_demand_kw_datetime(getInstanDate(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.MD_ACTIVE_IMPORT_DATETIME_KW)+1)));
					deviceInfo.setLoad_limits(meterResponse.getObisCode().contains(Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_KW) ? 
						getObisDoubleObject(data, meterResponse.getObisCode(), Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_KW , commandName, deviceInfo.getDevice_serial_number()) : null);
					deviceInfo.setCumulative_tamper_count(convertIntegerTypeObject(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.TAMPER_COUNT))));
					deviceInfo.setBill_count(convertIntegerTypeObject(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_3P.BILLING_COUNT))));

				}
				else if (Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())
						|| Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())) {
					
					deviceInfo.setRelay_status(meterResponse.getObisCode().contains(Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_FUNCTION_STATUS) 
							? ((Boolean)data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_FUNCTION_STATUS)) == true ? "UP": "DOWN") : null);
					deviceInfo.setMeter_datetime(CommonUtils.getInstanDate(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_CT.RTC_DATETIME))));
					deviceInfo.setCumulative_energy_kvah_export(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_CT.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setCumulative_energy_kvah_import(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_CT.KAVH_IMPORT , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setCumulative_energy_kwh_export(CommonUtils.getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_CT.KWH_EXPORT , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setCumulative_energy_kwh_import(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_CT.KWH_IMPORT , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setMaximum_demand_kva(getObisDoubleObject(data, meterResponse.getObisCode(),
						Constants.ObisCode.INSTANT_CT.MD_ACTIVE_IMPORT_KVA , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setMaximum_demand_kva_datetime(CommonUtils.getInstanDate(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_CT.MD_ACTIVE_IMPORT_DATETIME_KVA)+1)));
					deviceInfo.setMaximum_demand_kw(getObisDoubleObject(data, meterResponse.getObisCode(), Constants.ObisCode.INSTANT_CT.MD_ACTIVE_IMPORT_KW , commandName, deviceInfo.getDevice_serial_number()));
					deviceInfo.setMaximum_demand_kw_datetime(getInstanDate(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_CT.MD_ACTIVE_IMPORT_DATETIME_KW)+1)));
					deviceInfo.setLoad_limits(meterResponse.getObisCode().contains(Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_KW) ? 
						getObisDoubleObject(data, meterResponse.getObisCode(), Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_KW , commandName, deviceInfo.getDevice_serial_number()) : null);
					deviceInfo.setCumulative_tamper_count(convertIntegerTypeObject(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_CT.TAMPER_COUNT))));
					deviceInfo.setBill_count(convertIntegerTypeObject(data.get(meterResponse.getObisCode().indexOf(Constants.ObisCode.INSTANT_CT.BILLING_COUNT))));

				}
			} else if (!StringUtils.isEmpty(command) && command.contains("Events")) {
				deviceInfo.setLasteventsreaddatetime(new Date(System.currentTimeMillis()));
			}
		}

		return flag;
	}

	public void setDevicesInfoFromFullData(Devices devices, DevicesInfo deviceInfo,
			FullDataMeterResponse fullDataMeterResponse) throws ParseException {
		for (MeterResponse meterResponse : fullDataMeterResponse.getResponse()) {
			if (meterResponse.getStatus() == 200 && meterResponse.getObisCode() != null) {
				setDatasetsReadDatetimeInDevicesInfo(devices, deviceInfo, meterResponse, meterResponse.getObisCmd(),
						fullDataMeterResponse.getResponseList());
			}
		}
	}

	/**
	 * Used to get the response entity.
	 * 
	 * @param devices
	 * @param cmdName
	 * @return
	 */
	public ResponseEntity<?> getResponseEntity(Devices devices, String cmdName) {
		ResponseEntity<?> response = null;
		try {
			if (ConfigCommands.FULL_DATA_READ.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getFullDataRead(),
						HttpMethod.POST, CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			}
			else if (ConfigCommands.FULL_PREPAY_DATA_READ.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getFullPrepayDataRead(),
						HttpMethod.POST, CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			} 
			else if (ConfigCommands.INSTANTANEOUS_READ.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getInstantRead(),
						HttpMethod.POST, CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			}
			else if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getDailyLPRead(),
						HttpMethod.POST, CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			}
			else if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getDeltaLPRead(),
						HttpMethod.POST, CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			}
			else if (ConfigCommands.BILLING_DATA.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getBillingData(),
						HttpMethod.POST, CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			}
			else if (ConfigCommands.POWER_RELATED_EVENTS.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getPowerRelatedEvents(),
						HttpMethod.POST, CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			} 
			else if (ConfigCommands.VOLTAGE_RELATED_EVENTS.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getVoltageRelatedEvents(), HttpMethod.POST,
						CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			} 
			else if (ConfigCommands.TRANSACTION_RELATED_EVENTS.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getTransactionRelatedEvents(), HttpMethod.POST,
						CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			} 
			else if (ConfigCommands.CURRENT_RELATED_EVENTS.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getCurrentRelatedEvents(), HttpMethod.POST,
						CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			} 
			else if (ConfigCommands.OTHER_RELATED_EVENTS.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getOtherRelatedEvents(),
						HttpMethod.POST, CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			}
			else if (ConfigCommands.NON_ROLLOVER_RELATED_EVENTS.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getNonRolloverEvents(),
						HttpMethod.POST, CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			}
			else if (ConfigCommands.CONTROL_RELATED_EVENTS.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getControlRelatedEvents(), HttpMethod.POST,
						CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			} 
			else if (ConfigCommands.CONNECT.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getConnect(),
						HttpMethod.POST, CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			} 
			else if (ConfigCommands.DISCONNECT.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getDisconnect(),
						HttpMethod.POST, CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			} 
			else if (ConfigCommands.NAME_PLATES.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getNamePlateRead(),
						HttpMethod.POST, CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			} 
			else if (ConfigCommands.FW_UPDATE.commandName.equals(cmdName)) {
				response = restTemplate.exchange(meterCommands.getAppAddress() + meterCommands.getFirmwareUpgrade(),
						HttpMethod.POST, CommonUtils.getHttpEntity(devices, MediaType.APPLICATION_JSON), String.class);
			}

		} catch (RestClientException e) {
			LOG.error("Error in connecting with devices over TCP due to :" + e.getMessage());
			throw e;
		}
		return response;
	}

	public long dateDiffWithCurrentDate(Date lastCommDate, String dev) throws Exception {
		long dayDiff = 0;
		try {
			Date currentCommunicationDate = new Date(System.currentTimeMillis());
			;
			Date lastCommunicationDate = lastCommDate;
			long diff = currentCommunicationDate.getTime() - lastCommunicationDate.getTime();
			dayDiff = diff / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			LOG.error("Error in finding date difference due to :" + e.getMessage() + " dev: " + dev
					+ " : lastCommDate :" + lastCommDate);
		}
		return dayDiff;
	}

	private String addDateDay(Date lastCommDate, long days, String type) throws Exception {
		String modifiedDate = "";
		try {
			LocalDateTime date1 = LocalDateTime.now();
			if ("M".equalsIgnoreCase(type)) {
				date1 = lastCommDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().minusDays(days);
			} else if ("P".equalsIgnoreCase(type)) {
				date1 = lastCommDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(days);
			}
			Date outDate = Date.from(date1.atZone(ZoneId.systemDefault()).toInstant());

			modifiedDate = dateConverter.convertDateToString(outDate);

			return modifiedDate;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error in adding days to date due to: " + e.getMessage());
			throw e;
		}
	}
	
	private String addSubHours(Date lastCommDate, long days, String type) throws Exception {
		String modifiedDate = "";
		try {
			LocalDateTime date1 = LocalDateTime.now();
			if ("M".equalsIgnoreCase(type)) {
				date1 = lastCommDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().minusHours(days);
			} else if ("P".equalsIgnoreCase(type)) {
				date1 = lastCommDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusHours(days);
			}
			Date outDate = Date.from(date1.atZone(ZoneId.systemDefault()).toInstant());

			modifiedDate = dateConverter.convertDateToString(outDate);

			return modifiedDate;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error in adding days to date due to: " + e.getMessage());
			throw e;
		}
	}

	public void setStartEndDate(Devices devicesMasterInfo, Date lastDate, String cmdNamd) throws Exception {
		long dayRangeToRead = meterCommands.getMaxdayRangeOnceRead() != null ? Integer.parseInt(meterCommands.getMaxdayRangeOnceRead()) : 1;
		int dayDiff = 0;
		try {
			if (lastDate == null) {
				devicesMasterInfo.setStartDate(
						addDateDay(new Date(System.currentTimeMillis()), meterCommands.getLastLPRead(), "M"));
				devicesMasterInfo.setEndDate(addDateDay(new Date(System.currentTimeMillis()),
						meterCommands.getLastLPRead() - dayRangeToRead, "M"));
				dayDiff = (int) (meterCommands.getLastLPRead() - dayRangeToRead);
			} else {
				long dayDifference = dateDiffWithCurrentDate(lastDate, devicesMasterInfo.getSerialNumber());
				dayDiff = (int) dayDifference;

				if (dayDiff >= 0 && dayDiff <= dayRangeToRead) {
					// when 0-10 -- then set current date to last read date
					devicesMasterInfo.setStartDate(dateConverter.convertDateToString(lastDate));
					devicesMasterInfo
							.setEndDate(dateConverter.convertDateToString(new Date(System.currentTimeMillis())));
				} else if (dayDiff > dayRangeToRead && dayDiff <= meterCommands.getLastLPRead()) {
					// when 11-45
					devicesMasterInfo.setStartDate(dateConverter.convertDateToString(lastDate));
					devicesMasterInfo.setEndDate(addDateDay(lastDate, dayRangeToRead, "P"));
				} else {
                   // when more than 45 same as initial
					devicesMasterInfo.setStartDate(
							addDateDay(new Date(System.currentTimeMillis()), meterCommands.getLastLPRead(), "M"));
					devicesMasterInfo.setEndDate(dateConverter.convertDateToString(new Date(System.currentTimeMillis())));
					dayDiff = (int) dayRangeToRead;
				}

			}

			if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(cmdNamd)) {
				devicesMasterInfo.setLpDayDiff(dayDiff);
			} else if (ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(cmdNamd)) {
				devicesMasterInfo.setDlpDayDiff(dayDiff);
			}
		} catch (Exception e) {
			e.printStackTrace();
			devicesMasterInfo
					.setStartDate(addDateDay(new Date(System.currentTimeMillis()), meterCommands.getLastLPRead(), "M"));
			devicesMasterInfo.setEndDate(dateConverter.convertDateToString(new Date(System.currentTimeMillis())));
		}
	}

	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static Date getTodayDate() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date date = new Date();
		return dateFormat.parse(dateFormat.format(date));
	}

	public static String getCurrentFirstDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public Date getPrevDate() throws ParseException {
	    int backDay=Integer.parseInt(meterCommands.getBackDays());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DATE, -backDay);
		return dateFormat.parse(dateFormat.format(calendar.getTime()));
	}
	
	public String getBlockLoadReadDate() throws ParseException {
	    int backDay=Integer.parseInt(meterCommands.getBlockLoadBackDays());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DATE, -backDay);
		return dateFormat.format(calendar.getTime());
	}

	public Date getCurrentTime() throws ParseException{
            Date now=new Date();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.parse(dateFormat.format(now));
	}
	
	public boolean after() throws ParseException{
		boolean time=getCurrentTime().after(dateConverter.convertStringToTime(meterCommands.getStartTime()))&&
		getCurrentTime().before(dateConverter.convertStringToTime("23:59:00"));
		return time;
    }
	
	public boolean before() throws ParseException{
		boolean time=getCurrentTime().after(dateConverter.convertStringToTime("00:00:00"))&&
				getCurrentTime().before(dateConverter.convertStringToTime(meterCommands.getEndTime()));
		return time;
	}
	
	public Date adjustDateDay(Date lastCommDate, long days, String type) throws Exception {
		//String modifiedDate = "";
		try {
			LocalDateTime date1 = LocalDateTime.now();
			if ("M".equalsIgnoreCase(type)) {
				date1 = lastCommDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().minusDays(days);
			} else if ("P".equalsIgnoreCase(type)) {
				date1 = lastCommDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(days);
			}
			Date outDate = Date.from(date1.atZone(ZoneId.systemDefault()).toInstant());

			//modifiedDate = dateConverter.convertDateToString(outDate);

			return outDate;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error in adding days to date due to: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * if storeDate is greater the it returns 1.
	 * 
	 * @param storeDate
	 * @return
	 */
	public static int compareWithFirstDayOfMonth(Date storeDate) {
		if (storeDate == null) {
			return -1;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		Date firstDayOfMonth = cal.getTime();

		return storeDate.compareTo(firstDayOfMonth);
	}

	public static void main(String args[]) {
		try {
			CommonUtils c = new CommonUtils();

			String s = c.addSubHours(new Date(System.currentTimeMillis()), 2 , "M");
			System.out.println(s);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static boolean isWithinRange(Date testDate, int lpReadDays) {
		Date startDate = Date
				.from(LocalDateTime.now().minusDays(lpReadDays).atZone(ZoneId.systemDefault()).toInstant());
		Date endDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

		return !(testDate.before(startDate) || testDate.after(endDate));
	}

	public boolean isValid() throws Exception {

		/*if (dateConverter == null) {
			dateConverter = new DateConverter();
		}

		String validVal = "MjAyMi0wOS0wMSAwMDowMDowMA==";
		byte[] base64decodedBytes = Base64.getDecoder().decode(validVal);
		String fD = new String(base64decodedBytes);

		Date currDate = new Date(System.currentTimeMillis());
		Date matchDate = dateConverter.convertStringToDate(fD);

		try {
			long matchDiff = matchDate.getTime() - currDate.getTime();
			matchDiff = matchDiff / (24 * 60 * 60 * 1000);
			if (matchDiff < -1) {
			}
		} catch (Exception e) {
		}*/
		return true;
	}

	public boolean isGreaterFromMidNight(String type, Date date) {
		boolean validComm = false;

		if (dateConverter == null) {
			dateConverter = new DateConverter();
		}
		
		if(date != null) {
			try {
				LocalTime midnight = LocalTime.MIDNIGHT;
				LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
				LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
				LocalDateTime yesterdayMidnight = todayMidnight.minusDays(1);
				LocalDateTime weekMidnight = todayMidnight.minusDays(7);
				LocalDateTime monthMidnight = todayMidnight.minusMonths(1);

				if (Constants.CommType.DayWise.equalsIgnoreCase(type)) {
					validComm = todayMidnight.isBefore(dateConverter.convertToLocalDateViaInstant(date));
				} else if (Constants.CommType.YesterdayWise.equalsIgnoreCase(type)) {
					validComm = yesterdayMidnight.isBefore(dateConverter.convertToLocalDateViaInstant(date));
				} else if (Constants.CommType.WeekWise.equalsIgnoreCase(type)) {
					validComm = weekMidnight.isBefore(dateConverter.convertToLocalDateViaInstant(date));
				} else if (Constants.CommType.MonthWise.equalsIgnoreCase(type)) {
					validComm = monthMidnight.isBefore(dateConverter.convertToLocalDateViaInstant(date));
				} else {
					new Throwable("Invalid Type");
				}
			} catch (Exception e) {
				System.out.println("Exception ----> " + e.getMessage());
			}
		}else {
			validComm = false;
		}
		

		return validComm;
	}
	
	public List<Feeders> getFeederList() {
		List<Feeders> feedersList = new ArrayList<Feeders>();
		try {
			String query = "Select * from " + MeterDBAppStarter.keyspace + "."+Tables.FEEDERS; 
			String feederList = CommonUtils.getMapper()
					.writeValueAsString(prestoJdbcTemplate.queryForList(query));
			feedersList = CommonUtils.getMapper().readValue(feederList,
					new TypeReference<List<Feeders>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return feedersList;
	}

	public List<DevicesInfo> getDevicesInfo() {
		List<DevicesInfo> devicesList = new ArrayList<DevicesInfo>();
		try {
			String query = "Select * from " + MeterDBAppStarter.keyspace + "."+Tables.DEVICES_INFO; 
			String devices = CommonUtils.getMapper()
					.writeValueAsString(prestoJdbcTemplate.queryForList(query));
			devicesList = CommonUtils.getMapper().readValue(devices,
					new TypeReference<List<DevicesInfo>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devicesList;
	}

	public int getDcuCount() {
		int dcuCount = 0;
		try {
			String query = "Select * from " + MeterDBAppStarter.keyspace + "."+Tables.DCU_INFO; 
			String dcu = CommonUtils.getMapper()
					.writeValueAsString(prestoJdbcTemplate.queryForList(query).size());
			dcuCount = CommonUtils.getMapper().readValue(dcu,
					new TypeReference<Integer>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dcuCount;
	}

	public List<DtTrans> getDtTrans() {
		List<DtTrans> dtList = new ArrayList<DtTrans>();
		try {
			String query = "Select * from " + MeterDBAppStarter.keyspace + "."+Tables.DT_TRANS; 
			String dt = CommonUtils.getMapper()
					.writeValueAsString(prestoJdbcTemplate.queryForList(query));
			dtList = CommonUtils.getMapper().readValue(dt,
					new TypeReference<List<DtTrans>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtList;
	}

	public List<Subdivisions> getSubdivision() {
		List<Subdivisions> subdivisionList = new ArrayList<Subdivisions>();
		try {
			String query = "Select * from " + MeterDBAppStarter.keyspace + "."+Tables.SUBDEVISIONS; 
			String subdivisions = CommonUtils.getMapper()
					.writeValueAsString(prestoJdbcTemplate.queryForList(query));
			subdivisionList = CommonUtils.getMapper().readValue(subdivisions,
					new TypeReference<List<Subdivisions>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subdivisionList;
	}

	public List<Substations> getSubstation() {
		List<Substations> substationList = new ArrayList<Substations>();
		try {
			String query = "Select * from " + MeterDBAppStarter.keyspace + "."+Tables.SUBSTATIONS; 
			String substations = CommonUtils.getMapper()
					.writeValueAsString(prestoJdbcTemplate.queryForList(query));
			substationList = CommonUtils.getMapper().readValue(substations,
					new TypeReference<List<Substations>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return substationList;
	}

	 public static String numberFormat(Double value)  {
		 return  new DecimalFormat("#.###").format(value);			
		 }
	 
	 public static double decimalFormat(double data) {
		 double val = 0.0;
		 if(data != 0.0) {
			return Double.parseDouble(numberFormat(data));
		 }
		 return val;
		 }
	 
	 public Properties getPushEventPropertiesFile() throws Exception
		{
			Properties pushProp = new Properties();
	        InputStream in = new FileInputStream(meterCommands.getPushEventTypeList());
	        pushProp.load(in);
	        in.close();
			return pushProp;
		}
	 public Properties getPullEventPropertiesFile() throws Exception
		{
			Properties pullProp = new Properties();
	        InputStream in = new FileInputStream(meterCommands.getEventTypeList());
	        pullProp.load(in);
	        in.close();
			return pullProp;
		}
	 public Properties getPullEventCategoryPropertiesFile() throws Exception
		{
			Properties pullProp = new Properties();
	        InputStream in = new FileInputStream(meterCommands.getEventTypeCategoryList());
	        pullProp.load(in);
	        in.close();
			return pullProp;
		}
	 
	 /**
	  * This function checks the empty or null validation
	  * @param val
	  * @return
	  */
	 public static boolean isNullOrEmpty(String val) {
		 	boolean isEmptyFlag = false;
		 	if(StringUtils.isEmpty(val)) {
		 		isEmptyFlag = true;
		 	}
	        return isEmptyFlag;
	 }
	 public static boolean checkNineteenSeventyDate(Date date) {
		 boolean val = false;
		 if(String.valueOf(date).equalsIgnoreCase("Fri Jan 09 23:57:55 IST 1970") || date ==null)
		 {
			val = true;	
		 }
		return val;
	 }
	 
	 public static double kWToWatts(double kilowatts) throws IllegalArgumentException {
	        if (kilowatts < 0) {
	            throw new IllegalArgumentException("Input must be a non-negative value");
	        }
	        return kilowatts * 1000; // 1 kW = 1000 W
	    }
	 
	 public static double wattsToKilowatts(double watts) {
	        return watts / 1000.0;
	    }
	 
	 public static Date getWeeklyDate() throws ParseException {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		    Calendar calendar = Calendar.getInstance();
		    calendar.add(Calendar.DATE, -7);
			return dateFormat.parse(dateFormat.format(calendar.getTime()));
		}
	 public static Date getMonthlyDate() throws ParseException {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		    Calendar calendar = Calendar.getInstance();
		    calendar.add(Calendar.DATE, -30);
			return dateFormat.parse(dateFormat.format(calendar.getTime()));
		}
	 public static synchronized LocalDate getFirstDateOfMonth() {
		 
		 LocalDate currentDate = LocalDate.now();
		 LocalDate firstDateOfMonth = currentDate.withDayOfMonth(1);
	     return firstDateOfMonth;
	    }
	 
	 public static double calculatePercentage(int totalNoOutcome, int outcome) {
			if (totalNoOutcome == 0) {
				throw new IllegalArgumentException("Total number of outcomes cannot be zero.");
			}

			return ((double)outcome * 100) / totalNoOutcome;
		}
	 
	    public static String createBatchId() {

	        String localDate = String.valueOf(encryptLocalDate());
	    			return localDate.contains("-") ? localDate.replace("-", "G") : "G"+localDate;
		}
		 public static synchronized long encryptLocalDate() {
		        try {
		            // Create a SHA-256 message digest
		            MessageDigest digest = MessageDigest.getInstance("SHA-256");
		            
		            // Update the digest with the date string
		            byte[] hash = digest.digest(LocalDate.now().toString().getBytes());
		            
		            // Convert the hash bytes to a long
		            return new BigInteger(1, hash).longValue();
		        } catch (NoSuchAlgorithmException e) {
		            e.printStackTrace();
		            return 0;
		        }
		    }
		 /*
		  * Method to split reason column and pick last three reasons
		  */
			public static String splitReason(String reason, int retry) {
				String result = "";
				String[] isData = reason.split(":");
			 List<String> arrayList = Arrays.asList(isData);
		     List<String> modifiedList = arrayList
		    	        .stream()
		    	        .map(value -> (value.trim().equalsIgnoreCase("null")) ? "-" : value.trim())
		    	        .collect(Collectors.toList());
		     ArrayList<String> reasons = new ArrayList<>(modifiedList);
		     
				if(retry==1 || reasons.size()==1) {
					result = reasons.get(reasons.size()-1);
				}else if (retry == 2 || reasons.size()==2) {
					result = reasons.get(reasons.size()-2) +" : "+ reasons.get(reasons.size()-1);
				}else if (retry >= 3 || reasons.size()>=3) {
					result = reasons.get(reasons.size()-3) +" : "+ reasons.get(reasons.size()-2) +" : "+ reasons.get(reasons.size()-1);
				}
				return result;
			}
			
	public static String getPushPort(DevicesInfo deviceInfo){
				
		Map<String, String> devicesMasterInfoPair = MeterDBAppStarter.pushPortsInfo;

		String key = (deviceInfo.getMeter_type() == null || deviceInfo.getMeter_type().equalsIgnoreCase("JPM") ? "IHM"
				: deviceInfo.getMeter_type())+ ":" + deviceInfo.getDevice_type()+":"+deviceInfo.getMode_of_comm();
		if(!devicesMasterInfoPair.containsKey(key)) {
			key = deviceInfo.getMeter_type().equalsIgnoreCase("JPM") ? "IHM"+":"+deviceInfo.getMode_of_comm() : deviceInfo.getMeter_type()+":"+deviceInfo.getMode_of_comm();
		}
		
		return MeterDBAppStarter.pushPortsInfo.get(key);
	} 
	
	
	public static synchronized LocalDateTime getFirstDateTimeOfCurrentMonth() {
		 LocalDate currentDate = LocalDate.now();
	        int currentYear = currentDate.getYear();
	        int currentMonth = currentDate.getMonthValue();

	        // Create a LocalDateTime object for the current year and month with day "01" and time "00:00:00"
	        LocalDateTime dateTime = LocalDateTime.of(currentYear, currentMonth, 1, 0, 0, 0);
			return dateTime;
		}
//decimal formate for two digits
	 public static double decimalFormatTwoDigits(double data) {
		 double val = 0.0;
		 if(data != 0.0) {
			return Double.parseDouble(new DecimalFormat("#.##").format(data));
		 }
		 return val;
		 }
	 
	 public Properties getPropertiesFile(String path) throws Exception
		{
		 	File file = new File(path);  
			Properties pullProp = new Properties();
			InputStream in = null;
			try {
				in = new FileInputStream(file.getAbsolutePath());
				pullProp.load(in);
			} finally {
				if (in != null)
					in.close();
			}
			return pullProp;
		}
	 
	 public static long getDayCount(String fromDate, String toDate) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        
	        // Parse the dates with time
	        LocalDateTime start = LocalDateTime.parse(fromDate, formatter);
	        LocalDateTime end = LocalDateTime.parse(toDate, formatter);
	        
	        // Calculate the days between the two dates
	        return ChronoUnit.DAYS.between(start, end);
	    }
	 public static long getSlotCount(String fromDate, String toDate, int timeInSec) {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 
		 // Parse the dates with time
		 LocalDateTime start = LocalDateTime.parse(fromDate, formatter);
		 LocalDateTime end = LocalDateTime.parse(toDate, formatter);
		 
		 // Calculate the days between the two dates
		 return ChronoUnit.SECONDS.between(start, end)/timeInSec;
	 }
}