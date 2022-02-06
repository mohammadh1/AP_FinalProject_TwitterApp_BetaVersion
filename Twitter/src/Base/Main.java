package Base;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
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
        try (Reader reader = Files.newBufferedReader(Paths.get("login-Request.json"))) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray value = (JsonArray) jsonObject.get("value");
            //Map<?, ?> map = gson.fromJson(jsonObject, Map.class);
            System.out.println(value);
            Iterator<JsonElement> iterator = value.iterator();
            JsonElement jsonElement = iterator.next();
            System.out.println(jsonElement.getAsJsonObject().get("password").getAsString().getClass().getSimpleName());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
