package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/number-of-permutation-with-k-inversions/
// https://stackoverflow.com/a/25747326/4210068
// https://leetcode.com/problems/k-inverse-pairs-array/solution/

public class NumberOfPermutationsWithKInversions {
    
    /*
    Idea:
    1, 2, 3, 4, 5
    
    lets place 5 at different positions
    1, 2, 3, 4, 5 : f(n-1, k)
    1, 2, 3, 5, 4 : f(n-1, k-1)
    1, 2, 5, 3, 4 : f(n-1, k-2)
    1, 5, 2, 3, 4 : f(n-1, k-3)
    5, 1, 2, 3, 4 : f(n-1, k-4)
    */
    
    class SimpleRecursiveSolution {
        // T(n): Exponential
        public int count(int n, int k) {
            // Base cases:
            if (n == 0)
                return 0;
            if (k == 0)
                return 1;
            
            int count = 0;
            for (int i = 0; i < n && i <= k; i++)
                count += count(n-1, k-i);
            return count;
        }
    }
    
    class DPSolution {
        // T(n): O(n*k*min(n,k)), S(n): O(n*k)
        public int count(int n, int k) {
            int[][] dp = new int[n+1][k+1];
            
            for (int j = 0; j <= k; j++) // when n == 0
                dp[0][j] = 0;
            for (int i = 1; i <= n; i++) // when n != 0 and k == 0
                dp[i][0] = 1;
            
            // populate rest of the table
            for (int i = 1; i <= n; i++)
                for (int j = 1; j <= k; j++)
                    for (int x = 0; x < i && x <= j; x++)
                        dp[i][j] += dp[i-1][j-x];
            
            return dp[n][k];
        }
    }
    
    public static void main(String[] args) {
        int n = 5, k = 4;
        NumberOfPermutationsWithKInversions o = new NumberOfPermutationsWithKInversions();
        
        System.out.println(o.new SimpleRecursiveSolution().count(n, k)); // 20
        System.out.println(o.new DPSolution().count(n, k)); // 20
    }
}
