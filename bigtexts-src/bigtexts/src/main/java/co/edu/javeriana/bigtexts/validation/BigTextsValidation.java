/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.bigtexts.validation;

import co.edu.javeriana.bigtexts.dto.Pipeline;
import co.edu.javeriana.bigtexts.dto.PreprocessingTask;
import co.edu.javeriana.bigtexts.dto.PreprocessingTaskParameter;
import co.edu.javeriana.bigtexts.enums.BigTextsConstants;
import co.edu.javeriana.bigtexts.enums.DeliveryMethodEnum;
import co.edu.javeriana.bigtexts.execution.ExecutionManager;
import co.edu.javeriana.bigtexts.pig.PigManager;
import co.edu.javeriana.bigtexts.util.DateUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import org.apache.avro.generic.GenericData;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author hduser
 */
public class BigTextsValidation {

    /**
     * La constante con la ubicación de los archivos
     */
    private static final String FOLDER = "src/main/resources/EHR/";

    private static final int MAX_FILE_ID = 200;

    private static final int MIN_FILE_ID = 1;

    /**
     * La instancia del log
     */
    private static final Logger logger = org.apache.log4j.Logger.getLogger(
            BigTextsValidation.class.getName());

    /**
     * Método principal de la clase
     *
     * @param args Los argumentos
     */
    public static void main(String[] args) {
        int numeroIteraciones = 5;
        generateTestSuite(numeroIteraciones);
    }

    public static List<PreprocessingTask> getRegexNer() {
        List<PreprocessingTask> preprocessingTasks = new ArrayList<>();

        PreprocessingTask regexNER = new PreprocessingTask();
        regexNER.setName("RegexNamedEntityRecognition");
        regexNER.setRequiredJars(Arrays.asList("stanford-corenlp-3.4.1.jar", "jollyday-0.4.7.jar", "bigtexts-udfs-1.0.jar"));
        regexNER.setCommands(Arrays.asList("co.edu.javeriana.bigtextsudfs.REGEXNER((chararray)$0)"));
        regexNER.setImplementationClass("co.edu.javeriana.bigtextsudfs.REGEXNER");

        PreprocessingTaskParameter param;
        param = new PreprocessingTaskParameter();
        param.setName("regexner.rules");
        param.setLabel("regexner.rules");
        param.setValue(null);

        List<PreprocessingTaskParameter> params = new ArrayList<>();
        params.add(param);
        regexNER.setParameters(params);
        preprocessingTasks.add(regexNER);

        return preprocessingTasks;
    }

    public static List<PreprocessingTask> getSnowball() {
        List<PreprocessingTask> preprocessingTasks = new ArrayList<>();

        PreprocessingTask tokenizer = new PreprocessingTask();
        tokenizer.setName("Tokenizer");
        tokenizer.setCommands(Arrays.asList("flatten(TOKENIZE((chararray)$0))"));
        preprocessingTasks.add(tokenizer);

        PreprocessingTask snowball = new PreprocessingTask();
        snowball.setName("SnowballStemmer-EN");
        snowball.setRequiredJars(Arrays.asList("libstemmer-1.0.jar", "bigtexts-udfs-1.0.jar"));
        snowball.setCommands(Arrays.asList("co.edu.javeriana.bigtextsudfs.SNOWBALL_STEMMER_EN((chararray)$0)"));
        preprocessingTasks.add(snowball);
        return preprocessingTasks;
    }

    public static List<PreprocessingTask> getTokenizer() {
        List<PreprocessingTask> preprocessingTasks = new ArrayList<>();

        PreprocessingTask tokenizer = new PreprocessingTask();
        tokenizer.setName("Tokenizer");
        tokenizer.setCommands(Arrays.asList("flatten(TOKENIZE((chararray)$0))"));
        preprocessingTasks.add(tokenizer);
        return preprocessingTasks;
    }

    public static List<PreprocessingTask> getPOS() {
        List<PreprocessingTask> preprocessingTasks = new ArrayList<>();

        PreprocessingTask tokenizer = new PreprocessingTask();
        tokenizer.setName("Tokenizer");
        tokenizer.setCommands(Arrays.asList("flatten(TOKENIZE((chararray)$0))"));
        preprocessingTasks.add(tokenizer);

        PreprocessingTask posTagger = new PreprocessingTask();
        posTagger.setName("POS-Tagger-EN");
        posTagger.setRequiredJars(Arrays.asList("stanford-corenlp-3.4.1.jar", "bigtexts-udfs-1.0.jar"));
        posTagger.setCommands(Arrays.asList("co.edu.javeriana.bigtextsudfs.POS_TAGGER_EN((chararray)$0)"));
        preprocessingTasks.add(posTagger);
        return preprocessingTasks;
    }

