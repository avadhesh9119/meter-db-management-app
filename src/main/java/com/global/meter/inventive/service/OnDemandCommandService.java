package com.global.meter.inventive.service;

import java.text.ParseException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.global.meter.business.model.DailyLoadProfileSinglePhase;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.data.repository.DailyLoadProfileRepository;
import com.global.meter.data.repository.DeltaLoadProfileRepository;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class OnDemandCommandService {

	private static final Logger LOG = LoggerFactory.getLogger(OnDemandCommandService.class);
	
	@Autowired
	DailyLoadProfileRepository dailyLoadProfileRepository;
	
	@Autowired
	DeltaLoadProfileRepository deltaLoadProfileRepository;

	@Autowired
	DevicesInfoService devicesInfoService;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;
	
	public boolean isDataExist(String devNo) {
		boolean isExist = false;
		
		LOG.info("Check daily load profile is exist");
		
		try {
			Optional<DailyLoadProfileSinglePhase> dailyLoadProfile = dailyLoadProfileRepository
					.getData(devNo, CommonUtils.getTodayDate());
			
			if(dailyLoadProfile.isPresent()) {
				isExist = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			LOG.info("Issue in check daily load profile: "+e.getMessage());
		}

		
		return isExist;
	}
	
	
	public boolean isRangeDataExist(DeviceCommand req, DevicesInfo devicesInfo) {
		
		boolean isExist = false;
		String date = "";
		String startDate = "";
		String endDate = "";

		LOG.info("Check daily load profile is exist");
		
		try {
			if((req.getDailyRangeDate()!=null || req.getDeltaRangeDate()!=null) 
					&& (req.getCommand().equalsIgnoreCase("DailyLoadProfile") || req.getCommand().equalsIgnoreCase("DeltaLoadProfile"))) {
				
			String table = "";
			if(req.getCommand().equalsIgnoreCase("DailyLoadProfile")) {
			if(devicesInfo.getDevice_type().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.DAILY_LP_SP;
			}
			else if(devicesInfo.getDevice_type().contains(ExternalConstants.DeviceTypes.THREE_PHASE)){
				table = meterConfiguration.getKeyspace() + "." + Tables.DAILY_LP_TP;
			}
			else if(devicesInfo.getDevice_type().contains(ExternalConstants.DeviceTypes.CT_METER)){
				table = meterConfiguration.getKeyspace() + "." + Tables.DAILY_LP_CT;
			}
			date = "datetime";
			startDate = req.getDailyRangeDate().get("startDate");
			endDate = req.getDailyRangeDate().get("endDate");
			}
			else if (req.getCommand().equalsIgnoreCase("DeltaLoadProfile")) {

				if(devicesInfo.getDevice_type().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
					table = meterConfiguration.getKeyspace() + "." + Tables.DELTA_LP_SP;
				}
				else if(devicesInfo.getDevice_type().contains(ExternalConstants.DeviceTypes.THREE_PHASE)){
					table = meterConfiguration.getKeyspace() + "." + Tables.DELTA_LP_TP;
				}
				else if(devicesInfo.getDevice_type().contains(ExternalConstants.DeviceTypes.CT_METER)){
					table = meterConfiguration.getKeyspace() + "." + Tables.DELTA_LP_CT;
				}
				date = "interval_datetime";
				startDate = req.getDeltaRangeDate().get("startDate");
				endDate = req.getDeltaRangeDate().get("endDate");
			}

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select device_serial_number from ").append(table).append(" where device_serial_number = '").append(req.getHier().getValue()).append("' and ").append(date).append(" >= cast('")
			  .append(startDate).append("' as timestamp) and ").append(date).append(" <= cast('")
			  .append(endDate).append("' as timestamp)");
			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if(outputList != null && !outputList.isEmpty() && outputList.length() >2) {
				isExist = true;
			}
		
			}
		}catch (Exception e) {
			e.printStackTrace();
			LOG.info("Issue in check daily load profile: "+e.getMessage());
		}

		return isExist;
	}
	
}
