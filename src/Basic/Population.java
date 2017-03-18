package Basic;



public class Population {
	private Individual[] individuals;
	
	private TspAllCities allcities;
	
//	private double[] Pfps;
	
	private double totalFitness;

	
	public Population(int populationsize){
		individuals = new Individual[populationsize];
	}
	
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

	
	public TspAllCities getAllcities(){
		return this.allcities;
	}
	
	public void evaluate(){
		this.totalFitness = 0.0;
		for(int i = 0; i < individuals.length; i++){
			Individual current = individuals[i];
			current.CalculateTotalCost();
			current.CalculateFitness();
			totalFitness += current.getFitness();
		}
	}
	
	public double gettotalFitness(){
		return this.totalFitness;
	}
	
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

	public Individual getIndividualByIndex(int Index){
		return individuals[Index];
	}

	public void addIndividual(Individual individual, int Index){
		individuals[Index] = individual;
	}

	public void removeIndividualByIndex(int Index){
		individuals[Index] = null;
	}

	public int SizeOfPopulation(){
		return individuals.length;
	}





}
