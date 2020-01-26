package basic.array.problems;

// https://www.geeksforgeeks.org/find-smallest-value-represented-sum-subset-given-array/
// https://stackoverflow.com/a/21078133/4210068

public class SmallestPositiveIntegerWithRestriction {

    public static int smallestNum(int[] a) {
        
        // start with candidate as 1
        int candidate = 1;
        
        int i = 0;
        while (i < a.length) {
            if (a[i] <= candidate) {
                /*
                 * if current number is less than or equal to current candidate,
                 * then we need to find next candidate. 
                 * We know that all the numbers from 1....(candidate-1) are not valid,
                 * and now a new number a[i] is getting included, which will make all
                 * new different possible sums. We can add a[i] to all existing subsets
                 * and get new sums which will not be valid candidates. So we can a[i]
                 * to 1, 2, 3, .... up to (candidate - 1). And the max number which is 
                 * not valid now, is "candidate - 1 + a[i]", And therefore next candidate 
                 * should be immediate next number, i.e., "candidate + a[i]"
                 */                
                candidate += a[i];
                i++;
            }
            else
                break;
        }
        return candidate;
    }
    
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        System.out.println(smallestNum(a)); // 7
        
        a = new int[]{1, 10, 12, 20};
        System.out.println(smallestNum(a)); // 2
        
        a = new int[]{3, 6, 9, 10, 20, 28};
        System.out.println(smallestNum(a)); // 1
        
        a = new int[]{1, 1, 1, 2, 3};
        System.out.println(smallestNum(a)); // 9
    }
}
