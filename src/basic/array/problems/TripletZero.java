package basic.array.problems;

import java.util.HashMap;
import java.util.Map;

public class TripletZero {

  // T(n): O(n^2)
  public static boolean isTripletZero(int[] a) {
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    for (int i = 0; i < a.length; i++) {
      map.put(a[i], i);
    }

    for (int i = 0; i < a.length; i++) {
      for (int j = i+1; j < a.length; j++) {
        int pairSum = a[i] + a[j];
        if (map.containsKey(-pairSum)) {
          int index = map.get(-pairSum);
          if (index != i && index != j)
            return true;
        }
      }
    }
    return false;
  }
  
  public static void main(String[] args) {
    int[] a = {2, -1, 2, 3, 1};
    System.out.println(isTripletZero(a));
  }
}
