package VariationOperators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Basic.Individual;
import Basic.Population;
import Basic.TspAllCities;

/**
 * 
 * @author PuzhiYAO
 * This class contains several crossover operators which include Order Crossover,
 * PMX Crossover, Cycle Crossover and Edge Recombination for permutation representation.
 */
public class Recombination {

//	public static void main(String[] args) {
//		// testing script
//		Individual p1 = new Individual();
//		Individual p2 = new Individual();
//		TspAllCities ta = new TspAllCities();
//		p1.getIndividuals().add(1);
//		p1.getIndividuals().add(2);
//		p1.getIndividuals().add(3);
//		p1.getIndividuals().add(4);
//		p1.getIndividuals().add(5);
//		p1.getIndividuals().add(6);
//		p1.getIndividuals().add(7);
//		p1.getIndividuals().add(8);
//		p1.getIndividuals().add(9);
//
//		p2.getIndividuals().add(9);
//		p2.getIndividuals().add(3);
//		p2.getIndividuals().add(7);
//		p2.getIndividuals().add(8);
//		p2.getIndividuals().add(2);
//		p2.getIndividuals().add(6);
//		p2.getIndividuals().add(5);
//		p2.getIndividuals().add(1);
//		p2.getIndividuals().add(4);
//
//		System.out.println(PMX(p1,p2).toString());
//		System.out.println(orderCrossover(p1,p2).toString());
//		System.out.println(edgeCrossover(p1,p2).toString());
//		System.out.println(cycleCrossover(p1,p2).toString());
//	}

	/**
	 * Cycle Crossover
	 * This method will use p2 to generate offspring of p1,
	 * Therefore, it has to be called twice in order to general both 
	 * offsprings of p1 and p2.
	 * Example:
	 * Child_of_p1 = cycleCrossover(parent1, parent2)
	 * Child_of_p2 = cycleCrossover(parent2, parent1)
	 * @param p1
	 * @param p2
	 * @return Individual newchild
	 */
	public static Individual cycleCrossover(Individual p1, Individual p2) {
		int size = p1.getIndividuals().size();

		// check if input is null
		if(size == 0) {
			return null;
		}

		// initialization
		int[] childArray = new int[size];
		for(int i = 0; i < size; ++i) {
			childArray[i] = -1;
		}

		// Start with the first unused position and allele of P1
		int iniIndex = 0;
		int originValue = p1.getIndividuals().get(iniIndex);
		int p2Value = -1;

		// Step 2: Look at the allele in the same position in P2
		// Step 3: Go to the position with the same allele in P1
		// Step 4: Add this allele to the cycle
		// Step 5: Repeat steps 2 through 4 until you arrive at the first allele of P1
		while( size!= 0 ) {
			childArray[iniIndex] = p1.getIndividuals().get(iniIndex);
			p2Value = p2.getIndividuals().get(iniIndex);

			for(int i = 0; i < size; ++i) {
				if(p1.getIndividuals().get(i) == p2Value) {
					iniIndex = i;
					break;
				}
			}

			if(p1.getIndividuals().get(iniIndex) == originValue) {
				break;
			}
		}

		// Step 6: copy remaining value from p2 to child
		for(int i = 0; i < size; ++i) {
			if(childArray[i] == -1) {
				childArray[i] = p2.getIndividuals().get(i);
			}
		}

		// Final step: copy result value to arrayList
		ArrayList<Integer> childArrayList = new ArrayList<Integer>();
		for(int i = 0; i < size; ++i) {
			childArrayList.add(childArray[i]);
		}
		
		return  new Individual(childArrayList, p1.getAllCities());
	}

