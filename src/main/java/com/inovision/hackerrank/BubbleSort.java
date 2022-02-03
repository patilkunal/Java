package com.inovision.hackerrank;

import java.util.Arrays;

public class BubbleSort {

    public static void bubbleSort(int[] array) {
        int len = array.length;
        int swapttemp = 0;
        int itr = 0;
        for(int i = 0; i < len; i++) {
            for(int j=1; j < (len-i); j++) {
                if(array[j-1] > array[j]) {
                    swapttemp = array[j-1];
                    array[j-1] = array[j];
                    array[j] = swapttemp;
                }
                itr++;
            }
        }
        System.out.println("Iterations: " + itr);
    }


    public static void bubbleSort2(int[] array) {
        int len = array.length;
        int swapttemp = 0;
        int itr = 0;
        for(int i = 0; i < len-1; i++) {
            for(int j=len-1; j > i; j--) {
                if(array[j-1] > array[j]) {
                    swapttemp = array[j-1];
                    array[j-1] = array[j];
                    array[j] = swapttemp;
                }
                itr++;
            }
        }
        System.out.println("Iterations: " + itr);
    }

    public static void main(String[] args) {
        int[] arr = {4,6,23,45,89,22,55,6,1,3,2};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
        int[] arr2 = {4,6,23,45,89,22,55,6,1,3,2};
        bubbleSort2(arr2);
        System.out.println(Arrays.toString(arr2));
    }
}
