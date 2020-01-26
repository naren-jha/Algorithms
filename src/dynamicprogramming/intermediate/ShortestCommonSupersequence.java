package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/shortest-common-supersequence/
// https://www.geeksforgeeks.org/shortest-possible-combination-two-strings/

public class ShortestCommonSupersequence {

    class SimpleRecursiveSolution {
        // T(n): Exp, O(2^min(m, n)) to be precise
        public int scs(String s1, String s2) {
            return scs(s1.toCharArray(), s2.toCharArray(), s1.length(), s2.length());
        }
        
        private int scs(char[] s1, char[] s2, int m, int n) {
            if (m == 0)
                return n;
            if (n == 0)
                return m;
            
            if (s1[m-1] == s2[n-1])
                return 1 + scs(s1, s2, m-1, n-1);
            else
                return 1 + Math.min(scs(s1, s2, m, n-1), scs(s1, s2, m-1, n));
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(n^2), S(n): O(n^2)
        public int scs(String s1, String s2) {
            int m = s1.length();
            int n = s2.length();
            
            char[] ch1 = s1.toCharArray();
            char[] ch2 = s2.toCharArray();
            
            int[][] res = new int[m+1][n+1];
            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    if (i == 0)
                        res[i][j]= j;
                    else if (j == 0)
                        res[i][j] = i;
                    else if (ch1[i-1] == ch2[j-1])
                        res[i][j] = 1 + res[i-1][j-1];
                    else
                        res[i][j] = 1 + Math.min(res[i][j-1], res[i-1][j]);
                }
            }
            return res[m][n];
        }
        
        /*
         * We can also find the length of SCS using LCS. 
         * SCS(s1, s2) = m + n - LCS(s1, s2);
         */
        
        /*
         * We can also print the Shortest Common Supersequence string
         * see solution below.
         */
        // T(n): O(n^2), S(n): O(n^2)
        public String printScs(String s1, String s2) {
            int m = s1.length();
            int n = s2.length();
            
            char[] ch1 = s1.toCharArray();
            char[] ch2 = s2.toCharArray();
            
            int[][] res = new int[m+1][n+1];
            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    if (i == 0)
                        res[i][j]= j;
                    else if (j == 0)
                        res[i][j] = i;
                    else if (ch1[i-1] == ch2[j-1])
                        res[i][j] = 1 + res[i-1][j-1];
                    else
                        res[i][j] = 1 + Math.min(res[i][j-1], res[i-1][j]);
                }
            }
            
            int i = m, j = n;
            String scs = "";
            while (i > 0 && j > 0) {
                if (ch1[i-1] == ch2[j-1]) {
                    scs = ch1[i-1] + scs;
                    i--;
                    j--;
                }
                else if (res[i-1][j] < res[i][j-1]) {
                    scs = ch1[i-1] + scs;
                    i--;
                }
                else {
                    scs = ch2[j-1] + scs;
                    j--;
                }
            }
            
            while (i > 0) {
                scs = ch1[i-1] + scs;
                i--;
            }
            
            while (j > 0) {
                scs = ch2[j-1] + scs;
                j--;
            }
            
            return scs;
        }
    }
    
    public static void main(String[] args) {
        ShortestCommonSupersequence o = new ShortestCommonSupersequence();
        String s1 = "algorithm";
        String s2 = "rhythm";
        System.out.println(o.new SimpleRecursiveSolution().scs(s1, s2)); // 11
        System.out.println(o.new DPSolution().scs(s1, s2)); // 11
        System.out.println(o.new DPSolution().printScs(s1, s2)); // algorihythm
    }
}
