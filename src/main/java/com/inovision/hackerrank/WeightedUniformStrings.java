package com.inovision.hackerrank;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/*
A weighted string is a string of lowercase English letters where each letter has a weight. Character weights are to from to as shown below:
a = 1, b = 2, c = 3, ... z=26
Weight of string is add all values for char
e.g apple = 50, ccccc = 15
Uniform string is one or more same char repeated, e.g. 'a' or 'cccc', but not 'aba'

Given String and queries
s = 'abbcccdddd'
Queries = [1,7,5,4,15]
find if a query matches any combination of uniform string that be created from string
answer for above would be ['Yes', 'No', 'No', 'Yes', 'No']

s = 'aaabbbbcccddd'
Q = [5 9 7 8 12 5]
Ans = [Yes no yes yes no]
 */
public class WeightedUniformStrings {

    public static List<String> weightedUniformStrings(String s, List<Integer> queries) {
        // Write your code here
        Map<Character, List<Long>> map = new HashMap<>();
        char current = s.charAt(0);
        map.put(current, new ArrayList<>());
        long uniformsum = 0L;
        for( char c :(s.toCharArray())){
            if(current != c) {
                map.put(c, new ArrayList<>());
                current = c;
                uniformsum = 0L;
            }
            uniformsum += ((int)c - 96);
            map.get(c).add(uniformsum);
        }

        List<Long> allvalues = map.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        List<String> ans = queries.stream().map(val -> allvalues.contains(val.longValue()) ? "Yes" : "No").collect(Collectors.toList());
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(weightedUniformStrings("abbcccdddd", List.of(1,7,5,4,15)));
    }
}
