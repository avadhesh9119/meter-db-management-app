package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.InstantaneousThreePhase;

public interface InstantaneousThreePhaseRepository extends CassandraRepository<InstantaneousThreePhase, String> {

	public Optional<InstantaneousThreePhase> findById(String deviceNo);
	
}
