package co.edu.javeriana.bigtexts.util;

import co.edu.javeriana.bigtexts.dto.PreprocessingTask;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Clase de pruebas unitarias de XMLUtils
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class XMLUtilsTest {

    public XMLUtilsTest() {
    }
    private static final Logger logger = Logger.getLogger(XMLUtilsTest.class.getName());

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of unmarshalObjectFromFile method, of class XMLUtils.
     */
    @Test
    public void testUnmarshalObjectFromFile() {
        logger.info("unmarshalObjectFromFile");
        try {
            Class destinationClass = PreprocessingTask.class;
            String destinationPath = "XMLTests.xml";

            PreprocessingTask snowball = new PreprocessingTask();
            snowball.setName("SnowballStemmer-EN");
            snowball.setRequiredJars(Arrays.asList("libstemmer-1.0.jar", "bigtexts-udfs-1.0.jar"));
            snowball.setCommands(Arrays.asList("co.edu.javeriana.bigtextsudfs.SNOWBALL_STEMMER_EN((chararray)$0)"));

            Object expResult = snowball;
            Object result = XMLUtils.unmarshalObjectFromFile(destinationClass, destinationPath);
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("testUnmarshalObjectFromFile() " + e.getMessage());
        }
    }

    /**
     * Test of unmarshalObjectFromString method, of class XMLUtils.
     */
    @Test
    public void testUnmarshalObjectFromString() {
        logger.info("unmarshalObjectFromString");
        try {
            Class destinationClass = PreprocessingTask.class;
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                    + "<preprocessingTask>\n"
                    + "    <name>SnowballStemmer-EN</name>\n"
                    + "    <requiredJars>\n"
                    + "        <requiredJar>libstemmer-1.0.jar</requiredJar>\n"
                    + "        <requiredJar>bigtexts-udfs-1.0.jar</requiredJar>\n"
                    + "    </requiredJars>\n"
                    + "    <commands>\n"
                    + "        <command>co.edu.javeriana.bigtextsudfs.SNOWBALL_STEMMER_EN((chararray)$0)</command>\n"
                    + "    </commands>\n"
                    + "</preprocessingTask>";
            PreprocessingTask snowball = new PreprocessingTask();
            snowball.setName("SnowballStemmer-EN");
            snowball.setRequiredJars(Arrays.asList("libstemmer-1.0.jar", "bigtexts-udfs-1.0.jar"));
            snowball.setCommands(Arrays.asList("co.edu.javeriana.bigtextsudfs.SNOWBALL_STEMMER_EN((chararray)$0)"));

            Object expResult = snowball;
            Object result = XMLUtils.unmarshalObjectFromString(destinationClass, xml);
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("testUnmarshalObjectFromString() " + e.getMessage());
        }
    }

    /**
     * Test of marshalObject method, of class XMLUtils.
     */
    @Test
    public void testMarshalObject() {
        logger.info("marshalObject");
        try {
            PreprocessingTask snowball = new PreprocessingTask();
            snowball.setName("SnowballStemmer-EN");
            snowball.setRequiredJars(Arrays.asList("libstemmer-1.0.jar", "bigtexts-udfs-1.0.jar"));
            snowball.setCommands(Arrays.asList("co.edu.javeriana.bigtextsudfs.SNOWBALL_STEMMER_EN((chararray)$0)"));
            Object object = snowball;

            String destinationPath = null;
            String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                    + "<preprocessingTask>\n"
                    + "    <name>SnowballStemmer-EN</name>\n"
                    + "    <requiredJars>\n"
                    + "        <requiredJar>libstemmer-1.0.jar</requiredJar>\n"
                    + "        <requiredJar>bigtexts-udfs-1.0.jar</requiredJar>\n"
                    + "    </requiredJars>\n"
                    + "    <commands>\n"
                    + "        <command>co.edu.javeriana.bigtextsudfs.SNOWBALL_STEMMER_EN((chararray)$0)</command>\n"
                    + "    </commands>\n"
                    + "</preprocessingTask>\n";

            String result = XMLUtils.marshalObject(object, destinationPath);
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("testMarshalObject() " + e.getMessage());
        }
    }
}
