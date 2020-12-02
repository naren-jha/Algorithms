package graph;

/**
 * Finds all the bridges in an undirected graph
 * 
 * https://www.youtube.com/watch?v=09_LlHjoEiY&t=8959s
 * https://www.hackerearth.com/practice/algorithms/graphs/articulation-points-and-bridges/tutorial/
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class Bridges extends Graph {
    
    private int n, id;
    private int[] ids, lowLink;
    private boolean[] visited;
    private List<Pair<Integer, Integer>> bridges;

    public Bridges(int numberOfVertices) {
        super(numberOfVertices);
        n = numberOfVertices;
    }
    
    // Returns a list of pair of nodes forming bridges in the given graph
    public List<Pair<Integer, Integer>> findBridges() {
        id = 0;
        lowLink = new int[n];
        ids = new int[n];
        visited = new boolean[n];
        bridges = new ArrayList<Pair<Integer, Integer>>();
        
        // find all bridges in the graph across various connected components
        for (int vn = 0; vn < n; ++vn) {
            if (!visited[vn])
                dfs(vn, -1);
        }
        
        return bridges;
    }
    
    private void dfs(int at, int parent) {
        visited[at] = true;
        
        // label nodes with incremental id value
        ids[at] = ++id;
        
        // start with low-link value same as node id
        lowLink[at] = id;
        
        for (Edge e : adjList.get(at)) {
            int to = e.to;
            
            // ignore the edge that you came from
            if (to == parent) continue;
            
            // if node is not visited, then call dfs recursively,
            // update low-link value during backward propagation when dfs returns,
            // and add possible bridges to the result list
            if (!visited[to]) {
                dfs(to, at);
                lowLink[at] = min(lowLink[at], lowLink[to]);
                if (ids[at] < lowLink[to])
                    bridges.add(new Pair<>(at, to));
            }
            
            // if the adjacent node that you're going to is already visited, 
            // then update low-link value with possible minimum id value 
            // of destination node
            else {
                lowLink[at] = min(lowLink[at], ids[to]);
            }
        }
        
    }
    
    public static void main(String[] args) {
        Bridges graph = new Bridges(9);
        
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(2, 5);
        graph.addEdge(5, 6);
        graph.addEdge(6, 7);
        graph.addEdge(7, 8);
        graph.addEdge(8, 5);
        
        List<Pair<Integer, Integer>> bridges = graph.findBridges();
        for (Pair<Integer, Integer> bridge : bridges) {
            int from = bridge.getKey();
            int to = bridge.getValue();
            System.out.printf("Bridge between nodes: %d and %d \n", from, to);
        }
        /* Output:
         * Bridge between nodes: 2 and 5 
         * Bridge between nodes: 3 and 4 
         * Bridge between nodes: 2 and 3 
         */
    }
    
}
