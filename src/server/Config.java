package server;

import java.io.*;
import java.util.Properties;

public class Config {
    public static final String PROPERTIES_FILE = "src/server.properties";
    public static int PORT;
    public static String VERSION_ID;
    public static int UNIQUE_ID;
    public static String DATA_PATH;
    public static String CLIENTS_DATA_FILENAME;
    public static String REGEX_HISTORY;
    public static int BUFFER_HISTORY_SIZE;

    private static Properties properties = new Properties();

    static {
        FileInputStream inputFile = null;

        try {
            inputFile = new FileInputStream(PROPERTIES_FILE);
            properties.load(inputFile);

            PORT = Integer.parseInt(properties.getProperty("PORT"));
            VERSION_ID = properties.getProperty("VERSION_ID");
            UNIQUE_ID = Integer.parseInt(properties.getProperty("UNIQUE_ID"));
            DATA_PATH = properties.getProperty("DATA_PATH");
            CLIENTS_DATA_FILENAME = properties.getProperty("CLIENTS_DATA_FILENAME");
            REGEX_HISTORY = properties.getProperty("REGEX_HISTORY");
            BUFFER_HISTORY_SIZE = Integer.parseInt(properties.getProperty("BUFFER_HISTORY_SIZE"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateProperties(){
        FileInputStream inputFile = null;

        try {
            inputFile = new FileInputStream(PROPERTIES_FILE);
            properties.load(inputFile);

            PORT = Integer.parseInt(properties.getProperty("PORT"));
            VERSION_ID = properties.getProperty("VERSION_ID");
            UNIQUE_ID = Integer.parseInt(properties.getProperty("UNIQUE_ID"));
            DATA_PATH = properties.getProperty("DATA_PATH");
            CLIENTS_DATA_FILENAME = properties.getProperty("CLIENTS_DATA_FILENAME");
            REGEX_HISTORY = properties.getProperty("REGEX_HISTORY");
            BUFFER_HISTORY_SIZE = Integer.parseInt(properties.getProperty("BUFFER_HISTORY_SIZE"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void changeID(int newID){
        try{
            properties.setProperty("PORT", String.valueOf(Config.PORT));
            properties.setProperty("VERSION_ID", Config.VERSION_ID);
            properties.setProperty("UNIQUE_ID", String.valueOf(newID));
            properties.setProperty("DATA_PATH", Config.DATA_PATH);
            properties.setProperty("CLIENTS_DATA_FILENAME", Config.CLIENTS_DATA_FILENAME);
            properties.setProperty("REGEX_HISTORY", Config.REGEX_HISTORY);
            properties.setProperty("BUFFER_HISTORY_SIZE",String.valueOf(Config.BUFFER_HISTORY_SIZE));

            OutputStream fis = new FileOutputStream(Config.PROPERTIES_FILE);
            properties.store(fis,"Changed UNIQUE_ID");
            fis.flush();

            updateProperties();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
