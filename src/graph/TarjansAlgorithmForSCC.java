package graph;

import static java.lang.Math.min;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of Tarjan's algorithm to find strongly connected components 
 * in a given directed graph. Time complexity: O(V+E)
 * 
 * https://youtu.be/09_LlHjoEiY?t=10653
 * https://www.hackerearth.com/practice/algorithms/graphs/strongly-connected-components/tutorial/
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class TarjansAlgorithmForSCC extends Graph {
    
    private int n, id;
    private int[] ids, lowLink;
    //private boolean[] visited;
    private Deque<Integer> stack;
    private boolean[] onStack;
    
    private static final int UNVISITED = -1;

    public TarjansAlgorithmForSCC(int numberOfVertices) {
        super(numberOfVertices);
        n = numberOfVertices;
    }
    
    public Collection<List<Integer>> findSCCs() {
        id = 0;
        lowLink = new int[n];
        ids = new int[n];
        //visited = new boolean[n];
        stack = new ArrayDeque<Integer>();
        onStack = new boolean[n];
        
        Arrays.fill(ids, UNVISITED);
        
        for (int vn = 0; vn < n; ++vn) {
            if (ids[vn] == UNVISITED)
                dfs(vn);
        }
        
        // Generate groups of SCC nodes from lowLink values
        Map<Integer, List<Integer>> sccMap = new HashMap<>();
        for (int i = 0; i < lowLink.length; ++i) {
            if (!sccMap.containsKey(lowLink[i]))
                sccMap.put(lowLink[i], new ArrayList<>());
            sccMap.get(lowLink[i]).add(i);
        }
        return sccMap.values();
    }
    
    private void dfs(int at) {
        ids[at] = lowLink[at] = ++id;
        //visited[at] = true;
        stack.push(at);
        onStack[at] = true;
        
        for (Edge e : adjList.get(at)) {
            int to = e.to;
            if (ids[to] == UNVISITED)
                dfs(to);
            if (onStack[to]) 
                lowLink[at] = min(lowLink[at], lowLink[to]);
        }
        
        // On recursive callback, if we're at the root node (start of a SCC)
        // empty the seen stack until back to root
        if (ids[at] == lowLink[at]) {
            int node;
            do {
                node = stack.pop();
                onStack[node] = false;
            } while (node != at);
        }
    }
    
    public static void main(String[] args) {
        TarjansAlgorithmForSCC graph = new TarjansAlgorithmForSCC(8);
        // add directed edges
        graph.addEdge(0, 1, false);
        graph.addEdge(1, 2, false);
        graph.addEdge(2, 0, false);
        graph.addEdge(4, 5, false);
        graph.addEdge(5, 0, false);
        graph.addEdge(5, 6, false);
        graph.addEdge(6, 0, false);
        graph.addEdge(6, 2, false);
        graph.addEdge(6, 4, false);
        graph.addEdge(3, 4, false);
        graph.addEdge(3, 7, false);
        graph.addEdge(7, 5, false);
        graph.addEdge(7, 3, false);
        
        Collection<List<Integer>> sccs = graph.findSCCs();
        System.out.printf("Number of Strongly Connected Components: %d \n", sccs.size());
        for (List<Integer> scc : sccs) {
            System.out.println("Nodes: " + scc + " form a Strongly Connected Component");
        }
        
        /* Outputs:
         * Number of Strongly Connected Components: 3 
         * Nodes: [0, 1, 2] form a Strongly Connected Component
         * Nodes: [3, 7] form a Strongly Connected Component
         * Nodes: [4, 5, 6] form a Strongly Connected Component
         */
        
        // TEST 2:
        graph = new TarjansAlgorithmForSCC(11);
        // add directed edges
        graph.addEdge(0, 1, false);
        graph.addEdge(1, 2, false);
        graph.addEdge(2, 0, false);
        graph.addEdge(1, 3, false);
        graph.addEdge(3, 4, false);
        graph.addEdge(4, 5, false);
        graph.addEdge(5, 3, false);
        graph.addEdge(6, 7, false);
        graph.addEdge(7, 8, false);
        graph.addEdge(8, 9, false);
        graph.addEdge(9, 6, false);
        graph.addEdge(9, 10, false);
        graph.addEdge(6, 5, false);
        
        sccs = graph.findSCCs();
        System.out.printf("Number of Strongly Connected Components: %d \n", sccs.size());
        for (List<Integer> scc : sccs) {
            System.out.println("Nodes: " + scc + " form a Strongly Connected Component");
        }
        
        /* Outputs:
         * Number of Strongly Connected Components: 4 
         * Nodes: [0, 1, 2] form a Strongly Connected Component
         * Nodes: [3, 4, 5] form a Strongly Connected Component
         * Nodes: [6, 7, 8, 9] form a Strongly Connected Component
         * Nodes: [10] form a Strongly Connected Component
         */
    }

}
