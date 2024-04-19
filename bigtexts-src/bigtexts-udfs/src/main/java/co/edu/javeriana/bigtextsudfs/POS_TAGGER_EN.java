package co.edu.javeriana.bigtextsudfs;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.Tuple;

/**
 * UDF que implementa Part Of Speech en inglés
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class POS_TAGGER_EN extends EvalFunc<String> {

    /**
     * Instancia singleton del marcador
     */
    private static MaxentTagger tagger = null;

    /**
     * Método que ejecuta el UDF
     *
     * @param input el String de entrada
     * @return El árbol de correferencias en String
     * @throws IOException
     */
    @Override
    public String exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0) {
            return null;
        }
        try {

            String str = (String) input.get(0);
            // The tagged string
            String tagged = getTagger().tagString(str);

            return tagged;
        } catch (ExecException e) {
            return "";
        }
    }

    /**
     * Método singleton que obtiene la instancia del marcador
     *
     * @return
     */
    public static MaxentTagger getTagger() {
        if (tagger == null) {

            //String taggingModel = "taggers/spanish-distsim.tagger";
            String taggingModel = "taggers/english-left3words-distsim.tagger";
            tagger = new MaxentTagger(taggingModel);
        }
        return tagger;
    }

}
