package com.global.meter.inventive.mdm.service;

import java.util.List;

import com.global.meter.inventive.mdm.models.MdmMasterRequest;
import com.global.meter.inventive.models.CommonResponse;

public interface MdmMasterService {

	public CommonResponse addMdmMaster(List<MdmMasterRequest> mdmdMasterRequest);

	public CommonResponse updateMdmMaster(List<MdmMasterRequest> mdmdMasterRequest);

	public CommonResponse deleteMdmMaster(MdmMasterRequest mdmdMasterRequest);

	public CommonResponse getMdmMaster(MdmMasterRequest mdmdMasterRequest);

	public CommonResponse getMdmMasterList(MdmMasterRequest mdmdMasterRequest);

	public CommonResponse getMdmMasterLog(MdmMasterRequest mdmMasterRequest);

	public CommonResponse updateMDMIdHierarchyWise(MdmMasterRequest mdmMasterRequest);

}
