package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "dcu_info")
public class DcuMasterInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6692567871177252257L;

	@PrimaryKey
	private String dcu_serial_number;

	private String dcu_mac_nic_id;
	private double frequency;
	private String manufacturer;
	private String ipv6_address;
	private String sim_no;
	private String network;
	private String latitude;
	private String longitude;
	private String remarks;
	private String owner;
	private Date created_datetime; 
	private String created_by;
	private String updated_by;
	private Date updated_datetime;
	private String deleted_by;
	private Date deleted_datetime;
	
	public DcuMasterInfo() {
	}
	
	public DcuMasterInfo(String dcu_serial_number, String dcu_mac_nic_id, double frequency, String manufacturer,
			String ipv6_address, String sim_no, String network, String latitude, String longitude, String remarks,
			String owner, Date created_datetime, String created_by, String updated_by, Date updated_datetime,
			String deleted_by, Date deleted_datetime) {
		super();
		this.dcu_serial_number = dcu_serial_number;
		this.dcu_mac_nic_id = dcu_mac_nic_id;
		this.frequency = frequency;
		this.manufacturer = manufacturer;
		this.ipv6_address = ipv6_address;
		this.sim_no = sim_no;
		this.network = network;
		this.latitude = latitude;
		this.longitude = longitude;
		this.remarks = remarks;
		this.owner = owner;
		this.created_datetime = created_datetime;
		this.created_by = created_by;
		this.updated_by = updated_by;
		this.updated_datetime = updated_datetime;
		this.deleted_by = deleted_by;
		this.deleted_datetime = deleted_datetime;
	}

	public String getDcu_serial_number() {
		return dcu_serial_number;
	}

	public void setDcu_serial_number(String dcu_serial_number) {
		this.dcu_serial_number = dcu_serial_number;
	}

	public String getDcu_mac_nic_id() {
		return dcu_mac_nic_id;
	}

	public void setDcu_mac_nic_id(String dcu_mac_nic_id) {
		this.dcu_mac_nic_id = dcu_mac_nic_id;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getIpv6_address() {
		return ipv6_address;
	}

	public void setIpv6_address(String ipv6_address) {
		this.ipv6_address = ipv6_address;
	}

	public String getSim_no() {
		return sim_no;
	}

	public void setSim_no(String sim_no) {
		this.sim_no = sim_no;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getCreated_datetime() {
		return created_datetime;
	}

	public void setCreated_datetime(Date created_datetime) {
		this.created_datetime = created_datetime;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Date getUpdated_datetime() {
		return updated_datetime;
	}

	public void setUpdated_datetime(Date updated_datetime) {
		this.updated_datetime = updated_datetime;
	}

	public String getDeleted_by() {
		return deleted_by;
	}

	public void setDeleted_by(String deleted_by) {
		this.deleted_by = deleted_by;
	}

	public Date getDeleted_datetime() {
		return deleted_datetime;
	}

	public void setDeleted_datetime(Date deleted_datetime) {
		this.deleted_datetime = deleted_datetime;
	}

	@Override
	public String toString() {
		return "DcuMasterInfo [dcu_serial_number=" + dcu_serial_number + ", dcu_mac_nic_id=" + dcu_mac_nic_id
				+ ", frequency=" + frequency + ", manufacturer=" + manufacturer + ", ipv6_address=" + ipv6_address
				+ ", sim_no=" + sim_no + ", network=" + network + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", remarks=" + remarks + ", owner=" + owner + ", created_datetime=" + created_datetime
				+ ", created_by=" + created_by + ", updated_by=" + updated_by + ", updated_datetime=" + updated_datetime
				+ ", deleted_by=" + deleted_by + ", deleted_datetime=" + deleted_datetime + "]";
	}
}
