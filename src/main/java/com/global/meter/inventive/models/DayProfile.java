package com.global.meter.inventive.models;

import java.util.List;

public class DayProfile {

	private int dayId;
	private List<DayProfileTime> dayProfileTime;
	private Object activatePassiveCalendar;
	
	public int getDayId() {
		return dayId;
	}
	public void setDayId(int dayId) {
		this.dayId = dayId;
	}
	public List<DayProfileTime> getDayProfileTime() {
		return dayProfileTime;
	}
	public void setDayProfileTime(List<DayProfileTime> dayProfileTime) {
		this.dayProfileTime = dayProfileTime;
	}
	public Object getActivatePassiveCalendar() {
		return activatePassiveCalendar;
	}
	public void setActivatePassiveCalendar(Object activatePassiveCalendar) {
		this.activatePassiveCalendar = activatePassiveCalendar;
	}
}
