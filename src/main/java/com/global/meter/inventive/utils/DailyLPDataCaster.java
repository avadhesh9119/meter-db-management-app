package com.global.meter.inventive.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DailyLoadProfileCT;
import com.global.meter.business.model.DailyLoadProfileSinglePhase;
import com.global.meter.business.model.DailyLoadProfileThreePhase;
import com.global.meter.inventive.models.DailyLPCTResponse;
import com.global.meter.inventive.models.DailyLPSinglePhaseResponse;
import com.global.meter.inventive.models.DailyLPThreePhaseResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class DailyLPDataCaster {
	private static final Logger LOG = LoggerFactory.getLogger(InstantDevicesInfoCaster.class);
	
	@Autowired
	DateConverter dateConverter;

	public void prepareSinglePhase(String outputList, List<DailyLPSinglePhaseResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception{
		LOG.info("Single Phase: Daily LP Data Caster called.");
		List<DailyLoadProfileSinglePhase> singlePhaseData = new ArrayList<DailyLoadProfileSinglePhase>();
		singlePhaseData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<DailyLoadProfileSinglePhase>>() {});
		LOG.info("Single Phase: Daily LP Data Response Caster Adding.");
	
		for (DailyLoadProfileSinglePhase ispData : singlePhaseData) {
			DailyLPSinglePhaseResponse ispResponse = new DailyLPSinglePhaseResponse();
			
			ispResponse.setHesDatetime(ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : req.getReplaceBy());

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

			ispResponse.setkWhExport(ispData.getCumulative_energy_kwh_export() != null ? String.valueOf(ispData.getCumulative_energy_kwh_export()) : req.getReplaceBy());
			ispResponse.setkWhImport(ispData.getCumulative_energy_kwh_import() != null ? String.valueOf(ispData.getCumulative_energy_kwh_import()) : req.getReplaceBy());
			ispResponse.setKwhTier1(ispData.getCumulative_energy_kwh_tier1() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier1()) : req.getReplaceBy());
			ispResponse.setKwhTier2(ispData.getCumulative_energy_kwh_tier2() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier2()) : req.getReplaceBy());
			ispResponse.setKwhTier3(ispData.getCumulative_energy_kwh_tier3() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier3()) : req.getReplaceBy());
			ispResponse.setKwhTier4(ispData.getCumulative_energy_kwh_tier4() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier4()) : req.getReplaceBy());
			ispResponse.setKwhTier5(ispData.getCumulative_energy_kwh_tier5() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier5()) : req.getReplaceBy());
			ispResponse.setKwhTier6(ispData.getCumulative_energy_kwh_tier6() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier6()) : req.getReplaceBy());
			ispResponse.setKwhTier7(ispData.getCumulative_energy_kwh_tier7() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier7()) : req.getReplaceBy());
			ispResponse.setKwhTier8(ispData.getCumulative_energy_kwh_tier8() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier8()) : req.getReplaceBy());

			ispResponse.setDatetime(ispData.getDatetime() != null ? dateConverter.convertDateToHesString(ispData.getDatetime()) : req.getReplaceBy());
			ispResponse.setMeterNumber(ispData.getDevice_serial_number() != null ? ispData.getDevice_serial_number() : req.getReplaceBy());

			ispResponse.setMdkva(ispData.getMaximum_demand_kva() !=null ? String.valueOf(ispData.getMaximum_demand_kva()) : req.getReplaceBy());
			ispResponse.setMdkvaDatetime(ispData.getMaximum_demand_kva_datetime() !=null ?dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_datetime()) : req.getReplaceBy());
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
		
			ispResponse.setMdkw(ispData.getMaximum_demand_kw() !=null ? String.valueOf(ispData.getMaximum_demand_kw()) : req.getReplaceBy());
			ispResponse.setMdkwDatetime(ispData.getMaximum_demand_kw_datetime() !=null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_datetime()) : req.getReplaceBy());
		
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

			ispResponseList.add(ispResponse);
		}
		LOG.info("Single Phase: Daily LP Data Response Caster Added.");
	}
	public void prepareThreePhase(String outputList, List<DailyLPThreePhaseResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception{
		LOG.info("Three Phase: Daily LP Data Caster called.");
		List<DailyLoadProfileThreePhase> threePhaseData = new ArrayList<DailyLoadProfileThreePhase>();
		threePhaseData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<DailyLoadProfileThreePhase>>() {});
		LOG.info("Three Phase: Daily LP Data Response Caster Adding.");
	
		for (DailyLoadProfileThreePhase ispData : threePhaseData) {
			DailyLPThreePhaseResponse ispResponse = new DailyLPThreePhaseResponse();
			
			ispResponse.setHesDatetime(ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : req.getReplaceBy());
			ispResponse.setKvahExport(ispData.getCumulative_energy_kvah_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_export())) : req.getReplaceBy());
			ispResponse.setKvahImport(ispData.getCumulative_energy_kvah_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_import())) : req.getReplaceBy());		
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

			ispResponse.setkWhExport(ispData.getCumulative_energy_kwh_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_export())) : req.getReplaceBy());
			ispResponse.setkWhImport(ispData.getCumulative_energy_kwh_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_import())) : req.getReplaceBy());
			ispResponse.setKwhTier1(ispData.getCumulative_energy_kwh_tier1() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier1()) : req.getReplaceBy());
			ispResponse.setKwhTier2(ispData.getCumulative_energy_kwh_tier2() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier2()) : req.getReplaceBy());
			ispResponse.setKwhTier3(ispData.getCumulative_energy_kwh_tier3() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier3()) : req.getReplaceBy());
			ispResponse.setKwhTier4(ispData.getCumulative_energy_kwh_tier4() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier4()) : req.getReplaceBy());
			ispResponse.setKwhTier5(ispData.getCumulative_energy_kwh_tier5() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier5()) : req.getReplaceBy());
			ispResponse.setKwhTier6(ispData.getCumulative_energy_kwh_tier6() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier6()) : req.getReplaceBy());
			ispResponse.setKwhTier7(ispData.getCumulative_energy_kwh_tier7() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier7()) : req.getReplaceBy());
			ispResponse.setKwhTier8(ispData.getCumulative_energy_kwh_tier8() != null ? String.valueOf(ispData.getCumulative_energy_kwh_tier8()) : req.getReplaceBy());

			ispResponse.setDatetime(ispData.getDatetime() != null ? dateConverter.convertDateToHesString(ispData.getDatetime()) : req.getReplaceBy());
			ispResponse.setMeterNumber(ispData.getDevice_serial_number() != null ? ispData.getDevice_serial_number() : req.getReplaceBy());		
			ispResponse.setKvarh_q1(ispData.getCumulative_energy_kvarh_q1() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q1()) : req.getReplaceBy());
			ispResponse.setKvarh_q2(ispData.getCumulative_energy_kvarh_q2() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q2()) : req.getReplaceBy());
			ispResponse.setKvarh_q3(ispData.getCumulative_energy_kvarh_q3() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q3()) : req.getReplaceBy());
			ispResponse.setKvarh_q4(ispData.getCumulative_energy_kvarh_q4() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q4()) : req.getReplaceBy());
			ispResponse.setMdkva(ispData.getMaximum_demand_kva() !=null ? String.valueOf(ispData.getMaximum_demand_kva()) : req.getReplaceBy());
			ispResponse.setMdkvaDatetime(ispData.getMaximum_demand_kva_datetime() !=null ?dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_datetime()) : req.getReplaceBy());
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

			ispResponse.setMdkw(ispData.getMaximum_demand_kw() !=null ? String.valueOf(ispData.getMaximum_demand_kw()) : req.getReplaceBy());
			ispResponse.setMdkwDatetime(ispData.getMaximum_demand_kw_datetime() !=null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_datetime()) : req.getReplaceBy());
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

			ispResponse.setMdkwExport(ispData.getMaximum_demand_kw_export() !=null ? String.valueOf(ispData.getMaximum_demand_kw_export()) : req.getReplaceBy());
			ispResponse.setMdkwExportDatetime(ispData.getMaximum_demand_kw_export_datetime() !=null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_export_datetime()) : req.getReplaceBy());
			
			
			
			ispResponseList.add(ispResponse);
		}
		LOG.info("Three Phase: Daily LP Data Response Caster Added.");
	}
	
	public void prepareCT(String outputList, List<DailyLPCTResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception{
		LOG.info("CT: Daily LP Data Caster called.");
		List<DailyLoadProfileCT> ctPhaseData = new ArrayList<DailyLoadProfileCT>();
		ctPhaseData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<DailyLoadProfileCT>>() {});
		LOG.info("CT: Daily LP Data Response Caster Adding.");
	
		for (DailyLoadProfileCT ispData : ctPhaseData) {
			DailyLPCTResponse ispResponse = new DailyLPCTResponse();
			
			ispResponse.setHesDatetime(ispData.getMdas_datetime() !=null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : req.getReplaceBy());
			ispResponse.setKvahExport(ispData.getCumulative_energy_kvah_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_export())) : req.getReplaceBy());
			ispResponse.setKvahImport(ispData.getCumulative_energy_kvah_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_import())) : req.getReplaceBy());		
			ispResponse.setkWhExport(ispData.getCumulative_energy_kwh_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_export())) : req.getReplaceBy());
			ispResponse.setkWhImport(ispData.getCumulative_energy_kwh_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_import())) : req.getReplaceBy());
			ispResponse.setDatetime(ispData.getDatetime() !=null ? dateConverter.convertDateToHesString(ispData.getDatetime()) : req.getReplaceBy());
			ispResponse.setMeterNumber(ispData.getDevice_serial_number() !=null ? ispData.getDevice_serial_number() : req.getReplaceBy());			
			ispResponse.setKvarh_q1(ispData.getCumulative_energy_kvarh_q1() !=null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q1())) : req.getReplaceBy());
			ispResponse.setKvarh_q2(ispData.getCumulative_energy_kvarh_q2() !=null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q2())) : req.getReplaceBy());
			ispResponse.setKvarh_q3(ispData.getCumulative_energy_kvarh_q3() !=null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q3())) : req.getReplaceBy());
			ispResponse.setKvarh_q4(ispData.getCumulative_energy_kvarh_q4() !=null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvarh_q4())) : req.getReplaceBy());
			ispResponse.setMdkva(ispData.getMaximum_demand_kva() !=null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kva())) : req.getReplaceBy());
			ispResponse.setMdkvaDatetime(ispData.getMaximum_demand_kva_datetime() !=null ?dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_datetime()) : req.getReplaceBy());
			ispResponse.setMdkw(ispData.getMaximum_demand_kw() !=null ? String.valueOf(CommonUtils.decimalFormat(ispData.getMaximum_demand_kw())) : req.getReplaceBy());
			ispResponse.setMdkwDatetime(ispData.getMaximum_demand_kw_datetime() !=null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_datetime()) : req.getReplaceBy());
			ispResponse.setNoSupply(ispData.getNo_supply() !=null ? ispData.getNo_supply() : req.getReplaceBy());
			ispResponse.setNoLoad(ispData.getNo_load() !=null ? ispData.getNo_load() : req.getReplaceBy());
	
			ispResponseList.add(ispResponse);
		}
		LOG.info("CT: Daily LP Data Response Caster Added.");
	}
}