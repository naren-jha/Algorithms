package basic.stack;

import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * Infix expression evaluation
 */
public class InfixExpressionEvaluation {
    
    public static int evaluate(String expr) {
        char[] e = expr.toCharArray();
        Stack<Character> operators = new Stack<Character>();
        Stack<Integer> values = new Stack<Integer>();
        
        for (int i = 0; i < e.length; i++) {
            // If character is a delimiter, move on
            if (e[i] == ' ' || e[i] == ',')
                continue;
            
            if (isOperand(e[i])) {
                int operand = 0;
                // Keep incrementing 'i' as long as 
                // you are getting a numeric digit. 
                while (i < e.length && isOperand(e[i])) {
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
                values.push(operand);
            }
            
            else if (isOperator(e[i])) {
                while (!operators.empty() && !isOpeningParentheses(operators.peek())
                        && hasHigherPrecedence(operators.peek(), e[i])) {
                    int operand2 = values.pop();
                    int operand1 = values.pop();
                    char operator = operators.pop();
                    values.push(performOperation(operator, operand1, operand2));
                }
                operators.push(e[i]);
            }
            
            else if (isOpeningParentheses(e[i])) {
                operators.push(e[i]);
            }
            
            else if (isClosingParentheses(e[i])) {
                while (!operators.empty() && !isOpeningParentheses(operators.peek())) {
                    int operand2 = values.pop();
                    int operand1 = values.pop();
                    char operator = operators.pop();
                    values.push(performOperation(operator, operand1, operand2));
                }
                if (operators.empty())
                    throw new IllegalArgumentException("Invalid Expression");
                else
                    operators.pop();
            }
        }
        
        while (!operators.empty()) {
            int operand2 = values.pop();
            int operand1 = values.pop();
            char operator = operators.pop();
            values.push(performOperation(operator, operand1, operand2));
        }
        
        return values.pop();
    }
    
    // Method to verify whether a character is an operand or not
    private static boolean isOperand(char c) {
        return c >= '0' && c <= '9';
    }
    
    // Method to verify whether a character is operator symbol or not. 
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }
    
    // Method to check if a token is an opening parenthesis or not
    private static boolean isOpeningParentheses(char c) {
        return c == '(' || c == '{' || c == '[';
    }
    
    // Method to check if a token is a closing parenthesis or not
    private static boolean isClosingParentheses(char c) {
        return c == ')' || c == '}' || c == ']';
    }
    
    private static boolean hasHigherPrecedence(char op1, char op2) {
        int op1Weight = getOperatorWeight(op1);
        int op2Weight = getOperatorWeight(op2);
        
        // If operators have equal precedence, return true if they are left 
        // associative, and return false, if right associative. 
        // if operator is left-associative, left one should be given priority. 
        // if operators are right associative, we give precedence to second
        // operand, because in expression 2^3^2 = 2^(3^2) = 2^9 = 512,
        // we see that 3^2 is solved first to calculate correct result
        // therefore, second operand is given higher precedence.
        if (op1Weight == op2Weight) {
            if (isRightAssociative(op1))
                return false;
            else
                return true;
        }
        return op1Weight > op2Weight;
    }
    
    // Method to verify whether an operator is right associative or not. 
    private static boolean isRightAssociative(char operator) {
        return operator == '^';
    }
    
    // Method to get weight of an operator. 
    // An operator with higher weight will have higher precedence. 
    private static int getOperatorWeight(char operator) {
        int weight = -1;
        switch(operator) {
            case '+':
            case '-':
                weight = 1;
                break;
            case '*':
            case '/':
                weight = 2;
                break;
            case '^':
                weight = 3;
                break;
        }
        return weight;
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

    public static void main(String[] args) {
        System.out.println(evaluate("3 ^ 2 ^ 2")); // 81
        System.out.println(evaluate("10 ^ 2 + 6")); // 106
        System.out.println(evaluate("10 + 2 * 6")); // 22
        System.out.println(evaluate("100 * 2 + 12")); // 212
        System.out.println(evaluate("100 * (2 + 12)")); // 1400
        System.out.println(evaluate("100 * (2 + 12) / 14")); // 100
    }

}
