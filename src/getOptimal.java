import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Basic.Individual;
import Basic.TspAllCities;
import Basic.TspCity;
/**
 * using optimal tour to get the optimal cost
 * @author ian
 *
 */
public class getOptimal {
	
	
	/**
	 * get all cities information from files
	 * @param f file path
	 * @return TspAllcities 
	 */
	public static TspAllCities ReadFromFile(File f){
		TspAllCities cities = new TspAllCities();
		try {
			Scanner input = new Scanner(f);
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
	 * get the optimal tour to create the optimal individual 
	 * @param file file path
	 * @return ArrayList<Integer> the node id in order
	 */
	public static ArrayList<Integer> tour(String file){
		ArrayList<Integer> result = new ArrayList<Integer>();
		try {
			Scanner input = new Scanner(new File(file));
			for(int i = 0; i < 5; i++){
				if(!input.hasNextLine()){
					System.out.println("Cannot read input file! System exit now!");
					System.exit(0);
				}
				input.nextLine();
			}
			while(input.hasNextLine()){
				String line = input.nextLine();
				if(line.equals("-1")){
//					System.out.println("Read complete");
					break;
				}
				result.add(Integer.parseInt(line));
			}			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * main function
	 * @param args
	 */
	public static void main(String[] args){
		File folder = new File("./input/"); 
		File[] files = folder.listFiles();
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("./optimal/optimal.txt"));
			out.write("File name: \t Optimal Distance: \n");
			for(int i = 0; i < files.length; i ++){
				String o = "./optimal/" + files[i].getName().split("\\.")[0] + ".opt.tour";
				TspAllCities allcities = ReadFromFile(files[i]);
				Individual individual = new Individual(tour(o),allcities);
				double best = individual.getTotalCost();
				out.write(files[i].getName() + "\t" + best + "\n");
				out.flush();
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		System.out.print("Finished.");
		
	}

}
