package com.global.meter.inventive.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.InstantaneousCt;
import com.global.meter.business.model.InstantaneousSinglePhase;
import com.global.meter.business.model.InstantaneousThreePhase;
import com.global.meter.inventive.models.InstantaneousCtResponse;
import com.global.meter.inventive.models.InstantaneousSinglePhaseResponse;
import com.global.meter.inventive.models.InstantaneousThreePhaseResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class InstantaneousDataCaster {
	private static final Logger LOG = LoggerFactory.getLogger(FirmwareConfigCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareSinglePhase(String outputList, List<InstantaneousSinglePhaseResponse> ispResponseList, MeterDataVisualizationReq req)
			throws Exception {
		LOG.info("Single Phase: Instantaneous Data Caster called....");
		List<InstantaneousSinglePhase> singlePhaseData = new ArrayList<InstantaneousSinglePhase>();
		singlePhaseData = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<InstantaneousSinglePhase>>() {
				});
		LOG.info("Single Phase: Instantaneous Response Data Adding....");

		for (InstantaneousSinglePhase ispData : singlePhaseData) {
			InstantaneousSinglePhaseResponse ispResponse = new InstantaneousSinglePhaseResponse();

			ispResponse.setActivePowerKW(ispData.getActive_power_kw() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getActive_power_kw())) : req.getReplaceBy());
			ispResponse.setApparentPowerKva(ispData.getApparent_power_kva() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getApparent_power_kva())) : req.getReplaceBy());
			ispResponse.setBillCount(ispData.getCumulative_bill_count() != null ? String.valueOf(ispData.getCumulative_bill_count()) : req.getReplaceBy());
			ispResponse.setFrequency(ispData.getFrequency() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getFrequency())) : req.getReplaceBy());
			
			ispResponse.setHesDatetime(ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : req.getReplaceBy());
			ispResponse.setMeterDatetime(ispData.getMeter_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMeter_datetime()) : req.getReplaceBy());
			
			ispResponse.setKvahExport(ispData.getCumulative_energy_kvah_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_export())) : req.getReplaceBy());
			ispResponse.setKvahImport(ispData.getCumulative_energy_kvah_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_import())) : req.getReplaceBy());
			ispResponse.setKwhExport(ispData.getCumulative_energy_kwh_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_export())) : req.getReplaceBy());
			ispResponse.setKwhImport(ispData.getCumulative_energy_kwh_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_import())) : req.getReplaceBy());
			ispResponse.setLoadLimit(ispData.getLoad_limits() != null ? String.valueOf(ispData.getLoad_limits()) : req.getReplaceBy());
			ispResponse.setLoadLimitStatus(ispData.getLoad_limit_status() != null ?
					ispData.getLoad_limit_status() == 1 ? Constants.Status.CONNECTED : Constants.Status.DISCONNECTED : req.getReplaceBy());
			ispResponse.setMdKva(ispData.getMaximum_demand_kva() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kva())) : req.getReplaceBy());
			ispResponse.setMdKvaDatetime(ispData.getMaximum_demand_kva_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_datetime()) : req.getReplaceBy());
			ispResponse.setMdKw(ispData.getMaximum_demand_kw() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kw())) : req.getReplaceBy());
			ispResponse.setMdKwDatetime(ispData.getMaximum_demand_kw_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_datetime()) : req.getReplaceBy());

			ispResponse.setMeterNumber(ispData.getDevice_serial_number());
			ispResponse.setNeutralCurrent(ispData.getNeutral_current() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getNeutral_current())) : req.getReplaceBy());
			ispResponse.setPhaseCurrent(ispData.getPhase_current() != null ?  String.valueOf(CommonUtils.decimalFormat(ispData.getPhase_current())) : req.getReplaceBy());
			ispResponse.setPowerDuration(ispData.getCumulative_power_on_duration() != null ? String.valueOf(ispData.getCumulative_power_on_duration()) : req.getReplaceBy());
			ispResponse.setPowerFactor(ispData.getPower_factor() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPower_factor())) : req.getReplaceBy());
			ispResponse.setProgramCount(ispData.getCumulative_program_count() != null ? String.valueOf(ispData.getCumulative_program_count()) : req.getReplaceBy());
			ispResponse.setTamperCount(ispData.getCumulative_tamper_count() != null ? String.valueOf(ispData.getCumulative_tamper_count()) : req.getReplaceBy());
			ispResponse.setVoltage(ispData.getInstant_voltage() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getInstant_voltage())) : req.getReplaceBy());

			ispResponseList.add(ispResponse);
		}
		LOG.info("Single Phase: Instantaneous Response Data Added....");
	}
	
	public void prepareThreePhase(String outputList, List<InstantaneousThreePhaseResponse> ispResponseList, MeterDataVisualizationReq req)
			throws Exception {
		LOG.info("Three Phase: Instantaneous Data Caster called....");
		List<InstantaneousThreePhase> threePhaseData = new ArrayList<InstantaneousThreePhase>();
		threePhaseData = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<InstantaneousThreePhase>>() {
				});
		LOG.info("Three Phase: Instantaneous Response Data Adding....");

		for (InstantaneousThreePhase ispData : threePhaseData) {
			InstantaneousThreePhaseResponse ispResponse = new InstantaneousThreePhaseResponse();

			ispResponse.setMeterNumber(ispData.getDevice_serial_number());
			ispResponse.setActivePowerKW(ispData.getActive_power_kw() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getActive_power_kw())) : req.getReplaceBy());
			ispResponse.setApparentPowerKva(ispData.getApparent_power_kva() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getApparent_power_kva())) : req.getReplaceBy());
			ispResponse.setReactivePowerKvar(ispData.getReactive_power_kvar() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getReactive_power_kvar())) : req.getReplaceBy());
			ispResponse.setKvahExport(ispData.getCumulative_energy_kvah_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_export())) : req.getReplaceBy());
			ispResponse.setKvahImport(ispData.getCumulative_energy_kvah_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_import())) : req.getReplaceBy());
			ispResponse.setKwhExport(ispData.getCumulative_energy_kwh_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_export())) : req.getReplaceBy());
			ispResponse.setKwhImport(ispData.getCumulative_energy_kwh_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_import())) : req.getReplaceBy());
			ispResponse.setKvahQ1(ispData.getCumulative_energy_kvarh_q1() != null ?  String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q1())) : req.getReplaceBy());
			ispResponse.setKvahQ2(ispData.getCumulative_energy_kvarh_q2() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q2())) : req.getReplaceBy());
			ispResponse.setKvahQ3(ispData.getCumulative_energy_kvarh_q3() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q3())) : req.getReplaceBy());
			ispResponse.setKvahQ4(ispData.getCumulative_energy_kvarh_q4() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q4())) : req.getReplaceBy());
			ispResponse.setMdKva(ispData.getMaximum_demand_kva() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kva())) : req.getReplaceBy());
			ispResponse.setMdKvaDatetime(ispData.getMaximum_demand_kva_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_datetime()) : req.getReplaceBy());
			ispResponse.setMdKw(ispData.getMaximum_demand_kw() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kw())) : req.getReplaceBy());
			ispResponse.setMdKwDatetime(ispData.getMaximum_demand_kw_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_datetime()) : req.getReplaceBy());
			ispResponse.setPowerFailureDuration(ispData.getCumm_power_off_duration_in_mins() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumm_power_off_duration_in_mins())) : req.getReplaceBy());
			ispResponse.setProgramCount(ispData.getCumulative_program_count() != null ? String.valueOf(ispData.getCumulative_program_count()) : req.getReplaceBy());
			ispResponse.setTamperCount(ispData.getCumulative_tamper_count() != null ? String.valueOf(ispData.getCumulative_tamper_count()) : req.getReplaceBy());
			ispResponse.setBillCount(ispData.getCumulative_bill_count() != null ? String.valueOf(ispData.getCumulative_bill_count()) : req.getReplaceBy());
			ispResponse.setPowerFailureCount(ispData.getNo_of_power_failure() != null ? String.valueOf(ispData.getNo_of_power_failure()): req.getReplaceBy());
			ispResponse.setFrequency(ispData.getFrequency() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getFrequency())) : req.getReplaceBy());
			ispResponse.setLoadLimitStatus(
					ispData.getLoad_limit_status() != null ?
					ispData.getLoad_limit_status() == 1 ? Constants.Status.CONNECTED : Constants.Status.DISCONNECTED : req.getReplaceBy());
			ispResponse.setLoadLimit(ispData.getLoad_limits() != null ? String.valueOf(ispData.getLoad_limits()) : req.getReplaceBy());
			
			ispResponse.setVoltageL1(ispData.getInstant_voltage_l1() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getInstant_voltage_l1())) : req.getReplaceBy());
			ispResponse.setVoltageL2(ispData.getInstant_voltage_l2() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getInstant_voltage_l2())) : req.getReplaceBy());
			ispResponse.setVoltageL3(ispData.getInstant_voltage_l3() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getInstant_voltage_l3())) : req.getReplaceBy());
			
			ispResponse.setCurrentL1(ispData.getPhase_current_l1() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPhase_current_l1())) : req.getReplaceBy());
			ispResponse.setCurrentL2(ispData.getPhase_current_l2() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPhase_current_l2())) : req.getReplaceBy());
			ispResponse.setCurrentL3(ispData.getPhase_current_l3() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPhase_current_l3())) : req.getReplaceBy());
			
			ispResponse.setPfL1(ispData.getPower_factor_l1() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPower_factor_l1())) : req.getReplaceBy());
			ispResponse.setPfL2(ispData.getPower_factor_l2() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPower_factor_l2())) : req.getReplaceBy());
			ispResponse.setPfL3(ispData.getPower_factor_l3() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPower_factor_l3())) : req.getReplaceBy());
			ispResponse.setPf3Ph(ispData.getPower_factor_3ph() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPower_factor_3ph())) : req.getReplaceBy());
			
			ispResponse.setMeterDatetime(ispData.getMeter_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMeter_datetime()) : req.getReplaceBy());
			ispResponse.setHesDatetime(ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : req.getReplaceBy());
			ispResponse.setLastBillingDatetime(ispData.getLast_billing_datetime() != null ? dateConverter.convertDateToHesString(ispData.getLast_billing_datetime()) : req.getReplaceBy());
			ispResponse.setPowerOnDuration(ispData.getCumm_power_on_duration_in_mins() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumm_power_on_duration_in_mins())) : req.getReplaceBy());

			ispResponseList.add(ispResponse);
		}
		LOG.info("Three Phase: Instantaneous Response Data Added....");
	}
	
	
	
	public void prepareCt(String outputList, List<InstantaneousCtResponse> ispResponseList, MeterDataVisualizationReq req)
			throws Exception {
		LOG.info("CT: Instantaneous Data Caster called....");
		List<InstantaneousCt> ctData = new ArrayList<InstantaneousCt>();
		ctData = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<InstantaneousCt>>() {
				});
		LOG.info("CT: Instantaneous Response Data Adding....");

		for (InstantaneousCt ispData : ctData) {
			InstantaneousCtResponse ispResponse = new InstantaneousCtResponse();

			ispResponse.setMeterNumber(ispData.getDevice_serial_number());
			ispResponse.setActivePowerKW(ispData.getActive_power_kw() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getActive_power_kw())) : req.getReplaceBy());
			ispResponse.setApparentPowerKva(ispData.getApparent_power_kva() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getApparent_power_kva())) : req.getReplaceBy());
			ispResponse.setReactivePowerKvar(ispData.getReactive_power_kvar() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getReactive_power_kvar())) : req.getReplaceBy());
			ispResponse.setKvahExport(ispData.getCumulative_energy_kvah_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_export())) : req.getReplaceBy());
			ispResponse.setKvahImport(ispData.getCumulative_energy_kvah_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_import())) : req.getReplaceBy());
			ispResponse.setKwhExport(ispData.getCumulative_energy_kwh_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_export())) : req.getReplaceBy());
			ispResponse.setKwhImport(ispData.getCumulative_energy_kwh_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_import())) : req.getReplaceBy());
			ispResponse.setKvahQ1(ispData.getCumulative_energy_kvarh_q1() != null ?  String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q1())) : req.getReplaceBy());
			ispResponse.setKvahQ2(ispData.getCumulative_energy_kvarh_q2() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q2())) : req.getReplaceBy());
			ispResponse.setKvahQ3(ispData.getCumulative_energy_kvarh_q3() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q3())) : req.getReplaceBy());
			ispResponse.setKvahQ4(ispData.getCumulative_energy_kvarh_q4() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q4())) : req.getReplaceBy());
			ispResponse.setMdKva(ispData.getMaximum_demand_kva() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kva())) : req.getReplaceBy());
			ispResponse.setMdKvaDatetime(ispData.getMaximum_demand_kva_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_datetime()) : req.getReplaceBy());
			ispResponse.setMdKw(ispData.getMaximum_demand_kw() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kw())) : req.getReplaceBy());
			ispResponse.setMdKwDatetime(ispData.getMaximum_demand_kw_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_datetime()) : req.getReplaceBy());
			ispResponse.setProgramCount(String.valueOf(ispData.getCumulative_program_count()));
			ispResponse.setTamperCount(String.valueOf(ispData.getCumulative_tamper_count()));
			ispResponse.setBillCount(String.valueOf(ispData.getCumulative_bill_count()));
			ispResponse.setPowerFailureCount(String.valueOf(ispData.getNo_of_power_failure()));
			ispResponse.setFrequency(ispData.getFrequency() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getFrequency())) : req.getReplaceBy());
			ispResponse.setLoadLimitStatus(
					ispData.getLoad_limit_status() != null ?
					ispData.getLoad_limit_status() == 1 ? Constants.Status.CONNECTED : Constants.Status.DISCONNECTED : req.getReplaceBy());
			ispResponse.setLoadLimit(ispData.getLoad_limit() != null ? String.valueOf(ispData.getLoad_limit()) : req.getReplaceBy());
			
			ispResponse.setVoltageL1(ispData.getInstant_voltage_l1() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getInstant_voltage_l1())) : req.getReplaceBy());
			ispResponse.setVoltageL2(ispData.getInstant_voltage_l2() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getInstant_voltage_l2())) : req.getReplaceBy());
			ispResponse.setVoltageL3(ispData.getInstant_voltage_l3() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getInstant_voltage_l3())) : req.getReplaceBy());
			
			ispResponse.setCurrentL1(ispData.getPhase_current_l1() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPhase_current_l1())) : req.getReplaceBy());
			ispResponse.setCurrentL2(ispData.getPhase_current_l2() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPhase_current_l2())) : req.getReplaceBy());
			ispResponse.setCurrentL3(ispData.getPhase_current_l3() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPhase_current_l3())) : req.getReplaceBy());
			
			ispResponse.setPfL1(ispData.getPower_factor_l1() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPower_factor_l1())) : req.getReplaceBy());
			ispResponse.setPfL2(ispData.getPower_factor_l2() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPower_factor_l2())) : req.getReplaceBy());
			ispResponse.setPfL3(ispData.getPower_factor_l3() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPower_factor_l3())) : req.getReplaceBy());
			ispResponse.setPf3Ph(ispData.getPower_factor_3ph() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getPower_factor_3ph())) : req.getReplaceBy());
			
			ispResponse.setMeterDatetime(ispData.getMeter_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMeter_datetime()) : req.getReplaceBy());
			ispResponse.setHesDatetime(ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : req.getReplaceBy());
			ispResponse.setLastBillingDatetime(ispData.getLast_billing_datetime() != null ? dateConverter.convertDateToHesString(ispData.getLast_billing_datetime()) : req.getReplaceBy());
			
			ispResponse.setMdWExport(ispData.getMaximum_demand_w_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_w_export())) : req.getReplaceBy());
			ispResponse.setMdWExportDatetime(ispData.getMaximum_demand_w_export_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_w_export_datetime()) : req.getReplaceBy());
			ispResponse.setMdVAExport(ispData.getMaximum_demand_va_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_va_export())) : req.getReplaceBy());
			ispResponse.setMdVAExportDatetime(ispData.getMaximum_demand_va_export_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_va_export_datetime()) : req.getReplaceBy());

			ispResponse.setaPVoltAB(ispData.getAngle_phase_volt_ab() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getAngle_phase_volt_ab())) : req.getReplaceBy());
			ispResponse.setaPVoltBC(ispData.getAngle_phase_volt_bc() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getAngle_phase_volt_bc())) : req.getReplaceBy());
			ispResponse.setaPVoltAC(ispData.getAngle_phase_volt_ac() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getAngle_phase_volt_ac())) : req.getReplaceBy());
			ispResponse.setPowerOffDuration(ispData.getCumm_power_off_duration_in_mins() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumm_power_off_duration_in_mins())) : req.getReplaceBy());
			
			
			ispResponseList.add(ispResponse);
		}
		LOG.info("CT: Instantaneous Response Data Added....");
	}

}
