package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/longest-palindrome-substring-set-1/

public class LongestPalindromicSubstring {
    
    // If we apply brute-force approach to check. Then to check all the possible substring, it'll take O(n^2)
    // and then to check if each of those substring is a palindrome or not, it'll take another O(n). So total O(n^3).
    // But we are doing some repetitive work in this process. When we check for s[i to j], we are also checking for 
    // s[i+1 to j-1], and then later we solve for s[i+1 to j-1] again (subproblem for s[i+1 to j-1] itself
    // has many other overlapping subproblems). So we'll have to use DP to solve this efficiently.
    // We can solve the subproblems starting from smaller length and going towards higher length.
    
    // d[i][j] stores true, if s[i...j] is a palindrome, else it stores false.

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
            for (int i = 0; i < n - (len-1); i++){
                int j = i + (len - 1);
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
