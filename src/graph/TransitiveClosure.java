package graph;

/**
 * TransitiveClosure Of A Graph
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class TransitiveClosure extends Graph {

    public TransitiveClosure(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    // TC: O(V*(V+E))
    public void transitiveClosure() {
        boolean[][] tc = new boolean[numberOfVertices][numberOfVertices];
        
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            DFSUtil(vn, vn, tc);
        }
        
        for (int i = 0; i < numberOfVertices; ++i) {
            for (int j = 0; j < numberOfVertices; ++j)
                System.out.print(tc[i][j] ? "1 " : "0 ");
            System.out.println();
        }
    }

    private void DFSUtil(int s, int d, boolean[][] tc) {
        tc[s][d] = true;
        
        for (Edge edge : adjList.get(d)) {
            int dest = edge.to;
            if (!tc[s][dest])
                DFSUtil(s, dest, tc);
        }
    }
    
    public static void main(String[] args) {
        TransitiveClosure graph = new TransitiveClosure(5);
        graph.addEdge(0, 1, false); // directed graph
        graph.addEdge(0, 4, false);
        graph.addEdge(1, 2, false);
        graph.addEdge(1, 3, false);
        graph.addEdge(1, 4, false);
        graph.addEdge(2, 3, false);
        graph.addEdge(3, 4, false);
        
        graph.transitiveClosure();
        /*
         * 1 1 1 1 1 
         * 0 1 1 1 1 
         * 0 0 1 1 1 
         * 0 0 0 1 1 
         * 0 0 0 0 1 
         */
    }
}
