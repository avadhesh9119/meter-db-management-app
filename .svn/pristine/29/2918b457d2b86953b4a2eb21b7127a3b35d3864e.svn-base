# ChangeLogs
All notable changes to Data Management Service will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
Changes that are already committed but could not be merged due to some blocker will go in this section.

## Version 4.0.0 - Target release/4.0.0

### Electrical Meter:-

#### Added 

- All RF features related to data read, write activities are added in this feature(`HPL Support`). And new Columns for push are added in alter.sql file.

- (05/09/2024)
# Firmware Deleted Log creation
> add new three parameters deleted logs use firmware deleted data to save data base
>create new add api firmware-deleted-logs/get

- (13-05-2024)
- Import of metering data(xml file) that were read locally from meter.
- Two extra parameter APN & IPv4Address added in configuration Read page.
- Details(Billing Dates, Profile Capture Period, Payment mode) to save in devices info when in changes done in 	configuration.
- Replacement of faulty meter with new meter that are triggered from MDM.
- Report for replacement of faulty meter with new meter.
- System must do time sync with the device when it connects for the first time. 

- (17-05-2024)
-DCU Master creation Add crud operations
-Add,Update,Get,Get-List,Delete,deleted-logs

-(22-05-2024)
-Dcu health push report
-Add new two api (add,push-get)

- (21-005-2024)
-Show date range in command log for dailyLP and deltaLP. 

- (30-05-2024)
-added invalid relay status report.

- (30-05-2024)
-added recent configuration report.

- (05-06-2024)
-added new column in billing and monthly billing for power_on_duration_mins.


- (17-07-2024)
- worked network-report-drillDown Add new one parameter

- (07-10-2024)
-added connection pooling for cassandra.
-added connectin pooling for presto.
-added cache implementation using hazelcast. 
-added common exception handling.
#### Updated

- (20-03-2024) - TODO


#### BugFixed

- (20-03-2024) - TODO

- (20-005-2024)
-Resolved issues for discarding config read command with same write command. 

- (23-05-2024)
-added null check in common utils for set authentication.

- (04-06-2024)
-resolved issues in scheduler config log.

- (27-06-2024)
-resolved issues to update status and retry for fulldata command log.

- (07-10-2024)
-resolved UAT issues.

#### Removed

- (20-03-2024) - TODO


### Gas Meter:- 

#### Removed

- (27-03-2024)
Remove All Gas Service Packages & Internal Classes Listed below:

-com.global.meter.gas.inventive.utils
- GasTables
- NBGasMeterDataCaster

-com.global.meter.gas.inventive.serviceImpl
- GasDeviceBatchLogsServiceImpl
- GasManufacturerMasterServiceImpl
- GasMeterCommandLogsServiceImpl
- GasMeterDataVisualizationServiceImpl
- GasMeterInfoServiceImpl
- LorawanGatewayMasterServiceImpl

-com.global.meter.gas.inventive.service
- GasDeviceBatchLogsService
- GasManufacturerMasterService
- GasMeterCommandLogsService
- GasMeterDataVisualizationService
- GasMeterInfoService
- LorawanGatewayMasterService
	
-com.global.meter.gas.inventive.models
- GasCommandLogResponse
- GasCommandsLogReq
- GasDeviceBatchLogsDataRequest
- GasDeviceBatchLogsDataResponse
- GasHierarchyRequest
- GasHierarchyResponse
- GasMeterCommandLogResponse
- GasMeterInfoRequest
- GasMeterInfoResponse
- LorawanGatewayMasterReq
- LorawanGatewayMasterResponse
- MeterState
- NBGasMeterDataReader
- NBGasMeterDataResponse
- NBGasMeterState

-com.global.meter.gas.inventive.model
- GasMeterCommandLogsReq
- GasMeterDataVisualizationReq
	
-com.global.meter.gas.inventive.data.repository
- GasDeviceBatchLogsRepository
- GasHierarchyAreaRepository
- GasHierarchyAssetRepository
- GasHierarchyCityRepository
- GasHierarchySocietyRepository
- GasHierarchySubAreaRepository
- GasHierarchySubAreaRepository
- GasHierarchyUtilityRepository
- GasManufacturerMasterRepository
- GasMeterInfoHistoryRepository
- GasMeterInfoRepository
- LorawanGasMeterCommandLogsRepository
- LorawanGatewayMasterHistoryRepository
- LorawanGatewayMasterRepository
- NonDLMSGasMeterCommandLogsRepository
	
-com.global.meter.gas.inventive.controller
- GasDeviceBatchLogsController
- GasManufacturerMasterController
- GasMeterCommandLogsController
- GasMeterDataVisualizationController
- GasMeterInfoController
- LorawanGatewayMasterController

-com.global.meter.gas.common.enums
- DownlinkCommands
	
-com.global.meter.gas.business.model
- GasCommandsLog
- GasDeviceBatchLogs
- GasHierarchyArea
- GasHierarchyAsset
- GasHierarchyCity
- GasHierarchySociety
- GasHierarchySubArea
- GasHierarchyUtility
- GasManufacturerMaster
- GasMeterInfo
- GasMeterInfoHistory
- LorawanGasCommandsLog
- LorawanGasMeterCommandLogs
- LorawanGatewayMaster
- LorawanGatewayMasterHistory
- NBGasMeterData
- NonDLMSGasMeterCommandLogs	

-Remove class from package

-com.global.meter.inventive.scheduler
- GasMeterProcessedCommandLogsScheduler

 
-Same cron job added in gas service for move command log for next day.
- schedule.process.nonDLMSGasMeterCommandLogs.cron=00 00 00 * * *  

-removed six parameter from firmware_config table.