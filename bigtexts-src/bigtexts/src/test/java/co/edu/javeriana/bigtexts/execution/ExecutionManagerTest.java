/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.bigtexts.execution;

import co.edu.javeriana.bigtexts.dto.Pipeline;
import co.edu.javeriana.bigtexts.hdfs.HDFSManager;
import co.edu.javeriana.bigtexts.util.XMLUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hduser
 */
public class ExecutionManagerTest {
    
    public ExecutionManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        String strPath = "/user/hduser/2014-10-23-16-00-55/regexNerExample.txt-result";
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
     * Test of execute method, of class ExecutionManager.
     */
    @Test
    public void testExecute() throws Exception {
        System.out.println("testExecute()");
        try {
            Pipeline pipeline = (Pipeline) XMLUtils.unmarshalObjectFromFile(Pipeline.class, "PipelineWithParams.xml");
	    String pipelineXML = XMLUtils.marshalObject(pipeline, null);
            ExecutionManager.execute(pipeline, pipelineXML);
        } catch (Exception e) {
            fail("testExecute() " + e.getMessage());
        }
    }
    
}
