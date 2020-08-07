package sorting;

import java.util.Arrays;
import java.util.Random;

/**
 * Implementation of Merge Sort
 * Time complexity: O(nlgn)
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class MergeSort {
    
    // To simplify mergeSort method call
    public static void mergeSort(int[] a) {
        mergeSort(a, 0, a.length - 1);
    }
    
    public static void mergeSort(int[] a, int p, int r) {
        if (p >= r) return;
        
        int q = (p + r) / 2;
        mergeSort(a, p, q);
        mergeSort(a, q+1, r);
        merge(a, p, q, r);
    }
    
    public static void merge(int[] a,int p,int q,int r) {
        int[] la = Arrays.copyOfRange(a, p, q + 1); // a[p..q]
        int[] ra = Arrays.copyOfRange(a, q + 1, r + 1); // a[q+1..r]
        
        int i = 0, j = 0, k = p;
        while (i < la.length && j < ra.length)
            a[k++] = (la[i] <= ra[j]) ? la[i++] : ra[j++];
        
        while (i < la.length) a[k++] = la[i++];
        while (j < ra.length) a[k++] = ra[j++];
    }
    
    public static void main(String[] args) {
        int[] array = {10, 4, 6, 8, 13, 2, 3};
        mergeSort(array);
        System.out.println(Arrays.toString(array)); // [2, 3, 4, 6, 8, 10, 13]
        
        runTests();
    }
    
    private static Random RANDOM = new Random();

    public static void runTests() {
        final int NUM_TESTS = 1000;
        for (int i = 1; i <= NUM_TESTS; i++) {

            int[] array = new int[i];
            for (int j = 0; j < i; j++) array[j] = randInt(-1000000, +1000000);
            int[] arrayCopy = array.clone();
    
            mergeSort(array);
            Arrays.sort(arrayCopy);
    
            if (!Arrays.equals(array, arrayCopy)) {
                System.err.println("ERROR");
                System.out.println("Actual: " + Arrays.toString(array));
                System.out.println("Expected: " + Arrays.toString(arrayCopy));
            }
        }
    }

    private static int randInt(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

}
