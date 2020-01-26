package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/minimum-cells-required-reach-destination-jumps-equal-cell-values/

public class MinCellsRequiredToReachDestination {

    class SimpleRecursiveSolution {
        int R, C, MAX_VAL;
        
        // T(n): Exp
        public int minCells(int[][] a) {
            R = a.length; C = a[0].length;
            MAX_VAL = Integer.MAX_VALUE - Math.max(R, C);
            
            int minCell = minCells(a, 0, 0);
            return minCell >= MAX_VAL ? -1 : minCell;
        }
        
        private int minCells(int[][] a, int r, int c) {
            // Base cases:
            if (r > R-1 || c > C-1)
                return MAX_VAL;
            if (r == R-1 && c == C-1)
                return 1;
            
            return 1 + Math.min(minCells(a, r + a[r][c], c), 
                                minCells(a, r, c + a[r][c]));
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(r*c), S(n): O(r*c)
        public int minCells(int[][] a) {
            int r = a.length, c = a[0].length;
            int MAX_VAL = Integer.MAX_VALUE - Math.max(r, c);
            
            int[][] res = new int[r][c];
            
            int bottom, right, next_i, next_j;
            for (int i = r-1; i >= 0; i--) {
                for (int j = c-1; j >= 0; j--) {
                    if (i == r-1 && j == c-1) {
                        res[i][j] = 1;
                        continue;
                    }
                    next_i = i + a[i][j];
                    bottom =  (next_i < r) ? res[next_i][j] : MAX_VAL;
                    next_j = j + a[i][j];
                    right = (next_j < c) ? res[i][next_j] : MAX_VAL;
                    res[i][j] = 1 + Math.min(bottom, right);
                }
            }
            return res[0][0] >= MAX_VAL ? -1 : res[0][0];
        }
    }
    
    public static void main(String[] args) {
        MinCellsRequiredToReachDestination o = new MinCellsRequiredToReachDestination();
        int[][] a = { { 2, 3, 2, 1, 4 }, 
                      { 3, 2, 5, 8, 2 }, 
                      { 1, 1, 2, 2, 1 } };
        System.out.println(o.new SimpleRecursiveSolution().minCells(a)); // 4
        System.out.println(o.new DPSolution().minCells(a)); // 4
    }
}
