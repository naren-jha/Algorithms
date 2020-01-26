package basic.array.problems;

import java.util.Arrays;

// https://www.geeksforgeeks.org/inplace-rotate-square-matrix-by-90-degrees/
// https://www.geeksforgeeks.org/rotate-matrix-90-degree-without-using-extra-space-set-2/
// https://www.geeksforgeeks.org/rotate-a-matrix-by-90-degree-in-clockwise-direction-without-using-any-extra-space/

public class InplaceRotateSquareMatrixBy90Degrees {

    // T(n): O(n^2), S(n): O(1)
    public static void rotateAnticlockWise(int[][] m) {
        int i = 0, j = m.length-1, tmp;
        while (i < j) {
            for (int k = i; k < j; k++) {
                tmp = m[i][k];
                m[i][k] = m[k][j];
                m[k][j] = m[j][j-k+i];
                m[j][j-k+i] = m[j-k+i][i];
                m[j-k+i][i] = tmp;
            }
            i++; j--;
        }
    }
    // Similarly we can rotate in clockwise direction as well
    
    /* APPROACH 2: easier to understand
     * https://www.geeksforgeeks.org/rotate-matrix-90-degree-without-using-extra-space-set-2/
     * 
     * Algorithm: 
     *         1. Find transpose of matrix.
     *         2. Reverse columns of the transpose matrix.
     */
    // T(n): O(n^2), S(n): O(1)
    public static void rotateAnticlockWise_A2(int[][] m) {
        transpose(m);
        reverseColumns(m);
    }
    
    private static void transpose(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = i+1; j < m.length; j++) {
                // swap
                int tmp = m[i][j];
                m[i][j] = m[j][i];
                m[j][i] = tmp;
            }
        }
    }
    
    private static void reverseColumns(int[][] m) {
        for (int col = 0; col < m.length; col++) {
            int i = 0, j = m.length-1;
            while (i < j) {
                // swap
                int tmp = m[i][col];
                m[i][col] = m[j][col];
                m[j][col] = tmp;
                
                i++; j--;
            }
        }
    }
    /*
     * To rotate in clockwise direction using approach 2:
     * 
     *         1. Find transpose of matrix.
     *         2. Reverse rows of the transpose matrix.
     */
    
    public static void main(String[] args) {
        int[][] m = {
                {1, 2, 3, 4}, 
                {5, 6, 7, 8}, 
                {9, 10, 11, 12}, 
                {13, 14, 15, 16}
        };
        
        rotateAnticlockWise(m);
        System.out.println(Arrays.deepToString(m));
        // [[4, 8, 12, 16], [3, 7, 11, 15], [2, 6, 10, 14], [1, 5, 9, 13]]
        
        int[][] m2 = {
                {1, 2, 3, 4}, 
                {5, 6, 7, 8}, 
                {9, 10, 11, 12}, 
                {13, 14, 15, 16}
        };
        rotateAnticlockWise_A2(m2);
        System.out.println(Arrays.deepToString(m2));
        // [[4, 8, 12, 16], [3, 7, 11, 15], [2, 6, 10, 14], [1, 5, 9, 13]]
    }
}
