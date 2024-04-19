package co.edu.javeriana.bigtextsudfs.sandbox;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author walzate
 */
public class StanfordTokenize {

    /**
     * Instancia singleton del procesador de NLP
     */
    protected static StanfordCoreNLP pipeline;

    /**
     *
     */
    public static void main(String args[]) {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
        Properties props;
        props = new Properties();
        //"tokenize, ssplit, pos, lemma, ner, parse, dcoref
        props.put("annotators", "tokenize, ssplit");        

        // StanfordCoreNLP loads a lot of models, so you probably
        // only want to do this once per execution
        pipeline = new StanfordCoreNLP(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation("acetaminofen ibuprofeno");

        // run all Annotators on this text
        pipeline.annotate(document);
        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
      // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {                
                System.out.println(token.originalText());
            }
        }
    }   
}
