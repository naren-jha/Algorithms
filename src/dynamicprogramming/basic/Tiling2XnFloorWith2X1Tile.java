package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/tiling-problem/

/*
count(n) = n if n = 0 or n = 1 or n = 2
count(n) = count(n-1) + count(n-2) , otherwise
*/
public class Tiling2XnFloorWith2X1Tile {

	int tileMemoized(int n, int[] res) {
		if (n <= 2)
			return n;
		
		if (res[n] != -1)
			return res[n];
		
		res[n] = tileMemoized(n-1, res) + tileMemoized(n-2, res);
		return res[n];
	}
	
	int tileBottomUp(int n) {
		int[] res = new int[n+1]; // 1 extra to handle n = 0 case
		res[0] = 0; res[1] = 1; res[2] = 2;
		
		for (int i = 3; i <= n; i++)
			res[i] = res[i-1] + res[i-2];
		
		return res[n];
	}
	
	int tileBottomUpSpaceOptimized(int n) {
		if (n <= 2) // edge case
			return n;
		
		int a = 1, b = 2, c = 0;
		for (int i = 3; i <= n; i++) {
			c = a + b;
			a = b;
			b = c;
		}
		return c;
	}
	
	public static void main(String[] args) {
		Tiling2XnFloorWith2X1Tile obj = new Tiling2XnFloorWith2X1Tile();
		int[] res = new int[5]; // 1 extra to handle n = 0 case
		Arrays.fill(res, -1);
		System.out.println(obj.tileMemoized(4, res));
		System.out.println(obj.tileBottomUp(4));
		System.out.println(obj.tileBottomUpSpaceOptimized(4));
	}

}
