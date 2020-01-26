package graph;

import java.util.Arrays;
import java.util.LinkedList;


/**
 * https://youtu.be/09_LlHjoEiY?t=6647 
 * https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm 
 * https://brilliant.org/wiki/bellman-ford-algorithm/
 * https://www.youtube.com/watch?v=Ttezuzs39nk
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class BellmanFord extends Graph {

    public BellmanFord(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    public void shortestPath(int s) {
        validateVertex(s);
        double[] dist = new double[numberOfVertices];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[s] = 0;
        
        for (int vn = 0; vn < numberOfVertices - 1; ++vn) {
            for (LinkedList<Edge> edges : adjList) {
                for (Edge edge : edges) {
                    if (dist[edge.to] > dist[edge.from] + edge.wt) // relaxation
                        dist[edge.to] = dist[edge.from] + edge.wt;
                }
            }
        }
        
        for (int vn = 0; vn < numberOfVertices - 1; ++vn) {
            for (LinkedList<Edge> edges : adjList) {
                for (Edge edge : edges) {
                    if (dist[edge.to] > dist[edge.from] + edge.wt)
                        dist[edge.to] = Double.NEGATIVE_INFINITY;
                }
            }
        }
        
        for (int vn = 0; vn < numberOfVertices; ++vn)
            System.out.printf("The cost to get from node %d to %d is %.2f\n", s, vn, dist[vn]);
    }
    
    public static void main(String[] args) {
        BellmanFord graph = new BellmanFord(9);
        graph.addEdge(0, 1, false, 1);
        graph.addEdge(1, 2, false, 1);
        graph.addEdge(2, 4, false, 1);
        graph.addEdge(4, 3, false, -3);
        graph.addEdge(3, 2, false, 1);
        graph.addEdge(1, 5, false, 4);
        graph.addEdge(1, 6, false, 4);
        graph.addEdge(5, 6, false, 5);
        graph.addEdge(6, 7, false, 4);
        graph.addEdge(5, 7, false, 3);
        
        int start = 0;
        graph.shortestPath(start);
    }
}
