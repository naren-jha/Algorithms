package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import graph.Graph.Edge;
 
/**
 * Adjacency list representation of Graph
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class Graph {

    protected int numberOfVertices;
    protected List<LinkedList<Edge>> adjList;
    
    protected class Edge implements Comparable<Edge> {
        int from; // source vertex
        int to; // destination vertex
        double wt; // weight
        
        Edge(int from, int to, double wt) {
            this.from = from;
            this.to = to;
            this.wt = wt;
        }
        
        @Override
        public boolean equals(Object obj) {
            return ((Edge) obj).to == this.to;
        }
        
        @Override
        public String toString() {
            return String.format("{from=%d, to=%d, weight=%.2f}", from, to, wt);
        }

        @Override
        public int compareTo(Edge o) {
            if (this.wt > o.wt) return 1;
            else if (this.wt < o.wt) return -1;
            return 0;
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
    
    public void addEdge(int src, int dest, boolean isUndirected, double weight) {
        validateSourceAndDestinationVertices(src, dest);
        
        // add to the beginning of the linked list
        adjList.get(src).addFirst(new Edge(src, dest, weight));
        if (isUndirected)
            adjList.get(dest).addFirst(new Edge(dest, src, weight));
    }
    
    public void removeEdge(int src, int dest) {
        removeEdge(src, dest, true);
    }
    
    public void removeEdge(int src, int dest, boolean isUndirected) {
        validateSourceAndDestinationVertices(src, dest);
        adjList.get(src).remove(new Edge(src, dest, 0));
        if (isUndirected)
            adjList.get(dest).remove(new Edge(dest, src, 0));
    }
    
    public boolean areNeighbor(int src, int dest) {
        for (Edge edge : adjList.get(src)) {
            if (edge.to == dest)
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
    
    protected void validateVertex(int v) {
        if (v < 0 || v >= numberOfVertices)
            throw new IllegalArgumentException("Given vertex is invalid");
    }
    
    @Override
    public String toString() {
        StringBuilder graph = new StringBuilder();
        for(int i = 0; i < numberOfVertices; i++) {
            graph.append("V" + i);
            for (Edge edge : adjList.get(i)) {
                graph.append(" -> " + edge.to);
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
        
        System.out.print(graph.neighborsOf(1)); 
        /*
         * [{from=1, to=4, weight=0.00}, {from=1, to=3, weight=0.00}, 
         * {from=1, to=2, weight=0.00}, {from=1, to=0, weight=0.00}]
         */
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
