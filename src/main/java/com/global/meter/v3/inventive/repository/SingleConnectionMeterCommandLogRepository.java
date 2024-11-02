package com.global.meter.v3.inventive.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.v3.inventive.business.model.SingleConnectionMeterCommandLog;

public interface SingleConnectionMeterCommandLogRepository extends CassandraRepository<SingleConnectionMeterCommandLog, String> {

	public static final String GET_METER_LOGS = "SELECT * FROM single_connection_meter_command_logs"
			+ " where hes_date= :hes_date and device_serial_number = :device_serial_number ;";

	@Query(value = GET_METER_LOGS)
	public Optional<SingleConnectionMeterCommandLog> getMeterCommandLog(@Param("hes_date") Date hes_date, 
			@Param("device_serial_number") String device_serial_number);

	public static final String GET_NEW_METER_LOGS = "SELECT * FROM single_connection_meter_command_logs"
			+ " where hes_date= :hes_date and device_serial_number = :device_serial_number and status= :status allow filtering;";

	@Query(value = GET_NEW_METER_LOGS)
	public Optional<List<SingleConnectionMeterCommandLog>> getNewCommandLog(@Param("hes_date") Date hes_date, 
			@Param("device_serial_number") String device_serial_number, @Param("status") String status);

	
	public static final String GET_METER_LOGS_LIST = "SELECT * FROM single_connection_meter_command_logs"
			+ " where hes_date= :hes_date;";

	@Query(value = GET_METER_LOGS_LIST)
	public Optional<List<SingleConnectionMeterCommandLog>> getMeterCommandLogList(@Param("hes_date") Date hes_date);

	
	public static final String GET_METER_LOGS_STATUS = "SELECT device_serial_number, batch_id FROM single_connection_meter_command_logs"
            + " where hes_date >= :hes_date and status = :status allow filtering;";
    @Query(value = GET_METER_LOGS_STATUS)
    public Optional<List<SingleConnectionMeterCommandLog>> getDeviceBatchByStaus(@Param("hes_date") Date hes_date, @Param("status") String status);

    public static final String GET_METER_LOGS_BATCH_WISE = "SELECT * FROM single_connection_meter_command_logs where batch_id = :batch_id and device_serial_number = :device_serial_number allow filtering;";
    @Query(value = GET_METER_LOGS_BATCH_WISE)
    public Optional<SingleConnectionMeterCommandLog> getLogsByBatchId(@Param("batch_id") String batch_id, @Param("device_serial_number") String device_serial_number);
    
    public static final String GET_SINGLE_METER_COMMAND_LOGS_BY_BATCH_ID = "SELECT * FROM single_connection_meter_command_logs"
            + " where batch_id = :batch_id allow filtering;";

    @Query(value = GET_SINGLE_METER_COMMAND_LOGS_BY_BATCH_ID)
    public Optional<List<SingleConnectionMeterCommandLog>> getDataByBatchId(@Param("batch_id") String batch_id);
    
    public static final String GET_FROM_DATA_WITH_METER_NO = "SELECT * FROM single_connection_meter_command_logs"
            + " where hes_date= :hes_date and device_serial_number = :device_serial_number allow filtering;";

	@Query(value = GET_FROM_DATA_WITH_METER_NO)
	public Optional<List<SingleConnectionMeterCommandLog>> getFromDataWithMeterNo(@Param("hes_date") Date hes_date, 
			@Param("device_serial_number") String device_serial_number);
	
	public static final String GET_SINGLE_METER_LOGS_DATE_WISE = "SELECT * FROM single_connection_meter_command_logs"
            + " where hes_date>= :fromdate and hes_date<= :todate and device_serial_number = :device_serial_number and device_type = :device_type allow filtering;";

    @Query(value = GET_SINGLE_METER_LOGS_DATE_WISE)
    public Optional<List<SingleConnectionMeterCommandLog>> getDataByMeter(@Param("fromdate") Date fromdate, @Param("todate") Date todate,
            @Param("device_serial_number") String device_serial_number, @Param("device_type") String device_type);
    
    public static final String GET_SINGLE_METER_LOGS_FOR_HIER1 = "SELECT * FROM single_connection_meter_command_logs"
            + " where hes_date>= :fromdate and hes_date<= :todate and owner_name = :owner_name and device_type = :device_type allow filtering;";

