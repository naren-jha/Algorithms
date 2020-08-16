package graph;

/**
 * Finding Cycle In Undirected Graph
 * 
 * https://youtu.be/n_t0a_8H8VY?t=323
 * https://www.geeksforgeeks.org/detect-cycle-undirected-graph/
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class CycleInUndirectedGraph extends Graph {

    public CycleInUndirectedGraph(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    public boolean hasCycle() {
        boolean[] visited = new boolean[numberOfVertices];
        
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (visited[vn]) continue;
            if (dfs(vn, visited, -1)) return true;
        }
        return false;
    }
    
    private boolean dfs(int at, boolean[] visited,  int parent) {
        visited[at] = true;
        for (Edge edge : adjList.get(at)) {
            // ignore parent
            if (edge.to == parent)
                continue;
            
            // if node is already visited and it is not parent, 
            // then there is cycle in graph
            if (visited[edge.to]) 
                return true;
            
            // go deeper using DFS
            if (dfs(edge.to, visited, at))
                return true;
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        CycleInUndirectedGraph g1 = new CycleInUndirectedGraph(5); 
        g1.addEdge(1, 0); 
        g1.addEdge(0, 2); 
        g1.addEdge(2, 1); 
        g1.addEdge(0, 3); 
        g1.addEdge(3, 4);
        System.out.println(g1.hasCycle()); // true
  
        CycleInUndirectedGraph g2 = new CycleInUndirectedGraph(3); 
        g2.addEdge(0, 1); 
        g2.addEdge(1, 2); 
        System.out.println(g2.hasCycle()); // false
    }
}
