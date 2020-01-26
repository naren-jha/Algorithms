package basic.array.problems;

// https://www.geeksforgeeks.org/counting-inversions/
// https://www.geeksforgeeks.org/count-inversions-in-an-array-set-2-using-self-balancing-bst/
// https://www.geeksforgeeks.org/count-inversions-array-set-3-using-bit/

public class CountInversions_Approach1 {

    // Counting inversions using modified merge sort
    
    public static int countInversions(int[] a) {
        int[] tmp = new int[a.length];
        return mergeSort(a, tmp, 0, a.length-1);
    }
    
    public static int mergeSort(int[] a, int[] tmp, int low, int high) {
        int inversionCount = 0;
        if (low < high) {
            int mid = (low + high) / 2;
            inversionCount = mergeSort(a, tmp, low, mid);
            inversionCount += mergeSort(a, tmp, mid+1, high);
            inversionCount += merge(a, tmp, low, mid+1, high);
        }
        return inversionCount;
    }
    
    public static int merge(int[] a, int[] tmp, int low, int mid, int high) {
        // a[low...mid-1] is sorted, and we have inversion count calculated for that
        // a[mid...high] is sorted, and we have inversion count calculated for that
        // We have to count additional inversions from when we merge a[low...mid-1]
        // and a[mid...high]
        
        int i = low, j = mid;
        int k = low;
        int inversionCount = 0;
        while (i <= mid-1 && j <= high) {
            if (a[i] <= a[j]) {
                tmp[k] = a[i];
                i++; k++;
            }
            else {
                tmp[k] = a[j];
                j++; k++;
                
                inversionCount += (mid-i);
            }
        }
        
        while (i <= mid-1) {
            tmp[k] = a[i];
            i++; k++;
        }
        while (j <= high) {
            tmp[k] = a[j];
            j++; k++;
        }
        
        for (i = low; i <= high; i++)
            a[i] = tmp[i];
        
        return inversionCount;
    }
    
    /*
     * Time and space complexity is same as merge sort, i.e., O(nlgn) and O(n) respectively.
     * 
     * Note that above code modifies (or sorts) the input array. If we want to count only 
     * inversions then we need to create a copy of original array and call mergeSort() on copy.
     */
    
    public static void main(String[] args) {
        int[] a = {10, 6, 15, 20, 30, 5, 7};
        System.out.println(countInversions(a)); // 10
    }
}
