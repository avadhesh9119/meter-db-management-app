package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.MeterCommCount;

public interface MeterCommCountRepository extends CassandraRepository<MeterCommCount, String> {
	
	String FEEDER_COUNT_QUERY = "Select count(feeder_name) from feeders";
	String DEVICES_COUNT_QUERY = "Select count(device_serial_number) from devices_info";
	String DCU_COUNT_QUERY = "Select count(dcu_serial_number) from dcu_info";
	String DT_COUNT_QUERY = "Select count(dt_name) from dt_trans";
	String SUB_DIVISION_COUNT_QUERY = "Select count(subdevision_name) from subdevisions";
	String SUB_STATION_COUNT_QUERY = "Select count(substation_name) from substations";
	
	@Query(FEEDER_COUNT_QUERY)
	public int getFeederCount();
	
	@Query(DEVICES_COUNT_QUERY)
	public int getDevicesCount();
	
	@Query(DCU_COUNT_QUERY)
	public int getDcuCount();
	
	@Query(DT_COUNT_QUERY)
	public int getDtCount();
	
	@Query(SUB_DIVISION_COUNT_QUERY)
	public int getSubdivisionCount();
	
	@Query(SUB_STATION_COUNT_QUERY)
	public int getSubstationCount();
	
	@Query("Select * From meter_comm_count where owner_name = :owner_name allow filtering")
	public Optional<MeterCommCount> findByOwnerName(@Param("owner_name") String owner_name);
}
