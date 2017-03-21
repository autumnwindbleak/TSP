import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Algorithm.Distribute;
import Algorithm.GA;
import Algorithm.GP;
import Basic.Population;
import Basic.TspAllCities;
import Basic.TspCity;



/**
 * 
 * @author Ian
 * 	main function
 */
public class tsp {
	
	/**
	 * the limit size of the population
	 */
	private int populationsize;
	/**
	 * the limit size of the generation
	 */
	private int generationsize;
	
	/**
	 * store the current Generation
	 */
	private Population Generation;
	
	
	/**
	 * constructor
	 * @param file input file address
	 */
	tsp(String file,int populationsize,int generationsize){
		this.populationsize = populationsize;
		this.generationsize = generationsize;
		TspAllCities cities = ReadFromFile(file);
		System.out.println("Population Size:\t" + populationsize + "\t Generation Size:\t" + generationsize);
		Generation = new Population(populationsize,true,cities);
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
				String line = input.nextLine();
				if(line.equals("EOF")){
					System.out.println("Read complete");
					break;
				}
				String[] tmp = line.split(" ");
				TspCity city = new TspCity(Integer.parseInt(tmp[0]),Double.parseDouble(tmp[1]),Double.parseDouble(tmp[2]));
				cities.addCity(city);
			}			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cities;
	}
	
	
	
	/**
	 * run GA algorithm
	 */
	public void runGA(){
		System.out.println("Running GA algorithm.");
		double start = System.currentTimeMillis();
		for(int i = 0; i < generationsize; i++){
			Generation = GA.evolve(Generation);
			if(i % 1000 == 0)
			System.out.println(i);
		}
		double end = System.currentTimeMillis();
		System.out.println("Time cost: " + (end - start) + "ms");
	}
	
	
	/**
	 * run GP algorithm
	 */
	public void runGP(){
		System.out.println("Running GP algorithm.");
		double start = System.currentTimeMillis();
		for(int i = 0; i < generationsize; i++){
			Generation = GP.evolve(Generation);
		}
		double end = System.currentTimeMillis();
		System.out.println("Time cost: " + (end - start) + "ms");
	}
	/**
	 * run distribute algorithm
	 */
	public void runDistribute(){
		System.out.println("Running Distribute algorithm.");
		double start = System.currentTimeMillis();
		for(int i = 0; i < generationsize; i++){
			Generation = Distribute.evolve(Generation);
		}
		double end = System.currentTimeMillis();
		System.out.println("Time cost: " + (end - start) + "ms");
	}
	
	
	
	/**
	 * output the best distance
	 */
	public void output(){
		System.out.println("Best distance is: " + Generation.getBestOne().getTotalCost());
	}
	

	
	/**
	 * main function
	 * @param args [population limit, generation limit, input file address] 
	 */
	public static void main(String[] args){
		int populationsize = Integer.parseInt(args[0]);
		int generationsize = Integer.parseInt(args[1]);
		String file = args[2];
		tsp instance = new tsp(file,populationsize,generationsize);
		instance.output();
//		instance.runGA();
//		instance.runGP();
		instance.runDistribute();
		instance.output();
	
	}
	
	

}
