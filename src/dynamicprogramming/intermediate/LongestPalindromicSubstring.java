package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/longest-palindrome-substring-set-1/

public class LongestPalindromicSubstring {
    
    // This is similar to LongestCommonSubstring problem
    
    public int f(String s) {
        int n = s.length();
        int[][] mem = new int[n][n];
        for (int i = 0; i < n; i++)
            Arrays.fill(mem[i], -1);
        
        f(s, 0, n-1, mem);
        return Arrays.stream(mem).flatMapToInt(Arrays::stream).max().getAsInt();
    }
    
    private void f(String s, int si, int ei, int[][] mem) {
        if (si == ei) {
            mem[si][ei] = 1;
            return;
        }
        if (ei < si) {
            mem[si][ei] = 0;
            return;
        }
        
        f(s, si+1, ei, mem);
        f(s, si, ei-1, mem);
        f(s, si+1, ei-1, mem);
        
        if (s.charAt(si) == s.charAt(ei))
           mem[si][ei] = mem[si+1][ei-1] + 2;
        else
           mem[si][ei] = 0;
    }

    // bottom-up tabulation
    // T(n): O(n^2), S(n): O(n^2)
    public int longestPalindromeSubstring(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        
        int longest = 0;
        int si = 0, ei = 0;
        for (int i = n-1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i+1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j))
                    dp[i][j] = dp[i+1][j-1] + 2;
                else
                    dp[i][j] = 0;
                
                if (dp[i][j] > longest) {
                    longest = dp[i][j];
                    si = i; ei = j;
                }
            }
        }
        
        System.out.println("Longest Palindrome Substring is: " + s.substring(si, ei+1));
        return longest;
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
        System.out.println(obj.f("forgeeksskeegfor")); // 10
        
        System.out.println(obj.longestPalindromeSubstring("forgeeksskeegfor")); // 10
        // Longest Palindrome Substring is: geeksskeeg
    }
}
