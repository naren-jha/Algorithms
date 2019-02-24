package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/longest-zig-zag-subsequence/

public class LongestZigZagSubsequence {

	// This problem is similar to LIS problem
	
	// Bottom-up tabulation
	// T(n): O(n^2), S(n): O(n)
	public int length(int[] a) {
		int n = a.length;
		
		/* Create an array to store intermediate results
		 * z[i][0] = Length of the longest Zig-Zag subsequence ending at 
		 * index i when last element is greater than its previous element 
		 * 
		 * z[i][1] = Length of the longest Zig-Zag subsequence ending at 
		 * index i when last element is smaller than its previous element   
        */
		int[][] z = new int[n][2];
		
		int res = 1;
		for (int i = 0; i < n; i++) {
			z[i][0] = 1; z[i][1] = 1;
			for (int j = 0; j < i; j++) {
				if (a[j] < a[i])
					if (z[i][0] < z[j][1] + 1)
						z[i][0] = z[j][1] + 1;
				
				if (a[j] > a[i])
					if (z[i][1] < z[j][0] + 1)
						z[i][1] = z[j][0] + 1;
			}
			if (res < Math.max(z[i][0], z[i][1]))
				res = Math.max(z[i][0], z[i][1]);
		}
		return res;
	}
	
	/*
	 * The first recurrence relation is based on the fact that, If we are at 
	 * position i and this element has to be bigger than its previous element 
	 * then for this sequence (upto i) to be bigger we will try to choose an 
	 * element j ( < i) such that A[j] < A[i] i.e. A[j] can become A[i]’s 
	 * previous element and Z[j][1] + 1 is bigger than Z[i][0] then we will 
	 * update Z[i][0].
	 * 
	 * Remember we have chosen Z[j][1] + 1 not Z[j][0] + 1 to satisfy alternate 
	 * property because in Z[j][0] last element is bigger than its previous one 
	 * and A[i] is greater than A[j] which will break the alternating property 
	 * if we update. So above fact derives first recurrence relation, similar 
	 * argument can be made for second recurrence relation also.
	 */
	
	public static void main(String[] args) {
		int[] a = {10, 22, 9, 33, 49, 50, 31, 60};
		LongestZigZagSubsequence zigZag = new LongestZigZagSubsequence();
		System.out.println(zigZag.length(a)); // 6
	}
}
