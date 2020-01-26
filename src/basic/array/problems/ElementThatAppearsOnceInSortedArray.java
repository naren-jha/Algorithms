package basic.array.problems;

// https://practice.geeksforgeeks.org/problems/find-the-element-that-appears-once-in-sorted-array/0

public class ElementThatAppearsOnceInSortedArray {

    public static int getElement(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i += 2)
            if (i+1 == n || a[i] != a[i+1])
                return a[i];
        return -1;
    }
    
    public static void main(String[] args) {
        int[] a = {1, 1, 2, 2, 3, 3, 4, 50, 50, 65, 65};
        System.out.println(getElement(a));
    }
}
