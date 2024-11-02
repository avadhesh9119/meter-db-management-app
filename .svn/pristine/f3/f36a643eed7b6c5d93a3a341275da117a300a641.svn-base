package com.global.meter.v3.inventive.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.global.meter.business.model.DeviceCommandLog;
import com.global.meter.business.model.DeviceCommandPrepayLogs;
import com.global.meter.business.model.DeviceConfigLogs;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.PrepayData;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.data.repository.DeviceCommandLogRepository;
import com.global.meter.data.repository.DeviceCommandPrepayLogRepository;
import com.global.meter.data.repository.DeviceConfigLogsRepository;
import com.global.meter.data.repository.PrepayDataRepository;
import com.global.meter.inventive.models.DayProfile;
import com.global.meter.inventive.models.DayProfileTime;
import com.global.meter.inventive.utils.DataUtils;
import com.global.meter.service.DeviceCommandService;
import com.global.meter.service.HistoricalDataService;
import com.global.meter.service.NamePlatesDataService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.v3.inventive.business.model.SingleConnectionMeterCommandLog;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;

@Service
public class SingleConnectionCommandLogsService {

	private static final Logger LOG = LoggerFactory.getLogger(DeviceCommandService.class);
	
	@Autowired
	DeviceCommandLogRepository devCommandLogRepository;
	
	@Autowired
	DeviceCommandPrepayLogRepository deviceCommandPrepayLogRepository;
	
	@Autowired
	DeviceConfigLogsRepository deviceConfigLogsRepository;
	
	@Autowired
	PrepayDataRepository prepayDataRepository;
	
	@Autowired
	HistoricalDataService historicalDataService;
	
	@Autowired
	NamePlatesDataService namePlatesDataService;
	
	@Autowired
	DateConverter dateConverter;

	public void logDevicesCommands(DevicesInfo devicesInfo, String deviceSNO, String cmdName, String status, String reason,
			String trackingId, Date commandCompletionDate, int attempts, Date mdasDatetime, SingleConnectionMeterCommandLog commandLog, Map<String, String> dailyRangeDate, Map<String, String> deltaRangeDate)
	{
		if(StringUtils.isEmpty(trackingId)) {
			trackingId = String.valueOf(System.nanoTime());
		}
		DeviceCommandLog deviceCommandLogs = new DeviceCommandLog(deviceSNO, trackingId,
				cmdName, status, mdasDatetime,
				devicesInfo.getDcu_serial_number(), devicesInfo.getDt_name(),
				devicesInfo.getFeeder_name(), devicesInfo.getOwner_name(),
				devicesInfo.getSubdevision_name(), devicesInfo.getSubstation_name(),
				reason ,commandCompletionDate, attempts);
		deviceCommandLogs.setBatch_id(commandLog.getBatch_id());
		deviceCommandLogs.setSource(commandLog.getSource());
		deviceCommandLogs.setUser_id(commandLog.getUser_id());
		
		if(ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(cmdName)) {
				deviceCommandLogs.setDatewise_range(dailyRangeDate);	
		}else if (ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(cmdName)) {
				deviceCommandLogs.setDatewise_range(deltaRangeDate);
		}
		devCommandLogRepository.save(deviceCommandLogs);
	}
	
	public void logDevicesCommands(DevicesInfo devicesInfo, String deviceSNO, Map<String,String> cmdName, String status, String reason,
			String trackingId, Date commandCompletionDate, Integer attempts, Date mdasDatetime, Map<String,Integer> retry, String cellId, SingleConnectionMeterCommandLog deviceCommand)
	{
		if(StringUtils.isEmpty(trackingId)) {
			trackingId = String.valueOf(System.nanoTime());
		}
		DeviceCommandPrepayLogs deviceCommandLogs = new DeviceCommandPrepayLogs(deviceSNO, trackingId,
				cmdName, status, mdasDatetime,
				devicesInfo.getDcu_serial_number(), devicesInfo.getDt_name(),
				devicesInfo.getFeeder_name(), devicesInfo.getOwner_name(),
				devicesInfo.getSubdevision_name(), devicesInfo.getSubstation_name(),
				reason ,commandCompletionDate, attempts, retry);
		deviceCommandLogs.setCell_id(cellId);
		deviceCommandLogs.setBatch_id(deviceCommand.getBatch_id());
		deviceCommandLogs.setSource(deviceCommand.getSource());
		deviceCommandLogs.setUser_id(deviceCommand.getUser_id());
		
		deviceCommandPrepayLogRepository.save(deviceCommandLogs);
	}
	
