package basic.array.problems;

public class SumOfDistinctElementsInRange1toN {
    
    // solution is similar to CountFrequenciesOfAllElements problem

    public static int sum(int[] a) {
        int i = 0, n = a.length, sum = 0;
        while (i < n) {
            if (a[i] <= 0) {
                i++;
                continue;
            }
            
            int ei = a[i] - 1; // element index
            if (a[ei] < 0) {
                a[i] = 0;
                i++;
            }
            else {
                sum += a[i];
                a[i] = a[ei];
                a[ei] = -1;
            }
        }
        return sum;
    }
    
    public static void main(String[] args) {
        int[] a = {5, 1, 2, 4, 6, 7, 3, 6, 7};
        System.out.println(sum(a)); // 28
        
        a = new int[]{1, 1, 1, 1, 1};
        System.out.println(sum(a)); // 1
    }
}
