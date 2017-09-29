package com.dbm.app.error;

public enum ApplicationError {

	SUCCESS(1, "Success"), FILE_NOT_FOUND(2, "File Not Found"), INVALID_INPUT(-3, "Invalid Input"), INTERNAL_ERROR(3,
			"Internal Procressing Error"), SQL_ERROR(4, "Sql Query Error"), INTERNAL_SERVER_ERROR(5,
					"Internal Server Error"), DATABASE_ERROR(6, "Database Error"), XML_CONFIG_ERROR(7,
							"Falied to load XML config file"), Query_Build_ERROR(8,
									"Falied to build the query"), DATA_EXISTS(9, "data already exists for this id");

	private int errorCode;
	private String errorMessage;

	private ApplicationError(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
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
