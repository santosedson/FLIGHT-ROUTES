package com.edson.filho.flight.routes.util;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-08-29 
*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dijkstra {
	 	
		
	    private double routes[][];
	    private static final int UNDEFINED_COST = -1;

	    public Dijkstra(int numOfRoutes) {
	        routes = new double[numOfRoutes][numOfRoutes];
	    }

	    public void defineRoutes(int route1, int route2, double cost) {
	        routes[route1][route2] = cost;
	        routes[route2][route1] = cost;
	    }

	    public double getRouteCost(int vertex1, int vertex2) {
	        return routes[vertex1][vertex2];
	    }

	    private List<Integer> defineRouteConnections(int[] preceding, int nextRoute) {
	        List<Integer> route = new ArrayList<>();
	        route.add(nextRoute);
	        while (preceding[nextRoute] != UNDEFINED_COST) {
	        	route.add(preceding[nextRoute]);
	        	nextRoute = preceding[nextRoute];
	        }
	        Collections.reverse(route);
	        return route;
	    }
	    private int closestRoute(double[] cost, Set<Integer> unvisited) {
	        double minCost = Integer.MAX_VALUE;
	        int minCostIndex = 0;
	        for (Integer i : unvisited) {
	            if (cost[i] < minCost) {
	            	minCost = cost[i];
	            	minCostIndex = i;
	            }
	        }
	        return minCostIndex;
	    }
	    public List<Integer> getIndexesOfConnectedRoutes(int route) {
	        List<Integer> connectedRoutes = new ArrayList<>();
	        for (int i = 0; i < routes[route].length; i++)
	            if (routes[route][i] > 0) {
	            	connectedRoutes.add(i);
	            }

	        return connectedRoutes;
	    }

	    public List<Integer> getRouteConnectionsWithLowestPrice(int from, int to) {

	        double cost[] = new double[routes.length];
	        cost[from] = 0;
	        int preceding[] = new int[routes.length];
	        Set<Integer> notVerified = new HashSet<>();

	        for (int i = 0; i < routes.length; i++) {
	            if (i != from) {
	                cost[i] = Integer.MAX_VALUE;
	            }
	            preceding[i] = UNDEFINED_COST;
	            notVerified.add(i);
	        }

	        while (!notVerified.isEmpty()) {
	            int nextRoute = closestRoute(cost, notVerified);
	            notVerified.remove(nextRoute);
	  
	            for (Integer connectedRoute : getIndexesOfConnectedRoutes(nextRoute)) {
	                double costTotal = cost[nextRoute] + getRouteCost(nextRoute, connectedRoute);
	                if (costTotal < cost[connectedRoute]) {
	                    cost[connectedRoute] = costTotal;
	                    preceding[connectedRoute] = nextRoute;
	                }
	            }
	            //Rota encontrada?
	            if (nextRoute == to) {
	                return defineRouteConnections(preceding, nextRoute); 
	            }
	        }
	        return Collections.emptyList();
	    }

}
