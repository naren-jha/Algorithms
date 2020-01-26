package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/count-ways-reach-nth-stair-using-step-1-2-3/
// https://www.geeksforgeeks.org/count-number-of-ways-to-cover-a-distance/

public class CoverDistance {

    class SimpleRecursiveSolution {
        // T(n): Exp
        public int countWays(int n) {
            if (n == 0)
                return 1;
            if (n < 0)
                return 0;
            return countWays(n-3) + countWays(n-2) + countWays(n-1);
        }
    }
    
    class DPSolution {
        // bottom-up tabulation
        // T(n): O(n), S(n): O(n)
        public int countWays(int n) {
            int[] res = new int[n+1];
            
            res[0] = 1;
            res[1] = 1;
            res[2] = 2;
            
            for (int i = 3; i <= n; i++)
                res[i] = res[i-3] + res[i-2] + res[i-1];
            
            return res[n];
        }
        
        // we can space optimize above solution to O(1) by using 3 variables
    }
    
    public static void main(String[] args) {
        int n = 4;
        CoverDistance obj = new CoverDistance();
        System.out.println(obj.new SimpleRecursiveSolution().countWays(n)); // 7
        System.out.println(obj.new DPSolution().countWays(n)); // 7
    }
}
