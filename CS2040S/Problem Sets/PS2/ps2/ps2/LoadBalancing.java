import java.util.Arrays;

/**
 * Contains static routines for solving the problem of balancing m jobs on p processors
 * with the constraint that each processor can only perform consecutive jobs.
 */
public class LoadBalancing {

    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */
    public static boolean isFeasibleLoad(int[] jobSizes, int queryLoad, int p) {
        // TODO: Implement this
        int numJobs = jobSizes.length; // 1
        int numProcessorsNeeded = 1; // 1
        int currProcessorLoad = 0; // 1
        if (queryLoad <= 0 || p <= 0 || numJobs == 0) {
            return false; // 1
        } else {
            // n
            for (int i = 0; i < numJobs; i++) {
                if (jobSizes[i] > queryLoad) {
                    return false; // 1
                }
                if (currProcessorLoad + jobSizes[i] <= queryLoad) {
                    currProcessorLoad += jobSizes[i]; // 1
                } else {
                    numProcessorsNeeded++; // 1
                    currProcessorLoad = jobSizes[i]; // 1
                }
            }
        }
        // total = 4 + 4n = O(n)
        return numProcessorsNeeded <= p;
    }

    private static int getLoad(int[] jobSizes) {
        int total = 0; // 1
        // n
        for (int i = 0; i < jobSizes.length; i++) {
            total += jobSizes[i];
        }
        return total;
    }

    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param p the number of processors
     * @return the maximum load for a job assignment that minimizes the maximum load
     */
    public static int findLoad(int[] jobSizes, int p) {
        // TODO: Implement this
        // check if p and jobSizes are valid inputs
        if (p <= 0 || jobSizes.length == 0) {
            return -1;
        }

        // calculate the total load of all the jobs
        // to be used as end for binary search
        int max = LoadBalancing.getLoad(jobSizes); // n
        int min = 0;

        // binary search to find the minimum achievable load
        // this binary search tries to find the lowest possible
        // query load that is feasible for p number of processors
        // since we wish to find the minimum achievable load, we can
        // make use of the isFeasibleLoad() function to find what is the
        // smallest possible queryLoad we can use that is still a feasible
        // load for p number of processors
        // n * log(nM)
        while (min < max) {
            int mid = min + (max - min) / 2;

            // n
            if (LoadBalancing.isFeasibleLoad(jobSizes, mid, p)) {
                max = mid;
            } else {
                min = mid + 1;
            }
        }
        // total = n + n log(nM) = O(n log nM + n)
        return min;
    }

    // These are some arbitrary testcases.
    public static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, 100, 80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83},
            {7}
    };

    /**
     * Some simple tests for the findLoad routine.
     */
    public static void main(String[] args) {
        for (int p = 1; p < 30; p++) {
            System.out.println("Processors: " + p);
            for (int[] testCase : testCases) {
                System.out.println(findLoad(testCase, p));
            }
        }
    }
}
