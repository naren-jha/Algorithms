package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/dice-throw-dp-30/

public class DiceThrowProblem {

    /*
     * The idea is this: lets say if we are solving this problem for m = 1
     * that means we have n dice with each having only 1 face with value = 1
     * so we have got to get a sum of x by using n 1's: (1, 1, 1,.... up to n terms)
     * and since we have to include number from each dice to get sum, 
     * we can write recurrence for this as below:
     * f(n, 1, x) = f(n-1, 1, x-1)
     * 
     * Now if we have m = 2, i.e., n dice with each having 2 faces
     * then we have got n 1's and n 2's to make a sum of x
     * now since each dice can have value either 1 or 2
     * we can write recurrence as
     * f(n, 2, x) = f(n-1, 2, x-1) + f(n-1, 2, x-2)
     * 
     * Similarly if we have m = 3, then recurrence will be
     * f(n, 3, x) = f(n-1, 3, x-1) + f(n-1, 3, x-2) + f(n-1, 3, x-3)
     * 
     * If we generalize it for m face
     * f(n, m, x) = f(n-1, m, x-1) + f(n-1, m, x-2) + ... + f(n-1, m, x-m)
     * => f(n, m, x) = sum(f(n-1, m, x - faceValue)), 
     *                     where faceValue = (1, 2, 3,...., m)
     * 
     * So basically we include last dice into the sum, and then solve problem
     * for n-1 dice, and since each dice can have m different values, we consider 
     * all possibilities from m = (1 to m)
     * 
     * 
     * ** Base cases: **
     * if (x < 1)
     *     then sum cannot be formed as minimum number in dice is 1
     *     so even if we have n = 1 (only one dice), sum cannot be formed
     * 
     * if (n == 1)
     *     with only one dice, sum should be equal to face value of the single dice
     *     so if x is in the range 1 to m, then sum is possible, otherwise not.
     * 
     * 
     * ** We can also add following base cases to improve performance **
     * if (x > m*n)
     *     sum is not possible even with maximum value in each dice
     *     so return 0
     * 
     * if (x == m*n)
     *     there is only one way to get sum, i.e., having maximum value in each dice
     *     so return 1
     * 
     * if (x < n)
     *     sum cannot be formed even with minimum value (1) in each dice
     *     so return 0
     * 
     * if (x == n)
     *     there is only one way to get sum, i.e., having minimum value in each dice
     *     so return 1
     */
    
    class SimpleRecursiveSolution {
        // T(n,m) = O(m * 2^n)
        public int countWays(int n, int m, int x) {
            if (x < 1)
                return 0;
            if (n == 1) {
                if (x <= m)
                    return 1;
                else
                    return 0;
            }
            
            int  numberOfWays = 0;
            for (int faceValue = 1; faceValue <= m; faceValue++) {
                numberOfWays += countWays(n-1, m, x - faceValue);
            }
            return numberOfWays;
        }
    }
    
    class DPSolution {
        // T(n,m,x) = O(m*n*x), S(n,m,x) = O(n*x)
        public int countWays(int n, int m, int x) {
            int[][] res = new int[n+1][x+1];
            
            // fill first column
            for (int i = 0; i <= n; i++)
                res[i][0] = 0; // redundant in Java
            
            // fill second row
            for (int j = 1; j <= x; j++) {
                if (j <= m)
                    res[1][j] =    1;
                else
                    res[1][j] =    0;
            }
            // we don't need to fill first row as that will never be used
            // in result calculation
            
            // fill rest of the table
            for (int i = 2; i <= n; i++) {
                for (int j = 1; j <= x; j++) {
                    for (int fv = 1; fv <= j && fv <= m; fv++) {
                        res[i][j] += res[i-1][j - fv];
                    }
                }
            }
            
            return res[n][x];
        }
        
        /*
         * We can space optimize above solution, as in each iteration
         * we need only the immediate previous row, so we can manage
         * with just 2 rows. See solution below:
         */
        
        // T(n,m,x) = O(m*n*x), S(n,m,x) = O(x)
        public int countWaysSO(int n, int m, int x) {
            int[][] res = new int[2][x+1];
            
            // fill first column
            res[0][0] = 0; // redundant in Java
            res[1][0] = 0; // redundant in Java
            
            // fill second row
            for (int j = 1; j <= x; j++) {
                if (j <= m)
                    res[1][j] =    1;
                else
                    res[1][j] =    0;
            }
            
            // fill rest of the table
            for (int i = 2; i <= n; i++) {
                for (int j = 1; j <= x; j++) {
                    for (int fv = 1; fv <= j && fv <= m; fv++) {
                        res[i%2][j] += res[(i-1)%2][j - fv];
                    }
                }
            }
            
            return res[n%2][x];
        }
    }
    
    public static void main(String[] args) {
        DiceThrowProblem dt = new DiceThrowProblem();
        DiceThrowProblem.SimpleRecursiveSolution srs = dt.new SimpleRecursiveSolution();
        System.out.println(srs.countWays(2, 4, 1)); // 0
        System.out.println(srs.countWays(2, 2, 3)); // 2
        System.out.println(srs.countWays(3, 6, 8)); // 21
        System.out.println(srs.countWays(2, 4, 5)); // 4
        System.out.println(srs.countWays(3, 4, 5)); // 6
        
        System.out.println("----");
        DiceThrowProblem.DPSolution dps = dt.new DPSolution();
        System.out.println(dps.countWays(2, 4, 1)); // 0
        System.out.println(dps.countWays(2, 2, 3)); // 2
        System.out.println(dps.countWays(3, 6, 8)); // 21
        System.out.println(dps.countWays(2, 4, 5)); // 4
        System.out.println(dps.countWays(3, 4, 5)); // 6
        
        System.out.println("----");
        System.out.println(dps.countWaysSO(2, 4, 1)); // 0
        System.out.println(dps.countWaysSO(2, 2, 3)); // 2
        System.out.println(dps.countWaysSO(3, 6, 8)); // 21
        System.out.println(dps.countWaysSO(2, 4, 5)); // 4
        System.out.println(dps.countWaysSO(3, 4, 5)); // 6
    }
    
}
