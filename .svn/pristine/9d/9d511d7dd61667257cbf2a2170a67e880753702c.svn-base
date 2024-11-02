package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.LastBillingDataThreePhase;

public interface LastBillingDataThreePhaseRepository extends CassandraRepository<LastBillingDataThreePhase, String> {

	public Optional<LastBillingDataThreePhase> findById(String deviceNo);
	
}
