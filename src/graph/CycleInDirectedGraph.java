package graph;

/**
 * Finding Cycle In Directed Graph
 * 
 * https://youtu.be/rKQaZuoUR4M
 * https://www.geeksforgeeks.org/detect-cycle-in-a-graph/
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class CycleInDirectedGraph extends Graph {
    
    public CycleInDirectedGraph(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    public boolean hasCycle() {
        boolean[] visited = new boolean[numberOfVertices];
        
        // indicates whether a node is currently in call stack or not
        boolean[] inStack = new boolean[numberOfVertices];
        
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (visited[vn]) continue;
            if (dfs(vn, visited, inStack)) return true;
        }
        
        return false;
    }
    
    private boolean dfs(int at, boolean[] visited,  boolean[] inStack) {
        visited[at] = true;
        inStack[at] = true;
        
        for (Edge edge : adjList.get(at)) {
            if (inStack[edge.to]) return true;
            
            if (visited[edge.to]) continue;
            
            if (dfs(edge.to, visited, inStack))
                return true;
        }
        
        inStack[at] = false;
        return false;
    }

    public static void main(String[] args) {
        CycleInDirectedGraph graph = new CycleInDirectedGraph(6);
        graph.addEdge(0, 1, false);
        graph.addEdge(0, 3, false);
        graph.addEdge(1, 2, false);
        graph.addEdge(3, 4, false);
        graph.addEdge(4, 0, false);
        System.out.println(graph.hasCycle()); // true
        
        graph.removeEdge(4, 0, false);
        System.out.println(graph.hasCycle()); // false
    }

}
