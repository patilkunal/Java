package com.inovision.hackerrank;

import java.util.LinkedList;
import java.util.List;

public class RotateLeft {

    public static List<Integer> rotateLeft(int d, List<Integer> arr) {
        LinkedList<Integer> ll = new LinkedList<>(arr);
        //ll.addAll(arr);
        while(d > 0) {
            ll.addLast(ll.removeFirst());
            d--;
        }
        return ll;
    }

    public static void main(String[] args) {
        List<Integer> arr = List.of(1,2,3,4,5);
        System.out.println(arr);
        System.out.println(rotateLeft(4, arr));
    }
}
