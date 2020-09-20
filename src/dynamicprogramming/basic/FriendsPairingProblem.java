package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/friends-pairing-problem/

public class FriendsPairingProblem {
    
    private class SimpleRecursiveSolution {
        // T(n): Exponential
        public int pairFriends(int n) {
            if (n == 0)
                return 1;
            if (n == 1)
                return 1;
            
            return pairFriends(n-1) + (n-1)*pairFriends(n-2);
        }
    }
    
    private class DPSolution {
        // top-to-bottom memoized
        // T(n): O(n), S(n): O(n)
        public int pairFriendsMemoized(int n, int[] res) {
            if (n == 0)
                return 1;
            if (n == 1)
                return 1;
            
            if (res[n] != -1)
                return res[n];
            
            res[n] = pairFriendsMemoized(n-1, res) + (n-1)*pairFriendsMemoized(n-2, res);
            return res[n];
        }
        
        // bottom-up tabulation
        // T(n): O(n), S(n): O(n)
        public int pairFriendsBottomUp(int n) {
            int[] res = new int[n+1];
            
            res[0] = 1; res[1] = 1;
            for (int i = 2; i <= n; i++)
                res[i] = res[i-1] + (i-1)*res[i-2];
            
            return res[n];
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
