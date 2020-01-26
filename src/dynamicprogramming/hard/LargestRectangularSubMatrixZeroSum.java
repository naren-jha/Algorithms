package dynamicprogramming.hard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// https://www.geeksforgeeks.org/largest-rectangular-sub-matrix-whose-sum-0/

public class LargestRectangularSubMatrixZeroSum {

    // This problem is similar to previous problem 'Maximum sum rectangle in a 2D matrix'
    
    // TC: O(cols^2 * rows), SC: O(rows)
    public Result largestRectZeroSum(int[][] m) {
        int rows = m.length;
        int cols = m[0].length;
        
        int[] tmp = new int[rows];
        Result result = new Result();
        for (int left = 0; left < cols; left++) {
            
            Arrays.fill(tmp, 0);
            
            for (int right = left; right < cols; right++) {
                
                for (int i = 0; i < rows; i++)
                    tmp[i] += m[i][right];
                
                ZeroSumResult zeroSumResult = zeroSum(tmp);
                int size = (right - left + 1) * (zeroSumResult.end - zeroSumResult.start + 1);
                
                if (zeroSumResult.isZeroSum && size > result.maxSize) {
                    result.maxSize = size;
                    result.left = left;
                    result.right = right;
                    result.top = zeroSumResult.start;
                    result.bottom = zeroSumResult.end;
                }
            }
        }
        return result;
    }
    
    private class Result {
        int maxSize;
        int left, right, top, bottom;
        
        @Override
        public String toString() {
            return "Largest size of rectangle with zero sum = " + maxSize + "\n"
                    + "left bound = " + left + "\n"
                    + "right bound = " + right + "\n"
                    + "top bound = " + top + "\n"
                    + "bottom bound = " + bottom;
        }
    }
    
    // https://www.geeksforgeeks.org/find-the-largest-subarray-with-0-sum/
    private ZeroSumResult zeroSum(int[] a) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        int sum = 0, maxLen = 0, currLen = 0;
        int start = 0, end = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
            
            if (a[i] ==    0 && maxLen == 0) {
                maxLen = 1;
                start = i;
                end = i;
            }
            
            if (sum == 0) {
                maxLen = i+1;
                start = 0;
                end = i;
            }
            
            if (map.containsKey(sum)) {
                currLen = i - map.get(sum);
                if (currLen > maxLen) {
                    maxLen = currLen;
                    start = map.get(sum) + 1;
                    end = i;
                }
            }
            else {
                map.put(sum, i);
            }
        }
        return new ZeroSumResult(maxLen != 0, start, end);
    }

    private class ZeroSumResult {
        boolean isZeroSum; int start, end;
        
        ZeroSumResult(boolean isZeroSum, int start, int end) {
            this.isZeroSum = isZeroSum;
            this.start = start;
            this.end = end;
        }
    }
    
    public static void main(String[] args) {
        int[][] m = { 
                        { 9, 7, 16, 5 },
                        { 1, -6, -7, 3 }, 
                        { 1, 8, 7, 9 },
                        { 7, -2, 0, 10 }
                    };
        
        Result result = new LargestRectangularSubMatrixZeroSum().largestRectZeroSum(m);
        System.out.println(result);
        /*
         * Largest size of rectangle with zero sum = 6
         * left bound = 1
         * right bound = 2
         * top bound = 1
         * bottom bound = 3
         */
        
        // Printing actual largest rectangular sub-matrix with zero sum
        System.out.println("\nLargest rectangular sub-matrix is:");
        for (int r = result.top; r <= result.bottom; r++) {
            for (int c = result.left; c <= result.right; c++) {
                System.out.print(m[r][c] + " ");
            }
            System.out.println();
        }
        /*
         * Largest rectangular sub-matrix is:
         * -6 -7 
         * 8 7 
         * -2 0 
         */
    }
}
