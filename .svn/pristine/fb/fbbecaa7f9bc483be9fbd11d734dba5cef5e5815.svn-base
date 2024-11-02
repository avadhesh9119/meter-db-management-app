package com.global.meter.v3.inventive.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "manufacturer_specific_obis_code")
public class ManufacturerSpecificObis implements Serializable {

	private static final long serialVersionUID = -5290816761455278114L;

	@PrimaryKey
	private String manufacturer;
	private String profile_type;
	private Set<String> default_obis_code;
	private String device_type;
	private Set<String> extra_obis_code;
	private Date mdas_datetime;
	private Set<String> obis_code;
	private String status;
	private Date occurence_datetime;
	private Date implementation_datetime;
	private String version;

	public ManufacturerSpecificObis() {
		super();
	}

	public ManufacturerSpecificObis(String manufacturer, String profile_type, Set<String> default_obis_code,
			String device_type, Set<String> extra_obis_code, Date mdas_datetime, Set<String> obis_code, String status,
			Date occurence_datetime, Date implementation_datetime, String version) {
		super();
		this.manufacturer = manufacturer;
		this.profile_type = profile_type;
		this.default_obis_code = default_obis_code;
		this.device_type = device_type;
		this.extra_obis_code = extra_obis_code;
		this.mdas_datetime = mdas_datetime;
		this.obis_code = obis_code;
		this.status = status;
		this.occurence_datetime = occurence_datetime;
		this.implementation_datetime = implementation_datetime;
		this.version = version;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getProfile_type() {
		return profile_type;
	}

	public void setProfile_type(String profile_type) {
		this.profile_type = profile_type;
	}

	public Set<String> getDefault_obis_code() {
		return default_obis_code;
	}

	public void setDefault_obis_code(Set<String> default_obis_code) {
		this.default_obis_code = default_obis_code;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public Set<String> getExtra_obis_code() {
		return extra_obis_code;
	}

	public void setExtra_obis_code(Set<String> extra_obis_code) {
		this.extra_obis_code = extra_obis_code;
	}

	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	public Set<String> getObis_code() {
		return obis_code;
	}

	public void setObis_code(Set<String> obis_code) {
		this.obis_code = obis_code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOccurence_datetime() {
		return occurence_datetime;
	}

	public void setOccurence_datetime(Date occurence_datetime) {
		this.occurence_datetime = occurence_datetime;
	}

	public Date getImplementation_datetime() {
		return implementation_datetime;
	}

	public void setImplementation_datetime(Date implementation_datetime) {
		this.implementation_datetime = implementation_datetime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "ManufacturerSpecificObis [manufacturer=" + manufacturer + ", profile_type=" + profile_type
				+ ", default_obis_code=" + default_obis_code + ", device_type=" + device_type + ", extra_obis_code="
				+ extra_obis_code + ", mdas_datetime=" + mdas_datetime + ", obis_code=" + obis_code + ", status="
				+ status + ", occurence_datetime=" + occurence_datetime + ", implementation_datetime="
				+ implementation_datetime + ", version=" + version + "]";
	}

}
