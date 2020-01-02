package graph_old;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Narendra Jha
 * 
 * Topological sorting using Kahn's algorithm
 */
class Graph4 {
	private int v; // no. of vertices
	private LinkedList<Integer>[] adjList; // adjacency lists
	
	// constructor
	Graph4(int v) {
		this.v = v;
		
		// assign array of linked list
		adjList = (LinkedList<Integer>[]) new LinkedList<?>[v];
		
		// create a new list for each vertex
        // to store adjacent nodes
		for (int i = 0; i < v; i++)
			adjList[i] = new LinkedList<Integer>();
	}
	
	// method to add a directed edge to the graph
	public void addEdge(int src, int dest) {
		adjList[src].add(dest);
	}
	
	// Kahn's algorithm for topological sorting
	public void kahnsTopSort() {
		// calculate indegrees of all vertices: O(V+E)
		int[] indegree = new int[v];
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < adjList[i].size(); j++) {
				int dest = adjList[i].get(j);
				indegree[dest]++;
			}
		}
		
		// enqueue all vertices with 0 indegrees to a queue
		Queue<Integer> q = new LinkedList<Integer>();
		for (int i = 0; i < v; i++) {
			if (indegree[i] == 0)
				q.add(i);
		}
		
		int cnt = 0; // count of visited vertices
		
		// list to store result (nodes in topological order)
		List<Integer> topOrder = new ArrayList<Integer>(v);
		
		// O(V+E)
		while (!q.isEmpty()) {
			// extract front of the queue
			// and add it to the result
			int src = q.poll();
			topOrder.add(src);
			
			// decrease indegress of adjacent nodes by one and 
			// an adjacent node to queue if it's indegree = 0
			for (int i = 0; i < adjList[src].size(); i++) {
				int dest = adjList[src].get(i);
				indegree[dest]--;
				if (indegree[dest] == 0)
					q.add(dest);
			}
			cnt++;
		}
		
		// check for cycle in the graph
		// if graph has cycle, then count of visited nodes will be lesser than
		// the total number of nodes in graph, because we will reach a point
		// where there will be unvisited nodes left in the graph with more than 0
		// indegrees and queue will become empty without visiting those nodes 
		// but if graph doesn't have cycle then count of visited nodes
		// will be equal to number of nodes in the graph
		if (cnt != v)
			throw new IllegalStateException("Graph is not a DAG");
		
		// print topological ordering
		for (int i = 0; i < topOrder.size(); i++)
			System.out.print(topOrder.get(i) + " ");
	}
	
}

public class TopologicalSortTest2 {
	public static void main(String[] args) {
		Graph4 g = new Graph4(6);
        g.addEdge(5, 2);
        g.addEdge(5, 0);
        g.addEdge(4, 0);
        g.addEdge(4, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);
        
        g.kahnsTopSort(); // 4 5 2 0 3 1 
	}
}
