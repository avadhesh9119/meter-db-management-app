CREATE TABLE mdm_master_logs (
    mdm_id text,
    created_on timestamp,
    created_by text,
    ftp_address text,
    ftp_login text,
    ftp_password text,
    is_active boolean,
    login_id text,
    login_password text,
    mdm_auth_url text,
    mdm_name text,
    updated_by text,
    updated_on timestamp,
    PRIMARY KEY (mdm_id, created_on));
    
CREATE TABLE manufacturer_specific_obis_code (
    manufacturer text,
    device_type text,
    profile_type text,
    version text,
    default_obis_code set<text>,
    extra_obis_code set<text>,
    implementation_datetime timestamp,
    mdas_datetime timestamp,
    obis_code set<text>,
    occurence_datetime timestamp,
    status text,
    PRIMARY KEY (manufacturer, device_type, profile_type, version));
    
CREATE TABLE firmware_deleted_logs (
    file_name text,
    deleted_on timestamp,
    created timestamp,
    deleted_by text,
    device_type text,
    manufacturer text,
    modified timestamp,
    owner text,
    remarks text,
    source text,
    status text,
    updated_by text,
    user_id text,
    version text,
    PRIMARY KEY (file_name, deleted_on));

CREATE TABLE dcu_power_data (
    dcu_serial_number text,
    mdas_datetime timestamp,
    battery_charging_status text,
    battery_mv_value text,
    battery_raw_value text,
    dcu_mac_nic_id text,
    hierarchy text,
    message_type text,
    power_good_status text,
    request_id text,
    PRIMARY KEY (dcu_serial_number, mdas_datetime));