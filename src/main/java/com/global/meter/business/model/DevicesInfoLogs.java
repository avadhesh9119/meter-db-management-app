package com.global.meter.business.model;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "devices_info_logs")
public class DevicesInfoLogs {

	@PrimaryKey
	private String device_serial_number;
	private String address;
	private String commissioning_status;
	private String consumer_name;
	private String cell_id;
	private Date creation_date;
	private String crn;
	private String crn_new;
	private String sim_no;
	private String dcu_serial_number;
	private String description;
	private String device_type;
	private String device_id;
	private String dt_name;
	private String email_id;
	private String feeder_name;
	private String groups;
	private Date installation_datetime;
	private String ip_address_main;
	private String ip_address_push;
	private String ip_config;
	private String ip_port_main;
	private String ip_port_push;
	private String latitude;
	private String longitude;
	private String meter_type;
	private String mios_type;
	private String network;
	private Date modification_date;
	private String owner_name;
	private String phone_number;
	private Double sanction_load;
	private String status;
	private String subdevision_name;
	private String substation_name;
	private Date lastdeltalp_range_readdatetime;
	private Date lastdeltalpreaddatetime;
	private Date lastdailylp_range_readdatetime;
	private Date lastdailylpreaddatetime;
	private Date lastbillingreaddatetime;
	private Date lastcommunicationdatetime;
	private Date lasteventsreaddatetime;
	private Date lastinstanteousreaddatetime;
	private Date lastconnectdatetime;
	private Date lastdisconnectdatetime;
	private Date last_gasp_datetime;
	private Date first_breath_datetime;
	private Date instant_push_datetime;
	private Date last_nameplate_datetime;
	private String relay_status;
	private Date meter_datetime;
	private Double cumulative_energy_kvah_export;
	private Double cumulative_energy_kvah_import;
	private Double cumulative_energy_kwh_export;
	private Double cumulative_energy_kwh_import;
	private Double maximum_demand_kva;
	private Date maximum_demand_kva_datetime;
	private Double maximum_demand_kw;
	private Date maximum_demand_kw_datetime;
	private Double load_limits;
	private Integer cumulative_tamper_count;
	private Integer bill_count;

	private String created_by;
	private Date created_on_datetime;
	private Date last_updated_on_datetime;
	private String last_updated_by;

	private Integer metering_mode;
	private Integer payment_mode;
	private Integer last_token_recharge_amount;
	private Date last_token_recharge_time;
	private Integer total_amount_at_last_recharge;
	private Integer current_balance_amount;
	private Date current_balance_time;
	private String dev_mode;
	private String batch_id;
	private String version;
	private String source;
	private Date commissioning_datetime;
	private String category_level;
	private Date approved_datetime;
	private Date decommissioned_datetime;
	private String is_installed;
	private String approved_by;
	private String billing_datetime;
	private String decommissioning_status;
	private String decommissioning_reason;
	private String decommissioning_req_by;
	private Date decommissioning_req_datetime;
	private String zone_name;
	private String circle_name;
	private String division_name;
	private String tracking_id;
	private Date Action_taken_on;
	private String Action_taken_by;
	private String Action_taken;
	private String meter_nic_id;
	private String mode_of_comm;
	private String authkey;
	private String cipheringkey;
	private String firmwarepwd;
	private String highpwd;
	private String lowpwd;
	private String remarks;
	private String replace_by_device_serial_number;

