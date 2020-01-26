package dynamicprogramming.intermediate;

import java.util.ArrayList;
import java.util.List;

// https://www.geeksforgeeks.org/minimum-maximum-values-expression/

public class MinAndMaxValueOfExpr {
    
    // This problem is similar to matrix chain multiplication problem
    
    public void printMinAndMaxValueOfExpr(String expr) {
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
        
        // Solve the problem of placing parenthesis at different positions
        // and calculating minimum and maximum values
        // by using similar logic as matrix chain multiplication problem
        int n = num.size();
        int[][] minVal = new int[n][n];
        int[][] maxVal = new int[n][n];
        
        // initialize values for length 1
        for (int i = 0; i < n; i++)
            minVal[i][i] = maxVal[i][i] = num.get(i);
        
        // fill entries for lengths 2 to n
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                
                minVal[i][j] = Integer.MAX_VALUE;
                maxVal[i][j] = Integer.MIN_VALUE;
                for (int k = i; k < j; k++) {
                    int minTmp, maxTmp;
                    if (opr.get(k) == '+') {
                        minTmp = minVal[i][k] + minVal[k+1][j];
                        maxTmp = maxVal[i][k] + maxVal[k+1][j];
                    }
                    else { // (opr.get(k) == '*')
                        minTmp = minVal[i][k] * minVal[k+1][j];
                        maxTmp = maxVal[i][k] * maxVal[k+1][j];
                    }
                    if (minTmp < minVal[i][j])
                        minVal[i][j] = minTmp;
                    if (maxTmp > maxVal[i][j])
                        maxVal[i][j] = maxTmp;
                }
            }
        }
        
        System.out.println("Minimum Value = " + minVal[0][n-1]);
        System.out.println("Maximum Value = " + maxVal[0][n-1]);
    }
    
    private boolean isOperator(char op) {
        return op == '+' || op == '*';
    }
    
    public static void main(String[] args) {
        String expr = "1+2*3+4*5";
        MinAndMaxValueOfExpr obj = new MinAndMaxValueOfExpr();
        obj.printMinAndMaxValueOfExpr(expr);
        /*
         * Minimum Value = 27
         * Maximum Value = 105
         */
    }

}
