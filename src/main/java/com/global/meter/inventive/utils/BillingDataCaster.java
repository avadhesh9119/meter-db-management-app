package com.global.meter.inventive.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.BillingDataCT;
import com.global.meter.business.model.BillingDataSinglePhase;
import com.global.meter.business.model.BillingDataThreePhase;
import com.global.meter.inventive.models.BillingCTResponse;
import com.global.meter.inventive.models.BillingSinglePhaseResponse;
import com.global.meter.inventive.models.BillingThreePhaseResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class BillingDataCaster {
	private static final Logger LOG = LoggerFactory.getLogger(BillingDataCaster.class);


	@Autowired
	DateConverter dateConverter;

	public void prepareSinglePhase(String outputList, List<BillingSinglePhaseResponse> ispResponseList, MeterDataVisualizationReq req)
			throws Exception {
		LOG.info("Single phase: Billing Data Caster Called.");
		List<BillingDataSinglePhase> singlePhaseData = new ArrayList<BillingDataSinglePhase>();
		singlePhaseData = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<BillingDataSinglePhase>>() {
				});
		LOG.info("Single phase: Billing Response Data Caster Adding...");

		for (BillingDataSinglePhase ispData : singlePhaseData) {
			BillingSinglePhaseResponse ispResponse = new BillingSinglePhaseResponse();

			ispResponse.setDeviceSerialNumber(ispData.getDevice_serial_number() != null ? ispData.getDevice_serial_number() : req.getReplaceBy());
			ispResponse.setBillingDatetime(ispData.getBilling_datetime() != null ? dateConverter.convertDateToHesString(ispData.getBilling_datetime()) : req.getReplaceBy());
			ispResponse.setAvPfBillingPeriod(ispData.getAverage_power_factor_for_billing_period() != null ? String.valueOf(ispData.getAverage_power_factor_for_billing_period()) : req.getReplaceBy());
			ispResponse.setBillPOffDuration(String.valueOf(ispData.getBilling_power_off_duration_in_billing()!= null ? ispData.getBilling_power_off_duration_in_billing():req.getReplaceBy()));
			ispResponse.setBillPOnDuration(ispData.getBilling_power_on_duration_in_billing() != null ? String.valueOf(ispData.getBilling_power_on_duration_in_billing().intValue()) : req.getReplaceBy());
			ispResponse.setKvahExport(ispData.getCumulative_energy_kvah_export() != null ? String.valueOf(ispData.getCumulative_energy_kvah_export()) : req.getReplaceBy());
			ispResponse.setKvahImport(ispData.getCumulative_energy_kvah_import() != null ? String.valueOf(ispData.getCumulative_energy_kvah_import()) : req.getReplaceBy());
			ispResponse.setKvahTier1(ispData.getCumulative_energy_kvah_tier1() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier1()) : req.getReplaceBy());
			ispResponse.setKvahTier2(ispData.getCumulative_energy_kvah_tier2() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier2()) : req.getReplaceBy());
			ispResponse.setKvahTier3(ispData.getCumulative_energy_kvah_tier3() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier3()) : req.getReplaceBy());
			ispResponse.setKvahTier4(ispData.getCumulative_energy_kvah_tier4() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier4()) : req.getReplaceBy());
			ispResponse.setKvahTier5(ispData.getCumulative_energy_kvah_tier5() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier5()) : req.getReplaceBy());
			ispResponse.setKvahTier6(ispData.getCumulative_energy_kvah_tier6() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier6()) : req.getReplaceBy());
			ispResponse.setKvahTier7(ispData.getCumulative_energy_kvah_tier7() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier7()) : req.getReplaceBy());
			ispResponse.setKvahTier8(ispData.getCumulative_energy_kvah_tier8() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier8()) : req.getReplaceBy());
			ispResponse.setKwhExport(ispData.getCumulative_energy_kwh_export() != null ? String.valueOf(ispData.getCumulative_energy_kwh_export()) : req.getReplaceBy());
			ispResponse.setKwhImport(ispData.getCumulative_energy_kwh_import() != null ? String.valueOf(ispData.getCumulative_energy_kwh_import()) : req.getReplaceBy());
			ispResponse.setKwhTier1(ispData.getCumulative_energy_kwh_tier1() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier1()) : req.getReplaceBy());
			ispResponse.setKwhTier2(ispData.getCumulative_energy_kwh_tier2() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier2()) : req.getReplaceBy());
			ispResponse.setKwhTier3(ispData.getCumulative_energy_kwh_tier3() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier3()) : req.getReplaceBy());
			ispResponse.setKwhTier4(ispData.getCumulative_energy_kwh_tier4() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier4()) : req.getReplaceBy());
			ispResponse.setKwhTier5(ispData.getCumulative_energy_kwh_tier5() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier5()) : req.getReplaceBy());
			ispResponse.setKwhTier6(ispData.getCumulative_energy_kwh_tier6() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier6()) : req.getReplaceBy());
			ispResponse.setKwhTier7(ispData.getCumulative_energy_kwh_tier7() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier7()) : req.getReplaceBy());
			ispResponse.setKwhTier8(ispData.getCumulative_energy_kwh_tier8() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier8()) : req.getReplaceBy());
			ispResponse.setMdkva(ispData.getMaximum_demand_kva() != null ? String.valueOf(ispData.getMaximum_demand_kva()) : req.getReplaceBy());
			ispResponse.setMdkvaDatetime(ispData.getMaximum_demand_kva_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_datetime()) : req.getReplaceBy());
			ispResponse.setMdkw(ispData.getMaximum_demand_kw() != null ? String.valueOf(ispData.getMaximum_demand_kw()) : req.getReplaceBy());
			ispResponse.setMdkwDatetime(ispData.getMaximum_demand_kw_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_datetime()) : req.getReplaceBy());
			ispResponse.setMdasDatetime(ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : req.getReplaceBy());
			
			ispResponse.setMdkwTier1(ispData.getMaximum_demand_kw_tier1() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier1()) : req.getReplaceBy());
			ispResponse.setMdkwTier2(ispData.getMaximum_demand_kw_tier2() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier2()) : req.getReplaceBy());
			ispResponse.setMdkwTier3(ispData.getMaximum_demand_kw_tier3() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier3()) : req.getReplaceBy());
			ispResponse.setMdkwTier4(ispData.getMaximum_demand_kw_tier4() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier4()) : req.getReplaceBy());
			ispResponse.setMdkwTier5(ispData.getMaximum_demand_kw_tier5() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier5()) : req.getReplaceBy());
			ispResponse.setMdkwTier6(ispData.getMaximum_demand_kw_tier6() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier6()) : req.getReplaceBy());
			ispResponse.setMdkwTier7(ispData.getMaximum_demand_kw_tier7() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier7()) : req.getReplaceBy());
			ispResponse.setMdkwTier8(ispData.getMaximum_demand_kw_tier8() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier8()) : req.getReplaceBy());
			ispResponse.setMdkwTier1Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kw_tier1_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier1_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier2Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kw_tier2_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier2_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier3Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kw_tier3_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier3_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier4Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kw_tier4_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier4_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier5Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kw_tier5_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier5_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier6Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kw_tier6_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier6_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier7Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kw_tier7_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier7_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier8Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kw_tier8_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier8_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier1(ispData.getMaximum_demand_kva_tier1() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier1()) : req.getReplaceBy());
			ispResponse.setMdkvaTier2(ispData.getMaximum_demand_kva_tier2() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier2()) : req.getReplaceBy());
			ispResponse.setMdkvaTier3(ispData.getMaximum_demand_kva_tier3() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier3()) : req.getReplaceBy());
			ispResponse.setMdkvaTier4(ispData.getMaximum_demand_kva_tier4() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier4()) : req.getReplaceBy());
			ispResponse.setMdkvaTier5(ispData.getMaximum_demand_kva_tier5() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier5()) : req.getReplaceBy());
			ispResponse.setMdkvaTier6(ispData.getMaximum_demand_kva_tier6() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier6()) : req.getReplaceBy());
			ispResponse.setMdkvaTier7(ispData.getMaximum_demand_kva_tier7() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier7()) : req.getReplaceBy());
			ispResponse.setMdkvaTier8(ispData.getMaximum_demand_kva_tier8() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier8()) : req.getReplaceBy());
			ispResponse.setMdkvaTier1Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kva_tier1_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier1_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier2Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kva_tier2_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier2_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier3Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kva_tier3_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier3_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier4Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kva_tier4_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier4_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier5Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kva_tier5_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier5_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier6Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kva_tier6_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier6_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier7Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kva_tier7_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier7_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier8Datetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kva_tier8_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier8_date()) : req.getReplaceBy());
			
		
			ispResponseList.add(ispResponse);
		}
		LOG.info("Single phase: Billing Response Data Caster Added.");
	}
	
	public void prepareThreePhase(String outputList, List<BillingThreePhaseResponse> ispResponseList, MeterDataVisualizationReq req)
			throws Exception {
		LOG.info("Three phase: Billing Data Caster Called.");
		List<BillingDataThreePhase> singlePhaseData = new ArrayList<BillingDataThreePhase>();
		singlePhaseData = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<BillingDataThreePhase>>() {
				}); 
		LOG.info("Three phase: Billing Response Data Caster Adding...");

		for (BillingDataThreePhase ispData : singlePhaseData) {
			BillingThreePhaseResponse ispResponse = new BillingThreePhaseResponse();

			ispResponse.setDeviceSerialNumber(ispData.getDevice_serial_number() != null ? ispData.getDevice_serial_number() : req.getReplaceBy());
			ispResponse.setKvahExport(ispData.getCumulative_energy_kvah_export() != null ? String.valueOf(ispData.getCumulative_energy_kvah_export()) : req.getReplaceBy());
			ispResponse.setKvahImport(ispData.getCumulative_energy_kvah_import() != null ? String.valueOf(ispData.getCumulative_energy_kvah_import()) : req.getReplaceBy());
			ispResponse.setKvahTier1(ispData.getCumulative_energy_kvah_tier1() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier1()) : req.getReplaceBy());
			ispResponse.setKvahTier2(ispData.getCumulative_energy_kvah_tier2() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier2()) : req.getReplaceBy());
			ispResponse.setKvahTier3(ispData.getCumulative_energy_kvah_tier3() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier3()) : req.getReplaceBy());
			ispResponse.setKvahTier4(ispData.getCumulative_energy_kvah_tier4() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier4()) : req.getReplaceBy());
			ispResponse.setKvahTier5(ispData.getCumulative_energy_kvah_tier5() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier5()) : req.getReplaceBy());
			ispResponse.setKvahTier6(ispData.getCumulative_energy_kvah_tier6() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier6()) : req.getReplaceBy());
			ispResponse.setKvahTier7(ispData.getCumulative_energy_kvah_tier7() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier7()) : req.getReplaceBy());
			ispResponse.setKvahTier8(ispData.getCumulative_energy_kvah_tier8() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier8()) : req.getReplaceBy());
			ispResponse.setKvarhQ1(ispData.getCumulative_energy_kvarh_q1() != null ? String.valueOf(ispData.getCumulative_energy_kvarh_q1()) : req.getReplaceBy());
			ispResponse.setKvarhQ2(ispData.getCumulative_energy_kvarh_q2() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q2()) : req.getReplaceBy());
			ispResponse.setKvarhQ3(ispData.getCumulative_energy_kvarh_q3() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q3()) : req.getReplaceBy());
			ispResponse.setKvarhQ4(ispData.getCumulative_energy_kvarh_q4() != null ? String.valueOf(ispData.getCumulative_energy_kvarh_q4()) : req.getReplaceBy());
			ispResponse.setKwhExport(ispData.getCumulative_energy_kwh_export() != null ? String.valueOf(ispData.getCumulative_energy_kwh_export()) : req.getReplaceBy());
			ispResponse.setKwhImport(ispData.getCumulative_energy_kwh_import() != null ? String.valueOf(ispData.getCumulative_energy_kwh_import()) : req.getReplaceBy());
			ispResponse.setKwhTier1(ispData.getCumulative_energy_kwh_tier1() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier1()) : req.getReplaceBy());
			ispResponse.setKwhTier2(ispData.getCumulative_energy_kwh_tier2() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier2()) : req.getReplaceBy());
			ispResponse.setKwhTier3(ispData.getCumulative_energy_kwh_tier3() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier3()) : req.getReplaceBy());
			ispResponse.setKwhTier4(ispData.getCumulative_energy_kwh_tier4() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier4()) : req.getReplaceBy());
			ispResponse.setKwhTier5(ispData.getCumulative_energy_kwh_tier5() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier5()) : req.getReplaceBy());
			ispResponse.setKwhTier6(ispData.getCumulative_energy_kwh_tier6() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier6()) : req.getReplaceBy());
			ispResponse.setKwhTier7(ispData.getCumulative_energy_kwh_tier7() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier7()) : req.getReplaceBy());
			ispResponse.setKwhTier8(ispData.getCumulative_energy_kwh_tier8() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier8()) : req.getReplaceBy());
			ispResponse.setMdkva(ispData.getMaximum_demand_kva() != null ? String.valueOf(ispData.getMaximum_demand_kva()) : req.getReplaceBy());
			ispResponse.setMdkvaDatetime(ispData.getMaximum_demand_kva_date() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier1(ispData.getMaximum_demand_kva_tier1() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier1()) : req.getReplaceBy());
			ispResponse.setMdkvaTier1Datetime(ispData.getMaximum_demand_kva_tier1_date() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier1_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier2(ispData.getMaximum_demand_kva_tier2() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier2()) : req.getReplaceBy());
			ispResponse.setMdkvaTier2Datetime(ispData.getMaximum_demand_kva_tier2_date() != null ?  dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier2_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier3(ispData.getMaximum_demand_kva_tier3() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier3()) : req.getReplaceBy());
			ispResponse.setMdkvaTier3Datetime(ispData.getMaximum_demand_kva_tier3_date() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier3_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier4(ispData.getMaximum_demand_kva_tier4() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier4()) : req.getReplaceBy());
			ispResponse.setMdkvaTier4Datetime(ispData.getMaximum_demand_kva_tier4_date() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier4_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier5(ispData.getMaximum_demand_kva_tier5() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier5()) : req.getReplaceBy());
			ispResponse.setMdkvaTier5Datetime(ispData.getMaximum_demand_kva_tier5_date() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier5_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier6(ispData.getMaximum_demand_kva_tier6() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier6()) : req.getReplaceBy());
			ispResponse.setMdkvaTier6Datetime(ispData.getMaximum_demand_kva_tier6_date() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier6_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier7(ispData.getMaximum_demand_kva_tier7() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier7()) : req.getReplaceBy());
			ispResponse.setMdkvaTier7Datetime(ispData.getMaximum_demand_kva_tier7_date() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier7_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier8(ispData.getMaximum_demand_kva_tier8() != null ? String.valueOf(ispData.getMaximum_demand_kva_tier8()) : req.getReplaceBy());
			ispResponse.setMdkvaTier8Datetime(ispData.getMaximum_demand_kva_tier8_date() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_tier8_date()) : req.getReplaceBy());
			ispResponse.setMdkw(ispData.getMaximum_demand_kw() != null ? String.valueOf(ispData.getMaximum_demand_kw()) : req.getReplaceBy());
			ispResponse.setMdkwDatetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kw_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier1(ispData.getMaximum_demand_kw_tier1() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier1()) : req.getReplaceBy());
			ispResponse.setMdkwTier1Datetime(ispData.getMaximum_demand_kw_tier1_date()!= null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier1_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier2(ispData.getMaximum_demand_kw_tier2() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier2()) : req.getReplaceBy());
			ispResponse.setMdkwTier2Datetime(ispData.getMaximum_demand_kw_tier2_date()!= null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier2_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier3(ispData.getMaximum_demand_kw_tier3() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier3()) : req.getReplaceBy());
			ispResponse.setMdkwTier3Datetime(ispData.getMaximum_demand_kw_tier3_date()!= null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier3_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier4(ispData.getMaximum_demand_kw_tier4() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier4()) : req.getReplaceBy());
			ispResponse.setMdkwTier4Datetime(ispData.getMaximum_demand_kw_tier4_date()!= null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier4_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier5(ispData.getMaximum_demand_kw_tier5() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier5()) : req.getReplaceBy());
			ispResponse.setMdkwTier5Datetime(ispData.getMaximum_demand_kw_tier5_date()!= null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier5_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier6(ispData.getMaximum_demand_kw_tier6() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier6()) : req.getReplaceBy());
			ispResponse.setMdkwTier6Datetime(ispData.getMaximum_demand_kw_tier6_date()!= null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier6_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier7(ispData.getMaximum_demand_kw_tier7() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier7()) : req.getReplaceBy());
			ispResponse.setMdkwTier7Datetime(ispData.getMaximum_demand_kw_tier7_date()!= null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier7_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier8(ispData.getMaximum_demand_kw_tier8() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier8()) : req.getReplaceBy());
			ispResponse.setMdkwTier8Datetime(ispData.getMaximum_demand_kw_tier8_date() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_tier8_date()) : req.getReplaceBy());
			ispResponse.setMdasDatetime(ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : req.getReplaceBy());
			ispResponse.setMeterDatetime(ispData.getMeter_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMeter_datetime()) : req.getReplaceBy());
			ispResponse.setpOnDurationMins(ispData.getPower_on_duration_mins() != null ? String.valueOf(ispData.getPower_on_duration_mins()) : 
				        (ispData.getPower_on_duration_minutes() != null ? String.valueOf(ispData.getPower_on_duration_minutes()) : req.getReplaceBy()));
			ispResponse.setsPFBillingPeriod(ispData.getSystem_power_factor_billing_period() != null ? String.valueOf(ispData.getSystem_power_factor_billing_period()) : req.getReplaceBy());

			ispResponseList.add(ispResponse);
		}
		LOG.info("Three phase: Billing Response Data Caster Added.");
	}
	
	public void prepareCT(String outputList, List<BillingCTResponse> ispResponseList, MeterDataVisualizationReq req)
			throws Exception {
		LOG.info("CT: Billing Data Caster Called.");
		List<BillingDataCT> ctData = new ArrayList<BillingDataCT>();
		ctData = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<BillingDataCT>>() {
				});
		LOG.info("CT:Billing Response Data Caster Adding.");

		for (BillingDataCT ispData : ctData) {
			BillingCTResponse ispResponse = new BillingCTResponse();

			ispResponse.setDeviceSerialNumber(ispData.getDevice_serial_number() != null ? ispData.getDevice_serial_number() : req.getReplaceBy());
			ispResponse.setMeterDatetime(ispData.getMeter_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMeter_datetime()) : req.getReplaceBy());
			ispResponse.setMdasDatetime(ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : req.getReplaceBy());
			ispResponse.setKvahImport(ispData.getCumulative_energy_kvah_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_import())) : req.getReplaceBy());
			ispResponse.setKvahExport(ispData.getCumulative_energy_kvah_export() != null ? String.valueOf(ispData.getCumulative_energy_kvah_export()) : req.getReplaceBy());
			ispResponse.setKvahTier1(ispData.getCumulative_energy_kvah_tier1() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_tier1())) : req.getReplaceBy());
			ispResponse.setKvahTier2(ispData.getCumulative_energy_kvah_tier2() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_tier2())) : req.getReplaceBy());
			ispResponse.setKvahTier3(ispData.getCumulative_energy_kvah_tier3() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_tier3())) : req.getReplaceBy());
			ispResponse.setKvahTier4(ispData.getCumulative_energy_kvah_tier4() !=null ? String.valueOf(ispData.getCumulative_energy_kvah_tier4()) : req.getReplaceBy());
			ispResponse.setKvahTier5(ispData.getCumulative_energy_kvah_tier5() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier5()) : req.getReplaceBy());
			ispResponse.setKvahTier6(ispData.getCumulative_energy_kvah_tier6() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier6()) : req.getReplaceBy());
			ispResponse.setKvahTier7(ispData.getCumulative_energy_kvah_tier7() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier7()) : req.getReplaceBy());
			ispResponse.setKvahTier8(ispData.getCumulative_energy_kvah_tier8() != null ? String.valueOf(ispData.getCumulative_energy_kvah_tier8()) : req.getReplaceBy());
			ispResponse.setKwhImport(ispData.getCumulative_energy_kwh_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_import())) : req.getReplaceBy());
			ispResponse.setKwhExport(ispData.getCumulative_energy_kwh_export() != null ? String.valueOf(ispData.getCumulative_energy_kwh_export()) : req.getReplaceBy());
			ispResponse.setKwhTier1(ispData.getCumulative_energy_kwh_tier1() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_tier1())) : req.getReplaceBy());
			ispResponse.setKwhTier2(ispData.getCumulative_energy_kwh_tier2() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_tier2())) : req.getReplaceBy());
			ispResponse.setKwhTier3(ispData.getCumulative_energy_kwh_tier3() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_tier3())) : req.getReplaceBy());
			ispResponse.setKwhTier4(ispData.getCumulative_energy_kwh_tier4() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier4()) : req.getReplaceBy());
			ispResponse.setKwhTier5(ispData.getCumulative_energy_kwh_tier5() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier5()) : req.getReplaceBy());
			ispResponse.setKwhTier6(ispData.getCumulative_energy_kwh_tier6() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier6()) : req.getReplaceBy());
			ispResponse.setKwhTier7(ispData.getCumulative_energy_kwh_tier7() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier7()) : req.getReplaceBy());
			ispResponse.setKwhTier8(ispData.getCumulative_energy_kwh_tier8() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier8()) : req.getReplaceBy());
			ispResponse.setKvarhQ1(ispData.getCumulative_energy_kvarh_q1() != null ? String.valueOf(ispData.getCumulative_energy_kvarh_q1()) : req.getReplaceBy());
			ispResponse.setKvarhQ2(ispData.getCumulative_energy_kvarh_q2() != null ? String.valueOf(ispData.getCumulative_energy_kvarh_q2()) : req.getReplaceBy());
			ispResponse.setKvarhQ3(ispData.getCumulative_energy_kvarh_q3() != null ? String.valueOf(ispData.getCumulative_energy_kvarh_q3()) : req.getReplaceBy());
			ispResponse.setKvarhQ4(ispData.getCumulative_energy_kvarh_q4() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q4())) : req.getReplaceBy());
			ispResponse.setMdkva(ispData.getMaximum_demand_kva() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kva())) : req.getReplaceBy());
			ispResponse.setMdkvaDatetime(ispData.getMaximum_demand_kva_date() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date()) : req.getReplaceBy());
			ispResponse.setMdkvaTier1(ispData.getMaximum_demand_kva_tier1() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kva_tier1())) : req.getReplaceBy());
			ispResponse.setMdkvaTier1Datetime(ispData.getMaximum_demand_kva_date_export_tier1() != null ?  dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier1()) : req.getReplaceBy());
			ispResponse.setMdkvaTier2(ispData.getMaximum_demand_kva_tier2() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kva_tier2())) : req.getReplaceBy());
			ispResponse.setMdkvaTier2Datetime(ispData.getMaximum_demand_kva_date_export_tier2() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier2()) : req.getReplaceBy());
			ispResponse.setMdkvaTier3(ispData.getMaximum_demand_kva_tier3() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kva_tier3())) : req.getReplaceBy());
			ispResponse.setMdkvaTier3Datetime(ispData.getMaximum_demand_kva_date_export_tier3() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier3()) : req.getReplaceBy());
			ispResponse.setMdkvaTier4(ispData.getMaximum_demand_kva_tier4() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kva_tier4())) : req.getReplaceBy());
			ispResponse.setMdkvaTier4Datetime(ispData.getMaximum_demand_kva_date_export_tier4() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier4()) : req.getReplaceBy());
			ispResponse.setMdkvaTier5(ispData.getMaximum_demand_kva_tier5() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kva_tier5())) : req.getReplaceBy());
			ispResponse.setMdkvaTier5Datetime(ispData.getMaximum_demand_kva_date_export_tier5() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier5()) : req.getReplaceBy());
			ispResponse.setMdkvaTier6(ispData.getMaximum_demand_kva_tier6() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kva_tier6())) : req.getReplaceBy());
			ispResponse.setMdkvaTier6Datetime(ispData.getMaximum_demand_kva_date_export_tier6() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier6()) : req.getReplaceBy());
			ispResponse.setMdkvaTier7(ispData.getMaximum_demand_kva_tier7() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kva_tier7())) : req.getReplaceBy());
			ispResponse.setMdkvaTier7Datetime(ispData.getMaximum_demand_kva_date_export_tier7() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier7()) : req.getReplaceBy());
			ispResponse.setMdkvaTier8(ispData.getMaximum_demand_kva_tier8() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kva_tier8())) : req.getReplaceBy());
			ispResponse.setMdkvaTier8Datetime(ispData.getMaximum_demand_kva_date_export_tier8() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier8()) : req.getReplaceBy());
			ispResponse.setMdkw(ispData.getMaximum_demand_kw() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kw())) : req.getReplaceBy());
			ispResponse.setMdkwDatetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kw_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date()) : req.getReplaceBy());
			ispResponse.setMdkwTier1(ispData.getMaximum_demand_kw_tier1() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kw_tier1())) : req.getReplaceBy());
			ispResponse.setMdkwTier1Datetime(ispData.getMaximum_demand_kw_date_export_tier1() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier1()) : req.getReplaceBy());
			ispResponse.setMdkwTier2(ispData.getMaximum_demand_kw_tier2() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier2()) : req.getReplaceBy());
			ispResponse.setMdkwTier2Datetime(ispData.getMaximum_demand_kw_date_export_tier2() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier2()) : req.getReplaceBy());
			ispResponse.setMdkwTier3(ispData.getMaximum_demand_kw_tier3() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kw_tier3())) : req.getReplaceBy());
			ispResponse.setMdkwTier3Datetime(ispData.getMaximum_demand_kw_date_export_tier3() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier3()) : req.getReplaceBy());
			ispResponse.setMdkwTier4(ispData.getMaximum_demand_kw_tier4() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier4()) : req.getReplaceBy());
			ispResponse.setMdkwTier4Datetime(ispData.getMaximum_demand_kw_date_export_tier4() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier4()) : req.getReplaceBy());
			ispResponse.setMdkwTier5(ispData.getMaximum_demand_kw_tier5() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier5()) : req.getReplaceBy());
			ispResponse.setMdkwTier5Datetime(ispData.getMaximum_demand_kw_date_export_tier5() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier5()) : req.getReplaceBy());
			ispResponse.setMdkwTier6(ispData.getMaximum_demand_kw_tier6() != null ?  String.valueOf(ispData.getMaximum_demand_kw_tier6()) : req.getReplaceBy());
			ispResponse.setMdkwTier6Datetime(ispData.getMaximum_demand_kw_date_export_tier6() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier6()) : req.getReplaceBy());
			ispResponse.setMdkwTier7(ispData.getMaximum_demand_kw_tier7() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier7()) : req.getReplaceBy());
			ispResponse.setMdkwTier7Datetime(ispData.getMaximum_demand_kw_date_export_tier7() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier7()) : req.getReplaceBy());
			ispResponse.setMdkwTier8(ispData.getMaximum_demand_kw_tier8() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier8()) : req.getReplaceBy());
			ispResponse.setMdkwTier8Datetime(ispData.getMaximum_demand_kw_date_export_tier8() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier8()) : req.getReplaceBy());
			ispResponse.setkVarhLag(ispData.getCumulative_energy_kvarh_lag() != null ? String.valueOf(ispData.getCumulative_energy_kvarh_lag()) : req.getReplaceBy());
			ispResponse.setKwhExportT1(ispData.getCumulative_energy_kwh_export_tier1() != null ? String.valueOf(ispData.getCumulative_energy_kwh_export_tier1()) : req.getReplaceBy());
			ispResponse.setKwhExportT2(ispData.getCumulative_energy_kwh_export_tier2() != null ? String.valueOf(ispData.getCumulative_energy_kwh_export_tier2()) : req.getReplaceBy());
			ispResponse.setKwhExportT3(ispData.getCumulative_energy_kwh_export_tier3() != null ? String.valueOf(ispData.getCumulative_energy_kwh_export_tier3()) : req.getReplaceBy());
			ispResponse.setKwhExportT4(ispData.getCumulative_energy_kwh_export_tier4() != null ? String.valueOf(ispData.getCumulative_energy_kwh_export_tier4()) : req.getReplaceBy());
			ispResponse.setKwhExportT5(ispData.getCumulative_energy_kwh_export_tier5() != null ? String.valueOf(ispData.getCumulative_energy_kwh_export_tier5()) : req.getReplaceBy());
			ispResponse.setKwhExportT6(ispData.getCumulative_energy_kwh_export_tier6() != null ? String.valueOf(ispData.getCumulative_energy_kwh_export_tier6()) : req.getReplaceBy());
			ispResponse.setKwhExportT7(ispData.getCumulative_energy_kwh_export_tier7() != null ? String.valueOf(ispData.getCumulative_energy_kwh_export_tier7()) : req.getReplaceBy());
			ispResponse.setKwhExportT8(ispData.getCumulative_energy_kwh_export_tier8() != null ? String.valueOf(ispData.getCumulative_energy_kwh_export_tier8()) : req.getReplaceBy());
			ispResponse.setKvahExportT1(ispData.getCumulative_energy_kvah_export_tier1() != null ? String.valueOf(ispData.getCumulative_energy_kvah_export_tier1()) : req.getReplaceBy());
			ispResponse.setKvahExportT2(ispData.getCumulative_energy_kvah_export_tier2() != null ? String.valueOf(ispData.getCumulative_energy_kvah_export_tier2()) : req.getReplaceBy());
			ispResponse.setKvahExportT3(ispData.getCumulative_energy_kvah_export_tier3() != null ? String.valueOf(ispData.getCumulative_energy_kvah_export_tier3()) : req.getReplaceBy());
			ispResponse.setKvahExportT4(ispData.getCumulative_energy_kvah_export_tier4() != null ? String.valueOf(ispData.getCumulative_energy_kvah_export_tier4()) : req.getReplaceBy());
			ispResponse.setKvahExportT5(ispData.getCumulative_energy_kvah_export_tier5() != null ? String.valueOf(ispData.getCumulative_energy_kvah_export_tier5()) : req.getReplaceBy());
			ispResponse.setKvahExportT6(ispData.getCumulative_energy_kvah_export_tier6() != null ? String.valueOf(ispData.getCumulative_energy_kvah_export_tier6()) : req.getReplaceBy());
			ispResponse.setKvahExportT7(ispData.getCumulative_energy_kvah_export_tier7() != null ? String.valueOf(ispData.getCumulative_energy_kvah_export_tier7()) : req.getReplaceBy());
			ispResponse.setKvahExportT8(ispData.getCumulative_energy_kvah_export_tier8() != null ? String.valueOf(ispData.getCumulative_energy_kvah_export_tier8()) : req.getReplaceBy());
			ispResponse.setMdkwExport(ispData.getMaximum_demand_kw_export() != null ? String.valueOf(ispData.getCumulative_energy_kvah_export_tier8()) : req.getReplaceBy());
			ispResponse.setMdkwExportDatetime(ispData.getMaximum_demand_kw_date() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date()) : req.getReplaceBy());
			ispResponse.setMdkwExportT1(ispData.getMaximum_demand_kw_tier1() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier1()) : req.getReplaceBy());
			ispResponse.setMdkwExportT2(ispData.getMaximum_demand_kw_tier2() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier2()) : req.getReplaceBy());
			ispResponse.setMdkwExportT3(ispData.getMaximum_demand_kw_tier3() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier3()) : req.getReplaceBy());
			ispResponse.setMdkwExportT4(ispData.getMaximum_demand_kw_tier4() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier4()) : req.getReplaceBy());
			ispResponse.setMdkwExportT5(ispData.getMaximum_demand_kw_tier5() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier5()) : req.getReplaceBy());
			ispResponse.setMdkwExportT6(ispData.getMaximum_demand_kw_tier6() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier6()) : req.getReplaceBy());
			ispResponse.setMdkwExportT7(ispData.getMaximum_demand_kw_tier7() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier7()) : req.getReplaceBy());
			ispResponse.setMdkwExportT8(ispData.getMaximum_demand_kw_tier8() != null ? String.valueOf(ispData.getMaximum_demand_kw_tier8()) : req.getReplaceBy());
			ispResponse.setMdkwExport(ispData.getMaximum_demand_kw_export() != null ? String.valueOf(ispData.getMaximum_demand_kw_export()) : req.getReplaceBy());
			ispResponse.setMdkwExportDatetime(!CommonUtils.checkNineteenSeventyDate(ispData.getMaximum_demand_kw_date()) ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date()) : req.getReplaceBy());
			ispResponse.setMdkwExportT1Datetime(ispData.getMaximum_demand_kw_date_export_tier1() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier1()) : req.getReplaceBy());
			ispResponse.setMdkwExportT2Datetime(ispData.getMaximum_demand_kw_date_export_tier2() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier2()) : req.getReplaceBy());
			ispResponse.setMdkwExportT3Datetime(ispData.getMaximum_demand_kw_date_export_tier3() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier3()) : req.getReplaceBy());
			ispResponse.setMdkwExportT4Datetime(ispData.getMaximum_demand_kw_date_export_tier4() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier4()) : req.getReplaceBy());
			ispResponse.setMdkwExportT5Datetime(ispData.getMaximum_demand_kw_date_export_tier5() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier5()) : req.getReplaceBy());
			ispResponse.setMdkwExportT6Datetime(ispData.getMaximum_demand_kw_date_export_tier6() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier6()) : req.getReplaceBy());
			ispResponse.setMdkwExportT7Datetime(ispData.getMaximum_demand_kw_date_export_tier7() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier7()) : req.getReplaceBy());
			ispResponse.setMdkwExportT8Datetime(ispData.getMaximum_demand_kw_date_export_tier8() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_date_export_tier8()) : req.getReplaceBy());
			ispResponse.setMdkvaExport(ispData.getMaximum_demand_kva_export() != null ? String.valueOf(ispData.getMaximum_demand_kva_export()) : req.getReplaceBy());
			ispResponse.setMdkvaExportDatetime(ispData.getMaximum_demand_kva_date() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT1(ispData.getMaximum_demand_kva_export_tier1() != null ? String.valueOf(ispData.getMaximum_demand_kva_export_tier1()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT2(ispData.getMaximum_demand_kva_export_tier2() != null ? String.valueOf(ispData.getMaximum_demand_kva_export_tier2()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT3(ispData.getMaximum_demand_kva_export_tier3() != null ? String.valueOf(ispData.getMaximum_demand_kva_export_tier3()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT4(ispData.getMaximum_demand_kva_export_tier4() != null ? String.valueOf(ispData.getMaximum_demand_kva_export_tier4()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT5(ispData.getMaximum_demand_kva_export_tier5() != null ? String.valueOf(ispData.getMaximum_demand_kva_export_tier5()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT6(ispData.getMaximum_demand_kva_export_tier6() != null ? String.valueOf(ispData.getMaximum_demand_kva_export_tier6()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT7(ispData.getMaximum_demand_kva_export_tier7() != null ? String.valueOf(ispData.getMaximum_demand_kva_export_tier7()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT8(ispData.getMaximum_demand_kva_export_tier8() != null ? String.valueOf(ispData.getMaximum_demand_kva_export_tier8()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT1Datetime(ispData.getMaximum_demand_kva_date_export_tier1() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier1()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT2Datetime(ispData.getMaximum_demand_kva_date_export_tier2() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier2()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT3Datetime(ispData.getMaximum_demand_kva_date_export_tier3() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier3()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT4Datetime(ispData.getMaximum_demand_kva_date_export_tier4() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier4()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT5Datetime(ispData.getMaximum_demand_kva_date_export_tier5() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier5()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT6Datetime(ispData.getMaximum_demand_kva_date_export_tier6() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier6()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT7Datetime(ispData.getMaximum_demand_kva_date_export_tier7() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier7()) : req.getReplaceBy());
			ispResponse.setMdkvaExportT8Datetime(ispData.getMaximum_demand_kva_date_export_tier8() != null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_date_export_tier8()) : req.getReplaceBy());
			ispResponse.setTamperCount(ispData.getTamper_count() != null ? String.valueOf(ispData.getTamper_count()) : req.getReplaceBy());
			ispResponse.setpOnDurationMins(ispData.getPower_on_duration_mins() != null ? String.valueOf(ispData.getPower_on_duration_mins()) : 
		        (ispData.getPower_on_duration_minutes() != null ? String.valueOf(ispData.getPower_on_duration_minutes()) : req.getReplaceBy()));
			ispResponse.setsPFBillingPeriod(ispData.getSystem_power_factor_billing_period() != null ? String.valueOf(ispData.getSystem_power_factor_billing_period()) : req.getReplaceBy());

			ispResponseList.add(ispResponse);
		}
		LOG.info("CT: Billing Response Data Caster Added.");
	}


}
