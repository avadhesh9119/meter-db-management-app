package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.PullEventsCategory;

public interface PullEventsCategoryRepository extends CassandraRepository<PullEventsCategory, String> {

	public Optional<PullEventsCategory> findById(String deviceNo);

}
