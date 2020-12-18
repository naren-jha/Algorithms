package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/edit-distance-dp-5/

public class EditDistance {

    class SimpleRecursiveSolution {
        // T(m,n) = Exp = O(3^max(m,n))
        public int editDistance(String s1, String s2) {
            return editDistance(s1, s2, s1.length(), s2.length());
        }
        
        private int editDistance(String s1, String s2, int m, int n) {
            // Base cases
            if (m == 0)
                return n; // insert all characters of 2nd string into 1st
            if (n == 0)
                return m; // remove all characters of 1st string
            
            if (s1.charAt(m-1) == s2.charAt(n-1))
                return editDistance(s1, s2, m-1, n-1);
            
            return 1 + Math.min(editDistance(s1, s2, m, n-1), // insert
                            Math.min(editDistance(s1, s2, m-1, n), // remove
                                    editDistance(s1, s2, m-1, n-1))); // replace
        }
    }
    
    class DPSolution {
        // bottom-up tabulation
        // T(m,n) = (mn), S(m,n) = (mn)
        public int editDistance(String s1, String s2) {
            int m = s1.length();
            int n = s2.length();
            
            int[][] dp = new int[m+1][n+1];
            
            // fill first column, n == 0 case
            for (int i = 0; i <= m; i++)
                dp[i][0] = i;
            
            // fill first row, m == 0 case
            for (int j = 0; j <= n; j++)
                dp[0][j] = j;
            
            // fill rest of the table
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (s1.charAt(i-1) == s2.charAt(j-1))
                        dp[i][j] = dp[i-1][j-1];
                    else
                        dp[i][j] = 1 + Math.min(dp[i][j-1], Math.min(dp[i-1][j], dp[i-1][j-1]));
                }
            }
            
            return dp[m][n];
        }
        
        // we can optimize above solution on space, 
        // as at any time we need to access only one previous row
    }
    
    public static void main(String[] args) {
        String s1 = "saturday";
        String s2 = "sunday";
        EditDistance o = new EditDistance();
        System.out.println(o.new SimpleRecursiveSolution().editDistance(s1, s2)); // 3
        System.out.println(o.new DPSolution().editDistance(s1, s2)); // 3
    }
}
