package co.edu.javeriana.bigtextsudfs;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.Tuple;

/**
 * UDF Pig que implementa el Named Entity Recognition en inglés
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class NER_EN extends EvalFunc<String> {

    private static AbstractSequenceClassifier<CoreLabel> classifier;

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
            String result = getClassifier().classifyToString(str);
            return result;
        } catch (ExecException e) {
            return "";
        }
    }

    /**
     * Método singleton que obtiene la instancia del clasificador
     * @return
     */
    public static AbstractSequenceClassifier<CoreLabel> getClassifier() {
        if (classifier == null) {
            try {
                String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";
                classifier = CRFClassifier.getClassifier(serializedClassifier);
            } catch (IOException ex) {
                Logger.getLogger(NER_EN.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassCastException ex) {
                Logger.getLogger(NER_EN.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NER_EN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return classifier;
    }
}
