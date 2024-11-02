package com.global.meter.inventive.mdm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DevicesConfigBatchLogs;
import com.global.meter.business.model.DevicesConfigReadBatchLogs;
import com.global.meter.data.repository.DevicesConfigBatchLogsRepository;
import com.global.meter.data.repository.DevicesConfigReadBatchLogsRepository;
import com.global.meter.inventive.models.DevicesCommandsBatchLogsRequest;
import com.global.meter.inventive.models.DevicesConfigBatchLogsResponse;
import com.global.meter.inventive.models.DevicesConfigReadBatchLogsResponse;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class DevicesConfigBatchLogsMdmCaster {
	private static final Logger LOG = LoggerFactory.getLogger(DevicesConfigBatchLogsMdmCaster.class);

	@Autowired
	DateConverter dateConverter;

	@Autowired
	DevicesConfigBatchLogsRepository devicesConfigBatchLogsRepository;
	
	@Autowired
	DevicesConfigReadBatchLogsRepository devicesConfigReadBatchLogsRepository;

	public void saveDevicesConfigBatchLogs(String outputList) throws Exception {
		LOG.info("DevicesConfigBatchLogs Data Caster Called....");
		List<DevicesCommandsBatchLogsRequest> batchRequestList = new ArrayList<DevicesCommandsBatchLogsRequest>();
		batchRequestList = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<DevicesCommandsBatchLogsRequest>>() {
				});
		LOG.info("DevicesConfigBatchLogs Response Data Adding....");

		 String batch = "";
		 int count = 0;
		 int added = 0;
		 int success = 0;
		 int inprogress = 0;
		 int failure = 0;
		 TreeSet<String> owners = new TreeSet<String>();
		 TreeSet<String> subdevisions = new TreeSet<String>();
		 TreeSet<String> substations = new TreeSet<String>();
		 TreeSet<String> feeders = new TreeSet<String>();
		 TreeSet<String> dts = new TreeSet<String>();
		 TreeSet<String> meters = new TreeSet<String>();
		 String owner = "";
		 String subdevision = "";
		 String substation = "";
		 String feeder = "";
		 String dt = "";
		 String meter = "";
		boolean isFirstTime = true;
		ArrayList<DevicesConfigBatchLogs> batchLogsList = new ArrayList<DevicesConfigBatchLogs>();
		DevicesConfigBatchLogs batchLogs = new DevicesConfigBatchLogs();

		for (DevicesCommandsBatchLogsRequest ispData : batchRequestList) {
			
			count++;
			if (isFirstTime || ispData.getBatch_id().equals(batch)) {
				
				setBatchLogs(ispData, batchLogs, owners, subdevisions, substations, feeders, dts, meters);
				
				if ("ADDED".equalsIgnoreCase(ispData.getStatus())) {
					added++;
				}else if ("SUCCESS".equalsIgnoreCase(ispData.getStatus())) {
					success++;
				} else if ("IN_PROGRESS".equalsIgnoreCase(ispData.getStatus())) {
					inprogress++;
				} else if ("FAILURE".equalsIgnoreCase(ispData.getStatus())) {
					failure++;
				}
				owner=ispData.getOwner();
				subdevision=ispData.getSubdevision();
				substation=ispData.getSubstation();
				feeder=ispData.getFeeder();
				dt=ispData.getDt();
				meter=ispData.getMeterNo();
				batch = ispData.getBatch_id();
				isFirstTime = false;
				
				if(count==batchRequestList.size())
				{
					setBatchLogConfiguration(batchLogs, added, success, inprogress, failure, owner, subdevision, substation, feeder, dt, meter, owners, subdevisions, substations, feeders, dts, meters);
		            batchLogsList.add(batchLogs);	
				}
			}
			else { 
			   setBatchLogConfiguration(batchLogs, added, success, inprogress, failure, owner, subdevision, substation, feeder, dt, meter, owners, subdevisions, substations, feeders, dts, meters);
               batchLogsList.add(batchLogs);
               
               batchLogs = new DevicesConfigBatchLogs();
               owners=new TreeSet<String>();
      		   subdevisions=new TreeSet<String>();
      		   substations=new TreeSet<String>();
      	       feeders=new TreeSet<String>();
      		   dts=new TreeSet<String>();
      		   meters=new TreeSet<String>();
               added=0; success=0;inprogress=0;failure=0;
               setBatchLogs(ispData, batchLogs, owners, subdevisions, substations, feeders, dts, meters);

               if ("ADDED".equalsIgnoreCase(ispData.getStatus())) {
					added++;
				}else if ("SUCCESS".equalsIgnoreCase(ispData.getStatus())) {
					success++;
				} else if ("IN_PROGRESS".equalsIgnoreCase(ispData.getStatus())) {
					inprogress++;
				} else if ("FAILURE".equalsIgnoreCase(ispData.getStatus())) {
					failure++;
				}
				owner=ispData.getOwner();
				subdevision=ispData.getSubdevision();
				substation=ispData.getSubstation();
				feeder=ispData.getFeeder();
				dt=ispData.getDt();
				batch = ispData.getBatch_id();	
				if(count==batchRequestList.size())
				{
					setBatchLogConfiguration(batchLogs, added, success, inprogress, failure, owner, subdevision, substation, feeder, dt, meter, owners, subdevisions, substations, feeders, dts, meters);
		            batchLogsList.add(batchLogs);	
				}
			}
		}

		if (batchLogsList.size() > 0) {
			devicesConfigBatchLogsRepository.saveAll(batchLogsList);
			LOG.info("DevicesConfigBatchLogs Saved Successfully.");
		}
	}

    public void setBatchLogs(DevicesCommandsBatchLogsRequest ispData, DevicesConfigBatchLogs batchLogs, TreeSet<String> owners, TreeSet<String> subdevisions,
  			TreeSet<String> substations, TreeSet<String> feeders, TreeSet<String> dts, TreeSet<String> meters) {
  
	batchLogs.setMdas_datetime(ispData.getMdas_datetime());
	batchLogs.setCommand_name(ispData.getCommand_name());
	batchLogs.setBatch_id(ispData.getBatch_id());
	owners.add(ispData.getOwner());
	subdevisions.add(ispData.getSubdevision());
	substations.add(ispData.getSubstation());
	feeders.add(ispData.getFeeder());
	dts.add(ispData.getDt());
	meters.add(ispData.getMeterNo());
  }
	 public void setBatchLogConfiguration(DevicesConfigBatchLogs batchLogs, int added, int success, int inprogress, int failure,
	    		String owner, String subdevision, String substation, String feeder, String dt, String meter,
	    		TreeSet<String> owners, TreeSet<String> subdevisions, TreeSet<String> substations,
	    		TreeSet<String> feeders, TreeSet<String> dts, TreeSet<String> meters) {
	    	
	    	  if(1==meters.size()) {
	           batchLogs.setHier_name("7");
	           batchLogs.setHier_value(meter);
	          }  
	    	 else if(1==dts.size()) {
	      	   batchLogs.setHier_name("5");
	      	   batchLogs.setHier_value(dt);
	         }
	         else if(1==feeders.size()) {
	      	   batchLogs.setHier_name("4");
	      	   batchLogs.setHier_value(feeder);
	         }
	         else if(1==substations.size()) {
	      	   batchLogs.setHier_name("3");
	      	   batchLogs.setHier_value(substation);
	         }
	         else if(1==subdevisions.size()) {
	      	   batchLogs.setHier_name("2");
	      	   batchLogs.setHier_value(subdevision);
	         }
	         else if(1==owners.size()) {
	      	   batchLogs.setHier_name("1");
	      	   batchLogs.setHier_value(owner);
	         }
	    	 batchLogs.setAdded(added);
	         batchLogs.setSuccess(success);
	         batchLogs.setIn_progress(inprogress);
	         batchLogs.setFailure(failure);   
		}
	      
	public void prepareDevicesConfigBatchLogs(String outputList,
			List<DevicesConfigBatchLogsResponse> ispResponseList) throws Exception {
		LOG.info("DevicesConfigBatchLogs Data Caster Called....");
		List<DevicesConfigBatchLogs> batchRequestList = new ArrayList<DevicesConfigBatchLogs>();
		batchRequestList = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<DevicesConfigBatchLogs>>() {
				});
		LOG.info("DevicesConfigBatchLogs Response Data Adding....");

		for (DevicesConfigBatchLogs ispData : batchRequestList) {
			DevicesConfigBatchLogsResponse ispResponse = new DevicesConfigBatchLogsResponse();

			ispResponse.setBatchId(ispData.getBatch_id());
			ispResponse.setCommandName(ispData.getCommand_name());
			ispResponse.setSuccess(ispData.getSuccess());
			ispResponse.setFailure(ispData.getFailure());
			ispResponse.setInProgress(ispData.getIn_progress());
			ispResponse.setAdded(ispData.getAdded());
			ispResponse.setHierName(MdmExternalConstants.getUIBatchLevelName(ispData.getHier_name()));
			ispResponse.setHierValue(ispData.getHier_value());
			ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			
			ispResponseList.add(ispResponse);
		}

	}

	public void saveDevicesConfigReadBatchLogs(String outputList) throws Exception {
		LOG.info("DevicesConfigReadBatchLogs Data Caster Called....");
		List<DevicesCommandsBatchLogsRequest> batchRequestList = new ArrayList<DevicesCommandsBatchLogsRequest>();
		batchRequestList = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<DevicesCommandsBatchLogsRequest>>() {
				});
		LOG.info("DevicesConfigReadBatchLogs Response Data Adding....");

		 String batch = "";
		 int count = 0;
		 int success = 0;
		 int inprogress = 0;
		 int failure = 0;
		 TreeSet<String> owners = new TreeSet<String>();
		 TreeSet<String> subdevisions = new TreeSet<String>();
		 TreeSet<String> substations = new TreeSet<String>();
		 TreeSet<String> feeders = new TreeSet<String>();
		 TreeSet<String> dts = new TreeSet<String>();
		 TreeSet<String> meters = new TreeSet<String>();
		 String owner = "";
		 String subdevision = "";
		 String substation = "";
		 String feeder = "";
		 String dt = "";
		 String meter = "";
		boolean isFirstTime = true;
		ArrayList<DevicesConfigReadBatchLogs> batchLogsList = new ArrayList<DevicesConfigReadBatchLogs>();
		DevicesConfigReadBatchLogs batchLogs = new DevicesConfigReadBatchLogs();

		for (DevicesCommandsBatchLogsRequest ispData : batchRequestList) {
			
			count++;
			if (isFirstTime || ispData.getBatch_id().equals(batch)) {
				
				setBatchLogs(ispData, batchLogs, owners, subdevisions, substations, feeders, dts, meters);
				
				if ("SUCCESS".equalsIgnoreCase(ispData.getStatus())) {
					success++;
				} else if ("IN_PROGRESS".equalsIgnoreCase(ispData.getStatus())) {
					inprogress++;
				} else if ("FAILURE".equalsIgnoreCase(ispData.getStatus())) {
					failure++;
				}
				owner=ispData.getOwner();
				subdevision=ispData.getSubdevision();
				substation=ispData.getSubstation();
				feeder=ispData.getFeeder();
				dt=ispData.getDt();
				meter=ispData.getMeterNo();
				batch = ispData.getBatch_id();
				isFirstTime = false;
				
				if(count==batchRequestList.size())
				{
					setBatchLogConfiguration(batchLogs, success, inprogress, failure, owner, subdevision, substation, feeder, dt, meter, owners, subdevisions, substations, feeders, dts, meters);
		            batchLogsList.add(batchLogs);	
				}
			}
			else { 
			   setBatchLogConfiguration(batchLogs, success, inprogress, failure, owner, subdevision, substation, feeder, dt, meter, owners, subdevisions, substations, feeders, dts, meters);
               batchLogsList.add(batchLogs);
               
               batchLogs = new DevicesConfigReadBatchLogs();
               owners=new TreeSet<String>();
      		   subdevisions=new TreeSet<String>();
      		   substations=new TreeSet<String>();
      	       feeders=new TreeSet<String>();
      		   dts=new TreeSet<String>();
      		   meters=new TreeSet<String>();
               success=0;inprogress=0;failure=0;
               setBatchLogs(ispData, batchLogs, owners, subdevisions, substations, feeders, dts, meters);

                if ("SUCCESS".equalsIgnoreCase(ispData.getStatus())) {
					success++;
				} else if ("IN_PROGRESS".equalsIgnoreCase(ispData.getStatus())) {
					inprogress++;
				} else if ("FAILURE".equalsIgnoreCase(ispData.getStatus())) {
					failure++;
				}
				owner=ispData.getOwner();
				subdevision=ispData.getSubdevision();
				substation=ispData.getSubstation();
				feeder=ispData.getFeeder();
				dt=ispData.getDt();
				batch = ispData.getBatch_id();	
				if(count==batchRequestList.size())
				{
					setBatchLogConfiguration(batchLogs, success, inprogress, failure, owner, subdevision, substation, feeder, dt, meter, owners, subdevisions, substations, feeders, dts, meters);
		            batchLogsList.add(batchLogs);	
				}
			}
		}

		if (batchLogsList.size() > 0) {
			devicesConfigReadBatchLogsRepository.saveAll(batchLogsList);
			LOG.info("DevicesConfigReadBatchLogs Saved Successfully.");
		}
	}
	
	 public void setBatchLogs(DevicesCommandsBatchLogsRequest ispData, DevicesConfigReadBatchLogs batchLogs, TreeSet<String> owners, TreeSet<String> subdevisions,
	   			TreeSet<String> substations, TreeSet<String> feeders, TreeSet<String> dts, TreeSet<String> meters) {
	   
	 	    batchLogs.setMdas_datetime(ispData.getMdas_datetime());
			batchLogs.setBatch_id(ispData.getBatch_id());
			owners.add(ispData.getOwner());
			subdevisions.add(ispData.getSubdevision());
			substations.add(ispData.getSubstation());
			feeders.add(ispData.getFeeder());
			dts.add(ispData.getDt());
			meters.add(ispData.getMeterNo());
		}
	 
	public void setBatchLogConfiguration(DevicesConfigReadBatchLogs batchLogs, int success, int inprogress, int failure,
    		String owner, String subdevision, String substation, String feeder, String dt, String meter,
    		TreeSet<String> owners, TreeSet<String> subdevisions, TreeSet<String> substations,
    		TreeSet<String> feeders, TreeSet<String> dts, TreeSet<String> meters) {
    	
    	  if(1==meters.size()) {
           batchLogs.setHier_name("7");
           batchLogs.setHier_value(meter);
          }  
    	 else if(1==dts.size()) {
      	   batchLogs.setHier_name("5");
      	   batchLogs.setHier_value(dt);
         }
         else if(1==feeders.size()) {
      	   batchLogs.setHier_name("4");
      	   batchLogs.setHier_value(feeder);
         }
         else if(1==substations.size()) {
      	   batchLogs.setHier_name("3");
      	   batchLogs.setHier_value(substation);
         }
         else if(1==subdevisions.size()) {
      	   batchLogs.setHier_name("2");
      	   batchLogs.setHier_value(subdevision);
         }
         else if(1==owners.size()) {
      	   batchLogs.setHier_name("1");
      	   batchLogs.setHier_value(owner);
         }
         batchLogs.setSuccess(success);
         batchLogs.setIn_progress(inprogress);
         batchLogs.setFailure(failure);   
	}
	
	public void prepareDevicesConfigReadBatchLogs(String outputList,
			List<DevicesConfigReadBatchLogsResponse> ispResponseList) throws Exception {
		LOG.info("DevicesConfigReadBatchLogs Data Caster Called....");
		List<DevicesConfigReadBatchLogs> batchRequestList = new ArrayList<DevicesConfigReadBatchLogs>();
		batchRequestList = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<DevicesConfigReadBatchLogs>>() {
				});
		LOG.info("DevicesConfigReadBatchLogs Response Data Adding....");

		for (DevicesConfigReadBatchLogs ispData : batchRequestList) {
			DevicesConfigReadBatchLogsResponse ispResponse = new DevicesConfigReadBatchLogsResponse();

			ispResponse.setBatchId(ispData.getBatch_id());
			ispResponse.setHierName(MdmExternalConstants.getUIBatchLevelName(ispData.getHier_name()));
			ispResponse.setHierValue(ispData.getHier_value());
			ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			
			ispResponseList.add(ispResponse);
		}

	}

}
