package server;

import java.io.*;
import java.util.Properties;

public class Config {
    public static final String PROPERTIES_FILE = "src/server.properties";
    public static int PORT;
    public static String VERSION_ID;
    public static int UNIQUE_ID;
    private static Properties properties = new Properties();

    static {
        FileInputStream inputFile = null;

        try {
            inputFile = new FileInputStream(PROPERTIES_FILE);
            properties.load(inputFile);

            PORT = Integer.parseInt(properties.getProperty("PORT"));
            VERSION_ID = properties.getProperty("VERSION_ID");
            UNIQUE_ID = Integer.parseInt(properties.getProperty("UNIQUE_ID"));

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
