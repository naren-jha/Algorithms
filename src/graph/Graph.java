package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Adjacency list representation of Graph

public class Graph {

    protected int numberOfVertices;
    protected List<LinkedList<Integer>> adjList;
    
    public Graph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        adjList = new ArrayList<LinkedList<Integer>>();
        
        // create a new linked list for each vertex
        for (int i = 0; i < numberOfVertices; ++i) {
            adjList.add(new LinkedList<Integer>());
        }
    }
    
    public void addEdge(int src, int dest) {
        addEdge(src, dest, true);
    }
    
    public void addEdge(int src, int dest, boolean isUndirected) {
        validateSourceAndDestinationVertices(src, dest);
        
        // add to the beginning of the linked list
        adjList.get(src).addFirst(dest);
        if (isUndirected)
            adjList.get(dest).addFirst(src);
    }
    
    public void removeEdge(int src, int dest) {
        removeEdge(src, dest, true);
    }
    
    public void removeEdge(int src, int dest, boolean isUndirected) {
        validateSourceAndDestinationVertices(src, dest);
        adjList.get(src).remove(Integer.valueOf(dest));
        if (isUndirected)
            adjList.get(dest).remove(Integer.valueOf(src));
    }
    
    public boolean areNeighbor(int src, int dest) {
        for (Integer adjNode : adjList.get(src)) {
            if (adjNode == dest)
                return true;
        }
        return false;
    }
    
    public List<Integer> neighborsOf(int src) {
        return adjList.get(src);
    }

    private void validateSourceAndDestinationVertices(int src, int dest) {
        if (src < 0 || src >= numberOfVertices)
            throw new IllegalArgumentException("invalid source vertex");
        if (dest < 0 || dest >= numberOfVertices)
            throw new IllegalArgumentException("invalid destination vertex");
    }
    
    @Override
    public String toString() {
        StringBuilder graph = new StringBuilder();
        for(int i = 0; i < numberOfVertices; i++) {
            graph.append("V" + i);
            for (Integer adjNode : adjList.get(i)) {
                graph.append(" -> " + adjNode);
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
        
        System.out.print(graph.neighborsOf(1)); // [4, 3, 2, 0]
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
