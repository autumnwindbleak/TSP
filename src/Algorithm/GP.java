package Algorithm;

import java.util.Random;

import Basic.Individual;
import Basic.Population;
import Selection.Selection;
import VariationOperators.Mutation;
import VariationOperators.Recombination;

/**
 * Implement of the GP Algorithm
 * @author Ian
 *
 */
public class GP {
	
	/**
	 * the chance of crossover
	 */
	private static double crossoverrate = 0.9;
	
	/**
	 * the chance of mutation
	 */
	private static double mutaterate = 0.015;
	/**
	 * the size of the tournament when select parents
	 */
	private static int tournamentsize = 5;
	/**
	 * the type of crossover
	 * 0 for cycle
	 * 1 for PMX
	 * 2 for order
	 * 3 for random
	 */
	private static int crossovertype = 3;
	/**
	 * the type of mutation
	 * 0 for swap
	 * 1 for insert
	 * 2 for inversion
	 * 3 for random
	 */
	private static int mutationtype = 3;
	
	
	public static Population evolve(Population parents){
		Population children = new Population(parents.SizeOfPopulation());
		Random ran = new Random();
		
		for(int i = 0; i < children.SizeOfPopulation();){
			Individual[] selected_parents = Selection.tournamentSelection(parents,3,5);
			Individual parent1 = selected_parents[0];
			Individual parent2 = selected_parents[1];
			Individual parent3 = selected_parents[2];
			Individual child = new Individual();
			
			//cross over
			if(ran.nextDouble() <= crossoverrate){
				if(crossovertype == 3){
					crossovertype = ran.nextInt(3);
				}
				switch(crossovertype){
				case 0:							// cycle crossover
					child = Recombination.cycleCrossover(parent1,parent2);
					break;
				case 1:							//PMX
					child = Recombination.PMX(parent1,parent2);
					break;
				case 2:							//order crossover
					child = Recombination.orderCrossover(parent1,parent2);
					break;
//					
//					
//					
//					this part have some problems
//					
//					
//					
//					
//				case 3:							//edge crossover
//					child = Recombination.edgeCrossover(parent1,parent2);
//					Individual tmp = Recombination.edgeCrossover(parent2,parent1);
//					if(child.getFitness() <= tmp.getFitness()){
//						child = tmp;
//					}
//					break;
				}
			}else{
				child =parent1;
			}
			children.addIndividual(child, i);
			i++;
			if(i >= children.SizeOfPopulation()){
				break;
			}
			
			//mutation
			if(ran.nextDouble() <= mutaterate){
				if(mutationtype == 3){
					mutationtype = ran.nextInt(3);
				}
				switch(mutationtype){
				case 0:							// swap mutation
					child = Mutation.Swap(parent3);
					break;
				case 1:							//insert mutation
					child = Mutation.Insert(parent3);
					break;
				case 2:							//inversion mutation
					child = Mutation.Inversion(parent3);
					break;
				}
			}else{
				child = parent3;
			}
			children.addIndividual(child, i);
		}
		return children;	
	}

}
