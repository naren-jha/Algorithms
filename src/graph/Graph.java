package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Adjacency list representation of Graph

public class Graph {

    protected int numberOfVertices;
    protected List<LinkedList<Edge>> adjList;
    
    protected class Edge {
        int dv; // destination vertex
        int wt; // weight
        
        Edge(int dv, int wt) {
            this.dv = dv;
            this.wt = wt;
        }
        
        @Override
        public boolean equals(Object obj) {
            return ((Edge) obj).dv == this.dv;
        }
        
        @Override
        public String toString() {
            return "{" + dv + ", " + wt + "}";
        }
    }
    
    public Graph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        adjList = new ArrayList<LinkedList<Edge>>();
        
        // create a new linked list for each vertex
        for (int i = 0; i < numberOfVertices; ++i) {
            adjList.add(new LinkedList<Edge>());
        }
    }
    
    // adds an unweighted edge
    public void addEdge(int src, int dest, boolean isUndirected) {
        addEdge(src, dest, isUndirected, 0);
    }
    
    // adds undirected and unweighted edge
    public void addEdge(int src, int dest) {
        addEdge(src, dest, true, 0);
    }
    
    public void addEdge(int src, int dest, boolean isUndirected, int weight) {
        validateSourceAndDestinationVertices(src, dest);
        
        // add to the beginning of the linked list
        adjList.get(src).addFirst(new Edge(dest, weight));
        if (isUndirected)
            adjList.get(dest).addFirst(new Edge(src, weight));
    }
    
    public void removeEdge(int src, int dest) {
        removeEdge(src, dest, true);
    }
    
    public void removeEdge(int src, int dest, boolean isUndirected) {
        validateSourceAndDestinationVertices(src, dest);
        adjList.get(src).remove(new Edge(dest, 0));
        if (isUndirected)
            adjList.get(dest).remove(new Edge(src, 0));
    }
    
    public boolean areNeighbor(int src, int dest) {
        for (Edge edge : adjList.get(src)) {
            if (edge.dv == dest)
                return true;
        }
        return false;
    }
    
    public List<Edge> neighborsOf(int src) {
        return adjList.get(src);
    }
    
    public void addVertex() {
        adjList.add(new LinkedList<Edge>());
    }

    protected void validateSourceAndDestinationVertices(int src, int dest) {
        if (src < 0 || src >= numberOfVertices)
            throw new IllegalArgumentException("Invalid source vertex");
        if (dest < 0 || dest >= numberOfVertices)
            throw new IllegalArgumentException("Invalid destination vertex");
    }
    
    protected void validateVerticex(int v) {
        if (v < 0 || v >= numberOfVertices)
            throw new IllegalArgumentException("Given vertex is invalid");
    }
    
    @Override
    public String toString() {
        StringBuilder graph = new StringBuilder();
        for(int i = 0; i < numberOfVertices; i++) {
            graph.append("V" + i);
            for (Edge edge : adjList.get(i)) {
                graph.append(" -> " + edge.dv);
            }
            graph.append("\n");
        }
        return graph.toString();
    }
    
    public static void main(String[] args) {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        
        System.out.println(graph);
        /* Output:
         
             V0 -> 4 -> 1

             V1 -> 4 -> 3 -> 2 -> 0
            
             V2 -> 3 -> 1
            
             V3 -> 4 -> 2 -> 1
            
             V4 -> 3 -> 1 -> 0
         */
        
        System.out.println(graph.areNeighbor(1, 2)); // true
        System.out.println(graph.areNeighbor(2, 0)); // false
        System.out.println();
        
        System.out.print(graph.neighborsOf(1)); // [{4, 0}, {3, 0}, {2, 0}, {0, 0}]
        System.out.println("\n");
        
        graph.removeEdge(1, 3);
        graph.removeEdge(4, 0);
        System.out.println(graph);
        /* Output after removing edges:
         
             V0 -> 1

             V1 -> 4 -> 2 -> 0
            
             V2 -> 3 -> 1
            
             V3 -> 4 -> 2
            
             V4 -> 3 -> 1
         */
    }
}
