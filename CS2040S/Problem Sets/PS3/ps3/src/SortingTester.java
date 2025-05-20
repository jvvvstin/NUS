import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class SortingTester {
    public static boolean checkSort(ISort sorter, int size) {
        // TODO: implement this
        if (size == 0 || size == 1) {
            return true;
        }

        KeyValuePair[] testArray = generateRandomArray(size);

        sorter.sort(testArray);

        for (int i = 1; i < size; i++) {
            if (!(testArray[i - 1].getKey() <= testArray[i].getKey())) {
                return false;
            }
        }
        return true;
    }

    public static boolean isStable(ISort sorter, int size) {
        // TODO: implement this
        if (size == 0 || size == 1) {
            return true;
        }

        Random rand = new Random();
        KeyValuePair[] testArray = new KeyValuePair[size];
        for (int i = 0; i < size; i++) {
            int randInt = rand.nextInt(size + 1);
            testArray[i] = new KeyValuePair(randInt, i);
        }

        sorter.sort(testArray);

        for (int i = 1; i < size; i++) {
            if (testArray[i - 1].getKey() == testArray[i].getKey()) {
                if (testArray[i - 1].getValue() > testArray[i].getValue()) {
                    return false;
                }
            }
        }

        return true;
    }

    public static KeyValuePair[] generateRandomArray(int size) {
        Random rand = new Random();

        KeyValuePair[] testArray = new KeyValuePair[size];
        for (int i = 0; i < size; i++) {
            testArray[i] = new KeyValuePair(rand.nextInt(size + 1), 0);
        }

        return testArray;
    }

    public static boolean isDrEvil(ISort sortingObject) {
        Random rand = new Random();

        for (int i = 1; i <= 10000; i++) {
            if (!checkSort(sortingObject, rand.nextInt(101))) {
                return true;
            }
        }
        return false;
    }

    public static void testDrEvil() {
        System.out.println("=== Finding Dr Evil ===");
        ISort sortingObject = new SorterA();
        String str = String.format("Sorter A is Dr Evil: %b", SortingTester.isDrEvil(sortingObject));
        System.out.println(str);

        sortingObject = new SorterB();
        str = String.format("Sorter B is Dr Evil: %b", SortingTester.isDrEvil(sortingObject));
        System.out.println(str);

        sortingObject = new SorterC();
        str = String.format("Sorter C is Dr Evil: %b", SortingTester.isDrEvil(sortingObject));
        System.out.println(str);

        sortingObject = new SorterD();
        str = String.format("Sorter D is Dr Evil: %b", SortingTester.isDrEvil(sortingObject));
        System.out.println(str);

        sortingObject = new SorterE();
        str = String.format("Sorter E is Dr Evil: %b", SortingTester.isDrEvil(sortingObject));
        System.out.println(str);

        sortingObject = new SorterF();
        str = String.format("Sorter F is Dr Evil: %b", SortingTester.isDrEvil(sortingObject));
        System.out.println(str);
    }

    public static KeyValuePair[] generateSortedArray(int size) {
        KeyValuePair[] testArray = new KeyValuePair[size];
        for (int i = 0; i < size; i++) {
            testArray[i] = new KeyValuePair(i, 0);
        }
        return testArray;
    }

    public static KeyValuePair[] generateReverseSortedArray(int size) {
        KeyValuePair[] testArray = generateSortedArray(size);
        Arrays.sort(testArray, Collections.reverseOrder());
        return testArray;
    }

    public static void testSorterA() {
        System.out.println("\n=== Testing Sorter A ===");
        // running this block of code here shows that Sorter A is stable
        // since Sorter A is stable, it might be possible that Sorter A is Merge Sort, Bubble
        // Sort or Insertion Sort
        ISort sortingObject = new SorterA();
        int size = 1000;
        String str = String.format("Sorter A is stable: %b", isStable(sortingObject, size));
        System.out.println(str);

        // this block of code proceeds to use Sorter A to sort both already sorted arrays and
        // a reverse sorted array. if the cost returned is the same, then Sorter A is Merge Sort.
        // if the cost is much lower on the already sorted array as compared to the reverse
        // sorted array, then Sorter A might be Bubble or Insertion Sort.
        KeyValuePair[] testArray = generateSortedArray(size);
        str = String.format("\nCost to sort sorted array of size %d: %d",
                testArray.length, sortingObject.sort(testArray));
        System.out.println(str);

        testArray = generateReverseSortedArray(size);
        str = String.format("Cost to sort reverse sorted array of size %d: %d",
                testArray.length, sortingObject.sort(testArray));
        System.out.println(str);
    }

    public static void testSorterC() {
        System.out.println("\n=== Testing Sorter C ===");
        // running this block of code here shows that Sorter C is stable
        // since Sorter C is stable, it might be possible that Sorter C is Bubble Sort, Merge
        // Sort or Insertion Sort
        ISort sortingObject = new SorterC();
        int size = 1000;
        String str = String.format("Sorter C is stable: %b", isStable(sortingObject, size));
        System.out.println(str);

        // 10, 9, 1, 2, 3, 4, 5, 6, 7, 8
        // simplification of the array generated
        // if Sorter C is Bubble Sort, its expected cost would not be that high since the array
        // is nearly sorted, and would require few iterations to move the largest 2 elements
        // from its current index at the front of the array all the way to the back of the array
        // after doing so, the array will be considered to be sorted.
        KeyValuePair[] testArray = generateSortedArray(size);
        testArray[0] = new KeyValuePair(2000, 0);
        testArray[1] = new KeyValuePair(2001, 0);
        sortingObject = new SorterC();
        str = String.format("\nCost to sort this array: %d", sortingObject.sort(testArray));
        System.out.println(str);

        // 3, 4, 5, 6, 7, 8, 9, 10, 1, 2
        // for this array, if Sorter C is Bubble Sort, its expected cost would be much higher in
        // comparison to the previous array since, even though the array is nearly sorted, more
        // iterations would be needed to complete sorting the array, going by Bubble Sort's loop
        // invariant whereby after k iterations, the k largest elements would be sorted. since the
        // smallest elements are now at the end of the array, more iterations would be needed to
        // completely sort the array
        testArray = generateSortedArray(size);
        testArray[999] = new KeyValuePair(1, 0);
        testArray[998] = new KeyValuePair(2, 0);
        str = String.format("Cost to sort this array: %d", sortingObject.sort(testArray));
        System.out.println(str);

        // 3, 4, 5, 6, 7, 1, 2, 8, 9, 10
        // lastly, for this array, if Sorter C is Bubble Sort, its expected cost would be somewhere
        // in between the previous two costs, since its nearly sorted but the smallest elements are
        // slotted in the middle of the array. the larger half of the array has already been sorted
        // and thus only the first lower half of the array will need to be sorted. thus the number
        // of steps required as well as cost of the sorting operation should be expected to be in
        // between the first two arrays.
        testArray = generateSortedArray(size);
        testArray[500] = new KeyValuePair(1, 0);
        testArray[501] = new KeyValuePair(2, 0);
        str = String.format("Cost to sort this array: %d", sortingObject.sort(testArray));
        System.out.println(str);
    }

    public static void testSorterD() {
        System.out.println("\n=== Testing Sorter D ===");
        // running this block of code here shows that Sorter D is not stable
        // since Sorter D is not stable, it might be possible that Sorter D is Selection Sort or
        // Quick Sort
        ISort sortingObject = new SorterD();
        int size = 1000;
        String str = String.format("Sorter D is stable: %b", isStable(sortingObject, size));
        System.out.println(str);

        // running this function shows that Sorter D on the same constant arrays have
        // similar costs and the cost does not differ by much from one run to another.
        // this shows that Sorter D's sorting efficiency on the same array is constant
        // If Sorter E was indeed Quick Sort, its cost would vary between each iteration,
        // since its time complexity can be as good as O(n log n) and as bad as O(n^2).
        testMultipleRunsOnSameArray(sortingObject, 10);

        // this block of code here proceeds to test both the best and worst possible
        // arrays. if Sorter D is indeed Selection Sort, it would be seen that the
        // cost of sorting both arrays would not differ much since the time complexity
        // of Selection Sort is always O(n^2)
        KeyValuePair[] testArray = generateSortedArray(size);
        str = String.format("\nCost to sort sorted array of size %d: %d", testArray.length, sortingObject.sort(testArray));
        System.out.println(str);

        testArray = generateReverseSortedArray(size);
        str = String.format("Cost to sort reverse sorted array of size %d: %d", testArray.length, sortingObject.sort(testArray));
        System.out.println(str);
        // seeing the cost, it can be seen that their costs indeed do not vary much and
        // is quite similar to each other despite sorting both the best and worst possible
        // arrays. thus, Sorter B is Selection Sort.
    }

    public static void testMultipleRunsOnSameArray(ISort sortingObject, int numRuns) {
        int size = 1000;
        for (int i = 0; i < numRuns; i++) {
            KeyValuePair[] testArray = generateSortedArray(size);
            long sortCost = sortingObject.sort(testArray);
            String str = String.format("Cost to sort array of size %d: %d", testArray.length, sortCost);
            System.out.println(str);
        }
    }

    public static void testSorterE() {
        System.out.println("\n=== Testing Sorter E ===");
        // running this block of code here shows that Sorter E is not stable
        // since Sorter E is not stable, it might be possible that Sorter E is Quick Sort or
        // Selection Sort
        ISort sortingObject = new SorterE();
        int size = 1000;
        String str = String.format("Sorter E is stable: %b", isStable(sortingObject, size));
        System.out.println(str);

        // running this block of code here shows that Sorter E on the same constant arrays
        // have differing costs and the cost varies from one run to another
        // this shows that Sorter E's sorting efficiency on the same array is not constant
        // and is dependent on other factors such as whether a good pivot is chosen for
        // Quick Sort. If Sorter E was indeed Selection Sort, its cost would stay roughly
        // similar for different iterations since its time complexity is O(n^2) and its cost
        // is unlikely to differ between each run

        testMultipleRunsOnSameArray(sortingObject, 10);
        // seeing the different costs on multiple different runs, it can be seen that their
        // costs indeed can vary by a lot despite sorting the same arrays during each iteration.
        // thus, Sorter E is indeed Quick Sort.
    }

    public static void testSorterF() {
        System.out.println("\n=== Testing Sorter F ===");
        // running this block of code here shows that Sorter F is stable
        // since Sorter F is stable, it might be possible that Sorter F is Insertion Sort,
        // Merge Sort or Bubble Sort
        ISort sortingObject = new SorterF();
        int size = 1000;
        String str = String.format("Sorter F is stable: %b", isStable(sortingObject, size));
        System.out.println(str);

        // 10, 9, 1, 2, 3, 4, 5, 6, 7, 8
        // if Sorter C is Insertion Sort, its expected cost would not be that high since the array
        // is nearly sorted, but would require a few iterations to move the smallest elements
        // from its current index at the "back" of the array all the way to the starting of the array
        // after doing so, the array will be considered to be sorted.
        KeyValuePair[] testArray = generateSortedArray(size);
        testArray[0] = new KeyValuePair(2000, 0);
        testArray[1] = new KeyValuePair(2001, 0);
        str = String.format("Cost to sort this array: %d", sortingObject.sort(testArray));
        System.out.println(str);

        // 3, 4, 5, 6, 7, 8, 9, 10, 1, 2
        // for this array, if Sorter F is Insertion Sort, its expected cost would be similar in
        // comparison to the previous array. likewise to the previous array, the number of steps
        // or swaps needed to move the smallest 2 elements from the back of the array to the front
        // of the array is quite similar to the previous array arrangement. thus, the expected cost
        // would not vary by much in comparison.
        testArray = generateSortedArray(size);
        testArray[999] = new KeyValuePair(1, 0);
        testArray[998] = new KeyValuePair(2, 0);
        str = String.format("Cost to sort this array: %d", sortingObject.sort(testArray));
        System.out.println(str);

        // 3, 4, 5, 6, 7, 1, 2, 8, 9, 10
        // lastly, for this array, if Sorter F is Insertion Sort, its expected cost would be lower
        // than the previous two arrays. for this array, since it is nearly sorted but the smallest
        // elements are slotted in the middle of the array, the larger half of the array has already
        // been sorted and thus, the smallest two elements only need to swap positions with lesser
        // elements in comparison to the previous arrays. thus the number of steps or swaps required
        // would result in a lower cost output by the sorting algorithm.
        testArray = generateSortedArray(size);
        testArray[500] = new KeyValuePair(1, 0);
        testArray[501] = new KeyValuePair(2, 0);
        str = String.format("Cost to sort this array: %d", sortingObject.sort(testArray));
        System.out.println(str);
    }

    public static void main(String[] args) {
        // TODO: implement this
        int size = 1000;
        // testStableSorters(size);
        // running this function, we see that Sorters B, E and F are not stable
        // algorithms that are not stable
        // - Selection sort
        // - Quick sort

        // merge, bubble, insertion, dr evil
        testDrEvil();
        // running this function, we see that Sorter B is Dr Evil since after running 10,000
        // iterations of sorting randomly generated key-value pairs, it is not able to sort
        // every single array correctly
        testSorterA(); // merge sort

        testSorterC(); // bubble sort

        testSorterD(); // selection sort

        testSorterE(); // quick sort

        testSorterF(); // insertion sort
    }
}
