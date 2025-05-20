import java.util.Arrays;

class WiFi {

    /**
     * Implement your solution here
     */
    public static double computeDistance(int[] houses, int numOfAccessPoints) {
        int low = 0;
        Arrays.sort(houses);
        int high = houses[houses.length - 1];
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (coverable(houses, numOfAccessPoints, mid)) {
                high = mid;
            }
            else {
                low = mid + 1;
            }
        }
        return low;
    }

    /**
     * Implement your solution here
     */
    public static boolean coverable(int[] houses, int numOfAccessPoints, double distance) {
        if (houses.length == numOfAccessPoints) {
            return true;
        }
        Arrays.sort(houses); // n
        int accessPointsNeeded = 1;
        int totalCoverageDist = 0;
        int startingHouse = houses[0]; // 1
        double currAP = houses[0]; // 1
        for (int i = 0; i < houses.length - 1; i++) {
            int distBetweenHouses = houses[i + 1] - houses[i]; // 3
            if (distBetweenHouses <= 2 * distance) {
                if (totalCoverageDist + distBetweenHouses <= 2 * distance) {
                    totalCoverageDist += distBetweenHouses; // 4
                    currAP = (houses[i + 1] + startingHouse) / 2; // 3
                    continue;
                }
            }
            accessPointsNeeded++;
            currAP = houses[i + 1];
            startingHouse = houses[i + 1];
            totalCoverageDist = 0;
        }
        return accessPointsNeeded <= numOfAccessPoints;
    }
}
