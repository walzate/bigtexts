package co.edu.javeriana.bigtexts.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Clase con métodos utilitarios para la gestión de archivos de propiedades
 *
 * @author Wilson Alzate Calderón
 */
public class PropertiesUtils {

    /**
     * Gestor de la bitácora
     */
    private static final Logger logger = Logger.getLogger(PropertiesUtils.class.getName());

    /**
     * Método que retorna el valor de una propiedad que se encuentra en un
     * archivo
     *
     * @param key El key a consultar
     * @param filePath La ruta del archivo en el que se va a consultar
     * @return El valor de la propiedad en el archivo
     */
    public static String getProperty(String key, String filePath) {
        Properties prop = new Properties();
        InputStream input;
        String result = null;
        try {

            input = new FileInputStream(filePath);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            result = prop.getProperty(key);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        return result;
    }

    /**
     * Método que escribe un mapa de propiedades en un archivo .properties
     *
     * @param properties La lista de propiedades
     * @param filePath La ruta del archivo destino
     */
    public static void writePropertiesFile(Map<String, String> properties, String filePath) {
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream(filePath);
            // set the properties value
            for (String key : properties.keySet()) {
                prop.setProperty(key, properties.get(key));
            }
            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            logger.error(io.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }
}
