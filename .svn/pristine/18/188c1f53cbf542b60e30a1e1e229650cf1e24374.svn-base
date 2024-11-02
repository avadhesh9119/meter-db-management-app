package com.global.meter.inventive.task;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.common.model.FullDataMeterResponse;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.request.model.DeviceCommand;
import com.global.meter.service.DeviceCommandService;
import com.global.meter.service.FullDataCommandService;
import com.global.meter.service.PrepayDataCommandService;
import com.global.meter.utils.Constants;

public class HistoricalGroupReadCommandTask implements Runnable{

	private static final Logger LOG = LoggerFactory.getLogger(HistoricalGroupReadCommandTask.class);
	
	private CountDownLatch countDownLatch;
	private DeviceCommand deviceCommands;
	private DeviceCommandService deviceCommandService;
	private FullDataCommandService fullDataCommandService;
	private PrepayDataCommandService prepayDataCommandService;
	
	public HistoricalGroupReadCommandTask(CountDownLatch countDownLatch, DeviceCommand device, 
			String balanceCommand, DeviceCommandService deviceCommandService,
			FullDataCommandService fullDataCommandService, PrepayDataCommandService prepayDataCommandService) {
		this.countDownLatch = countDownLatch;
		this.deviceCommands = device;
		this.deviceCommandService = deviceCommandService;
		this.fullDataCommandService = fullDataCommandService;
		this.prepayDataCommandService = prepayDataCommandService;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
				LOG.info(this.deviceCommands.getCommand() + " command send to meter : "
					+ deviceCommands.getDevice().getPlainText());
				ResponseEntity<?> response = null;
				if(ConfigCommands.DISCONNECT.commandName.equals(this.deviceCommands.getCommand())
						|| ConfigCommands.CONNECT.commandName.equals(this.deviceCommands.getCommand())) {
					response = (ResponseEntity<MeterResponse>) 
							deviceCommandService.writeConnectDisconnectEntity(this.deviceCommands);
				}
				else if(ConfigCommands.FULL_DATA_READ.commandName.equals(this.deviceCommands.getCommand())) {
					FullDataMeterResponse fullDataResponse = new FullDataMeterResponse();
					response = fullDataCommandService.getResponseReadEntity(this.deviceCommands, fullDataResponse);
				}
				else if(Constants.DataSet.Full_Prepay_Data.equals(this.deviceCommands.getCommand())) {
					FullDataMeterResponse fullDataResponse = new FullDataMeterResponse();
					response = prepayDataCommandService.getResponseReadEntity(this.deviceCommands, fullDataResponse);
				}
				else {
					response = (ResponseEntity<MeterResponse>) 
							deviceCommandService.getResponseReadEntity(this.deviceCommands);
				}
				
				LOG.info(this.deviceCommands.getCommand() + " Response Received meter: "
						+ deviceCommands.getDevice().getPlainText() + " : " + response.getBody().toString());
				
		}
		catch(Exception exception) {
			LOG.error(this.deviceCommands.getDevice().getPlainText()+" : "+this.deviceCommands.getCommand()
					+" :API is having exception while calling:"+exception.getMessage());
		}
		this.countDownLatch.countDown();
	
	}

}
