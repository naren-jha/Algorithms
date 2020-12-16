package dynamicprogramming.basic;

import static java.lang.Math.max;

// https://www.geeksforgeeks.org/maximum-subsequence-sum-such-that-no-three-are-consecutive/

public class MaximumSumNoThreeAdjacent {
    
    class SimpleRecursiveSolution {
        
        // TC: O(3^n), SC: O(n)
        private int f(int[] a, int n) {
            if (n == 0) return 0;
            if (n == 1) return a[0];
            if (n == 2) return a[0] + a[1];
            
            return max(a[n-1] + a[n-2] + f(a, n-3),
                       max(a[n-1] + f(a, n-2), f(a, n-1)));
        }
        
    }

    class DPSolution {
        // bottom-up tabulation
        // TC: O(n), SC: O(n)
        public int maxSum(int[] a) {
            int n = a.length;
            if (n == 0) return 0;
            if (n == 1) return a[0];
            
            int[] dp = new int[n+1];
            dp[0] = 0; dp[1] = a[0]; dp[2] = a[0] + a[1];
            
            for (int i = 3; i <= n; i++)
                dp[i] = max(a[i-1] + a[i-2] + dp[i-3], 
                            max(a[i-1] + dp[i-2], dp[i-1]));
                
            return dp[n];
        }
        
        // TC: O(n), SC: O(1)
        public int maxSumSpaceOptimized(int[] a) {
            int n = a.length;
            if (n == 0) return 0;
            if (n == 1) return a[0];
            
            int i3 = 0, i2 = a[0], i1 = a[0] + a[1], t;
            for (int i = 3; i <= n; i++) {
                t = max(a[i-1] + a[i-2] + i3, max(a[i-1] + i2, i1));
                i3 = i2;
                i2 = i1;
                i1 = t;
            }
                
            return i1;
        }
    }
    
    public static void main(String[] args) {
        int[] a = { 100, 1000, 100, 1000, 1 };
        MaximumSumNoThreeAdjacent solver = new MaximumSumNoThreeAdjacent();
        System.out.println(solver.new SimpleRecursiveSolution().f(a, a.length)); // 2101
        System.out.println(solver.new DPSolution().maxSum(a)); // 2101
        System.out.println(solver.new DPSolution().maxSumSpaceOptimized(a)); // 2101
    }
}