package dynamicprogramming.basic;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.util.Arrays;
import java.util.StringJoiner;

// https://www.geeksforgeeks.org/find-maximum-length-snake-sequence/

public class MaximumLengthSnakeSequence {
    
    // Recurrence
    int f(int[][] mat, int i, int j) {
        if (i == mat.length-1 && j == mat[0].length-1) return 0; // 2 cells form snake of length 1
        
        int right = 0;
        if (j < mat[0].length-1 && abs(mat[i][j] - mat[i][j+1]) == 1)
            right = 1 + f(mat, i, j+1);
        
        int down = 0;
        if (i < mat.length-1 && abs(mat[i][j] - mat[i+1][j]) == 1)
            down = 1 + f(mat, i+1, j);
        
        return max(down, right);
    }
    
    // dp bottom-up
    int R, C;
    public void findMaxLengthSnakeSequence(int[][] mat) {
        R = mat.length;
        if (R == 0) throw new IllegalArgumentException("empty grid.");
        C = mat[0].length;
        
        int[][] dp = new int[R][C];
        
        int maxLen = 0;
        int maxRow = 0, maxCol = 0;
        for (int i = R-1; i >= 0; --i) {
            for (int j = C-1; j >= 0; --j) {
                int right = 0;
                if (j < C-1 && abs(mat[i][j] - mat[i][j+1]) == 1)
                    right = 1 + dp[i][j+1];
                
                int down = 0;
                if (i < R-1 && abs(mat[i][j] - mat[i+1][j]) == 1)
                    down = 1 + dp[i+1][j];
                
                dp[i][j] = max(down, right);
                
                if (dp[i][j] > maxLen) {
                    maxLen = dp[i][j];
                    maxRow = i; maxCol = j;
                }
            }
        }
        
        System.out.println("Max length of snake sequence is " + maxLen);
        //System.out.println(Arrays.deepToString(dp));
        
        printSequence(dp, maxRow, maxCol);
    }
    
    private void printSequence(int[][] dp, int i, int j) {
        StringJoiner joiner = new StringJoiner(" -> ");
        do {
            joiner.add(String.format("(%d, %d)", i, j));
            
            if (i == R-1) j++; // if it can only go right
            else if (j == C-1) i++; // if it can only go down
            else if (dp[i][j+1] > dp[i+1][j]) j++;
            else i++;
            
        } while (dp[i][j] != 0);
        
        joiner.add(String.format("(%d, %d)", i, j)); // add bottom right cell
        
        System.out.println("Path: " + joiner.toString());
    }
    
    public static void main(String[] args) {
        int[][] mat = { 
                         {9, 6, 5, 2}, 
                         {8, 7, 6, 5}, 
                         {7, 3, 1, 6}, 
                         {1, 1, 1, 7}, 
                      };
        /* dp
         * [6, 5, 4, 0]
         * [5, 4, 3, 2]
         * [0, 0, 0, 1]
         * [0, 0, 0, 0]
         */
        MaximumLengthSnakeSequence solver = new MaximumLengthSnakeSequence();
        
        System.out.println(solver.f(mat, 0, 0)); // 6
        
        solver.findMaxLengthSnakeSequence(mat);
        // Max length of snake sequence is 6
        // Path: (0, 0) -> (1, 0) -> (1, 1) -> (1, 2) -> (1, 3) -> (2, 3) -> (3, 3)
    }

}
