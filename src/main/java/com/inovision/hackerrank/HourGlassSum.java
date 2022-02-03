package com.inovision.hackerrank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HourGlassSum {

    public static int hourGlassSum(List<List<Integer>> arr) {
        // int[][] sums = new int[4][4];  ([0,0,0,0], [0,0,0,0], [0,0,0,0], [0,0,0,0] ]);
        int max = -9 * 7;
        for(int i = 0; i < arr.size() - 2 ; i++) {
            int sum = 0;
            for(int j = 0; j < arr.get(0).size() - 2; j++) {
                sum = arr.get(i).get(j) +    arr.get(i).get(j+1) + arr.get(i).get(j+2);
                sum +=                       arr.get(i+1).get(j+1);
                sum += arr.get(i+2).get(j) + arr.get(i+2).get(j+1) + arr.get(i+2).get(j+2);
                System.out.print(sum + " ");
                max = Math.max(max, sum);
            }
            System.out.println(" ");
        }

        return max;
    }

    public static void main(String args[]) throws IOException {
        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        List<List<Integer>> arr = new ArrayList<>();
        /*
        IntStream.range(0, 6).forEach(i -> {
            try {
                arr.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList())
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        bufferedReader.close();
        */
        arr = List.of(
                List.of(1,1,1,0,0,0),
                List.of(0,1,0,0,0,0),
                List.of(1,1,1,0,0,0),
                List.of(0,0,2,4,4,0),
                List.of(0,0,0,2,0,0),
                List.of(0,0,1,2,4,0)
        );
        System.out.println(HourGlassSum.hourGlassSum(arr));
    }
}
