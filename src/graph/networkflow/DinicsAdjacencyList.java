package graph.networkflow;

import static java.lang.Math.min;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Implementation of Dinic's algorithm to find Max Flow and Min Cut
 * in a given flow network.
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class DinicsAdjacencyList extends NetworkFlowBase {
    
    private int[] level;

    /**
     * @param n - The number of nodes in the graph including source and sink nodes
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n, t != s
     */
    public DinicsAdjacencyList(int n, int s, int t) {
        super(n, s, t);
        level = new int[n];
    }

    @Override
    public void solve() {
        // next[i] indicates the next unused edge index in the adjacency 
        // list for node i. This is part of the Shimon Even and Alon Itai 
        // optimization of pruning dead ends as part of the DFS phase
        int[] next = new int[n];
        
        while (bfs()) {
            // after every blocking flow iteration, the next unused edge for
            // every node should reset to 0, to allow previously forbidden edges
            Arrays.fill(next, 0);
            
            // Find augmenting paths in current level graph
            for (long b = dfs(s, next, INF); b != 0; b = dfs(s, next, INF)) {
                maxFlow += b;
            }
        }
        
        // Separate nodes to get min cut
        for (int i = 0; i < n; ++i) if (level[i] != -1) minCut[i] = true;
    }
    
    // Do a BFS from source to sink and compute the depth/level of each node
    // which is the minimum number of edges from that node to the source
    private boolean bfs() {
        Arrays.fill(level, -1);
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(s);
        level[s] = 0;
        
        while (!q.isEmpty()) {
            int node = q.poll();
            for (FlowEdge edge : graph.get(node)) {
                long cap = edge.getResidualCapacity();
                if (cap > 0 && level[edge.to] == -1) {
                    level[edge.to] = level[node] + 1;
                    q.offer(edge.to);
                }
            }
        }
        
        // If we're not able to reach the sink, that means the graph is
        // fully saturated and therefore we can terminate the algorithm
        return level[t] != -1;
    }
    
    private long dfs(int at, int[] next, long min) {
        if (at == t) return min;
        
        List<FlowEdge> edges = graph.get(at);
        int numEdges = edges.size();
        
        while (next[at] < numEdges) {
            FlowEdge edge = edges.get(next[at]);
            long cap = edge.getResidualCapacity();
            if (cap > 0 && level[edge.to] == level[at] + 1) {
                long bottleNeck = dfs(edge.to, next, min(min, cap));
                
                // Augment the flow with bottleneck value, if a s-t augmenting path exists
                if (bottleNeck > 0) {
                    edge.augment(bottleNeck);
                    return bottleNeck;
                }
            }
            
            // Shimon Even and Alon Itai optimization
            next[at]++;
        }
        
        return 0;
    }
    
    public static void main(String[] args) {
        example1();
        example2();
        example3();
        example4();
    }
    
    private static void example1() {
        int n = 12;
        int s = n - 2;
        int t = n - 1;

        DinicsAdjacencyList solver = new DinicsAdjacencyList(n, s, t);

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
    
    private static void example2() {
        int n = 6;
        int s = n - 2;
        int t = n - 1;

        DinicsAdjacencyList solver = new DinicsAdjacencyList(n, s, t);

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
        solver.printMinCut(); // A: [s] B: [0, 1, 2, 3, t]
    }
    
    // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
    private static void example3() {
        int n = 6;
        int s = n - 1;
        int t = n - 2;

        DinicsAdjacencyList solver = new DinicsAdjacencyList(n, s, t);

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
    
    private static void example4() {
        int n = 11;
        int s = n - 1;
        int t = n - 2;

        DinicsAdjacencyList solver = new DinicsAdjacencyList(n, s, t);

        // Edges from source
        solver.addEdge(s, 0, 5);
        solver.addEdge(s, 1, 10);
        solver.addEdge(s, 2, 15);

        // Middle edges
        solver.addEdge(0, 3, 10);
        solver.addEdge(1, 0, 15);
        solver.addEdge(1, 4, 20);
        solver.addEdge(2, 5, 25);
        solver.addEdge(3, 4, 25);
        solver.addEdge(3, 6, 10);
        solver.addEdge(3, 7, 20);
        solver.addEdge(4, 2, 5);
        solver.addEdge(4, 7, 30);
        solver.addEdge(5, 7, 20);
        solver.addEdge(5, 8, 10);
        solver.addEdge(7, 8, 15);
        
        // Edges to sink
        solver.addEdge(6, t, 5);
        solver.addEdge(7, t, 15);
        solver.addEdge(8, t, 10);

        System.out.println(solver.getMaxFlow()); // 30
        solver.printMinCut(); // A: [s] B: [0, 1, 2, 3, 4, 5, 6, 7, 8, t]
    }

}
