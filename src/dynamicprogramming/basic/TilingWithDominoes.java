package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/tiling-with-dominoes/

public class TilingWithDominoes {
    
    // TC: Exponential
    class SimpleRecursiveSolution {
        int fa(int n) {
            if (n == 0) return 1;
            if (n == 1) return 0;
            
            return fa(n-2) + 2*fb(n-1);
        }
        
        int fb(int n) {
            if (n == 0) return 0;
            if (n == 1) return 1;
            
            return fa(n-1) + fb(n-2);
        }
    }
    
    // T(n): O(n), S(n): O(n)
    class Memoization {
        int[] ma, mb; // memoization tables for type A and type B problems
        
        int f(int n) {
            ma = new int[n+1];
            Arrays.fill(ma, -1);
            
            mb = new int[n+1];
            Arrays.fill(mb, -1);
            
            return fa(n);
        }
        
        int fa(int n) {
            if (n == 0) return 1;
            if (n == 1) return 0;
            
            if (ma[n] != -1) return ma[n];
            
            return ma[n] = fa(n-2) + 2*fb(n-1);
        }
        
        int fb(int n) {
            if (n == 0) return 0;
            if (n == 1) return 1;
            
            if (mb[n] != -1) return mb[n];
            
            return mb[n] = fa(n-1) + fb(n-2);
        }
    }
    
    // dp bottom-up tabulation
    // T(n): O(n), S(n): O(n)
    public int countWays(int n) {
        if (n % 2 == 1) {
            return 0;
            // this is because (n=odd) => (3*n = odd)
            // that means total number of slots in the tile will be odd
            // and we cannot fill odd number of slots using even size tile (2x1 = 2 slots)
        }
        
        int[] a = new int[n+1];
        int[] b = new int[n+1];
        
        a[0] = 1; a[1] = 0;
        b[0] = 0; b[1] = 1;
        for (int i = 2; i <= n; i++) {
            a[i] = a[i-2] + 2*b[i-1];
            b[i] = a[i-1] + b[i-2];
        }
        return a[n];
    }
    
    // T(n): O(n), S(n): O(1)
    public int countWaysSpaceOptimized(int n) {
        if (n % 2 == 1)
            return 0;
        
        int a0 = 1, a1 = 0;
        int b0 = 0, b1 = 1;
        int a, b;
        for (int i = 2; i <= n; i++) {
            a = a0 + 2*b1;
            b = a1 + b0;
            a0 = a1; a1 = a;
            b0 = b1; b1 = b;
        }
        return a1;
    }
    
    public static void main(String[] args) {
        int n = 8;
        TilingWithDominoes solver = new TilingWithDominoes();
        
        System.out.println(solver.new SimpleRecursiveSolution().fa(n)); // 153
        
        System.out.println(solver.new Memoization().f(n)); // 153
        System.out.println(solver.countWays(n)); // 153
        System.out.println(solver.countWaysSpaceOptimized(n)); // 153
        
        n = 118;
        //System.out.println(obj.new SimpleRecursiveSolution().fa(n)); // 759148825
        System.out.println(solver.new Memoization().f(n)); // 759148825
        System.out.println(solver.countWays(n)); // 759148825
        System.out.println(solver.countWaysSpaceOptimized(n)); // 759148825
    }

}
