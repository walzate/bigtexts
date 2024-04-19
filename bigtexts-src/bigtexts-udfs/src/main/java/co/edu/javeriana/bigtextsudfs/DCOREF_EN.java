package co.edu.javeriana.bigtextsudfs;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.Tuple;

/**
 * UDF de identifición de correferencias en inglés
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class DCOREF_EN extends EvalFunc<String> {

    /**
     * Instancia singleton del procesador de NLP
     */
    private static StanfordCoreNLP stanfordCoreNLP;

    /**
     * Método que ejecuta el UDF
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
            Map<Integer, CorefChain> graph = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
            String result = "";
            if (graph != null) {
                result = graph.toString();
            }
            return result;
        } catch (ExecException e) {
            return "";
        }

    }

    /**
     * Método singleton que retorna la instancia del procesador de NLP
     * @return La instancia del procesador de NLP
     */
    public static StanfordCoreNLP getStanfordCoreNLP() {
        if (stanfordCoreNLP == null) {
            // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
            Properties props = new Properties();
            //"tokenize, ssplit, pos, lemma, ner, parse, dcoref"
            props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
            stanfordCoreNLP = new StanfordCoreNLP(props);
        }
        return stanfordCoreNLP;
    }
}
