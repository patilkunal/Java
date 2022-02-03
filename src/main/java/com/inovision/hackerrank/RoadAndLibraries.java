package com.inovision.hackerrank;

import java.util.*;
import java.util.stream.Collectors;

/*
Determine the minimum cost to provide library access to all citizens of HackerLand. There are cities numbered from to .
Currently there are no libraries and the cities are not connected.
Bidirectional roads may be built between any city pair listed in

A citizen has access to a library if:
    Their city contains a library.
    They can travel by road from their city to a city containing a library.

Solution:
0. Find clusters of the cities which are together
1. Build library in each city (cost of library < cost of road)
2. Or Build library in one city and connected rest with roads (cost of road < cost of library)
4. Can be solved using DFS,
    count nodes which are connected to each other (size of cluster)
        cost = 1 + road cost * (node count - 1)
        cost = library cost * node count
        find minimum of above
    node which are not connected to anything have size of 1
        cost = library cost * 1

Example input
n = 3, cities[] size m = 3, c_lib = 2, c_road = 1
cities = [[1, 2], [3, 1], [2, 3]]
Answer: 4

n = 6, cities[] size m = 6, c_lib = 2, c_road = 5
cities = [[1, 3], [3, 4],[2,4], [1,2], [2,3], [5,6]]
Answer: 12

*/
public class RoadAndLibraries {
    private static boolean log = false;

    private static class CityNode {
        public final int number;
        public boolean visited = false;
        public List<CityNode> connectedTo = new ArrayList<>();

        public CityNode(int num) {
            this.number = num;
        }

        @Override
        public String toString() {
            return "City{" +
                    "number=" + number +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CityNode cityNode = (CityNode) o;
            return number == cityNode.number;
        }

        @Override
        public int hashCode() {
            return Objects.hash(number);
        }
    }

    public static long roadsAndLibraries(int n, int c_lib, int c_road, List<List<Integer>> cities) {
        List<Integer> cityClusters = new ArrayList<>();
        Map<Integer, CityNode> cityMap = new HashMap<>();

        for(int i=1; i <= n; i++) {
            cityMap.put(i, new CityNode(i));
        }
        for(List<Integer> conn: cities) {
            Integer cityNum1 = conn.get(0);
            Integer cityNum2 = conn.get(1);
            CityNode node1 = cityMap.get(cityNum1);
            CityNode node2 = cityMap.get(cityNum2);
            node1.connectedTo.add(node2);
            if(log)
                System.out.println("Getting city [1] " + cityNum1);
            if(log)
                System.out.println("Getting city [2] " + cityNum2);
            node2.connectedTo.add(node1);
        }
        if(log)
        for(CityNode city: cityMap.values()) {
            System.out.println(city);
        }

        for(CityNode city: cityMap.values()) {
            if(city.connectedTo.size() == 0) {
                cityClusters.add(1);
            } else if(!city.visited) {
                cityClusters.add(getNodeCount(city));
            }
        }
        long answer = 0;
        for(Integer clusterSize: cityClusters) {
            answer += Math.min(c_lib + c_road * (clusterSize-1), c_lib * clusterSize);
        }
        return answer;
    }

    // Depth first search
    private static int getNodeCount(CityNode city) {
        if(log)
        System.out.println("Visiting city === " + city.number);
        if(log)
        System.out.println("City " + city.number + " has nodes " + city.connectedTo.stream().map(c -> c.number).collect(Collectors.toList()));
        city.visited = true;
        int nodeCount = 1;
        for(CityNode cityNode: city.connectedTo) {
            if(!cityNode.visited) {
                if(log)
                System.out.println("Counting nodes for connected city " + cityNode.number);
                nodeCount += getNodeCount(cityNode);
            } else {
                if(log)
                System.out.println("City is visited " + cityNode.number);
            }
        }
        if(log)
        System.out.println("Returning Node count " + nodeCount + " for city " + city.number);
        return nodeCount;
    }

    public static void main(String[] args) {
        //System.out.println(roadsAndLibraries(3,2,1, List.of(List.of(1,2), List.of(3,1), List.of(2,3))));
        //System.out.println(roadsAndLibraries(6,2,5, List.of(List.of(1,3), List.of(3,4), List.of(2,4), List.of(1,2), List.of(2,3), List.of(5,6))));
        System.out.println(roadsAndLibraries(5,6,1, List.of(List.of(1,2), List.of(1,3), List.of(1,4))));
    }

}
