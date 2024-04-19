package co.edu.javeriana.bigtextsudfs;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

/**
 * UDF de Named Entity Recognition basado en expresiones regulares
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class REGEXNER extends EvalFunc<String> {

    /**
     * Instancia singleton del procesador de NLP
     */
    private static StanfordCoreNLP stanfordCoreNLP;

    /**
     * Gestor de la bitácora
     */
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(
	    REGEXNER.class.getName());

    /**
     * Nombre del archivo que continene las reglas
     */
    private static final String FILE_NAME = "HiddenRegexNerRules.txt";
    /**
     * Nuevas reglas a parametrizar
     */
    private static String newRules;

    /**
     * Constructor con parámetros
     * @param newRules las reglas a adicionar
     */
    public REGEXNER(String newRules) {
	this.newRules = newRules;
    }

    /**
     * Constructor sin parámetros
     */
    public REGEXNER() {	
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
	    logger.info("Tupla nula");
	    return null;
	}
	String str = (String) input.get(0);
	try {
	    logger.info("Tupla: " + str);
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
		for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
		    // this is the NER label of the token
		    String ne = token.get(NamedEntityTagAnnotation.class);
		    result += token.originalText() + ", " + ne + "\n";

		    logger.info("****************" + result);
		}
	    }
	    return result;
	} catch (Exception e) {
	    logger.info(e.getMessage());
	    e.printStackTrace();

	    return "Error: " + e.getMessage() + " Stacktrace: " + e.getStackTrace().toString() + " Tupla: " + str;
	}

    }

    /**
     * Método singleton que retorna la instancia del procesador de NLP
     *
     * @return La instancia del procesador de NLP
     */
    public static StanfordCoreNLP getStanfordCoreNLP() throws Exception {
	logger.info("Creacion stanfordCoreNLP");
	if (stanfordCoreNLP == null) {
	    // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
	    Properties props = new Properties();
	    //regexner            
	    props = new Properties();
	    //"tokenize, ssplit, pos, lemma, ner, parse, dcoref
	    props.put("annotators", "tokenize, ssplit, pos, lemma, regexner");

	    writeFile();

	    props.put("regexner.mapping", FILE_NAME);
	    //props.put("regexner.mapping", "org/foo/resources/jg-regexner.txt");            
	    stanfordCoreNLP = new StanfordCoreNLP(props);
	    logger.info("Creado stanfordCoreNLP");
	}
	return stanfordCoreNLP;
    }

    /**
     * Método que escribe el archivo con las reglas
     *
     * @throws Exception Se envía cualquier error que pueda suceder
     */
    public static void writeFile() throws Exception {

	String content = "acetaminofen\tMEDICAMENTO\n"
		+ "ibuprofeno\tMEDICAMENTO\n"
		+ "Sinutab\tMEDICAMENTO\n"
		+ "Noxpirin\tMEDICAMENTO\n"
		+ "viagra\tMEDICAMENTO\n";

	if (newRules != null) {
	    content += newRules;
	}

	File file = new File(FILE_NAME);

	// if file doesnt exists, then create it
	if (!file.exists()) {
	    file.createNewFile();
	}

	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(content);
	bw.close();

	System.out.println("Done");
    }
}
