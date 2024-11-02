package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "daily_load_profile_singlephase")
public class DailyLoadProfileSinglePhase  implements Serializable{
	
	private static final long serialVersionUID = -5581059776447864657L;

	@PrimaryKey
	private String device_serial_number;

	private Date datetime;
	private Double cumulative_energy_kvah_export;
	private Double cumulative_energy_kvah_import;
	private Double cumulative_energy_kvah_tier1;
	private Double cumulative_energy_kvah_tier2;
	private Double cumulative_energy_kvah_tier3;
	private Double cumulative_energy_kvah_tier4;
	private Double cumulative_energy_kvah_tier5;
	private Double cumulative_energy_kvah_tier6;
	private Double cumulative_energy_kvah_tier7;
	private Double cumulative_energy_kvah_tier8;
	
	private Double cumulative_energy_kwh_export;
	private Double cumulative_energy_kwh_import;
	private Double cumulative_energy_kwh_tier1;
	private Double cumulative_energy_kwh_tier2;
	private Double cumulative_energy_kwh_tier3;
	private Double cumulative_energy_kwh_tier4;
	private Double cumulative_energy_kwh_tier5;
	private Double cumulative_energy_kwh_tier6;
	private Double cumulative_energy_kwh_tier7;
	private Double cumulative_energy_kwh_tier8;
	private Double maximum_demand_kw;
	private Date maximum_demand_kw_datetime;
	private Double maximum_demand_kw_tier1;
	private Date maximum_demand_kw_tier1_date;
	private Double maximum_demand_kw_tier2;
	private Date maximum_demand_kw_tier2_date;
	private Double maximum_demand_kw_tier3;
	private Date maximum_demand_kw_tier3_date;
	private Double maximum_demand_kw_tier4;
	private Date maximum_demand_kw_tier4_date;
	private Double maximum_demand_kw_tier5;
	private Date maximum_demand_kw_tier5_date;
	private Double maximum_demand_kw_tier6;
	private Date maximum_demand_kw_tier6_date;
	private Double maximum_demand_kw_tier7;
	private Date maximum_demand_kw_tier7_date;
	private Double maximum_demand_kw_tier8;
	private Date maximum_demand_kw_tier8_date;
	private Double maximum_demand_kva;
	private Date maximum_demand_kva_datetime;
	private Double maximum_demand_kva_tier1;
	private Date maximum_demand_kva_tier1_date;
	private Double maximum_demand_kva_tier2;
	private Date maximum_demand_kva_tier2_date;
	private Double maximum_demand_kva_tier3;
	private Date maximum_demand_kva_tier3_date;
	private Double maximum_demand_kva_tier4;
	private Date maximum_demand_kva_tier4_date;
	private Double maximum_demand_kva_tier5;
	private Date maximum_demand_kva_tier5_date;
	private Double maximum_demand_kva_tier6;
	private Date maximum_demand_kva_tier6_date;
	private Double maximum_demand_kva_tier7;
	private Date maximum_demand_kva_tier7_date;
	private Double maximum_demand_kva_tier8;
	private Date maximum_demand_kva_tier8_date;
    private String dcu_serial_number;
    private String dt_name;
    private String feeder_name;
    private Date mdas_datetime;
    private Date meter_datetime;
    private String owner_name;
    private String subdevision_name;
    private String substation_name;
    private String mode_of_comm;


	public DailyLoadProfileSinglePhase() {
	}

