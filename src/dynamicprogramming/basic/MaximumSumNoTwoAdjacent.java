package dynamicprogramming.basic;

import static java.lang.Math.max;

// https://www.geeksforgeeks.org/find-maximum-possible-stolen-value-houses/
// https://www.geeksforgeeks.org/maximum-sum-such-that-no-two-elements-are-adjacent/

public class MaximumSumNoTwoAdjacent {

    /*
     * 1. If last element is included, then max sum will be
     * maxSum(a, n) = a[n-1] + maxSum(a, n-2)
     * this is because, if last element is included then next
     * adjacent element cannot be include.
     * 
     * 2. If last element is not included, then max sum will be
     * maxSum(a, n) = maxSum(a, n-1)
     * 
     * Combining 1 and 2, we get recurrence equation
     * maxSum(a, n) = max(a[n-1] + maxSum(a, n-2), maxSum(a, n-1))
     */
    class SimpleRecursiveSolution {
        
        // TC: O(2^n), SC: O(n)
        private int maxSum(int[] a, int n) {
            if (n == 0) return 0;
            if (n == 1) return a[0];
            
            return max(a[n-1] + maxSum(a, n-2), maxSum(a, n-1));
        }
    }
    
    class DPSolution {
        // bottom-up tabulation
        // TC: O(n), SC: O(n)
        public int maxSum(int[] a) {
            int n = a.length;
            if (n == 0) return 0;
            
            int[] dp = new int[n+1];
            dp[0] = 0; dp[1] = a[0];
            
            for (int i = 2; i <= n; i++)
                dp[i] = max(a[i-1] + dp[i-2], dp[i-1]);
            
            return dp[n];
        }
        
        /*
         * Since we need only two immediate previous indices to calculate each dp[i],
         * so we can manage with just two separate variables. See the solution below.
         */
        // TC: O(n), SC: O(1)
        public int maxSumSpaceOptimized(int[] a) {
            int n = a.length;
            if (n == 0) return 0;
            
            int i2 = 0, i1 = a[0], t;
            for (int i = 2; i <= n; i++) {
                t = max(a[i-1] + i2, i1);
                i2 = i1;
                i1 = t;
            }
            
            return i1;
        }
    }
    
    public static void main(String[] args) {
        int[] a = {5, 5, 10, 100, 10, 5};
        MaximumSumNoTwoAdjacent solver = new MaximumSumNoTwoAdjacent();
        System.out.println(solver.new SimpleRecursiveSolution().maxSum(a, a.length)); // 110
        System.out.println(solver.new DPSolution().maxSum(a)); // 110
        System.out.println(solver.new DPSolution().maxSumSpaceOptimized(a)); // 110
    }
}
