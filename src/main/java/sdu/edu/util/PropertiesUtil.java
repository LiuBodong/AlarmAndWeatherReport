package sdu.edu.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    private final String filePath;
    private final Properties properties;

    public PropertiesUtil(String name) throws IOException {
        if (name.endsWith(".properties")) {
            this.filePath = name;
        } else {
            this.filePath = name + ".properties";
        }
        this.properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream(filePath));
    }

    public static Properties load(String name) throws Exception {
        String path;
        if (name.endsWith(".properties")) {
            path = name;
        } else {
            path = name + ".properties";
        }
        Properties properties = new Properties();
        properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(path));
        return properties;
    }

}
