package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MeterCommCountResponse {
	
	private String utility;
	
	@JsonProperty("Billing Day Failure Count")
	private int billingDayFailureCount;
	
	@JsonProperty("Billing Day Success Count")
	private int billingDaySuccessCount;

	@JsonProperty("Billing Month Failure Count")
	private int billingMonthFailureCount;

	@JsonProperty("Billing Month Success Count")
	private int billingMonthSuccessCount;

	@JsonProperty("Billing Week Failure Count")
	private int billingWeekFailureCount;

	@JsonProperty("Billing Week Success Count")
	private int billingWeekSuccessCount;

	@JsonProperty("Billing Yest Failure Count")
	private int billingYesFailureCount;

	@JsonProperty("Billing Yest Success Count")
	private int billingYesSuccessCount;

	@JsonProperty("Comm Day Failure Count")
	private int commDayFailureCount;

	@JsonProperty("Comm Day Success Count")
	private int commDaySuccessCount;

	@JsonProperty("Comm Month Failure Count")
	private int comMonthFailureCount;

	@JsonProperty("Comm Month Success Count")
	private int comMonthSuccessCount;
	
	@JsonProperty("Comm Week Failure Count")
	private int commWeekFailureCount;
	
	@JsonProperty("Comm Week Success Count")
	private int commWeekSuccessCount;

	@JsonProperty("Comm Yest Failure Count")
	private int commYestFailureCount;

	@JsonProperty("Comm Yest Success Count")
	private int commYestSuccessCount;

	@JsonProperty("Ct Phase Count")
	private int ctPhase;

	@JsonProperty("Daily Day Failure Count")
	private int dailyDayFailureCount;

	@JsonProperty("Daily Day Success Count")
	private int dailyDaySuccessCount;

	@JsonProperty("Daily Month Failure Count")
	private int dailyMonthFailureCount;

	@JsonProperty("Daily Month Success Count")
	private int dailyMonthSuccessCount;

	@JsonProperty("Daily Week Failure Count")
	private int dailyWeekFailureCount;

	@JsonProperty("Daily Week Success Count")
	private int dailyWeekSuccessCount;

	@JsonProperty("Daily Yest Failure Count")
	private int dailyYesFailureCount;

	@JsonProperty("Daily Yest Success Count")
	private int dailyYesSuccessCount;

	@JsonProperty("Dcu Count")
	private int dcu;

	@JsonProperty("Delta Day Failure Count")
	private int deltaDayFailureCount;

	@JsonProperty("Delta Day Success Count")
	private int deltaDaySuccessCount;

	@JsonProperty("Delta Month Failure Count")
	private int deltaMonthFailureCount;

	@JsonProperty("Delta Month Success Count")
	private int deltaMonthSuccessCount;

	@JsonProperty("Delta Week Failure Count")
	private int deltaWeekFailureCount;

	@JsonProperty("Delta Week Success Count")
	private int deltaWeekSuccessCount;

	@JsonProperty("Delta Yest Failure Count")
	private int deltaYesFailureCount;

	@JsonProperty("Delta Yest Success Count")
	private int deltaYesSuccessCount;

	@JsonProperty("Device Count")
	private int device;

	@JsonProperty("DT Count")
	private int dt;

	@JsonProperty("Event Day Failure Count")
	private int eventDayFailureCount;

	@JsonProperty("Event Day Success Count")
	private int eventDaySuccessCount;

	@JsonProperty("Event Month Failure Count")
	private int eventMonthFailureCount;

	@JsonProperty("Event Month Success Count")
	private int eventMonthSuccessCount;

	@JsonProperty("Event week Failure Count")
	private int eventWeekFailureCount;

	@JsonProperty("Event Week Success Count")
	private int eventWeekSuccessCount;

	@JsonProperty("Event Yest Failure Count")
	private int eventYesFailureCount;

	@JsonProperty("Event Yest Success Count")
	private int eventYesSuccessCount;

	@JsonProperty("Feeder Count")
	private int feeder;

	@JsonProperty("Ht Phase Count")
	private int htPhase;

	@JsonProperty("Inactive Device Count")
	private int inActiveDev;

	@JsonProperty("Instant Day Failure Count")
	private int instantDayFailureCount;

	@JsonProperty("Instant Day Success Count")
	private int instantDaySuccessCount;

	@JsonProperty("Instant Month Failure Count")
	private int instantMonthFailureCount;

	@JsonProperty("Instant Month Success Count")
	private int instantMonthSuccessCount;

	@JsonProperty("Instant Week Failure Count")
	private int instantWeekFailureCount;

	@JsonProperty("Instant Week Success Count")
	private int instantWeekSuccessCount;

	@JsonProperty("Instant Yest Failure Count")
	private int instantYestFailureCount;

	@JsonProperty("Instant Yest Success Count")
	private int instantYestSuccessCount;
	
	@JsonProperty("Last Updated DateTime")
	private String lastUpdatedTime;

	@JsonProperty("Manual Active Device Count")
	private int manualActiveDev;

	@JsonProperty("Manual Faulty Device Count")
	private int manualFaultyDev;

	@JsonProperty("Single Phase Count")
	private int singlePhase;

	@JsonProperty("Sub Division Count")
	private int subDevision;

	@JsonProperty("Sub Station Count")
	private int substation;

	@JsonProperty("Three Phase Count")
	private int threePhase;

	public String getUtility() {
		return utility;
	}

	public void setUtility(String utility) {
		this.utility = utility;
	}

	public int getBillingDayFailureCount() {
		return billingDayFailureCount;
	}

	public void setBillingDayFailureCount(int billingDayFailureCount) {
		this.billingDayFailureCount = billingDayFailureCount;
	}

	public int getBillingDaySuccessCount() {
		return billingDaySuccessCount;
	}

	public void setBillingDaySuccessCount(int billingDaySuccessCount) {
		this.billingDaySuccessCount = billingDaySuccessCount;
	}

	public int getBillingMonthFailureCount() {
		return billingMonthFailureCount;
	}

	public void setBillingMonthFailureCount(int billingMonthFailureCount) {
		this.billingMonthFailureCount = billingMonthFailureCount;
	}

	public int getBillingMonthSuccessCount() {
		return billingMonthSuccessCount;
	}

	public void setBillingMonthSuccessCount(int billingMonthSuccessCount) {
		this.billingMonthSuccessCount = billingMonthSuccessCount;
	}

	public int getBillingWeekFailureCount() {
		return billingWeekFailureCount;
	}

	public void setBillingWeekFailureCount(int billingWeekFailureCount) {
		this.billingWeekFailureCount = billingWeekFailureCount;
	}

	public int getBillingWeekSuccessCount() {
		return billingWeekSuccessCount;
	}

	public void setBillingWeekSuccessCount(int billingWeekSuccessCount) {
		this.billingWeekSuccessCount = billingWeekSuccessCount;
	}

	public int getBillingYesFailureCount() {
		return billingYesFailureCount;
	}

	public void setBillingYesFailureCount(int billingYesFailureCount) {
		this.billingYesFailureCount = billingYesFailureCount;
	}

	public int getBillingYesSuccessCount() {
		return billingYesSuccessCount;
	}

	public void setBillingYesSuccessCount(int billingYesSuccessCount) {
		this.billingYesSuccessCount = billingYesSuccessCount;
	}

	public int getCommDayFailureCount() {
		return commDayFailureCount;
	}

	public void setCommDayFailureCount(int commDayFailureCount) {
		this.commDayFailureCount = commDayFailureCount;
	}

	public int getCommDaySuccessCount() {
		return commDaySuccessCount;
	}

	public void setCommDaySuccessCount(int commDaySuccessCount) {
		this.commDaySuccessCount = commDaySuccessCount;
	}

	public int getComMonthFailureCount() {
		return comMonthFailureCount;
	}

	public void setComMonthFailureCount(int comMonthFailureCount) {
		this.comMonthFailureCount = comMonthFailureCount;
	}

	public int getComMonthSuccessCount() {
		return comMonthSuccessCount;
	}

	public void setComMonthSuccessCount(int comMonthSuccessCount) {
		this.comMonthSuccessCount = comMonthSuccessCount;
	}

	public int getCommWeekFailureCount() {
		return commWeekFailureCount;
	}

	public void setCommWeekFailureCount(int commWeekFailureCount) {
		this.commWeekFailureCount = commWeekFailureCount;
	}

	public int getCommWeekSuccessCount() {
		return commWeekSuccessCount;
	}

	public void setCommWeekSuccessCount(int commWeekSuccessCount) {
		this.commWeekSuccessCount = commWeekSuccessCount;
	}

	public int getCommYestFailureCount() {
		return commYestFailureCount;
	}

	public void setCommYestFailureCount(int commYestFailureCount) {
		this.commYestFailureCount = commYestFailureCount;
	}

	public int getCommYestSuccessCount() {
		return commYestSuccessCount;
	}

	public void setCommYestSuccessCount(int commYestSuccessCount) {
		this.commYestSuccessCount = commYestSuccessCount;
	}

	public int getCtPhase() {
		return ctPhase;
	}

	public void setCtPhase(int ctPhase) {
		this.ctPhase = ctPhase;
	}

	public int getDailyDayFailureCount() {
		return dailyDayFailureCount;
	}

	public void setDailyDayFailureCount(int dailyDayFailureCount) {
		this.dailyDayFailureCount = dailyDayFailureCount;
	}

	public int getDailyDaySuccessCount() {
		return dailyDaySuccessCount;
	}

	public void setDailyDaySuccessCount(int dailyDaySuccessCount) {
		this.dailyDaySuccessCount = dailyDaySuccessCount;
	}

	public int getDailyMonthFailureCount() {
		return dailyMonthFailureCount;
	}

	public void setDailyMonthFailureCount(int dailyMonthFailureCount) {
		this.dailyMonthFailureCount = dailyMonthFailureCount;
	}

	public int getDailyMonthSuccessCount() {
		return dailyMonthSuccessCount;
	}

	public void setDailyMonthSuccessCount(int dailyMonthSuccessCount) {
		this.dailyMonthSuccessCount = dailyMonthSuccessCount;
	}

	public int getDailyWeekFailureCount() {
		return dailyWeekFailureCount;
	}

	public void setDailyWeekFailureCount(int dailyWeekFailureCount) {
		this.dailyWeekFailureCount = dailyWeekFailureCount;
	}

	public int getDailyWeekSuccessCount() {
		return dailyWeekSuccessCount;
	}

	public void setDailyWeekSuccessCount(int dailyWeekSuccessCount) {
		this.dailyWeekSuccessCount = dailyWeekSuccessCount;
	}

	public int getDailyYesFailureCount() {
		return dailyYesFailureCount;
	}

	public void setDailyYesFailureCount(int dailyYesFailureCount) {
		this.dailyYesFailureCount = dailyYesFailureCount;
	}

	public int getDailyYesSuccessCount() {
		return dailyYesSuccessCount;
	}

	public void setDailyYesSuccessCount(int dailyYesSuccessCount) {
		this.dailyYesSuccessCount = dailyYesSuccessCount;
	}

	public int getDcu() {
		return dcu;
	}

	public void setDcu(int dcu) {
		this.dcu = dcu;
	}

	public int getDeltaDayFailureCount() {
		return deltaDayFailureCount;
	}

	public void setDeltaDayFailureCount(int deltaDayFailureCount) {
		this.deltaDayFailureCount = deltaDayFailureCount;
	}

	public int getDeltaDaySuccessCount() {
		return deltaDaySuccessCount;
	}

	public void setDeltaDaySuccessCount(int deltaDaySuccessCount) {
		this.deltaDaySuccessCount = deltaDaySuccessCount;
	}

	public int getDeltaMonthFailureCount() {
		return deltaMonthFailureCount;
	}

	public void setDeltaMonthFailureCount(int deltaMonthFailureCount) {
		this.deltaMonthFailureCount = deltaMonthFailureCount;
	}

	public int getDeltaMonthSuccessCount() {
		return deltaMonthSuccessCount;
	}

	public void setDeltaMonthSuccessCount(int deltaMonthSuccessCount) {
		this.deltaMonthSuccessCount = deltaMonthSuccessCount;
	}

	public int getDeltaWeekFailureCount() {
		return deltaWeekFailureCount;
	}

	public void setDeltaWeekFailureCount(int deltaWeekFailureCount) {
		this.deltaWeekFailureCount = deltaWeekFailureCount;
	}

	public int getDeltaWeekSuccessCount() {
		return deltaWeekSuccessCount;
	}

	public void setDeltaWeekSuccessCount(int deltaWeekSuccessCount) {
		this.deltaWeekSuccessCount = deltaWeekSuccessCount;
	}

	public int getDeltaYesFailureCount() {
		return deltaYesFailureCount;
	}

	public void setDeltaYesFailureCount(int deltaYesFailureCount) {
		this.deltaYesFailureCount = deltaYesFailureCount;
	}

	public int getDeltaYesSuccessCount() {
		return deltaYesSuccessCount;
	}

	public void setDeltaYesSuccessCount(int deltaYesSuccessCount) {
		this.deltaYesSuccessCount = deltaYesSuccessCount;
	}

	public int getDevice() {
		return device;
	}

	public void setDevice(int device) {
		this.device = device;
	}

	public int getDt() {
		return dt;
	}

	public void setDt(int dt) {
		this.dt = dt;
	}

	public int getEventDayFailureCount() {
		return eventDayFailureCount;
	}

	public void setEventDayFailureCount(int eventDayFailureCount) {
		this.eventDayFailureCount = eventDayFailureCount;
	}

	public int getEventDaySuccessCount() {
		return eventDaySuccessCount;
	}

	public void setEventDaySuccessCount(int eventDaySuccessCount) {
		this.eventDaySuccessCount = eventDaySuccessCount;
	}

	public int getEventMonthFailureCount() {
		return eventMonthFailureCount;
	}

	public void setEventMonthFailureCount(int eventMonthFailureCount) {
		this.eventMonthFailureCount = eventMonthFailureCount;
	}

	public int getEventMonthSuccessCount() {
		return eventMonthSuccessCount;
	}

	public void setEventMonthSuccessCount(int eventMonthSuccessCount) {
		this.eventMonthSuccessCount = eventMonthSuccessCount;
	}

	public int getEventWeekFailureCount() {
		return eventWeekFailureCount;
	}

	public void setEventWeekFailureCount(int eventWeekFailureCount) {
		this.eventWeekFailureCount = eventWeekFailureCount;
	}

	public int getEventWeekSuccessCount() {
		return eventWeekSuccessCount;
	}

	public void setEventWeekSuccessCount(int eventWeekSuccessCount) {
		this.eventWeekSuccessCount = eventWeekSuccessCount;
	}

	public int getEventYesFailureCount() {
		return eventYesFailureCount;
	}

	public void setEventYesFailureCount(int eventYesFailureCount) {
		this.eventYesFailureCount = eventYesFailureCount;
	}

	public int getEventYesSuccessCount() {
		return eventYesSuccessCount;
	}

	public void setEventYesSuccessCount(int eventYesSuccessCount) {
		this.eventYesSuccessCount = eventYesSuccessCount;
	}

	public int getFeeder() {
		return feeder;
	}

	public void setFeeder(int feeder) {
		this.feeder = feeder;
	}

	public int getHtPhase() {
		return htPhase;
	}

	public void setHtPhase(int htPhase) {
		this.htPhase = htPhase;
	}

	public int getInActiveDev() {
		return inActiveDev;
	}

	public void setInActiveDev(int inActiveDev) {
		this.inActiveDev = inActiveDev;
	}

	public int getInstantDayFailureCount() {
		return instantDayFailureCount;
	}

	public void setInstantDayFailureCount(int instantDayFailureCount) {
		this.instantDayFailureCount = instantDayFailureCount;
	}

	public int getInstantDaySuccessCount() {
		return instantDaySuccessCount;
	}

	public void setInstantDaySuccessCount(int instantDaySuccessCount) {
		this.instantDaySuccessCount = instantDaySuccessCount;
	}

	public int getInstantMonthFailureCount() {
		return instantMonthFailureCount;
	}

	public void setInstantMonthFailureCount(int instantMonthFailureCount) {
		this.instantMonthFailureCount = instantMonthFailureCount;
	}

	public int getInstantMonthSuccessCount() {
		return instantMonthSuccessCount;
	}

	public void setInstantMonthSuccessCount(int instantMonthSuccessCount) {
		this.instantMonthSuccessCount = instantMonthSuccessCount;
	}

	public int getInstantWeekFailureCount() {
		return instantWeekFailureCount;
	}

	public void setInstantWeekFailureCount(int instantWeekFailureCount) {
		this.instantWeekFailureCount = instantWeekFailureCount;
	}

	public int getInstantWeekSuccessCount() {
		return instantWeekSuccessCount;
	}

	public void setInstantWeekSuccessCount(int instantWeekSuccessCount) {
		this.instantWeekSuccessCount = instantWeekSuccessCount;
	}

	public int getInstantYestFailureCount() {
		return instantYestFailureCount;
	}

	public void setInstantYestFailureCount(int instantYestFailureCount) {
		this.instantYestFailureCount = instantYestFailureCount;
	}

	public int getInstantYestSuccessCount() {
		return instantYestSuccessCount;
	}

	public void setInstantYestSuccessCount(int instantYestSuccessCount) {
		this.instantYestSuccessCount = instantYestSuccessCount;
	}

	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public int getManualActiveDev() {
		return manualActiveDev;
	}

	public void setManualActiveDev(int manualActiveDev) {
		this.manualActiveDev = manualActiveDev;
	}

	public int getManualFaultyDev() {
		return manualFaultyDev;
	}

	public void setManualFaultyDev(int manualFaultyDev) {
		this.manualFaultyDev = manualFaultyDev;
	}

	public int getSinglePhase() {
		return singlePhase;
	}

	public void setSinglePhase(int singlePhase) {
		this.singlePhase = singlePhase;
	}

	public int getSubDevision() {
		return subDevision;
	}

	public void setSubDevision(int subDevision) {
		this.subDevision = subDevision;
	}

	public int getSubstation() {
		return substation;
	}

	public void setSubstation(int substation) {
		this.substation = substation;
	}

	public int getThreePhase() {
		return threePhase;
	}

	public void setThreePhase(int threePhase) {
		this.threePhase = threePhase;
	}

	@Override
	public String toString() {
		return "MeterCommCountResponse [billingDayFailureCount=" + billingDayFailureCount + ", billingDaySuccessCount="
				+ billingDaySuccessCount + ", billingMonthFailureCount=" + billingMonthFailureCount
				+ ", billingMonthSuccessCount=" + billingMonthSuccessCount + ", billingWeekFailureCount="
				+ billingWeekFailureCount + ", billingWeekSuccessCount=" + billingWeekSuccessCount
				+ ", billingYesFailureCount=" + billingYesFailureCount + ", billingYesSuccessCount="
				+ billingYesSuccessCount + ", commDayFailureCount=" + commDayFailureCount + ", commDaySuccessCount="
				+ commDaySuccessCount + ", comMonthFailureCount=" + comMonthFailureCount + ", comMonthSuccessCount="
				+ comMonthSuccessCount + ", commWeekFailureCount=" + commWeekFailureCount + ", commWeekSuccessCount="
				+ commWeekSuccessCount + ", commYestFailureCount=" + commYestFailureCount + ", commYestSuccessCount="
				+ commYestSuccessCount + ", ctPhase=" + ctPhase + ", dailyDayFailureCount=" + dailyDayFailureCount
				+ ", dailyDaySuccessCount=" + dailyDaySuccessCount + ", dailyMonthFailureCount="
				+ dailyMonthFailureCount + ", dailyMonthSuccessCount=" + dailyMonthSuccessCount
				+ ", dailyWeekFailureCount=" + dailyWeekFailureCount + ", dailyWeekSuccessCount="
				+ dailyWeekSuccessCount + ", dailyYesFailureCount=" + dailyYesFailureCount + ", dailyYesSuccessCount="
				+ dailyYesSuccessCount + ", dcu=" + dcu + ", deltaDayFailureCount=" + deltaDayFailureCount
				+ ", deltaDaySuccessCount=" + deltaDaySuccessCount + ", deltaMonthFailureCount="
				+ deltaMonthFailureCount + ", deltaMonthSuccessCount=" + deltaMonthSuccessCount
				+ ", deltaWeekFailureCount=" + deltaWeekFailureCount + ", deltaWeekSuccessCount="
				+ deltaWeekSuccessCount + ", deltaYesFailureCount=" + deltaYesFailureCount + ", deltaYesSuccessCount="
				+ deltaYesSuccessCount + ", device=" + device + ", dt=" + dt + ", eventDayFailureCount="
				+ eventDayFailureCount + ", eventDaySuccessCount=" + eventDaySuccessCount + ", eventMonthFailureCount="
				+ eventMonthFailureCount + ", eventMonthSuccessCount=" + eventMonthSuccessCount
				+ ", eventWeekFailureCount=" + eventWeekFailureCount + ", eventWeekSuccessCount="
				+ eventWeekSuccessCount + ", eventYesFailureCount=" + eventYesFailureCount + ", eventYesSuccessCount="
				+ eventYesSuccessCount + ", feeder=" + feeder + ", htPhase=" + htPhase + ", inActiveDev=" + inActiveDev
				+ ", instantDayFailureCount=" + instantDayFailureCount + ", instantDaySuccessCount="
				+ instantDaySuccessCount + ", instantMonthFailureCount=" + instantMonthFailureCount
				+ ", instantMonthSuccessCount=" + instantMonthSuccessCount + ", instantWeekFailureCount="
				+ instantWeekFailureCount + ", instantWeekSuccessCount=" + instantWeekSuccessCount
				+ ", instantYestFailureCount=" + instantYestFailureCount + ", instantYestSuccessCount="
				+ instantYestSuccessCount + ", lastUpdatedTime=" + lastUpdatedTime + ", manualActiveDev="
				+ manualActiveDev + ", manualFaultyDev=" + manualFaultyDev + ", singlePhase=" + singlePhase
				+ ", subDevision=" + subDevision + ", substation=" + substation + ", threePhase=" + threePhase + "]";
	}

}
