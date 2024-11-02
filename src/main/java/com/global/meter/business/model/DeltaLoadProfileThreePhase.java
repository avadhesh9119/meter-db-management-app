package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "load_profile_data_threephase")
public class DeltaLoadProfileThreePhase implements Serializable{

	private static final long serialVersionUID = 2284736758614945005L;

	@PrimaryKey
	private String device_serial_number;

	private Date interval_datetime;
    private Double block_energy_kvah_export;
    private Double block_energy_kvah_import;
    private Double block_energy_kwh_export;
    private Double block_energy_kwh_import;
    private String dcu_serial_number;
    private String dt_name;
    private String feeder_name;
    private Date mdas_datetime;
    private String owner_name;
    private Double phase_current_l1;
    private Double phase_current_l2;
    private Double phase_current_l3;
    private Double phase_voltage_l1;
    private Double phase_voltage_l2;
    private Double phase_voltage_l3;
    private String subdevision_name;
    private String substation_name;
    private Double cumulative_energy_kvarh_q1;
    private Double cumulative_energy_kvarh_q2;
    private Double cumulative_energy_kvarh_q3;
    private Double cumulative_energy_kvarh_q4;
    private Double status_byte;
    private Double avg_signal_strength;
    private Integer power_downtime_in_mins;
    private Double r_phase_active_power_kw;
    private Double y_phase_active_power_kw;
    private Double b_phase_active_power_kw;
    private Double lead_block_energy_kvarh;
    private Double cumulative_energy_kwh;
    private Double cumulative_energy_kvah;
    private Double md_type;
    private Double md_block_sliding_type;
    private Double neutral_current;
    private Double mobile_no;
    private Double instant_avgpf;
    private Double module_rtc;
    private Double kva_max;
    private Double max_current;
    private Double max_voltage;
    
	public DeltaLoadProfileThreePhase() {
		super();
	}


	
	