	public void logDeviceConfig(DevicesInfo devicesInfo, String deviceSNO, Map<String,String> cmdName, String status, String reason,
			String trackingId, Date commandCompletionDate, Integer attempts, Date mdasDatetime, Map<String,Integer> retry, String cellId,
			Map<String,String> configStatus, SingleConnectionMeterCommandLog deviceCommand)
	{
		if(StringUtils.isEmpty(trackingId)) {
			trackingId = String.valueOf(System.nanoTime());
		}
		DeviceConfigLogs deviceConfigLogs = new DeviceConfigLogs(deviceSNO, trackingId,
				cmdName, status, mdasDatetime,
				devicesInfo.getDcu_serial_number(), devicesInfo.getDt_name(),
				devicesInfo.getFeeder_name(), devicesInfo.getOwner_name(),
				devicesInfo.getSubdevision_name(), devicesInfo.getSubstation_name(),
				reason ,commandCompletionDate, attempts, retry);
		deviceConfigLogs.setCommand_status(configStatus);
		deviceConfigLogs.setUser_id(deviceCommand.getUser_id());
		deviceConfigLogs.setBatch_id(deviceCommand.getBatch_id());
		deviceConfigLogs.setSource(deviceCommand.getSource());
		
		deviceConfigLogsRepository.save(deviceConfigLogs);
	}

	public boolean insertPrepayData(DevicesInfo deviceInfo, List<MeterResponse> meterResponses, SingleConnectionCommandReq req)

