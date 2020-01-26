package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/subset-sum-divisible-m/

public class SubsetSumDivisibleByM {

    public boolean isSubsetSumDivisibleByM(int[] a, int n, int m) {
        // If n > m there will always be a subset with sum 
        // divisible by m (by pigeonhole principle)
        
        /*
         * Explanation: 
         * consider n=m for simplicity; clearly the case n>m follows through.
           If the array contains some element a[i] that is a multiple of m, 
           the theorem is trivially satisfied: 
           just return the one-element subset { a[i] }.
        
           Consider partial sequences P_i = { the first i values from 'a' } 
           and their modular sums S_i = SUM(P_i) mod m . For example: 
           S_1 = a[1] mod m, 
           S_2 = a[1] + a[2] mod m, 
           S_3 = a[1] + a[2] + a[3] mod m, 
           etc...
           
           If one of those S_i = 0, the theorem trivially holds. 
           Furthermore, if two S's have the same value, the theorem holds too. 
           consider j and k such that S_j = S_k, for k>j , 
           then SUM(a[j...k]) will be a multiple of m.
        
           So the only case left is when all partial sums are distinct and non-zero.
           There are m partial sums S_1,S_2,....,S_m that may only take values 
           1,2,..,m-1 , which is clearly impossible.
         */
        if (n > m)
            return true;
        
        /*
         * The technique used in second case (n <= m) is that we will record all the 
         * mod values which can be obtained when partial sums are divided by m. 
         * If at any stage we capture the mod value to be zero then we are done. 
         * The bottom up procedure adds one element at a time. 
         */
        
        boolean[] dp = new boolean[m];
        Arrays.fill(dp, false);
        for (int i = 0; i < n; i++) {
            if (dp[0])
                return true;
            
            boolean[] temp = new boolean[m];
            Arrays.fill(temp, false);
            for (int j = 0; j < m; j++) {
                if (dp[j])
                    temp[(j + a[i]) % m] = true;
            }
            
            for (int j = 0; j < m; j++)
                dp[j] = dp[j] || temp[j];
            
            dp[a[i] % m] = true;
        }
        
        return dp[0];
    }
    
    // Method 2
    class Method2 {
        /*
         * Algorithm:
         * 1. Take modulo of each number in the array with 'm'. 
         *       Now array becomes of remainders.
         * 2. Now use the simple SUBSET SUM PROBLEM solution
         * 
         * Edge case: there is one catch in this. if any single element
         * is multiple of 'm',then this technique would fail. 
         * for example, say m=6 and any one element in input array is
         * a multiple of 6, then remainder for that element would be 
         * zero, and then while solving simple SUBSET SUM PROBLEM for 
         * sum 6, we will miss this combination.
         * so we have to take care of this.
         */
        public boolean isSubsetSumDivisibleByM(int[] a, int n, int m) {
            for (int i = 0; i < n; i++) {
                // Edge case: check if a single element is a multiple of 'm'
                if (a[i] > 0 && (a[i] % m) == 0)
                    return true;
                a[i] = a[i] % m;
            }
            
            SubsetSumProblem.DPSolution simpleSubsetSum = new SubsetSumProblem().new DPSolution();
            return simpleSubsetSum.isSubsetSumSpaceOptimized(a, n, m);
        }
    }
    
    public static void main(String[] args) {
        int[] a = {1, 34, 14, 12, 11, 7};
        int n = a.length;
        int m = 9;
        
        SubsetSumDivisibleByM obj = new SubsetSumDivisibleByM();
        System.out.println(obj.isSubsetSumDivisibleByM(a, n, m));
        System.out.println(obj.new Method2().isSubsetSumDivisibleByM(a, n, m));
    }
}
