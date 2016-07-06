package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by demo on 05.07.16.
 */
public class Config {
    private static final String PROPERTIES_FILE = "/server.properties";
    public static int PORT;
    public static String VERSION_ID = "ChatApp 2.0";

    static {
        Properties properties = new Properties();
        FileInputStream inputFile = null;

        try {
            inputFile = new FileInputStream(PROPERTIES_FILE);
            properties.load(inputFile);

            PORT = Integer.parseInt(properties.getProperty("PORT"));
            VERSION_ID = properties.getProperty("VERSION_ID");
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
