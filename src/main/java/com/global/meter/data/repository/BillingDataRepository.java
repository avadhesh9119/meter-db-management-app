package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.BillingDataSinglePhase;

public interface BillingDataRepository extends CassandraRepository<BillingDataSinglePhase, String> {

	public Optional<BillingDataSinglePhase> findById(String deviceNo);
	
}
