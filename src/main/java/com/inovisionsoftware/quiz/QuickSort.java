package com.inovisionsoftware.quiz;

public class QuickSort {

private int[] numbers;

public void sort(int[] x) {
	this.numbers = x;
	int len = x.length;
	quicksort(0, len-1);
}

private void quicksort(int low, int high) {
	int i = low; int j = high;

	int pivot = numbers[low + (high - low)/2];
	
	while(i <= j) {
		while(numbers[i] < pivot) i++;
		while(numbers[j] > pivot) j--;

		if(i <= j) {
			swap(i, j);
			i++;
			j--;
		}	
	}
	
	if(low < j) //we have not yet reached the low mark
		quicksort(low, j);
	if(high > i)
		quicksort(i, high);
}

private void swap(int i, int j){
	int temp = numbers[i];
	numbers[i] = numbers[j];
	numbers[j] = temp;
}

public static void main(String args[]) {
		int[] nums = { 8, 9, 10, 90, 22, 9, 6 };
		QuickSort s = new QuickSort();
		s.sort(nums);
		for(int i : nums)
			System.out.println(i);
}

}