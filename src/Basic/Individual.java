package Basic;

import java.util.ArrayList;
import java.util.Collections;

public class Individual {
	private ArrayList<Integer> individual;
	
	private TspAllCities allcities;
	
	private double TotalCost;
	
	private double Fitness;

	public Individual(TspAllCities allcities){
		this.allcities = allcities;
		individual = this.allcities.getAllCityNodeId();
		TotalCost = -1;
		Fitness = -1;
		
	}
	
	public Individual(){
		individual = new ArrayList<>();
	}

	
	public void setCitywithSequenceIndex(int NodeId){
		individual.add(NodeId);
		//reset fitness and totalcost due to new city added
        Fitness = -1;
        TotalCost = -1;
	}

	public boolean EvaluateSizeofSolution(){
		return individual.size() == allcities.getNumberOfCities();
	}

	public ArrayList<Integer> getIndividuals(){
		return this.individual;
	}
	
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
		TotalCost = total;
	}
	
	public double getTotalCost(){
		return this.TotalCost;
	}
	
	public void CalculateFitness(){
		this.Fitness = 1 / this.TotalCost;
	}
	
	public void setFitness(double fitness){
		this.Fitness = fitness;
	}
	
	public double getFitness(){
		return this.Fitness;
	}


	public void RandomiseIndividual()
	{
		Collections.shuffle(individual);
	}

}
