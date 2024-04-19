package co.edu.javeriana.bigtexts.sandbox;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import org.apache.pig.ExecType;
import org.apache.pig.PigServer;

/**
 *
 * @author walzate
 */
public class PigWordCount {

    private final static Logger logger = Logger.getLogger(PigWordCount.class.getName());

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        logger.info("Inicio");
        try {
            Properties props = new Properties();
            //props.setProperty("fs.default.name", "hdfs://master:9000");
            //props.setProperty("mapred.job.tracker", "127.0.0.1:50030");
            PigServer pigServer = new PigServer(ExecType.MAPREDUCE, props);
            //runIdQuery(pigServer, "passwd");
            runMyQuery(pigServer, "passwd");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param pigServer
     * @param inputFile
     * @throws IOException
     * @see https://wiki.apache.org/pig/EmbeddedPig
     */
    public static void runMyQuery(PigServer pigServer, String inputFile) throws IOException {
        pigServer.registerQuery("A = load '" + inputFile + "' using PigStorage(':');");
        pigServer.registerQuery("B = foreach A generate flatten(TOKENIZE($0));");
        pigServer.registerQuery("C = group B by $0;");
        pigServer.registerQuery("D = foreach C generate flatten(group), COUNT(B.$0);");
        //pigServer.registerQuery("DUMP D;");
        //pigServer.registerQuery("D = order D by $ DESC;");
        //pigServer.registerQuery("DESCRIBE D;");

        pigServer.store("D", "myoutputA");
    }

    /**
     *
     * @param pigServer
     * @param inputFile
     * @throws IOException
     */
    public static void runIdQuery(PigServer pigServer, String inputFile)
            throws IOException {
        pigServer.registerQuery("A = load '" + inputFile
                + "' using PigStorage(':');");
        pigServer.registerQuery("B = foreach A generate $0 as id;");
        pigServer.store("B", "id.out");
    }
}
