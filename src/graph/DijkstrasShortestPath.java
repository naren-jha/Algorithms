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
    
    private class QueueEntry implements Comparable<QueueEntry> {
        int vn; // vertex number
        double dist; // distance
        
        QueueEntry(int vn, double dist) {
            this.vn = vn;
            this.dist = dist;
        }
        
        @Override
        public int compareTo(QueueEntry o) {
            // priority queue needs to keep element sorted 
            // in ascending order based on distance
            if (this.dist - o.dist > 0)
                return 1;
            else if (this.dist - o.dist < 0)
                return -1;
            else
                return 0;
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
        dist[s] = 0;
        
        // Array used to track which nodes have already been visited.
        boolean[] visited = new boolean[numberOfVertices];
        visited[s] = true;
        
        // Keep a priority queue of the next most promising node to visit.
        PriorityQueue<QueueEntry> pq = new PriorityQueue<>(2*numberOfVertices);
        pq.offer(new QueueEntry(s, 0));
        
        while (!pq.isEmpty()) {
            QueueEntry entry = pq.poll();
            int vn = entry.vn;
            visited[vn] = true;
            
            // We already found a better path before we got to
            // process this QueueEntry so we can ignore this entry
            if (dist[vn] < entry.dist) continue;
            
            for (Edge edge : adjList.get(vn)) {
                int src = edge.from;
                int dest = edge.to;

                // You cannot get a shorter path by revisiting
                // a node you have already visited before.
                if (visited[dest]) continue;
                
                // Relax edge by updating minimum cost if applicable
                if (dist[dest] > dist[src] + edge.wt) {
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
        dist[s] = 0;
        
        // Array used to track which nodes have already been visited.
        boolean[] visited = new boolean[numberOfVertices];
        visited[s] = true;
        
        // Keep a priority queue of the next most promising node to visit.
        PriorityQueue<QueueEntry> pq = new PriorityQueue<>(2*numberOfVertices);
        pq.offer(new QueueEntry(s, 0));
        
        int[] prev = new int[numberOfVertices];
        
        while (!pq.isEmpty()) {
            QueueEntry entry = pq.poll();
            int vn = entry.vn;
            visited[vn] = true;
            
            // is destination node found
            if (vn == d) break;
            
            // We already found a better path before we got to
            // process this QueueEntry so we can ignore this entry
            if (dist[vn] < entry.dist) continue;
            
            for (Edge edge : adjList.get(vn)) {
                int src = edge.from;
                int dest = edge.to;
                
                // You cannot get a shorter path by revisiting
                // a node you have already visited before.
                if (visited[dest]) continue;
                
                // Relax edge by updating minimum cost if applicable
                if (dist[dest] > dist[src] + edge.wt) {
                    dist[dest] = dist[src] + edge.wt;
                    pq.offer(new QueueEntry(dest, dist[dest]));
                    prev[dest] = src;
                }
            }
        }
        
        System.out.printf("The cost to get from node %d to %d is %.2f\n", s, d, dist[d]);
        reconstructPath(s, d, prev, dist[d]);
    }
    
    public void reconstructPath(int src, int dest, int[] prev, double dist) {
        validateSourceAndDestinationVertices(src, dest);
        
        if (dist == Double.POSITIVE_INFINITY) {
            System.out.println("No path found from " + src + " to " + dest);
            return;
        }
        
        List<Integer> path = new ArrayList<Integer>();
        for (int vn = dest; vn != src; vn = prev[vn]) 
            path.add(vn);
        path.add(src);
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
