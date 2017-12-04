package com.inovisionsoftware.quiz;

import java.util.ArrayList;

import com.inovisionsoftware.CombinationGenerator;
import com.inovisionsoftware.PermutationGenerator;


public class LegoBlocks {
	
	//Lego block lengths
	private static final float[] blocks = {3.0f, 4.5f};
	//The minimum size of lego block (can be either computed or set to first block if in ascending order
	private float minblocksize = blocks[0];
	//user input length of panel
	private float length = 0.0f;
	//user input height of panel
	private int height = 0;
	/*
	 * Maximum number of bricks we would need to build to one layer of panel 
	 */
	private int maxblocksneeded = 0;
	/*
	 * permutations of a layer of panel 
	 */
	private ArrayList<ArrayList<Float>> lengthPermutations = new ArrayList<ArrayList<Float>>();
	//how many times can we stack the consecutive layers of panel
	//@unused for now
	int duplicity = 1; 
	//brick structure hash
	private ArrayList<Integer> hashList = new ArrayList<Integer>();
	//hash of the complete structure
	private ArrayList<Float> combinationHashList = new ArrayList<Float>();
	//This is the final answer.
	long validCombinationCount = 0;
	//total number of combinations possible (including duplicate and invalid)
	int totalCombinationCount = 0;
	//do we want debug output?
	public boolean debug = false;
	
	/**
	 * Lego Panel constructor
	 * 
	 * @param l - length of the panel
	 * @param h - Height of the panel
	 */
	public LegoBlocks(float l, int h) {
		this.length = l;
		this.height = h;
		if(h > 2) duplicity = (int) Math.ceil(h/2 + 0.2);
		if(debug) System.out.println("Duplicity : " + duplicity);
	}
	
//	we need to find a unique lists with difference count of same length bricks
//	and then find permutations from them
//	How do we keep track of above?
//
//	We start we two layers and try to see if the end layers can be restacked (in case of two it would always be true).
	
	/**
	 * computes all the possible permutations to lay 
	 * down a layer for panel from available lego blocks
	 */
	private void computeLengthPermutations() {
		int len = blocks.length;
		
		//find if length can be computed as exact multiple of each brick		
		for(int j=0; j < blocks.length; j++) {
			if(length % blocks[j] == 0) {
				ArrayList<Float> list = new ArrayList<Float>();
				if(debug) log("found exact multiple for " + blocks[j]);
				for(int k=0; k < length/blocks[j]; k++) {
					list.add(blocks[j]);
				}
				addtoLengthPermutations(list);
			}
		}
		
		//now try different bricks combinations
		//starting with different brick each time
		for(int start=0; start < blocks.length; start++) {
			float remain = length;
			ArrayList<Float> list = new ArrayList<Float>();
			//while we have remaining length 
			while(remain >= minblocksize) {
				//Find combination with different bricks
				for(int j = 0; j < blocks.length; j++) {
					int index = (j + start)  % len ;
					if(remain >= blocks[index]) {
						remain = remain - blocks[index];
						list.add(blocks[index]);
					}
				}
			}
			if((remain == 0) && (list.size() > 0)) {
				//Since this list contains different bricks 
				// we need to find different permutations of this list
				//initialize a array
				float[] floatarr = new float[list.size()];
				int i=0;
				for(float f : list) {
					floatarr[i++] = f;
				}
				//initialize permutation generator
				PermutationGenerator perms = new PermutationGenerator(list.size());
				while(perms.hasMore()) {
					int[] posarr = perms.getNext();
					ArrayList<Float> list2 = new ArrayList<Float>();
					for(int pos : posarr ) {
						list2.add(list.get(pos));
					}
					addtoLengthPermutations(list2);					
				}
			}
		}		
		
	}
	
