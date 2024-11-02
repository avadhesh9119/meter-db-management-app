package com.global.meter.inventive.service;

import java.util.List;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DtTransMasterDataRequest;

public interface DtTransMasterService {
	
	public CommonResponse addDtTrans(List<DtTransMasterDataRequest> feederData); 
	
	public CommonResponse updateDtTrans(List<DtTransMasterDataRequest> feederData); 
	
	public CommonResponse deleteDtTrans(DtTransMasterDataRequest feederDataRequests); 

	public CommonResponse getDtTrans(DtTransMasterDataRequest feederDataRequests); 
	
	public CommonResponse getDtListByFeeder(DtTransMasterDataRequest feederDataRequests); 
	
	public CommonResponse getMeterListByDt(DtTransMasterDataRequest feederDataRequests); 
	
	public CommonResponse getDtTransList();
	
	public CommonResponse getDtTransNameList();
	
	public CommonResponse getDtListByUtility(DtTransMasterDataRequest feederDataRequests); 
}
