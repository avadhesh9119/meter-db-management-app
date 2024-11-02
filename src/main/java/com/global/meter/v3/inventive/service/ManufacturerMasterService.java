package com.global.meter.v3.inventive.service;

import java.util.List;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.v3.inventive.models.ManufacturerMasterDataRequest;

public interface ManufacturerMasterService {
	public CommonResponse addManufacturer(List<ManufacturerMasterDataRequest> manufacturerDataRequests);

	public CommonResponse updateManufacturer(List<ManufacturerMasterDataRequest> manufacturerDataRequests);

	public CommonResponse deleteManufacturer(ManufacturerMasterDataRequest manufacturerDataRequests);

	public CommonResponse getManufacturer(ManufacturerMasterDataRequest manufacturerDataRequests);
	
	public CommonResponse getManufacturerNameList();

}
