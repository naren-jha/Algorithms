package sorting;

import java.util.Arrays;
import java.util.Random;

/**
 * Implementation of Merge Sort
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class MergeSort {
    
    // to simplify mergeSort method call
    public static void mergeSort(int[] a) {
        mergeSort(a, 0, a.length-1);
    }
    
    public static void mergeSort(int[] a,int p,int r) {
        if (p < r) {
            int q = Math.floorDiv((p+r), 2); // int q = (p+r)/2 would also do just fine.
            mergeSort(a, p, q);
            mergeSort(a, q+1, r);
            merge(a, p, q, r);
        }
    }
    
    public static void merge(int[] a,int p,int q,int r) {
        int n1 = q-p+1, n2 = r-q;
        int[] la = new int[n1 + 1];
        int[] ra = new int[n2 + 1];
        
        for (int i = 0; i < n1; i++)
            la[i] = a[p+i];
        for (int j = 0; j < n2; j++)
            ra[j] = a[q+j+1];
        
        la[n1] = Integer.MAX_VALUE;
        ra[n2] = Integer.MAX_VALUE;
        
        int i = 0,j = 0;
        for (int k = p; k <= r; k++) {
            if (la[i] <= ra[j]) {
                a[k] = la[i];
                i++;
            }
            else {
                a[k] = ra[j];
                j++;
            }
        }
    }
    
    public static void main(String[] args) {
        int[] array = {10, 4, 6, 8, 13, 2, 3};
        mergeSort(array);
        System.out.println(Arrays.toString(array));
        
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
