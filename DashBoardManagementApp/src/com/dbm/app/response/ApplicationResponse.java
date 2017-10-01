/*
 * (c) copyright, 2017
 * 
 */
package com.dbm.app.response;

import java.util.List;

import com.dbm.app.entity.Users;

/**
 * This pojo class is responsible to store all kind of error message, error code and result.
 * 
 * @author ravi ranjan kumar
 * @since 2017-10-01
 *
 */
public class ApplicationResponse {

	private int errorCode;
	private String errorMessage;
	private List<Users> result;

	public List<Users> getResult() {
		return result;
	}
	public void setResult(List<Users> result) {
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
