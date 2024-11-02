package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "push_instant_singlephase")
public class PushInstantsSinglePhase implements Serializable{

	private static final long serialVersionUID = 3983779723424327443L;

	@PrimaryKey
 	private String device_serial_number;
	private String device_id;
	private Double voltage;
	private Double phase_current;
	private Double neutral_current;
	private Double signed_pf;
	private Double frequency;
	private Double apparent_power_va;
	private Double active_power_kw;
	private Double cum_import_kwh;
	private Double cum_export_kwh;
	private Double cum_import_kvah;
	private Double cum_export_kvah;
	private Double max_demand_kw;
	private Date max_demand_kw_datetime;
	private Double max_demand_kva;
	private Date max_demand_kva_datetime;
	private Integer tamper_count;
	private Integer bill_count;
	private Integer relay_status;
	private Double load_limit;
	private Double power_duration;
	private Date meter_datetime;
    private Date mdas_datetime;
    private String dt_name;
    private String feeder_name;
    private String owner_name;
    private String subdivision_name;
    private String substation_name;
    private Double curr_month_kwh_import;
    private Double curr_month_kvah_import;
    private Integer cumm_power_on_duration;
    private Integer program_count;
    private Integer no_of_power_failure;
    private Date billing_date;
    private Double temprature;
    private Integer no_of_load_switch;
    private Integer cumm_over_voltage_tamper_count;
    private Integer cumm_low_voltage_tamper_count;
    private Integer cumm_reverse_current_tamper_count;
    private Integer cumm_over_current_tamper_count;
    private Integer cumm_earth_tamper_count;
    private Integer cumm_magnet_tamper_count;
    private Integer cumm_neutral_disturbance_count;
    private Integer cumm_sw_tamper_count;
    private Integer cumm_over_load_tamper_count;
    private Integer cumm_comms_removal_tamper_count;
    private Integer cumm_case_open_tamper_count;
    private Integer cumm_temprature_rise_count;
    private Integer cumm_power_failure_duration;
    private Integer relay_operation_disconnect_count;
    private Integer relay_operation_connect_count;
    private Integer avg_signal_strength;
    private Double avg_pf_billing_period;
	private Double max_demand_kva_export;
	private Double max_demand_kw_export;

    public PushInstantsSinglePhase() {
		super();
	}

	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public Double getVoltage() {
		return voltage;
	}

	public void setVoltage(Double voltage) {
		this.voltage = voltage;
	}

	public Double getPhase_current() {
		return phase_current;
	}

	public void setPhase_current(Double phase_current) {
		this.phase_current = phase_current;
	}

	public Double getNeutral_current() {
		return neutral_current;
	}

	public void setNeutral_current(Double neutral_current) {
		this.neutral_current = neutral_current;
	}

	public Double getSigned_pf() {
		return signed_pf;
	}

	public void setSigned_pf(Double signed_pf) {
		this.signed_pf = signed_pf;
	}

	public Double getFrequency() {
		return frequency;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}

	public Double getApparent_power_va() {
		return apparent_power_va;
	}

	public void setApparent_power_va(Double apparent_power_va) {
		this.apparent_power_va = apparent_power_va;
	}

	public Double getActive_power_kw() {
		return active_power_kw;
	}

	public void setActive_power_kw(Double active_power_kw) {
		this.active_power_kw = active_power_kw;
	}

	public Double getCum_import_kwh() {
		return cum_import_kwh;
	}

	public void setCum_import_kwh(Double cum_import_kwh) {
		this.cum_import_kwh = cum_import_kwh;
	}

	public Double getCum_import_kvah() {
		return cum_import_kvah;
	}

	public void setCum_import_kvah(Double cum_import_kvah) {
		this.cum_import_kvah = cum_import_kvah;
	}

	public Double getMax_demand_kw() {
		return max_demand_kw;
	}

	public void setMax_demand_kw(Double max_demand_kw) {
		this.max_demand_kw = max_demand_kw;
	}

	public Date getMeter_datetime() {
		return meter_datetime;
	}

	public void setMeter_datetime(Date meter_datetime) {
		this.meter_datetime = meter_datetime;
	}

	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	public String getDt_name() {
		return dt_name;
	}

