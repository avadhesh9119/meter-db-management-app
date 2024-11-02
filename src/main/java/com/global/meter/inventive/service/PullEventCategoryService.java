package com.global.meter.inventive.service;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.PriorityEventRequest;

public interface PullEventCategoryService {
	public CommonResponse addEventsCategory(PriorityEventRequest req);

	public CommonResponse updateEventsCategory(PriorityEventRequest req);

	public CommonResponse getEventsCategory(PriorityEventRequest req);

	public CommonResponse getPullEventsCategoryCountDetail(MeterDataVisualizationReq req);
}
