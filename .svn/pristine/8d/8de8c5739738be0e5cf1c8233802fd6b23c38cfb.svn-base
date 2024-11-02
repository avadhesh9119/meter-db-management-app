#1 Added field in devices_info
alter table devices_info add version text;

#2 Added fields in push_instant_singlephase
alter table push_instant_singlephase add cum_export_kwh double;
alter table push_instant_singlephase add max_demand_kva double;
alter table push_instant_singlephase add tamper_count int;
alter table push_instant_singlephase add bill_count int;
alter table push_instant_singlephase add relay_status double;
alter table push_instant_singlephase add load_limit double;
alter table push_instant_singlephase add power_duration double;

#3 Added fields in devices_commands
alter table devices_commands add user_id text;
alter table devices_commands add source text;
alter table devices_commands add batch_id text;

#4 Added fields in devices_commands_logs
alter table devices_commands_logs add user_id text;
alter table devices_commands_logs add source text;
alter table devices_commands_logs add batch_id text;

#5 Added fields in devices_config_logs
alter table devices_config_logs add user_id text;
alter table devices_config_logs add source text;
alter table devices_config_logs add batch_id text;

#6 Added fields in devices_commands_prepay_logs
alter table devices_commands_prepay_logs add batch_id text;

#7 Added fields in prepay_data
alter table prepay_data add batch_id text;
alter table prepay_data add meter_type text;

#8 Added fields in instantaneous_data_threephase
alter table instantaneous_data_threephase add signed_power_factor_b_phase double;

#9 Added fields in event_data_threephase
ALTER TABLE event_data_threephase add cumulative_energy_kvah_export double;
ALTER TABLE event_data_threephase add cumulative_energy_kvah_import double;
ALTER TABLE event_data_threephase add neutral_current double;

#10 Added fields in name_plate
alter table name_plate add ct_ratio text;
alter table name_plate add pt_ratio text;

#11 Added fields in name_plate_logs
alter table name_plate_logs add ct_ratio text;
alter table name_plate_logs add pt_ratio text;

#12 Added field in devices_history
alter table devices_history add deleted_on timestamp;

#13 Added field in instantaneous_data_ct
alter table instantaneous_data_ct add maximum_demand_w_export double;
alter table instantaneous_data_ct add maximum_demand_w_export_datetime timestamp;
alter table instantaneous_data_ct add maximum_demand_va_export double;
alter table instantaneous_data_ct add maximum_demand_va_export_datetime timestamp;
alter table instantaneous_data_ct add angle_phase_volt_ab double;
alter table instantaneous_data_ct add angle_phase_volt_bc double;
alter table instantaneous_data_ct add angle_phase_volt_ac double;

#14 Added field in devices_commands_prepay_logs
ALTER TABLE devices_commands_prepay_logs add source text;
ALTER TABLE devices_commands_prepay_logs add user_id text;

#15 Added field in prepay_data
ALTER TABLE prepay_data add source text;
ALTER TABLE prepay_data add user_id text;

#16 Added fields in prepay_data
alter table prepay_data add demand_integration_period int;
alter table prepay_data add profile_capture_period int;
alter table prepay_data add load_limit double;
alter table prepay_data add instant_push_ip text;
alter table prepay_data add alert_push_ip text;
alter table prepay_data add push_setup_duration text;

#17 Added fields
alter table push_events_singlephase add  device_type text;

#18 Added field in subdevisions
ALTER TABLE subdevisions add source text;
ALTER TABLE subdevisions add user_id text;
ALTER TABLE subdevisions add updated_by text;

#19 Added field in substations
ALTER TABLE substations add source text;
ALTER TABLE substations add user_id text;
ALTER TABLE substations add updated_by text;

#20 Added field in feeders
ALTER TABLE feeders add source text;
ALTER TABLE feeders add user_id text;
ALTER TABLE feeders add updated_by text;

#21 Added field in dt_trans
ALTER TABLE dt_trans add source text;
ALTER TABLE dt_trans add user_id text;
ALTER TABLE dt_trans add updated_by text;

#22 Added field in devices_commands
alter TABLE devices_commands add datewise_range map<text,text>;

alter table devices_commands_logs add daily_range_date map<text,text>;
alter table devices_commands_logs add delta_range_date map<text,text>;

#23
alter table prepay_data add enable_disable_control_modes text;

#24
ALTER TABLE daily_load_profile_ct add no_load text;
ALTER TABLE daily_load_profile_ct add no_supply text;

#25
ALTER TABLE load_profile_data_threephase add kva_max double;
ALTER TABLE load_profile_data_threephase add max_current double;
ALTER TABLE load_profile_data_threephase add max_voltage double;

#26
ALTER TABLE load_profile_data_ct add ch_0_lo_current_neutral_current_avg_5 double;

#27
ALTER TABLE instantaneous_data_threephase add cumm_power_on_duration_in_mins double;

