package graph;

import static java.lang.Double.POSITIVE_INFINITY;

import java.util.Arrays;

/**
 * TransitiveClosure Of A Graph
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class TransitiveClosure extends Graph {

    public TransitiveClosure(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    // TC: O(V*(V+E))
    public void transitiveClosure() {
        boolean[][] tc = new boolean[numberOfVertices][numberOfVertices];
        
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            DFSUtil(vn, vn, tc);
        }
        
        for (int i = 0; i < numberOfVertices; ++i) {
            for (int j = 0; j < numberOfVertices; ++j)
                System.out.print(tc[i][j] ? "1 " : "0 ");
            System.out.println();
        }
    }

    private void DFSUtil(int s, int d, boolean[][] tc) {
        tc[s][d] = true;
        
        for (Edge edge : adjList.get(d)) {
            int d2 = edge.to;
            if (!tc[s][d2])
                DFSUtil(s, d2, tc);
        }
    }
    
    // Approach 2: using Floyd-Warshall Algorithm
    public void transitiveClosureUsingFW(double[][] m) {
        int n = m.length;
        boolean[][] tc = new boolean[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                tc[i][j] = m[i][j] != Double.POSITIVE_INFINITY;
            }
        }
        
        for (int k = 0; k < n; ++k) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    tc[i][j] = tc[i][j] || (tc[i][k] && tc[k][j]);
                }
            }
        }
        
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j)
                System.out.print(tc[i][j] ? "1 " : "0 ");
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        TransitiveClosure graph = new TransitiveClosure(5);
        graph.addEdge(0, 1, false); // directed graph
        graph.addEdge(0, 4, false);
        graph.addEdge(1, 2, false);
        graph.addEdge(1, 3, false);
        graph.addEdge(1, 4, false);
        graph.addEdge(2, 3, false);
        graph.addEdge(3, 4, false);
        
        graph.transitiveClosure();
        /*
         * 1 1 1 1 1 
         * 0 1 1 1 1 
         * 0 0 1 1 1 
         * 0 0 0 1 1 
         * 0 0 0 0 1 
         */
        
        // Test second approach (using Floyd-Warshall Algorithm)
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
        graph.transitiveClosureUsingFW(m);
        /*
         *  1 1 1 0 1 1 1 
            0 1 1 0 1 1 1 
            0 0 1 0 1 1 1 
            0 0 0 1 0 0 0 
            0 0 0 0 1 1 0 
            0 0 0 0 1 1 0 
            0 0 0 0 1 1 1 
         */
    }
}
