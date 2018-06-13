package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Narendra Jha
 * 
 * Adjacency list representation of directed or undirected graph
 */
class Graph {
	
	// template for node of linked list in graph
	private class Node {
		int vertexNum;
		Node next;
		
		Node(int vNum, Node next) {
			this.vertexNum = vNum;
			this.next = next;
		}
	}

	// template for vertex of graph
	private class Vertex {
		String name; // name of vertex
		Node adjList; // head of linked list
		
		Vertex(String name, Node head) {
			this.name = name;
			this.adjList = head;
		}
	}
	
	private Vertex[] vertices; // array of vertices

	public Graph(int v, String[] names) {
		// create graph with 'v' number of vertices
		vertices = new Vertex[v];
		
		// for each vertex, assign their names  
		// and mark corresponding linked lists empty
		for (int i = 0; i < v; i++) {
			vertices[i] = new Vertex(names[i], null);
		}
	}
	
	// An overloaded method to add undirected edge by default
	public void addEdge(String src, String dest) {
    	addEdge(src, dest, true);
	}

    // An overloaded method to add an undirected or a directed edge in graph
	public void addEdge(String src, String dest, boolean isUndirected) {
		// get indices for vertex names
		int srcNum = getIndexByName(src);
		int destNum = getIndexByName(dest);
		if (srcNum == -1 || destNum == -1)
			throw new IllegalArgumentException("Vertex not found in graph");
		
		// add an edge from src to dest
		Node node = new Node(destNum, vertices[srcNum].adjList);
		vertices[srcNum].adjList = node; // add at the head 
		
		// if graph is undirected, add an edge from dest to src as well
		if (isUndirected) {
			node = new Node(srcNum, vertices[destNum].adjList);
			vertices[destNum].adjList = node; // add at the head
		}
	}
	
	// An overloaded method to remove undirected edge by default
	public void removeEdge(String src, String dest) {
		removeEdge(src, dest, true);
	}
	
	// An overloaded method to remove an undirected or a directed edge in graph
	public void removeEdge(String src, String dest, boolean isUndirected) {
		// get indices for vertex names
		int srcNum = getIndexByName(src);
		int destNum = getIndexByName(dest);
		if (srcNum == -1 || destNum == -1)
			throw new IllegalArgumentException("Vertex not found in graph");
		
		// remove edge from src to dest
		Node head = vertices[srcNum].adjList;
		Node prev = null;
		while (head != null) {
			if(vertices[head.vertexNum].name.equals(dest)) {
				// edge found, delete it and exit loop
				
				if (prev == null) {
					// target node is the first node of linked list
					vertices[srcNum].adjList = head.next;
				}
				else {
					// target node is not the first node of linked list
					prev.next = head.next;
				}
				break;
			}
			prev = head;
			head = head.next;
		}
		
		// if graph is undirected, remove edge from dest to src as well
		if (isUndirected) {
			// remove edge from src to dest
			head = vertices[destNum].adjList;
			prev = null;
			while (head != null) {
				if(vertices[head.vertexNum].name.equals(src)) {
					// edge found, delete it and exit loop
					
					if (prev == null) {
						// target node is the first node of linked list
						vertices[destNum].adjList = head.next;
					}
					else {
						// target node is not the first node of linked list
						prev.next = head.next;
					}
					break;
				}
				prev = head;
				head = head.next;
			}
		}
	}
	
	// Checks if there is an edge from source to destination
	public boolean isNeighbor(String src, String dest) {
		// get indices for vertex name
		int srcNum = getIndexByName(src);
		if (srcNum == -1)
			throw new IllegalArgumentException("Vertex not found in graph");
		
		for (Node head = vertices[srcNum].adjList; head != null; 
				head = head.next) {
			if(vertices[head.vertexNum].name.equals(dest))
				return true;
		}
		return false;
	}
	
	// Returns list of neighbors for a given vertex
	public List<String> neighborsOf(String vertexName) {
		int vertexNum = getIndexByName(vertexName);
		if (vertexNum == -1)
			throw new IllegalArgumentException("Vertex not found in graph");
		
		// loop through the linked list for given vertex
		List<String> neighbors = new ArrayList<String>();
		for (Node head = vertices[vertexNum].adjList; head != null; 
				head = head.next) {
			neighbors.add(vertices[head.vertexNum].name);
		}
		
		return neighbors;
	}
	
