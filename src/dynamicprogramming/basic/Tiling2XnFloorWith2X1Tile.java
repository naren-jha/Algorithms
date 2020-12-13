package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/tiling-problem/

/*
f(n) = 1, if n = 0 or 1
f(n) = f(n-1) + f(n-2) , otherwise

for n == 0, there is exactly one way we can tile (by not doing anything)
*/
public class Tiling2XnFloorWith2X1Tile {
    
    // TC: O(2^n), SC: O(n) [max height of recursion tree is n and 
    // for each node we are making 2 recursive calls]
    int tile(int n) {
        if (n <= 1) return 1;
        return tile(n-1) + tile(n-2);
    }

    // TC: O(n), SC: O(n)
    int tileMemoized(int n, int[] mem) {
        if (n <= 1) return 1;
        
        if (mem[n] != -1) return mem[n];
        
        mem[n] = tileMemoized(n-1, mem) + tileMemoized(n-2, mem);
        return mem[n];
    }
    
    // TC: O(n), SC: O(n)
    int tileBottomUp(int n) {
        int[] dp = new int[n+1]; // 1 extra to handle n = 0 case
        dp[0] = dp[1] = 1;
        
        for (int i = 2; i <= n; i++)
            dp[i] = dp[i-1] + dp[i-2];
        
        return dp[n];
    }
    
    // TC: O(n), SC: O(1)
    int tileBottomUpSpaceOptimized(int n) {
        if (n <= 1) return 1;// edge case
            
        int a = 1, b = 1, c = 0;
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }
    
    public static void main(String[] args) {
        Tiling2XnFloorWith2X1Tile obj = new Tiling2XnFloorWith2X1Tile();
        int[] mem = new int[5]; // 1 extra to handle n = 0 case
        Arrays.fill(mem, -1);
        
        System.out.println(obj.tile(4)); // 5
        System.out.println(obj.tileMemoized(4, mem)); // 5
        System.out.println(obj.tileBottomUp(4)); // 5
        System.out.println(obj.tileBottomUpSpaceOptimized(4)); // 5
    }

}
