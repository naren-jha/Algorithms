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
            if (n == 0) return 0;
            
            int res = n;
            for (int x = 1; x*x <= n; x++)
                res = Math.min(res, 1 + minSquares(n - x*x));
            return res;
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(n*sqrt(n)), S(n): O(n)
        public int minSquares(int n) {
            int[] dp = new int[n+1];
            dp[0] = 0;
            
            for (int i = 1; i <= n; i++) {
                dp[i] = i;
                for (int x = 1; x*x <= i; x++)
                    dp[i] = Math.min(dp[i], 1 + dp[i - x*x]);
            }
            return dp[n];
        }
    }
    
    /*
     * There is a O(sqrt(n)) time solution to this problem.
     * 
     * Every natural number can be decomposed into sum of squares of at most 4 other natural numbers.
     * So the answer to this problem will never be more than 4. That means the answer will always be
     * either 1 or 2 or 3 or 4
     * 
     * If n can be expressed in the form 4^k(8m + 7), then n can be decomposed into sum of squares of
     * exactly 4 numbers (and not less), where k and m are also integers.
     * 
     * If n can not be expressed in the form 4^k(8m + 7), then n can be decomposed into 
     * sum of squares of at most 3 natural numbers. That means the answer will be
     * either 1 or 2 or 3
     * 
     * Checking for 1 is easy, just check if n is a perfect square or not
     * 
     * To check for 2, we will have to perform one round of enumeration
     * 
     * If the answer is neither 1 nor 2, then it must be 3
     * 
     */
    public int minSquares(int n) {
        // check for 4^k(8m + 7) form
        while (n % 4 == 0) n /= 4;
        if (n % 8 == 7) return 4;
        
        // answer is either 1 or 2 or 3
        // is answer 1
        if (isSquare(n)) return 1;
        
        // is answer 2
        for (int i = 1; i*i < n; ++i)
            if (isSquare(n - i*i))
                return 2;
        
        return 3;
    }
    
    private boolean isSquare(int n) {
        int sqrt = (int) Math.sqrt(n);
        return n == sqrt*sqrt;
    }
    
    public static void main(String[] args) {
        SquareSumToNumber o = new SquareSumToNumber();
        int n = 6;
        
        System.out.println(o.new SimpleRecursiveSolution().minSquares(n)); // 3
        System.out.println(o.new DPSolution().minSquares(n)); // 3
        System.out.println(o.minSquares(n)); // 3
        
        n = 127;
        System.out.println(o.new DPSolution().minSquares(n)); // 4
        System.out.println(o.minSquares(n)); // 4
    }
}
