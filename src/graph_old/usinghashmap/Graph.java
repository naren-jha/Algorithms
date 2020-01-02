package graph_old.usinghashmap;
import java.util.*;

/**
 * @author Narendra Jha
 * 
 * Adjacency list representation of an undirected Graph 
 * using Java HashMap
 */
class Graph {
	
	private HashMap<Integer, List<Integer>> adjList;
	// private HashMap<Integer, Node> adjList;
	
	// constructor
	public Graph() {
		adjList = new HashMap<Integer, List<Integer>>();
	}
	
	// adds an undirected edge from src to dest 
	public void addEdge(int src, int dest) {
		if (adjList.containsKey(src)) {
			// existing vertex found
			// update linked list for that vertex
			adjList.get(src).add(dest);
		}
		else {
			// existing vertex not found
			// create new vertex and add first node to it.
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(dest);
			adjList.put(src, list);
		}
	}
	
	// removes an undirected edge from src to dest 
	public void removeEdge(int src , int dest) {
		if (!adjList.containsKey(src))
			throw new IllegalArgumentException("Vertex not found in graph");

		Iterator<Integer> itr = adjList.get(src).iterator();
		while (itr.hasNext()) {
			if (itr.next() == dest) {
				itr.remove();
				break;
			}
		}
	}
	
	// checks if there is an edge from source to destination
	public boolean isNeighbour(int src , int dest) {
		if (!adjList.containsKey(src)) {
			return false;
		}
		
		for (Integer i : adjList.get(src)) {
			if (i == dest)
				return true;
		}
		return false;
	}
	
	// returns list of neighbors for a given vertex
	public List<Integer> neighborsOf(int src) {
		if (!adjList.containsKey(src))
			throw new IllegalArgumentException("Vertex not found in graph");
		
		return adjList.get(src);
	}
	
	// a utility method to print the graph 
	public void printGraph() {
		for (Integer i : adjList.keySet()) {
			List<Integer> list = adjList.get(i);
			System.out.println("Vertex" + i + " ---> " + list);
		}
	}
	
	public static void main(String args[]) {
		Graph g = new Graph();
		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(1, 2);
		g.printGraph();
		/* Output:
		 * 	Vertex0 ---> [1, 2]
		 * 	Vertex1 ---> [2]
		 */
		
		System.out.println();
		System.out.println(g.isNeighbour(0, 2)); // true
		System.out.println(g.isNeighbour(1, 0)); // false
		
		System.out.println();
		System.out.println(g.neighborsOf(0)); // [1, 2]
		
		System.out.println();
		g.removeEdge(0, 2);
		g.printGraph();
		/* Output:
		 * 	Vertex0 ---> [1]
		 * 	Vertex1 ---> [2]
		 */
	}
	
}
