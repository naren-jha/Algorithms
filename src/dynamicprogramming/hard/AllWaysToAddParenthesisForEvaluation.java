package dynamicprogramming.hard;

import java.util.ArrayList;
import java.util.List;

// https://www.geeksforgeeks.org/all-ways-to-add-parenthesis-for-evaluation/

public class AllWaysToAddParenthesisForEvaluation {

	// This problem is similar to Matrix Chain Multiplication and MinAndMaxValueOfExpr problem

	// T(n): O(n^3), S(n): O(n^2)
	public static List<String> calculateAllPossibleValuesOfExpr(String expr) {
		List<Integer> num = new ArrayList<Integer>();
		List<Character> opr = new ArrayList<Character>();
		
		// store operators and numbers in separate lists
		String tmp = "";
		for (int i = 0; i < expr.length(); i++) {
			char c = expr.charAt(i);
			if (isOperator(c)) {
				opr.add(c);
				num.add(Integer.parseInt(tmp));
				tmp = "";
			}
			else
				tmp += c;
		}
		// storing last number in num list
		num.add(Integer.parseInt(tmp));
		
		List<String> result = new ArrayList<String>();
		
		// Solve the problem of placing parenthesis at different positions
		// and calculating result for all the different combinations.
		// We use similar logic as matrix chain multiplication problem.
		int n = num.size();
		String[][] calc = new String[n][n];
		
		// initialize values for length 1
		for (int i = 0; i < n; i++)
			calc[i][i] = num.get(i).toString();
		
		// fill entries for lengths 2 to n
		for (int len = 2; len <= n; len++) {
			for (int i = 0; i <= n - len; i++) {
				int j = i + len - 1;
				
				for (int k = i; k < j; k++) {
					if (calc[i][j] == null)
						calc[i][j] = "";
					if (k > i)
						calc[i][j] += " ";
					for (String op1 : calc[i][k].split(" ")) {
						for (String op2 : calc[k+1][j].split(" ")) {
							if (opr.get(k) == '+') {
								calc[i][j] += Integer.parseInt(op1) + Integer.parseInt(op2);
							}
							else if (opr.get(k) == '-') {
								calc[i][j] += Integer.parseInt(op1) - Integer.parseInt(op2);
							}
							else { // (opr.get(k) == '*')
								calc[i][j] += Integer.parseInt(op1) * Integer.parseInt(op2);
							}
							calc[i][j] += " ";
						}
					}
					calc[i][j] = calc[i][j].substring(0, calc[i][j].length()-1); // remove last space
					
				}
				if (len == n)
					result.add(calc[i][j]);
			}
		}
		return result;
	}
	
	private static boolean isOperator(char op) {
		return op == '+' || op == '-' || op == '*';
	}
	
	public static void main(String[] args) {
		String expr = "5*4-3*2";
		List<String> result = calculateAllPossibleValuesOfExpr(expr);
		System.out.println(result); // [-10 10 14 10 34]
	}
}
