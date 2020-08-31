package com.edson.filho.flight.routes.service;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-08-31 
*/
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.edson.filho.flight.routes.commons.Messages;
import com.edson.filho.flight.routes.data.CheapestRoute;
import com.edson.filho.flight.routes.data.FileLine;
import com.edson.filho.flight.routes.data.FileLines;
import com.edson.filho.flight.routes.util.Dijkstra;
import com.edson.filho.flight.routes.util.Formater;
@Service
public class FlightRoutesHandler {
	
		FileHandler fileHandler = new FileHandler();
		Formater f= new Formater();
			
	    /**
	     * Given the chosen routes, this function will read the available csv files and return with the cheapest route
	     * @param route "from-to" 
	     * @param path of csv files
	     * @return Formated message with the cheapest route by file
	     */
		public String getCheapestRoute(String chosenRoute, String fileDir ) {
			
			 String [] chosenRouteArray = validateChosenRoute(chosenRoute);
			 String chosenFrom;
			 String chosenTo;
			 StringBuilder formatedMessage = new StringBuilder();
			 ArrayList<CheapestRoute> cheapestRoutes=null;
			 List<File> files=fileHandler.scanFiles(fileDir);
			 //if none csv files were found an error is returned
			 if(files.isEmpty()) {
				return  f.formatMessage(fileDir, Messages.EMPTY_DIR.getMsg());
			 }
			//if chosen route has a invalid format an error is returned
			 if(chosenRouteArray==null) {
				return f.formatMessage(chosenRoute, Messages.INVALID_ROUTE.getMsg());
			 }
			
			 chosenFrom=chosenRouteArray[0];
			 chosenTo=chosenRouteArray[1];
			 formatedMessage.append(f.formatMessage(fileDir,Messages.LOG_INFO_SEARCHING.getMsg()));
			 for (File file : files) {//for 1
				 
					if (file.isDirectory() == false) {//if 2
						cheapestRoutes=findCheapestRoutes(file,chosenFrom,chosenTo);
						formatedMessage.append(f.formatMessage(file.getName(),Messages.LOG_INFO_RESULT.getMsg()));
						if(!cheapestRoutes.isEmpty()) {//if 3
							formatedMessage.append(Messages.RESULT_CHEAPEST_ROUTE_MSG.getMsg());
							formatedMessage.append(f.formatMessage(cheapestRoutes.get(0).getRoute(),
									cheapestRoutes.get(0).getCost().toString(),
									Messages.RESULT_CHEAPEST_ROUTE.getMsg()));
								
						}else {
							formatedMessage.append(f.formatMessage(chosenRoute, Messages.ROUTE_NOT_FOUND.getMsg()));
						}//end if 3
						formatedMessage.append(Messages.LOG_INFO_END.getMsg());
					}//end if 2
			 }//end for 1
			
			 return formatedMessage.toString();
		}
		
