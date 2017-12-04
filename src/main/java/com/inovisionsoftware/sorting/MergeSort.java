package com.inovisionsoftware.sorting;

public class MergeSort {

	public int[] mergeSort(int[] arr, int start, int end) {
		if(start < end) {
			int middle = (end - start)/2;
			return merge(arr.length, mergeSort(arr, start, middle), mergeSort(arr, middle+1, start));
		}
		return null;
	}
	
	private int[] merge(int len, int[] left, int[] right) {
		int[] result = new int[len];
		int lefti = 0;
		int righti = 0;
		int k=0;
		while((lefti < left.length) && (righti < right.length)) {
			if(left[lefti] <= right[righti]) {
				result[k] = left[lefti]; 
			} else {
				result[k] = right[righti];
			}
			k++;
			lefti++;
			righti++;
		}
		
		while(lefti < left.length) 
			result[k++] = left[lefti++];
		while(righti < right.length)
			result[k++] = right[righti];
		
		return result;
	}
}
