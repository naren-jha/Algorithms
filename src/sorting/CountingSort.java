package sorting;

import java.util.Arrays;

/**
 * Implementation of Counting Sort
 * 
 * @author Narendra Jha
 * 
 */
public class CountingSort {

    public static void countingSort(int[] a, int[] b, int k) {
        int[] c = new int[k+1];
        for (int i = 0; i < k+1; i++)
            c[i] = 0;
        for (int j = 0; j < a.length; j++)
            c[a[j]]++;
        // c[i] now contains the number of elements equal to i.
        for (int i = 1; i < k+1; i++)
            c[i] = c[i] + c[i-1];
        // c[i] now contains the number of elements less than or equal to i.
        for (int j = a.length-1; j >= 0; j--){
            b[c[a[j]]-1] = a[j];
            c[a[j]]--;
        }
    }
    
    public static void main(String[] args) {        
        int[] input = {10, 4, 6, 8, 13, 2, 3};
        int maxValue = 13;
        int[] output = new int[input.length];
        
        countingSort(input, output, maxValue);
        System.out.println("Sorted elements are: " + Arrays.toString(output));
    }
}