	{
		boolean flag = false;
		int totSuccessVal = 0;
		try {
			PrepayData prepayData = new PrepayData();
			prepayData.setDcu_serial_number(deviceInfo.getDcu_serial_number());
			prepayData.setDevice_serial_number(deviceInfo.getDevice_serial_number());
			prepayData.setDt_name(deviceInfo.getDt_name());
			prepayData.setFeeder_name(deviceInfo.getFeeder_name());
			prepayData.setMdas_datetime(new Date(System.currentTimeMillis()));
			prepayData.setOwner_name(deviceInfo.getOwner_name());
			prepayData.setSubdevision_name(deviceInfo.getSubdevision_name());
			prepayData.setSubstation_name(deviceInfo.getSubstation_name());
			prepayData.setBatch_id(req.getBatchId());
			prepayData.setSource(req.getSource());
			prepayData.setUser_id(req.getUserId());
			prepayData.setDevice_type(deviceInfo.getDevice_type());
			
			for (MeterResponse meterResponse : meterResponses) {
				if(ConfigCommands.CURRENT_BALANCE_AMOUNT.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					deviceInfo.setCurrent_balance_amount((Integer) meterResponse.getData()[0]);
					prepayData.setCurrent_balance_amount((Integer) meterResponse.getData()[0]);
				}
				else if(ConfigCommands.CURRENT_BALANCE_TIME.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					deviceInfo.setCurrent_balance_time(CommonUtils.getInstanDate(meterResponse.getData()[0]));
					prepayData.setCurrent_balance_time(CommonUtils.getInstanDate(meterResponse.getData()[0]));
				}
				else if(ConfigCommands.LAST_TOKEN_RECHARGE_AMOUNT.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					deviceInfo.setLast_token_recharge_amount((Integer) meterResponse.getData()[0]);
					prepayData.setLast_token_recharge_amount((Integer) meterResponse.getData()[0]);
				}
				else if(ConfigCommands.LAST_TOKEN_RECHARGE_TIME.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					deviceInfo.setLast_token_recharge_time(CommonUtils.getInstanDate(meterResponse.getData()[0]));
					prepayData.setLast_token_recharge_time(CommonUtils.getInstanDate(meterResponse.getData()[0]));
				}
				else if(ConfigCommands.METERING_MODE.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					deviceInfo.setMetering_mode((Integer) meterResponse.getData()[0]);
					prepayData.setMetering_mode((Integer) meterResponse.getData()[0]);
				}
				else if(ConfigCommands.PAYMENT_MODE.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					deviceInfo.setPayment_mode((Integer) meterResponse.getData()[0]);
					prepayData.setPayment_mode((Integer) meterResponse.getData()[0]);
					
					deviceInfo.setDev_mode(meterResponse.getData()[0].equals("1") ? 
							Constants.DevMode.PREPAID : Constants.DevMode.POSTPAID) ;
				}
				else if(ConfigCommands.TOTAL_AMOUNT_AT_LAST_RECHARGE.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					deviceInfo.setTotal_amount_at_last_recharge((Integer) meterResponse.getData()[0]);
					prepayData.setTotal_amount_at_last_recharge((Integer) meterResponse.getData()[0]);
				}
				else if(ConfigCommands.PING_METER.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setPing_meter((boolean) meterResponse.getData()[0]);
				}
				else if(ConfigCommands.BILLING_DATES.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					String billingDate = dateConverter.convertDateToString(CommonUtils.getInstanDate(meterResponse.getData()[0]));
					String billingDates[] = billingDate.split(" ");
					String ymd[] = billingDates[0].split("-");
					StringBuilder formattedBillingDate = new StringBuilder();
					formattedBillingDate.append(ymd[2]).append(":").append(" ").append(billingDates[1]);
					prepayData.setBilling_dates(formattedBillingDate.toString());
					deviceInfo.setBilling_datetime(formattedBillingDate.toString());
				}
				else if(ConfigCommands.RTC_ClOCK.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setRtc_clock(CommonUtils.getInstanDate(meterResponse.getData()[0]));
				}
				else if(ConfigCommands.DEMAND_INTEGRATION_PERIOD.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setDemand_integration_period((Integer) meterResponse.getData()[0]);
				}
				else if(ConfigCommands.PROFILE_CAPTURE_PERIOD.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setProfile_capture_period((Integer) meterResponse.getData()[0]);
					deviceInfo.setProfile_capture_period((Integer) meterResponse.getData()[0]);
				}
				else if(ConfigCommands.LOAD_LIMIT.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setLoad_limit(CommonUtils.wattsToKilowatts(Double.parseDouble(meterResponse.getData()[0].toString())));
				}
				else if(ConfigCommands.INSTANT_IP_PUSH.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setInstant_push_ip((String) meterResponse.getData()[0]);
				}
				else if(ConfigCommands.ALERT_IP_PUSH.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setAlert_push_ip((String) meterResponse.getData()[0]);
				}
				else if(ConfigCommands.ACTIVITY_SCHEDULE_PUSH.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					if (deviceInfo.getDevice_type().contains(Constants.DeviceTypes.SINGLE_PHASE)
					        && !Constants.ManufacturerName.ZEN.equalsIgnoreCase(deviceInfo.getMeter_type())
					        && !Constants.ManufacturerName.HPL.equalsIgnoreCase(deviceInfo.getMeter_type())) {
					    String schedulePushDate = new CommonUtils().getInstanDateString(meterResponse.getData()[0], dateConverter);
					    prepayData.setPush_setup_duration(schedulePushDate);
					} else {
					    String data = CommonUtils.getMapper().writeValueAsString(meterResponse.getData()[0]);
					    Object[] schedulePushOb = CommonUtils.getMapper().readValue(data, Object[].class);
					    StringBuilder formattedPushTime = new StringBuilder();

					    for (Object dateOb : schedulePushOb) {
					        if (dateOb instanceof LinkedHashMap) {
					            String schedulePushDate = new CommonUtils().getInstanDateString(dateOb, dateConverter);
					            if (schedulePushDate != null) {
					            	 formattedPushTime.append(schedulePushDate).append(",");
								}    
					        }
					    }

					    if (formattedPushTime.length() > 0) {
					        formattedPushTime.setLength(formattedPushTime.length() - 1);
					    }

					    prepayData.setPush_setup_duration(formattedPushTime.toString());
					}
				}
				else if(ConfigCommands.ENABLE_DISABLE_DISCONNECT_CONTROL.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setEnable_disable_control_modes((String) meterResponse.getData()[0]);
				}
				else if(ConfigCommands.RELAY_STATUS.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setRelay_status((meterResponse.getData()[0] != null && meterResponse.getData()[0].equals(true)) ? Constants.Status.CONNECTED : Constants.Status.DISCONNECTED);
				}
				else if(ConfigCommands.ACTIVITY_CALENDER.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					StringBuilder formattedTOD = new StringBuilder();
					
					prepayData.setTod(CommonUtils.getMapper().writeValueAsString(meterResponse.getData()[0]));

					DayProfile dayProfile = CommonUtils.getMapper().readValue(prepayData.getTod(), DayProfile.class);
					
					List<DayProfileTime> profileTime = dayProfile.getDayProfileTime();
					for (DayProfileTime dayProfileTime : profileTime) {
						dayProfileTime.setStartTime(DataUtils.getTime(dayProfileTime.getStartTime()));
						formattedTOD.append(dayProfileTime.getStartTime()).append(",");
					}
					dayProfile.setDayProfileTime(profileTime);
										
					if (dayProfile.getActivatePassiveCalendar() != null) {
						 String activatePassiveCalendar = dateConverter.convertDateToString(CommonUtils.getInstanDate(dayProfile.getActivatePassiveCalendar()));
						 String activatePassiveCalendars[] = activatePassiveCalendar.split(" ");
						 StringBuilder formattedPassiveDate = new StringBuilder();
						 formattedPassiveDate.append(formattedTOD.substring(0, formattedTOD.length()-1))
								 .append("|").append(activatePassiveCalendars[0]).append(" ").append(activatePassiveCalendars[1]);
						 prepayData.setTod(formattedPassiveDate.toString());
					}
					else {
						prepayData.setTod(formattedTOD.substring(0, formattedTOD.length()-1));
					}
				}
				else if(ConfigCommands.AVERAGE_SIGNAL_STRENGTH.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setAverage_signal_strength((Integer) meterResponse.getData()[8]);
				}
				else if(ConfigCommands.METER_HEALTH_INDICATOR.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setMeter_health_indicator((Integer) meterResponse.getData()[7]);
				}
				else if(ConfigCommands.IPV6_ADDRESS.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setIpv6_address((String) meterResponse.getData()[0]);
				}
				else if(ConfigCommands.IPV4_ADDRESS.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setIpv4_address((String) meterResponse.getData()[0]);
				}
				else if(ConfigCommands.APN.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
			        prepayData.setApn((String)(meterResponse.getData()[0]));
				}
				else if(ConfigCommands.CONNECT.commandName.equalsIgnoreCase(meterResponse.getObisCmd()) 
						|| ConfigCommands.DISCONNECT.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					
					if(ConfigCommands.CONNECT.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					    prepayData.setCurrent_relay_status(Constants.Status.CONNECTED);
					}
					else if(ConfigCommands.DISCONNECT.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
						prepayData.setCurrent_relay_status(Constants.Status.DISCONNECTED);
					}
				}
				
				totSuccessVal = totSuccessVal+1;	
			}
			flag = true;
			prepayDataRepository.save(prepayData);
//			totSuccessResult.set(totSuccessVal); 
			LOG.info("Prepay Data is saved successfully..");
		} catch (Exception e) {
			LOG.error("Issue in inserting Prepay data for meter "+ deviceInfo.getDevice_serial_number() +" in db due to :"+ e.getMessage());
		}
		return flag;
	}
	
