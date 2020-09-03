package dynamicprogramming.intermediate;

import static java.lang.Math.max;
import static java.lang.Math.min;

// https://www.geeksforgeeks.org/minimum-positive-points-to-reach-destination/

// http://disq.us/p/19xdesf
// https://ideone.com/KnEtgc

// https://leetcode.com/problems/dungeon-game/

// https://youtu.be/0iux7PPkiU4

public class MinimumInitialPointsToReachDestination {
    
    // Aug 2020
    int minPointToEnd(int[][] a, int r, int c) {
        // base case: when reached end
        if (r == a.length - 1 && c == a[0].length - 1)
            return a[r][c] > 0 ? 1 : 1 - a[r][c];

        int minPointsNeeded;
        if (r == a.length - 1)  {// last row
            minPointsNeeded = minPointToEnd(a, r, c+1); // from (r, c+1) to end
        }
        else if (c == a[0].length - 1) { // last column
            minPointsNeeded = minPointToEnd(a, r+1, c); // from (r+1, c) to end
        }
        else {
            minPointsNeeded = min(minPointToEnd(a, r, c+1), 
                                minPointToEnd(a, r+1, c+1)); // minimum of either direction
        }
    
        minPointsNeeded  -= a[r][c]; // consider current cell in the path
        if (minPointsNeeded <= 0) 
            minPointsNeeded = 1;
    
        return minPointsNeeded;
    }

    class SimpleRecursiveSolution {
        // T(n): Exp
        public int minInitialPoints(int[][] a, int r, int c) {
            // Base case:
            if (r == a.length-1 && c == a[0].length-1)
                return a[r][c] > 0 ? 1 : -a[r][c] + 1;
            
            if (r == a.length-1) // last row
                return max(1, minInitialPoints(a, r, c+1) - a[r][c]);
            if (c == a[0].length-1) // last column
                return max(1, minInitialPoints(a, r+1, c) - a[r][c]);
            
            int min_pnts_on_exit = min(minInitialPoints(a, r+1, c), 
                                        minInitialPoints(a, r, c+1));
            return max(1, min_pnts_on_exit - a[r][c]);
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(r*c), S(n): O(r*c)
        public int minInitialPoints(int[][] a) {
            int r = a.length;
            int c = a[0].length;
            
            int[][] res = new int[r][c];
            
            res[r-1][c-1] = (a[r-1][c-1] > 0) ? 1 : -a[r-1][c-1] + 1;
            
            for (int j = c-2; j >= 0; j--)
                res[r-1][j] = max(1, res[r-1][j+1] - a[r-1][j]);
            for (int i = r-2; i >= 0; i--)
                res[i][c-1] = max(1, res[i+1][c-1] - a[i][c-1]);
            
            for (int i = r-2; i >= 0; i--) {
                for (int j = c-2; j >= 0; j--) {
                    int min_pnts_on_exit = min(res[i+1][j], res[i][j+1]);
                    res[i][j] = max(1, min_pnts_on_exit - a[i][j]);
                }
            }
            return res[0][0];
        }
    }
    
    public static void main(String[] args) {
        int[][] points = {
                            {-2,-3,3}, 
                            {-5,-10,1}, 
                            {10,30,-5}
                         };
        MinimumInitialPointsToReachDestination o = new MinimumInitialPointsToReachDestination();
        System.out.println(o.new SimpleRecursiveSolution().minInitialPoints(points, 0, 0)); // 7
        System.out.println(o.new DPSolution().minInitialPoints(points)); // 7
        
        System.out.println(o.minPointToEnd(points, 0, 0)); // 7
    }
}
