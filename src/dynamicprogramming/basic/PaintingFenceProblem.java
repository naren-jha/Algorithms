package dynamicprogramming.basic;

import java.util.Arrays;

/*
 * https://www.youtube.com/watch?v=deh7UpSRaEY
 * https://www.geeksforgeeks.org/painting-fence-algorithm/
 */

public class PaintingFenceProblem {
    
    public int countWays(int n, int k) {
        if (n <= 0 || k <= 0) return 0;
        if (n == 1) return k;
        
        // coloring first two post
        // we can color is either same or different
        int same = k*1; // k choices for first post and 1 choice for second
        int different = k*(k-1); // k choices for first post and k-1 choices for second
        
        // then solve from post 3 to n
        for (int i = 3; i <= n; ++i) {
            int sameCopy = same;
            same = different*1; // if last 2 posts were painted same, we cannot paint current post same again. But if last 2 posts were painted different, then we can paint current post with immediate previous color
            different = sameCopy*(k-1) + different*(k-1); // whether last 2 posts were painted same or different, we have k-1 choices for the current post
        }
        
        return same + different;
    }
    
    // same approach as previous one, but loop
    // starts from 2 instead of 3
    public int countWays2(int n, int k) {
        if (n <= 0)
            return 0;
        
        int same = 0, diff = k;
        int prevDiff;
        for(int i = 2; i <= n; i++) {
            prevDiff = diff;
            diff = (same + diff) * (k - 1);
            same = prevDiff;
        }
        return same + diff;
    }
    
    // new approach - 23/08/2020
    // Time and Space: O(3*n) = O(n)
    public int paintFence(int n, int k) {
        if (n == 0 || k == 0) return 0;
        
        int[][] dp = new int[n+1][3];
        for (int i = 1; i <= n; ++i)
            Arrays.fill(dp[i], -1);
        
        return paintFenceUtil(n, k, 0, dp);
    }
    
    private int paintFenceUtil(int n, int k, int repeatCount, int[][] dp) {
        if (n == 0) return 1;
        
        if (dp[n][repeatCount] != -1) return dp[n][repeatCount];
        
        int res;
        if (repeatCount == 0) {
            res = k * paintFenceUtil(n-1, k, 1, dp); // color first post with any of the k colors
        }
        else if (repeatCount == 1) {
            res = paintFenceUtil(n-1, k, 2, dp) + // either repeat the color to make pair
                    (k-1) * paintFenceUtil(n-1, k, 1, dp); // or color without repeating
        }
        else /*if (repeatCount == 2)*/ {
            res = (k-1) * paintFenceUtil(n-1, k, 1, dp); // use any of the remaining k-1 colors
        }
        
        return dp[n][repeatCount] = res;
    }
    
    // new approach - bottom-up tabulation
    // Time and Space: O(3*n) = O(n)
    public int paintFenceBottomUp(int n, int k) {
        if (n == 0 || k == 0) return 0;
        
        int[][] dp = new int[n+1][3];
        Arrays.fill(dp[0], 1);
        
        for (int i = 1; i <= n; ++i) {
           for (int j = 0; j < 3; ++j) {
               if (j == 0) dp[i][j] = k * dp[i-1][1];
               if (j == 1) dp[i][j] = dp[i-1][2] + (k-1) * dp[i-1][1];
               if (j == 2) dp[i][j] = (k-1) * dp[i-1][1];
           }
        }
        
        return dp[n][0];
    }
    
    // an even better way: https://leetcode.com/problems/paint-fence/discuss/178010/The-only-solution-you-need-to-read
    // ================================================================================================================
    
    public static void main(String[] args) {
        PaintingFenceProblem obj = new PaintingFenceProblem();
        int n = 3, k = 2;
        System.out.println(obj.countWays(n, k));
        System.out.println(obj.countWays2(n, k));
        
        System.out.println(obj.paintFence(n, k));
        System.out.println(obj.paintFenceBottomUp(n, k));
    }

}
