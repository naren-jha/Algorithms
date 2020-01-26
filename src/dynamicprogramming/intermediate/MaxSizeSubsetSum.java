package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/maximum-size-subset-given-sum/

public class MaxSizeSubsetSum {

    // This problem is mainly an extension of SubsetSumProblem
    
    // T(n) = O(n*sum), S(n) = O(n*sum)
    public int maxSizeSubsetSum(int[] a, int sum) {
        int n = a.length;
        boolean[][] res = new boolean[n+1][sum+1];
        
        // another array to store max size subset sum
        int[][] count = new int[n+1][sum+1];
        
        // when sum == 0
        for (int i = 0; i <= n; i++) {
            res[i][0] = true;
            count[i][0] = 0;
        }
        
        // when (sum != 0 && n == 0)
        for (int j = 1; j <= sum; j++) {
            res[0][j] = false; // redundant in Java
            count[0][j] = -1; // assign some -ve number
        }
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                if (a[i-1] > j) { // when last number cannot be included
                    res[i][j] = res[i-1][j];
                    count[i][j] = count[i-1][j];
                }
                else {
                    res[i][j] = res[i-1][j] || res[i-1][j-a[i-1]];
                    if (res[i][j])
                        count[i][j] = Math.max(count[i-1][j], 1 + count[i-1][j-a[i-1]]);
                }
            }
        }
        
        return count[n][sum];
    }
    
    public static void main(String[] args) {
        int[] a = {3, 6, 4, 12, 7, 2};
        int sum = 18;
        MaxSizeSubsetSum obj = new MaxSizeSubsetSum();
        System.out.println(obj.maxSizeSubsetSum(a, sum)); // 4
    }
}
