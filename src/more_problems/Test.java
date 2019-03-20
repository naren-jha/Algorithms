package more_problems;

import java.util.*;

public class Test {
	
	public static int f(int[][] m, int i, int j) {
		if (i == 0 && j == 0)
			return 0;
		
		if (i == 0)
			return f(m, i, j-1) + Math.abs(m[i][j]-m[i][j-1]);
		if (j == 0)
			return f(m, i-1, j) + Math.abs(m[i][j]-m[i-1][j]);
		
		return Math.min(f(m, i-1, j) + Math.abs(m[i][j]-m[i-1][j]), 
				Math.min(f(m, i-1, j-1) + Math.abs(m[i][j]-m[i-1][j-1]), 
						f(m, i, j-1) + Math.abs(m[i][j]-m[i][j-1])));
	}

	public static void main(String[] args) {
		int[] a = {1, 2, 3, 4, 5};
		System.out.println(Arrays.toString(Arrays.copyOfRange(a, 1, 3)));
		
		
	}
}
