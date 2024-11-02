package com.global.meter.v3.inventive.models;

import java.util.Arrays;
import java.util.List;

public class MeterRawData {
	private List<String> obisCode;
	private Object[] data;

	public List<String> getObisCode() {
		return obisCode;
	}

	public void setObisCode(List<String> obisCode) {
		this.obisCode = obisCode;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "MeterResponseRawData [obisCode=" + obisCode + ", data=" + Arrays.toString(data) + "]";
	}

}
