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
        
        // TC of above approach is either O(n^2*k) or O(k^2*n). We can reduce this TC to O(nk)
        
        // Observe that, to calculate dp[i][j], we need to accumulate sum of few (or all) columns in the previous row
        // So what if we can maintain that cumulative sum in dp array itself and use that to get rid of the 'x' loop
        
        // The 'x' loop goes from 0 to min(i-1, j). If min(i-1, j) = j, then we need sum of entire previous row
        // If min(i-1, j) = i-1, then we need to ignore few columns from the beginning of the previous row
        
        // In other words, j-(i-1) is the column from which we need to accumulate our sum, and j-i is the column 
        // before (and including) which we need to ignore the entries from previous row.
        // [0, 1, 2,......(j-i), (j-(i-1)),.....j-1, j]
        
        // So what we can do is get the entire previous row sum (till current j): dp[i-1][j]
        // and then if 'j-i' is valid a index then subtract sum in dp[i-1][j-i] 
        // this will give us sum of columns [(j-(i-1)),.....j-1, j]
        
        // TC: O(nk), SC: O(nk)
        public int countPerm(int n, int k) {
            int[][] dp = new int[n+1][k+1];
            
            for (int j = 0; j <= k; j++) // when n == 0
                dp[0][j] = 0;
            for (int i = 1; i <= n; i++) // when n != 0 and k == 0
                dp[i][0] = 1;
            
            // populate rest of the table
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= k; j++) {
                    int val = dp[i-1][j] - (j-i >= 0 ? dp[i-1][j-i] : 0);
                    dp[i][j] = dp[i][j-1] + val; // dp[i][j-1] because we are calculating cumulative sum
                }
            }
            
            // Now we cannot simply return dp[n][k], because dp[n][k] now stores sum of all the possible 
            // permutations, starting from 1 inversion up until k inversions, but we only want value with
            // k inversions, so we'll return dp[n][k] - dp[n][k-1]
            return dp[n][k] - (k > 0 ? dp[n][k-1] : 0);
        }
        
        
        // we can reduce space complexity of above solution from O(nk) to O(k), as at any time to calculate 
        // dp[i][j], we need to access only one immediate previous row. So we can manage with just 2 rows.
        
        // TC: O(nk), SC: O(k)
        public int countPermSO(int n, int k) {
            int[][] dp = new int[2][k+1];
            
            for (int j = 0; j <= k; j++) // when n == 0
                dp[0][j] = 0;
            dp[1][0] = 1; // when n != 0 and k == 0
            
            // populate rest of the table
            for (int i = 1; i <= n; i++) {
                dp[i % 2][0] = 1; // when n != 0 and k == 0
                for (int j = 1; j <= k; j++) {
                    int val = dp[(i-1) % 2][j] - (j-i >= 0 ? dp[(i-1) % 2][j-i] : 0);
                    dp[i % 2][j] = dp[i % 2][j-1] + val;
                }
            }
            
            return dp[n % 2][k] - (k > 0 ? dp[n % 2][k-1] : 0);
        }
    }
    
    public static void main(String[] args) {
        int n = 5, k = 4;
        NumberOfPermutationsWithKInversions o = new NumberOfPermutationsWithKInversions();
        
        System.out.println(o.new SimpleRecursiveSolution().count(n, k)); // 20
        System.out.println(o.new DPSolution().count(n, k)); // 20
        System.out.println(o.new DPSolution().countPerm(n, k)); // 20
        System.out.println(o.new DPSolution().countPermSO(n, k)); // 20
    }
}
