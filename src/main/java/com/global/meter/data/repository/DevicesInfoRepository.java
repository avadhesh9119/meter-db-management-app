package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.DevicesInfo;

public interface DevicesInfoRepository extends CassandraRepository<DevicesInfo, String> {

	public static final String FIND_DEVICES = "SELECT device_serial_number FROM devices_info;";

	@Query(value = FIND_DEVICES)
	public List<Object[]> findDevices();
	
	public Optional<DevicesInfo> findById(String id);
	
	public static final String FIND_BY_IP = "SELECT * FROM devices_info"
			+ " where ip_address_main= :ip_address_main allow filtering;";

	@Query(value = FIND_BY_IP)
	public Optional<List<DevicesInfo>> findDeviceByIp(@Param("ip_address_main") String ip_address_main);
	
	
	public static final String FIND_BY_PUSH_IP_PORT = "SELECT * FROM devices_info"
			+ " where ip_address_push= :ip_address_push and ip_port_push = :ip_port_push and device_type = :device_type allow filtering;";

	@Query(value = FIND_BY_PUSH_IP_PORT)
	public Optional<List<DevicesInfo>> findDeviceByPushIpAndPort(@Param("ip_address_push") String ip_address_push,
			@Param("ip_port_push") String ip_port_push, @Param("device_type") String device_type);

	public static final String FIND_BY_NIC = "SELECT * FROM devices_info"
			+ " where meter_nic_id = :meter_nic_id allow filtering;";

	@Query(value = FIND_BY_NIC)
	public Optional<List<DevicesInfo>> findDeviceByNic(@Param("meter_nic_id") String meter_nic_id);

	public static final String FIND_BY_SIM_NO = "SELECT * FROM devices_info"
			+ " where sim_no= :sim_no allow filtering;";

	@Query(value = FIND_BY_SIM_NO)
	public Optional<List<DevicesInfo>> findDeviceBySimNo(@Param("sim_no") String sim_no);
	
}
