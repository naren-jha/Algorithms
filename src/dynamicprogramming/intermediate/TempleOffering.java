package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/temple-offerings/

public class TempleOffering {

    class SimpleButInefficient {
        /*
         * Minimum offering required for each temple is =
         * 
         * Max(# of continuous smaller temple to the left,
         * # of continuous smaller temple to the right) + 1
         */
        // T(n): O(n^2), S(n): O(1)
        public int minimumOffering(int[] t) {
            int n = t.length;
            int sum = 0;
            for (int i = 0; i < n; i++) {
                int left = 0;
                for (int j = i-1; j >= 0; j--) {
                    if (t[j] < t[j+1])
                        left++;
                    else
                        break;
                }
                
                int right = 0;
                for (int j = i+1; j < n; j++) {
                    if (t[j] < t[j-1])
                        right++;
                    else
                        break;
                }
                
                sum += Math.max(left, right) + 1;
            }
            return sum;
        }
    }
    
    class DPSolution {
        // T(n): O(n), S(n): O(n)
        public int minimumOffering(int[] t) {
            int n = t.length;
            int sum = 0;
            
            int[] left = new int[n];
            int[] right = new int[n];
            
            left[0] = 1;
            right[n-1] = 1;
            
            for (int i = 1; i < n; i++) {
                if (t[i-1] < t[i])
                    left[i] = left[i-1] + 1;
                else
                    left[i] = 1;
            }
            
            for (int i = n-2; i >= 0; i--) {
                if (t[i+1] < t[i])
                    right[i] = right[i+1] + 1;
                else
                    right[i] = 1;
            }
            
            for (int i = 0; i < n; i++) {
                sum += Math.max(left[i], right[i]);
            }
            
            return sum;
        }
    }
    
    public static void main(String[] args) {
        int[] t = {1, 4, 3, 6, 2, 1};
        TempleOffering o = new TempleOffering();
        System.out.println(o.new SimpleButInefficient().minimumOffering(t)); // 10
        System.out.println(o.new DPSolution().minimumOffering(t)); // 10
    }
}
