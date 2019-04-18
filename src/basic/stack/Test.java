/*
 *  $URL$
 *  $Date$
 *  
 *  $Copyright-Start$
 *
 *  Copyright (c) 2018
 *  JDA Corporation
 *  All Rights Reserved
 *
 *  This software is furnished under a corporate license for use on a
 *  single computer system and can be copied (with inclusion of the
 *  above copyright) only for use on such a system.
 *
 *  The information in this document is subject to change without notice
 *  and should not be construed as a commitment by JDA Corporation.
 *
 *  JDA Corporation assumes no responsibility for the use of the
 *  software described in this document on equipment which has not been
 *  supplied or approved by JDA Corporation.
 *
 *  $Copyright-End$
 */

package basic.stack;



// A naive method to find maximum of minimum of all windows of
// different sizes
class Test {

	public static void printMaxOfMin(int[] arr) {
		int n = arr.length;
		
	    // Consider all windows of different sizes starting
	    // from size 1
	    for (int k=1; k<=n; k++) {
	        // Initialize max of min for current window size k
	        int maxOfMin = Integer.MIN_VALUE;
	   
	        // Traverse through all windows of current size k
	        for (int i = 0; i <= n-k; i++) {
	            // Find minimum of current window
	            int min = arr[i];
	            for (int j = 1; j < k; j++) {
	                if (arr[i+j] < min)
	                    min = arr[i+j];
	            }
	   
	            // Update maxOfMin if required
	            if (min > maxOfMin)
	            	maxOfMin = min;
	        }
	   
	        // Print max of min for current window size
	        System.out.print(maxOfMin + " ");
	    }
	}
	
	/* Method1
	 * public static int fib(int n) {
		if (n == 0 || n == 1) {
			return n;
		}
		return fib(n-1) + fib(n-2);
	}*/
	
	/* Method2
	 * public static int fib(int n) {
		int[] data = new int[n+1];
		return fibUtil(n, data);
	}
	  
	public static int fibUtil(int n, int[] data) {
		if (n == 0 || n == 1) {
			return n;
		}
		else {
			data[n] = (data[n-1] != 0 ? data[n-1] : fibUtil(n-1, data)) + (data[n-2] != 0 ? data[n-2] : fibUtil(n-2, data));
		}
		return data[n];
	}*/
	
	/* Method3
	 * public static int fib(int n) {
		int[] f = new int[n+1];
		f[0] = 0;
		f[1] = 1;
		
		for (int i = 2; i <= n; i++) {
			f[i] = f[i-1] + f[i-2];
		}
		
		return f[n];
	}*/
	
	/* Method4
	 * public static int fib(int n) {
		int a = 0, b = 1, c;
		if (n == 0)
			return 0;
		for (int i = 2; i <= n; i++) {
			c = a + b;
			a = b;
			b = c;
		}
		return b;
	}*/

	// Driver method
	public static void main(String[] args) {
		int[] arr = {-1, 20, 30, 50, 10, 70, 30};
	    printMaxOfMin(arr);
		//System.out.println(fib(8));
	}
}
