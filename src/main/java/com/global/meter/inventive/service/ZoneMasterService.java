package com.global.meter.inventive.service;

import java.util.List;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ZoneMasterDataRequest;

public interface ZoneMasterService {
	public CommonResponse addZone(List<ZoneMasterDataRequest> zoneDataRequests);

	public CommonResponse updateZone(List<ZoneMasterDataRequest> zoneDataRequests);

//	public CommonResponse deleteZone(ZoneMasterDataRequest zoneDataRequests);

	public CommonResponse getZone(ZoneMasterDataRequest zoneDataRequests);
	
	public CommonResponse getZoneList();
	
//	public CommonResponse getZoneNameList();
	
	public CommonResponse getListByUtility(ZoneMasterDataRequest zoneDataRequests);

}
