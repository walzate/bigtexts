package co.edu.javeriana.bigtexts.util;

import co.edu.javeriana.bigtexts.enums.BigTextsConstants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Clase de pruebas unitarias de DateUtils
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class DateUtilsTest {

    public DateUtilsTest() {
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
     * Test of getFormatedDate method, of class DateUtils.
     */
    @Test
    public void testGetFormatedDate() {
        try {
            System.out.println("getFormatedDate");
            Date date = new SimpleDateFormat(BigTextsConstants.BIG_TEXTS_DESTINATION_FOLDER_DATE_FORMAT).parse("1987-10-07-08-07-06");
            String format = BigTextsConstants.BIG_TEXTS_DESTINATION_FOLDER_DATE_FORMAT;
            String expResult = "1987-10-07-08-07-06";
            String result = DateUtils.getFormatedDate(date, format);
            assertEquals(expResult, result);
        } catch (ParseException ex) {
            fail("testGetFormatedDate() " + ex.getMessage());
        }
    }

    /**
     * Test of getDateFromString method, of class DateUtils.
     */
    @Test
    public void testGetDateFromString() throws Exception {
        try {
            System.out.println("getDateFromString");
            String date = "1987-10-07-08-07-06";
            String format = BigTextsConstants.BIG_TEXTS_DESTINATION_FOLDER_DATE_FORMAT;
            Date expResult = new SimpleDateFormat(BigTextsConstants.BIG_TEXTS_DESTINATION_FOLDER_DATE_FORMAT).parse("1987-10-07-08-07-06");
            Date result = DateUtils.getDateFromString(date, format);
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("testGetDateFromString() " + e.getMessage());
        }
    }
}
