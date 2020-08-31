package com.edson.filho.flight.routes.util;

import java.util.ArrayList;

import com.edson.filho.flight.routes.data.FileLine;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-08-29 
*/
public class Formater {

	 /**
     * This function will prepare the result to be shown in HTML format
     * @param String with result message 
     * @return String with result message for HTML
     */
	public String formatToHtml(String result) {
		return result.replace("#n", "</br>").replace("#b_", "</b>").replace("#b", "<b>");
	}
	 /**
     * This function will prepare the result to be shown in Console
     * @param String with result message 
     * @return String with result message for Console
     */
	
	public String formatToConsole(String result) {
		return result.replace("#n", "\n").replace("#b_", "").replace("#b", "");
	}
	
	 /**
     * This function will replace '?#1' by a param value inside message
     * @param param 1
     * @param message
     * @return the same message, but with params included
     */
	public String formatMessage(String p1, String msg) {
		return msg.replace("?#1", p1);
	}
	
	 /**
     * This function will replace '?#1' by a param value inside message
     * @param param 1
     * @param param 2
     * @param message
     * @return the same message, but with params included
     */
	public String formatMessage(String p1,String p2,String msg) {
		return msg.replace("?#1", p1).replace("?#2", p2);
	}
	
	 /**
     * Used by Rest Api to format inputted routes to be written in csv file
     * @param FileLines object
     * @return Route formated "from,to,cost"
     */
	public String formatInputRoute(FileLine line) {
		return line.getFrom()+","+line.getTo()+","+line.getCost();
	}
	 /**
     * easier way to format the result of Dijkstra algo
     * @param List of best Routes calculeted by Dijkstra
     * @return Route formated "from - to"
     */
	public String formateRoute(ArrayList<String> bestRoute) {
		String formatedRoute = bestRoute.toString();
		return formatedRoute.replace("[", "").replace("]", "").replace(",", " -");
	}
}
