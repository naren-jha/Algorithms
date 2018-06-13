package basic.array.problems;

import java.util.Arrays;

/**
 * @author Narendra Jha
 *
 * Program to rotate a given array
 */
public class RotateArray {

	public static void rotate1(int[] arr, int d) {
		int n = arr.length;
		if (n < d)
			throw new IllegalStateException("input size is smaller than d");
		
		int[] tmp = new int[d];
		int i = 0;
		for (; i < d; i++) {
			tmp[i] = arr[i];
		}
		
		for (; i < n; i++) {
			arr[i-2] = arr[i];
		}
		
		for (i = 0; i < d; i++) {
			arr[n-d+i] = tmp[i];
		}
		
		System.out.println(Arrays.toString(arr));
	}
	
	public static void rotate2(int[] arr, int d) {
		int n = arr.length;
		if (n < d)
			throw new IllegalStateException("input size is smaller than d");
		
		int tmp;
		for (int i = 0; i < d; i++) { // rotate d times
			tmp = arr[0];
			for (int j = 1; j < n; j++) {
				arr[j-1] = arr[j];
			}
			arr[n-1] = tmp;
		}
		
		System.out.println(Arrays.toString(arr));
	}
	
	public static void rotate3(int[] arr, int d) {
		int n = arr.length;
		if (n < d)
			throw new IllegalStateException("input size is smaller than d");
		
		int gcd = gcd(n, d);
		int i, j, k, temp;
		for (i = 0; i < gcd; i++) {
			temp = arr[i];
			j = i;
			while (true) {
				k = j + d;
				if (k >= n)
					k = k - n;
				if (k == i)
					break;
				arr[j] = arr[k];
				j = k;
			}
			arr[j] = temp;
		}
		
		System.out.println(Arrays.toString(arr));
	}
	
	// returns GCD of two numbers
	private static int gcd(int a, int b) {
		if (b == 0)
			return a;
		
		if (b > a) {
			// swap(a,b)
			int temp = a;
			a = b;
			b = temp;
		}
		
		int rem;
		while (true) {
			rem = a % b;
			if (rem == 0)
				break;
			a = b;
			b = rem;
		}
		return b;
	}
	
	public static void rotate4(int[] arr, int d) {
		int n = arr.length;
		if (n < d)
			throw new IllegalStateException("input size is smaller than d");
		
		// reverse first d elements
		reverseUtil(arr, 0, d-1);
		
		// reverse last n-d elements
		reverseUtil(arr, d, n-1);
		
		// reverse the whole array
		reverseUtil(arr, 0, n-1);
		
		System.out.println(Arrays.toString(arr));
	}
	
	private static void reverseUtil(int[] arr, int from, int to) {
		int i = from, j = to;
		int tmp;
		while (i < j) {
			tmp = arr[i];
			arr[i] = arr[j];
			arr[j] = tmp;
			
			i++; j--;
		}
	}
	
	// rotation using block swap
	/*public static void rotate5(int[] arr, int d) {
		int n = arr.length;
		if (n < d)
			throw new IllegalStateException("input size is smaller than d");
		
		
		
		System.out.println(Arrays.toString(arr));
		
	}
	
	private static void swap(int[] arr, int i, int j, int d) {
		int temp;
		for (int k = 0; k < d; k++) {
			temp = arr[i+k];
			arr[i+k] = arr[j+k];
			arr[j+k] =  temp;
		}
	}*/
	
	public static void cyclicRotateClockwiseByOne(int[] arr) {
		int n = arr.length;
		int temp = arr[n-1];
		for (int i = n-2; i >= 0; i--) {
			arr[i+1] = arr[i];
		}
		arr[0] = temp;
		System.out.println(Arrays.toString(arr));
	}
	
	
	/* searching in sorted and rotated array - start - */
	
	// searches an element key in a pivoted sorted array arr[]
	public static int pivotedBinarySearch(int[] arr, int key) {
		int n = arr.length;
		int pivot = findPivot(arr, 0, n-1);
		
		// if we didn't find a pivot, then array is not rotated at all
		if (pivot == -1) {
			return binarySearch(arr, 0, n-1, key);
		}
		
		// if we found a pivot, 
		// then search in either sub-array around pivot
		if (key >= arr[0])
			return binarySearch(arr, 0, pivot, key);
		else
			return binarySearch(arr, pivot+1, n-1, key);
	}
	
	// function to get pivot. For array [3, 4, 5, 6, 1, 2] it returns
	// 3 (index of 6)
	public static int findPivot(int[] arr, int low, int high) {
		// base case
		if (high < low) return -1;
		//if (high == low) return low; // -1
		
		int mid = (low + high)/2;
		if (mid < high && arr[mid] > arr[mid+1])
			return mid;
		else if (mid > low && arr[mid-1] > arr[mid])
			return mid-1;
		
		// if either of above two conditions are not met,
		// then recur further
		if (arr[mid] >= arr[low])
			return findPivot(arr, mid+1, high);
		else
			return findPivot(arr, low, mid-1);
	}
	
	// standard Binary Search function
	public static int binarySearch(int[] arr, int low, int high, int key) {
		// base case
		if (high < low) 
			return -1;
		
		int mid = (low + high)/2;
		if (key == arr[mid])
			return mid;
		
		// key is not the mid element, recur down on either side
		if (key > arr[mid])
			return binarySearch(arr, mid+1, high, key);
		else
			return binarySearch(arr, low, mid-1, key);
	}
	
	// search an element in sorted and rotated array using
	// single pass of Binary Search
	public static int pivotedBinarySearchImproved(int[] arr, int low, int high, int key) {
		// base case
		if (high < low)
			return -1;
		
		int mid = (low + high)/2;
		if (arr[mid] == key) {
			return mid;
		}
		
		// if arr[low...mid] is sorted
		if (arr[low] <= arr[mid]) {
			// check if key lies in this half or other half
			if (key >= arr[low] && key < arr[mid])
				return pivotedBinarySearchImproved(arr, low, mid-1, key);
			else
				return pivotedBinarySearchImproved(arr, mid+1, high, key);
		}
		
		// if arr[low...mid] is not sorted,
		// then arr[mid...high] must be sorted
		else {
			// check if key lies in this half or other half
			if (key > arr[mid] && key <= arr[high])
				return pivotedBinarySearchImproved(arr, mid+1, high, key);
			else
				return pivotedBinarySearchImproved(arr, low, mid-1, key);
		}
	}
	
	/* searching in sorted and rotated array - end - */

	
	public static void main(String[] args) {
		//int[] arr = {1, 2, 3, 4, 5, 6, 7};
        //rotate5(arr, 2);
		//cyclicRotateClockwiseByOne(arr);
		
		int[] arr = {5, 6, 7, 8, 9, 10, 1, 2, 3};
		int index = pivotedBinarySearchImproved(arr, 0, arr.length-1, 9);
		System.out.println(index);
	}
}
