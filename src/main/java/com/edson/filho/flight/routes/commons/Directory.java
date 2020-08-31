package com.edson.filho.flight.routes.commons;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-08-31 
*/
public enum Directory {
	DIR("src/main/resources/csv/"),
	FILE_TYPE(".csv");
	
	private String code;

	private Directory(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}

}
