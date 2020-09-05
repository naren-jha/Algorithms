package dynamicprogramming.hard;

// https://leetcode.com/problems/regular-expression-matching/
    
public class RegularExpressionMatching {
    
    // Aug 2020
    public boolean match(String s, String p) {
        return match(s.toCharArray(), p.toCharArray(), s.length(), p.length());
    }
    
    private boolean match(char[] s, char[] p, int sLen, int pLen) {
        //System.out.println("sLen: " + sLen + ", pLen: " + pLen);
        if (pLen == 0)
            return sLen == 0;
        if (sLen == 0) {
            if (p[pLen-1] == '*') {
                /*if (pLen == 1) return true/false; // there shouldn't be just one '*' left in the pattern string, but if there is - we can consider that either it'll match with the empty input string or it'll not match with empty string
                else return match(s, p, 0, pLen-2);*/
                return match(s, p, 0, pLen-2);
            }
            else {
                return false;
            }
        }

        if (s[sLen-1] == p[pLen-1] || p[pLen-1] == '.')
            return match(s, p, sLen-1, pLen-1);

        if (p[pLen-1] == '*') {
            //if (pLen == 1) return true/false; // there shouldn't be just one '*' left in the pattern string, but if there is - we can consider that either it'll match with everything left in the input string or it'll not match with anything
            boolean status = match(s, p, sLen, pLen-2); // without considering this wildcard
            if (p[pLen-2] == s[sLen-1] || p[pLen-2] == '.')
                status = status || match(s, p, sLen-1, pLen); // consider this wildcard (and repeat)
            return status;
        }

        return false;
    }

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
        //String s = "aab";
        //String p = "c*a*b"; // true
        
        String s = "mississippi";
        String p = "mis*is*ip*."; // true
        
        //String s = "mississippi";
        //String p = "mis*is*p*."; // false
        
        //String s = "mississippi";
        //String p = ".*"; // true
        
        RegularExpressionMatching o = new RegularExpressionMatching();
        System.out.println(o.new SimpleRecursiveSolution().isMatch(s, p)); // true
        System.out.println(o.new DPSolution().isMatch(s, p)); // true
        
        System.out.println(o.match(s, p)); // true
    }
}
