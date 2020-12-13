package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/super-ugly-number-number-whose-prime-factors-given-set/

public class SuperUglyNumbers {

    // DP solution
    // TC: O(n*m), SC: O(n+m), where m is length of primes[]
    public int getUglyNumber(int n, int[] primes) {
        int[] dp = new int[n]; // to store super ugly numbers
        dp[0] = 1; // first super ugly number
        
        int[] mi = new int[primes.length];  // multiplier indices
        Arrays.fill(mi, 0); // redundant in Java
        
        int[] mv = new int[primes.length];  // multiplier values
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < primes.length; j++)
                mv[j] = dp[mi[j]] * primes[j];
            
            int sm = smallest(mv); // get smallest
            for (int j = 0; j < primes.length; j++)
                if (sm == mv[j]) mi[j]++;
            
            dp[i] = sm;
        }
        return dp[n-1];
    }
    
    private int smallest(int[] mv) {
        int sm = Integer.MAX_VALUE;
        for (int j = 0; j < mv.length; j++) 
            sm = Math.min(sm, mv[j]);
        return sm;
    }
    
    public static void main(String[] args) {
        System.out.println(new SuperUglyNumbers().getUglyNumber(150, new int[]{2, 3, 5})); // 5832
        System.out.println(new SuperUglyNumbers().getUglyNumber(50, new int[]{2, 3, 5})); // 243
        System.out.println(new SuperUglyNumbers().getUglyNumber(5, new int[]{2, 5})); // 8
        System.out.println(new SuperUglyNumbers().getUglyNumber(9, new int[]{3, 5, 7, 11, 13})); // 21
    }

}
