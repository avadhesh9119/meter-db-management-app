package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.BillingDataThreePhase;

public interface BillingDataThreePhaseRepository extends CassandraRepository<BillingDataThreePhase, String> {

	public Optional<BillingDataThreePhase> findById(String deviceNo);
	
}
