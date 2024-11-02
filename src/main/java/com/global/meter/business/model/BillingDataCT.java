package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "billing_data_ct")
public class BillingDataCT implements Serializable{
	
	private static final long serialVersionUID = -5290816761455278114L;

	@PrimaryKey
	private String device_serial_number;
	
	private Date billing_datetime;
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
    private Double cumulative_energy_kvarh_q1;
    private Double cumulative_energy_kvarh_q2;
    private Double cumulative_energy_kvarh_q3;
    private Double cumulative_energy_kvarh_q4;
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
    private String dcu_serial_number;
    private String dt_name;
    private String feeder_name;
    private Double maximum_demand_kva;
    private Date maximum_demand_kva_date;
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
    private Double maximum_demand_kw;
    private Date maximum_demand_kw_date;
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
    private Date mdas_datetime;
    private Date meter_datetime;
    private String owner_name;
    private Long power_on_duration_minutes;
    private String subdevision_name;
    private String substation_name;
    private Double system_power_factor_billing_period;
    
    private Double cumulative_energy_kvarh_lag;
    private Double cumulative_energy_kwh_export_tier1;
    private Double cumulative_energy_kwh_export_tier2;
    private Double cumulative_energy_kwh_export_tier3;
    private Double cumulative_energy_kwh_export_tier4;
    private Double cumulative_energy_kwh_export_tier5;
    private Double cumulative_energy_kwh_export_tier6;
    private Double cumulative_energy_kwh_export_tier7;
    private Double cumulative_energy_kwh_export_tier8;
    private Double cumulative_energy_kvah_export_tier1;
    private Double cumulative_energy_kvah_export_tier2;
    private Double cumulative_energy_kvah_export_tier3;
    private Double cumulative_energy_kvah_export_tier4;
    private Double cumulative_energy_kvah_export_tier5;
    private Double cumulative_energy_kvah_export_tier6;
    private Double cumulative_energy_kvah_export_tier7;
    private Double cumulative_energy_kvah_export_tier8;
    private Double maximum_demand_kw_export;
    private Date maximum_demand_kw_export_date;
    private Double maximum_demand_kw_export_tier1;
    private Double maximum_demand_kw_export_tier2;
    private Double maximum_demand_kw_export_tier3;
    private Double maximum_demand_kw_export_tier4;
    private Double maximum_demand_kw_export_tier5;
    private Double maximum_demand_kw_export_tier6;
    private Double maximum_demand_kw_export_tier7;
    private Double maximum_demand_kw_export_tier8;
    private Date maximum_demand_kw_date_export_tier1;
    private Date maximum_demand_kw_date_export_tier2;
    private Date maximum_demand_kw_date_export_tier3;
    private Date maximum_demand_kw_date_export_tier4;
    private Date maximum_demand_kw_date_export_tier5;
    private Date maximum_demand_kw_date_export_tier6;
    private Date maximum_demand_kw_date_export_tier7;
    private Date maximum_demand_kw_date_export_tier8;
    private Double maximum_demand_kva_export;
    private Date maximum_demand_kva_export_date;
    private Double maximum_demand_kva_export_tier1;
    private Double maximum_demand_kva_export_tier2;
    private Double maximum_demand_kva_export_tier3;
    private Double maximum_demand_kva_export_tier4;
    private Double maximum_demand_kva_export_tier5;
    private Double maximum_demand_kva_export_tier6;
    private Double maximum_demand_kva_export_tier7;
    private Double maximum_demand_kva_export_tier8;
    private Date maximum_demand_kva_date_export_tier1;
    private Date maximum_demand_kva_date_export_tier2;
    private Date maximum_demand_kva_date_export_tier3;
    private Date maximum_demand_kva_date_export_tier4;
    private Date maximum_demand_kva_date_export_tier5;
    private Date maximum_demand_kva_date_export_tier6;
    private Date maximum_demand_kva_date_export_tier7;
    private Date maximum_demand_kva_date_export_tier8;
    private Integer tamper_count;
    private Integer power_on_duration_mins;//it is replaced with power_on_duration_minutes. this will remove in future.
    
	
    public BillingDataCT() {
		super();
	}

