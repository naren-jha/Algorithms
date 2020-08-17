package graph.networkflow;

import java.util.List;

import graph.utils.GraphUtils;

/**
 * Implementation of a DFS based algorithm to check 
 * if a given input graph is bipartite or not.
 * 
 * Time complexity: O(V + E)
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class BipartiteGraphCheckAdjacencyList {

    private int n;
    private int[] colors;
    private boolean solved;
    private boolean isBipartite;
    private List<List<Integer>> graph;
    
    // RED = 1, BLACK = 2
    public static final int RED = 0b01, BLACK = 0b10; 
    
    public BipartiteGraphCheckAdjacencyList(List<List<Integer>> graph) {
        if (graph == null) throw new IllegalArgumentException("Empty graph");
        this.n = graph.size();
        this.graph = graph;
    }
    
    /** Determines if a given graph is bipartite or not **/
    public boolean isBipartite() {
        if (!solved) solve();
        return isBipartite;
    }
    
    private void solve() {
        if (n <= 1) return;
        colors = new int[n];
        int nodesVisited = colorGraph(0, RED);
        
        // If all the nodes were not visited or colorGraph() method
        // returned -1, then the graph is not bipartite (not 2-colorable)
        isBipartite = (nodesVisited == n);
        solved = true;
    }
    
    private int colorGraph(int i, int color) {
        colors[i] = color;
        
        // Flip 2 lower order bits to toggle color between RED and BLACK
        int nextColor = color ^ 0b11;
        
        int visitCount = 1;
        for (int to : graph.get(i)) {
            // Contradiction: check if two connected nodes are of same color 
            if (colors[to] == color) return -1;
            
            // Check if the destination node is already colored with the right color
            if (colors[to] == nextColor) continue;
            
            // If a contradiction is found during DFS, propagate return -1
            int count = colorGraph(to, nextColor);
            if (count == -1) return -1;
            
            visitCount += count;
        }
        
        return visitCount;
    }
    
    // If the input graph is bipartite, it has a two coloring which can be obtained
    // using this method. Each index in the returned array is either RED or BLACK
    public int[] getTwoColoring() {
      return isBipartite() ? colors : null;
    }
    
    public static void main(String[] args) {
        // Singleton (not bipartite)
        example1();
        
        // Two nodes one edge between them (bipartite)
        example2();
        
        // Triangle graph (not bipartite)
        example3();
        
        // Disjoint graph is bipartite connected components (altogether not bipartite)
        example4();
        
        // Square graph (bipartite)
        example5();
        
        // Square graph with additional edge (not bipartite)
        example6();
    }
    
    private static void example1() {
        int n = 1;
        List<List<Integer>> graph = GraphUtils.createEmptyAdjacencyList(n);
        GraphUtils.addUndirectedEdge(graph, 0, 0);
        displayGraph(graph);
    
        // Prints:
        // Graph has 1 node(s) and the following edges:
        // 0 -> 0
        // 0 -> 0
        // This graph is bipartite: false
    }
    
    private static void example2() {
        int n = 2;
        List<List<Integer>> graph = GraphUtils.createEmptyAdjacencyList(n);
        GraphUtils.addUndirectedEdge(graph, 0, 1);
        displayGraph(graph);
        
        // Prints:
        // Graph has 2 node(s) and the following edges:
        // 0 -> 1
        // 1 -> 0
        // This graph is bipartite: true
    }
    
    private static void example3() {
        int n = 3;
        List<List<Integer>> graph = GraphUtils.createEmptyAdjacencyList(n);
        GraphUtils.addUndirectedEdge(graph, 0, 1);
        GraphUtils.addUndirectedEdge(graph, 1, 2);
        GraphUtils.addUndirectedEdge(graph, 2, 0);
        displayGraph(graph);
        
        // Prints:
        // Graph has 3 node(s) and the following edges:
        // 0 -> 1
        // 0 -> 2
        // 1 -> 0
        // 1 -> 2
        // 2 -> 1
        // 2 -> 0
        // This graph is bipartite: false
    }
    
    private static void example4() {
        int n = 4;
        List<List<Integer>> graph = GraphUtils.createEmptyAdjacencyList(n);
        GraphUtils.addUndirectedEdge(graph, 0, 1);
        GraphUtils.addUndirectedEdge(graph, 2, 3);
        displayGraph(graph);
        
        // Prints:
        // Graph has 4 node(s) and the following edges:
        // 0 -> 1
        // 1 -> 0
        // 2 -> 3
        // 3 -> 2
        // This graph is bipartite: false
    }
    
    private static void example5() {
        int n = 4;
        List<List<Integer>> graph = GraphUtils.createEmptyAdjacencyList(n);
        GraphUtils.addUndirectedEdge(graph, 0, 1);
        GraphUtils.addUndirectedEdge(graph, 1, 2);
        GraphUtils.addUndirectedEdge(graph, 2, 3);
        GraphUtils.addUndirectedEdge(graph, 3, 0);
        displayGraph(graph);
        
        // Prints:
        // Graph has 4 node(s) and the following edges:
        // 0 -> 1
        // 0 -> 3
        // 1 -> 0
        // 1 -> 2
        // 2 -> 1
        // 2 -> 3
        // 3 -> 2
        // 3 -> 0
        // This graph is bipartite: true
    }

    private static void example6() {
        int n = 4;
        List<List<Integer>> graph = GraphUtils.createEmptyAdjacencyList(n);
        GraphUtils.addUndirectedEdge(graph, 0, 1);
        GraphUtils.addUndirectedEdge(graph, 1, 2);
        GraphUtils.addUndirectedEdge(graph, 2, 3);
        GraphUtils.addUndirectedEdge(graph, 3, 0);
        GraphUtils.addUndirectedEdge(graph, 0, 2);
        displayGraph(graph);
        
        // Prints:
        // Graph has 4 node(s) and the following edges:
        // 0 -> 1
        // 0 -> 3
        // 0 -> 2
        // 1 -> 0
        // 1 -> 2
        // 2 -> 1
        // 2 -> 3
        // 2 -> 0
        // 3 -> 2
        // 3 -> 0
        // This graph is bipartite: false
    }
    
    private static void displayGraph(List<List<Integer>> graph) {
        final int n = graph.size();
    
        System.out.println("Graph has " + n + " node(s) and the following edges:");
        for (int f = 0; f < n; f++) 
            for (int t : graph.get(f)) 
                System.out.println(f + " -> " + t);
    
        BipartiteGraphCheckAdjacencyList solver;
        solver = new BipartiteGraphCheckAdjacencyList(graph);
    
        System.out.println("This graph is bipartite: " + (solver.isBipartite()));
        System.out.println();
    }
}
