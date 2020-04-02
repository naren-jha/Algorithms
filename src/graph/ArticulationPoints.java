package graph;

/**
 * Finds all the articulation points in an undirected graph
 * 
 * https://www.youtube.com/watch?v=09_LlHjoEiY&t=8959s
 * https://www.hackerearth.com/practice/algorithms/graphs/articulation-points-and-bridges/tutorial/
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */

import static java.lang.Math.min;

public class ArticulationPoints extends Graph {
    
    private int n, id, outgoingEdgeCount;
    private int[] ids, lowLink;
    private boolean[] visited, isArticulationPoint;

    public ArticulationPoints(int numberOfVertices) {
        super(numberOfVertices);
        n = numberOfVertices;
    }
    
    // Returns a list of indices of all the articulation points in the graph
    public boolean[] findArticulationPoints() {
        id = 0;
        lowLink = new int[n];
        ids = new int[n];
        visited = new boolean[n];
        isArticulationPoint = new boolean[n];
        
        // find all articulation points in the graph across various connected components
        for (int vn = 0; vn < n; ++vn) {
            if (!visited[vn]) {
                outgoingEdgeCount = 0;
                dfs(vn, vn, -1);
                
                // check if a node that we marked 'true' inside 'dfs' is really an AP or not
                // if it has 0 or 1 outgoing edge, then it's not an AP
                isArticulationPoint[vn] = outgoingEdgeCount > 1;
            }
        }
        
        return isArticulationPoint;
    }
    
    private void dfs(int root, int at, int parent) {
        // keep track of number of outgoing edge for root nodes
        if (parent == root) ++outgoingEdgeCount;
        
        visited[at] = true;
        
        // label nodes with incremental id value
        ids[at] = ++id;
        
        // start with low-link value same as node id
        lowLink[at] = id;
        
        for (Edge e : adjList.get(at)) {
            int to = e.to;
            
            // ignore the edge that you came from
            if (to == parent) continue;
            
            // if node is not visited, then call dfs recursively,
            // update low-link value during backward propagation when dfs returns,
            // and update possible Articulation Point flag list
            if (!visited[to]) {
                dfs(root, to, at);
                lowLink[at] = min(lowLink[at], lowLink[to]);
                
                // we check for both < and = 
                // ids[at] < lowLink[to] handles the case when there is a bridge
                // ids[at] = lowLink[to] handles the case when there is a cycle
                if (ids[at] <= lowLink[to])
                    isArticulationPoint[at] = true;
            }
            
            // if the adjacent node that you're going to is already visited, 
            // then update low-link value with possible minimum id value 
            // of destination node
            else {
                lowLink[at] = min(lowLink[at], ids[to]);
            }
        }
        
    }
    
    public static void main(String[] args) {
        ArticulationPoints graph = new ArticulationPoints(9);
        
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(2, 5);
        graph.addEdge(5, 6);
        graph.addEdge(6, 7);
        graph.addEdge(7, 8);
        graph.addEdge(8, 5);
        
        boolean[] articulationPoints = graph.findArticulationPoints();
        for (int i = 0; i < articulationPoints.length; ++i) {
            if (articulationPoints[i]) 
                System.out.printf("Node %d is an articulation point \n", i);
        }
        /* Output:
         * Node 2 is an articulation point 
         * Node 3 is an articulation point 
         * Node 5 is an articulation point 
         */
        
        // TESTCASE 2: 
        // Tests a graph with 3 nodes in a line: A - B - C
        // Only node 'B' should be an articulation point
        ArticulationPoints graph2 = new ArticulationPoints(3);
        graph2.addEdge(0, 1);
        graph2.addEdge(1, 2);
        articulationPoints = graph2.findArticulationPoints();
        for (int i = 0; i < articulationPoints.length; ++i) {
            if (articulationPoints[i]) 
                System.out.printf("Node %d is an articulation point \n", i);
        }
        /* Output:
         * Node 1 is an articulation point 
         */
    }
    
}
