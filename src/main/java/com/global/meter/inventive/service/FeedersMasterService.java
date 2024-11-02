package com.global.meter.inventive.service;

import java.util.List;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.FeederMasterDataRequest;

public interface FeedersMasterService {
	
	public CommonResponse addFeeders(List<FeederMasterDataRequest> feederData); 
	
	public CommonResponse updateFeeders(List<FeederMasterDataRequest> feederData); 
	
	public CommonResponse deleteFeeders(FeederMasterDataRequest feederDataRequests); 

	public CommonResponse getFeeder(FeederMasterDataRequest feederDataRequests); 
	
	public CommonResponse getFeedersBySubstation(FeederMasterDataRequest feederDataRequests); 
	
	public CommonResponse getFeedersList();
	
	public CommonResponse getFeederNameList();
	
	public CommonResponse getFeedersByUtility(FeederMasterDataRequest feederDataRequests); 

}
