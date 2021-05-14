import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class KevinBacon {

    public static void main(String[] args) {
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
        Graph.Node node1 = new Graph.Node(1, "test");
        Graph.Node node2 = new Graph.Node(2, "test again");
        graph.findPath(node1, node1);
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
            HashMap<Node, Integer> smallestPath = new HashMap<>();
            for (Object item : map.keySet()) {
                if (s == d) {
                    smallestPath.put(s, 0);
                } else {
                    smallestPath.put(s, Integer.MAX_VALUE);
                }
            }
            for (Edge edge : s.edges) {
                smallestPath.put(edge.destination, edge.weight);
                tempPath.put(edge.destination, s);
            }
            s.visit();
            while (true) {
                Node cur = nearestUnvisited(smallestPath);
            }


        }

        public Node nearestUnvisited(HashMap<Node, Integer> smallestPath) {
            Integer sDistance = Integer.MAX_VALUE;
            Node nearest = null;
            for (Object node : map.keySet()) {
                if (Node.isVisited()) {
                    continue;
                }
                Integer curDistance = smallestPath.get(node);
                if (curDistance == Integer.MAX_VALUE) {
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
            int weight;

            Edge(Node s, Node d) {
                source = s;
                destination = d;
                int weight = 1;
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

            public void visit() {
                visited = true;
            }

            public void unVisit() {
                visited = false;
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



