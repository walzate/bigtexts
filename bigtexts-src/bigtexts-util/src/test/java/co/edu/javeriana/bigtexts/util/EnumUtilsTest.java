/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.bigtexts.util;

import co.edu.javeriana.bigtexts.enums.DeliveryMethodEnum;
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
public class EnumUtilsTest {

    public EnumUtilsTest() {
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
     * Test of obtenerValorEnumByNombre method, of class EnumUtils.
     */
    @Test
    public void testObtenerValorEnumByNombre() {
        try {
            System.out.println("obtenerValorEnumByNombre");
            Enum expResult = DeliveryMethodEnum.FTP;
            Enum result = EnumUtils.obtenerValorEnumByNombre(DeliveryMethodEnum.class, "FTP");
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("testObtenerValorEnumByNombre() " + e.getMessage());
        }

    }
}
