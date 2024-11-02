package com.global.meter.inventive.mdm.models;

import java.util.Date;

public class MdmPushLogRequest {
	
	private String meterNumber;
	private Date hesTime;
	
	public String getMeterNumber() {
		return meterNumber;
	}
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
	public Date getHesTime() {
		return hesTime;
	}
	public void setHesTime(Date hesTime) {
		this.hesTime = hesTime;
	}
	
}
