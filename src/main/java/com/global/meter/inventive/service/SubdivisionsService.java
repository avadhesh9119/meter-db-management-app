package com.global.meter.inventive.service;

import java.util.List;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.SubdivisionsDataRequest;

public interface SubdivisionsService {
	public CommonResponse addSubdivision(List<SubdivisionsDataRequest> subDivisionDataRequest);

	public CommonResponse updateSubdivision(List<SubdivisionsDataRequest> subDivisionDataRequest);

	public CommonResponse deleteSubdivision(SubdivisionsDataRequest subDivisionDataRequest);

	public CommonResponse getSubdivision(SubdivisionsDataRequest subDivisionDataRequest);

	public CommonResponse getListByUtility(SubdivisionsDataRequest subDivisionDataRequest);

	public CommonResponse getSubdivisionList();

	public CommonResponse getSubdivisionNameList();

}
