package co.edu.javeriana.bigtexts.pig;

import co.edu.javeriana.bigtexts.dto.Pipeline;
import co.edu.javeriana.bigtexts.dto.PreprocessingTask;
import co.edu.javeriana.bigtexts.dto.PreprocessingTaskParameter;
import co.edu.javeriana.bigtexts.enums.BigTextsConstants;
import co.edu.javeriana.bigtexts.hdfs.HDFSManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pig.ExecType;
import org.apache.pig.PigServer;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.backend.executionengine.ExecJob;

/**
 * Clase con métodos de gestión con Pig
 *
 * @author Wilson Alzate Calderón
 */
public class PigManager {

    /**
     * Instancia de conexión con Pig
     */
    private static PigServer pigServer = null;
    /**
     * Cadena usada para ir manejando las variables en Pig
     */
    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Log de la aplicación
     */
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(
	    HDFSManager.class.getName());

    /**
     * Objeto singleton para obtener la conexión con Pig
     *
     * @return La instancia de conexión con pig
     */
    private static PigServer getPigServer() {
	if (pigServer == null) {
	    try {
		pigServer = new PigServer(ExecType.MAPREDUCE);
	    } catch (ExecException ex) {
		Logger.getLogger(PigManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	return pigServer;
    }

    /**
     * Método que construye y ejectuta el script pig basado en lo seleccionado
     * por el usuario
     *
     * @param pipeline El wrapper de las opciones seleccionadas por el usuario
     * para ejecución
     * @throws Exception
     */
    public static String runQuery(Pipeline pipeline) throws Exception {
	String result = "";
	//Se recorren los archivos montados
	try {
	    for (String file : pipeline.getFiles()) {
		//Variable usada 
		int i = 0;
		String loadQuery = alphabet.charAt(i) + " = load '" + pipeline.getTargetFolder() + File.separator + file
			+ "';";
		logger.info(loadQuery);
		getPigServer().registerQuery(loadQuery);
		i++;
		for (PreprocessingTask preprocessingTask : pipeline.getPreprocessingTasks()) {
		    if (preprocessingTask.getRequiredJars() != null) {

			for (String requiredJar : preprocessingTask.getRequiredJars()) {
			    getPigServer().registerJar(requiredJar);
			}
		    }
		    //Se adicionan los parámetros
		    //@see http://stackoverflow.com/questions/20081732/passing-custom-parameters-to-a-pig-udf-function-in-java
		    String alias = null;
		    if (hasValidParameters(preprocessingTask)) {
			alias = preprocessingTask.getImplementationClass().substring(preprocessingTask.getImplementationClass().lastIndexOf(".") + 1, preprocessingTask.getImplementationClass().length());
			String query = "DEFINE " + alias + " " + preprocessingTask.getImplementationClass() + "(";

			if (preprocessingTask.getParameters() != null) {
			    int k = 0;
			    for (PreprocessingTaskParameter param : preprocessingTask.getParameters()) {
				if (param.getName().equals("regexner.rules")) {
				    if (param.getValue() != null) {
					if (param.getType().equalsIgnoreCase("String")) {
					    query += "\'" + getRegexNerRule(param.getValue().toString()) + "\'";
					}
				    }
				    //appendToRegexNerRules(param.getValue(), "RegexNerRules.txt");
				} else {
				    if (param.getValue() != null) {
					if (param.getType().equalsIgnoreCase("String")) {
					    query += "\'" + param.getValue().toString() + "\'";
					} else if (param.getType().equalsIgnoreCase("Boolean")) {
					    query += "\'" + param.getValue().toString() + "\'";
					}
					if (k != preprocessingTask.getParameters().size() - 1) {
					    query += ",";
					}
				    }
				}
				k++;
			    }
			}
			query += ");";
			logger.info(query);
			getPigServer().registerQuery(query);
		    }
		    for (String command : preprocessingTask.getCommands()) {
			String query = alphabet.charAt(i) + " = foreach " + alphabet.charAt(i - 1) + " generate ";
			if (alias != null) {
			    query += command.replace(preprocessingTask.getImplementationClass(), alias);
			} else {
			    query += command;
			}
			query += ";";

			logger.info(query);
			getPigServer().registerQuery(query);
			i++;
		    }
		}
		ExecJob job
			= getPigServer().store(String.valueOf(alphabet.charAt(i - 1)), pipeline.getTargetFolder() + File.separator + file + BigTextsConstants.BIG_TEXTS_DESTINATION_SUFFIX);

		result = job.getStatus().toString();
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    e.printStackTrace();
	    throw e;
	}
	return result;
    }

    public static void runPigScript(String pigScript) {
	try {
	    getPigServer().registerScript(pigScript);
	} catch (IOException ex) {
	    Logger.getLogger(PigManager.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    /**
     * Método que evalúa si hay que enviar el UDF por defecto o con parámetros
     *
     * @param preprocessingTask La tarea de preprocesamiento a evaluar
     * @return true si llega un valor distinto al por defecto en cualquier
     * parámetro
     */
    private static boolean hasValidParameters(PreprocessingTask preprocessingTask) {
	boolean result = false;

	if (preprocessingTask.getImplementationClass() != null) {
	    for (PreprocessingTaskParameter param : preprocessingTask.getParameters()) {
		if (param.getValue() != null && !param.getValue().equals(param.getDefaultValue())) {
		    result = true;
		}
	    }
	}

	return result;
    }

    /**
     * Método que dada una regla, la convierte al formato necesario para el
     * regexNER
     *
     * @param newRegexNerRules el String con el contenido
     * @return La cadena en el formato requerido
     */
    private static String getRegexNerRule(String newRegexNerRules) {
	String result = "";
	result = newRegexNerRules.replace("=", "\t").trim().replace(",", "\n").replace(" ", "");
	return result;

    }

    private static void appendToRegexNerRules(String newRegexNerRules, String fileName) {
	try {
	    if (newRegexNerRules != null) {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
		out.println(getRegexNerRule(newRegexNerRules));
		out.close();
	    }
	} catch (IOException e) {
	    //exception handling left as an exercise for the reader
	}
    }
}
