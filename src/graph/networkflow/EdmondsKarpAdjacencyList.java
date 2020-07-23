package graph.networkflow;

import static java.lang.Math.min;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Implementation of Edmonds-Karp algorithm to find Max Flow and Min Cut in a
 * given flow network. Edmonds-Karp is basically an extension of Ford-Fulkerson
 * method. It uses BFS to find augmenting paths instead of DFS. Consequently
 * removing the dependency of time complexity on MaxFlow value and on capacity
 * values of the flow network.
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class EdmondsKarpAdjacencyList extends NetworkFlowBase {

    /**
     * @param n - The number of nodes in the graph including source and sink nodes
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n, t != s
     */
    public EdmondsKarpAdjacencyList(int n, int s, int t) {
        super(n, s, t);
    }

    @Override
    public void solve() {
        for (long b = bfs(); b > 0; b = bfs()) {
            markAllNodesUnvisited();
            maxFlow += b;
        }
        
        // During the last call to bfs, all the nodes reachable from s would have
        // been marked visited. Using that, we separate the entire flow graph into
        // 2 sets of nodes: set A which contains s and all nodes reachable from s,
        // and set B which contains t and all nodes from which t is reachable
        for (int i = 0; i < n; ++i)
            if (isVisited(i)) minCut[i] = true;
    }
    
    private long bfs() {
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(s);
        visit(s);
        
        FlowEdge[] prev = new FlowEdge[n];
        while (!q.isEmpty()) {
            int node = q.poll();
            if (node == t) break;
            
            for (FlowEdge edge : graph.get(node)) {
                long rCap = edge.getResidualCapacity();
                if (rCap > 0 && !isVisited(edge.to)) {
                    q.offer(edge.to);
                    visit(edge.to);
                    prev[edge.to] = edge;
                }
            }
        }
        
        // no augmenting path from source to sink exists
        if (prev[t] == null) return 0;
        
        long bottleNeck = Long.MAX_VALUE;
        for (FlowEdge e = prev[t]; e != null; e = prev[e.from])
            bottleNeck = min(e.getResidualCapacity(), bottleNeck);
        
        for (FlowEdge e = prev[t]; e != null; e = prev[e.from])
            e.augment(bottleNeck);
        
        return bottleNeck;
    }
    
    public static void main(String[] args) {
        example1();
        example2();
        example3();
    }
    
    private static void example1() {
        int n = 12;
        int s = n - 2;
        int t = n - 1;

        EdmondsKarpAdjacencyList solver = new EdmondsKarpAdjacencyList(n, s, t);

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
        //solver.printGraph();
        solver.printMinCut(); // A: [0, 1, 3, 4, 5, 6, 8, s] B: [2, 7, 9, t]
    }
    
    private static void example2() {
        int n = 6;
        int s = n - 2;
        int t = n - 1;

        EdmondsKarpAdjacencyList solver = new EdmondsKarpAdjacencyList(n, s, t);

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
        //solver.printGraph();
        solver.printMinCut(); // A: [s] B: [0, 1, 2, 3, t]
    }
    
    // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
    private static void example3() {
        int n = 6;
        int s = n - 1;
        int t = n - 2;

        EdmondsKarpAdjacencyList solver = new EdmondsKarpAdjacencyList(n, s, t);

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
        //solver.printGraph();
        solver.printMinCut(); // A: [1, s] B: [0, 2, 3, t]
    }

}
