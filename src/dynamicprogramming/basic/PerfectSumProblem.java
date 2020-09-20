package dynamicprogramming.basic;

import java.util.ArrayList;
import java.util.List;

// https://www.geeksforgeeks.org/perfect-sum-problem-print-subsets-given-sum/

public class PerfectSumProblem {

    // This problem is mainly an extension of SubsetSumProblem
    
    // T(n): O(sum*n) + O(sum+n) = O(sum*n)
    public void printAllSubsets(int[] a, int n, int sum) {
        boolean[][] dp = new boolean[n+1][sum+1];
        
        // when sum == 0 (first column of 2D array)
        for (int i = 0; i <= n; i++)
            dp[i][0] = true;
        
        // when (sum != 0 && n == 0)
        for (int j = 1; j <= sum; j++)
            dp[0][j] = false; // redundant in Java
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i-1][j];
                if (j >= a[i-1])
                    dp[i][j] = dp[i][j] || dp[i-1][j - a[i-1]];
            }
        }
        
        if (!dp[n][sum]) {
            System.out.println("There is no subset with the sum " + sum);
            return;
        }
        
        //System.out.println(Arrays.deepToString(res));
        /* 
         * a = {3, 6, 4, 12, 7, 2}
         * sum = 18
         * 
        [[T, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F], 
         [T, F, F, T, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F], 
         [T, F, F, T, F, F, T, F, F, T, F, F, F, F, F, F, F, F, F], 
         [T, F, F, T, T, F, T, T, F, T, T, F, F, T, F, F, F, F, F], 
         [T, F, F, T, T, F, T, T, F, T, T, F, T, T, F, T, T, F, T], 
         [T, F, F, T, T, F, T, T, F, T, T, T, T, T, T, T, T, T, T], 
         [T, F, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T]]
        */
        
        List<Integer> lst = new ArrayList<>();
        printSubsets(a, dp, n, sum, lst); // O(sum+n)
        
        //printOneSubset(a, dp, n, sum, lst); // to print just one subset
    }
    
    // T(n): O(sum+n)
    private void printSubsets(int[] a, boolean[][] dp, int i, int j, List<Integer> lst) {
        // Base conditions
        if (j == 0) {
            System.out.println(lst);
            //lst.clear();
            return;
        }
        if (i == 0) {
            return;
        }
        
        // if given sum can be achieved by excluding current element
        if (dp[i-1][j]) {
            List<Integer> lst2 = new ArrayList<>();
            lst2.addAll(lst); // add the elements which are already considered in previous iterations
            printSubsets(a, dp, i-1, j, lst2);
        }
        
        // if given sum can be achieved by including current element
        if (j >= a[i-1] && dp[i-1][j - a[i-1]]) {
            lst.add(a[i-1]);
            printSubsets(a, dp, i-1, j - a[i-1], lst);
        }
    }
    
    // T(n): O(sum+n)
    private void printOneSubset(int[] a, boolean[][] dp, int i, int j, List<Integer> lst) {
        // Base conditions
        if (j == 0) {
            System.out.println(lst);
            //lst.clear();
            return;
        }
        if (i == 0) {
            return;
        }
        
        // sum will be achievable by either including or not including the current element
        // so go in either direction
        if (dp[i-1][j]) { // not including
            printOneSubset(a, dp, i-1, j, lst);
        }
        else if (j >= a[i-1] && dp[i-1][j - a[i-1]]) { // including
            lst.add(a[i-1]);
            printOneSubset(a, dp, i-1, j - a[i-1], lst);
        }
    }
    
    public static void main(String[] args) {
        int[] a = {3, 6, 4, 12, 7, 2};
        int n = a.length;
        int sum = 18;
        
        PerfectSumProblem solver = new PerfectSumProblem();
        solver.printAllSubsets(a, n, sum);
        
        /* Output:
         * [12, 6]
         * [2, 12, 4]
         * [2, 7, 6, 3]
         */
    }
}
