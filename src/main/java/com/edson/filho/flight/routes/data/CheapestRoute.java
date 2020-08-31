package com.edson.filho.flight.routes.data;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-08-31 
*/
public class CheapestRoute {

	
	private String route;
	private Double cost;
	
	
	public CheapestRoute (String route,Double cost) {
		this.route=route;
		this.cost=cost;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	
}
