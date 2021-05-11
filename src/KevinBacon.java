import java.io.FileReader;
import java.io.Reader;
import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class KevinBacon {


    public static class Graph<T> {
            Map<T, List<T>> map = new HashMap<>();

            public void addVertex (T v){
                map.put(v, new LinkedList<T>());
            }

            public void addEdge(T source, T destination) {
                if (!map.containsKey(source)) {
                    addVertex(source);
                }
                if (!map.containsKey(destination)) {
                    addVertex(destination);
                }
                map.get(source).add(destination);
                map.get(destination).add(source);
            }
        }




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

                    System.out.println("Title: " + title);
                    Object object = jsonParser.parse(castJSON);
                    JSONArray jsonArray = (JSONArray) object;
                    List<Integer> actor_num_list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        System.out.println(" * " + jsonObject.get("name"));
                        Object actor = jsonObject.get("name");

                        actorsAsKey.putIfAbsent(actor, actorKCount++);

                        //actor_num_list.add((Integer) actor);
                        //
                        //this line of code is breaking the program
                        //figure out why
                    }
                    //for (int j = 0; j < actor_num_list.size() - 2; j++) {
                        //for (int k = 0; k < actor_num_list.size() - 1; k++) {
                        //    graph.addEdge(actor_num_list.get(j), actor_num_list.get(k));
                        //    System.out.println("test");

                        //}
                    //}
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
    }

}

