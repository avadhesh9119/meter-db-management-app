package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "event_data_threephase")
public class EventDataThreePhase implements Serializable {

	private static final long serialVersionUID = -5116915695400120904L;

	@PrimaryKey
	private String device_serial_number;

	private Integer event_code;
    private Date event_datetime;
    private Double cumulative_energy_kwh_export;
    private Double cumulative_energy_kwh_import;
    private Integer cumulative_tamper_count;
    private Double current_ib;
    private Double current_ir;
    private Double current_iy;
    private String event_category;
    private String event_type;
    private Date mdas_datetime;
    private Date meter_datetime;
    private Double power_factor_b_phase;
    private Double power_factor_r_phase;
    private Double power_factor_y_phase;
    private Double voltage_vbn;
    private Double voltage_vrn;
    private Double voltage_vyn;
    private String feeder_name;
    private String owner_name;
    private String subdevision_name;
    private String substation_name;
    private String dcu_serial_number;
    private String dt_name;
    private Double cumulative_energy_kvah_export;
    private Double cumulative_energy_kvah_import;
    private Double neutral_current;
    
	public EventDataThreePhase() {
		super();
	}

	public EventDataThreePhase(String device_serial_number, Integer event_code, Date event_datetime,
			Double cumulative_energy_kwh_export, Double cumulative_energy_kwh_import, Integer cumulative_tamper_count,
			Double current_ib, Double current_ir, Double current_iy, String event_category, String event_type,
			Date mdas_datetime, Date meter_datetime, Double power_factor_b_phase, Double power_factor_r_phase,
			Double power_factor_y_phase, Double voltage_vbn, Double voltage_vrn, Double voltage_vyn, String feeder_name,
			String owner_name, String subdevision_name, String substation_name, String dcu_serial_number,
			String dt_name) {
		super();
		this.device_serial_number = device_serial_number;
		this.event_code = event_code;
		this.event_datetime = event_datetime;
		this.cumulative_energy_kwh_export = cumulative_energy_kwh_export;
		this.cumulative_energy_kwh_import = cumulative_energy_kwh_import;
		this.cumulative_tamper_count = cumulative_tamper_count;
		this.current_ib = current_ib;
		this.current_ir = current_ir;
		this.current_iy = current_iy;
		this.event_category = event_category;
		this.event_type = event_type;
		this.mdas_datetime = mdas_datetime;
		this.meter_datetime = meter_datetime;
		this.power_factor_b_phase = power_factor_b_phase;
		this.power_factor_r_phase = power_factor_r_phase;
		this.power_factor_y_phase = power_factor_y_phase;
		this.voltage_vbn = voltage_vbn;
		this.voltage_vrn = voltage_vrn;
		this.voltage_vyn = voltage_vyn;
		this.feeder_name = feeder_name;
		this.owner_name = owner_name;
		this.subdevision_name = subdevision_name;
		this.substation_name = substation_name;
		this.dcu_serial_number = dcu_serial_number;
		this.dt_name = dt_name;
	}

	/**
	 * @return the device_serial_number
	 */
	public String getDevice_serial_number() {
		return device_serial_number;
	}

	/**
	 * @param device_serial_number the device_serial_number to set
	 */
	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	/**
	 * @return the event_code
	 */
	public Integer getEvent_code() {
		return event_code;
	}

	/**
	 * @param event_code the event_code to set
	 */
	public void setEvent_code(Integer event_code) {
		this.event_code = event_code;
	}

	/**
	 * @return the event_datetime
	 */
	public Date getEvent_datetime() {
		return event_datetime;
	}

	/**
	 * @param event_datetime the event_datetime to set
	 */
	public void setEvent_datetime(Date event_datetime) {
		this.event_datetime = event_datetime;
	}

	/**
	 * @return the cumulative_energy_kwh_export
	 */
	public Double getCumulative_energy_kwh_export() {
		return cumulative_energy_kwh_export;
	}

	/**
	 * @param cumulative_energy_kwh_export the cumulative_energy_kwh_export to set
	 */
	public void setCumulative_energy_kwh_export(Double cumulative_energy_kwh_export) {
		this.cumulative_energy_kwh_export = cumulative_energy_kwh_export;
	}