	/**
	 * Computes combination of layers to make a panel upto "combinationof" height 
	 * 
	 * @param combinationof - how many layers to take at a time
	 * @param checkdup - do we want to check for duplicate combination
	 */
	@SuppressWarnings("unchecked")
	private void computeCombinations(int combinationof, boolean checkdup) {
		ArrayList<Float>[] comblist = new ArrayList[combinationof];
		ArrayList<Float>[] permlist = new ArrayList[combinationof];
		CombinationGenerator combs = new CombinationGenerator(lengthPermutations.size(), combinationof);
		PermutationGenerator permsgenerator = new PermutationGenerator(combinationof);
		totalCombinationCount += combs.getTotal().intValue();
		while(combs.hasMore()) {
			int[] combarr = combs.getNext();
			int i=0;
			for(int index : combarr) {
				comblist[i++] = lengthPermutations.get(index);
			}
			while(permsgenerator.hasMore()) {
				int j=0;
				int[] posarr = permsgenerator.getNext();
				for(int index : posarr) {
					permlist[j++] = comblist[index];
				}
				if(isValidCombination(permlist, checkdup)) {
						validCombinationCount++;
						if(debug && (validCombinationCount % 10) == 0) System.out.println(validCombinationCount);
				}			
			}
			permsgenerator.reset();
		}
	}

