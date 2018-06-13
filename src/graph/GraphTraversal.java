package graph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Narendra Jha
 *
 */
class Graph2 {
	private int v; // no. of vertices
	private LinkedList<Integer>[] adjList; // adjacency lists
	
	// constructor
	Graph2(int v) {
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
	
	// prints BFS from a given source vertex 's'
	public void BFS(int s) {
		boolean[] visited = new boolean[v];
		Queue<Integer> q = new LinkedList<Integer>();
		
		q.add(s);
		visited[s] = true;
		while (!q.isEmpty()) {
			// dequeue vertex from front of queue and print it
			s = q.poll();
			System.out.print(s + " ");
			
			// Loop through adjacent vertices of dequeued vertex 's'
			// If an adjacent vertex has not been visited, then mark it
			// visited and enqueue it.
			for (Integer n : adjList[s]) {
				if (!visited[n]) {
					visited[n] = true;
					q.add(n);
				}
			}
		}
		System.out.println(); // new line after result
	}
	
	// prints BFS of complete graph
	public void BFS() {
		boolean[] visited = new boolean[v];
		Queue<Integer> q = new LinkedList<Integer>();
		
		// Do BFS starting from all vertices one by one
		for (int i = 0; i < v; i++) {
			if (!visited[i]) {
				q.add(i);
				visited[i] = true;
				while (!q.isEmpty()) {
					// dequeue vertex from front of queue and print it
					int j = q.poll();
					System.out.print(j + " ");
					
					// Loop through adjacent vertices of dequeued vertex 'j'
					// If an adjacent vertex has not been visited, then mark it
					// visited and enqueue it.
					for (Integer n : adjList[j]) {
						if (!visited[n]) {
							visited[n] = true;
							q.add(n);
						}
					}
				}
			}
		}
		System.out.println(); // new line after result
	}
	
	// Method to do DFS traversal from a source 's'. It uses recursive DFSUtil()
	public void DFS(int s) {
		// Mark all the vertices as not visited(set as
        // false by default in java)
		boolean[] visited = new boolean[v];
		
		// Call the recursive helper function to print DFS traversal
		DFSUtil(s, visited);
		System.out.println(); // new line after result
	}
	
	// Method to do DFS traversal of complete graph. It uses recursive DFSUtil()
	public void DFS() {
		// Mark all the vertices as not visited (is set as
        // false by default in java)
		boolean[] visited = new boolean[v];
		
		// Call the recursive helper function to print DFS traversal
		// starting from all vertices one by one
		for (int i = 0; i < v; i++)
			if (!visited[i])
				DFSUtil(i, visited);
		
		System.out.println(); // new line after result
	}
	
	// A utility method used by DFS() method
	public void DFSUtil(int s, boolean[] visited) {
		// Mark the current node visited and print it
		visited[s] = true;
		System.out.print(s + " ");
		
		// Recur for all the vertices adjacent to this vertex
		for (Integer n : adjList[s]) {
			if (!visited[n])
				DFSUtil(n, visited);
		}
	}
	
	// Iterative method to do DFS traversal of complete graph
	public void DFSItr() {
		boolean[] visited = new boolean[v];
		Stack<Integer> s = new Stack<Integer>();
		
		// Do DFS for all vertices one by one to print all nodes
		// of a disjoint graph. The outer 'for' loop is not needed if 
		// we want to print only the connected nodes
		for (int i = 0; i < v; i++) {
			if (!visited[i]) {
				// start with one node in stack
				s.push(i);
				System.out.print(i + " ");
				visited[i] = true;
				
				while (!s.isEmpty()) {
					int j = s.peek();
					boolean isFound = false;
					for (Integer n : adjList[j]) {
						if (!visited[n]) {
							s.push(n);
							System.out.print(n + " ");
							visited[n] = true;
							isFound = true;
							break;
						}
					}
					
					if (!isFound)
						s.pop();
				}
			}
		}
		System.out.println(); // new line after result
	}
	
	// Another Approach - source: GeeksForGeeks
	// Iterative method to do DFS traversal of a graph
	// Prints all vertices reachable from source 's'
	void DFSItr2(int s) {
	    // Initially mark all vertices as not visited
	    boolean[] visited = new boolean[v];
	    Stack<Integer> st = new Stack<>();
	     
	    // Push the current source node
	    st.push(s);
	     
	    while (!st.isEmpty()) {
	        // Pop a vertex from stack and print it
	        s = st.pop();
	        
	        // Stack may contain same vertex multiple times.
	        // So we need to print the popped item only
	        // if it is not visited.
	        if (!visited[s]) {
	            System.out.print(s + " ");
	            visited[s] = true;
	        }
	         
	        // Get all adjacent vertices of the popped vertex v
	        // If an adjacent vertex has not been visited, then push it
	        // to the stack.
	        for (Integer n : adjList[s]) {
	            if (!visited[n])
	                st.push(n);
	        }
	         
	    }
	    System.out.println(); // new line after result
	}

}

public class GraphTraversal {

	public static void main(String[] args) {
		Graph2 g = new Graph2(4);
		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(1, 2);
		g.addEdge(2, 0);
		g.addEdge(2, 3);
		g.addEdge(3, 3);
		
		g.BFS(2); // 2 0 3 1
		g.DFS(2); // 2 0 1 3
		g.BFS();  // 0 1 2 3 
		g.DFS();  // 0 1 2 3 
		g.DFSItr();  // 0 1 2 3 
		g.DFSItr2(0); // 0 2 3 1
		
		// undirected graph
		Graph2 g2 = new Graph2(6);
		g2.addEdge(0, 1);
		g2.addEdge(0, 2);
		g2.addEdge(1, 0);
		g2.addEdge(1, 3);
		g2.addEdge(1, 4);
		g2.addEdge(2, 0);
		g2.addEdge(2, 4);
		g2.addEdge(3, 1);
		g2.addEdge(3, 4);
		g2.addEdge(3, 5);
		g2.addEdge(4, 1);
		g2.addEdge(4, 2);
		g2.addEdge(4, 3);
		g2.addEdge(4, 5);
		g2.addEdge(5, 3);
		g2.addEdge(5, 4);
		
		g2.BFS();  // 0 1 2 3 4 5
		g2.DFS();  // 0 1 3 4 2 5
		g2.DFSItr();  // 0 1 3 4 2 5
		g2.DFSItr2(0); // 0 2 4 5 3 1
	}

}
