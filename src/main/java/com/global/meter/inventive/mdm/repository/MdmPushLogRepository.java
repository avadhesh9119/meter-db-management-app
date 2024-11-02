package com.global.meter.inventive.mdm.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.MdmPushLogs;

public interface MdmPushLogRepository extends CassandraRepository<MdmPushLogs, String> {

}