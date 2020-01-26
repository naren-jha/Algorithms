package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/find-length-longest-subsequence-one-string-substring-another-string/

public class LCSubSeqSubStr {

    // returns length of longest subsequence of string X 
    // which is substring in string Y. T(m,n) = O(m*n)
    public static int len(String x, String y) {
        char[] xa = x.toCharArray();
        char[] ya = y.toCharArray();
        
        int n = xa.length;
        int m = ya.length;
        
        int[][] dp = new int[n+1][m+1];
        
        for (int i = 0; i <= n; i++)
            dp[i][0] = 0;
        for (int j = 0; j <= m; j++)
            dp[0][j] = 0;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (xa[i-1] == ya[j-1])
                    dp[i][j] = 1 + dp[i-1][j-1];
                else
                    dp[i][j] = dp[i-1][j]; 
                    // As we are taking subsequence of x but substring of y
            }
        }
        
        return Arrays.stream(dp[n]).max().getAsInt();
    }
    
    public static void main(String[] args) {
        System.out.println(len("ABCD", "BACDBDCD")); // 3
    }
}
