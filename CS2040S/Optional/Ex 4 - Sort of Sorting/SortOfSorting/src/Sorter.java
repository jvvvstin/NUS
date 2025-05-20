class Sorter {

    public static void sortStrings(String[] arr) {
        // TODO: implement your sorting function here
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (isGreaterThan(arr[j], arr[j + 1])) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    public static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static boolean isGreaterThan(String str1, String str2) {
        int first = Character.compare(str1.charAt(0), str2.charAt(0));
        int second = Character.compare(str1.charAt(1), str2.charAt(1));

        if (first > 0) {
            return true;
        } else if (first < 0) {
            return false;
        } else {
            return second > 0;
        }
    }
}
