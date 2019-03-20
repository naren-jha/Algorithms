package dynamicprogramming.intermediate;

import java.util.HashSet;
import java.util.Set;

// https://www.geeksforgeeks.org/length-of-the-longest-substring-without-repeating-characters/
/*
 * This problem does not require DP, it can be solved using hashtable in O(n) time and space
 */
public class LongestSustringWithoutRepeatingCharacters {

	public static int len(String s) {
        int maxLength = 0;
        Set<Character> set = new HashSet<Character>();
        
        int i = 0;
        int j = 0;
        while (j < s.length()) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j));
                maxLength = Math.max(maxLength, j - i + 1);
                j++;
            } else {
                set.remove(s.charAt(i));
                i++;
            }
        }
        return maxLength;
    }
	
	public static void main(String[] args) {
		System.out.println(len("ABDEFGABEF")); // 6
	}
}
