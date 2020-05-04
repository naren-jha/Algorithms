package graph;

import java.util.LinkedList;

import basic.unionfind.UnionFind;

/**
 * Implementation of algorithm to find cycle in a given directed graph
 * using Union Find data structure
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class FindCycleUnionFind extends Graph {
    
    UnionFind uf;

    public FindCycleUnionFind(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    public boolean hasCycle() {
        uf = new UnionFind(numberOfVertices);
        
        for (LinkedList<Edge> edges : adjList) {
            for (Edge edge : edges) {
                int from = edge.from, to = edge.to;
                if (uf.areConnected(from, to)) 
                    return true;
                uf.union(from, to);
            }
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        // Example - directed graph
        FindCycleUnionFind graph = new FindCycleUnionFind(5);
        
        graph.addEdge(0, 1, false);
        graph.addEdge(0, 3, false);
        graph.addEdge(1, 2, false);
        graph.addEdge(3, 4, false);
        graph.addEdge(4, 0, false);
        
        System.out.println(graph.hasCycle()); // true
        
        graph.removeEdge(4, 0, false);
        System.out.println(graph.hasCycle()); // false
    }

}
