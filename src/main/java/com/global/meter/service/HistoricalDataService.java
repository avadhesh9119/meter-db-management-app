package com.global.meter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.meter.MeterDBAppStarter;
import com.global.meter.business.model.BillingDataCT;
import com.global.meter.business.model.BillingDataSinglePhase;
import com.global.meter.business.model.BillingDataThreePhase;
import com.global.meter.business.model.DailyLoadProfileCT;
import com.global.meter.business.model.DailyLoadProfileSinglePhase;
import com.global.meter.business.model.DailyLoadProfileThreePhase;
import com.global.meter.business.model.DeltaLoadProfileCT;
import com.global.meter.business.model.DeltaLoadProfileSinglePhase;
import com.global.meter.business.model.DeltaLoadProfileThreePhase;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.EventDataCT;
import com.global.meter.business.model.EventDataSinglePhase;
import com.global.meter.business.model.EventDataThreePhase;
import com.global.meter.business.model.InstantaneousCt;
import com.global.meter.business.model.InstantaneousSinglePhase;
import com.global.meter.business.model.InstantaneousThreePhase;
import com.global.meter.business.model.LastBillingDataCT;
import com.global.meter.business.model.LastBillingDataSinglePhase;
import com.global.meter.business.model.LastBillingDataThreePhase;
import com.global.meter.business.model.PrepayData;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.data.repository.BillingDataCTRepository;
import com.global.meter.data.repository.BillingDataRepository;
import com.global.meter.data.repository.BillingDataThreePhaseRepository;
import com.global.meter.data.repository.DailyLoadProfileCTRepository;
import com.global.meter.data.repository.DailyLoadProfileRepository;
import com.global.meter.data.repository.DailyLoadProfileThreePhaseRepository;
import com.global.meter.data.repository.DeltaLoadProfileCTRepository;
import com.global.meter.data.repository.DeltaLoadProfileRepository;
import com.global.meter.data.repository.DeltaLoadProfileThreePhaseRepository;
import com.global.meter.data.repository.EventDataCTRepository;
import com.global.meter.data.repository.EventDataRepository;
import com.global.meter.data.repository.EventDataThreePhaseRepository;
import com.global.meter.data.repository.InstantaneousCtRepository;
import com.global.meter.data.repository.InstantaneousSinglePhaseRepository;
import com.global.meter.data.repository.InstantaneousThreePhaseRepository;
import com.global.meter.data.repository.LastBillingDataCTRepository;
import com.global.meter.data.repository.LastBillingDataRepository;
import com.global.meter.data.repository.LastBillingDataThreePhaseRepository;
import com.global.meter.data.repository.PrepayDataRepository;
import com.global.meter.inventive.models.DayProfile;
import com.global.meter.inventive.models.DayProfileTime;
import com.global.meter.inventive.utils.DataUtils;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.task.PushDataToExtenalAddTask;

@Service
public class HistoricalDataService {

	private static final Logger LOG = LoggerFactory.getLogger(HistoricalDataService.class);
	
	@Autowired
	InstantaneousSinglePhaseRepository instantaneousSinglePhaseRepository;
	
	@Autowired
	InstantaneousThreePhaseRepository InstantaneousThreePhaseRepository;
	
	@Autowired
	InstantaneousCtRepository InstantaneousCtRepository;
	
	@Autowired
	DailyLoadProfileRepository dailyLoadProfileRepository;
	
	@Autowired
	DailyLoadProfileThreePhaseRepository dailyLoadProfileThreePhaseRepository;
	
	@Autowired
	DeltaLoadProfileRepository deltaLoadProfileRepository;
	
	@Autowired
	DeltaLoadProfileThreePhaseRepository deltaLoadProfileThreePhaseRepository;
	
	@Autowired
	BillingDataRepository billingDataRepository;
	
	@Autowired
	BillingDataThreePhaseRepository billingDataThreePhaseRepository;
	
	@Autowired
	LastBillingDataRepository lastBillingDataRepository;
	
	@Autowired
	LastBillingDataThreePhaseRepository lastBillingDataThreePhaseRepository;
	
	@Autowired
	EventDataRepository eventDataRepository;

	@Autowired
	EventDataThreePhaseRepository eventDataThreePhaseRepository;
	
	@Autowired
	PrepayDataRepository prepayDataRepository;
	
	@Autowired
	MetersCommandsConfiguration meteConfiguration;
	
	@Autowired
	CommonUtils commonUtils;
	
	@Autowired
	DailyLoadProfileCTRepository dailyLoadProfileCTRepository;
	
	@Autowired
	DeltaLoadProfileCTRepository deltaLoadProfileCTRepository;
	
	@Autowired
	BillingDataCTRepository billingDataCTRepository;
	
	@Autowired
	LastBillingDataCTRepository lastBillingDataCTRepository;
	
	@Autowired
	EventDataCTRepository eventDataCTRepository;
	
	@Autowired
	DateConverter dateConverter;
	
	
	/**
	 * Used to insert Instantaneous Data
	 * @param response
	 * @param deviceInfo
	 * @param meterResponse
	 * @return
	 */
	public boolean insertInstantaneousSinglePhaseData(DevicesInfo deviceInfo,
			MeterResponse meterResponse)
	{
		boolean flag = false;
		try {
			Object responseData[] = meterResponse.getData();
			List<String> obisCodeList = meterResponse.getObisCode();
			@SuppressWarnings("unchecked")
			List<Object> data = (List<Object>) responseData[0];
			
			//This is set to IN_PROGRESS because we need not to update Communication Datetime when data is null in case of instant.
			if(data == null || data.size() == 0) {
				meterResponse.setMessage(Constants.IN_PROGRESS);
				return flag;
			}
			
			String phaseVal = Constants.PhaseVal._1PH;
			if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._3PH;
			}
			else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._CT;
			}

			String commandName = deviceInfo.getMeter_type()+"_"+deviceInfo.getVersion()+ "_" + phaseVal + "_" +Constants.INSTANTANEOUS_READ;
			if(MeterDBAppStarter.meterDivFactorProperties.getProperty(commandName) == null) {
				commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.INSTANTANEOUS_READ;
			}
			LOG.info("Parsing & Inserting data of Instantaneous Single Phase : "+ deviceInfo.getDevice_serial_number());
			
			if(Constants.DeviceTypes.SINGLE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type()))
			{
				if("FLA".equalsIgnoreCase(deviceInfo.getMeter_type())) {
					int enableDisableLoad = "FLA".equalsIgnoreCase(deviceInfo.getMeter_type()) ? (int)data.get(20) : ((Boolean)data.get(20) == true ? 1: 0);
					InstantaneousSinglePhase instantaneousSinglePhase = new InstantaneousSinglePhase(deviceInfo.getDevice_serial_number(),
							String.valueOf(System.currentTimeMillis()),
							CommonUtils.getDoubleTypeObject(data.get(7), CommonUtils.getDivFactor(7, commandName, deviceInfo.getDevice_serial_number())),
							CommonUtils.getDoubleTypeObject(data.get(6), CommonUtils.getDivFactor(6, commandName, deviceInfo.getDevice_serial_number())),
							(Integer)data.get(16),
							CommonUtils.getDoubleTypeObject(data.get(19), CommonUtils.getDivFactor(19, commandName, deviceInfo.getDevice_serial_number())),
							CommonUtils.getDoubleTypeObject(data.get(9), CommonUtils.getDivFactor(9, commandName, deviceInfo.getDevice_serial_number())),
							CommonUtils.getDoubleTypeObject(data.get(18), CommonUtils.getDivFactor(18, commandName, deviceInfo.getDevice_serial_number())),
							CommonUtils.getDoubleTypeObject(data.get(8), CommonUtils.getDivFactor(8, commandName, deviceInfo.getDevice_serial_number())),
							CommonUtils.convertIntegerTypeObject(data.get(14)),
							(Integer)data.get(17),
							(Integer)data.get(15),
							CommonUtils.getInstanDate(null) ,deviceInfo.getDcu_serial_number(),
							deviceInfo.getDt_name(),
							enableDisableLoad,
							deviceInfo.getFeeder_name(),
							CommonUtils.getDoubleTypeObject(data.get(5), CommonUtils.getDivFactor(5, commandName, deviceInfo.getDevice_serial_number())),
							CommonUtils.getDoubleTypeObject(data.get(1), CommonUtils.getDivFactor(1, commandName, deviceInfo.getDevice_serial_number())),
							CommonUtils.getDoubleTypeObject(data.get(21), CommonUtils.getDivFactor(21, commandName, deviceInfo.getDevice_serial_number())),
							enableDisableLoad,
							CommonUtils.getDoubleTypeObject(data.get(12), CommonUtils.getDivFactor(12, commandName, deviceInfo.getDevice_serial_number())),
							CommonUtils.getInstanDate(data.get(13)),
							CommonUtils.getDoubleTypeObject(data.get(10), CommonUtils.getDivFactor(10, commandName, deviceInfo.getDevice_serial_number())),
							CommonUtils.getInstanDate(data.get(11)),
							CommonUtils.getInstanDate(null),
							CommonUtils.getInstanDate(data.get(0)),
							CommonUtils.getDoubleTypeObject(data.get(3), CommonUtils.getDivFactor(3, commandName, deviceInfo.getDevice_serial_number())),
							deviceInfo.getOwner_name(),
							CommonUtils.getDoubleTypeObject(data.get(2), CommonUtils.getDivFactor(2, commandName, deviceInfo.getDevice_serial_number())),
							CommonUtils.getDoubleTypeObject(data.get(4), CommonUtils.getDivFactor(4, commandName, deviceInfo.getDevice_serial_number())),
							deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name());
					
					instantaneousSinglePhaseRepository.save(instantaneousSinglePhase);
					LOG.info("Instantaneous Single Phase Data Inserted Successfully. "+ deviceInfo.getDevice_serial_number());
				}
				else {
					InstantaneousSinglePhase isp = new InstantaneousSinglePhase();
					isp.setDevice_serial_number(deviceInfo.getDevice_serial_number());
					isp.setActive_power_kw(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.ACTIVE_POWER_KW , commandName, deviceInfo.getDevice_serial_number()));
					isp.setApparent_power_kva(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.APPARENT_POWER_KVA , commandName, deviceInfo.getDevice_serial_number()));
					isp.setCumulative_bill_count(CommonUtils.convertIntegerTypeObject(
							data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.BILLING_COUNT))));
					isp.setCumulative_energy_kvah_export(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.CUMULATIVE_EXPORT_KVAH , commandName, deviceInfo.getDevice_serial_number()));
					isp.setCumulative_energy_kvah_import(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.CUMULATIVE_IMPORT_KVAH , commandName, deviceInfo.getDevice_serial_number()));
					isp.setCumulative_energy_kwh_export(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.CUMULATIVE_EXPORT_KWH , commandName, deviceInfo.getDevice_serial_number()));
					isp.setCumulative_energy_kwh_import(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.CUMULATIVE_IMPORT_KWH, commandName, deviceInfo.getDevice_serial_number()));
					isp.setCumulative_power_on_duration(CommonUtils.convertIntegerTypeObject(
							data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.CUMULATIVE_POWER_ON_DURATION))));
					isp.setCumulative_program_count(CommonUtils.convertIntegerTypeObject(
							data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.PROGRAMMING_COUNT))));
					isp.setCumulative_tamper_count(CommonUtils.convertIntegerTypeObject(
							data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.TAMPER_COUNT))));
					isp.setDatetime(CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.RTC_DATETIME))));
					isp.setDcu_serial_number(deviceInfo.getDcu_serial_number());
					isp.setDt_name(deviceInfo.getDt_name());
					if("AME".equalsIgnoreCase(deviceInfo.getMeter_type()) || "RMI".equalsIgnoreCase(deviceInfo.getMeter_type())) {
						isp.setEnable_disable_load(obisCodeList.contains(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT) 
								? (Integer.parseInt(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT)).toString()) == 1 ? 1: 0) : null);
					}
					else{
						isp.setEnable_disable_load(obisCodeList.contains(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT) 
								? ((Boolean)data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT)) == true ? 1: 0) : null);
					}
					isp.setFeeder_name(deviceInfo.getFeeder_name());
					isp.setFrequency(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.FREQ, commandName, deviceInfo.getDevice_serial_number()));
					isp.setInstant_voltage(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.VOLTAGE, commandName, deviceInfo.getDevice_serial_number()));
					isp.setLoad_limits(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.LOAD_LIMIT_VAL, commandName, deviceInfo.getDevice_serial_number()));
					
					if("AME".equalsIgnoreCase(deviceInfo.getMeter_type()) || "RMI".equalsIgnoreCase(deviceInfo.getMeter_type())) {
						isp.setLoad_limit_status(CommonUtils.convertIntegerTypeObject(
								obisCodeList.contains(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT) 
								? (Integer.parseInt(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT)).toString()) == 1 ? 1: 0) : null));
					}
					else{
						isp.setLoad_limit_status(CommonUtils.convertIntegerTypeObject(
								obisCodeList.contains(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT) 
								? ((Boolean)data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT)) == true ? 1: 0) : null));
					}
					
					isp.setMaximum_demand_kva(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.MAX_DEMAND_KVA, commandName, deviceInfo.getDevice_serial_number()));
					isp.setMaximum_demand_kva_datetime(CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.MAX_DEMAND_KVA)+1)));
					isp.setMaximum_demand_kw(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.MAX_DEMAND_KW, commandName, deviceInfo.getDevice_serial_number()));
					isp.setMaximum_demand_kw_datetime(CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.MAX_DEMAND_KW_DATETIME)+1)));
					isp.setMdas_datetime(CommonUtils.getInstanDate(null));
					isp.setMeter_datetime(CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.RTC_DATETIME))));
					isp.setNeutral_current(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.NUTRAL_CURRENT, commandName, deviceInfo.getDevice_serial_number()));
					isp.setOwner_name(deviceInfo.getOwner_name());
					isp.setPhase_current(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.PHASE_CURRENT, commandName, deviceInfo.getDevice_serial_number()));
					isp.setPower_factor(CommonUtils.getObisDoubleObject(data, 
							obisCodeList, Constants.ObisCode.INSTANT_1P.SIGNED_PF, commandName, deviceInfo.getDevice_serial_number()));
					isp.setSubdevision_name(deviceInfo.getSubdevision_name());
					isp.setSubstation_name(deviceInfo.getSubstation_name());
					isp.setTracking_id(String.valueOf(System.currentTimeMillis()));
					
					instantaneousSinglePhaseRepository.save(isp);
					LOG.info("Instantaneous Single Phase Data Inserted Successfully. "+ deviceInfo.getDevice_serial_number());
				
				}
				/*
				RecentInstantaneousSinglePhase recentInstantaneousSinglePhase = CommonUtils.getMapper().readValue(
						CommonUtils.getMapper().writeValueAsString(instantaneousSinglePhase), RecentInstantaneousSinglePhase.class);
				recentInstantaneousSinglePhaseRepository.save(recentInstantaneousSinglePhase);
				LOG.info("Recent Instantaneous Single Phase Data is also Inserted Successfully. "+ deviceInfo.getDevice_serial_number());
				*/
			}
			else if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())){
				int enableDisableLoad;
				int loadLimitStatus;
				switch (deviceInfo.getMeter_type()) {
				case "AME":
					enableDisableLoad = obisCodeList.contains(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT) 
							? ((Integer)data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT)) == 1 ? 1: 0) : null;
                break;
				default:
					enableDisableLoad = obisCodeList.contains(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT) 
							? (Boolean.parseBoolean(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT)).toString()) == true ? 1: 0) : null;
				}
				
				switch (deviceInfo.getMeter_type()) {
				case "AME":
					loadLimitStatus = CommonUtils.convertIntegerTypeObject(
							obisCodeList.contains(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT) 
							? ((Integer)data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT)) == 1 ? 1: 0) : null);
				break;
				default:
					loadLimitStatus = CommonUtils.convertIntegerTypeObject(
							obisCodeList.contains(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT) 
							? (Boolean.parseBoolean(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_1P.CONNECT_DISCONNECT_STATUS_LOAD_LIMIT)).toString()) == true ? 1: 0) : null);
				}
				InstantaneousThreePhase instantaneousThreePhase = new InstantaneousThreePhase(deviceInfo.getDevice_serial_number(),
						String.valueOf(System.currentTimeMillis()),
						CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_3P.RTC_DATETIME))),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.ACTIVE_POWER_KW , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.APP_POW_KVA , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.POW_OFF_DURATION_MINS , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_3P.BILLING_COUNT))),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.KAVH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.KVARH_Q1 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.KVARH_Q2 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.KVARH_Q3 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.KVARH_Q4 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.KWH_EXPORT , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.KWH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_3P.PROGRAMMING_COUNT))),
						CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_3P.TAMPER_COUNT))),
						deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name(),
						enableDisableLoad,
						deviceInfo.getFeeder_name(),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.FREQ , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.VOL_L1 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.VOL_L2 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.VOL_L3 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getInstanDate(obisCodeList.contains(Constants.ObisCode.INSTANT_3P.BILLING_DATE)? data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_3P.BILLING_DATE)) : null),
						obisCodeList.contains(Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_KW) ? 
						CommonUtils.getObisDoubleObject(data, meterResponse.getObisCode(), Constants.ObisCode.INSTANT_3P.LOAD_LIMIT_KW , commandName, deviceInfo.getDevice_serial_number()) : null,
						loadLimitStatus,
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.MD_ACTIVE_IMPORT_KVA , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_3P.MD_ACTIVE_IMPORT_DATETIME_KVA)+1)),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.MD_ACTIVE_IMPORT_KW , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_3P.MD_ACTIVE_IMPORT_DATETIME_KW)+1)),
						CommonUtils.getInstanDate(null),
						CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_3P.RTC_DATETIME))),
						CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_3P.NO_POWER_FAILURE))),
						deviceInfo.getOwner_name(),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.CURR_L1 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.CURR_L2 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.CURR_L3 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.PF_3P , commandName, deviceInfo.getDevice_serial_number()), 
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.PF_L1 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.PF_L2 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.PF_L3 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.REACTIVE_POWER_KVAR , commandName, deviceInfo.getDevice_serial_number()),
						deviceInfo.getSubdevision_name(),deviceInfo.getSubstation_name()
				);
				instantaneousThreePhase.setSigned_power_factor_b_phase(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.SIGNED_PF_BP , commandName, deviceInfo.getDevice_serial_number()));
				instantaneousThreePhase.setCumm_power_on_duration_in_mins(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_3P.CUMULATIVE_POWER_ON_DURATION , commandName, deviceInfo.getDevice_serial_number()));

				if(commonUtils.isValid()) {
					InstantaneousThreePhaseRepository.save(instantaneousThreePhase);
				}
				LOG.info("Instantaneous Three Phase Data Inserted Successfully . "+ deviceInfo.getDevice_serial_number());
			}
			else if(Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				
				InstantaneousCt instantaneousCt = new InstantaneousCt(deviceInfo.getDevice_serial_number(),
						String.valueOf(System.currentTimeMillis()),
						CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.RTC_DATETIME))),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.ACTIVE_POWER_KW , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.APP_POW_KVA , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.POW_OFF_DURATION_MINS , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.BILLING_COUNT))),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.KAVH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.KVARH_Q1 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.KVARH_Q2 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.KVARH_Q3 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.KVARH_Q4 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.KWH_EXPORT , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.KWH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.PROGRAMMING_COUNT))),
						CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.TAMPER_COUNT))),
						deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name(),
						obisCodeList.contains(Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_FUNCTION_STATUS) 
							? ((Boolean)data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_FUNCTION_STATUS)) == true ? 1: 0) : null,
						deviceInfo.getFeeder_name(),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.FREQ , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.VOL_L1 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.VOL_L2 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.VOL_L3 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.BILLING_DATE))),
						obisCodeList.contains(Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_KW) ? 
								CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_KW))) : null,
						obisCodeList.contains(Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_FUNCTION_STATUS) 
								? ((Boolean)data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.LOAD_LIMIT_FUNCTION_STATUS)) == true ? 1: 0) : null,
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.MD_ACTIVE_IMPORT_KVA , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.MD_ACTIVE_IMPORT_DATETIME_KVA)+1)),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.MD_ACTIVE_IMPORT_KW , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.MD_ACTIVE_IMPORT_DATETIME_KW)+1)),
						CommonUtils.getInstanDate(null),
						CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.RTC_DATETIME))),
						CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.NO_POWER_FAILURE))),
						deviceInfo.getOwner_name(),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.CURR_L1 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.CURR_L2 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.CURR_L3 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.PF_3P , commandName, deviceInfo.getDevice_serial_number()), 
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.PF_L1 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.PF_L2 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.PF_L3 , commandName, deviceInfo.getDevice_serial_number()),
						CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.REACTIVE_POWER_KVAR , commandName, deviceInfo.getDevice_serial_number()),
						deviceInfo.getSubdevision_name(),deviceInfo.getSubstation_name()
				);
