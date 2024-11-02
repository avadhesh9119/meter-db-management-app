package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.FirmwareData;

public interface FirmwareDataRepository extends CassandraRepository<FirmwareData, String> {

	static final String FIND_FirmwareData = "SELECT file_name, owner, version, source, user_id, updated_by, file_upload_datetime, image_identifier, "
			+ "manufacturer, status, device_type FROM firmware_data where owner= :owner allow filtering";    
	
	static final String FIND_Firmware_File = "SELECT * FROM firmware_data where owner= :owner and file_name = :file_name"; 
    
	static final String FIND_Firmware_File_NAME = "SELECT * FROM firmware_data where file_name = :file_name"; 

	@Query(value = FIND_FirmwareData)
	public List<FirmwareData> findByOwnerName(@Param("owner") String owner);
	
	@Query(value = FIND_Firmware_File)
	public Optional<FirmwareData> findByOwnerNameAndFileName(@Param("file_name") String fileName, @Param("owner") String owner);

	@Query(value = FIND_Firmware_File_NAME)
	public Optional<FirmwareData> findByFileName(@Param("file_name") String fileName);
}
