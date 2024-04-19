package co.edu.javeriana.bigtextsudfs;

import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.Tuple;

/**
 * UDF de Pig que recibe una cadena de caracteres y lo pone en mayúsculas
 *
 * @author walzate
 */
public class UPPER extends EvalFunc<String> {

    /**
     * Método que recibe la tupla, la convierte en String y lo pone en mayúsculas
     *
     * @param input La tupla de entrada
     * @return El texto en mayúsculas
     * @throws IOException
     */
    @Override
    public String exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0) {
            return null;
        }
        try {
            String str = (String) input.get(0);
            return str.toUpperCase();
        } catch (ExecException e) {
            return "";
        }
    }
}
