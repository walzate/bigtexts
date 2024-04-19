/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.bigtexts.client.queue;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Clase de pruebas unitarias de QueueManager
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class QueueManagerTest {

    public QueueManagerTest() {
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
     * Test of sendMessage method, of class QueueManager.
     */
    @Test
    public void testSendMessage() throws Exception {
        System.out.println("sendMessage");
        try {
            String message = "Message";
            QueueManager.sendMessage(message);
        } catch (Exception e) {
            fail("testSendMessage() " + e.getMessage());
        }
    }
}