	public DevicesInfoLogs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DevicesInfoLogs(String device_serial_number, String address, String commissioning_status,
			String consumer_name, String cell_id, Date creation_date, String crn, String crn_new, String sim_no,
			String dcu_serial_number, String description, String device_type, String device_id, String dt_name,
			String email_id, String feeder_name, String groups, Date installation_datetime, String ip_address_main,
			String ip_address_push, String ip_config, String ip_port_main, String ip_port_push, String latitude,
			String longitude, String meter_type, String mios_type, String network, Date modification_date,
			String owner_name, String phone_number, Double sanction_load, String status, String subdevision_name,
			String substation_name, Date lastdeltalp_range_readdatetime, Date lastdeltalpreaddatetime,
			Date lastdailylp_range_readdatetime, Date lastdailylpreaddatetime, Date lastbillingreaddatetime,
			Date lastcommunicationdatetime, Date lasteventsreaddatetime, Date lastinstanteousreaddatetime,
			Date lastconnectdatetime, Date lastdisconnectdatetime, Date last_gasp_datetime, Date first_breath_datetime,
			Date instant_push_datetime, Date last_nameplate_datetime, String relay_status, Date meter_datetime,
			Double cumulative_energy_kvah_export, Double cumulative_energy_kvah_import,
			Double cumulative_energy_kwh_export, Double cumulative_energy_kwh_import, Double maximum_demand_kva,
			Date maximum_demand_kva_datetime, Double maximum_demand_kw, Date maximum_demand_kw_datetime,
			Double load_limits, Integer cumulative_tamper_count, Integer bill_count, String created_by,
			Date created_on_datetime, Date last_updated_on_datetime, String last_updated_by, Integer metering_mode,
			Integer payment_mode, Integer last_token_recharge_amount, Date last_token_recharge_time,
			Integer total_amount_at_last_recharge, Integer current_balance_amount, Date current_balance_time,
			String dev_mode, String batch_id, String version, String source, Date commissioning_datetime,
			String category_level, Date approved_datetime, Date decommissioned_datetime, String is_installed,
			String approved_by, String billing_datetime, String decommissioning_status, String decommissioning_reason,
			String decommissioning_req_by, Date decommissioning_req_datetime, String zone_name, String circle_name,
			String division_name, String tracking_id, Date action_taken_on, String action_taken_by,
			String action_taken) {
		super();
		this.device_serial_number = device_serial_number;
		this.address = address;
		this.commissioning_status = commissioning_status;
		this.consumer_name = consumer_name;
		this.cell_id = cell_id;
		this.creation_date = creation_date;
		this.crn = crn;
		this.crn_new = crn_new;
		this.sim_no = sim_no;
		this.dcu_serial_number = dcu_serial_number;
		this.description = description;
		this.device_type = device_type;
		this.device_id = device_id;
		this.dt_name = dt_name;
		this.email_id = email_id;
		this.feeder_name = feeder_name;
		this.groups = groups;
		this.installation_datetime = installation_datetime;
		this.ip_address_main = ip_address_main;
		this.ip_address_push = ip_address_push;
		this.ip_config = ip_config;
		this.ip_port_main = ip_port_main;
		this.ip_port_push = ip_port_push;
		this.latitude = latitude;
		this.longitude = longitude;
		this.meter_type = meter_type;
		this.mios_type = mios_type;
		this.network = network;
		this.modification_date = modification_date;
		this.owner_name = owner_name;
		this.phone_number = phone_number;
		this.sanction_load = sanction_load;
		this.status = status;
		this.subdevision_name = subdevision_name;
		this.substation_name = substation_name;
		this.lastdeltalp_range_readdatetime = lastdeltalp_range_readdatetime;
		this.lastdeltalpreaddatetime = lastdeltalpreaddatetime;
		this.lastdailylp_range_readdatetime = lastdailylp_range_readdatetime;
		this.lastdailylpreaddatetime = lastdailylpreaddatetime;
		this.lastbillingreaddatetime = lastbillingreaddatetime;
		this.lastcommunicationdatetime = lastcommunicationdatetime;
		this.lasteventsreaddatetime = lasteventsreaddatetime;
		this.lastinstanteousreaddatetime = lastinstanteousreaddatetime;
		this.lastconnectdatetime = lastconnectdatetime;
		this.lastdisconnectdatetime = lastdisconnectdatetime;
		this.last_gasp_datetime = last_gasp_datetime;
		this.first_breath_datetime = first_breath_datetime;
		this.instant_push_datetime = instant_push_datetime;
		this.last_nameplate_datetime = last_nameplate_datetime;
		this.relay_status = relay_status;
		this.meter_datetime = meter_datetime;
		this.cumulative_energy_kvah_export = cumulative_energy_kvah_export;
		this.cumulative_energy_kvah_import = cumulative_energy_kvah_import;
		this.cumulative_energy_kwh_export = cumulative_energy_kwh_export;
		this.cumulative_energy_kwh_import = cumulative_energy_kwh_import;
		this.maximum_demand_kva = maximum_demand_kva;
		this.maximum_demand_kva_datetime = maximum_demand_kva_datetime;
		this.maximum_demand_kw = maximum_demand_kw;
		this.maximum_demand_kw_datetime = maximum_demand_kw_datetime;
		this.load_limits = load_limits;
		this.cumulative_tamper_count = cumulative_tamper_count;
		this.bill_count = bill_count;
		this.created_by = created_by;
		this.created_on_datetime = created_on_datetime;
		this.last_updated_on_datetime = last_updated_on_datetime;
		this.last_updated_by = last_updated_by;
		this.metering_mode = metering_mode;
		this.payment_mode = payment_mode;
		this.last_token_recharge_amount = last_token_recharge_amount;
		this.last_token_recharge_time = last_token_recharge_time;
		this.total_amount_at_last_recharge = total_amount_at_last_recharge;
		this.current_balance_amount = current_balance_amount;
		this.current_balance_time = current_balance_time;
		this.dev_mode = dev_mode;
		this.batch_id = batch_id;
		this.version = version;
		this.source = source;
		this.commissioning_datetime = commissioning_datetime;
		this.category_level = category_level;
		this.approved_datetime = approved_datetime;
		this.decommissioned_datetime = decommissioned_datetime;
		this.is_installed = is_installed;
		this.approved_by = approved_by;
		this.billing_datetime = billing_datetime;
		this.decommissioning_status = decommissioning_status;
		this.decommissioning_reason = decommissioning_reason;
		this.decommissioning_req_by = decommissioning_req_by;
		this.decommissioning_req_datetime = decommissioning_req_datetime;
		this.zone_name = zone_name;
		this.circle_name = circle_name;
		this.division_name = division_name;
		this.tracking_id = tracking_id;
		Action_taken_on = action_taken_on;
		Action_taken_by = action_taken_by;
		Action_taken = action_taken;
	}

	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCommissioning_status() {
		return commissioning_status;
	}

