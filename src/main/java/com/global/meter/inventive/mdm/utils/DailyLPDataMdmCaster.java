package com.global.meter.inventive.mdm.utils;

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
public class DailyLPDataMdmCaster {
	private static final Logger LOG = LoggerFactory.getLogger(InstantDevicesInfoMdmCaster.class);
	
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
			
			ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			ispResponse.setKvahExport(String.valueOf(ispData.getCumulative_energy_kvah_export()));
			ispResponse.setKvahImport(String.valueOf(ispData.getCumulative_energy_kvah_import()));
			ispResponse.setkWhExport(String.valueOf(ispData.getCumulative_energy_kwh_export()));
			ispResponse.setkWhImport(String.valueOf(ispData.getCumulative_energy_kwh_import()));
			ispResponse.setDatetime(dateConverter.convertDateToHesString(ispData.getDatetime()));
			ispResponse.setMeterNumber(ispData.getDevice_serial_number());
		
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
			
			ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			ispResponse.setKvahExport(ispData.getCumulative_energy_kvah_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_export())) : req.getReplaceBy());
			ispResponse.setKvahImport(ispData.getCumulative_energy_kvah_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_import())) : req.getReplaceBy());		
			ispResponse.setkWhExport(ispData.getCumulative_energy_kwh_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_export())) : req.getReplaceBy());
			ispResponse.setkWhImport(ispData.getCumulative_energy_kwh_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_import())) : req.getReplaceBy());
			ispResponse.setDatetime(dateConverter.convertDateToHesString(ispData.getDatetime()));
			ispResponse.setMeterNumber(ispData.getDevice_serial_number());		
			ispResponse.setKvarh_q1(ispData.getCumulative_energy_kvarh_q1() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q1()) : req.getReplaceBy());
			ispResponse.setKvarh_q2(ispData.getCumulative_energy_kvarh_q2() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q2()) : req.getReplaceBy());
			ispResponse.setKvarh_q3(ispData.getCumulative_energy_kvarh_q3() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q3()) : req.getReplaceBy());
			ispResponse.setKvarh_q4(ispData.getCumulative_energy_kvarh_q4() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q4()) : req.getReplaceBy());
			ispResponse.setMdkva(ispData.getMaximum_demand_kva() !=null ? String.valueOf(ispData.getMaximum_demand_kva()) : req.getReplaceBy());
			ispResponse.setMdkvaDatetime(ispData.getMaximum_demand_kva_datetime() !=null ?dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_datetime()) : req.getReplaceBy());
			ispResponse.setMdkw(ispData.getMaximum_demand_kw() !=null ? String.valueOf(ispData.getMaximum_demand_kw()) : req.getReplaceBy());
			ispResponse.setMdkwDatetime(ispData.getMaximum_demand_kw_datetime() !=null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_datetime()) : req.getReplaceBy());
		
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
			
			ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			ispResponse.setKvahExport(ispData.getCumulative_energy_kvah_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_export())) : req.getReplaceBy());
			ispResponse.setKvahImport(ispData.getCumulative_energy_kvah_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kvah_import())) : req.getReplaceBy());		
			ispResponse.setkWhExport(ispData.getCumulative_energy_kwh_export() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_export())) : req.getReplaceBy());
			ispResponse.setkWhImport(ispData.getCumulative_energy_kwh_import() != null ? String.valueOf(CommonUtils.decimalFormat(ispData.getCumulative_energy_kwh_import())) : req.getReplaceBy());
			ispResponse.setDatetime(dateConverter.convertDateToHesString(ispData.getDatetime()));
			ispResponse.setMeterNumber(ispData.getDevice_serial_number());			
			ispResponse.setKvarh_q1(ispData.getCumulative_energy_kvarh_q1() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q1()) : req.getReplaceBy());
			ispResponse.setKvarh_q2(ispData.getCumulative_energy_kvarh_q2() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q2()) : req.getReplaceBy());
			ispResponse.setKvarh_q3(ispData.getCumulative_energy_kvarh_q3() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q3()) : req.getReplaceBy());
			ispResponse.setKvarh_q4(ispData.getCumulative_energy_kvarh_q4() !=null ? String.valueOf(ispData.getCumulative_energy_kvarh_q4()) : req.getReplaceBy());
			ispResponse.setMdkva(ispData.getMaximum_demand_kva() !=null ? String.valueOf(ispData.getMaximum_demand_kva()) : req.getReplaceBy());
			ispResponse.setMdkvaDatetime(ispData.getMaximum_demand_kva_datetime() !=null ?dateConverter.convertDateToHesString(ispData.getMaximum_demand_kva_datetime()) : req.getReplaceBy());
			ispResponse.setMdkw(ispData.getMaximum_demand_kw() !=null ? String.valueOf(ispData.getMaximum_demand_kw()) : req.getReplaceBy());
			ispResponse.setMdkwDatetime(ispData.getMaximum_demand_kw_datetime() !=null ? dateConverter.convertDateToHesString(ispData.getMaximum_demand_kw_datetime()) : req.getReplaceBy());
			ispResponse.setNoSupply(ispData.getNo_supply() !=null ? ispData.getNo_supply() : req.getReplaceBy());
			ispResponse.setNoLoad(ispData.getNo_load() !=null ? ispData.getNo_load() : req.getReplaceBy());
	
			ispResponseList.add(ispResponse);
		}
		LOG.info("CT: Daily LP Data Response Caster Added.");
	}
}