	/**
	 * Edge crossover
	 * Note that only one child per recombination is created by this operator.
	 * @param p1
	 * @param p2
	 * @return Individual newchild
	 */
	public static Individual edgeCrossover(Individual p1, Individual p2) {
		// setup 
		int size = p1.getIndividuals().size();
		ArrayList<Integer> childArrayList = new ArrayList<Integer>();
		Individual child = new Individual();

		// step 1: Construct edge table
		HashMap<Integer, ArrayList<Integer>> edgeTable = new  HashMap<Integer, ArrayList<Integer>>();

		for(int i = 0; i < size; ++i) {
			if(i == 0) {
				ArrayList<Integer> edges = new ArrayList<Integer>();
				edges.add(p1.getIndividuals().get(i+1));
				edgeTable.put(p1.getIndividuals().get(i), edges);
			}
			else if( i == size - 1) {
				ArrayList<Integer> edges = new ArrayList<Integer>();
				edges.add(p1.getIndividuals().get(i-1));
				edgeTable.put(p1.getIndividuals().get(i), edges);
			}
			else {
				ArrayList<Integer> edges = new ArrayList<Integer>();
				edges.add(p1.getIndividuals().get(i+1));
				edges.add(p1.getIndividuals().get(i-1));
				edgeTable.put(p1.getIndividuals().get(i), edges);
			}
		}

		for(int i = 0; i < size; ++i) {
			if(i == 0) {
				edgeTable.get(p2.getIndividuals().get(i)).add(p2.getIndividuals().get(i+1));
			}
			else if(i == size - 1) {
				edgeTable.get(p2.getIndividuals().get(i)).add(p2.getIndividuals().get(i-1));
			}
			else {
				edgeTable.get(p2.getIndividuals().get(i)).add(p2.getIndividuals().get(i+1));
				edgeTable.get(p2.getIndividuals().get(i)).add(p2.getIndividuals().get(i-1));
			}
		}

		// step 2: Pick an initial element at random and put it in the offspring
		int[] pos = randPos(2);
		int entryElement = 0;
		if(pos[0] == 0) {
			entryElement = p1.getIndividuals().get(0);
		}
		else {
			entryElement = p2.getIndividuals().get(0);
		}

		// step 3:
		while(childArrayList.size() < size) {
			childArrayList.add(entryElement);
			if(childArrayList.size() >= size) {
				break;
			}
			removeRef(edgeTable, entryElement);

			int newEntry = -1;
			// In the case of reaching an empty list
			if(edgeTable.get(entryElement).size() != 0) {
				// If there is a common edge, pick that to be the next element
				int comEdge = commonEdge(edgeTable.get(entryElement));
				if( comEdge != -1) {
					newEntry = comEdge;
				}
				else {
					// Otherwise pick the entry in the list which itself has the shortest list
					int tmp = edgeTable.get(entryElement).get(0);
					int min = edgeTable.get(tmp).size();
					newEntry = edgeTable.get(entryElement).get(0);
					for(int i = 0; i < edgeTable.get(entryElement).size(); ++i) {
						int tmpMin = edgeTable.get(edgeTable.get(entryElement).get(i)).size();
						if(tmpMin < min) {
							newEntry = edgeTable.get(entryElement).get(i);
							min = tmpMin;
						}
					}
				}
			}
			else {
				// otherwise a new element is chosen at random
				int[] tmpPos = randPos(edgeTable.size());
				List<Integer> keys = new ArrayList<Integer>(edgeTable.keySet());

				int[] childArray = new int[childArrayList.size()];
				for(int i = 0; i < childArrayList.size(); ++i) {
					childArray[i] = childArrayList.get(i);
				}

				while(searchAarry(childArray, keys.get(tmpPos[0]))) {
					tmpPos = randPos(edgeTable.size());
				}

				newEntry = keys.get(tmpPos[0]);
			}
			entryElement = newEntry;
		}

		// Final step: copy Array value to child
		// for(int i = 0; i < size; ++i) {
		//	child.getIndividuals().add(childArrayList.get(i));
		// }

		return  new Individual(childArrayList,p1.getAllCities());
	}

	/**
	 * Order crossover
	 * This method will use p2 to generate offspring of p1,
	 * Therefore, it has to be called twice in order to general both 
	 * offsprings of p1 and p2.
	 * Example:
	 * Child_of_p1 = orderCrossover(parent1, parent2)
	 * Child_of_p2 = orderCrossover(parent2, parent1)
	 * @param p1
	 * @param p2
	 * @return Individual newchild
	 */
	public static Individual orderCrossover(Individual p1, Individual p2) {
		// setup
		int size = p1.getIndividuals().size();
		int[] child = new int[size];

		for(int i = 0; i < size; ++i) {
			child[i] = -1;
		}

		// random size
		int[] pos = randPos(size);
		if(pos[0] > pos[1]) {
			int tmp = pos[0];
			pos[0] = pos[1];
			pos[1] = tmp;
		}

		// step 1: copy random range
		for(int i = 0; i < size; ++i) {
			if(i >= pos[0] && i <= pos[1]) {
				child[i] = p1.getIndividuals().get(i);
			}
		}

		// step 1.5: store unused numbers in p2 from right cut point
		ArrayList<Integer> unused = new ArrayList<Integer>();
		for(int i = pos[1] + 1; i < size; ++i) {
			unused.add(p2.getIndividuals().get(i));
		}
		for(int i = 0; i < pos[1] + 1; ++i) {
			unused.add(p2.getIndividuals().get(i));
		}

		// step 2: copy remaining unused numbers
		int limit = size - (pos[1] - pos[0]) - 1;
		int index = pos[1] + 1;
		int count = 0;

		if(index >= child.length) {
			index = 0;
		}

		for(int i = 0; i < unused.size(); ++i) {
			if(!searchAarry(child, unused.get(i))) {
				child[index] = unused.get(i);
				index++;
				count++;
				if(index >= child.length) {
					index = 0;
				}
			}
			if(count >= limit) {
				break;
			}
		}

		// Final step: copy value to list
		ArrayList<Integer> childArrayList = new ArrayList<Integer>();
		for(int i = 0; i < size; ++i) {
			childArrayList.add(child[i]);
		}

		return  new Individual(childArrayList,p1.getAllCities());
	}

