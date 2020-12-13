package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/friends-pairing-problem/

public class FriendsPairingProblem {
    
    private class SimpleRecursiveSolution {
        // TC: O(2^n), SC: O(n) 
        // max height (or max number of levels) of recursion tree is n and 
        // for each node in the tree, we are making 2 recursive calls
        public int pairFriends(int n) {
            if (n <= 1) return 1;
            
            return pairFriends(n-1) + (n-1)*pairFriends(n-2);
        }
    }
    
    private class DPSolution {
        // top-to-bottom memoization
        // TC: O(n), SC: O(n)
        public int pairFriendsMemoized(int n, int[] mem) {
            if (n <= 1) return 1;
            
            if (mem[n] != -1) return mem[n];
            
            mem[n] = pairFriendsMemoized(n-1, mem) + (n-1)*pairFriendsMemoized(n-2, mem);
            return mem[n];
        }
        
        // bottom-up tabulation
        // TC: O(n), SC: O(n)
        public int pairFriendsBottomUp(int n) {
            int[] dp = new int[n+1];
            dp[0] = 1; dp[1] = 1;
            
            for (int i = 2; i <= n; i++)
                dp[i] = dp[i-1] + (i-1)*dp[i-2];
            
            return dp[n];
        }
    }

    public static void main(String[] args) {
        int n = 4;
        FriendsPairingProblem obj = new FriendsPairingProblem();
        System.out.println(obj.new SimpleRecursiveSolution().pairFriends(n)); // 10
        
        int[] res = new int[n+1];
        Arrays.fill(res, -1);
        System.out.println(obj.new DPSolution().pairFriendsMemoized(n, res)); // 10
        
        System.out.println(obj.new DPSolution().pairFriendsBottomUp(n)); // 10
    }
}
