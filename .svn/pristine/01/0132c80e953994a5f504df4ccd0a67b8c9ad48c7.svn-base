#1
CREATE TABLE mdm_master (
    mdm_id text,
    is_active boolean,
    login_id text,
    mdm_auth_url text,
    mdm_name text,
    "password" text,
    PRIMARY KEY (mdm_id)
)
#2
CREATE TABLE mdm_profile_master (
    mdm_id text,
    profile_name text,
    communication_mode text,
    data_mode text,
    is_active boolean,
    profile_url text,
    PRIMARY KEY (mdm_id, profile_name)
)
#3
CREATE TABLE mdm_push_log (
    device_serial_number text,
    block_load_profile_hes_datetime timestamp,
    block_load_profile_mdm_send_datetime timestamp,
    command_status_hes_datetime timestamp,
    command_status_mdm_send_datetime timestamp,
    daily_billing_hes_datetime timestamp,
    daily_billing_mdm_send_datetime timestamp,
    daily_load_profile_hes_datetime timestamp,
    daily_load_profile_mdm_send_datetime timestamp,
    device_type text,
    event_data_hes_datetime timestamp,
    event_data_mdm_send_datetime timestamp,
    instantaneous_hes_datetime timestamp,
    instantaneous_mdm_send_datetime timestamp,
    manufacturer text,
    mdm_id text,
    monthly_billing_hes_datetime timestamp,
    monthly_billing_mdm_send_datetime timestamp,
    name_plate_hes_datetime timestamp,
    name_plate_mdm_send_datetime timestamp,
    PRIMARY KEY (device_serial_number)
)