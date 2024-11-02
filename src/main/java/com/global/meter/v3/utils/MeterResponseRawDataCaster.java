package com.global.meter.v3.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.v3.inventive.business.model.MeterResponseRawData;
import com.global.meter.v3.inventive.models.MeterRawDataResponse;
import com.global.meter.v3.inventive.models.MeterResponseRawDataReq;

@Component
public class MeterResponseRawDataCaster {
	private static final Logger LOG = LoggerFactory.getLogger(MeterResponseRawDataCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareRawData(String outputList, List<MeterRawDataResponse> ispResponseList, MeterResponseRawDataReq req) throws Exception {
		LOG.info("Meter Response Raw Data Caster called....");
		List<MeterResponseRawData> rawData = new ArrayList<MeterResponseRawData>();
		rawData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<MeterResponseRawData>>() {});

		LOG.info("Meter Responce Raw Data Adding....");

		for (MeterResponseRawData ispData : rawData) {
			MeterRawDataResponse ispResponse = new MeterRawDataResponse();

			try {
				ispResponse.setDeviceSerialNo(ispData.getDevice_serial_number());
				ispResponse.setTrackingId(ispData.getTracking_id());
				ispResponse.setData(ispData.getData().toString());
				ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
				ispResponse.setProfileType(ispData.getProfile_type());

				ispResponseList.add(ispResponse);
				LOG.info("Meter Response Raw Data Added....");
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
		}
	}

}
