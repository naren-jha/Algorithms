package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/longest-palindrome-substring-set-1/

public class LongestPalindromicSubstring {

    // bottom-up tabulation
    // T(n): O(n^2), S(n): O(n^2)
    public int longestPalindromeSubstring(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int maxLen = 1, start = 0;
        
        // len 1 substrings
        for (int i = 0; i < n; ++i) dp[i][i] = true;
        
        // len 2 substrings
        for (int i = 0; i < n-1; ++i) {
            dp[i][i+1] = s.charAt(i) == s.charAt(i+1);
            if (dp[i][i+1]) {
                maxLen = 2;
                start = i;
            }
        }
        
        for (int len = 3; len <= n; ++len) {
            for (int i = 0; i <= n - len; i++){
                int j = i + len - 1;
                dp[i][j] = (s.charAt(i) == s.charAt(j) && dp[i+1][j-1]);
                if (dp[i][j] && len > maxLen) {
                    maxLen = len;
                    start = i;
                }
            }
        }
        
        System.out.println("Longest Palindrome Substring is: " + s.substring(start, start+maxLen));
        return maxLen;
    }
    
    /*
     * There is a O(n^2) time and O(1) space solution discussed at: 
     * https://www.geeksforgeeks.org/longest-palindromic-substring-set-2/
     */
    
    /*
     * This problem also has a linear time solution using Manacher’s Algorithm
     * https://www.geeksforgeeks.org/manachers-algorithm-linear-time-longest-palindromic-substring-part-1/
     */
    
    public static void main(String[] args) {
        LongestPalindromicSubstring obj = new LongestPalindromicSubstring();
        
        System.out.println(obj.longestPalindromeSubstring("aacabdkacaa")); // 3
        // Longest Palindrome Substring is: aca
    }
}
