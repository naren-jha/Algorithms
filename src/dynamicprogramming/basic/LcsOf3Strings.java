package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/lcs-longest-common-subsequence-three-strings/

public class LcsOf3Strings {

    /*
     * The solution is simply an extension of LCS for 
     * 2 Strings.
     */
    
    // Bottom-up tabulation
    // T(m,n,o): O(mno), S(m,n,o): O(mno)
    public int lcs(char[] x, char[] y, char[] z) {
        int m = x.length, n = y.length, o = z.length;
        int[][][] res = new int[m+1][n+1][o+1];
        
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k <= o; k++) {
                    // if any of the string is empty, 
                    // then result will be 0
                    if (i == 0 || j == 0 || k == 0)
                        res[i][j][k] = 0;
                    else if (x[i-1] == y[j-1] && y[j-1] == z[k-1])
                        res[i][j][k] = res[i-1][j-1][k-1] + 1;
                    else
                        res[i][j][k] = Math.max(res[i-1][j][k], 
                                Math.max(res[i][j-1][k], res[i][j][k-1]));
                }
            }
        }
        
        return res[m][n][o];
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
