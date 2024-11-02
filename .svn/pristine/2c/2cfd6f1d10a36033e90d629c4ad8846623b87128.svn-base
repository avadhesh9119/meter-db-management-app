package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.PriorityEvent;

public interface PriorityEventRepository extends CassandraRepository<PriorityEvent, String> {

	public Optional<PriorityEvent> findById(String deviceNo);
	
}
