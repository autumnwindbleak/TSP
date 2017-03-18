package Selection;
import java.util.ArrayList;
import java.util.Random;

import Basic.*;


public class Selection {


	public Selection(){

	}



	public double[] CalculateFitnessProportionalSelection(Population newpopulation){

		double totalFitness = 0.0;
		double[] Pfps = new double[newpopulation.SizeOfPopulation()];
		newpopulation.evaluate();
		totalFitness = newpopulation.gettotalFitness();
		for(int i = 0; i < Pfps.length ; i++){
			Pfps[i] = calculatePfpsForIndividual(newpopulation.getIndividualByIndex(i), totalFitness);
		}
		return Pfps;
	}

	private double calculatePfpsForIndividual(Individual individual, double totalFitness) {
		return individual.getFitness() / totalFitness;
	}

	
	
	
	
	
//	public double[] CalculateWindowing(Population newpopulation){
//		double[] result =  new double[newpopulation.SizeOfPopulation()];
//		double Bt = 1.0;
//
//		//Bt <- the worst fitness of this generation
//		for (int i = 0; i<newpopulation.SizeOfPopulation(); i++){
//			Individual current = newpopulation.getIndividualByIndex(i);
//			current.CalculateFitness();
//			if(Bt > current.getFitness()){
//				Bt = current.getFitness();
//			}
//		}
//
//		for(int i = 0; i < newpopulation.SizeOfPopulation(); i++){
//			double current = newpopulation.getIndividualByIndex(i).getFitness() - (Bt);
//			result[i] = current;
//
//		}
//		return result;
//	}
//	
//	
//	
//	public int[] Roulette(double[] propabilities, int KnumbersTobeSelected, boolean Elitism){
//		int[] SelectedIndex = new int[KnumbersTobeSelected];
//		Random generator = new Random();
//		for(int k = 0; k < KnumbersTobeSelected; k++){
//
//			double rnd = generator.nextDouble();
//			int found = -1;
//			int i = 0;
//			while (i < propabilities.length){
//				if(propabilities[i] < rnd) {
//					found = i;
//					break;
//				}
//				i++;
//				if(i == propabilities.length && found == -1) {
//					i = 0;
//					rnd = generator.nextDouble();
//				}
//			}
//			if(found != -1){
//				SelectedIndex[k] = found;
//			}
//		}
//		
//		return SelectedIndex;
//		
//	}
//
//	public double[] CalculateSigmaScaling(Population newpopulation){
//		double[] result =  new double[newpopulation.SizeOfPopulation()];
//		double sum = 0.0;
//		double newsum = 0.0;
//		double new_data[] = new double[newpopulation.SizeOfPopulation()];
//		for (int i = 0; i<newpopulation.SizeOfPopulation(); i++){
//			Individual current = newpopulation.getIndividualByIndex(i);
//			current.CalculateFitness();
//			sum += current.getFitness();
//		}
//
//		double meanFitness = sum / newpopulation.SizeOfPopulation();
//
//		for (int i = 0; i<newpopulation.SizeOfPopulation(); i++){
//			Individual current = newpopulation.getIndividualByIndex(i);
//			new_data[i] =  Math.pow(current.getFitness() - meanFitness, 2);
//			newsum += new_data[i]; 
//		}
//
//		double sdm = newsum / newpopulation.SizeOfPopulation();
//		double stddev = Math.sqrt(sdm);
//		int c = 2;
//
//		for(int i = 0; i < newpopulation.SizeOfPopulation(); i++){
//			double current = newpopulation.getIndividualByIndex(i).getFitness() - (meanFitness - c * stddev);
//			if(current > 0){
//				result[i] = current;
//			}
//			else{
//				result[i] = 0;
//			}
//		}
//
//		return result; 
//	}

	private Individual tournament(Population population, int K){

		Population tournament = new Population(K);

		for (int i = 0; i < K; i++) {
			int rnd = (int)(population.SizeOfPopulation() * Math.random());
			tournament.addIndividual( population.getIndividualByIndex(rnd), i);
		}
		
		Individual best = tournament.getBestOne();
		

		return best;
	}
	
	public Individual[] tournamentSelection(Population population, int lambda, int tournamentK){
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


}
