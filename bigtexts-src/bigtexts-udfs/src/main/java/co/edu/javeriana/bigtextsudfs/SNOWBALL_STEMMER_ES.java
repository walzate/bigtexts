package co.edu.javeriana.bigtextsudfs;

import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.Tuple;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.spanishStemmer;

/**
 * UDF Pig con la implementación de lematización usando snowball para español
 * @author walzate
 */
public class SNOWBALL_STEMMER_ES extends EvalFunc<String> {

    private static SnowballStemmer snowballStemmer = null;

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

            getSnowballStemmer().setCurrent(str);
            getSnowballStemmer().stem();
            String result = getSnowballStemmer().getCurrent();

            return result;
        } catch (ExecException e) {
            return "";
        }
    }

    /**
     * Método singleton para obtener la instancia del LovinsStemmer
     * @return La instancia de la implementación del LovinsStemmer
     */
    public static SnowballStemmer getSnowballStemmer() {
        if (snowballStemmer == null) {
            snowballStemmer = new spanishStemmer();
        }
        return snowballStemmer;
    }

}
