import java.sql.Array;
import java.util.*;
import java.util.Arrays;
import java.util.HashMap;

public class Graph {
    //stores nodes in the graph
    public static Set<Node> nodes;
    //included this but it's always set to false as the graph is undirected
    private boolean directed;
    //var to track edges in a given path
    public static int actorCount;

    //public static Map<Integer, List<Integer>> map = new HashMap<>();

    public static void addVertexMap(Integer v){
        KevinBacon.map.put(v, new LinkedList<>());
    }

    public static void addEdgeMap(Integer source, Integer destination) {
        if (!KevinBacon.map.containsKey(source)) {
            addVertexMap(source);
        }
        if (!KevinBacon.map.containsKey(destination)) {
            addVertexMap(destination);
        }
        if (!KevinBacon.map.get(source).contains(destination)) {
            KevinBacon.map.get(source).add(destination);
        }
        if (!KevinBacon.map.get(destination).contains(source)) {
            KevinBacon.map.get(destination).add(source);
        }
    }

    Graph(boolean directed) {
        this.directed = directed;
        nodes = new HashSet<>();
    }

    public void showAllEdges() {
        for (Node node : nodes) {
            LinkedList<Edge> edges = node.edges;
            if (edges.isEmpty()) {
                System.out.println(node.name + " is not connected to anything. this should not happen ");
                continue;
            }
            System.out.println(node.name + " has edges: ");
            for (Edge edge : edges) {
                System.out.println(edge.destination.name);
            }
        }

    }

    public void findPath(Node s, Node d) {
        actorCount = 0;
        HashMap<Node, Node> path = new HashMap<>();
        path.put(s, null);
        HashMap<Node, Double> bestPath = new HashMap<>();

        for (Node node : nodes) {
            if (node == s) {
                bestPath.put(node, 0.0);
            }
            else {
                bestPath.put(node, Double.MAX_VALUE);
            }
        }

        for (Edge edge : s.edges) {
            bestPath.put(edge.destination, edge.weight);
            path.put(edge.destination, s);
        }
        s.visit();
        while (true) {
            Node curNode = nearestNode(bestPath);
            if (curNode == null) {
                System.out.println("no path between the following: " + s.name + " to " + d.name);
                return;
            }
            if (curNode == d) {
                System.out.println("path found between " + s.name + " to " + d.name);
                Node c = d;
                String route = d.name;
                while (true) {
                    Node p = path.get(c);
                    if (p == null) {
                        break;
                    }
                    route = p.name + " to " + route;
                    c = p;
                    actorCount+=1;
                }
                System.out.println(route);
                System.out.println("number of actors (or edges) in the path: " + actorCount);
                return;
            }
            curNode.visit();
            for (Edge edge : curNode.edges) {
                if (edge.destination.isVisited()) {
                    continue;
                }
                if (bestPath.get(curNode) + edge.weight < bestPath.get(edge.destination)) {
                    bestPath.put(edge.destination, bestPath.get(curNode) + edge.weight);
                    path.put(edge.destination, curNode);
                }
            }

        }
    }


    private Node nearestNode(HashMap<Node, Double> bestPath) {
        double d = Double.MAX_VALUE;
        Node nearest = null;
        for (Node node : nodes) {
            if (node.isVisited()) {
                continue;
            }
            double curD = bestPath.get(node);
            if (curD == Double.MAX_VALUE) {
                continue;
            }
            if (curD < d) {
                d = curD;
                nearest = node;
            }
        }
        return nearest;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Graph{");
        sb.append("directed=").append(directed);
        sb.append('}');
        return sb.toString();
    }

    public void resetNodes() {
        for (Node node : nodes) {
            node.resetNodes();
        }
    }

    public void addEdge(Node s, Node d, double w) {
        nodes.add(s);
        nodes.add(d);
        checkDuplicate(s, d, w);
        if (!directed && s != d) {
            checkDuplicate(d, s, w);
        }
    }

    public void checkDuplicate(Node x, Node y, double w) {
        for (Edge edge : x.edges) {
            if (edge.source == x && edge.destination == y) {
                edge.weight = w;
                return;
            }
        }
        x.edges.add(new Edge(x, y, w));
    }

    //public int vertexCount() {
    //    return map.keySet().size();
    //}

/*    public void edgeCount() {
        int i = 0;
        for (T v : map.keySet()) {
            i += map.get(v).size();
        }
        System.out.println("edges in graph: " + i / 2);
    }*/

/*    public boolean hasEdge(T s, T d) {
        if (map.get(s).contains(d)) {
            return true;
        }
        return false;
    }*/

 /*   public void checkNeighbor(Node s, Node d, Graph graph) {
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



    }*/

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

            /*    public void addVertex(T v) {
        map.put(v, new LinkedList<T>());
    }*/
            //Map<T, List<T>> map = new HashMap<>();

}
