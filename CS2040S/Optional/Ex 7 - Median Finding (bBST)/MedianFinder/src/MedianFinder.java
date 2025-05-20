import java.util.TreeMap;
import java.util.TreeSet;

public class MedianFinder {

    // TODO: Include your data structures here
    TreeMap<Integer, Integer> tree;
    int size = 0;


    public MedianFinder() {
        // TODO: Construct/Initialise your data structures here
        this.tree = new TreeMap<>();
    }

    public void insert(int x) {
        // TODO: Implement your insertion operation here
        if (tree.containsKey(x)) {
            tree.put(x, tree.get(x) + 1);
        } else {
            tree.put(x, 1);
        }
        this.size++;
    }

    public int getMedian() {
        // TODO: Implement your getMedian operation here
        int size = this.size;
        int medianIndex;
        if (size % 2 == 0) {
            medianIndex = size / 2 + 1;
        } else {
            medianIndex = (size + 1) / 2;
        }
        int[] nums = new int[medianIndex - 1];
        for (int i = 0; i < medianIndex - 1; i++) {
            int key = this.tree.firstKey();
            nums[i] = key;
            if (this.tree.get(key) == 1) {
                this.tree.remove(key);
            } else {
                this.tree.put(key, this.tree.get(key) - 1);
            }
            this.size--;
        }
        int median = this.tree.firstKey();
        this.tree.remove(median);
        this.size--;
        for (int i : nums) {
            this.insert(i);
        }
        return median;
    }

//    public static void main(String[] args) {
//        MedianFinder mf = new MedianFinder();
//        mf.insert(4);
//        mf.insert(2);
//        mf.insert(3);
//        mf.insert(1);
//        for (int i = 0; i < 4; i++) {
//            System.out.println(mf.getMedian());
//        }
////        mf.insert(8);
////        mf.insert(2);
////        mf.insert(7);
////        mf.insert(1);
////        median = mf.getMedian();
////        System.out.println();
////        median = mf.getMedian();
//        System.out.println();
//    }
}
