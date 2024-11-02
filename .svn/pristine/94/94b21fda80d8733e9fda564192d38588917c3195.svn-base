package com.global.meter.inventive.mdm.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.PrepayData;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.inventive.models.ConfigurationReadDataResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class ConfigurationReadDataMdmCaster {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationReadDataMdmCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareConfigurationReadData(String outputList, List<ConfigurationReadDataResponse> ispResponseList)
			throws Exception {
		LOG.info("Configuration Read Data Caster called....");
		List<PrepayData> prepayData = new ArrayList<PrepayData>();
		prepayData = CommonUtils.getLogMapper().readValue(
				outputList, new TypeReference<List<PrepayData>>() {});

		LOG.info("Configuration Read Response is preparing..");
		for (PrepayData ispData : prepayData) {
			ConfigurationReadDataResponse ispResponse = new ConfigurationReadDataResponse();

			ispResponse.setDeviceSerialNumber(String.valueOf(ispData.getDevice_serial_number()));
			ispResponse.setMdasDateTime(ispData.getMdas_datetime() != null ?
					dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : "");
			/*
			ispResponse.setCurrentBalanceAmount(ispData.getCurrent_balance_amount() != null ?
					String.valueOf(ispData.getCurrent_balance_amount()) : "");
			ispResponse.setCurrentBalanceTime(ispData.getCurrent_balance_time() != null ?
					dateConverter.convertDateToHesString(ispData.getCurrent_balance_time()) : "");
			ispResponse.setLastTokenRechargeAmount(ispData.getLast_token_recharge_amount() != null ?
					String.valueOf(ispData.getLast_token_recharge_amount()) : "");
			ispResponse.setLastTokenRechargeTime(ispData.getLast_token_recharge_time() != null ?
					dateConverter.convertDateToHesString(ispData.getLast_token_recharge_time()) : "");
			ispResponse.setPaymentMode(ispData.getPayment_mode() != null ?
					String.valueOf(ispData.getPayment_mode()) : "");
			ispResponse.setTotalAmountAtLastRecharge(ispData.getTotal_amount_at_last_recharge() != null ?
					String.valueOf(ispData.getTotal_amount_at_last_recharge()) : "");
			ispResponse.setMeteringMode(ispData.getMetering_mode() != null ?
					String.valueOf(ispData.getMetering_mode()) : "");
			 */
			
			ispResponse.setBillingDate(ispData.getBilling_dates() != null ?
					ispData.getBilling_dates() : "");
			ispResponse.setPingMeter(ispData.getPing_meter() != null ?
					String.valueOf(ispData.getPing_meter()) : "");
			ispResponse.setRtcClock(ispData.getRtc_clock() != null ?
					dateConverter.convertDateToHesString(ispData.getRtc_clock()) : "");
			ispResponse.setTod(ispData.getTod() != null ?
					String.valueOf(ispData.getTod()) : "");
			
			ispResponse.setDtName(String.valueOf(ispData.getDt_name()));
			ispResponse.setFeederName(String.valueOf(ispData.getFeeder_name()));
			ispResponse.setSubdivisionName(String.valueOf(ispData.getSubdevision_name()));
			ispResponse.setSubstationName(String.valueOf(ispData.getSubstation_name()));
//			ispResponse.setSource(String.valueOf(ispData.getSource()));
//			ispResponse.setUserId(String.valueOf(ispData.getUserId()));

			ispResponseList.add(ispResponse);
		}

		LOG.info("Configuration Read Response Prepared.");
	}
	
	
	
	
	
	public void prepareConfigurationReadData(String outputList, List<ConfigurationReadDataResponse> ispResponseList, MeterDataVisualizationReq req)
			throws Exception {
		LOG.info("Configuration Read Data Caster called....");
		List<PrepayData> prepayData = new ArrayList<PrepayData>();
		prepayData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<PrepayData>>() {
		});

		LOG.info("Configuration Read Response is preparing..");
		for (PrepayData ispData : prepayData) {
			ConfigurationReadDataResponse ispResponse = new ConfigurationReadDataResponse();

			ispResponse.setDeviceSerialNumber(String.valueOf(ispData.getDevice_serial_number()));
			ispResponse.setMdasDateTime(ispData.getMdas_datetime() != null ?
					dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : req.getReplaceBy());
			if(ConfigCommands.BILLING_DATES.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setBillingDate(ispData.getBilling_dates() != null ?
						ispData.getBilling_dates() : req.getReplaceBy());
			}else if(ConfigCommands.RTC_ClOCK.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setRtcClock(ispData.getRtc_clock() != null ?
						dateConverter.convertDateToHesString(ispData.getRtc_clock()) : req.getReplaceBy());
			}else if(ConfigCommands.PING_METER.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setPingMeter(ispData.getPing_meter() != null ?
						String.valueOf(ispData.getPing_meter()) : req.getReplaceBy());
			}else if(ConfigCommands.ACTIVITY_CALENDER.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setTod(ispData.getTod() != null ?
						String.valueOf(ispData.getTod()) : req.getReplaceBy());
			}else if(ConfigCommands.LAST_TOKEN_RECHARGE_AMOUNT.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setLastTokenRechargeAmount(ispData.getLast_token_recharge_amount() != null ? 
						String.valueOf(ispData.getLast_token_recharge_amount()): req.getReplaceBy());
			}else if(ConfigCommands.TOTAL_AMOUNT_AT_LAST_RECHARGE.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setTotalAmountAtLastRecharge(ispData.getTotal_amount_at_last_recharge() != null ? 
						String.valueOf(ispData.getTotal_amount_at_last_recharge()) : req.getReplaceBy());
			}else if(ConfigCommands.LAST_TOKEN_RECHARGE_TIME.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setLastTokenRechargeTime(ispData.getLast_token_recharge_time() != null ?
						dateConverter.convertDateToHesString(ispData.getLast_token_recharge_time()) : req.getReplaceBy());
			}else if(ConfigCommands.CURRENT_BALANCE_AMOUNT.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setCurrentBalanceAmount(ispData.getCurrent_balance_amount() != null ?
						String.valueOf(ispData.getCurrent_balance_amount()) : req.getReplaceBy());
			}else if(ConfigCommands.CURRENT_BALANCE_TIME.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setCurrentBalanceTime(ispData.getCurrent_balance_amount() != null ? 
						dateConverter.convertDateToHesString(ispData.getCurrent_balance_time()) : req.getReplaceBy());
			}else if(ConfigCommands.PAYMENT_MODE.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setPaymentMode(ispData.getPayment_mode() != null ? 
						String.valueOf(ispData.getPayment_mode()) : req.getReplaceBy());
			}else if(ConfigCommands.DEMAND_INTEGRATION_PERIOD.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setDemandIntegrationPeriod(ispData.getDemand_integration_period() != null ? 
						String.valueOf(ispData.getDemand_integration_period()) : req.getReplaceBy());
			}else if(ConfigCommands.PROFILE_CAPTURE_PERIOD.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setProfileCapturePeriod(ispData.getProfile_capture_period() != null ? 
						String.valueOf(ispData.getProfile_capture_period()) : req.getReplaceBy());
			}else if(ConfigCommands.LOAD_LIMIT.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setLoadLimit(ispData.getLoad_limit() != null ? 
						String.valueOf(ispData.getLoad_limit()) : req.getReplaceBy());
			}else if(ConfigCommands.INSTANT_IP_PUSH.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setInstantIPPush(ispData.getInstant_push_ip() != null ? 
						ispData.getInstant_push_ip() : req.getReplaceBy());
			}else if(ConfigCommands.ALERT_IP_PUSH.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setAlertIPPush(ispData.getAlert_push_ip() != null ? 
						ispData.getAlert_push_ip() : req.getReplaceBy());
			}else if(ConfigCommands.ACTIVITY_SCHEDULE_PUSH.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setActivitySchedulePush(ispData.getPush_setup_duration() != null ? 
						ispData.getPush_setup_duration() : req.getReplaceBy());
			}else if(ConfigCommands.METERING_MODE.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setMeteringMode(ispData.getMetering_mode() != null ? 
						String.valueOf(ispData.getMetering_mode()) : req.getReplaceBy());
			}else if(ConfigCommands.ENABLE_DISABLE_DISCONNECT_CONTROL.commandName.equalsIgnoreCase(req.getCommand())) {
				ispResponse.setLoadControlMode(ispData.getEnable_disable_control_modes() != null ? 
						String.valueOf(ispData.getEnable_disable_control_modes()) : req.getReplaceBy());
			}
			else{
				ispResponse.setBillingDate(ispData.getBilling_dates() != null ?
						ispData.getBilling_dates() : req.getReplaceBy());
				ispResponse.setPingMeter(ispData.getPing_meter() != null ?
						String.valueOf(ispData.getPing_meter()) : req.getReplaceBy());
				ispResponse.setRtcClock(ispData.getRtc_clock() != null ?
						dateConverter.convertDateToHesString(ispData.getRtc_clock()) : req.getReplaceBy());
				ispResponse.setTod(ispData.getTod() != null ?
						String.valueOf(ispData.getTod()) : req.getReplaceBy());
				ispResponse.setLastTokenRechargeAmount(ispData.getLast_token_recharge_amount() != null ? 
						String.valueOf(ispData.getLast_token_recharge_amount()) : req.getReplaceBy());
				ispResponse.setTotalAmountAtLastRecharge(ispData.getTotal_amount_at_last_recharge() != null ? 
						String.valueOf(ispData.getTotal_amount_at_last_recharge()) : req.getReplaceBy());
				ispResponse.setLastTokenRechargeTime(ispData.getLast_token_recharge_time() != null ?
						dateConverter.convertDateToHesString(ispData.getLast_token_recharge_time()) : req.getReplaceBy());
				ispResponse.setCurrentBalanceAmount(ispData.getCurrent_balance_amount() != null ?
						String.valueOf(ispData.getCurrent_balance_amount()) : req.getReplaceBy());
				ispResponse.setCurrentBalanceTime(ispData.getCurrent_balance_time() != null ? 
						dateConverter.convertDateToHesString(ispData.getCurrent_balance_time()) : req.getReplaceBy());
				ispResponse.setPaymentMode(ispData.getPayment_mode() != null ? 
						String.valueOf(ispData.getPayment_mode()) : req.getReplaceBy());
				ispResponse.setDemandIntegrationPeriod(ispData.getDemand_integration_period() != null ? 
						String.valueOf(ispData.getDemand_integration_period()) : req.getReplaceBy());
				ispResponse.setProfileCapturePeriod(ispData.getProfile_capture_period() != null ? 
						String.valueOf(ispData.getProfile_capture_period()) : req.getReplaceBy());
				ispResponse.setLoadLimit(ispData.getLoad_limit() != null ? 
						String.valueOf(ispData.getLoad_limit()) : req.getReplaceBy());
				ispResponse.setInstantIPPush(ispData.getInstant_push_ip() != null ? 
						ispData.getInstant_push_ip() : req.getReplaceBy());
				ispResponse.setAlertIPPush(ispData.getAlert_push_ip() != null ? 
						ispData.getAlert_push_ip() : req.getReplaceBy());
				ispResponse.setActivitySchedulePush(ispData.getPush_setup_duration() != null ? 
						ispData.getPush_setup_duration() : req.getReplaceBy());
				ispResponse.setMeteringMode(ispData.getMetering_mode() != null ? 
						String.valueOf(ispData.getMetering_mode()) : req.getReplaceBy());
				ispResponse.setLoadControlMode(ispData.getEnable_disable_control_modes() != null ? 
						String.valueOf(ispData.getEnable_disable_control_modes()) : req.getReplaceBy());
			}
			
			ispResponse.setBatchId(ispData.getBatch_id() != null ?
						ispData.getBatch_id() : req.getReplaceBy());

			ispResponseList.add(ispResponse);
		}

		LOG.info("Configuration Read Response Prepared.");
	}

}
