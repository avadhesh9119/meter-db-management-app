CREATE TABLE zone_master (
    i_am_id int,
    hes_zone_id text,
    access set<text>,
    created timestamp,
    latitude text,
    longitude text,
    modified timestamp,
    owner_id text,
    owner_name text,
    source text,
    updated_by text,
    user_id text,
    zone_name text,
    PRIMARY KEY (i_am_id, hes_zone_id)
);

CREATE TABLE circle_master (
    i_am_id int,
    hes_circle_id text,
    access set<text>,
    address text,
    circle_name text,
    created timestamp,
    hes_owner_id text,
    hes_zone_id text,
    latitude text,
    longitude text,
    modified timestamp,
    owner_name text,
    source text,
    updated_by text,
    user_id text,
    zone_name text,
    PRIMARY KEY (i_am_id, hes_circle_id)
);

CREATE TABLE division_master (
    i_am_id int,
    hes_division_id text,
    access set<text>,
    circle_name text,
    created timestamp,
    division_name text,
    hes_circle_id text,
    hes_zone_id text,
    latitude text,
    longitude text,
    modified timestamp,
    owner_id text,
    owner_name text,
    source text,
    updated_by text,
    user_id text,
    zone_name text,
    PRIMARY KEY (i_am_id, hes_division_id)
);

CREATE TABLE process_billing_profile_data (
    device_serial_number text,
    circle text,
    device_type text,
    division text,
    dt_name text,
    feeder_name text,
    is_daily text,
    is_monthly text,
    is_weekly text,
    mdas_datetime timestamp,
    owner_name text,
    subdevision_name text,
    substation_name text,
    zone text,
    PRIMARY KEY (device_serial_number)
);

CREATE TABLE user_mst (
    auto_id int,
    attribute1 text,
    attribute2 text,
    attribute3 text,
    attribute4 text,
    attribute5 text,
    change_password_flag boolean,
    created_by int,
    created_on timestamp,
    department_code text,
    department_name text,
    designation_code text,
    designation_name text,
    email_id text,
    end_date timestamp,
    father_name text,
    identity_no text,
    identity_type_name text,
    ip_address text,
    is_active boolean,
    last_updated_by int,
    last_updated_on timestamp,
    login_id text,
    mobile_no text,
    photo text,
    reporting_user_id int,
    start_date timestamp,
    user_code text,
    user_id int,
    user_name text,
    user_remark_id int,
    user_type_name text,
    PRIMARY KEY (auto_id)
);

CREATE TABLE process_on_demand_command (
    command_date timestamp,
    command_name text,
    success_percentage double,
    PRIMARY KEY (command_date, command_name)
);

CREATE TABLE pull_events_category (
    owner_name text,
    created timestamp,
    created_by text,
    critical_event_list set<text>,
    modified timestamp,
    non_critical_event_list set<text>,
    source text,
    updated_by text,
    PRIMARY KEY (owner_name)
);

CREATE TABLE meter_commissioning_logs (
    datetime timestamp,
    commissioning_count text,
    devices_count text,
    installation_count text,
    mdas_datetime timestamp,
    non_commissioning_count text,
    non_installation_count text,
    PRIMARY KEY (datetime)
);

CREATE TABLE communication_summary_logs (
    mdas_datetime timestamp,
    hier_name text,
    hier_value text,
    communcating text,
    communcating_avg text,
    non_communcating text,
    non_communcating_avg text,
    total_devices text,
    PRIMARY KEY (mdas_datetime, hier_name, hier_value)
 );
 
 CREATE TABLE process_sla_data (
    device_serial_number text,
    mdas_datetime timestamp,
    device_type text,
    dt_name text,
    feeder_name text,
    is_dailylp_available text,
    lastdailylpreaddatetime timestamp,
    lastdeltalp_count text,
    lastdeltalp_failure_avg text,
    lastdeltalp_success_avg text,
    lastdeltalpreaddatetime timestamp,
    lastfirstbreath_readdatetime timestamp,
    lastgasp_readdatetime timestamp,
    lastinstanteousreaddatetime timestamp,
    lastinstantpushdata_readdatetime timestamp,
    lastnameplatereaddatetime timestamp,
    meter_type text,
    owner_name text,
    subdivision_name text,
    substation_name text,
    PRIMARY KEY (device_serial_number, mdas_datetime)
 );
 
 CREATE TABLE raw_data (
    device_serial_number text,
    tracking_id text,
    data text,
    mdas_datetime timestamp,
    profile_type text,
    PRIMARY KEY (device_serial_number, tracking_id, profile_type)
);

