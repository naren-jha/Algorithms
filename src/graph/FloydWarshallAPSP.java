package graph;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * Floyd-Warshall Algorithm for all-pair shortest path
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class FloydWarshallAPSP {
    
    private static final int REACHED_NEGATIVE_CYCLE = -1;
    
    public FWResult allPairShortestPath(double[][] m) {
        int n = m.length;
        if (n == 0) throw new IllegalStateException("empty graph.");
        
        double[][] dp = new double[n][n];
        int[][] next = new int[n][n];
        
        setup(m, n, dp, next);
        
        // compute all pair shortest path
        for (int k = 0; k < n; ++k) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (dp[i][k] + dp[k][j] < dp[i][j]) {
                        dp[i][j] = dp[i][k] + dp[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
        
        detectNegativeCycles(n, dp, next);
        
        return new FWResult(dp, next);
    }

    private void detectNegativeCycles(int n, double[][] dp, int[][] next) {
        // detect negative cycles, if any
        for (int k = 0; k < n; ++k) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (dp[i][k] + dp[k][j] < dp[i][j]) {
                        dp[i][j] = NEGATIVE_INFINITY;
                        next[i][j] = REACHED_NEGATIVE_CYCLE;
                    }
                }
            }
        }
    }

    private void setup(double[][] m, int n, double[][] dp, int[][] next) {
        // setup dp[][] and next[][] matrices
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                dp[i][j] = m[i][j];
                if (m[i][j] != POSITIVE_INFINITY)
                    next[i][j] = j;
            }
        }
    }
    
    private class FWResult {
        double[][] dp;
        int[][] next;
        FWResult(double[][] dp, int[][] next) {
            this.dp = dp;
            this.next = next;
        }
    }
    
    private List<Integer> reconstructPath(int start, int end,  double[][] dp, int[][] next) {
        List<Integer> path = new ArrayList<Integer>();
        if (dp[start][end] == POSITIVE_INFINITY) return path;
        int at = start;
        for (; at != end; at = next[at][end]) {
            if (at == REACHED_NEGATIVE_CYCLE) return null;
            path.add(at);
        }
        
        if (next[at][end] == REACHED_NEGATIVE_CYCLE) return null;
        path.add(end);
        return path;
    }

    public static void main(String[] args) {
        int n = 7;
        double[][] m = new double[n][n];
        for (int i = 0; i < n ; ++i) {
            Arrays.fill(m[i], POSITIVE_INFINITY);
            m[i][i] = 0;
        }
        // edges in graph
        m[0][1] = 2;
        m[0][2] = 5;
        m[0][6] = 10;
        m[1][2] = 2;
        m[1][4] = 11;
        m[2][6] = 2;
        m[6][5] = 11;
        m[4][5] = 1;
        m[5][4] = -2;
        
        FloydWarshallAPSP fw = new FloydWarshallAPSP();
        FWResult result = fw.allPairShortestPath(m);
        
        // print all pair shortest paths
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.printf("Shortest path length from node %d to %d is %.2f \n", i, j, result.dp[i][j]);
            }
        }
        /*
         *  Shortest path length from node 0 to 0 is 0.00 
            Shortest path length from node 0 to 1 is 2.00 
            Shortest path length from node 0 to 2 is 4.00 
            Shortest path length from node 0 to 3 is Infinity 
            Shortest path length from node 0 to 4 is -Infinity 
            Shortest path length from node 0 to 5 is -Infinity 
            Shortest path length from node 0 to 6 is 6.00 
            Shortest path length from node 1 to 0 is Infinity 
            Shortest path length from node 1 to 1 is 0.00 
            Shortest path length from node 1 to 2 is 2.00 
            .....
         */
        
        // reconstruct path
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                List<Integer> path = fw.reconstructPath(i, j, result.dp, result.next);
                if (path == null) {
                    System.out.printf("sp(%d, %d) has infinite number of solutions (due to -ve cycle) \n", i, j);
                }
                else if (path.size() == 0) {
                    System.out.printf("sp(%d, %d) DOES NOT EXIST \n", i, j);
                }
                else {
                    StringJoiner joiner = new StringJoiner("->");
                    for (Integer item : path) {
                        joiner.add(item.toString());
                    }
                    String pathString = "[" + joiner + "]";
                    System.out.printf("sp(%d, %d) = %s \n", i, j, pathString);
                }
            }
        }
        /*
         *  sp(0, 0) = [0] 
            sp(0, 1) = [0->1] 
            sp(0, 2) = [0->1->2] 
            sp(0, 3) DOES NOT EXIST 
            sp(0, 4) has infinite number of solutions (due to -ve cycle) 
            sp(0, 5) has infinite number of solutions (due to -ve cycle) 
            sp(0, 6) = [0->1->2->6] 
            sp(1, 0) DOES NOT EXIST 
            sp(1, 1) = [1] 
            sp(1, 2) = [1->2] 
            .....
         */
    }
}
