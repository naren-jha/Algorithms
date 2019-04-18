package basic.stack;

import java.util.Stack;

/**
 * @author Narendra Jha
 *
 */
public class PostfixEvaluation {

	public static void main(String[] args) {
		// Infix: 2 * 3 + 5 * 4 - 9 = 6 + 20 - 9 = 17
		System.out.println(evaluatePostfix("2 3 * 5 4 * + 9 -")); // 17
		// Infix: 22 * 3 + 15 * 4 - 18 = 66 + 60 - 18 = 108
		System.out.println(evaluatePostfix("22 3 * 15 4 * + 18 -")); // 108
		// Infix: 4 + 9 * 3 ^ 2 = 4 + 9 * 9 = 85
		System.out.println(evaluatePostfix("4 9 3 2 ^ * +")); // 85
	}
	
	public static int evaluatePostfix(String expr) {
		char[] e = expr.toCharArray();
		Stack<Integer> s = new Stack<Integer>();
		
		// Scanning each character from left to right
		for(int i = 0; i < e.length; i++) {
			// If character is a delimiter, move on
			if(e[i] == ' ' || e[i] == ',')
				continue;
			
			else if(isDigit(e[i])) {
				int operand = 0;
				// Keep incrementing 'i' as long as 
				// you are getting a numeric digit. 
				while(i < e.length && isDigit(e[i])) {
					/* For a number with more than one digits, 
					 * as we are scanning from left to right. 
					 * Everytime, we get a digit towards right, 
					 * we can multiply current total in operand 
					 * by 10 and add the new digit. 
					 */
					int opNum = Character.getNumericValue(e[i]);
					operand = (operand * 10) + opNum;
					i++;
				}
				/* Finally, you will come out of while loop with 'i' 
				 * set to a non-numeric character or end of string
				 * decrement 'i' as it will be incremented in 
				 * increment section of for loop once again. 
				 * We do not want to skip the non-numeric character 
				 * by incrementing 'i' twice. 
				 */
				i--;
				s.push(operand);
			}
			
			// If character is operator, pop two elements from stack, 
			// perform operation and push the result back. 
			else if(isOperator(e[i])) {
				int op2 = s.pop();
				int op1 = s.pop();
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
