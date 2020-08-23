package dynamicprogramming.basic;

import static java.lang.Math.min;

// https://www.geeksforgeeks.org/assembly-line-scheduling-dp-34/

public class AssemblyLineScheduling {
    
    public int getMinTime(int[][] a, int[][] t, int[] e, int[] x) {
        int n = a[0].length; // no. of stations
        if (n == 0) return 0;
        
        // minimum time required to leave first stations, in two lines
        int time1 = e[0] + a[0][0];
        int time2 = e[1] + a[1][0];
        
        // calculate minimum time required for rest of the stations
        // using bottom up DP approach
        for (int i = 1; i < n; i++) {
            int time1Copy = time1;
            time1 = min(time1 + a[0][i], time2 + t[1][i] + a[0][i]);
            time2 = min(time2 + a[1][i], time1Copy + t[0][i] + a[1][i]);
        }
        
        // add exit times
        time1 = time1 + x[0];
        time2 = time2 + x[1];
        
        return Math.min(time1, time2);
    }
    
    public static void main(String[] args) {
        AssemblyLineScheduling obj = new AssemblyLineScheduling();
        
        int[][] a = {
                        {4, 5, 3, 2}, 
                        {2, 10, 1, 4}
                    }; 
        int[][] t = {
                        {0, 7, 4, 5}, 
                        {0, 9, 2, 8}
                    }; 
        int[] e = {10, 12};
        int[] x = {18, 7};
        
        System.out.println(obj.getMinTime(a, t, e, x)); // 35
    }

}
