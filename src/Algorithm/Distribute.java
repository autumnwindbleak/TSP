package Algorithm;

import java.util.Random;

import Basic.Individual;
import Basic.Population;
import Selection.Selection;

/**
 * Implement of the Distribute Algorithm
 * @author Ian
 *
 */
public class Distribute {
	
	private static double elitismrate = 0.01;
	
	private static int number_of_subparents = 10;
	
	
	public static Population evolve(Population parents){
		int size_of_population = parents.SizeOfPopulation();
		int number_of_elitism = (int) Math.round(size_of_population * elitismrate);
		Population children = new Population(parents.SizeOfPopulation());
		
		int index = 0;
		
		int size_of_subparents = (size_of_population - number_of_elitism)/number_of_subparents;
		
		if((size_of_population - number_of_elitism) % number_of_subparents != 0){
			size_of_subparents++;
		}
		Random ran = new Random();
		
		while(index < size_of_population - number_of_elitism){
			Population subparents = subParents(parents,size_of_subparents);
			Population subchildren = new Population(size_of_subparents);
			if(ran.nextInt(2) == 0){
				subchildren = GA.evolve(subparents,1,3,3);
			}
			else
			{
				subchildren = GP.evolve(subparents);
			}
			for(int i = 0; i < size_of_subparents; i++){
				children.addIndividual(subchildren.getIndividualByIndex(i), index);
				index++;
				if(index >= size_of_population - number_of_elitism){
					break;
				}
			}
		}
		
		Individual[] elitism = Selection.Elitism(parents,number_of_elitism);
		for(int i = 0; i < elitism.length; i++){
			children.addIndividual(elitism[i], index);
			index++;
		}
		
		return children;
	}
	
	public static Population subParents(Population origion, int size){
		Random ran = new Random();
		int origionsize = origion.SizeOfPopulation();
		Population subparents = new Population(size);
		for(int i = 0; i < size; i++){
			subparents.addIndividual(origion.getIndividualByIndex(ran.nextInt(origionsize)), i);
		}
		return subparents;
	}

}
