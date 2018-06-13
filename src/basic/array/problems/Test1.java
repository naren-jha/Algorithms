package basic.array.problems;

import java.util.*;

public class Test1 {

	public static void main(String[] args) {
		//System.out.println(t1( "(I want (to write a language parser), would (you) help me)" , 2));
		//System.out.println(t1( "(()()((())))(())" , 5));
		//System.out.println(t2("GGGGGrrrrrrrrrrrrrrt"));
		
	}
	
	public static int t1(String inputSentence, int openingBraceNum) {
		char[] c = inputSentence.toCharArray();
		int n = c.length;
		int braceCount = 0;
		int res = -1;
		
		Stack<Character> s = new Stack<Character>();
		for (int i = 0; i < n; i++) {
			if (c[i] == '(') {
				s.push(c[i]);
				braceCount++;
			}
			else if (c[i] == ')') {
				if (s.empty() || s.pop() != '(') {
					return -1; 
				}
                
				if (braceCount == openingBraceNum  && res == -1) {
					res = i + 1;
				}
				if (braceCount >= openingBraceNum) {
					braceCount--;
				}
			}
		}
		return res;
	}

	public static String t2(String input) {
		if (input.isEmpty())
			return input;
		
		char[] c = input.toCharArray();
		int n = c.length;
		
		StringBuilder res = new StringBuilder();
		int charCount = 1;
		char prevChar = c[0];
		for (int i = 1; i < n; i++) {
			if (c[i] == prevChar) {
				charCount++;
			}
			else {
				res.append(charCount).append(prevChar);
				prevChar = c[i];
				charCount = 1;
			}
		}
		res.append(charCount).append(prevChar);
		
		return res.toString();
	}

}
