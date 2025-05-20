import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class TSPGraph implements IApproximateTSP {

    @Override
    public void MST(TSPMap map) {
        // TODO: implement this method
        boolean[] visited = new boolean[map.getCount()];
        HashMap<Integer, Integer> parentMap = new HashMap<>();
        TreeMapPriorityQueue<Double, Integer> priorityQueue = new TreeMapPriorityQueue<>();
        priorityQueue.add(0, 0.0);
        // n iterations - O(n)
        for (int i = 1; i < map.getCount(); i++) {
            priorityQueue.add(i, Double.MAX_VALUE); // log (n)
        }
        // n iterations
        while (!priorityQueue.isEmpty()) {
            int minIndex = priorityQueue.extractMin(); // log (n)
            TSPMap.Point p = map.getPoint(minIndex);
            if (visited[minIndex]) {
                continue;
            }
            visited[minIndex] = true;
            for (int i = 0; i < map.getCount(); i++) { // n iterations
                if (map.getPoint(i).equals(p) || visited[i]) {
                    continue;
                }
                double distanceBetweenTwoPoints = p.distance(map.getPoint(i));
                if (distanceBetweenTwoPoints < priorityQueue.lookup(i)) {
                    priorityQueue.decreasePriority(i, distanceBetweenTwoPoints); // log (n)
                    parentMap.put(i, minIndex);
                }
            }
        }
        // n iterations - O(n)
        for (HashMap.Entry<Integer, Integer> entry : parentMap.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            map.setLink(key, value);
        }
    }

    @Override
    public void TSP(TSPMap map) {
        MST(map); // O(n^2 log n)
        // TODO: implement the rest of this method.
        boolean[] visited = new boolean[map.getCount()];
        int src = 0;
        Stack<Integer> stack = new Stack<>();
        // initialising adjacency list
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
        // n iterations
        for (int i = 0; i < map.getCount(); i++) {
            adjList.add(new ArrayList<>());
        }
        // n iterations
        for (int i = 0; i < map.getCount(); i++) {
            int link = map.getLink(i);
            if (link != -1) {
                adjList.get(link).add(i);
                adjList.get(i).add(link);
            }
        }

        stack.push(src);
        visited[src] = true;

        int parent = -1;
        // n iterations
        while (!stack.isEmpty()) {
            int curr = stack.pop();
            // at most each vertice has n neighbours, i.e. n iterations
            for (int neighbour : adjList.get(curr)) {
                if (!visited[neighbour]) {
                    visited[neighbour] = true;
                    stack.push(neighbour);
                }
            }

            if (parent != -1) {
                map.setLink(curr, parent);
            }
            parent = curr;
        }
        map.setLink(src, parent);
    }

    @Override
    public boolean isValidTour(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        int start = 0;
        int curr = start;
        HashMap<Integer, Integer> linkMap = new HashMap<>();
        for (int i = 0; i < map.getCount(); i++) {
            int next = map.getLink(curr);
            linkMap.put(curr, next);
            curr = next;
        }
        return linkMap.size() == map.getCount() && curr == start;
    }

    @Override
    public double tourDistance(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        int start = 0;
        int curr = start;
        double dist = 0;
        for (int i = 0; i < map.getCount(); i++) {
            int next = map.getLink(curr);
            dist += map.pointDistance(curr, next);
            curr = next;
        }
        return dist;
    }

    public static void main(String[] args) {
//        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "fiftypoints.txt");
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "tenpoints.txt");
        TSPGraph graph = new TSPGraph();

//        graph.MST(map);
        graph.TSP(map);
//         System.out.println(graph.isValidTour(map));
        System.out.println(graph.tourDistance(map));
    }
}
