package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.InstantaneousSinglePhase;

public interface InstantaneousSinglePhaseRepository extends CassandraRepository<InstantaneousSinglePhase, String> {

	public Optional<InstantaneousSinglePhase> findById(String deviceNo);
	
}