	public void setDt_name(String dt_name) {
		this.dt_name = dt_name;
	}

	public String getFeeder_name() {
		return feeder_name;
	}

	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getSubdivision_name() {
		return subdivision_name;
	}

	public void setSubdivision_name(String subdivision_name) {
		this.subdivision_name = subdivision_name;
	}

	public String getSubstation_name() {
		return substation_name;
	}

	public void setSubstation_name(String substation_name) {
		this.substation_name = substation_name;
	}

	public Double getCum_export_kwh() {
		return cum_export_kwh;
	}

	public void setCum_export_kwh(Double cum_export_kwh) {
		this.cum_export_kwh = cum_export_kwh;
	}

	public Double getMax_demand_kva() {
		return max_demand_kva;
	}

	public void setMax_demand_kva(Double max_demand_kva) {
		this.max_demand_kva = max_demand_kva;
	}

	public Integer getTamper_count() {
		return tamper_count;
	}

	public void setTamper_count(Integer tamper_count) {
		this.tamper_count = tamper_count;
	}

	public Integer getBill_count() {
		return bill_count;
	}

	public void setBill_count(Integer bill_count) {
		this.bill_count = bill_count;
	}

	public Integer getRelay_status() {
		return relay_status;
	}

	public void setRelay_status(Integer relay_status) {
		this.relay_status = relay_status;
	}

	public Double getLoad_limit() {
		return load_limit;
	}

	public void setLoad_limit(Double load_limit) {
		this.load_limit = load_limit;
	}

	public Double getPower_duration() {
		return power_duration;
	}

	public void setPower_duration(Double power_duration) {
		this.power_duration = power_duration;
	}

	public Double getCum_export_kvah() {
		return cum_export_kvah;
	}

	public void setCum_export_kvah(Double cum_export_kvah) {
		this.cum_export_kvah = cum_export_kvah;
	}

	public Date getMax_demand_kw_datetime() {
		return max_demand_kw_datetime;
	}

	public void setMax_demand_kw_datetime(Date max_demand_kw_datetime) {
		this.max_demand_kw_datetime = max_demand_kw_datetime;
	}

	public Date getMax_demand_kva_datetime() {
		return max_demand_kva_datetime;
	}

	public void setMax_demand_kva_datetime(Date max_demand_kva_datetime) {
		this.max_demand_kva_datetime = max_demand_kva_datetime;
	}

	public Double getCurr_month_kwh_import() {
		return curr_month_kwh_import;
	}

	public void setCurr_month_kwh_import(Double curr_month_kwh_import) {
		this.curr_month_kwh_import = curr_month_kwh_import;
	}

	public Double getCurr_month_kvah_import() {
		return curr_month_kvah_import;
	}

	public void setCurr_month_kvah_import(Double curr_month_kvah_import) {
		this.curr_month_kvah_import = curr_month_kvah_import;
	}

	public Integer getCumm_power_on_duration() {
		return cumm_power_on_duration;
	}

	public void setCumm_power_on_duration(Integer cumm_power_on_duration) {
		this.cumm_power_on_duration = cumm_power_on_duration;
	}

	public Integer getProgram_count() {
		return program_count;
	}

	public void setProgram_count(Integer program_count) {
		this.program_count = program_count;
	}

	public Integer getNo_of_power_failure() {
		return no_of_power_failure;
	}

	public void setNo_of_power_failure(Integer no_of_power_failure) {
		this.no_of_power_failure = no_of_power_failure;
	}

	public Date getBilling_date() {
		return billing_date;
	}

	public void setBilling_date(Date billing_date) {
		this.billing_date = billing_date;
	}

	public Double getTemprature() {
		return temprature;
	}

	public void setTemprature(Double temprature) {
		this.temprature = temprature;
	}

	public Integer getNo_of_load_switch() {
		return no_of_load_switch;
	}

	public void setNo_of_load_switch(Integer no_of_load_switch) {
		this.no_of_load_switch = no_of_load_switch;
	}

	public Integer getCumm_over_voltage_tamper_count() {
		return cumm_over_voltage_tamper_count;
	}

	public void setCumm_over_voltage_tamper_count(Integer cumm_over_voltage_tamper_count) {
		this.cumm_over_voltage_tamper_count = cumm_over_voltage_tamper_count;
	}

