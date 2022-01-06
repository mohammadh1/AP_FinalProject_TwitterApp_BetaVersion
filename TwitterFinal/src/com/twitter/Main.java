package com.twitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        //File file = new File("../");
        //System.out.println(file.getAbsolutePath());
        int port = 0;
        String ip = "";
        /*try (FileReader fileReader = new FileReader("./src/com/resources/client-application.properties")) {
            Properties properties = new Properties();
            properties.load(fileReader);
            port = Integer.parseInt(properties.getProperty("server.port"));
            ip = properties.getProperty("server.ip");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Logger logger = Logger.getLogger("log");

    }
}
