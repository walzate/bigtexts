/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.bigtexts.persistence;

import co.edu.javeriana.bigtexts.entities.ExecutionLog;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Clase de pruebas de PersistenceManager
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class PersistenceManagerTest {

    public PersistenceManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
        if(id != null){
          PersistenceManager.removeExecutionLog(id);  
        }        
    }

    @Before
    public void setUp() {        
    }

    @After
    public void tearDown() {
    }

    //Variable para almacenar el id del log
    private static Integer id;
    //Logger
    private static final Logger logger = Logger.getLogger(PersistenceManagerTest.class.getName());
    
    /**
     * Test of persistExecutionLog method, of class PersistenceManager.
     */
    @Test
    public void testPersistExecutionLog() {
        System.out.println("persistExecutionLog");
        try {
            ExecutionLog executionLog = new ExecutionLog();
            PersistenceManager.persistExecutionLog(executionLog);
            id = executionLog.getId();
            logger.info("Id del log recién almacenado: "+id);
        } catch (Exception e) {
            fail("testPersistExecutionLog() " + e.getMessage());
        }

    }
    

    /**
     * Test of getAllExecutionLogs method, of class PersistenceManager.
     */
    @Test
    public void testGetAllExecutionLogs() {
        System.out.println("getAllExecutionLogs");
        try {            
            List result = PersistenceManager.getAllExecutionLogs();
            assertNotNull(result);
        } catch (Exception e) {
            fail("testGetAllExecutionLogs() " + e.getMessage());
        }
    }   
}
