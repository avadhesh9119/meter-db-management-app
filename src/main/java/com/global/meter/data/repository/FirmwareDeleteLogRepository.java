package com.global.meter.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.FirmwareDeleteLogs;

public interface FirmwareDeleteLogRepository extends CassandraRepository<FirmwareDeleteLogs, String> {

}
