package dynamicprogramming.hard;

/**
 * https://leetcode.com/problems/split-array-largest-sum/
 * 
 * Given an array which consists of non-negative numbers, split the array into m non-empty 
 * continuous subarrays. Minimize the largest sum among m subarrays.
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class LargestMinimumSumSplitSubarray {
    
    // Simple Recursion
    // TC: (n^m) as each of the n element can be present or not present in each of m subarrays,
    // and we're trying all such possiblities
    public int splitArr(int[] nums, int m) {
        int n = nums.length;
        int[] ss = new int[n+1]; // subarray sums
        for (int i = 0; i < n; ++i) {
            ss[i+1] = ss[i] + nums[i];
        }
        
        return splitArr(nums, n, m, ss);
    }
    
    int splitArr(int[] nums, int n, int m, int[] ss) {
        if (n == 0 && m == 0) return 0;
        if (n == 0 || m == 0) return Integer.MAX_VALUE;
        
        int minSum = Integer.MAX_VALUE;
        for (int k = 1; k <= n; ++k) { // consider last k elements in the mth subarrays
            int sum_k = Math.max(splitArr(nums, n-k, m-1, ss), ss[n] - ss[n-k]);
            minSum = Math.min(minSum, sum_k);
        }
        return minSum;
    }
    
    // dp bottom-up tabulation
    // TC: O(n^2*m)
    public int splitArray(int[] nums, int m) {
        int n = nums.length;
        int[] ss = new int[n+1]; // subarray sums
        for (int i = 0; i < n; ++i) {
            ss[i+1] = ss[i] + nums[i];
        }
        
        int[][] dp = new int[n+1][m+1];
        // dp[i][j] = min largest sum in nums[0..i] with j subarrays
        
        dp[0][0] = 0;
        for (int j = 1; j <= m; ++j) dp[0][j] = Integer.MAX_VALUE;
        for (int i = 1; i <= n; ++i) dp[i][0] = Integer.MAX_VALUE;
        
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= m; ++j) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = 1; k <= i; ++k) { // consider last k elements in the jth subarrays
                    int sum_k = Math.max(dp[i-k][j-1], ss[i] - ss[i-k]);
                    dp[i][j] = Math.min(dp[i][j], sum_k);
                }
            }
        }
        
        return dp[n][m];
    }
    
    // another approach: O(n*lg(sumOfArray))
    // https://leetcode.com/problems/split-array-largest-sum/discuss/89817/Clear-Explanation%3A-8ms-Binary-Search-Java
    public int splitArrayGreedy(int[] nums, int m) {
        int max = 0, sum = 0;
        for (int num : nums) {
            max = Math.max(max, num);
            sum += num;
        }
        
        int lo = max, hi = sum;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (isValid(nums, m, mid))
                hi = mid - 1;
            else
                lo = mid + 1;
        }
        
        return lo;
    }
    
    boolean isValid(int[] nums, int m, int target) {
        int count = 1, total = 0;
        for (int num : nums) {
            total += num;
            if (total > target) {
                total = num;
                count++;
                if (count > m) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        LargestMinimumSumSplitSubarray solver = new LargestMinimumSumSplitSubarray();
        int[] nums = {7, 2, 5, 10, 8};
        int m = 2;
        
        System.out.println(solver.splitArr(nums, m)); // 18
        System.out.println(solver.splitArray(nums, m)); // 18
        System.out.println(solver.splitArrayGreedy(nums, m)); // 18
    }

}
