import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
public class script {
	
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
	script(String file,int populationsize,int generationsize){
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
	public void runGA(){
		for(int i = 0; i < generationsize; i++){
			Generation = GA.evolve(Generation,1,3,3);
		}
	}
	
	
	/**
	 * run GP algorithm
	 */
	public void runGP(){
		for(int i = 0; i < generationsize; i++){
			Generation = GP.evolve(Generation);
		}
	}
	/**
	 * run distribute algorithm
	 */
	public void runDistribute(){
		for(int i = 0; i < generationsize; i++){
			Generation = Distribute.evolve(Generation);
		}
	}
	
	
	
	/**
	 * output the best distance
	 */
	public void output(){
		System.out.println("Best distance is: " + Generation.getBestOne().getTotalCost());
	}
	
	
	public static String[] getfiles(String foldername){
		File folder = new File(foldername);
		File[] files = folder.listFiles();
		String[] result = new String[files.length];
		for(int i = 0; i <result.length; i++){
			result[i] = files.toString();
		}
		return result;
	}
	
	

	

	public static void main(String[] args){
		int[] populationsize = {20,50,100,200};
		int[] generationsize = {2000,5000,10000,20000};
		File folder = new File(args[0]);
		File[] files = folder.listFiles();
		
		
		
		
		
		String output = "./output/output.txt";
		File outputfile = new File(output);
		try {
			outputfile.createNewFile();
			
			BufferedWriter out = new BufferedWriter(new FileWriter(outputfile));  
			
			double start = 0; 
			double end = 0;
			double cost = 0;
			
			
			for(int i = 0; i < files.length; i++){
				out.write("File Name: \t" + files[i].getName() + "\n");
				System.out.println("file:\t" + files[i].getName() );
				out.write("Algorithm \t Populationsize \t Generationsize \t TimeCost(ms) \t Distance \n");
				for(int j = 0; j < populationsize.length; j++){
					System.out.println("populationsize: \t" + populationsize[j]);
					for(int k = 0; k < generationsize.length; k++){
						System.out.println("Generationsize: \t " + generationsize[k]);
						for(int x = 0; x < 3; x++){
							script instance = new script(files[i].toString(),populationsize[j],generationsize[k]);
							switch(x){
							case 0:
								System.out.println("GA");
								start = System.currentTimeMillis();
								instance.runGA();
								end = System.currentTimeMillis();
								cost = end - start;
								out.write("GA \t" + populationsize[j] + " \t " + generationsize[k] +" \t " + cost + " \t " + instance.Generation.getBestOne().getTotalCost()+ "\n");		
								out.flush();
								break;
							case 1:
								System.out.println("GP");
								start = System.currentTimeMillis();
								instance.runGP();
								end = System.currentTimeMillis();
								cost = end - start;
								out.write("GP \t" + populationsize[j] + " \t " + generationsize[k] +" \t " + cost + " \t " + instance.Generation.getBestOne().getTotalCost()+ "\n");		
								out.flush();
								break;
							case 2:
								System.out.println("DS");
								start = System.currentTimeMillis();
								instance.runDistribute();
								end = System.currentTimeMillis();
								cost = end - start;
								out.write("DS \t" + populationsize[j] + " \t " + generationsize[k] +" \t " + cost + " \t " + instance.Generation.getBestOne().getTotalCost()+ "\n");	
								out.flush();
								break;
							}
						}
					}
				}
			}
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
