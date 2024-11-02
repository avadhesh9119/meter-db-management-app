package com.global.meter.inventive.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.PushInstantsSinglePhase;
import com.global.meter.business.model.PushInstantsThreePhase;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.PushInstantResponse;
import com.global.meter.inventive.models.PushInstantThreePhaseResponse;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class PushInstantDataCaster {
	private static final Logger LOG = LoggerFactory.getLogger(PushInstantDataCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareSinglePhase(String outputList, List<PushInstantResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception {
		LOG.info("Push Instant Data Single Phase Caster called....");
		List<PushInstantsSinglePhase> singlePhaseData = new ArrayList<PushInstantsSinglePhase>();
		singlePhaseData = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<PushInstantsSinglePhase>>() {
				});
		LOG.info("Push Instant Data Single Phase Response Data Adding....");

		for (PushInstantsSinglePhase ispData : singlePhaseData) {
			PushInstantResponse ispResponse = new PushInstantResponse();

			ispResponse.setMeterNumber(ispData.getDevice_serial_number());
			ispResponse.setMeterDatetime(ispData.getMeter_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMeter_datetime()) : req.getReplaceBy());
			ispResponse.setMdasDatetime(ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : req.getReplaceBy());
			ispResponse.setActivePowerKw(ispData.getActive_power_kw() != null ? String.valueOf(ispData.getActive_power_kw()) : req.getReplaceBy());
			ispResponse.setApparentPowerKva(ispData.getApparent_power_va() != null ? String.valueOf(ispData.getApparent_power_va()) : req.getReplaceBy());
			ispResponse.setCumulativeEnergyKwhImport(ispData.getCum_import_kwh() != null ? String.valueOf(ispData.getCum_import_kwh()) : req.getReplaceBy());
			ispResponse.setCumulativeEnergyKvahImport(ispData.getCum_import_kvah() != null ? String.valueOf(ispData.getCum_import_kvah()) : req.getReplaceBy());
			ispResponse.setMdKw(ispData.getMax_demand_kw() != null ? String.valueOf(ispData.getMax_demand_kw()) : req.getReplaceBy());
			ispResponse.setFrequency(ispData.getFrequency() != null ? String.valueOf(ispData.getFrequency()) : req.getReplaceBy());
			ispResponse.setVoltage(ispData.getVoltage() != null ? String.valueOf(ispData.getVoltage()): req.getReplaceBy());
			ispResponse.setNeutralCurrent(ispData.getNeutral_current() != null ? String.valueOf(ispData.getNeutral_current()) : req.getReplaceBy());
			ispResponse.setPhaseCurrent(ispData.getPhase_current() != null ? String.valueOf(ispData.getPhase_current()) : req.getReplaceBy());
			ispResponse.setSignedPf(ispData.getSigned_pf() != null ? String.valueOf(ispData.getSigned_pf()) : req.getReplaceBy());
			ispResponse.setTamperCount(ispData.getTamper_count() != null ? String.valueOf(ispData.getTamper_count()) : req.getReplaceBy());
			ispResponse.setBillingCount(ispData.getBill_count() != null ? String.valueOf(ispData.getBill_count()) : req.getReplaceBy());
			ispResponse.setRelayStatus(ispData.getRelay_status() != null ? String.valueOf(ispData.getRelay_status() == 1 ? Constants.CONNECT : Constants.DISCONNECT) : req.getReplaceBy());
			ispResponse.setLoadLimit(ispData.getLoad_limit() != null ? String.valueOf(ispData.getLoad_limit()) : req.getReplaceBy());
			ispResponse.setMdKvaImport(ispData.getMax_demand_kva() != null ? String.valueOf(ispData.getMax_demand_kva()) : req.getReplaceBy());
			ispResponse.setCumulativeEnergyKwhExport(ispData.getCum_export_kwh() != null ? String.valueOf(ispData.getCum_export_kwh()) : req.getReplaceBy());
	
			ispResponse.setCumulativeEnergyKvahExport(ispData.getCum_export_kvah() != null ? String.valueOf(ispData.getCum_export_kvah()) : req.getReplaceBy());
			ispResponse.setMdKwDatetime(ispData.getMax_demand_kw_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMax_demand_kw_datetime()) : req.getReplaceBy());
			ispResponse.setMdKvaDatetime(ispData.getMax_demand_kva_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMax_demand_kva_datetime()) : req.getReplaceBy());
			
			ispResponse.setCumulativeEnergyKwhExport(ispData.getCum_export_kwh() != null ? String.valueOf(ispData.getCum_export_kwh()) : req.getReplaceBy());
			ispResponse.setCurrentMonthcumulativeEnergyKwhImport(ispData.getCurr_month_kwh_import() != null ? String.valueOf(ispData.getCurr_month_kwh_import()) : req.getReplaceBy());
			ispResponse.setCurrentMonthcumulativeEnergyKvahImport(ispData.getCurr_month_kvah_import() != null ? String.valueOf(ispData.getCurr_month_kvah_import()) : req.getReplaceBy());
			ispResponse.setCummPowerOnDuration(ispData.getCum_export_kwh()!= null ? String.valueOf(ispData.getCum_export_kvah()) : req.getReplaceBy());
			ispResponse.setProgramCount(ispData.getProgram_count() != null ? String.valueOf(ispData.getProgram_count()) : req.getReplaceBy());
			ispResponse.setNoOfPowerFailure(ispData.getNo_of_power_failure() != null ? String.valueOf(ispData.getNo_of_power_failure()) : req.getReplaceBy());
			ispResponse.setBillingDate(ispData.getBilling_date() != null ? String.valueOf(ispData.getBilling_date()) : req.getReplaceBy());
			ispResponse.setTemprature(ispData.getTemprature()!= null ? String.valueOf(ispData.getTemprature()) : req.getReplaceBy());
			ispResponse.setNoOfLoadSwitch(ispData.getNo_of_load_switch() !=null ? String.valueOf(ispData.getNo_of_load_switch()): req.getReplaceBy());
			ispResponse.setCummOverVoltageTamperCount(ispData.getCumm_over_voltage_tamper_count() != null ? String.valueOf(ispData.getCumm_over_voltage_tamper_count()) : req.getReplaceBy());
			ispResponse.setCummLowVoltageTamperCount(ispData.getCumm_low_voltage_tamper_count() !=null ? String.valueOf(ispData.getCumm_low_voltage_tamper_count() ): req.getReplaceBy());
			ispResponse.setDcummReverseCurrentTamperCount(ispData.getCumm_reverse_current_tamper_count() !=null ? String.valueOf(ispData.getCumm_reverse_current_tamper_count()) : req.getReplaceBy());
			ispResponse.setCummOverCurrentTamperCount(ispData.getCumm_over_current_tamper_count() !=null ? String.valueOf(ispData.getCumm_over_current_tamper_count()) : req.getReplaceBy());
			ispResponse.setCummEarthTamperCount(ispData.getCumm_earth_tamper_count() !=null ? String.valueOf(ispData.getCumm_earth_tamper_count()) : req.getReplaceBy());
			ispResponse.setCummMagnetTamperCount(ispData.getCumm_magnet_tamper_count() !=null ? String.valueOf(ispData.getCumm_magnet_tamper_count()) : req.getReplaceBy());
			ispResponse.setCummNeutralDisturbanceCount(ispData.getCumm_neutral_disturbance_count() !=null ? String.valueOf(ispData.getCumm_neutral_disturbance_count()) : req.getReplaceBy());
			ispResponse.setCummSwTamperCount(ispData.getCumm_sw_tamper_count() !=null ? String.valueOf(ispData.getCumm_sw_tamper_count()): req.getReplaceBy());
			ispResponse.setCumOverLoadTamperCount(ispData.getCumm_over_load_tamper_count() != null ? String.valueOf(ispData.getCumm_over_load_tamper_count()) : req.getReplaceBy());
			ispResponse.setDcummCommsRemovalTamperCount(ispData.getCumm_comms_removal_tamper_count() != null ? String.valueOf(ispData.getCumm_comms_removal_tamper_count()) : req.getReplaceBy());
			ispResponse.setDcummCaseOpenTamperCount(ispData.getCumm_case_open_tamper_count() != null ? String.valueOf(ispData.getCumm_case_open_tamper_count()) : req.getReplaceBy());
			ispResponse.setCummTempratureRiseCount(ispData.getCumm_temprature_rise_count() != null ? String.valueOf(ispData.getCumm_temprature_rise_count()) : req.getReplaceBy());
			ispResponse.setCummPowerFailureDuration(ispData.getCumm_power_failure_duration() !=null ? String.valueOf(ispData.getCumm_power_failure_duration()) : req.getReplaceBy());
			ispResponse.setRelayOperationDisconnectCount(ispData.getRelay_operation_disconnect_count() != null ? String.valueOf(ispData.getRelay_operation_disconnect_count()) : req.getReplaceBy());
			ispResponse.setRelayOperationConnectCount(ispData.getRelay_operation_connect_count() !=null ? String.valueOf(ispData.getRelay_operation_connect_count()) : req.getReplaceBy());
			ispResponse.setAvgSignalStrength(ispData.getAvg_signal_strength() != null ? String.valueOf(ispData.getAvg_signal_strength()) : req.getReplaceBy());
			ispResponse.setAvgPfBillingPeriod(ispData.getAvg_pf_billing_period() != null ? String.valueOf(ispData.getAvg_pf_billing_period()) : req.getReplaceBy());
			ispResponse.setMaxdemandkvaExport(ispData.getMax_demand_kva_export() != null ? String.valueOf(ispData.getMax_demand_kva_export()) : req.getReplaceBy());
			ispResponse.setMaxdemandkwExport(ispData.getMax_demand_kw_export() != null ? String.valueOf(ispData.getMax_demand_kw_export()) : req.getReplaceBy());
			
					
			ispResponseList.add(ispResponse);
		}
		LOG.info("Push Instant Data Single Phase Response Data Added....");
	}

	
	public void prepareThreePhase(String outputList, List<PushInstantThreePhaseResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception {
		LOG.info("Push Instant Data Three Phase Caster called....");
		List<PushInstantsThreePhase> singlePhaseData = new ArrayList<PushInstantsThreePhase>();
		singlePhaseData = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<PushInstantsThreePhase>>() {
				});
		LOG.info("Push Instant Data Three Phase Response Data Adding....");

		for (PushInstantsThreePhase ispData : singlePhaseData) {
			PushInstantThreePhaseResponse ispResponse = new PushInstantThreePhaseResponse();

			ispResponse.setMeterNumber(ispData.getDevice_serial_number());
			ispResponse.setMeterDatetime(dateConverter.convertDateToHesString(ispData.getMeter_datetime()));
			ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			ispResponse.setKvahImport(ispData.getCum_import_kvah() != null ? String.valueOf(ispData.getCum_import_kvah()) : req.getReplaceBy());
			ispResponse.setKvahExport(ispData.getCum_export_kvah() != null ? String.valueOf(ispData.getCum_export_kvah()) : req.getReplaceBy());
			ispResponse.setKwhImport(ispData.getCum_import_kwh() != null ? String.valueOf(ispData.getCum_import_kwh()) : req.getReplaceBy());
			ispResponse.setKwhExport(ispData.getCum_export_kwh() != null ? String.valueOf(ispData.getCum_export_kwh()) : req.getReplaceBy());
			ispResponse.setCurrentL1(ispData.getCurrent_l1() != null ? String.valueOf(ispData.getCurrent_l1()) : req.getReplaceBy());
			ispResponse.setCurrentL2(ispData.getCurrent_l2() != null ? String.valueOf(ispData.getCurrent_l2()) : req.getReplaceBy());
			ispResponse.setCurrentL3(ispData.getCurrent_l3() != null ? String.valueOf(ispData.getCurrent_l3()) : req.getReplaceBy());
			ispResponse.setPfL1(ispData.getPf_l1() != null ? String.valueOf(ispData.getPf_l1()) : req.getReplaceBy());
			ispResponse.setPfL2(ispData.getPf_l2() != null ? String.valueOf(ispData.getPf_l2()) : req.getReplaceBy());
			ispResponse.setPfL3(ispData.getPf_l3() != null ? String.valueOf(ispData.getPf_l3()) : req.getReplaceBy());
			ispResponse.setvL1(ispData.getVoltage_l1() != null ? String.valueOf(ispData.getVoltage_l1()) : req.getReplaceBy());
			ispResponse.setvL2(ispData.getVoltage_l2() != null ? String.valueOf(ispData.getVoltage_l2()) : req.getReplaceBy());
			ispResponse.setvL3(ispData.getVoltage_l3() != null ? String.valueOf(ispData.getVoltage_l3()) : req.getReplaceBy());
			ispResponse.setSignedPf(ispData.getSigned_pf() != null ? String.valueOf(ispData.getSigned_pf()) : req.getReplaceBy());
			ispResponse.setTamperCount(ispData.getTamper_count() != null ? String.valueOf(ispData.getTamper_count()) : req.getReplaceBy());

			ispResponseList.add(ispResponse);
		}
		LOG.info("Push Instant Data Three Phase Response Data Added....");
	}
}
