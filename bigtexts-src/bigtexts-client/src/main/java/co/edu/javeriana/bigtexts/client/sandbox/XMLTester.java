package co.edu.javeriana.bigtexts.client.sandbox;

import co.edu.javeriana.bigtexts.dto.PreprocessingTask;
import co.edu.javeriana.bigtexts.dto.PreprocessingTasksCatalog;
import co.edu.javeriana.bigtexts.util.XMLUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author hduser
 */
public class XMLTester {

    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        PreprocessingTask snowball = new PreprocessingTask();
        snowball.setName("SnowballStemmer");
        snowball.setRequiredJars(Arrays.asList("Libstemmer-1.0.jar", "BigTextsUDFs.jar"));
        snowball.setCommands(Arrays.asList("co.edu.javeriana.bigtextsudfs.gate.SNOWBALL_STEMMER"));        
        String result = XMLUtils.marshalObject(snowball, "test.xml");
        System.out.println(result);
        PreprocessingTask snowball2 = (PreprocessingTask)XMLUtils.unmarshalObjectFromString(PreprocessingTask.class, result);
        System.out.println(snowball2);
        //marshall();
        //unmarshall();
    }

    /**
     *
     */
    public static void unmarshall() {
        try {

            File file = new File("preprocessingTasks.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(PreprocessingTasksCatalog.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            PreprocessingTasksCatalog catalog = (PreprocessingTasksCatalog) jaxbUnmarshaller.unmarshal(file);
            System.out.println(catalog);

        } catch (JAXBException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     *
     */
    public static void marshall() {
        PreprocessingTasksCatalog catalog = new PreprocessingTasksCatalog();
        List<PreprocessingTask> preprocessingTasks;
        preprocessingTasks = new ArrayList<PreprocessingTask>();

        PreprocessingTask snowball = new PreprocessingTask();
        snowball.setName("SnowballStemmer");
        snowball.setRequiredJars(Arrays.asList("Libstemmer-1.0.jar", "BigTextsUDFs.jar"));
        snowball.setCommands(Arrays.asList("co.edu.javeriana.bigtextsudfs.gate.SNOWBALL_STEMMER"));
        preprocessingTasks.add(snowball);

        PreprocessingTask lovins = new PreprocessingTask();
        lovins.setName("LovinsStemmer");
        lovins.setRequiredJars(Arrays.asList("weka-stable-3.6.10", "BigTextsUDFs.jar"));
        lovins.setCommands(Arrays.asList("co.edu.javeriana.bigtextsudfs.weka.LOVINS_STEMMER"));
        preprocessingTasks.add(lovins);

        PreprocessingTask tokenizer = new PreprocessingTask();
        tokenizer.setName("Tokenizer");
        tokenizer.setCommands(Arrays.asList("TOKENIZE"));
        preprocessingTasks.add(tokenizer);
        catalog.setPreprocessingTasks(preprocessingTasks);
        try {

            File file = new File("preprocessingTasks.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(PreprocessingTasksCatalog.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(catalog, file);
            jaxbMarshaller.marshal(catalog, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
