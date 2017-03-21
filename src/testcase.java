import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Basic.Individual;
import Basic.TspAllCities;
import Basic.TspCity;

public class testcase {
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
				TspCity city = new TspCity(Integer.parseInt(tmp[0]),Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]));
				cities.addCity(city);
			}			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cities;
	}
	
	
	private ArrayList<Integer> tour(String file){
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
					System.out.println("Read complete");
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
	
	public static void main(String[] args){
//		testcase t = new testcase();
//		TspAllCities allcities = t.ReadFromFile(args[0]);
//		Individual individual = new Individual(t.tour(args[1]),allcities);
//		System.out.println(individual.getTotalCost());
		String s = "4.00000e+02";
//		int n = Integer.parseInt(s);
		double d = Double.parseDouble(s);
		
		System.out.println(d);
	}

}
