package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/dynamic-programming-building-bridges/
// https://www.youtube.com/watch?v=w6tSmS86C4w

public class BuildingBridges {
    
    private class Pair implements Comparable<Pair> {
        int north, south;
        Pair(int north, int south) {
            this.north = north;
            this.south = south;
        }
        
        @Override
        public int compareTo(Pair otherPair) {
            if (this.south != otherPair.south)
                return this.south - otherPair.south;
            else
                return this.north - otherPair.north;
        }
    }

    /* Algorithm:
     * 1. Sort city pairs on one bank (say south bank)
     * 2. And then find LIS on other bank (north bank)
     * 
     * Two points to note:
     * - while sorting, if two pairs have equal south bank values, 
     *   then sort them on north bank, to maximize the number of bridges
     *   without two bridges crossing each other
     *   
     * - while finding LIS on north bank, along with greater values, 
     *   consider equal values as well, because equal values too, will 
     *   not create a crossing
     */
    public int maxBridges(Pair[] cityPairs) {
        Arrays.sort(cityPairs);
        
        int n = cityPairs.length;
        int[] res = new int[n];
        
        res[0] = 1;
        int lis = 1;
        for (int i = 1; i < n; i++) {
            res[i] = 1;
            for (int j = 0; j < i; j++) {
                if (cityPairs[j].north <= cityPairs[i].north) {
                    if (res[i] < res[j] + 1) {
                        res[i] = res[j] + 1;
                    }
                }
            }
            if (lis < res[i])
                lis = res[i];
        }
        return lis;
    }
    /*
     * Time and space complexities will be same as LIS, i.e.,
     * T(n): O(n^2), S(n): O(n)
     */
    
    public static void main(String[] args) {
        BuildingBridges o = new BuildingBridges();
        
        Pair[] cityPairs = {
                o.new Pair(0, 1),
                o.new Pair(2, 4),
                o.new Pair(1, 4),
                o.new Pair(1, 3)
        };
        
        System.out.println(o.maxBridges(cityPairs));
    }
}