    @Query(value = GET_SINGLE_METER_LOGS_FOR_HIER1)
    public Optional<List<SingleConnectionMeterCommandLog>> getDataByUtility(@Param("fromdate") Date fromdate, @Param("todate") Date todate,
    		@Param("owner_name") String owner_name, @Param("device_type") String device_type);
    
    public static final String GET_SINGLE_METER_LOGS_FOR_HIER2 = "SELECT * FROM single_connection_meter_command_logs"
            + " where hes_date>= :fromdate and hes_date<= :todate and subdivision_name = :subdivision_name and device_type = :device_type allow filtering;";

    @Query(value = GET_SINGLE_METER_LOGS_FOR_HIER2)
    public Optional<List<SingleConnectionMeterCommandLog>> getDataBySubdivision(@Param("fromdate") Date fromdate, @Param("todate") Date todate,
    		@Param("subdivision_name") String subdivision_name, @Param("device_type") String device_type);
    
    public static final String GET_SINGLE_METER_LOGS_FOR_HIER3 = "SELECT * FROM single_connection_meter_command_logs"
            + " where hes_date>= :fromdate and hes_date<= :todate and substation_name = :substation_name and device_type = :device_type allow filtering;";

    @Query(value = GET_SINGLE_METER_LOGS_FOR_HIER3)
    public Optional<List<SingleConnectionMeterCommandLog>> getDataBySubstation(@Param("fromdate") Date fromdate, @Param("todate") Date todate,
    		@Param("substation_name") String substation_name, @Param("device_type") String device_type);
    
    public static final String GET_SINGLE_METER_LOGS_FOR_HIER4 = "SELECT * FROM single_connection_meter_command_logs"
            + " where hes_date>= :fromdate and hes_date<= :todate and feeder_name = :feeder_name and device_type = :device_type allow filtering;";

    @Query(value = GET_SINGLE_METER_LOGS_FOR_HIER4)
    public Optional<List<SingleConnectionMeterCommandLog>> getDataByFeeder(@Param("fromdate") Date fromdate, @Param("todate") Date todate,
    		@Param("feeder_name") String feeder_name, @Param("device_type") String device_type);
    
    public static final String GET_SINGLE_METER_LOGS_FOR_HIER5 = "SELECT * FROM single_connection_meter_command_logs"
            + " where hes_date>= :fromdate and hes_date<= :todate and dt_name = :dt_name and device_type = :device_type allow filtering;";

    @Query(value = GET_SINGLE_METER_LOGS_FOR_HIER5)
    public Optional<List<SingleConnectionMeterCommandLog>> getDataByDt(@Param("fromdate") Date fromdate, @Param("todate") Date todate,
    		@Param("dt_name") String dt_name, @Param("device_type") String device_type);
    
	public static final String CHECK_NEW_COMMAND = "SELECT * FROM single_connection_meter_command_logs"
			+ " where hes_date= :hes_date and device_serial_number = :device_serial_number and backend_status= :backend_status allow filtering;";

	@Query(value = CHECK_NEW_COMMAND)
	public Optional<SingleConnectionMeterCommandLog> checkNewCommand(@Param("hes_date") Date hes_date, 
			@Param("device_serial_number") String device_serial_number, @Param("backend_status") String backend_status);

    public static final String GET_BATCH_WISE_METER_LOGS = "SELECT * FROM single_connection_meter_command_logs where batch_id = :batch_id and device_serial_number = :device_serial_number allow filtering;";
    @Query(value = GET_BATCH_WISE_METER_LOGS)
    public Optional<List<SingleConnectionMeterCommandLog>> getBatchIdLogs(@Param("batch_id") String batch_id, @Param("device_serial_number") String device_serial_number);
   
    public static final String GET_METER_LOGS_BY_BATCH_ID_DEV_TYPE = "SELECT * FROM single_connection_meter_command_logs"
    		+ " where batch_id = :batch_id and device_type = :device_type allow filtering;";
    
    @Query(value = GET_METER_LOGS_BY_BATCH_ID_DEV_TYPE)
    public Optional<List<SingleConnectionMeterCommandLog>> getLogByBatchIdDevType(@Param("batch_id") String batch_id, @Param("device_type") String device_type);
    
}
