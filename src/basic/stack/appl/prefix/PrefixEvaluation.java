package basic.stack.appl.prefix;

import java.util.Stack;

/**
 * @author Narendra Jha
 *
 */
public class PrefixEvaluation {

	public static void main(String[] args) {
		// Infix: 2 * 3 + 5 * 4 - 9 = 6 + 20 - 9 = 17
		System.out.println(evaluatePrefix("- + * 2 3 * 5 4 9")); // 17
		// Infix: 22 * 3 + 15 * 4 - 18 = 66 + 60 - 18 = 108
		System.out.println(evaluatePrefix("- + * 22 3 * 15 4 18")); // 108
		// Infix: 4 + 9 * 3 ^ 2 = 4 + 9 * 9 = 85
		System.out.println(evaluatePrefix("+ * ^ 3 2 9 4")); // 85
	}

	public static int evaluatePrefix(String expr) {
		char[] e = expr.toCharArray();
		Stack<Integer> s = new Stack<Integer>();
		
		// Scanning each character from right to left
		for(int i = e.length - 1; i >= 0; i--) {
			// If character is a delimiter, move on
			if(e[i] == ' ' || e[i] == ',')
				continue;
			
			else if(isDigit(e[i])) {
				int operand = 0, index = 1;
				// Keep decrementing 'i' as long as 
				// you are getting a numeric digit. 
				while(i >= 0 && isDigit(e[i])) {
					/* For a number with more than one digits, 
					 * as we are scanning from right to left. 
					 * Everytime, we get a digit towards left, 
					 * multiply it with its place value,
					 * which is 'index', and add that to current total 
					 */
					int opNum = Character.getNumericValue(e[i]);
					operand = operand + (index * opNum);
					index = index * 10;
					i--;
				}
				/* Finally, you will come out of while loop with 'i' 
				 * set to a non-numeric character or beginning of string
				 * increment 'i' as it will be decremented in 
				 * decrement section of for loop once again. 
				 * We do not want to skip the non-numeric character 
				 * by decrementing 'i' twice.
				 */
				i++;
				s.push(operand);
			}
			
			// If character is operator, pop two elements from stack, 
			// perform operation and push the result back. 
			else if(isOperator(e[i])) {
				int op1 = s.pop();
				int op2 = s.pop();
				int result = performOperation(e[i], op1, op2);
				s.push(result);
			}
		}
		// If expression is in correct format, 
		// Stack will finally have one element, which will be answer. 
		return s.pop();
	}
	
	// Method to verify whether a character is digit or not. 
	public static boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}
	
	// Method to verify whether a character is operator symbol or not. 
	public static boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
	}

	// Method to perform an operation and return result. 
	private static int performOperation(char operator, int op1, int op2) {
		switch(operator) {
			case '+': return op1 + op2;
			case '-': return op1 - op2;
			case '*': return op1 * op2;
			case '/': return op1 / op2;
			case '^': return (int) Math.pow(op1, op2);
			default: return 0;
		}
	}
}
