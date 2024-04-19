package co.edu.javeriana.bigtexts.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase con utilidades para el manejo de fechas
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class DateUtils {

    /**
     * Método que dada una fecha y un formato, retorna un String con la fecha
     * formateada
     *
     * @param date La fecha de entrada
     * @param format El formato
     * @return La cadena formateada
     */
    public static String getFormatedDate(Date date, String format) {
        // Create an instance of SimpleDateFormat used for formatting 
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(format);
        // Using DateFormat format method we can create a string 
        // representation of a date with the defined format.
        String result = "";
        if(date != null){
            result = df.format(date);
        }        
        return result;
    }
    
    /**
     * Método que dada una cadena de caracteres y un formato, retorna una fecha
     * @param date La cadena con la fecha
     * @param format El formato
     * @return Un objeto Date con la fecha
     * @throws ParseException 
     */
    public static Date getDateFromString(String date, String format) throws ParseException{        
        Date result = new SimpleDateFormat(format).parse(date);
        return result;
    }
}
