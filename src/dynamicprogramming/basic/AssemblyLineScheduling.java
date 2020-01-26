package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/assembly-line-scheduling-dp-34/

public class AssemblyLineScheduling {
    
    public int getMinTime(int[][] a, int[][] t, int[] e, int[] x) {
        int n = a[0].length; // no. of stations
        
        // create two time arrays, which will represent
        // minimum time required to leave a station, in two lines
        int[] time1 = new int[n]; // time array for 1st line
        int[] time2 = new int[n]; // time array for 2nd line
        
        // minimum time required to leave first stations, in two lines
        time1[0] = e[0] + a[0][0];
        time2[0] = e[1] + a[1][0];
        
        // calculate minimum time required for rest of the stations
        // using bottom up DP approach
        for (int i = 1; i < n; i++) {
            time1[i] = Math.min(time1[i-1] + a[0][i], 
                                time2[i-1] + t[1][i] + a[0][i]);
            time2[i] = Math.min(time2[i-1] + a[1][i], 
                                time1[i-1] + t[0][i] + a[1][i]);
        }
        
        // finally, minimum time required for entire process 
        // will be the minimum time required to come out of last  
        // station, plus the exit time
        return Math.min(time1[n-1] + x[0], time2[n-1] + x[1]);
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
