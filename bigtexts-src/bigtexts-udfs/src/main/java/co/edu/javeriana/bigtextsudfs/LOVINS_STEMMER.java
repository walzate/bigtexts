package co.edu.javeriana.bigtextsudfs;

import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.Tuple;
import weka.core.stemmers.LovinsStemmer;

/**
 * UDF Pig con la implementación de lematización de Lovins
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class LOVINS_STEMMER extends EvalFunc<String> {

    private static LovinsStemmer lovinsStemmer;

    /**
     * Recibe la tupla, le calcula el lema y retorna el resultado
     *
     * @param input La tupla de entrada
     * @return El lema de la palabra de entrada usando el algoritmo Lovins
     * @throws IOException
     */
    @Override
    public String exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0) {
            return null;
        }
        try {
            String str = (String) input.get(0);
            String result = getLovinsStemmer().stem(str);
            return result;
        } catch (ExecException e) {
            return "";
        }
    }

    /**
     * Método singleton para obtener la instancia del LovinsStemmer
     * @return La instancia de la implementación del LovinsStemmer
     */
    public static LovinsStemmer getLovinsStemmer() {
        if (lovinsStemmer == null) {
            lovinsStemmer = new LovinsStemmer();
        }
        return lovinsStemmer;
    }
}
