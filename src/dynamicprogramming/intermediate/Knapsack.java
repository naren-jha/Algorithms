package dynamicprogramming.intermediate;

import java.util.Stack;

// https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/
// https://www.geeksforgeeks.org/printing-items-01-knapsack/

public class Knapsack {

    class SimpleRecursiveSolution {
        // T(n): Exp
        public int knapsack(int[] wt, int[] val, int n, int w) {
            if (n == 0)
                return 0;
            if (w == 0)
                return 0;
            
            if (w < wt[n-1]) // when last element cannot be included
                return knapsack(wt, val, n-1, w);
            else
                return Math.max(knapsack(wt, val, n-1, w - wt[n-1]) + val[n-1], 
                            knapsack(wt, val, n-1, w));
        }
    }
    
    class DPSolution {
        // bottom-up tabulation
        // T(n): O(nw), S(n): O(nw)
        public int knapsack(int[] wt, int[] val, int n, int w) {
            int[][] k = new int[n+1][w+1];
            
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= w; j++) {
                    if (i == 0 || j == 0)
                        k[i][j] = 0;
                    else if (j < wt[i-1]) // when last element cannot be included
                        k[i][j] = k[i-1][j];
                    else
                        k[i][j] = Math.max(k[i-1][j-wt[i-1]] + val[i-1], 
                                            k[i-1][j]);
                }
            }
            
            return k[n][w];
        }
        
        // T(n): O(nw), S(n): O(w)
        public int knapsackSpaceOptimized(int[] wt, int[] val, int n, int w) {
            int[][] k = new int[2][w+1];
            /*
             * We cannot use 1-d array because we need k[i-1][j-wt[i-1]]
             * which is previous row's some previous column value, which
             * would get overwritten if we use 1-d array
             */
            
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= w; j++) {
                    if (i == 0 || j == 0)
                        k[i % 2][j] = 0;
                    else if (j < wt[i-1]) // when last element cannot be included
                        k[i % 2][j] = k[(i-1) % 2][j];
                    else
                        k[i % 2][j] = Math.max(k[(i-1) % 2][j-wt[i-1]] + val[i-1], 
                                            k[(i-1) % 2][j]);
                }
            }
            
            return k[n % 2][w];
        }
        
        // We can also print corresponding subsets
        public void printKnapsack(int[] wt, int[] val, int n, int w) {
            int[][] k = new int[n+1][w+1];
            
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= w; j++) {
                    if (i == 0 || j == 0)
                        k[i][j] = 0;
                    else if (j < wt[i-1]) // when last element cannot be included
                        k[i][j] = k[i-1][j];
                    else
                        k[i][j] = Math.max(k[i-1][j-wt[i-1]] + val[i-1], 
                                            k[i-1][j]);
                }
            }
            
            // For wt = {1, 3, 4, 5} and val = {1, 4, 5, 7}
            // System.out.println(Arrays.deepToString(k));
            // [[0, 0, 0, 0, 0, 0, 0, 0], 
            //  [0, 1, 1, 1, 1, 1, 1, 1], 
            //  [0, 1, 1, 4, 5, 5, 5, 5], 
            //  [0, 1, 1, 4, 5, 6, 6, 9], 
            //  [0, 1, 1, 4, 5, 7, 8, 9]]
            
            // return k[n][w];
            int i = n, j = w;
            Stack<Integer> res = new Stack<Integer>();
            while (i > 0 && k[i][j] > 0) {
                if (k[i][j] != k[i-1][j]) {
                    res.push(wt[i-1]);
                    j = j - wt[i-1];
                }
                i--;
            }
            
            while (!res.isEmpty()) {
                System.out.print(res.pop() + " ");
            }
            System.out.println(); // new line after result
        }
        
    }
    
    // https://www.geeksforgeeks.org/unbounded-knapsack-repetition-items-allowed/
    // When multiple instances of same item is allowed
    class UnboundedKnapsack {
        // T(n): O(nw), S(n): O(nw)
        public int knapsack(int[] wt, int[] val, int n, int w) {
            int[][] k = new int[n+1][w+1];
            
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= w; j++) {
                    if (i == 0 || j == 0)
                        k[i][j] = 0;
                    else if (j < wt[i-1]) // when last element cannot be included
                        k[i][j] = k[i-1][j];
                    else
                        k[i][j] = Math.max(k[i][j-wt[i-1]] + val[i-1], 
                                            k[i-1][j]);
                }
            }
            
            return k[n][w];
        }
        
        // T(n): O(nw), S(n): O(w)
        public int knapsackSpaceOptimized(int[] wt, int[] val, int n, int w) {
            int[] k = new int[w+1];
            /*
             * We can use 1-d array here because we need k[i][j-wt[i-1]]
             * which is same row's some previous column value
             */
            
            for (int i = 0; i < n; i++) {
                for (int j = 0; j <= w; j++) {
                    if (i == 0 || j == 0)
                        k[j] = 0; // redundant in Java
                    else if (j >= wt[i]) // when last element can be included
                        k[j] = Math.max(k[j-wt[i]] + val[i], k[j]);
                }
            }
            
            return k[w];
        }
    }
    
    public static void main(String[] args) {
        int[] wt = {1, 3, 4, 5};
        int[] val = {1, 4, 5, 7};
        int w = 7;
        int n = 4;
        Knapsack k = new Knapsack();
        System.out.println(k.new SimpleRecursiveSolution().knapsack(wt, val, n, w)); // 9
        System.out.println(k.new DPSolution().knapsack(wt, val, n, w)); // 9
        System.out.println(k.new DPSolution().knapsackSpaceOptimized(wt, val, n, w)); // 9
        
        k.new DPSolution().printKnapsack(wt, val, n, w); // 3 4 
        
        // Unbounded knapsack test
        int[] wt2 = {1, 3, 4, 5};
        int[] val2 = {10, 40, 50, 70};
        int w2 = 8;
        int n2 = 4;
        System.out.println(k.new UnboundedKnapsack().knapsack(wt2, val2, n2, w2)); // 110
        System.out.println(k.new UnboundedKnapsack().knapsackSpaceOptimized(wt2, val2, n2, w2)); // 110
        
        int[] wt3 = {5, 10, 15};
        int[] val3 = {10, 30, 20, 70};
        int w3 = 100;
        int n3 = 3;
        System.out.println(k.new UnboundedKnapsack().knapsack(wt3, val3, n3, w3)); // 300
        System.out.println(k.new UnboundedKnapsack().knapsackSpaceOptimized(wt3, val3, n3, w3)); // 300
    }
}
