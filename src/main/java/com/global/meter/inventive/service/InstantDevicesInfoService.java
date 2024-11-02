package com.global.meter.inventive.service;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;

public interface InstantDevicesInfoService {
	public CommonResponse getDevicesInfo(MeterDataVisualizationReq meterDataVisualizationReq);

}
