package dynamicprogramming.hard;

// https://leetcode.com/problems/regular-expression-matching/
    
public class RegularExpressionMatching {

    class SimpleRecursiveSolution {
        // T(n): Exponential
        public boolean isMatch(String s, String p) {
            return isMatch(s, p, 0, 0);
        }

        private boolean isMatch(String s, String p, int i, int j) {
            // Base case:
            if (j == p.length())
                return i == s.length();
            
            boolean firstMatch = (i < s.length()) && 
                                 (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');
            if (j+1 < p.length() && p.charAt(j+1) == '*')
                return isMatch(s, p, i, j+2) || (firstMatch && isMatch(s, p, i+1, j));
            else
                return firstMatch && isMatch(s, p, i+1, j+1);
        }
    }
    
    class DPSolution {
        // T(n): O(n*m), S(n): O(n*m)
        public boolean isMatch(String s, String p) {
            int n = s.length(), m = p.length();
            boolean[][] dp = new boolean[n+1][m+1];
            
            dp[n][m] = true;
            for (int i = 0; i < n; i++)
                dp[i][m] = false;
            
            for (int i = n; i >= 0; i--) {
                for (int j = m-1; j >= 0; j--) {
                    boolean firstMatch = (i < n) && 
                                         (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');
                    if (j+1 < m && p.charAt(j+1) == '*')
                        dp[i][j] = dp[i][j+2] || (firstMatch && dp[i+1][j]);
                    else
                        dp[i][j] = (firstMatch && dp[i+1][j+1]);
                }
            }
            return dp[0][0];
        }
    }
    
    public static void main(String[] args) {
        String s = "aab";
        String p = "c*a*b";
        RegularExpressionMatching o = new RegularExpressionMatching();
        System.out.println(o.new SimpleRecursiveSolution().isMatch(s, p)); // true
        System.out.println(o.new DPSolution().isMatch(s, p)); // true
    }
}
