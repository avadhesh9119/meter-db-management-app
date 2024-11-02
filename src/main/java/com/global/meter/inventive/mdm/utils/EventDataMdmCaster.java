package com.global.meter.inventive.mdm.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.EventDataCT;
import com.global.meter.business.model.EventDataSinglePhase;
import com.global.meter.business.model.EventDataThreePhase;
import com.global.meter.inventive.models.EventsCTResponse;
import com.global.meter.inventive.models.EventsSinglePhaseResponse;
import com.global.meter.inventive.models.EventsThreePhaseResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class EventDataMdmCaster {
	
	private static final Logger LOG = LoggerFactory.getLogger(EventDataMdmCaster.class);
	
	@Autowired
	DateConverter dateConverter;

	public void prepareSinglePhase(String outputList, List<EventsSinglePhaseResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception{
		LOG.info("Single Phase: Event Data Caster called....");
		List<EventDataSinglePhase> singlePhaseData = new ArrayList<EventDataSinglePhase>();
		singlePhaseData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<EventDataSinglePhase>>() {});
		LOG.info("Single Phase: Event Response Data Adding....");
	
		for (EventDataSinglePhase ispData : singlePhaseData) {
			try {
				EventsSinglePhaseResponse ispResponse = new EventsSinglePhaseResponse();
				
				ispResponse.setMeterNumber(ispData.getDevice_serial_number());
				ispResponse.setCurrent(ispData.getCurrent() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCurrent())) : req.getReplaceBy());
				ispResponse.setEnergy(ispData.getCumulative_energy() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy())) : req.getReplaceBy());
				ispResponse.setEventCategory(ispData.getEvent_category());
				ispResponse.setEventCode(ispData.getEvent_code() != null ? String.valueOf(ispData.getEvent_code()) : req.getReplaceBy());
				ispResponse.setEventDatetime(dateConverter.convertDateToHesString(ispData.getEvent_datetime()));
				ispResponse.setEventType(ispData.getEvent_type());
				ispResponse.setTamperCount(ispData.getTamper_count() != null ?
						String.valueOf(ispData.getTamper_count()) : req.getReplaceBy());
				ispResponse.setVoltage(ispData.getVoltage() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getVoltage())) : req.getReplaceBy());
				ispResponse.setPowerFactor(ispData.getPower_factor() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPower_factor())) : req.getReplaceBy());
				ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()) != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : req.getReplaceBy());
				ispResponse.setMeterTimestamp(ispData.getMeter_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMeter_datetime()) : req.getReplaceBy());
				
				ispResponseList.add(ispResponse);
			} catch (Exception e) {
				LOG.error("Single Phase: Issue in casting event data due to: "+e.getMessage());
			}
		}
		LOG.info("Single Phase: Event Response Data Added....");
	}
	
	public void prepareThreePhase(String outputList, List<EventsThreePhaseResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception{
		LOG.info("Three Phase: Event Data Caster called....");
		List<EventDataThreePhase> threePhaseData = new ArrayList<EventDataThreePhase>();
		threePhaseData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<EventDataThreePhase>>() {});
		LOG.info("Three Phase: Event Response Data Adding....");
	
		for (EventDataThreePhase ispData : threePhaseData) {
			try {
				EventsThreePhaseResponse ispResponse = new EventsThreePhaseResponse();
				
				ispResponse.setMeterNumber(ispData.getDevice_serial_number());
				ispResponse.setEventCategory(ispData.getEvent_category());
				ispResponse.setEventCode(ispData.getEvent_code());
				ispResponse.setEventDatetime(dateConverter.convertDateToHesString(ispData.getEvent_datetime()));
				ispResponse.setEventType(ispData.getEvent_type() != null ? ispData.getEvent_type() : req.getReplaceBy());
				ispResponse.setTamperCount(ispData.getCumulative_tamper_count() != null ?
						String.valueOf(ispData.getCumulative_tamper_count()) : req.getReplaceBy());
				ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));				
				ispResponse.setKvahExport(ispData.getCumulative_energy_kvah_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_export())) : req.getReplaceBy());
				ispResponse.setKvahImport(ispData.getCumulative_energy_kvah_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_import())) : req.getReplaceBy());				
				ispResponse.setKwhExport(ispData.getCumulative_energy_kwh_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_export())) : req.getReplaceBy());
				ispResponse.setKwhImport(ispData.getCumulative_energy_kwh_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_import())) : req.getReplaceBy());		
				ispResponse.setCurrentIb(ispData.getCurrent_ib() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCurrent_ib())) : req.getReplaceBy());
				ispResponse.setCurrentIr(ispData.getCurrent_ir() !=null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCurrent_ir())) : req.getReplaceBy());
				ispResponse.setCurrentIy(ispData.getCurrent_iy() !=null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCurrent_iy())) : req.getReplaceBy());
				ispResponse.setMeterTimestamp(dateConverter.convertDateToHesString(ispData.getMeter_datetime()));
				ispResponse.setNeutralCurrent(ispData.getNeutral_current() !=null ? String.valueOf(ispData.getNeutral_current()) : req.getReplaceBy());
				ispResponse.setPfBphase(ispData.getPower_factor_b_phase() != null ? String.valueOf(ispData.getPower_factor_b_phase()) : req.getReplaceBy());
				ispResponse.setPfRphase(ispData.getPower_factor_r_phase() != null ? String.valueOf(ispData.getPower_factor_r_phase()) : req.getReplaceBy());
				ispResponse.setPfYphase(ispData.getPower_factor_y_phase() != null ?String.valueOf(ispData.getPower_factor_y_phase()) : req.getReplaceBy());
				ispResponse.setVoltageVbn(ispData.getVoltage_vbn() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getVoltage_vbn())) : req.getReplaceBy());
				ispResponse.setVoltageVrn(ispData.getVoltage_vrn() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getVoltage_vrn())) : req.getReplaceBy());
				ispResponse.setVoltageVyn(ispData.getVoltage_vyn() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getVoltage_vyn())) : req.getReplaceBy());
				
				ispResponseList.add(ispResponse);
			} catch (Exception e) {
				LOG.error("Three Phase: Issue in casting event data due to: "+e.getMessage());
			}
		}
		LOG.info("Three Phase: Event Response Data Added....");
	}
	
	public void prepareCT(String outputList, List<EventsCTResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception{
		LOG.info("CT: Event Data Caster called....");
		List<EventDataCT> ctData = new ArrayList<EventDataCT>();
		ctData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<EventDataCT>>() {});
		LOG.info("CT: Event Response Data Adding....");
	
		for (EventDataCT ispData : ctData) {
			try {
				EventsCTResponse ispResponse = new EventsCTResponse();
				
				ispResponse.setMeterNumber(ispData.getDevice_serial_number());
				ispResponse.setEventCategory(ispData.getEvent_category());
				ispResponse.setEventCode(ispData.getEvent_code());
				ispResponse.setEventDatetime(dateConverter.convertDateToHesString(ispData.getEvent_datetime()));
				ispResponse.setEventType(ispData.getEvent_type());
				ispResponse.setTamperCount(ispData.getCumulative_tamper_count() != null ?
						String.valueOf(ispData.getCumulative_tamper_count()) : req.getReplaceBy());
				ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));				
				ispResponse.setKvahExport(ispData.getCumulative_energy_kvah_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_export())) : req.getReplaceBy());
				ispResponse.setKvahImport(ispData.getCumulative_energy_kvah_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_import())) : req.getReplaceBy());				
				ispResponse.setKwhExport(ispData.getCumulative_energy_kwh_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_export())) : req.getReplaceBy());
				ispResponse.setKwhImport(ispData.getCumulative_energy_kwh_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_import())) : req.getReplaceBy());		
				ispResponse.setCurrentIb(ispData.getCurrent_ib() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCurrent_ib())) : req.getReplaceBy());
				ispResponse.setCurrentIr(ispData.getCurrent_ir() !=null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCurrent_ir())) : req.getReplaceBy());
				ispResponse.setCurrentIy(ispData.getCurrent_iy() !=null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCurrent_iy())) : req.getReplaceBy());
				ispResponse.setMeterTimestamp(dateConverter.convertDateToHesString(ispData.getMeter_datetime()));
				ispResponse.setNeutralCurrent(ispData.getNeutral_current() !=null ? String.valueOf(ispData.getNeutral_current()) : req.getReplaceBy());
				ispResponse.setPfBphase(ispData.getPower_factor_b_phase() != null ? String.valueOf(ispData.getPower_factor_b_phase()) : req.getReplaceBy());
				ispResponse.setPfRphase(ispData.getPower_factor_r_phase() != null ? String.valueOf(ispData.getPower_factor_r_phase()) : req.getReplaceBy());
				ispResponse.setPfYphase(ispData.getPower_factor_y_phase() != null ?String.valueOf(ispData.getPower_factor_y_phase()) : req.getReplaceBy());
				ispResponse.setVoltageVbn(ispData.getVoltage_vbn() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getVoltage_vbn())) : req.getReplaceBy());
				ispResponse.setVoltageVrn(ispData.getVoltage_vrn() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getVoltage_vrn())) : req.getReplaceBy());
				ispResponse.setVoltageVyn(ispData.getVoltage_vyn() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getVoltage_vyn())) : req.getReplaceBy());
				
				ispResponseList.add(ispResponse);
			} catch (Exception e) {
				LOG.error("CT: Issue in casting event data due to: "+e.getMessage());
			}
		}
		LOG.info("CT: Event Response Data Added....");
	}
}