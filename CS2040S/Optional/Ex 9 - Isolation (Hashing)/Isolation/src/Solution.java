import java.util.HashMap;
import java.util.Hashtable;

public class Solution {
    // TODO: Implement your solution here
    public static int solve(int[] arr) {
        int maxLength = -1;
        HashMap<Integer, Integer> map = new HashMap<>();
        int left = 0;
        int right = 0;

        for (int i : arr) {
            if (map.containsKey(i)) {
                while (map.containsKey(i)) {
                    map.remove(arr[left]);
                    left++;
                }
            }
            map.put(i, i);
            right++;
            if (right - left > maxLength) {
                maxLength = right - left;
            }
        }

        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(solve(new int[]{1, 2, 20, 4, 3, 2, 7, 9}));
        System.out.println(solve(new int[]{4, 5, 4, 5, 4, 5}));
    }
}