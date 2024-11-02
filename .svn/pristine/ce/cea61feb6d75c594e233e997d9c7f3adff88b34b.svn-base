package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.LastBillingDataSinglePhase;

public interface LastBillingDataRepository extends CassandraRepository<LastBillingDataSinglePhase, String> {

	public Optional<LastBillingDataSinglePhase> findById(String deviceNo);
	
}
