package graph.networkflow;

import java.util.ArrayList;
import java.util.List;

/**
 * This class includes common setups for network flow algorithms
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public abstract class NetworkFlowBase {
    
    // To avoid overflow, set infinity to a value less than Long.MAX_VALUE;
    protected static final long INF = Long.MAX_VALUE / 2;
    
    public class FlowEdge {
        public int from, to;
        public FlowEdge backwardEdge;
        public final long capacity;
        public long flow;
        
        public FlowEdge(int from, int to, long capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }
        
        public boolean isBackEdge() {
            return capacity == 0;
        }
        
        public long getResidualCapacity() {
            return capacity - flow;
        }
        
        public void augment(long b) {
            flow += b;
            backwardEdge.flow -= b;
        }
        
        @Override
        public String toString() {
            return String.format(
                "Edge %s -> %s | flow = %d | capacity = %d | is residual: %s",
                getFrom(), getTo(), flow, capacity, isBackEdge());
        }
        
        public String getFrom() {
            return getNodeString(from);
        }
        
        public String getTo() {
            return getNodeString(to);
        }
        
        @Override
        public boolean equals(Object obj) {
            return ((FlowEdge) obj).from == this.from 
                    && ((FlowEdge) obj).to == this.to;
        }
    }
    
    // Inputs: n = number of nodes, s = source, t = sink
    protected final int n, s, t;

    protected long maxFlow;
    protected long minCost;

    protected boolean[] minCut;
    protected List<List<FlowEdge>> graph;

    // 'visited' and 'visitedToken' are variables used for graph sub-routines to
    // track whether a node has been visited or not. In particular, node 'i' was
    // recently visited if visited[i] == visitedToken is true. This is handy
    // because to mark all nodes as unvisited simply increment the visitedToken.
    private int visitedToken = 1;
    private int[] visited;

    // Indicates whether the network flow algorithm has ran. We should not need to
    // run the solver multiple times, because it always yields the same result.
    private boolean solved;
    
    /**
     * @param n - The number of nodes in the graph including source and sink nodes
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n, t != s
     */
    public NetworkFlowBase(int n, int s, int t) {
        this.n = n;
        this.s = s;
        this.t = t;
        
        // Initialize graph
        graph = new ArrayList<>();
        // Create a new array list for each vertex
        for (int i = 0; i < n; ++i)
            graph.add(new ArrayList<FlowEdge>());
        
        minCut = new boolean[n];
        visited = new int[n];
    }
    
    /**
     * Adds a directed edge (and corresponding back edge) to the flow graph
     *
     * @param from - The index of the node the directed edge starts at
     * @param to - The index of the node the directed edge ends at
     * @param capacity - The capacity of the edge
     */
    public void addEdge(int from, int to, long capacity) {
        validateInput(from, to, capacity);
        
        FlowEdge e1 = new FlowEdge(from, to, capacity);
        FlowEdge e2 = new FlowEdge(to, from, 0); // backward edge
        e1.backwardEdge = e2;
        e2.backwardEdge = e1;
        
        graph.get(from).add(e1);
        graph.get(to).add(e2);
    }
    
    public void addVertex() {
        graph.add(new ArrayList<FlowEdge>());
    }

    private void validateInput(int src, int dest, long capacity) {
        if (src < 0 || src >= n)
            throw new IllegalArgumentException("Invalid source vertex");
        if (dest < 0 || dest >= n)
            throw new IllegalArgumentException("Invalid destination vertex");
        if (capacity < 0) 
            throw new IllegalArgumentException("Negative capacity");
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
    
    // Returns graph after max flow algorithm is executed
    public List<List<FlowEdge>> getGraph() {
        execute();
        return graph;
    }
    
    // Prints graph after max flow algorithm is executed
    public void printGraph() {
        List<List<FlowEdge>> g = getGraph();
        for (List<FlowEdge> edges : g) {
            for (FlowEdge e : edges) {
                System.out.println(e);
            }
        }
    }

    // Returns the maximum flow from the source to the sink.
    public long getMaxFlow() {
        execute();
        return maxFlow;
    }

    // Returns the min cost from the source to the sink.
    public long getMinCost() {
        execute();
        return minCost;
    }
    
    // Returns the min-cut of this flow network in which the nodes on the "left side"
    // of the cut with the source are marked as true and those on the "right side"
    // of the cut with the sink are marked as false.
    public boolean[] getMinCut() {
        execute();
        return minCut;
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
      
        for (int i = 0; i < n; ++i) {
            if (minCut[i]) resMinCut[0].add(getNodeString(i));
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

    // Method to implement which solves the network flow problem.
    public abstract void solve();
}