	public BillingDataCT(String device_serial_number, Date billing_datetime, Double cumulative_energy_kvah_export,
			Double cumulative_energy_kvah_import, Double cumulative_energy_kvah_tier1,
			Double cumulative_energy_kvah_tier2, Double cumulative_energy_kvah_tier3,
			Double cumulative_energy_kvah_tier4, Double cumulative_energy_kvah_tier5,
			Double cumulative_energy_kvah_tier6, Double cumulative_energy_kvah_tier7,
			Double cumulative_energy_kvah_tier8, Double cumulative_energy_kvarh_q1, Double cumulative_energy_kvarh_q2,
			Double cumulative_energy_kvarh_q3, Double cumulative_energy_kvarh_q4, Double cumulative_energy_kwh_export,
			Double cumulative_energy_kwh_import, Double cumulative_energy_kwh_tier1, Double cumulative_energy_kwh_tier2,
			Double cumulative_energy_kwh_tier3, Double cumulative_energy_kwh_tier4, Double cumulative_energy_kwh_tier5,
			Double cumulative_energy_kwh_tier6, Double cumulative_energy_kwh_tier7, Double cumulative_energy_kwh_tier8,
			String dcu_serial_number, String dt_name, String feeder_name, Double maximum_demand_kva,
			Date maximum_demand_kva_date, Double maximum_demand_kva_tier1, Date maximum_demand_kva_tier1_date,
			Double maximum_demand_kva_tier2, Date maximum_demand_kva_tier2_date, Double maximum_demand_kva_tier3,
			Date maximum_demand_kva_tier3_date, Double maximum_demand_kva_tier4, Date maximum_demand_kva_tier4_date,
			Double maximum_demand_kva_tier5, Date maximum_demand_kva_tier5_date, Double maximum_demand_kva_tier6,
			Date maximum_demand_kva_tier6_date, Double maximum_demand_kva_tier7, Date maximum_demand_kva_tier7_date,
			Double maximum_demand_kva_tier8, Date maximum_demand_kva_tier8_date, Double maximum_demand_kw,
			Date maximum_demand_kw_date, Double maximum_demand_kw_tier1, Date maximum_demand_kw_tier1_date,
			Double maximum_demand_kw_tier2, Date maximum_demand_kw_tier2_date, Double maximum_demand_kw_tier3,
			Date maximum_demand_kw_tier3_date, Double maximum_demand_kw_tier4, Date maximum_demand_kw_tier4_date,
			Double maximum_demand_kw_tier5, Date maximum_demand_kw_tier5_date, Double maximum_demand_kw_tier6,
			Date maximum_demand_kw_tier6_date, Double maximum_demand_kw_tier7, Date maximum_demand_kw_tier7_date,
			Double maximum_demand_kw_tier8, Date maximum_demand_kw_tier8_date, Date mdas_datetime, Date meter_datetime,
			String owner_name, Long power_on_duration_minutes, String subdevision_name, String substation_name,
			Double system_power_factor_billing_period, Double cumulative_energy_kvarh_lag,
			Double cumulative_energy_kwh_export_tier1, Double cumulative_energy_kwh_export_tier2,
			Double cumulative_energy_kwh_export_tier3, Double cumulative_energy_kwh_export_tier4,
			Double cumulative_energy_kwh_export_tier5, Double cumulative_energy_kwh_export_tier6,
			Double cumulative_energy_kwh_export_tier7, Double cumulative_energy_kwh_export_tier8,
			Double cumulative_energy_kvah_export_tier1, Double cumulative_energy_kvah_export_tier2,
			Double cumulative_energy_kvah_export_tier3, Double cumulative_energy_kvah_export_tier4,
			Double cumulative_energy_kvah_export_tier5, Double cumulative_energy_kvah_export_tier6,
			Double cumulative_energy_kvah_export_tier7, Double cumulative_energy_kvah_export_tier8,
			Double maximum_demand_kw_export, Date maximum_demand_kw_export_date, Double maximum_demand_kw_export_tier1,
			Double maximum_demand_kw_export_tier2, Double maximum_demand_kw_export_tier3,
			Double maximum_demand_kw_export_tier4, Double maximum_demand_kw_export_tier5,
			Double maximum_demand_kw_export_tier6, Double maximum_demand_kw_export_tier7,
			Double maximum_demand_kw_export_tier8, Date maximum_demand_kw_date_export_tier1,
			Date maximum_demand_kw_date_export_tier2, Date maximum_demand_kw_date_export_tier3,
			Date maximum_demand_kw_date_export_tier4, Date maximum_demand_kw_date_export_tier5,
			Date maximum_demand_kw_date_export_tier6, Date maximum_demand_kw_date_export_tier7,
			Date maximum_demand_kw_date_export_tier8, Double maximum_demand_kva_export,
			Date maximum_demand_kva_export_date, Double maximum_demand_kva_export_tier1,
			Double maximum_demand_kva_export_tier2, Double maximum_demand_kva_export_tier3,
			Double maximum_demand_kva_export_tier4, Double maximum_demand_kva_export_tier5,
			Double maximum_demand_kva_export_tier6, Double maximum_demand_kva_export_tier7,
			Double maximum_demand_kva_export_tier8, Date maximum_demand_kva_date_export_tier1,
			Date maximum_demand_kva_date_export_tier2, Date maximum_demand_kva_date_export_tier3,
			Date maximum_demand_kva_date_export_tier4, Date maximum_demand_kva_date_export_tier5,
			Date maximum_demand_kva_date_export_tier6, Date maximum_demand_kva_date_export_tier7,
			Date maximum_demand_kva_date_export_tier8, Integer tamper_count) {
		super();
		this.device_serial_number = device_serial_number;
		this.billing_datetime = billing_datetime;
		this.cumulative_energy_kvah_export = cumulative_energy_kvah_export;
		this.cumulative_energy_kvah_import = cumulative_energy_kvah_import;
		this.cumulative_energy_kvah_tier1 = cumulative_energy_kvah_tier1;
		this.cumulative_energy_kvah_tier2 = cumulative_energy_kvah_tier2;
		this.cumulative_energy_kvah_tier3 = cumulative_energy_kvah_tier3;
		this.cumulative_energy_kvah_tier4 = cumulative_energy_kvah_tier4;
		this.cumulative_energy_kvah_tier5 = cumulative_energy_kvah_tier5;
		this.cumulative_energy_kvah_tier6 = cumulative_energy_kvah_tier6;
		this.cumulative_energy_kvah_tier7 = cumulative_energy_kvah_tier7;
		this.cumulative_energy_kvah_tier8 = cumulative_energy_kvah_tier8;
		this.cumulative_energy_kvarh_q1 = cumulative_energy_kvarh_q1;
		this.cumulative_energy_kvarh_q2 = cumulative_energy_kvarh_q2;
		this.cumulative_energy_kvarh_q3 = cumulative_energy_kvarh_q3;
		this.cumulative_energy_kvarh_q4 = cumulative_energy_kvarh_q4;
		this.cumulative_energy_kwh_export = cumulative_energy_kwh_export;
		this.cumulative_energy_kwh_import = cumulative_energy_kwh_import;
		this.cumulative_energy_kwh_tier1 = cumulative_energy_kwh_tier1;
		this.cumulative_energy_kwh_tier2 = cumulative_energy_kwh_tier2;
		this.cumulative_energy_kwh_tier3 = cumulative_energy_kwh_tier3;
		this.cumulative_energy_kwh_tier4 = cumulative_energy_kwh_tier4;
		this.cumulative_energy_kwh_tier5 = cumulative_energy_kwh_tier5;
		this.cumulative_energy_kwh_tier6 = cumulative_energy_kwh_tier6;
		this.cumulative_energy_kwh_tier7 = cumulative_energy_kwh_tier7;
		this.cumulative_energy_kwh_tier8 = cumulative_energy_kwh_tier8;
		this.dcu_serial_number = dcu_serial_number;
		this.dt_name = dt_name;
		this.feeder_name = feeder_name;
		this.maximum_demand_kva = maximum_demand_kva;
		this.maximum_demand_kva_date = maximum_demand_kva_date;
		this.maximum_demand_kva_tier1 = maximum_demand_kva_tier1;
		this.maximum_demand_kva_tier1_date = maximum_demand_kva_tier1_date;
		this.maximum_demand_kva_tier2 = maximum_demand_kva_tier2;
		this.maximum_demand_kva_tier2_date = maximum_demand_kva_tier2_date;
		this.maximum_demand_kva_tier3 = maximum_demand_kva_tier3;
		this.maximum_demand_kva_tier3_date = maximum_demand_kva_tier3_date;
		this.maximum_demand_kva_tier4 = maximum_demand_kva_tier4;
		this.maximum_demand_kva_tier4_date = maximum_demand_kva_tier4_date;
		this.maximum_demand_kva_tier5 = maximum_demand_kva_tier5;
		this.maximum_demand_kva_tier5_date = maximum_demand_kva_tier5_date;
		this.maximum_demand_kva_tier6 = maximum_demand_kva_tier6;
		this.maximum_demand_kva_tier6_date = maximum_demand_kva_tier6_date;
		this.maximum_demand_kva_tier7 = maximum_demand_kva_tier7;
		this.maximum_demand_kva_tier7_date = maximum_demand_kva_tier7_date;
		this.maximum_demand_kva_tier8 = maximum_demand_kva_tier8;
		this.maximum_demand_kva_tier8_date = maximum_demand_kva_tier8_date;
		this.maximum_demand_kw = maximum_demand_kw;
		this.maximum_demand_kw_date = maximum_demand_kw_date;
		this.maximum_demand_kw_tier1 = maximum_demand_kw_tier1;
		this.maximum_demand_kw_tier1_date = maximum_demand_kw_tier1_date;
		this.maximum_demand_kw_tier2 = maximum_demand_kw_tier2;
		this.maximum_demand_kw_tier2_date = maximum_demand_kw_tier2_date;
		this.maximum_demand_kw_tier3 = maximum_demand_kw_tier3;
		this.maximum_demand_kw_tier3_date = maximum_demand_kw_tier3_date;
		this.maximum_demand_kw_tier4 = maximum_demand_kw_tier4;
		this.maximum_demand_kw_tier4_date = maximum_demand_kw_tier4_date;
		this.maximum_demand_kw_tier5 = maximum_demand_kw_tier5;
		this.maximum_demand_kw_tier5_date = maximum_demand_kw_tier5_date;
		this.maximum_demand_kw_tier6 = maximum_demand_kw_tier6;
		this.maximum_demand_kw_tier6_date = maximum_demand_kw_tier6_date;
		this.maximum_demand_kw_tier7 = maximum_demand_kw_tier7;
		this.maximum_demand_kw_tier7_date = maximum_demand_kw_tier7_date;
		this.maximum_demand_kw_tier8 = maximum_demand_kw_tier8;
		this.maximum_demand_kw_tier8_date = maximum_demand_kw_tier8_date;
		this.mdas_datetime = mdas_datetime;
		this.meter_datetime = meter_datetime;
		this.owner_name = owner_name;
		this.power_on_duration_minutes = power_on_duration_minutes;
		this.subdevision_name = subdevision_name;
		this.substation_name = substation_name;
		this.system_power_factor_billing_period = system_power_factor_billing_period;
		this.cumulative_energy_kvarh_lag = cumulative_energy_kvarh_lag;
		this.cumulative_energy_kwh_export_tier1 = cumulative_energy_kwh_export_tier1;
		this.cumulative_energy_kwh_export_tier2 = cumulative_energy_kwh_export_tier2;
		this.cumulative_energy_kwh_export_tier3 = cumulative_energy_kwh_export_tier3;
		this.cumulative_energy_kwh_export_tier4 = cumulative_energy_kwh_export_tier4;
		this.cumulative_energy_kwh_export_tier5 = cumulative_energy_kwh_export_tier5;
		this.cumulative_energy_kwh_export_tier6 = cumulative_energy_kwh_export_tier6;
		this.cumulative_energy_kwh_export_tier7 = cumulative_energy_kwh_export_tier7;
		this.cumulative_energy_kwh_export_tier8 = cumulative_energy_kwh_export_tier8;
		this.cumulative_energy_kvah_export_tier1 = cumulative_energy_kvah_export_tier1;
		this.cumulative_energy_kvah_export_tier2 = cumulative_energy_kvah_export_tier2;
		this.cumulative_energy_kvah_export_tier3 = cumulative_energy_kvah_export_tier3;
		this.cumulative_energy_kvah_export_tier4 = cumulative_energy_kvah_export_tier4;
		this.cumulative_energy_kvah_export_tier5 = cumulative_energy_kvah_export_tier5;
		this.cumulative_energy_kvah_export_tier6 = cumulative_energy_kvah_export_tier6;
		this.cumulative_energy_kvah_export_tier7 = cumulative_energy_kvah_export_tier7;
		this.cumulative_energy_kvah_export_tier8 = cumulative_energy_kvah_export_tier8;
		this.maximum_demand_kw_export = maximum_demand_kw_export;
		this.maximum_demand_kw_export_date = maximum_demand_kw_export_date;
		this.maximum_demand_kw_export_tier1 = maximum_demand_kw_export_tier1;
		this.maximum_demand_kw_export_tier2 = maximum_demand_kw_export_tier2;
		this.maximum_demand_kw_export_tier3 = maximum_demand_kw_export_tier3;
		this.maximum_demand_kw_export_tier4 = maximum_demand_kw_export_tier4;
		this.maximum_demand_kw_export_tier5 = maximum_demand_kw_export_tier5;
		this.maximum_demand_kw_export_tier6 = maximum_demand_kw_export_tier6;
		this.maximum_demand_kw_export_tier7 = maximum_demand_kw_export_tier7;
		this.maximum_demand_kw_export_tier8 = maximum_demand_kw_export_tier8;
		this.maximum_demand_kw_date_export_tier1 = maximum_demand_kw_date_export_tier1;
		this.maximum_demand_kw_date_export_tier2 = maximum_demand_kw_date_export_tier2;
		this.maximum_demand_kw_date_export_tier3 = maximum_demand_kw_date_export_tier3;
		this.maximum_demand_kw_date_export_tier4 = maximum_demand_kw_date_export_tier4;
		this.maximum_demand_kw_date_export_tier5 = maximum_demand_kw_date_export_tier5;
		this.maximum_demand_kw_date_export_tier6 = maximum_demand_kw_date_export_tier6;
		this.maximum_demand_kw_date_export_tier7 = maximum_demand_kw_date_export_tier7;
		this.maximum_demand_kw_date_export_tier8 = maximum_demand_kw_date_export_tier8;
		this.maximum_demand_kva_export = maximum_demand_kva_export;
		this.maximum_demand_kva_export_date = maximum_demand_kva_export_date;
		this.maximum_demand_kva_export_tier1 = maximum_demand_kva_export_tier1;
		this.maximum_demand_kva_export_tier2 = maximum_demand_kva_export_tier2;
		this.maximum_demand_kva_export_tier3 = maximum_demand_kva_export_tier3;
		this.maximum_demand_kva_export_tier4 = maximum_demand_kva_export_tier4;
		this.maximum_demand_kva_export_tier5 = maximum_demand_kva_export_tier5;
		this.maximum_demand_kva_export_tier6 = maximum_demand_kva_export_tier6;
		this.maximum_demand_kva_export_tier7 = maximum_demand_kva_export_tier7;
		this.maximum_demand_kva_export_tier8 = maximum_demand_kva_export_tier8;
		this.maximum_demand_kva_date_export_tier1 = maximum_demand_kva_date_export_tier1;
		this.maximum_demand_kva_date_export_tier2 = maximum_demand_kva_date_export_tier2;
		this.maximum_demand_kva_date_export_tier3 = maximum_demand_kva_date_export_tier3;
		this.maximum_demand_kva_date_export_tier4 = maximum_demand_kva_date_export_tier4;
		this.maximum_demand_kva_date_export_tier5 = maximum_demand_kva_date_export_tier5;
		this.maximum_demand_kva_date_export_tier6 = maximum_demand_kva_date_export_tier6;
		this.maximum_demand_kva_date_export_tier7 = maximum_demand_kva_date_export_tier7;
		this.maximum_demand_kva_date_export_tier8 = maximum_demand_kva_date_export_tier8;
		this.tamper_count = tamper_count;
	}


	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public Date getBilling_datetime() {
		return billing_datetime;
	}

