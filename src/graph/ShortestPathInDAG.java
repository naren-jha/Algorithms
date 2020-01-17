package graph;

import java.util.Arrays;
import java.util.Stack;

// https://www.geeksforgeeks.org/shortest-path-for-directed-acyclic-graphs/

public class ShortestPathInDAG extends TopologicalSorting {
    
    public ShortestPathInDAG(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    public void shortestPath(int s) {
        validateVerticex(s);
        double[] dist = new double[numberOfVertices];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[s] = 0;
        
        Stack<Integer> stack = new Stack<Integer>();
        boolean[] visited = new boolean[numberOfVertices];
        
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (!visited[vn])
                topSortUtil(vn, visited, stack);
        }
        
        while (!stack.isEmpty()) {
            int v = stack.pop();
            
            if (dist[v] != Double.POSITIVE_INFINITY) {
                for (Edge edge : adjList.get(v)) {
                    if (dist[v] + edge.wt < dist[edge.to])
                        dist[edge.to] = dist[v] + edge.wt;
                }
            }
        }
        
        System.out.println("Following are shortest distances from source " + s);
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (dist[vn] == Double.POSITIVE_INFINITY)
                System.out.print("INF ");
            else
                System.out.print(dist[vn] + " ");
        }
    }
    
    public static void main(String[] args) {
        ShortestPathInDAG graph = new ShortestPathInDAG(6);
        
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
        
        graph.shortestPath(1);
        // Following are shortest distances from source 1
        // INF 0.0 2.0 6.0 5.0 3.0 
    }
}
