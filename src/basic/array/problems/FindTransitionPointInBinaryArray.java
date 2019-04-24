package basic.array.problems;

// https://www.geeksforgeeks.org/find-transition-point-binary-array/
	
public class FindTransitionPointInBinaryArray {

	// T(n): O(lgn), S(n): O(1)
	public static int transitionPoint(int[] a, int low, int high) {
		if (low > high)
			return -1;
		
		int mid = (low + high) / 2;
		
		if (a[mid] == 0)
			if (mid < high && a[mid+1] == 1)
				return mid+1;
			else // a[mid+1] == 0
				return transitionPoint(a, mid+1, high);
		
		else // a[mid] == 1
			if (mid > low && a[mid-1] == 0)
				return mid;
			else // a[mid-1] == 1
				return transitionPoint(a, low, mid-1);
	}

	public static void main(String[] args) {
		int[] a = {0, 0, 0, 0, 0, 1, 1, 1};
		System.out.println(transitionPoint(a, 0, a.length-1)); // 5
	}
}
