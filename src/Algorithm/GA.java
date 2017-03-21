package Algorithm;

import java.util.Random;

import Basic.Individual;
import Basic.Population;
import Selection.Selection;
import VariationOperators.Mutation;
import VariationOperators.Recombination;


/**
 * Implement of the GA algorithm
 * @author Ian
 *
 */
public class GA {
	
	
//	/**
//	 * the chance of crossover
//	 */
//	private static double crossoverrate = 0.9;
	
	/**
	 * the chance of mutation
	 */
	private static double mutaterate = 0.015;
	/**
	 * the size of the tournament when select parents
	 */
	private static int tournamentsize = 5;

	/**
	 * 
	 * @param parents  parents population
	 * @param selectiontype 0 for FPS, 1 for tournament, 2 for random
	 * @param crossovertype 0 for cycle, 1 for PMS, 2 for order, 3 for edge 4 for random
	 * @param mutationtype 0 for swap, 1 for insert, 2 for inversion, 3 for random
	 * @return
	 */
	public static Population evolve(Population parents, int selectiontype, int crossovertype, int mutationtype){
		
		Population children = new Population(parents.SizeOfPopulation(),false,parents.getAllcities());
		Random ran = new Random();
		Individual[] selected_parents = new Individual[2];
		for(int i = 0; i < children.SizeOfPopulation(); i++){
			//selection
			if(selectiontype == 2){
				selectiontype = ran.nextInt(2);
			}
			switch(selectiontype){
			case 0:
				selected_parents = Selection.Roulette(parents, 2, false);
				break;
			case 1:
				selected_parents = Selection.tournamentSelection(parents,2,tournamentsize);
				break;
			}
			
			Individual parent1 = selected_parents[0];
			Individual parent2 = selected_parents[1];
			Individual child = new Individual();
			
			//cross over
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
			case 3:							//edge crossover
				child = Recombination.edgeCrossover(parent1,parent2);
				Individual tmp = Recombination.edgeCrossover(parent2,parent1);
				if(child.getFitness() <= tmp.getFitness()){
					child = tmp;
				}
				break;
			}
			
			//mutation
			if(ran.nextDouble() <= mutaterate){
				if(mutationtype == 3){
					mutationtype = ran.nextInt(3);
				}
				switch(mutationtype){
				case 0:							// swap mutation
					child = Mutation.Swap(child);
					break;
				case 1:							//insert mutation
					child = Mutation.Insert(child);
					break;
				case 2:							//inversion mutation
					child = Mutation.Inversion(child);
					break;
				}
			}
			children.addIndividual(child, i);
		}
		return children;
		
	}
	
	

}
