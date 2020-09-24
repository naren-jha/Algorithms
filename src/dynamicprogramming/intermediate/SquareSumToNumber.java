package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/minimum-number-of-squares-whose-sum-equals-to-given-number-n/
// https://leetcode.com/problems/perfect-squares/

public class SquareSumToNumber {
    
    // there is a O(sqrt(n)) time solution to this problem
    // checkout approach 5 on leetcode

    class SimpleRecursiveSolution {
        // T(n): Exp
        public int minSquares(int n) {
            // Base case:
            if (n <= 3)
                return n;
            
            int res = n;
            for (int x = 1; x <= Math.sqrt(n); x++)
                res = Math.min(res, 1 + minSquares(n - x*x));
            return res;
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(n*sqrt(n)), S(n): O(n)
        public int minSquares(int n) {
            int[] dp = new int[n+1];
            dp[0] = 0; dp[1] = 1; dp[2] = 2; dp[3] = 3;
            
            for (int i = 4; i <= n; i++) {
                dp[i] = i;
                for (int x = 1; x <= Math.sqrt(i); x++)
                    dp[i] = Math.min(dp[i], 1 + dp[i - x*x]);
            }
            return dp[n];
        }
    }
    
    public static void main(String[] args) {
        SquareSumToNumber o = new SquareSumToNumber();
        int n = 6;
        
        System.out.println(o.new SimpleRecursiveSolution().minSquares(n)); // 3
        System.out.println(o.new DPSolution().minSquares(n)); // 3
    }
}
