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
            int i2 = 0, i3 = 0, i5 = 0; // multiplier indexes
            
            int nextMultipleOf2 = 2;
            int nextMultipleOf3 = 3;
            int nextMultipleOf5 = 5;
            for (int index = 1; index < n; index++) {
                uglyNumbers[index] = Math.min(nextMultipleOf2, Math.min(nextMultipleOf3, nextMultipleOf5));
                
                if (uglyNumbers[index] == nextMultipleOf2) {
                    i2++;
                    nextMultipleOf2 = uglyNumbers[i2]*2;
                }
                if (uglyNumbers[index] == nextMultipleOf3) {
                    i3++;
                    nextMultipleOf3 = uglyNumbers[i3]*3;
                }
                if (uglyNumbers[index] == nextMultipleOf5) {
                    i5++;
                    nextMultipleOf5 = uglyNumbers[i5]*5;
                }
            }
            return uglyNumbers[n-1];
        }
    }

    public static void main(String[] args) {
        System.out.println(new UglyNumbers().new SimpleButInefficientSolution().getUglyNumber(150)); // 5832
        System.out.println(new UglyNumbers().new DPSolution().getUglyNumber(150)); // 5832
    }

}
