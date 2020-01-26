package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/count-total-number-of-n-digit-numbers-such-that-the-difference-between-the-sum-of-even-digits-and-odd-digits-is-1/

public class CountNumsWithEvenOddSumDiff1 {

    // This problem similar to CountNumbersWithGivenSum problem
    
    class SimpleRecursiveSolution {
        public int countNumbers(int n, int evenSum, int oddSum) {
            // Base cases:
            if (n == 0)
                return (evenSum - oddSum) == 1 ? 1 : 0;
            
            int count = 0;
            int x = 0;
            if (n == 1) // excluding leading 0
                x = 1;
            for (; x <= 9; x++) {
                if ((n-1) % 2 == 0)
                    count += countNumbers(n-1, evenSum+x, oddSum);
                else
                    count += countNumbers(n-1, evenSum, oddSum+x);
            }
            return count;
        }
    }
    
    /*
     * We can easily memoize above solution, but there is no need for that
     * as there are no overlapping subproblems, all the sub-problems are unique
     * So time complexity of above solution will be O(n * (n/2)*10 * (n/2)*10)
     * as we will have n/2 odd digits, and n/2 even digits, with each digits 
     * having values from 0-9
     */
    
    public static void main(String[] args) {
        CountNumsWithEvenOddSumDiff1 o = new CountNumsWithEvenOddSumDiff1();
        int n = 3;
        System.out.println(o.new SimpleRecursiveSolution().countNumbers(n, 0, 0));
    }
}
