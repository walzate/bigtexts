package co.edu.javeriana.bigtexts.sandbox;

import java.io.IOException;
import java.util.Properties;
import org.apache.pig.ExecType;
import org.apache.pig.PigServer;

/**
 * 
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class PigTokenizerTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Properties props = new Properties();
        PigServer pigServer;
        try {
            pigServer = new PigServer(ExecType.MAPREDUCE, props);
            runMyQuery(pigServer, "passwd");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param pigServer
     * @param inputFile
     * @throws IOException
     * @see https://wiki.apache.org/pig/EmbeddedPig
     */
    public static void runMyQuery(PigServer pigServer, String inputFile)
            throws IOException {
        pigServer.registerQuery("A = load '" + inputFile + "' using PigStorage(':');");
        pigServer.registerQuery("B = foreach A generate flatten(TOKENIZE($0));");        

        pigServer.store("B", "myoutputTokenizer8");
    }

}
