package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/count-number-binary-strings-without-consecutive-1s/

public class CountBinaryStringsWithoutConsecutive1 {

    public static int count(int n) {
        if (n == 0)
            return 1;
        if (n == 1)
            return 2;
        
        return count(n-1) + count(n-2);
    }
    
    // Can be easily memoized/tabulated
    
    public static int countDP(int n) {
        
        int a = 1, b = 2, c;
        for (int i = 2; i <=n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        
        return b;
    }
    
    public static void main(String[] args) {
        System.out.println(count(5)); // 13
        System.out.println(countDP(5)); // 13
    }
}
