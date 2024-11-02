package com.global.meter.inventive.service;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DeviceBatchLogsDataRequest;

/**
 * 
 * @author Nitin Sethi
 *
 */
public interface DeviceBatchLogsService {
	
	public CommonResponse createLogs(DeviceBatchLogsDataRequest metersData);

	public CommonResponse getBatchById(DeviceBatchLogsDataRequest batchId);

	public CommonResponse getBatchByUsername(DeviceBatchLogsDataRequest user); 

	public CommonResponse updateLogs(DeviceBatchLogsDataRequest metersData);
	
}
