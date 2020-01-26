package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/sum-of-all-substrings-of-a-string-representing-a-number/

public class SumOfAllSubstrings {

    public static int sumOfSubstrings(String s) {
        int n = s.length();
        int[] digitsum = new int[n];
        
        digitsum[0] = s.charAt(0) - '0';
        int res = digitsum[0];
        for (int i = 1; i < n; i++) {
            int num_i = s.charAt(i) - '0';
            digitsum[i] = num_i*(i+1) + 10*digitsum[i-1];
            res += digitsum[i];
        }
        return res;
    }
    
    public static void main(String[] args) {
        System.out.println(sumOfSubstrings("1234"));
    }
}