	public DailyLoadProfileSinglePhase(String device_serial_number, Date datetime, Double cumulative_energy_kvah_export,
			Double cumulative_energy_kvah_import, Double cumulative_energy_kwh_export,
			Double cumulative_energy_kwh_import, String dcu_serial_number, String dt_name, String feeder_name,
			Date mdas_datetime, Date meter_datetime, String owner_name, String subdevision_name,
			String substation_name) {
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

	public Double getCumulative_energy_kvah_tier1() {
		return cumulative_energy_kvah_tier1;
	}

	public void setCumulative_energy_kvah_tier1(Double cumulative_energy_kvah_tier1) {
		this.cumulative_energy_kvah_tier1 = cumulative_energy_kvah_tier1;
	}

	public Double getCumulative_energy_kvah_tier2() {
		return cumulative_energy_kvah_tier2;
	}

	public void setCumulative_energy_kvah_tier2(Double cumulative_energy_kvah_tier2) {
		this.cumulative_energy_kvah_tier2 = cumulative_energy_kvah_tier2;
	}

	public Double getCumulative_energy_kvah_tier3() {
		return cumulative_energy_kvah_tier3;
	}

	public void setCumulative_energy_kvah_tier3(Double cumulative_energy_kvah_tier3) {
		this.cumulative_energy_kvah_tier3 = cumulative_energy_kvah_tier3;
	}

	public Double getCumulative_energy_kvah_tier4() {
		return cumulative_energy_kvah_tier4;
	}

	public void setCumulative_energy_kvah_tier4(Double cumulative_energy_kvah_tier4) {
		this.cumulative_energy_kvah_tier4 = cumulative_energy_kvah_tier4;
	}

	public Double getCumulative_energy_kvah_tier5() {
		return cumulative_energy_kvah_tier5;
	}

	public void setCumulative_energy_kvah_tier5(Double cumulative_energy_kvah_tier5) {
		this.cumulative_energy_kvah_tier5 = cumulative_energy_kvah_tier5;
	}

	public Double getCumulative_energy_kvah_tier6() {
		return cumulative_energy_kvah_tier6;
	}

	public void setCumulative_energy_kvah_tier6(Double cumulative_energy_kvah_tier6) {
		this.cumulative_energy_kvah_tier6 = cumulative_energy_kvah_tier6;
	}

	public Double getCumulative_energy_kvah_tier7() {
		return cumulative_energy_kvah_tier7;
	}

	public void setCumulative_energy_kvah_tier7(Double cumulative_energy_kvah_tier7) {
		this.cumulative_energy_kvah_tier7 = cumulative_energy_kvah_tier7;
	}

	public Double getCumulative_energy_kvah_tier8() {
		return cumulative_energy_kvah_tier8;
	}

	public void setCumulative_energy_kvah_tier8(Double cumulative_energy_kvah_tier8) {
		this.cumulative_energy_kvah_tier8 = cumulative_energy_kvah_tier8;
	}

	public Double getCumulative_energy_kwh_tier1() {
		return cumulative_energy_kwh_tier1;
	}

	public void setCumulative_energy_kwh_tier1(Double cumulative_energy_kwh_tier1) {
		this.cumulative_energy_kwh_tier1 = cumulative_energy_kwh_tier1;
	}

	public Double getCumulative_energy_kwh_tier2() {
		return cumulative_energy_kwh_tier2;
	}

	public void setCumulative_energy_kwh_tier2(Double cumulative_energy_kwh_tier2) {
		this.cumulative_energy_kwh_tier2 = cumulative_energy_kwh_tier2;
	}

	public Double getCumulative_energy_kwh_tier3() {
		return cumulative_energy_kwh_tier3;
	}

	public void setCumulative_energy_kwh_tier3(Double cumulative_energy_kwh_tier3) {
		this.cumulative_energy_kwh_tier3 = cumulative_energy_kwh_tier3;
	}

	public Double getCumulative_energy_kwh_tier4() {
		return cumulative_energy_kwh_tier4;
	}

	public void setCumulative_energy_kwh_tier4(Double cumulative_energy_kwh_tier4) {
		this.cumulative_energy_kwh_tier4 = cumulative_energy_kwh_tier4;
	}

	public Double getCumulative_energy_kwh_tier5() {
		return cumulative_energy_kwh_tier5;
	}

	public void setCumulative_energy_kwh_tier5(Double cumulative_energy_kwh_tier5) {
		this.cumulative_energy_kwh_tier5 = cumulative_energy_kwh_tier5;
	}

	public Double getCumulative_energy_kwh_tier6() {
		return cumulative_energy_kwh_tier6;
	}

	public void setCumulative_energy_kwh_tier6(Double cumulative_energy_kwh_tier6) {
		this.cumulative_energy_kwh_tier6 = cumulative_energy_kwh_tier6;
	}

	public Double getCumulative_energy_kwh_tier7() {
		return cumulative_energy_kwh_tier7;
	}

	public void setCumulative_energy_kwh_tier7(Double cumulative_energy_kwh_tier7) {
		this.cumulative_energy_kwh_tier7 = cumulative_energy_kwh_tier7;
	}

	public Double getCumulative_energy_kwh_tier8() {
		return cumulative_energy_kwh_tier8;
	}

	public void setCumulative_energy_kwh_tier8(Double cumulative_energy_kwh_tier8) {
		this.cumulative_energy_kwh_tier8 = cumulative_energy_kwh_tier8;
	}

	public Double getMaximum_demand_kw_tier1() {
		return maximum_demand_kw_tier1;
	}

	public void setMaximum_demand_kw_tier1(Double maximum_demand_kw_tier1) {
		this.maximum_demand_kw_tier1 = maximum_demand_kw_tier1;
	}

	public Date getMaximum_demand_kw_tier1_date() {
		return maximum_demand_kw_tier1_date;
	}

	public void setMaximum_demand_kw_tier1_date(Date maximum_demand_kw_tier1_date) {
		this.maximum_demand_kw_tier1_date = maximum_demand_kw_tier1_date;
	}

	public Double getMaximum_demand_kw_tier2() {
		return maximum_demand_kw_tier2;
	}

	public void setMaximum_demand_kw_tier2(Double maximum_demand_kw_tier2) {
		this.maximum_demand_kw_tier2 = maximum_demand_kw_tier2;
	}

	public Date getMaximum_demand_kw_tier2_date() {
		return maximum_demand_kw_tier2_date;
	}

	public void setMaximum_demand_kw_tier2_date(Date maximum_demand_kw_tier2_date) {
		this.maximum_demand_kw_tier2_date = maximum_demand_kw_tier2_date;
	}

	public Double getMaximum_demand_kw_tier3() {
		return maximum_demand_kw_tier3;
	}

	public void setMaximum_demand_kw_tier3(Double maximum_demand_kw_tier3) {
		this.maximum_demand_kw_tier3 = maximum_demand_kw_tier3;
	}

	public Date getMaximum_demand_kw_tier3_date() {
		return maximum_demand_kw_tier3_date;
	}

	public void setMaximum_demand_kw_tier3_date(Date maximum_demand_kw_tier3_date) {
		this.maximum_demand_kw_tier3_date = maximum_demand_kw_tier3_date;
	}

	public Double getMaximum_demand_kw_tier4() {
		return maximum_demand_kw_tier4;
	}

	public void setMaximum_demand_kw_tier4(Double maximum_demand_kw_tier4) {
		this.maximum_demand_kw_tier4 = maximum_demand_kw_tier4;
	}

	public Date getMaximum_demand_kw_tier4_date() {
		return maximum_demand_kw_tier4_date;
	}

	public void setMaximum_demand_kw_tier4_date(Date maximum_demand_kw_tier4_date) {
		this.maximum_demand_kw_tier4_date = maximum_demand_kw_tier4_date;
	}

	public Double getMaximum_demand_kw_tier5() {
		return maximum_demand_kw_tier5;
	}

	public void setMaximum_demand_kw_tier5(Double maximum_demand_kw_tier5) {
		this.maximum_demand_kw_tier5 = maximum_demand_kw_tier5;
	}

	public Date getMaximum_demand_kw_tier5_date() {
		return maximum_demand_kw_tier5_date;
	}

	public void setMaximum_demand_kw_tier5_date(Date maximum_demand_kw_tier5_date) {
		this.maximum_demand_kw_tier5_date = maximum_demand_kw_tier5_date;
	}

	public Double getMaximum_demand_kw_tier6() {
		return maximum_demand_kw_tier6;
	}

	public void setMaximum_demand_kw_tier6(Double maximum_demand_kw_tier6) {
		this.maximum_demand_kw_tier6 = maximum_demand_kw_tier6;
	}

	public Date getMaximum_demand_kw_tier6_date() {
		return maximum_demand_kw_tier6_date;
	}

	public void setMaximum_demand_kw_tier6_date(Date maximum_demand_kw_tier6_date) {
		this.maximum_demand_kw_tier6_date = maximum_demand_kw_tier6_date;
	}

	public Double getMaximum_demand_kw_tier7() {
		return maximum_demand_kw_tier7;
	}

	public void setMaximum_demand_kw_tier7(Double maximum_demand_kw_tier7) {
		this.maximum_demand_kw_tier7 = maximum_demand_kw_tier7;
	}

	public Date getMaximum_demand_kw_tier7_date() {
		return maximum_demand_kw_tier7_date;
	}

	public void setMaximum_demand_kw_tier7_date(Date maximum_demand_kw_tier7_date) {
		this.maximum_demand_kw_tier7_date = maximum_demand_kw_tier7_date;
	}

	public Double getMaximum_demand_kw_tier8() {
		return maximum_demand_kw_tier8;
	}

	public void setMaximum_demand_kw_tier8(Double maximum_demand_kw_tier8) {
		this.maximum_demand_kw_tier8 = maximum_demand_kw_tier8;
	}

	public Date getMaximum_demand_kw_tier8_date() {
		return maximum_demand_kw_tier8_date;
	}

	public void setMaximum_demand_kw_tier8_date(Date maximum_demand_kw_tier8_date) {
		this.maximum_demand_kw_tier8_date = maximum_demand_kw_tier8_date;
	}

	public Double getMaximum_demand_kva_tier1() {
		return maximum_demand_kva_tier1;
	}

	public void setMaximum_demand_kva_tier1(Double maximum_demand_kva_tier1) {
		this.maximum_demand_kva_tier1 = maximum_demand_kva_tier1;
	}

	public Date getMaximum_demand_kva_tier1_date() {
		return maximum_demand_kva_tier1_date;
	}

	public void setMaximum_demand_kva_tier1_date(Date maximum_demand_kva_tier1_date) {
		this.maximum_demand_kva_tier1_date = maximum_demand_kva_tier1_date;
	}

	public Double getMaximum_demand_kva_tier2() {
		return maximum_demand_kva_tier2;
	}

	public void setMaximum_demand_kva_tier2(Double maximum_demand_kva_tier2) {
		this.maximum_demand_kva_tier2 = maximum_demand_kva_tier2;
	}

	public Date getMaximum_demand_kva_tier2_date() {
		return maximum_demand_kva_tier2_date;
	}

	public void setMaximum_demand_kva_tier2_date(Date maximum_demand_kva_tier2_date) {
		this.maximum_demand_kva_tier2_date = maximum_demand_kva_tier2_date;
	}

	public Double getMaximum_demand_kva_tier3() {
		return maximum_demand_kva_tier3;
	}

	public void setMaximum_demand_kva_tier3(Double maximum_demand_kva_tier3) {
		this.maximum_demand_kva_tier3 = maximum_demand_kva_tier3;
	}

	public Date getMaximum_demand_kva_tier3_date() {
		return maximum_demand_kva_tier3_date;
	}

	public void setMaximum_demand_kva_tier3_date(Date maximum_demand_kva_tier3_date) {
		this.maximum_demand_kva_tier3_date = maximum_demand_kva_tier3_date;
	}

	public Double getMaximum_demand_kva_tier4() {
		return maximum_demand_kva_tier4;
	}

	public void setMaximum_demand_kva_tier4(Double maximum_demand_kva_tier4) {
		this.maximum_demand_kva_tier4 = maximum_demand_kva_tier4;
	}

	public Date getMaximum_demand_kva_tier4_date() {
		return maximum_demand_kva_tier4_date;
	}

	public void setMaximum_demand_kva_tier4_date(Date maximum_demand_kva_tier4_date) {
		this.maximum_demand_kva_tier4_date = maximum_demand_kva_tier4_date;
	}

	public Double getMaximum_demand_kva_tier5() {
		return maximum_demand_kva_tier5;
	}

	public void setMaximum_demand_kva_tier5(Double maximum_demand_kva_tier5) {
		this.maximum_demand_kva_tier5 = maximum_demand_kva_tier5;
	}

	public Date getMaximum_demand_kva_tier5_date() {
		return maximum_demand_kva_tier5_date;
	}

	public void setMaximum_demand_kva_tier5_date(Date maximum_demand_kva_tier5_date) {
		this.maximum_demand_kva_tier5_date = maximum_demand_kva_tier5_date;
	}

	public Double getMaximum_demand_kva_tier6() {
		return maximum_demand_kva_tier6;
	}

	public void setMaximum_demand_kva_tier6(Double maximum_demand_kva_tier6) {
		this.maximum_demand_kva_tier6 = maximum_demand_kva_tier6;
	}

	public Date getMaximum_demand_kva_tier6_date() {
		return maximum_demand_kva_tier6_date;
	}

	public void setMaximum_demand_kva_tier6_date(Date maximum_demand_kva_tier6_date) {
		this.maximum_demand_kva_tier6_date = maximum_demand_kva_tier6_date;
	}

	public Double getMaximum_demand_kva_tier7() {
		return maximum_demand_kva_tier7;
	}

	public void setMaximum_demand_kva_tier7(Double maximum_demand_kva_tier7) {
		this.maximum_demand_kva_tier7 = maximum_demand_kva_tier7;
	}

	public Date getMaximum_demand_kva_tier7_date() {
		return maximum_demand_kva_tier7_date;
	}

	public void setMaximum_demand_kva_tier7_date(Date maximum_demand_kva_tier7_date) {
		this.maximum_demand_kva_tier7_date = maximum_demand_kva_tier7_date;
	}

	public Double getMaximum_demand_kva_tier8() {
		return maximum_demand_kva_tier8;
	}

	public void setMaximum_demand_kva_tier8(Double maximum_demand_kva_tier8) {
		this.maximum_demand_kva_tier8 = maximum_demand_kva_tier8;
	}

	public Date getMaximum_demand_kva_tier8_date() {
		return maximum_demand_kva_tier8_date;
	}

	public void setMaximum_demand_kva_tier8_date(Date maximum_demand_kva_tier8_date) {
		this.maximum_demand_kva_tier8_date = maximum_demand_kva_tier8_date;
	}

	public String getMode_of_comm() {
		return mode_of_comm;
	}

	public void setMode_of_comm(String mode_of_comm) {
		this.mode_of_comm = mode_of_comm;
	}

	@Override
	public String toString() {
		return "DailyLoadProfileSinglePhase [device_serial_number=" + device_serial_number + ", datetime=" + datetime
				+ ", cumulative_energy_kvah_export=" + cumulative_energy_kvah_export
				+ ", cumulative_energy_kvah_import=" + cumulative_energy_kvah_import + ", cumulative_energy_kvah_tier1="
				+ cumulative_energy_kvah_tier1 + ", cumulative_energy_kvah_tier2=" + cumulative_energy_kvah_tier2
				+ ", cumulative_energy_kvah_tier3=" + cumulative_energy_kvah_tier3 + ", cumulative_energy_kvah_tier4="
				+ cumulative_energy_kvah_tier4 + ", cumulative_energy_kvah_tier5=" + cumulative_energy_kvah_tier5
				+ ", cumulative_energy_kvah_tier6=" + cumulative_energy_kvah_tier6 + ", cumulative_energy_kvah_tier7="
				+ cumulative_energy_kvah_tier7 + ", cumulative_energy_kvah_tier8=" + cumulative_energy_kvah_tier8
				+ ", cumulative_energy_kwh_export=" + cumulative_energy_kwh_export + ", cumulative_energy_kwh_import="
				+ cumulative_energy_kwh_import + ", cumulative_energy_kwh_tier1=" + cumulative_energy_kwh_tier1
				+ ", cumulative_energy_kwh_tier2=" + cumulative_energy_kwh_tier2 + ", cumulative_energy_kwh_tier3="
				+ cumulative_energy_kwh_tier3 + ", cumulative_energy_kwh_tier4=" + cumulative_energy_kwh_tier4
				+ ", cumulative_energy_kwh_tier5=" + cumulative_energy_kwh_tier5 + ", cumulative_energy_kwh_tier6="
				+ cumulative_energy_kwh_tier6 + ", cumulative_energy_kwh_tier7=" + cumulative_energy_kwh_tier7
				+ ", cumulative_energy_kwh_tier8=" + cumulative_energy_kwh_tier8 + ", maximum_demand_kw="
				+ maximum_demand_kw + ", maximum_demand_kw_datetime=" + maximum_demand_kw_datetime
				+ ", maximum_demand_kw_tier1=" + maximum_demand_kw_tier1 + ", maximum_demand_kw_tier1_date="
				+ maximum_demand_kw_tier1_date + ", maximum_demand_kw_tier2=" + maximum_demand_kw_tier2
				+ ", maximum_demand_kw_tier2_date=" + maximum_demand_kw_tier2_date + ", maximum_demand_kw_tier3="
				+ maximum_demand_kw_tier3 + ", maximum_demand_kw_tier3_date=" + maximum_demand_kw_tier3_date
				+ ", maximum_demand_kw_tier4=" + maximum_demand_kw_tier4 + ", maximum_demand_kw_tier4_date="
				+ maximum_demand_kw_tier4_date + ", maximum_demand_kw_tier5=" + maximum_demand_kw_tier5
				+ ", maximum_demand_kw_tier5_date=" + maximum_demand_kw_tier5_date + ", maximum_demand_kw_tier6="
				+ maximum_demand_kw_tier6 + ", maximum_demand_kw_tier6_date=" + maximum_demand_kw_tier6_date
				+ ", maximum_demand_kw_tier7=" + maximum_demand_kw_tier7 + ", maximum_demand_kw_tier7_date="
				+ maximum_demand_kw_tier7_date + ", maximum_demand_kw_tier8=" + maximum_demand_kw_tier8
				+ ", maximum_demand_kw_tier8_date=" + maximum_demand_kw_tier8_date + ", maximum_demand_kva="
				+ maximum_demand_kva + ", maximum_demand_kva_datetime=" + maximum_demand_kva_datetime
				+ ", maximum_demand_kva_tier1=" + maximum_demand_kva_tier1 + ", maximum_demand_kva_tier1_date="
				+ maximum_demand_kva_tier1_date + ", maximum_demand_kva_tier2=" + maximum_demand_kva_tier2
				+ ", maximum_demand_kva_tier2_date=" + maximum_demand_kva_tier2_date + ", maximum_demand_kva_tier3="
				+ maximum_demand_kva_tier3 + ", maximum_demand_kva_tier3_date=" + maximum_demand_kva_tier3_date
				+ ", maximum_demand_kva_tier4=" + maximum_demand_kva_tier4 + ", maximum_demand_kva_tier4_date="
				+ maximum_demand_kva_tier4_date + ", maximum_demand_kva_tier5=" + maximum_demand_kva_tier5
				+ ", maximum_demand_kva_tier5_date=" + maximum_demand_kva_tier5_date + ", maximum_demand_kva_tier6="
				+ maximum_demand_kva_tier6 + ", maximum_demand_kva_tier6_date=" + maximum_demand_kva_tier6_date
				+ ", maximum_demand_kva_tier7=" + maximum_demand_kva_tier7 + ", maximum_demand_kva_tier7_date="
				+ maximum_demand_kva_tier7_date + ", maximum_demand_kva_tier8=" + maximum_demand_kva_tier8
				+ ", maximum_demand_kva_tier8_date=" + maximum_demand_kva_tier8_date + ", dcu_serial_number="
				+ dcu_serial_number + ", dt_name=" + dt_name + ", feeder_name=" + feeder_name + ", mdas_datetime="
				+ mdas_datetime + ", meter_datetime=" + meter_datetime + ", owner_name=" + owner_name
				+ ", subdevision_name=" + subdevision_name + ", substation_name=" + substation_name + ", mode_of_comm="
				+ mode_of_comm + "]";
	}

}
