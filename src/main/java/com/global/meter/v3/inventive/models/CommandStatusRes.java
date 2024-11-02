package com.global.meter.v3.inventive.models;

public class CommandStatusRes {
	private String commandName;
	private String trackingId;
	private String extBatchId;
	private String commandDateTime;
	private String commandCompletionDateTime;
	private String status;
	public String getCommandName() {
		return commandName;
	}
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getExtBatchId() {
		return extBatchId;
	}
	public void setExtBatchId(String extBatchId) {
		this.extBatchId = extBatchId;
	}
	public String getCommandDateTime() {
		return commandDateTime;
	}
	public void setCommandDateTime(String commandDateTime) {
		this.commandDateTime = commandDateTime;
	}
	public String getCommandCompletionDateTime() {
		return commandCompletionDateTime;
	}
	public void setCommandCompletionDateTime(String commandCompletionDateTime) {
		this.commandCompletionDateTime = commandCompletionDateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "CommandStatus [commandName=" + commandName + ", trackingId=" + trackingId + ", extBatchId=" + extBatchId
				+ ", commandDateTime=" + commandDateTime + ", commandCompletionDateTime=" + commandCompletionDateTime
				+ ", status=" + status + "]";
	}
}
