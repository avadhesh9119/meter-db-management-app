package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.DeviceCommandLogsOnePerDay;

public interface DeviceCommandLogsOnePerDayRepository extends CassandraRepository<DeviceCommandLogsOnePerDay, String> {

	/*
	 * public static final String FIND_DEVICES =
	 * "SELECT device_serial_number FROM devices_info;";
	 * 
	 * @Query(value = FIND_DEVICES) public List<Object[]> findDevices();
	 */	
	public Optional<DeviceCommandLogsOnePerDay> findById(String id);
	
}