	/**
	 * Returns true if we have already computed same combination of layers to make the panel
	 * 
	 * @param hash - hash value of the combination
	 * @return - true if combination hash exist
	 */
	private boolean existCombination(float hash) {
		for(float num : combinationHashList) {
			if(hash == num) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Computes a hash of given layer combinations
	 * 
	 * @param list - list of layers which makes the panel
	 * @return - hash value
	 */
	private float computeCombinationHash(ArrayList<Float>[] list) {
		float hash = 0.0f;
		for(int i=1; i <= list.length; i++) {
			int j=1;
			for(float num : list[i-1]) {
				hash += i*((j) * num);
				j++;
			}
		}
		return hash;
	}
	
	/**
	 * Prints the list (used for debugging purpose)
	 * @param list
	 * @param hash
	 */
	@SuppressWarnings("unused")
	private void printCombination(ArrayList<Float>[] list, float hash) {
		System.out.println("Combination {");
		for(ArrayList<Float> l : list) {
			printList(l);
		}
		System.out.println("}, " + hash);
	}
	
	/**
	 * Computes if given list of blocks makes a valid panel which would stand and 
	 * that the block do not line up at any length value
	 * 
	 * @param list - list of layers which would make a panel
	 * @param checkduplicate - do we want to check if we have already built such a panel?
	 * @return - true if list of block layes make valid panel
	 */
	private boolean isValidCombination(ArrayList<Float>[] list, boolean checkduplicate) {
		boolean isvalid = true;
		boolean isDup = false;
		//allocate length array to maximum number of blocks in any layer
		float[] prevlengths = new float[maxblocksneeded];
		float[] currentlengths = new float[maxblocksneeded];
		int prevlistsize=maxblocksneeded;
		int currlistsize=0;
		for(int k = 0; k < list.length; k++) {
			currlistsize = list[k].size();
			//calculate the lengths at which current line of block line up
			for(int j=0; j < currlistsize; j++) {
				//Add previous length to current length
				//if index is zero then there is no previous, hence return 0
				currentlengths[j] = ((j > 0) ? currentlengths[j-1] : 0) + list[k].get(j);
			}
			//if less than max blocks needed, initialize rest to zero
			for(int j=currlistsize; j < maxblocksneeded; j++) currentlengths[j]=0.0f;
			int prevcounter = 0; int currentcounter = 0;
			//Count until we everything is valid so far or one of the counter runs out of max list size
			while(isvalid && (prevcounter < prevlistsize-1) && (currentcounter < currlistsize-1)) {
				//at any point if we find that the current layer length value and previous layer
				//length value are equal, it means brick length line up - INVALID COMBINATION
				if(prevlengths[prevcounter] == currentlengths[currentcounter]) {
					isvalid = false;
				} else if(prevlengths[prevcounter] > currentlengths[currentcounter]) {
					//if previous length at a position is greater, then we increment current counter
					currentcounter++;
				} else if(prevlengths[prevcounter] < currentlengths[currentcounter]) {
					//if current length at a position is greater, then we increment previous counter
					prevcounter++;
				}
			}
			//if valid flag survives it's truth, then copy current lengths as previous for next comparison
			if(isvalid) {
				for(int i=0; i < maxblocksneeded; i++) {
					prevlengths[i] = currentlengths[i];
				}
				prevlistsize = currlistsize;
			} else {
				//we don't need to any more computation if we find invalid combination
				break;
			}
		}
		//if we are instructed to check if such a panel was already built
		if(checkduplicate) {
			float hash = 0.0f;
			if(isvalid)  {
				//compute hash
				hash = computeCombinationHash(list);
				//and check for duplicate
				isDup = existCombination(hash);
				if(!isDup) {
					//add this hash to list if unique
					combinationHashList.add(hash);
				} else {
					//if(debug) System.out.print("Dup ");
				}
				//if(debug) printCombination(list, hash);			
			}
		}
		return isvalid && !isDup; // return true if valid and not duplicate
	}
	
	/**
	 * the main computation routine to be called for this class
	 */
	public void compute() {
		long start = System.currentTimeMillis();
		//first compute all the permutations to build a layer 
		computeLengthPermutations();
		//if we have enough permutations to make combinations of height
		if(lengthPermutations.size() >= height) {
			//compute the combinations for given height
			computeCombinations(height, true);
			//if height is more than 2, we can stack two consecutive lego layers
			//upto the height and it would a valid combination too
			if(height > 2) {
				computeCombinations(2, false);
			}
		}
		long end = System.currentTimeMillis();
		if(debug) System.out.println("Total time required : " + (end-start) + " mseconds");
		if(debug) System.out.println("Found permutations for length : " + getLengthPermutationCount());
		if(debug) System.out.println("Out of total " + getTotalCominationCount() +  " there are valid "  + getValidCombinationsCount() + " count."); 
	}
	
	
	/**
	 * Returns how many ways one layer lego panel can be constructed
	 * @return
	 */
	public int getLengthPermutationCount() {
		return lengthPermutations.size();
	}
	
	/**
	 * Returns possible valid combinations of the lego panels for given width and height
	 * @return
	 */
	public long getValidCombinationsCount() {
		return validCombinationCount;
	}
	
	/**
	 * total number of possible combinations tried (including invalid and duplicate)
	 * @return
	 */
	public int getTotalCominationCount() {
		return totalCombinationCount;
	}
	
	/**
	 * My logger
	 */
	private void log(String msg) {
		System.out.println(msg);
	}
	
	private void addtoLengthPermutations(ArrayList<Float> list) {
		if(list.size() > 0) {
			maxblocksneeded = (list.size() > maxblocksneeded) ? list.size() : maxblocksneeded;
			int hashcode = computeHash(list);
			if(!existPermutation(hashcode)) {
				lengthPermutations.add(list);
				hashList.add(hashcode);
				if(debug) printList(list);
			}
		}
	}
	
	/**
	 * Returns true if hash of a permutation exists
	 * @param hashParam - permutation hash
	 * @return - true if permutation hash exists
	 */
	private boolean existPermutation(int hashParam) {
		for(int hash : hashList) {
			if(hash == hashParam) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Computes layer's hash value
	 * @param list
	 * @return
	 */
	private int computeHash(ArrayList<Float> list) {
		int i=1;
		int hash = 0;
		for(float val : list) {
			hash = hash + (int)(val * i++);
		}
		return hash;
	}
	
	/**
	 * Prints list (for debugging)
	 * @param list
	 */
	private void printList(ArrayList<Float> list) {
		System.out.print('[');
		for(Float f : list) {
			System.out.print(f + " ");
		}
		System.out.println(']');
	}
	
	/**
	 * Main routine
	 */
	public static void main(String args[]) {
		try {
			if(args.length > 1) {
				float length = Float.parseFloat(args[0]);
				int height = Integer.parseInt(args[1]);
				LegoBlocks b = new LegoBlocks(length, height);
				if(args.length > 2) {
					b.debug = Boolean.parseBoolean(args[2]);
				}
				b.compute();
				System.out.println(b.getValidCombinationsCount());
			} else {
				System.out.println("Please input width and height");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
