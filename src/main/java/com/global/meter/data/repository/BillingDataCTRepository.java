package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.BillingDataCT;

public interface BillingDataCTRepository extends CassandraRepository<BillingDataCT, String> {

	public Optional<BillingDataCT> findById(String deviceNo);
	
}
