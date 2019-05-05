package basic.array.problems;

// https://practice.geeksforgeeks.org/problems/sum-of-middle-elements-of-two-sorted-arrays/0

// similar
// https://www.geeksforgeeks.org/median-of-two-sorted-arrays/

public class SumOfMiddleElements {

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
	
	public static void main(String[] args) {
		int[] a1 = {1, 2, 4, 6, 10};
		int[] a2 = {4, 5, 6, 9, 12};
		System.out.println(sumMiddle(a1, a2)); // 11
		
		a1 = new int[]{1, 2};
		a2 = new int[]{4, 5};
		System.out.println(sumMiddle(a1, a2)); // 6
		
		a1 = new int[]{1};
		a2 = new int[]{4};
		System.out.println(sumMiddle(a1, a2)); // 5
	}
}
