import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Basic.Population;
import Basic.TspAllCities;
import Basic.TspCity;

public class tsp {
	
	private int population;
	private int generation;
	
	private Population firstGeneration;
	
	
	/**
	 * constructor
	 * @param file input file address
	 */
	tsp(String file){
		TspAllCities cities = ReadFromFile(file);
		firstGeneration = new Population(population,true,cities);
	}
	
	/**
	 * 
	 * Input file standard: first 6 lines for description (no use), 
	 * then each line have 3 numbers describe one city as [node id, x location, y location] separate with a single space,
	 * and then end with EOF.
	 * node id, x&y location are all integer.
	 * 
	 * @param file input file address
	 * @return TspAllCities: a map of all cities.
	 */
	private TspAllCities ReadFromFile(String file){
		TspAllCities cities = new TspAllCities();
		try {
			Scanner input = new Scanner(new File(file));
			for(int i = 0; i < 6; i++){
				if(!input.hasNextLine()){
					System.out.println("Cannot read input file! System exit now!");
					System.exit(0);
				}
				input.nextLine();
			}
			while(input.hasNextLine()){
				if(input.nextLine().equals("EOF")){
					System.out.println("Read complete");
					break;
				}
				String[] tmp = input.nextLine().split(" ");
				TspCity city = new TspCity(Integer.parseInt(tmp[0]),Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]));
				cities.addCity(city);
			}			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cities;
	}
	
	
	
	/**
	 * set population limit
	 * @param population
	 */
	public void setPopulation(int population){
		this.population = population;
	}
	
	/**
	 * set generation limit
	 * @param generation
	 */
	public void setGeneration(int generation){
		this.generation = generation;
	}
	
	

	
	/**
	 * main function
	 * @param args [population limit, generation limit, input file address] 
	 */
	public static void main(String[] args){
		int population = Integer.parseInt(args[0]);
		int generation = Integer.parseInt(args[1]);
		String file = args[2];
		tsp instance = new tsp(file);
		instance.setPopulation(population);
		instance.setGeneration(generation);
		
	}
	
	

}
