package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/maximum-path-sum-triangle/
/*
 * Input : 
       3
      7 4
     2 4 6
    8 5 9 3
    
    Output : 23
    Explanation : 3 + 7 + 4 + 9 = 23 
 */
public class MaxPathSumTriangle {
    
    public int maxPathSum(int[][] t) {
        int n = t.length;
        int[][] res = new int[n][];
        
        int col = t[n-1].length;
        res[n-1] = new int[col];
        // initialize last row
        for (int j = 0; j < t[n-1].length; j++)
            res[n-1][j] = t[n-1][j];
        
        for (int i = n-2; i >= 0; i--) {
            col = t[i].length;
            res[i] = new int[col];
            for (int j = 0; j < col; j++) {
                res[i][j] = Math.max(res[i+1][j], res[i+1][j+1]) + t[i][j];
            }
        }
        return res[0][0];
    }
    
    public int maxPathSumSpaceOptimized(int[][] t) {
        int n = t.length;
        int[] res = new int[n];
        
        // first, calculate for bottom most row
        for (int j = 0; j < t[n-1].length; j++)
            res[j] = t[n-1][j];
        
        // calculate for rest of the rows bottom-up
        for (int i = n-2; i >= 0; i--) {
            for (int j = 0; j < t[i].length; j++) {
                res[j] = Math.max(res[j], res[j+1]) + t[i][j];
            }
        }
        
        return res[0];
    }
    
    public static void main(String[] args) {
        int[][] t = {
                {3},
                {7, 4},
                {2, 4, 6},
                {8, 5, 9, 3}
        };
        
        MaxPathSumTriangle o = new MaxPathSumTriangle();
        System.out.println(o.maxPathSum(t)); // 23
        System.out.println(o.maxPathSumSpaceOptimized(t)); // 23
    }
}
