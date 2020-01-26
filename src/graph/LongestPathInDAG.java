package graph;

import java.util.Arrays;
import java.util.Stack;

/**
 * Longest Path In Directed Acyclic Graph (DAG)
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class LongestPathInDAG extends TopologicalSorting {
    
    public LongestPathInDAG(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    public void longestPath(int s) {
        validateVertex(s);
        double[] dist = new double[numberOfVertices];
        Arrays.fill(dist, Double.NEGATIVE_INFINITY);
        dist[s] = 0;
        
        Stack<Integer> stack = new Stack<Integer>();
        boolean[] visited = new boolean[numberOfVertices];
        
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (!visited[vn])
                topSortUtil(vn, visited, stack);
        }
        
        while (!stack.isEmpty()) {
            int v = stack.pop();
            
            if (dist[v] != Double.NEGATIVE_INFINITY) {
                for (Edge edge : adjList.get(v)) {
                    if (dist[v] + edge.wt > dist[edge.to])
                        dist[edge.to] = dist[v] + edge.wt;
                }
            }
        }
        
        System.out.println("Following are longest distances from source " + s);
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (dist[vn] == Double.NEGATIVE_INFINITY)
                System.out.print("INF ");
            else
                System.out.print(dist[vn] + " ");
        }
    }
    
    public static void main(String[] args) {
        LongestPathInDAG graph = new LongestPathInDAG(6);
        
        // add directed weighted edges
        graph.addEdge(0, 1, false, 5); 
        graph.addEdge(0, 2, false, 3); 
        graph.addEdge(1, 3, false, 6); 
        graph.addEdge(1, 2, false, 2); 
        graph.addEdge(2, 4, false, 4); 
        graph.addEdge(2, 5, false, 2); 
        graph.addEdge(2, 3, false, 7); 
        graph.addEdge(3, 4, false, -1); 
        graph.addEdge(4, 5, false, -2);
        
        graph.longestPath(1);
        // Following are shortest distances from source 1
        // INF 0.0 2.0 9.0 8.0 6.0 
    }
}
