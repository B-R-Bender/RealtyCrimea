package agency.realtycrimea.vk.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Bender on 30.11.2016.
 */
public class AppProperty {

    public static Properties properties;

    static {
        try {
            properties = new Properties();

            InputStream inputStream = AppProperty.class.getClassLoader().getResourceAsStream("auth.properties");
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file auth.properties not found in classpath root.");
            }
        } catch (IOException e) {
            //TODO: сделать логирование
        }
    }

}
