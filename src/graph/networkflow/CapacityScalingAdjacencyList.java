package graph.networkflow;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.List;

/**
 * Implementation of Capacity Scaling algorithm to find Max Flow and Min Cut
 * in a given flow network. Uses DFS to find augmenting paths.
 * 
 * Time Complexity: O(E^2log(U)), where E = number of edges, U = max capacity
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class CapacityScalingAdjacencyList extends NetworkFlowBase {
    
    private long delta;

    /**
     * @param n - The number of nodes in the graph including source and sink nodes
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n, t != s
     */
    public CapacityScalingAdjacencyList(int n, int s, int t) {
        super(n, s, t);
    }
    
    /**
     * Adds a directed edge (and corresponding residual edge) to the flow graph
     * Also captures the largest capacity value as we build the graph
     *
     * @param from - The index of the node the directed edge starts at
     * @param to - The index of the node the directed edge ends at
     * @param capacity - The capacity of the edge
     */
    @Override
    public void addEdge(int from, int to, long capacity) {
        super.addEdge(from, to, capacity);
        delta = max(delta, capacity);
    }

    @Override
    public void solve() {
        // Start delta at the largest power of 2 <= the largest capacity
        // Equivalent to: delta = (long) pow(2, (int)floor(log(delta)/log(2)))
        delta = Long.highestOneBit(delta);
        
        // Repeatedly find augmenting paths from source to sink using only the edges
        // with a remaining capacity >= delta. Half delta every time we are unable
        // to find an augmenting path from source to sink, until the graph is saturated
        for (long b = 0; delta > 0; delta /= 2) {
            do {
                markAllNodesUnvisited();
                b = dfs(s, INF);
                maxFlow += b;
            } while (b > 0);
        }
        
        // Separate nodes to find min cut
        for (int i = 0; i < n; ++i) if (isVisited(i)) minCut[i] = true;
    }
    
    private long dfs(int node, long min) {
        if (node == t) return min;
        
        List<FlowEdge> edges = graph.get(node);
        visit(node);
        
        for (FlowEdge edge : edges) {
            long remCap = edge.getResidualCapacity();
            if (!isVisited(edge.to) && remCap >= delta) {
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
        example1();
        example2();
        example3();
    }
    
    private static void example1() {
        int n = 6;
        int s = n - 1;
        int t = n - 2;

        CapacityScalingAdjacencyList solver = new CapacityScalingAdjacencyList(n, s, t);

        // Edges from source
        solver.addEdge(s, 0, 6);
        solver.addEdge(s, 1, 14);
        
        // Middle edges
        solver.addEdge(0, 1, 1);
        solver.addEdge(2, 3, 1);
        solver.addEdge(0, 2, 5);
        solver.addEdge(1, 2, 7);
        solver.addEdge(1, 3, 10);
        
        // Edges to sink
        solver.addEdge(2, t, 11);
        solver.addEdge(3, t, 12);

        System.out.println(solver.getMaxFlow()); // 20
        solver.printMinCut(); // A: [s] B: [0, 1, 2, 3, t]
    }
    
    // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
    private static void example2() {
        int n = 6;
        int s = n - 1;
        int t = n - 2;

        CapacityScalingAdjacencyList solver = new CapacityScalingAdjacencyList(n, s, t);

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
        solver.printMinCut(); // A: [1, s] B: [0, 2, 3, t]
    }
    
    private static void example3() {
        int n = 12;
        int s = n - 2;
        int t = n - 1;

        CapacityScalingAdjacencyList solver = new CapacityScalingAdjacencyList(n, s, t);

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
        solver.printMinCut(); // A: [0, 1, 3, 4, 5, 6, 8, s] B: [2, 7, 9, t]
    }

}
