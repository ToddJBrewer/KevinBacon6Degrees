import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class KevinBacon {
    public static void main (String[] args) {

        try {
            Reader reader = new FileReader(args[0]);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            JSONParser jsonParser = new JSONParser();

            int movies = 0;

            for (CSVRecord csvRecord : csvParser) {
                if (movies == 1) {
                    String title = csvRecord.get(1);
                    String castJSON = csvRecord.get(2);
                    // [] = array
                    // { } = "object" / "dictionary" / "hashtable" -- key "name": value

                    System.out.println("Title: " + title);
                    Object object = jsonParser.parse(castJSON);

                    JSONArray jsonArray = (JSONArray)object;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                        System.out.println(" * " + jsonObject.get("name"));
                    }
                }
                ++movies;
            }

            csvParser.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("File " + args[0] + " is invalid or is in the wrong format.");
        }

    }
}
