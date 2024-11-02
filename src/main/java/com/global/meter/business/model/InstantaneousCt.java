package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "instantaneous_data_ct")
public class InstantaneousCt  implements Serializable{

	private static final long serialVersionUID = 4482752534060586260L;
	
	@PrimaryKey
 	private String device_serial_number;
	private String tracking_id;
 	
    private Date datetime;
    private Double active_power_kw;
    private Double apparent_power_kva;
    private Double cumm_power_off_duration_in_mins;
    private Integer cumulative_bill_count;
    private Double cumulative_energy_kvah_export;
    private Double cumulative_energy_kvah_import;
    private Double cumulative_energy_kvarh_q1;
    private Double cumulative_energy_kvarh_q2;
    private Double cumulative_energy_kvarh_q3;
    private Double cumulative_energy_kvarh_q4;
    private Double cumulative_energy_kwh_export;
    private Double cumulative_energy_kwh_import;
    private Integer cumulative_program_count;
    private Integer cumulative_tamper_count;
    private String dcu_serial_number;
    private String dt_name;
    private Integer enable_disable_load;
    private String feeder_name;
    private Double frequency;
    private Double instant_voltage_l1;
    private Double instant_voltage_l2;
    private Double instant_voltage_l3;
    private Date last_billing_datetime;
    private Integer load_limit;
    private Integer load_limit_status;
    private Double maximum_demand_kva;
    private Date maximum_demand_kva_datetime;
    private Double maximum_demand_kw;
    private Date maximum_demand_kw_datetime;
    private Date mdas_datetime;
    private Date meter_datetime;
    private Integer no_of_power_failure;
    private String owner_name;
    private Double phase_current_l1;
    private Double phase_current_l2;
    private Double phase_current_l3;
    private Double power_factor_3ph;
    private Double power_factor_l1;
    private Double power_factor_l2;
    private Double power_factor_l3;
    private Double reactive_power_kvar;
    private String subdevision_name;
    private String substation_name;
    private Double signed_power_factor_b_phase;
    private Double maximum_demand_w_export;
    private Date maximum_demand_w_export_datetime;
    private Double maximum_demand_va_export;
    private Date maximum_demand_va_export_datetime;
    private Double angle_phase_volt_ab;
    private Double angle_phase_volt_bc;
    private Double angle_phase_volt_ac;
    private Double cumm_power_on_duration_in_mins;
    
    
	public InstantaneousCt() {
		super();
	}
	
