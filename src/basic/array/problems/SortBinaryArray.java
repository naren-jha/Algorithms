package basic.array.problems;

import java.util.Arrays;

// https://www.geeksforgeeks.org/sort-binary-array-using-one-traversal/

public class SortBinaryArray {

	public static void sort(int[] a) {
		int i = 0, j = a.length-1;
		while (i < j) {
			if (a[i] == 1) {
				while (i < j && a[j] == 1)
					j--;
				if (i < j) {
					// swap
					a[i] = 0;
					a[j] = 1;
					j--;
				}
			}
			i++;
		}
	}
	
	public static void main(String[] args) {
		int[] a = { 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 
						1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0 };
		sort(a);
		System.out.println(Arrays.toString(a));
	}
}
