package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of Kosaraju's algorithm to find strongly connected components 
 * in a given directed graph. Time complexity: O(V+E)
 * 
 * https://youtu.be/RpgcYiky7uw
 * https://www.hackerearth.com/practice/algorithms/graphs/strongly-connected-components/tutorial/
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class KosarajusAlgorithmForSCC extends Graph {

    private int n;
    private boolean[] visited;
    private Deque<Integer> stack;
    private Graph trasposeGraph;
    
    public KosarajusAlgorithmForSCC(int numberOfVertices) {
        super(numberOfVertices);
        n = numberOfVertices;
    }
    
    public List<List<Integer>> findSCCs() {
        visited = new boolean[n];
        stack = new ArrayDeque<Integer>();
        
        for (int vn = 0; vn < n; ++vn) {
            if (!visited[vn])
                dfs(vn);
        }
        
        createTransposeGraph();
        Arrays.fill(visited, false);
        List<List<Integer>> sccs = new ArrayList<>();
        
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited[v]) {
                List<Integer> scc =  new ArrayList<Integer>();
                dfsOnTransposeGraph(v, scc);
                sccs.add(scc);
            }
        }
        
        return sccs;
    }
    
    private void dfs(int v) {
        visited[v] = true;
        for (Edge edge : adjList.get(v)) {
            int to = edge.to;
            if (!visited[to])
                dfs(to);
        }
        stack.push(v);
    }
    
    private void createTransposeGraph() {
        trasposeGraph = new Graph(n);
        for (LinkedList<Edge> eList : adjList) {
            for (Edge edge : eList) {
                trasposeGraph.addEdge(edge.to, edge.from, false);
            }
        }
    }
    
    private void dfsOnTransposeGraph(int v, List<Integer> scc) {
        visited[v] = true;
        scc.add(v);
        for (Edge edge : trasposeGraph.adjList.get(v)) {
            int to = edge.to;
            if (!visited[to])
                dfsOnTransposeGraph(to, scc);
        }
    }
    
    public static void main(String[] args) {
        KosarajusAlgorithmForSCC graph = new KosarajusAlgorithmForSCC(8);
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
        
        List<List<Integer>> sccs = graph.findSCCs();
        System.out.printf("Number of Strongly Connected Components: %d \n", sccs.size());
        for (List<Integer> scc : sccs) {
            System.out.println("Nodes: " + scc + " form a Strongly Connected Component");
        }
        
        /* Outputs:
         * Number of Strongly Connected Components: 3 
         * Nodes: [3, 7] form a Strongly Connected Component
         * Nodes: [5, 4, 6] form a Strongly Connected Component
         * Nodes: [0, 2, 1] form a Strongly Connected Component
         */
    }

}
