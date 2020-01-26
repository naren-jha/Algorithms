package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/boolean-parenthesization-problem-dp-37/

public class BooleanParenthesizationProblem {

    // This problem is similar to matrix chain multiplication problem
    
    public int count(String operands, String operators) {
        int n = operands.length();
        
        // T[i][j] stores the number of ways operands[i..j] can be evaluated to true
        int[][] T = new int[n][n];
        
        // F[i][j] stores the number of ways operands[i..j] can be evaluated to false
        int[][] F = new int[n][n];
        
        // We will have our final result in T[0][n-1]
        
        // initialize values for length 1
        for (int i = 0; i < n; i++) {
            T[i][i] = (operands.charAt(i) == 'T') ? 1 : 0;
            F[i][i] = (operands.charAt(i) == 'F') ? 1 : 0;
        }
        
        // fill entries for lengths 2 to n
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                
                for (int k = i; k < j; k++) {
                    // total # of ways operands[i..k] can be evaluated to true/false
                    int totalIK = T[i][k] + F[i][k];
                    
                    // total # of ways operands[k+1..j] can be evaluated to true/false
                    int totalKJ = T[k+1][j] + F[k+1][j];
                    
                    char opr = operators.charAt(k);
                    if (opr == '&') {
                        T[i][j] += T[i][k] * T[k+1][j];
                        F[i][j] += (totalIK * totalKJ) - (T[i][k] * T[k+1][j]);
                        // or F[i][j] += (T[i][k] * F[k+1][j]) + (F[i][k] * T[k+1][j]) 
                        //                    + (F[i][k] * F[k+1][j]);
                    }
                    else if (opr == '|') {
                        F[i][j] += F[i][k] * F[k+1][j];
                        T[i][j] += (totalIK * totalKJ) - (F[i][k] * F[k+1][j]);
                        // or T[i][j] += (T[i][k] * F[k+1][j]) + (F[i][k] * T[k+1][j]) 
                        //                    + (T[i][k] * T[k+1][j]);
                    }
                    else if (opr == '^') {
                        T[i][j] += (T[i][k] * F[k+1][j]) + (F[i][k] * T[k+1][j]);
                        F[i][j] += (T[i][k] * T[k+1][j]) + (F[i][k] * F[k+1][j]);
                    }
                }
            }
        }
        return T[0][n-1];
    }
    
    public static void main(String[] args) {
        BooleanParenthesizationProblem o = new BooleanParenthesizationProblem();
        String operands = "TTFT";
        String operators = "|&^";
        System.out.println(o.count(operands, operators)); // 4
    }
}
