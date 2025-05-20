import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BellmanFord {
    // DO NOT MODIFY THE TWO STATIC VARIABLES BELOW
    public static int INF = 20000000;
    public static int NEGINF = -20000000;

    private class Edge {
        private int from;
        private int to;
        private int weight;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object obj) {
            Edge e = (Edge) obj;
            return this.from == e.from && this.to == e.to;
        }

        @Override
        public int hashCode() {
            return this.from + this.to;
        }
    }

    // TODO: add additional attributes and/or variables needed here, if any
    private int[] dist;
    private HashMap<Edge, Integer> edges;
//    private HashMap<Edge, Integer> edges = new HashMap<>();
    private int source;

    public BellmanFord(ArrayList<ArrayList<IntPair>> adjList) {
        // TODO: initialize your attributes here, if any
        this.dist = new int[adjList.size()];

        initialiseEdgesHashMap(adjList);
    }

    private void initialiseEdgesHashMap(ArrayList<ArrayList<IntPair>> adjList) {
        this.edges = new HashMap<>();
        for (int i = 0; i < adjList.size(); i++) {
            for (int j = 0; j < adjList.get(i).size(); j++) {
                Edge e = new Edge(i, adjList.get(i).get(j).first, adjList.get(i).get(j).second);
                if (!this.edges.containsKey(e)) {
                    this.edges.put(e, e.weight);
                } else {
                    if (this.edges.get(e) < e.weight) {
                        this.edges.put(e, e.weight);
                    }
                }
            }
        }
    }

    public void computeShortestPaths(int source) {
        this.source = source;
        for (int i = 0; i < dist.length; i++) {
            dist[i] = INF;
        }
        dist[source] = 0;
        boolean allSame = true;
        for (int i = 0; i < dist.length - 1; i++) {
            for (Map.Entry<Edge, Integer> entry : this.edges.entrySet()) {
                Edge edge = entry.getKey();
                // if after one iteration, everything remains the same, we can end
                // changed = true if estimates have changed
                boolean changed = relax(edge.from, edge.to);
                if (changed) {
                    allSame = false;
                }
            }
            if (allSame) {
                break;
            }
        }
        for (Map.Entry<Edge, Integer> entry : this.edges.entrySet()) {
            Edge edge = entry.getKey();
            if (relax(edge.from, edge.to)) {
                this.dist[edge.to] = NEGINF;
            }
        }
//        this.dist[source] = 0;
        System.out.println(this.getDistance(1));
    }

    // TODO: add additional methods here, if any

    public int getDistance(int node) {
        // TODO: implement your getDistance operation here
        if (node < 0 || node >= dist.length) {
            return INF;
        }
        return this.dist[node];
    }

    public boolean relax(int from, int to) {
        int original = this.dist[to];
        if (this.dist[to] != INF || this.dist[from] != INF) {
            if (this.dist[to] != NEGINF && this.dist[to] > this.dist[from] + weight(from, to)) {
                this.dist[to] = this.dist[from] + weight(from, to);
            }
        }

        return original != this.dist[to];
    }

    public int weight(int from, int to) {
        return this.edges.getOrDefault(new Edge(from, to), 1);
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<IntPair>> adjList = initialiseAdjList();
        BellmanFord bf = new BellmanFord(adjList);
        bf.computeShortestPaths(2);
        System.out.println();
    }

    private static ArrayList<ArrayList<IntPair>> initialiseAdjList() {
        ArrayList<ArrayList<IntPair>> adjList = new ArrayList<>(5);
        ArrayList<IntPair> adjList0 = new ArrayList<>();
        adjList0.add(new IntPair(3, 2));
        adjList.add(adjList0);

        ArrayList<IntPair> adjList1 = new ArrayList<>();
        adjList1.add(new IntPair(2, -2));
        adjList.add(adjList1);

        ArrayList<IntPair> adjList2 = new ArrayList<>();
        adjList2.add(new IntPair(1, 1));
        adjList.add(adjList2);

        ArrayList<IntPair> adjList3 = new ArrayList<>();
        adjList.add(adjList3);

        ArrayList<IntPair> adjList4 = new ArrayList<>();
        adjList.add(adjList4);
        return adjList;
    }
}
