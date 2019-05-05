package basic.array.problems;

// https://www.geeksforgeeks.org/find-a-peak-in-a-given-array/
// https://leetcode.com/problems/find-peak-element/

public class PeakElement {
	
	// Approach 1 : linear search in O(n) time
	
	// Approach 2 : binary search in O(lgn) time
	public static int firstPeakIndex(int[] a, int low, int high) {
		if (low > high)
			return -1;
		
		int mid = (low + high) / 2;
		if ( (mid == 0 || a[mid-1] < a[mid]) && (mid == a.length-1 || a[mid+1] < a[mid]) )
			return mid;
		
		if (mid > 0 && a[mid-1] > a[mid])
			return firstPeakIndex(a, low, mid-1);
		else
			return firstPeakIndex(a, mid+1, high);
	}
	
	public static void main(String[] args) {
		int[] a = {1, 3, 20, 4, 1, 0};
		System.out.println(firstPeakIndex(a, 0, a.length-1));
	}

}
