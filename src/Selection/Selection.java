package Selection;
import java.util.ArrayList;
import java.util.Random;

import Basic.*;

/**
 * This class is implemented for the selection of EC as a module
 * @author zhuoying li
 *
 */
public class Selection {



	/**
	 * This method is designed for calculating probabilities of individuals using Fitness Proportional selection
	 * @param newpopulation a population to calculate fitness probabilities
	 * @return double[] Pfps all probabilities of individuals in a population
	 */
	public static double[] CalculateFitnessProportionalSelection(Population newpopulation){

		double totalFitness = 0.0;
		double[] Pfps = new double[newpopulation.SizeOfPopulation()];
		newpopulation.evaluate();
		totalFitness = newpopulation.gettotalFitness();
		for(int i = 0; i < Pfps.length ; i++){
			Pfps[i] = calculatePfpsForIndividual(newpopulation.getIndividualByIndex(i), totalFitness);
		}

		return Pfps;
	}

	/**
	 * This method is implemented for calculating Pfps For one Individual
	 * @param individual to be calculated the Pfps 
	 * @param totalFitness totalfitness of the whole individual
	 * @return Pfps the probability of being chosen to the individual
	 */
	private static double calculatePfpsForIndividual(Individual individual, double totalFitness) {
		return individual.getFitness() / totalFitness;
	}
	
	/**
	 * This method is implemented for calculating Pfps using the Windowing Formula
	 * @param newpopulation individual to be calculated  fitness probabilities
	 * @return double[] result all probabilities of individuals in a population
	 */
	public static double[] CalculateWindowing(Population newpopulation){
		double[] result =  new double[newpopulation.SizeOfPopulation()];
		double Bt = 1.0;

		//Bt <- the worst fitness of this generation
		for (int i = 0; i<newpopulation.SizeOfPopulation(); i++){
			Individual current = newpopulation.getIndividualByIndex(i);
			current.CalculateFitness();
			if(Bt > current.getFitness()){
				Bt = current.getFitness();
			}
		}

		for(int i = 0; i < newpopulation.SizeOfPopulation(); i++){
			double current = newpopulation.getIndividualByIndex(i).getFitness() - (Bt);
			result[i] = current;

		}
		return result;
	}
	
	/**
	 * This method is implemented as the Roulette Wheel Selection 
	 * @param propabilities the weights of individuals for selection 
	 * @param KnumbersTobeSelected K parents to be selected
	 * @param Elitism whether to keep the best individual
	 * @return SelectedIndex the Index of selected Individuals
	 */
	public static int[] Roulette(double[] propabilities, int KnumbersTobeSelected, boolean Elitism){
		int[] SelectedIndex = new int[KnumbersTobeSelected];
		Random generator = new Random();
		for(int k = 0; k < KnumbersTobeSelected; k++){

			double rnd = generator.nextDouble();
			int found = -1;
			int i = 0;
			while (i < propabilities.length){
				if(propabilities[i] < rnd) {
					found = i;
					break;
				}
				i++;
				if(i == propabilities.length && found == -1) {
					i = 0;
					rnd = generator.nextDouble();
				}
			}
			if(found != -1){
				SelectedIndex[k] = found;
			}
		}
		
		return SelectedIndex;
		
	}
	
	
	/**
	 * This method is implemented for calculating Pfps using the Sigma Scaling Formula
	 * @param newpopulation individual to be calculated  fitness probabilities
	 * @return double[] result all probabilities of individuals in a population
	 */
	public static double[] CalculateSigmaScaling(Population newpopulation){
		double[] result =  new double[newpopulation.SizeOfPopulation()];
		double sum = 0.0;
		double newsum = 0.0;
		double new_data[] = new double[newpopulation.SizeOfPopulation()];
		for (int i = 0; i<newpopulation.SizeOfPopulation(); i++){
			Individual current = newpopulation.getIndividualByIndex(i);
			current.CalculateFitness();
			sum += current.getFitness();
		}

		double meanFitness = sum / newpopulation.SizeOfPopulation();

		for (int i = 0; i<newpopulation.SizeOfPopulation(); i++){
			Individual current = newpopulation.getIndividualByIndex(i);
			new_data[i] =  Math.pow(current.getFitness() - meanFitness, 2);
			newsum += new_data[i]; 
		}

		double sdm = newsum / newpopulation.SizeOfPopulation();
		double stddev = Math.sqrt(sdm);
		int c = 2;

		for(int i = 0; i < newpopulation.SizeOfPopulation(); i++){
			double current = newpopulation.getIndividualByIndex(i).getFitness() - (meanFitness - c * stddev);
			if(current > 0){
				result[i] = current;
			}
			else{
				result[i] = 0;
			}
		}

		return result; 
	}

	/**
	 * This method is implemented to support tournament selection. Choosing the best one from a set of K individuals that selected randomly.
	 * @param population the current population
	 * @param K the number of individual to be chosen randomly
	 * @return Individual the best one of the K randomly selected individual 
	 */
	public static Individual tournament(Population population, int K){

		Population tournament = new Population(K);

		for (int i = 0; i < K; i++) {
			int rnd = (int)(population.SizeOfPopulation() * Math.random());
			tournament.addIndividual( population.getIndividualByIndex(rnd), i);
		}
		
		Individual best = tournament.getBestOne();
		

		return best;
	}
	
	
	/**
	 * This method is implemented for the tournament selection
	 * @param population the current generation of population
	 * @param lambda the number of matings to be selected
	 * @param tournamentK the number of random selected parents  
	 * @return Individual[] result the result of tournament selection
	 */
	public static Individual[] tournamentSelection(Population population, int lambda, int tournamentK){
		Individual[] result ;
		ArrayList<Individual> temp = new ArrayList<>();
		
		int lambdaIndex = 0;
//		if(Elitism){
//			temp.add(population.getBestOne());
//			lambdaIndex ++;
//		}
		for( int i = lambdaIndex; i < lambda; i++){
			temp.add(tournament(population, tournamentK)) ;
		}
		result = temp.toArray(new Individual[temp.size()]);
		return result;
	}
	
	public static Individual[] Elitism(Population population, int K){
		Individual[] result = new Individual[K];
		Population tempPop = new Population(population.SizeOfPopulation());
		for(int i = 0;i < population.SizeOfPopulation();i++){
			tempPop.addIndividual(population.getIndividualByIndex(i), i);
		}
		for(int j = 0; j < K ; ++j){
			
			Individual tempI = tempPop.getBestOne();
			int bestIndex = -1;
			for(int o = 0 ; o < tempPop.SizeOfPopulation() ; o++){
				if(tempI.equals(tempPop.getIndividualByIndex(o))){
					bestIndex = o;
				}
			}
			
			result[j] = tempI;
			Population newPop = new Population(tempPop.SizeOfPopulation()-1);
			int index = 0;
			for(int i = 0;i < tempPop.SizeOfPopulation();i++){
				Individual current = tempPop.getIndividualByIndex(i);
				if(i != bestIndex){
					newPop.addIndividual(current, index);
					index ++;
				}
			}
			tempPop = newPop;
			
		}

		
		
		return result;
	}


}
