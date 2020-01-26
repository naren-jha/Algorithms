package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/longest-even-length-substring-sum-first-second-half/

public class LongestEvenLengthSubstr {

    public static int len(String s) {
        int n = s.length();
        
        // sum[i][j] stores sum of digits from s[i] to s[j]
        int[][] sum = new int[n][n];
        
        // for substring of length 1
        for (int i = 0; i < n; i++)
            sum[i][i] = s.charAt(i) - '0';
        
        // fill entries for substrings of length 2 to n, and keep updating maxLen
        int maxLen = 0;
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                int k = len/2;
                
                sum[i][j] = sum[i][j-k] + sum[j-k+1][j];
                
                // update max length, if 'len' is even and left and right sums are same
                if ( (len % 2 == 0) && (sum[i][j-k] == sum[j-k+1][j]) && (len > maxLen) )
                    maxLen = len;
            }
        }
        return maxLen;
    }
    
    public static void main(String[] args) {
        String s = "153803";
        System.out.println(len(s)); // 4
    }
}