	public InstantaneousCt(String device_serial_number, String tracking_id, Date datetime,
			Double active_power_kw, Double apparent_power_kva, Double cumm_power_off_duration_in_mins,
			Integer cumulative_bill_count, Double cumulative_energy_kvah_export,
			Double cumulative_energy_kvah_import, Double cumulative_energy_kvarh_q1,
			Double cumulative_energy_kvarh_q2, Double cumulative_energy_kvarh_q3, Double cumulative_energy_kvarh_q4,
			Double cumulative_energy_kwh_export, Double cumulative_energy_kwh_import,
			Integer cumulative_program_count, Integer cumulative_tamper_count,
			String dcu_serial_number, String dt_name, Integer enable_disable_load, String feeder_name,
			Double frequency, Double instant_voltage_l1, Double instant_voltage_l2, Double instant_voltage_l3,
			Date last_billing_datetime, Integer load_limit, Integer load_limit_status, Double maximum_demand_kva,
			Date maximum_demand_kva_datetime, Double maximum_demand_kw, Date maximum_demand_kw_datetime,
			Date mdas_datetime, Date meter_datetime, Integer no_of_power_failure, String owner_name,
			Double phase_current_l1, Double phase_current_l2, Double phase_current_l3, Double power_factor_3ph,
			Double power_factor_l1, Double power_factor_l2, Double power_factor_l3, Double reactive_power_kvar,
			String subdevision_name, String substation_name) {
		super();
		this.device_serial_number = device_serial_number;
		this.tracking_id = tracking_id;
		this.datetime = datetime;
		this.active_power_kw = active_power_kw;
		this.apparent_power_kva = apparent_power_kva;
		this.cumm_power_off_duration_in_mins = cumm_power_off_duration_in_mins;
		this.cumulative_bill_count = cumulative_bill_count;
		this.cumulative_energy_kvah_export = cumulative_energy_kvah_export;
		this.cumulative_energy_kvah_import = cumulative_energy_kvah_import;
		this.cumulative_energy_kvarh_q1 = cumulative_energy_kvarh_q1;
		this.cumulative_energy_kvarh_q2 = cumulative_energy_kvarh_q2;
		this.cumulative_energy_kvarh_q3 = cumulative_energy_kvarh_q3;
		this.cumulative_energy_kvarh_q4 = cumulative_energy_kvarh_q4;
		this.cumulative_energy_kwh_export = cumulative_energy_kwh_export;
		this.cumulative_energy_kwh_import = cumulative_energy_kwh_import;
		this.cumulative_program_count = cumulative_program_count;
		this.cumulative_tamper_count = cumulative_tamper_count;
		this.dcu_serial_number = dcu_serial_number;
		this.dt_name = dt_name;
		this.enable_disable_load = enable_disable_load;
		this.feeder_name = feeder_name;
		this.frequency = frequency;
		this.instant_voltage_l1 = instant_voltage_l1;
		this.instant_voltage_l2 = instant_voltage_l2;
		this.instant_voltage_l3 = instant_voltage_l3;
		this.last_billing_datetime = last_billing_datetime;
		this.load_limit = load_limit;
		this.load_limit_status = load_limit_status;
		this.maximum_demand_kva = maximum_demand_kva;
		this.maximum_demand_kva_datetime = maximum_demand_kva_datetime;
		this.maximum_demand_kw = maximum_demand_kw;
		this.maximum_demand_kw_datetime = maximum_demand_kw_datetime;
		this.mdas_datetime = mdas_datetime;
		this.meter_datetime = meter_datetime;
		this.no_of_power_failure = no_of_power_failure;
		this.owner_name = owner_name;
		this.phase_current_l1 = phase_current_l1;
		this.phase_current_l2 = phase_current_l2;
		this.phase_current_l3 = phase_current_l3;
		this.power_factor_3ph = power_factor_3ph;
		this.power_factor_l1 = power_factor_l1;
		this.power_factor_l2 = power_factor_l2;
		this.power_factor_l3 = power_factor_l3;
		this.reactive_power_kvar = reactive_power_kvar;
		this.subdevision_name = subdevision_name;
		this.substation_name = substation_name;
	}




	public Double getReactive_power_kvar() {
		return reactive_power_kvar;
	}

	public void setReactive_power_kvar(Double reactive_power_kvar) {
		this.reactive_power_kvar = reactive_power_kvar;
	}

	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public String getTracking_id() {
		return tracking_id;
	}

