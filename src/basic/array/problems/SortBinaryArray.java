package basic.array.problems;

import java.util.Arrays;

// https://www.geeksforgeeks.org/sort-binary-array-using-one-traversal/

public class SortBinaryArray {

    // T(n): O(n), S(n): O(1)
    public static void sort(int[] a) {
        int i = 0, j = a.length-1;
        while (i < j) {
            if (a[i] == 1 && a[j] == 0) {
                // swap
                a[i] = 0;
                a[j] = 1;
                i++; j--;
            }
            else {
                if (a[i] == 0)
                    i++;
                if (a[j] == 1)
                    j--;
            }
        }
    }
    
    public static void main(String[] args) {
        int[] a = { 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 
                        1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0 };
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
