package graph.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A set of utility methods frequently used in graph theory
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public final class GraphUtils {
    
    /**
     * Creates an empty graph represented as an adjacency list of size n
     *
     * @param n The number of nodes in the graph
     */
    public static List<List<Integer>> createEmptyAdjacencyList(int n) {
        if (n < 0) throw new IllegalArgumentException("Invalid number of vertices, n = " + n);
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; ++i) graph.add(new ArrayList<>());
        return graph;
    }
    
    /** Adds an unweighted directed edge from the node at index 'from' to the node at index 'to'. */
    public static void addDirectedEdge(List<List<Integer>> graph, int from, int to) {
        validateArguments(graph, from, to);
        graph.get(from).add(to);
    }
    
    /** Adds an unweighted undirected edge from the node at index 'from' to the node at index 'to'. */
    public static void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
        validateArguments(graph, from, to);
        graph.get(from).add(to);
        graph.get(to).add(from);
    }
    
    private static void validateArguments(List<List<Integer>> graph, int src, int dest) {
        if (graph == null) throw new IllegalArgumentException("Empty graph");
        
        int n = graph.size();
        if (src < 0 || src >= n)
            throw new IllegalArgumentException("Invalid source vertex");
        if (dest < 0 || dest >= n)
            throw new IllegalArgumentException("Invalid destination vertex");
    }
    
    /** Adds a new vertex in the given adjacency list representation of graph. */
    public void addVertex(List<List<Integer>> graph) {
        if (graph == null) throw new IllegalArgumentException("Empty graph");
        graph.add(new ArrayList<>());
    }

}
