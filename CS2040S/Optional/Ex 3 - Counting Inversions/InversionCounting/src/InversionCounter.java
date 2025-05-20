class InversionCounter {
    public static long mergeSort(int[] arr, int[] temp, int begin, int end) {
        long numInversions = 0;
        if (begin >= end) {
            return numInversions;
        }
        int mid = begin + (end - begin) / 2;
        numInversions += mergeSort(arr, temp, begin, mid);
        numInversions += mergeSort(arr, temp, mid + 1, end);
        numInversions += merge(arr, temp, begin, mid, mid + 1, end);
        return numInversions;
    }

    public static long countSwaps(int[] arr) {
        int begin = 0;
        int end = arr.length - 1;
        int[] temp = new int[arr.length];

        return mergeSort(arr, temp, begin, end);
    }

    public static long merge(int[] arr, int[] temp, int left1, int right1, int left2, int right2) {
        long numInversions = 0;
        int leftPointer = left1;
        int rightPointer = left2;
        int tempPointer = left1;

        while (leftPointer <= right1 && rightPointer <= right2) {
            if (arr[leftPointer] <= arr[rightPointer]) {
                temp[tempPointer] = arr[leftPointer];
                leftPointer++;
            } else {
                temp[tempPointer] = arr[rightPointer];
                numInversions += right1 - leftPointer + 1;
                rightPointer++;
            }
            tempPointer++;
        }

        while (rightPointer <= right2) {
            temp[tempPointer] = arr[rightPointer];
            rightPointer++;
            tempPointer++;
        }

        while (leftPointer <= right1) {
            temp[tempPointer] = arr[leftPointer];
            leftPointer++;
            tempPointer++;
        }

        for (int i = left1; i <= right2; i++) {
            arr[i] = temp[i];
        }

        return numInversions;
    }

    /**
     * Given an input array so that arr[left1] to arr[right1] is sorted and arr[left2] to arr[right2] is sorted
     * (also left2 = right1 + 1), merges the two so that arr[left1] to arr[right2] is sorted, and returns the
     * minimum amount of adjacent swaps needed to do so.
     */
    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        int[] temp = new int[arr.length];
        return merge(arr, temp, left1, right1, left2, right2);
    }
}
