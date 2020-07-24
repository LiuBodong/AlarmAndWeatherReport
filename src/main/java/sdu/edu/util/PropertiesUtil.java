package sdu.edu.util;

import java.util.Properties;

public class PropertiesUtil {

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
