package com.global.meter.inventive.models;

import java.util.List;
import java.util.Map;

public class EventCategory {
	
	private String type;
	private Map<String, List<String>> events;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, List<String>> getEvents() {
		return events;
	}
	public void setEvents(Map<String, List<String>> events) {
		this.events = events;
	}
	
	
}
