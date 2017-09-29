package com.dbm.app.response;

import java.util.List;
import java.util.Map;

public class ApplicationResponse {

	private int errorCode;
	private String errorMessage;
	private List<Map<String, String>> result;

	public List<Map<String, String>> getResult() {
		return result;
	}
	public void setResult(List<Map<String, String>> result) {
		this.result = result;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}	
}
