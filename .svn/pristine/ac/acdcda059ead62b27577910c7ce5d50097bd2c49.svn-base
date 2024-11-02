package com.global.meter.v3.inventive.task;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;
import com.global.meter.v3.inventive.service.SingleConnectionCommandService;

public class SingleConnectionReadCommandTask  implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(SingleConnectionReadCommandTask.class);
	
	private CountDownLatch countDownLatch;
	private SingleConnectionCommandReq CommandReq;
	private SingleConnectionCommandService singleConnectionCommandService;
	
	public SingleConnectionReadCommandTask(CountDownLatch countDownLatch, 
			String balanceCommand, SingleConnectionCommandReq CommandReq,
			SingleConnectionCommandService singleConnectionCommandService) {
		this.countDownLatch = countDownLatch;
		this.CommandReq = CommandReq;
		this.singleConnectionCommandService = singleConnectionCommandService;
	}
	
	@Override
	public void run() {
		try {
				LOG.info("Command send to meter : "
					+ CommandReq.getDevice().getPlainText());
				Object objResponse = null;
				
				objResponse =  singleConnectionCommandService.addSingleConnectionCommandLogs(this.CommandReq);
					
					if (objResponse instanceof CommonResponse) {
						
						CommonResponse response = (CommonResponse) objResponse;
						
						LOG.info("Global Batch Id: " + response.getBatchId() != null ? response.getBatchId() : "-" +"\nResponse Received meter: "
								+ CommandReq.getDevice().getPlainText() + "\n" + response.getCommandStatus() != null ? response.getCommandStatus().toString() : "-"+"\n\n");
					}
				
				
				
		}
		catch(Exception exception) {
			LOG.error(this.CommandReq.getDevice().getPlainText()+" : "+this.CommandReq.getCommand()
					+" :API is having exception while calling:"+exception.getMessage());
		}
		this.countDownLatch.countDown();
	
	}
}
