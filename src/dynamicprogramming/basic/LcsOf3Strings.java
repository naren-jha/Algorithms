package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/lcs-longest-common-subsequence-three-strings/

public class LcsOf3Strings {

    /*
     * The solution is simply an extension of LCS for 2 Strings.
     */
    
    // Bottom-up tabulation
    // T(m,n,o): O(mno), S(m,n,o): O(mno)
    public int lcs(char[] x, char[] y, char[] z) {
        int m = x.length, n = y.length, o = z.length;
        int[][][] dp = new int[m+1][n+1][o+1];
        
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k <= o; k++) {
                    if (i == 0 || j == 0 || k == 0)
                        dp[i][j][k] = 0;
                    else if (x[i-1] == y[j-1] && y[j-1] == z[k-1])
                        dp[i][j][k] = dp[i-1][j-1][k-1] + 1;
                    else
                        dp[i][j][k] = Math.max(dp[i-1][j][k], 
                                Math.max(dp[i][j-1][k], dp[i][j][k-1]));
                }
            }
        }
        
        return dp[m][n][o];
    }
    
    public static void main(String[] args) {
        String s1 = "abcd1e2";
        String s2 = "bc12ea";
        String s3 = "bd1ea";
        
        char[] x = s1.toCharArray();
        char[] y = s2.toCharArray();
        char[] z = s3.toCharArray();
        
        LcsOf3Strings obj = new LcsOf3Strings();
        System.out.println(obj.lcs(x, y, z)); // 3
    }
}
