package com.xad.model;

import com.google.gson.annotations.SerializedName;

public class Transformer {

	@SerializedName("iso8601_timestamp")
	private String timestamp;

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("connection_type")
	private String connectionType;

	@SerializedName("device_type")
	private String deviceType;

	@SerializedName("imps")
	private long imps;

	@SerializedName("clicks")
	private long clicks;

	public Transformer() {
	}

	public Transformer(String timestamp, String transactionId,
			String connectionType, String deviceType, long imps, long clicks) {
		this.timestamp = timestamp;
		this.transactionId = transactionId;
		this.connectionType = connectionType;
		this.deviceType = deviceType;
		this.imps = imps;
		this.clicks = clicks;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public long getImps() {
		return imps;
	}

	public void setImps(long imps) {
		this.imps = imps;
	}

	public long getClicks() {
		return clicks;
	}

	public void setClicks(long clicks) {
		this.clicks = clicks;
	}

}
