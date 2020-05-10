package graph;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * Implementation of Prim's algorithm (eager version) to
 * find Minimum Spanning Tree using a self-balancing BST
 * Time Complexity: O(ElogV)
 * 
 * This implementation uses a self-balancing BST (specifically Red-Black Tree)
 * 
 * We can do eager Prim's implementation using an Indexed Priority Queue (IPQ) as well
 * 
 * Comparison: IPQ vs BST
 * To get next best edge in IPQ, we pop the min element from root, and 
 * then heapify the tree, which overall takes O(lgn). To get next best edge in 
 * BST, it would take O(lgn) as well, and then we’ll have to delete that entry 
 * which would take another O(lgn), but overall it is still O(lgn)
 * 
 * Insertion into both BST and IPQ takes O(lgn) anyway
 * 
 * Update in IPQ takes O(lgn). Update in BST as well can be done in 
 * O(lgn) [search the element in O(lgn) then delete that entry in another 
 * O(lgn) and then insert new entry with updated edge weight (and source node) 
 * in yet another O(lgn). Intotal, it takes 3*logn but overall still O(lgn)]

 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class PrimsMstUsingTreeSet extends Graph {

    private int n, m, edgeCount;
    private boolean[] visited;
    private Edge[] mst;
    private double cost;
    private TreeSet<Edge> sortedSet;
    
    public PrimsMstUsingTreeSet(int numberOfVertices) {
        super(numberOfVertices);
        n = numberOfVertices;
    }
    
    public Double mst(int s) {
        m = n - 1; // number of expected edges in mst
        edgeCount = 0;
        mst = new Edge[m];
        visited = new boolean[n];
        sortedSet = new TreeSet<>(getComparator());
        
        relaxEdgesAtNode(s);
        
        while (!sortedSet.isEmpty() && edgeCount != m) {
            //System.out.println(sortedSet);
            // pollFirst() retrieves and removes smallest element from TreeSet
            Edge edge = sortedSet.pollFirst();
            int nodeIndex = edge.to;
            
            // skip edges pointing to already visited nodes
            if (visited[nodeIndex]) continue;
            
            mst[edgeCount] = edge;
            edgeCount++;
            cost += edge.wt;
            
            relaxEdgesAtNode(nodeIndex);
        }
        
        return (edgeCount == m) ? cost : null;
    }
    
    private void relaxEdgesAtNode(int index) {
        visited[index] = true;

        for (Edge edge : adjList.get(index))  {
            int to = edge.to;
            
            if (visited[to]) continue;
            
            if (!sortedSet.contains(edge)) {
                sortedSet.add(edge);
            }
            else {
                Edge existingEdge = search(edge);
                if (existingEdge.wt > edge.wt) {
                    sortedSet.remove(existingEdge);
                    sortedSet.add(edge);
                }
            }
        }
    }
    
    private Comparator<Edge> getComparator() {
        return new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                // Java TreeSet is implemented in a way that it uses 
                // Comparable/Comparator logics for all comparisons.
                
                // i.e., it will use this comparator to do comparison 
                // in contains() method.
                
                // It will use this same comparator to do comparison 
                // during remove() method.
                
                // It will also use this same comparator, to perform 
                // sorting during add() method.
                
                // While looking up an edge from contains() or remove(), 
                // we need to perform check based on destinationNodeIndex,
                
                // But to keep elements in sorted order during add() operation
                // we need to compare elements based on their edge weights
                
                // For correct behavior of contains() and remove()
                if (e1.to == e2.to) return 0;
                
                // For correct sorting behavior
                if (e1.wt > e2.wt) return 1;
                else if (e1.wt < e2.wt) return -1;
                
                // Return -1 or 1 to make sure that different edges with equal 
                // weights are not ignored by TreeSet.add()
                // this check can be included in either 'if' or 'else' part 
                // above. Keeping this separate for readability.
                return -1;
            }
        };
    }
    
    // O(log n) search in TreeSet
    private Edge search(Edge e) {
        Edge ceil  = sortedSet.ceiling(e); // smallest element >= e
        Edge floor = sortedSet.floor(e);   // largest element <= e
        
        return ceil.equals(floor) ? ceil : null; 
    }
    
    public static void main(String[] args) {
        //example1();
        //example2();
        //example3();
        example4();
    }
    
    // example 1 from notes
    private static void example1() {
        int n = 7;
        PrimsMstUsingTreeSet graph = 
                new PrimsMstUsingTreeSet(n);
        
        graph.addEdge(0, 1, true, 9);
        graph.addEdge(0, 2, true, 0);
        graph.addEdge(0, 3, true, 5);
        graph.addEdge(0, 5, true, 7);
        graph.addEdge(1, 3, true, -2);
        graph.addEdge(1, 4, true, 3);
        graph.addEdge(1, 6, true, 4);
        graph.addEdge(2, 5, true, 6);
        graph.addEdge(3, 5, true, 2);
        graph.addEdge(3, 6, true, 3);
        graph.addEdge(4, 6, true, 6);
        graph.addEdge(5, 6, true, 1);
        
        int s = 0;
        Double cost = graph.mst(s);
        if (cost != null) {
            System.out.println(cost); // 9.0
            for (Edge e : graph.mst)
                System.out.println(String.format("Used edge (%d, %d) with cost: %.2f", e.from, e.to, e.wt));
            /*
             * Used edge (0, 2) with cost: 0.00
             * Used edge (0, 3) with cost: 5.00
             * Used edge (3, 1) with cost: -2.00
             * Used edge (3, 5) with cost: 2.00
             * Used edge (5, 6) with cost: 1.00
             * Used edge (1, 4) with cost: 3.00
             */
        }
        else {
            System.out.println("MST not found!");
        }
    }

    // example from kruskal's
    private static void example2() {
        int n = 10;
        PrimsMstUsingTreeSet graph = 
                new PrimsMstUsingTreeSet(n);
        
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
        
        int s = 0;
        Double cost = graph.mst(s);
        if (cost != null) {
            System.out.println(cost); // 14.0
            for (Edge e : graph.mst)
                System.out.println(String.format("Used edge (%d, %d) with cost: %.2f", e.from, e.to, e.wt));
            /*
             * Used edge (0, 4) with cost: 1.00
             * Used edge (4, 5) with cost: 1.00
             * Used edge (4, 3) with cost: 2.00
             * Used edge (3, 1) with cost: 2.00
             * Used edge (3, 7) with cost: 2.00
             * Used edge (7, 6) with cost: 1.00
             * Used edge (6, 8) with cost: 4.00
             * Used edge (8, 9) with cost: 0.00
             * Used edge (8, 2) with cost: 1.00
             */
        }
        else {
            System.out.println("MST not found!");
        }
    }
    
    // example 3 from notes
    private static void example3() {
        int n = 9;
        PrimsMstUsingTreeSet graph = 
                new PrimsMstUsingTreeSet(n);
        
        graph.addEdge(0, 1, true, 6);
        graph.addEdge(0, 3, true, 3);
        graph.addEdge(1, 2, true, 4);
        graph.addEdge(1, 4, true, 2);
        graph.addEdge(2, 5, true, 12);
        graph.addEdge(3, 4, true, 1);
        graph.addEdge(3, 6, true, 8);
        graph.addEdge(4, 5, true, 7);
        graph.addEdge(4, 7, true, 9);
        graph.addEdge(5, 8, true, 10);
        graph.addEdge(6, 7, true, 11);
        graph.addEdge(7, 8, true, 5);
        
        int s = 0;
        Double cost = graph.mst(s);
        if (cost != null) {
            System.out.println(cost); // 39.0
            for (Edge e : graph.mst)
                System.out.println(String.format("Used edge (%d, %d) with cost: %.2f", e.from, e.to, e.wt));
            /*
             * Used edge (0, 3) with cost: 3.00
             * Used edge (3, 4) with cost: 1.00
             * Used edge (4, 1) with cost: 2.00
             * Used edge (1, 2) with cost: 4.00
             * Used edge (4, 5) with cost: 7.00
             * Used edge (3, 6) with cost: 8.00
             * Used edge (4, 7) with cost: 9.00
             * Used edge (7, 8) with cost: 5.00
             */
        }
        else {
            System.out.println("MST not found!");
        }
    }
    
    // example used in demonstration from notes
    private static void example4() {
        int n = 8;
        PrimsMstUsingTreeSet graph = 
                new PrimsMstUsingTreeSet(n);
        
        graph.addEdge(0, 1, true, 10);
        graph.addEdge(0, 2, true, 1);
        graph.addEdge(0, 3, true, 4);
        graph.addEdge(2, 1, true, 3);
        graph.addEdge(2, 5, true, 8);
        graph.addEdge(2, 3, true, 2);
        graph.addEdge(3, 5, true, 2);
        graph.addEdge(3, 6, true, 7);
        graph.addEdge(5, 4, true, 1);
        graph.addEdge(5, 7, true, 9);
        graph.addEdge(5, 6, true, 6);
        graph.addEdge(4, 1, true, 0);
        graph.addEdge(4, 7, true, 8);
        graph.addEdge(6, 7, true, 12);
        
        int s = 0;
        Double cost = graph.mst(s);
        if (cost != null) {
            System.out.println(cost); // 20.0
            for (Edge e : graph.mst)
                System.out.println(String.format("Used edge (%d, %d) with cost: %.2f", e.from, e.to, e.wt));
            /*
             * Used edge (0, 2) with cost: 1.00
             * Used edge (2, 3) with cost: 2.00
             * Used edge (3, 5) with cost: 2.00
             * Used edge (5, 4) with cost: 1.00
             * Used edge (4, 1) with cost: 0.00
             * Used edge (5, 6) with cost: 6.00
             * Used edge (4, 7) with cost: 8.00
             */
        }
        else {
            System.out.println("MST not found!");
        }
    }
}