	/**
	 * @return the cumulative_energy_kwh_import
	 */
	public Double getCumulative_energy_kwh_import() {
		return cumulative_energy_kwh_import;
	}

	/**
	 * @param cumulative_energy_kwh_import the cumulative_energy_kwh_import to set
	 */
	public void setCumulative_energy_kwh_import(Double cumulative_energy_kwh_import) {
		this.cumulative_energy_kwh_import = cumulative_energy_kwh_import;
	}

	/**
	 * @return the cumulative_tamper_count
	 */
	public Integer getCumulative_tamper_count() {
		return cumulative_tamper_count;
	}

	/**
	 * @param cumulative_tamper_count the cumulative_tamper_count to set
	 */
	public void setCumulative_tamper_count(Integer cumulative_tamper_count) {
		this.cumulative_tamper_count = cumulative_tamper_count;
	}

	/**
	 * @return the current_ib
	 */
	public Double getCurrent_ib() {
		return current_ib;
	}

	/**
	 * @param current_ib the current_ib to set
	 */
	public void setCurrent_ib(Double current_ib) {
		this.current_ib = current_ib;
	}

	/**
	 * @return the current_ir
	 */
	public Double getCurrent_ir() {
		return current_ir;
	}

	/**
	 * @param current_ir the current_ir to set
	 */
	public void setCurrent_ir(Double current_ir) {
		this.current_ir = current_ir;
	}

	/**
	 * @return the current_iy
	 */
	public Double getCurrent_iy() {
		return current_iy;
	}

	/**
	 * @param current_iy the current_iy to set
	 */
	public void setCurrent_iy(Double current_iy) {
		this.current_iy = current_iy;
	}

	/**
	 * @return the event_category
	 */
	public String getEvent_category() {
		return event_category;
	}

	/**
	 * @param event_category the event_category to set
	 */
	public void setEvent_category(String event_category) {
		this.event_category = event_category;
	}

	/**
	 * @return the event_type
	 */
	public String getEvent_type() {
		return event_type;
	}

	/**
	 * @param event_type the event_type to set
	 */
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	/**
	 * @return the mdas_datetime
	 */
	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	/**
	 * @param mdas_datetime the mdas_datetime to set
	 */
	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	/**
	 * @return the meter_datetime
	 */
	public Date getMeter_datetime() {
		return meter_datetime;
	}

	/**
	 * @param meter_datetime the meter_datetime to set
	 */
	public void setMeter_datetime(Date meter_datetime) {
		this.meter_datetime = meter_datetime;
	}

	/**
	 * @return the power_factor_b_phase
	 */
	public Double getPower_factor_b_phase() {
		return power_factor_b_phase;
	}

	/**
	 * @param power_factor_b_phase the power_factor_b_phase to set
	 */
	public void setPower_factor_b_phase(Double power_factor_b_phase) {
		this.power_factor_b_phase = power_factor_b_phase;
	}

	/**
	 * @return the power_factor_r_phase
	 */
	public Double getPower_factor_r_phase() {
		return power_factor_r_phase;
	}

	/**
	 * @param power_factor_r_phase the power_factor_r_phase to set
	 */
	public void setPower_factor_r_phase(Double power_factor_r_phase) {
		this.power_factor_r_phase = power_factor_r_phase;
	}

	/**
	 * @return the power_factor_y_phase
	 */
	public Double getPower_factor_y_phase() {
		return power_factor_y_phase;
	}

	/**
	 * @param power_factor_y_phase the power_factor_y_phase to set
	 */
	public void setPower_factor_y_phase(Double power_factor_y_phase) {
		this.power_factor_y_phase = power_factor_y_phase;
	}

	/**
	 * @return the voltage_vbn
	 */
	public Double getVoltage_vbn() {
		return voltage_vbn;
	}

	/**
	 * @param voltage_vbn the voltage_vbn to set
	 */
	public void setVoltage_vbn(Double voltage_vbn) {
		this.voltage_vbn = voltage_vbn;
	}

	/**
	 * @return the voltage_vrn
	 */
	public Double getVoltage_vrn() {
		return voltage_vrn;
	}

	/**
	 * @param voltage_vrn the voltage_vrn to set
	 */
	public void setVoltage_vrn(Double voltage_vrn) {
		this.voltage_vrn = voltage_vrn;
	}

