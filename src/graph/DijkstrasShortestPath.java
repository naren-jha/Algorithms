package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringJoiner;

/*
 * https://youtu.be/09_LlHjoEiY?t=4776
 * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 * https://brilliant.org/wiki/dijkstras-short-path-finder/
 * https://www.youtube.com/watch?v=XB4MIexjvY0
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 */

public class DijkstrasShortestPath extends Graph {

    public DijkstrasShortestPath(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    private class QueueEntry {
        int vn; // vertex number
        double dist; // distance
        
        QueueEntry(int vn, double dist) {
            this.vn = vn;
            this.dist = dist;
        }
    }
    
    /*
     * Finds shortest path distances from a given source node 
     * to all destination nodes in the graph
     */
    public void shortestPath(int s) {
        validateVertex(s);
        
        double[] dist = new double[numberOfVertices];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        
        // Array used to track which nodes have already been visited.
        boolean[] visited = new boolean[numberOfVertices];
        
        // Keep a priority queue of the next most promising node to visit.
        PriorityQueue<QueueEntry> pq = new PriorityQueue<>(2 * numberOfVertices, (a, b) -> Double.compare(a.dist, b.dist));
        
        // start with source
        pq.offer(new QueueEntry(s, 0));
        dist[s] = 0;
        
        while (!pq.isEmpty()) {
            QueueEntry entry = pq.poll();
            int vn = entry.vn;
            if (visited[vn]) continue;
            visited[vn] = true;
            
            for (Edge edge : adjList.get(vn)) {
                int src = edge.from;
                int dest = edge.to;

                // You cannot get a shorter path by revisiting
                // a node you have already visited before.
                if (visited[dest]) continue;
                
                // Relax edge by updating minimum cost if applicable
                if (dist[src] + edge.wt < dist[dest]) {
                    dist[dest] = dist[src] + edge.wt;
                    pq.offer(new QueueEntry(dest, dist[dest]));
                }
            }
        }
        
        for (int vn = 0; vn < numberOfVertices; ++vn)
            System.out.printf("The cost to get from node %d to %d is %.2f\n", s, vn, dist[vn]);
    }
    
    /*
     * Finds shortest path distance from a given source to node in the graph
     * Also prints the corresponding path
     */
    public void shortestPath(int s, int d) {
        validateSourceAndDestinationVertices(s, d);
        
        double[] dist = new double[numberOfVertices];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        
        // Array used to track which nodes have already been visited.
        boolean[] visited = new boolean[numberOfVertices];
        
        // Keep a priority queue of the next most promising node to visit.
        PriorityQueue<QueueEntry> pq = new PriorityQueue<>(2 * numberOfVertices, (a, b) -> Double.compare(a.dist, b.dist));
        pq.offer(new QueueEntry(s, 0));
        dist[s] = 0;
        
        int[] prev = new int[numberOfVertices];
        Arrays.fill(prev, -1);
        
        while (!pq.isEmpty()) {
            QueueEntry entry = pq.poll();
            int vn = entry.vn;
            if (visited[vn]) continue;
            visited[vn] = true;
            
            // is destination node found
            if (vn == d) break;
            
            for (Edge edge : adjList.get(vn)) {
                int src = edge.from;
                int dest = edge.to;
                
                // You cannot get a shorter path by revisiting
                // a node you have already visited before.
                if (visited[dest]) continue;
                
                // Relax edge by updating minimum cost if applicable
                if (dist[src] + edge.wt < dist[dest]) {
                    dist[dest] = dist[src] + edge.wt;
                    pq.offer(new QueueEntry(dest, dist[dest]));
                    prev[dest] = src;
                }
            }
        }
        
        if (prev[d] == -1) {
            System.out.println("No path found from node " + s + " to " + d);
            return;
        }
        
        System.out.printf("The cost to get from node %d to %d is %.2f\n", s, d, dist[d]);
        reconstructPath(d, prev);
    }
    
    public void reconstructPath(int d, int[] prev) {       
        List<Integer> path = new ArrayList<Integer>();
        for (int v = d; v != -1; v = prev[v]) 
            path.add(v);
        Collections.reverse(path);
        
        StringJoiner joiner = new StringJoiner("->");
        for (Integer item : path)
            joiner.add(item.toString());
        
        System.out.println("Shortest path is: " + joiner);
    }
    
    public static void main(String[] args) {
        DijkstrasShortestPath graph = new DijkstrasShortestPath(6);
        graph.addEdge(0, 1, false, 5);
        graph.addEdge(0, 2, false, 1);
        graph.addEdge(1, 2, false, 2);
        graph.addEdge(1, 3, false, 3);
        graph.addEdge(1, 4, false, 20);
        graph.addEdge(2, 1, false, 3);
        graph.addEdge(2, 4, false, 12);
        graph.addEdge(3, 2, false, 3);
        graph.addEdge(3, 4, false, 2);
        graph.addEdge(3, 5, false, 6);
        graph.addEdge(4, 5, false, 1);
        
        graph.shortestPath(0);
        /*
         * The cost to get from node 0 to 0 is 0.00
         * The cost to get from node 0 to 1 is 4.00
         * The cost to get from node 0 to 2 is 1.00
         * The cost to get from node 0 to 3 is 7.00
         * The cost to get from node 0 to 4 is 9.00
         * The cost to get from node 0 to 5 is 10.00
         */
        System.out.println();
        
        graph.shortestPath(0, 5);
        /*
         * The cost to get from node 0 to 5 is 10.00
         * Shortest path is: 0->2->1->3->4->5
         */
    }
}
