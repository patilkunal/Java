package com.inovision.hackerrank;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/*
Starting with a 1-indexed array of zeros and a list of operations, for each operation add a value to each the array element
between two given indices, inclusive.
Once all operations have been performed, return the maximum value in the array.
Take single dimension array of all zeros. Add value of column 'k' to all the elements indexed by value of 'a' and 'b' (inclusive)
 */
public class ManipulateArray {


    public static long arrayManipulation(int n, List<List<Integer>> queries) {
        long[] arr = new long[n]; //{0,0,0,0,0 ,0,0,0,0,0};
        AtomicLong max = new AtomicLong(0);
        queries.parallelStream().forEach(l -> {
            int a = l.get(0);
            int b = l.get(1);
            int k = l.get(2);
            for(int i = a-1; i < b; i++) {
                arr[i] += k;
                max.set(Math.max(max.get(), arr[i]));
            }
        });
        return max.get();
        /*
        queries.forEach(l -> {
            int a = l.get(0);
            int b = l.get(1);
            int k = l.get(2);
            for(int i = a-1; i < b; i++) {
                arr[i] += k;
                max.set(Math.max(max.get(), arr[i]));
            }
        });
        return max.get();

         */
    }

    public static void staircase(int n) {
        StringBuilder buf = new StringBuilder();
        for(int i=1; i <= n; i++) {
            String format = "%" + n + "s%n";
            buf.append("#");
            System.out.printf(format, buf.toString());
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> arr = List.of(List.of(1,5,3), List.of(4,8,7), List.of(6,9,1));

        //System.out.println(arrayManipulation(10, arr));
        System.out.println(arrayManipulation(5, List.of(List.of(1,2,100), List.of(2,5,100), List.of(3,4,100))));
        staircase(6);
    }
}
