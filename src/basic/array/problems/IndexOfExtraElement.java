package basic.array.problems;

// https://practice.geeksforgeeks.org/problems/index-of-an-extra-element/1

public class IndexOfExtraElement {

    public static int extraElementAt(int[] a1, int[] a2, int low, int high) {
        // Base case: No extra element found
        if (low > high)
            return -1;
        
        // Base case: Only one element
        /*if (low == high) {
            if (a1[low] == a2[low])
                return -1;
            
            if (low > 0 && low < a1.length-1)
                if (a1[low-1] == a2[low-1] && a1[low+1] == a2[low])
                    return low;
            else if (low > 0 && low == a1.length-1)
                if (a1[low-1] == a2[low-1])
                    return low;
            else if (low == 0 && low < a1.length-1)
                if (a1[low+1] == a2[low])
                    return low;
            
            return -1;
        }*/
        
        int mid = (low + high) / 2;
        if (mid == a2.length)
            return mid;
        else if (a1[mid] == a2[mid])
            return extraElementAt(a1, a2, mid+1, high);
        else if (mid > 0 && mid < a1.length-1 && a1[mid-1] == a2[mid-1] && a1[mid+1] == a2[mid])
            return mid;
        else if (mid > 0 && mid == a1.length-1 && a1[mid-1] == a2[mid-1])
            return mid;
        else if (mid == 0 && mid < a1.length-1 && a1[mid+1] == a2[mid])
            return mid;
        else
            return extraElementAt(a1, a2, low, mid-1);
    }
    
    public static void main(String[] args) {
        int[] a1 = {2, 4, 6, 8, 10, 12};
        int[] a2 = {2, 4, 6, 8, 10, 12};
        System.out.println(extraElementAt(a1, a2, 0, a1.length-1));
    }
}
