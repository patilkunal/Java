package com.inovisionsoftware.quiz;

import java.util.ArrayList;

import com.inovisionsoftware.CombinationGenerator;
import com.inovisionsoftware.PermutationGenerator;


public class LegoBlocks2 {
	
	private static final float[] blocks = {3.0f, 4.5f};
	private float length = 0.0f;
	private int height = 0;
	private int maxblocksneeded = 0;
	private ArrayList<ArrayList<Float>> lengthPermutations = new ArrayList<ArrayList<Float>>();
	int duplicity = 0;
	private ArrayList<Integer> hashList = new ArrayList<Integer>();
	private ArrayList<Float> combinationHashList = new ArrayList<Float>();
	int validCombinationCount = 0;
	int totalCombinationCount = 0;
	private boolean debug = true;
	
	public LegoBlocks2(float l, int h) {
		this.length = l;
		this.height = h;
		if(h > 2) duplicity = (int) Math.ceil(h/2 + 0.2);
		if(duplicity < 2) duplicity = 2;
		System.out.println("Duplicity : " + duplicity);
	}
	
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
		
		//starting with different brick each time
		for(int start=0; start < blocks.length; start++) {
			float remain = length;
			ArrayList<Float> list = new ArrayList<Float>();
			while(remain > blocks[0]) {
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
				// we need to find permutations of this list
				float[] floatarr = new float[list.size()];
				int i=0;
				for(float f : list) {
					floatarr[i++] = f;
				}
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
	
	@SuppressWarnings("unchecked")
	private void computeCombinations() {
		ArrayList<Float>[] comblist = new ArrayList[height];
		ArrayList<Float>[] permlist = new ArrayList[height];
		CombinationGenerator combs = new CombinationGenerator(lengthPermutations.size() * duplicity, height);
		PermutationGenerator permsgenerator = new PermutationGenerator(height);
		totalCombinationCount = combs.getTotal().intValue();
		while(combs.hasMore()) {
			int[] combarr = combs.getNext();
			int i=0;
			for(int index : combarr) {
				comblist[i++] = lengthPermutations.get(index/duplicity);
			}
			while(permsgenerator.hasMore()) {
				int j=0;
				int[] posarr = permsgenerator.getNext();
				for(int index : posarr) {
					permlist[j++] = comblist[index];
				}
				if(isValidCombination(permlist)) {
//					float hash = computeCombinationHash(permlist);
//					if(!existCombination(hash)) {
//						combinationHashList.add(hash);
						validCombinationCount++;
//						if(debug) printCombination(permlist, hash);
//					} else {
//						if(debug) { System.out.print("dup "); printCombination(permlist, hash); }
//					}
						/*
					ArrayList<Float>[] permlist2 = new ArrayList[height];
					//if combination is good we can also make pairs of consecutive two's
					if(height > 2) {
						int n = 1;
						while( n <= height) {
							for(int r=0; r < height; r++) {
								permlist2[r] = permlist[r % n];
							}
							if(isValidCombination(permlist2)) {
//								hash = computeCombinationHash(permlist2);
//								if(!existCombination(hash)) {
//									combinationHashList.add(hash);
									validCombinationCount++;
//									if(debug) printCombination(permlist2, hash);
//								} 
//							} else {
//								if(debug) { System.out.print("dup "); printCombination(permlist, hash); }								
							}
							n++;
						}
					}
					*/
					
				}				
			}
			permsgenerator.reset();
		}
	}
	
	private boolean existCombination(float hash) {
		for(float num : combinationHashList) {
			if(hash == num) {
				return true;
			}
		}
		return false;
	}
	
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
	
	private void printCombination(ArrayList<Float>[] list, float hash) {
		System.out.println("Combination {");
		for(ArrayList<Float> l : list) {
			printList(l);
		}
		System.out.println("}, " + hash);
	}
	
	private boolean isValidCombination(ArrayList<Float>[] list) {
		boolean isvalid = true;
		//Calculate if given lego block combination will stand
		float[] prevlengths = new float[maxblocksneeded];
		float[] currentlengths = new float[maxblocksneeded];
		int prevlistsize=maxblocksneeded;
		int currlistsize=0;
		for(int k = 0; k < list.length; k++) {
			currlistsize = list[k].size();
			//calculate the lengths at which current line has block breaks
			for(int j=0; j < currlistsize; j++) {
				//Add previous length to current length
				//if index is zero then there is no previous, hence return 0
				currentlengths[j] = ((j > 0) ? currentlengths[j-1] : 0) + list[k].get(j);
			}
			//if less than max blocks needed, initialize rest to zero
			for(int j=currlistsize; j < maxblocksneeded; j++) currentlengths[j]=0.0f;
			int prevcounter = 0; int currentcounter = 0;
			//Count until we have valid or one of the counter runs out of max
			while(isvalid && (prevcounter < prevlistsize-1) && (currentcounter < currlistsize-1)) {
				if(prevlengths[prevcounter] == currentlengths[currentcounter]) {
					isvalid = false;
				} else if(prevlengths[prevcounter] > currentlengths[currentcounter]) {
					currentcounter++;
				} else if(prevlengths[prevcounter] < currentlengths[currentcounter]) {
					prevcounter++;
				}
			}
			//so far so valid, then copy current to previous for next comparision
			if(isvalid) {
				for(int i=0; i < maxblocksneeded; i++) {
					prevlengths[i] = currentlengths[i];
				}
				prevlistsize = currlistsize;
			} else {
				break;
			}
		}
		if(isvalid) computeCombinationHash(list);
		return isvalid;
	}
	
	public void compute() {
		computeLengthPermutations();
		computeCombinations();
	}
	
	public int getLengthPermutationCount() {
		return lengthPermutations.size();
	}
	
	public int getValidCombinationsCount() {
		return validCombinationCount;
	}
	
	public int getTotalCominationCount() {
		return totalCombinationCount;
	}
	
	private void log(String msg) {
		System.out.println(msg);
	}
	
	private void addtoLengthPermutations(ArrayList<Float> list) {
		if(list.size() > 0) {
			maxblocksneeded = (list.size() > maxblocksneeded) ? list.size() : maxblocksneeded;
			int hashcode = computeHash(list);
			if(!existPermutation(hashcode)) {
				lengthPermutations.add(list);
//				for(int i=0; i < duplicity; i++)
//					lengthPermutations.add(list);
				hashList.add(hashcode);
				if(debug) printList(list);
			} else {
				//if(debug) log("List already exist. not adding " + list.toString());
			}
		}
	}
	
	private boolean existPermutation(int hashParam) {
		for(int hash : hashList) {
			if(hash == hashParam) {
				return true;
			}
		}
		return false;
	}
	
	private int computeHash(ArrayList<Float> list) {
		int i=1;
		int hash = 0;
		for(float val : list) {
			hash = hash + (int)(val * i++);
		}
		return hash;
	}
	
	private void printList(ArrayList<Float> list) {
		System.out.print('[');
		for(Float f : list) {
			System.out.print(f + " ");
		}
		System.out.println(']');
	}
	
	public static void main(String args[]) {
		try {
			float length = Float.parseFloat(args[0]);
			int height = Integer.parseInt(args[1]);
			LegoBlocks2 b = new LegoBlocks2(length, height);
			b.compute();
			System.out.println("Found permutations for length : " + b.getLengthPermutationCount());
			System.out.println("Out of total " + b.getTotalCominationCount() +  " there are valid "  + b.getValidCombinationsCount() + " count."); 
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
