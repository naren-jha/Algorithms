package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/longest-arithmetic-progression-dp-35/

public class LongestArithmeticProgression {

	// T(n): O(n^2), S(n): O(n^2)
	public static int lengthOfLongestAP(int[] a) {
		int n = a.length;
		if (n <= 2)
			return n;
		
		// L[i][j] stores length of LAP with a[i] as first and a[j] as 
		// second element of AP, where i < j
		int[][] L = new int[n][n]; // first column and last row of table is never used
		
		int llap = 2;
		
		// fill entries in last column to 2, because if the given input array
		// has two or more elements, then length of LAP is at least 2
		for (int i = 0; i < n-1; i++)
			L[i][n-1] = 2;
		
		// fill rest of the table in bottom-up manner, and keep updating llap
		for (int j = n-2; j >= 1; j--) {
			int i = j-1, k = j+1;
			while (i >= 0 && k < n) {
				if (a[i] + a[k] < 2*a[j]) {
					k++;
				}
				else if (a[i] + a[k] > 2*a[j]) {
					L[i][j] = 2;
					i--;
				}
				else { // if (a[i] + a[k] == 2*a[j])
					L[i][j] = L[j][k] + 1;
					llap = Math.max(llap, L[i][j]);
					i--; k++;
				}
			}
			
			while (i >= 0) {
				L[i][j] = 2;
				i--;
			}
		}
		
		return llap;
	}
	
	// 1. Above algorithm assumes that input array is sorted in ascending order
	// 2. We should also be able to solve it left to right, istead of right to left
	// 3. O(n) space solution given at GFG is incorrect
	
	// Driver program  
    public static void main (String[] args) { 
        int[] a = {1, 7, 10, 13, 14, 19}; 
        System.out.println (lengthOfLongestAP(a)); // 4
      
        a = new int[]{1, 7, 10, 15, 27, 29}; 
        System.out.println(lengthOfLongestAP(a)); // 3
      
        a = new int[]{2, 4, 6, 8, 10}; 
        System.out.println(lengthOfLongestAP(a)); // 5
      
      
    }
}
