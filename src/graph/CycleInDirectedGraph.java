package graph;

// https://youtu.be/rKQaZuoUR4M 
// https://www.geeksforgeeks.org/detect-cycle-in-a-graph/

public class CycleInDirectedGraph extends Graph {
    
    public CycleInDirectedGraph(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    public boolean hasCycle() {
        boolean[] visited = new boolean[numberOfVertices];
        
        // indicates whether a node is currently in call stack or not
        boolean[] stackFlag = new boolean[numberOfVertices];
        
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (!visited[vn] && hasCycleUtil(vn, visited, stackFlag)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasCycleUtil(int v, boolean[] visited,  boolean[] stackFlag) {
        stackFlag[v] = true;
        for (Edge edge : adjList.get(v)) {
            int adjNode = edge.dv;
            if (visited[adjNode])
                continue;
            
            if (stackFlag[adjNode])
                return true;
            
            if (hasCycleUtil(adjNode, visited, stackFlag))
                return true;
        }
        
        stackFlag[v] = false;
        visited[v] = true;
        return false;
    }

    public static void main(String[] args) {
        CycleInDirectedGraph graph = new CycleInDirectedGraph(5);
        graph.addEdge(0, 1, false);
        graph.addEdge(0, 3, false);
        graph.addEdge(1, 2, false);
        graph.addEdge(3, 4, false);
        graph.addEdge(4, 0, false);
        
        System.out.println(graph.hasCycle()); // true;
        
        graph.removeEdge(4, 0, false);
        System.out.println(graph.hasCycle()); // false;
    }

}
