package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.RecentInstantaneousSinglePhase;

public interface RecentInstantaneousSinglePhaseRepository extends CassandraRepository<RecentInstantaneousSinglePhase, String> {

	public Optional<RecentInstantaneousSinglePhase> findById(String deviceNo);
	
}
