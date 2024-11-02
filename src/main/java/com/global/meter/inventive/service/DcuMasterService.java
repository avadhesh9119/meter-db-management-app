package com.global.meter.inventive.service;

import java.util.List;
import javax.validation.Valid;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DcuMasterReq;
import com.global.meter.inventive.models.DcuPowerReq;

public interface DcuMasterService {

	public CommonResponse addDcuMaster(List<DcuMasterReq> req);

	public CommonResponse updateDcuMaster(List<DcuMasterReq> req);

	public CommonResponse deleteDcuMaster( DcuMasterReq req);

	public CommonResponse getDcuMaster(DcuMasterReq req);

	public CommonResponse getListDcuMaster(@Valid DcuMasterReq req);

	public CommonResponse getDcuDeleteMaster(DcuMasterReq req);

	public CommonResponse dcuPower(@Valid DcuPowerReq req);

	public CommonResponse getDcuPush(@Valid DcuPowerReq req);
	
}