	public boolean saveHistoryData( DevicesInfo deviceInfo, String cmdName,
			MeterResponse meterResponse) throws Exception
	{
		boolean flag = false;
		try {
			if(ConfigCommands.INSTANTANEOUS_READ.commandName.equals(cmdName)){
				flag = historicalDataService.insertInstantaneousSinglePhaseData( deviceInfo, meterResponse);
			}
			else if(ConfigCommands.DAILY_LOAD_PROFILE.commandName.equals(cmdName)){
				flag = historicalDataService.insertDailyLoadProfileData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.DELTA_LOAD_PROFILE.commandName.equals(cmdName)){
				flag = historicalDataService.insertDeltaLoadProfileData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.BILLING_DATA.commandName.equals(cmdName)){
				flag = historicalDataService.insertBillingData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.POWER_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertPowerRelatedEventsData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.VOLTAGE_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertVoltageRelatedEventsData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.TRANSACTION_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertTransactionRelatedEventsData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.CURRENT_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertCurrentRelatedEventsData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.OTHER_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertOthersRelatedEventsData( deviceInfo, meterResponse);
			}
			else if(ConfigCommands.NON_ROLLOVER_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertNonRolloverRelatedEventsData( deviceInfo, meterResponse);
			} 
			else if(ConfigCommands.CONTROL_RELATED_EVENTS.commandName.equals(cmdName)){
				flag = historicalDataService.insertControlRelatedEventsData( deviceInfo, meterResponse);
			}
			else if(ConfigCommands.NAME_PLATES.commandName.equals(cmdName)){
				flag = namePlatesDataService.insertNamePlateData(deviceInfo, meterResponse);
			}
			return flag;
		} catch (Exception e) {
			throw e;
		}
	}
	
}
