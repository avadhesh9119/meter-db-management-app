package com.global.meter.inventive.mdm.utils;

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
import com.global.meter.utils.DateConverter;

@Component
public class PushInstantDataMdmCaster {
	private static final Logger LOG = LoggerFactory.getLogger(PushInstantDataMdmCaster.class);

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
			ispResponse.setCumulativeEnergyKvahExport(ispData.getCum_import_kvah() != null ? String.valueOf(ispData.getCum_import_kvah()) : req.getReplaceBy());
			ispResponse.setMdKw(ispData.getMax_demand_kw() != null ? String.valueOf(ispData.getMax_demand_kw()) : req.getReplaceBy());
			ispResponse.setFrequency(ispData.getFrequency() != null ? String.valueOf(ispData.getFrequency()) : req.getReplaceBy());
			ispResponse.setVoltage(ispData.getVoltage() != null ? String.valueOf(ispData.getVoltage()): req.getReplaceBy());
			ispResponse.setNeutralCurrent(ispData.getNeutral_current() != null ? String.valueOf(ispData.getNeutral_current()) : req.getReplaceBy());
			ispResponse.setPhaseCurrent(ispData.getPhase_current() != null ? String.valueOf(ispData.getPhase_current()) : req.getReplaceBy());
			ispResponse.setSignedPf(ispData.getSigned_pf() != null ? String.valueOf(ispData.getSigned_pf()) : req.getReplaceBy());

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

			ispResponseList.add(ispResponse);
		}
		LOG.info("Push Instant Data Three Phase Response Data Added....");
	}
}
