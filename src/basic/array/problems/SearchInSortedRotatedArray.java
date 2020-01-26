package basic.array.problems;

// https://www.geeksforgeeks.org/search-an-element-in-a-sorted-and-pivoted-array/
// https://leetcode.com/problems/search-in-rotated-sorted-array/

public class SearchInSortedRotatedArray {
    
    // USE SECOND APPROACH 

    // searches an element key in a pivoted sorted array arr[]
    public static int pivotedBinarySearch(int[] arr, int key) {
        int n = arr.length;
        int pivot = findPivot(arr, 0, n-1);
        
        // if we didn't find a pivot, then array is not rotated at all
        if (pivot == -1) {
            return binarySearch(arr, 0, n-1, key);
        }
        
        // if we found a pivot, then search in either sub-array around pivot
        if (key >= arr[0])
            return binarySearch(arr, 0, pivot, key);
        else
            return binarySearch(arr, pivot+1, n-1, key);
    }
    
    // function to get index of pivot element
    public static int findPivot(int[] arr, int low, int high) {
        // base case
        if (high < low) return -1;
        //if (high == low) return low; // -1
        
        int mid = (low + high)/2;
        if (mid < high && arr[mid] > arr[mid+1])
            return mid;
        else if (mid > low && arr[mid-1] > arr[mid])
            return mid-1;
        
        // if either of above two conditions are not met, then recur further
        if (arr[mid] >= arr[low])
            return findPivot(arr, mid+1, high);
        else
            return findPivot(arr, low, mid-1);
    }
    
    // standard Binary Search function
    public static int binarySearch(int[] arr, int low, int high, int key) {
        // base case
        if (high < low) 
            return -1;
        
        int mid = (low + high)/2;
        if (key == arr[mid])
            return mid;
        
        // key is not the mid element, recur down on either side
        if (key > arr[mid])
            return binarySearch(arr, mid+1, high, key);
        else
            return binarySearch(arr, low, mid-1, key);
    }
    
    // A BETTER APPROACH:
    /*
     * IDEA:
     * =====
     * The interesting property of a sorted + rotated array is that when you divide it into 
     * two halves, at least one of the two halves will always be sorted. We can easily know 
     * which half is sorted by comparing start and end element of each half.
     * 
     * Once we find which half is sorted we can see if the key is present in that half - 
     * simple comparison with the extremes.
     * 
     * If the key is present in that half we recursively call the function on that half 
     * else we recursively call our search on the other half.
     * 
     * We are discarding one half of the array in each call which makes this algorithm O(lgN)
     * 
     * ALGORITHM:
     * ==========
     * 1) Find middle point mid = (l + h)/2
     * 2) If key is present at middle point, return mid.
     * 3) Else If arr[l..mid] is sorted
     *         a) If key to be searched lies in range from arr[l]
     *            to arr[mid], recur for arr[l..mid].
     * 
     *         b) Else recur for arr[mid+1..h]
     * 
     * 4) Else (arr[mid+1..h] must be sorted)
     *         a) If key to be searched lies in range from arr[mid+1]
     *            to arr[h], recur for arr[mid+1..h].
     * 
     *         b) Else recur for arr[l..mid] 
     */
    // https://stackoverflow.com/questions/4773807/searching-in-an-sorted-and-rotated-array
    public static int pivotedBinarySearch_m2(int[] arr, int low, int high, int key) {
        // base case: when key not present
        if (high < low)
            return -1;
        
        int mid = (low + high)/2;
        
        // key found
        if (arr[mid] == key) {
            return mid;
        }
        
        // if left half is sorted
        if (arr[low] <= arr[mid]) {
            // if key is present in left half
            if (key >= arr[low] && key < arr[mid])
                return pivotedBinarySearch_m2(arr, low, mid-1, key);
            
            // if key is not present in left half..search right half
            else
                return pivotedBinarySearch_m2(arr, mid+1, high, key);
        }
        
        // if right half is sorted
        else {
            // if key is present in right half
            if (key > arr[mid] && key <= arr[high])
                return pivotedBinarySearch_m2(arr, mid+1, high, key);
            
            // if key is not present in right half..search in left half
            else
                return pivotedBinarySearch_m2(arr, low, mid-1, key);
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {5, 6, 7, 8, 9, 10, 1, 2, 3};
        
        int index = pivotedBinarySearch(arr, 9);
        System.out.println(index); // 4
        
        index = pivotedBinarySearch_m2(arr, 0, arr.length-1, 9);
        System.out.println(index); // 4
    }
}
