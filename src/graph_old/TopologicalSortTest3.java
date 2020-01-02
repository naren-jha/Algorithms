package graph_old;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Narendra Jha
 * 
 * All topological sorting
 */
class Graph5 {
	private int v; // no. of vertices
	private LinkedList<Integer>[] adjList; // adjacency lists
	private int[] indegree; // indegree of vertices
	
	// constructor
	Graph5(int v) {
		this.v = v;
		
		// assign array of linked list
		adjList = (LinkedList<Integer>[]) new LinkedList<?>[v];
		
		// create a new list for each vertex
        // to store adjacent nodes
		for (int i = 0; i < v; i++)
			adjList[i] = new LinkedList<Integer>();
		
		indegree = new int[v]; // all 0's by default
	}
	
	// method to add a directed edge to the graph
	public void addEdge(int src, int dest) {
		adjList[src].add(dest); // Add dest to src's list
		
		// increasing indegree of dest by 1
	    indegree[dest]++;
	}
	
	// recursive method to print all possible topological sort
	private void allTopologicalSortUtil(List<Integer> res, boolean[] visited) {
		boolean flag = false; // indicates completion of a topological sort
		
		for (int i = 0; i < v; i++) {
			
			// if unvisited vertex with 0 indegree
			if (indegree[i] == 0 && !visited[i]) {
				
				// reduce indegree of adjacent vertices
				for (int j = 0; j < adjList[i].size(); j++) {
					int dest = adjList[i].get(j);
					indegree[dest]--;
				}
				
				// add to result, mark visited, and recurse
				res.add(i);
				visited[i] = true;
				allTopologicalSortUtil(res, visited);
				
				// reset visited, res and indegree for backtracking
				visited[i] = false;
				res.remove(res.size() - 1);
				for (int j = 0; j < adjList[i].size(); j++) {
					int dest = adjList[i].get(j);
					indegree[dest]++;
				}
	 
	            flag = true;
			}
		}
		
		if (!flag) {
			// print a topological sort
			for (int i = 0; i < res.size(); i++)
				System.out.print(res.get(i) + " ");
			
			System.out.println(); // new line for next topological sort
		}
	}
	
	// method to print all topological sort
	// it uses recursive allTopologicalSortUtil() method
	public void allTopologicalSort() {
		// mark all the vertices as not visited (is set as
        // false by default in java)
		boolean[] visited = new boolean[v];
		
		// list to store result
		List<Integer> res = new ArrayList<Integer>(v);
		allTopologicalSortUtil(res, visited);
	}
	
}

public class TopologicalSortTest3 {
	public static void main(String[] args) {
		Graph5 g = new Graph5(6);
        g.addEdge(5, 2);
        g.addEdge(5, 0);
        g.addEdge(4, 0);
        g.addEdge(4, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);
        
        g.allTopologicalSort();
        /* Output:
         *  4 5 0 2 3 1 
			4 5 2 0 3 1 
			4 5 2 3 0 1 
			4 5 2 3 1 0 
			5 2 3 4 0 1 
			5 2 3 4 1 0 
			5 2 4 0 3 1 
			5 2 4 3 0 1 
			5 2 4 3 1 0 
			5 4 0 2 3 1 
			5 4 2 0 3 1 
			5 4 2 3 0 1 
			5 4 2 3 1 0
         */
	}
}
