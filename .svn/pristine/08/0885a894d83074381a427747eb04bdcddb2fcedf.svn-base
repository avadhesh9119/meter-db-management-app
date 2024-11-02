package com.global.meter.inventive.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DcuPowerData;
import com.global.meter.inventive.models.DcuPowerReq;
import com.global.meter.inventive.models.DcuPushResponse;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class DcuPowerDataCaster {

	private static final Logger LOG = LoggerFactory.getLogger(DcuPowerDataCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void dcuDataEvent(String outputList, List<DcuPushResponse> dcuResponses, DcuPowerReq req) throws Exception {

		LOG.info("Dcu Push Event Data Caster called....");
		List<DcuPowerData> dcuPowerData = new ArrayList<DcuPowerData>();

		dcuPowerData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DcuPowerData>>() {
		});

		LOG.info(" Dcu Push Event Data Response Data Adding....");

		for (DcuPowerData ispData : dcuPowerData) {
			DcuPushResponse ispResponse = new DcuPushResponse();

			ispResponse.setDcuSerialNumber(
					ispData.getDcu_serial_number() != null ? ispData.getDcu_serial_number() : req.getReplaceBy());
			ispResponse.setDcuMacNicId(
					ispData.getDcu_mac_nic_id() != null ? ispData.getDcu_mac_nic_id() : req.getReplaceBy());
			ispResponse.setRequestId(ispData.getRequest_id() != null ? ispData.getRequest_id() : req.getReplaceBy());
			ispResponse.setBatteryChargingStatus(
					ispData.getBattery_charging_status() != null ? ispData.getBattery_charging_status()
							: req.getReplaceBy());
			ispResponse.setPowerGoodStatus(
					ispData.getPower_good_status() != null ? ispData.getPower_good_status() : req.getReplaceBy());
			ispResponse.setBatteryRawValue(
					ispData.getBattery_raw_value() != null ? ispData.getBattery_raw_value() : req.getReplaceBy());
			ispResponse.setBatteryMvValue(
					ispData.getBattery_mv_value() != null ? ispData.getBattery_mv_value() : req.getReplaceBy());
			ispResponse.setHesDateTime(ispData.getMdas_datetime() != null
					? dateConverter.convertDateToHesString(ispData.getMdas_datetime())
					: req.getReplaceBy());

			dcuResponses.add(ispResponse);
		}
		LOG.info(" Dcu Push Event Data Response Data Added....");
	}

}
