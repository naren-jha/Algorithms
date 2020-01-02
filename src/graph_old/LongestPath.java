package graph_old;

import java.util.Stack;

/**
 * @author Narendra Jha
 * 
 * Longest distances from a source vertex ‘s’ 
 * to all other vertices in a graph
 */
class Graph6 {

	// template for weighted node of linked list in graph
	private class Node {
		int vertexNum;
		int weight;
		Node next;
		
		Node(int vNum, int weight, Node next) {
			this.vertexNum = vNum;
			this.weight = weight;
			this.next = next;
		}
	}
	
	// template for vertex of graph
	private class Vertex {
		Node head; // head of linked list
		
		Vertex(Node head) {
			this.head = head;
		}
	}
	
	private int v; // no. of vertices
	private Vertex[] vertices; // array of vertices - adjacency list

	public Graph6(int v) {
		this.v = v;
		// create graph with 'v' number of vertices
		vertices = new Vertex[v];
		
		// assign Vertex objects
		for (int i = 0; i < v; i++) {
			vertices[i] = new Vertex(null);
		}
	}

    // method to add a directed edge in graph
	public void addEdge(int src, int dest, int weight) {
		// add an edge from src to dest
		Node node = new Node(dest, weight, vertices[src].head);
		vertices[src].head = node; // add at the head of src
	}
	
	// a recursive method used by longestPath
	private void topologicalSortUtil(int vertexNum, boolean[] visited, 
			Stack<Integer> stack) {
		
		// mark current node as visited
		visited[vertexNum] = true;
		
		// loop through all the vertices adjacent to this vertex
		for (Node head = vertices[vertexNum].head; head != null; 
				head = head.next) {
			int adj = head.vertexNum;
			if (!visited[adj])
				topologicalSortUtil(adj, visited, stack);
		}
		
		// done with all the adjacent vertices
		// push current node to stack
		stack.push(vertexNum);
	}
	
	// method to find longest distances from a given source vertex
	// it uses recursive topologicalSortUtil() to get topological sorting
	public void longestPath(int s) {
		// initialize distances to all vertices as minus infinity
		// and distance to source vertex as 0
		int[] dist = new int[v];
		for (int i = 0; i < v; i++)
			dist[i] = Integer.MIN_VALUE;
		dist[s] = 0;
		
		/* creating topological order of all vertices - start - */
		Stack<Integer> stack = new Stack<Integer>();
		
		// mark all the vertices as not visited
		boolean[] visited = new boolean[v];
		
		// call recursive helper method to store 
		// topological sort in stack, starting from all vertices one by one
		for (int i = 0; i < v; i++) {
			if (!visited[i])
				topologicalSortUtil(i, visited, stack);
		}
		/* creating topological order of all vertices - end - */
		
		// process vertices in topological order
		while (!stack.isEmpty()) {
			// get next vertex in topological order
			int u = stack.pop();
			
			if (dist[u] != Integer.MIN_VALUE) {
				// update distances of all adjacent vertices
				for (Node head = vertices[u].head; head != null; 
						head = head.next) {
					int v = head.vertexNum;
					if (dist[v] < dist[u] + head.weight)
						dist[v] = dist[u] + head.weight;
				}
			}
		}
		
		for (int i = 0; i < v; i++) {
			if (dist[i] == Integer.MIN_VALUE)
				System.out.print("Unreachable ");
			else
				System.out.print(dist[i] + " ");
		}
	}
	
}

public class LongestPath {
	public static void main(String[] args) {
		Graph6 g = new Graph6(6);
		// Create a graph as in diagram. 
	    // Here vertex numbers are 0, 1, 2, 3, 4, 5 with 
	    // following mappings:
	    // 0=r, 1=s, 2=t, 3=x, 4=y, 5=z
	    g.addEdge(0, 1, 5);
	    g.addEdge(0, 2, 3);
	    g.addEdge(1, 3, 6);
	    g.addEdge(1, 2, 2);
	    g.addEdge(2, 4, 4);
	    g.addEdge(2, 5, 2);
	    g.addEdge(2, 3, 7);
	    g.addEdge(3, 5, 1);
	    g.addEdge(3, 4, -1);
	    g.addEdge(4, 5, -2);
	    
	    int s = 1;
	    g.longestPath(s); // Unreachable 0 2 9 8 10 
	}
}
