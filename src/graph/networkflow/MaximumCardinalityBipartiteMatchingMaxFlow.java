package graph.networkflow;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds maximum cardinality matching in a bipartite graph using FF max-flow
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class MaximumCardinalityBipartiteMatchingMaxFlow {
    
    private final int n; // # of nodes in set 1
    private final int m; // # of nodes in set 2
    private final int N; // total # of nodes in bipartite graph
    
    private final int s, t;
    private List<List<Integer>> graph;
    
    public MaximumCardinalityBipartiteMatchingMaxFlow(int n, int m) {
        this.n = n;
        this.m = m;
        N = n + m;
        
        // 0 to N-1 are the indices of nodes in bipartite graph
        // So let's make index N as 'source' and index (N + 1) as 'sink'
        s = N; 
        t = N + 1;
        
        initializeGraph();
    }

    private void initializeGraph() {
        graph = new ArrayList<>();
        // Create a new array list for each vertex
        for (int i = 0; i < N; ++i)
            graph.add(new ArrayList<Integer>());
    }
    
    public long mcbm() {
        FordFulkersonDfsAdjacencyList ff = new FordFulkersonDfsAdjacencyList(N + 2, s, t);
        
        // Hook up edges from the 'source' to nodes in set1
        // And from set1 to set2 nodes
        for (int i = 0; i < n; ++i) {
            ff.addEdge(s, i, 1);
            
            for (int j : graph.get(i))
                ff.addEdge(i, j, 1);
        }
        
        // Hook up edges from nodes in set2 to the 'sink'
        // And from set2 to set1 nodes
        for (int j = n; j < N; ++j) {
            ff.addEdge(j, t, 1);
            
            for (int i : graph.get(j))
                ff.addEdge(j, i, 1);
        }
        
        return ff.getMaxFlow();
    }
    
    public void addEdge(int from, int to) {
        validateVertices(from, to);
        graph.get(from).add(to);
    }
    
    protected void validateVertices(int src, int dest) {
        if (src < 0 || src >= N)
            throw new IllegalArgumentException("Invalid source vertex");
        if (dest < 0 || dest >= N)
            throw new IllegalArgumentException("Invalid destination vertex");
    }
    
    public static void main(String[] args) {
        example1();
        example2();
    }
    
    public static void example1() {
        // Set 1 = {0, 1, 2, 3} 
        // Set 2 = {4, 5, 6, 7}
        MaximumCardinalityBipartiteMatchingMaxFlow solver = new MaximumCardinalityBipartiteMatchingMaxFlow(4, 4);
        solver.addEdge(0, 4);
        solver.addEdge(1, 5);
        solver.addEdge(2, 7);
        solver.addEdge(3, 6);
        solver.addEdge(1, 4);
        solver.addEdge(1, 6);
        solver.addEdge(5, 2);
        solver.addEdge(5, 3);
        System.out.println(solver.mcbm()); // 4
    }
    
    public static void example2() {
        // Set 1 = {0, 1, 2, 3, 4} 
        // Set 2 = {5, 6, 7, 8, 9}
        MaximumCardinalityBipartiteMatchingMaxFlow solver = new MaximumCardinalityBipartiteMatchingMaxFlow(5, 5);
        solver.addEdge(0, 6);
        solver.addEdge(0, 7);
        
        solver.addEdge(1, 6);
        solver.addEdge(1, 7);
        solver.addEdge(1, 8);
        
        solver.addEdge(2, 5);
        solver.addEdge(2, 6);
        solver.addEdge(2, 7);
        solver.addEdge(2, 9);
        
        solver.addEdge(3, 7);
        
        solver.addEdge(4, 7);
        solver.addEdge(4, 8);
        solver.addEdge(4, 9);
        System.out.println(solver.mcbm()); // 5
    }

}
