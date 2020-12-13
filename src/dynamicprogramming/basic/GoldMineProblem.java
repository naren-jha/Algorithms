package dynamicprogramming.basic;

import static java.lang.Math.max;

import java.util.Arrays;
import java.util.StringJoiner;

// https://www.geeksforgeeks.org/gold-mine-problem/

public class GoldMineProblem {
    
    private class BruteForce {
        int R, C;
        
        // TC: O(R * 3^C) [max height of the recursion tree will be C
        // and for each node in the tree, we are making at most 3 recursive calls]
        
        // SC: O(C) [as max height of the recursion tree is C]
        public int maxGold(int[][] gm) {
            R = gm.length;
            if (R == 0) throw new IllegalArgumentException("empty mine");
            C = gm[0].length;
            
            int maxGold = 0;
            for (int i = 0; i < R; i++) {
                maxGold = max(maxGoldUtil(gm, i, 0), maxGold);
            }
            
            return maxGold;
        }
        
        // O(3^C)
        private int maxGoldUtil(int[][] gm, int i, int j) {
            // base condition
            if (j == C-1) return gm[i][j];
            
            int ru = (i > 0) ? maxGoldUtil(gm, i-1, j+1) : 0; // right up
            int r = maxGoldUtil(gm, i, j+1); // right
            int rd = (i < R-1) ? maxGoldUtil(gm, i+1, j+1) : 0; // right down
            
            return gm[i][j] + max(ru, max(r, rd));
        }
    }
    
    
    private class DPSolution {
        int R, C;
        
        // memoization
        // TC: O(RC), SC: O(RC)
        public int maxGoldMemoized(int[][] gm) {
            R = gm.length;
            if (R == 0) throw new IllegalArgumentException("empty mine");
            C = gm[0].length;
            
            // create a matrix to store intermediate results
            int[][] mem = new int[R][C];
            for (int i = 0; i < R; i++)
                Arrays.fill(mem[i], -1);
            
            int maxGold = 0;
            for (int i = 0; i < R; i++) {
                maxGold = max(maxGoldUtilMemoized(gm, i, 0, mem), maxGold);
            }
            
            return maxGold;
        }
        
        // top to bottom memoized
        private int maxGoldUtilMemoized(int[][] gm, int i, int j, int[][] mem) {
            // base condition
            if (j == C-1) return gm[i][j];
            
            if (mem[i][j] != -1)
                return mem[i][j];
            
            int ru = (i > 0) ? maxGoldUtilMemoized(gm, i-1, j+1, mem) : 0; // right up
            int r = maxGoldUtilMemoized(gm, i, j+1, mem); // right
            int rd = (i < R-1) ? maxGoldUtilMemoized(gm, i+1, j+1, mem) : 0; // right down
            
            mem[i][j] = gm[i][j] + max(ru, max(r, rd));
            return mem[i][j];
        }
        
        // bottom-up tabulation
        // TC: O(RC), SC: O(RC)
        public int maxGoldBottomUp(int[][] gm) {
            R = gm.length;
            if (R == 0) throw new IllegalArgumentException("empty mine");
            C = gm[0].length;
            
            // matrix to store intermediate results
            int[][] dp = new int[R][C];
            
            for (int i = 0; i < R; ++i) dp[i][C-1] = gm[i][C-1];
            
            for (int j = C-2; j >= 0; --j) {
                for (int i = 0; i < R; ++i) {
                    int ru = (i > 0) ? dp[i-1][j+1] : 0;
                    int r = dp[i][j+1];
                    int rd = (i < R-1) ? dp[i+1][j+1] : 0;
                    
                    dp[i][j] = gm[i][j] + max(ru, max(r, rd));
                }
            }
            
            int maxGold = 0;
            for (int i = 0; i < R; ++i)
                maxGold = max(maxGold, dp[i][0]);
            
            return maxGold;
        }
        
        // TC: O(RC), SC: O(RC)
        public String maxGoldBottomUpWithPath(int[][] gm) {
            R = gm.length;
            if (R == 0) throw new IllegalArgumentException("empty mine");
            C = gm[0].length;
            
            // matrix to store intermediate results
            int[][] dp = new int[R][C];
            int[][] next = new int[R][C];
            
            for (int i = 0; i < R; ++i) {
                dp[i][C-1] = gm[i][C-1];
                next[i][C-1] = -1; // to indicate end of path
            }
            
            for (int j = C-2; j >= 0; --j) {
                for (int i = 0; i < R; ++i) {
                    // right
                    int mv = dp[i][j+1]; // max value
                    int nr = i; // next row
                    
                    // right up
                    if (i > 0 && dp[i-1][j+1] > mv) {
                        mv = dp[i-1][j+1];
                        nr = i-1;
                    }
                    
                    // right down
                    if (i < R-1 && dp[i+1][j+1] > mv) {
                        mv = dp[i+1][j+1];
                        nr = i+1;
                    }
                    
                    dp[i][j] = gm[i][j] + mv;
                    next[i][j] = nr;
                }
            }
            
            int maxGold = 0, startRow = 0;
            for (int i = 0; i < R; ++i) {
                if (dp[i][0] > maxGold) {
                    maxGold = dp[i][0];
                    startRow = i;
                }
            }
            
            String path = reconstructPath(next, startRow);
            return String.format("Max Gold: %d, Path: %s", maxGold, path);
        }
        
        private String reconstructPath(int[][] next, int sr) {
            StringJoiner joiner = new StringJoiner(" -> ");
            for (int i = sr, j = 0; i != -1; i = next[i][j], j++)
                joiner.add(String.format("(%d, %d)", i, j));
            
            return joiner.toString();
        }
    }
    
    public static void main(String[] args) {
        GoldMineProblem solver = new GoldMineProblem();
        int[][] goldMine = {
                             {1, 3, 1, 5}, 
                             {2, 2, 4, 1}, 
                             {5, 0, 2, 3}, 
                             {0, 6, 1, 2}
                           };
        
        System.out.println(solver.new BruteForce().maxGold(goldMine)); // 16
        System.out.println(solver.new DPSolution().maxGoldMemoized(goldMine)); // 16
        System.out.println(solver.new DPSolution().maxGoldBottomUp(goldMine)); // 16
        
        System.out.println(solver.new DPSolution().maxGoldBottomUpWithPath(goldMine)); 
        // Max Gold: 16, Path: (2, 0) -> (1, 1) -> (1, 2) -> (0, 3)
    }

}
