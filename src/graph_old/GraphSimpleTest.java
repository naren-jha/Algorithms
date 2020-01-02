package graph_old;

import java.util.LinkedList;

/**
 * @author Narendra Jha
 * 
 * A simpler implementation of adjacency list 
 * representation of Graph using Java bulit-in linked list
 */

class GraphSimple {
	private int v; // no. of vertices
	private LinkedList<Integer>[] adjList; // adjacency lists
	
	// constructor
	GraphSimple(int v) {
		this.v = v;
		
		// assign array of linked list
		adjList = (LinkedList<Integer>[]) new LinkedList<?>[v];
		
		// create a new list for each vertex
        // to store adjacent nodes
		for (int i = 0; i < v; i++)
			adjList[i] = new LinkedList<Integer>();
	}
	
	// An overloaded method to add undirected edge by default
    public void addEdge(int src, int dest) {
    	addEdge(src, dest, true);
	}
	
    // An overloaded method to add an undirected or a directed edge in graph
	public void addEdge(int src, int dest, boolean isUndirected) {
		// add an edge from src to dest
		adjList[src].addFirst(dest);
		
		// if graph is undirected, add an edge from dest to src as well
		if (isUndirected)
			adjList[dest].addFirst(src);
	}
	
	// An overloaded method to remove undirected edge by default
    public void removeEdge(int src, int dest) {
    	removeEdge(src, dest, true);
	}
	
    // An overloaded method to remove an undirected or a directed edge in graph
	public void removeEdge(int src, int dest, boolean isUndirected) {
		// remove edge from src to dest
		adjList[src].remove(new Integer(dest));
		
		// if graph is undirected, remove edge from dest to src as well
		if (isUndirected)
			adjList[dest].remove(new Integer(src));
	}
	
	// Checks if there is an edge from source to destination
	public boolean isNeighbor(int src, int dest) {
		for (Integer node : adjList[src]) {
			if (node == dest)
				return true;
		}
		return false;
	}
	
	
	// Returns list of neighbors for a given vertex
	public LinkedList<Integer> neighborsOf(int src) {
		return adjList[src];
	}
	
	// A utility method to print the graph 
    public void print() {       
        for(int i = 0; i < v; i++) {
            System.out.print("Vertex" + i);
            for (Integer n : adjList[i]) {
                System.out.print(" -> " + n);
            }
            System.out.println("\n");
        }
    }
}


public class GraphSimpleTest {

	public static void main(String[] args) {
		// create a graph 5 vertices
		GraphSimple g = new GraphSimple(5);
		
		// add undirected edges
        g.addEdge(0, 1);
        g.addEdge(0, 4);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
      
        g.print();
        /* Output:
         
         	Vertex0 -> 4 -> 1

			Vertex1 -> 4 -> 3 -> 2 -> 0
			
			Vertex2 -> 3 -> 1
			
			Vertex3 -> 4 -> 2 -> 1
			
			Vertex4 -> 3 -> 1 -> 0
         */
        
        System.out.println("");
        System.out.println(g.isNeighbor(1, 2)); // true
        System.out.println(g.isNeighbor(2, 0)); // false
        
        System.out.println("\n");
        System.out.print(g.neighborsOf(1)); // [4, 3, 2, 0]
        
        System.out.println("\n");
        g.removeEdge(1, 3);
        g.removeEdge(4, 0);
        g.print();
        /* Output after removing edges:
         
         	Vertex0 -> 1

			Vertex1 -> 4 -> 2 -> 0
			
			Vertex2 -> 3 -> 1
			
			Vertex3 -> 4 -> 2
			
			Vertex4 -> 3 -> 1
         */
	}

}