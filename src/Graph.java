import java.sql.Array;
import java.util.*;
import java.util.Arrays;

public class Graph<T> {
    Map<T, List<T>> map = new HashMap<>();
    List<List<Integer>> pathResult;
    int target;

    public void addVertex(T v) {
        map.put(v, new LinkedList<T>());
    }

    public void addEdge(T source, T destination) {
        if (!map.containsKey(source)) {
            addVertex(source);
        }
        if (!map.containsKey(destination)) {
            addVertex(destination);
        }
        if (!map.get(source).contains(destination)) {
            map.get(source).add(destination);
        }
        if (!map.get(destination).contains(source)) {
            map.get(destination).add(source);
        }
    }

    public int vertexCount() {
        return map.keySet().size();
    }

    public void edgeCount() {
        int i = 0;
        for (T v : map.keySet()) {
            i += map.get(v).size();
        }
        System.out.println("edges in graph: " + i / 2);
    }

    public boolean hasEdge(T s, T d) {
        if (map.get(s).contains(d)) {
            return true;
        }
        return false;
    }
        public void findPath(Node s, Node d) {
        HashMap<Node, Node> tempPath = new HashMap<>();
        tempPath.put(s, null);
        HashMap<Node, Double> smallestPath = new HashMap<>();
        for (Object item : map.keySet()) {
            if (s == d) {
                smallestPath.put(s, 0.0);
            } else {
                smallestPath.put(s, Double.MAX_VALUE);
            }
        }
        //Object[] test = s.edges.toArray();
        //for (int i = 0; test.length > i; i++) {
        //for (Edge edge : s.edges) {
        //smallestPath.put(s.edges.get(i).destination, s.edges.get(i).weight);
        //tempPath.put(s.edges.get(i).destination, s);
        //for (int edge = 0; edge < s.edges.size(); edge++) {
        //smallestPath.put(s.edges.get(edge).destination, s.edges.get(edge).weight);
        //int temp = s.edges.indexOf(edge);
        //System.out.println(temp);
        //smallestPath.put(temp, 1.0);
        //System.out.println("test");
        //}
        for (Integer edge : s.edges) {
            smallestPath.put(Edge.destination, Edge.weight);
            tempPath.put(Edge.destination, s);
        }
        //System.out.println(checkHere);
        s.visit();
        while (true) {
            Node cur = nearestUnvisited(smallestPath);
            if (cur == null) {
                System.out.println("no path between " + s.name + " and " + d.name);
                return;
            }
            if (cur == d) {
                System.out.println("path with shortest path between " + s.name.toString() + " and " + d.name.toString() + ": ");
                Node child = d;
                String path = d.name;
                while (true) {
                    Node par = tempPath.get(child);
                    if (par == null) {
                        break;
                    }
                    path = par.name + " " + path;
                    child = par;
                }
                System.out.println(path);
                System.out.println("The path costs: " + smallestPath.get(d));
                return;
            }
            cur.visit();
            assert cur != null;
            for (Integer edge : cur.edges) {
                if (Edge.destination.isVisited())
                    continue;
                if (smallestPath.get(cur) + Edge.weight < smallestPath.get(Edge.destination)) {
                    smallestPath.put(Edge.destination, smallestPath.get(cur) + Edge.weight);
                    tempPath.put(Edge.destination, cur);
                }
            }
        }
    }
    public Node nearestUnvisited(HashMap<Node, Double> smallestPath) {
        Double sDistance = Double.MAX_VALUE;
        Node nearest = null;
        for (Object node : map.keySet()) {
            if (Node.isVisited()) {
                continue;
            }
            Double curDistance = smallestPath.get(node);
            if (curDistance == Double.MAX_VALUE) {
                continue;
            }
            if (sDistance > curDistance) {
                sDistance = curDistance;
                nearest = (Node) node;
            }
        }
        return nearest;
    }

    public void checkNeighbor(Node s, Node d, Graph graph) {
        //System.out.println(s.edges);
        HashSet setS = new HashSet();
        setS.add(s.edges);
        //if (setS.contains(s.edges.contains(d.n))); {
        //    System.out.println("true");
        //}
        if (graph.hasEdge(s.n, d.n)) {
            System.out.println("true");
        }
        else {
            System.out.println("false");
        }



    }

/*    public void findPath(Node s, Node d, Graph graph) {
        int len = graph.vertexCount();
        int[] Vertex = new int[len];
        boolean[] Known = new boolean[len];
        int[] Path = new int[len];
        int[] bestCost = new int[len];
        for (int i = 0; i < len-1; i++) {
            Vertex[i] = i;
            Known[i] = false;
            Path[i] = -1;
            bestCost[i] = -1;
        }
        bestCost[0] = 0;


        System.out.println(Vertex[5]);
        System.out.println(Path[19]);
        System.out.println(Known[8]);
        System.out.println(bestCost[0]);
    }*/

}
