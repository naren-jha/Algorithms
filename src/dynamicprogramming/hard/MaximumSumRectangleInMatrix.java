package dynamicprogramming.hard;

import java.util.Arrays;

/*
 * https://www.geeksforgeeks.org/maximum-sum-rectangle-in-a-2d-matrix-dp-27/
 * https://www.youtube.com/watch?v=-FgseNO-6Gk
 * https://www.youtube.com/watch?v=yCQN096CwWM
 */
public class MaximumSumRectangleInMatrix {
    
    /*
     * The idea is this:
     * 
     *   We fix the left and right columns one by one and find the maximum sum 
     *   contiguous rows for every left and right column combination
     *   
     *   i.e., For every left and right index of the POSSIBLE maximum sum rectangle,
     *   we want to know the largest sum we can yield VERTICALLY so that we can
     *   deduce the best top index and bottom index.
     *   
     *   This is why we keep the array of running sums (tmp[]) for each row. 
     *   We are interested in best vertical value to start and end at (for each 
     *   attempt of all the combinations of left and right index values).
     *   
     *   To summarize,
     *   For every left and right column combination, we need to find the top and 
     *   bottom row combination, which leads to maximum sum. 
     *   To find top and bottom row combination, we take a tmp[] array of 'rows' 
     *   size, we keep adding current 'right' column elements to it, and keep 
     *   finding max sum contiguous subarray using kadane's algorithm. 
     *   Kadanes algorithm gives us the max sum possible for a given left and right 
     *   column bound, it also gives us the top and bottom indices for the current 
     *   left and right index combination, top and bottom is start and end of max
     *   sum contiguous subarray.
     *   Everytime we run Kadane's algorithm, we check if it's result beats the optimal 
     *   solution (i.e., max sum) so far, if so, then we update the max sum, and the 
     *   left, right, top, bottom indices.
     */
    
    /*
     * Time complexity will be O(cols^2 * rows), where O(rows) is contributed by 
     * Kadane's algorithm. Space complexity will be O(rows).
     * 
     * If we do it other way around, like fix the top and bottom indices instead of 
     * left and right, and then run Kadane's algorithm on tmp[] array of size cols, 
     * then time complexity will become O(rows^2 * cols), and space complexity O(cols)
     */
    
    public Result maxSum(int[][] m) {
        int rows = m.length;
        int cols = rows != 0 ? m[0].length : 0;
        
        int[] tmp = new int[rows];
        Result result = new Result();
        for (int left = 0; left < cols; left++) {
            
            Arrays.fill(tmp, 0);
            
            for (int right = left; right < cols; right++) {
                
                for (int i = 0; i < rows; i++)
                    tmp[i] += m[i][right];
                
                KadaneResult kadaneResult = kadane(tmp);
                
                if (result.maxSum < kadaneResult.maxSum) {
                    result.maxSum = kadaneResult.maxSum;
                    result.left = left;
                    result.right = right;
                    result.top = kadaneResult.start;
                    result.bottom = kadaneResult.end;
                }
            }
        }
        return result;
    }
    
    private class Result {
        int maxSum;
        int left, right, top, bottom;
        
        @Override
        public String toString() {
            return "Max rectangle sum = " + maxSum + "\n"
                    + "left bound = " + left + "\n"
                    + "right bound = " + right + "\n"
                    + "top bound = " + top + "\n"
                    + "bottom bound = " + bottom;
        }
    }
    
    private KadaneResult kadane(int[] a) {
        int maxSoFar = Integer.MIN_VALUE;
        int maxEndingHere = 0;
        int start = 0, end = -1, sp = 0;
        
        for (int i = 0; i < a.length; i++) {
            maxEndingHere += a[i];
            
            if (maxEndingHere < 0) {
                maxEndingHere = 0;
                sp = i+1;
            }
            else if (maxSoFar < maxEndingHere) {
                maxSoFar = maxEndingHere;
                start = sp;
                end = i;
            }
        }
        
        // there is at least one positive number
        if (end != -1)
            return new KadaneResult(maxSoFar, start, end);
        
        // Special case: when all numbers in array a[] are negative
        maxSoFar = a[0]; start = 0; end = 0;
        for (int i = 0; i < a.length; i++) {
            if (maxSoFar < a[i]) {
                maxSoFar = a[i];
                start = i; end = i;
            }
        }
        
        return new KadaneResult(maxSoFar, start, end);
    }

    private class KadaneResult {
        int maxSum, start, end;
        
        KadaneResult(int maxSum, int start, int end) {
            this.maxSum = maxSum;
            this.start = start;
            this.end = end;
        }
    }
    
    public static void main(String[] args) {
        int[][] m = {
                        { 1,  2, -1, -4, -20},
                        {-8, -3,  4,  2,  1},
                        { 3,  8, 10,  1,  3},
                        {-4, -1,  1,  7, -6}
                    };
        
        System.out.println(new MaximumSumRectangleInMatrix().maxSum(m));
        /*
         * Max rectangle sum = 29
         * left bound = 1
         * right bound = 3
         * top bound = 1
         * bottom bound = 3
         */
    }
}
