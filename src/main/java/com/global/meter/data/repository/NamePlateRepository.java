package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.NamePlate;

public interface NamePlateRepository extends CassandraRepository<NamePlate, String> {

	public Optional<NamePlate> findById(String deviceNo);
	
}
