package sorting;

import java.util.Arrays;
import java.util.Random;

/**
 * Implementation of Quick Sort
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class QuickSort {

    public static void randomizedQuickSort(int[] a) {
        randomizedQuickSort(a, 0, a.length-1);
    }

    public static void randomizedQuickSort(int[] a, int p, int r) {
        if (p >= r) return;
        
        int q = randomizedPartition(a, p, r);
        randomizedQuickSort(a, p, q-1);
        randomizedQuickSort(a, q+1, r);
    }

    public static int randomizedPartition(int[] a, int p, int r) {
        int i = randInt(p, r);
        // exchange a[r] and a[i] to randomize the selection of pivot element
        swap(a, r, i);
        return partition(a, p, r);
    }

    public static int partition(int[] a, int p, int r) {
        int pivot = a[r];
        int i = p;
        for (int j = p; j < r; j++) {
            if (a[j] <= pivot)
                swap(a, i++, j);
        }
        
        swap(a, i, r);
        return i;
    }
    
    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
    
    private static Random RANDOM = new Random();
    
    // Returns a random number b/w lower and upper bound
    private static int randInt(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }
    
    public static void main(String[] args) {
        int[] array = {10, 4, 6, 8, 13, 2, 3};
        randomizedQuickSort(array);
        System.out.println(Arrays.toString(array)); // [2, 3, 4, 6, 8, 10, 13]
        
        runTests();
    }

    public static void runTests() {
        final int NUM_TESTS = 1000;
        for (int i = 1; i <= NUM_TESTS; i++) {

            int[] array = new int[i];
            for (int j = 0; j < i; j++) array[j] = randInt(-1000000, +1000000);
            int[] arrayCopy = array.clone();
    
            randomizedQuickSort(array);
            Arrays.sort(arrayCopy);
    
            if (!Arrays.equals(array, arrayCopy)) {
                System.err.println("ERROR");
                System.out.println("Actual: " + Arrays.toString(array));
                System.out.println("Expected: " + Arrays.toString(arrayCopy));
            }
        }
    }

}
