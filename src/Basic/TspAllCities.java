package Basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TspAllCities {
	private Map<Integer, TspCity> allcities;
	
	public TspAllCities(){
		allcities = new HashMap<Integer, TspCity>();
	}
	
	public TspCity getCitywithNodeId(int nodeid){
		return allcities.get(nodeid);
	}
	
	public void addCity(TspCity city){
		allcities.put(city.getNodeId(), city);
	}
	
	public int getNumberOfCities(){
		return allcities.size();
	}
	
	public ArrayList<TspCity> getAllCities(){
		ArrayList<TspCity> result = new ArrayList<TspCity>(allcities.values());
		return result;
	}
	
	public ArrayList<Integer> getAllCityNodeId(){
		return new ArrayList<Integer>(allcities.keySet());
	}
}
