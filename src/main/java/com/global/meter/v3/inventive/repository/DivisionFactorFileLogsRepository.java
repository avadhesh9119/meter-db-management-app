package com.global.meter.v3.inventive.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.v3.inventive.business.model.DivisionFactorFileLog;

public interface DivisionFactorFileLogsRepository extends CassandraRepository<DivisionFactorFileLog, String> {

}
