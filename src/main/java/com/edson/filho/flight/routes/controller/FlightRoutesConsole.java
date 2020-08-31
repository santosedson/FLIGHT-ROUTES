package com.edson.filho.flight.routes.controller;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.edson.filho.flight.routes.commons.Directory;
import com.edson.filho.flight.routes.commons.Messages;
import com.edson.filho.flight.routes.service.FlightRoutesHandler;
import com.edson.filho.flight.routes.util.Formater;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-08-31 
*/
@Component
public class FlightRoutesConsole {
	
	 /**
     * This function represents the console interface that is runned at the same time that spring application (Rest interface)
     */
	public static void startConsoleInterface() {
		 Scanner input = new Scanner(System.in);  
		 Formater f= new Formater();
		 String rota ;
		 String inputAgain=Messages.CONSOLE_CONTINUE_CHOISE.getMsg();
	
		 while(inputAgain.equalsIgnoreCase(Messages.CONSOLE_CONTINUE_CHOISE.getMsg())) {
			 
			System.out.println(f.formatToConsole(Messages.CONSOLE_START_MSG.getMsg()));
			rota = input.nextLine(); 
			FlightRoutesHandler flightRoutesHandler = new FlightRoutesHandler();
			String result =flightRoutesHandler.getCheapestRoute( rota, Directory.DIR.getCode() );
			System.out.println(f.formatToConsole(result));
			System.out.println(Messages.CONSOLE_CONTINUE_MSG.getMsg());
			inputAgain = input.nextLine(); 
		 }
		 System.out.println(Messages.CONSOLE_END_MSG.getMsg());
		 input.close();
	}
	
}
