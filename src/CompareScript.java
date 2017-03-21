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
public class CompareScript {
	
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
	CompareScript(String file,int populationsize,int generationsize){
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
		
		
		String output = "./output/output.txt";
		File outputfile = new File(output);
		try {
			outputfile.createNewFile();
			
			BufferedWriter out = new BufferedWriter(new FileWriter(outputfile));  
			
			double start = 0; 
			double end = 0;
			double cost = 0;
			double distance = 0;
			
			double bestd = Double.MAX_VALUE;
			double bestt = Double.MAX_VALUE;
			double sd;
			
			for(int i = 0; i < files.length; i++){
				out.write("File Name: \t" + files[i].getName() + "\n");
				System.out.println("file:\t" + files[i].getName() );
				out.write("CrossOver \t Muation \t Populationsize \t Generationsize \t TimeCost(ms) \t Distance \t Standard Deviation \n");
				for(int j = 0; j < populationsize.length; j++){
//					System.out.println("populationsize: \t" + populationsize[j]);
					for(int k = 0; k < generationsize.length; k++){
//						System.out.println("Generationsize: \t " + generationsize[k]);
						
						CompareScript instance = new CompareScript(files[i].toString(),populationsize[j],generationsize[k]);
						for(int ctype = 0; ctype < 4; ctype ++){
							for(int mtype = 0; mtype < 3; mtype ++){
								bestd = Double.MAX_VALUE;
								bestt = Double.MAX_VALUE;
								for(int time = 0; time <5; time ++){
									start = System.currentTimeMillis();
									instance.runGA(ctype,mtype);
									end = System.currentTimeMillis();
									cost = end - start;
									distance = instance.Generation.getBestOne().getTotalCost();
									if(distance < bestd){
										bestd = distance;
										bestt = cost;
									}
								}
								sd = bestd/optimal.get(files[i].getName());
								out.write(ctype + "\t " + mtype + " \t " + populationsize[j] + " \t " + generationsize[k] +" \t " + bestt + " \t " + bestd + " \t " + sd + "\n");		
								System.out.print(ctype + "\t " + mtype + " \t " + populationsize[j] + " \t " + generationsize[k] +" \t " + bestt + " \t " + bestd + " \t " + sd + "\n");
								out.flush();
								
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
