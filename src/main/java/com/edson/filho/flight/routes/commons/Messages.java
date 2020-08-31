package com.edson.filho.flight.routes.commons;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-08-31 
*/
public enum Messages {
	//FOR INPUT
	INPUT_FILE_NOT_FOUND("Csv file ?#1 not found! #n"),
	INPUT_INVALID_ROUTE("Empty '?#1' in route ?#2  #n"),
	INPUT_INVALID_COST(" '?#1' is an invalid number in route ?#2  #n"),
	INPUT_ROUTE_SUCCESS("Routes have been successfully added to file '?#1'  #n"),
	//FOR CONSOLE
	CONSOLE_CONTINUE_MSG("Do you want to input a new route? (S/N)"),
	CONSOLE_CONTINUE_CHOISE("S"),
	CONSOLE_END_MSG("Ending console application. Thank you!#n #n"),
	CONSOLE_START_MSG("#n #n Please enter the route:"),
	//FOR ROUTES RESULT
	LOG_INFO_SEARCHING(" searching for csv files in  ?#1 #n"),
	LOG_INFO_RESULT("#n #n-----------------Result for file ?#1 -------------------#n"),
	LOG_INFO_END("----------------------------------------------------------------#n"),
	RESULT_CHEAPEST_ROUTE_MSG("Cheapest route:#n"),
	RESULT_CHEAPEST_ROUTE("#b      ?#1 > ?#2 #b_#n"),
	INVALID_ROUTE("(?#1) is an invalid route! "
			+ "Please input a departure airport"
			+ " and a destination airport in the following format: ABC-DEF."
			+ " valid examples:  REC-GRU , GRU-BRC, ORL-CDG ...#n"),
	ROUTE_NOT_FOUND("Sorry!"
			+ "We are unable to find any available routes"
			+ " to your chosen departure airport and destination airport(?#1).#n"),
	EMPTY_DIR("Dir '?#1' not found or doesn't have '.csv' files!"),
	//OTHERS
	COST("cost"),
	TO("to"),
	FROM("from"),
	VALUES("values");

	
	private String msg;

	private Messages(String msg) {
		this.msg = msg;
	}
	
	public String getMsg() {
		return this.msg;
	}

}
