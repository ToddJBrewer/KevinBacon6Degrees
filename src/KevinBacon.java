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
    public static int actorVCount = 0;
    public static int actorKCount = 0;
    public static Hashtable actorsAsKey = new Hashtable();
    public static Hashtable actorsAsValue = new Hashtable();
    public static Graph graph = new Graph(false);
    public static Map<Integer, List<Integer>> map = new HashMap<>();



    public static void main(String[] args) {

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
                                Graph.addEdgeMap(actor_num_list.get(j), actor_num_list.get(k));
                                int sNum = actor_num_list.get(j);
                                String sName = (String) actorsAsValue.get(actor_num_list.get(j));
                                Node s = new Node(sNum, sName);

                                int dNum = actor_num_list.get(k);
                                String dName = (String) actorsAsValue.get(actor_num_list.get(k));
                                Node d = new Node(dNum, dName);


                                //this adds an edge between the two nodes but because of the way i wrote
                                //the code the nodes keep overwriting eachother which causes it not to work
                                //i think i need use my hashmap var map which properly stores the edges and
                                //add them in outside the loop. making edges a data member of the node class
                                //seems to have been a mistake
                                graph.addEdge(s, d, 1.0);

                            }
                    }
                }
                ++movies;
            }

                csvParser.close();


            } catch(Exception e){
                System.out.println("File " + args[0] + " is invalid or is in the wrong format.");
            }

        //System.out.println(graph.checkEdge(s, d));
        //graph.findPath(s, d);


        //for (int i = 0; i < actorKCount; i++) {
        //    int num = i;
        //    String name = (String) actorsAsValue.get(i);
        //    System.out.println(name);
        //
        //}


        //test code. I included this small hardcoded graph to show how
        //the logic of the program works. I couldn't get my code to work with the actor graph
        //I realized too late that I need a different method of adding nodes
        //to the graph among other problems that i ran out of time to fix
        //sorry about that
        Graph test = new Graph(false);
        Node actor1 = new Node(1, "actor 1");
        Node actor2 = new Node(2, "actor 2");
        Node actor3 = new Node(3, "actor 3");
        Node actor4 = new Node(4, "actor 4");
        Node actor5 = new Node(5, "actor 5");
        Node actor6 = new Node(6, "actor 6");

        test.addEdge(actor1, actor2, 1);
        test.addEdge(actor1, actor4, 1 );
        test.addEdge(actor2, actor3, 1 );
        test.addEdge(actor2, actor6, 1 );
        test.addEdge(actor1, actor3, 1 );

        //should find path in 3 edges
        test.findPath(actor4, actor6);
        test.resetNodes();
        //should find path in 2 edges
        test.findPath(actor1, actor6);
        test.resetNodes();
        //should fail
        test.findPath(actor3, actor5);




    }
}



