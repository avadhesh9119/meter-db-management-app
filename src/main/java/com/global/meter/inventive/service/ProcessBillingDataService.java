package com.global.meter.inventive.service;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;

public interface ProcessBillingDataService {

	public void saveBillingDataProcess();

	public CommonResponse getProcessBillingData(MeterDataVisualizationReq req);

	public CommonResponse getProcessBillingDataDrillDown(MeterDataVisualizationReq req);

}
