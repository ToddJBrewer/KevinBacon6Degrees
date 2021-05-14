import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import javax.xml.soap.Node;

public class KevinBacon {

    public static void main(String[] args) {
        int actorVCount = 0;
        int actorKCount = 0;
        Hashtable actorsAsKey = new Hashtable();
        Hashtable actorsAsValue = new Hashtable();
        Graph graph = new Graph();

        try {
            Reader reader = new FileReader(args[0]);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            JSONParser jsonParser = new JSONParser();

            int movies = 0;

            for (CSVRecord csvRecord : csvParser) {
                if (movies > 0) {
                    String title = csvRecord.get(1);
                    String castJSON = csvRecord.get(2);
                    // [] = array
                    // { } = "object" / "dictionary" / "hashtable" -- key "name": value

                    //System.out.println("Title: " + title);
                    Object object = jsonParser.parse(castJSON);
                    JSONArray jsonArray = (JSONArray) object;
                    List<Integer> actor_num_list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        //System.out.println(" * " + jsonObject.get("name"));
                        Object actor = jsonObject.get("name");

                        actorsAsKey.putIfAbsent(actor, actorKCount++);
                        actor_num_list.add((Integer) actorsAsKey.get(actor));
                        actorsAsValue.putIfAbsent(actorVCount++, actor);
                    }
                    for (int j = 0; j < actor_num_list.size() - 1; j++) {
                        for (int k = 0; k < actor_num_list.size(); k++) {
                            graph.addEdge(actor_num_list.get(j), actor_num_list.get(k));

                        }
                    }
                }
                ++movies;
            }

            csvParser.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("File " + args[0] + " is invalid or is in the wrong format.");
        }
        //System.out.println(graph.map);
        //System.out.println(actorsAsValue);
        //System.out.println(graph.vertexCount());
        //graph.edgeCount();
        //System.out.println(graph.hasEdge(3, 15));
        //System.out.println(graph.hasEdge(0, 1250));
        //System.out.println(graph.map.get(1));
        //Graph.Node node1 = new Graph.Node(1, "actor x");
        //Graph.Node node2 = new Graph.Node(2, "actor y");

        //Graph.Node five = new Graph.Node(5, "test");
        //five.edges = (LinkedList<Graph.Edge>) graph.map.get(5);
        //five.name = (String) actorsAsKey.get("Kevin Bacon");
        //System.out.println(five);
        //System.out.println(actorsAsKey.get(20648));
        //Object actor = actorsAsValue.get(818);
        //System.out.println(actor);

        int actor1 = (int) actorsAsKey.get("Kate Bosworth");
        Graph.Node node1 = new Graph.Node(actor1, "Kate Bosworth");
        node1.edges = (LinkedList<Graph.Edge>) graph.map.get(816);
        //System.out.println(node1);
        int actor2 = (int) actorsAsKey.get("Parker Posey");
        Graph.Node node2 = new Graph.Node(actor2, "Parker Posey");
        node2.edges = (LinkedList<Graph.Edge>) graph.map.get(818);
        //System.out.println(node2);

        graph.findPath(node1, node2);
        //System.out.println(node1.edges);

        //System.out.println(graph.map.get(1));
        //System.out.println(graph.map.get(2066));
    }

    Graph graph = new Graph();

    public static class Graph<T> {
        Map<T, List<T>> map = new HashMap<>();

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
            //Set keys = map.keySet();
            //for (Object key : keys) {
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
            for (Edge edge : s.edges) {
                //smallestPath.put(s.edges.get(i).destination, s.edges.get(i).weight);
                //tempPath.put(s.edges.get(i).destination, s);
            //for (int edge = 0; edge < s.edges.size(); edge++) {
                //Edge tempDest = s.edges.pop(edge);
                //smallestPath.put(s.edges.get(edge).destination, s.edges.get(edge).weight);
                System.out.println("test");
            }
            //}
            System.out.println(s.edges);
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
                for (Edge edge : cur.edges) {
                    if (edge.destination.isVisited())
                        continue;
                    if (smallestPath.get(cur) + edge.weight < smallestPath.get(edge.destination)) {
                        smallestPath.put(edge.destination, smallestPath.get(cur) + edge.weight);
                        tempPath.put(edge.destination, cur);
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


        public static class Edge implements Comparable<Edge> {
            Node source;
            Node destination;
            Double weight;

            Edge(Node s, Node d) {
                source = s;
                destination = d;
                Double weight = 1.0;
            }

            @Override
            public int compareTo(Edge o) {
                if (this.weight > o.weight) {
                    return 1;
                } else return -1;
            }
        }

        public static class Node {
            public static boolean visited;
            int n;
            String name;
            //boolean visited;
            LinkedList<Edge> edges;

            Node(int n, String name) {
                this.n = n;
                this.name = name;
                visited = false;
                edges = new LinkedList<>();
            }

            public static boolean isVisited() {
                return visited;
            }

            public static void visit() {
                visited = true;
            }

            public static void unVisit() {
                visited = false;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "n=" + n +
                        ", name='" + name + '\'' +
                        ", edges=" + edges +
                        '}';
            }
        }

        public void createGraph() {
        }


/*        public static void main(String[] args) {
            //int actorVCount = 0;
            int actorKCount = 0;
            Hashtable actorsAsKey = new Hashtable();
            //Hashtable actorsAsValue = new Hashtable();
            Graph graph = new Graph();

            try {
                Reader reader = new FileReader(args[0]);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
                JSONParser jsonParser = new JSONParser();

                int movies = 0;

                for (CSVRecord csvRecord : csvParser) {
                    if (movies > 0) {
                        String title = csvRecord.get(1);
                        String castJSON = csvRecord.get(2);
                        // [] = array
                        // { } = "object" / "dictionary" / "hashtable" -- key "name": value

                        //System.out.println("Title: " + title);
                        Object object = jsonParser.parse(castJSON);
                        JSONArray jsonArray = (JSONArray) object;
                        List<Integer> actor_num_list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            //System.out.println(" * " + jsonObject.get("name"));
                            Object actor = jsonObject.get("name");

                            actorsAsKey.putIfAbsent(actor, actorKCount++);
                            actor_num_list.add((Integer) actorsAsKey.get(actor));
                        }
                        for (int j = 0; j < actor_num_list.size() - 1; j++) {
                            for (int k = 0; k < actor_num_list.size(); k++) {
                                graph.addEdge(actor_num_list.get(j), actor_num_list.get(k));

                            }
                        }
                    }
                    ++movies;
                }

                csvParser.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("File " + args[0] + " is invalid or is in the wrong format.");
            }
            //System.out.println(graph.map);
            //System.out.println(actorsAsKey);
            System.out.println(graph.vertexCount());
            graph.edgeCount();
            System.out.println(graph.hasEdge(3, 15));
            System.out.println(graph.hasEdge(0, 1250));
            Node node1 = new Node(1, "test");
            Node node2 = new Node(2, "test again");
            graph.findPath(node1, node1);
        }*/

    }
}



