package graph.networkflow;

import static java.lang.Math.min;

import java.util.List;

/**
 * Implements Ford-Fulkerson algorithm, using DFS to find augmenting paths
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class FordFulkersonDfsAdjacencyList extends NetworkFlowBase {

    /**
     * @param n - The number of nodes in the graph including source and sink nodes
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n, t != s
     */
    public FordFulkersonDfsAdjacencyList(int n, int s, int t) {
        super(n, s, t);
    }

    @Override
    public void solve() {
        for (long b = dfs(s, INF); b > 0; b = dfs(s, INF)) {
            markAllNodesUnvisited();
            maxFlow += b;
        }
        
        // During last dfs call, all the nodes reachable from 's' 
        // would have been marked visited.
        // Using that, we separate entire flow graph into 2 sets of nodes:
        // set A which contains s and all nodes reachable from s,
        // and set B which contains t and all nodes from which t is reachable
        for (int i = 0; i < n; ++i)
            if (IsVisited(i)) minCut[i] = true;
    }
    
    private long dfs(int node, long min) {
        if (node == t) return min;
        
        List<FlowEdge> edges = graph.get(node);
        visit(node);
        
        for (FlowEdge edge : edges) {
            long remCap = edge.getResidualCapacity();
            if (!IsVisited(edge.to) && remCap > 0) {
                long bottleNeck = dfs(edge.to, min(min, remCap));
                
                // Augment the flow with bottleneck value, if a s-t augmenting path exists
                if (bottleNeck > 0) {
                    edge.augment(bottleNeck);
                    return bottleNeck;
                }
            }
        }
        
        return 0;
    }
    
    public static void main(String[] args) {
        //example1();
        //example2();
        example3();
    }
    
    private static void example1() {
        int n = 12;
        int s = n - 2;
        int t = n - 1;

        FordFulkersonDfsAdjacencyList solver = new FordFulkersonDfsAdjacencyList(n, s, t);

        // Edges from source
        solver.addEdge(s, 0, 7);
        solver.addEdge(s, 1, 2);
        solver.addEdge(s, 2, 1);

        // Middle edges
        solver.addEdge(0, 3, 2);
        solver.addEdge(0, 4, 4);

        solver.addEdge(1, 4, 5);
        solver.addEdge(1, 5, 6);

        solver.addEdge(2, 3, 4);
        solver.addEdge(2, 7, 8);

        solver.addEdge(3, 6, 7);
        solver.addEdge(3, 7, 1);

        solver.addEdge(4, 5, 8);
        solver.addEdge(4, 6, 3);
        solver.addEdge(4, 8, 3);

        solver.addEdge(5, 8, 3);

        // Edges to sink
        solver.addEdge(6, t, 1);
        solver.addEdge(7, t, 3);
        solver.addEdge(8, t, 4);

        System.out.println(solver.getMaxFlow()); // 7
        
        solver.printGraph();
        /*
        Edge 0 -> s | flow = -6 | capacity = 0 | is residual: true
        Edge 0 -> 3 | flow = 2 | capacity = 2 | is residual: false
        Edge 0 -> 4 | flow = 4 | capacity = 4 | is residual: false
        Edge 1 -> s | flow = 0 | capacity = 0 | is residual: true
        Edge 1 -> 4 | flow = 0 | capacity = 5 | is residual: false
        Edge 1 -> 5 | flow = 0 | capacity = 6 | is residual: false
        Edge 2 -> s | flow = -1 | capacity = 0 | is residual: true
        Edge 2 -> 3 | flow = 0 | capacity = 4 | is residual: false
        Edge 2 -> 7 | flow = 1 | capacity = 8 | is residual: false
        Edge 3 -> 0 | flow = -2 | capacity = 0 | is residual: true
        Edge 3 -> 2 | flow = 0 | capacity = 0 | is residual: true
        Edge 3 -> 6 | flow = 1 | capacity = 7 | is residual: false
        Edge 3 -> 7 | flow = 1 | capacity = 1 | is residual: false
        Edge 4 -> 0 | flow = -4 | capacity = 0 | is residual: true
        Edge 4 -> 1 | flow = 0 | capacity = 0 | is residual: true
        Edge 4 -> 5 | flow = 3 | capacity = 8 | is residual: false
        Edge 4 -> 6 | flow = 0 | capacity = 3 | is residual: false
        Edge 4 -> 8 | flow = 1 | capacity = 3 | is residual: false
        Edge 5 -> 1 | flow = 0 | capacity = 0 | is residual: true
        Edge 5 -> 4 | flow = -3 | capacity = 0 | is residual: true
        Edge 5 -> 8 | flow = 3 | capacity = 3 | is residual: false
        Edge 6 -> 3 | flow = -1 | capacity = 0 | is residual: true
        Edge 6 -> 4 | flow = 0 | capacity = 0 | is residual: true
        Edge 6 -> t | flow = 1 | capacity = 1 | is residual: false
        Edge 7 -> 2 | flow = -1 | capacity = 0 | is residual: true
        Edge 7 -> 3 | flow = -1 | capacity = 0 | is residual: true
        Edge 7 -> t | flow = 2 | capacity = 3 | is residual: false
        Edge 8 -> 4 | flow = -1 | capacity = 0 | is residual: true
        Edge 8 -> 5 | flow = -3 | capacity = 0 | is residual: true
        Edge 8 -> t | flow = 4 | capacity = 4 | is residual: false
        Edge s -> 0 | flow = 6 | capacity = 7 | is residual: false
        Edge s -> 1 | flow = 0 | capacity = 2 | is residual: false
        Edge s -> 2 | flow = 1 | capacity = 1 | is residual: false
        Edge t -> 6 | flow = -1 | capacity = 0 | is residual: true
        Edge t -> 7 | flow = -2 | capacity = 0 | is residual: true
        Edge t -> 8 | flow = -4 | capacity = 0 | is residual: true
        */
        
        solver.printMinCut(); // A: [0, 1, 3, 4, 5, 6, 8, s] B: [2, 7, 9, t]
    }
    
    private static void example2() {
        int n = 6;
        int s = n - 2;
        int t = n - 1;

        FordFulkersonDfsAdjacencyList solver = new FordFulkersonDfsAdjacencyList(n, s, t);

        // Edges from source
        solver.addEdge(s, 0, 10);
        solver.addEdge(s, 1, 10);
        
        // Middle edges
        solver.addEdge(0, 2, 25);
        solver.addEdge(1, 3, 15);
        solver.addEdge(3, 0, 6);
        
        // Edges to sink
        solver.addEdge(2, t, 10);
        solver.addEdge(3, t, 10);

        System.out.println(solver.getMaxFlow()); // 20
        
        solver.printGraph();
        /*
        Edge 0 -> s | flow = -10 | capacity = 0 | is residual: true
        Edge 0 -> 2 | flow = 10 | capacity = 25 | is residual: false
        Edge 0 -> 3 | flow = 0 | capacity = 0 | is residual: true
        Edge 1 -> s | flow = -10 | capacity = 0 | is residual: true
        Edge 1 -> 3 | flow = 10 | capacity = 15 | is residual: false
        Edge 2 -> 0 | flow = -10 | capacity = 0 | is residual: true
        Edge 2 -> t | flow = 10 | capacity = 10 | is residual: false
        Edge 3 -> 1 | flow = -10 | capacity = 0 | is residual: true
        Edge 3 -> 0 | flow = 0 | capacity = 6 | is residual: false
        Edge 3 -> t | flow = 10 | capacity = 10 | is residual: false
        Edge s -> 0 | flow = 10 | capacity = 10 | is residual: false
        Edge s -> 1 | flow = 10 | capacity = 10 | is residual: false
        Edge t -> 2 | flow = -10 | capacity = 0 | is residual: true
        Edge t -> 3 | flow = -10 | capacity = 0 | is residual: true
        */
        
        solver.printMinCut(); // A: [s] B: [0, 1, 2, 3, t]
    }
    
    // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
    private static void example3() {
        int n = 6;
        int s = n - 1;
        int t = n - 2;

        FordFulkersonDfsAdjacencyList solver = new FordFulkersonDfsAdjacencyList(n, s, t);

        // Edges from source
        solver.addEdge(s, 0, 10);
        solver.addEdge(s, 1, 10);

        // Middle edges
        solver.addEdge(0, 1, 2);
        solver.addEdge(0, 2, 4);
        solver.addEdge(0, 3, 8);
        solver.addEdge(1, 3, 9);
        solver.addEdge(3, 2, 6);
        
        // Edges to sink
        solver.addEdge(2, t, 10);
        solver.addEdge(3, t, 10);

        System.out.println(solver.getMaxFlow()); // 19
        
        solver.printGraph();
        /*
        Edge 0 -> s | flow = -10 | capacity = 0 | is residual: true
        Edge 0 -> 1 | flow = 0 | capacity = 2 | is residual: false
        Edge 0 -> 2 | flow = 4 | capacity = 4 | is residual: false
        Edge 0 -> 3 | flow = 6 | capacity = 8 | is residual: false
        Edge 1 -> s | flow = -9 | capacity = 0 | is residual: true
        Edge 1 -> 0 | flow = 0 | capacity = 0 | is residual: true
        Edge 1 -> 3 | flow = 9 | capacity = 9 | is residual: false
        Edge 2 -> 0 | flow = -4 | capacity = 0 | is residual: true
        Edge 2 -> 3 | flow = -6 | capacity = 0 | is residual: true
        Edge 2 -> t | flow = 10 | capacity = 10 | is residual: false
        Edge 3 -> 0 | flow = -6 | capacity = 0 | is residual: true
        Edge 3 -> 1 | flow = -9 | capacity = 0 | is residual: true
        Edge 3 -> 2 | flow = 6 | capacity = 6 | is residual: false
        Edge 3 -> t | flow = 9 | capacity = 10 | is residual: false
        Edge t -> 2 | flow = -10 | capacity = 0 | is residual: true
        Edge t -> 3 | flow = -9 | capacity = 0 | is residual: true
        Edge s -> 0 | flow = 10 | capacity = 10 | is residual: false
        Edge s -> 1 | flow = 9 | capacity = 10 | is residual: false
        */
        
        solver.printMinCut(); // A: [1, s] B: [0, 2, 3, t]
    }

}
