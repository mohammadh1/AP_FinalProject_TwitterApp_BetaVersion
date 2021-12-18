package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public abstract class ConsoleViewService {
    public static void terminalStart(File file) {
        JSONParser jsonParser = new JSONParser();
        if (file.exists()) {
            try {
                Object obj = jsonParser.parse(new FileReader(file));
                JSONObject jsonObj = (JSONObject) obj;
                boolean errorState = (boolean) jsonObj.get("hasError");
                if (errorState) {
                    int errorCode = (int) jsonObj.get("errorCode");
                    System.err.println("program execution faced an error ( ERROR CODE : " + errorCode);
                } else {
                    JSONArray result = (JSONArray) jsonObj.get("Results");
                    int count = (int) jsonObj.get("count");
                    int countInternal = result.size();
                    if (count == countInternal || count > 1) {
                        Iterator<JSONObject> iterator = result.iterator();
                        while (iterator.hasNext()) {
                            System.out.println(iterator.next());
                        }
                    } else {
                        System.err.println(" result array is not what was expected, try again ");
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            System.err.println(" File has not been existed ");
        }
    }

}
