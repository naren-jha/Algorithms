package basic.array.problems;

// https://www.geeksforgeeks.org/find-rotation-count-rotated-sorted-array/

public class RotationCountInRotatedSorted {

	// approach 1: linear time solution
	public static int rotationCount1(int[] a) {
		int n = a.length;
		int i;
		for (i = 0; i < n-1; i++)
			if (a[i] > a[i+1])
				break;
		// i is now the index of pivot element
		
		return (i+1) % n; // +1 since zero based index
		// Mod is used when there is no rotation
		// When there is no rotation, for loop stops at i = n-1
		// then i+1 = n, and (i+1) % n = 0, which is what we want
	}
	
	// approach 1: O(lgn) time solution using binary search
	public static int rotationCount2(int[] a, int low, int high) {
		if (low > high)
			return 0; // no rotation
		
		/*if (a[high] > a[low])
			return 0; // no rotation
		*/
		
		int mid = (low + high) / 2;
		if (mid < high && a[mid] > a[mid+1])
			return mid+1; // +1 since zero based index
		if (mid > low && a[mid-1] > a[mid])
			return mid; // +1 since zero based index
		
		// if first half is sorted, then pivot must be in second half
		if (a[low] <= a[mid])
			return rotationCount2(a, mid+1, high);
		
		// if second half is sorted, then pivot must be in first half
		else
			return rotationCount2(a, low, mid-1);
	}
	
	public static void main(String[] args) {
		int[] a = {15, 18, 2, 3, 6, 12};
		System.out.println(rotationCount1(a)); // 2
		System.out.println(rotationCount2(a, 0, a.length-1)); // 2
	}
}
