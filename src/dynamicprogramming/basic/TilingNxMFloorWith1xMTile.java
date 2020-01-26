package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/count-number-ways-tile-floor-size-n-x-m-using-1-x-m-size-tiles/

/*
 * count(n, m) = 1, if n < m
 * count(n, m) = 2, if n == m
 * count(n, m) = count(n-1, m) + count(n-m, m)
 */
public class TilingNxMFloorWith1xMTile {

    int tileMemoized(int n, int m, int[] res) {        
        if (n < m)
            return 1;
        if (n == m)
            return 2;
        
        if (res[n] != -1)
            return res[n];
        
        res[n] = tileMemoized(n-1, m, res) + tileMemoized(n-m, m, res);
        return res[n];
    }
    
    int tileBottomUpTabulation(int n, int m) {
        int[] res = new int[n+1]; // 1 extra for n = 0 case
        res[0] = 0;
        for (int i = 1; i <= n; i++) {
            if (i < m)
                res[i] = 1;
            else if (i == m)
                res[i] = 2;
            else
                res[i] = res[i-1] + res[i-m];
        }
        return res[n];
    }
    
    public static void main(String[] args) {
        TilingNxMFloorWith1xMTile obj = new TilingNxMFloorWith1xMTile();
        int[] res = new int[8]; // 1 extra for n = 0 case
        Arrays.fill(res, -1);
        System.out.println(obj.tileMemoized(7, 4, res)); // 5
        
        System.out.println(obj.tileBottomUpTabulation(7, 4)); // 5
        System.out.println(obj.tileBottomUpTabulation(8, 4)); // 7
    }
}