CREATE TABLE meter_pwds (
    manufacturer text,
    tracking_id text,
    authkey text,
    authmode text,
    cipheringkey text,
    cipheringmode text,
    device_type text,
    firmwarepwd text,
    highpwd text,
    lowpwd text,
    part int,
    pushports text,
    systemtitle text,
    PRIMARY KEY (manufacturer, tracking_id)
);

CREATE TABLE user_hierarchy_mapping (
    user_id int,
    mapping_id int,
    company_id int,
    created_by int,
    created_on timestamp,
    ip_address text,
    is_active boolean,
    is_default boolean,
    last_updated_by int,
    last_updated_on timestamp,
    level1_id int,
    level2_id int,
    level3_id int,
    level4_id int,
    level5_id int,
    level6_id int,
    PRIMARY KEY (user_id, mapping_id)
)

CREATE TABLE user_hierarchy_mapping_log (
    user_id int,
    mapping_id int,
    company_id int,
    created_by int,
    created_on timestamp,
    ip_address text,
    is_active boolean,
    is_default boolean,
    last_updated_by int,
    last_updated_on timestamp,
    level1_id int,
    level2_id int,
    level3_id int,
    level4_id int,
    level5_id int,
    level6_id int,
    PRIMARY KEY (user_id, mapping_id)
);

CREATE TABLE meter_pwds_history (
    manufacturer text,
    tracking_id text,
    authkey text,
    authmode text,
    cipheringkey text,
    cipheringmode text,
    created_by text,
    created_on timestamp,
    device_type text,
    firmwarepwd text,
    highpwd text,
    lowpwd text,
    part int,
    pushports text,
    systemtitle text,
    updated_by text,
    updated_on timestamp,
    PRIMARY KEY (manufacturer,updated_on));
    
CREATE TABLE manufacturer_masters (
    code text,
    name text,
    is_active boolean,
    PRIMARY KEY (code)
)

CREATE TABLE mdm_master (
    mdm_id text,
    ftp_address text,
    ftp_login text,
    ftp_password text,
    is_active boolean,
    login_id text,
    mdm_auth_url text,
    mdm_name text,
    login_password text,
    PRIMARY KEY (mdm_id)
)

CREATE TABLE properties_file_logs (
    tracking_id text,
    description text,
    mdas_datetime timestamp,
    new_property_value text,
    old_property_value text,
    source text,
    updated_by text,
    updated_on timestamp,
    user_comment text,
    PRIMARY KEY (tracking_id)
)

CREATE TABLE division_factor_file_logs (
    tracking_id text,
    description text,
    mdas_datetime timestamp,
    new_division_factor_value text,
    old_division_factor_value text,
    source text,
    updated_by text,
    updated_on timestamp,
    user_comment text,
    PRIMARY KEY (tracking_id)
)

