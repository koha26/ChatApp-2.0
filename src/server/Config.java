package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String PROPERTIES_FILE = "src/server.properties";
    public static int PORT;
    public static String VERSION_ID = "ChatApp 2.0";
    public static int UNIQUE_ID;

    static {
        Properties properties = new Properties();
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


}
