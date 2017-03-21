package Basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is implemented to store all city data collected from input files
 * @author zhuoyingli
 *
 */
public class TspAllCities {
	/**
	 * To store all city data
	 */
	private Map<Integer, TspCity> allcities;
	
	/**
	 * Default constructor
	 */
	public TspAllCities(){
		allcities = new HashMap<Integer, TspCity>();
	}
	
	/**
	 * Get the data of a city with a node id
	 * @param nodeid the id of a city
	 * @return the city with the defined node id
	 */
	public TspCity getCitywithNodeId(int nodeid){
		return allcities.get(nodeid);
	}
	
	/**
	 * Add the data of a city
	 * @param city the city to be add
	 */
	public void addCity(TspCity city){
		allcities.put(city.getNodeId(), city);
	}
	
	/**
	 * Get the number of all cities
	 * @return int number of cities
	 */
	public int getNumberOfCities(){
		return allcities.size();
	}
	
	/**
	 * Get all city representations
	 * @return  ArrayList<TspCity> all city data
	 */
	public ArrayList<TspCity> getAllCities(){
		ArrayList<TspCity> result = new ArrayList<TspCity>(allcities.values());
		return result;
	}
	
	/**
	 * Get all nodeid of cities
	 * @return  ArrayList<Integer> all nodeid
	 */
	public ArrayList<Integer> getAllCityNodeId(){
		return new ArrayList<Integer>(allcities.keySet());
	}
}
