package com.global.meter.inventive.mdm.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DeltaLoadProfileCT;
import com.global.meter.business.model.DeltaLoadProfileSinglePhase;
import com.global.meter.business.model.DeltaLoadProfileThreePhase;
import com.global.meter.inventive.models.DeltaLPCTResponse;
import com.global.meter.inventive.models.DeltaLPSinglePhaseResponse;
import com.global.meter.inventive.models.DeltaLPThreePhaseResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class DeltaLPDataMdmCaster {
	private static final Logger LOG = LoggerFactory.getLogger(DeltaLPDataMdmCaster.class);
	
	@Autowired
	DateConverter dateConverter;

	public void prepareSinglePhase(String outputList, List<DeltaLPSinglePhaseResponse> ispResponseList) throws Exception{
		LOG.info("Single phase: Delta LP Data Caster called.");
		List<DeltaLoadProfileSinglePhase> singlePhaseData = new ArrayList<DeltaLoadProfileSinglePhase>();
		singlePhaseData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<DeltaLoadProfileSinglePhase>>() {});
		LOG.info("Single phase: Delta LP Response Data Caster Adding.");
	
		for (DeltaLoadProfileSinglePhase ispData : singlePhaseData) {
			DeltaLPSinglePhaseResponse ispResponse = new DeltaLPSinglePhaseResponse();
			
			ispResponse.setIntervalDatetime(dateConverter.convertDateToHesString(ispData.getInterval_datetime()));
			ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			
			ispResponse.setKvahExport(String.valueOf(ispData.getBlock_energy_kvah_export()));
			ispResponse.setKvahImport(String.valueOf(ispData.getBlock_energy_kvah_import()));
			ispResponse.setkWhExport(String.valueOf(ispData.getBlock_energy_kwh_export()));
			ispResponse.setkWhImport(String.valueOf(ispData.getBlock_energy_kwh_import()));
			
			ispResponse.setAvgCurrent(String.valueOf(ispData.getAverage_current()));
			ispResponse.setAvgVoltage(String.valueOf(ispData.getAverage_voltage()));
			//ispResponse.setFrequency(String.valueOf(ispData.getFrequency()!= null ? ispData.getFrequency(): "-"));
			
			ispResponse.setMeterNumber(ispData.getDevice_serial_number());
		
			ispResponseList.add(ispResponse);
		}
		LOG.info("Single phase: Delta LP Response Data Caster Added.");
	}
	
	public void prepareThreePhase(String outputList, List<DeltaLPThreePhaseResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception{
		LOG.info("Three phase: Delta LP Data Caster called.");
		List<DeltaLoadProfileThreePhase> threePhaseData = new ArrayList<DeltaLoadProfileThreePhase>();
		threePhaseData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<DeltaLoadProfileThreePhase>>() {});
		LOG.info("Three phase: Delta LP Response Data Caster Adding.");
	
		for (DeltaLoadProfileThreePhase ispData : threePhaseData) {
			DeltaLPThreePhaseResponse ispResponse = new DeltaLPThreePhaseResponse();
			

			ispResponse.setMeterNumber(ispData.getDevice_serial_number());
			ispResponse.setIntervalDatetime(dateConverter.convertDateToHesString(ispData.getInterval_datetime()));
			ispResponse.setKvahExport(ispData.getBlock_energy_kvah_export() != null ? String.valueOf(ispData.getBlock_energy_kvah_export()) : req.getReplaceBy());
			ispResponse.setKvahImport(ispData.getBlock_energy_kvah_import() != null ? String.valueOf(ispData.getBlock_energy_kvah_import()) : req.getReplaceBy());
			ispResponse.setkWhExport(ispData.getBlock_energy_kwh_export() != null ? String.valueOf(ispData.getBlock_energy_kwh_export()) : req.getReplaceBy());
			ispResponse.setkWhImport(ispData.getBlock_energy_kwh_import() != null ? String.valueOf(ispData.getBlock_energy_kwh_import()) : req.getReplaceBy());
			ispResponse.setPhaseCurrentL1(ispData.getPhase_current_l1() != null ? String.valueOf(ispData.getPhase_current_l1()) : req.getReplaceBy());
			ispResponse.setPhaseCurrentL2(ispData.getPhase_current_l2() != null ? String.valueOf(ispData.getPhase_current_l2()) : req.getReplaceBy());
			ispResponse.setPhaseCurrentL3(ispData.getPhase_current_l3() != null ? String.valueOf(ispData.getPhase_current_l3()) : req.getReplaceBy());
			ispResponse.setPhaseVoltageL1(ispData.getPhase_voltage_l1() != null ? String.valueOf(ispData.getPhase_voltage_l1()) : req.getReplaceBy());
			ispResponse.setPhaseVoltageL2(ispData.getPhase_voltage_l2() != null ? String.valueOf(ispData.getPhase_voltage_l2()) : req.getReplaceBy());
			ispResponse.setPhaseVoltageL3(ispData.getPhase_voltage_l3() != null ? String.valueOf(ispData.getPhase_voltage_l3()) : req.getReplaceBy());
			ispResponse.setKvarhQ1(ispData.getCumulative_energy_kvarh_q1() != null ? String.valueOf(ispData.getCumulative_energy_kvarh_q1()) : req.getReplaceBy());
			ispResponse.setKvarhQ2(ispData.getCumulative_energy_kvarh_q2() != null ? String.valueOf(ispData.getCumulative_energy_kvarh_q2()) : req.getReplaceBy());
			ispResponse.setKvarhQ3(ispData.getCumulative_energy_kvarh_q3() != null ? String.valueOf(ispData.getCumulative_energy_kvarh_q3()) : req.getReplaceBy());
			ispResponse.setKvarhQ4(ispData.getCumulative_energy_kvarh_q4() != null ? String.valueOf(ispData.getCumulative_energy_kvarh_q4()) : req.getReplaceBy());
			ispResponse.setStatusByte(String.valueOf(ispData.getStatus_byte()));
			ispResponse.setAvgSignalStrength(String.valueOf(ispData.getAvg_signal_strength()));
			ispResponse.setPowerDowntimeInMins(ispData.getPower_downtime_in_mins() != null ? String.valueOf(ispData.getPower_downtime_in_mins()) : req.getReplaceBy());
			ispResponse.setrPhaseActivePowerKw(ispData.getR_phase_active_power_kw() != null ? String.valueOf(ispData.getR_phase_active_power_kw()) : req.getReplaceBy());
			ispResponse.setyPhaseActivePowerKw(ispData.getY_phase_active_power_kw() != null ? String.valueOf(ispData.getY_phase_active_power_kw()) : req.getReplaceBy());
			ispResponse.setbPhaseActivePowerKw(ispData.getB_phase_active_power_kw() != null ? String.valueOf(ispData.getB_phase_active_power_kw()) : req.getReplaceBy());
			ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			
			ispResponse.setLeadBlockEnergyKvarh(ispData.getLead_block_energy_kvarh() != null ? String.valueOf(ispData.getLead_block_energy_kvarh()) : req.getReplaceBy());
			ispResponse.setCumulativeEnergyKwh(ispData.getCumulative_energy_kwh() != null ? String.valueOf(ispData.getCumulative_energy_kwh()) : req.getReplaceBy());
			ispResponse.setCumulativeEnergyKvah(ispData.getCumulative_energy_kvah() != null ? String.valueOf(ispData.getCumulative_energy_kvah()) : req.getReplaceBy());
			ispResponse.setMdType(ispData.getMd_type() != null ? String.valueOf(ispData.getMd_type()) : req.getReplaceBy());
			ispResponse.setMdBlockSlidingType(ispData.getMd_block_sliding_type() != null ? String.valueOf(ispData.getMd_block_sliding_type()) : req.getReplaceBy());
			ispResponse.setNeutralCurrent(ispData.getNeutral_current() != null ? String.valueOf(ispData.getNeutral_current()) : req.getReplaceBy());
			ispResponse.setMobileNo(ispData.getMobile_no() != null ? String.valueOf(ispData.getMobile_no().intValue()) : req.getReplaceBy());
			ispResponse.setInstantAvgpf(ispData.getInstant_avgpf() != null ? String.valueOf(ispData.getInstant_avgpf()) : req.getReplaceBy());
			ispResponse.setModuleRtc(ispData.getModule_rtc() != null ? String.valueOf(ispData.getModule_rtc()) : req.getReplaceBy());
			ispResponse.setkVAMax(ispData.getKva_max() != null ? String.valueOf(ispData.getKva_max()) : req.getReplaceBy());
			ispResponse.setMaxCurrent(ispData.getMax_current() != null ? String.valueOf(ispData.getMax_current()) : req.getReplaceBy());
			ispResponse.setMaxVoltage(ispData.getMax_voltage() != null ? String.valueOf(ispData.getMax_voltage()) : req.getReplaceBy());

			ispResponseList.add(ispResponse);
		}
		LOG.info("Three phase: Delta LP Response Data Caster Added.");
	}
	
	public void prepareCT(String outputList, List<DeltaLPCTResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception{
		LOG.info("CT: Delta LP Data  for CT Caster called.");
		List<DeltaLoadProfileCT> ctData = new ArrayList<DeltaLoadProfileCT>();
		ctData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<DeltaLoadProfileCT>>() {});
		LOG.info("CT: Delta LP Response Data for CT Caster Adding.");
	
		for (DeltaLoadProfileCT ispData : ctData) {
			DeltaLPCTResponse ispResponse = new DeltaLPCTResponse();
			

			ispResponse.setMeterNumber(ispData.getDevice_serial_number());
			ispResponse.setIntervalDatetime(dateConverter.convertDateToHesString(ispData.getInterval_datetime()));
			ispResponse.setKvahExport(String.valueOf(CommonUtils.decimalFormat(ispData.getBlock_energy_kvah_export())));
			ispResponse.setKvahImport(String.valueOf(CommonUtils.decimalFormat(ispData.getBlock_energy_kvah_import())));
			ispResponse.setkWhExport(String.valueOf(CommonUtils.decimalFormat(ispData.getBlock_energy_kwh_export())));
			ispResponse.setkWhImport(String.valueOf(CommonUtils.decimalFormat(ispData.getBlock_energy_kwh_import())));
			ispResponse.setPhaseCurrentL1(String.valueOf(CommonUtils.decimalFormat(ispData.getPhase_current_l1())));
			ispResponse.setPhaseCurrentL2(String.valueOf(CommonUtils.decimalFormat(ispData.getPhase_current_l2())));
			ispResponse.setPhaseCurrentL3(String.valueOf(CommonUtils.decimalFormat(ispData.getPhase_current_l3())));
			ispResponse.setPhaseVoltageL1(String.valueOf(CommonUtils.decimalFormat(ispData.getPhase_voltage_l1())));
			ispResponse.setPhaseVoltageL2(String.valueOf(CommonUtils.decimalFormat(ispData.getPhase_voltage_l2())));
			ispResponse.setPhaseVoltageL3(String.valueOf(CommonUtils.decimalFormat(ispData.getPhase_voltage_l3())));
			ispResponse.setKvarhQ1(ispData.getCumulative_energy_kvarh_q1() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q1())) : req.getReplaceBy());
			ispResponse.setKvarhQ2(ispData.getCumulative_energy_kvarh_q2() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q2())) : req.getReplaceBy());
			ispResponse.setKvarhQ3(ispData.getCumulative_energy_kvarh_q3() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q3())) : req.getReplaceBy());
			ispResponse.setKvarhQ4(ispData.getCumulative_energy_kvarh_q4() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q4())) : req.getReplaceBy());
			ispResponse.setStatusByte(String.valueOf(CommonUtils.decimalFormat(ispData.getStatus_byte())));
			ispResponse.setAvgSignalStrength(String.valueOf(CommonUtils.decimalFormat(ispData.getAvg_signal_strength())));
			ispResponse.setkVARHLeadBlockEnergy(ispData.getLead_block_energy_kvarh() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getLead_block_energy_kvarh())) : req.getReplaceBy());
			ispResponse.setPowerDowntimeInMins(ispData.getPower_downtime_in_mins() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPower_downtime_in_mins())) : req.getReplaceBy());
			ispResponse.setrPhaseActivePowerKw(ispData.getR_phase_active_power_kw() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getR_phase_active_power_kw())) : req.getReplaceBy());
			ispResponse.setyPhaseActivePowerKw(ispData.getY_phase_active_power_kw() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getY_phase_active_power_kw())) : req.getReplaceBy());
			ispResponse.setbPhaseActivePowerKw(ispData.getB_phase_active_power_kw() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getB_phase_active_power_kw())) : req.getReplaceBy());
			ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			ispResponse.setAvg5NeutralCurrent(ispData.getCh_0_lo_current_neutral_current_avg_5() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCh_0_lo_current_neutral_current_avg_5())) : req.getReplaceBy());

			
			ispResponseList.add(ispResponse);
		}
		LOG.info("CT: Delta LP Response Data Caster Added.");
	}
}
