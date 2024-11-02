package com.global.meter.inventive.service;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;

public interface DevicesConfigBatchLogsService {

	public void saveDevicesConfigBatchLogs();

	public CommonResponse getDevicesConfigBatchLogs(MeterDataVisualizationReq req);

	public CommonResponse getDevicesConfigBatchDrillDown(MeterDataVisualizationReq req);
	
	public void saveDevicesConfigReadBatchLogs();
	
	public CommonResponse getDevicesConfigReadBatchData(MeterDataVisualizationReq req);
	
	public CommonResponse getDevicesConfigReadBatchDrillDown(MeterDataVisualizationReq req);

}
