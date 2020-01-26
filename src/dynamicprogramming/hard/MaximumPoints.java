package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/maximum-points-top-left-matrix-bottom-right-return-back/

public class MaximumPoints {
    
    /*
     * Sum of max points collected by two independent paths - 
     * one from "top left to bottom right" and other from "bottom right to top left"
     * does not give us overall optimal solution. So we calculate both the paths 
     * simultaneously. This is needed because the answer for path 2 depends on the 
     * route chosen by path 1. Simultaneous calculations can be done by considering 
     * two paths from (0, 0) to (R-1, C-1) and making four decisions at each 
     * position (two for each).
     */
    
    // TC: O(R^2 * C), SC: O(R^2 * C)
    public int solve(char[][] mat) {
        int R = mat.length;
        int C = (R != 0) ? mat[0].length : 0;
        
        if (R == 0 || C == 0)
            return Integer.MIN_VALUE;
        if (mat[0][0] == '#' || mat[R-1][C-1] == '#')
            return Integer.MIN_VALUE;
        
        int[][][] dp = new int[R][C][R];
        for (int row1 = 0; row1 < R; row1++) {
            for (int col1 = 0; col1 < C; col1++) {
                for (int row2 = 0; row2 < R; row2++) {
                    dp[row1][col1][row2] = -1;
                }
            }
        }
        
        int ans = 0;
        if (mat[0][0] == '*') {
            ans++;
            mat[0][0] = '.';
        }
        if (mat[R-1][C-1] == '*') {
            ans++;
            mat[R-1][C-1] = '.';
        }
        
        ans += solve(mat, R, C, dp, 0, 0, 0);
        return ans;
    }

    public int solve(char[][] mat, int R, int C, int[][][] dp, int row1, int col1, int row2) {
        
        int col2 = (row1 + col1) - row2;
        
        // Base case: If both path reach the bottom right cell
        if (row1 == R-1 && col1 == C-1 && row2 == R-1 && col2 ==C-1)
            return 0;
        
        // If we have already solved this subproblem before
        if (dp[row1][col1][row2] != -1)
            return dp[row1][col1][row2];
        
        // variable for 4 options
        int ch1, ch2, ch3, ch4;
        ch1 = ch2 = ch3 = ch4 = Integer.MIN_VALUE;
        
        // if both path move right
        if (col1+1 < C && col2+1 < C)
            if (mat[row1][col1+1] != '#' && mat[row2][col2+1] != '#')
                ch1 = cost(mat, row1, col1+1, row2, col2+1) 
                        + solve(mat, R, C, dp, row1, col1+1, row2);
        
        // if path1 moves right and path2 moves down
        if (col1+1 < C && row2+1 < R)
            if (mat[row1][col1+1] != '#' && mat[row2+1][col2] != '#')
                ch2 = cost(mat, row1, col1+1, row2+1, col2) 
                        + solve(mat, R, C, dp, row1, col1+1, row2+1);
        
        // if path1 moves down and path2 moves right
        if (row1+1 < R && col2+1 < C)
            if (mat[row1+1][col1] != '#' && mat[row2][col2+1] != '#')
                ch3 = cost(mat, row1+1, col1, row2, col2+1) 
                        + solve(mat, R, C, dp, row1+1, col1, row2);
        
        // if both path move down
        if (row1+1 < R && row2+1 < R)
            if (mat[row1+1][col1] != '#' && mat[row2+1][col2] != '#')
                ch4 = cost(mat, row1+1, col1, row2+1, col2) 
                        + solve(mat, R, C, dp, row1+1, col1, row2+1);
        
        int ans = Math.max(Math.max(ch1, ch2), Math.max(ch3, ch4));
        return dp[row1][col1][row2] = ans;
    }
    
    public int cost(char[][] mat, int row1, int col1, int row2, int col2) {
        // If both path is at same cell
        if (row1 == row2 && col1 == col2) {
            if (mat[row1][col1] == '*')
                return 1;
            else
                return 0;
        }
        
        int point = 0;
        if (mat[row1][col1] == '*')
            point++;
        if (mat[row2][col2] == '*')
            point++;
        return point;
    }
    
    public static void main(String[] args) {
        char[][] mat = {
                { '.', '*', '.', '*', '.' }, 
                { '*', '#', '#', '#', '.' }, 
                { '*', '.', '*', '.', '*' }, 
                { '.', '#', '#', '#', '*' }, 
                { '.', '*', '.', '*', '.' }
        };
        
        MaximumPoints obj = new MaximumPoints();
        System.out.println(obj.solve(mat)); // 8
    }
}