		/**
	     * This function will split chosen route in an array with size 2. After that it will check if the chosen route is valid.
	     * @param route "from-to" 
	     * @return Valid (Array[0]= from , Array[1]=to) , Invalid ( null )
	     */
		public  String[]  validateChosenRoute(String chosenRoute) {
			String[] chosenRouteArray=null;
			if(chosenRoute!=null) {
				chosenRouteArray  = chosenRoute.replace(" ", "").split("-");
				if(chosenRouteArray.length!=2) { 
					chosenRouteArray=null;
				}else {
					if(chosenRouteArray[0].equalsIgnoreCase(chosenRouteArray[1])) {
						chosenRouteArray=null;
					}
				}
			}
			return chosenRouteArray;
		}
		 /**
	     * Used by Rest Api, this function will input the new routes editting csv file
	     * @param FileLines object ( deserialized json)
	     * @return message to the user (success or fail)
	     */
		public String inputNewRoutes(FileLines newRoutes) {
			String fileName=newRoutes.getFileName();
			String formatedRoute;
			boolean sucess = false;
			String invalidRoutes =validateInputRoutes (newRoutes);
			if(invalidRoutes==null) {
				for(FileLine line: newRoutes.getLines()) {
					formatedRoute=f.formatInputRoute(line);
					sucess=fileHandler.editFile(fileName, formatedRoute);
					if(!sucess) {
						break;
					}
				}
				if(sucess) {
					return f.formatMessage(fileName, Messages.INPUT_ROUTE_SUCCESS.getMsg()) ;
				}else {
					return f.formatMessage(fileName, Messages.INPUT_FILE_NOT_FOUND.getMsg()) ;
				}
			}else {
				return invalidRoutes;
			}
		}
		 /**
	     * Used by Rest Api, this function will validate the content of json used to represent a new line in csv file
	     * @param FileLines object (deserialized json)
	     * @return valid input (null) , invalid input (Error message)
	     */
		public  String  validateInputRoutes(FileLines newRoutes) {
			String invalid=null;
			int count=0;
			String from;
			String to;
			String cost;
			for(FileLine line: newRoutes.getLines()) {
				count++;
				if(line!=null) {
					
					 from =line.getFrom();
					 to=line.getTo();
					 cost=line.getCost();
					
					if(cost!=null) {
						if(cost.trim().equals("")) {
							invalid=f.formatMessage(Messages.COST.getMsg(), count+"", Messages.INPUT_INVALID_ROUTE.getMsg());
							break;
						}else {
							try {
								Double.parseDouble(cost);
							}catch(Exception e) {
								invalid=f.formatMessage( cost,count+"", Messages.INPUT_INVALID_COST.getMsg());
							}
						}
					}else {
						invalid=f.formatMessage(Messages.COST.getMsg(), count+"", Messages.INPUT_INVALID_ROUTE.getMsg());
						break;
					}
					if(to!=null) {
						if(to.trim().equals("")) {
							invalid=f.formatMessage(Messages.TO.getMsg(), count+"", Messages.INPUT_INVALID_ROUTE.getMsg());
							break;
						}
					}else {
						invalid=f.formatMessage(Messages.TO.getMsg(), count+"", Messages.INPUT_INVALID_ROUTE.getMsg());
						break;
					}
					if(from!=null) {
						if(from.trim().equals("")) {
							invalid=f.formatMessage(Messages.FROM.getMsg(), count+"", Messages.INPUT_INVALID_ROUTE.getMsg());
							break;
						}
					}else {
						invalid=f.formatMessage(Messages.FROM.getMsg(), count+"", Messages.INPUT_INVALID_ROUTE.getMsg());
						break;
					}
				}else {
					invalid=f.formatMessage(Messages.VALUES.getMsg(), count+"", Messages.INPUT_INVALID_ROUTE.getMsg());
					break;
				}
			}
			return invalid;
		}
		
	    /**
	     * This function will handle the content of the file and will work to find the cheapest route
	     * @param csv file
	     * @param chosen route "from" 
	     * @param chosen route "to"
	     * @return The cheapest route
	     */
		private ArrayList<CheapestRoute> findCheapestRoutes(File file, String chosenFrom, String chosenTo) {
					
					ArrayList<CheapestRoute> cheapestRouteList= new ArrayList<CheapestRoute>();
					ArrayList<String> distinctAirports = new ArrayList<String>();
					Map<String, Integer> airportsCodeMap = new HashMap<String, Integer>();
					ArrayList<String> bestRoute=new ArrayList<String>();
					FileLines fileLines;
					double totalCost;
					int from;
					int to;
					
					try {
						//file content replicated in an java object to make it easier to work
						fileLines = fileHandler.readFile(file);
						
						//To make it easier for Dijkstra algorithm, I will select all distinct airport present in the file and give an correspondent code for each one
						for (FileLine fileLine : fileLines.getLines()) {
							if (!alreadyBeenAdded(distinctAirports, fileLine.getFrom())) {
								distinctAirports.add(fileLine.getFrom());
							}
							if (!alreadyBeenAdded(distinctAirports, fileLine.getTo())) {
								distinctAirports.add(fileLine.getTo());
							}
						}
						
						//if the chosen route isn't present in distincts airports list , doesn't make sense to continue
						if(isChosenRouteSearchable(distinctAirports,chosenFrom, chosenTo)==false){
							return cheapestRouteList;//Empty list
						}
						
						//giving an associate code for each airport
						airportsCodeMap=setAirportsCodeMap( distinctAirports);
						
						//init Dijkstra for calculate the best route
						Dijkstra rotas = new Dijkstra(distinctAirports.size());
						
						//Mapping all file lines to Dijkstra algo
						for (FileLine fileLine : fileLines.getLines()) {
							rotas.defineRoutes(airportsCodeMap.get(fileLine.getFrom()), airportsCodeMap.get(fileLine.getTo()),
									Double.parseDouble(fileLine.getCost()));
						}
						
						//set correspondent code of chosen 'From' and chosen 'To'
						from = airportsCodeMap.get(chosenFrom.toUpperCase());
						to = airportsCodeMap.get(chosenTo.toUpperCase());
						
						//get route connections with the lowest price
						for (Integer station : rotas.getRouteConnectionsWithLowestPrice(from, to)) {
							bestRoute.add(getKeyByValue(airportsCodeMap, station));
						}
						totalCost=gestTotalCost( fileLines, bestRoute);
						cheapestRouteList.add(new CheapestRoute(f.formateRoute(bestRoute),totalCost));
								
					} catch (Exception e) {
						System.out.println("An error occurred: "+e.getMessage());
					}
				return cheapestRouteList;
		}
		
