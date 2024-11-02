ALTER TABLE devices_info add category_level text;

ALTER TABLE subdivisions_master add hes_zone_id text;
ALTER TABLE subdivisions_master add hes_circle_id text;
ALTER TABLE subdivisions_master add hes_division_id text;
ALTER TABLE subdivisions_master add zone_name text;
ALTER TABLE subdivisions_master add circle_name text;
ALTER TABLE subdivisions_master add division_name text;

ALTER TABLE devices_info add approved_by text;
ALTER TABLE devices_info add approved_datetime timestamp;
ALTER TABLE devices_info add decommissioned_datetime timestamp;
ALTER TABLE devices_info add is_installed text;

ALTER TABLE instantaneous_data_singlephase drop load_limit;
ALTER TABLE instantaneous_data_singlephase add load_limits double;

ALTER TABLE push_instant_singlephase add cum_export_kvah double;
ALTER TABLE push_instant_singlephase add max_demand_kva_datetime timestamp;
ALTER TABLE push_instant_singlephase add max_demand_kw_datetime timestamp;
ALTER TABLE push_instant_singlephase add curr_month_kwh_import double;
ALTER TABLE push_instant_singlephase add curr_month_kvah_import double;

ALTER TABLE utility_master add company_id text;
ALTER TABLE utility_master add created_by text;
ALTER TABLE utility_master add created_on timestamp;
ALTER TABLE utility_master add modified timestamp;
ALTER TABLE utility_master add is_active text;
ALTER TABLE utility_master add updated_by text;
ALTER TABLE utility_master add level1_id text;

ALTER TABLE zone_master add is_active text;
ALTER TABLE zone_master add level2_id text;

ALTER TABLE circle_master add is_active text;
ALTER TABLE circle_master add level3_id text;

ALTER TABLE division_master add is_active text;
ALTER TABLE division_master add level4_id text;

ALTER TABLE subdivisions_master add is_active text;
ALTER TABLE subdivisions_master add level5_id text;

ALTER TABLE single_connection_meter_command_logs add backend_status text;

ALTER TABLE push_instant_threephase add signed_pf double;
ALTER TABLE push_instant_threephase add tamper_count int;

ALTER TABLE utility_master add owner_code text;

ALTER TABLE zone_master add zone_code text;

ALTER TABLE circle_master add circle_code text;

ALTER TABLE division_master add division_code text;

ALTER TABLE subdivisions_master add subdivision_code text;

ALTER TABLE devices_info add billing_datetime text;

ALTER TABLE process_billing_profile_data add is_on_time_billing text;

ALTER TABLE process_billing_profile_data add is_received_billing text;

ALTER TABLE process_billing_profile_data add commissioning_status text;

ALTER TABLE devices_history add approved_by text;
ALTER TABLE devices_history add approval_description text;

ALTER TABLE devices_info add decommissioning_status text;
ALTER TABLE devices_info add decommissioning_req_datetime timestamp;
ALTER TABLE devices_info add decommissioning_req_by text;
ALTER TABLE devices_info add decommissioning_reason text;

ALTER TABLE meter_pwds add created_by text;
ALTER TABLE meter_pwds add created_on timestamp;
ALTER TABLE meter_pwds add updated_by text;
ALTER TABLE meter_pwds add updated_on timestamp;

ALTER TABLE devices_history add action_taken_by text;
ALTER TABLE devices_history add action_taken_on timestamp;
ALTER TABLE devices_history add action_taken text;

ALTER TABLE mdm_push_log add alarm_data_hes_datetime timestamp;
ALTER TABLE mdm_push_log add alarm_data_mdm_send_datetime timestamp;

ALTER TABLE manufacturer_masters add created_by text;
ALTER TABLE manufacturer_masters add created_on timestamp;
ALTER TABLE manufacturer_masters add updated_by text;
ALTER TABLE manufacturer_masters add updated_on timestamp;

ALTER TABLE devices_history add decommissioning_req_datetime timestamp;
ALTER TABLE devices_history add decommissioning_req_by text;

ALTER TABLE devices_info add zone_name text;
ALTER TABLE devices_info add circle_name text;
ALTER TABLE devices_info add division_name text;

ALTER TABLE division_factor_file_logs add created_by text;
ALTER TABLE division_factor_file_logs add created_on timestamp;

ALTER TABLE meter_pwds add mode_of_comm text;

ALTER TABLE meter_pwds_history add mode_of_comm text;

ALTER TABLE devices_info add mode_of_comm text;
ALTER TABLE devices_info add meter_nic_id text;

ALTER TABLE devices_info_logs add meter_nic_id text;
ALTER TABLE devices_info_logs add mode_of_comm text;
ALTER TABLE devices_info_logs add action_taken text;
ALTER TABLE devices_info_logs add action_taken_by text;
ALTER TABLE devices_info_logs add action_taken_on timestamp;

ALTER TABLE gas_meter_info add mode_of_comm text;
ALTER TABLE gas_meter_info add account_no bigint;

ALTER TABLE lorawan_gateway_master add updated_by text;
ALTER TABLE lorawan_gateway_master add updated_on timestamp;

ALTER TABLE lorawan_gateway_master add created_by text;
ALTER TABLE lorawan_gateway_master add created_on timestamp;

ALTER TABLE gas_meter_info add app_eui text;
ALTER TABLE gas_meter_info add app_key text;
ALTER TABLE gas_meter_info add gateway_ui text;
ALTER TABLE gas_meter_info add gateway_serial_number text;
ALTER TABLE gas_meter_info add gateway_mac text;
ALTER TABLE gas_meter_info add utility_name text;
ALTER TABLE gas_meter_info add city_name text;
ALTER TABLE gas_meter_info add asset_name text;
ALTER TABLE gas_meter_info add area_name text;
ALTER TABLE gas_meter_info add sub_area_name text;
ALTER TABLE gas_meter_info add society_name text;

ALTER TABLE devices_info add authkey text;
ALTER TABLE devices_info add cipheringkey text;
ALTER TABLE devices_info add firmwarepwd text;
ALTER TABLE devices_info add highpwd text;
ALTER TABLE devices_info add lowpwd text;

ALTER TABLE devices_info_logs add authkey text;
ALTER TABLE devices_info_logs add cipheringkey text;
ALTER TABLE devices_info_logs add firmwarepwd text;
ALTER TABLE devices_info_logs add highpwd text;
ALTER TABLE devices_info_logs add lowpwd text;

ALTER TABLE gas_meter_info add acct_number text;
ALTER TABLE gas_meter_info drop account_no;

ALTER TABLE division_factor_file_logs add div_factor_type text;

ALTER TABLE devices_info_logs add remarks text;

ALTER TABLE devices_info add profile_capture_period int;
