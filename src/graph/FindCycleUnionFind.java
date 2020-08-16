package graph;

import java.util.LinkedList;

import basic.unionfind.UnionFind;

/**
 * Implementation of algorithm to find cycle in a given undirected graph
 * using Union Find data structure
 * 
 * Note: Using Union Find, we cannot find cycle in a directed graph
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class FindCycleUnionFind extends Graph {
    
    public FindCycleUnionFind(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    public boolean hasCycle() {
        UnionFind uf = new UnionFind(numberOfVertices);
        
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
        FindCycleUnionFind g1 = new FindCycleUnionFind(5); 
        g1.addEdge(1, 0, false); 
        g1.addEdge(0, 2, false); 
        g1.addEdge(2, 1, false); 
        g1.addEdge(0, 3, false); 
        g1.addEdge(3, 4, false);
        System.out.println(g1.hasCycle()); // true
  
        FindCycleUnionFind g2 = new FindCycleUnionFind(3); 
        g2.addEdge(0, 1, false); 
        g2.addEdge(1, 2, false); 
        System.out.println(g2.hasCycle()); // false
        
        // Every directed edge here is treated as an undirected edge
        // that means, we don't add two edges u->v and v->u
        // there is just one u->v which is treated as bidirectional
    }

}
