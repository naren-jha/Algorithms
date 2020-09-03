package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/count-strings-can-formed-using-b-c-given-constraints/

public class CountStringWithABC {

    class SimpleRecursiveSolution {
        // T(n): Exp
        public int count(int n, int bCount, int cCount) {
            if (n == 0)
                return 1;
            
            int res = count(n-1, bCount, cCount);
            if (bCount > 0)
                res += count(n-1, bCount-1, cCount);
            if (cCount > 0)
                res += count(n-1, bCount, cCount-1);
            
            return res;
        }
    }
    
    class DPSolution {
        /*
         * T(n): O(n*bCount*cCount), S(n): O(n*bCount*cCount)
         * 
         * O(n*bCount*cCount) time complexity is more like linear time
         * because bCount and cCount are some small constant value. In 
         * this case it is 1, and 2 respectively, so the time and space 
         * complexities are 2*n = O(n)
         */
        public int count(int n, int bCount, int cCount) {
            int[][][] res = new int[n+1][bCount+1][cCount+1];
            
            for (int j = 0; j <= bCount; j++) {
                for (int k = 0; k <= cCount; k++) {
                    res[0][j][k] = 1; // when n == 0
                }
            }
            
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j <= bCount; j++) {
                    for (int k = 0; k <= cCount; k++) {
                        res[i][j][k] = res[i-1][j][k];
                        if (j > 0)
                            res[i][j][k] += res[i-1][j-1][k];
                        if (k > 0)
                            res[i][j][k] += res[i-1][j][k-1];
                    }
                }
            }
            return res[n][bCount][cCount];
        }
    }
    
    class ConstantTimeSolution {
        // T(n): O(1), S(n): O(1)
        public int count(int n, int bCount, int cCount) {
            /*
             * There are several combinations, lets see them one by one,
             * 
             * If characters in string are:
             * 
             * 1. all a's, count = n!/n! = 1
             * Explanation:
             * n characters can be arranged in n! ways, 
             * but since all characters are identical, so we divide by n!
             * 
             * 2. 1 b, and remaining a's, count = n!/(n-1)! = n
             * Explanation:
             * n characters can be arranged in n! ways, 
             * but since (n-1) a's are identical, so we divide by (n-1)!
             * 
             * 3. 1 c, and remaining a's, count = n!/(n-1)! = n
             * Explanation:
             * n characters can be arranged in n! ways, 
             * but since (n-1) a's are identical, so we divide by (n-1)!
             * 
             * 4. 1 b, 1 c, and remaining a's, count = n!/(n-2)! = n(n-1)
             * Explanation:
             * n characters can be arranged in n! ways, 
             * but since (n-2) a's are identical, so we divide by (n-2)!
             * 
             * 5. 2 c's, and remaining a's, count = n!/[(n-2)! 2!] = n(n-1)/2
             * Explanation:
             * n characters can be arranged in n! ways, 
             * but since (n-2) a's are identical, and 2 c's are also identical 
             * so we divide by (n-2)! and 2!
             * 
             * 6. 1 b, 2 c's, and remaining a's, count = n!/[(n-3)! 2!] = n(n-1)(n-2)/2
             * Explanation:
             * n characters can be arranged in n! ways, 
             * but since (n-3) a's are identical, and 2 c's are also identical 
             * so we divide by (n-3)! and 2!
             * 
             * Adding up all the cases we get
             * total count = 1 + 2n + n(n-1) + n(n-1)/2 + n(n-1)(n-2)/2
             * 
             * We can calculate above expression as it is, or we can 
             * simplify it further to minimize cost of multiplications
             * 
             * in above expression
             * n(n-1) + n(n-1)/2 + n(n-1)(n-2)/2 = (n^3 - n) / 2 = n(n^2 - 1) / 2
             * 
             * Therefore, total count = 1 + 2n + n(n^2 - 1) / 2
             * 
             * Source: https://jramellblog.wordpress.com/2018/01/27/o1-solution-to-count-of-strings-that-can-be-formed-using-a-b-and-c-under-given-constraints/
             */
            return 1 + 2*n + n*(n*n - 1)/2; 
        }
    }
    
    /*
     * The advantage of DP solution is that it will work for any given value 
     * of bCount and cCount, however constant time solution is specific to 
     * bCount = 1, and cCount = 2
     */
    
    public static void main(String[] args) {
        int n = 3;
        int bCount = 1;
        int cCount = 2;
        
        CountStringWithABC o = new CountStringWithABC();
        System.out.println(o.new SimpleRecursiveSolution().count(n, bCount, cCount)); // 19
        System.out.println(o.new DPSolution().count(n, bCount, cCount)); // 19
        System.out.println(o.new ConstantTimeSolution().count(n, bCount, cCount)); // 19
    }
}
