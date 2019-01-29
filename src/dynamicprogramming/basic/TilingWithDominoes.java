package dynamicprogramming.basic;

 // https://www.geeksforgeeks.org/tiling-with-dominoes/

public class TilingWithDominoes {
	
	// T(n): O(n), S(n): O(n)
	public int countWays(int n) {
		if (n % 2 == 1)
			return 0;
		
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
		TilingWithDominoes obj = new TilingWithDominoes();
		System.out.println(obj.countWays(n)); // 153
		System.out.println(obj.countWaysSpaceOptimized(n)); // 153
	}

}