	public void setCommissioning_status(String commissioning_status) {
		this.commissioning_status = commissioning_status;
	}

	public String getConsumer_name() {
		return consumer_name;
	}

	public void setConsumer_name(String consumer_name) {
		this.consumer_name = consumer_name;
	}

	public String getCell_id() {
		return cell_id;
	}

	public void setCell_id(String cell_id) {
		this.cell_id = cell_id;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public String getCrn_new() {
		return crn_new;
	}

	public void setCrn_new(String crn_new) {
		this.crn_new = crn_new;
	}

	public String getSim_no() {
		return sim_no;
	}

	public void setSim_no(String sim_no) {
		this.sim_no = sim_no;
	}

	public String getDcu_serial_number() {
		return dcu_serial_number;
	}

	public void setDcu_serial_number(String dcu_serial_number) {
		this.dcu_serial_number = dcu_serial_number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getDt_name() {
		return dt_name;
	}

	public void setDt_name(String dt_name) {
		this.dt_name = dt_name;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getFeeder_name() {
		return feeder_name;
	}

	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public Date getInstallation_datetime() {
		return installation_datetime;
	}

	public void setInstallation_datetime(Date installation_datetime) {
		this.installation_datetime = installation_datetime;
	}

	public String getIp_address_main() {
		return ip_address_main;
	}

	public void setIp_address_main(String ip_address_main) {
		this.ip_address_main = ip_address_main;
	}

	public String getIp_address_push() {
		return ip_address_push;
	}

	public void setIp_address_push(String ip_address_push) {
		this.ip_address_push = ip_address_push;
	}

	public String getIp_config() {
		return ip_config;
	}

	public void setIp_config(String ip_config) {
		this.ip_config = ip_config;
	}

	public String getIp_port_main() {
		return ip_port_main;
	}

	public void setIp_port_main(String ip_port_main) {
		this.ip_port_main = ip_port_main;
	}

	public String getIp_port_push() {
		return ip_port_push;
	}

	public void setIp_port_push(String ip_port_push) {
		this.ip_port_push = ip_port_push;
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

	public String getMeter_type() {
		return meter_type;
	}

	public void setMeter_type(String meter_type) {
		this.meter_type = meter_type;
	}

	public String getMios_type() {
		return mios_type;
	}

	public void setMios_type(String mios_type) {
		this.mios_type = mios_type;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public Date getModification_date() {
		return modification_date;
	}

	public void setModification_date(Date modification_date) {
		this.modification_date = modification_date;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public Double getSanction_load() {
		return sanction_load;
	}

	public void setSanction_load(Double sanction_load) {
		this.sanction_load = sanction_load;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getLastdeltalp_range_readdatetime() {
		return lastdeltalp_range_readdatetime;
	}

	public void setLastdeltalp_range_readdatetime(Date lastdeltalp_range_readdatetime) {
		this.lastdeltalp_range_readdatetime = lastdeltalp_range_readdatetime;
	}

	public Date getLastdeltalpreaddatetime() {
		return lastdeltalpreaddatetime;
	}

	public void setLastdeltalpreaddatetime(Date lastdeltalpreaddatetime) {
		this.lastdeltalpreaddatetime = lastdeltalpreaddatetime;
	}

	public Date getLastdailylp_range_readdatetime() {
		return lastdailylp_range_readdatetime;
	}

	public void setLastdailylp_range_readdatetime(Date lastdailylp_range_readdatetime) {
		this.lastdailylp_range_readdatetime = lastdailylp_range_readdatetime;
	}

	public Date getLastdailylpreaddatetime() {
		return lastdailylpreaddatetime;
	}

	public void setLastdailylpreaddatetime(Date lastdailylpreaddatetime) {
		this.lastdailylpreaddatetime = lastdailylpreaddatetime;
	}

	public Date getLastbillingreaddatetime() {
		return lastbillingreaddatetime;
	}

	public void setLastbillingreaddatetime(Date lastbillingreaddatetime) {
		this.lastbillingreaddatetime = lastbillingreaddatetime;
	}

	public Date getLastcommunicationdatetime() {
		return lastcommunicationdatetime;
	}

	public void setLastcommunicationdatetime(Date lastcommunicationdatetime) {
		this.lastcommunicationdatetime = lastcommunicationdatetime;
	}

	public Date getLasteventsreaddatetime() {
		return lasteventsreaddatetime;
	}

	public void setLasteventsreaddatetime(Date lasteventsreaddatetime) {
		this.lasteventsreaddatetime = lasteventsreaddatetime;
	}

	public Date getLastinstanteousreaddatetime() {
		return lastinstanteousreaddatetime;
	}

	public void setLastinstanteousreaddatetime(Date lastinstanteousreaddatetime) {
		this.lastinstanteousreaddatetime = lastinstanteousreaddatetime;
	}

	public Date getLastconnectdatetime() {
		return lastconnectdatetime;
	}

	public void setLastconnectdatetime(Date lastconnectdatetime) {
		this.lastconnectdatetime = lastconnectdatetime;
	}

	public Date getLastdisconnectdatetime() {
		return lastdisconnectdatetime;
	}

	public void setLastdisconnectdatetime(Date lastdisconnectdatetime) {
		this.lastdisconnectdatetime = lastdisconnectdatetime;
	}

	public Date getLast_gasp_datetime() {
		return last_gasp_datetime;
	}

	public void setLast_gasp_datetime(Date last_gasp_datetime) {
		this.last_gasp_datetime = last_gasp_datetime;
	}

	public Date getFirst_breath_datetime() {
		return first_breath_datetime;
	}

	public void setFirst_breath_datetime(Date first_breath_datetime) {
		this.first_breath_datetime = first_breath_datetime;
	}

	public Date getInstant_push_datetime() {
		return instant_push_datetime;
	}

	public void setInstant_push_datetime(Date instant_push_datetime) {
		this.instant_push_datetime = instant_push_datetime;
	}

	public Date getLast_nameplate_datetime() {
		return last_nameplate_datetime;
	}

	public void setLast_nameplate_datetime(Date last_nameplate_datetime) {
		this.last_nameplate_datetime = last_nameplate_datetime;
	}

	public String getRelay_status() {
		return relay_status;
	}

	public void setRelay_status(String relay_status) {
		this.relay_status = relay_status;
	}

	public Date getMeter_datetime() {
		return meter_datetime;
	}

	public void setMeter_datetime(Date meter_datetime) {
		this.meter_datetime = meter_datetime;
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

	public Double getLoad_limits() {
		return load_limits;
	}

	public void setLoad_limits(Double load_limits) {
		this.load_limits = load_limits;
	}

	public Integer getCumulative_tamper_count() {
		return cumulative_tamper_count;
	}

	public void setCumulative_tamper_count(Integer cumulative_tamper_count) {
		this.cumulative_tamper_count = cumulative_tamper_count;
	}

	public Integer getBill_count() {
		return bill_count;
	}

	public void setBill_count(Integer bill_count) {
		this.bill_count = bill_count;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_on_datetime() {
		return created_on_datetime;
	}

	public void setCreated_on_datetime(Date created_on_datetime) {
		this.created_on_datetime = created_on_datetime;
	}

	public Date getLast_updated_on_datetime() {
		return last_updated_on_datetime;
	}

	public void setLast_updated_on_datetime(Date last_updated_on_datetime) {
		this.last_updated_on_datetime = last_updated_on_datetime;
	}

	public String getLast_updated_by() {
		return last_updated_by;
	}

	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}

	public Integer getMetering_mode() {
		return metering_mode;
	}

	public void setMetering_mode(Integer metering_mode) {
		this.metering_mode = metering_mode;
	}

	public Integer getPayment_mode() {
		return payment_mode;
	}

	public void setPayment_mode(Integer payment_mode) {
		this.payment_mode = payment_mode;
	}

	public Integer getLast_token_recharge_amount() {
		return last_token_recharge_amount;
	}

	public void setLast_token_recharge_amount(Integer last_token_recharge_amount) {
		this.last_token_recharge_amount = last_token_recharge_amount;
	}

	public Date getLast_token_recharge_time() {
		return last_token_recharge_time;
	}

	public void setLast_token_recharge_time(Date last_token_recharge_time) {
		this.last_token_recharge_time = last_token_recharge_time;
	}

	public Integer getTotal_amount_at_last_recharge() {
		return total_amount_at_last_recharge;
	}

	public void setTotal_amount_at_last_recharge(Integer total_amount_at_last_recharge) {
		this.total_amount_at_last_recharge = total_amount_at_last_recharge;
	}

	public Integer getCurrent_balance_amount() {
		return current_balance_amount;
	}

	public void setCurrent_balance_amount(Integer current_balance_amount) {
		this.current_balance_amount = current_balance_amount;
	}

	public Date getCurrent_balance_time() {
		return current_balance_time;
	}

	public void setCurrent_balance_time(Date current_balance_time) {
		this.current_balance_time = current_balance_time;
	}

	public String getDev_mode() {
		return dev_mode;
	}

	public void setDev_mode(String dev_mode) {
		this.dev_mode = dev_mode;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getCommissioning_datetime() {
		return commissioning_datetime;
	}

	public void setCommissioning_datetime(Date commissioning_datetime) {
		this.commissioning_datetime = commissioning_datetime;
	}

	public String getCategory_level() {
		return category_level;
	}

	public void setCategory_level(String category_level) {
		this.category_level = category_level;
	}

	public Date getApproved_datetime() {
		return approved_datetime;
	}

	public void setApproved_datetime(Date approved_datetime) {
		this.approved_datetime = approved_datetime;
	}

	public Date getDecommissioned_datetime() {
		return decommissioned_datetime;
	}

	public void setDecommissioned_datetime(Date decommissioned_datetime) {
		this.decommissioned_datetime = decommissioned_datetime;
	}

	public String getIs_installed() {
		return is_installed;
	}

	public void setIs_installed(String is_installed) {
		this.is_installed = is_installed;
	}

	public String getApproved_by() {
		return approved_by;
	}

	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}

	public String getBilling_datetime() {
		return billing_datetime;
	}

	public void setBilling_datetime(String billing_datetime) {
		this.billing_datetime = billing_datetime;
	}

	public String getDecommissioning_status() {
		return decommissioning_status;
	}

	public void setDecommissioning_status(String decommissioning_status) {
		this.decommissioning_status = decommissioning_status;
	}

	public String getDecommissioning_reason() {
		return decommissioning_reason;
	}

	public void setDecommissioning_reason(String decommissioning_reason) {
		this.decommissioning_reason = decommissioning_reason;
	}

	public String getDecommissioning_req_by() {
		return decommissioning_req_by;
	}

	public void setDecommissioning_req_by(String decommissioning_req_by) {
		this.decommissioning_req_by = decommissioning_req_by;
	}

	public Date getDecommissioning_req_datetime() {
		return decommissioning_req_datetime;
	}

	public void setDecommissioning_req_datetime(Date decommissioning_req_datetime) {
		this.decommissioning_req_datetime = decommissioning_req_datetime;
	}

	public String getZone_name() {
		return zone_name;
	}

	public void setZone_name(String zone_name) {
		this.zone_name = zone_name;
	}

	public String getCircle_name() {
		return circle_name;
	}

	public void setCircle_name(String circle_name) {
		this.circle_name = circle_name;
	}

	public String getDivision_name() {
		return division_name;
	}

	public void setDivision_name(String division_name) {
		this.division_name = division_name;
	}

	public String getTracking_id() {
		return tracking_id;
	}

	public void setTracking_id(String tracking_id) {
		this.tracking_id = tracking_id;
	}

	public Date getAction_taken_on() {
		return Action_taken_on;
	}

	public void setAction_taken_on(Date action_taken_on) {
		Action_taken_on = action_taken_on;
	}

	public String getAction_taken_by() {
		return Action_taken_by;
	}

	public void setAction_taken_by(String action_taken_by) {
		Action_taken_by = action_taken_by;
	}

	public String getAction_taken() {
		return Action_taken;
	}

	public void setAction_taken(String action_taken) {
		Action_taken = action_taken;
	}

	public String getMeter_nic_id() {
		return meter_nic_id;
	}

	public void setMeter_nic_id(String meter_nic_id) {
		this.meter_nic_id = meter_nic_id;
	}

	public String getMode_of_comm() {
		return mode_of_comm;
	}

	public void setMode_of_comm(String mode_of_comm) {
		this.mode_of_comm = mode_of_comm;
	}

	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public String getCipheringkey() {
		return cipheringkey;
	}

	public void setCipheringkey(String cipheringkey) {
		this.cipheringkey = cipheringkey;
	}

	public String getFirmwarepwd() {
		return firmwarepwd;
	}

	public void setFirmwarepwd(String firmwarepwd) {
		this.firmwarepwd = firmwarepwd;
	}

	public String getHighpwd() {
		return highpwd;
	}

	public void setHighpwd(String highpwd) {
		this.highpwd = highpwd;
	}

	public String getLowpwd() {
		return lowpwd;
	}

	public void setLowpwd(String lowpwd) {
		this.lowpwd = lowpwd;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReplace_by_device_serial_number() {
		return replace_by_device_serial_number;
	}

	public void setReplace_by_device_serial_number(String replace_by_device_serial_number) {
		this.replace_by_device_serial_number = replace_by_device_serial_number;
	}

	@Override
	public String toString() {
		return "DevicesInfoLogs [device_serial_number=" + device_serial_number + ", address=" + address
				+ ", commissioning_status=" + commissioning_status + ", consumer_name=" + consumer_name + ", cell_id="
				+ cell_id + ", creation_date=" + creation_date + ", crn=" + crn + ", crn_new=" + crn_new + ", sim_no="
				+ sim_no + ", dcu_serial_number=" + dcu_serial_number + ", description=" + description
				+ ", device_type=" + device_type + ", device_id=" + device_id + ", dt_name=" + dt_name + ", email_id="
				+ email_id + ", feeder_name=" + feeder_name + ", groups=" + groups + ", installation_datetime="
				+ installation_datetime + ", ip_address_main=" + ip_address_main + ", ip_address_push="
				+ ip_address_push + ", ip_config=" + ip_config + ", ip_port_main=" + ip_port_main + ", ip_port_push="
				+ ip_port_push + ", latitude=" + latitude + ", longitude=" + longitude + ", meter_type=" + meter_type
				+ ", mios_type=" + mios_type + ", network=" + network + ", modification_date=" + modification_date
				+ ", owner_name=" + owner_name + ", phone_number=" + phone_number + ", sanction_load=" + sanction_load
				+ ", status=" + status + ", subdevision_name=" + subdevision_name + ", substation_name="
				+ substation_name + ", lastdeltalp_range_readdatetime=" + lastdeltalp_range_readdatetime
				+ ", lastdeltalpreaddatetime=" + lastdeltalpreaddatetime + ", lastdailylp_range_readdatetime="
				+ lastdailylp_range_readdatetime + ", lastdailylpreaddatetime=" + lastdailylpreaddatetime
				+ ", lastbillingreaddatetime=" + lastbillingreaddatetime + ", lastcommunicationdatetime="
				+ lastcommunicationdatetime + ", lasteventsreaddatetime=" + lasteventsreaddatetime
				+ ", lastinstanteousreaddatetime=" + lastinstanteousreaddatetime + ", lastconnectdatetime="
				+ lastconnectdatetime + ", lastdisconnectdatetime=" + lastdisconnectdatetime + ", last_gasp_datetime="
				+ last_gasp_datetime + ", first_breath_datetime=" + first_breath_datetime + ", instant_push_datetime="
				+ instant_push_datetime + ", last_nameplate_datetime=" + last_nameplate_datetime + ", relay_status="
				+ relay_status + ", meter_datetime=" + meter_datetime + ", cumulative_energy_kvah_export="
				+ cumulative_energy_kvah_export + ", cumulative_energy_kvah_import=" + cumulative_energy_kvah_import
				+ ", cumulative_energy_kwh_export=" + cumulative_energy_kwh_export + ", cumulative_energy_kwh_import="
				+ cumulative_energy_kwh_import + ", maximum_demand_kva=" + maximum_demand_kva
				+ ", maximum_demand_kva_datetime=" + maximum_demand_kva_datetime + ", maximum_demand_kw="
				+ maximum_demand_kw + ", maximum_demand_kw_datetime=" + maximum_demand_kw_datetime + ", load_limits="
				+ load_limits + ", cumulative_tamper_count=" + cumulative_tamper_count + ", bill_count=" + bill_count
				+ ", created_by=" + created_by + ", created_on_datetime=" + created_on_datetime
				+ ", last_updated_on_datetime=" + last_updated_on_datetime + ", last_updated_by=" + last_updated_by
				+ ", metering_mode=" + metering_mode + ", payment_mode=" + payment_mode
				+ ", last_token_recharge_amount=" + last_token_recharge_amount + ", last_token_recharge_time="
				+ last_token_recharge_time + ", total_amount_at_last_recharge=" + total_amount_at_last_recharge
				+ ", current_balance_amount=" + current_balance_amount + ", current_balance_time="
				+ current_balance_time + ", dev_mode=" + dev_mode + ", batch_id=" + batch_id + ", version=" + version
				+ ", source=" + source + ", commissioning_datetime=" + commissioning_datetime + ", category_level="
				+ category_level + ", approved_datetime=" + approved_datetime + ", decommissioned_datetime="
				+ decommissioned_datetime + ", is_installed=" + is_installed + ", approved_by=" + approved_by
				+ ", billing_datetime=" + billing_datetime + ", decommissioning_status=" + decommissioning_status
				+ ", decommissioning_reason=" + decommissioning_reason + ", decommissioning_req_by="
				+ decommissioning_req_by + ", decommissioning_req_datetime=" + decommissioning_req_datetime
				+ ", zone_name=" + zone_name + ", circle_name=" + circle_name + ", division_name=" + division_name
				+ ", tracking_id=" + tracking_id + ", Action_taken_on=" + Action_taken_on + ", Action_taken_by="
				+ Action_taken_by + ", Action_taken=" + Action_taken + ", meter_nic_id=" + meter_nic_id
				+ ", mode_of_comm=" + mode_of_comm + ", authkey=" + authkey + ", cipheringkey=" + cipheringkey
				+ ", firmwarepwd=" + firmwarepwd + ", highpwd=" + highpwd + ", lowpwd=" + lowpwd + ", remarks="
				+ remarks + "]";
	}

}
