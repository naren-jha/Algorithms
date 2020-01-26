package basic.array.problems;

// https://www.geeksforgeeks.org/find-the-maximum-element-in-an-array-which-is-first-increasing-and-then-decreasing/

public class MaximumValueInBitonicArray {

    // T(n): O(lgn), S(n): O(1)
    public static int max(int[] a, int low, int high) {
        // Base cases:
        // 1: if only one element
        if (low == high)
            return a[low];
        
        // 2: if only two elements
        if (low+1 == high) {
            if (a[low] > a[high])
                return a[low];
            else
                return a[high];
        }
        
        int mid = (low + high) / 2;
        if (a[mid] > a[mid-1] && a[mid] > a[mid+1])
            return a[mid];
        else if (a[mid] < a[mid-1] && a[mid] > a[mid+1])
            return max(a, low, mid-1);
        else // (a[mid] > a[mid-1] && a[mid] < a[mid+1])
            return max(a, mid+1, high);
    }
    
    public static void main(String[] args) {
        int[] a = {1, 3, 50, 10, 9, 7, 6};;
        System.out.println(max(a, 0, a.length-1)); // 50
    }
}
