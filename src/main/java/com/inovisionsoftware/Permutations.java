package com.inovisionsoftware;


/*
 *
 * A selection of objects in which the order of the objects matters.
 * Example: The permutations of the letters in the set {a, b, c} are:

abc        acb
bac        bca
cab        cba 

 * A formula for the number of possible permutations of k objects from a set of n. This is usually written nPk .
Formula: 	nPr = (n!) / (n-r)! = n (n-1)(n-2)(n-3) .... (n-r+1)
Example: 	
How many ways can 4 students from a group of 15 be lined up for a photograph?
Answer: 	

There are 15P4 possible permutations of 4 students from a group of 15.
15P4 = 15!/11! = 15 x 14 x 13 x 12 = 32760 
different lineups

Notes: 
Permutation is "same" k number of objects taken from n objects and how can they be arranged in different ways along with other n-k objects
Combination is "different" k number of objects taken from n objects and how many possible ways to take those k objects (but other n-k objects are not involved)

 */
public class Permutations {
	private int pcount=1;
	private int count;
	private int[] arr;
	private int[][] permarr;
	private int i;

	public Permutations(int count) {
		arr = new int[count];
		this.count = count;
		getFactorial(count);
		permarr = new int[pcount][];
	}
	
	public void calculate() {
		perm(arr, 0, arr.length - 1);
	}
	
	
	private void getFactorial(int number) {
		int num = number;
		while(num > 1) {
			pcount *= num;
			num--;
		}
	}
	
	private void perm(int[] paramarr, int prefix, int postfix) {
		if(postfix == prefix) {
			permarr[i] = new int[count];
			for(int j=0; j < paramarr.length; j++) {
				permarr[i][j] = paramarr[j];
			}
			printarr(paramarr);
			i++;
		} else {
			for(int k=0; k < postfix; k++)
				swap(paramarr, k, prefix);
				perm(paramarr, prefix++,  postfix);
		}
	}
	
	private void swap(int[] arr, int pos1, int pos2) {
		int temp = arr[pos1];
		arr[pos1] = arr[pos2];
		arr[pos2] = temp;
	}
	
	private void printarr(int[] param) {
		System.out.print("[");
		for(int x : param) {
			System.out.print(x + ",");
		}
		System.out.println("]");
	}
	
	public int permutationCount() {
		return pcount;
	}
	
	public int[] getNext() {
		
		return null;
	}
	
	
	public static void main(String args[]) {
		Permutations p = new Permutations(3);
		
		System.out.println("Number of permutations : " + p.permutationCount());
		p.calculate();
	}
}
