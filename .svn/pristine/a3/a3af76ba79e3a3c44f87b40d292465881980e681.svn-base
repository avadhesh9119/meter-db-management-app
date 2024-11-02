package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.ProcessBillingData;

public interface ProcessBillingDataRepository extends CassandraRepository<ProcessBillingData, String> {

	public Optional<ProcessBillingData> findById(String deviceNo);
	
}