	// A helper method to get vertex index by vertex name
	private int getIndexByName(String name) {
		// we can avoid linear search by using hash map
		for (int v = 0; v < vertices.length; v++) {
			if (vertices[v].name.equals(name)) {
				return v;
			}
		}
		return -1;
	}
	
	// A utility method to print the graph 
	public void print() {
		for (int v = 0; v < vertices.length; v++) {
			System.out.print(vertices[v].name);
			for (Node n = vertices[v].adjList; n != null; n = n.next) {
				System.out.print(" --> " + vertices[n.vertexNum].name);
			}
			System.out.println("\n");
		}
	}
}

public class GraphTest {

	public static void main(String[] args) {
		
		// Undirected graph test: An example graph of a social network
		String[] users = {"Sara", "Sam", "Sean", "Ajay", "Mira", "Jane", 
				             "Maria", "Rahul", "Sapna", "Rohit"};
		Graph graph = new Graph(10, users);
		graph.addEdge("Sara", "Sam");
		graph.addEdge("Sara", "Ajay");
		graph.addEdge("Sam", "Sean");
		graph.addEdge("Sam", "Mira");
		graph.addEdge("Mira", "Jane");
		graph.addEdge("Jane", "Maria");
		graph.addEdge("Rahul", "Sapna");
		graph.addEdge("Sapna", "Rohit");
		
		graph.print();
		/*  Below is the output
		 
			Sara --> Ajay --> Sam

			Sam --> Mira --> Sean --> Sara
			
			Sean --> Sam
			
			Ajay --> Sara
			
			Mira --> Jane --> Sam
			
			Jane --> Maria --> Mira
			
			Maria --> Jane
			
			Rahul --> Sapna
			
			Sapna --> Rohit --> Rahul
			
			Rohit --> Sapna
		 */
		
		List<String> neighborsOfSam = graph.neighborsOf("Sam");
		for (String neighbor : neighborsOfSam)
			System.out.print(neighbor + " "); // Mira Sean Sara
		
		System.out.println("\n");		
		System.out.println(graph.isNeighbor("Sara", "Sam")); // true
		System.out.println(graph.isNeighbor("Sara", "Jane")); // false
		
		System.out.println();
		graph.removeEdge("Sam", "Sean");
		graph.removeEdge("Ajay", "Sara");
		graph.print();
		/*  Below is the output after removing edges
		 
			Sara --> Sam

			Sam --> Mira --> Sara
			
			Sean
			
			Ajay
			
			Mira --> Jane --> Sam
			
			Jane --> Maria --> Mira
			
			Maria --> Jane
			
			Rahul --> Sapna
			
			Sapna --> Rohit --> Rahul
			
			Rohit --> Sapna
		 */
		
		System.out.println("\n");
		
		// Directed graph test: An example graph of world wide web
		String[] pages = {"PageA", "PageB", "PageC", "PageD", 
				"PageE", "PageF"};
		graph = new Graph(6, pages);
		graph.addEdge("PageA", "PageB", false);
		graph.addEdge("PageA", "PageD", false);
		graph.addEdge("PageA", "PageE", false);
		graph.addEdge("PageB", "PageD", false);
		graph.addEdge("PageC", "PageA", false);
		graph.addEdge("PageD", "PageB", false);
		graph.addEdge("PageE", "PageF", false);
		graph.addEdge("PageF", "PageD", false);
		
		graph.print();
		/*  Below is the output
		 
			PageA --> PageE --> PageD --> PageB

			PageB --> PageD
			
			PageC --> PageA
			
			PageD --> PageB
			
			PageE --> PageF
			
			PageF --> PageD
		 */
		
		List<String> pagesFromPageA = graph.neighborsOf("PageA");
		for (String page : pagesFromPageA)
			System.out.print(page + " "); // PageE PageD PageB
		
		System.out.println("\n");		
		System.out.println(graph.isNeighbor("PageA", "PageB")); // true
		System.out.println(graph.isNeighbor("PageA", "PageF")); // false
		
		System.out.println();
		graph.removeEdge("PageA", "PageD", false);
		graph.removeEdge("PageE", "PageF", false);
		graph.print();
		/*  Below is the output after removing edges
		 
		PageA --> PageE --> PageB

		PageB --> PageD
		
		PageC --> PageA
		
		PageD --> PageB
		
		PageE
		
		PageF --> PageD
	 */
	}

}