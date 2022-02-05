package com.inovision.hackerrank;

import java.util.*;
import java.util.stream.Collectors;

/*
The member states of the UN are planning to send people to the moon. They want them to be from different countries. You will be given a list of pairs of astronaut ID's. Each pair is made of astronauts from the same country. Determine how many pairs of astronauts from different countries they can choose from.
Example input
======
n = 5
astronaut = [0,1], [2,3], [0,4]
Output = 6
Pairs = [0 2] [0 3] [1 2] [1 3] [4 2] [4 3]
=====
n = 10
astronaut = [0,2], [1,8], [1,4], [2,8], [2,6], [3,5], [6,9]
Output = 23
======
n = 100000
astronaut = [1,2], [3,4]
Output = 4999949998
======
n = 10
[0,1], [0,3], [0,4], [1,2], [1,3], [1,5], [1,7], [1,8], [1,9], [2,8], [2.7], [3,5], [3,8], [3,7],  [4,9], [
 */
public class JourneyToMoon {

    private static class Node<T> {
        public T value;
        public boolean visited;
        public List<Node<T>> connected;

        public Node(T val) {
            this.value = val;
            connected = new ArrayList<>();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return value.equals(node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    /*
     * Complete the 'journeyToMoon' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. 2D_INTEGER_ARRAY astronaut
     */

    public static long journeyToMoon(int n, List<List<Integer>> astronaut) {
        // Write your code here
        Map<Integer, Node<Integer>> countryMap = new HashMap<>();
        for(List<Integer> pairs: astronaut) {
            Node<Integer> node1 = countryMap.computeIfAbsent(pairs.get(0), Node::new);
            Node<Integer> node2 = countryMap.computeIfAbsent(pairs.get(1), Node::new);
            node1.connected.add(node2);
            node2.connected.add(node1);
       }

        List<Integer> countryCluster = new ArrayList<>();
        for(Node<Integer> node: countryMap.values()) {
            if(node.connected.size() == 0) {
                countryCluster.add(1);
            } else if(!node.visited){
                countryCluster.add(getNodeCount3(node));
            }
        }
        List<Integer> countryNodes = countryMap.values().stream().map(nn -> nn.value).collect(Collectors.toList());
        List<Integer> missingNodes = new ArrayList<>();
        for(int i=0; i < n; i++) {
            if(!countryNodes.contains(i))
                missingNodes.add(i);
        }

        for(int i=0; i < missingNodes.size(); i++) {
            countryCluster.add(1);
        }

        long ans = 0;
        //For one country we cannot pair with any other astronauts
        if(countryCluster.size() >= 2) {
            ans = (long) countryCluster.get(0) * countryCluster.get(1);
            if(countryCluster.size() > 2) {
                int sum = countryCluster.get(0) + countryCluster.get(1);
                for(int i=2; i < countryCluster.size(); i++) {
                    ans += (long) sum * countryCluster.get(i);
                    sum += countryCluster.get(i);
                }
            }
        }

        /*
        permutation of two set with size A and B = AxB
        permutation of three set with size A,B,C = AxB + AxC + BxC = AxB + (A+B)xC
        permutation of four set with size A,B,C,D = AxB + AxC + AxD + BxC + BxD + CxD = AxB + (A+B)xC + (A+B+C)xD
         */
        /*
        if(keys.length == 1) {
            ans = keys[0].size();
        } else {
            ans = keys[0].size() * keys[1].size();
            if(keys.length > 2) {
                int sum = keys[0].size() + keys[1].size();
                for (int i = 2; i < keys.length; i++) {
                      ans += sum * keys[i].size();
                      sum += keys[i].size();
                }
            }
        }
         */
        return ans;
    }

    // Using DFS (Recursion)
    public static int getNodeCount(Node<Integer> node) {
            node.visited = true;
            int count = 1;
            for(Node<Integer> child: node.connected) {
                if(!child.visited)
                    count += getNodeCount(child);
            }
            return count;
    }

    //Same DFS using stream and recursion
    public static int getNodeCount2(Node<Integer> node) {
        node.visited = true;
        //Count this node (1) and it's children (getNodeCount)
        return 1 + node.connected.stream().filter(n -> !n.visited).map(JourneyToMoon::getNodeCount2).reduce(Integer::sum).orElse(0);
    }

    //DFS using stack
    public static int getNodeCount3(Node<Integer> node) {
        //Using stack
        Stack<Node<Integer>> stack = new Stack<>();
        stack.push(node);
        int count = 0;
        while(!stack.empty()) {
            Node<Integer> current = stack.pop();
            if(!current.visited) {
                current.visited = true;
                count++;
                current.connected.forEach(stack::push);
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(journeyToMoon(5, List.of(List.of(0,1), List.of(2,3), List.of(0,4))));
        System.out.println(journeyToMoon(4, List.of(List.of(0,2))));
        System.out.println(journeyToMoon(10, List.of(List.of(0,2), List.of(1,8), List.of(1,4), List.of(2,8), List.of(2,6), List.of(3,5), List.of(6,9))));
        System.out.println(journeyToMoon(100000, List.of(List.of(1,2), List.of(3,4))));
    }
}