    public static List<PreprocessingTask> getPOS_ES() {
        List<PreprocessingTask> preprocessingTasks = new ArrayList<>();

        PreprocessingTask posTagger = new PreprocessingTask();
        posTagger.setName("POS-Tagger-ES");
        posTagger.setRequiredJars(Arrays.asList("stanford-corenlp-3.4.1.jar", "bigtexts-udfs-1.0.jar"));
        posTagger.setCommands(Arrays.asList("co.edu.javeriana.bigtextsudfs.POS_TAGGER_ES((chararray)$0)"));
        preprocessingTasks.add(posTagger);
        return preprocessingTasks;
    }

    public static List<String> getFilesList() {
        //String[] array = {"pg19699.txt", "pg5000.txt", "ResultadosConsultaTextoNarrativo.csv", "1.txt"};
        String[] array = {"ResultadosConsultaTextoNarrativo.csv"};
        List<String> filesList = Arrays.asList(array);
        return filesList;
    }

    public static List<String> chargeFilesCatalog() {
        List<String> result = new ArrayList<>();
        for (int i = MIN_FILE_ID; i <= MAX_FILE_ID; i++) {
            String fileName = i + ".txt";
            result.add(fileName);
        }
        return result;
    }

    public static void runPigScript() {
        PigManager.runPigScript("src/main/resources/pig/wordcount.pig");
    }

    public static void generateTestSuite(int iterations) {
        try {
            List<List<PreprocessingTask>> listOfLists = new ArrayList<>();

            /**listOfLists.add(getRegexNer());
            listOfLists.add(getSnowball());*/
            listOfLists.add(getTokenizer());

            for (String file : getFilesList()) {
                for (List<PreprocessingTask> preprocessingTasks : listOfLists) {
                    for (int i = 0; i < iterations; i++) {
                        Pipeline pipeline = new Pipeline();
                        String targetFolderName = DateUtils.getFormatedDate(new Date(), BigTextsConstants.BIG_TEXTS_DESTINATION_FOLDER_DATE_FORMAT);
                        pipeline.setTargetFolder(targetFolderName);
                        pipeline.setDeliveryMethod(DeliveryMethodEnum.HDFS);

                        File targetFolder = new File(BigTextsConstants.BIG_DFAULT_FTP_FOLDER + targetFolderName);
                        targetFolder.mkdir();
                        
                        File physicalFile = new File(FOLDER+file);
                        FileUtils.copyFileToDirectory(physicalFile, targetFolder);
                        
                        pipeline.setPreprocessingTasks(preprocessingTasks);
                        List<String> files = new ArrayList<String>();
                        files.add(file);
                        pipeline.setFiles(files);
                        System.out.println(pipeline.toString());

                        ExecutionManager.execute(pipeline, null);
                    }
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BigTextsValidation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive. The
     * difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value. Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     * @see
     * http://stackoverflow.com/questions/363681/generating-random-integers-in-a-range-with-java
     */
    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /**
     * Método que toma el archivo inicial de los registros clínicos y lo parte
     * en un archivo por registro
     */
    public static void splitEHRFile() {

        BufferedReader br = null;
        String sCurrentLine;
        try {
            //Se toma el archivo a partir
            br = new BufferedReader(new FileReader(FOLDER + "ResultadosConsultaTextoNarrativo.csv"));

            //Se inicializa el contador de archivos
            int fileCounter = 1;
            //Se crea el primer archivo
            File file = new File(FOLDER + fileCounter + ".txt");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            //Se lee la linea
            while ((sCurrentLine = br.readLine()) != null) {
                //Si es el primer archivo, se escribe y se suma uno al contador
                if (fileCounter == 1) {
                    fileCounter++;
                    bw.write(sCurrentLine);
                } //Si es un registro de otro archivo se cierra el archivo actual, 
                //se crea otro, se escribe y se le suma uno al contador
                else if (sCurrentLine.startsWith(fileCounter + ",")) {
                    bw.close();
                    fw.close();
                    file = new File(FOLDER + fileCounter + ".txt");
                    fw = new FileWriter(file.getAbsoluteFile());
                    bw = new BufferedWriter(fw);
                    bw.write(sCurrentLine);
                    fileCounter++;
                } //Si es una linea suelta, se escribe en el archivo actual
                else {
                    bw.write(sCurrentLine);
                }

                System.out.println(sCurrentLine);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }
    }
}
