package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/count-number-of-ways-to-partition-a-set-into-k-subsets/

public class PartitionSetIntoKsubsets {
    
    /*
     * The idea is this: 
     * Lets say f(n, k) is the function that we are trying to solve,
     * which returns number of ways, an n element set can be partitioned
     * into k subsets.
     * 
     * Since every element has to be considered in some or other set,
     * we have two choices for every element.
     * 
     * Lets start with last element: 
     * 
     * 1. If we take last element as a single element set, then we have
     * reduced our problem to finding number of ways to partition k-1 
     * different subsets from an n-1 element set, i.e., to find f(n-1, k-1)
     * 
     * 2. If we don't take last element as a single element set, then it 
     * must go into existing sets, and since we have k existing sets (to
     * be formed), so last element can go into any of those k different 
     * subsets, in k different ways, and then we have reduced our problem 
     * to finding number of ways to partition k different subsets from n-1 
     * element set, i.e., to find f(n-1, k), so total number of ways in
     * this case will be, k*f(n-1, k)
     * 
     * And therefore overall recurrence equation will be
     * f(n, k) = f(n-1, k-1) + k*f(n-1, k)
     */
    class SimpleRecursiveSolution {
        // T(n): Exp
        public int countPartitions(int n, int k) {
            // Base cases
            if (n == 0)
                return k == 0 ? 1 : 0;
            if (k == 0)
                return 0;
            
            return countPartitions(n-1, k-1) + k*countPartitions(n-1, k);
        }
    }
    
    class DPSolution {
        // bottom-up tabulation
        // T(n): O(n*k), S(n): O(n*k)
        public int countPartitions(int n, int k) {
            int[][] dp = new int[n+1][k+1];
            
            dp[0][0] = 1;
            for (int j = 1; j <= k; j++) dp[0][j] = 0;
            for (int i = 1; i <= n; i++) dp[i][0] = 0;
            
            for (int i = 1; i <= n; i++)
                for (int j = 1; j <= k; j++)
                    dp[i][j] = dp[i-1][j-1] + j*dp[i-1][j];
                
            return dp[n][k];
        }
        
        // We can space optimize above solution to O(k), as at any time,
        // we are using only two rows, so we can manage with res[2][k+1]
    }
    
    public static void main(String[] args) {
        int n = 5, k = 2;
        PartitionSetIntoKsubsets o = new PartitionSetIntoKsubsets();
        System.out.println(o.new SimpleRecursiveSolution().countPartitions(n, k)); // 15
        System.out.println(o.new DPSolution().countPartitions(n, k)); // 15
    }
}
