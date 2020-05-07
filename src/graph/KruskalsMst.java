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
public class KruskalsMst extends Graph {
    
    // List to store MST edges
    private List<Edge> mst;

    public KruskalsMst(int numberOfVertices) {
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
        KruskalsMst graph = new KruskalsMst(10);
        
        graph.addEdge(0, 1, true, 5);
        graph.addEdge(1, 2, true, 4);
        graph.addEdge(2, 9, true, 2);
        graph.addEdge(0, 4, true, 1);
        graph.addEdge(0, 3, true, 4);
        graph.addEdge(1, 3, true, 2);
        graph.addEdge(2, 7, true, 4);
        graph.addEdge(2, 8, true, 1);
        graph.addEdge(9, 8, true, 0);
        graph.addEdge(4, 5, true, 1);
        graph.addEdge(5, 6, true, 7);
        graph.addEdge(6, 8, true, 4);
        graph.addEdge(4, 3, true, 2);
        graph.addEdge(5, 3, true, 5);
        graph.addEdge(3, 6, true, 11);
        graph.addEdge(6, 7, true, 1);
        graph.addEdge(3, 7, true, 2);
        graph.addEdge(7, 8, true, 6);
        
        Double cost = graph.mstCost();
        if (cost != null) {
            System.out.println(cost); // 14.0
            for (Edge e : graph.mst)
                System.out.println(String.format("Used edge (%d, %d) with cost: %.2f", e.from, e.to, e.wt));
            /*
             * Used edge (8, 9) with cost: 0.00
             * Used edge (0, 4) with cost: 1.00
             * Used edge (2, 8) with cost: 1.00
             * Used edge (4, 5) with cost: 1.00
             * Used edge (6, 7) with cost: 1.00
             * Used edge (1, 3) with cost: 2.00
             * Used edge (3, 7) with cost: 2.00
             * Used edge (3, 4) with cost: 2.00
             * Used edge (1, 2) with cost: 4.00
             */
        }
        else {
            System.out.println("MST not found!");
        }
    }

}
