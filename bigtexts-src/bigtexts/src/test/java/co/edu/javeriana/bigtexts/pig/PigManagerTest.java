package co.edu.javeriana.bigtexts.pig;

import co.edu.javeriana.bigtexts.dto.Pipeline;
import co.edu.javeriana.bigtexts.dto.PreprocessingTask;
import co.edu.javeriana.bigtexts.enums.DeliveryMethodEnum;
import co.edu.javeriana.bigtexts.hdfs.HDFSManager;
import co.edu.javeriana.bigtexts.util.XMLUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class PigManagerTest {

    public PigManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        String strPath = "/user/hduser/test/passwd-result";
        boolean exists = HDFSManager.exists(strPath);
        if(exists){
            HDFSManager.delete(strPath);
        }
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
     * Test of runQuery method, of class PigManager.
     */
    @Test
    public void testRunQuery() throws Exception {
        System.out.println("runQuery");
        try {
            Pipeline pipeline = new Pipeline();
            List<String> files = new ArrayList<String>();
            files.add("passwd");
            pipeline.setTargetFolder("test");
            pipeline.setFiles(files);

            PreprocessingTask snowball = new PreprocessingTask();
            snowball.setName("SnowballStemmer-EN");
            snowball.setRequiredJars(Arrays.asList("libstemmer-1.0.jar", "bigtexts-udfs-1.0.jar"));
            snowball.setCommands(Arrays.asList("co.edu.javeriana.bigtextsudfs.SNOWBALL_STEMMER_EN((chararray)$0)"));

            pipeline.setDeliveryMethod(DeliveryMethodEnum.HDFS);
            List<PreprocessingTask> preprocessingTasks = new ArrayList<PreprocessingTask>();
            preprocessingTasks.add(snowball);
            pipeline.setPreprocessingTasks(preprocessingTasks);

            PigManager.runQuery(pipeline);
        } catch (Exception e) {
            fail("testRunQuery() " + e.getMessage());
        }
    }
}
