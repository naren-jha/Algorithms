package basic.array.problems;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// https://www.geeksforgeeks.org/given-an-array-a-and-a-number-x-check-for-pair-in-a-with-sum-as-x

public class CheckForAPairWithGivenSum {

    // APPROACH 1: Using Sorting
    // T(n): O(n*lgn), S(n): O(1)
    public static boolean isPair(int[] a, int sum) {
        Arrays.sort(a);
        
        int i = 0, j = a.length - 1;
        while (i < j) {
            if (a[i] + a[j] == sum)
                return true;
            else if (a[i] + a[j] < sum)
                i++;
            else
                j--;
        }
        return false;
    }
    // Above algorithm takes O(n*lgn) time due to sorting, we can do this in O(n) time
    // using some extra space
    
    // APPROACH 2: Using HashTable
    // T(n): O(n), S(n): O(n)
    public static boolean isPairLinearTime(int[] a, int sum) {
        Set<Integer> set = new HashSet<Integer>();
        
        for (int i = 0; i < a.length; i++) {
            if (set.contains(sum - a[i]))
                return true;
            set.add(a[i]);
        }
        return false;
    }
    // If input array is sorted, then approach 1 is better, else approach 2 is better
    
    public static void main(String[] args) {
        int[] a = {1, 4, 45, 6, 10, -8};
        int sum = 16;
        System.out.println(isPair(a, sum)); // true
        System.out.println(isPairLinearTime(a, sum)); // true
        
        sum = 2;
        System.out.println(isPair(a, sum)); // true
        System.out.println(isPairLinearTime(a, sum)); // true
        
        sum = 3;
        System.out.println(isPair(a, sum)); // false
        System.out.println(isPairLinearTime(a, sum)); // false
    }
}