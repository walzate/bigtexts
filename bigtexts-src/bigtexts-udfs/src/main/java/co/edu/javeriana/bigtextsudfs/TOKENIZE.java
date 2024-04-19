/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.bigtextsudfs;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
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
 * UDF de tokenización
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class TOKENIZE extends EvalFunc<String> {

    /**
     * if set to true, separates words only when whitespace is encountered.
     */
    private static String whitespace;
    /**
     * Accepts the options of PTBTokenizer for example, things like
     * "americanize=false" or "strictTreebank3=true,untokenizable=allKeep".
     */
    private static String options;
    /**
     * Instancia singleton del procesador de NLP
     */
    private static StanfordCoreNLP stanfordCoreNLP;

    /**
     * Constructor con parámetros
     *
     * @param whitespace if set to true, separates words only when whitespace is
     * encountered.
     * @param options Accepts the options of PTBTokenizer for example, things
     * like "americanize=false" or "strictTreebank3=true,untokenizable=allKeep".
     */
    public TOKENIZE(String whitespace, String options) {
        TOKENIZE.whitespace = whitespace;
        TOKENIZE.options = options;
    }

    /**
     * Constructor sin parámetros
     */
    public TOKENIZE() {
    }

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
            // Iterate over all of the sentences found
            List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

            String result = "";
            for (CoreMap sentence : sentences) {
                // traversing the words in the current sentence
                // a CoreLabel is a CoreMap with additional token-specific methods
                for (CoreLabel token : sentence.get(TokensAnnotation.class)) {                    
                    result += token.originalText() + "\n";                    
                }
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
            //"tokenize, ssplit, pos, lemma, ner, parse, dcoref"
            props.put("annotators", "tokenize, ssplit");
            if (whitespace != null) {
                props.put("tokenize.whitespace", whitespace);
            }
            if (options != null) {
                props.put("tokenize.options", options);
            }
            stanfordCoreNLP = new StanfordCoreNLP(props);
        }
        return stanfordCoreNLP;
    }
}
