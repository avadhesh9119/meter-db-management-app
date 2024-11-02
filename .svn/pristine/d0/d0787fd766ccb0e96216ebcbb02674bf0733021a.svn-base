package com.global.meter.inventive.dashboard.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.inventive.dashboard.business.model.ProcessSlaData;
import com.global.meter.inventive.dashboard.model.DailylpSla;
import com.global.meter.inventive.dashboard.model.DeltalpSla;
import com.global.meter.inventive.models.AllDevices;
import com.global.meter.inventive.utils.DataUtils;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Component
public class ProcessSlaDataUtils {
	private static final Logger LOG = LoggerFactory.getLogger(DataUtils.class);

	JdbcTemplate prestoJdbcTemplate;

	MetersCommandsConfiguration meterConfiguration;

	CommonUtils commonUtils;

	@Autowired
	public DateConverter dateConverter;

	ProcessSlaDataUtils() {
	}

	public ProcessSlaDataUtils(JdbcTemplate prestoJdbcTemplate, MetersCommandsConfiguration meterConfiguration,
			CommonUtils commonUtils) {
		this.prestoJdbcTemplate = prestoJdbcTemplate;
		this.meterConfiguration = meterConfiguration;
		this.commonUtils = commonUtils;
	}

	public static CopyOnWriteArrayList<AllDevices> allDeviceList;
	public static List<DailylpSla> loadProfileSpList;
	public static List<DailylpSla> loadProfileTpList;
	public static List<DailylpSla> loadProfileCtList;
	public static List<DeltalpSla> deltaLpSpList;
	public static List<DeltalpSla> deltaLpTpList;
	public static List<DeltalpSla> deltaLpCtList;

