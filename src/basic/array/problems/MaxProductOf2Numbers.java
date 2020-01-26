package basic.array.problems;

public class MaxProductOf2Numbers {

    public static int maxProduct(int[] a) {
        if (a.length < 2)
            return -1;
        
        int fMax = 0, sMax = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > fMax) {
                sMax = fMax;
                fMax = a[i];
            }
            else if (a[i] > sMax)
                sMax = a[i];
        }
        return fMax*sMax;
    }
    
    public static void main(String[] args) {
        int[] a = {1, 100, 42, 4, 23}; // 4200
        System.out.println(maxProduct(a));
    }
}
