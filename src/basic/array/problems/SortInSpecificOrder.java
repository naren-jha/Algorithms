package basic.array.problems;

import java.util.Arrays;
import java.util.Collections;

// https://practice.geeksforgeeks.org/problems/sort-in-specific-order/0

public class SortInSpecificOrder {

	public static void sortOddEven(Integer[] a) {
		int i = 0, j = a.length-1;
		while (i < j) {
			if (a[i] % 2 == 0 && a[j] % 2 == 1) {
				// swap
				int tmp = a[i];
				a[i] = a[j];
				a[j] = tmp;
				i++; j--;
			}
			else {
				if (a[i] % 2 == 1)
					i++;
				if (a[j] % 2 == 0)
					j--;
			}
		}
		
		int oddEnd = i; // for i > j
		if (i == j && a[i] % 2 == 0)
			oddEnd = i-1;
		
		Arrays.sort(a, 0, oddEnd+1, Collections.reverseOrder());
		Arrays.sort(a, oddEnd+1, a.length);
	}
	
	public static void main(String[] args) {
		Integer[] a = {1, 2, 3, 5, 4, 7, 10};
		sortOddEven(a);
		System.out.println(Arrays.toString(a));
		// [7, 5, 3, 1, 2, 4, 10]
	}
}
