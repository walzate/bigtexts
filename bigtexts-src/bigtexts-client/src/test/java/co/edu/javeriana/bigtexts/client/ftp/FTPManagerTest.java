/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.bigtexts.client.ftp;

import co.edu.javeriana.bigtexts.dto.Pipeline;
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
public class FTPManagerTest {

    public FTPManagerTest() {
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
     * Test of uploadFile method, of class FTPManager.
     */
    @Test
    public void testUploadFile() throws Exception {
        System.out.println("uploadFile");
        try {
            String localFileFullName = "preprocessingTasks.xml";
            String fileName = "preprocessingTasks.xml";
            String hostDir = "test";
            Pipeline pipeline = new Pipeline();
            FTPManager.uploadFile(localFileFullName, fileName, hostDir, pipeline);
        } catch (Exception e) {
            fail("testUploadFile() " + e.getMessage());
        }
    }
}
