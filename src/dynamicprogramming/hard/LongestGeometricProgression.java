package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/longest-geometric-progression/

public class LongestGeometricProgression {

    // This problem is similar to 'Longest Arithmetic Progression' problem
    
    // T(n): O(n^2), S(n): O(n^2)
    public static int lengthOfLongestGP(int[] a) {
        int n = a.length;
        if (n < 2)
            return n;
        if (n == 2)
            return (a[1] % a[0] == 0) ? 2 : 1;
        
        // L[i][j] stores length of LGP with a[i] as first and a[j] as 
        // second element of GP, where i < j
        int[][] L = new int[n][n]; // first column and last row of table is never used
        
        int llgp = 1;
        
        // fill entries in last column
        for (int i = 0; i < n-1; i++) {
            if (a[n-1] % a[i] == 0) {
                L[i][n-1] = 2;
                llgp = 2;
            }
            else {
                L[i][n-1] = 1;
            }
        }
        
        // fill rest of the table in bottom-up manner, and keep updating llgp
        for (int j = n-2; j >= 1; j--) {
            int i = j-1, k = j+1;
            while (i >= 0 && k < n) {
                if (a[i] * a[k] < a[j] * a[j]) {
                    k++;
                }
                else if (a[i] * a[k] > a[j] * a[j]) {
                    if (a[j] % a[i] == 0) {
                        L[i][j] = 2;
                        llgp = 2;
                    }
                    else {
                        L[i][j] = 1;
                    }
                    i--;
                }
                else { // if (a[i] * a[k] == a[j] * a[j])
                    L[i][j] = L[j][k] + 1;
                    llgp = Math.max(llgp, L[i][j]);
                    i--; k++;
                }
            }
            
            // If the loop was stopped due to k becoming more than 
            // n-1, set the remaining enties in column j
            while (i >= 0) {
                if (a[j] % a[i] == 0) {
                    L[i][j] = 2;
                    llgp = 2;
                }
                else {
                    L[i][j] = 1;
                }
                i--;
            }
        }
        
        return llgp;
    }
    
    public static void main(String[] args) {
        int[] a = {1, 3, 9, 27, 81, 243};
        System.out.println(lengthOfLongestGP(a)); // 6
        
        a = new int[] {1, 3, 4, 9, 7, 27};
        System.out.println(lengthOfLongestGP(a)); // 4
        
        a = new int[] {2, 3, 5, 7, 11, 8};
        System.out.println(lengthOfLongestGP(a)); // 2
        
        a = new int[] {2, 8, 5, 7, 11, 13};
        System.out.println(lengthOfLongestGP(a)); // 2
        
        a = new int[] {2, 3, 5, 7, 11, 13};
        System.out.println(lengthOfLongestGP(a)); // 1
    }
}
