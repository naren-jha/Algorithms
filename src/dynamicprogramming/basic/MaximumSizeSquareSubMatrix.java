package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/maximum-size-sub-matrix-with-all-1s-in-a-binary-matrix/

public class MaximumSizeSquareSubMatrix {

    public static void printMaxSubSquare(int[][] m) {
        int r = m.length, c = m[0].length;
        int[][] s = new int[r][c];
        
        // fill first column
        for (int i = 0; i < r; i++)
            s[i][0] = m[i][0];
        
        // fill first row
        for (int j = 1; j < c; j++)
            s[0][j] = m[0][j];
        
        // construct rest of the table
        for (int i = 1; i < r; i++) {
            for (int j = 1; j < c; j++) {
                if (m[i][j] == 1)
                    s[i][j] = Math.min(s[i][j-1], Math.min(s[i-1][j], s[i-1][j-1])) + 1;
                else
                    s[i][j] = 0;
            }
        }
        
        // find maximum entry and corresponding indices in 's' table
        int max_i = 0, max_j = 0, max_of_s = s[0][0];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (max_of_s < s[i][j]) {
                    max_of_s = s[i][j];
                    max_i = i;
                    max_j = j;
                }
            }
        }
        
        // print maximum size square sub matrix of 1's
        System.out.println("Max size square sub-matrix of 1's is: ");
        for (int i = max_i; i > max_i - max_of_s; i--) {
            for (int j = max_j; j > max_j - max_of_s; j--) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println(); // new line after each row
        }
    }
    
    public static void main(String[] args) {
        int[][] m = {
                        {0, 1, 1, 0, 1},  
                        {1, 1, 0, 1, 0},  
                        {0, 1, 1, 1, 0}, 
                        {1, 1, 1, 1, 0}, 
                        {1, 1, 1, 1, 1}, 
                        {0, 0, 0, 0, 0}
                    };
        
        printMaxSubSquare(m);
        /*
         * Max size square sub-matrix of 1's is: 
         * 1 1 1 
         * 1 1 1 
         * 1 1 1 
         */
    }
}
