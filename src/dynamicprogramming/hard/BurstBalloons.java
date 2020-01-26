package dynamicprogramming.hard;

// https://leetcode.com/problems/burst-balloons/

// Explanations:
// https://youtu.be/IFNibRVgFBo
// https://leetcode.com/problems/burst-balloons/discuss/76228/Share-some-analysis-and-explanations
// https://leetcode.com/problems/burst-balloons/discuss/76229/For-anyone-that-is-still-confused-after-reading-all-kinds-of-explanations...
// https://leetcode.com/problems/burst-balloons/discuss/76230/C%2B%2B-dp-detailed-explanation
// https://leetcode.com/problems/burst-balloons/discuss/76275/Java-Solution-with-Explanations

public class BurstBalloons {
    
    // This problem is similar to MatrixChainMultiplication
    
    // T(n): O(n^3), S(n): O(n^2)
    public static int maxCoins(int[] nums) {
        int n = nums.length;
        
        // dp[i][j] stores maximum coin that can be obtained by bursting balloons from i...j
        int[][] dp = new int[n][n];
        
        int leftVal, rightVal, before, after, coins;
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                
                // choose the best option for 'last balloon to burst'
                for (int k = i; k <= j; k++) {
                    leftVal = (i == 0) ? 1 : nums[i-1];
                    rightVal = (j == n-1) ? 1 : nums[j+1];
                    
                    before = (k == i) ? 0 : dp[i][k-1];
                    after = (k == j) ? 0 : dp[k+1][j];
                    
                    coins = (leftVal * nums[k] * rightVal) + before + after;
                    dp[i][j] = Math.max(dp[i][j], coins);
                }
            }
        }
        
        return dp[0][n-1];
    }
    
    // Below is a Top-down memoization approach discussed at:
    // https://leetcode.com/problems/burst-balloons/discuss/76228/Share-some-analysis-and-explanations
    public static int maxCoinsMem(int[] iNums) {
        int n = iNums.length;
        
        // create a new array, and pad a 1 to either side, 
        // with rest of the element being same as input array
        int[] nums = new int[n+2];
        for (int i = 0; i < n; i++)
            nums[i+1] = iNums[i];
        nums[0] = nums[n+1] = 1; // pad a 1 to either side
        n = nums.length; // n = n + 2

        int[][] memo = new int[n][n];
        return burst(memo, nums, 0, n - 1);
    }

    public static int burst(int[][] memo, int[] nums, int left, int right) {
        // base case: when only the two padded 1's are left
        if (left + 1 == right)
            return 0;
        
        // if result is already calculated before
        if (memo[left][right] > 0)
            return memo[left][right];
        
        int ans = 0, coins;
        for (int i = left + 1; i < right; ++i) {
            coins = (nums[left] * nums[i] * nums[right]) 
                        + burst(memo, nums, left, i) + burst(memo, nums, i, right);
            ans = Math.max(ans, coins);
        }
        
        return memo[left][right] = ans;
    }
    
    public static void main(String[] args) {
        int[] nums = {3,1,5,8};
        System.out.println(maxCoins(nums)); // 167
        System.out.println(maxCoinsMem(nums)); // 167
    }

}
