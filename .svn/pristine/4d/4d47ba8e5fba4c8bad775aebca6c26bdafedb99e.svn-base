package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.DeviceCommandBackupLog;

public interface DeviceCommandBackupLogRepository extends CassandraRepository<DeviceCommandBackupLog, String> {

	/*
	 * public static final String FIND_DEVICES =
	 * "SELECT device_serial_number FROM devices_info;";
	 * 
	 * @Query(value = FIND_DEVICES) public List<Object[]> findDevices();
	 */	
	public Optional<DeviceCommandBackupLog> findById(String id);
	
}
