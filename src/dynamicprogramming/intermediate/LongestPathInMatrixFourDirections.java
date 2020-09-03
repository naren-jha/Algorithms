package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/find-the-longest-path-in-a-matrix-with-given-constraints/

public class LongestPathInMatrixFourDirections {
    
    int n;
    
    // T(n): O(n^2), as problem for each cell is solved only once
    public int findLongestPath(int[][] mat) {
        n = mat.length; 
        
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++)
            Arrays.fill(dp[i], -1);
        
        int result = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == -1)
                    dp[i][j] = findLongestPathForCell(mat, i, j, dp);
                result = Math.max(result, dp[i][j]);
            }
        }
        return result;
    }
    
    private int findLongestPathForCell(int[][] mat, int r, int c, int[][] dp) {
        if (dp[r][c] != -1)
            return dp[r][c];
        
        if (r > 0 && (mat[r][c] + 1) == mat[r-1][c])
            return dp[r][c] = 1 + findLongestPathForCell(mat, r-1, c, dp);
        if (r < n-1 && (mat[r][c] + 1) == mat[r+1][c])
            return dp[r][c] = 1 + findLongestPathForCell(mat, r+1, c, dp);
        if (c > 0 && (mat[r][c] + 1) == mat[r][c-1])
            return dp[r][c] = 1 + findLongestPathForCell(mat, r, c-1, dp);
        if (c < n-1 && (mat[r][c] + 1) == mat[r][c+1])
            return dp[r][c] = 1 + findLongestPathForCell(mat, r, c+1, dp);
        return dp[r][c] = 1;
    }
    
    public static void main(String[] args) {
        LongestPathInMatrixFourDirections o = new LongestPathInMatrixFourDirections();
        int[][]  mat = { {1, 2, 9}, 
                         {5, 3, 8}, 
                         {4, 6, 7} };
        System.out.println(o.findLongestPath(mat)); // 4
    }
}
