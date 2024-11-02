package com.global.meter.inventive.service;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.PropertyRequest;

public interface ApplicationPropertyService {

	public CommonResponse updateAppProperty(PropertyRequest propertyRequest);
	
	public CommonResponse getAppProperty();
	
	public void restartScheduler();
	
}
