package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.DeviceCommandLog;

public interface DeviceCommandLogRepository extends CassandraRepository<DeviceCommandLog, String> {

	/*
	 * public static final String FIND_DEVICES =
	 * "SELECT device_serial_number FROM devices_info;";
	 * 
	 * @Query(value = FIND_DEVICES) public List<Object[]> findDevices();
	 */	
	public Optional<DeviceCommandLog> findById(String id);
	
}
