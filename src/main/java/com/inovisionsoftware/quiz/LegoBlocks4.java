package com.inovisionsoftware.quiz;

import java.util.ArrayList;

import com.inovisionsoftware.CombinationGenerator;
import com.inovisionsoftware.PermutationGenerator;


public class LegoBlocks4 {
	
	private static final float[] blocks = {3.0f, 4.5f};
	private float minblocksize = blocks[0];
	private float length = 0.0f;
	private int height = 0;
	/*
	 * Maximum number of bricks we would need to build to the length 
	 */
	private int maxblocksneeded = 0;
	/*
	 * brick structure permutations
	 */
	private ArrayList<ArrayList<Float>> lengthPermutations = new ArrayList<ArrayList<Float>>();
	//how many times can we stack the same brick structure
	int duplicity = 1; 
	//brick structure hash
	private ArrayList<Integer> hashList = new ArrayList<Integer>();
	//hash of the complete structure
	private ArrayList<Float> combinationHashList = new ArrayList<Float>();
	//This is the final answer.
	long validCombinationCount = 0;
	int totalCombinationCount = 0;
	private boolean debug = true;
	
	public LegoBlocks4(float l, int h) {
		this.length = l;
		this.height = h;
		if(h > 2) duplicity = (int) Math.ceil(h/2 + 0.2);
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
				//add the list upfront, since we know the duplicity
				for(int i=0; i < duplicity-1; i++) {
					lengthPermutations.add(list);
				}
			}
		}
		
		//starting with different brick each time
		for(int start=0; start < blocks.length; start++) {
			float remain = length;
			ArrayList<Float> list = new ArrayList<Float>();
			while(remain > minblocksize) {
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
					//add the list upfront, since we know the duplicity
					for(int j=0; j < duplicity-1; j++) {
						lengthPermutations.add(list2);
					}
				}
			}
		}		
		
	}
	
	@SuppressWarnings("unchecked")
	private void computeCombinations() {
		ArrayList<Float>[] comblist = new ArrayList[height];
		ArrayList<Float>[] permlist = new ArrayList[height];
		int len = lengthPermutations.size();
		CombinationGenerator combs = new CombinationGenerator(lengthPermutations.size(), height);
		PermutationGenerator permsgenerator = new PermutationGenerator(height);
		totalCombinationCount = combs.getTotal().intValue();
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
				if(isValidCombination(permlist)) {
						validCombinationCount++;
						if((validCombinationCount % 10) == 0) System.out.println(validCombinationCount);
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
			//hash = i * computeHash(list[i-1]);
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
		boolean isDup = false;
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
		float hash = 0.0f;
		if(isvalid)  {
			hash = computeCombinationHash(list);
			isDup = existCombination(hash);
			if(!isDup) {
				combinationHashList.add(hash);
			} else {
				//if(debug) System.out.print("Dup ");
			}
			//if(debug) printCombination(list, hash);			
		}
		return isvalid && !isDup;
	}
	
	public void compute() {
		computeLengthPermutations();
		computeCombinations();
	}
	
	public int getLengthPermutationCount() {
		return lengthPermutations.size();
	}
	
	public long getValidCombinationsCount() {
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
				hashList.add(hashcode);
				if(debug) printList(list);
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
			long start = System.currentTimeMillis();
			LegoBlocks4 b = new LegoBlocks4(length, height);
			b.compute();
			long end = System.currentTimeMillis();
			System.out.println("Total time required : " + (end-start)/1000L + " seconds");
			System.out.println("Found permutations for length : " + b.getLengthPermutationCount());
			System.out.println("Out of total " + b.getTotalCominationCount() +  " there are valid "  + b.getValidCombinationsCount() + " count."); 
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
