package com.edson.filho.flight.routes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.edson.filho.flight.routes.controller.FlightRoutesConsole;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-08-31 
*/
@SpringBootApplication
public class FlightRoutesApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(FlightRoutesApplication.class, args);
		//starts console application
		FlightRoutesConsole.startConsoleInterface();
	}

}
