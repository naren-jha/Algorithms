package graph.networkflow;

import java.awt.geom.Point2D;

/**
 * Problem Statement:
 * Suppose there are M mice out on a field and there is a hungry owl about to make a move. 
 * Assume that the owl can reach every single one of these mice. Further suppose that 
 * there are H holes scattered across the ground, and that each hole has a certain 
 * capacity for the number of mice that can hide in it. Every Mouse is capable of 
 * running a distance of R in any direction before being caught by the owl. The question 
 * asks what is the maximum number of mice that can hide safely before being caught?
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class MiceAndOwls {
    
    public static class Mouse {
        Point2D loc;
        
        public Mouse(int x, int y) {
            this.loc = new Point2D.Double(x, y);
        }
    }
    
    public static class Hole {
        Point2D loc;
        int capacity;
        
        public Hole(int x, int y, int capacity) {
            this.loc = new Point2D.Double(x, y);;
            this.capacity = capacity;
        }
    }
    
    public long solve(Mouse[] mice, Hole[] holes, double radius) {
        if (mice == null || holes == null) return -1;
        
        int m = mice.length;
        int h = holes.length;
        int N = m + h + 2;
        int s = N - 1; int t = N - 2;
        NetworkFlowBase ff = new FordFulkersonDfsAdjacencyList(N, s, t);
        
        // Hook up edges from source to mice
        for (int i = 0; i < m; ++i)
            ff.addEdge(s, i, 1);
        
        // Hook up edges from mice to holes
        for (int i = 0; i < m; ++i) {
            Point2D mouseLoc = mice[i].loc;
            for (int j = 0; j < h; ++j) {
                Point2D holeLoc = holes[j].loc;
                if (mouseLoc.distance(holeLoc) <= radius) {
                    ff.addEdge(i, m + j, 1);
                }
            }
        }
        
        // Hook up edges from holes to sink
        for (int j = 0; j < h; ++j)
            ff.addEdge(m + j, t, holes[j].capacity);
        
        return ff.getMaxFlow();
    }
    
    public static void main(String[] args) {
        Mouse[] mice = {
            new Mouse(1, 0),
            new Mouse(0, 1),
            new Mouse(8, 1),
            new Mouse(12, 0),
            new Mouse(12, 4),
            new Mouse(15, 5)
        };
        
        Hole[] holes = {
            new Hole(1, 1, 1), 
            new Hole(10, 2, 2), 
            new Hole(14, 5, 1)
        };
        
        MiceAndOwls solver = new MiceAndOwls();
        long res = solver.solve(mice, holes, 3);
        System.out.println("Number of safe mice = " + res);
        // Output: Number of safe mice = 4
    }

}
