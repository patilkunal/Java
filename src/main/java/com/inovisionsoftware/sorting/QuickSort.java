package com.inovisionsoftware.sorting;

public class QuickSort {

	private int[] numbers;
	private int length;
	
	public void sort(int[] values) {
		numbers = values;
		length = values.length;
		quicksort(0, length-1);
	}
	
	private void quicksort(int low, int high) {
		int i = low; int j = high;
		// get the pivot element from the middle of the list
		int pivot = numbers[low + (high - low)/2];
		
		//while low counter is till lower than high mark
		while(i <= j){
			//Check the numbers from the left and see if they are less than the pivot value
			while(numbers[i] < pivot) 
				i++;
			//Ah! we got first value greater than pivot
			
			//Now look on the right hand side and see if they are greater than the pivot
			while(numbers[j] > pivot)
				j--;
			//now we have first value from right which is lower than pivot
			
			//lower needs to  go on left and greater on right
			if(i <= j) { //make sure we have not crossed the pivot boundary
				swap(i, j);
				//and start with next numbers
				i++;
				j--;
			}
			
		}
		//recursion
		if(i < high) //if low counter is still lower than high mark
			quicksort(i, high);
		if(low < j) //if high counter is still above low mark
			quicksort(low, j);
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
		int[] x = {5, 8, 10, 20, 1, 50, 3, 4};
		//int[] x = { 5, 8, 3};
		QuickSort q = new QuickSort();
		q.sort(x);
		for(int i : x) {
			System.out.println(i);
		}

	}

}
