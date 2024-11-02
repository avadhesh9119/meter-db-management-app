package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.MeterTypeInfo;

public interface MeterTypeRepository extends CassandraRepository<MeterTypeInfo, String> {
	
	public static final String FIND_DEVICES = "SELECT meter_type FROM meter_type_info;";
	public Optional<MeterTypeInfo> findById(String meterType);
  
	public List<MeterTypeInfo> findAll() ;
}
