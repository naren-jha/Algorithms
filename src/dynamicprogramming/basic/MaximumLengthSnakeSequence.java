package dynamicprogramming.basic;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import java.util.StringJoiner;

// https://www.geeksforgeeks.org/find-maximum-length-snake-sequence/

public class MaximumLengthSnakeSequence {
    
    // Recurrence
    int f(int[][] mat, int i, int j) {
        if (i == mat.length-1 && j == mat[0].length-1) return 0; // 2 cells form snake of length 1
        
        int down = 0;
        if (i != mat.length-1) {
            down = f(mat, i+1, j);
            if (abs(mat[i][j] - mat[i+1][j]) == 1) down += 1;
        }
        
        int right = 0;
        if (j != mat[0].length-1) {
            right = f(mat, i, j+1);
            if (abs(mat[i][j] - mat[i][j+1]) == 1) right += 1;
        }
        
        return max(down, right);
    }
    
    public void findMaxLengthSnakeSequence(int[][] mat) {
        int r = mat.length;
        if (r == 0)
            return;
        int c = mat[0].length;
        
        int[][] dp = new int[r][c];
        
        dp[r-1][c-1] = 0; // redundant in java
        
        // fill the lookup matrix by calculating max length
        // snake sequence possible for each location
        int maxLength = 0;
        int maxRow = 0, maxCol = 0;
        for (int i = r-1; i >= 0; --i) {
            for (int j = c-1; j >= 0; --j) {
                if (i == r-1 && j== c-1) continue;
                
                int down = 0;
                if (i != r-1) {
                    down = dp[i+1][j];
                    if (abs(mat[i][j] - mat[i+1][j]) == 1) down += 1;
                }
                
                int right = 0;
                if (j != c-1) {
                    right = dp[i][j+1];
                    if (abs(mat[i][j] - mat[i][j+1]) == 1) right += 1;
                }
                
                dp[i][j] = max(down, right);
                
                if (dp[i][j] > maxLength) {
                    maxLength = dp[i][j];
                    maxRow = i; maxCol = j;
                }
            }
        }
        
        System.out.println("Max length of snake sequnence is " + maxLength);
        
        printSequence(mat, dp, maxRow, maxCol);
    }
    
    private void printSequence(int[][] mat, int[][] dp, int i, int j) {
        StringJoiner joiner = new StringJoiner(" -> ");
        int r = mat.length, c = mat[0].length;
        
        while (i != r-1 || j != c-1) {
            joiner.add(String.format("(%d, %d)", i, j));
            
            // if it can only go right
            if (i == r-1) {
                j++;
            }
            
            // if it can only go down
            else if (j == c-1) {
                i++;
            }
            
            // can go in both direction
            else {
                int down = dp[i+1][j];
                int right = dp[i][j+1];
                
                if (abs(mat[i][j] - mat[i+1][j]) == 1 && down >= right) i++;
                else j++;
            }
        }
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
         * {7, 6, 5, 3}, 
           {6, 5, 4, 3}, 
           {2, 2, 2, 2}, 
           {1, 1, 1, 1},
         */
        MaximumLengthSnakeSequence solver = new MaximumLengthSnakeSequence();
        
        System.out.println(solver.f(mat, 0, 0)); // 6
        
        solver.findMaxLengthSnakeSequence(mat);
        // Max length of snake sequnence is 6
        // Path: (0, 0) -> (1, 0) -> (1, 1) -> (1, 2) -> (1, 3) -> (2, 3) -> (3, 3)
    }

}
