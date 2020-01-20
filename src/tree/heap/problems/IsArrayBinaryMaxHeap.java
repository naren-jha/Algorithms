package tree.heap.problems;

public class IsArrayBinaryMaxHeap {
	
	// Iterative method to check if a given array
	// represents a binary max-heap or not
	public static boolean isMaxHeap(int[] a) {
		int n = a.length;
		// Iterate through all internal nodes
		for (int i = n/2 - 1; i >= 0; i--) {
			// If any internal node is smaller than either of its children
			// then array does not represent a max-heap, so return false.
			
			// Check left child first
			if (a[i] < a[2*i + 1])
				return false;
			
			// There is possibility that the last internal node has 
			// only left child, and no right child. So fist check if 
			// current node has a right child or not, and then check
			// for heap property
			if (2*i + 2 < n)
				if (a[i] < a[2*i + 2])
					return false;
		}
		return true;
	}
	
	// Recursive method to check if a given array
	// represents a binary max-heap or not
	public static boolean isMaxHeap(int[] a, int i) {
		// Base case: leaf nodes are heap by default
		if (i > a.length/2 - 1)
			return true;
		
		// If an internal node is greater than or equal to
		// both of its children and same is true recursively
		// then return true, else false
		
		// Check left child first
		boolean res = false;
		if (a[i] >= a[2*i + 1])
			res = isMaxHeap(a, 2*i + 1);
		
		// There is possibility that the last internal node has 
		// only left child, and no right child. So fist check if 
		// current node has a right child or not, and then check
		// for heap property
		if (res && 2*i + 2 < a.length) {
			if (a[i] >= a[2*i + 2])
				res = res && isMaxHeap(a, 2*i + 2);
			else
				res = false;
		}
		
		return res;
	}
	
	public static void main(String[] args) {
        int[] a = {90, 15, 10, 7, 12, 2, 7, 3};
        System.out.println(isMaxHeap(a)); // true
        System.out.println(isMaxHeap(a, 0)); // true
        
        int[] b = {90, 15, 10, 16, 12, 2, 7, 3};
        System.out.println(isMaxHeap(b)); // false
        System.out.println(isMaxHeap(b, 0)); // false
    }

}
