package co.edu.javeriana.bigtexts.util;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;

/**
 * Clase con métodos utilitarios para la serialización y desserialización de
 * objetos a XML usando JAX-B
 *
 * @author Wilson Alzate Calderón
 */
public class XMLUtils {

    /**
     * Log de la aplicación
     */
    private static final Logger logger = Logger.getLogger(
            XMLUtils.class.getName());

    /**
     * Método que dada la ubicación de un archivo XML realiza la des
     * serialización a un objeto
     *
     * @param destinationClass La clase del objeto que se requiere desserializar
     * @param destinationPath La ubicación del archivo
     * @return El objeto resultado
     */
    public static Object unmarshalObjectFromFile(Class destinationClass, String destinationPath) {
        Object result = null;
        try {
            if (result == null) {
                File file = new File(destinationPath);
                JAXBContext jaxbContext = JAXBContext.newInstance(destinationClass);

                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                result = jaxbUnmarshaller.unmarshal(file);
                System.out.println(result);
            }
        } catch (JAXBException e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    /**
     * Método que realiza la desserialización de un objeto teniendo en cuenta la
     * representación en XML del mismo
     *
     * @param destinationClass La clase del objeto que se requiere desserializar
     * @param xml La representación XML del objeto
     * @return Un objeto de la clase destino como representación del xml de
     * entrada
     */
    public static Object unmarshalObjectFromString(Class destinationClass, String xml) {
        Object result = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(destinationClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(xml);
            result = unmarshaller.unmarshal(reader);

        } catch (JAXBException ex) {
            logger.error(ex.getMessage());
        }
        return result;
    }

    /**
     * Método cuyo objetivo es serializar a XML un objeto
     *
     * @param object El objeto a serializar
     * @param destinationPath El destino del archivo
     * @return
     */
    public static String marshalObject(Object object, String destinationPath) {
        String result = "";
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            java.io.StringWriter sw = new StringWriter();

            if (destinationPath != null) {
                File file;
                file = new File(destinationPath);
                jaxbMarshaller.marshal(object, file);
            }
            //pinta en la consola el resultado
            jaxbMarshaller.marshal(object, System.out);
            jaxbMarshaller.marshal(object, sw);
            result = sw.toString();
        } catch (JAXBException e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}