CREATE TABLE gas_hierarchy_utility (
    utility_code text,
    created_by text,
    created_on timestamp,
    hes_utility_id text,
    iam_utility_id text,
    is_active text,
    source text,
    updated_by text,
    updated_on timestamp,
    utility_name text,
    PRIMARY KEY (utility_code)
)
CREATE TABLE gas_hierarchy_city (
    city_code text,
    city_name text,
    created_by text,
    created_on timestamp,
    hes_city_id text,
    iam_city_id text,
    is_active text,
    source text,
    updated_by text,
    updated_on timestamp,
    utility_code text,
    PRIMARY KEY (city_code)
)
CREATE TABLE gas_hierarchy_asset (
    asset_code text,
    asset_name text,
    city_code text,
    created_by text,
    created_on timestamp,
    hes_asset_id text,
    iam_asset_id text,
    is_active text,
    source text,
    updated_by text,
    updated_on timestamp,
    PRIMARY KEY (asset_code)
)
CREATE TABLE gas_hierarchy_area (
    area_code text,
    area_name text,
    asset_code text,
    created_by text,
    created_on timestamp,
    hes_area_id text,
    iam_area_id text,
    is_active text,
    source text,
    updated_by text,
    updated_on timestamp,
    PRIMARY KEY (area_code)
)
CREATE TABLE gas_hierarchy_sub_area (
    sub_area_code text,
    area_code text,
    created_by text,
    created_on timestamp,
    hes_sub_area_id text,
    iam_sub_area_id text,
    is_active text,
    source text,
    sub_area_name text,
    updated_by text,
    updated_on timestamp,
    PRIMARY KEY (sub_area_code)
)
CREATE TABLE gas_hierarchy_society (
    society_code text,
    created_by text,
    created_on timestamp,
    hes_society_id text,
    iam_society_id text,
    is_active text,
    society_name text,
    source text,
    sub_area_code text,
    updated_by text,
    updated_on timestamp,
    PRIMARY KEY (society_code)
)
CREATE TABLE devices_info_logs (
    tracking_id text,
    device_serial_number text,
    access set<text>,
    address text,
    approved_by text,
    approved_datetime timestamp,
    batch_id text,
    bill_count int,
    billing_date timestamp,
    billing_datetime text,
    category_level text,
    cell_id text,
    circle_name text,
    commissioning_datetime timestamp,
    commissioning_status text,
    consumer_name text,
    created_by text,
    created_on_datetime timestamp,
    creation_date timestamp,
    crn text,
    crn_new text,
    cumulative_energy_kvah_export double,
    cumulative_energy_kvah_import double,
    cumulative_energy_kwh_export double,
    cumulative_energy_kwh_import double,
    cumulative_tamper_count int,
    current_balance_amount int,
    current_balance_time timestamp,
    dcu_serial_number text,
    decommissioned_approved_by text,
    decommissioned_datetime timestamp,
    decommissioning_reason text,
    decommissioning_req_by text,
    decommissioning_req_datetime timestamp,
    decommissioning_status text,
    description text,
    dev_mode text,
    device_id text,
    device_type text,
    division_name text,
    dt_name text,
    email_id text,
    feeder_name text,
    first_breath_datetime timestamp,
    groups text,
    installation_datetime timestamp,
    instant_push_datetime timestamp,
    ip_address_main text,
    ip_address_push text,
    ip_config text,
    ip_port_main text,
    ip_port_push text,
    is_installed text,
    last_gasp_datetime timestamp,
    last_nameplate_datetime timestamp,
    last_token_recharge_amount int,
    last_token_recharge_time timestamp,
    last_updated_by text,
    last_updated_on_datetime timestamp,
    lastbillingreaddatetime timestamp,
    lastcommunicationdatetime timestamp,
    lastconnectdatetime timestamp,
    lastdailylp_range_readdatetime timestamp,
    lastdailylpreaddatetime timestamp,
    lastdeltalp_range_readdatetime timestamp,
    lastdeltalpreaddatetime timestamp,
    lastdisconnectdatetime timestamp,
    lasteventsreaddatetime timestamp,
    lastinstanteousreaddatetime timestamp,
    latitude text,
    load_limits double,
    longitude text,
    maximum_demand_kva double,
    maximum_demand_kva_datetime timestamp,
    maximum_demand_kw double,
    maximum_demand_kw_datetime timestamp,
    meter_datetime timestamp,
    meter_type text,
    metering_mode int,
    mios_type text,
    modification_date timestamp,
    network text,
    owner_name text,
    payment_mode int,
    phone_number text,
    relay_status text,
    sanction_load double,
    sim_no text,
    source text,
    status text,
    subdevision_name text,
    substation_name text,
    total_amount_at_last_recharge int,
    version text,
    zone_name text,
    PRIMARY KEY (tracking_id, device_serial_number)
)
CREATE TABLE lorawan_gateway_master (
    gateway_serial_number text,
    dc_input text,
    gateway_mac text,
    gateway_ui text,
    manufacturer text,
    model text,
    PRIMARY KEY (gateway_serial_number)
)
CREATE TABLE lorawan_gateway_master_history (
    gateway_serial_number text,
    dc_input text,
    gateway_mac text,
    gateway_ui text,
    manufacturer text,
    model text,
    modified_on timestamp,
    updated_by text,
    PRIMARY KEY (gateway_serial_number)
)
CREATE TABLE gas_manufacturer_masters (
    code text,
    created_by text,
    created_on timestamp,
    is_active boolean,
    name text,
    updated_by text,
    updated_on timestamp,
    PRIMARY KEY (code)
)
CREATE TABLE lorawan_gas_meter_command_logs (
    hesdate timestamp,
    gasmeterno text,
    batchid text,
    commandslist frozen <list<frozen <lorawangascommandslog>>>,
    hesdatetime timestamp,
    ownername text,
    trackingid text,
    PRIMARY KEY (hesdate, gasmeterno)
)
CREATE TABLE gas_device_batch_logs (
    batch_id text,
    completion_datetime timestamp,
    failure_records int,
    file_name text,
    mdas_datetime timestamp,
    status text,
    success_records int,
    tot_records int,
    user_name text,
    PRIMARY KEY (batch_id)
)

drop table firmware_config;

CREATE TABLE firmware_config (
    device_serial_number text,
    tracking_id text,
    command_completion_datetime timestamp,
    command_datetime timestamp,
    command_val text,
    created timestamp,
    dcu_serial_number text,
    device_type text,
    dt_name text,
    feeder_name text,
    mdas_datetime timestamp,
    modified timestamp,
    owner_name text,
    reason text,
    source text,
    status text,
    subdevision_name text,
    substation_name text,
    tot_attempts int,
    updated_by text,
    user_id text,
    version text,
    PRIMARY KEY (device_serial_number, tracking_id)
)