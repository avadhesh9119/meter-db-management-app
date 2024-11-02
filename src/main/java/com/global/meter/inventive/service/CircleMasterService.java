package com.global.meter.inventive.service;

import java.util.List;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.CircleMasterDataRequest;

public interface CircleMasterService {

	public CommonResponse addCircle(List<CircleMasterDataRequest> circleMasterDataRequest);

	public CommonResponse updateCircle(List<CircleMasterDataRequest> subStationMasterDataRequest);

//	public CommonResponse deleteCircle(CircleMasterDataRequest subStationMasterDataRequests);

	public CommonResponse getCircle(CircleMasterDataRequest subStationMasterDataRequests);

//	public CommonResponse getListByCircle(CircleMasterDataRequest subStationMasterDataRequests);

	public CommonResponse getCircleList();

//	public CommonResponse getCircleNameList();
	
	public CommonResponse getCircleByZone(CircleMasterDataRequest subStationMasterDataRequests);

}
