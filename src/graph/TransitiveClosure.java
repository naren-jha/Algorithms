package graph;

import java.util.LinkedList;

/**
 * @author Narendra Jha
 * 
 */
class Graph7 {
	private int v; // no. of vertices
	private LinkedList<Integer>[] adjList; // adjacency lists
	
	// constructor
	Graph7(int v) {
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
	
	// A recursive DFS traversal method that finds
	// all reachable vertices from s.
	private void DFSUtil(int s,int v, boolean[][] tc) {
		// Mark reachability from s to v as true.
		tc[s][v] = true;
		
		// loop through adjacent vertices of vertex v
		// to find all vertices reachable through v
		for (int i = 0; i < adjList[v].size(); i++) {
			int dest = adjList[v].get(i);
			if (!tc[s][dest])
				DFSUtil(s, dest, tc);
		}
	}
	
	// method to find transitive closure of a graph
	// it uses recursive DFSUtil() method
	public void transitiveClosure() {
		boolean[][] tc = new boolean[v][v];
		
		// call DFS for each vertex,
		// to find reachable noodes
		for (int i = 0; i < v; i++)
			DFSUtil(i, i, tc); // every vertex is reachable from self
		
		// print result
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++)
				System.out.print(tc[i][j] ? "1 " : "0 ");
			System.out.println();
		}
	}
	
}

public class TransitiveClosure {
	public static void main(String[] args) {
		Graph7 g = new Graph7(4);
		g.addEdge(0, 1);
	    g.addEdge(0, 2);
	    g.addEdge(1, 2);
	    g.addEdge(2, 0);
	    g.addEdge(2, 3);
	    g.addEdge(3, 3);
	    
	    g.transitiveClosure();
	    /*
	     * Output:
	     *  1 1 1 1 
			1 1 1 1 
			1 1 1 1 
			0 0 0 1 
	     */
	}
}
