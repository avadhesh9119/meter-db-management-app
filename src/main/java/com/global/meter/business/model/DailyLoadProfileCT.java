package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "daily_load_profile_ct")
public class DailyLoadProfileCT implements Serializable{
	
	private static final long serialVersionUID = 4675590361358502034L;

	@PrimaryKey
	private String device_serial_number;

	private Date datetime;
    private Double cumulative_energy_kvah_export;
    private Double cumulative_energy_kvah_import;
    private Double cumulative_energy_kwh_export;
    private Double cumulative_energy_kwh_import;
    private String dcu_serial_number;
    private String dt_name;
    private String feeder_name;
    private Date mdas_datetime;
    private Date meter_datetime;
    private String owner_name;
    private String subdevision_name;
    private String substation_name;
    private Double cumulative_energy_kvarh_q1;
    private Double cumulative_energy_kvarh_q2;
    private Double cumulative_energy_kvarh_q3;
    private Double cumulative_energy_kvarh_q4;
    private Double maximum_demand_kva;
    private Date maximum_demand_kva_datetime;
    private Double maximum_demand_kw;
    private Date maximum_demand_kw_datetime;
    private Double maximum_demand_kw_export;
    private Date maximum_demand_kw_export_datetime;
    private String no_supply;
    private String no_load;
	
    public DailyLoadProfileCT() {
	
	}

	public DailyLoadProfileCT(String device_serial_number, Date datetime, Double cumulative_energy_kvah_export,
			Double cumulative_energy_kvah_import, Double cumulative_energy_kwh_export,
			Double cumulative_energy_kwh_import, String dcu_serial_number, String dt_name, String feeder_name,
			Date mdas_datetime, Date meter_datetime, String owner_name, String subdevision_name, String substation_name,
			Double cumulative_energy_kvarh_q1, Double cumulative_energy_kvarh_q2, Double cumulative_energy_kvarh_q3,
			Double cumulative_energy_kvarh_q4, Double maximum_demand_kva, Date maximum_demand_kva_datetime,
			Double maximum_demand_kw, Date maximum_demand_kw_datetime) {
		super();
		this.device_serial_number = device_serial_number;
		this.datetime = datetime;
		this.cumulative_energy_kvah_export = cumulative_energy_kvah_export;
		this.cumulative_energy_kvah_import = cumulative_energy_kvah_import;
		this.cumulative_energy_kwh_export = cumulative_energy_kwh_export;
		this.cumulative_energy_kwh_import = cumulative_energy_kwh_import;
		this.dcu_serial_number = dcu_serial_number;
		this.dt_name = dt_name;
		this.feeder_name = feeder_name;
		this.mdas_datetime = mdas_datetime;
		this.meter_datetime = meter_datetime;
		this.owner_name = owner_name;
		this.subdevision_name = subdevision_name;
		this.substation_name = substation_name;
		this.cumulative_energy_kvarh_q1 = cumulative_energy_kvarh_q1;
		this.cumulative_energy_kvarh_q2 = cumulative_energy_kvarh_q2;
		this.cumulative_energy_kvarh_q3 = cumulative_energy_kvarh_q3;
		this.cumulative_energy_kvarh_q4 = cumulative_energy_kvarh_q4;
		this.maximum_demand_kva = maximum_demand_kva;
		this.maximum_demand_kva_datetime = maximum_demand_kva_datetime;
		this.maximum_demand_kw = maximum_demand_kw;
		this.maximum_demand_kw_datetime = maximum_demand_kw_datetime;
	}


	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
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

	public String getFeeder_name() {
		return feeder_name;
	}

	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
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

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
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

	public Double getMaximum_demand_kw_export() {
		return maximum_demand_kw_export;
	}

	public void setMaximum_demand_kw_export(Double maximum_demand_kw_export) {
		this.maximum_demand_kw_export = maximum_demand_kw_export;
	}

	public Date getMaximum_demand_kw_export_datetime() {
		return maximum_demand_kw_export_datetime;
	}

	public void setMaximum_demand_kw_export_datetime(Date maximum_demand_kw_export_datetime) {
		this.maximum_demand_kw_export_datetime = maximum_demand_kw_export_datetime;
	}

	public String getNo_supply() {
		return no_supply;
	}

	public void setNo_supply(String no_supply) {
		this.no_supply = no_supply;
	}

	public String getNo_load() {
		return no_load;
	}

	public void setNo_load(String no_load) {
		this.no_load = no_load;
	}

	@Override
	public String toString() {
		return "DailyLoadProfileCT [device_serial_number=" + device_serial_number + ", datetime=" + datetime
				+ ", cumulative_energy_kvah_export=" + cumulative_energy_kvah_export
				+ ", cumulative_energy_kvah_import=" + cumulative_energy_kvah_import + ", cumulative_energy_kwh_export="
				+ cumulative_energy_kwh_export + ", cumulative_energy_kwh_import=" + cumulative_energy_kwh_import
				+ ", dcu_serial_number=" + dcu_serial_number + ", dt_name=" + dt_name + ", feeder_name=" + feeder_name
				+ ", mdas_datetime=" + mdas_datetime + ", meter_datetime=" + meter_datetime + ", owner_name="
				+ owner_name + ", subdevision_name=" + subdevision_name + ", substation_name=" + substation_name
				+ ", cumulative_energy_kvarh_q1=" + cumulative_energy_kvarh_q1 + ", cumulative_energy_kvarh_q2="
				+ cumulative_energy_kvarh_q2 + ", cumulative_energy_kvarh_q3=" + cumulative_energy_kvarh_q3
				+ ", cumulative_energy_kvarh_q4=" + cumulative_energy_kvarh_q4 + ", maximum_demand_kva="
				+ maximum_demand_kva + ", maximum_demand_kva_datetime=" + maximum_demand_kva_datetime
				+ ", maximum_demand_kw=" + maximum_demand_kw + ", maximum_demand_kw_datetime="
				+ maximum_demand_kw_datetime + ", maximum_demand_kw_export=" + maximum_demand_kw_export
				+ ", maximum_demand_kw_export_datetime=" + maximum_demand_kw_export_datetime + ", no_supply="
				+ no_supply + ", no_load=" + no_load + "]";
	}

}
    