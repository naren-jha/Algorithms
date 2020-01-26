package basic.array.problems;

// https://www.geeksforgeeks.org/maximum-sum-iarri-among-rotations-given-array/

public class MaximumSumAmongAllRotations {

    /*
     *  {8, 3, 1, 2} = 8*0 + 3*1 + 1*2 + 2*3 = 11
        {3, 1, 2, 8} = 3*0 + 1*1 + 2*2 + 8*3 = 29
        {1, 2, 8, 3} = 1*0 + 2*1 + 8*2 + 3*3 = 27
        {2, 8, 3, 1} = 2*0 + 8*1 + 3*2 + 1*1 = 17
     */
    
    // T(n): O(n), S(n): O(1)
    public static int maxSum(int[] a) {
        int n = a.length;
        
        int sum = 0; // sum of all elements
        int currSum = 0; // initial configuration: sum without any rotation
        for (int i = 0; i < n; i++) {
            sum += a[i];
            currSum += i * a[i];
        }

        // try rest of n-1 rotation
        int res = currSum;
        for (int i = 0; i < n-1; i++) {
            currSum += (a[i] * (n-1)) - (sum - a[i]);
            res = Math.max(res, currSum);
        }
        
        return res;
    }
    
    public static void main(String[] args) {
        int[] a = {8, 3, 1, 2};
        System.out.println(maxSum(a)); // 29
    }
}
