package basic.array.problems;

import java.util.Arrays;

public class Sort012 {

  public static void sort(int[] a) {
    int i = 0, j = a.length-1;
    while (i < j) {
      if (a[i] != 0 && a[j] == 0) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        i++; i--;
      }
      else {
        if (a[i] == 0)
          i++;
        if (a[j] != 0)
          j--;
      }
    }
    
    j = a.length-1;
    while (i < j) {
      if (a[i] == 2 && a[j] == 1) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        i++; i--;
      }
      else {
        if (a[i] != 2)
          i++;
        if (a[j] != 1)
          j--;
      }
    }
  }
  
  public static void main(String[] args) {
    int[] a = {0, 2, 1, 2, 0};
    sort(a);
    System.out.println(Arrays.toString(a));
  }
}