	public void setBilling_datetime(Date billing_datetime) {
		this.billing_datetime = billing_datetime;
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

	public Double getMaximum_demand_kva() {
		return maximum_demand_kva;
	}

	public void setMaximum_demand_kva(Double maximum_demand_kva) {
		this.maximum_demand_kva = maximum_demand_kva;
	}

	public Date getMaximum_demand_kva_date() {
		return maximum_demand_kva_date;
	}

	public void setMaximum_demand_kva_date(Date maximum_demand_kva_date) {
		this.maximum_demand_kva_date = maximum_demand_kva_date;
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

	public Double getMaximum_demand_kw() {
		return maximum_demand_kw;
	}

	public void setMaximum_demand_kw(Double maximum_demand_kw) {
		this.maximum_demand_kw = maximum_demand_kw;
	}

	public Date getMaximum_demand_kw_date() {
		return maximum_demand_kw_date;
	}

	public void setMaximum_demand_kw_date(Date maximum_demand_kw_date) {
		this.maximum_demand_kw_date = maximum_demand_kw_date;
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

	public Integer getPower_on_duration_mins() {
		return power_on_duration_mins;
	}

	public void setPower_on_duration_mins(Integer power_on_duration_mins) {
		this.power_on_duration_mins = power_on_duration_mins;
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

	public Double getSystem_power_factor_billing_period() {
		return system_power_factor_billing_period;
	}

	public void setSystem_power_factor_billing_period(Double system_power_factor_billing_period) {
		this.system_power_factor_billing_period = system_power_factor_billing_period;
	}

	public Double getCumulative_energy_kvarh_lag() {
		return cumulative_energy_kvarh_lag;
	}

	public void setCumulative_energy_kvarh_lag(Double cumulative_energy_kvarh_lag) {
		this.cumulative_energy_kvarh_lag = cumulative_energy_kvarh_lag;
	}

	public Double getCumulative_energy_kwh_export_tier1() {
		return cumulative_energy_kwh_export_tier1;
	}

	public void setCumulative_energy_kwh_export_tier1(Double cumulative_energy_kwh_export_tier1) {
		this.cumulative_energy_kwh_export_tier1 = cumulative_energy_kwh_export_tier1;
	}

	public Double getCumulative_energy_kwh_export_tier2() {
		return cumulative_energy_kwh_export_tier2;
	}

	public void setCumulative_energy_kwh_export_tier2(Double cumulative_energy_kwh_export_tier2) {
		this.cumulative_energy_kwh_export_tier2 = cumulative_energy_kwh_export_tier2;
	}

	public Double getCumulative_energy_kwh_export_tier3() {
		return cumulative_energy_kwh_export_tier3;
	}

	public void setCumulative_energy_kwh_export_tier3(Double cumulative_energy_kwh_export_tier3) {
		this.cumulative_energy_kwh_export_tier3 = cumulative_energy_kwh_export_tier3;
	}

	public Double getCumulative_energy_kwh_export_tier4() {
		return cumulative_energy_kwh_export_tier4;
	}

	public void setCumulative_energy_kwh_export_tier4(Double cumulative_energy_kwh_export_tier4) {
		this.cumulative_energy_kwh_export_tier4 = cumulative_energy_kwh_export_tier4;
	}

	public Double getCumulative_energy_kwh_export_tier5() {
		return cumulative_energy_kwh_export_tier5;
	}

	public void setCumulative_energy_kwh_export_tier5(Double cumulative_energy_kwh_export_tier5) {
		this.cumulative_energy_kwh_export_tier5 = cumulative_energy_kwh_export_tier5;
	}

	public Double getCumulative_energy_kwh_export_tier6() {
		return cumulative_energy_kwh_export_tier6;
	}

	public void setCumulative_energy_kwh_export_tier6(Double cumulative_energy_kwh_export_tier6) {
		this.cumulative_energy_kwh_export_tier6 = cumulative_energy_kwh_export_tier6;
	}

	public Double getCumulative_energy_kwh_export_tier7() {
		return cumulative_energy_kwh_export_tier7;
	}

	public void setCumulative_energy_kwh_export_tier7(Double cumulative_energy_kwh_export_tier7) {
		this.cumulative_energy_kwh_export_tier7 = cumulative_energy_kwh_export_tier7;
	}

	public Double getCumulative_energy_kwh_export_tier8() {
		return cumulative_energy_kwh_export_tier8;
	}

	public void setCumulative_energy_kwh_export_tier8(Double cumulative_energy_kwh_export_tier8) {
		this.cumulative_energy_kwh_export_tier8 = cumulative_energy_kwh_export_tier8;
	}

	public Double getCumulative_energy_kvah_export_tier1() {
		return cumulative_energy_kvah_export_tier1;
	}

	public void setCumulative_energy_kvah_export_tier1(Double cumulative_energy_kvah_export_tier1) {
		this.cumulative_energy_kvah_export_tier1 = cumulative_energy_kvah_export_tier1;
	}

	public Double getCumulative_energy_kvah_export_tier2() {
		return cumulative_energy_kvah_export_tier2;
	}

	public void setCumulative_energy_kvah_export_tier2(Double cumulative_energy_kvah_export_tier2) {
		this.cumulative_energy_kvah_export_tier2 = cumulative_energy_kvah_export_tier2;
	}

	public Double getCumulative_energy_kvah_export_tier3() {
		return cumulative_energy_kvah_export_tier3;
	}

	public void setCumulative_energy_kvah_export_tier3(Double cumulative_energy_kvah_export_tier3) {
		this.cumulative_energy_kvah_export_tier3 = cumulative_energy_kvah_export_tier3;
	}

	public Double getCumulative_energy_kvah_export_tier4() {
		return cumulative_energy_kvah_export_tier4;
	}

	public void setCumulative_energy_kvah_export_tier4(Double cumulative_energy_kvah_export_tier4) {
		this.cumulative_energy_kvah_export_tier4 = cumulative_energy_kvah_export_tier4;
	}

	public Double getCumulative_energy_kvah_export_tier5() {
		return cumulative_energy_kvah_export_tier5;
	}

	public void setCumulative_energy_kvah_export_tier5(Double cumulative_energy_kvah_export_tier5) {
		this.cumulative_energy_kvah_export_tier5 = cumulative_energy_kvah_export_tier5;
	}

	public Double getCumulative_energy_kvah_export_tier6() {
		return cumulative_energy_kvah_export_tier6;
	}

	public void setCumulative_energy_kvah_export_tier6(Double cumulative_energy_kvah_export_tier6) {
		this.cumulative_energy_kvah_export_tier6 = cumulative_energy_kvah_export_tier6;
	}

	public Double getCumulative_energy_kvah_export_tier7() {
		return cumulative_energy_kvah_export_tier7;
	}

	public void setCumulative_energy_kvah_export_tier7(Double cumulative_energy_kvah_export_tier7) {
		this.cumulative_energy_kvah_export_tier7 = cumulative_energy_kvah_export_tier7;
	}

	public Double getCumulative_energy_kvah_export_tier8() {
		return cumulative_energy_kvah_export_tier8;
	}

	public void setCumulative_energy_kvah_export_tier8(Double cumulative_energy_kvah_export_tier8) {
		this.cumulative_energy_kvah_export_tier8 = cumulative_energy_kvah_export_tier8;
	}

	public Double getMaximum_demand_kw_export() {
		return maximum_demand_kw_export;
	}

	public void setMaximum_demand_kw_export(Double maximum_demand_kw_export) {
		this.maximum_demand_kw_export = maximum_demand_kw_export;
	}

	public Date getMaximum_demand_kw_export_date() {
		return maximum_demand_kw_export_date;
	}

	public void setMaximum_demand_kw_export_date(Date maximum_demand_kw_export_date) {
		this.maximum_demand_kw_export_date = maximum_demand_kw_export_date;
	}

	public Double getMaximum_demand_kw_export_tier1() {
		return maximum_demand_kw_export_tier1;
	}

	public void setMaximum_demand_kw_export_tier1(Double maximum_demand_kw_export_tier1) {
		this.maximum_demand_kw_export_tier1 = maximum_demand_kw_export_tier1;
	}

	public Double getMaximum_demand_kw_export_tier2() {
		return maximum_demand_kw_export_tier2;
	}

	public void setMaximum_demand_kw_export_tier2(Double maximum_demand_kw_export_tier2) {
		this.maximum_demand_kw_export_tier2 = maximum_demand_kw_export_tier2;
	}

	public Double getMaximum_demand_kw_export_tier3() {
		return maximum_demand_kw_export_tier3;
	}

	public void setMaximum_demand_kw_export_tier3(Double maximum_demand_kw_export_tier3) {
		this.maximum_demand_kw_export_tier3 = maximum_demand_kw_export_tier3;
	}

	public Double getMaximum_demand_kw_export_tier4() {
		return maximum_demand_kw_export_tier4;
	}

	public void setMaximum_demand_kw_export_tier4(Double maximum_demand_kw_export_tier4) {
		this.maximum_demand_kw_export_tier4 = maximum_demand_kw_export_tier4;
	}

	public Double getMaximum_demand_kw_export_tier5() {
		return maximum_demand_kw_export_tier5;
	}

	public void setMaximum_demand_kw_export_tier5(Double maximum_demand_kw_export_tier5) {
		this.maximum_demand_kw_export_tier5 = maximum_demand_kw_export_tier5;
	}

	public Double getMaximum_demand_kw_export_tier6() {
		return maximum_demand_kw_export_tier6;
	}

	public void setMaximum_demand_kw_export_tier6(Double maximum_demand_kw_export_tier6) {
		this.maximum_demand_kw_export_tier6 = maximum_demand_kw_export_tier6;
	}

	public Double getMaximum_demand_kw_export_tier7() {
		return maximum_demand_kw_export_tier7;
	}

	public void setMaximum_demand_kw_export_tier7(Double maximum_demand_kw_export_tier7) {
		this.maximum_demand_kw_export_tier7 = maximum_demand_kw_export_tier7;
	}

	public Double getMaximum_demand_kw_export_tier8() {
		return maximum_demand_kw_export_tier8;
	}

	public void setMaximum_demand_kw_export_tier8(Double maximum_demand_kw_export_tier8) {
		this.maximum_demand_kw_export_tier8 = maximum_demand_kw_export_tier8;
	}

	public Date getMaximum_demand_kw_date_export_tier1() {
		return maximum_demand_kw_date_export_tier1;
	}

	public void setMaximum_demand_kw_date_export_tier1(Date maximum_demand_kw_date_export_tier1) {
		this.maximum_demand_kw_date_export_tier1 = maximum_demand_kw_date_export_tier1;
	}

	public Date getMaximum_demand_kw_date_export_tier2() {
		return maximum_demand_kw_date_export_tier2;
	}

	public void setMaximum_demand_kw_date_export_tier2(Date maximum_demand_kw_date_export_tier2) {
		this.maximum_demand_kw_date_export_tier2 = maximum_demand_kw_date_export_tier2;
	}

	public Date getMaximum_demand_kw_date_export_tier3() {
		return maximum_demand_kw_date_export_tier3;
	}

	public void setMaximum_demand_kw_date_export_tier3(Date maximum_demand_kw_date_export_tier3) {
		this.maximum_demand_kw_date_export_tier3 = maximum_demand_kw_date_export_tier3;
	}

	public Date getMaximum_demand_kw_date_export_tier4() {
		return maximum_demand_kw_date_export_tier4;
	}

	public void setMaximum_demand_kw_date_export_tier4(Date maximum_demand_kw_date_export_tier4) {
		this.maximum_demand_kw_date_export_tier4 = maximum_demand_kw_date_export_tier4;
	}

	public Date getMaximum_demand_kw_date_export_tier5() {
		return maximum_demand_kw_date_export_tier5;
	}

	public void setMaximum_demand_kw_date_export_tier5(Date maximum_demand_kw_date_export_tier5) {
		this.maximum_demand_kw_date_export_tier5 = maximum_demand_kw_date_export_tier5;
	}

	public Date getMaximum_demand_kw_date_export_tier6() {
		return maximum_demand_kw_date_export_tier6;
	}

	public void setMaximum_demand_kw_date_export_tier6(Date maximum_demand_kw_date_export_tier6) {
		this.maximum_demand_kw_date_export_tier6 = maximum_demand_kw_date_export_tier6;
	}

	public Date getMaximum_demand_kw_date_export_tier7() {
		return maximum_demand_kw_date_export_tier7;
	}

	public void setMaximum_demand_kw_date_export_tier7(Date maximum_demand_kw_date_export_tier7) {
		this.maximum_demand_kw_date_export_tier7 = maximum_demand_kw_date_export_tier7;
	}

	public Date getMaximum_demand_kw_date_export_tier8() {
		return maximum_demand_kw_date_export_tier8;
	}

	public void setMaximum_demand_kw_date_export_tier8(Date maximum_demand_kw_date_export_tier8) {
		this.maximum_demand_kw_date_export_tier8 = maximum_demand_kw_date_export_tier8;
	}

	public Double getMaximum_demand_kva_export() {
		return maximum_demand_kva_export;
	}

	public void setMaximum_demand_kva_export(Double maximum_demand_kva_export) {
		this.maximum_demand_kva_export = maximum_demand_kva_export;
	}

	public Date getMaximum_demand_kva_export_date() {
		return maximum_demand_kva_export_date;
	}

	public void setMaximum_demand_kva_export_date(Date maximum_demand_kva_export_date) {
		this.maximum_demand_kva_export_date = maximum_demand_kva_export_date;
	}

	public Double getMaximum_demand_kva_export_tier1() {
		return maximum_demand_kva_export_tier1;
	}

	public void setMaximum_demand_kva_export_tier1(Double maximum_demand_kva_export_tier1) {
		this.maximum_demand_kva_export_tier1 = maximum_demand_kva_export_tier1;
	}

	public Double getMaximum_demand_kva_export_tier2() {
		return maximum_demand_kva_export_tier2;
	}

	public void setMaximum_demand_kva_export_tier2(Double maximum_demand_kva_export_tier2) {
		this.maximum_demand_kva_export_tier2 = maximum_demand_kva_export_tier2;
	}

	public Double getMaximum_demand_kva_export_tier3() {
		return maximum_demand_kva_export_tier3;
	}

	public void setMaximum_demand_kva_export_tier3(Double maximum_demand_kva_export_tier3) {
		this.maximum_demand_kva_export_tier3 = maximum_demand_kva_export_tier3;
	}

	public Double getMaximum_demand_kva_export_tier4() {
		return maximum_demand_kva_export_tier4;
	}

	public void setMaximum_demand_kva_export_tier4(Double maximum_demand_kva_export_tier4) {
		this.maximum_demand_kva_export_tier4 = maximum_demand_kva_export_tier4;
	}

	public Double getMaximum_demand_kva_export_tier5() {
		return maximum_demand_kva_export_tier5;
	}

	public void setMaximum_demand_kva_export_tier5(Double maximum_demand_kva_export_tier5) {
		this.maximum_demand_kva_export_tier5 = maximum_demand_kva_export_tier5;
	}

	public Double getMaximum_demand_kva_export_tier6() {
		return maximum_demand_kva_export_tier6;
	}

	public void setMaximum_demand_kva_export_tier6(Double maximum_demand_kva_export_tier6) {
		this.maximum_demand_kva_export_tier6 = maximum_demand_kva_export_tier6;
	}

	public Double getMaximum_demand_kva_export_tier7() {
		return maximum_demand_kva_export_tier7;
	}

	public void setMaximum_demand_kva_export_tier7(Double maximum_demand_kva_export_tier7) {
		this.maximum_demand_kva_export_tier7 = maximum_demand_kva_export_tier7;
	}

	public Double getMaximum_demand_kva_export_tier8() {
		return maximum_demand_kva_export_tier8;
	}

	public void setMaximum_demand_kva_export_tier8(Double maximum_demand_kva_export_tier8) {
		this.maximum_demand_kva_export_tier8 = maximum_demand_kva_export_tier8;
	}

	public Date getMaximum_demand_kva_date_export_tier1() {
		return maximum_demand_kva_date_export_tier1;
	}

	public void setMaximum_demand_kva_date_export_tier1(Date maximum_demand_kva_date_export_tier1) {
		this.maximum_demand_kva_date_export_tier1 = maximum_demand_kva_date_export_tier1;
	}

	public Date getMaximum_demand_kva_date_export_tier2() {
		return maximum_demand_kva_date_export_tier2;
	}

	public void setMaximum_demand_kva_date_export_tier2(Date maximum_demand_kva_date_export_tier2) {
		this.maximum_demand_kva_date_export_tier2 = maximum_demand_kva_date_export_tier2;
	}

	public Date getMaximum_demand_kva_date_export_tier3() {
		return maximum_demand_kva_date_export_tier3;
	}

	public void setMaximum_demand_kva_date_export_tier3(Date maximum_demand_kva_date_export_tier3) {
		this.maximum_demand_kva_date_export_tier3 = maximum_demand_kva_date_export_tier3;
	}

	public Date getMaximum_demand_kva_date_export_tier4() {
		return maximum_demand_kva_date_export_tier4;
	}

	public void setMaximum_demand_kva_date_export_tier4(Date maximum_demand_kva_date_export_tier4) {
		this.maximum_demand_kva_date_export_tier4 = maximum_demand_kva_date_export_tier4;
	}

	public Date getMaximum_demand_kva_date_export_tier5() {
		return maximum_demand_kva_date_export_tier5;
	}

	public void setMaximum_demand_kva_date_export_tier5(Date maximum_demand_kva_date_export_tier5) {
		this.maximum_demand_kva_date_export_tier5 = maximum_demand_kva_date_export_tier5;
	}

	public Date getMaximum_demand_kva_date_export_tier6() {
		return maximum_demand_kva_date_export_tier6;
	}

	public void setMaximum_demand_kva_date_export_tier6(Date maximum_demand_kva_date_export_tier6) {
		this.maximum_demand_kva_date_export_tier6 = maximum_demand_kva_date_export_tier6;
	}

	public Date getMaximum_demand_kva_date_export_tier7() {
		return maximum_demand_kva_date_export_tier7;
	}

	public void setMaximum_demand_kva_date_export_tier7(Date maximum_demand_kva_date_export_tier7) {
		this.maximum_demand_kva_date_export_tier7 = maximum_demand_kva_date_export_tier7;
	}

	public Date getMaximum_demand_kva_date_export_tier8() {
		return maximum_demand_kva_date_export_tier8;
	}

	public void setMaximum_demand_kva_date_export_tier8(Date maximum_demand_kva_date_export_tier8) {
		this.maximum_demand_kva_date_export_tier8 = maximum_demand_kva_date_export_tier8;
	}

	public Integer getTamper_count() {
		return tamper_count;
	}

	public void setTamper_count(Integer tamper_count) {
		this.tamper_count = tamper_count;
	}

	public Long getPower_on_duration_minutes() {
		return power_on_duration_minutes;
	}

	public void setPower_on_duration_minutes(Long power_on_duration_minutes) {
		this.power_on_duration_minutes = power_on_duration_minutes;
	}

	@Override
	public String toString() {
		return "BillingDataCT [device_serial_number=" + device_serial_number + ", billing_datetime=" + billing_datetime
				+ ", cumulative_energy_kvah_export=" + cumulative_energy_kvah_export
				+ ", cumulative_energy_kvah_import=" + cumulative_energy_kvah_import + ", cumulative_energy_kvah_tier1="
				+ cumulative_energy_kvah_tier1 + ", cumulative_energy_kvah_tier2=" + cumulative_energy_kvah_tier2
				+ ", cumulative_energy_kvah_tier3=" + cumulative_energy_kvah_tier3 + ", cumulative_energy_kvah_tier4="
				+ cumulative_energy_kvah_tier4 + ", cumulative_energy_kvah_tier5=" + cumulative_energy_kvah_tier5
				+ ", cumulative_energy_kvah_tier6=" + cumulative_energy_kvah_tier6 + ", cumulative_energy_kvah_tier7="
				+ cumulative_energy_kvah_tier7 + ", cumulative_energy_kvah_tier8=" + cumulative_energy_kvah_tier8
				+ ", cumulative_energy_kvarh_q1=" + cumulative_energy_kvarh_q1 + ", cumulative_energy_kvarh_q2="
				+ cumulative_energy_kvarh_q2 + ", cumulative_energy_kvarh_q3=" + cumulative_energy_kvarh_q3
				+ ", cumulative_energy_kvarh_q4=" + cumulative_energy_kvarh_q4 + ", cumulative_energy_kwh_export="
				+ cumulative_energy_kwh_export + ", cumulative_energy_kwh_import=" + cumulative_energy_kwh_import
				+ ", cumulative_energy_kwh_tier1=" + cumulative_energy_kwh_tier1 + ", cumulative_energy_kwh_tier2="
				+ cumulative_energy_kwh_tier2 + ", cumulative_energy_kwh_tier3=" + cumulative_energy_kwh_tier3
				+ ", cumulative_energy_kwh_tier4=" + cumulative_energy_kwh_tier4 + ", cumulative_energy_kwh_tier5="
				+ cumulative_energy_kwh_tier5 + ", cumulative_energy_kwh_tier6=" + cumulative_energy_kwh_tier6
				+ ", cumulative_energy_kwh_tier7=" + cumulative_energy_kwh_tier7 + ", cumulative_energy_kwh_tier8="
				+ cumulative_energy_kwh_tier8 + ", dcu_serial_number=" + dcu_serial_number + ", dt_name=" + dt_name
				+ ", feeder_name=" + feeder_name + ", maximum_demand_kva=" + maximum_demand_kva
				+ ", maximum_demand_kva_date=" + maximum_demand_kva_date + ", maximum_demand_kva_tier1="
				+ maximum_demand_kva_tier1 + ", maximum_demand_kva_tier1_date=" + maximum_demand_kva_tier1_date
				+ ", maximum_demand_kva_tier2=" + maximum_demand_kva_tier2 + ", maximum_demand_kva_tier2_date="
				+ maximum_demand_kva_tier2_date + ", maximum_demand_kva_tier3=" + maximum_demand_kva_tier3
				+ ", maximum_demand_kva_tier3_date=" + maximum_demand_kva_tier3_date + ", maximum_demand_kva_tier4="
				+ maximum_demand_kva_tier4 + ", maximum_demand_kva_tier4_date=" + maximum_demand_kva_tier4_date
				+ ", maximum_demand_kva_tier5=" + maximum_demand_kva_tier5 + ", maximum_demand_kva_tier5_date="
				+ maximum_demand_kva_tier5_date + ", maximum_demand_kva_tier6=" + maximum_demand_kva_tier6
				+ ", maximum_demand_kva_tier6_date=" + maximum_demand_kva_tier6_date + ", maximum_demand_kva_tier7="
				+ maximum_demand_kva_tier7 + ", maximum_demand_kva_tier7_date=" + maximum_demand_kva_tier7_date
				+ ", maximum_demand_kva_tier8=" + maximum_demand_kva_tier8 + ", maximum_demand_kva_tier8_date="
				+ maximum_demand_kva_tier8_date + ", maximum_demand_kw=" + maximum_demand_kw
				+ ", maximum_demand_kw_date=" + maximum_demand_kw_date + ", maximum_demand_kw_tier1="
				+ maximum_demand_kw_tier1 + ", maximum_demand_kw_tier1_date=" + maximum_demand_kw_tier1_date
				+ ", maximum_demand_kw_tier2=" + maximum_demand_kw_tier2 + ", maximum_demand_kw_tier2_date="
				+ maximum_demand_kw_tier2_date + ", maximum_demand_kw_tier3=" + maximum_demand_kw_tier3
				+ ", maximum_demand_kw_tier3_date=" + maximum_demand_kw_tier3_date + ", maximum_demand_kw_tier4="
				+ maximum_demand_kw_tier4 + ", maximum_demand_kw_tier4_date=" + maximum_demand_kw_tier4_date
				+ ", maximum_demand_kw_tier5=" + maximum_demand_kw_tier5 + ", maximum_demand_kw_tier5_date="
				+ maximum_demand_kw_tier5_date + ", maximum_demand_kw_tier6=" + maximum_demand_kw_tier6
				+ ", maximum_demand_kw_tier6_date=" + maximum_demand_kw_tier6_date + ", maximum_demand_kw_tier7="
				+ maximum_demand_kw_tier7 + ", maximum_demand_kw_tier7_date=" + maximum_demand_kw_tier7_date
				+ ", maximum_demand_kw_tier8=" + maximum_demand_kw_tier8 + ", maximum_demand_kw_tier8_date="
				+ maximum_demand_kw_tier8_date + ", mdas_datetime=" + mdas_datetime + ", meter_datetime="
				+ meter_datetime + ", owner_name=" + owner_name + ", power_on_duration_minutes="
				+ power_on_duration_minutes + ", subdevision_name=" + subdevision_name + ", substation_name="
				+ substation_name + ", system_power_factor_billing_period=" + system_power_factor_billing_period
				+ ", cumulative_energy_kvarh_lag=" + cumulative_energy_kvarh_lag
				+ ", cumulative_energy_kwh_export_tier1=" + cumulative_energy_kwh_export_tier1
				+ ", cumulative_energy_kwh_export_tier2=" + cumulative_energy_kwh_export_tier2
				+ ", cumulative_energy_kwh_export_tier3=" + cumulative_energy_kwh_export_tier3
				+ ", cumulative_energy_kwh_export_tier4=" + cumulative_energy_kwh_export_tier4
				+ ", cumulative_energy_kwh_export_tier5=" + cumulative_energy_kwh_export_tier5
				+ ", cumulative_energy_kwh_export_tier6=" + cumulative_energy_kwh_export_tier6
				+ ", cumulative_energy_kwh_export_tier7=" + cumulative_energy_kwh_export_tier7
				+ ", cumulative_energy_kwh_export_tier8=" + cumulative_energy_kwh_export_tier8
				+ ", cumulative_energy_kvah_export_tier1=" + cumulative_energy_kvah_export_tier1
				+ ", cumulative_energy_kvah_export_tier2=" + cumulative_energy_kvah_export_tier2
				+ ", cumulative_energy_kvah_export_tier3=" + cumulative_energy_kvah_export_tier3
				+ ", cumulative_energy_kvah_export_tier4=" + cumulative_energy_kvah_export_tier4
				+ ", cumulative_energy_kvah_export_tier5=" + cumulative_energy_kvah_export_tier5
				+ ", cumulative_energy_kvah_export_tier6=" + cumulative_energy_kvah_export_tier6
				+ ", cumulative_energy_kvah_export_tier7=" + cumulative_energy_kvah_export_tier7
				+ ", cumulative_energy_kvah_export_tier8=" + cumulative_energy_kvah_export_tier8
				+ ", maximum_demand_kw_export=" + maximum_demand_kw_export + ", maximum_demand_kw_export_date="
				+ maximum_demand_kw_export_date + ", maximum_demand_kw_export_tier1=" + maximum_demand_kw_export_tier1
				+ ", maximum_demand_kw_export_tier2=" + maximum_demand_kw_export_tier2
				+ ", maximum_demand_kw_export_tier3=" + maximum_demand_kw_export_tier3
				+ ", maximum_demand_kw_export_tier4=" + maximum_demand_kw_export_tier4
				+ ", maximum_demand_kw_export_tier5=" + maximum_demand_kw_export_tier5
				+ ", maximum_demand_kw_export_tier6=" + maximum_demand_kw_export_tier6
				+ ", maximum_demand_kw_export_tier7=" + maximum_demand_kw_export_tier7
				+ ", maximum_demand_kw_export_tier8=" + maximum_demand_kw_export_tier8
				+ ", maximum_demand_kw_date_export_tier1=" + maximum_demand_kw_date_export_tier1
				+ ", maximum_demand_kw_date_export_tier2=" + maximum_demand_kw_date_export_tier2
				+ ", maximum_demand_kw_date_export_tier3=" + maximum_demand_kw_date_export_tier3
				+ ", maximum_demand_kw_date_export_tier4=" + maximum_demand_kw_date_export_tier4
				+ ", maximum_demand_kw_date_export_tier5=" + maximum_demand_kw_date_export_tier5
				+ ", maximum_demand_kw_date_export_tier6=" + maximum_demand_kw_date_export_tier6
				+ ", maximum_demand_kw_date_export_tier7=" + maximum_demand_kw_date_export_tier7
				+ ", maximum_demand_kw_date_export_tier8=" + maximum_demand_kw_date_export_tier8
				+ ", maximum_demand_kva_export=" + maximum_demand_kva_export + ", maximum_demand_kva_export_date="
				+ maximum_demand_kva_export_date + ", maximum_demand_kva_export_tier1="
				+ maximum_demand_kva_export_tier1 + ", maximum_demand_kva_export_tier2="
				+ maximum_demand_kva_export_tier2 + ", maximum_demand_kva_export_tier3="
				+ maximum_demand_kva_export_tier3 + ", maximum_demand_kva_export_tier4="
				+ maximum_demand_kva_export_tier4 + ", maximum_demand_kva_export_tier5="
				+ maximum_demand_kva_export_tier5 + ", maximum_demand_kva_export_tier6="
				+ maximum_demand_kva_export_tier6 + ", maximum_demand_kva_export_tier7="
				+ maximum_demand_kva_export_tier7 + ", maximum_demand_kva_export_tier8="
				+ maximum_demand_kva_export_tier8 + ", maximum_demand_kva_date_export_tier1="
				+ maximum_demand_kva_date_export_tier1 + ", maximum_demand_kva_date_export_tier2="
				+ maximum_demand_kva_date_export_tier2 + ", maximum_demand_kva_date_export_tier3="
				+ maximum_demand_kva_date_export_tier3 + ", maximum_demand_kva_date_export_tier4="
				+ maximum_demand_kva_date_export_tier4 + ", maximum_demand_kva_date_export_tier5="
				+ maximum_demand_kva_date_export_tier5 + ", maximum_demand_kva_date_export_tier6="
				+ maximum_demand_kva_date_export_tier6 + ", maximum_demand_kva_date_export_tier7="
				+ maximum_demand_kva_date_export_tier7 + ", maximum_demand_kva_date_export_tier8="
				+ maximum_demand_kva_date_export_tier8 + ", tamper_count=" + tamper_count + ", power_on_duration_mins="
				+ power_on_duration_mins + "]";
	}

}
