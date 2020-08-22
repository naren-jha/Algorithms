package dynamicprogramming.basic;

import java.util.ArrayList;
import java.util.List;

// https://www.geeksforgeeks.org/perfect-sum-problem-print-subsets-given-sum/

public class PerfectSumProblem {

    // This problem is mainly an extension of SubsetSumProblem
    
    // T(n): O(sum*n) + O(sum+n) = O(sum*n)
    public void printAllSubsets(int[] a, int n, int sum) {
        boolean[][] res = new boolean[n+1][sum+1];
        
        // when sum == 0 (first column of 2D array)
        for (int i = 0; i <= n; i++)
            res[i][0] = true;
        
        // when (sum != 0 && n == 0)
        for (int j = 1; j <= sum; j++)
            res[0][j] = false; // redundant in Java
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                res[i][j] = res[i-1][j];
                if (j - a[i-1] >= 0)
                    res[i][j] = res[i][j] || res[i-1][j - a[i-1]];
            }
        }
        
        if (!res[n][sum]) {
            System.out.println("There is no subset with the sum " + sum);
            return;
        }
        
        List<Integer> lst = new ArrayList<>();
        printSubsetsUtil(a, res, n, sum, lst); // O(sum+n)
    }
    
    // T(n): O(sum+n)
    private void printSubsetsUtil(int[] a, boolean[][] res, int i, int sum, List<Integer> lst) {
        // Base conditions
        if (sum == 0) {
            System.out.println(lst);
            lst.clear();
            return;
        }
        if (i == 0) {
            return;
        }
        
        // if given sum can be achieved by excluding current element
        if (res[i-1][sum]) {
            List<Integer> lst2 = new ArrayList<>();
            lst2.addAll(lst); // add the elements which are already considered in previous iterations
            printSubsetsUtil(a, res, i-1, sum, lst2);
        }
        
        // if given sum can be achieved by including current element
        if (a[i-1] <= sum && res[i-1][sum - a[i-1]]) {
            lst.add(a[i-1]);
            printSubsetsUtil(a, res, i-1, sum - a[i-1], lst);
        }
    }
    
    public static void main(String[] args) {
        int[] a = {3, 6, 4, 12, 7, 2};
        int n = a.length;
        int sum = 18;
        
        PerfectSumProblem obj = new PerfectSumProblem();
        obj.printAllSubsets(a, n, sum);
        
        /* Output:
         * [12, 6]
         * [2, 12, 4]
         * [2, 7, 6, 3]
         */
    }
}
