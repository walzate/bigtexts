package co.edu.javeriana.bigtexts.sandbox;

import java.io.IOException;
import org.apache.pig.ExecType;
import org.apache.pig.PigServer;

/**
 * Clase de prueba del UDF de Stemming
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class GateSnowballStemmerUDFsTest {

	/**
         * Método de inicio de ejecución
	 * @param args los argumentos de ejecución
	 */
	public static void main(String[] args) {
		
		PigServer pigServer;
		try {
			pigServer = new PigServer(ExecType.MAPREDUCE);
			runMyQuery(pigServer, "passwd", "stemming3B");
                        runMyQuery(pigServer, "stemming2.txt", "stemming4B");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
         * Método que 
	 * @param pigServer
	 * @param inputFile
         * @param outputFile 
         * @throws IOException
	 * @see https://wiki.apache.org/pig/EmbeddedPig
	 */
	public static void runMyQuery(PigServer pigServer, String inputFile, String outputFile)
			throws IOException {
		pigServer.registerJar("Libstemmer-1.0.jar");
		pigServer.registerJar("BigTextsUDFs-1.0.jar");
		pigServer.registerQuery("A = load '" + inputFile
				+ "' using PigStorage(':');");
		pigServer.registerQuery("B = foreach A generate co.edu.javeriana.bigtextsudfs.gate.SNOWBALL_STEMMER((chararray)$0);");				
		pigServer.store("B", outputFile);
	}

}
