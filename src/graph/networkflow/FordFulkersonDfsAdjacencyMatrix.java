package graph.networkflow;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implements Ford-Fulkerson algorithm, using DFS to find augmenting paths
 * uses adjacency matrix representation of graph
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class FordFulkersonDfsAdjacencyMatrix {
    
    private static final long INF = Long.MAX_VALUE / 2;
    
    // Inputs: n = number of nodes, s = source, t = sink
    private final int n, s, t;

    private long maxFlow;
    
    private int visitedToken = 1;
    private int[] visited;
    
    private boolean solved;
    
    private int[][] graph;
    
    /**
     * @param n - The number of nodes in the graph including source and sink nodes
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n, t != s
     */
    public FordFulkersonDfsAdjacencyMatrix(int[][] caps, int s, int t) {
        if (caps == null || caps.length == 0) throw new IllegalStateException("empty matrix!");
        if (caps[0].length != caps.length) throw new IllegalStateException("not a square matrix!");
        
        this.n = caps.length;
        this.s = s;
        this.t = t;
        
        this.graph = caps;
        
        visited = new int[n];
    }
    
    public void solve() {
        for (long b = dfs(s, INF); b > 0; b = dfs(s, INF)) {
            markAllNodesUnvisited();
            maxFlow += b;
        }
    }
    
    private long dfs(int node, long min) {
        if (node == t) return min;
        
        int[] edges = graph[node];
        visit(node);
        
        for (int vn = 0; vn < n; ++vn) {
            if (!IsVisited(vn) && edges[vn] > 0) {
                long bottleNeck = dfs(vn, min(min, edges[vn]));
                
                // Augment the flow with bottleneck value, if a s-t augmenting path exists
                if (bottleNeck > 0) {
                    // Here we subtract from forward edge and add to backward edge.
                    // Looks like it is exactly opposite behaviour of what we did 
                    // in adjacency list impl, but it's not. Here we're not 
                    // operating on edge flows, but on edge capacities itself. 
                    // We're reducing capacities for forward edge which is
                    // equivalent to increasing flow through it.
                    graph[node][vn] -= bottleNeck;
                    graph[vn][node] += bottleNeck;
                    return bottleNeck;
                }
            }
        }
        
        return 0;
    }
    
    // Marks node 'i' as visited
    public void visit(int i) {
        visited[i] = visitedToken;
    }

    // Returns whether or not node 'i' has been visited
    public boolean IsVisited(int i) {
        return visited[i] == visitedToken;
    }

    // Resets all nodes as unvisited. This is especially useful to do
    // between iterations of finding augmenting paths, O(1)
    public void markAllNodesUnvisited() {
        visitedToken++;
    }

    // Returns the maximum flow from the source to the sink.
    public long getMaxFlow() {
        execute();
        return maxFlow;
    }
    
    // Returns a mincut formed by two disjoint sets A and B, 
    // where set A contains node s and all the nodes reachable from s,
    // and set B contains node t and all the nodes from which t is reachable
    @SuppressWarnings("unchecked")
    public List<String>[] getMinCutSets() {
        execute();
        List<String>[] resMinCut = new List[2];
        resMinCut[0] = new ArrayList<String>(); // set A
        resMinCut[1] = new ArrayList<String>(); // set B
      
        // During the last dfs call in solve() method, all the nodes 
        // reachable from 's' would have been marked visited.
        // Using that, we separate entire flow graph into 2 sets of nodes:
        // set A which contains s and all nodes reachable from s,
        // and set B which contains t and all nodes from which t is reachable
        for (int i = 0; i < n; ++i) {
            if (IsVisited(i)) resMinCut[0].add(getNodeString(i));
            else resMinCut[1].add(getNodeString(i));
        }
      
        return resMinCut;
    }
    
    // Prints mincut node sets after max flow algorithm is executed
    // set A contains node s and all the nodes reachable from s,
    // set B contains node t and all the nodes from which t is reachable
    public void printMinCut() {
        List<String>[] minCutNodeSets = getMinCutSets();
        System.out.println("A: " + minCutNodeSets[0] + " B: " + minCutNodeSets[1]);
    }
    
    // Returns String representation of a node. 
    // s and t are represented by their respective symbol
    // rest all nodes are represented by their node numbers in the flow network
    public String getNodeString(int node) {
        return (node == s) ? "s" : ((node == t) ? "t" : String.valueOf(node));
    }

    // Wrapper method that ensures we only call solve() once
    private void execute() {
        if (solved) return;
        solved = true;
        solve();
    }
    
    public static void main(String[] args) {
        example1();
        //example2();
        //example3();
    }
    
    private static void example1() {
        int[][] caps = {
                {0, 0, 0, 2, 4, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 5, 6, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 4, 0, 0, 0, 8, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 7, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 8, 3, 0, 3, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {7, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        
        int n = caps.length; // 12
        int s = n - 2; // 10
        int t = n - 1; // 11

        FordFulkersonDfsAdjacencyMatrix solver = new FordFulkersonDfsAdjacencyMatrix(caps, s, t);

        System.out.println(solver.getMaxFlow()); // 7
        
        System.out.println(Arrays.deepToString(caps));
        /* graph with remaining capacities across edges after execution of FF
        [[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0], 
         [0, 0, 0, 0, 5, 6, 0, 0, 0, 0, 0, 0], 
         [0, 0, 0, 4, 0, 0, 0, 7, 0, 0, 1, 0], 
         [2, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0], 
         [4, 0, 0, 0, 0, 5, 3, 0, 2, 0, 0, 0], 
         [0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0], 
         [0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0], 
         [0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1], 
         [0, 0, 0, 0, 1, 3, 0, 0, 0, 0, 0, 0], 
         [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
         [1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
         [0, 0, 0, 0, 0, 0, 1, 2, 4, 0, 0, 0]]
        */
        
        solver.printMinCut(); // A: [0, 1, 3, 4, 5, 6, 8, s] B: [2, 7, 9, t]
    }

}
