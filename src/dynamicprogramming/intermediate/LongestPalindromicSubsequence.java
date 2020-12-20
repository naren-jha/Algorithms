package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/longest-palindromic-subsequence-dp-12/

public class LongestPalindromicSubsequence {

    /*
     * Let X[0..n-1] be the input sequence of length n and L(0, n-1) be 
     * the length of the longest palindromic subsequence of X[0..n-1]
     * 
     * If first and last characters of X are same, then L(0, n-1) = L(1, n-2) + 2
     * else L(0, n-1) = max(L(1, n-1), L(0, n-2))
     */
    
    class SimpleRecursiveSolution {
        // T(n): Exp
        public int lps(String s) {
            return lps(s, 0, s.length()-1);
        }
        
        private int lps(String s, int si, int ei) {
            if (si == ei) return 1;
            if (ei < si) return 0;
            
            if (s.charAt(si) == s.charAt(ei))
                return lps(s, si+1, ei-1) + 2;
            return Math.max(lps(s, si, ei-1), lps(s, si+1, ei));
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(n^2), S(n): O(n^2)
        public int lps(String s) {
            int n = s.length();
            int[][] dp = new int[n][n];
            
            for (int i = n-1; i >= 0; i--) {
                dp[i][i] = 1;
                for (int j = i+1; j < n; j++) {
                    if (s.charAt(i) == s.charAt(j))
                        dp[i][j] = dp[i+1][j-1] + 2;
                    else
                        dp[i][j] = Math.max(dp[i][j-1], dp[i+1][j]);
                }
            }
            
            return dp[0][n-1];
        }
        
        /*
         * This problem is close to the Longest Common Subsequence (LCS) problem. 
         * In fact, we can use LCS as a subroutine to solve this problem. 
         * Following is the two step solution that uses LCS.
         * 
         * 1) Reverse the given sequence and store the reversed string
         *    in another array say rev[0..n-1]
         * 2) LCS of the given sequence and rev[] will be the longest palindromic subsequence
         * This solution is also a O(n^2) solution.
         * 
         * We can also print the longest palindromic subsequence by printing LCS of 
         * given sequence and rev[]
         */
        
        /*
         * We can space optimize above solution to O(n). See below solution
         */
        // T(n): O(n^2), S(n): O(n)
        public int lpsSO(String s) {
            int n = s.length();
            int[] dp = new int[n];
            
            for (int i = n-1; i >= 0; i--) {
                dp[i] = 1;
                int backUp = 0;
                for (int j = i+1; j < n; j++) {
                    if (s.charAt(i) == s.charAt(j)) {
                        int tmp = dp[j];
                        dp[j] = backUp + 2;
                        backUp = tmp;
                    }
                    else {
                        backUp = dp[j];
                        dp[j] = Math.max(dp[j-1], dp[j]);
                    }
                }
            }
            return dp[n-1];
        }
    }
    
    public static void main(String[] args) {
        LongestPalindromicSubsequence solver = new LongestPalindromicSubsequence();
        String s = "GEEKSFORGEEKS";
        System.out.println(solver.new SimpleRecursiveSolution().lps(s)); // 5
        System.out.println(solver.new DPSolution().lps(s)); // 5
        System.out.println(solver.new DPSolution().lpsSO(s)); // 5
    }
}
