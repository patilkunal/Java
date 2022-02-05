package com.inovision.hackerrank;

import java.util.List;

/*
Given five positive integers, find the minimum and maximum values that can be calculated by
summing exactly four of the five integers. Then print the respective minimum and maximum
values as a single line of two space-separated long integers.

Eg
arr = [1,3,5,7,9]
min sum = 16
max sum = 24
 */
public class MinMaxSum {

    public static void miniMaxSum(List<Integer> arr) {
        // Write your code here
        System.out.println(arr.stream().sorted().limit(4).map(Integer::longValue).reduce(Long::sum).orElse(0L));
        System.out.println(arr.stream().sorted((o1, o2) -> o2 - o1).limit(4).map(Integer::longValue).reduce(Long::sum).orElse(0L));

    }

    public static void main(String[] args) {
        miniMaxSum(List.of(1,3,5,7,9));
        miniMaxSum(List.of(256741038, 623958417, 467905213, 714532089, 938071625));
    }
}