	/**
	 * @return the voltage_vyn
	 */
	public Double getVoltage_vyn() {
		return voltage_vyn;
	}

	/**
	 * @param voltage_vyn the voltage_vyn to set
	 */
	public void setVoltage_vyn(Double voltage_vyn) {
		this.voltage_vyn = voltage_vyn;
	}
	
	/**
	 * @return the feeder_name
	 */
	public String getFeeder_name() {
		return feeder_name;
	}

	/**
	 * @param feeder_name the feeder_name to set
	 */
	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
	}

	/**
	 * @return the owner_name
	 */
	public String getOwner_name() {
		return owner_name;
	}

	/**
	 * @param owner_name the owner_name to set
	 */
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	/**
	 * @return the subdevision_name
	 */
	public String getSubdevision_name() {
		return subdevision_name;
	}

	/**
	 * @param subdevision_name the subdevision_name to set
	 */
	public void setSubdevision_name(String subdevision_name) {
		this.subdevision_name = subdevision_name;
	}

	/**
	 * @return the substation_name
	 */
	public String getSubstation_name() {
		return substation_name;
	}

	/**
	 * @param substation_name the substation_name to set
	 */
	public void setSubstation_name(String substation_name) {
		this.substation_name = substation_name;
	}

	/**
	 * @return the dcu_serial_number
	 */
	public String getDcu_serial_number() {
		return dcu_serial_number;
	}

	/**
	 * @param dcu_serial_number the dcu_serial_number to set
	 */
	public void setDcu_serial_number(String dcu_serial_number) {
		this.dcu_serial_number = dcu_serial_number;
	}

	/**
	 * @return the dt_name
	 */
	public String getDt_name() {
		return dt_name;
	}

	/**
	 * @param dt_name the dt_name to set
	 */
	public void setDt_name(String dt_name) {
		this.dt_name = dt_name;
	}

	public Double getCumulative_energy_kvah_export() {
		return cumulative_energy_kvah_export;
	}

	public void setCumulative_energy_kvah_export(Double cumulative_energy_kvah_export) {
		this.cumulative_energy_kvah_export = cumulative_energy_kvah_export;
	}

	public Double getCumulative_energy_kvah_import() {
		return cumulative_energy_kvah_import;
	}

	public void setCumulative_energy_kvah_import(Double cumulative_energy_kvah_import) {
		this.cumulative_energy_kvah_import = cumulative_energy_kvah_import;
	}

	public Double getNeutral_current() {
		return neutral_current;
	}

	public void setNeutral_current(Double neutral_current) {
		this.neutral_current = neutral_current;
	}

	@Override
	public String toString() {
		return "EventDataThreePhase [device_serial_number=" + device_serial_number + ", event_code=" + event_code
				+ ", event_datetime=" + event_datetime + ", cumulative_energy_kwh_export="
				+ cumulative_energy_kwh_export + ", cumulative_energy_kwh_import=" + cumulative_energy_kwh_import
				+ ", cumulative_tamper_count=" + cumulative_tamper_count + ", current_ib=" + current_ib
				+ ", current_ir=" + current_ir + ", current_iy=" + current_iy + ", event_category=" + event_category
				+ ", event_type=" + event_type + ", mdas_datetime=" + mdas_datetime + ", meter_datetime="
				+ meter_datetime + ", power_factor_b_phase=" + power_factor_b_phase + ", power_factor_r_phase="
				+ power_factor_r_phase + ", power_factor_y_phase=" + power_factor_y_phase + ", voltage_vbn="
				+ voltage_vbn + ", voltage_vrn=" + voltage_vrn + ", voltage_vyn=" + voltage_vyn + ", feeder_name="
				+ feeder_name + ", owner_name=" + owner_name + ", subdevision_name=" + subdevision_name
				+ ", substation_name=" + substation_name + ", dcu_serial_number=" + dcu_serial_number + ", dt_name="
				+ dt_name + ", cumulative_energy_kvah_export=" + cumulative_energy_kvah_export
				+ ", cumulative_energy_kvah_import=" + cumulative_energy_kvah_import + ", neutral_current="
				+ neutral_current + "]";
	}

}
