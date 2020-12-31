package dynamicprogramming.intermediate;

import static java.lang.Math.max;

import java.util.HashSet;
import java.util.Set;

// https://www.geeksforgeeks.org/length-of-the-longest-substring-without-repeating-characters/
/*
 * This problem does not require DP, it can be solved using hashtable in O(n) time and space
 */
public class LongestSustringWithoutRepeatingCharacters {

    public static int len(String s) {
        int maxLen = 0;
        Set<Character> set = new HashSet<Character>();
        
        int i = 0;
        int j = 0;
        while (j < s.length()) {
            char c = s.charAt(j);
            if (!set.contains(c)) {
                set.add(c);
                maxLen = max(maxLen, j - i + 1);
                j++;
            }
            else {
                set.remove(s.charAt(i));
                i++;
            }
        }
        
        return maxLen;
    }
    
    // since the keyspace is limited, we can also use a boolean array instead of hashtable
    // boolean[] visited = new boolean[256]
    // int i = c = s.charAt(j);
    // if (!visited[i])
    
    public static void main(String[] args) {
        System.out.println(len("ABDEFGABEF")); // 6
    }
}
