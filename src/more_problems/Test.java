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
		int m[][] = { { 5, 4, 2 }, 
                { 9, 2, 1 }, 
                { 2, 5, 9 }, 
                { 1, 3, 11 } }; 
		System.out.println(f(m, m.length-1, m[0].length-1));
	}
}
