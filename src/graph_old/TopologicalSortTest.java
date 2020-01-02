package graph_old;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @author Narendra Jha
 * 
 * Topological sorting using Depth-First Search based technique
 */
class Graph3 {
	private int v; // no. of vertices
	private LinkedList<Integer>[] adjList; // adjacency lists
	
	// constructor
	Graph3(int v) {
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
	
	// a recursive helper method used by topological sort
	private void topologicalSortUtil(int vertexNum, boolean[] visited, 
			Stack<Integer> stack) {
		
		// mark current node as visited
		visited[vertexNum] = true;
		
		// loop through all the vertices adjacent to this vertex
		for (int i = 0; i < adjList[vertexNum].size(); i++) {
			int adj = adjList[vertexNum].get(i);
			if (!visited[adj])
				topologicalSortUtil(adj, visited, stack);
		}
		
		// done with all the adjacent vertices
		// push current node to stack
		stack.push(vertexNum);
	}
	
	// method to do topological sort
	public void topologicalSort() {
		Stack<Integer> stack = new Stack<Integer>();
		
		// mark all the vertices as not visited (is set as
        // false by default in java)
		boolean[] visited = new boolean[v];
		
		// call recursive helper method to store 
		// topological sort in stack,
		// starting from all vertices one by one
		for (int i = 0; i < v; i++) {
			if (!visited[i])
				topologicalSortUtil(i, visited, stack);
		}
		
		// print result
		while (!stack.isEmpty())
			System.out.print(stack.pop() + " ");
	}
	
}

public class TopologicalSortTest {
	public static void main(String[] args) {
		Graph3 g = new Graph3(6);
        g.addEdge(5, 2);
        g.addEdge(5, 0);
        g.addEdge(4, 0);
        g.addEdge(4, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);
        
        g.topologicalSort(); // 5 4 2 3 1 0 
	}
}
