package com.global.meter.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.DcuMasterLogs;

public interface DcuMasterLogsRepository extends CassandraRepository<DcuMasterLogs, String>{

}
