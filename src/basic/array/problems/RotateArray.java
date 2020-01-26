package basic.array.problems;

import java.util.Arrays;

// https://www.geeksforgeeks.org/array-rotation/

public class RotateArray {

    // Approach 1: Using temp array
    // TC: O(n), SC: O(d)
    public static void rotate1(int[] arr, int d) {
        int n = arr.length;
        if (n < d)
            throw new IllegalStateException("input size is smaller than d");
        
        int[] tmp = new int[d];
        for (int i = 0; i < d; i++) {
            tmp[i] = arr[i];
        }
        
        for (int i = d; i < n; i++) {
            arr[i-d] = arr[i];
        }
        
        for (int i = 0; i < d; i++) {
            arr[n-d+i] = tmp[i];
        }
        
        System.out.println(Arrays.toString(arr));
    }
    
    // Approach 2: Rotate one by one
    // TC: O(n*d), SC: O(1)
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
    
    // Approach 2: A Juggling Algorithm
    // TC: O(n), SC: O(1)
    public static void rotate3(int[] arr, int d) {
        int n = arr.length;
        if (n < d)
            throw new IllegalStateException("input size is smaller than d");
        
        int gcd = gcd(n, d);
        for (int i = 0; i < gcd; i++) {
            int tmp = arr[i];
            int j = i;
            int k = (j + d) % n;
            while (k != i) {
                arr[j] = arr[k];
                j = k;
                k = (j + d) % n;
            }
            arr[j] = tmp;
        }
        
        System.out.println(Arrays.toString(arr));
    }
    
    // returns GCD of two numbers, assuming a > b
    private static int gcd(int a, int b) {
        if (b == 0)
            return a;
        
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
    
    // https://www.geeksforgeeks.org/program-for-array-rotation-continued-reversal-algorithm/
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
    
    private static void reverseUtil(int[] arr, int i, int j) {
        while (i < j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            i++; j--;
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        rotate1(arr, 2); // [3, 4, 5, 6, 7, 1, 2]
        
        arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotate2(arr, 2); // [3, 4, 5, 6, 7, 1, 2]
        
        arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotate3(arr, 2); // [3, 4, 5, 6, 7, 1, 2]
        
        arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotate4(arr, 2); // [3, 4, 5, 6, 7, 1, 2]
        
        arr = new int[]{1, 2, 3, 4, 5, 6};
        rotate3(arr, 4); // [5, 6, 1, 2, 3, 4]
    }
}
