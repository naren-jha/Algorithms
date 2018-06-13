package tree.heap.problems;

public class IsArrayBinaryMinHeap {
	
	public static void main(String[] args) {
		int[] a = {10, 15, 14, 25, 30};
		System.out.println(isMinHeap(a)); // true
		System.out.println(isMinHeap(a, 0)); // true
		
		int[] b = {30, 56, 22, 49, 30, 51, 2, 67};
		System.out.println(isMinHeap(b)); // false
		System.out.println(isMinHeap(b, 0)); // false
	}
	
	// Iterative method to check if a given array
	// represents a binary min-heap or not
	public static boolean isMinHeap(int[] a) {
		int n = a.length;
		// Iterate through all internal nodes
		for (int i = n/2 - 1; i >= 0; i--) {
			// If any internal node is greater than either of its children
			// then array does not represent a min-heap, so return false.
			
			// Check left child first
			if (a[i] > a[2*i + 1])
				return false;
			
			// There is possibility that the last internal node has 
			// only left child, and no right child. So fist check if 
			// current node has a right child or not, and then check
			// for heap property
			if (2*i + 2 < n)
				if (a[i] > a[2*i + 2])
					return false;
		}
		return true;
	}
	
	// Recursive method to check if a given array
	// represents a binary min-heap or not
	public static boolean isMinHeap(int[] a, int i) {
		// Base case: leaf nodes are heap by default
		if (i > a.length/2 - 1)
			return true;
		
		// If an internal node is less than or equal to
		// both of its children and same is true recursively
		// then return true, else false
		
		// Check left child first
		boolean res = false;
		if (a[i] <= a[2*i + 1])
			res = isMinHeap(a, 2*i + 1);
		
		// There is possibility that the last internal node has 
		// only left child, and no right child. So fist check if 
		// current node has a right child or not, and then check
		// for heap property
		if (res && 2*i + 2 < a.length) {
			if (a[i] <= a[2*i + 2])
				res = res && isMinHeap(a, 2*i + 2);
			else
				res = false;
		}
		
		return res;
	}
	
}
