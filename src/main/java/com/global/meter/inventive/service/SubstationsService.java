package com.global.meter.inventive.service;

import java.util.List;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.SubstationsDataRequest;

public interface SubstationsService {

	public CommonResponse addSubstation(List<SubstationsDataRequest> subStationDataRequest);

	public CommonResponse updateSubstation(List<SubstationsDataRequest> subStationDataRequest);

	public CommonResponse deleteSubstation(SubstationsDataRequest subStationDataRequest);

	public CommonResponse getSubstation(SubstationsDataRequest subStationDataRequest);

	public CommonResponse getListBySubdivision(SubstationsDataRequest subStationDataRequest);

	public CommonResponse getSubstationList();

	public CommonResponse getSubstationNameList();
	
	public CommonResponse getSubstationsByUtility(SubstationsDataRequest subStationDataRequest);

}
