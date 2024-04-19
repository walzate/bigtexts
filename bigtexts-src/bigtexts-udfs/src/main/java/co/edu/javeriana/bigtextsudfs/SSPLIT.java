/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.bigtextsudfs;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.Tuple;

/**
 * UDF de Partición de textos por frases
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class SSPLIT  extends EvalFunc<String> {
    /**
     * Instancia singleton del procesador de NLP
     */
    private static StanfordCoreNLP stanfordCoreNLP;

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
            // create an empty Annotation just with the given text
            Annotation document;
            document = new Annotation(str);

            // run all Annotators on this text
            getStanfordCoreNLP().annotate(document);
            // This is the coreference link graph
            // Each chain stores a set of mentions that link to each other,
            // along with a method for getting the most representative mention
            // Both sentence and token offsets start at 1!
            List<CoreMap> map = document.get(SentencesAnnotation.class);
            String result = "";
            if (map != null) {
                result = map.toString();
            }
            return result;
        } catch (ExecException e) {
            return "";
        }

    }

    /**
     * Método singleton que retorna la instancia del procesador de NLP
     *
     * @return La instancia del procesador de NLP
     */
    public static StanfordCoreNLP getStanfordCoreNLP() {
        if (stanfordCoreNLP == null) {
            // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
            Properties props = new Properties();
            //regexner
            props.put("annotators", "tokenize, ssplit");
            //props.put("regexner.mapping", "org/foo/resources/jg-regexner.txt");            
            stanfordCoreNLP = new StanfordCoreNLP(props);
        }
        return stanfordCoreNLP;
    }
}
