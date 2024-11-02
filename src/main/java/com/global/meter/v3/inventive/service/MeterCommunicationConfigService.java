package com.global.meter.v3.inventive.service;

import java.util.List;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.v3.inventive.models.MeterCommunicationConfig;

public interface MeterCommunicationConfigService {
	
	public CommonResponse addMeterTypeInfo(List<MeterCommunicationConfig> metersData);

	public CommonResponse updateMeterTypeInfo(List<MeterCommunicationConfig> metersData);

	public CommonResponse getMeterTypeInfo(MeterCommunicationConfig metersDataRequests);

	public CommonResponse getMeterTypeInfoList();

}
