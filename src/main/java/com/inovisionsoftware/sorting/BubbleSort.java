package com.inovisionsoftware.sorting;

public class BubbleSort {

	private int[] numbers;
	static int i = 0;
	
	public BubbleSort() {
		i = 10;
		i=8;
	}
	
	public void sort(int[] input) {
		numbers = input;
		int count = input.length - 1;
		for(int i=0; i < count; i++)
			for(int j=count; j > i; j--)
				if(input[j] < input[j-1]) 
					swap(j, j-1);
	}
	
	private void swap(int i, int j) {
		int temp = numbers[i];
		numbers[i] = numbers[j];
		numbers[j] = temp;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] x = {9,5, 8} ;// 10, 20, 1, 50, 3, 4, 100, 2,0};
		BubbleSort q = new BubbleSort();
		q.sort(x);
		for(int i : x) {
			System.out.println(i);
		}
		
		System.out.println("static " + BubbleSort.i);
	}
	
	static {
		i = 11;
		System.out.println("static block");
	}


}
