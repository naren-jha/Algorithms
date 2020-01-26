package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/gold-mine-problem/

public class GoldMineProblem {
    
    private class BruteForce {
        // T(n) = Exponential
        public int getMaxGold(int[][] goldMine) {
            int m = goldMine.length; // row length
            int n = goldMine[0].length; // col length
            
            int maxGold = 0;
            for (int row = 0; row < m; row++) {
                maxGold = Math.max(solveFor(row, 0, m, n, goldMine), maxGold);
            }
            
            return maxGold;
        }
        
        private int solveFor(int row, int col, int m, int n, int[][] goldMine) {
            // base conditions
            if (row < 0 || row > m-1)
                return 0;
            if (col == n-1)
                return goldMine[row][col];
            
            return goldMine[row][col] + Math.max(solveFor(row-1, col+1, m, n, goldMine), Math.max(solveFor(row, col+1, m, n, goldMine), solveFor(row+1, col+1, m, n, goldMine)));
        }
    }
    
    
    private class DPSolution {
        // T(n) = O(mn)
        public int getMaxGoldMemoized(int[][] goldMine) {
            int m = goldMine.length; // row length
            int n = goldMine[0].length; // col length
            
            // create a matrix to store intermediate results
            int[][] maxGoldMat = new int[m][n];
            for (int row = 0; row < m; row++)
                Arrays.fill(maxGoldMat[row],  -1);
            
            int maxGold = 0;
            for (int row = 0; row < m; row++) {
                maxGold = Math.max(solveForMemoized(row, 0, m, n, goldMine, maxGoldMat), maxGold);
            }
            
            return maxGold;
        }
        
        // top to bottom memoized
        private int solveForMemoized(int row, int col, int m, int n, int[][] goldMine, int[][] maxGoldMat) {
            // base conditions
            if (row < 0 || row > m-1)
                return 0;
            if (col == n-1)
                return goldMine[row][col];
            
            if (maxGoldMat[row][col] != -1)
                return maxGoldMat[row][col];
            
            maxGoldMat[row][col] = goldMine[row][col] + Math.max(solveForMemoized(row-1, col+1, m, n, goldMine, maxGoldMat), Math.max(solveForMemoized(row, col+1, m, n, goldMine, maxGoldMat), solveForMemoized(row+1, col+1, m, n, goldMine, maxGoldMat)));
            return maxGoldMat[row][col];
        }
        
        /*
         * In top-to-bottom memoized solution (above), we are going from col = 0 to col = n-1
         * We can also solve this using botton-up tabulation technique, 
         * going from col = n-1 to col = 0. Which should be even cleaner solution.
         * 
         */
        // T(n) = O(mn)
        public int getMaxGoldBottomUp(int[][] goldMine) {
            int m = goldMine.length; // row length
            int n = goldMine[0].length; // col length
            
            // matrix to store intermediate results
            int[][] maxGoldMat = new int[m][n];
            
            int maxGold = 0, right, rightUp, rightDown;
            for (int col = n-1; col >= 0; col--) {
                for (int row = 0; row < m; row++) {
                    rightUp = (col == n-1 || row == 0) ? 0 : maxGoldMat[row - 1][col + 1];
                    right = (col == n-1) ? 0 : maxGoldMat[row][col + 1];
                    rightDown = (col == n-1 || row == m-1) ? 0 : maxGoldMat[row + 1][col + 1];
                    maxGoldMat[row][col] = goldMine[row][col] + Math.max(rightUp, Math.max(right, rightDown));
                    
                    // The max amount of gold collected will be the max value in first column
                    if (col == 0)
                        maxGold = Math.max(maxGoldMat[row][col], maxGold);
                }
            }
            
            return maxGold;
        }
        
        public String getMaxGoldBottomUpWithPath(int[][] goldMine) {
            int m = goldMine.length; // row length
            if (m == 0)
                return "";
            
            int n = goldMine[0].length; // col length
            
            // matrix to store intermediate results
            int[][] maxGoldMat = new int[m][n];
            int[][] maxGoldPathMat = new int[m][n-1];
            
            int maxGold = 0, right, rightUp, rightDown;
            int maxGoldIndex = 0;
            for (int col = n-1; col >= 0; col--) {
                for (int row = 0; row < m; row++) {
                    rightUp = (col == n-1 || row == 0) ? 0 : maxGoldMat[row - 1][col + 1];
                    right = (col == n-1) ? 0 : maxGoldMat[row][col + 1];
                    rightDown = (col == n-1 || row == m-1) ? 0 : maxGoldMat[row + 1][col + 1];
                    
                    int nextMax = Math.max(rightUp, Math.max(right, rightDown));
                    maxGoldMat[row][col] = goldMine[row][col] + nextMax;
                    
                    if (col != n-1) {
                        if (nextMax == rightUp)
                            maxGoldPathMat[row][col] = row - 1;
                        else if (nextMax == right)
                            maxGoldPathMat[row][col] = row;
                        else // rightDown
                            maxGoldPathMat[row][col] = row + 1;
                    }
                    
                    // The max amount of gold collected will be the max value in first column
                    if (col == 0) {
                        if (maxGoldMat[row][col] > maxGold) {
                            maxGold = maxGoldMat[row][col];
                            maxGoldIndex = row;
                        }
                    }
                }
            }
            
            StringBuilder path = new StringBuilder();
            path.append("(").append(maxGoldIndex).append(", ").append(0).append(")");
            for (int col = 0; col < n-1; col++) {
                path.append(" -> (").append(maxGoldPathMat[maxGoldIndex][col]).append(", ").append(col+1).append(")");
                maxGoldIndex = maxGoldPathMat[maxGoldIndex][col];
            }
            return "maxGold: " + maxGold + ", Path: " + path.toString();
        }
    }
    
    public static void main(String[] args) {
        GoldMineProblem obj = new GoldMineProblem();
        
        int[][] goldMine = {
                        {1, 3, 1, 5}, 
                        {2, 2, 4, 1}, 
                        {5, 0, 2, 3}, 
                        {0, 6, 1, 2}
                      };
        
        System.out.println(obj.new BruteForce().getMaxGold(goldMine));
        System.out.println(obj.new DPSolution().getMaxGoldMemoized(goldMine));
        System.out.println(obj.new DPSolution().getMaxGoldBottomUp(goldMine));
        
        System.out.println(obj.new DPSolution().getMaxGoldBottomUpWithPath(goldMine)); 
        // maxGold: 16, Path: (2, 0) -> (1, 1) -> (1, 2) -> (0, 3)
    }

}
