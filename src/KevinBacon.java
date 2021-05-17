import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Arrays;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

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
        Node node1 = new Node(actor1, "Kate Bosworth");
        node1.edges = (LinkedList<Edge>) graph.map.get(816);
        //System.out.println(node1);
        int actor2 = (int) actorsAsKey.get("Parker Posey");
        Node node2 = new Node(actor2, "Parker Posey");
        node2.edges = (LinkedList<Edge>) graph.map.get(818);
        //System.out.println(node2);

        //graph.findPath(node1, node2);
        //graph.checkNeighbor(node1, node2, graph);
        graph.findPath(node1, node2, graph);


        //System.out.println(graph.map.get(1));
        //System.out.println(graph.map.get(2066));
    }
}