	    /**
	     * This function will sum the cost of all best routes.
	     * @param FileLines ( csv file deserialized in a Java object)
	     * @param best route returned by Dijkstra algo
	     * @return Total cost of best route
	     */
		public double gestTotalCost(FileLines fileLines,ArrayList<String> bestRoute) {
			double totalCost=0.0;
			int maxIndex=bestRoute.size()-1;
			for (FileLine fileLine : fileLines.getLines()) {
				for(int i=0; i<bestRoute.size();i++) {
					if(fileLine.getFrom().equals(bestRoute.get(i))) {
						if(i+1 <=maxIndex) {
							if(fileLine.getTo().equals(bestRoute.get(++i))) {
								totalCost+= Double.parseDouble( fileLine.getCost());
							}
						}
					}
				}
			}
			return totalCost;
		}
		
	    /**
	     * This function will set a code for each airport to make it easier for Dijkstra algorithm
	     * @param List of all distinct airports 
	     * @return Map of airport with an associated code
	     */
		public Map<String, Integer> setAirportsCodeMap(ArrayList<String> distinctAirports){
			Map<String, Integer> airportsCodeMap = new HashMap<String, Integer>();
			int cod = 0;
			for (String key : distinctAirports) {
				airportsCodeMap.put(key, cod);
				cod++;
			}
			return airportsCodeMap;
		}
		
	    /**
	     * This function will check if the chosen route is present in distincts airports list
	     * @param List of all distinct airports 
	     * @param chosen route "from"
	     * @param chosen route "from"
	     * @return true (is present in the list) or false (isn't present in the list)
	     */
		public boolean isChosenRouteSearchable(ArrayList<String> distinctAirports, String chosenFrom, String chosenTo){
			
			boolean searchableRoute=false;
			boolean searchableFrom=false;
			boolean searchableTo=false;
			for (String route : distinctAirports) {
				if(chosenFrom.equalsIgnoreCase(route)) {
					searchableFrom=true;
				}
				if(chosenTo.equalsIgnoreCase(route)) {
					searchableTo=true;
				}
			}
			if(searchableFrom&&searchableTo) {
				searchableRoute=true;
			}
			return searchableRoute;
		}
		
	    /**
	     * This function will check if the informed airport already is present in list of distinct airports
	     * @param List of all distinct airports 
	     * @param airport
	     * @return true (already added) or false (not added yet)
	     */
		public  boolean alreadyBeenAdded(ArrayList<String> distinctAirports, String airport) {
			boolean jafoiAdd = false;
			for (String aeroporto : distinctAirports) {
				if(aeroporto!=null) {
					jafoiAdd = aeroporto.equals(airport);
					if (jafoiAdd) {
						break;
					}
				}
			}
			return jafoiAdd;
		}
		
		/**
	     * This function will unmap the airports mapped in setAirportsCodeMap funciton
	     * @param Map of airport with an correspondent code 
	     * @param code
	     * @return Airport string
	     */
		public  String getKeyByValue(Map<String, Integer> map, int value) {
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (value==(int)(entry.getValue())) {
					return entry.getKey();
				}
			}
			return null;
		}
}
