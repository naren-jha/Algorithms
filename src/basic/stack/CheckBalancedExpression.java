package basic.stack;

import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class CheckBalancedExpression {
    
    public static char[][] PARENTHESES = {{'(', ')'}, {'{', '}'}, {'[', ']'}};
    
    private static boolean isBalanced(String expr) {
        char[] c = expr.toCharArray();
        int n = c.length;
        Stack<Character> s = new Stack<Character>();
        for (int i = 0; i < n; i++) {
            if (isOpeningParentheses(c[i]))
                s.push(c[i]);
            else if (isClosingParentheses(c[i]))
                if (s.empty() || !arePair(s.pop(), c[i]))
                    return false;
        }
        return s.empty();
    }

    // Method to check if a token is an opening parenthesis or not
    public static boolean isOpeningParentheses(char c) {
        // return c == '(' || c == '{' || c == '[';
        for (char[] p : PARENTHESES) {
            if (p[0] == c)
                return true;
        }
        return false;
    }
    
    // Method to check if a token is a closing parenthesis or not
    public static boolean isClosingParentheses(char c) {
        // return c == ')' || c == '}' || c == ']';
        for (char[] p : PARENTHESES) {
            if (p[1] == c)
                return true;
        }
        return false;
    }
    
    // Method to match a opening and closing parentheses.
    private static boolean arePair(char opening, char closing) {
        /* if (opening == '(' && closing == ')') return true;
        else if (opening == '{' && closing == '}') return true;
        else if (opening == '[' && closing == ']') return true;
        return false; */
        for (char[] p : PARENTHESES) {
            if (p[0] == opening)
                return p[1] == closing;
        }
        return false;
    }
    
    public static void main(String[] args) {
        System.out.println(isBalanced("()")); // true
        System.out.println(isBalanced("{()}")); // true
        System.out.println(isBalanced("{()+()}")); // true
        System.out.println(isBalanced("{(A + B)*(C + D)}")); // true
        System.out.println(isBalanced("{(X + Y) * (Z)")); // false
        System.out.println(isBalanced("[2*3] + (A)]")); // false
        System.out.println(isBalanced(")(")); // false
        System.out.println(isBalanced("{a + z)")); // false
    }

}
