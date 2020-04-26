package graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of finding Eulerian path on a directed graph
 * 
 * https://youtu.be/09_LlHjoEiY?t=13947
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class EulerianPathDirectedGraph extends GraphUsingArrayOfArray {
    
    private final int n;
    private int edgeCount;
    private int[] in, out;
    private LinkedList<Integer> path;

    public EulerianPathDirectedGraph(int numberOfVertices) {
        super(numberOfVertices);
        
        n = numberOfVertices;
        in = new int[n];
        out = new int[n];
        path = new LinkedList<Integer>();
        edgeCount = 0;
    }
    
    // Returns a list of (edgeCount + 1) node ids that give the Eulerian path or
    // null if no path exists or the graph is disconnected.
    public LinkedList<Integer> getEulerianPath() {
        countInAndOutDegrees();
        if (!hasEulerianPath()) return null;
        dfs(findStartNode());
        
        // Make sure all edges of the graph were traversed. It could be the
        // case that the graph is disconnected in that case there isn't a
        // valid Eulerian path, and therefore we return null.
        if (path.size() != edgeCount + 1) return null;
        
        return path;
    }
    
    private void dfs(int at) {
        
        /* 
         * for (Edge edge : adjList.get(at)) {
               dfs(edge.to);
         * }
         * 
         * This won't work because we need to keep track of visited edges
         * otherwise this will go into infinite recursive calls and throw
         * stackoverflow error
         * 
         * Usually in a standard dfs we keep track of visited nodes
         * but here a node can be visited multiple times, so we can't do that,
         * we need to keep track of visited edges instead
        */
        
        
        // While the current node still has unvisited outgoing edges
        /*
         * We see that the out[] array here is currently serving two purposes
         * One is to track whether or not there are still outgoing edges, and 
         * the other is to index into the adjacency list to select the next outgoing edge
         * 
         * This assumes the adjacency list stores edges in a data structure that is 
         * indexable in O(1) (e.g stored in a array of array), 
         * that's why we are using GraphUsingArrayOfArray implementation and not 
         * the standard Graph implementation for this algorithm
         */
        while (out[at] > 0) {
            // reduce the number of outgoing edge for this node
            // marking the edge visited
            --out[at];
            
            // select the outgoing edge, and call dfs for destination node
            Edge nextEdge = adjList.get(at).get(out[at]); // we're using GraphUsingArrayOfArray
            // implementation of adjacency list here because of the .get(out[at]) used here, 
            // which would make this take linear time because of LinkedList.get(index), 
            // if we used standard Graph implementation, consequently affecting overall 
            // time-complexity of this algorithm
            
            dfs(nextEdge.to);
        }
        path.addFirst(at);
    }
    
    private int findStartNode() {
        int startNode = 0;
        for (int i = 0; i < n; ++i) {
            // Check if we have a unique starting node 
            // with exactly one extra outgoing edge
            if (out[i] - in[i] == 1) return i;
            
            // Make sure that we start at a node with an outgoing edge
            // This check prevents starting dfs at a singleton node
            // in a disconnected graph
            if (out[i] > 0)
                startNode = i;
        }
        return startNode;
    }
    
    private boolean hasEulerianPath() {
        if (edgeCount == 0) return false;
        
        int startNodeCount = 0, endNodeCount = 0;
        for (int i = 0; i < n; ++i) {
            // Check for too many incoming or outgoing edges
            // (out[i] - in[i] > 1) or (in[i] - out[i] > 1)
            if (Math.abs(in[i] - out[i]) > 1) return false;
            
            // Check if a node has exactly one extra outgoing edges
            // a potential start node in Eulerian path
            else if (out[i] - in[i] == 1) {
                startNodeCount++;
                
                // OPTIMIZATION: break the loop instantly and return false
                // if we've found more than one candidate for start node
                if (startNodeCount > 1) return false;
            }
            
            // Check if a node has exactly one extra incoming edges
            // a potential end node in Eulerian path
            else if (in[i] - out[i] == 1) {
                endNodeCount++;
                
                // OPTIMIZATION: break the loop instantly and return false
                // if we've found more than one candidate for end node
                if (endNodeCount > 1) return false;
            }
        }
        
        // Finally check if number of start nodes and end nodes are both either 0 or 1
        return (startNodeCount == 0 && endNodeCount == 0) ||
                (startNodeCount == 1 && endNodeCount == 1);
    }
    
    private void countInAndOutDegrees() {
        for (List<Edge> edges : adjList) {
            for (Edge edge : edges) {
                out[edge.from]++;
                in[edge.to]++;
                edgeCount++;
            }
        }
    }
    
    public static void main(String[] args) {
        EulerianPathDirectedGraph g = new EulerianPathDirectedGraph(7);
        g.addEdge(1, 2, false);
        g.addEdge(1, 3, false);
        g.addEdge(2, 2, false);
        g.addEdge(2, 4, false);
        g.addEdge(2, 4, false);
        g.addEdge(3, 1, false);
        g.addEdge(3, 2, false);
        g.addEdge(3, 5, false);
        g.addEdge(4, 3, false);
        g.addEdge(4, 6, false);
        g.addEdge(5, 6, false);
        g.addEdge(6, 3, false);
        
        System.out.println(g.getEulerianPath());
        // Prints: [1, 3, 5, 6, 3, 2, 4, 3, 1, 2, 2, 4, 6]
        
        // TEST CASE 2:
        EulerianPathDirectedGraph g2 = new EulerianPathDirectedGraph(5);
        g2.addEdge(0, 1, false);
        g2.addEdge(1, 2, false);
        g2.addEdge(1, 4, false);
        g2.addEdge(1, 3, false);
        g2.addEdge(2, 1, false);
        g2.addEdge(4, 1, false);
        System.out.println(g2);
        
        System.out.println(g2.getEulerianPath());
        // Prints: [0, 1, 4, 1, 2, 1, 3]
    }

}
