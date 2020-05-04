package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import basic.unionfind.UnionFind;

/**
 * implementation of Kruskal's algorithm to find Minimum Spanning Tree
 * Time Complexity: O(ElogE)
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class KruskalsMST extends Graph {
    
    // List to store MST edges
    private List<Edge> mst;

    public KruskalsMST(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    public Double mstCost() {
        LinkedList<Edge> allEdges = new LinkedList<Edge>();
        for (LinkedList<Edge> edges : adjList)
            allEdges.addAll(edges);
        
        Collections.sort(allEdges);
        
        UnionFind uf = new UnionFind(numberOfVertices);
        double cost = 0.0;
        mst = new ArrayList<Edge>();
        for (Edge edge : allEdges) {
            int from = edge.from, to = edge.to;
            
            // If nodes of this edge are in the same group
            // skip it to avoid forming a cycle
            if (uf.areConnected(from, to)) continue;
            
            // otherwise include this edge in MST
            uf.union(from, to);
            cost += edge.wt;
            mst.add(edge);
            
            // Optimization: stop early if we've already found a MST
            // that includes all nodes in the graph
            if (uf.getCompSize(0) == numberOfVertices) break;
        }
        
        // Make sure we've found a valid MST with all nodes in the graph
        if (uf.getCompSize(0) != numberOfVertices) return null;
        
        return cost;
    }
    
    public static void main(String[] args) {
        KruskalsMST graph = new KruskalsMST(10);
        
        graph.addEdge(0, 1, false, 5);
        graph.addEdge(1, 2, false, 4);
        graph.addEdge(2, 9, false, 2);
        graph.addEdge(0, 4, false, 1);
        graph.addEdge(0, 3, false, 4);
        graph.addEdge(1, 3, false, 2);
        graph.addEdge(2, 7, false, 4);
        graph.addEdge(2, 8, false, 1);
        graph.addEdge(9, 8, false, 0);
        graph.addEdge(4, 5, false, 1);
        graph.addEdge(5, 6, false, 7);
        graph.addEdge(6, 8, false, 4);
        graph.addEdge(4, 3, false, 2);
        graph.addEdge(5, 3, false, 5);
        graph.addEdge(3, 6, false, 11);
        graph.addEdge(6, 7, false, 1);
        graph.addEdge(3, 7, false, 2);
        graph.addEdge(7, 8, false, 6);
        
        System.out.println(graph.mstCost()); // 14.0
        for (Edge e : graph.mst)
            System.out.println(String.format("Used edge (%d, %d) with cost: %.2f", e.from, e.to, e.wt));
        /*
         * Used edge (9, 8) with cost: 0.00
         * Used edge (0, 4) with cost: 1.00
         * Used edge (2, 8) with cost: 1.00
         * Used edge (4, 5) with cost: 1.00
         * Used edge (6, 7) with cost: 1.00
         * Used edge (1, 3) with cost: 2.00
         * Used edge (3, 7) with cost: 2.00
         * Used edge (4, 3) with cost: 2.00
         * Used edge (1, 2) with cost: 4.00
         */
    }

}
