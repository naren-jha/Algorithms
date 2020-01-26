package dynamicprogramming.intermediate;

// https://leetcode.com/problems/range-sum-query-2d-immutable/
// https://www.geeksforgeeks.org/submatrix-sum-queries/

// https://leetcode.com/problems/range-sum-query-2d-immutable/solution/
// https://youtu.be/PwDqpOMwg6U

public class ImmutableRangeSumQuery2D {
    
    int[][] dp;

    public ImmutableRangeSumQuery2D(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0)
            return;
        
        int m = matrix.length, n = matrix[0].length;
        dp = new int[m + 1][n + 1];
        
        // fill first row and first column by 0, redundant in Java
        for (int c = 0; c <= n; c++)
            dp[0][c] = 0;
        for (int r = 0; r <= m; r++)
            dp[r][0] = 0;
        
        // fill rest of the table
        for (int r = 1; r <= m; r++) {
            for (int c = 1; c <= n; c++) {
                dp[r][c] = dp[r][c-1] + dp[r-1][c] - dp[r-1][c-1] + matrix[r-1][c-1];
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        row1++; col1++; row2++; col2++;
        return dp[row2][col2] - dp[row1-1][col2] - dp[row2][col1-1] + dp[row1-1][col1-1];
    }
    
    /*
     * Time complexity : O(1) time per query, O(mn) time pre-computation. The pre-computation 
     * in the constructor takes O(mn) time. Each sumRegion query takes O(1) time
     * 
     * Space complexity : O(mn). The algorithm uses O(mn) space to store the cumulative 
     * region sum
     */
    
    public static void main(String[] args) {
        int[][] matrix = {
                            {3, 0, 1, 4, 2},
                            {5, 6, 3, 2, 1},
                            {1, 2, 0, 1, 5},
                            {4, 1, 0, 1, 7},
                            {1, 0, 3, 0, 5}
                         };
        ImmutableRangeSumQuery2D obj = new ImmutableRangeSumQuery2D(matrix);
        System.out.println(obj.sumRegion(2, 1, 4, 3)); // 8
        System.out.println(obj.sumRegion(1, 1, 2, 2)); // 11
        System.out.println(obj.sumRegion(1, 2, 2, 4)); // 12
    }
}
