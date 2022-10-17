package com.virtusa.jsm.exception;

import java.time.LocalDateTime;

public class ErrorInfo {
	
	LocalDateTime timestamp;
	String msg;
	String uri;
	public ErrorInfo() {
		super();
	}
	public ErrorInfo(LocalDateTime timestamp, String msg, String uri) {
		super();
		this.timestamp = timestamp;
		this.msg = msg;
		this.uri = uri;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	@Override
	public String toString() {
		return "ErrorInfo [timestamp=" + timestamp + ", msg=" + msg + ", uri=" + uri + "]";
	}
	
	
}
