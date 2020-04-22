package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * Solution of Traveling Salesman Problem using DP Approach (Iterative)
 * 
 * Time Complexity: O(n^2 * 2^n)
 * Space Complexity: O(n * 2^n)
 * 
 * https://youtu.be/09_LlHjoEiY?t=12011
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class TspDynamicProgrammingIterative {
    
    private final double[][] m;
    private final int N, S;
    private List<Integer> tour;
    private double minTourCost;
    
    public TspDynamicProgrammingIterative(double[][] m) {
        this(0, m);
    }

    public TspDynamicProgrammingIterative(int start, double[][] m) {
        N = m.length;

        if (N <= 2) throw new IllegalStateException("What on earth are you doing!");
        if (start < 0 || start >= N) throw new IllegalArgumentException("Invalid start node.");
        if (N != m[0].length) throw new IllegalArgumentException("Square matric required.");
        if (N > 32) throw new IllegalArgumentException("Matrix too large for computation");
       
        this.S = start;
        this.m = m;
    }
    
    public void tsp() {
        double[][] dp = new double[N][1 << N];
        setup(dp);
        solve(dp);
        findMinCost(dp);
        findOptimalTour(dp);
    }
    
    private void findOptimalTour(double[][] dp) {
        int lastIndex = S;
        int state = (1 << N) - 1; // END_STATE
        tour = new ArrayList<Integer>();
        
        tour.add(S); // first node
        
        // generate all nodes in the 'tour' list except the first and last node
        // the first and last node in the tour will be 'S'
        for (int i = N-1; i >= 1; --i) {
            int index = -1;
            for (int j = 0; j < N; ++j) {
                // ignore start node and already visited nodes
                if (j == S || notIn(j, state)) continue;
                
                if (index == -1) index = j;
                double prevDist = dp[index][state] + m[index][lastIndex];
                double newDist = dp[j][state] + m[j][lastIndex];
                if (newDist < prevDist)
                    index = j;
            }
            
            tour.add(index);
            state = state ^ (1 << index);
            lastIndex = index;
        }
        
        tour.add(S); // last node
        Collections.reverse(tour);
    }
    
    private void findMinCost(double[][] dp) {
        minTourCost = Double.POSITIVE_INFINITY;
        int END_STATE = (1 << N) - 1; // all bit set to 1
        
        for (int i = 0; i < N; ++i) {
            if (i == S) continue;
            double tourCost = dp[i][END_STATE] + m[i][S];
            if (tourCost < minTourCost)
                minTourCost = tourCost;
        }
    }

    private void solve(double[][] dp) {
        // solve for path of length = 3 to N
        for (int l = 3; l <= N; ++l) {
            for (int subset : combinations(l, N)) {
                if (notIn(S, subset)) continue;
                
                // consider all possible next nodes in this 'subset'
                for (int next = 0; next < N; ++next) {
                    if (next == S || notIn(next, subset)) continue;
                    
                    int subsetWihtoutNext = subset ^ (1 << next); // subset after excluding next node
                    double minDist = Double.POSITIVE_INFINITY;
                    
                    // consider all possible last visited node 
                    // for this 'next' node in this 'subset'
                    for (int end = 0; end < N; ++end) {
                        if (end == S || end == next || notIn(end, subset)) continue;
                        
                        double newDist = dp[end][subsetWihtoutNext] + m[end][next];
                        if (newDist < minDist)
                            minDist = newDist;
                    }
                    dp[next][subset] = minDist;
                }
            }
        }
    }
    
    // Checks if ith bit in the subset is 0 or 1
    private boolean notIn(int i, int subset) {
        return (subset & 1 << i) == 0;
    }
    
    // Generates all bit sets of size n where l bits are set to 1
    // For example, combinations(3, 4) = {1110, 1101, 1011, 0111}
    // The result is returned as a list of integer masks
    private List<Integer> combinations(int l, int n) {
        List<Integer> subsets = new ArrayList<>();
        combinations(0, 0, l, n, subsets);
        return subsets;
    }
    
    private void combinations(int set, int at, int l, int n, List<Integer> subsets) {
        // Return early if there are more elements left to select than what is available
        int numElementsLeft = n - at;
        if (numElementsLeft < l) return;
        
        // Valid set found. so add to list and return
        if (l == 0) {
            subsets.add(set);
            return;
        }
        
        for (int i = at; i < n; ++i) {
            // Flip on ith bit
            set = set | 1 << i;
            
            combinations(set, i + 1, l - 1, n, subsets);
            
            // Backtrack and flip off ith bit
            set = set & ~(1 << i);
        }
    }
    
    // Initializes dp table by caching optimal solution from
    // start node to every other node
    private void setup(double[][] dp) {
        for (int i = 0; i < N; ++i) {
            if (i == S) continue;
            dp[i][1 << S | 1 << i] = m[S][i];
        }
    }

    public static void main(String[] args) {
        // Create adjacency matrix
        int n = 6;
        double[][] distanceMatrix = new double[n][n];
        for (double[] row : distanceMatrix) 
            Arrays.fill(row, 10000);
        
        distanceMatrix[0][3] = 8;
        distanceMatrix[3][2] = 6;
        distanceMatrix[2][4] = 4;
        distanceMatrix[4][1] = 2;
        distanceMatrix[1][5] = 12;
        distanceMatrix[5][0] = 10;
        
        int startNode = 0;
        TspDynamicProgrammingIterative solver =
            new TspDynamicProgrammingIterative(startNode, distanceMatrix);
        
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
