package graph;

import java.util.LinkedList;
import java.util.Queue;

// Graph Traversal - BFS
// https://youtu.be/09_LlHjoEiY?t=1998

public class BreadthFirstSearch extends Graph {
    
    public BreadthFirstSearch(int numberOfVetices) {
        super(numberOfVetices);
    }
    
    // TC: O(V+E), SC: O(V)
    public void BFS(int s) {
        boolean[] visited = new boolean[numberOfVertices];
        Queue<Integer> q = new LinkedList<Integer>();
        
        q.add(s);
        visited[s] = true;
        while (!q.isEmpty()) {
            int v = q.poll();
            System.out.print(v + " ");
            
            for (Edge edge : adjList.get(v)) {
                int adjNode = edge.to;
                if (!visited[adjNode]) {
                    q.add(adjNode);
                    visited[adjNode] = true;
                }
            }
        }
    }
    
    public void BFSAllForDisconnectedGraph() {
        boolean[] visited = new boolean[numberOfVertices];
        Queue<Integer> q = new LinkedList<Integer>();
        
        // do BFS starting with each node
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (!visited[vn]) {
                q.add(vn);
                visited[vn] = true;
                while (!q.isEmpty()) {
                    int v = q.poll();
                    System.out.print(v + " ");
                    
                    for (Edge edge : adjList.get(v)) {
                        int adjNode = edge.to;
                        if (!visited[adjNode]) {
                            q.add(adjNode);
                            visited[adjNode] = true;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        BreadthFirstSearch graph = new BreadthFirstSearch(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        
        graph.BFS(0); // 0 4 1 3 2 
    }
}
