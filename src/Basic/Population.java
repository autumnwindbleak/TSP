package Basic;


/**
 * This class is implemented for represent the population of TSP in EC
 * @author zhuoyingli
 *
 */
public class Population {
	
	/**
	 *  This argument is to store all individuals for a population
	 */
	private Individual[] individuals;
	
	
	/**
	 * A link to reference the data in individual to all real city data.
	 */
	private TspAllCities allcities;
	
//	private double[] Pfps;
	
	
	/**
	 * The total fitness of the current generation population
	 */
	private double totalFitness;

	
	
	/**
	 * Generate an empty population of the size of populationsize
	 * @param populationsize the size of population to be generated
	 */
	public Population(int populationsize){
		individuals = new Individual[populationsize];
	}

	/**
	 * Generate an population of the size of populationsize using data from TspAllCities
	 * @param populationsize  the size of population to be generated
	 * @param randomise whether to initialise the population
	 * @param Allcities the store of all city data
	 */
	public Population(int populationsize, boolean randomise, TspAllCities Allcities){
		individuals = new Individual[populationsize];
//		Pfps = new double[populationsize];
		totalFitness = 0.0;
		this.allcities = Allcities;
		if(randomise){
			for(int i = 0; i < populationsize; i++){
				Individual newIn = new Individual(this.allcities);
				newIn.RandomiseIndividual();
				individuals[i] = newIn;
			}
		}
		
	}
	
//	public void CalculatePfps(){
//		FitnessProportionalSelection fps = new FitnessProportionalSelection();
//		Pfps = fps.CalculatePfps(this);
//	}
//	
//	public double[] getPfps(){
//		return Pfps;
//	}

	/**
	 * Get the pointer to the store of all city data
	 * @return
	 */
	public TspAllCities getAllcities(){
		return this.allcities;
	}
	
	
	/**
	 * Evaluate the current population
	 */
	public void evaluate(){
		this.totalFitness = 0.0;
		for(int i = 0; i < individuals.length; i++){
			Individual current = individuals[i];
			current.CalculateTotalCost();
			current.CalculateFitness();
			totalFitness += current.getFitness();
		}
	}
	
	
	/**
	 * Retrieve the totalFitness of the population
	 * @return the totalFitness of the population
	 */
	public double gettotalFitness(){
		return this.totalFitness;
	}
	
	
	/**
	 * Calculate the Best Individual of the current population
	 * @return Individual the Best Individual
	 */
	public Individual getBestOne(){
		Individual best = individuals[0];
		for(int i = 1 ; i < individuals.length; i++  ){
			Individual nextOne = individuals[i];
			if(best.getFitness() < nextOne.getFitness()){
				best = nextOne;
			}
		}
		
		return best;
	}

	
	/**
	 * Get an individual by position Index
	 * @param Index position Index of an individual
	 * @return Individual the targeted individual
	 */
	public Individual getIndividualByIndex(int Index){
		return individuals[Index];
	}

	/**
	 * Add an individual to a certain position
	 * @param individual the individual to be added
	 * @param Index
	 */
	public void addIndividual(Individual individual, int Index){
		individuals[Index] = individual;
	}

	/**
	 * Remove an individual with a position index
	 * @param Index position index
	 */
	public void removeIndividualByIndex(int Index){
		individuals[Index] = null;
	}

	/**
	 * Get the size of the individual
	 * @return the size of the individual
	 */
	public int SizeOfPopulation(){
		return individuals.length;
	}

}
