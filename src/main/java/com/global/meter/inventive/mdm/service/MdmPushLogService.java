package com.global.meter.inventive.mdm.service;

import com.global.meter.inventive.mdm.models.MdmPushRequest;
import com.global.meter.inventive.models.CommonResponse;

public interface MdmPushLogService {
	public CommonResponse addMDMPushLog(MdmPushRequest mdmPushRequest);

	public CommonResponse updateMdmPushLog(MdmPushRequest mdmPushRequest);

}
