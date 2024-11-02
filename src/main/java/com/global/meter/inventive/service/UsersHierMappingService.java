package com.global.meter.inventive.service;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.UserHierarchyMappingReq;
import com.global.meter.inventive.models.UsersHierMappingDataRequest;

public interface UsersHierMappingService {

	public CommonResponse addUserHierMapping(UserHierarchyMappingReq usersHierMappingDataRequests);

	public CommonResponse updateUserHierMapping(UserHierarchyMappingReq usersHierMappingDataRequest);

	public CommonResponse deleteUserHierMapping(UsersHierMappingDataRequest usersHierMappingDataRequest);

	public CommonResponse getUserHierMapping(UsersHierMappingDataRequest usersHierMappingDataRequest);

	public CommonResponse getUserHierMappingList();

	public CommonResponse getUserHierMappingIdList();

}
