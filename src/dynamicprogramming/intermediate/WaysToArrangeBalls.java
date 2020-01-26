package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/ways-to-arrange-balls-such-that-adjacent-balls-are-of-different-types/

public class WaysToArrangeBalls {

    // T(n): O(pqr), S(n): O(pqr)
    public static int countWays(int p, int q, int r) {
        int[][][][] dp = new int[p+1][q+1][r+1][3];
        
        for (int i = 0; i <= p; i++) {
            for (int j = 0; j <= q; j++) {
                for (int k = 0; k <= r; k++) {
                    for (int l = 0; l < 3; l++) {
                        dp[i][j][k][l] = -1;
                    }
                }
            }
        }
        
        return countWays(p, q, r, dp, 0) // last ball required is of type p
                + countWays(p, q, r, dp, 1) // type q
                    + countWays(p, q, r, dp, 2); // typr r
    }

    private static int countWays(int p, int q, int r, int[][][][] dp, int last) {
        // Base case1: When required type of ball is not available
        if ( (last == 0 && p == 0) || (last == 1 && q == 0) || (last == 2 && r == 0) )
            return 0;
        
        // Base case2: When only one ball of the required type is available,
        // and no other types of balls are not available
        if (last == 0 && p == 1 && q == 0 && r == 0)
            return 1;
        if (last == 1 && q == 1 && p == 0 && r == 0)
            return 1;
        if (last == 2 && r == 1 && p == 0 && q == 0)
            return 1;
        
        // if subproblem was already solved before
        if (dp[p][q][r][last] != -1)
            return dp[p][q][r][last];
        
        if (last == 0)
            dp[p][q][r][last] = countWays(p-1, q, r, dp, 1) + countWays(p-1, q, r, dp, 2);
        else if (last == 1)
            dp[p][q][r][last] = countWays(p, q-1, r, dp, 0) + countWays(p, q-1, r, dp, 2);
        else if (last == 2)
            dp[p][q][r][last] = countWays(p, q, r-1, dp, 0) + countWays(p, q, r-1, dp, 1);
        
        return dp[p][q][r][last];
    }
    
    public static void main(String[] args) {
        int p = 1, q = 1, r = 1;
        System.out.println(countWays(p, q, r));
    }
}
