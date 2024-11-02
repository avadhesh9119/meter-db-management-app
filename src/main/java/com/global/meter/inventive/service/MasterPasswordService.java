package com.global.meter.inventive.service;

import java.util.List;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MasterPasswordRequest;

public interface MasterPasswordService {

	public CommonResponse addMeterTypeInfo(List<MasterPasswordRequest> metersData);

	public CommonResponse updateMeterTypeInfo(List<MasterPasswordRequest> metersData);

	public CommonResponse getMeterTypeInfo(MasterPasswordRequest metersDataRequests);

	public CommonResponse getMeterTypeInfoList();
}
