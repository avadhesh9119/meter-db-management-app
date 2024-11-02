package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.NamePlateLogs;

public interface NamePlateLogsRepository extends CassandraRepository<NamePlateLogs, String> {

	public Optional<NamePlateLogs> findById(String deviceNo);
	
}
