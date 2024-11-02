package com.global.meter.inventive.mdm.service;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;

/**
 * 
 * @author Nitin Sethi
 *
 */
public interface MeterDataVisualizationMdmService {

	public CommonResponse getInstantData(MeterDataVisualizationReq req);
	
	public CommonResponse getDailyLPData(MeterDataVisualizationReq req);
	
	public CommonResponse getDeltaLPData(MeterDataVisualizationReq req);
	
	public CommonResponse getNamePlateData(MeterDataVisualizationReq req);
	
	public CommonResponse getNamePlateHistoryData(MeterDataVisualizationReq req);
	
	public CommonResponse getDeviceConfigLog(MeterDataVisualizationReq req);
	
	public CommonResponse getBillingData(MeterDataVisualizationReq req);
	
	public CommonResponse getEventData(MeterDataVisualizationReq req);
	
	public CommonResponse getConfigurationReadDataByHier(MeterDataVisualizationReq req);
	
	public CommonResponse getConfigurationReadDataLogsByHier(MeterDataVisualizationReq req);
	
	public CommonResponse getMonthlyBillingProfile(MeterDataVisualizationReq req);
	
	public CommonResponse getEventsPushData(MeterDataVisualizationReq req);

	public CommonResponse getInstantPushData(MeterDataVisualizationReq req);
	
	public CommonResponse getNamePlateDevicesInfo(MeterDataVisualizationReq req);
	
	public CommonResponse getRecentRelayStatus(MeterDataVisualizationReq req);

	public CommonResponse getDataByQuery(MeterDataVisualizationReq req);
}
