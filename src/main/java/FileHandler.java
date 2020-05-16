import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class FileHandler {
    public static void saveToFile(String[] outputs) {

        JSONParser jsonParser = new JSONParser();

        try {
            Object obj = jsonParser.parse(new FileReader("movies.json")); // Create An Object From File
            JSONArray movieList = (JSONArray) obj; // Create An Array From Existing JSON File
            System.out.println(movieList);

            JSONObject movieDetails = new JSONObject(); // Create A New JSON Object
            movieDetails.put("title", outputs[0]); //   Add Some Elements
            movieDetails.put("description", outputs[1]); // Add Some Elements
            movieDetails.put("date", outputs[2]); // Add Some Elements
            movieDetails.put("grade", outputs[3]); // Add Some Elements

            movieList.add(movieDetails); // Add To Previously Imported JSON Array
            FileWriter file = new FileWriter("movies.json"); // Save New Array To The File
            file.write(movieList.toJSONString());  // JSON Format
            file.flush(); // Push Data To The File
            file.close(); // Close It

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static String[][] readFromFile() {
        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(new FileReader("movies.json")); // Create An Object From File
            JSONArray movieList = (JSONArray) obj; // Create An Array From Object

            String[][] movieListArr = new String[movieList.size()][4];

            for(int i=0; i<movieList.size(); i++) {
                JSONObject element = (JSONObject) movieList.get(i);
                movieListArr[i][0] = element.get("title").toString();
                movieListArr[i][1] = element.get("date").toString();
                movieListArr[i][2] = element.get("description").toString();
                movieListArr[i][3] = element.get("grade").toString();
            }
            return movieListArr;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