	public Integer getCumm_low_voltage_tamper_count() {
		return cumm_low_voltage_tamper_count;
	}

	public void setCumm_low_voltage_tamper_count(Integer cumm_low_voltage_tamper_count) {
		this.cumm_low_voltage_tamper_count = cumm_low_voltage_tamper_count;
	}

	public Integer getCumm_reverse_current_tamper_count() {
		return cumm_reverse_current_tamper_count;
	}

	public void setCumm_reverse_current_tamper_count(Integer cumm_reverse_current_tamper_count) {
		this.cumm_reverse_current_tamper_count = cumm_reverse_current_tamper_count;
	}

	public Integer getCumm_over_current_tamper_count() {
		return cumm_over_current_tamper_count;
	}

	public void setCumm_over_current_tamper_count(Integer cumm_over_current_tamper_count) {
		this.cumm_over_current_tamper_count = cumm_over_current_tamper_count;
	}

	public Integer getCumm_earth_tamper_count() {
		return cumm_earth_tamper_count;
	}

	public void setCumm_earth_tamper_count(Integer cumm_earth_tamper_count) {
		this.cumm_earth_tamper_count = cumm_earth_tamper_count;
	}

	public Integer getCumm_magnet_tamper_count() {
		return cumm_magnet_tamper_count;
	}

	public void setCumm_magnet_tamper_count(Integer cumm_magnet_tamper_count) {
		this.cumm_magnet_tamper_count = cumm_magnet_tamper_count;
	}

	public Integer getCumm_neutral_disturbance_count() {
		return cumm_neutral_disturbance_count;
	}

	public void setCumm_neutral_disturbance_count(Integer cumm_neutral_disturbance_count) {
		this.cumm_neutral_disturbance_count = cumm_neutral_disturbance_count;
	}

	public Integer getCumm_sw_tamper_count() {
		return cumm_sw_tamper_count;
	}

	public void setCumm_sw_tamper_count(Integer cumm_sw_tamper_count) {
		this.cumm_sw_tamper_count = cumm_sw_tamper_count;
	}

	public Integer getCumm_over_load_tamper_count() {
		return cumm_over_load_tamper_count;
	}

	public void setCumm_over_load_tamper_count(Integer cumm_over_load_tamper_count) {
		this.cumm_over_load_tamper_count = cumm_over_load_tamper_count;
	}

	public Integer getCumm_comms_removal_tamper_count() {
		return cumm_comms_removal_tamper_count;
	}

	public void setCumm_comms_removal_tamper_count(Integer cumm_comms_removal_tamper_count) {
		this.cumm_comms_removal_tamper_count = cumm_comms_removal_tamper_count;
	}

	public Integer getCumm_case_open_tamper_count() {
		return cumm_case_open_tamper_count;
	}

	public void setCumm_case_open_tamper_count(Integer cumm_case_open_tamper_count) {
		this.cumm_case_open_tamper_count = cumm_case_open_tamper_count;
	}

	public Integer getCumm_temprature_rise_count() {
		return cumm_temprature_rise_count;
	}

	public void setCumm_temprature_rise_count(Integer cumm_temprature_rise_count) {
		this.cumm_temprature_rise_count = cumm_temprature_rise_count;
	}

	public Integer getCumm_power_failure_duration() {
		return cumm_power_failure_duration;
	}

	public void setCumm_power_failure_duration(Integer cumm_power_failure_duration) {
		this.cumm_power_failure_duration = cumm_power_failure_duration;
	}

	public Integer getRelay_operation_disconnect_count() {
		return relay_operation_disconnect_count;
	}

	public void setRelay_operation_disconnect_count(Integer relay_operation_disconnect_count) {
		this.relay_operation_disconnect_count = relay_operation_disconnect_count;
	}

	public Integer getRelay_operation_connect_count() {
		return relay_operation_connect_count;
	}

	public void setRelay_operation_connect_count(Integer relay_operation_connect_count) {
		this.relay_operation_connect_count = relay_operation_connect_count;
	}

	public Integer getAvg_signal_strength() {
		return avg_signal_strength;
	}

