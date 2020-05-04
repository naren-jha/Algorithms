package graph;

import java.util.LinkedList;

import basic.unionfind.UnionFind;

/**
 * Implements algorithm to find number of connected components
 * using Union Find data structure
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class NumberOfConnectedComponentsUnionFind extends Graph {

    public NumberOfConnectedComponentsUnionFind(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    public int numOfConnectedComponents() {
        UnionFind uf = new UnionFind(numberOfVertices);
        
        for (LinkedList<Edge> edges : adjList)
            for (Edge edge : edges)
                uf.union(edge.from, edge.to);
        
        return uf.getNumOfComponents();
    }
    
    public static void main(String[] args) {
        NumberOfConnectedComponentsUnionFind graph =  
                new NumberOfConnectedComponentsUnionFind(18);
        
        graph.addEdge(0, 4); graph.addEdge(0, 8); graph.addEdge(0, 13); graph.addEdge(0, 14);
        graph.addEdge(13, 14); graph.addEdge(8, 4); graph.addEdge(8, 14);
        
        graph.addEdge(5, 1); graph.addEdge(5, 16); graph.addEdge(5, 17);
        
        graph.addEdge(15, 2); graph.addEdge(15, 9); graph.addEdge(15, 10);
        graph.addEdge(9, 2); graph.addEdge(9, 3);
        
        graph.addEdge(6, 7); graph.addEdge(6, 11); graph.addEdge(7, 11);
        
        System.out.println(graph.numOfConnectedComponents()); // 5
    }

}
