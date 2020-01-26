package dynamicprogramming.hard;

import java.util.HashMap;
import java.util.Map;

// https://www.geeksforgeeks.org/number-of-palindromic-paths-in-a-matrix/

public class CountPalindromicPathsInMatrix {

    private int R, C;
    private Map<String, Integer> mem = new HashMap<String, Integer>();
    
    // T(n): O(R*C), S(n): O(R*C)
    public int count(char[][] m) {
        R = m.length; C = m[0].length;
        return count(m, 0, 0, R-1, C-1);
    }
    
    /*
     * (rs, cs) => indices of current cell from starting point
     * (re, ce) => indices of current cell from ending point
     */
    private int count(char[][] m, int rs, int cs, int re, int ce) {
        if (rs < 0 || rs >= R || cs < 0 || cs >= C)
            return 0;
        if (re < 0 || re < rs || ce < 0 || ce < cs) // Observe conditions: re < rs and ce < cs
            return 0;
        
        // if values are not equal
        if (m[rs][cs] != m[re][ce])
            return 0;
        
        // if adjacent cell
        if ((re-rs) + (ce-cs) == 1)
            return 1;
        
        String key = rs + "-" + cs + "-" + re + "-" + ce;
        if (mem.containsKey(key))
            return mem.get(key);
        
        int res = count(m, rs+1, cs, re-1, ce) + count(m, rs+1, cs, re, ce-1)
                + count(m, rs, cs+1, re-1, ce) + count(m, rs, cs+1, re, ce-1);
        mem.put(key, res);
        return res;
    }      
    
    public static void main(String[] args) {
        char[][] m = {
                {'a', 'a', 'a', 'b'}, 
                {'b', 'a', 'a', 'a'}, 
                {'a', 'b', 'b', 'a'}
        };
        CountPalindromicPathsInMatrix o = new CountPalindromicPathsInMatrix();
        System.out.println(o.count(m)); // 3
    }
}