	public void setAvg_signal_strength(Integer avg_signal_strength) {
		this.avg_signal_strength = avg_signal_strength;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Double getAvg_pf_billing_period() {
		return avg_pf_billing_period;
	}

	public void setAvg_pf_billing_period(Double avg_pf_billing_period) {
		this.avg_pf_billing_period = avg_pf_billing_period;
	}

	public Double getMax_demand_kva_export() {
		return max_demand_kva_export;
	}

	public void setMax_demand_kva_export(Double max_demand_kva_export) {
		this.max_demand_kva_export = max_demand_kva_export;
	}

	public Double getMax_demand_kw_export() {
		return max_demand_kw_export;
	}

	public void setMax_demand_kw_export(Double max_demand_kw_export) {
		this.max_demand_kw_export = max_demand_kw_export;
	}

	@Override
	public String toString() {
		return "PushInstantsSinglePhase [device_serial_number=" + device_serial_number + ", device_id=" + device_id
				+ ", voltage=" + voltage + ", phase_current=" + phase_current + ", neutral_current=" + neutral_current
				+ ", signed_pf=" + signed_pf + ", frequency=" + frequency + ", apparent_power_va=" + apparent_power_va
				+ ", active_power_kw=" + active_power_kw + ", cum_import_kwh=" + cum_import_kwh + ", cum_export_kwh="
				+ cum_export_kwh + ", cum_import_kvah=" + cum_import_kvah + ", cum_export_kvah=" + cum_export_kvah
				+ ", max_demand_kw=" + max_demand_kw + ", max_demand_kw_datetime=" + max_demand_kw_datetime
				+ ", max_demand_kva=" + max_demand_kva + ", max_demand_kva_datetime=" + max_demand_kva_datetime
				+ ", tamper_count=" + tamper_count + ", bill_count=" + bill_count + ", relay_status=" + relay_status
				+ ", load_limit=" + load_limit + ", power_duration=" + power_duration + ", meter_datetime="
				+ meter_datetime + ", mdas_datetime=" + mdas_datetime + ", dt_name=" + dt_name + ", feeder_name="
				+ feeder_name + ", owner_name=" + owner_name + ", subdivision_name=" + subdivision_name
				+ ", substation_name=" + substation_name + ", curr_month_kwh_import=" + curr_month_kwh_import
				+ ", curr_month_kvah_import=" + curr_month_kvah_import + ", cumm_power_on_duration="
				+ cumm_power_on_duration + ", program_count=" + program_count + ", no_of_power_failure="
				+ no_of_power_failure + ", billing_date=" + billing_date + ", temprature=" + temprature
				+ ", no_of_load_switch=" + no_of_load_switch + ", cumm_over_voltage_tamper_count="
				+ cumm_over_voltage_tamper_count + ", cumm_low_voltage_tamper_count=" + cumm_low_voltage_tamper_count
				+ ", cumm_reverse_current_tamper_count=" + cumm_reverse_current_tamper_count
				+ ", cumm_over_current_tamper_count=" + cumm_over_current_tamper_count + ", cumm_earth_tamper_count="
				+ cumm_earth_tamper_count + ", cumm_magnet_tamper_count=" + cumm_magnet_tamper_count
				+ ", cumm_neutral_disturbance_count=" + cumm_neutral_disturbance_count + ", cumm_sw_tamper_count="
				+ cumm_sw_tamper_count + ", cumm_over_load_tamper_count=" + cumm_over_load_tamper_count
				+ ", cumm_comms_removal_tamper_count=" + cumm_comms_removal_tamper_count
				+ ", cumm_case_open_tamper_count=" + cumm_case_open_tamper_count + ", cumm_temprature_rise_count="
				+ cumm_temprature_rise_count + ", cumm_power_failure_duration=" + cumm_power_failure_duration
				+ ", relay_operation_disconnect_count=" + relay_operation_disconnect_count
				+ ", relay_operation_connect_count=" + relay_operation_connect_count + ", avg_signal_strength="
				+ avg_signal_strength + ", avg_pf_billing_period=" + avg_pf_billing_period + ", max_demand_kva_export="
				+ max_demand_kva_export + ", max_demand_kw_export=" + max_demand_kw_export + "]";
	}

}