	public void setTracking_id(String tracking_id) {
		this.tracking_id = tracking_id;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Double getActive_power_kw() {
		return active_power_kw;
	}

	public void setActive_power_kw(Double active_power_kw) {
		this.active_power_kw = active_power_kw;
	}

	public Double getApparent_power_kva() {
		return apparent_power_kva;
	}

	public void setApparent_power_kva(Double apparent_power_kva) {
		this.apparent_power_kva = apparent_power_kva;
	}

	public Double getCumm_power_off_duration_in_mins() {
		return cumm_power_off_duration_in_mins;
	}

	public void setCumm_power_off_duration_in_mins(Double cumm_power_off_duration_in_mins) {
		this.cumm_power_off_duration_in_mins = cumm_power_off_duration_in_mins;
	}

	public Integer getCumulative_bill_count() {
		return cumulative_bill_count;
	}

	public void setCumulative_bill_count(Integer cumulative_bill_count) {
		this.cumulative_bill_count = cumulative_bill_count;
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

	public Double getCumulative_energy_kwh_export() {
		return cumulative_energy_kwh_export;
	}

	public void setCumulative_energy_kwh_export(Double cumulative_energy_kwh_export) {
		this.cumulative_energy_kwh_export = cumulative_energy_kwh_export;
	}

	public Double getCumulative_energy_kwh_import() {
		return cumulative_energy_kwh_import;
	}

	public void setCumulative_energy_kwh_import(Double cumulative_energy_kwh_import) {
		this.cumulative_energy_kwh_import = cumulative_energy_kwh_import;
	}

	public Integer getCumulative_program_count() {
		return cumulative_program_count;
	}

	public void setCumulative_program_count(Integer cumulative_program_count) {
		this.cumulative_program_count = cumulative_program_count;
	}

	public Integer getCumulative_tamper_count() {
		return cumulative_tamper_count;
	}

	public void setCumulative_tamper_count(Integer cumulative_tamper_count) {
		this.cumulative_tamper_count = cumulative_tamper_count;
	}

	public String getDcu_serial_number() {
		return dcu_serial_number;
	}

	public void setDcu_serial_number(String dcu_serial_number) {
		this.dcu_serial_number = dcu_serial_number;
	}

	public String getDt_name() {
		return dt_name;
	}

	public void setDt_name(String dt_name) {
		this.dt_name = dt_name;
	}

	public Integer getEnable_disable_load() {
		return enable_disable_load;
	}

	public void setEnable_disable_load(Integer enable_disable_load) {
		this.enable_disable_load = enable_disable_load;
	}

	public String getFeeder_name() {
		return feeder_name;
	}

	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
	}

	public Double getFrequency() {
		return frequency;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}

	public Double getInstant_voltage_l1() {
		return instant_voltage_l1;
	}

	public void setInstant_voltage_l1(Double instant_voltage_l1) {
		this.instant_voltage_l1 = instant_voltage_l1;
	}

	public Double getInstant_voltage_l2() {
		return instant_voltage_l2;
	}

	public void setInstant_voltage_l2(Double instant_voltage_l2) {
		this.instant_voltage_l2 = instant_voltage_l2;
	}

	public Double getInstant_voltage_l3() {
		return instant_voltage_l3;
	}

	public void setInstant_voltage_l3(Double instant_voltage_l3) {
		this.instant_voltage_l3 = instant_voltage_l3;
	}

	public Date getLast_billing_datetime() {
		return last_billing_datetime;
	}

	public void setLast_billing_datetime(Date last_billing_datetime) {
		this.last_billing_datetime = last_billing_datetime;
	}
	
	public Integer getLoad_limit() {
		return load_limit;
	}

	public void setLoad_limit(Integer load_limit) {
		this.load_limit = load_limit;
	}

	public Integer getLoad_limit_status() {
		return load_limit_status;
	}

	public void setLoad_limit_status(Integer load_limit_status) {
		this.load_limit_status = load_limit_status;
	}

	public Double getMaximum_demand_kva() {
		return maximum_demand_kva;
	}

	public void setMaximum_demand_kva(Double maximum_demand_kva) {
		this.maximum_demand_kva = maximum_demand_kva;
	}

	public Date getMaximum_demand_kva_datetime() {
		return maximum_demand_kva_datetime;
	}

	public void setMaximum_demand_kva_datetime(Date maximum_demand_kva_datetime) {
		this.maximum_demand_kva_datetime = maximum_demand_kva_datetime;
	}

	public Double getMaximum_demand_kw() {
		return maximum_demand_kw;
	}

	public void setMaximum_demand_kw(Double maximum_demand_kw) {
		this.maximum_demand_kw = maximum_demand_kw;
	}

	public Date getMaximum_demand_kw_datetime() {
		return maximum_demand_kw_datetime;
	}

	public void setMaximum_demand_kw_datetime(Date maximum_demand_kw_datetime) {
		this.maximum_demand_kw_datetime = maximum_demand_kw_datetime;
	}

	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	public Date getMeter_datetime() {
		return meter_datetime;
	}

	public void setMeter_datetime(Date meter_datetime) {
		this.meter_datetime = meter_datetime;
	}

	public Integer getNo_of_power_failure() {
		return no_of_power_failure;
	}

	public void setNo_of_power_failure(Integer no_of_power_failure) {
		this.no_of_power_failure = no_of_power_failure;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public Double getPhase_current_l1() {
		return phase_current_l1;
	}

	public void setPhase_current_l1(Double phase_current_l1) {
		this.phase_current_l1 = phase_current_l1;
	}

	public Double getPhase_current_l2() {
		return phase_current_l2;
	}

	public void setPhase_current_l2(Double phase_current_l2) {
		this.phase_current_l2 = phase_current_l2;
	}

	public Double getPhase_current_l3() {
		return phase_current_l3;
	}

	public void setPhase_current_l3(Double phase_current_l3) {
		this.phase_current_l3 = phase_current_l3;
	}

	public Double getPower_factor_3ph() {
		return power_factor_3ph;
	}

	public void setPower_factor_3ph(Double power_factor_3ph) {
		this.power_factor_3ph = power_factor_3ph;
	}

	public Double getPower_factor_l1() {
		return power_factor_l1;
	}

	public void setPower_factor_l1(Double power_factor_l1) {
		this.power_factor_l1 = power_factor_l1;
	}

	public Double getPower_factor_l2() {
		return power_factor_l2;
	}

	public void setPower_factor_l2(Double power_factor_l2) {
		this.power_factor_l2 = power_factor_l2;
	}

	public Double getPower_factor_l3() {
		return power_factor_l3;
	}

	public void setPower_factor_l3(Double power_factor_l3) {
		this.power_factor_l3 = power_factor_l3;
	}

	public String getSubdevision_name() {
		return subdevision_name;
	}

	public void setSubdevision_name(String subdevision_name) {
		this.subdevision_name = subdevision_name;
	}

	public String getSubstation_name() {
		return substation_name;
	}

	public void setSubstation_name(String substation_name) {
		this.substation_name = substation_name;
	}
	
	public Double getSigned_power_factor_b_phase() {
		return signed_power_factor_b_phase;
	}

	public void setSigned_power_factor_b_phase(Double signed_power_factor_b_phase) {
		this.signed_power_factor_b_phase = signed_power_factor_b_phase;
	}
	
	public Double getMaximum_demand_w_export() {
		return maximum_demand_w_export;
	}

	public void setMaximum_demand_w_export(Double maximum_demand_w_export) {
		this.maximum_demand_w_export = maximum_demand_w_export;
	}

	public Date getMaximum_demand_w_export_datetime() {
		return maximum_demand_w_export_datetime;
	}

	public void setMaximum_demand_w_export_datetime(Date maximum_demand_w_export_datetime) {
		this.maximum_demand_w_export_datetime = maximum_demand_w_export_datetime;
	}

	public Double getMaximum_demand_va_export() {
		return maximum_demand_va_export;
	}

	public void setMaximum_demand_va_export(Double maximum_demand_va_export) {
		this.maximum_demand_va_export = maximum_demand_va_export;
	}

	public Date getMaximum_demand_va_export_datetime() {
		return maximum_demand_va_export_datetime;
	}

	public void setMaximum_demand_va_export_datetime(Date maximum_demand_va_export_datetime) {
		this.maximum_demand_va_export_datetime = maximum_demand_va_export_datetime;
	}

	public Double getAngle_phase_volt_ab() {
		return angle_phase_volt_ab;
	}

	public void setAngle_phase_volt_ab(Double angle_phase_volt_ab) {
		this.angle_phase_volt_ab = angle_phase_volt_ab;
	}

	public Double getAngle_phase_volt_bc() {
		return angle_phase_volt_bc;
	}

	public void setAngle_phase_volt_bc(Double angle_phase_volt_bc) {
		this.angle_phase_volt_bc = angle_phase_volt_bc;
	}

	public Double getAngle_phase_volt_ac() {
		return angle_phase_volt_ac;
	}

	public void setAngle_phase_volt_ac(Double angle_phase_volt_ac) {
		this.angle_phase_volt_ac = angle_phase_volt_ac;
	}

	public Double getCumm_power_on_duration_in_mins() {
		return cumm_power_on_duration_in_mins;
	}

	public void setCumm_power_on_duration_in_mins(Double cumm_power_on_duration_in_mins) {
		this.cumm_power_on_duration_in_mins = cumm_power_on_duration_in_mins;
	}

	@Override
	public String toString() {
		return "InstantaneousCt [device_serial_number=" + device_serial_number + ", tracking_id=" + tracking_id
				+ ", datetime=" + datetime + ", active_power_kw=" + active_power_kw + ", apparent_power_kva="
				+ apparent_power_kva + ", cumm_power_off_duration_in_mins=" + cumm_power_off_duration_in_mins
				+ ", cumulative_bill_count=" + cumulative_bill_count + ", cumulative_energy_kvah_export="
				+ cumulative_energy_kvah_export + ", cumulative_energy_kvah_import=" + cumulative_energy_kvah_import
				+ ", cumulative_energy_kvarh_q1=" + cumulative_energy_kvarh_q1 + ", cumulative_energy_kvarh_q2="
				+ cumulative_energy_kvarh_q2 + ", cumulative_energy_kvarh_q3=" + cumulative_energy_kvarh_q3
				+ ", cumulative_energy_kvarh_q4=" + cumulative_energy_kvarh_q4 + ", cumulative_energy_kwh_export="
				+ cumulative_energy_kwh_export + ", cumulative_energy_kwh_import=" + cumulative_energy_kwh_import
				+ ", cumulative_program_count=" + cumulative_program_count + ", cumulative_tamper_count="
				+ cumulative_tamper_count + ", dcu_serial_number=" + dcu_serial_number + ", dt_name=" + dt_name
				+ ", enable_disable_load=" + enable_disable_load + ", feeder_name=" + feeder_name + ", frequency="
				+ frequency + ", instant_voltage_l1=" + instant_voltage_l1 + ", instant_voltage_l2="
				+ instant_voltage_l2 + ", instant_voltage_l3=" + instant_voltage_l3 + ", last_billing_datetime="
				+ last_billing_datetime + ", load_limit=" + load_limit + ", load_limit_status=" + load_limit_status
				+ ", maximum_demand_kva=" + maximum_demand_kva + ", maximum_demand_kva_datetime="
				+ maximum_demand_kva_datetime + ", maximum_demand_kw=" + maximum_demand_kw
				+ ", maximum_demand_kw_datetime=" + maximum_demand_kw_datetime + ", mdas_datetime=" + mdas_datetime
				+ ", meter_datetime=" + meter_datetime + ", no_of_power_failure=" + no_of_power_failure
				+ ", owner_name=" + owner_name + ", phase_current_l1=" + phase_current_l1 + ", phase_current_l2="
				+ phase_current_l2 + ", phase_current_l3=" + phase_current_l3 + ", power_factor_3ph=" + power_factor_3ph
				+ ", power_factor_l1=" + power_factor_l1 + ", power_factor_l2=" + power_factor_l2 + ", power_factor_l3="
				+ power_factor_l3 + ", reactive_power_kvar=" + reactive_power_kvar + ", subdevision_name="
				+ subdevision_name + ", substation_name=" + substation_name + ", signed_power_factor_b_phase="
				+ signed_power_factor_b_phase + ", maximum_demand_w_export=" + maximum_demand_w_export
				+ ", maximum_demand_w_export_datetime=" + maximum_demand_w_export_datetime
				+ ", maximum_demand_va_export=" + maximum_demand_va_export + ", maximum_demand_va_export_datetime="
				+ maximum_demand_va_export_datetime + ", angle_phase_volt_ab=" + angle_phase_volt_ab
				+ ", angle_phase_volt_bc=" + angle_phase_volt_bc + ", angle_phase_volt_ac=" + angle_phase_volt_ac
				+ ", cumm_power_on_duration_in_mins=" + cumm_power_on_duration_in_mins + "]";
	}

}