	public List<ProcessSlaData> processSlaData(String owner_name) {
		List<ProcessSlaData> processSlaDataList = new ArrayList<>();

		try {
			

			if (ProcessSlaDataUtils.allDeviceList != null && !ProcessSlaDataUtils.allDeviceList.isEmpty()) {
				for (AllDevices dev : ProcessSlaDataUtils.allDeviceList) {
					if (owner_name.equals(dev.getUtility()) && dev.getDevicesInfo() != null
							&& !dev.getDevicesInfo().isEmpty()) {
						LOG.info("Data add in MeterCommCount");
						try {
							for (DevicesInfo info : dev.getDevicesInfo()) {
								ProcessSlaData slaData = processSlaData(info, dev.getDevicesInfo().size());
								if (slaData != null && slaData.getDevice_serial_number() != null) {
									processSlaDataList.add(slaData);
									slaData = null;
								}

							}
							LOG.info("Data add Success in MeterCommCount");
						} catch (Exception e) {
							LOG.error("Data add failure in MeterCommCount: " + e.getMessage());
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Error fetching load profiles: " + e.getMessage());
		}

		return processSlaDataList;
	}

	private List<?> fetchDailyLoadProfile(String deviceType) throws Exception {
		
		String tableName = getDailyLoadTableName(deviceType);
		String addTime = meterConfiguration.getDailyLoadRangeTime();
		String currentDate = CommonUtils.getCurrentDate();
		StringBuilder queryBuilder = new StringBuilder();
		
	    queryBuilder.append("SELECT device_serial_number,mdas_datetime,datetime, ");
	    queryBuilder.append("CASE WHEN mdas_datetime BETWEEN datetime AND date_add('MINUTE',").append(addTime).append(", datetime) THEN 'True' ELSE 'False' END AS capture  ");
	    queryBuilder.append("FROM ").append(tableName).append(" ");
	    queryBuilder.append("WHERE datetime >= CAST('").append(currentDate).append("' AS TIMESTAMP) order by mdas_datetime asc");
        
		
		String query = queryBuilder.toString();


		List<Map<String, Object>> resultList = prestoJdbcTemplate.queryForList(query);
		String loadProfileList = CommonUtils.getMapper().writeValueAsString(resultList);

		return CommonUtils.getMapper().readValue(loadProfileList,
				new TypeReference<List<DailylpSla>>() {
				});
	}	
	
	private List<?> fetchBlockLoadProfile(String deviceType) throws Exception {
		String tableName = getBlockLoadTableName(deviceType);
		
		String addTime = meterConfiguration.getBlockLoadRangeTime();
		String fromDate = DateConverter.addSecondsToDateString(commonUtils.getBlockLoadReadDate(), 1) ;
		String toDate = DateConverter.addSecondsToDateString(CommonUtils.getCurrentDate(), 1);
		
		StringBuilder queryBuilder = new StringBuilder();
	    queryBuilder.append("SELECT device_serial_number, ");
	    queryBuilder.append("SUM(CASE WHEN mdas_datetime <= date_add('MINUTE', ").append(addTime).append(", interval_datetime) THEN 1 ELSE 0 END) AS present, ");
	    queryBuilder.append("SUM(CASE WHEN mdas_datetime > date_add('MINUTE', ").append(addTime).append(", interval_datetime) THEN 1 ELSE 0 END) AS notPresent, ");
	    queryBuilder.append("COUNT(*) AS totalCapturedSlots ");
	    queryBuilder.append("FROM ").append(tableName).append(" ");
	    queryBuilder.append("WHERE interval_datetime BETWEEN CAST('").append(fromDate).append("' AS TIMESTAMP) AND CAST('").append(toDate).append("' AS TIMESTAMP) ");
	    queryBuilder.append("GROUP BY device_serial_number");  
        
        String sqlQuery = queryBuilder.substring(0, queryBuilder.length());
        

		List<Map<String, Object>> resultList = prestoJdbcTemplate.queryForList(sqlQuery);

		
		String loadProfileList = CommonUtils.getMapper().writeValueAsString(resultList);
		
		return CommonUtils.getMapper().readValue(loadProfileList,
				new TypeReference<List<DeltalpSla>>() {
		});
	}

	public static Object getTime(Object calObject) {
		String result = null;
		DateConverter dateConverter = new DateConverter();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> dateMap = (Map<String, Object>) calObject;
			result = dateConverter.convertDateToTimeString(new Date((long) dateMap.get("value")));
		} catch (Exception e) {
			return calObject;
		}
		return result;

	}

	public static synchronized List<Date> generateTimeIntervals(Date startTime, Date endTime, int intervalMinutes) {
		List<Date> timeIntervals = new ArrayList<>();

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startTime);
		startCalendar.add(Calendar.SECOND, 1);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);

		
		while (calendar.getTime().before(endTime)) {
			timeIntervals.add(calendar.getTime());
			calendar.add(Calendar.MINUTE, intervalMinutes);			
		}
		
		
		timeIntervals.set(0,startCalendar.getTime());
		 
		return timeIntervals;
	}
	
	public String isTimeInRangeOrAfter(Date currentTime, Date startTime, Date endTime) {
		long currentTimeMillis = currentTime.getTime();
		long startTimeMillis = startTime.getTime();
		long endTimeMillis = endTime.getTime();

		if (currentTimeMillis >= startTimeMillis && currentTimeMillis <= endTimeMillis) {
			return "within";
		} else if (currentTimeMillis > endTimeMillis) {
			return "after";
		} else {
			return "missing";
		}
	}

	public static synchronized Date addMinutesToDate(Date date, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}
	
	public static synchronized Date addSecondToDate(Date date, int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	private double calculatePercentage(int totalNoOutcome, int outcome) {
		if (totalNoOutcome == 0) {
			throw new IllegalArgumentException("Total number of outcomes cannot be zero.");
		}

		return ((double)outcome * 100) / totalNoOutcome;
	}

	private String getDailyLoadTableName(String meterType) {
		switch (meterType) {
		case Constants.DeviceTypes.SINGLE_PHASE:
			return meterConfiguration.getKeyspace() + "." + Tables.DAILY_LP_SP;
		case Constants.DeviceTypes.THREE_PHASE:
			return meterConfiguration.getKeyspace() + "." + Tables.DAILY_LP_TP;
		case Constants.DeviceTypes.CT:
			return meterConfiguration.getKeyspace() + "." + Tables.DAILY_LP_CT;
		default:
			return "";
		}
	}

	private String getBlockLoadTableName(String meterType) {
		switch (meterType) {
		case Constants.DeviceTypes.SINGLE_PHASE:
			return meterConfiguration.getKeyspace() + "." + Tables.DELTA_LP_SP;
		case Constants.DeviceTypes.THREE_PHASE:
			return meterConfiguration.getKeyspace() + "." + Tables.DELTA_LP_TP;
		case Constants.DeviceTypes.CT:
			return meterConfiguration.getKeyspace() + "." + Tables.DELTA_LP_CT;
		default:
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	private ProcessSlaData processSlaData(DevicesInfo info, int totDevCount) {
		
		ProcessSlaData processSlaData = null;
		
		try {
		List<DailylpSla> loadProfileList = null;
		List<DeltalpSla> deltaLpList = null;
		
		// Daily Load Profile Data List Initializing
		if(Constants.DeviceTypes.SINGLE_PHASE.contains(info.getDevice_type())) {
			if(loadProfileSpList == null) {
				loadProfileList = (List<DailylpSla>) fetchDailyLoadProfile(
						Constants.DeviceTypes.SINGLE_PHASE);
				loadProfileSpList = loadProfileList;
			}
			else 
			{
				loadProfileList = loadProfileSpList;
			}
		}else if(Constants.DeviceTypes.THREE_PHASE.contains(info.getDevice_type())) {
			if(loadProfileTpList == null) {
				loadProfileList = (List<DailylpSla>) fetchDailyLoadProfile(
					Constants.DeviceTypes.THREE_PHASE);
				loadProfileTpList = loadProfileList;
			}
			else 
			{
				loadProfileList = loadProfileTpList;
			}
		}else if(Constants.DeviceTypes.CT.contains(info.getDevice_type())) {
			if(loadProfileCtList == null) {
				loadProfileList = (List<DailylpSla>) fetchDailyLoadProfile(
					Constants.DeviceTypes.CT);
				loadProfileCtList = loadProfileList;
			}
			else 
			{
				loadProfileList = loadProfileCtList;
			}
		}else {
			loadProfileList = null;
		}
					
		// Block Load Profile Data List Initializing			
		if(Constants.DeviceTypes.SINGLE_PHASE.contains(info.getDevice_type())) {
			if(deltaLpSpList == null) {
				deltaLpList = (List<DeltalpSla>) fetchBlockLoadProfile(
					Constants.DeviceTypes.SINGLE_PHASE);
				deltaLpSpList = deltaLpList;
			}
			else 
			{
				deltaLpList = deltaLpSpList;
			}
		}else if(Constants.DeviceTypes.THREE_PHASE.contains(info.getDevice_type())) {
			if(deltaLpTpList == null) {
				deltaLpList = (List<DeltalpSla>) fetchBlockLoadProfile(
						Constants.DeviceTypes.THREE_PHASE);
				deltaLpTpList = deltaLpList;
			}
			else 
			{
				deltaLpList = deltaLpTpList;
			}
		}else if(Constants.DeviceTypes.CT.contains(info.getDevice_type())) {
			if(deltaLpCtList == null) {
				deltaLpList = (List<DeltalpSla>) fetchBlockLoadProfile(
					Constants.DeviceTypes.CT);
				deltaLpCtList = deltaLpList;
			}
			else 
			{
				deltaLpList = deltaLpCtList;
			}
		}else {
			deltaLpList = null;
		}
			

		

	
				if (loadProfileList != null && loadProfileList.size() > 0) {
					processSlaData = processDailyLoadProfile(info, loadProfileList,
							processSlaData);
				}
				
				if(deltaLpList != null && deltaLpList.size() > 0) {
					processSlaData = processDeltaLoadProfile(info, deltaLpList, processSlaData);
				}
				
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return processSlaData;
	}

	private ProcessSlaData processDailyLoadProfile(DevicesInfo info,
			List<DailylpSla> loadProfileSinglePhases, ProcessSlaData processSlaData) {
		if (processSlaData == null) {
			processSlaData = new ProcessSlaData();
		}
		try {
			if (loadProfileSinglePhases != null && !loadProfileSinglePhases.isEmpty()) {
				for (DailylpSla loadProfile : loadProfileSinglePhases) {
					if (info.getDevice_serial_number().equals(loadProfile.getDevice_serial_number())) {
						
						if ((Constants.TRUE).equalsIgnoreCase(loadProfile.getCapture())) {
							processSlaData.setIs_dailylp_available("YES");
						}
						else if ((Constants.FALSE).equalsIgnoreCase(loadProfile.getCapture())) {
							processSlaData.setIs_dailylp_available("Late");
						}
						processSlaData.setLastdailylpreaddatetime(info.getLastdailylpreaddatetime());
					}
					
				}
				setCommonSlaData(info, processSlaData);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return processSlaData;
	}

	private ProcessSlaData processDeltaLoadProfile(DevicesInfo info,
			List<DeltalpSla> deltaLpList, ProcessSlaData processSlaData) throws ParseException {
		if (processSlaData == null) {
			processSlaData = new ProcessSlaData();
		}
		int successCount = 0;
		int lateCount = 0;
		int totalSlots = 0;
		if (deltaLpList != null && !deltaLpList.isEmpty()) {
			for (DeltalpSla deltaLp : deltaLpList) {
				if (info.getDevice_serial_number().equals(deltaLp.getDevice_serial_number())) {
					
						successCount = deltaLp.getPresent();
						lateCount = deltaLp.getNotPresent();
						totalSlots = deltaLp.getTotalCapturedSlots();
						
						processSlaData.setLastdeltalp_count(String.valueOf(totalSlots));
						processSlaData.setLastdeltalp_success_avg(
								String.valueOf(Math.round(calculatePercentage(totalSlots, successCount))));
						processSlaData
								.setLastdeltalp_failure_avg(String.valueOf(Math.round(calculatePercentage(totalSlots, lateCount))));
						processSlaData.setLastdeltalpreaddatetime(info.getLastdeltalpreaddatetime());
						setCommonSlaData(info, processSlaData);
					
				}
			}
		}
		return processSlaData;
	}


	private ProcessSlaData setCommonSlaData(DevicesInfo info, ProcessSlaData processSlaData) throws ParseException {
			processSlaData.setDevice_serial_number(info.getDevice_serial_number());
			processSlaData.setDevice_type(info.getDevice_type());
			processSlaData.setSubdivision_name(info.getSubdevision_name());
			processSlaData.setSubstation_name(info.getSubstation_name());
			processSlaData.setFeeder_name(info.getFeeder_name());
			processSlaData.setDt_name(info.getDt_name());
			processSlaData.setMeter_type(info.getMeter_type());
			processSlaData.setOwner_name(info.getOwner_name());
			if (info.getLast_nameplate_datetime() != null) {
				processSlaData.setLastnameplatereaddatetime(info.getLast_nameplate_datetime());
			}
			if (info.getInstant_push_datetime() != null && info.getInstant_push_datetime().after(CommonUtils.getTodayDate())) {
				processSlaData.setLastinstantpushdata_readdatetime(info.getInstant_push_datetime());
			}
			if (info.getLast_gasp_datetime() != null && info.getLast_gasp_datetime().after(CommonUtils.getTodayDate())) {
				processSlaData.setLastgasp_readdatetime(info.getLast_gasp_datetime());
			}
			if (info.getFirst_breath_datetime() != null && info.getFirst_breath_datetime().after(CommonUtils.getTodayDate())) {
				processSlaData.setLastfirstbreath_readdatetime(info.getFirst_breath_datetime());
			}
			if (info.getLastinstanteousreaddatetime() != null && info.getLastinstanteousreaddatetime().after(CommonUtils.getTodayDate())) {
				processSlaData.setLastinstanteousreaddatetime(info.getLastinstanteousreaddatetime());
			}
			processSlaData.setMdas_datetime(new Date(System.currentTimeMillis()));
		return processSlaData;
	}
}
