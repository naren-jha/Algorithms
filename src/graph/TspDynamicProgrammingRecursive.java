package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * Solution of Traveling Salesman Problem using DP Approach (Recursive)
 * 
 * The main idea is that since we need to do all n! permutations of nodes to find 
 * the optimal solution, caching the results of sub paths can improve performance
 *
 * For example, if a permutation is: '... 3 1 2 0' and later say we have to 
 * calculate another permutation '... 4 2 1 0', we would have already cached 
 * the answer for the subgraph containing the nodes {0, 1, 2}, consequently
 * improving on performance
 * 
 * Time Complexity: O(n^2 * 2^n)
 * Space Complexity: O(n * 2^n)
 * 
 * https://youtu.be/09_LlHjoEiY?t=12011
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class TspDynamicProgrammingRecursive {
    
    private final double[][] m;
    private final int N, S;
    private List<Integer> tour;
    private double minTourCost;
    
    private final int END_STATE;
    
    public TspDynamicProgrammingRecursive(double[][] m) {
        this(0, m);
    }

    public TspDynamicProgrammingRecursive(int start, double[][] m) {
        N = m.length;

        if (N <= 2) throw new IllegalStateException("What on earth are you doing!");
        if (start < 0 || start >= N) throw new IllegalArgumentException("Invalid start node.");
        if (N != m[0].length) throw new IllegalArgumentException("Square matric required.");
        if (N > 32) throw new IllegalArgumentException("Matrix too large for computation");
       
        this.S = start;
        this.m = m;
        
        // When all bits are set to 1. Means when all nodes are visited
        END_STATE = (1 << N) - 1;
        
        tour = new ArrayList<Integer>();
    }
    
    public void tsp() {
        Double[][] dp = new Double[N][1 << N];
        Integer[][] nextNode = new Integer[N][1 << N];
        
        int state = 1 << S;
        minTourCost = tspUtil(S, state, dp, nextNode);
        
        // Regenerate path
        Integer index = S;
        state = S;
        while (index != null) {
            tour.add(index);
            state = state | (1 << index);
            index = nextNode[index][state];
        }
        
        tour.add(S);
    }
    
    public double tspUtil(int i, int state, Double[][] dp, Integer[][] nextNode) {
        // If this tour is done, return cost of going back to start node
        if (state == END_STATE) return m[i][S];
        
        // If already computed before, return the cached result
        if (dp[i][state] != null) return dp[i][state];
        
        double minCost = Double.MAX_VALUE;
        int index = -1;
        for (int next = 0; next < N; ++next) {
            // Skip already visited nodes
            if ((state & (1 << next)) != 0) continue;
            
            int nextState = state | (1 << next);
            double newCost = m[i][next] + tspUtil(next, nextState, dp, nextNode);
            if (newCost < minCost) {
                minCost = newCost;
                index = next;
            }
        }
        
        nextNode[i][state] = index;
        return dp[i][state] = minCost;
    }
    
    

    public static void main(String[] args) {
        // Create adjacency matrix
        int n = 5;
        double[][] dist = new double[n][n];
        for (double[] row : dist) 
            Arrays.fill(row, 100);
     
        // Assume matrix is symmetric for simplicity.
        dist[1][3] = dist[3][1] = 1;
        dist[3][0] = dist[0][3] = 2;
        dist[0][2] = dist[2][0] = 3;
        dist[2][4] = dist[4][2] = 4;
        dist[4][1] = dist[1][4] = 5;
        
        TspDynamicProgrammingRecursive solver = 
                new TspDynamicProgrammingRecursive(dist);
        
        solver.tsp();
        
        System.out.println("Tour Cost: " + solver.minTourCost); // Tour Cost: 42.0
        
        StringJoiner joiner = new StringJoiner(" -> ");
        for (Integer cs: solver.tour) {
            joiner.add(cs.toString());
        }
        System.out.println("Tour: " + joiner.toString());
        // Tour: 0 -> 3 -> 2 -> 4 -> 1 -> 5 -> 0
    }

}
