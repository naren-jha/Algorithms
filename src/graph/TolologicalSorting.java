package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class TolologicalSorting extends Graph {
    
    public TolologicalSorting(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    public void topologicalSort() {
        Stack<Integer> stack = new Stack<Integer>();
        boolean[] visited = new boolean[numberOfVertices];
        
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (!visited[vn])
                topologicalSortUtil(vn, visited, stack);
        }
        
        while (!stack.isEmpty())
            System.out.print(stack.pop() + " ");
    }

    private void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        
        for (Integer adjNode : adjList.get(v)) {
            if (!visited[adjNode])
                topologicalSortUtil(adjNode, visited, stack);
        }
        
        stack.push(v);
    }
    
    /* Approach 2: Kahn's algorithm for topological sorting */
    public void kahnsTopSort() {
        // calculate indegrees of all nodes: O(V+E)
        int[] indegree = new int[numberOfVertices];
        for (int vn = 0; vn < numberOfVertices; ++vn)
            for (Integer dest : adjList.get(vn))
                indegree[dest]++;
        
        // enqueue all nodes with 0 indegrees to a queue
        Queue<Integer> q = new LinkedList<Integer>();
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (indegree[vn] == 0)
                q.add(vn);
        }
        
        int cnt = 0; 
        List<Integer> topOrder = new ArrayList<Integer>(numberOfVertices);
        while (!q.isEmpty()) { // O(V+E)
            int v = q.poll();
            topOrder.add(v);
            
            for (Integer dest : adjList.get(v)) {
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
        if (cnt != numberOfVertices)
            throw new IllegalStateException("Graph is not a DAG");
        
        // print topological ordering
        for (Integer e : topOrder)
            System.out.print(e + " ");
    }
    
    /* All topological sorting of a DAG */
    public void allTopologicalSort() {
        boolean[] visited = new boolean[numberOfVertices];
        
        int[] indegree = new int[numberOfVertices];
        for (int vn = 0; vn < numberOfVertices; ++vn)
            for (Integer dest : adjList.get(vn))
                indegree[dest]++;
        
        List<Integer> result = new ArrayList<Integer>();
        allTopologicalSortUtil(visited, indegree, result);
    }

    private void allTopologicalSortUtil(boolean[] visited, int[] indegree, List<Integer> result) {
        boolean flag = false;
        for (int vn = 0; vn < numberOfVertices; ++vn) {
            if (indegree[vn] == 0 && !visited[vn]) {
                visited[vn] = true;
                result.add(vn);
                for (Integer dest : adjList.get(vn))
                    indegree[dest]--;
                
                allTopologicalSortUtil(visited, indegree, result);
                
                visited[vn] = false;
                result.remove(result.size()-1);
                for (Integer dest : adjList.get(vn))
                    indegree[dest]++;
                flag = true;
            }
        }
        
        if (!flag) {
            for (Integer e : result)
                System.out.print(e + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TolologicalSorting graph = new TolologicalSorting(6);
        graph.addEdge(5, 2, false);
        graph.addEdge(5, 0, false);
        graph.addEdge(4, 0, false);
        graph.addEdge(4, 1, false);
        graph.addEdge(2, 3, false);
        graph.addEdge(3, 1, false);
        
        graph.topologicalSort(); // 5 4 2 3 1 0 
        System.out.println();
        
        graph.kahnsTopSort(); // 4 5 0 2 3 1 
        System.out.println();
        
        graph.allTopologicalSort();
        /*
         * 4 5 0 2 3 1 
         * 4 5 2 0 3 1 
         * 4 5 2 3 0 1 
         * 4 5 2 3 1 0 
         * 5 2 3 4 0 1 
         * 5 2 3 4 1 0 
         * 5 2 4 0 3 1 
         * 5 2 4 3 0 1 
         * 5 2 4 3 1 0 
         * 5 4 0 2 3 1 
         * 5 4 2 0 3 1 
         * 5 4 2 3 0 1 
         * 5 4 2 3 1 0 
         */
    }
}
