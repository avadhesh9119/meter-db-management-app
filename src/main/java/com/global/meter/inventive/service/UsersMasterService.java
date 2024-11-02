package com.global.meter.inventive.service;

import java.util.List;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.UsersMasterDataRequest;

public interface UsersMasterService {

	public CommonResponse addUser(List<UsersMasterDataRequest> userMasterDataRequest);

	public CommonResponse updateUser(List<UsersMasterDataRequest> userMasterDataRequest);

	public CommonResponse deleteUser(UsersMasterDataRequest userMasterDataRequest);

	public CommonResponse getUser(UsersMasterDataRequest userMasterDataRequest);

	public CommonResponse getUserList();

	public CommonResponse getUserIdList();

}
