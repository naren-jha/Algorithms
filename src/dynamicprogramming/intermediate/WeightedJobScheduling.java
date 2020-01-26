package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/weighted-job-scheduling/
// https://www.geeksforgeeks.org/weighted-job-scheduling-set-2-using-lis/
// https://www.youtube.com/watch?v=cr6Ip0J9izc
// https://www.geeksforgeeks.org/weighted-job-scheduling-log-n-time/
// http://www.cs.princeton.edu/courses/archive/spr05/cos423/lectures/06dynamic-programming.pdf

public class WeightedJobScheduling {

    // This solution is similar to LIS problem solution
    
    class Job implements Comparable<Job> {
        int start, end, profit;
        
        Job(int start, int end, int profit) {
            this.start = start;
            this.end = end;
            this.profit = profit;
        }
        
        // sorting in ascending order of end time
        @Override
        public int compareTo(Job job2) {
            return this.end - job2.end;
        }
    }
    
    // T(n): O(n^2), S(n): O(n)
    public int maxProfit(Job[] jobs) {
        Arrays.sort(jobs); // sorting jobs in ascending order of end time
        
        int n = jobs.length;
        int[] dp = new int[n];
        
        dp[0] = jobs[0].profit;
        int maxProfit = dp[0];
        for (int i = 1; i < n; i++) {
            dp[i] = jobs[i].profit;
            for (int j = 0; j < i; j++) {
                if (jobs[j].end <= jobs[i].start) {
                    if (dp[i] < dp[j] + jobs[i].profit) {
                        dp[i] = dp[j] + jobs[i].profit;
                        break;
                    }
                }
            }
            if (dp[i] > maxProfit)
                maxProfit = dp[i];
        }
        
        return maxProfit;
    }
    
    /*
     * We can improve time complexity of above solution to O(nlgn) 
     * by applying binary search instead of linear search to find 'j'
     * See solutuon below
     */
    // T(n): O(nlgn), S(n): O(n)
    public int maxProfitNlgN(Job[] jobs) {
        Arrays.sort(jobs); // sorting jobs in ascending order of end time
        
        int n = jobs.length;
        int[] dp = new int[n];
        
        dp[0] = jobs[0].profit;
        int maxProfit = dp[0];
        for (int i = 1; i < n; i++) {
            dp[i] = jobs[i].profit;
            
            // apply binary search instead of linear search
            int j = -1;
            int low = 0, high = i-1, mid;
            while (low <= high) {
                mid = (low + high) / 2;
                if (jobs[mid].end <= jobs[i].start) {
                    // we have found a match, but we need to make sure
                    // that it is first such job before 'i'
                    if (jobs[mid+1].end <= jobs[i].start) {
                        low = mid+1;
                    }
                    else {
                        j = mid;
                        break;
                    }
                }
                else {
                    high = mid-1;
                }
            }
            // 'j' now has been found using binary search
            
            if (j != -1) {
                if (dp[i] < dp[j] + jobs[i].profit) {
                    dp[i] = dp[j] + jobs[i].profit;
                }
            }
            
            if (dp[i] > maxProfit)
                maxProfit = dp[i];
        }
        
        return maxProfit;
    }
    
    public static void main(String[] args) {
        WeightedJobScheduling o = new WeightedJobScheduling();
        Job[] jobs = {
                        o.new Job(3, 10, 20), 
                        o.new Job(1, 2, 50), 
                        o.new Job(6, 19, 100), 
                        o.new Job(2, 100, 200)
                     };
        System.out.println(o.maxProfit(jobs)); // 250
        System.out.println(o.maxProfitNlgN(jobs)); // 250
    }
}
