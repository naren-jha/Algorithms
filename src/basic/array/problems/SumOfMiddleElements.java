package basic.array.problems;

// https://practice.geeksforgeeks.org/problems/sum-of-middle-elements-of-two-sorted-arrays/0

// similar
// https://www.geeksforgeeks.org/median-of-two-sorted-arrays/

public class SumOfMiddleElements {
	
	// USE SECOND APPROACH

	// O(n) solution
	// (n-1)th and nth element in merged array will be middle elements
	public static int sumMiddle(int[] a1, int[] a2) {
		int n = a1.length;
		
		int i = 0, j = 0, count = -1, mid1 = 0, mid2 = 0;
		while (count != n) {
			int[] a; int index;
			
			// if first array is exhausted
			if (i == n) {
				a = a2; index = j;
				j++;
			}
			
			// if second array is exhausted
			else if (j == n) {
				a = a1; index = i;
				i++;
			}
			
			// no array is exhausted
			else {
				if (a1[i] < a2[j]) {
					a = a1; index = i;
					i++;
				}
				else {
					a = a2; index = j;
					j++;
				}
			}
			
			count++;
			if (count == n-1)
				mid1 = a[index];
			if (count == n)
				mid2 = a[index];
		}
		return mid1 + mid2;
	}
	
	// O(lgn) solution using binary Search
	// approach is similar to previous problem: MedianOfTwoSortedArray
	public static int sumMiddle_A2(int[] a1, int[] a2) {
		int n = a1.length;
		
		int low = 0, high = n, halfLen = ((2*n + 1) / 2);
		int pa1, pa2; // partition indices
		int maxLeftA1 = 0, minRightA1 = 0, maxLeftA2 = 0, minRightA2 = 0;
		while (low <= high) {
			pa1 = (low + high) / 2;
			pa2 = halfLen - pa1;
			
			maxLeftA1 = (pa1 == 0) ? Integer.MIN_VALUE : a1[pa1-1];
			minRightA1 = (pa1 == n) ? Integer.MAX_VALUE : a1[pa1];
			
			maxLeftA2 = (pa2 == 0) ? Integer.MIN_VALUE : a2[pa2-1];
			minRightA2 = (pa2 == n) ? Integer.MAX_VALUE : a2[pa2];
			
			if (maxLeftA1 <= minRightA2 && maxLeftA2 <= minRightA1) {
				// we have found the correct partitioning
				break;
			}
			else if (maxLeftA1 > minRightA2) {
				high = pa1 - 1;
			}
			else {
				low = pa1 + 1;
			}
		}
		
		// return the result after we break out of while loop
		return Math.max(maxLeftA1, maxLeftA2) + Math.min(minRightA1, minRightA2);
	}
	
	/*
	 * Just like the previous problem for finding median, the two input arrays 
	 * for this problem could be of different sizes, and we can handle the cases 
	 * when we have one middle element or two, depending on whether total number 
	 * of elements is odd or even.
	 */
	
	public static void main(String[] args) {
		int[] a1 = {1, 2, 4, 6, 10};
		int[] a2 = {4, 5, 6, 9, 12};
		System.out.println(sumMiddle(a1, a2)); // 11
		System.out.println(sumMiddle_A2(a1, a2)); // 11
		
		a1 = new int[]{1, 2};
		a2 = new int[]{4, 5};
		System.out.println(sumMiddle(a1, a2)); // 6
		System.out.println(sumMiddle_A2(a1, a2)); // 6
		
		a1 = new int[]{1};
		a2 = new int[]{4};
		System.out.println(sumMiddle(a1, a2)); // 5
		System.out.println(sumMiddle_A2(a1, a2)); // 5
	}
}