	public DeltaLoadProfileThreePhase(String device_serial_number, Date interval_datetime,
			Double block_energy_kvah_export, Double block_energy_kvah_import, Double block_energy_kwh_export,
			Double block_energy_kwh_import, String dcu_serial_number, String dt_name, String feeder_name,
			Date mdas_datetime, String owner_name, Double phase_current_l1, Double phase_current_l2,
			Double phase_current_l3, Double phase_voltage_l1, Double phase_voltage_l2, Double phase_voltage_l3,
			String subdevision_name, String substation_name, Double cumulative_energy_kvarh_q1,
			Double cumulative_energy_kvarh_q2, Double cumulative_energy_kvarh_q3, Double cumulative_energy_kvarh_q4,
			Double status_byte, Double avg_signal_strength) {
		super();
		this.device_serial_number = device_serial_number;
		this.interval_datetime = interval_datetime;
		this.block_energy_kvah_export = block_energy_kvah_export;
		this.block_energy_kvah_import = block_energy_kvah_import;
		this.block_energy_kwh_export = block_energy_kwh_export;
		this.block_energy_kwh_import = block_energy_kwh_import;
		this.dcu_serial_number = dcu_serial_number;
		this.dt_name = dt_name;
		this.feeder_name = feeder_name;
		this.mdas_datetime = mdas_datetime;
		this.owner_name = owner_name;
		this.phase_current_l1 = phase_current_l1;
		this.phase_current_l2 = phase_current_l2;
		this.phase_current_l3 = phase_current_l3;
		this.phase_voltage_l1 = phase_voltage_l1;
		this.phase_voltage_l2 = phase_voltage_l2;
		this.phase_voltage_l3 = phase_voltage_l3;
		this.subdevision_name = subdevision_name;
		this.substation_name = substation_name;
		this.cumulative_energy_kvarh_q1 = cumulative_energy_kvarh_q1;
		this.cumulative_energy_kvarh_q2 = cumulative_energy_kvarh_q2;
		this.cumulative_energy_kvarh_q3 = cumulative_energy_kvarh_q3;
		this.cumulative_energy_kvarh_q4 = cumulative_energy_kvarh_q4;
		this.status_byte = status_byte;
		this.avg_signal_strength = avg_signal_strength;
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
	 * @return the interval_datetime
	 */
	public Date getInterval_datetime() {
		return interval_datetime;
	}
	/**
	 * @param interval_datetime the interval_datetime to set
	 */
	public void setInterval_datetime(Date interval_datetime) {
		this.interval_datetime = interval_datetime;
	}
	/**
	 * @return the block_energy_kvah_export
	 */
	public Double getBlock_energy_kvah_export() {
		return block_energy_kvah_export;
	}
	/**
	 * @param block_energy_kvah_export the block_energy_kvah_export to set
	 */
	public void setBlock_energy_kvah_export(Double block_energy_kvah_export) {
		this.block_energy_kvah_export = block_energy_kvah_export;
	}
	/**
	 * @return the block_energy_kvah_import
	 */
	public Double getBlock_energy_kvah_import() {
		return block_energy_kvah_import;
	}
	/**
	 * @param block_energy_kvah_import the block_energy_kvah_import to set
	 */
	public void setBlock_energy_kvah_import(Double block_energy_kvah_import) {
		this.block_energy_kvah_import = block_energy_kvah_import;
	}
	/**
	 * @return the block_energy_kwh_export
	 */
	public Double getBlock_energy_kwh_export() {
		return block_energy_kwh_export;
	}
	/**
	 * @param block_energy_kwh_export the block_energy_kwh_export to set
	 */
	public void setBlock_energy_kwh_export(Double block_energy_kwh_export) {
		this.block_energy_kwh_export = block_energy_kwh_export;
	}
	/**
	 * @return the block_energy_kwh_import
	 */
	public Double getBlock_energy_kwh_import() {
		return block_energy_kwh_import;
	}
	/**
	 * @param block_energy_kwh_import the block_energy_kwh_import to set
	 */
	public void setBlock_energy_kwh_import(Double block_energy_kwh_import) {
		this.block_energy_kwh_import = block_energy_kwh_import;
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
	 * @return the phase_current_l1
	 */
	public Double getPhase_current_l1() {
		return phase_current_l1;
	}
	/**
	 * @param phase_current_l1 the phase_current_l1 to set
	 */
	public void setPhase_current_l1(Double phase_current_l1) {
		this.phase_current_l1 = phase_current_l1;
	}
	/**
	 * @return the phase_current_l2
	 */
	public Double getPhase_current_l2() {
		return phase_current_l2;
	}
	/**
	 * @param phase_current_l2 the phase_current_l2 to set
	 */
	public void setPhase_current_l2(Double phase_current_l2) {
		this.phase_current_l2 = phase_current_l2;
	}
	/**
	 * @return the phase_current_l3
	 */
	public Double getPhase_current_l3() {
		return phase_current_l3;
	}
	/**
	 * @param phase_current_l3 the phase_current_l3 to set
	 */
	public void setPhase_current_l3(Double phase_current_l3) {
		this.phase_current_l3 = phase_current_l3;
	}
	/**
	 * @return the phase_voltage_l1
	 */
	public Double getPhase_voltage_l1() {
		return phase_voltage_l1;
	}
	/**
	 * @param phase_voltage_l1 the phase_voltage_l1 to set
	 */
	public void setPhase_voltage_l1(Double phase_voltage_l1) {
		this.phase_voltage_l1 = phase_voltage_l1;
	}
	/**
	 * @return the phase_voltage_l2
	 */
	public Double getPhase_voltage_l2() {
		return phase_voltage_l2;
	}
	/**
	 * @param phase_voltage_l2 the phase_voltage_l2 to set
	 */
	public void setPhase_voltage_l2(Double phase_voltage_l2) {
		this.phase_voltage_l2 = phase_voltage_l2;
	}
	/**
	 * @return the phase_voltage_l3
	 */
	public Double getPhase_voltage_l3() {
		return phase_voltage_l3;
	}
	/**
	 * @param phase_voltage_l3 the phase_voltage_l3 to set
	 */
	public void setPhase_voltage_l3(Double phase_voltage_l3) {
		this.phase_voltage_l3 = phase_voltage_l3;
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

	public Double getCumulative_energy_kvarh_q1() {
		return cumulative_energy_kvarh_q1;
	}

	public void setCumulative_energy_kvarh_q1(Double cumulative_energy_kvarh_q1) {
		this.cumulative_energy_kvarh_q1 = cumulative_energy_kvarh_q1;
	}

	public Double getCumulative_energy_kvarh_q2() {
		return cumulative_energy_kvarh_q2;
	}

	public void setCumulative_energy_kvarh_q2(Double cumulative_energy_kvarh_q2) {
		this.cumulative_energy_kvarh_q2 = cumulative_energy_kvarh_q2;
	}

	public Double getCumulative_energy_kvarh_q3() {
		return cumulative_energy_kvarh_q3;
	}

	public void setCumulative_energy_kvarh_q3(Double cumulative_energy_kvarh_q3) {
		this.cumulative_energy_kvarh_q3 = cumulative_energy_kvarh_q3;
	}

	public Double getCumulative_energy_kvarh_q4() {
		return cumulative_energy_kvarh_q4;
	}

	public void setCumulative_energy_kvarh_q4(Double cumulative_energy_kvarh_q4) {
		this.cumulative_energy_kvarh_q4 = cumulative_energy_kvarh_q4;
	}

	public Double getStatus_byte() {
		return status_byte;
	}

	public void setStatus_byte(Double status_byte) {
		this.status_byte = status_byte;
	}

	public Double getAvg_signal_strength() {
		return avg_signal_strength;
	}

	public void setAvg_signal_strength(Double avg_signal_strength) {
		this.avg_signal_strength = avg_signal_strength;
	}

	public Integer getPower_downtime_in_mins() {
		return power_downtime_in_mins;
	}

	public void setPower_downtime_in_mins(Integer power_downtime_in_mins) {
		this.power_downtime_in_mins = power_downtime_in_mins;
	}
	public Double getR_phase_active_power_kw() {
		return r_phase_active_power_kw;
	}

	public void setR_phase_active_power_kw(Double r_phase_active_power_kw) {
		this.r_phase_active_power_kw = r_phase_active_power_kw;
	}

	public Double getY_phase_active_power_kw() {
		return y_phase_active_power_kw;
	}

	public void setY_phase_active_power_kw(Double y_phase_active_power_kw) {
		this.y_phase_active_power_kw = y_phase_active_power_kw;
	}

	public Double getB_phase_active_power_kw() {
		return b_phase_active_power_kw;
	}

	public void setB_phase_active_power_kw(Double b_phase_active_power_kw) {
		this.b_phase_active_power_kw = b_phase_active_power_kw;
	}

	public Double getLead_block_energy_kvarh() {
		return lead_block_energy_kvarh;
	}

	public void setLead_block_energy_kvarh(Double lead_block_energy_kvarh) {
		this.lead_block_energy_kvarh = lead_block_energy_kvarh;
	}

	public Double getCumulative_energy_kwh() {
		return cumulative_energy_kwh;
	}

	public void setCumulative_energy_kwh(Double cumulative_energy_kwh) {
		this.cumulative_energy_kwh = cumulative_energy_kwh;
	}

	public Double getCumulative_energy_kvah() {
		return cumulative_energy_kvah;
	}

	public void setCumulative_energy_kvah(Double cumulative_energy_kvah) {
		this.cumulative_energy_kvah = cumulative_energy_kvah;
	}

	public Double getMd_type() {
		return md_type;
	}

	public void setMd_type(Double md_type) {
		this.md_type = md_type;
	}

	public Double getMd_block_sliding_type() {
		return md_block_sliding_type;
	}

	public void setMd_block_sliding_type(Double md_block_sliding_type) {
		this.md_block_sliding_type = md_block_sliding_type;
	}

	public Double getNeutral_current() {
		return neutral_current;
	}

	public void setNeutral_current(Double neutral_current) {
		this.neutral_current = neutral_current;
	}

	public Double getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(Double mobile_no) {
		this.mobile_no = mobile_no;
	}

	public Double getInstant_avgpf() {
		return instant_avgpf;
	}

	public void setInstant_avgpf(Double instant_avgpf) {
		this.instant_avgpf = instant_avgpf;
	}

	public Double getModule_rtc() {
		return module_rtc;
	}

	public void setModule_rtc(Double module_rtc) {
		this.module_rtc = module_rtc;
	}

	public Double getKva_max() {
		return kva_max;
	}

	public void setKva_max(Double kva_max) {
		this.kva_max = kva_max;
	}

	public Double getMax_current() {
		return max_current;
	}

	public void setMax_current(Double max_current) {
		this.max_current = max_current;
	}

	public Double getMax_voltage() {
		return max_voltage;
	}

	public void setMax_voltage(Double max_voltage) {
		this.max_voltage = max_voltage;
	}




	@Override
	public String toString() {
		return "DeltaLoadProfileThreePhase [device_serial_number=" + device_serial_number + ", interval_datetime="
				+ interval_datetime + ", block_energy_kvah_export=" + block_energy_kvah_export
				+ ", block_energy_kvah_import=" + block_energy_kvah_import + ", block_energy_kwh_export="
				+ block_energy_kwh_export + ", block_energy_kwh_import=" + block_energy_kwh_import
				+ ", dcu_serial_number=" + dcu_serial_number + ", dt_name=" + dt_name + ", feeder_name=" + feeder_name
				+ ", mdas_datetime=" + mdas_datetime + ", owner_name=" + owner_name + ", phase_current_l1="
				+ phase_current_l1 + ", phase_current_l2=" + phase_current_l2 + ", phase_current_l3=" + phase_current_l3
				+ ", phase_voltage_l1=" + phase_voltage_l1 + ", phase_voltage_l2=" + phase_voltage_l2
				+ ", phase_voltage_l3=" + phase_voltage_l3 + ", subdevision_name=" + subdevision_name
				+ ", substation_name=" + substation_name + ", cumulative_energy_kvarh_q1=" + cumulative_energy_kvarh_q1
				+ ", cumulative_energy_kvarh_q2=" + cumulative_energy_kvarh_q2 + ", cumulative_energy_kvarh_q3="
				+ cumulative_energy_kvarh_q3 + ", cumulative_energy_kvarh_q4=" + cumulative_energy_kvarh_q4
				+ ", status_byte=" + status_byte + ", avg_signal_strength=" + avg_signal_strength
				+ ", power_downtime_in_mins=" + power_downtime_in_mins + ", r_phase_active_power_kw="
				+ r_phase_active_power_kw + ", y_phase_active_power_kw=" + y_phase_active_power_kw
				+ ", b_phase_active_power_kw=" + b_phase_active_power_kw + ", lead_block_energy_kvarh="
				+ lead_block_energy_kvarh + ", cumulative_energy_kwh=" + cumulative_energy_kwh
				+ ", cumulative_energy_kvah=" + cumulative_energy_kvah + ", md_type=" + md_type
				+ ", md_block_sliding_type=" + md_block_sliding_type + ", neutral_current=" + neutral_current
				+ ", mobile_no=" + mobile_no + ", instant_avgpf=" + instant_avgpf + ", module_rtc=" + module_rtc
				+ ", kva_max=" + kva_max + ", max_current=" + max_current + ", max_voltage=" + max_voltage + "]";
	}
	
}
