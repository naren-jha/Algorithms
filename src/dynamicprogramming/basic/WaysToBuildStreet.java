package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/count-ways-build-street-given-constraints/

public class WaysToBuildStreet {

    /*
     * dp[0] = 1;
     * dp[1] = 3;
     * 
     * dp[i] = dp[i-1] + 2 * (dp[i-1] - ( dp[i-1] - dp[i-2] ) / 2 );
     * 
     * Above recurrence is used to solve this problem. If we have to 
     * add a new row in the street there are 3 ways in which we can do 
     * this - (H,H) , (O,H) , (H,O).
     * 
     * Case 1:
     * If we add (H,H) in ith row, then it doesn't matter what the 
     * orientation is in the (i-1)th row of the street. So, with (H,H) as 
     * the new last row we can have as many different possibilities as 
     * there were till (i-1)th row i.e. dp[i-1].
     * 
     * Case 2:
     * We add (O,H) or (H,O) in the ith row. Both will have same result, 
     * so we calculate one of them and multiply it by 2. The (i-1)th row 
     * must have a H above O of the ith row. For this we subtract the number 
     * of possibilities of having O in (i-1)th row above the O in the ith row. 
     * We can do this in (dp[i-1] - ( dp[i-1] - dp[i-2] ) / 2 ) ways. 
     * (dp[i-1] - dp[i-2]) is the number of possible orientations in which 
     * (i-1)th row has an O in either of its side as dp[i-2] will be the 
     * number of ways of having (H,H) in the (i-1)th row. We divide it by 2 
     * to get the number of ways of having O in one side.
     */
    
    class SimpleRecursiveSolution {
        // T(n): Exp
        public int countWays(int n) {
            if (n == 0)
                return 1;
            if (n == 1)
                return 3;
            
            return countWays(n-1) + 2 * ( countWays(n-1) - ( countWays(n-1) - countWays(n-2) ) / 2 );
        }
    }
    
    class DPSolution {
        // bottom-up tabulation
        // T(n): O(n), S(n): O(n)
        public int countWays(int n) {
            int[] res = new int[n+1];
            
            res[0] = 1;
            res[1] = 3;
            
            for (int i = 2; i <= n; i++)
                res[i] = res[i-1] + 2 * ( res[i-1] - (res[i-1] - res[i-2])/2 );
            
            return res[n];
        }
        
        // T(n): O(n), S(n): O(1)
        public int countWaysSpaceOptimized(int n) {
            int i_2 = 1;
            int i_1 = 3;
            
            int res = 0;
            for (int i = 2; i <= n; i++) {
                res = i_1 + 2 * ( i_1 - (i_1 - i_2)/2 );
                // res = 2 * i_1 + i_2;
                i_2 = i_1;
                i_1 = res;
            }
            
            return res;
        }
    }
    
    public static void main(String[] args) {
        WaysToBuildStreet o = new WaysToBuildStreet();
        int n = 5;
        
        System.out.println(o.new SimpleRecursiveSolution().countWays(n)); // 99
        System.out.println(o.new DPSolution().countWays(n)); // 99
        System.out.println(o.new DPSolution().countWaysSpaceOptimized(n)); // 99
    }
}
