ALTER TABLE prepay_data add relay_status text;

ALTER TABLE push_instant_singlephase add max_demand_kw_export double;
ALTER TABLE push_instant_singlephase add max_demand_kva_export double;
ALTER TABLE push_instant_singlephase add avg_pf_billing_period double;

ALTER TABLE push_instant_singlephase add cumm_power_on_duration int;
ALTER TABLE push_instant_singlephase add program_count int;
ALTER TABLE push_instant_singlephase add no_of_power_failure int;
ALTER TABLE push_instant_singlephase add billing_date timestamp;
ALTER TABLE push_instant_singlephase add temprature double;
ALTER TABLE push_instant_singlephase add no_of_load_switch int;
ALTER TABLE push_instant_singlephase add cumm_over_voltage_tamper_count int;
ALTER TABLE push_instant_singlephase add cumm_low_voltage_tamper_count int;
ALTER TABLE push_instant_singlephase add cumm_reverse_current_tamper_count int;
ALTER TABLE push_instant_singlephase add cumm_over_current_tamper_count int;
ALTER TABLE push_instant_singlephase add cumm_earth_tamper_count int;
ALTER TABLE push_instant_singlephase add cumm_magnet_tamper_count int;
ALTER TABLE push_instant_singlephase add cumm_neutral_disturbance_count int;
ALTER TABLE push_instant_singlephase add cumm_sw_tamper_count int;
ALTER TABLE push_instant_singlephase add cumm_over_load_tamper_count int;
ALTER TABLE push_instant_singlephase add cumm_comms_removal_tamper_count int;
ALTER TABLE push_instant_singlephase add cumm_case_open_tamper_count int;
ALTER TABLE push_instant_singlephase add cumm_temprature_rise_count int;
ALTER TABLE push_instant_singlephase add cumm_power_failure_duration int;
ALTER TABLE push_instant_singlephase add relay_operation_disconnect_count int;
ALTER TABLE push_instant_singlephase add relay_operation_connect_count int;
ALTER TABLE push_instant_singlephase add avg_signal_strength int;
-- 13/5/2024
ALTER TABLE device_batch_logs add bulk_type text;
ALTER TABLE prepay_data add average_signal_strength int;
ALTER TABLE prepay_data add meter_health_indicator int;
ALTER TABLE prepay_data add apn text;
ALTER TABLE prepay_data add ipv4_address text;
ALTER TABLE prepay_data add ipv6_address text;
ALTER TABLE devices_info_logs add replace_by_device_serial_number text;
ALTER TABLE instantaneous_data_threephase add load_limits double;
ALTER TABLE instantaneous_data_threephase drop load_limit;
ALTER TABLE single_connection_meter_command_logs add cmd_res map<text,text>;

-- 23/5/2024
ALTER TABLE dcu_info add dcu_mac_nic_id text;

-- 30/5/2024
ALTER TABLE prepay_data add current_relay_status text;

-- 05/06/2024
ALTER TABLE billing_data_ct add power_on_duration_minutes bigint;
ALTER TABLE billing_data_threephase add power_on_duration_minutes bigint;
ALTER TABLE last_billing_data_ct add power_on_duration_minutes bigint;
ALTER TABLE last_billing_data_threephase add power_on_duration_minutes bigint;

ALTER TABLE division_factor_file_logs add div_factor_type text;
