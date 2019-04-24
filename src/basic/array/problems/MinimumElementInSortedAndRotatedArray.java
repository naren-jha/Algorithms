package basic.array.problems;

// https://www.geeksforgeeks.org/find-minimum-element-in-a-sorted-and-rotated-array/

public class MinimumElementInSortedAndRotatedArray {

	// solution is very similar to RotationCountInRotatedSorted problem
	
	// T(n): O(lgn), S(n): O(1)
	public static int min(int[] a, int low, int high) {
		// when array is not rotated
		if (low > high)
			return a[0];
		
		int mid = (low + high) / 2;
		if (mid < high && a[mid] > a[mid+1])
			return a[mid+1];
		if (mid > low && a[mid-1] > a[mid])
			return a[mid];
		
		// if first half is sorted, then pivot must be in second half
		if (a[low] <= a[mid])
			return min(a, mid+1, high);
		
		// if second half is sorted, then pivot must be in first half
		else
			return min(a, low, mid-1);
	}
	
	public static void main(String[] args) {
		int[] a = {5, 6, 1, 2, 3, 4};
		System.out.println(min(a, 0, a.length-1)); // 1
		
		a = new int[]{1, 2, 3, 4};
		System.out.println(min(a, 0, a.length-1)); // 1
	}
}
