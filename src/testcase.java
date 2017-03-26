import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
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
public class testcase {
	
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
	testcase(String file,int populationsize,int generationsize){
		this.populationsize = populationsize;
		this.generationsize = generationsize;
		TspAllCities cities = ReadFromFile(file);
//		System.out.println("Population Size:\t" + populationsize + "\t Generation Size:\t" + generationsize);
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
//					System.out.println("Read complete");
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
	public void runGA(int ctype,int mtype){
		for(int i = 0; i < generationsize; i++){
			Generation = GA.evolve(Generation,1,ctype,mtype);
		}
	}
	


	/**
	 * main function 
	 * @param args: [pupulationsize] [generationsize] [crossover type] [mutation type] [running times] [input folder path]
	 */
	public static void main(String[] args){
		int populationsize = Integer.parseInt(args[0]);
		int generationsize = Integer.parseInt(args[1]);
		int ctype = Integer.parseInt(args[2]);
		int mtype = Integer.parseInt(args[3]);
		int number_of_times = Integer.parseInt(args[4]);
		File folder = new File(args[5]);
		File[] files = folder.listFiles();
		
		//get optimal distance
		File optimalfile = new File("./optimal/optimal.txt");
		HashMap<String,Double> optimal = new HashMap<String,Double>();
		try {
			Scanner input = new Scanner(optimalfile);
			String line = input.nextLine();
			String name = "";
			double distance = 0;
			while(input.hasNextLine()){
				line = input.nextLine();
				name = line.split("\t")[0];
				distance = Double.parseDouble(line.split("\t")[1]);
				optimal.put(name, distance);
			}
			
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String output = "./output/testoutput.txt";
		File outputfile = new File(output);
		try {
			outputfile.createNewFile();
			
			BufferedWriter out = new BufferedWriter(new FileWriter(outputfile));  
			
			double start = 0; 
			double end = 0;
			double cost = 0;
			double distance = 0;
			
		
			double sd;
			
			for(int i = 0; i < files.length; i++){ 							//for each file
				out.write("File Name: \t" + files[i].getName() + "\n");
				System.out.println("file:\t" + files[i].getName() );
				out.write("CrossOver \t Muation \t Populationsize \t Generationsize \t TimeCost(ms) \t Distance \t Standard Deviation \n");				
				for(int time = 0; time <number_of_times; time ++){ 			//running for several times
					testcase instance = new testcase(files[i].toString(),populationsize,generationsize);
					start = System.currentTimeMillis();
					instance.runGA(ctype,mtype);
					end = System.currentTimeMillis();
					cost = end - start;
					distance = instance.Generation.getBestOne().getTotalCost();
					sd = distance/optimal.get(files[i].getName());
					out.write(ctype + "\t " + mtype + " \t " + populationsize + " \t " + generationsize +" \t " + cost + " \t " + distance + " \t " + sd + "\n");		
					System.out.print(ctype + "\t " + mtype + " \t " + populationsize + " \t " + generationsize +" \t " + cost + " \t " + distance + " \t " + sd + "\n");
					out.flush();
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