//				instantaneousCt.setSigned_power_factor_b_phase(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.SIGNED_PF_BP , commandName, deviceInfo.getDevice_serial_number()));
				
				instantaneousCt.setMaximum_demand_w_export(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.MD_EXPORT_W , commandName, deviceInfo.getDevice_serial_number()));
				instantaneousCt.setMaximum_demand_w_export_datetime(CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.MD_EXPORT_DATETIME_W)+1)));
				instantaneousCt.setMaximum_demand_va_export(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.MD_EXPORT_VA , commandName, deviceInfo.getDevice_serial_number()));
				instantaneousCt.setMaximum_demand_va_export_datetime(CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.INSTANT_CT.MD_EXPORT_DATETIME_VA)+1)));
				instantaneousCt.setAngle_phase_volt_ab(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.ANGLE_PHASE_VOLT_AB , commandName, deviceInfo.getDevice_serial_number()));
				instantaneousCt.setAngle_phase_volt_bc(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.ANGLE_PHASE_VOLT_BC , commandName, deviceInfo.getDevice_serial_number()));
				instantaneousCt.setAngle_phase_volt_ac(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.ANGLE_PHASE_VOLT_AC , commandName, deviceInfo.getDevice_serial_number()));
				instantaneousCt.setCumm_power_on_duration_in_mins(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.INSTANT_CT.CUMULATIVE_POWER_ON_DURATION , commandName, deviceInfo.getDevice_serial_number()));
	
				if(commonUtils.isValid()) {
					InstantaneousCtRepository.save(instantaneousCt);
				}
				LOG.info("Instantaneous Three Phase Data Inserted Successfully . "+ deviceInfo.getDevice_serial_number());
			}
			flag = true;
		} catch (Exception e) {
			LOG.error("Issue in inserting data for meter "+ deviceInfo.getDevice_serial_number() +" in db due to :"+ e.getMessage());
			meterResponse.setMessage("Issue in inserting data due to: "+e.getMessage());
		}
		return flag;
	}
	
	/**
	 * Daily Load Profile Data
	 * @param response
	 * @param deviceInfo
	 * @param meterResponse
	 * @return
	 */
	public boolean insertDailyLoadProfileData(DevicesInfo deviceInfo,
			MeterResponse meterResponse)
	{
		boolean flag = false;
		try {
			Object responseData[] = meterResponse.getData();
			
			//This is set to IN_PROGRESS because we need not to update Communication Datetime when data is null in case of DailyLP.
			if(responseData == null || responseData.length == 0) {
				meterResponse.setMessage(Constants.IN_PROGRESS);
				return flag;
			}
			
			List<String> obisCodeList = meterResponse.getObisCode();
			String phaseVal = Constants.PhaseVal._1PH;
			if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._3PH;
			}
			else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._CT;
			}

			//String commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.DAILY_LOAD_PROFILE;
			
			String commandName = deviceInfo.getMeter_type()+"_"+deviceInfo.getVersion()+ "_" + phaseVal + "_" +Constants.DAILY_LOAD_PROFILE;
			if(MeterDBAppStarter.meterDivFactorProperties.getProperty(commandName) == null) {
				commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.DAILY_LOAD_PROFILE;
			}
			
			if(Constants.DeviceTypes.SINGLE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type()))
			{
				List<DailyLoadProfileSinglePhase> dailyLoadProfileDataList = new ArrayList<DailyLoadProfileSinglePhase>();
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					

					DailyLoadProfileSinglePhase dailyLoadProfileSinglePhase = new DailyLoadProfileSinglePhase(
							deviceInfo.getDevice_serial_number(),
							
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.RTC_DATETIME))), 
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KVAH_EXPORT , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KVAH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KWH_EXPORT , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KWH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
							deviceInfo.getDcu_serial_number(),deviceInfo.getDt_name()
							, deviceInfo.getFeeder_name(),  new Date(System.currentTimeMillis()), 
							CommonUtils.getInstanDate(data.get(0)), deviceInfo.getOwner_name(), 
							deviceInfo.getSubdevision_name(), deviceInfo.getSubstation_name());
					
					dailyLoadProfileSinglePhase.setMaximum_demand_kw(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KW , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kw_datetime(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW) ?
									CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW)+1)) : null);
					dailyLoadProfileSinglePhase.setMaximum_demand_kva(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KVA , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kva_datetime(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA) ?
									CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA)+1)) : null);
					
					dailyLoadProfileSinglePhase.setCumulative_energy_kvah_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KVAH_IMPORT_T1 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kvah_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KVAH_IMPORT_T2 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kvah_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KVAH_IMPORT_T3 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kvah_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KVAH_IMPORT_T4 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kvah_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KVAH_IMPORT_T5 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kvah_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KVAH_IMPORT_T6 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kvah_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KVAH_IMPORT_T7 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kvah_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KVAH_IMPORT_T8 , commandName, deviceInfo.getDevice_serial_number()));;
					
					dailyLoadProfileSinglePhase.setCumulative_energy_kwh_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KWH_IMPORT_T1 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kwh_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KWH_IMPORT_T2 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kwh_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KWH_IMPORT_T3 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kwh_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KWH_IMPORT_T4 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kwh_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KWH_IMPORT_T5 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kwh_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KWH_IMPORT_T6 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kwh_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KWH_IMPORT_T7 , commandName, deviceInfo.getDevice_serial_number()));;
					dailyLoadProfileSinglePhase.setCumulative_energy_kwh_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.KWH_IMPORT_T8 , commandName, deviceInfo.getDevice_serial_number()));;
					
					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KW_T1 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier1_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T1) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T1)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KW_T2 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier2_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T2) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T2)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KW_T3 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier3_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T3) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T3)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KW_T4 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier4_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T4) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T4)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KW_T5 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier5_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T5) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T5)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KW_T6 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier6_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T6) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T6)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KW_T7 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier7_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T7) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T7)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KW_T8 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kw_tier8_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T8) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KW_T8)+1)) : null);
				
					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KVA_T1 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier1_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T1) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T1)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KVA_T2 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier2_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T2) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T2)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KVA_T3 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier3_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T3) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T3)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KVA_T4 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier4_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T4) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T4)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KVA_T5 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier5_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T5) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T5)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KVA_T6 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier6_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T6) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T6)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KVA_T7 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier7_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T7) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T7)+1)) : null);

					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_1P.MD_KVA_T8 , commandName, deviceInfo.getDevice_serial_number()));
					dailyLoadProfileSinglePhase.setMaximum_demand_kva_tier8_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T8) ?
					                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_1P.MD_DATETIME_KVA_T8)+1)) : null);
					
					Optional<DailyLoadProfileSinglePhase> isDataExits = dailyLoadProfileRepository.getData(
							dailyLoadProfileSinglePhase.getDevice_serial_number(), dailyLoadProfileSinglePhase.getDatetime());
					
					if(isDataExits.isPresent()) {
						dailyLoadProfileDataList.add(dailyLoadProfileSinglePhase);
					}
					
				}
				LOG.info("Parsing & Inserting data of Daily Load Profile Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					dailyLoadProfileRepository.saveAll(dailyLoadProfileDataList);
				}
			}
			else if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type()))
			{
				List<DailyLoadProfileThreePhase> dailyLoadProfileThreePhaseDataList = new ArrayList<DailyLoadProfileThreePhase>();
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					DailyLoadProfileThreePhase dailyLoadProfileThreePhase = null;
					if(deviceInfo.getMeter_type().equalsIgnoreCase(Constants.MeterMake.INISH)) {
						dailyLoadProfileThreePhase = new DailyLoadProfileThreePhase(
								deviceInfo.getDevice_serial_number(),
								CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.RTC_DATETIME))), 
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
								deviceInfo.getDcu_serial_number(),deviceInfo.getDt_name()
								, deviceInfo.getFeeder_name(),  new Date(System.currentTimeMillis()), 
								CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.RTC_DATETIME))),
								deviceInfo.getOwner_name(), 
								deviceInfo.getSubdevision_name(), deviceInfo.getSubstation_name(),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVARH_Q1, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVARH_Q2, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVARH_Q3, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVARH_Q4, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA, commandName, deviceInfo.getDevice_serial_number()),
								obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_KVA_DATETIME) ?
								     CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_KVA_DATETIME)+1)) : null,
							    CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW, commandName, deviceInfo.getDevice_serial_number()),
							    obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_KW_DATETIME) ?
										CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_KW_DATETIME)+1)) : null
							    );
						dailyLoadProfileThreePhase.setMaximum_demand_kw(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_datetime(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_KW_DATETIME) ?
										CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_KW_DATETIME)+1)) : null);
						dailyLoadProfileThreePhase.setMaximum_demand_kva(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_datetime(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_KVA_DATETIME) ?
										CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_KVA_DATETIME)+1)) : null);
						
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T1 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T2 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T3 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T4 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T5 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T6 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T7 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T8 , commandName, deviceInfo.getDevice_serial_number()));;
						
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T1 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T2 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T3 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T4 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T5 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T6 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T7 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T8 , commandName, deviceInfo.getDevice_serial_number()));;
						
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T1 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier1_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T1) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T1)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T2 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier2_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T2) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T2)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T3 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier3_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T3) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T3)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T4 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier4_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T4) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T4)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T5 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier5_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T5) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T5)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T6 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier6_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T6) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T6)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T7 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier7_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T7) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T7)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T8 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier8_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T8) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T8)+1)) : null);
					
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T1 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier1_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T1) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T1)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T2 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier2_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T2) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T2)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T3 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier3_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T3) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T3)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T4 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier4_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T4) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T4)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T5 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier5_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T5) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T5)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T6 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier6_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T6) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T6)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T7 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier7_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T7) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T7)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T8 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier8_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T8) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T8)+1)) : null);
						
						dailyLoadProfileThreePhase.setMaximum_demand_kva_export(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_EXPORT_VA , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier8_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_EXPORT_DATETIME_VA) ?
								CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_EXPORT_DATETIME_VA)+1)) : null);

					}
					else {
						dailyLoadProfileThreePhase = new DailyLoadProfileThreePhase(
							deviceInfo.getDevice_serial_number(),CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.RTC_DATETIME)), dateConverter), 
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							deviceInfo.getDcu_serial_number(),deviceInfo.getDt_name()
							, deviceInfo.getFeeder_name(),  new Date(System.currentTimeMillis()), 
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.RTC_DATETIME)), dateConverter), deviceInfo.getOwner_name(), 
							deviceInfo.getSubdevision_name(), deviceInfo.getSubstation_name(),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVARH_Q1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVARH_Q2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVARH_Q3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVARH_Q4, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_KVA_DATETIME) ?
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_KVA_DATETIME)+1), dateConverter) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_KW_DATETIME) ?
									CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_KW_DATETIME)+1), dateConverter) : null
							);
						dailyLoadProfileThreePhase.setMaximum_demand_kw(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_datetime(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_KW_DATETIME) ?
										CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_KW_DATETIME)+1)) : null);
						dailyLoadProfileThreePhase.setMaximum_demand_kva(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_datetime(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_KVA_DATETIME) ?
										CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_KVA_DATETIME)+1)) : null);
						
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T1 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T2 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T3 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T4 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T5 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T6 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T7 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kvah_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KVAH_IMPORT_T8 , commandName, deviceInfo.getDevice_serial_number()));;
						
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T1 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T2 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T3 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T4 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T5 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T6 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T7 , commandName, deviceInfo.getDevice_serial_number()));;
						dailyLoadProfileThreePhase.setCumulative_energy_kwh_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.KWH_IMPORT_T8 , commandName, deviceInfo.getDevice_serial_number()));;
						
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T1 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier1_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T1) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T1)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T2 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier2_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T2) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T2)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T3 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier3_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T3) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T3)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T4 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier4_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T4) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T4)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T5 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier5_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T5) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T5)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T6 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier6_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T6) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T6)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T7 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier7_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T7) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T7)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KW_T8 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kw_tier8_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T8) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KW_T8)+1)) : null);
					
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T1 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier1_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T1) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T1)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T2 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier2_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T2) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T2)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T3 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier3_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T3) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T3)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T4 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier4_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T4) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T4)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T5 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier5_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T5) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T5)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T6 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier6_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T6) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T6)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T7 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier7_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T7) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T7)+1)) : null);

						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_KVA_T8 , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier8_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T8) ?
						                CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_DATETIME_KVA_T8)+1)) : null);
						
						dailyLoadProfileThreePhase.setMaximum_demand_kva_export(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_3P.MD_EXPORT_VA , commandName, deviceInfo.getDevice_serial_number()));
						dailyLoadProfileThreePhase.setMaximum_demand_kva_tier8_date(obisCodeList.contains(Constants.ObisCode.DAILY_LP_3P.MD_EXPORT_DATETIME_VA) ?
								CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_3P.MD_EXPORT_DATETIME_VA)+1)) : null);
					}
					
					Optional<DailyLoadProfileThreePhase> isDataExits = dailyLoadProfileThreePhaseRepository.getData(
							dailyLoadProfileThreePhase.getDevice_serial_number(), dailyLoadProfileThreePhase.getDatetime());
					
					if(isDataExits.isPresent()) {
						dailyLoadProfileThreePhaseDataList.add(dailyLoadProfileThreePhase);
					}
					
				}
				LOG.info("Parsing & Inserting data of Daily Load Profile Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					dailyLoadProfileThreePhaseRepository.saveAll(dailyLoadProfileThreePhaseDataList);
				}
			}
			else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {

				List<DailyLoadProfileCT> dailyLoadProfileCTDataList = new ArrayList<DailyLoadProfileCT>();
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					DailyLoadProfileCT dailyLoadProfileCT = null;
					if(deviceInfo.getMeter_type().equalsIgnoreCase(Constants.MeterMake.INISH)) {
						dailyLoadProfileCT = new DailyLoadProfileCT(
								deviceInfo.getDevice_serial_number(),
								CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_CT.RTC_DATETIME))), 
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
								deviceInfo.getDcu_serial_number(),deviceInfo.getDt_name(),
							    deviceInfo.getFeeder_name(),  new Date(System.currentTimeMillis()), 
								CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_CT.RTC_DATETIME))),
								deviceInfo.getOwner_name(), 
								deviceInfo.getSubdevision_name(), deviceInfo.getSubstation_name(),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KVARH_Q1, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KVARH_Q2, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KVARH_Q3, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KVARH_Q4, commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.MD_KVA, commandName, deviceInfo.getDevice_serial_number()),
								obisCodeList.contains(Constants.ObisCode.DAILY_LP_CT.MD_KVA_DATETIME) ?
								CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_CT.MD_KVA_DATETIME)+1)) : null,			
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.MD_KW, commandName, deviceInfo.getDevice_serial_number()),
								obisCodeList.contains(Constants.ObisCode.DAILY_LP_CT.MD_KW_DATETIME) ?
										CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_CT.MD_KW_DATETIME)+1)) : null
								);
						dailyLoadProfileCT.setNo_supply(obisCodeList.contains(Constants.ObisCode.DAILY_LP_CT.NO_SUPPLY) ? String.valueOf(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_CT.NO_SUPPLY))) : null);
						dailyLoadProfileCT.setNo_load(obisCodeList.contains(Constants.ObisCode.DAILY_LP_CT.NO_LOAD) ? String.valueOf(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_CT.NO_LOAD))) : null);

					}
					else {
						dailyLoadProfileCT = new DailyLoadProfileCT(
							deviceInfo.getDevice_serial_number(),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_CT.RTC_DATETIME))), 
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							deviceInfo.getDcu_serial_number(),deviceInfo.getDt_name()
							, deviceInfo.getFeeder_name(),  new Date(System.currentTimeMillis()), 
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_CT.RTC_DATETIME))), deviceInfo.getOwner_name(), 
							deviceInfo.getSubdevision_name(), deviceInfo.getSubstation_name(),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KVARH_Q1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KVARH_Q2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KVARH_Q3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.KVARH_Q4, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.MD_KVA, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.contains(Constants.ObisCode.DAILY_LP_CT.MD_KVA_DATETIME) ?
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_CT.MD_KVA_DATETIME)+1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DAILY_LP_CT.MD_KW, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.contains(Constants.ObisCode.DAILY_LP_CT.MD_KW_DATETIME) ?
									CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_CT.MD_KW_DATETIME)+1)) : null								
							 );
						dailyLoadProfileCT.setNo_supply(obisCodeList.contains(Constants.ObisCode.DAILY_LP_CT.NO_SUPPLY) ? String.valueOf(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_CT.NO_SUPPLY))) : null);
						dailyLoadProfileCT.setNo_load(obisCodeList.contains(Constants.ObisCode.DAILY_LP_CT.NO_LOAD) ? String.valueOf(data.get(obisCodeList.indexOf(Constants.ObisCode.DAILY_LP_CT.NO_LOAD))) : null);
						
					}
					
					Optional<DailyLoadProfileCT> isDataExits = dailyLoadProfileCTRepository.getData(
							dailyLoadProfileCT.getDevice_serial_number(), dailyLoadProfileCT.getDatetime());
					
					if(!isDataExits.isPresent()) {
						dailyLoadProfileCTDataList.add(dailyLoadProfileCT);
					}
						
				}
				LOG.info("Parsing & Inserting data of Daily Load Profile Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					dailyLoadProfileCTRepository.saveAll(dailyLoadProfileCTDataList);
				}
			
			}
			
			LOG.info("Daily Load Profile Data Inserted Successfully. "+ deviceInfo.getDevice_serial_number());
			flag = true;
		} catch (Exception e) {
			LOG.error("Issue in inserting Daily Load Profile data for meter "+ deviceInfo.getDevice_serial_number() 
				+" in db due to :"+ e.getMessage());
			meterResponse.setMessage("Issue in inserting data due to: "+e.getMessage());
		}
		return flag;
	}
	
	/**
	 * Insert Delta Load Profile Data
	 * @param response
	 * @param deviceInfo
	 * @param meterResponse
	 * @return
	 */
	public boolean insertDeltaLoadProfileData(DevicesInfo deviceInfo,
			MeterResponse meterResponse)
	{
		boolean flag = false;
		try {
			String phaseVal = Constants.PhaseVal._1PH;
			if (Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._3PH;
			} else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._CT;
			}

			//String commandName = deviceInfo.getMeter_type() + "_" + phaseVal + "_" + Constants.DELTA_LOAD_PROFILE;
			
			String commandName = deviceInfo.getMeter_type()+"_"+deviceInfo.getVersion()+ "_" + phaseVal + "_" +Constants.DELTA_LOAD_PROFILE;
			if(MeterDBAppStarter.meterDivFactorProperties.getProperty(commandName) == null) {
				commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.DELTA_LOAD_PROFILE;
			}
			
			Object responseData[] = meterResponse.getData();

			// This is set to IN_PROGRESS because we need not to update Communication
			// Datetime when data is null in case of DeltaLP.
			if (responseData == null || responseData.length == 0) {
				meterResponse.setMessage(Constants.IN_PROGRESS);
				return flag;
			}
			
			List<String> obisCodeList = meterResponse.getObisCode();
			
			if(Constants.DeviceTypes.SINGLE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				List<DeltaLoadProfileSinglePhase> deltaLoadProfileList = new ArrayList<DeltaLoadProfileSinglePhase>();
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	

					DeltaLoadProfileSinglePhase deltaLoadProfileSinglePhase = new DeltaLoadProfileSinglePhase(
							deviceInfo.getDevice_serial_number(), 
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DELTA_LP_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_1P.AVG_CURRENT , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_1P.AVG_VOLTAGE , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_1P.KVAH_EXPORT , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_1P.KVAH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_1P.KWH_EXPORT , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_1P.KWH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
							deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name(), deviceInfo.getFeeder_name(),
							null, new Date(System.currentTimeMillis()),
							new Date(System.currentTimeMillis()), deviceInfo.getOwner_name(), 
							deviceInfo.getSubdevision_name(), deviceInfo.getSubstation_name());
					
					deltaLoadProfileSinglePhase.setStatus_byte(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_1P.STATUS_BYTE , commandName, deviceInfo.getDevice_serial_number()));
					deltaLoadProfileSinglePhase.setAvg_signal_strength(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_1P.AVG_SIGNAL_STRENGTH , commandName, deviceInfo.getDevice_serial_number()));
					
					
					Optional<DeltaLoadProfileSinglePhase> isDataExits = deltaLoadProfileRepository.getData(
							deltaLoadProfileSinglePhase.getDevice_serial_number(), deltaLoadProfileSinglePhase.getInterval_datetime());
					
					if(!isDataExits.isPresent()) {
						deltaLoadProfileList.add(deltaLoadProfileSinglePhase);
					}
					
					
				}
				LOG.info("Single Phase: Parsing & Inserting data of Delta Load Profile Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					deltaLoadProfileRepository.saveAll(deltaLoadProfileList);
				}
			}
			else if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				List<DeltaLoadProfileThreePhase> deltaLoadProfileList = new ArrayList<DeltaLoadProfileThreePhase>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;
					DeltaLoadProfileThreePhase deltaLoadProfileThreePhase = null;
					if(deviceInfo.getMeter_type().equalsIgnoreCase("INISH")) {
						deltaLoadProfileThreePhase = new DeltaLoadProfileThreePhase(
								deviceInfo.getDevice_serial_number(), 
								CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DELTA_LP_3P.RTC_DATETIME))),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KVAH_EXPORT , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KVAH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KWH_EXPORT , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KWH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
								deviceInfo.getDcu_serial_number(),
								deviceInfo.getDt_name(),deviceInfo.getFeeder_name(),
								new Date(System.currentTimeMillis()),
								deviceInfo.getOwner_name(),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.CURR_L1 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.CURR_L2 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.CURR_L3 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.VOL_L1 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.VOL_L2 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.VOL_L3 , commandName, deviceInfo.getDevice_serial_number()),
								deviceInfo.getSubdevision_name(),deviceInfo.getSubstation_name(),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KVARH_Q1 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KVARH_Q2 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KVARH_Q3 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KVARH_Q4 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.STATUS_BYTE , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.AVG_SIGNAL_STRENGTH , commandName, deviceInfo.getDevice_serial_number())
							);
						deltaLoadProfileThreePhase.setPower_downtime_in_mins(
								CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.DELTA_LP_3P.POWER_DOWNTIME_IN_MINS))));
						deltaLoadProfileThreePhase.setR_phase_active_power_kw(
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.R_PHASE_ACTIVE_POWER_KW , commandName, deviceInfo.getDevice_serial_number()));
						deltaLoadProfileThreePhase.setY_phase_active_power_kw(
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.Y_PHASE_ACTIVE_POWER_KW , commandName, deviceInfo.getDevice_serial_number()));
						deltaLoadProfileThreePhase.setB_phase_active_power_kw(
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.B_PHASE_ACTIVE_POWER_KW , commandName, deviceInfo.getDevice_serial_number()));
					}
					else {
						// Few of the obis code mapping is pending as we need name from manufacturer.
						deltaLoadProfileThreePhase = new DeltaLoadProfileThreePhase(
								deviceInfo.getDevice_serial_number(), 
								CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DELTA_LP_3P.RTC_DATETIME))),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KVAH_EXPORT , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KVAH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KWH_EXPORT , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KWH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
								deviceInfo.getDcu_serial_number(),
								deviceInfo.getDt_name(),deviceInfo.getFeeder_name(),
								new Date(System.currentTimeMillis()),
								deviceInfo.getOwner_name(),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.CURR_L1 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.CURR_L2 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.CURR_L3 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.VOL_L1 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.VOL_L2 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.VOL_L3 , commandName, deviceInfo.getDevice_serial_number()),
								deviceInfo.getSubdevision_name(),deviceInfo.getSubstation_name(),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KVARH_Q1 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KVARH_Q2 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KVARH_Q3 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KVARH_Q4 , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.STATUS_BYTE , commandName, deviceInfo.getDevice_serial_number()),
								CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.AVG_SIGNAL_STRENGTH , commandName, deviceInfo.getDevice_serial_number())
						);
						deltaLoadProfileThreePhase.setLead_block_energy_kvarh(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.LEAD_BLOCK_ENERGY_KVARH , commandName, deviceInfo.getDevice_serial_number()));
						deltaLoadProfileThreePhase.setCumulative_energy_kwh(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.CUMULATIVE_ENERGY_KWH , commandName, deviceInfo.getDevice_serial_number()));
						deltaLoadProfileThreePhase.setCumulative_energy_kvah(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.CUMULATIVE_ENERGY_KVAH , commandName, deviceInfo.getDevice_serial_number()));
						deltaLoadProfileThreePhase.setMd_type(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.MD_TYPE , commandName, deviceInfo.getDevice_serial_number()));
						deltaLoadProfileThreePhase.setMd_block_sliding_type(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.MD_BLOCK_SLIDING_TYPE , commandName, deviceInfo.getDevice_serial_number()));
						deltaLoadProfileThreePhase.setNeutral_current(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.NEUTRAL_CURRENT_3P , commandName, deviceInfo.getDevice_serial_number()));
						deltaLoadProfileThreePhase.setMobile_no(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.MOBILE_NO , commandName, deviceInfo.getDevice_serial_number()));
						deltaLoadProfileThreePhase.setInstant_avgpf(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.INSTANT_AVGPF , commandName, deviceInfo.getDevice_serial_number()));
						deltaLoadProfileThreePhase.setModule_rtc(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.MODULE_RTC , commandName, deviceInfo.getDevice_serial_number()));
						deltaLoadProfileThreePhase.setKva_max(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.KVA_MAX , commandName, deviceInfo.getDevice_serial_number()));
						deltaLoadProfileThreePhase.setMax_current(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.MAX_CURRENT , commandName, deviceInfo.getDevice_serial_number()));
						deltaLoadProfileThreePhase.setMax_voltage(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_3P.MAX_VOLTAGE , commandName, deviceInfo.getDevice_serial_number()));
					}
					
					Optional<DeltaLoadProfileThreePhase> isDataExits = deltaLoadProfileThreePhaseRepository.getData(
							deltaLoadProfileThreePhase.getDevice_serial_number(), deltaLoadProfileThreePhase.getInterval_datetime());
					
					if(!isDataExits.isPresent()) {
						deltaLoadProfileList.add(deltaLoadProfileThreePhase);
					}
					
				}
				LOG.info("Three Phase: Parsing & Inserting data of Delta Load Profile Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					deltaLoadProfileThreePhaseRepository.saveAll(deltaLoadProfileList);
				}
			}
			else if (Constants.DeviceTypes.CT_METER.contains(deviceInfo.getDevice_type())) {
				// Few of the obis code mapping is pending as we need name from manufacturer.
				List<DeltaLoadProfileCT> deltaLoadProfileList = new ArrayList<DeltaLoadProfileCT>();

				for (Object object : responseData) {
					 @SuppressWarnings("unchecked")
					 List<Object> data = (List<Object>) object;
					 DeltaLoadProfileCT deltaLoadProfileCT = new DeltaLoadProfileCT(deviceInfo.getDevice_serial_number(),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.DELTA_LP_CT.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name(), deviceInfo.getFeeder_name(),
							new Date(System.currentTimeMillis()), deviceInfo.getOwner_name(),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.CURR_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.CURR_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.CURR_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.VOL_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.VOL_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.VOL_L3, commandName, deviceInfo.getDevice_serial_number()),
							deviceInfo.getSubdevision_name(), deviceInfo.getSubstation_name(),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.KVARH_Q1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.KVARH_Q2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.KVARH_Q3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.KVARH_Q4, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.STATUS_BYTE, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.AVG_SIGNAL_STRENGTH, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.LEAD_BLOCK_ENERGY_kVARH, commandName, deviceInfo.getDevice_serial_number()));

					 deltaLoadProfileCT.setCh_0_lo_current_neutral_current_avg_5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.DELTA_LP_CT.AVG_5_NEUTRAL_CURRENT, commandName, deviceInfo.getDevice_serial_number()));
					 
					
					
					Optional<DeltaLoadProfileCT> isDataExits = deltaLoadProfileCTRepository.getData(
							deltaLoadProfileCT.getDevice_serial_number(), deltaLoadProfileCT.getInterval_datetime());
					
					if(!isDataExits.isPresent()) {
						deltaLoadProfileList.add(deltaLoadProfileCT);
					}
				}
				LOG.info("CT: Parsing & Inserting data of Delta Load Profile Data : "
						+ deviceInfo.getDevice_serial_number());
				if (commonUtils.isValid()) {
					deltaLoadProfileCTRepository.saveAll(deltaLoadProfileList);
				}
			}
			LOG.info("Delta Load Profile Data Inserted Successfully. " + deviceInfo.getDevice_serial_number());
			flag = true;
		} catch (Exception e) {
			LOG.error("Issue in inserting Delta Load Profile data for meter "+ deviceInfo.getDevice_serial_number() 
				+" in db due to :"+ e.getMessage());
			meterResponse.setMessage("Issue in inserting data due to: "+e.getMessage());
		}
		return flag;
	}
	
	/**
	 * Insert Billing Data
	 * @param response
	 * @param deviceInfo
	 * @param meterResponse
	 * @return
	 */
	public boolean insertBillingData(DevicesInfo deviceInfo,
			MeterResponse meterResponse)
	{
		boolean flag = false;
		PushDataToExtenalAddTask billingTask = null;
		try {
			String phaseVal = Constants.PhaseVal._1PH;
			if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._3PH;
			} else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._CT;
			}

			//String commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.BILLING_DATA;
			
			String commandName = deviceInfo.getMeter_type()+"_"+deviceInfo.getVersion()+ "_" + phaseVal + "_" +Constants.BILLING_DATA;
			if(MeterDBAppStarter.meterDivFactorProperties.getProperty(commandName) == null) {
				commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.BILLING_DATA;
			}
			
			
			Object responseData[] = meterResponse.getData();
			List<String> obisCodeList = meterResponse.getObisCode();
			
			if(Constants.DeviceTypes.SINGLE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())){
				BillingDataSinglePhase billingDataSinglePhase = null;
				List<LastBillingDataSinglePhase> lastBillingDataList = new CopyOnWriteArrayList<LastBillingDataSinglePhase>();
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					LastBillingDataSinglePhase lastBillingDataSinglePhase = new LastBillingDataSinglePhase(deviceInfo.getDevice_serial_number(),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.BILL_DATETIME))), 
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.AVG_PF , commandName, deviceInfo.getDevice_serial_number()),
							null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.BILL_PON , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KVAH_EXPORT , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KVAH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KVAH_IMPORT_T1 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KVAH_IMPORT_T2 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KVAH_IMPORT_T3 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KVAH_IMPORT_T4 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KVAH_IMPORT_T5 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KVAH_IMPORT_T6 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KVAH_IMPORT_T7 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KVAH_IMPORT_T8 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KWH_EXPORT , commandName, deviceInfo.getDevice_serial_number()), 
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KWH_IMPORT , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KWH_IMPORT_T1 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KWH_IMPORT_T2 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KWH_IMPORT_T3 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KWH_IMPORT_T4 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KWH_IMPORT_T5 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KWH_IMPORT_T6 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KWH_IMPORT_T7 , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.KWH_IMPORT_T8 , commandName, deviceInfo.getDevice_serial_number()),
							deviceInfo.getDcu_serial_number(), 
							deviceInfo.getDt_name(), deviceInfo.getFeeder_name(),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KVA , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_KVA)+1)),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KW , commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_KW)+1)),
							new Date(System.currentTimeMillis()),
						    new Date(System.currentTimeMillis()), deviceInfo.getOwner_name(), 
							deviceInfo.getSubdevision_name(), deviceInfo.getSubstation_name());
							
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KW_T1 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KW_T2 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KW_T3 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KW_T4 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KW_T5 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KW_T6 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KW_T7 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KW_T8 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier1_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T1) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T1)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier2_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T2) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T2)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier3_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T3) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T3)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier4_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T4) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T4)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier5_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T5) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T5)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier6_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T6) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T6)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier7_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T7) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T7)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kw_tier8_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T8) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KW_T8)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier1(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KVA_T1 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier2(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KVA_T2 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier3(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KVA_T3 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier4(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KVA_T4 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier5(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KVA_T5 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier6(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KVA_T6 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier7(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KVA_T7 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier8(CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_1P.MD_KVA_T8 , commandName, deviceInfo.getDevice_serial_number()));
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier1_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T1) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T1)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier2_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T2) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T2)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier3_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T3) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T3)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier4_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T4) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T4)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier5_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T5) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T5)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier6_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T6) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T6)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier7_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T7) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T7)+1)) : null);
					lastBillingDataSinglePhase.setMaximum_demand_kva_tier8_date(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T8) != -1 
							? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_1P.MD_DATETIME_KVA_T8)+1)) : null);
					
					if(billingDataSinglePhase == null){
						billingDataSinglePhase = CommonUtils.getMapper().readValue(
								CommonUtils.getMapper().writeValueAsString(lastBillingDataSinglePhase), BillingDataSinglePhase.class);
					}
					else{
						if(lastBillingDataSinglePhase.getBilling_datetime().compareTo(
								billingDataSinglePhase.getBilling_datetime()) == 1)
						{
							billingDataSinglePhase = CommonUtils.getMapper().readValue(
									CommonUtils.getMapper().writeValueAsString(lastBillingDataSinglePhase), BillingDataSinglePhase.class);
						}
					}
					lastBillingDataList.add(lastBillingDataSinglePhase);
				}
				
				final BillingDataSinglePhase checkBillingDataSinglePhase = billingDataSinglePhase;
				
				if(lastBillingDataList.size() > 1) {
					lastBillingDataList.stream().filter(e -> e.getBilling_datetime().equals(checkBillingDataSinglePhase.getBilling_datetime()))
					.forEach(lastBillingDataList::remove);
				}
					
				
				LOG.info("Single Phase: Parsing & Inserting data of Billing Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					lastBillingDataRepository.saveAll(lastBillingDataList);
					billingDataRepository.save(billingDataSinglePhase);
				}
				
				if(billingDataSinglePhase != null && Constants.ENABLE.equalsIgnoreCase(meteConfiguration.getExternalPushEnable())) {
					try {
						LOG.info("Pushing Single Phase: Billing Data To External. "+ deviceInfo.getDevice_serial_number());
						billingTask = new PushDataToExtenalAddTask( 
								billingDataSinglePhase, meteConfiguration.getDailyBillingPushURL(),Constants.BILLING_DATA, deviceInfo.getDevice_serial_number());
						new Thread(billingTask).start();
					} catch (Exception e) {
						LOG.error("Error in sending Event Push Data to External URL");
					}
				}
			}else if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				BillingDataThreePhase billingDataThreePhase = null;
				List<LastBillingDataThreePhase> lastBillingThreePhaseDataList = new CopyOnWriteArrayList<LastBillingDataThreePhase>();
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
	
					LastBillingDataThreePhase lastBillingDataThreePhase = new LastBillingDataThreePhase(
							deviceInfo.getDevice_serial_number(),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.BILL_DATETIME))), 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVAH_IMPORT_T1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVAH_IMPORT_T2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVAH_IMPORT_T3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVAH_IMPORT_T4, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVAH_IMPORT_T5, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVAH_IMPORT_T6, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVAH_IMPORT_T7, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVAH_IMPORT_T8, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVARH_Q1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVARH_Q2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVARH_Q3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KVARH_Q4, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KWH_IMPORT_T1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KWH_IMPORT_T2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KWH_IMPORT_T3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KWH_IMPORT_T4, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KWH_IMPORT_T5, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KWH_IMPORT_T6, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KWH_IMPORT_T7, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.KWH_IMPORT_T8, commandName, deviceInfo.getDevice_serial_number()),
							deviceInfo.getDcu_serial_number(),
							deviceInfo.getDt_name(),
							deviceInfo.getFeeder_name(),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KVA, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KVA_T1, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T1) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T1)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KVA_T2, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T2) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T2)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KVA_T3, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T3) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T3)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KVA_T4, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T4) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T4)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KVA_T5, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T5) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T5)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KVA_T6, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T6) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T6)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KVA_T7, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T7) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T7)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KVA_T8, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T8) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KVA_T8)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KW, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KW_T1, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T1) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T1)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KW_T2, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T2) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T2)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KW_T3, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T3) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T3)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KW_T4, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T4) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T4)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KW_T5, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T5) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T5)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KW_T6, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T6) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T6)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KW_T7, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T7) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T7)+1)) : null, 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.MD_KW_T8, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T8) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T8)+1)) : null, 
							CommonUtils.getInstanDate(null),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.BILL_DATETIME))), 
							deviceInfo.getOwner_name(),
							CommonUtils.convertLongTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.BILL_PON))),
							deviceInfo.getSubdevision_name(), deviceInfo.getSubstation_name(),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_3P.AVG_PF, commandName, deviceInfo.getDevice_serial_number())
							);
					
					if(billingDataThreePhase == null){
						billingDataThreePhase = CommonUtils.getMapper().readValue(
								CommonUtils.getMapper().writeValueAsString(lastBillingDataThreePhase), BillingDataThreePhase.class);
					}
					else{
						if(lastBillingDataThreePhase.getBilling_datetime().compareTo(
								billingDataThreePhase.getBilling_datetime()) == 1)
						{
							billingDataThreePhase = CommonUtils.getMapper().readValue(
									CommonUtils.getMapper().writeValueAsString(lastBillingDataThreePhase), BillingDataThreePhase.class);
						}
					}
					lastBillingThreePhaseDataList.add(lastBillingDataThreePhase);
				}
				
				final BillingDataThreePhase checkBillingDataThreePhase = billingDataThreePhase;
				
				if (lastBillingThreePhaseDataList.size() > 1) {
					lastBillingThreePhaseDataList.stream().filter(
							e -> e.getBilling_datetime().equals(checkBillingDataThreePhase.getBilling_datetime()))
							.forEach(lastBillingThreePhaseDataList::remove);
				}
				
				LOG.info("Three Phase: Parsing & Inserting data of Billing Data : "+ deviceInfo.getDevice_serial_number());
				
				if(commonUtils.isValid()) {
					lastBillingDataThreePhaseRepository.saveAll(lastBillingThreePhaseDataList);
					billingDataThreePhaseRepository.save(billingDataThreePhase);
				}
				
				if(billingDataThreePhase != null && Constants.ENABLE.equalsIgnoreCase(meteConfiguration.getExternalPushEnable())) {
					try {
						LOG.info("Pushing Three Phase: Billing Data To External. "+ deviceInfo.getDevice_serial_number());
						billingTask = new PushDataToExtenalAddTask( 
								billingDataThreePhase, meteConfiguration.getDailyBillingPushURL(),Constants.BILLING_DATA, deviceInfo.getDevice_serial_number());
						
						new Thread(billingTask).start();
					} catch (Exception e) {
						LOG.error("Error in sending Event Push Data to External URL");
					}
				}
			} else if (Constants.DeviceTypes.CT_METER.contains(deviceInfo.getDevice_type())) {
				BillingDataCT billingDataCT = null;
				List<LastBillingDataCT> lastBillingCTDataList = new CopyOnWriteArrayList<LastBillingDataCT>();
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;

					LastBillingDataCT lastBillingDataCT = new LastBillingDataCT(deviceInfo.getDevice_serial_number(),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.BILL_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_IMPORT_T1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_IMPORT_T2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_IMPORT_T3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_IMPORT_T4, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_IMPORT_T5, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_IMPORT_T6, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_IMPORT_T7, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_IMPORT_T8, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVARH_Q1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVARH_Q2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVARH_Q3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVARH_Q4, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_IMPORT_T1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_IMPORT_T2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_IMPORT_T3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_IMPORT_T4, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_IMPORT_T5, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_IMPORT_T6, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_IMPORT_T7, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_IMPORT_T8, commandName, deviceInfo.getDevice_serial_number()),
							deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name(), deviceInfo.getFeeder_name(),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA,commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_T1, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T1) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T1) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_T2,commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T2) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T2) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_T3, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T3) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T3) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_T4, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T4) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T4) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_T5, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T5) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T5) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_T6, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T6) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T6) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_T7, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T7) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T7) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_T8, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T8) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_T8) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW,commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW)+1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_T1, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T1) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T1) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_T2, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T2) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T2) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_T3, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T3) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T3) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_T4,commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T4) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T4) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_T5, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T5) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T5) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_T6, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T6) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_3P.MD_DATETIME_KW_T6) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_T7,commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T7) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T7) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_T8, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T8) != -1 ? CommonUtils.getInstanDate(data .get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_T8) + 1)) : null,
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.BILL_DATETIME))),
							deviceInfo.getOwner_name(),
							CommonUtils.convertLongTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.BILL_POWER_ON))),
							deviceInfo.getSubdevision_name(), deviceInfo.getSubstation_name(),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.AVG_PF, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.CUMULATIVE_ENERGY_KVARH_LAG, commandName,deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_EXPORT_T1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_EXPORT_T2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_EXPORT_T3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_EXPORT_T4, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_EXPORT_T5, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_EXPORT_T6, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_EXPORT_T7, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KWH_EXPORT_T8, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_EXPORT_T1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_EXPORT_T2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_EXPORT_T3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_EXPORT_T4, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_EXPORT_T5, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_EXPORT_T6, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_EXPORT_T7, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.KVAH_EXPORT_T8, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT) + 1)),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_CT.MD_KW_EXPORT_T1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_CT.MD_KW_EXPORT_T2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.BILLING_CT.MD_KW_EXPORT_T3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_EXPORT_T4, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_EXPORT_T5, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_EXPORT_T6, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_EXPORT_T7, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KW_EXPORT_T8, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T1) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T1) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T2) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T2) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T3) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T3) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T4) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T4) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T5) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T5) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T6) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T6) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T7) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T7) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T8) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KW_EXPORT_T8) + 1)) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT) + 1)),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_EXPORT_T1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_EXPORT_T2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_EXPORT_T3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_EXPORT_T4, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_EXPORT_T5, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_EXPORT_T6, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_EXPORT_T7, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.BILLING_CT.MD_KVA_EXPORT_T8, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T1) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T1) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T2) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T2) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T3) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T3) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T4) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T4) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T5) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T5) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T6) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T6) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T7) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T7) + 1)) : null,
							obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T8) != -1 ? CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.MD_DATETIME_KVA_EXPORT_T8) + 1)) : null,					
							obisCodeList.contains(Constants.ObisCode.BILLING_CT.TAMPER_COUNT) == true ?

									CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.BILLING_CT.TAMPER_COUNT))) : null
					
					);

					if (billingDataCT == null) {
						billingDataCT = CommonUtils.getMapper().readValue(
								CommonUtils.getMapper().writeValueAsString(lastBillingDataCT), BillingDataCT.class);
					} else {
						if (lastBillingDataCT.getBilling_datetime()
								.compareTo(billingDataCT.getBilling_datetime()) == 1) {
							billingDataCT = CommonUtils.getMapper().readValue(
									CommonUtils.getMapper().writeValueAsString(lastBillingDataCT), BillingDataCT.class);
						}
					}
					lastBillingCTDataList.add(lastBillingDataCT);
				}

				final BillingDataCT checkBillingDataCT = billingDataCT;
				if (lastBillingCTDataList.size() > 1) {
				lastBillingCTDataList.stream()
						.filter(e -> e.getBilling_datetime().equals(checkBillingDataCT.getBilling_datetime()))
						.forEach(lastBillingCTDataList::remove);
				}

				LOG.info("Parsing & Inserting data of Billing Data : " + deviceInfo.getDevice_serial_number());

				if (commonUtils.isValid()) {
					lastBillingDataCTRepository.saveAll(lastBillingCTDataList);
					billingDataCTRepository.save(billingDataCT);
				}
				
				if(billingDataCT != null && Constants.ENABLE.equalsIgnoreCase(meteConfiguration.getExternalPushEnable())) {
					try {
						LOG.info("Pushing CT: Billing Data To External. "+ deviceInfo.getDevice_serial_number());
						billingTask = new PushDataToExtenalAddTask( 
								billingDataCT, meteConfiguration.getDailyBillingPushURL(),Constants.BILLING_DATA, deviceInfo.getDevice_serial_number());
						
						new Thread(billingTask).start();
					} catch (Exception e) {
						LOG.error("Error in sending Event Push Data to External URL");
					}
				}
				LOG.info("CT: Billing Data Inserted Successfully. "+ deviceInfo.getDevice_serial_number());
			}			
			meterResponse.setMessage(Constants.SUCCESS);
			flag = true;
		} catch (Exception e) {
			LOG.error("Issue in inserting Billing data for meter "+ deviceInfo.getDevice_serial_number() 
				+" in db due to :"+ e.getMessage());
			meterResponse.setMessage("Issue in inserting data due to: "+e.getMessage());
		}
		return flag;
	}
	
	/**
	 * Used to insert Power Related Events 
	 * @param response
	 * @param deviceInfo
	 * @param meterResponse
	 * @return
	 */
	public boolean insertPowerRelatedEventsData(DevicesInfo deviceInfo,
			MeterResponse meterResponse)
	{
		boolean flag = false;
		try {
			Object responseData[] = meterResponse.getData();
			
			//This is set to Failure because we need not to update Communication Datetime when data is null in case of event.
			if(responseData == null || responseData.length == 0) {
				meterResponse.setMessage(Constants.FAILURE_NA);
				return flag;
			}
			List<String> obisCodeList = meterResponse.getObisCode();
			
			String phaseVal = Constants.PhaseVal._1PH;
			if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._3PH;
			}
			else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._CT;
			}
			String commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.POWER_RELATED_EVENTS;
			
			if(Constants.DeviceTypes.SINGLE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				List<EventDataSinglePhase> powerRelatedEventsDataList = new ArrayList<EventDataSinglePhase>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataSinglePhase eventDataSinglePhase = new EventDataSinglePhase(deviceInfo.getDevice_serial_number(),
							Integer.parseInt(data.get(1).toString()), CommonUtils.getInstanDate(data.get(0)), 
							null, null, deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name(), 
							Constants.POWER_RELATED,  CommonUtils.getEventName(data.get(1)) ,
							deviceInfo.getFeeder_name(), new Date(System.currentTimeMillis()),
							new Date(System.currentTimeMillis()), deviceInfo.getOwner_name(), 
							null, deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), null, null);
					
					
					Optional<EventDataSinglePhase> isDataExits = eventDataRepository.getData(
							eventDataSinglePhase.getDevice_serial_number(), eventDataSinglePhase.getEvent_code(),
							eventDataSinglePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						powerRelatedEventsDataList.add(eventDataSinglePhase);
					}
				}
				
				LOG.info("Single Phase: Parsing & Inserting Power Related Events Data : "+ deviceInfo.getDevice_serial_number());
				eventDataRepository.saveAll(powerRelatedEventsDataList);
			}
			else if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				
				List<EventDataThreePhase> powerRelatedThreeEventsDataList = new ArrayList<EventDataThreePhase>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataThreePhase eventDataThreePhase = new EventDataThreePhase(deviceInfo.getDevice_serial_number(),						
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),							
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.contains(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT) == true ?
									CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT))) : null,
      						CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L2, commandName, deviceInfo.getDevice_serial_number()),							
							Constants.POWER_RELATED, CommonUtils.getEventName(data.get(1)),							
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L2, commandName, deviceInfo.getDevice_serial_number()),	
							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name()
						
					);
					eventDataThreePhase.setCumulative_energy_kvah_export(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataThreePhase.setCumulative_energy_kvah_import(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataThreePhase.setNeutral_current(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.NEUTRAL_CURRENT, commandName, deviceInfo.getDevice_serial_number()));	
					
					
					Optional<EventDataThreePhase> isDataExits = eventDataThreePhaseRepository.getData(
							eventDataThreePhase.getDevice_serial_number(), eventDataThreePhase.getEvent_code(),
							eventDataThreePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						powerRelatedThreeEventsDataList.add(eventDataThreePhase);
					}
					
				}
				
				LOG.info("Three Phase: Parsing & Inserting Power Related Events Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					eventDataThreePhaseRepository.saveAll(powerRelatedThreeEventsDataList);
				}
			}
			else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {

				List<EventDataCT> powerRelatedCTEventsDataList = new ArrayList<EventDataCT>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataCT eventDataCT = new EventDataCT(deviceInfo.getDevice_serial_number(),						
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.RTC_DATETIME))),							
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.contains(Constants.ObisCode.EVENTS_CT.TAMPER_COUNT) == true ?
									CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.TAMPER_COUNT))) : null,
      						CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L2, commandName, deviceInfo.getDevice_serial_number()),							
							Constants.POWER_RELATED, CommonUtils.getEventName(data.get(1)),							
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L2, commandName, deviceInfo.getDevice_serial_number()),	
							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name()
						
					);
					eventDataCT.setCumulative_energy_kvah_export(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataCT.setCumulative_energy_kvah_import(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataCT.setNeutral_current(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.NEUTRAL_CURRENT, commandName, deviceInfo.getDevice_serial_number()));	
					
					
					Optional<EventDataCT> isDataExits = eventDataCTRepository.getData(
							eventDataCT.getDevice_serial_number(), eventDataCT.getEvent_code(),
							eventDataCT.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						powerRelatedCTEventsDataList.add(eventDataCT);
					}
				}
				
				LOG.info("CT: Parsing & Inserting Power Related Events Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					eventDataCTRepository.saveAll(powerRelatedCTEventsDataList);
				}
			
			}
			LOG.info("Power Related Events Data Inserted Successfully. "+ deviceInfo.getDevice_serial_number());
			flag = true;
		} catch (Exception e) {
			LOG.error("Issue in inserting Power Related Events Data for meter "+ deviceInfo.getDevice_serial_number() 
				+" in db due to :"+ e.getMessage());
			meterResponse.setMessage("Issue in inserting data due to: "+e.getMessage());
		}
		return flag;
	}
	
	/**
	 * Used to capture Voltage Related Events
	 * @param response
	 * @param deviceInfo
	 * @param meterResponse
	 * @return
	 */
	public boolean insertVoltageRelatedEventsData(DevicesInfo deviceInfo,
			MeterResponse meterResponse)
	{
		boolean flag = false;
		try {
			
			Object responseData[] = meterResponse.getData();
			
			//This is set to Failure because we need not to update Communication Datetime when data is null in case of event.
			if(responseData == null || responseData.length == 0) {
				meterResponse.setMessage(Constants.FAILURE_NA);
				return flag;
			}
			List<String> obisCodeList = meterResponse.getObisCode();
			
			String phaseVal = Constants.PhaseVal._1PH;
			if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._3PH;
			}
			else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._CT;
			}
			String commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.VOLTAGE_RELATED_EVENTS;
			
			if(Constants.DeviceTypes.SINGLE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				
				List<EventDataSinglePhase> voltageRelatedEventsDataList = new ArrayList<EventDataSinglePhase>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataSinglePhase eventDataSinglePhase = new EventDataSinglePhase(deviceInfo.getDevice_serial_number(),
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_1P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_1P.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),

							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_1P.CURRENT, commandName, deviceInfo.getDevice_serial_number()),

							deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name(), 
							Constants.VOLTAGE_RELATED,  CommonUtils.getEventName(data.get(1)) ,
							deviceInfo.getFeeder_name(), new Date(System.currentTimeMillis()),
							new Date(System.currentTimeMillis()), deviceInfo.getOwner_name(), 
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_1P.POWER_FACTOR, commandName, deviceInfo.getDevice_serial_number()),

							deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(),
							obisCodeList.contains(Constants.ObisCode.EVENTS_1P.TAMPER_COUNT) == true ?
									CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_1P.TAMPER_COUNT))) : null,

							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_1P.VOLTAGE, commandName, deviceInfo.getDevice_serial_number())

							);
					
					Optional<EventDataSinglePhase> isDataExits = eventDataRepository.getData(
							eventDataSinglePhase.getDevice_serial_number(), eventDataSinglePhase.getEvent_code(),
							eventDataSinglePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						voltageRelatedEventsDataList.add(eventDataSinglePhase);
					}
					
				}
				
				LOG.info("Single Phase: Parsing & Inserting Voltage Related Events Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					eventDataRepository.saveAll(voltageRelatedEventsDataList);
				}
				
			}
			else if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				
				List<EventDataThreePhase> voltageRelatedEventsDataList = new ArrayList<EventDataThreePhase>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
				
					EventDataThreePhase eventDataThreePhase = new EventDataThreePhase(deviceInfo.getDevice_serial_number(),
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.contains(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT) == true ?
									CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT))) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L2, commandName, deviceInfo.getDevice_serial_number()),							
							Constants.VOLTAGE_RELATED, CommonUtils.getEventName(data.get(1)),							
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L2, commandName, deviceInfo.getDevice_serial_number()),	
							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name()
							
							);
					eventDataThreePhase.setCumulative_energy_kvah_export(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataThreePhase.setCumulative_energy_kvah_import(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataThreePhase.setNeutral_current(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.NEUTRAL_CURRENT, commandName, deviceInfo.getDevice_serial_number()));	
					
					Optional<EventDataThreePhase> isDataExits = eventDataThreePhaseRepository.getData(
							eventDataThreePhase.getDevice_serial_number(), eventDataThreePhase.getEvent_code(),
							eventDataThreePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						voltageRelatedEventsDataList.add(eventDataThreePhase);
					}
				}
				
				LOG.info("Three Phase: Parsing & Inserting Voltage Related Events Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {

					eventDataThreePhaseRepository.saveAll(voltageRelatedEventsDataList);
				}
			}
			else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {

				List<EventDataCT> voltageRelatedEventsDataList = new ArrayList<EventDataCT>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
				
					EventDataCT eventDataCT = new EventDataCT(deviceInfo.getDevice_serial_number(),
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.contains(Constants.ObisCode.EVENTS_CT.TAMPER_COUNT) == true ?
									CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.TAMPER_COUNT))) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L2, commandName, deviceInfo.getDevice_serial_number()),							
							Constants.VOLTAGE_RELATED, CommonUtils.getEventName(data.get(1)),							
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L2, commandName, deviceInfo.getDevice_serial_number()),	
							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name()
							
							);
					eventDataCT.setCumulative_energy_kvah_export(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataCT.setCumulative_energy_kvah_import(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataCT.setNeutral_current(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.NEUTRAL_CURRENT, commandName, deviceInfo.getDevice_serial_number()));	
					
					
					Optional<EventDataCT> isDataExits = eventDataCTRepository.getData(
							eventDataCT.getDevice_serial_number(), eventDataCT.getEvent_code(),
							eventDataCT.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						voltageRelatedEventsDataList.add(eventDataCT);
					}
				}
				
				LOG.info("CT: Parsing & Inserting Voltage Related Events Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {

					eventDataCTRepository.saveAll(voltageRelatedEventsDataList);
				}
			
			}
			
			LOG.info("Voltage Related Events Data Inserted Successfully. "+ deviceInfo.getDevice_serial_number());
			flag = true;
		} catch (Exception e) {
			LOG.error("Issue in inserting Voltage Related Events Data for meter "+ deviceInfo.getDevice_serial_number() 
				+" in db due to :"+ e.getMessage());
			meterResponse.setMessage("Issue in inserting data due to: "+e.getMessage());
		}
		return flag;
	}
	
	/**
	 * Used to capture Transaction Related Events 
	 * @param response
	 * @param deviceInfo
	 * @param meterResponse
	 * @return
	 */
	public boolean insertTransactionRelatedEventsData(DevicesInfo deviceInfo,
			MeterResponse meterResponse)
	{
		boolean flag = false;
		try {
			Object responseData[] = meterResponse.getData();
			
			//This is set to Failure because we need not to update Communication Datetime when data is null in case of event.
			if(responseData == null || responseData.length == 0) {
				meterResponse.setMessage(Constants.FAILURE_NA);
				return flag;
			}
            List<String> obisCodeList = meterResponse.getObisCode();
			
            String phaseVal = Constants.PhaseVal._1PH;
			if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._3PH;
			}
			else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._CT;
			}
			String commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.TRANSACTION_RELATED;
			
			if(Constants.DeviceTypes.SINGLE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				List<EventDataSinglePhase> transactionRelatedEventsDataList = new ArrayList<EventDataSinglePhase>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataSinglePhase eventDataSinglePhase = new EventDataSinglePhase(deviceInfo.getDevice_serial_number(),
							Integer.parseInt(data.get(1).toString()), CommonUtils.getInstanDate(data.get(0)), 
							null, null, deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name(), 
							Constants.TRANSACTION_RELATED,  CommonUtils.getEventName(data.get(1)) ,
							deviceInfo.getFeeder_name(), new Date(System.currentTimeMillis()),
							new Date(System.currentTimeMillis()), deviceInfo.getOwner_name(), 
							null, deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), null, null);
					
					Optional<EventDataSinglePhase> isDataExits = eventDataRepository.getData(
							eventDataSinglePhase.getDevice_serial_number(), eventDataSinglePhase.getEvent_code(),
							eventDataSinglePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						transactionRelatedEventsDataList.add(eventDataSinglePhase);
					}
					
				}
				
				LOG.info("Single Phase: Parsing & Inserting Transaction Related Events Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					eventDataRepository.saveAll(transactionRelatedEventsDataList);
				}
		    }
			else if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				
				List<EventDataThreePhase> transactionRelatedThreeEventsDataList = new ArrayList<EventDataThreePhase>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataThreePhase eventDataThreePhase = new EventDataThreePhase(deviceInfo.getDevice_serial_number(),							
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),						
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),							
							obisCodeList.contains(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT) == true ?
									CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT))) : null,							
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L2, commandName, deviceInfo.getDevice_serial_number()),							
							Constants.TRANSACTION_RELATED, CommonUtils.getEventName(data.get(1)),							
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L2, commandName, deviceInfo.getDevice_serial_number()),	
							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name()
	
							);
					eventDataThreePhase.setCumulative_energy_kvah_export(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataThreePhase.setCumulative_energy_kvah_import(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataThreePhase.setNeutral_current(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.NEUTRAL_CURRENT, commandName, deviceInfo.getDevice_serial_number()));	
					
					
					Optional<EventDataThreePhase> isDataExits = eventDataThreePhaseRepository.getData(
							eventDataThreePhase.getDevice_serial_number(), eventDataThreePhase.getEvent_code(),
							eventDataThreePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						transactionRelatedThreeEventsDataList.add(eventDataThreePhase);
					}
				}
				
				LOG.info("Three Phase: Parsing & Inserting Transaction Related Events Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					eventDataThreePhaseRepository.saveAll(transactionRelatedThreeEventsDataList);
				}
			}
			else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {

				List<EventDataCT> transactionRelatedCTEventsDataList = new ArrayList<EventDataCT>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataCT eventDataCT = new EventDataCT(deviceInfo.getDevice_serial_number(),							
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.RTC_DATETIME))),						
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),							
							obisCodeList.contains(Constants.ObisCode.EVENTS_CT.TAMPER_COUNT) == true ?
									CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.TAMPER_COUNT))) : null,							
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L2, commandName, deviceInfo.getDevice_serial_number()),							
							Constants.TRANSACTION_RELATED, CommonUtils.getEventName(data.get(1)),							
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L2, commandName, deviceInfo.getDevice_serial_number()),	
							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name()
	
							);
					eventDataCT.setCumulative_energy_kvah_export(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataCT.setCumulative_energy_kvah_import(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataCT.setNeutral_current(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.NEUTRAL_CURRENT, commandName, deviceInfo.getDevice_serial_number()));	
					
					
					Optional<EventDataCT> isDataExits = eventDataCTRepository.getData(
							eventDataCT.getDevice_serial_number(), eventDataCT.getEvent_code(),
							eventDataCT.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						transactionRelatedCTEventsDataList.add(eventDataCT);
					}
				}
				
				LOG.info("CT: Parsing & Inserting Transaction Related Events Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					eventDataCTRepository.saveAll(transactionRelatedCTEventsDataList);
				}
			
			}
			LOG.info("Transaction Related Events Data Inserted Successfully. "+ deviceInfo.getDevice_serial_number());
			flag = true;
		} catch (Exception e) {
			LOG.error("Issue in inserting Transaction Related Events Data for meter "+ deviceInfo.getDevice_serial_number() 
				+" in db due to :"+ e.getMessage());
			meterResponse.setMessage("Issue in inserting data due to: "+e.getMessage());
		}
		return flag;
	}
	
	/**
	 * Used to NonRollover Related Events. 
	 * @param response
	 * @param deviceInfo
	 * @param meterResponse
	 * @return
	 */
	public boolean insertNonRolloverRelatedEventsData(DevicesInfo deviceInfo,
			MeterResponse meterResponse)
	{
		boolean flag = false;
		try {
			
			Object responseData[] = meterResponse.getData();
		
			//This is set to Failure because we need not to update Communication Datetime when data is null in case of event.
			if(responseData == null || responseData.length == 0) {
				meterResponse.setMessage(Constants.FAILURE_NA);
				return flag;
			}
			List<String> obisCodeList = meterResponse.getObisCode();

			String phaseVal = Constants.PhaseVal._1PH;
			if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._3PH;
			}
			else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._CT;
			}
			String commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.NON_ROLLOVER__RELATED;
			
			if(Constants.DeviceTypes.SINGLE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				List<EventDataSinglePhase> transactionRelatedEventsDataList = new ArrayList<EventDataSinglePhase>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataSinglePhase eventDataSinglePhase = new EventDataSinglePhase(deviceInfo.getDevice_serial_number(),
							(Integer)data.get(1), CommonUtils.getInstanDate(data.get(0)), 
							null, null, deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name(), 
							Constants.NON_ROLLOVER__RELATED,  CommonUtils.getEventName(data.get(1)) ,
							deviceInfo.getFeeder_name(), new Date(System.currentTimeMillis()),
							new Date(System.currentTimeMillis()), deviceInfo.getOwner_name(), 
							null, deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), null, null);
					
					Optional<EventDataSinglePhase> isDataExits = eventDataRepository.getData(
							eventDataSinglePhase.getDevice_serial_number(), eventDataSinglePhase.getEvent_code(),
							eventDataSinglePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						transactionRelatedEventsDataList.add(eventDataSinglePhase);
					}
					
				}
				
				LOG.info("Single Phase: Parsing & Inserting Non-Rollover Related Events Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					eventDataRepository.saveAll(transactionRelatedEventsDataList);
				}
		    }
			else if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				
				List<EventDataThreePhase> transactionRelatedThreeEventsDataList = new ArrayList<EventDataThreePhase>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;
					
					EventDataThreePhase eventDataThreePhase = new EventDataThreePhase(deviceInfo.getDevice_serial_number(),
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),							
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.contains(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT) == true ?
									CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT))) : null,							
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L2, commandName, deviceInfo.getDevice_serial_number()),							
							Constants.NON_ROLLOVER__RELATED, CommonUtils.getEventName(data.get(1)),							
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L2, commandName, deviceInfo.getDevice_serial_number()),	
							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name()	
					);
					eventDataThreePhase.setCumulative_energy_kvah_export(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataThreePhase.setCumulative_energy_kvah_import(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataThreePhase.setNeutral_current(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.NEUTRAL_CURRENT, commandName, deviceInfo.getDevice_serial_number()));	
					
					
					Optional<EventDataThreePhase> isDataExits = eventDataThreePhaseRepository.getData(
							eventDataThreePhase.getDevice_serial_number(), eventDataThreePhase.getEvent_code(),
							eventDataThreePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						transactionRelatedThreeEventsDataList.add(eventDataThreePhase);
					}
				}
				LOG.info("Three Phase: Parsing & Inserting Non-Rollover Related Events Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					eventDataThreePhaseRepository.saveAll(transactionRelatedThreeEventsDataList);
				}
			}
			else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {

				List<EventDataCT> transactionRelatedCTEventsDataList = new ArrayList<EventDataCT>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;
					
					EventDataCT eventDataCT = new EventDataCT(deviceInfo.getDevice_serial_number(),
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.RTC_DATETIME))),							
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.contains(Constants.ObisCode.EVENTS_CT.TAMPER_COUNT) == true ?
									CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.TAMPER_COUNT))) : null,							
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L2, commandName, deviceInfo.getDevice_serial_number()),							
							Constants.NON_ROLLOVER__RELATED, CommonUtils.getEventName(data.get(1)),							
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L2, commandName, deviceInfo.getDevice_serial_number()),	
							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name()	
					);
					eventDataCT.setCumulative_energy_kvah_export(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataCT.setCumulative_energy_kvah_import(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataCT.setNeutral_current(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.NEUTRAL_CURRENT, commandName, deviceInfo.getDevice_serial_number()));	
					
					
					Optional<EventDataCT> isDataExits = eventDataCTRepository.getData(
							eventDataCT.getDevice_serial_number(), eventDataCT.getEvent_code(),
							eventDataCT.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						transactionRelatedCTEventsDataList.add(eventDataCT);
					}
				}
				LOG.info("CT: Parsing & Inserting Non-Rollover Related Events Data : "+ deviceInfo.getDevice_serial_number());
				if(commonUtils.isValid()) {
					eventDataCTRepository.saveAll(transactionRelatedCTEventsDataList);
				}
			
			}
			LOG.info("Non-Rollover Related Events Data Inserted Successfully. "+ deviceInfo.getDevice_serial_number());
			flag = true;
		} catch (Exception e) {
			LOG.error("Issue in inserting Non-Rollover Related Events Data for meter "+ deviceInfo.getDevice_serial_number() 
				+" in db due to :"+ e.getMessage());
			meterResponse.setMessage("Issue in inserting data due to: "+e.getMessage());
		}
		return flag;
	}
	
	/**
	 * Used to capture Current Related Events 
	 * @param response
	 * @param deviceInfo
	 * @param meterResponse
	 * @return
	 */
	public boolean insertCurrentRelatedEventsData(DevicesInfo deviceInfo,
			MeterResponse meterResponse)
	{
		boolean flag = false;
		try {
			Object responseData[] = meterResponse.getData();
			//This is set to Failure because we need not to update Communication Datetime when data is null in case of event.
			if(responseData == null || responseData.length == 0) {
				meterResponse.setMessage(Constants.FAILURE_NA);
				return flag;
			}
			List<String> obisCodeList = meterResponse.getObisCode();
			String phaseVal = Constants.PhaseVal._1PH;
			if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._3PH;
			}
			else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._CT;
			}

			String commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.CURRENT_RELATED_EVENTS;
			
			if(Constants.DeviceTypes.SINGLE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				List<EventDataSinglePhase> currentRelatedEventsDataList = new ArrayList<EventDataSinglePhase>();

				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataSinglePhase eventDataSinglePhase = new EventDataSinglePhase(deviceInfo.getDevice_serial_number(),
							Integer.parseInt(data.get(1).toString()), CommonUtils.getInstanDate(data.get(0)), 
							CommonUtils.getDoubleTypeObject(data.get(5), CommonUtils.getDivFactor(5, commandName, deviceInfo.getDevice_serial_number())), 
							CommonUtils.getDoubleTypeObject(data.get(2), CommonUtils.getDivFactor(2, commandName, deviceInfo.getDevice_serial_number())), 
							deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name(), 
							Constants.CURRENT_RELATED,  CommonUtils.getEventName(data.get(1)) ,
							deviceInfo.getFeeder_name(), new Date(System.currentTimeMillis()),
							new Date(System.currentTimeMillis()), deviceInfo.getOwner_name(), 
							CommonUtils.getDoubleTypeObject(data.get(4), CommonUtils.getDivFactor(4, commandName, deviceInfo.getDevice_serial_number())), 
							deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), Integer.parseInt(data.get(6).toString()), 
							CommonUtils.getDoubleTypeObject(data.get(3), CommonUtils.getDivFactor(3, commandName, deviceInfo.getDevice_serial_number())));
					
					Optional<EventDataSinglePhase> isDataExits = eventDataRepository.getData(
							eventDataSinglePhase.getDevice_serial_number(), eventDataSinglePhase.getEvent_code(),
							eventDataSinglePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						currentRelatedEventsDataList.add(eventDataSinglePhase);
					}
					
				}
				
				LOG.info("Single Phase: Parsing & Inserting Current Related Events Data : "+ deviceInfo.getDevice_serial_number());
				eventDataRepository.saveAll(currentRelatedEventsDataList);
			}
			else if(Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				
				List<EventDataThreePhase> currentRelatedThreeEventsDataList = new ArrayList<EventDataThreePhase>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataThreePhase eventDataThreePhase = new EventDataThreePhase(deviceInfo.getDevice_serial_number(),	
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L2, commandName, deviceInfo.getDevice_serial_number()),
						    Constants.CURRENT_RELATED, CommonUtils.getEventName(data.get(1)),							
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L2, commandName, deviceInfo.getDevice_serial_number()),
							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name()
									
					);
					eventDataThreePhase.setCumulative_energy_kvah_export(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataThreePhase.setCumulative_energy_kvah_import(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataThreePhase.setNeutral_current(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.NEUTRAL_CURRENT, commandName, deviceInfo.getDevice_serial_number()));	
					
					
					Optional<EventDataThreePhase> isDataExits = eventDataThreePhaseRepository.getData(
							eventDataThreePhase.getDevice_serial_number(), eventDataThreePhase.getEvent_code(),
							eventDataThreePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						currentRelatedThreeEventsDataList.add(eventDataThreePhase);
					}
				}
				
				LOG.info("Three Phase: Parsing & Inserting Current Related Events Data : "+ deviceInfo.getDevice_serial_number());
				eventDataThreePhaseRepository.saveAll(currentRelatedThreeEventsDataList);
			}
			else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {

				List<EventDataCT> currentRelatedCTEventsDataList = new ArrayList<EventDataCT>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataCT eventDataCT = new EventDataCT(deviceInfo.getDevice_serial_number(),	
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.TAMPER_COUNT))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.CURR_L2, commandName, deviceInfo.getDevice_serial_number()),
						    Constants.CURRENT_RELATED, CommonUtils.getEventName(data.get(1)),							
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.PF_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.VOL_L2, commandName, deviceInfo.getDevice_serial_number()),
							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name()
									
					);
					eventDataCT.setCumulative_energy_kvah_export(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataCT.setCumulative_energy_kvah_import(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()));
					eventDataCT.setNeutral_current(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_CT.NEUTRAL_CURRENT, commandName, deviceInfo.getDevice_serial_number()));	
					
					Optional<EventDataCT> isDataExits = eventDataCTRepository.getData(
							eventDataCT.getDevice_serial_number(), eventDataCT.getEvent_code(),
							eventDataCT.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						currentRelatedCTEventsDataList.add(eventDataCT);
					}
				}
				
				LOG.info("CT: Parsing & Inserting Current Related Events Data : "+ deviceInfo.getDevice_serial_number());
				eventDataCTRepository.saveAll(currentRelatedCTEventsDataList);
			
			}
			LOG.info("Current Related Events Data Inserted Successfully. "+ deviceInfo.getDevice_serial_number());
			flag = true;
		} catch (Exception e) {
			LOG.error("Issue in inserting Current Related Events Data for meter "+ deviceInfo.getDevice_serial_number() 
				+" in db due to :"+ e.getMessage());
			meterResponse.setMessage("Issue in inserting data due to: "+e.getMessage());
		}
		return flag;
	}

	/**
	 * Used to capture Others Related Events 
	 * @param response
	 * @param deviceInfo
	 * @param meterResponse
	 * @return
	 */
	public boolean insertOthersRelatedEventsData(DevicesInfo deviceInfo,
			MeterResponse meterResponse)
	{
		boolean flag = false;
		try {
			Object responseData[] = meterResponse.getData();
			
			//This is set to Failure because we need not to update Communication Datetime when data is null in case of event.
			if(responseData == null || responseData.length == 0) {
				meterResponse.setMessage(Constants.FAILURE_NA);
				return flag;
			}
			List<String> obisCodeList = meterResponse.getObisCode();
			
			String phaseVal = Constants.PhaseVal._1PH;
			if (Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._3PH;
			} else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._CT;
			}

			String commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.OTHER_RELATED_EVENTS;
			if(Constants.DeviceTypes.SINGLE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				List<EventDataSinglePhase> othersRelatedEventsDataList = new ArrayList<EventDataSinglePhase>();
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataSinglePhase eventDataSinglePhase = new EventDataSinglePhase(deviceInfo.getDevice_serial_number(),
							Integer.parseInt(String.valueOf(data.get(1))), CommonUtils.getInstanDate(data.get(0)), 
							CommonUtils.getDoubleTypeObject(data.get(5), CommonUtils.getDivFactor(5, commandName, deviceInfo.getDevice_serial_number())),
							CommonUtils.getDoubleTypeObject(data.get(2), CommonUtils.getDivFactor(2, commandName, deviceInfo.getDevice_serial_number())), 
							deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name(), 
							Constants.OTHERS_RELATED,  CommonUtils.getEventName(data.get(1)) ,
							deviceInfo.getFeeder_name(), new Date(System.currentTimeMillis()),
							new Date(System.currentTimeMillis()), deviceInfo.getOwner_name(), 
							CommonUtils.getDoubleTypeObject(data.get(4), CommonUtils.getDivFactor(4, commandName, deviceInfo.getDevice_serial_number())), 
							deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), Integer.parseInt(data.get(6).toString()), 
							CommonUtils.getDoubleTypeObject(data.get(3), CommonUtils.getDivFactor(3, commandName, deviceInfo.getDevice_serial_number())));
					
					Optional<EventDataSinglePhase> isDataExits = eventDataRepository.getData(
							eventDataSinglePhase.getDevice_serial_number(), eventDataSinglePhase.getEvent_code(),
							eventDataSinglePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						othersRelatedEventsDataList.add(eventDataSinglePhase);
					}
					
				}
				
				LOG.info("Single Phase: Parsing & Inserting Others Related Events Data : "+ deviceInfo.getDevice_serial_number());
				eventDataRepository.saveAll(othersRelatedEventsDataList);

			} else if (Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				// Few of the obis code mapping is pending as we need name from manufacturer.
				List<EventDataThreePhase> othersRelatedThreeEventsDataList = new ArrayList<EventDataThreePhase>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataThreePhase eventDataThreePhase = new EventDataThreePhase(
							deviceInfo.getDevice_serial_number(),
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L2, commandName, deviceInfo.getDevice_serial_number()),
							Constants.OTHERS_RELATED, CommonUtils.getEventName(data.get(1)),
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L2, commandName, deviceInfo.getDevice_serial_number()),
							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name());
							eventDataThreePhase.setNeutral_current(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.NEUTRAL_CURRENT, commandName, deviceInfo.getDevice_serial_number()));
							eventDataThreePhase.setCumulative_energy_kvah_export(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KVAH_EXPORT, commandName, deviceInfo.getDevice_serial_number()));
							eventDataThreePhase.setCumulative_energy_kvah_import(CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KVAH_IMPORT, commandName, deviceInfo.getDevice_serial_number()));

							
					
					Optional<EventDataThreePhase> isDataExits = eventDataThreePhaseRepository.getData(
							eventDataThreePhase.getDevice_serial_number(), eventDataThreePhase.getEvent_code(),
							eventDataThreePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						othersRelatedThreeEventsDataList.add(eventDataThreePhase);
					}
				}
				
				LOG.info("Three Phase: Parsing & Inserting Others Related Events Data : "+ deviceInfo.getDevice_serial_number());
				eventDataThreePhaseRepository.saveAll(othersRelatedThreeEventsDataList);
			} else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				// Few of the obis code mapping is pending as we need name from manufacturer.
				List<EventDataCT> othersRelatedCTEventsDataList = new ArrayList<EventDataCT>();

				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;

					EventDataCT eventDataCT = new EventDataCT(deviceInfo.getDevice_serial_number(),
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(
									data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_CT.KWH_EXPORT,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_CT.KWH_IMPORT,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.convertIntegerTypeObject(
									data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.TAMPER_COUNT))),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_CT.CURR_L3,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_CT.CURR_L1,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_CT.CURR_L2,
									commandName, deviceInfo.getDevice_serial_number()),
							Constants.OTHERS_RELATED, CommonUtils.getEventName(data.get(1)),
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(
									data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_CT.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_CT.PF_L3,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_CT.PF_L1,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_CT.PF_L2,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_CT.VOL_L3,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_CT.VOL_L1,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_CT.VOL_L2,
									commandName, deviceInfo.getDevice_serial_number()),
							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(),
							deviceInfo.getDt_name());
					eventDataCT.setNeutral_current(CommonUtils.getObisDoubleObject(data, obisCodeList,
							Constants.ObisCode.EVENTS_CT.NEUTRAL_CURRENT, commandName,
							deviceInfo.getDevice_serial_number()));
					eventDataCT.setCumulative_energy_kvah_export(CommonUtils.getObisDoubleObject(data, obisCodeList,
							Constants.ObisCode.EVENTS_CT.KVAH_EXPORT, commandName,
							deviceInfo.getDevice_serial_number()));
					eventDataCT.setCumulative_energy_kvah_import(CommonUtils.getObisDoubleObject(data, obisCodeList,
							Constants.ObisCode.EVENTS_CT.KVAH_IMPORT, commandName,
							deviceInfo.getDevice_serial_number()));

					Optional<EventDataCT> isDataExits = eventDataCTRepository.getData(
							eventDataCT.getDevice_serial_number(), eventDataCT.getEvent_code(),
							eventDataCT.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						othersRelatedCTEventsDataList.add(eventDataCT);
					}
				}

				LOG.info("CT: Parsing & Inserting Others Related Events Data : " + deviceInfo.getDevice_serial_number());
				eventDataCTRepository.saveAll(othersRelatedCTEventsDataList);
			}
			LOG.info("Others Related Events Data Inserted Successfully. "+ deviceInfo.getDevice_serial_number());
			flag = true;
		} catch (Exception e) {
			LOG.error("Issue in inserting Others Related Events Data for meter "+ deviceInfo.getDevice_serial_number() 
				+" in db due to :"+ e.getMessage());
			meterResponse.setMessage("Issue in inserting data due to: "+e.getMessage());
		}
		return flag;
	}
	
	/**
	 * Used to capture Control Related Events
	 * @param deviceInfo
	 * @param meterResponse
	 * @return
	 */
	public boolean insertControlRelatedEventsData(DevicesInfo deviceInfo,
			MeterResponse meterResponse)
	{
		boolean flag = false;
		try {
			Object responseData[] = meterResponse.getData();

			// This is set to Failure because we need not to update Communication Datetime
			// when data is null in case of event.
			if (responseData == null || responseData.length == 0) {
				meterResponse.setMessage(Constants.FAILURE_NA);
				return flag;
			}
			List<String> obisCodeList = meterResponse.getObisCode();
			
			String phaseVal = Constants.PhaseVal._1PH;
			if (Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._3PH;
			} else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())
					|| Constants.DeviceTypes.CT.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				phaseVal = Constants.PhaseVal._CT;
			}

			String commandName = deviceInfo.getMeter_type()+ "_" + phaseVal + "_" +Constants.OTHER_RELATED_EVENTS;
			if(Constants.DeviceTypes.SINGLE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				List<EventDataSinglePhase> controlRelatedEventsDataList = new ArrayList<EventDataSinglePhase>();
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataSinglePhase eventDataSinglePhase = new EventDataSinglePhase(deviceInfo.getDevice_serial_number(),
							Integer.parseInt(data.get(1).toString()), CommonUtils.getInstanDate(data.get(0)), 
							null, null, deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name(), 
							Constants.CONTROL_RELATED,  CommonUtils.getEventName(data.get(1)) ,
							deviceInfo.getFeeder_name(), new Date(System.currentTimeMillis()),
							new Date(System.currentTimeMillis()), deviceInfo.getOwner_name(), 
							null, deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), null, null);
					
					Optional<EventDataSinglePhase> isDataExits = eventDataRepository.getData(
							eventDataSinglePhase.getDevice_serial_number(), eventDataSinglePhase.getEvent_code(),
							eventDataSinglePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						controlRelatedEventsDataList.add(eventDataSinglePhase);
					}
					
				}
				
				LOG.info("Single Phase: Parsing & Inserting Control Related Events Data : "+ deviceInfo.getDevice_serial_number());
				eventDataRepository.saveAll(controlRelatedEventsDataList);
			} else if (Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				// Few of the obis code mapping is pending as we need name from manufacturer.
				
				List<EventDataThreePhase> controlRelatedThreeEventsDataList = new ArrayList<EventDataThreePhase>();
				
				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;	
					
					EventDataThreePhase eventDataThreePhase = new EventDataThreePhase(deviceInfo.getDevice_serial_number(),
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_EXPORT, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.KWH_IMPORT, commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.contains(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT) == true ? 
									CommonUtils.convertIntegerTypeObject(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT))) : null,
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.CURR_L2, commandName, deviceInfo.getDevice_serial_number()),
							Constants.CONTROL_RELATED, CommonUtils.getEventName(data.get(1)),							
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.PF_L3, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L1, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L2, commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList,Constants.ObisCode.EVENTS_3P.VOL_L3, commandName, deviceInfo.getDevice_serial_number()),

							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(), deviceInfo.getDt_name()
							);
					
					Optional<EventDataThreePhase> isDataExits = eventDataThreePhaseRepository.getData(
							eventDataThreePhase.getDevice_serial_number(), eventDataThreePhase.getEvent_code(),
							eventDataThreePhase.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						controlRelatedThreeEventsDataList.add(eventDataThreePhase);
					}
					
				}	
				LOG.info("Three Phase: Parsing & Inserting Control Related Events Data : "+ deviceInfo.getDevice_serial_number());
				eventDataThreePhaseRepository.saveAll(controlRelatedThreeEventsDataList);
			} else if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(deviceInfo.getDevice_type())) {
				// Few of the obis code mapping is pending as we need name from manufacturer.

				List<EventDataCT> controlRelatedCTEventsDataList = new ArrayList<EventDataCT>();

				for (Object object : responseData) {
					@SuppressWarnings("unchecked")
					List<Object> data = (List<Object>) object;

					EventDataCT eventDataCT = new EventDataCT(deviceInfo.getDevice_serial_number(),
							CommonUtils.convertIntegerTypeObject(data.get(1)),
							CommonUtils.getInstanDate(
									data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_3P.KWH_EXPORT,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_3P.KWH_IMPORT,
									commandName, deviceInfo.getDevice_serial_number()),
							obisCodeList.contains(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT) == true
									? CommonUtils.convertIntegerTypeObject(
											data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.TAMPER_COUNT)))
									: null,
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_3P.CURR_L3,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_3P.CURR_L1,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_3P.CURR_L2,
									commandName, deviceInfo.getDevice_serial_number()),
							Constants.CONTROL_RELATED, CommonUtils.getEventName(data.get(1)),
							new Date(System.currentTimeMillis()),
							CommonUtils.getInstanDate(
									data.get(obisCodeList.indexOf(Constants.ObisCode.EVENTS_3P.RTC_DATETIME))),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_3P.PF_L1,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_3P.PF_L2,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_3P.PF_L3,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_3P.VOL_L1,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_3P.VOL_L2,
									commandName, deviceInfo.getDevice_serial_number()),
							CommonUtils.getObisDoubleObject(data, obisCodeList, Constants.ObisCode.EVENTS_3P.VOL_L3,
									commandName, deviceInfo.getDevice_serial_number()),

							deviceInfo.getFeeder_name(), deviceInfo.getOwner_name(), deviceInfo.getSubdevision_name(),
							deviceInfo.getSubstation_name(), deviceInfo.getDcu_serial_number(),
							deviceInfo.getDt_name());
					
					Optional<EventDataCT> isDataExits = eventDataCTRepository.getData(
							eventDataCT.getDevice_serial_number(), eventDataCT.getEvent_code(),
							eventDataCT.getEvent_datetime());
					
					if(!isDataExits.isPresent()) {
						controlRelatedCTEventsDataList.add(eventDataCT);
					}
				}
				LOG.info("CT: Parsing & Inserting Control Related Events Data : " + deviceInfo.getDevice_serial_number());
				eventDataCTRepository.saveAll(controlRelatedCTEventsDataList);
			}
			
			LOG.info("Control Related Events Data Inserted Successfully. "+ deviceInfo.getDevice_serial_number());
			flag = true;
		} catch (Exception e) {
			LOG.error("Issue in inserting Control Related Events Data for meter "+ deviceInfo.getDevice_serial_number() 
				+" in db due to :"+ e.getMessage());
			meterResponse.setMessage("Issue in inserting data due to: "+e.getMessage());
		}
		return flag;
	}
	
	/**
	 * Used to insert Instantaneous Data
	 * @param response
	 * @param deviceInfo
	 * @param meterResponse
	 * @return
	 */
	public boolean insertPrepayData(DevicesInfo deviceInfo,List<MeterResponse> meterResponses, 
			Map<String, String> dataSaveResults, Map<String, Integer> dataRetryCount, AtomicInteger totSuccessResult, DeviceCommand deviceCommand)
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
			prepayData.setBatch_id(deviceCommand.getBatchId());
			prepayData.setSource(deviceCommand.getSource());
			prepayData.setUser_id(deviceCommand.getUserId());
			prepayData.setDevice_type(deviceInfo.getDevice_type());
			prepayData.setMeter_type(deviceInfo.getMeter_type());
			
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
				}
				else if(ConfigCommands.RTC_ClOCK.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setRtc_clock(CommonUtils.getInstanDate(meterResponse.getData()[0]));
				}
				else if(ConfigCommands.DEMAND_INTEGRATION_PERIOD.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setDemand_integration_period((Integer) meterResponse.getData()[0]);
				}
				else if(ConfigCommands.PROFILE_CAPTURE_PERIOD.commandName.equalsIgnoreCase(meterResponse.getObisCmd())) {
					prepayData.setProfile_capture_period((Integer) meterResponse.getData()[0]);
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
				
				dataSaveResults.put(meterResponse.getObisCmd(), Constants.SUCCESS);
				dataRetryCount.put(meterResponse.getObisCmd(), ((int)dataRetryCount.get(meterResponse.getObisCmd())+1) );
				totSuccessVal = totSuccessVal+1;	
			}
			flag = true;
			prepayDataRepository.save(prepayData);
			totSuccessResult.set(totSuccessVal);
			LOG.info("Prepay Data is saved successfully..");
		} catch (Exception e) {
			LOG.error("Issue in inserting Prepay data for meter "+ deviceInfo.getDevice_serial_number() +" in db due to :"+ e.getMessage());
		}
		return flag;
	}
	
	/**
	 * Used to insert Meter Ping status when id does not connected.
	 * @param response
	 * @param deviceInfo
	 * @param meterResponse
	 * @return
	 */
	public boolean insertPrepayData(DevicesInfo deviceInfo, DeviceCommand deviceCommand)
	{
		boolean flag = false;
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
			prepayData.setBatch_id(deviceCommand.getBatchId());
			prepayData.setPing_meter(false);
			prepayData.setSource(deviceCommand.getSource());
			prepayData.setUser_id(deviceCommand.getUserId());
			prepayData.setDevice_type(deviceInfo.getDevice_type());
			prepayData.setMeter_type(deviceInfo.getMeter_type());
			
			prepayDataRepository.save(prepayData);
			LOG.info("Prepay Data is saved successfully..");
		} catch (Exception e) {
			LOG.error("Issue in inserting Prepay data for meter "+ deviceInfo.getDevice_serial_number() +" in db due to :"+ e.getMessage());
		}
		return flag;
	}
	
}
