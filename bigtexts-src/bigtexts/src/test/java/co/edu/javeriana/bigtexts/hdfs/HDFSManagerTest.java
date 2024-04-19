package co.edu.javeriana.bigtexts.hdfs;

import co.edu.javeriana.bigtexts.dto.Pipeline;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Clase de pruebas unitarias HDFSManager
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class HDFSManagerTest {

    /**
     * Gestor de la bitácora
     */
    private static final Logger logger = Logger.getLogger(HDFSManagerTest.class.getName());
    
    public HDFSManagerTest() {
    }
    
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
     * Test of copyFilesToHDFS method, of class HDFSManager.
     */
    @Test
    public void testCopyFilesToHDFS() throws Exception {
        logger.info("copyFilesToHDFS");
        try {
            Pipeline pipeline = new Pipeline();
            List<String> files = new ArrayList<String>();
            files.add("passwd");
            HDFSManager.mkdir("test");
            pipeline.setTargetFolder("test");
            pipeline.setFiles(files);
            HDFSManager.copyFilesToHDFS(pipeline);
        } catch (Exception e) {
            fail("testCopyFilesToHDFS() " + e.getMessage());
        }
    }

    /**
     * Test of copyFilesToLocal method, of class HDFSManager.
     */
    @Test
    public void testCopyFilesToLocal() throws Exception {
        logger.info("copyFilesToLocal");
        try {
            Pipeline pipeline = new Pipeline();
            List<String> files = new ArrayList<String>();
            files.add("passwd");
            pipeline.setTargetFolder("test");
            pipeline.setFiles(files);
            HDFSManager.copyFilesToLocal(pipeline, false);
        } catch (Exception e) {
            fail("testCopyFilesToLocal() " + e.getMessage());
        }
    }

    /**
     * Test of getNumberOfActiveDatanodes method, of class HDFSManager.
     */
    @Test
    public void testGetNumberOfActiveDatanodes() throws Exception {
        logger.info("getNumberOfActiveDatanodes");
        try {
            int result = HDFSManager.getNumberOfActiveDatanodes();
            assertNotNull(result);
        } catch (Exception e) {
            fail("testGetNumberOfActiveDatanodes() " + e.getMessage());
        }
    }
}
