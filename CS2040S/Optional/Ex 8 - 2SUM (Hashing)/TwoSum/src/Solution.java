import java.util.HashMap;

public class Solution {
    public static int solve(int[] arr, int target) {
        // TODO: Implement your solution here
        int count = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            if (map.containsKey(i)) {
                int currCount = map.get(i);
                map.put(i, currCount + 1);
            } else {
                map.put(i, 1);
            }
        }
        for (int i : arr) {
            int keyToFind = target - i;
            if (map.containsKey(keyToFind)) {
                if (map.get(keyToFind) > 0 && map.get(i) > 0) {
                    if (keyToFind == i) {
                        if (map.get(keyToFind) <= 1) {
                            continue;
                        }
                    }
                    count++;
                    map.put(i, map.get(i) - 1);
                    map.put(keyToFind, map.get(keyToFind) - 1);
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
//        int result = solve(new int[] {1, 1, 3, 10}, 4);
//        System.out.println(result);
        int result = solve(new int[] {1, 1, 1}, 2);
        System.out.println(result);
    }
}
