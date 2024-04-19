/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.bigtexts.util;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Clase de pruebas unitarias de PropertiesUtils
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class PropertiesUtilsTest {

    public PropertiesUtilsTest() {
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
     * Test of getProperty method, of class PropertiesUtils.
     */
    @Test
    public void testGetProperty() {
        try {
            System.out.println("getProperty");
            String key = "testKey";
            String filePath = "bigtextsTests.properties";
            String expResult = "testValue";
            String result = PropertiesUtils.getProperty(key, filePath);
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("testGetProperty() " + e.getMessage());
        }
    }

    /**
     * Test of writePropertiesFile method, of class PropertiesUtils.
     */
    @Test
    public void testWritePropertiesFile() {
        System.out.println("writePropertiesFile");
        try{
         Map<String, String> properties = new HashMap<String, String>();
         properties.put("testKey", "testValue");
         properties.put("testKey1", "testValue1");
         String filePath = "bigtextsTests.properties";
         PropertiesUtils.writePropertiesFile(properties, filePath);
        }catch(Exception e){
            fail("testWritePropertiesFile() "+e.getMessage());
        }         
    }
}
