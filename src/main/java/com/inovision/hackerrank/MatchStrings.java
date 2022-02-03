package com.inovision.hackerrank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchStrings {

    public static List<Integer> matchStrings(List<String> strings, List<String> queries) {
        final List<Integer> ret = new ArrayList<>();
        queries.forEach(q -> {
           int c = strings.stream().map(s -> s.equals(q) ? 1 : 0).reduce(Integer::sum).orElse(0);
            ret.add(c);
        });
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(matchStrings(List.of("aba", "baba", "aba", "xzxb"), List.of("aba", "xzxb", "ab")));
        List<List<String>> list = List.of(List.of("123", "333"), List.of("456"));
        String str = list.stream().flatMap(l -> l.stream()).collect(Collectors.joining(""));
                //.flatMap(Collection::stream).collect(Collectors.joining(""));
        System.out.println(str);
    }
}
