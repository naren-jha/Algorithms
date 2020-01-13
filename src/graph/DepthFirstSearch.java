package graph;

import java.util.Stack;

// Graph Traversal - DFS

public class DepthFirstSearch extends Graph {

    public DepthFirstSearch(int numberOfVerices) {
        super(numberOfVerices);
    }
    
    /* Recursive solution */
    public void DFS(int s) {
        boolean[] visited = new boolean[numberOfVertices];
        DFSUtil(s, visited);
    }
    
    protected void DFSUtil(int s, boolean[] visited) {
        System.out.print(s + " ");
        visited[s] = true;
        
        for (Edge edge : adjList.get(s)) {
            int adjNode = edge.dv;
            if (!visited[adjNode])
                DFSUtil(adjNode, visited);
        }
    }
    
    // All DFS - For disconnected graph
    public void DFSAllDisconnectedGraph() {
        boolean[] visited = new boolean[numberOfVertices];
        
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (!visited[vn]) {
                DFSUtil(vn, visited);
            }
        }
    }
    
    /* Iterative Solution */
    public void DFSItr(int s) {
        boolean[] visited = new boolean[numberOfVertices];
        Stack<Integer> stack = new Stack<Integer>();
        
        stack.push(s);
        visited[s] = true;
        while (!stack.isEmpty()) {
            Integer v = stack.pop();
            System.out.print(v + " ");
            
            for (Edge edge : adjList.get(v)) {
                int adjNode = edge.dv;
                if (!visited[adjNode]) {
                    stack.push(adjNode);
                    visited[adjNode] = true;
                }
            }
        }
    }
    
    // All DFS - For disconnected graph
    public void DFSItrAllDisconnectedGraph() {
        boolean[] visited = new boolean[numberOfVertices];
        Stack<Integer> stack = new Stack<Integer>();
        
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (!visited[vn]) {
                stack.push(vn);
                visited[vn] = true;
                while (!stack.isEmpty()) {
                    Integer v = stack.pop();
                    System.out.print(v + " ");
                    
                    for (Edge edge : adjList.get(v)) {
                        int adjNode = edge.dv;
                        if (!visited[adjNode]) {
                            stack.push(adjNode);
                            visited[adjNode] = true;
                        }
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        DepthFirstSearch graph = new DepthFirstSearch(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        
        graph.DFS(0); // 0 4 3 2 1 
        System.out.println();
        graph.DFSAllDisconnectedGraph(); // 0 4 3 2 1 
        System.out.println();
        
        graph.DFSItr(0); // 0 1 2 3 4 
        System.out.println();
        graph.DFSItrAllDisconnectedGraph(); // 0 1 2 3 4 
    }
}
