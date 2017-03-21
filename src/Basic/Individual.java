package Basic;

import java.util.ArrayList;
import java.util.Collections;
/**
 * This class is implemented for represent the individual in EC
 * @author zhuoyingli
 *
 */
public class Individual {
	/**
	 * The sequence of cities to be visited
	 */
	private ArrayList<Integer> individual;
	
	/**
	 * The store of all city data
	 */
	private TspAllCities allcities;
	
	/**
	 * The total Distance of traveling all cities following the sequence of this individual
	 */
	private double TotalCost;
	
	/**
	 * The fitness of this individual
	 */
	private double Fitness;
	
	/**
	 * Constructor of Individual.class
	 * @param allcities the store of all city data
	 */
	public Individual(TspAllCities allcities){
		this.allcities = allcities;
		individual = this.allcities.getAllCityNodeId();
		TotalCost = -1;
		Fitness = -1;
		CalculateTotalCost();
		CalculateFitness();
		
	}
	
	/**
	 * Default Constructors
	 */
	public Individual(){
		individual = new ArrayList<>();
	}
	
	public Individual(ArrayList<Integer> individual, TspAllCities allcities){
		this.individual = individual;
		this.allcities = allcities;
		CalculateTotalCost();
		CalculateFitness();
	}
	
	/**
	 * Set a city into individual with Node id
	 * @param NodeId
	 */
	public void setCitywithSequenceIndex(int NodeId){
		individual.add(NodeId);
		//reset fitness and totalcost due to new city added
        Fitness = -1;
        TotalCost = -1;
	}

	/**
	 * Evaluate the size of this individual
	 * @return boolean whether the size is correct
	 */
	public boolean EvaluateSizeofSolution(){
		return individual.size() == allcities.getNumberOfCities();
	}

	/**
	 * Get all city nodeid of the individual
	 * @return ArrayList<Integer>  node Ids
	 */
	public ArrayList<Integer> getIndividuals(){
		return this.individual;
	}
	/**
	 * Calculate the total cost of this individual
	 */
	public void CalculateTotalCost(){
		double total = 0.0;
		for(int i = 0; i < individual.size(); i++){
			 TspCity from = allcities.getCitywithNodeId(individual.get(i));
			 TspCity to;
			 if(i+1 < individual.size()){
				 to = allcities.getCitywithNodeId(individual.get(i+1));
			 }
			 else{
				 to = allcities.getCitywithNodeId(individual.get(0));
			 }
			 total += from.CalculateDistanceToCity(to);
			 
			 
		}
		total += allcities.getCitywithNodeId(individual.get(individual.size()-1)).CalculateDistanceToCity( allcities.getCitywithNodeId(individual.get(0)));
		TotalCost = total;
	}
	
	/**
	 * Get the totalCost of this individual
	 * @return double  the totalCost
	 */
	public double getTotalCost(){
		return this.TotalCost;
	}
	
	/**
	 * Calculate this fitness of this individual
	 */
	public void CalculateFitness(){
		this.Fitness = 1 / this.TotalCost;
	}
	
	/**
	 * Set the fitness of this individual
	 * @param fitness the fitness value to be set
	 */
	public void setFitness(double fitness){
		this.Fitness = fitness;
	}
	
	/**
	 * get the fitness of this individual
	 * @return double the fitness
	 */
	public double getFitness(){
		return this.Fitness;
	}


	/**
	 * Initialise this individual with randomise the sequence of city in the individual
	 */
	public void RandomiseIndividual()
	{
		Collections.shuffle(individual);
	}
	
	/**
	 * get Tspallcities
	 * @return allcities;
	 */
	public TspAllCities getAllCities(){
		return allcities;
	}

}
