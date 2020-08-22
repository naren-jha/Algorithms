package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/super-ugly-number-number-whose-prime-factors-given-set/

public class SuperUglyNumbers {

    public int getUglyNumber(int n, int[] primes) {
        int[] uglyNumbers = new int[n];
        uglyNumbers[0] = 1; // first ugly number
        
        int[] nextMultiples = primes.clone();
        int[] multiplierIndices = new int[primes.length];
        
        for (int index = 1; index < n; index++) {
            uglyNumbers[index] = Arrays.stream(nextMultiples).min().getAsInt();
            
            for (int j = 0; j < primes.length; j++) {
                if (uglyNumbers[index] == nextMultiples[j]) {
                    multiplierIndices[j]++;
                    nextMultiples[j] = uglyNumbers[multiplierIndices[j]] * primes[j];
                }
            }
        }
        return uglyNumbers[n-1];
    }
    public static void main(String[] args) {
        System.out.println(new SuperUglyNumbers().getUglyNumber(150, new int[]{2, 3, 5})); // 5832
        System.out.println(new SuperUglyNumbers().getUglyNumber(50, new int[]{2, 3, 5})); // 243
        System.out.println(new SuperUglyNumbers().getUglyNumber(5, new int[]{2, 5})); // 8
        System.out.println(new SuperUglyNumbers().getUglyNumber(9, new int[]{3, 5, 7, 11, 13})); // 21
    }

}
