package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.MeterPwds;

public interface MeterPasswordRepository extends CassandraRepository<MeterPwds, String> {
	
	public static final String FIND_DEVICES = "SELECT meter_type FROM meter_pwds;";
	public Optional<MeterPwds> findById(String meterType);
  
	public List<MeterPwds> findAll() ;
}
