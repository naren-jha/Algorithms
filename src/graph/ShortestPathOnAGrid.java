package graph;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Shortest Path On A Grid
 * 
 * https://youtu.be/09_LlHjoEiY?t=2426
 * https://www.geeksforgeeks.org/shortest-distance-two-cells-matrix-grid/
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class ShortestPathOnAGrid {
    
    // direction vectors
    private static final int[] dr = {-1, +1, 0, 0}; // direction row
    private static final int[] dc = {0, 0, -1, +1}; // direction column
    
    private static final char BLOCKED = '#'; // block symbol
    private static final char END = 'E'; // end symbol
    
    public int shortestPath(char[][] grid, int sr, int sc) {
        int R = grid.length;
        if (R == 0) 
            throw new IllegalStateException("empty grid");
        int C = grid[0].length;
        
        boolean[][] visited = new boolean[R][C];
        Queue<Integer> rq = new ArrayDeque<Integer>(); // row queue
        Queue<Integer> cq = new ArrayDeque<Integer>(); // column queue
        
        rq.add(sr);
        cq.add(sc);
        visited[sr][sc] = true;
        
        int moveCount = 0;
        int nodesLeftInLayer = 1;
        int nodesInNextLayer = 0;
        boolean reachedEnd = false;
        while (!rq.isEmpty() && !cq.isEmpty()) {
            int r = rq.poll();
            int c = cq.poll();
            
            if (grid[r][c] == END) {
                reachedEnd = true;
                break;
            }
             
            nodesInNextLayer = exploreNeighbours(grid, R, C, visited, rq, cq, r, c, nodesInNextLayer);
            
            --nodesLeftInLayer;
            if (nodesLeftInLayer == 0) {
                nodesLeftInLayer = nodesInNextLayer;
                nodesInNextLayer = 0;
                ++moveCount;
            }
        }
        
        return reachedEnd ? moveCount : -1;
    }

    private int exploreNeighbours(char[][] grid, int R, int C, boolean[][] visited, Queue<Integer> rq,
            Queue<Integer> cq, int r, int c, int nodesInNextLayer) {
        for (int i = 0; i < dr.length; i++) {
            int rr = r + dr[i];
            int cc = c + dc[i];
            
            // Skip out of bounds locations
            if (rr < 0 || rr >= R)
                continue;
            if (cc < 0 || cc >= C)
                continue;
            
            // Skip visited locations or blocked cells
            if (visited[rr][cc] || grid[rr][cc] == BLOCKED)
                continue;
            
            rq.add(rr);
            cq.add(cc);
            visited[rr][cc] = true;
            ++nodesInNextLayer;
        }
        
        return nodesInNextLayer;
    }
    
    // Second Approach: Use a delimiter in queue to identify next layer
    public int shortestPathA2(char[][] grid, int sr, int sc) {
        int R = grid.length;
        if (R == 0) 
            throw new IllegalStateException("empty grid");
        int C = grid[0].length;
        
        boolean[][] visited = new boolean[R][C];
        // Lets use LinkedList implementation of queue as ArrayDeque
        // implementation does not allow null insertions
        Queue<Integer> rq = new LinkedList<Integer>(); // row queue
        Queue<Integer> cq = new LinkedList<Integer>(); // column queue
        
        rq.add(sr);
        cq.add(sc);
        visited[sr][sc] = true;
        
        rq.add(null);
        cq.add(null);
        
        int moveCount = 0;
        boolean reachedEnd = false;
        
        while (!rq.isEmpty() && !cq.isEmpty()) {
            Integer r = rq.poll();
            Integer c = cq.poll();
            
            if (r == null || c == null) {
                ++moveCount;
                rq.add(null);
                cq.add(null);
                continue;
            }
            
            if (grid[r][c] == END) {
                reachedEnd = true;
                break;
            }
             
            exploreNeighboursA2(grid, R, C, visited, rq, cq, r, c);
        }
        
        return reachedEnd ? moveCount : -1;
    }
    
    private void exploreNeighboursA2(char[][] grid, int R, int C, boolean[][] visited, 
            Queue<Integer> rq, Queue<Integer> cq, int r, int c) {
        for (int i = 0; i < dr.length; i++) {
            int rr = r + dr[i];
            int cc = c + dc[i];
            
            // Skip out of bounds locations
            if (rr < 0 || rr >= R)
                continue;
            if (cc < 0 || cc >= C)
                continue;
            
            // Skip visited locations or blocked cells
            if (visited[rr][cc] || grid[rr][cc] == BLOCKED)
                continue;
            
            rq.add(rr);
            cq.add(cc);
            visited[rr][cc] = true;
        }
    }
    
    public static void main(String[] args) {
        char[][] grid = { { 'S', '.', '.', '.' }, 
                          { '.', '#', '.', '.' }, 
                          { '#', '.', '#', '.' }, 
                          { '.', 'E', '.', '.' } 
                        };
        ShortestPathOnAGrid graph = new ShortestPathOnAGrid();
        int shortestPath = graph.shortestPath(grid, 0, 0);
        System.out.println(shortestPath); // 8
        
        shortestPath = graph.shortestPathA2(grid, 0, 0);
        System.out.println(shortestPath); // 8
    }
}
