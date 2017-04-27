package assignment.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class Config {

    private Properties properties = new Properties();
    private static Config instance;

    private Config() {
        this.getConfig();
    }

    public static Config getInstance() {
        if(instance == null){
            instance = new Config();
        }
        return instance;
    }

    private void getConfig() {
        InputStream input = null;

        try {
            input = new FileInputStream("local.properties");

            if (input == null) {
                System.out.println("Unable to find the config file: local.properties.");
                System.out.println("See template_local.properties for details.\n");
                return;
            }

            // load the properties file
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void printProperties() {
        Enumeration<?> e = properties.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = properties.getProperty(key);
            System.out.println("Key : " + key + ", Value : " + value);
        }
    }
}
