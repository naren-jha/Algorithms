package sorting;

import java.util.Arrays;
import java.util.Random;

/**
 * Implementation of Radix Sort
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class RadixSort {

    // Method to sort given input array using radix sort algorithm
    public static void radixSort(int[] a) {
        // Find the maximum number to know number of digits
        int max = Arrays.stream(a).max().getAsInt();
        
        // Do counting sort for every digit. Note that instead 
        // of passing digit number, pos is passed. pos is 10^i 
        // where i is current digit number
        for (int pos = 1; max/pos > 0; pos *= 10) {
            countingSort(a, pos);
        }
    }
    
    // Method to do digit sort as per the digit represented by 'pos'
    public static void countingSort(int[] a, int pos) {
        int n = a.length;
        int[] b = new int[n];
        
        // k for radix sort will be 9, as 
        // values in each digit sort ranges from 0 to 9
        int[] c = new int[10];
        for (int i = 0; i < 10; i++)
            c[i] = 0;
        
        // store count of each element
        for (int j = 0; j < n; j++)
            c[(a[j]/pos) % 10]++;
        
        // c[i] now contains the number of elements equal to i
        
        for (int i = 1; i < 10; i++)
            c[i] = c[i] + c[i-1];
        
        // c[i] now contains the number of elements less than or equal to i.
        
        // build the output array
        for (int j = n - 1; j >= 0; j--){
            b[c[(a[j]/pos) % 10] - 1] = a[j];
            c[(a[j]/pos) % 10]--;
        }
        
        // copy sorted output to original array so that 
        // it contains sorted numbers according to current digit
        for (int j = 0; j < n; j++)
            a[j] = b[j];
    }
    
    public static void main(String[] args) {
        int[] array = {10, 4, 6, 8, 13, 2, 3};
        radixSort(array);
        System.out.println(Arrays.toString(array));
        
        runTests();
    }
    
    private static Random RANDOM = new Random();

    public static void runTests() {
        final int NUM_TESTS = 1000;
        for (int i = 1; i <= NUM_TESTS; i++) {

            int[] array = new int[i];
            for (int j = 0; j < i; j++) array[j] = randInt(0, +1000000);
            int[] arrayCopy = array.clone();
    
            radixSort(array);
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
