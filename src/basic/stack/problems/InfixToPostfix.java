package basic.stack.problems;

import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class InfixToPostfix {

    public static void main(String[] args) {
        String res = infixToPostfix("((A + B) * C - D) * E");
        System.out.println(res); // A B + C * D - E *
        
        res = infixToPostfix("A * (B + C)");
        System.out.println(res); // A B C + *
        
        res = infixToPostfix("[(X + Y) * {(Z + A) ^ B ^ C}] * P");
        System.out.println(res); // X Y + Z A + B C ^ ^ * P *
    }

    public static String infixToPostfix(String expr) {
        char[] e = expr.toCharArray();
        Stack<Character> s = new Stack<Character>();
        StringBuilder postfix = new StringBuilder("");
        
        for (int i = 0; i < e.length; i++) {
            // If character is a delimiter, move on
            if (e[i] == ' ' || e[i] == ',')
                continue;
            
            if (isOperand(e[i])) {
                postfix.append(e[i]);
                postfix.append(" ");
            }
            
            else if (isOperator(e[i])) {
                while (!s.empty() && !isOpeningParentheses(s.peek())
                        && hasHigherPrecedence(s.peek(), e[i])) {
                    postfix.append(s.pop());
                    postfix.append(" ");
                }
                s.push(e[i]);
            }
            
            else if (isOpeningParentheses(e[i])) {
                s.push(e[i]);
            }
            
            else if (isClosingParentheses(e[i])) {
                while (!s.empty() && !isOpeningParentheses(s.peek())) {
                    postfix.append(s.pop());
                    postfix.append(" ");
                }
                if (s.empty())
                    return "Invalid Expression";
                else
                    s.pop();
            }
        }
        while (!s.empty()) {
            postfix.append(s.pop());
            postfix.append(" ");
        }
        return postfix.toString().trim();
    }
    
    // Method to verify whether a character is an operand or not.
    // We are assuming in this solution that operand will be a single character
    private  static boolean isOperand(char c) {
        return ((c >= '0' && c <= '9') ||
                (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z'));
    }
    
    // Method to verify whether a character is operator symbol or not. 
    private  static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }
    
    // Method to check if a token is an opening parenthesis or not
    private  static boolean isOpeningParentheses(char c) {
        return c == '(' || c == '{' || c == '[';
    }
    
    // Method to check if a token is a closing parenthesis or not
    private  static boolean isClosingParentheses(char c) {
        return c == ')' || c == '}' || c == ']';
    }
    
    private  static boolean hasHigherPrecedence(char op1, char op2) {
        int op1Weight = getOperatorWeight(op1);
        int op2Weight = getOperatorWeight(op2);
        
        // If operators have equal precedence, return true if they are left 
        // associative. and return false, if right associative. 
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
    private  static boolean isRightAssociative(char operator) {
        return operator == '^';
    }
    
    // Method to get weight of an operator. 
    // An operator with higher weight will have higher precedence. 
    private  static int getOperatorWeight(char operator) {
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
}
