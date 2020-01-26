package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/highway-billboard-problem/

public class HighwayBillboardProblem {

    class SimpleRecursiveSolution {
        // T(n): Exp
        public int maxRevenue(int[] x, int[] r, int m, int t, int i, int lbb) {
            if (i == 0)
                return 0;
            
            // if site is with in t miles, or out of m miles,
            // then we cannot consider this site
            if (lbb - x[i-1] <= t || x[i-1] > m)
                return maxRevenue(x, r, m, t, i-1, lbb);
            else
                return Math.max(r[i-1] + maxRevenue(x, r, m, t, i-1, x[i-1]), 
                                maxRevenue(x, r, m, t, i-1, lbb));
        }
    }
    
    // Above solution can be memoized or tabulated
    
    public static void main(String[] args) {
        HighwayBillboardProblem o = new HighwayBillboardProblem();
        int m = 20;
        int t = 5;
        int[] x = {6, 7, 12, 13, 14};
        int[] r = {5, 6, 5, 3, 1};
        int n = x.length;
        
        System.out.println(o.new SimpleRecursiveSolution()
                            .maxRevenue(x, r, m, t, n, Integer.MAX_VALUE)); // 10
    }
}
