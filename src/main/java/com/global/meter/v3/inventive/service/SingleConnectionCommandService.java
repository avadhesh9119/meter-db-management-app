package com.global.meter.v3.inventive.service;

import org.springframework.web.multipart.MultipartFile;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogsReq;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;

public interface SingleConnectionCommandService {

	public CommonResponse addSingleConnectionCommandLogs(SingleConnectionCommandReq req);
	
	public CommonResponse getSingleConnectionCommandLogs(SingleConnectionCommandLogsReq req);
	
	public CommonResponse cancelSingleConnectionCommandLog(SingleConnectionCommandLogsReq req);

	public CommonResponse getSingleConnectionCommandLogsByBatchId(SingleConnectionCommandLogsReq req);
	
	/*
		Code For Extra Obis code Implementation
	*/

	public CommonResponse getObisCodeDetails();
	
	public CommonResponse updateObisCodeDetails(SingleConnectionCommandLogsReq req);
	
	/*
		Code to upload xml file 
	*/
	
	public CommonResponse uploadXmlFile(MultipartFile xmlFile, String data);
}
