package com.global.meter.inventive.mdm.service;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogsReq;

public interface SingleConnectionCommandMdmService {

	public CommonResponse getSingleConnectionCommandLogs(SingleConnectionCommandLogsReq req);
}
