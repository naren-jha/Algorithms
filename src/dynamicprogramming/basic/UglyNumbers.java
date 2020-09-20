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
    
    // Dynamic Programming Solution - linear time
    private class DPSolution {
        public int getUglyNumber(int n) {
            int[] uglyNumbers = new int[n];
            uglyNumbers[0] = 1; // first ugly number
            int p2 = 0, p3 = 0, p5 = 0; // multiplier indexes
            
            for (int i = 1; i < n; ++i) {
                int m2 = uglyNumbers[p2] * 2;
                int m3 = uglyNumbers[p3] * 3;
                int m5 = uglyNumbers[p5] * 5;
                
                uglyNumbers[i] = Math.min(m2, Math.min(m3, m5));
                
                if (uglyNumbers[i] == m2) p2++;
                if (uglyNumbers[i] == m3) p3++;
                if (uglyNumbers[i] == m5) p5++;
            }
            return uglyNumbers[n-1];
        }
    }

    public static void main(String[] args) {
        System.out.println(new UglyNumbers().new SimpleButInefficientSolution().getUglyNumber(150)); // 5832
        System.out.println(new UglyNumbers().new DPSolution().getUglyNumber(150)); // 5832
    }

}
