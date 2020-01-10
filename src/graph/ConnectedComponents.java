package graph;

/*
 * https://www.geeksforgeeks.org/connected-components-in-an-undirected-graph/
 * https://youtu.be/09_LlHjoEiY?t=1703
 * 
 * Connected Components For Undirected Graph
 * =========================================
 * 
 * Given an undirected graph, print all connected components line by line
 * 
 */
public class ConnectedComponents extends DepthFirstSearch {

    public ConnectedComponents(int numberOfVerices) {
        super(numberOfVerices);
    }
    
    public void connectedComponents() {
        boolean[] visited = new boolean[numberOfVertices];
        int count = 0;
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (!visited[vn]) {
                count++;
                System.out.print("Connected components " + count + ": ");
                
                DFSUtil(vn, visited);
                System.out.println();
            }
        }
        
        System.out.println("Hurray!!! You found " + count + " connected components");
    }
    
    public static void main(String[] args) {
        ConnectedComponents graph = new ConnectedComponents(18);
        
        // https://youtu.be/09_LlHjoEiY?t=1749
        graph.addEdge(0, 4); graph.addEdge(0, 8); graph.addEdge(0, 13); graph.addEdge(0, 14);
        graph.addEdge(13, 14); graph.addEdge(8, 4); graph.addEdge(8, 14);
        
        graph.addEdge(5, 1); graph.addEdge(5, 16); graph.addEdge(5, 17);
        
        graph.addEdge(15, 2); graph.addEdge(15, 9); graph.addEdge(15, 10);
        graph.addEdge(9, 2); graph.addEdge(9, 3);
        
        graph.addEdge(6, 7); graph.addEdge(6, 11); graph.addEdge(7, 11);
        
        graph.connectedComponents();
        /*
         * Connected component 1: 0 14 8 4 13 
         * Connected component 2: 1 5 17 16 
         * Connected component 3: 2 9 3 15 10 
         * Connected component 4: 6 11 7 
         * Connected component 5: 12 
         * Hurray!!! You found 5 connected components
         */
    }

}
