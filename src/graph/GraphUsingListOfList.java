package graph;

import java.util.ArrayList;
import java.util.List;
 
/**
 * Adjacency list representation of Graph using List<List<Edge>>
 * 
 * Earlier implementation (Graph) uses List<LinkedList<Edge>>, in
 * which it takes linear time to get an edge by index for a given
 * source vertex number. for example:
 * If we want to get edge number 'i' for source vertex number 's'
 * adjList.get(s).get(i) takes linear time because of linked list.
 * 
 * This implementation will take O(1) time to do the same.
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class GraphUsingListOfList {

    protected int numberOfVertices;
    protected List<List<Edge>> adjList;
    
    protected class Edge {
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
    }
    
    public GraphUsingListOfList(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        adjList = new ArrayList<>();
        
        // create a new array list for each vertex
        for (int i = 0; i < numberOfVertices; ++i) {
            adjList.add(new ArrayList<Edge>());
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
        
        // add to the end of the array list
        adjList.get(src).add(new Edge(src, dest, weight));
        if (isUndirected)
            adjList.get(dest).add(new Edge(dest, src, weight));
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
        adjList.add(new ArrayList<Edge>());
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
        GraphUsingListOfList graph = new GraphUsingListOfList(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        
        System.out.println(graph);
        /* Output:
         
             V0 -> 1 -> 4

             V1 -> 0 -> 2 -> 3 -> 4

             V2 -> 1 -> 3

             V3 -> 1 -> 2 -> 4

             V4 -> 0 -> 1 -> 3
         */
        
        System.out.println(graph.areNeighbor(1, 2)); // true
        System.out.println(graph.areNeighbor(2, 0)); // false
        System.out.println();
        
        System.out.print(graph.neighborsOf(1)); 
        /*
         * [{from=1, to=0, weight=0.00}, {from=1, to=2, weight=0.00}, 
         * {from=1, to=3, weight=0.00}, {from=1, to=4, weight=0.00}]
         */
        System.out.println("\n");
        
        graph.removeEdge(1, 3);
        graph.removeEdge(4, 0);
        System.out.println(graph);
        /* Output after removing edges:
         
             V0 -> 1

             V1 -> 0 -> 2 -> 4

             V2 -> 1 -> 3

             V3 -> 2 -> 4

             V4 -> 1 -> 3
         */
    }
}
