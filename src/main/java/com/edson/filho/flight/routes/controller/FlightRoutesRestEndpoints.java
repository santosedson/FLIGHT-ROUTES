package com.edson.filho.flight.routes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edson.filho.flight.routes.commons.Directory;
import com.edson.filho.flight.routes.data.FileLines;
import com.edson.filho.flight.routes.service.FlightRoutesHandler;
import com.edson.filho.flight.routes.util.Formater;

/**
* @author  Edson Filho
* @version 1.0
* @since   2020-08-31 
*/
@RestController
@RequestMapping("/api/v1/route")
public class FlightRoutesRestEndpoints {
	
	FlightRoutesHandler flightRoutesHandler = new FlightRoutesHandler();
	Formater f= new Formater();
	 
	/**
     * This endpoint is used to insert a new route in csv file
     * @param Json with the same structure of FileLines (See an example in src/main/resources/samples)
     * @return formated result in HTML
     */
	@PostMapping("/input")
	public ResponseEntity<?> inputNewRoutes(@RequestBody FileLines newRoutes) {
		String result=flightRoutesHandler.inputNewRoutes(newRoutes);
		return ResponseEntity.accepted()
		        .body(f.formatToHtml(result));
	}
	
	/**
     * This endpoint is used find the routes with the best cost
     * @param chosen routes "FROM-TO"
     * @return formated result in HTML
     */
	@GetMapping("/find")
	public ResponseEntity<?> findcheapestRoute(@RequestParam(value = "route", required = false) String route ) {
		String result=flightRoutesHandler.getCheapestRoute(route, Directory.DIR.getCode());
		return ResponseEntity.accepted()
			    .body(f.formatToHtml(result));
	}


}
