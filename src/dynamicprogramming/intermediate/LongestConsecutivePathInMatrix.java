package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/find-length-of-the-longest-consecutive-path-in-a-character-matrix/

public class LongestConsecutivePathInMatrix {

    private static int R, C;
    int[][] dp;
    
    // adjacent cell indices
    int[][] aci = { {-1, -1}, {-1, 0}, {-1, 1},
                    {0, -1}, {0, 1},
                    {1, -1}, {1, 0}, {1, 1} };
    
    public int longestConsecutivePath(char[][] mat, char s) {
        R = mat.length; C = mat[0].length;
        dp = new int[R][C];
        
        for (int i = 0; i < R; i++)
            Arrays.fill(dp[i], -1);

        int ans = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (mat[i][j] == s) {
                    for (int k = 0; k < aci.length; k++)
                        ans = Math.max(ans, 1 + longestConsecutivePathForCell(mat, i + aci[k][0], j + aci[k][1], mat[i][j]));
                }
            }
        }
        return ans;
    }

    private int longestConsecutivePathForCell(char[][] mat, int i, int j, char prev) {
        if (!isValid(i, j) || !isAdjacent(prev, mat[i][j]))
            return 0;
        
        if (dp[i][j] != -1)
            return dp[i][j];
        
        int ans = 0;
        for (int k = 0; k < aci.length; k++)
            ans = Math.max(ans, 1 + longestConsecutivePathForCell(mat, i + aci[k][0], j + aci[k][1], mat[i][j]));
        return dp[i][j] = ans;
    }
    
    private boolean isValid(int i, int j) {
        if (i < 0 || i >= R || j < 0 || j >= C)
            return false;
        return true;
    }

    private boolean isAdjacent(char prev, char curr) {
        return (curr - prev) == 1;
    }
    
    public static void main(String[] args) {
        char[][] mat = { {'a','c','d'}, 
                         {'h','b','a'}, 
                         {'i','g','f'} };
        LongestConsecutivePathInMatrix o = new LongestConsecutivePathInMatrix();
        System.out.println(o.longestConsecutivePath(mat, 'a')); // 4
        System.out.println(o.longestConsecutivePath(mat, 'e')); // 0
        System.out.println(o.longestConsecutivePath(mat, 'b')); // 3
        System.out.println(o.longestConsecutivePath(mat, 'f')); // 4
        System.out.println(o.longestConsecutivePath(mat, 'c')); // 2
        System.out.println(o.longestConsecutivePath(mat, 'd')); // 1
    }

}