	/**
	 * PMX method will use p2 to generate offspring of p1,
	 * Therefore, it has to be called twice in order to general both 
	 * offsprings of p1 and p2.
	 * Example: 
	 * Child_of_p1 = PMX(parent1, parent2)
	 * Child_of_p2 = PMX(parent2, parent1)
	 * @param p1
	 * @param p2
	 * @return Individual newchild
	 */
	public static Individual PMX(Individual p1, Individual p2){
		// setup
		int size = p1.getIndividuals().size();
		int[] child = new int[size];
		for(int i = 0; i < size; ++i) {
			child[i] = -1;
		}

		// random size
		int[] pos = randPos(size);
		if(pos[0] > pos[1]) {
			int tmp = pos[0];
			pos[0] = pos[1];
			pos[1] = tmp;
		}

		// step 1: copy random range
		for(int i = 0; i < size; ++i) {
			if(i >= pos[0] && i <= pos[1]) {
				child[i] = p1.getIndividuals().get(i);
			}
		}

		// step 2:
		for(int i = 0; i < size; ++i) {
			if(i >= pos[0] && i <= pos[1]) {
				if(p1.getIndividuals().get(i) != p2.getIndividuals().get(i)) {
					int index = i;
					if(!searchAarry(child, p2.getIndividuals().get(index))) {
						while(child[index] != -1) {

							int searchVal = child[index];
							index = p2.getIndividuals().indexOf(searchVal);
						}
						child[index] = p2.getIndividuals().get(i);
					}	
				}
			}
		}

		// step 3: copy remaining value in p2 to child
		for(int i = 0; i < size; ++i) {
			if(child[i] == -1) {
				child[i] = p2.getIndividuals().get(i);
			}
		}

		// Final step: copy value to list
		ArrayList<Integer> childArrayList = new ArrayList<Integer>();
		for(int i = 0; i < size; ++i) {
			childArrayList.add(child[i]);
		}

		return  new Individual(childArrayList,p1.getAllCities());
	}

	/**
	 * Generate two distinct random numbers
	 * @param range
	 * @return int[] pos
	 */
	public static int[] randPos(int range) {
		// create an integer array from 0 to range
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i=0; i<range; ++i) {
			list.add(new Integer(i));
		}

		// shuffle the array
		Collections.shuffle(list);

		// output the first two integers as random numbers
		int[] pos = new int[2];
		for (int i=0; i<2; i++) {
			pos[i] = list.get(i);
		}
		return pos;
	}

	/**
	 * Search item in array
	 * @param A
	 * @param target
	 * @return boolean flag
	 */
	public static boolean searchAarry(int[] A, int target) {
		boolean flag = false;
		for(int i = 0; i < A.length; i++) {
			if(A[i] == target) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * Remove all reference of entry
	 * @param map
	 * @param ref
	 */
	public static void removeRef(HashMap<Integer, ArrayList<Integer>> map, int ref) {
		for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {		
			if(entry.getValue().contains(ref)) {
				ArrayList<Integer> tmpArray = new ArrayList<Integer>();
				for(int i = 0; i < entry.getValue().size(); ++i) {
					if(entry.getValue().get(i) != ref) {
						tmpArray.add(entry.getValue().get(i));
					}
				}
				entry.setValue(tmpArray);
			}
		}
	}

	/**
	 * Find common edges
	 * @param edges
	 * @return int nextEntry
	 */
	public static int commonEdge(ArrayList<Integer> edges) {
		int nextEntry = -1;
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		for(int i = 0; i < edges.size(); ++i) {
			if(map.containsKey(edges.get(i))) {
				nextEntry = edges.get(i);
				break;
			}
			else {
				map.put(edges.get(i), 1);
			}
		}
		return nextEntry;
	}
}
