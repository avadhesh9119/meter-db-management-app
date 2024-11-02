#1
ALTER TABLE mdm_push_log add created_by text;
ALTER TABLE mdm_push_log add created_on timestamp;
ALTER TABLE mdm_push_log add updated_by text;
ALTER TABLE mdm_push_log add updated_on timestamp;

#2
ALTER TABLE mdm_master add created_by text;
ALTER TABLE mdm_master add created_on timestamp;
ALTER TABLE mdm_master add updated_by text;
ALTER TABLE mdm_master add updated_on timestamp;

ALTER TABLE mdm_push_log add device_status text;

ALTER TABLE mdm_master add ftp_address text;
ALTER TABLE mdm_master add ftp_login text;
ALTER TABLE mdm_master add ftp_password text;
ALTER TABLE mdm_master add login_password text;