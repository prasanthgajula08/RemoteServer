package com.rollout.pcremoteclient.udptest;

import java.io.Serializable;

public class Data_Object implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String sender;
	private String continuous;
	private String continuous_filename;
	private String continuous_filesize;
	private String continuous_id;
	private byte[] data;

	public Data_Object(String type, String sender, String continuous, String continuous_id, String continuous_filename,
	                   String continuous_filesize, byte[] data) {

		this.type = type;
		this.sender = sender;
		this.continuous = continuous;
		this.continuous_id = continuous_id;
		this.continuous_filename = continuous_filename;
		this.continuous_filesize = continuous_filesize;
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContinuous() {
		return continuous;
	}

	public void setContinuous(String continuous) {
		this.continuous = continuous;
	}

	public String getContinuous_filename() {
		return continuous_filename;
	}

	public void setContinuous_filename(String continuous_filename) {
		this.continuous_filename = continuous_filename;
	}

	public String getContinuous_id() {
		return continuous_id;
	}

	public void setContinuous_id(String continuous_id) {
		this.continuous_id = continuous_id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getContinuous_filesize() {
		return continuous_filesize;
	}

	public void setContinuous_filesize(String continuous_filesize) {
		this.continuous_filesize = continuous_filesize;
	}

}