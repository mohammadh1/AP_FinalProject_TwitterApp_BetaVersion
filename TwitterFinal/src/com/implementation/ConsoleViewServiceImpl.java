package com.implementation;


import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.interfaces.ConsoleViewService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * show the response of server in terminal
 * just print everything that is in json file
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 *
 */
public class ConsoleViewServiceImpl implements ConsoleViewService {
    public void terminalStart(File file) {
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
        if (file.length() != 0) {
            JsonArray result = null;
            try (JsonReader jsonReader = gson.newJsonReader(new FileReader(file))) {
                JsonObject jsonObj = gson.fromJson(jsonReader, JsonObject.class);
                boolean errorState = jsonObj.get("hasError").getAsBoolean();
                if (!errorState) {
                    result = jsonObj.get("result").getAsJsonArray();
                    Iterator<JsonElement> iterator = result.iterator();
                    int countInternal = result.size();
                    System.out.println("size of Json array : " + countInternal);
                    System.out.println("JSON OBJECT : ");
                    for (int i = 0; i < countInternal; i++) {
                        JsonElement element = iterator.next();
                        System.out.println(gson1.toJson(element));
                    }
                } else {
                    int errorCode = jsonObj.get("errorCode").getAsInt();
                    System.err.println("program execution faced an error ( ERROR CODE : " + errorCode + " )");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println(" File is empty");
        }
    }
}
