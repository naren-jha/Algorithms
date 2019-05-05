package basic.array.problems;import java.util.Arrays;

// https://practice.geeksforgeeks.org/problems/convert-array-into-zig-zag-fashion/0

public class ConvertArrayToZigZag {

	public static void convert(int[] a) {
		int tmp;
		for (int i = 0; i < a.length-1; i++) {
			if ((i % 2 == 0 && a[i] > a[i+1]) || (i % 2 == 1 && a[i] < a[i+1])) {
				tmp = a[i];
				a[i] = a[i+1];
				a[i+1] = tmp;
			}
		}
	}
	
	public static void main(String[] args) {
		int[] a = {4, 3, 7, 8, 6, 2, 1};
		convert(a);
		System.out.println(Arrays.toString(a));
	}
}
