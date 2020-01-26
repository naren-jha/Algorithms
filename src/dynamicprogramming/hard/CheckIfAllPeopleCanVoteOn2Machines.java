package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/check-people-can-vote-two-machines/

public class CheckIfAllPeopleCanVoteOn2Machines {

    // T(n): O(nx), S(n): O(nx)
    public static boolean canVote(int[] a, int x) {
        int n = a.length;
        
        // calculate total time taken by all n people
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += a[i];
        
        // check if all n people can vote on a single machine
        if (sum <= x)
            return true;
        
        /*
         * When all n people cannot vote on a single machine, then we need to check
         * if they can vote using both machines. 
         * 
         * To do that, we need to divide the original array in two halves, so that
         * one half can vote on one machine, and other half on another machine. 
         * We use 0-1 knapsack technique to solve this part.
         * 
         * We find the maximum sum possible, i.e., maximum number of people who can 
         * vote on one machine using 0-1 knapsack technique, then we simply have to 
         * check if rest of the people can vote on another machine or not
         */
        return sum - knapsack(a, x) <= x;
    }
    
    // T(n): O(nw), S(n): O(nw)
    public static int knapsack(int[] a, int w) {
        int n = a.length;
        int[][] k = new int[n+1][w+1];
        
        for (int i = 0; i <= n; i++)
            k[i][0] = 0;
        for (int j = 1; j <= w; j++)
            k[0][j] = 0;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= w; j++) {
                if (a[i-1] > j) // when last element cannot be included
                    k[i][j] = k[i-1][j];
                else
                    k[i][j] = Math.max(k[i-1][j - a[i-1]] + a[i-1], k[i-1][j]);
            }
        }
        
        return k[n][w];
    }
    
    public static void main(String[] args) {
        int x = 4;
        int[] a = {2, 4, 2};
        System.out.println(canVote(a, x)); // true
        
        a = new int[]{2, 4, 3};
        System.out.println(canVote(a, x)); // false
    }
}
