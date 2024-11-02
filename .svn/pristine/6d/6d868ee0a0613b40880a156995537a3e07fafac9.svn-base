package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.InstantaneousCt;

public interface InstantaneousCtRepository extends CassandraRepository<InstantaneousCt, String> {

	public Optional<InstantaneousCt> findById(String deviceNo);
	
}
