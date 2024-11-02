package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "push_instant_threephase")
public class PushInstantsThreePhase implements Serializable{

	private static final long serialVersionUID = 3983779723424327443L;

	@PrimaryKey
 	private String device_serial_number;
	private String device_id;
	private Double voltage_l1;
	private Double voltage_l2;
	private Double voltage_l3;
	private Double current_l1;
	private Double current_l2;
	private Double current_l3;
	private Double pf_l1;
	private Double pf_l2;
	private Double pf_l3;
	private Double cum_import_kwh;
	private Double cum_export_kwh;
	private Double cum_import_kvah;
	private Double cum_export_kvah;
	private Date meter_datetime;
    private Date mdas_datetime;
    private String dt_name;
    private String feeder_name;
    private String owner_name;
    private String subdivision_name;
    private String substation_name;
    private Double signed_pf;
    private Integer tamper_count;
	
    public PushInstantsThreePhase() {
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

	public Double getVoltage_l1() {
		return voltage_l1;
	}

	public void setVoltage_l1(Double voltage_l1) {
		this.voltage_l1 = voltage_l1;
	}

	public Double getVoltage_l2() {
		return voltage_l2;
	}

	public void setVoltage_l2(Double voltage_l2) {
		this.voltage_l2 = voltage_l2;
	}

	public Double getVoltage_l3() {
		return voltage_l3;
	}

	public void setVoltage_l3(Double voltage_l3) {
		this.voltage_l3 = voltage_l3;
	}

	public Double getCurrent_l1() {
		return current_l1;
	}

	public void setCurrent_l1(Double current_l1) {
		this.current_l1 = current_l1;
	}

	public Double getCurrent_l2() {
		return current_l2;
	}

	public void setCurrent_l2(Double current_l2) {
		this.current_l2 = current_l2;
	}

	public Double getCurrent_l3() {
		return current_l3;
	}

	public void setCurrent_l3(Double current_l3) {
		this.current_l3 = current_l3;
	}

	public Double getPf_l1() {
		return pf_l1;
	}

	public void setPf_l1(Double pf_l1) {
		this.pf_l1 = pf_l1;
	}

	public Double getPf_l2() {
		return pf_l2;
	}

	public void setPf_l2(Double pf_l2) {
		this.pf_l2 = pf_l2;
	}

	public Double getPf_l3() {
		return pf_l3;
	}

	public void setPf_l3(Double pf_l3) {
		this.pf_l3 = pf_l3;
	}

	public Double getCum_import_kwh() {
		return cum_import_kwh;
	}

	public void setCum_import_kwh(Double cum_import_kwh) {
		this.cum_import_kwh = cum_import_kwh;
	}

	public Double getCum_export_kwh() {
		return cum_export_kwh;
	}

	public void setCum_export_kwh(Double cum_export_kwh) {
		this.cum_export_kwh = cum_export_kwh;
	}

	public Double getCum_import_kvah() {
		return cum_import_kvah;
	}

	public void setCum_import_kvah(Double cum_import_kvah) {
		this.cum_import_kvah = cum_import_kvah;
	}

	public Double getCum_export_kvah() {
		return cum_export_kvah;
	}

	public void setCum_export_kvah(Double cum_export_kvah) {
		this.cum_export_kvah = cum_export_kvah;
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

	public Double getSigned_pf() {
		return signed_pf;
	}

	public void setSigned_pf(Double signed_pf) {
		this.signed_pf = signed_pf;
	}

	public Integer getTamper_count() {
		return tamper_count;
	}

	public void setTamper_count(Integer tamper_count) {
		this.tamper_count = tamper_count;
	}

}
