package Base;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // write your code here
        //CommandParserService commandParserService = new CommandParserService();
        //RequestParser.commandParser(new File("tweet-Request.json"));
        Gson gson = new Gson();
        try (JsonReader json = gson.newJsonReader(new FileReader("Request.json"))){
            JsonObject jsonObj = gson.fromJson(json, JsonObject.class);
            String method = jsonObj.get("method").getAsString();
            System.out.println(method);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
