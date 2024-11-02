package com.global.meter.inventive.service;

import java.util.List;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.GetDeviceListsRequest;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.MetersDataRequest;

/**
 * 
 * @author Nitin Sethi
 *
 */
public interface DevicesService {
	
	public CommonResponse addDevice(List<MetersDataRequest> metersData); 
	
	public CommonResponse updateDevice(List<MetersDataRequest> metersData); 
	
	public CommonResponse deleteDevice(MetersDataRequest metersDataRequests); 

	public CommonResponse getDevice(MetersDataRequest metersDataRequests); 
	
	public CommonResponse getDeviceList(MeterDataVisualizationReq inputReq);
	
	public CommonResponse getDeviceLists(GetDeviceListsRequest inputReq);

	public CommonResponse getDeletedDeviceList(MeterDataVisualizationReq inputReq);
	
	public CommonResponse decommissioningApproval(MetersDataRequest inputReq);

	public CommonResponse getDecommissionedRequestDeviceList(MeterDataVisualizationReq inputReq);

	public CommonResponse pingDevice(MetersDataRequest inputReq);

	public CommonResponse getDeviceLogs(MeterDataVisualizationReq inputReq);
	
	public CommonResponse getReplaceDeviceReport(MeterDataVisualizationReq inputReq);
}