#28
ALTER TABLE instantaneous_data_ct add cumm_power_on_duration_in_mins double;

#29
ALTER TABLE firmware_config add source text;
ALTER TABLE firmware_config add user_id text;
ALTER TABLE firmware_config add updated_by text;
ALTER TABLE firmware_config add created timestamp;
ALTER TABLE firmware_config add modified timestamp;
ALTER TABLE firmware_config add version text;

#30
ALTER TABLE firmware_data add source text;
ALTER TABLE firmware_data add user_id text;
ALTER TABLE firmware_data add updated_by text;
ALTER TABLE firmware_data add created timestamp;
ALTER TABLE firmware_data add modified timestamp;

#31
ALTER TABLE devices_history add deleted_by text;

#32
ALTER TABLE devices_info add source text;

#33
ALTER TABLE devices_history add source text;

#34
ALTER TYPE gascommandslog add trackingid text;

#35
ALTER TABLE prepay_data add device_type text;

#36
ALTER TABLE devices_commands_prepay_logs add device_type text;

#37
ALTER TABLE devices_commands_prepay_logs add meter_type text;

#38
ALTER TABLE devices_config_logs add device_type text;

#39
ALTER TABLE devices_config_logs add meter_type text;

#40
ALTER TABLE firmware_data add device_type text;

#41
ALTER TABLE devices_commands_batch_logs add source text;

#42
ALTER TABLE devices_commands_batch_logs add user_id text;

#43
ALTER TABLE devices_config_batch_logs add source text;

#44
ALTER TABLE devices_config_batch_logs add user_id text;

#45
ALTER TABLE devices_config_read_batch_logs add source text;

#46
ALTER TABLE devices_config_read_batch_logs add user_id text;

#47
ALTER TABLE daily_load_profile_singlephase add maximum_demand_kw double;

#48
ALTER TABLE daily_load_profile_singlephase add maximum_demand_kw_datetime timestamp;

#49
ALTER TABLE daily_load_profile_singlephase add maximum_demand_kva double;

#50
ALTER TABLE daily_load_profile_singlephase add maximum_demand_kva_datetime timestamp;

#51
ALTER TABLE load_profile_data_singlephase add avg_signal_strength double;

#52
ALTER TABLE load_profile_data_singlephase add status_byte double;

#53
ALTER TABLE substations_master add address double;

#54
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier1 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier2 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier3 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier4 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier5 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier6 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier7 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier8 double;

ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier1_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier2_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier3_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier4_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier5_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier6_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier7_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kw_tier8_date timestamp;

ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier1 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier2 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier3 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier4 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier5 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier6 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier7 double;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier8 double;

ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier1_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier2_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier3_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier4_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier5_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier6_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier7_date timestamp;
ALTER TABLE billing_data_singlephase add maximum_demand_kva_tier8_date timestamp;

#55
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier1 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier2 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier3 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier4 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier5 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier6 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier7 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier8 double;

ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier1_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier2_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier3_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier4_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier5_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier6_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier7_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kw_tier8_date timestamp;

ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier1 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier2 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier3 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier4 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier5 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier6 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier7 double;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier8 double;

ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier1_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier2_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier3_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier4_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier5_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier6_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier7_date timestamp;
ALTER TABLE last_billing_data_singlephase add maximum_demand_kva_tier8_date timestamp;

#56
ALTER TABLE devices_info add commissioning_datetime timestamp;

ALTER TABLE devices_info add last_gasp_datetime timestamp;
ALTER TABLE devices_info add first_breath_datetime timestamp;
ALTER TABLE devices_info add instant_push_datetime timestamp;
ALTER TABLE devices_info add last_nameplate_datetime timestamp;

#57
ALTER TABLE dcu_master_logs add dcu_serial_number text;
ALTER TABLE dcu_master_logs add creation_date timestamp;
ALTER TABLE dcu_master_logs add created_by text;
ALTER TABLE dcu_master_logs add created_datetime;
ALTER TABLE dcu_master_logs add dcu_mac_nic_id text;
ALTER TABLE dcu_master_logs add deleted_by text;
ALTER TABLE dcu_master_logs add deleted_datetime timestamp;
ALTER TABLE dcu_master_logs add frequency double;
ALTER TABLE dcu_master_logs add ipv6_address text;
ALTER TABLE dcu_master_logs add latitude text;
ALTER TABLE dcu_master_logs add longitude text;
ALTER TABLE dcu_master_logs add manufacturer text;
ALTER TABLE dcu_master_logs add network text;
ALTER TABLE dcu_master_logs add remarks text;
ALTER TABLE dcu_master_logs add sim_no text;
ALTER TABLE dcu_master_logs add updated_by text;
ALTER TABLE dcu_master_logs add updated_datetime timestamp;


