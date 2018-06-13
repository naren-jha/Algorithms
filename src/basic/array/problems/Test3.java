package basic.array.problems;

import java.util.*;

class Emp {
	
	@Override
	public boolean equals(Object arg0) {
		return false;
	}
	
	@Override
	public int hashCode() {
		return 100;
	}
}

public class Test3 {

	public static void main(String[] args) {
		/*Emp e1 = new Emp();
		Emp e2 = new Emp();
		HashMap<Emp, String> m = new HashMap<Emp, String>();
		m.put(e1, "Tom");
		m.put(e1, "Anita");
		System.out.println(m.get(e1));*/
		
		List<Integer> l = new ArrayList<Integer>();
		l.add(1, 10); // IndexOutOfBoundsException: for memory optimization
	}

}
