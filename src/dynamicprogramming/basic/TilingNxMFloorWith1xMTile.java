package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/count-number-ways-tile-floor-size-n-x-m-using-1-x-m-size-tiles/

/*
 * f(n, m) = 1, if n == 0
 * f(n, m) = 1, if n < m
 * f(n, m) = f(n-1, m) + f(n-m, m)
 */
public class TilingNxMFloorWith1xMTile {
    
    // TC: O(2^n), SC: O(n) [max height of recursion tree is n and 
    // for each node we are making 2 recursive calls]
    int tile(int n, int m) {
        if (n == 0) return 1;
        if (n < m) return 1;
        
        return tile(n-1, m) + tile(n-m, m);
    }

    // TC: O(n), SC: O(n)
    int tileMemoized(int n, int m, int[] mem) {
        if (n == 0) return 1;
        if (n < m) return 1;
        
        if (mem[n] != -1) return mem[n];
        
        mem[n] = tileMemoized(n-1, m, mem) + tileMemoized(n-m, m, mem);
        return mem[n];
    }
    
    // TC: O(n), SC: O(n)
    int tileBottomUpTabulation(int n, int m) {
        int[] dp = new int[n+1]; // 1 extra for n = 0 case
        dp[0] = 1;
        
        for (int i = 1; i <= n; i++) {
            if (i < m) dp[i] = 1;
            else dp[i] = dp[i-1] + dp[i-m];
        }
        
        return dp[n];
    }
    
    public static void main(String[] args) {
        TilingNxMFloorWith1xMTile obj = new TilingNxMFloorWith1xMTile();
        int[] mem = new int[8]; // 1 extra for n = 0 case
        Arrays.fill(mem, -1);
        
        System.out.println(obj.tile(7, 4)); // 5
        System.out.println(obj.tileMemoized(7, 4, mem)); // 5
        
        System.out.println(obj.tileBottomUpTabulation(7, 4)); // 5
        System.out.println(obj.tileBottomUpTabulation(8, 4)); // 7
    }
}
