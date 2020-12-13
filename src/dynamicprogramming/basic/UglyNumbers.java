package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/ugly-numbers/

public class UglyNumbers {
    
    // Simple but inefficient solution - not linear time
    private class SimpleButInefficientSolution {
        
        public int getUglyNumber(int n) {
            int index = 1, uglyCount = 1;
            while (uglyCount != n) {
                index++;
                if (isUgly(index))
                    uglyCount++;
            }
            return index;
        }

        private boolean isUgly(int num) {
            num = maxDivide(num, 2);
            num = maxDivide(num, 3);
            num = maxDivide(num, 5);
            
            return num == 1;
        }
        
        private int maxDivide(int a, int b) {
            while (a % b == 0)
                a /= b;
            
            return a;
        }
        
    }
    
    // DP solution
    // TC: O(n), SC: O(n)
    private class DPSolution {
        public int getUglyNumber(int n) {
            int[] dp = new int[n]; // to store ugly numbers
            dp[0] = 1; // first ugly number
            
            int mi2 = 0, mi3 = 0, mi5 = 0; // multiplier indices
            
            for (int i = 1; i < n; ++i) {
                int mv2 = dp[mi2] * 2;
                int mv3 = dp[mi3] * 3;
                int mv5 = dp[mi5] * 5;
                
                int sm = smallest(mv2, mv3, mv5); // get smallest
                if (sm == mv2) mi2++;
                if (sm == mv3) mi3++;
                if (sm == mv5) mi5++;
                
                dp[i] = sm;
            }
            return dp[n-1];
        }
    }
    
    private int smallest(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    public static void main(String[] args) {
        System.out.println(new UglyNumbers().new SimpleButInefficientSolution().getUglyNumber(150)); // 5832
        System.out.println(new UglyNumbers().new DPSolution().getUglyNumber(150)); // 5832
    }

}
