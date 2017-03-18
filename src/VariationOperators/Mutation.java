package VariationOperators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Mutation {

//	public static void main(String[] args) {
//		ArrayList<Integer> test1 = new ArrayList<Integer>();
//		test1.add(1);
//		test1.add(2);
//		test1.add(3);
//		test1.add(4);
//		test1.add(5);
//		test1.add(6);
//		ArrayList<Integer> test2 = new ArrayList<Integer>();
//		test2.add(1);
//		test2.add(2);
//		test2.add(3);
//		test2.add(4);
//		test2.add(5);
//		test2.add(6);
//		ArrayList<Integer> test3 = new ArrayList<Integer>();
//		test3.add(1);
//		test3.add(2);
//		test3.add(3);
//		test3.add(4);
//		test3.add(5);
//		test3.add(6);
//		
//		System.out.println("Origin: " + test1.toString());
//		System.out.println("Swap: " + Swap(test1).toString());
//		System.out.println("Insert: " + Insert(test2).toString());
//		System.out.println("Inversion: " + Inversion(test3).toString());
//	}
//	
	/*
	 * Swap Mutation
	 * Two positions (genes) in the chromosome are selected at
	 * random and their allele values swapped
	 */
	public static ArrayList<Integer> Swap(ArrayList<Integer> city) {
		// generate two random position
		int range = city.size();
		int[] pos = randPos(range);
		
		// swap integers
		Collections.swap(city, pos[0], pos[1]);
		
		return city;
	}
	
	/*
	 * Insert Mutation
	 * Two alleles are selected at random and the second moved 
	 * next to the first, shuffling along the others to make room.
	 */
	public static ArrayList<Integer> Insert(ArrayList<Integer> city) {
		// generate two random position
		int range = city.size();
		int[] pos = randPos(range);
		
		// swap integers
		if(pos[0] > pos[1]) {
			int index = pos[0];
			while(index > pos[1]) {
				Collections.swap(city, index - 1, index);
				index -= 1;
			}
		}
		else {
			int index = pos[1];
			while(index > pos[0]) {
				Collections.swap(city, index - 1, index);
				index -= 1;
			}
		}
		return city;
	}
	
	/*
	 * Inversion Mutation
	 * Inversion mutation works by randomly selecting two 
	 * positions in the chromosome and reversing the order in which the values appear 
	 * between those positions.
	 */
	public static ArrayList<Integer> Inversion(ArrayList<Integer> city) {
		// generate two random position
		int range = city.size();
		int[] pos = randPos(range);
		
		// swap integers
		int index1 = pos[0];
		int index2 = pos[1];
		if(index1 > index2) {
			while(index1 > index2) {
				Collections.swap(city, index1, index2);
				index1--;
				index2++;
			}
		}
		else {
			while(index1 < index2) {
				Collections.swap(city, index1, index2);
				index1++;
				index2--;
			}
		}
			
		return city;
	}
	
	
	
	/*
	 * Generate two distinct random numbers
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

}
