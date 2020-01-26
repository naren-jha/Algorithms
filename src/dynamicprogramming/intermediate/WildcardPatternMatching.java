package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/wildcard-pattern-matching/

public class WildcardPatternMatching {

    class SimpleRecursiveSolution {
        // T(n): Exp
        public boolean match(String s, String p) {
            return match(s.toCharArray(), p.toCharArray(), s.length(), p.length());
        }
        
        private boolean match(char[] sa, char[] pa, int sLen, int pLen) {
            // Base cases - start -
            /* When pattern is exhausted, to have a successful match, 
             * input string should also be exhausted */
            if (pLen == 0)
                return sLen == 0;
            
            /* When pattern is not exhausted, but input string is exhausted */
            if (sLen == 0)
                if (pa[pLen-1] == '*')
                    return match(sa, pa, 0, pLen-1);
                else
                    return false;
            // Base cases - end -
            
            
            /* Current characters are considered as matching in two cases:
             * (a) current character of pattern is '?' 
             * (b) characters actually match */
            if (pa[pLen-1] == '?' || (pa[pLen-1] == sa[sLen-1]))
                return match(sa, pa, sLen-1, pLen-1);
            
            /* If we encounter '*', two cases are possible:
             * a) We ignore '*' and move to next character in the pattern,
             *    i.e., '*' indicates an empty sequence.
             * b) '*' character matches with ith character in input 
             * 
             * For a wildcard character, it is not the case that when we substitute 
             * it with a matching character in text then the wildcard will be exhausted.
             * Hence instead of going (sLen-1, pLen-1) we go upwards to (sLen-1, pLen) 
             * to consider the case where the wildcard might match further more previous 
             * text characters. */  
            else if (pa[pLen-1] == '*')
                return match(sa, pa, sLen, pLen-1) || match(sa, pa, sLen-1, pLen);
            
            else
                return false;
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n) = O(sLen*pLen), S(n) = O(sLen*pLen)
        public boolean match(String s, String p) {
            char[] sa = s.toCharArray();
            char[] pa = p.toCharArray();
            
            int sLen = sa.length;
            int pLen = pa.length;
            
            boolean[][] dp = new boolean[sLen+1][pLen+1];
            
            dp[0][0] = true;
            for (int i = 1; i <= sLen; i++)
                dp[i][0] = false;
            
            for (int j = 1; j <= pLen; j++)
                if (pa[j-1] == '*')
                    dp[0][j] = dp[0][j-1];
                else
                    dp[0][j] = false;
            
            for (int i = 1; i <= sLen; i++) {
                for (int j = 1; j <= pLen; j++) {
                    if (pa[j-1] == '?' || (pa[j-1] == sa[i-1]))
                        dp[i][j] = dp[i-1][j-1];
                    else if (pa[j-1] == '*')
                        dp[i][j] = dp[i][j-1] || dp[i-1][j];
                    else
                        dp[i][j] = false;
                }
            }
            return dp[sLen][pLen];
        }
    }
    
    public static void main(String[] args) {
        WildcardPatternMatching o = new WildcardPatternMatching();
        
        String s = "baaabab";
        String p = "*****ba*****ab"; // true
        // String p = "ba*****ab"; // true
        // String p = "ba*ab"; // true
        // String p = "a*ab"; // false
        // String p = "a*****ab"; // false
        // String p = "*a*****ab"; // true
        // String p = "ba*ab****"; // true
        // String p = "****"; // true
        // String p = "*"; // true
        // String p = "aa?ab"; // false
        // String p = "b*b"; // true
        // String p = "a*a"; // false
        // String p = "baaabab"; // true
        // String p = "?baaabab"; // false
        // String p = "*baaaba*"; // true
        // String p = "*aa?ab"; // true
        System.out.println(o.new SimpleRecursiveSolution().match(s, p)); // true
        System.out.println(o.new DPSolution().match(s, p)); // true
    }
}
