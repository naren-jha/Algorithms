package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/maximum-profit-sale-wines/

public class MaxProfitWineSale {
    
    class SimpleRecursiveSolution {
        // T(n): Exp
        public int maxProfit(int[] a, int beg, int end) {
            int year = a.length - (end - beg);
            if (beg == end)
                return a[beg]*year;
            
            return Math.max(maxProfit(a, beg, end-1) + a[end]*year, 
                            maxProfit(a, beg+1, end) + a[beg]*year);
        }
    }

    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(n^2), S(n): O(n^2)
        public int maxProfit(int[] a) {
            int n = a.length;
            
            // res[i][j] indicates maximum profit made by selling wines a[i...j]
            // res[0][n-1] therefore will hold the final result
            int[][] res = new int[n][n];
            
            int year;
            for (int i = n-1; i >= 0; i--) {
                for (int j = i; j < n; j++) {
                    year = n - (j - i);
                    if (i == j) // when beg == end
                        res[i][j] = a[i]*year;
                    else
                        res[i][j] = Math.max(res[i][j-1] + a[j]*year, 
                                                res[i+1][j] + a[i]*year);
                }
            }
            return res[0][n-1];
        }
        
        /*
         * We can also print the optimum sell result 
         * i.e., the element (beg/end) which are sold every year
         * See solution below
         */
        public int maxProfitWithOptimumSellRes(int[] a) {
            int n = a.length;
            
            // res[i][j] indicates maximum profit made by selling wines a[i...j]
            // res[0][n-1] therefore will hold the final result
            int[][] res = new int[n][n];
            
            // We create another array to store directions of sell made
            // we store 1 in sell[i][j] if 'beginning' element is sold to get maximum
            // profit in a[i...j], else we store 0 to indicate 'end' element is sold 
            // to get maximum profit in a[i...j]
            int[][] sell = new int[n][n];
            
            int year;
            for (int i = n-1; i >= 0; i--) {
                for (int j = i; j < n; j++) {
                    year = n - (j - i);
                    if (i == j) { // when beg == end
                        res[i][j] = a[i]*year;
                        sell[i][j] = 1;
                    }
                    else {
                        int endRes = res[i][j-1] + a[j]*year;
                        int begRes = res[i+1][j] + a[i]*year;
                        
                        if (begRes > endRes) {
                            res[i][j] = begRes;
                            sell[i][j] = 1;
                        }
                        else {
                            res[i][j] = endRes;
                            sell[i][j] = 0; // redundant in Java
                        }
                    }
                }
            }
            
            int i = 0, j = n-1;
            while (i <= j) {
                if (sell[i][j] == 1) {
                    System.out.print("beg ");
                    i++;
                }
                else {
                    System.out.print("end ");
                    j--;
                }
            }
            System.out.println();
            
            return res[0][n-1];
        }
    }
    
    public static void main(String[] args) {
        MaxProfitWineSale o = new MaxProfitWineSale();
        int[] a = {2, 4, 6, 2, 5};
        
        System.out.println(o.new SimpleRecursiveSolution().maxProfit(a, 0, a.length-1)); // 64
        System.out.println(o.new DPSolution().maxProfit(a)); // 64
        
        System.out.println(o.new DPSolution().maxProfitWithOptimumSellRes(a));
        // beg end end beg beg
        // 64
    }
}
