package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.LastBillingDataCT;

public interface LastBillingDataCTRepository extends CassandraRepository<LastBillingDataCT, String> {

	public Optional<LastBillingDataCT> findById(String deviceNo);
	
}
