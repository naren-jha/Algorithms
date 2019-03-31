package basic.array.problems;

/*
 * Write a function that takes two strings input, s and p
 * and returns a boolean denoting whether s matches p
 * 
 * p is a sequence of any number of the following
 * 1. a-z - which stands for itself
 * 2. . - which matches any character
 * 3. * - which matches 0 or more occurrences of the previous 
 * 	      single character
 * 
 * Examples:
 * s = "aba", p = "ab" => false
 * s = "aa", p = "a*" => true
 * s = "ab", p = ".*" => true
 * s = "ab", p = "." => false
 * s = "aab", p = "c*a*b" => true
 * s = "aaa", p = "a*" => true
 */

// https://www.youtube.com/watch?v=tj_sBZw9nS0

public class RegularExpressionMatcher {

	public static boolean isMatch(String s, String p) {
		char[] sa = s.toCharArray();
		char[] pa = p.toCharArray();
		
		int si = 0;
		for (int pi = 0; pi < pa.length; pi++) {
			if (pa[pi] >= 'a' && pa[pi] <= 'z') {
				if (pi == pa.length-1 || pa[pi+1] != '*') {
					if (si < sa.length && sa[si] == pa[pi])
						si++;
					else
						return false;
				}
				else {
					while (si < sa.length && sa[si] == pa[pi] )
						si++;
				}
			}
			else if (pa[pi] == '.') {
				if (pi == pa.length-1 || pa[pi+1] != '*') {
					if (si < sa.length && sa[si] >= 'a' && sa[si] <= 'z')
						si++;
					else
						return false;
				}
				else {
					while (si < sa.length && sa[si] >= 'a' && sa[si] <= 'z') {
						if (pi < pa.length-2) {
							if (sa[si] != pa[pi+2])
								si++;
							else
								break;
						}
						else
							si++;
					}
				}
			}
			else {
				// it must be a *
				continue;
			}
		}
		return si == sa.length;
	}
	
	public static void main(String[] args) {
		System.out.println(isMatch("aba", "ab")); // false
		System.out.println(isMatch("aa", "a*")); // true
		System.out.println(isMatch("ab", ".*")); // true
		System.out.println(isMatch("ab", ".")); // false
		System.out.println(isMatch("aab", "c*a*b")); // true
		System.out.println(isMatch("aaa", "a*")); // true
		
		System.out.println(isMatch("abdc", ".*dc")); // true
		System.out.println(isMatch("abdc", ".*fc")); // false
		System.out.println(isMatch("mississippi", "mis*is*p*.")); // true
	}
}
